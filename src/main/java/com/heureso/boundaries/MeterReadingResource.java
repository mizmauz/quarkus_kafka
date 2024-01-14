package com.heureso.boundaries;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.heureso.entities.MeterReadingRepository;
import com.heureso.entities.MeterReadingValueCache;
import com.heureso.events.Utils;
import com.heureso.mapping.MeterReading;
import io.opentelemetry.context.Context;
import io.smallrye.mutiny.CompositeException;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.TracingMetadata;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Path("consumption/meterreading")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class MeterReadingResource
{
    @Inject
    MeterReadingRepository meterReadingRepository;

    @Inject
    MeterReadingValueCache meterReadingValueCache;

      private static final Logger LOGGER = Logger.getLogger(MeterReadingResource.class.getName());

    @Channel("METERREADING_OUT")
    MutinyEmitter<MeterReading> meterReadingCreationEmitter;

    @GET
    public Uni<List<MeterReading>> get()
    {
        return meterReadingRepository.listAll()
            .invoke(
                item ->
                {
                    MeterReading reading = new MeterReading();
                    reading.id = Utils.generateId();
                    if ( item.size() > 0 )
                    {
                        reading = item.get(0);
                    }

                }
            );
    }

    @POST
    @Path("create")
    public Uni<Void> create(MeterReading meterreading) {

        LOGGER.info("Meterreading create requested");

        final String action;

        if (meterreading == null)
        {
            throw new WebApplicationException("No Payload.", 422);
        }

        if (meterreading.meternumber == null)
        {
            throw new WebApplicationException("Meternumber must not be null", 422);
        }

        if (meterreading.reading_date == null)
        {
            throw new WebApplicationException("ReadingDate must not be null", 422);
        }

        meterreading.id = Utils.generateId();
        meterReadingValueCache.set(meterreading.id.toString(), meterreading);

        OutgoingKafkaRecordMetadata<?> metadata = OutgoingKafkaRecordMetadata.builder()
            .withKey( String.valueOf(meterreading.id ))
            .build();
//        meterReadingCreationEmitter.send(Message.of(meterreading).addMetadata(metadata)).withAck(
//            () ->
//            {
//                return CompletableFuture.completedFuture(null);
//            }
//        );

        return meterReadingCreationEmitter.sendMessage(
            Message.of( meterreading )
                .addMetadata(metadata)
                .addMetadata(TracingMetadata.withCurrent(Context.current()) )
                .withAck(
                    () ->
                    {
                        return CompletableFuture.completedFuture(null);
                    }
                )
        ).onFailure().invoke(item -> LOGGER.error("Sending Error " + item.toString() ));

        //return meterreading;
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception>
    {
        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {

            LOGGER.errorf("Failed to handle request. %s", exception.getMessage() );

            Throwable throwable = exception;

            int code = 500;
            if (throwable instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            if (throwable instanceof CompositeException) {
                throwable = ((CompositeException) throwable).getCause();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", throwable.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", throwable.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}

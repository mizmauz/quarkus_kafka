package com.heureso.boundaries;

import com.heureso.events.MeterReadingEvent;
import com.heureso.entities.MeterReading;
import com.heureso.mapping.MeterReadingMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@ApplicationScoped
public class CreationProcessorCache
{

    private static final Logger LOGGER = Logger.getLogger(CreationProcessorCache.class.getName());

    @ActivateRequestContext
    @Incoming("METERREADING_IN")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)

    public Uni<Void> process(Message<MeterReadingEvent> meterreadingmessage)
    {
        MeterReadingEvent meterreadingevent = meterreadingmessage.getPayload();

        Optional<IncomingKafkaRecordMetadata> incomingKafkaRecordMetadata = meterreadingmessage.getMetadata(IncomingKafkaRecordMetadata.class);
        //incomingTrace(incomingKafkaRecordMetadata.get() );

        Long key = Long.parseLong(incomingKafkaRecordMetadata.get().getRecord().key().toString());
        String topic = incomingKafkaRecordMetadata.get().getTopic();

        MeterReadingMapper mapper = Mappers.getMapper(MeterReadingMapper.class);
        MeterReading reading = mapper.toSchema(meterreadingevent);

        LOGGER.info("Create new Creation Request " + key);
        return Uni.createFrom().voidItem();


    }


}

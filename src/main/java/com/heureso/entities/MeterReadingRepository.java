package com.heureso.entities;

import com.heureso.mapping.MeterReading;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MeterReadingRepository implements PanacheRepository<MeterReading>
{

    private static final Logger LOGGER = Logger.getLogger(MeterReadingRepository.class.getName());


    public Uni<List<MeterReading>> findMeterReadingbyMeternumberAsc(String meternumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("meternumber", meternumber);
        return list("meternumber =:meternumber order by reading_date asc", params)
            .onItem().ifNull().fail()
            .onFailure()
            .invoke(item -> LOGGER.errorf("error", item.getMessage()));
    }

        public Uni<List<MeterReading>> findMeterReadingbyMeternumberDesc(String meternumber) {
            Map<String, Object> params = new HashMap<>();
            params.put("meternumber", meternumber);
            return list("meternumber =:meternumber order by reading_date desc", params)
                .onItem().ifNull().fail()
                .onFailure()
                .invoke(item -> LOGGER.errorf("error", item.getMessage()));
        }
}

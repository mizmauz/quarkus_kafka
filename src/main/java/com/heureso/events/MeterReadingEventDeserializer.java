package com.heureso.events;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class MeterReadingEventDeserializer extends ObjectMapperDeserializer<MeterReadingEvent> {
    public MeterReadingEventDeserializer(){
        // pass the class to the parent.
        super(MeterReadingEvent.class);
    }
}

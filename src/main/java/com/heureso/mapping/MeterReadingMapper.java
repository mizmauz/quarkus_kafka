package com.heureso.mapping;

import com.heureso.events.MeterReadingEvent;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = "cdi")
@Mapper(componentModel = "jakarta")
public interface MeterReadingMapper
{
    MeterReadingMapper INSTANCE = Mappers.getMapper(  MeterReadingMapper.class );

    //@Mapping(target = "id", ignore = true)
    MeterReading toSchema(MeterReadingEvent meterreadingevent);

    @AfterMapping
    default void addPeriods(@MappingTarget MeterReading reading, MeterReadingEvent meterreadingevent)
    {
        reading.calc_consumption = 0.0;
        reading.calc_days = 0;
        reading.readingamount_estimated = 0.0;

    }

}

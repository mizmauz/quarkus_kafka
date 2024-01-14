package com.heureso.mapping;

import com.heureso.events.MeterReadingEvent;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-14T20:53:10+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Singleton
@Named
public class MeterReadingMapperImpl implements MeterReadingMapper {

    @Override
    public MeterReading toSchema(MeterReadingEvent meterreadingevent) {
        if ( meterreadingevent == null ) {
            return null;
        }

        MeterReading meterReading = new MeterReading();

        meterReading.setId( meterreadingevent.id );
        meterReading.meternumber = meterreadingevent.meternumber;
        meterReading.reading_date = meterreadingevent.getReading_date();
        meterReading.reading_time = meterreadingevent.reading_time;
        meterReading.readingsource = meterreadingevent.readingsource;
        meterReading.readingreason = meterreadingevent.readingreason;
        meterReading.unitofreading = meterreadingevent.unitofreading;
        meterReading.readingamount = meterreadingevent.readingamount;
        meterReading.calc_consumption = meterreadingevent.calc_consumption;
        meterReading.calc_days = meterreadingevent.calc_days;
        meterReading.readingstatus = meterreadingevent.readingstatus;

        addPeriods( meterReading, meterreadingevent );

        return meterReading;
    }
}

package com.heureso.events;

import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

public class MeterReadingEvent
{

    public MeterReadingEvent()
    {
    }

    @Id
    public Long id;


    public String meternumber;


    public String meterlocation;


    public String obisKennzahl;


    public LocalDate reading_date;


    public LocalTime reading_time;

    // Varianten für ENUM: https://thorben-janssen.com/hibernate-enum-mappings/
    //@Enumerated(EnumType.STRING)
    //private Gender gender;

    public Units.ReadingSource readingsource;


    public Units.ReadingReason readingreason;


    public Units.UnitofReading unitofreading;

    public Double readingamount;


    public Double calc_consumption;


    public int calc_days;

    public Units.ReadingStatus readingstatus;

    public LocalDate getReading_date() {
        return reading_date;
    }

    @Override
    public String toString() {
        return "MeterReadingEvent{" +
            "id=" + id +
            ", meternumber='" + meternumber + '\'' +
            ", meterlocation='" + meterlocation + '\'' +
            ", obisKennzahl='" + obisKennzahl + '\'' +
            ", reading_date=" + reading_date +
            ", reading_time=" + reading_time +
            ", readingsource=" + readingsource +
            ", readingreason=" + readingreason +
            ", unitofreading=" + unitofreading +
            ", readingamount=" + readingamount +
            ", calc_consumption=" + calc_consumption +
            ", calc_days=" + calc_days +
            ", readingstatus=" + readingstatus +
            '}';
    }
}

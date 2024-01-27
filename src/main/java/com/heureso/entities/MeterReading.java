package com.heureso.entities;


import com.heureso.events.Units;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NamedQuery;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NamedQuery(name = "MeterReading.findbyMeternumber", query = "SELECT r FROM MeterReading r WHERE r.meternumber = :meternumber ORDER BY r.reading_date desc, r.reading_time desc")
@NamedQuery(name = "MeterReading.findAll", query = "SELECT r FROM MeterReading r ORDER BY r.reading_date desc, r.reading_time desc")

//@Cacheable
public class MeterReading extends PanacheEntityBase
{

    @Id
//    @SequenceGenerator(
//           name ="meterreadingSequence",
//           sequenceName = "meterreadingId_seq",
//           allocationSize = 1,
//           initialValue = 1
//   )
//    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "meterreadingSequence")
    public Long id;

    @NotBlank(message="Meternumber may not be blank")
    @Column(length = 50)
    public String meternumber;

    @NotNull(message="Reading Date may not be blank")
    @Column(length = 8)
    public LocalDate reading_date;

    @NotNull(message="Reading Time may not be blank")
    @Column(length = 8)
    public LocalTime reading_time;

    // Varianten für ENUM: https://thorben-janssen.com/hibernate-enum-mappings/
    //@Enumerated(EnumType.STRING)
    //private Gender gender;
    @Column( length = 1)
    public Units.ReadingSource readingsource;

    @Column( length = 1)
    public Units.ReadingReason readingreason;

    @Column( length = 1)
    public Units.UnitofReading unitofreading;

    @ColumnDefault("0")
    public Double readingamount;

    @Transient
    @ColumnDefault("0")
    public Double readingamount_estimated = 0.0;


    @Transient // Internal Consumption
    @ColumnDefault("0")
    public Double calc_consumption = 0.0;

    @Transient // Internal Consumption
    @ColumnDefault("0")
    public int calc_days = 0;

    @Transient // Internal Consumption
    public double weight = 0;

    @Column( length = 1)
    public Units.ReadingStatus readingstatus;

    @Column( length = 1)
    public Units.ErrorCode errorcode;

    public MeterReading()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "MeterReading{" +
            "id=" + id +
            ", meternumber='" + meternumber + '\'' +
            ", reading_date=" + reading_date +
            ", reading_time=" + reading_time +
            ", readingsource=" + readingsource +
            ", readingreason=" + readingreason +
            ", unitofreading=" + unitofreading +
            ", readingamount=" + readingamount +
            ", readingamount_estimated=" + readingamount_estimated +
            ", calc_consumption=" + calc_consumption +
            ", calc_days=" + calc_days +
            ", weight=" + weight +
            ", readingstatus=" + readingstatus +
            ", errorcode=" + errorcode +
            '}';
    }
}

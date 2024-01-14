package com.heureso.events;

public class Units
{
    public enum ErrorCode
    {
        ok,
        checkDate,
        checkZeroCons,
        checkNumberReadings,
        checkNumberEstimations,
        checkToleraceAbsolute,
        checkOverrun
    }

    public enum ReadingReason {
        MOVEIN,MOVEOUT,TURNUS,INTERMEDIATE
    }

     public enum ReadingSource {
            CUSTOMER,SUPPLIER,DSO,MSB
            }

    public enum UnitofReading {
        KWH, KVAR, KW, M3
    }

    public enum ReadingStatus {
        RELEASED,
        UNPLAUSIBLE

    }
}

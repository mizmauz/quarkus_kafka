package com.heureso.events;

import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Singleton
public class Utils
{
    /**
     * GenerateID
     * 64 Bit ist zu lang, da Javascript nur eine Mantisse von 53 Bit verträgt
     *         // Rundung der letzten beiden Ziffern auf null: https://stackoverflow.com/questions/17320706/javascript-long-integer
     * @return
     */
    public static Long generateId()
    {
        long leftLimit = 1L;
        //long rightLimit = 999999999999999999L;  // 18 Stellen
        long rightLimit = 99999999999999L;

        long id = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return id;

    }

    public static Double round( Double value, int decimalplaces)
    {
        if ( decimalplaces < 0) throw new IllegalArgumentException();

        BigDecimal dcvalue = new BigDecimal(value);
        BigDecimal dcround = dcvalue.setScale(decimalplaces, RoundingMode.HALF_UP);
        return dcround.doubleValue();

    }

    public static boolean isBeforeOrEqual(LocalDate compareToDate, LocalDate basisdate) {
        if (basisdate == null || compareToDate == null) {
            return false;
        }
        return compareToDate.isBefore(basisdate) || compareToDate.isEqual(basisdate);
    }

    public static boolean isAfterOrEqual( LocalDate compareToDate, LocalDate basisdate) {
        if (basisdate == null || compareToDate == null) {
            return false;
        }
        return compareToDate.isAfter(basisdate) || compareToDate.isEqual(basisdate);
    }
}

package com.util;

import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Log4j2
public class DateTimeFormat {
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final long MINUTE = 60 * 1000;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long YEAR = DAY * 365;

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
    public static final DateFormat dateFormat3 = new SimpleDateFormat("MM");

    // format string YYYY-MM-DDTHH:mm:ss.SSSZ
    public static Timestamp stringToTimestamp(String timeString) {
        DateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp(dateFormat.parse(timeString).getTime());
        } catch (ParseException e) {
            log.error("", e);
        }
        return timestamp;
    }

    // return yyyy-MM-dd
    public static String getBasicDate(Date date) {

        if(date == null) {
            return "";
        }

        return dateFormat.format(date);
    }

    // return yyyy-MM
    public static String getBasicDateYear(Date date) {

        if(date == null) {
            return "";
        }

        return dateFormat2.format(date);
    }

    // return MM
    public static String getBasicMonth(Date date) {

        if(date == null) {
            return "";
        }

        return dateFormat3.format(date);
    }


    // return yyyyMMdd
    public static String getBasicDateTS(Timestamp timestamp) {

        if(timestamp == null) {
            return "";
        }

        return timestamp.toLocalDateTime().format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    // return yyyyMMddHHmmss
    public static String getBasicTimeSecond(Timestamp timestamp) {

        if(timestamp == null) {
            return "";
        }
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    // return yyyy-MM-dd
    public static String getLocalDate(Timestamp timestamp) {
        if(timestamp == null) {
            return "";
        }
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    // return HH:mm:ss
    public static String getLocalTime(Timestamp timestamp) {
        if(timestamp == null) {
            return "";
        }
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ISO_TIME);
    }

    // return yyyy/MM/dd HH:mm:ss
    public static String getTimeSecond(Timestamp timestamp) {

        if(timestamp == null) {
            return "";
        }
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public static String periodToString(Period period) {
        String date = "";
        if (period.isNegative()) {
            return "Đã hết hạn";
        } else {
            if (period.getYears() != 0) {
                date = period.getYears() + " năm ";
            }
            if (period.getMonths() != 0) {
                date = date + period.getMonths() + " tháng ";
            }
            if (period.getDays() != 0) {
                date = date + period.getDays() + " ngày";
            }
            return date;
        }
    }

}


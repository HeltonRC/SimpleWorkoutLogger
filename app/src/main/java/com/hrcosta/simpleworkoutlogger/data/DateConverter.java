package com.hrcosta.simpleworkoutlogger.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

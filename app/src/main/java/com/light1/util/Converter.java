package com.light1.util;

import androidx.room.TypeConverter;

import com.light1.model.Priority;

import java.util.Date;

public class Converter {

    @TypeConverter
    public static Date getDateFromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long getTimestampFromDate(Date value){
        return value == null ? null : value.getTime();
    }

    @TypeConverter
    public static String getStringFromPriority(Priority value){
        return value == null ? null : value.name();
    }

    @TypeConverter
    public static Priority getPriorityFromString(String value){
        return value == null ? null : Priority.valueOf(value);
    }


}

package com.light1.adapter.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyy年 MM月 dd号 EEEE HH点mm分");
        return simpleDateFormat.format(date);
    }
}

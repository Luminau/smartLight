package com.light1.adapter.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.light1.model.Priority;
import com.light1.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyy年MM月dd号HH点mm分");
        return simpleDateFormat.format(date);
    }

    public static String formatDateNoLetter2(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyyMMddHHmmss");
        return simpleDateFormat.format(date) + getDayNumInWeek(date);
    }

    public static String formatDateNoLetter(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyyMMddHHmm");
        return simpleDateFormat.format(date);
    }

    public static String getDayNumInWeek(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE", Locale.US);
//        simpleDateFormat.applyPattern("EEE");
//        return simpleDateFormat.format(date);
        String dayNumInWeek = simpleDateFormat.format(date);
        if (dayNumInWeek == "Mon") {
            return "1";
        } else if (dayNumInWeek.equals("Tue")) {
            return "2";
        } else if (dayNumInWeek.equals("Wed")) {
            return "3";
        } else if (dayNumInWeek.equals("Thu")) {
            return "4";
        } else if (dayNumInWeek.equals("Fri")) {
            return "5";
        } else if (dayNumInWeek.equals("Sat")) {
            return "6";
        } else if (dayNumInWeek.equals("Sun")) {
            return "7";
        } else {
            return "";
        }
    }

//    public static Long formatChineseToGBK(String s) {
//        try {
//            String res = URLEncoder.encode(s, "GBK");
//            res = res.replace("%", "");
//            return Long.parseLong(res, 16);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(Task task) {
        int color = 0;
        if (task.getPriority() == Priority.HIGH) {
            color = Color.argb(200, 201, 21, 23);
        } else if (task.getPriority() == Priority.MEDIUM) {
            color = Color.rgb(255, 179, 0);
        } else if (task.getPriority() == Priority.LOW) {
            color = 0xff33b5e5;
        } else if (task.getPriority() == Priority.VERYHIGH) {
            color = Color.BLACK;
        } else {
            color = Color.argb(200, 51, 181, 129);
        }
        return color;
    }
}

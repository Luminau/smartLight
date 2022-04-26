package com.light1.adapter.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.light1.model.Priority;
import com.light1.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyy年MM月dd号HH点mm分");
        return simpleDateFormat.format(date);
    }

    public static String formatDateNoLetter(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyyMMddHHmm");
        return simpleDateFormat.format(date);
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
            color = Color.argb(200, 155, 179, 0);
        } else if (task.getPriority() == Priority.LOW) {
            color = Color.argb(200, 51, 181, 129);
        }
        return color;
    }
}

package com.light1.adapter.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyy年 MM月 dd号 EEEE HH点mm分");
        return simpleDateFormat.format(date);
    }

    public static void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

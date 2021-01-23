package me.wangcai.dailyshop.utils;

import java.util.Calendar;

public class TimeUtil {
    public static int getDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
}

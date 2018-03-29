package com.raimvititor.paho_mqtt_test.libs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static Date convertChatTime(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
        try {
            return format.parse(strCurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String convertChatTime(Date CurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
        try {
            return format.format(CurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
            return format.format(new Date());
        }
    }
}

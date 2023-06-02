package io.studio.authservice.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 3:09
 */
public class TimeUtils {
    public static Timestamp getTimeByNow(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }
}

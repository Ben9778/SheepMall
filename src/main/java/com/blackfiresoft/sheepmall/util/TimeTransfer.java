package com.blackfiresoft.sheepmall.util;

import lombok.NonNull;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理字符串转时间戳的工具
 */
public class TimeTransfer {

    public static Timestamp StringToTimestamp(@NonNull String str) {
        Assert.notNull(str, "time param must not be null");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        try {
            Date date=format.parse(str);
            long time=date.getTime();
            return new Timestamp(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

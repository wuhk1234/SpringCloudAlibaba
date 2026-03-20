package com.wuhk.util;

import java.time.Duration;
import java.time.Instant;

/**
 * @className: SqlTime
 * @description: sql打印时间
 * @author: wuhk
 * @date: 2024/3/1 17:20
 * @version: 1.0
 * @company 航天信息
 **/

public class SqlTime {
    public static long sqlTime(){
        Instant start = Instant.now();
        Instant time = Instant.now();
        Duration duration = Duration.between(start,time);
        long times = duration.toMillis();
        return times;
    }
}

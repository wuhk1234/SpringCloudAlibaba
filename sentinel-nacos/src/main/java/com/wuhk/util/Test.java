package com.wuhk.util;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: Test
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/3 16:17
 * @version: 1.0
 * @company 航天信息
 **/

public class Test {
    public static void main(String[] args) {
        /*long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        System.out.println("max: " + (max/(double)1024/1024)+"MB");
        System.out.println("total: " + (total/(double)1024/1024)+"MB");
        long times  = SqlTime.sqlTime();
        System.out.println("times = " + times);*/
        /*String[] arr = new String[3];
        System.out.println("arr[0].length() = " + arr[0].length());*/
        List list1 = new ArrayList();
        list1.add(0);
        List list2 = list1;
        System.out.println(list1.get(0) instanceof Integer);
        System.out.println(list2.get(0) instanceof Integer);
        int x = 20;
        int y = 30;
        boolean b;
        b = x > 50 && y>60 || x > 50 && y < -60 || x < -50 && y>60 || x < -50 && y < -60;
        System.out.println("b = " + b);
    }
    public void a(){
        int x = 20;
        int y = 30;
        boolean b;
        b = x > 50 && y>60 || x > 50 && y < -60 || x < -50 && y>60 || x < -50 && y < -60;
        System.out.println("b = " + b);
    }
}

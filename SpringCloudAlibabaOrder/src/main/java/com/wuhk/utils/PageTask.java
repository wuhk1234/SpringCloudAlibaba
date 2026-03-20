package com.wuhk.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @className: PageTask
 * @description: 线程
 * @author: wuhk
 * @date: 2023/8/5 14:50
 * @version: 1.0
 * @company 航天信息
 **/
public class PageTask implements Runnable {

    private CountDownLatch           countDownLatch;
    private Sheet                    sheet;
    private String[]                 title;
    private CellStyle                style;
    private List<LinkedList<String>> list;
    public PageTask(CountDownLatch countDownLatch, Sheet sheet, String[] title, CellStyle style, List<LinkedList<String>> list) {
        this.countDownLatch = countDownLatch;
        this.sheet = sheet;
        this.title = title;
        this.style = style;
        this.list = list;
    }
    @Override
    public void run() {
        try {
            Row row = sheet.createRow(0);
            Cell cell = null;
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < list.size(); i++) {
                LinkedList<String> list1 = this.list.get(i);
                row = sheet.createRow(i + 1);
                for (int j = 0; j < title.length; j++) {
                    row.createCell(j).setCellValue(list1.get(j));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}



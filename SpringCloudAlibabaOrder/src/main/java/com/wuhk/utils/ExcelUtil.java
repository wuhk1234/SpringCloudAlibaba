package com.wuhk.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @className: ExcelUtil
 * @description: 通过线程池对文件进行批处理
 * @author: wuhk
 * @date: 2023/8/5 14:49
 * @version: 1.0
 * @company 航天信息
 **/
public class ExcelUtil {
    public static SXSSFWorkbook exportExcel(String[] title, String sheetName , List<LinkedList<String>> list) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        int count = 1;
        CountDownLatch downLatch = new CountDownLatch(count);
        Executor executor = newFixedThreadPool(count);
        SXSSFSheet sheet = wb.createSheet(sheetName);
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        executor.execute(new PageTask(downLatch, sheet, title, style, list));
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wb;
    }
}
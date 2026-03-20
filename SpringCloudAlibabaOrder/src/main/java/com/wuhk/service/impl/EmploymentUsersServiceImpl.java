package com.wuhk.service.impl;

import com.wuhk.entity.OrderInfo;
import com.wuhk.entity.User;
import com.wuhk.mapper.DownloadMapper;
import com.wuhk.service.EmploymentUsersService;
import com.wuhk.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @className: EmploymentUsersServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/5 14:16
 * @version: 1.0
 * @company 航天信息
 **/
@Service
@Slf4j
public class EmploymentUsersServiceImpl implements EmploymentUsersService {
    @Autowired
    private DownloadMapper downloadMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    public static final String[] TITLE = new String[]{"第1列", "第2列", "第3列", "第4列", "第5列", "第6列", "第7列", "第8列", "第9列", "第10列"};
    public static final String   SHEET_NAME = "sheet1";

    @Override
    public void downloadFailedUsingJson(HttpServletResponse response) {
        //excel文件名
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = dateFormat.format(new Date());
        String fileName = date + ".xlsx";
        //sheet页中的行数,行数数据；
        Integer num = 1000000;
        //此項构建自己的数据，其他按需修改
        List<LinkedList<String>> list = buildContent(num);
        System.out.println("list：{"+list.size()+"}");
        long start = System.currentTimeMillis();
        SXSSFWorkbook wb = ExcelUtil.exportExcel(TITLE, SHEET_NAME, list);
        long millis = System.currentTimeMillis() - start;
        long second = millis / 1000;
        System.out.println("SXSSF Page Thread 导出" + num + "条数据，花费：" + second + "s/ " + millis + "ms");
        writeAndClose(response, fileName, wb);
        wb.dispose();
    }

    @Override
    public void batchSave(List<OrderInfo> orderInfoList) {
        //批量新增处理,需要在jdbc连接那里添加rewriteBatchedStatements=true属性，批量新增才能生效
        // ExecutorType.SIMPLE: 这个执行器类型不做特殊的事情。它为每个语句的执行创建一个新的预处理语句。自动提交不关闭的前提下，默认设置是这个
        // ExecutorType.REUSE: 这个执行器类型会复用预处理语句。
        // ExecutorType.BATCH: 这个执行器会批量执行所有更新语句,如果 SELECT 在它们中间执行还会标定它们是 必须的,来保证一个简单并易于理解的行为。
        //如果自动提交设置为true,将无法控制提交的条数，改为最后统一提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            DownloadMapper testMapper = sqlSession.getMapper(DownloadMapper.class);
            orderInfoList.stream().forEach(orderInfo -> testMapper.batchSave(orderInfo));
            //提交数据
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public int batchExportCount() {
        return downloadMapper.batchExportCount();
    }

    @Override
    public List<OrderInfo> batchExport(int pageNum, int pageSize) {
        return downloadMapper.batchExport(pageNum, pageSize);
    }

    /**
     * 构建内容
     *
     * @param num
     * @return
     */
    private List<LinkedList<String>> buildContent(int num) {

        List<LinkedList<String>> resultList = new ArrayList<>();
        //小于当前的总行数
        for (int i=1; i<=num;i++){   //i的值会在1~4之间变化
            LinkedList<String> linkedList = new LinkedList<>();
            //合同编号
            linkedList.add("1");
            linkedList.add("2");
            linkedList.add("3");
            linkedList.add("4");
            linkedList.add("5");
            linkedList.add("6");
            linkedList.add("7");
            linkedList.add("8");
            linkedList.add("9");
            linkedList.add("10");
            resultList.add(linkedList);
        }
        return resultList;
    }

    private void writeAndClose(HttpServletResponse response, String fileName, Workbook wb) {
        try (OutputStream os = response.getOutputStream()){
            this.setResponseHeader(response, fileName);
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

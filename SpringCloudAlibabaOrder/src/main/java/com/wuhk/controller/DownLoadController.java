package com.wuhk.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wuhk.entity.OrderInfo;
import com.wuhk.entity.OrderInfoExcel;
import com.wuhk.service.EmploymentUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: DownLoadController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/5 14:14
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/download")
public class DownLoadController {
    @Autowired
    private EmploymentUsersService employmentUsersService;
    @RequestMapping(value = "/exportThread")
    @ResponseBody
    public void export(HttpServletResponse response) {
        employmentUsersService.downloadFailedUsingJson(response);
    }


    //批量新增数据处理
    @PostMapping(value = "/batchSave")
    public String batchSave() {
        //随机生成电话号码
        String[] start = {"130", "131", "132", "133", "134", "150", "151", "155", "158", "166", "180", "181", "184", "185", "188"};
        List<OrderInfo> orderInfoList=new ArrayList<>();
        //生成500万数据批量新增到mysql数据库里面
        for(int i=1;i<=9480;i++){
            OrderInfo orderInfo=new OrderInfo();
            orderInfo.setPeriod(202206);
            orderInfo.setAmount(new BigDecimal(i));
            orderInfo.setUserName("用户"+i);
            orderInfo.setPhone(start[(int) (Math.random() * start.length)]+(10000000+(int)(Math.random()*(99999999-10000000+1))));
            orderInfo.setCreator("用户"+i);
            orderInfo.setModifier("用户"+i);
            orderInfoList.add(orderInfo);
            //每一万条数据进行批量新增
            if(i%10000==0){
                employmentUsersService.batchSave(orderInfoList);
                //新增完成后清空list集合防止内存溢出
                orderInfoList.clear();
                System.out.println("当前已新增完数据："+i+"行");
            }
        }
        return "成功";
    }
    

    //批量导出数据到excel
    @GetMapping(value = "/batchExport")
    public void batchExport(HttpServletResponse response) {
        try(OutputStream outputStream =response.getOutputStream()){
            //查询出总数据量大小，这里为500万
            int count=employmentUsersService.batchExportCount();
            System.out.println("count="+count);
            //根据总数得到总页数
            int totalPage=(count-1)/100000+1;//总页数,每页10万行数据
            System.out.println("totalPage="+totalPage);
            //xlsx每个sheet页最大数据行为1048576,超过这个数值就会报错,所以这里将500万数据分5个导出到5个sheet页
            //根据总数得到每页sheet应该分几个sheet,每个sheet导入100万数据,500万就是5个sheet
            int totalSheet=(count-1)/1000000+1;//总sheet页数,每个sheet100万行数据
            System.out.println("totalSheet="+totalSheet);
            //设置初始页数
            OrderInfo orderInfo=new OrderInfo();
            orderInfo.setPageNum(0);//初始页,从0开始
            orderInfo.setPageSize(100000);//每页返回数据
            //文件名
            String fileName="批量测试导出.xlsx";
            //使用EasyExcel进行导出
            ExcelWriter excelWriter = EasyExcel.write(outputStream, OrderInfoExcel.class).build();
            //这里最终会写到5个sheet里面
            for (int i = 0; i < totalSheet; i++) {
                //writerSheet第一个参数表示往几个sheet开始写数据,从0开始表示第一个
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                //分页去数据库查询数据
                for(int j = orderInfo.getPageNum(); j < totalPage; j++){
                    //页数,对应后端数据库来说是索引
                    int pageNum=j*100000;
                    //每页要查询的行数
                    int pageSize=orderInfo.getPageSize();
                    //根据分页参数去查询每页数据
                    List<OrderInfo> data = employmentUsersService.batchExport(pageNum,pageSize);
                    excelWriter.write(data, writeSheet);
                    System.out.println("已导出数据:"+(pageNum+100000));
                    if((pageNum+100000)%1000000==0){
                        //记录当前页数j并加1,并跳出这个for循环,往下一个sheet页写入数据
                        orderInfo.setPageNum(j+1);
                        break;
                    }
                }
            }
            //下载
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");//以流的形式对文件进行下载
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));//对文件名编码,防止文件名乱码
            excelWriter.finish();
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.wuhk.controller;

import com.wuhk.service.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * @className: EmploymentController
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 22:33
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/api/emp")
public class EmploymentController {
    @Autowired
    private EmploymentService employmentService;
    /**
     * <p>单线程解析，单线程批量导入,只能导入一个sheet页的数据</p>
     * @method EmploymentController.importExcle()
     * @param file
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @RequestMapping(value = "/importExcle")
    public void importExcleThread(@RequestParam("file") MultipartFile file) throws IOException {
        employmentService.importExcle(file);
    }
    /**
     * <p>多线程解析，多线程批量导入,导入多个sheet页的数据</p>
     * @method EmploymentController.importExcleAsync()
     * @param file
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @RequestMapping(value = "/importExcleAsync")
    public void importExcleThreads(@RequestParam("file") MultipartFile file) throws IOException {
        employmentService.importExcleAsync(file);
    }
    /**
     * <p>多线程解析，多线程批量导入,导入多个sheet页的数据</p>
     * @method EmploymentController.importExcleAsync()
     * @param file
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @RequestMapping(value = "/importExcleAsync2")
    public void importExcleThreads2(@RequestParam("file") MultipartFile file) throws IOException {
        employmentService.importExcleAsync2(file);
    }
    /**
     * <p>单线程解析，单线程异步批量导入,只能导入一个sheet页的数据</p>
     * @method EmploymentController.importExcle()
     * @param file
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @PostMapping("/importExcleAsync1")
    public ResponseEntity<String> importExcleThreadAsync(@RequestParam("file") MultipartFile file) throws IOException {
        employmentService.importUsers(file);
        return ResponseEntity.ok("Excel import started successfully.");
    }


    /**
     * <p>监听器导出：未实现</p>
     * @method EmploymentController.importGenericListenerExcle()
     * @param file
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @RequestMapping(value = "/importGenericListenerExcle")
    public String importGenericListenerExcle(@RequestParam("file") MultipartFile file) throws IOException {
        try{
            employmentService.importGenericListenerExcle(file);
            return "导入成功！";
        }catch (Exception e){
            return "导入失败！";
        }
    }
    /**
     * <p>查全表，所有数据写入一个sheet页</p>
     * @method EmploymentController.expoerExcle1()
     * @param response
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @PostMapping(value = "/expoerExcle1")
    public void expoerExcle1(HttpServletResponse response) throws IOException {
        employmentService.expoerExcle1(response);
    }
    /**
     * <p>分页查询，每页数据写入每个sheet页</p>
     * @method EmploymentController.expoerExcle3()
     * @param response
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @PostMapping(value = "/expoerExcle3")
    public void expoerExcle3(HttpServletResponse response) throws IOException {
        employmentService.expoerExcle3(response);
    }
    /**
     * <p>多线程分页查询，每页数据写入每个sheet页</p>
     * @method EmploymentController.expoerExcle4()
     * @param response
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @PostMapping(value = "/expoerExcle4")
    public void expoerExcle4(HttpServletResponse response) throws IOException, InterruptedException {
        employmentService.expoerExcle4(response);
    }

    /**
     * <p>多线程分页查询，每页数据写入每个sheet页</p>
     * @method EmploymentController.expoerExcle4()
     * @param response
     * @return
     * @author wuhk
     * @date 2026/3/17 0017 18:08
     **/
    @PostMapping(value = "/expoerExcle5")
    public void expoerExcle5(HttpServletResponse response) throws IOException, InterruptedException {
        employmentService.expoerExcle5(response);
    }
}

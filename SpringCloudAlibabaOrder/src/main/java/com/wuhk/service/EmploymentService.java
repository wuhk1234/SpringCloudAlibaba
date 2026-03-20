package com.wuhk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuhk.entity.Employment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @className: EmploymentService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 20:11
 * @version: 1.0
 * @company 航天信息
 **/

public interface EmploymentService extends IService<Employment> {
    /*
     * 导入Excle数据-->方案2：单线程解析，单线程批量导入
     */
    void importExcle(MultipartFile file) throws IOException;
    /*
     * 导入Excle数据-->方案5：多线程解析，多线程批量导入
     */
    void importExcleAsync(MultipartFile file) throws IOException;
    void importExcleAsync2(MultipartFile file) throws IOException;
    /*
     * 导出Excle数据-->方案1：查全表，写入一个sheet页
     */
    void expoerExcle1(HttpServletResponse response) throws IOException;
    /*
     * 导出Excle数据-->方案3：分页查询，每页数据写入每个sheet页
     */
    void expoerExcle3(HttpServletResponse response) throws IOException;
    /*
     * 导出Excle数据-->方案4：多线程分页查询，每页数据写入每个sheet页
     */
    void expoerExcle4(HttpServletResponse response) throws IOException, InterruptedException;
    /*
     * 导出Excle数据-->方案5：分页查询，每页数据写入每个sheet页
     */
    void expoerExcle5(HttpServletResponse response) throws IOException, InterruptedException;
    /*
     * 使用通用监听导入数据
     */
    void importGenericListenerExcle(MultipartFile file);

    CompletableFuture<Integer> importUsers(MultipartFile file)throws IOException;
}

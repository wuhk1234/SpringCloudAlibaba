package com.wuhk.controller;

import com.google.protobuf.ServiceException;
import com.wuhk.entity.SysUserAddress;
import com.wuhk.service.ThreadTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: ThreadTransactionalController
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/2 0002 16:45
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/threadTest")
public class ThreadTransactionalController {
    @Autowired
    private ThreadTransactionalService threadTransactionalService;
    @RequestMapping("/insertData")
    public void threadTransactional() throws InterruptedException {
        //
        threadTransactionalService.a();
    }

    @RequestMapping("/insertDatatestMultiThreadTransactional")
    public void testMultiThreadTransactional() {
        //
        threadTransactionalService.testMultiThreadTransactional();
    }
    @RequestMapping("/saveThread1")
    public void saveThread1() throws ServiceException {
        //
        List< SysUserAddress > employeeDOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            employeeDOList.add(new SysUserAddress(i+1, "上海市" + (i + 1), "上海市", "浦东新区"));
        }
        threadTransactionalService.saveThread1(employeeDOList);
    }
}

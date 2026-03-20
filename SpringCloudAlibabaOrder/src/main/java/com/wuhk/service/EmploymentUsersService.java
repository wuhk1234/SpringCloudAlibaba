package com.wuhk.service;

import com.wuhk.entity.OrderInfo;
import com.wuhk.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @className: EmploymentUsersService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/5 14:16
 * @version: 1.0
 * @company 航天信息
 **/

public interface EmploymentUsersService {
    void downloadFailedUsingJson(HttpServletResponse response);
    void batchSave(List<OrderInfo> orderInfoList);
    int batchExportCount();
    List<OrderInfo> batchExport(int pageNum, int pageSize);
}

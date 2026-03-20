package com.wuhk.service;

import com.google.protobuf.ServiceException;
import com.wuhk.entity.SysUserAddress;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.sql.SQLException;
import java.util.List;

/**
 * @className: ThreadTransactionalService
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/2 0002 16:50
 * @version: 1.0
 * @company 航天信息
 **/

public interface ThreadTransactionalService {
    void a() throws InterruptedException;
    void b();
    void testMultiThreadTransactional();
    void saveSysUserAddressByTransaMan(PlatformTransactionManager transactionManager, List<TransactionStatus> transactionStatuses, List<SysUserAddress> sysUserAddressList);
    void saveThread(List<SysUserAddress> employeeDOList) throws SQLException, ServiceException;
    void saveThread1(List<SysUserAddress> employeeDOList) throws ServiceException;
}

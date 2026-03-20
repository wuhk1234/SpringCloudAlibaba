package com.wuhk.mapper;

import com.wuhk.entity.SysUserAddress;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface ThreadTransactionMapper {

    @Insert(value = "INSERT INTO `seata-stock`.`t_user` (`id`, `username`, `password`, `user_role`) VALUES ('1', 'tiankong', '221014', 'dd')")
    void ThreadTransactionalInsertA();

    @Insert(value = "INSERT INTO `seata-stock`.`t_user` (`id`, `username`, `password`, `user_role`) VALUES ('1', 'tiankong', '221014', 'dd')")
    void ThreadTransactionalInsertB();

    //@Insert(value = "INSERT INTO `seata-stock`.`t_user` (`id`, `username`, `password`, `user_role`) VALUES ('1', 'tiankong', '221014', 'dd')")
    void ThreadTransactionalInsertC(SysUserAddress sysUserAddress);

    void saveBatch(List<SysUserAddress> list);

    void delete(Object o);
}

package com.wuhk.mapper;

import com.wuhk.entity.OtherTable;

import java.util.List;

/**
 * @className: OtherTableMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 17:33
 * @version: 1.0
 * @company 航天信息
 **/

public interface OtherTableMapper {
    /**
     * Description:
     *
     * @author fanxb
     * @date 2019/3/26 10:16
     * @param dictionary dictionary
     * @return long
     */
    long addOne(OtherTable table);

    /**
     * Description:
     *
     * @author fanxb
     * @date 2019/3/26 16:31
     * @return java.util.List<com.fanxb.sjdemo.entity.OtherTable>
     */
    List<OtherTable> getAll();

}

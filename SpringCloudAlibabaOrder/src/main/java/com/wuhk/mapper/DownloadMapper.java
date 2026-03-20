package com.wuhk.mapper;

import com.wuhk.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @className: DownloadMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/8 10:58
 * @version: 1.0
 * @company 航天信息
 **/

public interface DownloadMapper {
    void batchSave(OrderInfo orderInfo);

    int batchExportCount();

    List<OrderInfo> batchExport(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}

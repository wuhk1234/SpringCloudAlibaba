package com.wuhk.mapper;

import com.wuhk.entity.RedPacketRecord;

import java.util.List;

/**
 * @className: RedPacketRecordMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 20:04
 * @version: 1.0
 * @company 航天信息
 **/

public interface RedPacketRecordMapper {
    List<RedPacketRecord> selectByExample(RedPacketRecord redPacketRecord);
    int updateByPrimaryKeySelective(RedPacketRecord redPacketRecord);
    int insertSelective(RedPacketRecord redPacketRecord);

}

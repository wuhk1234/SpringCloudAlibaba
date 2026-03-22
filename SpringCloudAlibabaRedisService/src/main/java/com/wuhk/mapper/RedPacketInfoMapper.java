package com.wuhk.mapper;

import com.wuhk.entity.RedPacketInfo;
import com.wuhk.entity.RedPacketInfoExample;

/**
 * @className: RedPacketInfoMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 20:05
 * @version: 1.0
 * @company 航天信息
 **/

public interface RedPacketInfoMapper {
    int updateByExampleSelective(RedPacketInfo redPacketInfo, RedPacketInfoExample redPacketInfoExample);
    int updateByPrimaryKeySelective(RedPacketInfo redPacketInfo);
    int insertSelective(RedPacketInfo redPacketInfo);
}

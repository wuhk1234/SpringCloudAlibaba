package com.wuhk.service;

import com.wuhk.common.ServiceResponse;
import com.wuhk.entity.RedPacketInfo;
import com.wuhk.entity.Result;

/**
 * @className: RedpacketService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 18:45
 * @version: 1.0
 * @company 航天信息
 **/

public interface RedpacketService {
    /**
     * 用户新增红包
     * @param uid 用户ID
     * @param totalNum 红包总数
     * @param totalAmount 红包总额
     */
    ServiceResponse addRedPacket(String uid, Integer totalNum, Integer totalAmount);

    /**
     * 用户抢红包
     * @param redPacketId 红包ID
     */
    ServiceResponse getRedPacket(String redPacketId);

    /**
     * 用户拆红包
     * @param redPacketId 红包ID
     * @param uid 用户ID
     */
    ServiceResponse unpackRedPacket(String redPacketId, String uid);

    /**
     * 获取红包
     * @param redPacketId
     * @return
     */
    RedPacketInfo get(long redPacketId);
    /**
     * 抢红包业务实现
     * @param redPacketId
     * @return
     */
    Result startSeckil(long redPacketId, int userId);

    /**
     * 微信抢红包业务实现
     * @param redPacketId
     * @param userId
     * @return
     */
    Result startTwoSeckil(long redPacketId,int userId);
}

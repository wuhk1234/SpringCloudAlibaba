package com.wuhk.mapper;

import com.wuhk.entity.UserScore;

import java.util.List;

/**
 * @className: userScoreMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 22:26
 * @version: 1.0
 * @company 航天信息
 **/

public interface UserScoreMapper {
    List<UserScore> selectByExample();
}

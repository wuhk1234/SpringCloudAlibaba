package com.wuhk.service.impl;

import com.wuhk.entity.Dictionary;
import com.wuhk.mapper.DictionaryMapper;
import com.wuhk.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: DictionaryServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:53
 * @version: 1.0
 * @company 航天信息
 **/
@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Override
    public long addOne(Dictionary dictionary) {
        this.dictionaryMapper.addOne(dictionary);
        return dictionary.getDictionaryId();
    }
}

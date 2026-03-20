package com.wuhk.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.wuhk.entity.Dictionary;
import com.wuhk.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

import java.util.Random;

/**
 * @className: DictionaryController
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:05
 * @version: 1.0
 * @company 航天信息
 **/

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping("/add")
    public long addOne(@RequestBody Dictionary dictionary) {
        Random rand = new Random();
        int randomNumber = rand.nextInt();
        dictionary.setDictionaryId((long) randomNumber);
        return this.dictionaryService.addOne(dictionary);
    }
}
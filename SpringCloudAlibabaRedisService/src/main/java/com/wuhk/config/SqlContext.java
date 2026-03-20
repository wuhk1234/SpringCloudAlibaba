package com.wuhk.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: SqlContext
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/14 0014 15:59
 * @version: 1.0
 * @company 航天信息
 **/
@Component
public class SqlContext {
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSession getSqlSession(){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }
}
package com.wuhk.config;

import com.wuhk.entity.Employment;
import com.wuhk.mapper.DownloadMapper;
import com.wuhk.mapper.EmploymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @className: DataInitializer
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 19:31
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private EmploymentMapper employmentMapper;
    @Override
    public void run(String... args) {
        log.info("开始初始化数据集......");
        Long l = employmentMapper.selectCount(null);
        if (l>0){
            log.info("初始化的数据有：",l);
            return;
        }
        List<Employment> list = generateTestData(1000);
        log.info("成功初始化{}条数据",list.size());
    }
    private List<Employment> generateTestData(int size){
        List<Employment> list = new ArrayList<>();
        Random random = new Random();
        String[] department = {"技术部","销售部","人事部","财务部","市场部","监管部"};
        String[] status = {"在职","离职","试用期"};
        for (int i = 0; i < size; i++) {
            Employment employment = new Employment();
            employment.setName("员工"+i+String.format("%04d",i));
            employment.setEmail("Employment"+i+"@Company.com");
            employment.setDepartment(department[random.nextInt(department.length)]);
            employment.setBaseSalary(BigDecimal.valueOf(5000+random.nextInt(20000)));
            employment.setBonus(BigDecimal.valueOf(random.nextInt(20000)));
            employment.setHireDate("2026-03-16");
            employment.setStatus(status[random.nextInt(status.length)]);
            list.add(employment);
        }
        return list;
    }
}

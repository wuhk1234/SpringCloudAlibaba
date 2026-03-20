package com.wuhk.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.wuhk.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: controller
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/13 20:41
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@Slf4j
public class SentinlController {
    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGREAD_RESOURCE_NAME = "degread";

    @RequestMapping("/hello")
    public String hello() {
        //定义Sentinel的资源名称
        Entry entry = null;
        try {
            entry = SphU.entry(RESOURCE_NAME);
            //被保护的业务逻辑
            String str = "hello world !";
            return str;
        }catch (BlockException el){
            //资源访问受阻，被限流活被降级
            log.error("block");
            //进行相应的处理操作
            return "被流控了！";
        }catch (Exception e){
            //若配置降级规则，则需要通过这种方式记录错误日志
            Tracer.traceEntry(e,entry);
        }finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return null;
    }
    @PostConstruct //初始化注解
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        //流控
        FlowRule flowRule = new FlowRule();
        //设置手保护的资源
        flowRule.setResource(RESOURCE_NAME);
        //设置流控规则QPS
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置受保护资源的阈值：set limt QPS to 20
        flowRule.setCount(1);
        rules.add(flowRule);

        //通过@SentinelResource注解流控规则，定义资源及配置和流控的处理方法
        FlowRule flowRule1 = new FlowRule();
        //设置手保护的资源
        flowRule1.setResource(USER_RESOURCE_NAME);
        //设置流控规则QPS
        flowRule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置受保护资源的阈值：set limt QPS to 20
        flowRule1.setCount(1);
        rules.add(flowRule1);
        //加载集合流控规则
        FlowRuleManager.loadRules(rules);

    }

    @PostConstruct //初始化注解
    private static void initDegread(){
        List<DegradeRule> rules = new ArrayList<>();
        //降级规则
        DegradeRule degradeRule = new DegradeRule();
        //设置受保护的资源
        degradeRule.setResource(DEGREAD_RESOURCE_NAME);
        //设置降级策略：异常数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        //触发熔断异常数：2
        degradeRule.setCount(2);
        //触发熔断最小请求数：2
        degradeRule.setMinRequestAmount(2);
        //统计时长
        degradeRule.setStatIntervalMs(60*1000);
        //
        degradeRule.setTimeWindow(10);
        rules.add(degradeRule);
        //加载集合流控规则
        DegradeRuleManager.loadRules(rules);

    }

    /**
     * <p>@SentinelResource注解：改善接口中定义和被流控降级后的处理方法
     *    使用方法：1.添加sentinel-annotation-aspectj切面依赖
     *             2.配置bean-SentinelResourceAspect
     *             3.添加@SentinelResource注解，
     *             4.value：定义资源
     *             5.blockHandler:设置降级后的处理方法（默认必须声明在同一个类中）
     *               5.1 如果不在统一个额额类中，通过blockHandlerClass来声明。例如：blockHandlerClass = User.class，方法必须是static的
     *             6.fallback：当借口出现了异常，就可以直接交给fallback方法进行处理
     *               6.1 5.1 如果不在统一个额额类中，通过fallbackClass来声明。例如：fallbackClass = User.class，方法必须是static的
     *             当blockHandler和fallback同时定义，blockHandler优先级更高
     * </p>
     * @method SentinlController.getUser()
     * @param
     * @return User
     * @author wuhk
     * @date 2023/3/14 8:56
     **/
    @RequestMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME,fallback = "fallbackHandler",/*blockHandlerClass = User.class,*/blockHandler = "blockHandlerForGetUser")
    public User getUser(){
        int a = 1/0;
        return new User("吴洪奎");
    }

    public User fallbackHandler(Throwable throwable){
        throwable.printStackTrace();
        return new User("异常处理！");
    }
    /**
     * <p>设置降级后的处理方法：
     *        1.必须是public方法
     *        2.返回值一定要和源方法保持一致，参数一致，顺序不变
     * </p>
     * @method SentinlController.blockHandlerForGetUser()
     * @param id
     * @return
     * @author wuhk
     * @date 2023/3/14 9:03
     **/
    public User blockHandlerForGetUser(BlockException e){
        e.printStackTrace();
        return new User("被限流了！！！！");
    }

    @RequestMapping("/degrade")
    @SentinelResource(value = DEGREAD_RESOURCE_NAME,entryType = EntryType.IN, blockHandler = "blockHandlerForDegrade")
    public User getDegread(String id){
        throw new RuntimeException("降级限流");
        //return new User("吴洪奎");
    }

    public User blockHandlerForDegrade(String id, BlockException e){
        e.printStackTrace();
        return new User("被熔断降级了！！！！");
    }
}

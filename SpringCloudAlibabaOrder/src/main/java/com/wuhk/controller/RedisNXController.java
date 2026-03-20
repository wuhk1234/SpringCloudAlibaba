package com.wuhk.controller;

import com.wuhk.utils.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: RedisNXController
 * @description: redis实现分布式锁
 * @author: wuhk
 * @date: 2026/3/10 0010 13:40
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/redisNX")
public class RedisNXController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    //Redis分布式锁的key
    public static final String REDIS_LOCK = "good_lock";
    @Autowired
    private Redisson redisson;

    /**
     * <p>单机数据一致性:
     * 测试结果出现多个用户购买同一商品，发生了数据不一致问题！
     * 通过ReentrantLock解决办法：单体应用的情况下，对并发的操作进行加锁操作，保证对数据的操作具有原子性</p>
     * @method RedisNXController.redisNx1()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:51
     **/
    @RequestMapping("/redsiNX1")
    public String redisNx1(){
        String result = redisTemplate.opsForValue().get("good:001").toString();
        int total = result == null ? 0:Integer.parseInt(result);
        if (total > 0){
            int reTotal = total - 1;
            redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
            return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
        }else {
            return "购买商品失败，当前库存暂无剩余";
        }
    }

    /**
     * <p>单机数据一致性:
     * 解决办法：单体应用的情况下，对并发的操作进行加锁操作，保证对数据的操作具有原子性
     * synchronized
     * ReentrantLock</p>
     * @method RedisNXController.redsiNX2()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:50
     **/
    @RequestMapping("/redsiNX2")
    public String redisNx2(){
        //使用ReentrantLock锁解决单体应用的并发问题
        Lock lock = new ReentrantLock();
        try{
            lock.lock();
            String result = redisTemplate.opsForValue().get("good:001").toString();
            int total = result == null ? 0:Integer.parseInt(result);
            if (total > 0){
                int reTotal = total - 1;
                redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
                return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
            }
        }catch (Exception e){
            lock.unlock();
        }finally {
            lock.unlock();
        }
        return "购买商品失败，当前库存暂无剩余";
    }
    /**
     * <p>分布式数据一致性:
     * 1.可以解决分布式架构中数据一致性问题。
     * @method RedisNXController.redsiNX3()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:50
     **/
    @RequestMapping("/redsiNX3")
    public String redisNx3(){
        //SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]
        //EX seconds 设置指定的到期时间(以秒为单位)
        //PX milliseconds 设置指定的到期时间(以毫秒为单位)
        //NX 仅在键不存在时设置键
        //XX 只有在键已存在时才设置</p>
        try{
            // 每个人进来先要进行加锁，key值为"good_lock"
            String uuid = UUID.randomUUID().toString().replace("_","");
            // key不加一个过期时间
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK,uuid);
            if (!flag) {
                return "抢锁失败";
            }
            String result = redisTemplate.opsForValue().get("good:001").toString();            int total = result == null ? 0:Integer.parseInt(result);
            if (total > 0){
                int reTotal = total - 1;
                redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
                return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
            }
        }catch (Exception e){
            redisTemplate.delete(REDIS_LOCK);
        }
        return "购买商品失败，当前库存暂无剩余";
    }

    /**
     * <p>分布式数据一致性:
     * 1.可以解决分布式架构中数据一致性问题。
     * 2.如果程序在运行期间，部署了微服务jar包的机器突然挂了，代码层面根本就没有走到finally代码块，
     * 也就是说在宕机前，锁并没有被删除掉，这样的话，就没办法保证解锁，所以加锁的同时就进行了设置过期时间,
     * @method RedisNXController.redsiNX4()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:50
     **/
    @RequestMapping("/redsiNX4")
    public String redisNx4(){
        //SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]
        //EX seconds 设置指定的到期时间(以秒为单位)
        //PX milliseconds 设置指定的到期时间(以毫秒为单位)
        //NX 仅在键不存在时设置键
        //XX 只有在键已存在时才设置</p>
        try{
            // 每个人进来先要进行加锁，key值为"good_lock"
            String uuid = UUID.randomUUID().toString().replace("_","");
            //// 为key加一个过期时间
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK,uuid,10L, TimeUnit.SECONDS);
            if (!flag) {
                return "抢锁失败";
            }
            String result = redisTemplate.opsForValue().get("good:001").toString();            int total = result == null ? 0:Integer.parseInt(result);
            if (total > 0){
                int reTotal = total - 1;
                redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
                return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
            }
        }catch (Exception e){
            redisTemplate.delete(REDIS_LOCK);
        }
        return "购买商品失败，当前库存暂无剩余";
    }

    /**
     * <p>分布式数据一致性:
     * 1.可以解决分布式架构中数据一致性问题。
     * 2.如果程序在运行期间，部署了微服务jar包的机器突然挂了，代码层面根本就没有走到finally代码块，
     * 也就是说在宕机前，锁并没有被删除掉，这样的话，就没办法保证解锁，所以加锁的同时就进行了设置过期时间,
     * 3.谁上的锁，谁才能删除
     * @method RedisNXController.redsiNX5()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:50
     **/
    @RequestMapping("/redsiNX5")
    public String redisNx5(){
        //SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]
        //EX seconds 设置指定的到期时间(以秒为单位)
        //PX milliseconds 设置指定的到期时间(以毫秒为单位)
        //NX 仅在键不存在时设置键
        //XX 只有在键已存在时才设置</p>
        // 每个人进来先要进行加锁，key值为"good_lock"
        String uuid = UUID.randomUUID().toString().replace("_","");
        try{
            // 为key加一个过期时间
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK,uuid,10L, TimeUnit.SECONDS);
            if (!flag) {
                return "抢锁失败";
            }
            String result = redisTemplate.opsForValue().get("good:001").toString();            int total = result == null ? 0:Integer.parseInt(result);
            if (total > 0){
                int reTotal = total - 1;
                redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
                return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
            }
        }catch (Exception e){

        }finally {
            // 谁加的锁，谁才能删除！！！！
            if (redisTemplate.opsForValue().get(REDIS_LOCK).equals(uuid)) {
                redisTemplate.delete(REDIS_LOCK);
            }
        }
        return "购买商品失败，当前库存暂无剩余";
    }

    /**
     * <p>分布式数据一致性:
     * 1.可以解决分布式架构中数据一致性问题。
     * 2.如果程序在运行期间，部署了微服务jar包的机器突然挂了，代码层面根本就没有走到finally代码块，
     * 也就是说在宕机前，锁并没有被删除掉，这样的话，就没办法保证解锁，所以加锁的同时就进行了设置过期时间,
     * 3.谁上的锁，谁才能删除
     * 4.规定了谁上的锁，谁才能删除，但finally快的判断和del删除操作不是原子操作，并发的时候也会出问题，
     * 并发嘛，就是要保证数据的一致性，保证数据的一致性，最好要保证对数据的操作具有原子性
     * @method RedisNXController.redsiNX6()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:50
     **/
    @RequestMapping("/redsiNX6")
    public String redisNx6() {
        //SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]
        //EX seconds 设置指定的到期时间(以秒为单位)
        //PX milliseconds 设置指定的到期时间(以毫秒为单位)
        //NX 仅在键不存在时设置键
        //XX 只有在键已存在时才设置</p>
        // 每个人进来先要进行加锁，key值为"good_lock"
        String uuid = UUID.randomUUID().toString().replace("_","");
        try{
            // 为key加一个过期时间
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK,uuid,10L, TimeUnit.SECONDS);
            if (!flag) {
                return "抢锁失败";
            }
            String result = redisTemplate.opsForValue().get("good:001").toString();            int total = result == null ? 0:Integer.parseInt(result);
            if (total > 0){
                int reTotal = total - 1;
                redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
                return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
            }
        }catch (Exception e){

        }finally {
            // 谁加的锁，谁才能删除，使用Lua脚本，进行锁的删除
            Jedis jedis = null;
            try{
                jedis = RedisUtils.getJedis();
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] " +
                        "then " +
                        "return redis.call('del',KEYS[1]) " +
                        "else " +
                        "   return 0 " +
                        "end";
                Object eval = jedis.eval(script, Collections.singletonList(REDIS_LOCK),Collections.singletonList(uuid));
                if("1".equals(eval.toString())){
                    System.out.println("-----del redis lock ok....");
                }else{
                    System.out.println("-----del redis lock error ....");
                }
            }catch (Exception e){

            }finally {
                if(null != jedis){
                    jedis.close();
                }
            }
        }
        return "购买商品失败，当前库存暂无剩余";
    }

    /**
     * <p>分布式数据一致性:
     * 1.可以解决分布式架构中数据一致性问题。
     * 2.如果程序在运行期间，部署了微服务jar包的机器突然挂了，代码层面根本就没有走到finally代码块，
     * 也就是说在宕机前，锁并没有被删除掉，这样的话，就没办法保证解锁，所以加锁的同时就进行了设置过期时间,
     * 3.谁上的锁，谁才能删除
     * 4.规定了谁上的锁，谁才能删除，但finally快的判断和del删除操作不是原子操作，并发的时候也会出问题，
     * 并发嘛，就是要保证数据的一致性，保证数据的一致性，最好要保证对数据的操作具有原子性
     * 5.规定了谁上的锁，谁才能删除，并且解决了删除操作没有原子性问题。但还没有考虑缓存续命，
     * 以及Redis集群部署下，异步复制造成的锁丢失：主节点没来得及把刚刚set进来这条数据给从节点，
     * 就挂了。所以直接上RedLock的Redisson落地实现
     * @method RedisNXController.redsiNX7()
     * @param
     * @return String
     * @author wuhk
     * @date 2026/3/10 0010 13:50
     **/
    @RequestMapping("/redsiNX7")
    public String redisNx7() {
        //SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]
        //EX seconds 设置指定的到期时间(以秒为单位)
        //PX milliseconds 设置指定的到期时间(以毫秒为单位)
        //NX 仅在键不存在时设置键
        //XX 只有在键已存在时才设置</p>
        // 每个人进来先要进行加锁，key值为"good_lock"
        RLock lock = redisson.getLock(REDIS_LOCK);
        lock.lock();
        String uuid = UUID.randomUUID().toString().replace("_","");
        try{
            // 为key加一个过期时间
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK,uuid,10L, TimeUnit.SECONDS);
            if (!flag) {
                return "抢锁失败";
            }
            String result = redisTemplate.opsForValue().get("good:001").toString();            int total = result == null ? 0:Integer.parseInt(result);
            if (total > 0){
                int reTotal = total - 1;
                redisTemplate.opsForValue().set("good:001",String.valueOf(reTotal));
                return "购买商品成功，当前库存还剩余：" + reTotal+ "件";
            }
        }catch (Exception e){

        }finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
        return "购买商品失败，当前库存暂无剩余";
    }
}

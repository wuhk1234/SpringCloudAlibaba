package com.wuhk.test;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @className: TestController
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/18 0018 14:09
 * @version: 1.0
 * @company 航天信息
 **/

public class TestController {
    public static void main(String[] args) throws InterruptedException {
        //Fail-fast ： 表示快速失败，在集合遍历过程中，一旦发现容器中的数据被修改了，会
        //立刻抛出 ConcurrentModificationException 异常，从而导致遍历失败，集合有HashMap，ArrayList
        /*Map<String ,String> map = new HashMap<>();
        map.put("1","11");
        map.put("2","22");
        map.put("3","33");
        map.put("4","44");
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            System.out.println(map.get(iterator.next()));
            map.put("5","55");
        }*/
        //Fail-safe，表示失败安全，也就是在这种机制下，出现集合元素的修改，不会抛出
        //ConcurrentModificationException。
        //原因是采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先
        //复制原有集合内容，
        //在拷贝的集合上进行遍历。由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中
        //对原集合所作的修改并不能被迭代器检测到
        //java.util.concurrent 包下的容器都是安全失败的,可以在多线程下并发使用,并发修改。
        //常见的的使用 fail-safe 方式遍历的容器有 ConcerrentHashMap 和
        //CopyOnWriteArrayList 等
        /*CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1,2,3,4,5,6,7});
        Iterator iterator1 = list.iterator();
        while (iterator1.hasNext()){
            Integer i = (Integer) iterator1.next();
            if (i == 7){
                list.add(15);
            }
        }*/

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        executorService.execute(() -> {
            try {
                Thread.sleep(3000);
                countDownLatch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        countDownLatch.await();
        executorService.shutdown();
    }
    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
}

package com.wuhk.test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @className: TestController
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/18 0018 14:09
 * @version: 1.0
 * @company 航天信息
 **/

public class TestController {
    public static void main(String[] args) {
        grapRed(10,6);
    }
    /**
     * 拆红包
     * @param price 红包总金额
     * @param person 红包个数
     */
    public static void grapRed(int price,int person){
        List<BigDecimal> moneys = math(BigDecimal.valueOf(price), person);
        if (moneys != null) {
            BigDecimal b = new BigDecimal(0);
            int num = 1;
            for (BigDecimal bigDecimal : moneys) {
                System.out.println("第" + num + "个人抢到：" + bigDecimal + "元    ");
                b = b.add(bigDecimal);
                num++;
            }
            for (int i = 0;i < moneys.size(); i++) {
                BigDecimal bigDecimal = moneys.get(i);
                if (bigDecimal.equals(Collections.max(moneys))) {
                    System.out.println("运气王是第"+(i + 1)+"个人，" + "金额最大值：" + Collections.max(moneys));
                }
            }
            System.out.println("红包总额：" + b + "元 ");
        }
    }
    /**
     * 计算每人获得红包金额;最小每人0.01元
     * @param redPrice  红包总额
     * @param personNumber 人数
     * @return
     */
    public static List<BigDecimal> math(BigDecimal redPrice, int personNumber) {
        if (redPrice.doubleValue() < personNumber * 0.01) {//发红包最少0.01*personNumber
            return null;
        }
        Random random = new Random();
        // 将红包总金额换算为单位分
        int money = redPrice.multiply(BigDecimal.valueOf(100)).intValue();
        double count = 0;// 随机数总额
        // 每人获得随机点数
        double[] arrRandom = new double[personNumber];
        // 每人获得钱数
        List<BigDecimal> arrMoney = new ArrayList<BigDecimal>(personNumber);
        // 循环人数 随机点
        for (int i = 0; i < arrRandom.length; i++) {
            int r = random.nextInt((personNumber) * 99) + 1;
            count += r;
            arrRandom[i] = r;
        }
        // 计算每人拆红包获得金额
        int c = 0;
        for (int i = 0; i < arrRandom.length; i++) {
            // 每人获得随机数相加 计算每人占百分比
            Double x = new Double(arrRandom[i] / count);
            // 每人通过百分比获得金额
            int m = (int) Math.floor(x * money);
            // 如果获得 0 金额，则设置最小值 1分钱
            if (m == 0) {
                m = 1;
            }
            c += m;// 计算获得总额
            // 如果不是最后一个人则正常计算
            if (i < arrRandom.length - 1) {
                arrMoney.add(new BigDecimal(m).divide(new BigDecimal(100)));
            } else {
                // 如果是最后一个人，则把剩余的钱数给最后一个人
                arrMoney.add(new BigDecimal(money - c + m).divide(new BigDecimal(100)));
            }
        }
        // 随机打乱每人获得金额
        Collections.shuffle(arrMoney);
        return arrMoney;
    }
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

        /*ExecutorService executorService = Executors.newFixedThreadPool(10);
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
    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();*/
}

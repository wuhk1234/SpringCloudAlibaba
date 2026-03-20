package com.config;

/**
 * @className: BloomFilterCalculator
 * @description: 参数计算公式
 * @author: wuhk
 * @date: 2026/3/9 0009 20:41
 * @version: 1.0
 * @company 航天信息
 **/

public class BloomFilterCalculator {
    /**
     * 计算最优位数组长度
     * @param n 预期元素数量
     * @param p 可接受误判率
     */
    public static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
    /**
     * 计算最优哈希函数个数
     * @param n 预期元素数量
     * @param m 位数组长度
     */
    public static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}

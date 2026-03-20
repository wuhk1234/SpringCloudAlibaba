package com.wuhk.listener;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @className: UploadDataListener
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/17 0017 17:27
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
public class UploadDataListener<T> implements ReadListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * Predicate用于过滤数据
     */
    private Predicate<T> predicate;
    /**
     * 调用持久层批量保存
     */
    private Consumer<Collection<T>> consumer;
    public UploadDataListener(Predicate<T> predicate, Consumer<Collection<T>> consumer) {
        this.predicate = predicate;
        this.consumer = consumer;
    }
    public UploadDataListener(Consumer<Collection<T>> consumer) {
        this.consumer = consumer;
    }
        /**
         * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
         *
         * @param demoDAO
         */

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if (predicate != null && !predicate.test(data)) {
            return;
        }
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            try {
                // 执行具体消费逻辑
                consumer.accept(cachedDataList);
            } catch (Exception e) {
                log.error("Failed to upload data!data={}", cachedDataList);
                throw new RuntimeException("导入失败");
            }
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if (CollUtil.isNotEmpty(cachedDataList)) {
            try {
                // 执行具体消费逻辑
                consumer.accept(cachedDataList);
                log.info("所有数据解析完成！");
            } catch (Exception e) {
                log.error("Failed to upload data!data={}", cachedDataList);
                // 抛出自定义的提示信息
                if (e instanceof RuntimeException) {
                    throw e;
                }
                throw new RuntimeException("导入失败");
            }
        }
    }
}
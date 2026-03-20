package com.wuhk.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ConverterUtils;
import com.wuhk.entity.Employment;
import com.wuhk.service.EmploymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.record.ConvertedRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @className: GenericEasyExcleListener
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 20:21
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
public class GenericEasyExcleListener<T> implements ReadListener<T> {
    private static final int BATCH_SIZE = 5000;
    private final int batchSize;
    private List<Map<Integer,String>> headData;
    private List<T> readData;
    private final int headRowCount;
    private Function<Map<Integer,String>,String> headDataCheck;
    private final Consumer<List<Map<Integer,String>>> headDataConsumer;
    private Function<T,String> readDataCheck;
    private final Consumer<List<T>> readDataConsumer ;
    private final List<String> errorList ;

    public GenericEasyExcleListener(int headRowCount,
                                    Function<Map<Integer, String>, String> headDataCheck,
                                    Consumer<List<Map<Integer, String>>> headDataConsumer,
                                    Function<T, String> readDataCheck,
                                    Consumer<List<T>> readDataConsumer) {
        this.headRowCount = headRowCount;
        this.batchSize = BATCH_SIZE;
        this.headDataCheck = headDataCheck;
        this.headDataConsumer = headDataConsumer;
        this.readDataCheck = readDataCheck;
        this.readDataConsumer = readDataConsumer;
        headData = new ArrayList<>(headRowCount);
        readData = new ArrayList<>(batchSize);
        errorList = new ArrayList<>();
    }
    public GenericEasyExcleListener(
                                    int headRowCount,
                                    int batchSize,
                                    Function<Map<Integer, String>, String> headDataCheck,
                                    Consumer<List<Map<Integer, String>>> headDataConsumer,
                                    Function<T, String> readDataCheck,
                                    Consumer<List<T>> readDataConsumer) {
        this.headRowCount = headRowCount;
        this.batchSize = batchSize;
        this.headDataCheck = headDataCheck;
        this.headDataConsumer = headDataConsumer;
        this.readDataCheck = readDataCheck;
        this.readDataConsumer = readDataConsumer;
        headData = new ArrayList<>(headRowCount);
        readData = new ArrayList<>(batchSize);
        errorList = new ArrayList<>();
    }

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        String checkRes = readDataCheck.apply(t);
        if (StringUtils.isNotBlank(checkRes)){
            int rowIndex =analysisContext.readRowHolder().getRowIndex()+1;
            errorList.add("行为号"+ rowIndex+checkRes);
            return;
        }
        readData.add(t);
        if (readData.size() >= batchSize){
            readDataConsumer.accept(readData);
            readData = new ArrayList<>(batchSize);
        }
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        readDataConsumer.accept(readData);

    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return false;
    }
    private List<Map<Integer,String>> getHeadData(){
        return headData;
    }
    private List<T> getReadData(){
        return readData;
    }

    private List<String> getErrorList(){
        return errorList;
    }
}

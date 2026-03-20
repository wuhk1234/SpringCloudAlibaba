package com.wuhk.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: ListPublicUse
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/14 0014 16:01
 * @version: 1.0
 * @company 航天信息
 **/

public class ListPublicUse {
    /**
     * 平均拆分list方法.
     * @param source
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;
        int number=source.size()/n;
        int offset=0;//偏移量，公众号Java精选，有惊喜！
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }
}

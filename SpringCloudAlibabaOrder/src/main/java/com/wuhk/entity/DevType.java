package com.wuhk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: DevType
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/22 10:58
 * @version: 1.0
 * @company 航天信息
 **/

@Data
@TableName("dev_type")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DevType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 等级(1 ，2，3)
     */
    private String level;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 子节点
     */
    private List<DevType> child;
}

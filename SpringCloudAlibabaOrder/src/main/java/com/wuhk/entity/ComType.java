package com.wuhk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @className: ComType
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/22 14:26
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@TableName("com_type")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ComType {
    private static final long serialVersionUID = 1L;
    private Long typeId;
    /**
     * 名称
     */
    private String typeName;

    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 子节点
     */
    private List<ComType> childList;
}

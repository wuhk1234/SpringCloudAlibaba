package com.wuhk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@TableName("user_score")
public class UserScore implements Serializable{

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userId;
    private int user_score;
    private String user_name;

}


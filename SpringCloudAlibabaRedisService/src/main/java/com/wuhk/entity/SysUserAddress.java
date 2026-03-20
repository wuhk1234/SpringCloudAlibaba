package com.wuhk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: SysUserAddress
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/12 0012 20:33
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class SysUserAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String userRole;

    public SysUserAddress(int id, String username, String password, String userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}

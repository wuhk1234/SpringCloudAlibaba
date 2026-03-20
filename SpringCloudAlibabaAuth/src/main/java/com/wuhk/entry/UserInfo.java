package com.wuhk.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: User
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/21 16:53
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String password;
    private String userRole;
}

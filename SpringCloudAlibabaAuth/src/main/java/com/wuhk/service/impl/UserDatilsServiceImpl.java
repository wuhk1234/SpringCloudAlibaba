package com.wuhk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuhk.entry.UserInfo;
import com.wuhk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @className: UserDatilsServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/21 16:40
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class UserDatilsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        //username就是在网址中填入的用户名
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username); //查找相同的用户名是否存在
        UserInfo user = userMapper.selectOne(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),passwordEncoder.encode(user.getPassword()), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUserRole())); //给UserDetails一个User，创建一个新的登录用户
    }
}

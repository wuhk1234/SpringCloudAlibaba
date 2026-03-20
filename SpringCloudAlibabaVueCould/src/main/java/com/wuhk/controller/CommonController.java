package com.wuhk.controller;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.wuhk.contants.Constant;
import com.wuhk.service.impl.SysAdminUserServiceImpl;
import com.wuhk.entity.SysAdminUser;
import com.wuhk.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @className: CommonController
 * @description: 公共工具控制器
 * @author: wuhk
 * @date: 2026/3/13 0013 09:43
 * @version: 1.0
 * @company 航天信息
 **/
public class CommonController {
	@Autowired
	private SysAdminUserServiceImpl sysAdminUserServiceImpl;
	
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public SysAdminUser getCurrentUser(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String authKey = request.getHeader(Constant.AUTH_KEY);
		if(StringUtils.isNotBlank(authKey)) {
			String decryptAuthKey = EncryptUtil.decryptBase64(authKey, Constant.SECRET_KEY);
			String[]  auths = decryptAuthKey.split("\\|");
			String username = auths[0];
			String password = auths[1];
			SysAdminUser record = new SysAdminUser();
			record.setUsername(username);
			record.setPassword(password);
			return sysAdminUserServiceImpl.selectOne(record);
		}
		return null;
	}

}

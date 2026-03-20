package com.wuhk.service.impl;

import com.wuhk.base.BaseServiceImpl;
import com.wuhk.mapper.SysSystemConfigMapper;
import com.wuhk.entity.SysSystemConfig;
import com.wuhk.service.SysSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class SysSystemConfigServiceImpl extends BaseServiceImpl<SysSystemConfig> implements SysSystemConfigService {
	@Autowired
	private SysSystemConfigMapper sysSystemConfigMapper;
	
	@Override
	public Mapper<SysSystemConfig> getMapper() {
		return sysSystemConfigMapper;
	}

}

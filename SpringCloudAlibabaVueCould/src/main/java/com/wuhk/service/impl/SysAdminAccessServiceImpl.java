package com.wuhk.service.impl;

import com.wuhk.base.BaseServiceImpl;
import com.wuhk.mapper.SysAdminAccessMapper;
import com.wuhk.entity.SysAdminAccess;
import com.wuhk.service.SysAdminAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class SysAdminAccessServiceImpl extends BaseServiceImpl<SysAdminAccess> implements SysAdminAccessService {
	
	@Autowired
	private SysAdminAccessMapper sysAdminAccessMapper;
	
	@Override
	public Mapper<SysAdminAccess> getMapper() {
		return sysAdminAccessMapper;
	}

}

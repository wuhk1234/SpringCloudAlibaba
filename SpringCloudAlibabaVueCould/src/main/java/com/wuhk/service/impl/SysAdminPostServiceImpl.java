package com.wuhk.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.wuhk.base.BaseServiceImpl;
import com.wuhk.entity.SysAdminPost;
import com.wuhk.service.SysAdminPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service
public class SysAdminPostServiceImpl extends BaseServiceImpl<SysAdminPost> implements SysAdminPostService {
	
	@Autowired
	private Mapper<SysAdminPost> sysAdminPostMapper;
	
	@Override
	public Mapper<SysAdminPost> getMapper() {
		return sysAdminPostMapper;
	}

	@Override
	public List<SysAdminPost> getDataList(String name) {
		Example example = new Example(SysAdminPost.class,false);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(name)){
			criteria.andLike("name", name);
		}
		return sysAdminPostMapper.selectByExample(example);
	}

}

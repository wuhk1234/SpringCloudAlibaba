package com.wuhk.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wuhk.base.BaseServiceImpl;
import com.wuhk.mapper.SysAdminGroupMapper;
import com.wuhk.entity.SysAdminGroup;
import com.wuhk.service.SysAdminGroupService;
import com.wuhk.util.BeanToMapUtil;
import com.wuhk.util.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SysAdminGroupServiceImpl extends BaseServiceImpl<SysAdminGroup> implements SysAdminGroupService {
	@Autowired
	private SysAdminGroupMapper sysAdminGroupMapper;
	
	@Override
	public Mapper<SysAdminGroup> getMapper() {
		return sysAdminGroupMapper;
	}
	/**
	 * 列表
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDataList() {
		Example example = new Example(SysAdminGroup.class);
		List<SysAdminGroup> rootSysAdminGroups = sysAdminGroupMapper.selectByExample(example);
		Map<String, String> fields = Maps.newHashMap();
		fields.put("cid", "id");
		fields.put("fid", "pid");
		fields.put("name", "title");
		fields.put("fullname", "title");
		List<Map<String, Object>> rawList = Lists.newArrayList();
		rootSysAdminGroups.forEach((m)->{
			rawList.add(BeanToMapUtil.convertBean(m));
		});
		Category cate = new Category(fields, rawList);
		return cate.getList(Integer.valueOf("0"));
	}

}

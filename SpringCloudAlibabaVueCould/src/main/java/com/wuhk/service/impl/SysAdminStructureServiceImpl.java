package com.wuhk.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wuhk.base.BaseServiceImpl;
import com.wuhk.mapper.SysAdminStructureMapper;
import com.wuhk.entity.SysAdminStructure;
import com.wuhk.service.SysAdminStructureService;
import com.wuhk.util.BeanToMapUtil;
import com.wuhk.util.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SysAdminStructureServiceImpl extends BaseServiceImpl<SysAdminStructure> implements SysAdminStructureService {

	@Autowired
	private SysAdminStructureMapper sysAdminStructureMapper;
	
	@Override
	public Mapper<SysAdminStructure> getMapper() {
		return sysAdminStructureMapper;
	}

	@Override
	public List<Map<String, Object>> getDataList() {
		Example example = new Example(SysAdminStructure.class);
		example.setOrderByClause(" id asc");
		List<SysAdminStructure> rootSysAdminStructure = sysAdminStructureMapper.selectByExample(example);
		Map<String, String> fields = Maps.newHashMap();
		fields.put("cid", "id");
		fields.put("fid", "pid");
		fields.put("name", "name");
		fields.put("fullname", "title");
		List<Map<String, Object>> rawList = Lists.newArrayList();
		rootSysAdminStructure.forEach((m)->{
			rawList.add(BeanToMapUtil.convertBean(m));
		});
		Category cate = new Category(fields, rawList);
		return cate.getList(Integer.valueOf("0"));
	}

}

package com.wuhk.controller;

import com.wuhk.service.impl.SysSystemConfigServiceImpl;
import com.wuhk.entity.SysSystemConfig;
import com.wuhk.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: SysConfigController
 * @description: 系统配置 控制层
 * @author: wuhk
 * @date: 2026/3/13 0013 18:36
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/admin")
@Api(value = "SysConfigController", description = "系统配置接口")
public class SysConfigController extends CommonController{
	@Autowired
	private SysSystemConfigServiceImpl sysSystemConfigServiceImpl;
	
	@ApiOperation(value = "获取配置", httpMethod="POST")
	@PostMapping(value = "/configs", produces = {"application/json;charset=UTF-8"})
	public String configs(@RequestBody(required=false) SysSystemConfig record, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<SysSystemConfig> configs = sysSystemConfigServiceImpl.select(record);
		for (SysSystemConfig c : configs) {
			data.put(c.getName(), c.getValue());
		}
		return FastJsonUtils.resultSuccess(200, "查询配置成功", data);
	}
}

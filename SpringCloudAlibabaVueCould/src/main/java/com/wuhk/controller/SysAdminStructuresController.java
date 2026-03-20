package com.wuhk.controller;

import com.wuhk.service.impl.SysAdminStructureServiceImpl;
import com.wuhk.entity.SysAdminStructure;
import com.wuhk.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @className: SysAdminStructuresController
 * @description: 系统权限控制层
 * @author: wuhk
 * @date: 2026/3/13 0013 18:00
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/admin/structures")
@Api(value = "SysAdminStructuresController", description = "系统权限接口")
public class SysAdminStructuresController extends CommonController{
	@Autowired
	private SysAdminStructureServiceImpl sysAdminStructureServiceImpl;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", httpMethod="GET")
	@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String index(HttpServletRequest request) {
		List<Map<String, Object>> goups = sysAdminStructureServiceImpl.getDataList();
		return FastJsonUtils.resultSuccess(200, "成功", goups);
	}
	
	/**
	 * 读取
	 */
	@ApiOperation(value = "编辑", httpMethod="GET")
	@GetMapping(value = "edit/{id}", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String read(@PathVariable Integer id, HttpServletRequest request) {
		SysAdminStructure goup = sysAdminStructureServiceImpl.selectByPrimaryKey(id);
		return FastJsonUtils.resultSuccess(200, "成功", goup);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存", httpMethod="POST")
	@PostMapping(value = "save", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String save(@RequestBody(required=false) SysAdminStructure record, HttpServletRequest request) {
		int row = sysAdminStructureServiceImpl.save(record);
		if(row == 0) {
			return FastJsonUtils.resultError(-200, "保存失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "成功", null);
	}
	
	
	/**
	 * 更新
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "update", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String update(@RequestBody(required=false) SysAdminStructure record, HttpServletRequest request) {
		int row = sysAdminStructureServiceImpl.save(record);
		if(row == 0) {
			return FastJsonUtils.resultError(-200, "更新失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "更新成功", null);
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除")
	@DeleteMapping(value = "delete/{id}", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int row = sysAdminStructureServiceImpl.deleteByPrimaryKey(id);
		if(row == 0) {
			return FastJsonUtils.resultError(-200, "删除失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "删除成功", null);
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "根据ids批量删除")
	@PostMapping(value = "deletes", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deletes(@RequestBody Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		List<Integer> ids = (List<Integer>)params.get("ids");
		if (CollectionUtils.isEmpty(ids)) {
			return FastJsonUtils.resultError(-200, "操作失败", null);
		}
		try {
			for (int i = 0; i < ids.size(); i++) {
				sysAdminStructureServiceImpl.deleteByPrimaryKey(ids.get(i));
			}
		} catch (Exception e) {
			return FastJsonUtils.resultError(-200, "保存失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "成功", null);
	}
	
	/**
	 * 启用或禁用
	 */
	@ApiOperation(value = "根据ids批量启用或禁用")
	@PostMapping(value = "enables", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String enables(@RequestBody Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		List<Integer> ids = (List<Integer>)params.get("ids");
		byte status = Byte.valueOf(params.get("status").toString());
		if (CollectionUtils.isEmpty(ids)) {
			return FastJsonUtils.resultError(-200, "操作失败", null);
		}
		try {
			for (int i = 0; i < ids.size(); i++) {
				SysAdminStructure record = new SysAdminStructure();
				record.setId(Integer.valueOf(ids.get(0)));
				record.setStatus(status);
				sysAdminStructureServiceImpl.updateByPrimaryKeySelective(record);
			}
		} catch (Exception e) {
			return FastJsonUtils.resultError(-200, "保存失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "成功", null);
	}
}

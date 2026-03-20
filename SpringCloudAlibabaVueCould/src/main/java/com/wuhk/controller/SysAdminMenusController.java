package com.wuhk.controller;

import com.wuhk.service.impl.SysAdminMenuServiceImpl;
import com.wuhk.entity.SysAdminMenu;
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
 * @className: SysAdminMenusController
 * @description: 系统菜单控制层
 * @author: wuhk
 * @date: 2026/3/11 0013 12:43
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/admin/menus")
@Api(value = "SysAdminMenusController", description="系统菜单接口")
public class SysAdminMenusController extends CommonController{
	@Autowired
	private SysAdminMenuServiceImpl sysAdminMenuServiceImpl;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", httpMethod="GET")
	@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String index(@RequestBody(required=false) SysAdminMenu record, HttpServletRequest request) {
		List<Map<String, Object>> menus = sysAdminMenuServiceImpl.getDataList(this.getCurrentUser().getId(), record == null ? null : record.getStatus());
		return FastJsonUtils.resultSuccess(200, "成功", menus);
	}
	
	/**
	 * 读取
	 */
	@ApiOperation(value = "编辑", httpMethod="GET")
	@GetMapping(value = "edit/{id}", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String read(@PathVariable Integer id, HttpServletRequest request) {
		SysAdminMenu menu = sysAdminMenuServiceImpl.selectByPrimaryKey(id);
		return FastJsonUtils.resultSuccess(200, "成功", menu);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存", httpMethod="POST")
	@PostMapping(value = "save", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String save(@RequestBody(required=false) SysAdminMenu record, HttpServletRequest request) {
		int row = sysAdminMenuServiceImpl.save(record);
		if(row == 0) {
			return FastJsonUtils.resultError(-200, "保存失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "成功", null);
	}
	
	
	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", httpMethod="POST")
	@PostMapping(value = "update", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String update(@RequestBody(required=false) SysAdminMenu record, HttpServletRequest request) {
		int row = sysAdminMenuServiceImpl.save(record);
		if(row == 0) {
			return FastJsonUtils.resultError(-200, "操作失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "操作成功", null);
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除")
	@DeleteMapping(value = "delete/{id}", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int row = sysAdminMenuServiceImpl.deleteByPrimaryKey(id);
		if(row == 0) {
			return FastJsonUtils.resultError(-200, "操作失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "操作成功", null);
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
				//sysAdminMenuService.deleteByPrimaryKey(ids.get(i));
			}
		} catch (Exception e) {
			return FastJsonUtils.resultError(-200, "操作失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "操作成功", null);
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
				SysAdminMenu record = new SysAdminMenu();
				record.setId(Integer.valueOf(ids.get(0)));
				record.setStatus(status);
				sysAdminMenuServiceImpl.updateByPrimaryKeySelective(record);
			}
		} catch (Exception e) {
			return FastJsonUtils.resultError(-200, "操作失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "成功", null);
	}
}

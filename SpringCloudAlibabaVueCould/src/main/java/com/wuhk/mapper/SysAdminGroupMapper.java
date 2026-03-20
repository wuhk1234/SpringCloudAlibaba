package com.wuhk.mapper;

import com.wuhk.entity.SysAdminGroup;
import com.wuhk.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAdminGroupMapper extends MyMapper<SysAdminGroup> {
	/**
	 * 查询分组信息
	 * @param userId 用户ID
	 * @param status 状态
	 * @return
	 */
	List<SysAdminGroup> selectByUserId(@Param("userId") Integer userId, @Param("status") Byte status);
}
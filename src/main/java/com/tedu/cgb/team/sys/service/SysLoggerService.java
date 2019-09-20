package com.tedu.cgb.team.sys.service;

import org.apache.ibatis.annotations.Param;

import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.common.entity.Logger;

public interface SysLoggerService {
	
	Page<Logger> findPage(String username, Integer pageCurrent);
	int deleteByIds(Integer... ids);
	void insertRecord(@Param("logger") Logger logger);
}

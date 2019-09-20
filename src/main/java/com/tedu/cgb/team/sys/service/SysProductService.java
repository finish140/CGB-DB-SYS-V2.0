package com.tedu.cgb.team.sys.service;

import java.util.Map;

import com.tedu.cgb.team.common.vo.Page;

public interface SysProductService {
	
	/**
	 * 根据context字段查找数据并封装到Page对象，
	 * 当context为null或空串时查询所有记录
	 * @param name
	 * @return
	 */
	Page<Map<String, Object>> findPage(String context, Integer pageCurrent);

}

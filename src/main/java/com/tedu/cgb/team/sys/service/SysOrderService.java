package com.tedu.cgb.team.sys.service;

import java.util.Map;

import com.tedu.cgb.team.common.vo.Page;

public interface SysOrderService {

	/**
	 * 根据当前页码查询订单和产品数据，
	 * 多条记录封装在List中，一条记录封装了订单数据和对应的产品信息的Map里，多条产品信息封装List中
	 * @param pageCurrent
	 * @return
	 */
	Page<Map<String, Object>> findPage(Integer pageCurrent);

}

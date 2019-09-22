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

	/**
	 * 根据单个或多个订单id删除数据和关系表数据
	 * @param ids
	 * @return
	 */
	int deleteObjectsByIds(Integer[] ids);

	/**
	 * 根据订单id删除关系表的对应产品id，
	 * 如果订单不再与产品有关系数据，删除此订单数据
	 * @param id
	 * @return
	 */
	int removeProduct(Integer orderId, Integer productId);

	/**
	 * 根据订单id和产品id修改对应的产品数量，
	 * @param id
	 * @return
	 */
	int updateTotal(Integer orderId, Integer productId, Integer total);

}

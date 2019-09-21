package com.tedu.cgb.team.sys.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tedu.cgb.team.common.dao.OrderMapper;
import com.tedu.cgb.team.common.dao.OrderProductMapper;
import com.tedu.cgb.team.common.dao.ProductMapper;
import com.tedu.cgb.team.common.entity.Order;
import com.tedu.cgb.team.common.entity.OrderExample;
import com.tedu.cgb.team.common.entity.OrderProduct;
import com.tedu.cgb.team.common.entity.OrderProductExample;
import com.tedu.cgb.team.common.entity.OrderProductExample.Criteria;
import com.tedu.cgb.team.common.entity.Product;
import com.tedu.cgb.team.common.entity.ProductExample;
import com.tedu.cgb.team.common.util.Assert;
import com.tedu.cgb.team.common.util.ResultValidator;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysOrderService;

@Service
public class SysOrderServiceImpl implements SysOrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private OrderProductMapper orderProductMapper;
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	@Override
	public Page<Map<String, Object>> findPage(Integer pageCurrent) {
		Assert.notZero(pageCurrent, "当前页码错误，请刷新页面重试");
		
		OrderExample orderExample = new OrderExample();
		int rowCount = orderMapper.countByExample(orderExample);
		ResultValidator.validateResult(rowCount, "没有相对应的记录");
		
		List<Order> orders = orderMapper.selectByExample(orderExample);
		Assert.noNullElement(orders, "系统故障，请联系管理员修复");
		
		int pageSize = DEFAULT_PAGE_SIZE;
		PageHelper.startPage(pageCurrent, pageSize);
		
		List<Map<String, Object>> records = findRecordsToMaps(orders);
	
		return new Page<>(pageCurrent, pageSize, rowCount, records);
	}
	
	
	/**
	 * 根据订单信息的查询结果与产品信息连接在一起并封装成Map
	 * @param orders
	 * @return
	 */
	private List<Map<String, Object>> findRecordsToMaps(List<Order> orders) {
		// 准备查询需要的对象
		OrderProductExample orderProductExample = new OrderProductExample();
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria productCriteria = productExample.createCriteria();
		Criteria orderProductCriteria = orderProductExample.createCriteria();
		
		List<Map<String, Object>> records = new LinkedList<>();
		for (Order order : orders) {
			// 根据单个订单号查询对应的多个产品
			orderProductExample.clear();
			orderProductCriteria.andOrderIdEqualTo(order.getId());
			List<OrderProduct> orderProducts = 
					orderProductMapper.selectByExample(orderProductExample);
			Assert.noNullElement(orderProducts, "订单信息异常，请联系管理员修复");
			
			// 将查询结果转为产品id列表
			List<Integer> orderProductIds = new LinkedList<>();
			for (OrderProduct orderProduct : orderProducts) {
				orderProductIds.add(orderProduct.getProductId());
			}
			
			// 根据产品id列表查询对应的产品信息
			productExample.clear();
			productCriteria.andIdIn(orderProductIds);
			List<Product> products = productMapper.selectByExample(productExample);
			
			// 将订单信息和产品信息封装到Map中，并加在最终查询结果的单条记录中
			Map<String, Object> map = new HashMap<>();
			map.put("order", order);
			map.put("products", products);
			records.add(map);
		}
		return records;
	}

}

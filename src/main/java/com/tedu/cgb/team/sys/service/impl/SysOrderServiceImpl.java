package com.tedu.cgb.team.sys.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tedu.cgb.team.common.annotation.OperationLogger;
import com.tedu.cgb.team.common.dao.OrderMapper;
import com.tedu.cgb.team.common.dao.OrderProductMapper;
import com.tedu.cgb.team.common.dao.ProductMapper;
import com.tedu.cgb.team.common.dao.UserMapper;
import com.tedu.cgb.team.common.entity.Order;
import com.tedu.cgb.team.common.entity.OrderExample;
import com.tedu.cgb.team.common.entity.OrderProduct;
import com.tedu.cgb.team.common.entity.OrderProductExample;
import com.tedu.cgb.team.common.entity.User;
import com.tedu.cgb.team.common.util.Assert;
import com.tedu.cgb.team.common.util.ResultValidator;
import com.tedu.cgb.team.common.vo.OrderProductVo;
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
	@Autowired
	private UserMapper userMapper;
	
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	
	@Override
	public Page<Map<String, Object>> findPage(Integer pageCurrent) {
		pageCurrent = 1;
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
	
	@OperationLogger("删除订单")
	@Override
	public int deleteObjectsByIds(Integer[] ids) {
		Assert.noNullElement(ids, "请至少选择一个商品");
		// 删除关系表数据
		OrderProductExample orderProductExample = new OrderProductExample();
		orderProductExample.createCriteria().andIdIn(Arrays.asList(ids));
		int rows = orderProductMapper.deleteByExample(orderProductExample );
		ResultValidator.validateResult(rows, "删除失败，请刷新页面重试");
		// 删除订单表数据
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria().andIdIn(Arrays.asList(ids));
		rows = orderMapper.deleteByExample(orderExample);
		ResultValidator.validateResult(rows, "删除失败，请刷新页面重试");
		return rows;
	}
	
	@Override
	public int removeProduct(Integer orderId, Integer productId) {
		Assert.isId(orderId, "订单信息异常，请联系管理员修复");
		Assert.isId(productId, "订单信息异常，请联系管理员修复");
		
		OrderProductExample orderProductExample = new OrderProductExample();
		orderProductExample.createCriteria()
		.andProductIdEqualTo(productId)
		.andOrderIdEqualTo(orderId);
		int rows = orderProductMapper.deleteByExample(orderProductExample);
		ResultValidator.validateResult(rows, "删除失败，请刷新页面重试");
		
		orderProductExample.clear();
		orderProductExample.createCriteria()
		.andOrderIdEqualTo(orderId);
		int rowCount = orderProductMapper.countByExample(orderProductExample);
		if (rowCount == 0) {
			rows = orderMapper.deleteByPrimaryKey(orderId);
			ResultValidator.validateResult(rows, "订单信息异常，请联系管理员修复");
		}
		return 0;
	}
	
	@Override
	public int updateTotal(Integer orderId, Integer productId, Integer total) {
		Assert.isId(orderId, "订单信息异常，请联系管理员修复");
		Assert.isId(productId, "订单信息异常，请联系管理员修复");
		
		OrderProductExample orderProductExample = new OrderProductExample();
		orderProductExample.createCriteria()
		.andProductIdEqualTo(productId)
		.andOrderIdEqualTo(orderId);
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setTotal(total);
		int rows = orderProductMapper.updateByExampleSelective(orderProduct, orderProductExample);
		ResultValidator.validateResult(rows, "删除失败，请刷新页面重试");
		return 0;
	}
	
	
	/**
	 * 根据订单信息的查询结果与产品信息连接在一起并封装成Map
	 * @param orders
	 * @return
	 */
	private List<Map<String, Object>> findRecordsToMaps(List<Order> orders) {
		List<Map<String, Object>> records = new LinkedList<>();
		for (Order order : orders) {
			// 根据订单id进行三表联查，将一个订单对应的多个产品信息封装到List里
			List<OrderProductVo> products = productMapper.selectByIdToVo(order.getId());
			Assert.noNullElement(products, "订单信息异常，请联系管理员修复");
			
			User user = userMapper.selectByPrimaryKey(order.getUserid());
			Assert.notNull(user, "订单信息异常，请联系管理员修复");
			// 将订单信息和产品信息封装到Map中，并加在最终查询结果的单条记录中
			Map<String, Object> map = new HashMap<>();
			map.put("order", order);
			map.put("products", products);
			map.put("user", user);
			records.add(map);
		}
		return records;
	}

}

package com.tedu.cgb.team.sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.cgb.team.common.vo.JsonResult;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysOrderService;

@RestController
@RequestMapping("/sys/order")
public class SysOrderController {
	@Autowired
	private SysOrderService orderService;
	
	@RequestMapping("doFindPage")
	public JsonResult doFindPage(Integer pageCurrent) {
		Page<Map<String, Object>> result = orderService.findPage(pageCurrent);
		return new JsonResult(result);
	}
	
	@RequestMapping("doDeleteByIds")
	public JsonResult doDeleteByIds(Integer... ids) {
		orderService.deleteObjectsByIds(ids);
		return new JsonResult("删除成功");
	}
	
	@RequestMapping("doRemoveProduct")
	public JsonResult doRemoveProduct(Integer orderId, Integer productId) {
		orderService.removeProduct(orderId, productId);
		return new JsonResult("移除成功");
	}
	
	@RequestMapping("doUpdateTotal")
	public JsonResult doUpdateTotal(Integer orderId, Integer productId, Integer total) {
		orderService.updateTotal(orderId, productId, total);
		return new JsonResult("修改成功");
	}
}

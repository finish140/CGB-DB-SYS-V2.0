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
}

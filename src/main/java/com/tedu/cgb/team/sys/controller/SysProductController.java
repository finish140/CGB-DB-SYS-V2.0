package com.tedu.cgb.team.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.cgb.team.common.entity.Category;
import com.tedu.cgb.team.common.vo.JsonResult;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysProductService;

@RestController
@RequestMapping("/sys/product/")
public class SysProductController {
	
	@Autowired
	private SysProductService productService;
	
	@RequestMapping("doFindPage")
	public JsonResult doFindPage(String context, Integer pageCurrent) {
		Page<Map<String, Object>> page = productService.findPage(context, pageCurrent);
		return new JsonResult(page);
	}
	
}

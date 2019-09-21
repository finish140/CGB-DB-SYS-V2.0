package com.tedu.cgb.team.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.cgb.team.common.entity.Category;
import com.tedu.cgb.team.common.entity.Product;
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
	
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(Product product) {
		productService.updateObject(product);
		return new JsonResult("修改成功");
	}
	
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(Product product) {
		productService.saveObejct(product);
		return new JsonResult("保存成功");
	}
	
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		Product product = productService.findObjectById(id);
		return new JsonResult(product);
	}
	
	@RequestMapping("doGetCategories")
	public JsonResult doGetCategories() {
		List<Category> result = productService.getCategories();
		return new JsonResult(result);
	}
	
}

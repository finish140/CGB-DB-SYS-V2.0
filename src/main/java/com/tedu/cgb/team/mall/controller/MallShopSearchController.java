package com.tedu.cgb.team.mall.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cgb.team.common.entity.Product;
import com.tedu.cgb.team.common.vo.JsonResult;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.mall.service.MallShopSearchService;


@Controller
@RequestMapping("/search/")
public class MallShopSearchController {
	@Autowired
	private MallShopSearchService mallShopSearchService;
	
	@RequestMapping("doFindPage")
	@ResponseBody
	private JsonResult doFindPage(Integer categoryId,String context,Integer pageCurrent) throws Exception {
		if(context!=null)
			context = URLDecoder.decode(context, "UTF-8");
		System.out.println(context);
		System.out.println(categoryId);
		Page<Product> page=mallShopSearchService.findPage(categoryId,context, pageCurrent);
		return new JsonResult(page);
	}
}

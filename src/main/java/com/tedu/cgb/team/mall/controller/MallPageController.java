package com.tedu.cgb.team.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MallPageController {
	

	@RequestMapping("{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
		return "shop/" + moduleUI;
	}
	

}
package com.tedu.cgb.team.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.cgb.team.common.entity.Logger;
import com.tedu.cgb.team.common.vo.JsonResult;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysLoggerService;

@RequestMapping("/sys/log/")
@RestController
public class SysLoggerController {
	
	@Autowired
	private SysLoggerService loggerService;
	
	@RequestMapping("doFindPage")
	public JsonResult doFindPage(String username, Integer pageCurrent) {
		Page<Logger> page = loggerService.findPage(username, pageCurrent);
		return new JsonResult(page);
	}
	
	@RequestMapping("doDeleteByIds")
	public JsonResult doDeleteByIds(Integer... ids) {
		loggerService.deleteByIds(ids);
		return new JsonResult("删除成功");
	}
}

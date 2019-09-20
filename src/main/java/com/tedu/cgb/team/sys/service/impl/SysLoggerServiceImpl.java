package com.tedu.cgb.team.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tedu.cgb.team.common.dao.LoggerMapper;
import com.tedu.cgb.team.common.entity.LoggerExample;
import com.tedu.cgb.team.common.entity.LoggerExample.Criteria;
import com.tedu.cgb.team.common.entity.Logger;
import com.tedu.cgb.team.common.util.Assert;
import com.tedu.cgb.team.common.util.ResultValidator;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysLoggerService;

@Service
public class SysLoggerServiceImpl implements SysLoggerService {
	
	@Autowired
	private LoggerMapper loggerMapper;
	private static final int DEFAULT_PAGE_SIZE = 5;
	
	@Override
	public Page<Logger> findPage(String username, Integer pageCurrent) {
		Assert.notZero(pageCurrent, "页面参数不合法");
		
		LoggerExample loggerExample = new LoggerExample();
		Criteria criteria = loggerExample.createCriteria();
		
		
		if (username != null) {
			criteria.andUsernameLike("%" + username + "%");
		}
		int rowCount = loggerMapper.countByExample(loggerExample);
		
		
		ResultValidator.validateResult(rowCount, "没有找到相应的记录");
		int pageSize = DEFAULT_PAGE_SIZE;
		
		PageHelper.startPage(pageCurrent, DEFAULT_PAGE_SIZE);
		List<Logger> records = loggerMapper.selectByExample(loggerExample);
		
		ResultValidator.validateResult(records, "没有找到相应的记录");
		return new Page<>(pageCurrent, pageSize, rowCount, records);
	}
	
	@Override
	public int deleteByIds(Integer... ids) {
		Assert.notEmpty(ids, "id参数不合法");
		
		LoggerExample loggerExample = new LoggerExample();
		loggerExample.createCriteria()
		.andIdIn(Arrays.asList(ids));
		int rows = loggerMapper.deleteByExample(loggerExample);
		
		ResultValidator.validateResult(rows, "删除失败，记录不存在");
		return rows;
	}
	
	@Override
	public void insertRecord(Logger logger) {
		Assert.notNull(logger, "日志新增失败，请联系系统管理员");
		int rows = loggerMapper.insertSelective(logger);
		ResultValidator.validateResult(rows, "日志新增失败，请联系系统管理员排查错误");
	}

}

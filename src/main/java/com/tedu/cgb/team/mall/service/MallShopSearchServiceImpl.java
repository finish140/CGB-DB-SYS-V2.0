package com.tedu.cgb.team.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tedu.cgb.team.common.dao.ProductMapper;
import com.tedu.cgb.team.common.entity.Product;
import com.tedu.cgb.team.common.entity.ProductExample;
import com.tedu.cgb.team.common.entity.ProductExample.Criteria;
import com.tedu.cgb.team.common.exception.ServiceException;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.mall.service.MallShopSearchService;

@Service
public class MallShopSearchServiceImpl implements MallShopSearchService{
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public Page<Product> findPage(Integer categoryId,String context,Integer pageCurrent) {
		// 1.验证参数合法性
				// 1.1验证pageCurrent的合法性，
				// 不合法抛出IllegalArgumentException异常
				if (pageCurrent == null || pageCurrent < 1)
					throw new IllegalArgumentException("当前页码不正确");
				// 2.基于条件查询总记录数
				// 2.1) 执行查询
				
				ProductExample example = new ProductExample();
				Criteria criteria = example.createCriteria();
				
				if(categoryId != null)
					criteria.andCategoryIdEqualTo(categoryId);
				if(context != null)
					criteria.andContextLike("%" + context + "%");
				int rowCount = productMapper.countByExample(example);
				
//				// 2.2) 验证查询结果，假如结果为0不再执行如下操作
				if (rowCount == 0)
					throw new ServiceException("系统没有查到对应记录");
				// 3.基于条件查询当前页记录(pageSize定义为2)
				// 3.1)定义pageSize
				int pageSize = 30;
				PageHelper.startPage(pageCurrent, pageSize);
				
				List<Product> records = productMapper.selectByExample(example);
				
				// 4.对分页信息以及当前页记录进行封装
				// 4.1)构建PageObject对象
				Page<Product> pageObject = new Page<>(pageCurrent, pageSize, rowCount, records);
				// 4.2)封装数据
//				  pageObject.setPageCurrent(pageCurrent);
//				  pageObject.setPageSize(pageSize);
//				  pageObject.setRowCount(rowCount);
//				  pageObject.setRecords(records);
//		          pageObject.setPageCount((rowCount-1)/pageSize+1);
				// 5.返回封装结果。
				return pageObject;
	}
}

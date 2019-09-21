package com.tedu.cgb.team.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.tedu.cgb.team.common.dao.CategoryMapper;
import com.tedu.cgb.team.common.dao.ProductMapper;
import com.tedu.cgb.team.common.entity.Category;
import com.tedu.cgb.team.common.entity.CategoryExample;
import com.tedu.cgb.team.common.entity.Product;
import com.tedu.cgb.team.common.entity.ProductExample;
import com.tedu.cgb.team.common.entity.ProductExample.Criteria;
import com.tedu.cgb.team.common.util.Assert;
import com.tedu.cgb.team.common.util.ResultValidator;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysProductService;

@Service
public class SysProductServiceImpl implements SysProductService {
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public Page<Map<String, Object>> findPage(String context, Integer categoryId, Integer pageCurrent) {
		Assert.notZero(pageCurrent, "页码错误，请刷新重试");
		
		ProductExample productExample = new ProductExample();
		Criteria criteria = productExample.createCriteria();
		if (context != null || !StringUtils.isEmpty(context)) 
			criteria.andContextLike("%" + context + "%");
		if (categoryId != null) 
			criteria.andCategoryIdEqualTo(categoryId);
		
		int rowCount = productMapper.countByExample(productExample);
		Assert.notZero(rowCount, "没有相对应的记录");
		
		int pageSize = 10;
		PageHelper.startPage(pageCurrent, pageSize);
		
		List<Product> products = productMapper.selectByExample(productExample);
		List<Map<String, Object>> records = new ArrayList<>();
		for (Product p : products) {
			Category category = categoryMapper.selectByPrimaryKey(p.getCategoryId());
			Assert.notNull(category, "商品分类信息异常，请联系管理员修复");
			Map<String, Object> map = new HashMap<>();
			map.put("product", p);
			map.put("categoryTypeName", category.getTypeName());
			records.add(map);
		}
		return new Page<>(pageCurrent, pageSize, rowCount, records);
	}
	
	@Override
	public int updateObject(Product product) {
		Assert.notNull(product, "参数异常，请联系管理员修复");
		Assert.notZero(product.getId(), "参数异常，请联系管理员修复");
		isLegalProduct(product);
		
		Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
		if (category == null)
			throw new IllegalArgumentException("商品分类信息异常，请刷新页面重试");
			
		int rows = productMapper.updateByPrimaryKeySelective(product);
		ResultValidator.validateResult(rows, "更新失败，请刷新页面重试");
		return rows;
	}
	
	@Override
	public Product findObjectById(Integer id) {
		Assert.notZero(id, "参数异常，请刷新页面重试");
		Product product = productMapper.selectByPrimaryKey(id);
		ResultValidator.notNull(product, "参数异常，请刷新页面重试");
		return product;
	}
	
	@Override
	public int saveObejct(Product product) {
		Assert.notNull(product, "参数异常，请联系管理员修复");
		isLegalProduct(product);
		
		int rows = productMapper.insertSelective(product);
		ResultValidator.validateResult(rows, "保存失败，请刷新页面重试");
		return rows;
	}
	
	@Override
	public List<Category> getCategories() {
		List<Category> records = categoryMapper.selectByExample(new CategoryExample());
		Assert.noNullElement(records, "商品分类信息异常，请刷新页面重试");
		return records;
	}
	
	@Override
	public int deleteObjectsByIds(Integer[] ids) {
		Assert.noNullElement(ids, "请至少选择一个商品");
		
		ProductExample example = new ProductExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(ids));
		int rows = productMapper.deleteByExample(example);
		
		ResultValidator.validateResult(rows, "删除失败，该商品或已不存在");
		return rows;
	}
	
	/**
	 * 验证产品信息数据是否合法
	 * @param product
	 */
	private void isLegalProduct(Product product) {
		Assert.notBlank(product.getContext(), "请输入商品标题");
		product.getContext().trim();
		// 验证图片
		Assert.notNull(product.getImg(), "请上传图片");
		product.setImg(product.getImg().trim());
		if (product.getImg().startsWith("http://localhost/")) {
			product.setImg(product.getImg().substring(17));
		}
		// 验证价格
		Assert.isPrice(product.getPrice(), "请输入正确的商品价格");
		
		Assert.notZero(product.getCategoryId(), "请选择商品分类");
	}
	
	
}

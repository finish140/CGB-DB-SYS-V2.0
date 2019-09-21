package com.tedu.cgb.team.sys.service;

import java.util.List;
import java.util.Map;

import com.tedu.cgb.team.common.entity.Category;
import com.tedu.cgb.team.common.entity.Product;
import com.tedu.cgb.team.common.vo.Page;

public interface SysProductService {
	
	/**
	 * 根据context和categoryId字段查找数据并封装到Page对象，
	 * 字段值可以为null，null值时不作为查询条件进行查询
	 * @param name
	 * @return
	 */
	Page<Map<String, Object>> findPage(String context, Integer categoryId, Integer pageCurrent);

	/**
	 * 根据product的id属性更新数据库对应的记录
	 * @param product
	 * @return 
	 */
	int updateObject(Product product);

	/**
	 * 根据id查找对应的记录
	 * @param id
	 * @return
	 */
	Product findObjectById(Integer id);

	/**
	 * 存入一条新纪录
	 * @param product
	 */
	int saveObejct(Product product);

	/**
	 * 获取所有分类信息
	 * @return
	 */
	List<Category> getCategories();

	/**
	 * 根据一个或多个id删除对应的记录
	 * @param ids
	 * @return
	 */
	int deleteObjectsByIds(Integer[] ids);


}

package com.tedu.cgb.team.mall.service;

import com.tedu.cgb.team.common.entity.Product;
import com.tedu.cgb.team.common.vo.Page;


public interface MallShopSearchService {
	Page<Product> findPage(Integer categoryId,String context,Integer pageCurrent);
}

package com.tedu.cgb.team.common.vo;

import com.tedu.cgb.team.common.entity.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderProductVo extends Product {
	private static final long serialVersionUID = 1L;
	private Integer total;
	private String typeName;
}

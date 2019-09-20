package com.tedu.cgb.team.common.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 验证Service层实参的合法性，验证失败时抛出相应的异常，
 * 通过{@link ThreadLocal}来达到线程安全。<br>
 * 用instance()方法创建实例。<br>
 * exceptionMessage: 验证失败抛出异常的信息
 * 所有验证方法返回此对象本身，可以使用连锁式调用来美化代码。
 * <li>notNull: 验证对象是否为空</li>
 * <li>notEmpty: 验证数组或集合是否没有元素</li>
 * <li>notBlank: 验证String是否为空串</li>
 * <li>notZero: 验证数据类型是否小于等于0</li>
 * @version 1.0.20190918
 * @author David Zhao
 *
 */
public abstract class Assert {
	private Assert() {}
	
	
	/**
	 * 验证对象是否不为null值，验证失败时抛出异常：<br>
	 * {@link NullPointerArgumentExcpection}
	 * @param object 验证对象
	 * @param exceptionMessage 验证失败时抛出的异常信息
	 * @return
	 */
	public static void notNull(Object object, String exceptionMessage) {
		if (object == null) 
			throw new IllegalArgumentException(exceptionMessage);
	}
	
	public static void isNull(Object object, String exceptionMessage) {
		if (object != null) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}
	
	public static void isTrue(boolean expression, String exceptionMessage) {
		if (!expression) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}
	
	/**
	 * 验证对象是否不为null值和是否为空串，验证失败时抛出异常：<br>
	 * {@link BlankStringArgumentException}
	 * @param str 需要验证的字符串
	 * @param exceptionMessage 验证失败时抛出的异常信息
	 * @return
	 */
	public static void notBlank(String str, String exceptionMessage) {
		if (!StringUtils.hasText(str)) 
			throw new IllegalArgumentException(exceptionMessage);
	}
	
	/**
	 * 验证数组是否不为null值和是否有元素，验证失败时抛出异常：<br>
	 * {@link EmptyArrayArgumentException}
	 * @param array 需要验证的数组
	 * @param exceptionMessage 验证失败时抛出的异常信息
	 * @return
	 */
	public static void notEmpty(Object[] array, String exceptionMessage) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}
	
	/**
	 * 验证数组是否不为null值和是否有元素，验证失败时抛出异常：<br>
	 * {@link EmptyCollectionArgumentException}
	 * @param collection 需要验证的集合
	 * @param exceptionMessage 验证失败时抛出的异常信息
	 * @return
	 */
	public static void notEmpty(Collection<?> collection, String exceptionMessage) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}
	
	public static void notEmpty(Map<?, ?> map, String exceptionMessage) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}
	
	/**
	 * 验证数字是否不为null值和是否小于等于0，验证失败时抛出异常：<br>
	 * {@link NumberArgumentException}
	 * @param number 需要验证的数字
	 * @param exceptionMessage 验证失败时抛出的异常信息
	 * @return
	 */
	public static void notZero(Number number, String exceptionMessage) {
		notNull(number, exceptionMessage);
		if (number.doubleValue() == 0) 
			throw new IllegalArgumentException(exceptionMessage);
	}
	
	public static void isEquals(Object object, Object other, String exceptionMessage) {
		notNull(object, exceptionMessage);
		notNull(other, exceptionMessage);
		if(!ObjectUtils.nullSafeEquals(object, other)) 
			throw new IllegalArgumentException(exceptionMessage);
	}
	
	public static void notEquals(Object object, Object other, String exceptionMessage) {
		notNull(object, exceptionMessage);
		notNull(other, exceptionMessage);
		if(ObjectUtils.nullSafeEquals(object, other)) 
			throw new IllegalArgumentException(exceptionMessage);
	}
	
	/**
	 * 验证id是否为null值或是否小于等于0，验证失败时抛出异常：<br>
	 * {@link IdArgumentException}
	 * @param id 需要验证的id
	 * @param exceptionMessage 验证失败时抛出的异常信息
	 * @return
	 */
	public static void isId(Number id, String exceptionMessage) {
		notNull(id, exceptionMessage);
		if (id.doubleValue() <= 0)
			throw new IllegalArgumentException(exceptionMessage);
	}
	
	public static void isEmail(String email, String exceptionMessage) {
		notBlank(email, exceptionMessage);
		String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		if (email.matches(regex)) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}


	public static void noNullElement(Collection<?> collection, String exceptionMessage) {
		notNull(collection, exceptionMessage);
		for (Object object : collection) {
			notNull(object, exceptionMessage);
		}
	}

}

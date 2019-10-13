package com.tedu.cgb.team.sys.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tedu.cgb.team.common.annotation.OperationLogger;
import com.tedu.cgb.team.common.dao.UserMapper;
import com.tedu.cgb.team.common.entity.User;
import com.tedu.cgb.team.common.entity.UserExample;
import com.tedu.cgb.team.common.entity.UserExample.Criteria;
import com.tedu.cgb.team.common.util.Assert;
import com.tedu.cgb.team.common.util.ResultValidator;
import com.tedu.cgb.team.common.util.ShiroUtils;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private UserMapper           userMapper;
	private static final Integer DEFAULT_PAGE_SIZE = 5;

	@Override
	@OperationLogger("查看用户列表")
	public Page<User> findPageObjects(String username, Integer pageCurrent) {
		Assert.notZero(pageCurrent, "当前页面不正确");

		int pageSize = DEFAULT_PAGE_SIZE;
		PageHelper.startPage(pageCurrent, pageSize);

		UserExample usersExample = new UserExample();
		Criteria criteria = usersExample.createCriteria();
		if (username != null) {
			criteria.andUsernameLike("%" + username + "%");
		}
		int rowCount = userMapper.countByExample(usersExample);
		ResultValidator.validateResult(rowCount, "没有相对应的记录");
		usersExample.setOrderByClause("id desc");
		List<User> records = userMapper.selectByExample(usersExample);
		ResultValidator.validateResult(records, "没有相对应的记录");
		return new Page<>(pageCurrent, pageSize, rowCount, records);
	}

	@Override
	public int saveObject(User user) {
		Assert.notNull(user, "参数不合法");
		Assert.notBlank(user.getUsername(), "用户名不能为空");
		Assert.notBlank(user.getPassword(), "密码不能为空");

		String source = user.getPassword();
		String salt = UUID.randomUUID().toString();
		SimpleHash sh = new SimpleHash("MD5", source, salt, 1);
		user.setSalt(salt);
		user.setPassword(sh.toHex());

		int rows = userMapper.insertSelective(user);
		ResultValidator.validateResult(rows, "保存失败，请刷新页面重试");
		return rows;
	}

	@Override
	public User findObjectById(Integer id) {
		Assert.isId(id, "id参数不合法");
		User user = userMapper.selectByPrimaryKey(id);
		ResultValidator.notNull(user, "该用户已不存在");

		return user;
	}

	@Override
	public int updateById(User user) {
		Assert.notNull(user, "更新失败，参数不合法");
		Assert.notBlank(user.getUsername(), "用户名不能为空");

		int rows = userMapper.updateByPrimaryKeySelective(user);
		ResultValidator.validateResult(rows, "该用户已不存在");
		return rows;
	}

	@Override
	public int updatePasswordById(String oldPassword, String newPassword,
		String confirmPassword) {
		// 验证参数
		Assert.notBlank(oldPassword, "请输入原密码");
		Assert.notBlank(newPassword, "请输入新密码");
		Assert.isEquals(newPassword, confirmPassword, "两次密码输入不相同");

		Integer id = ShiroUtils.getCurrentUser().getId();
		Assert.isId(id, "系统出错，请重新登录");

		// 根据id查找数据库并对新旧密码和数据库密码进行加密核对验证
		validatePasswords(id, oldPassword, newPassword);
		// 用新盐值和新密码进行加密，更新数据库与Subject用户对象
		int updatedRows = updateUserAndSubject(id, newPassword);
		return updatedRows;
	}

//	-------------------------------------------------------------------------------------
//	== 私 有 方 法 =======================================================================
//	-------------------------------------------------------------------------------------

	/**
	 * 根据id查找用户，加密后核对用户输入的旧密码、新密码，和数据库的密码：<br>
	 * 1、核对用户输入的旧密码与数据库密码是否相同，不同则抛异常<br>
	 * 2、核对用户输入的新密码与数据库密码是否相同，相同则抛异常
	 * 
	 * @param id          修改密码的用户id
	 * @param oldPassword
	 * @param newPassword
	 */
	private void validatePasswords(Integer id, String oldPassword,
		String newPassword) {
		// 根据id查询数据库
		User user = userMapper.selectByPrimaryKey(id);
		ResultValidator.notNull(user, "该用户已不存在");

		// 获取盐值
		String salt = user.getSalt();
		// 加密参数密码
		String oldPasswordHex = new SimpleHash("MD5", oldPassword, salt, 1).toHex();
		// 核对数据库与参数
		if (!oldPasswordHex.equals(user.getPassword()))
			throw new IncorrectCredentialsException();
		// 加密新密码并验证确保不重复
		String newPasswordHex = new SimpleHash("MD5", newPassword, salt, 1).toHex();

		Assert.notEquals(newPasswordHex, user.getPassword(), "新密码不能与旧密码相同");
	}

	/**
	 * 用新的盐值和新密码进行加密，并更新数据库， 获取Subject对象里的用户对象，更新此用户对象的盐值和密码属性值
	 * 
	 * @param id          修改密码的用户id
	 * @param newPassword 新密码
	 * @return
	 */
	private int updateUserAndSubject(Integer id, String newPassword) {
		// 对新密码采用新盐值重新加密
		String newSalt = UUID.randomUUID().toString();
		String newPasswordNewSaltHex = 
			new SimpleHash("MD5", newPassword, newSalt, 1)
			.toHex();
		// 修改数据库
		User user = new User();
		user.setId(id);
		user.setPassword(newPasswordNewSaltHex);
		user.setSalt(newSalt);
		int rows = userMapper.updateByPrimaryKeySelective(user);
		ResultValidator.validateResult(rows, "该用户已不存在");
		// 修改当前Subject里的用户信息
		User currentUser = ShiroUtils.getCurrentUser();
		currentUser.setPassword(newPasswordNewSaltHex);
		currentUser.setSalt(newSalt);
		return rows;
	}

}

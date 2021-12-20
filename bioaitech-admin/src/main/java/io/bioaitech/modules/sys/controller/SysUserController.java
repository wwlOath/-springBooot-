/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.controller;


import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private io.bioaitech.modules.sys.service.SysUserService sysUserService;
	@Autowired
	private io.bioaitech.modules.sys.service.SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public io.bioaitech.common.utils.R list(@RequestParam Map<String, Object> params){
		io.bioaitech.common.utils.PageUtils page = sysUserService.queryPage(params);

		return io.bioaitech.common.utils.R.ok().put("page", page);
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public io.bioaitech.common.utils.R info(){
		return io.bioaitech.common.utils.R.ok().put("user", getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@io.bioaitech.common.annotation.SysLog("修改密码")
	@RequestMapping("/password")
	public io.bioaitech.common.utils.R password(String password, String newPassword){
		io.bioaitech.common.validator.Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = io.bioaitech.modules.sys.shiro.ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = io.bioaitech.modules.sys.shiro.ShiroUtils.sha256(newPassword, getUser().getSalt());

		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return io.bioaitech.common.utils.R.error("原密码不正确");
		}

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public io.bioaitech.common.utils.R info(@PathVariable("userId") Long userId){
		io.bioaitech.modules.sys.entity.SysUserEntity user = sysUserService.getById(userId);

		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return io.bioaitech.common.utils.R.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@io.bioaitech.common.annotation.SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public io.bioaitech.common.utils.R save(@RequestBody io.bioaitech.modules.sys.entity.SysUserEntity user){
		io.bioaitech.common.validator.ValidatorUtils.validateEntity(user, io.bioaitech.common.validator.group.AddGroup.class);

		sysUserService.saveUser(user);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 修改用户
	 */
	@io.bioaitech.common.annotation.SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public io.bioaitech.common.utils.R update(@RequestBody io.bioaitech.modules.sys.entity.SysUserEntity user){
		io.bioaitech.common.validator.ValidatorUtils.validateEntity(user, io.bioaitech.common.validator.group.UpdateGroup.class);

		sysUserService.update(user);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 删除用户
	 */
	@io.bioaitech.common.annotation.SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public io.bioaitech.common.utils.R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return io.bioaitech.common.utils.R.error("系统管理员不能删除");
		}

		if(ArrayUtils.contains(userIds, getUserId())){
			return io.bioaitech.common.utils.R.error("当前用户不能删除");
		}

		sysUserService.removeByIds(Arrays.asList(userIds));

		return io.bioaitech.common.utils.R.ok();
	}
}

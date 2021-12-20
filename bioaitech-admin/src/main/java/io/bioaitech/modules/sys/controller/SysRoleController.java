/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.controller;

import io.bioaitech.modules.sys.entity.SysRoleEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private io.bioaitech.modules.sys.service.SysRoleService sysRoleService;
	@Autowired
	private io.bioaitech.modules.sys.service.SysRoleMenuService sysRoleMenuService;
	@Autowired
	private io.bioaitech.modules.sys.service.SysRoleDeptService sysRoleDeptService;

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public io.bioaitech.common.utils.R list(@RequestParam Map<String, Object> params){
		io.bioaitech.common.utils.PageUtils page = sysRoleService.queryPage(params);

		return io.bioaitech.common.utils.R.ok().put("page", page);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public io.bioaitech.common.utils.R select(){
		List<SysRoleEntity> list = sysRoleService.list();

		return io.bioaitech.common.utils.R.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public io.bioaitech.common.utils.R info(@PathVariable("roleId") Long roleId){
		io.bioaitech.modules.sys.entity.SysRoleEntity role = sysRoleService.getById(roleId);

		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);

		return io.bioaitech.common.utils.R.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@io.bioaitech.common.annotation.SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public io.bioaitech.common.utils.R save(@RequestBody io.bioaitech.modules.sys.entity.SysRoleEntity role){
		io.bioaitech.common.validator.ValidatorUtils.validateEntity(role);

		sysRoleService.saveRole(role);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 修改角色
	 */
	@io.bioaitech.common.annotation.SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public io.bioaitech.common.utils.R update(@RequestBody io.bioaitech.modules.sys.entity.SysRoleEntity role){
		io.bioaitech.common.validator.ValidatorUtils.validateEntity(role);

		sysRoleService.update(role);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 删除角色
	 */
	@io.bioaitech.common.annotation.SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public io.bioaitech.common.utils.R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);

		return io.bioaitech.common.utils.R.ok();
	}
}

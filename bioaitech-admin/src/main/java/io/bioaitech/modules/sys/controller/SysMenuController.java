/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.controller;

import io.bioaitech.modules.sys.entity.SysMenuEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private io.bioaitech.modules.sys.service.SysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public io.bioaitech.common.utils.R nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		return io.bioaitech.common.utils.R.ok().put("menuList", menuList);
	}

	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenuEntity> list(){
		List<SysMenuEntity> menuList = sysMenuService.list();
		for(io.bioaitech.modules.sys.entity.SysMenuEntity sysMenuEntity : menuList){
			io.bioaitech.modules.sys.entity.SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}

		return menuList;
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public io.bioaitech.common.utils.R select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		io.bioaitech.modules.sys.entity.SysMenuEntity root = new io.bioaitech.modules.sys.entity.SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return io.bioaitech.common.utils.R.ok().put("menuList", menuList);
	}

	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public io.bioaitech.common.utils.R info(@PathVariable("menuId") Long menuId){
		io.bioaitech.modules.sys.entity.SysMenuEntity menu = sysMenuService.getById(menuId);
		return io.bioaitech.common.utils.R.ok().put("menu", menu);
	}

	/**
	 * 保存
	 */
	@io.bioaitech.common.annotation.SysLog("保存菜单")
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public io.bioaitech.common.utils.R save(@RequestBody io.bioaitech.modules.sys.entity.SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.save(menu);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 修改
	 */
	@io.bioaitech.common.annotation.SysLog("修改菜单")
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public io.bioaitech.common.utils.R update(@RequestBody io.bioaitech.modules.sys.entity.SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.updateById(menu);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 删除
	 */
	@io.bioaitech.common.annotation.SysLog("删除菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public io.bioaitech.common.utils.R delete(long menuId){
		if(menuId <= 31){
			return io.bioaitech.common.utils.R.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return io.bioaitech.common.utils.R.error("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(io.bioaitech.modules.sys.entity.SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new io.bioaitech.common.exception.RRException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new io.bioaitech.common.exception.RRException("上级菜单不能为空");
		}

		//菜单
		if(menu.getType() == io.bioaitech.common.utils.Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new io.bioaitech.common.exception.RRException("菜单URL不能为空");
			}
		}

		//上级菜单类型
		int parentType = io.bioaitech.common.utils.Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			io.bioaitech.modules.sys.entity.SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		//目录、菜单
		if(menu.getType() == io.bioaitech.common.utils.Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == io.bioaitech.common.utils.Constant.MenuType.MENU.getValue()){
			if(parentType != io.bioaitech.common.utils.Constant.MenuType.CATALOG.getValue()){
				throw new io.bioaitech.common.exception.RRException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getType() == io.bioaitech.common.utils.Constant.MenuType.BUTTON.getValue()){
			if(parentType != io.bioaitech.common.utils.Constant.MenuType.MENU.getValue()){
				throw new io.bioaitech.common.exception.RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}

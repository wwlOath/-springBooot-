/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private io.bioaitech.modules.sys.service.SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public io.bioaitech.common.utils.R list(@RequestParam Map<String, Object> params){
		io.bioaitech.common.utils.PageUtils page = sysConfigService.queryPage(params);

		return io.bioaitech.common.utils.R.ok().put("page", page);
	}


	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	@ResponseBody
	public io.bioaitech.common.utils.R info(@PathVariable("id") Long id){
		io.bioaitech.modules.sys.entity.SysConfigEntity config = sysConfigService.getById(id);

		return io.bioaitech.common.utils.R.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@io.bioaitech.common.annotation.SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public io.bioaitech.common.utils.R save(@RequestBody io.bioaitech.modules.sys.entity.SysConfigEntity config){
		io.bioaitech.common.validator.ValidatorUtils.validateEntity(config);

		sysConfigService.saveConfig(config);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 修改配置
	 */
	@io.bioaitech.common.annotation.SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public io.bioaitech.common.utils.R update(@RequestBody io.bioaitech.modules.sys.entity.SysConfigEntity config){
		io.bioaitech.common.validator.ValidatorUtils.validateEntity(config);

		sysConfigService.update(config);

		return io.bioaitech.common.utils.R.ok();
	}

	/**
	 * 删除配置
	 */
	@io.bioaitech.common.annotation.SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public io.bioaitech.common.utils.R delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);

		return io.bioaitech.common.utils.R.ok();
	}

}

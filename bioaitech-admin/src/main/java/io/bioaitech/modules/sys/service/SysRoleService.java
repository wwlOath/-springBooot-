/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.modules.sys.entity.SysRoleEntity;

import java.util.Map;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	io.bioaitech.common.utils.PageUtils queryPage(Map<String, Object> params);

	void saveRole(io.bioaitech.modules.sys.entity.SysRoleEntity role);

	void update(io.bioaitech.modules.sys.entity.SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

}

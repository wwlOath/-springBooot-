/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.annotation.DataFilter;
import io.bioaitech.modules.sys.dao.SysUserDao;
import io.bioaitech.modules.sys.entity.SysUserEntity;
import io.bioaitech.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private io.bioaitech.modules.sys.service.SysUserRoleService sysUserRoleService;
	@Autowired
	private io.bioaitech.modules.sys.service.SysDeptService sysDeptService;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public io.bioaitech.common.utils.PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");

		IPage<SysUserEntity> page = this.page(
			new io.bioaitech.common.utils.Query<SysUserEntity>().getPage(params),
			new QueryWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username)
				.apply(params.get(io.bioaitech.common.utils.Constant.SQL_FILTER) != null, (String)params.get(io.bioaitech.common.utils.Constant.SQL_FILTER))
		);

		for(io.bioaitech.modules.sys.entity.SysUserEntity sysUserEntity : page.getRecords()){
			io.bioaitech.modules.sys.entity.SysDeptEntity sysDeptEntity = sysDeptService.getById(sysUserEntity.getDeptId());
			sysUserEntity.setDeptName(sysDeptEntity.getName());
		}

		return new io.bioaitech.common.utils.PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(io.bioaitech.modules.sys.entity.SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(io.bioaitech.modules.sys.shiro.ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.save(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(io.bioaitech.modules.sys.entity.SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			io.bioaitech.modules.sys.entity.SysUserEntity userEntity = this.getById(user.getUserId());
			user.setPassword(io.bioaitech.modules.sys.shiro.ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
		}
		this.updateById(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}


	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
        io.bioaitech.modules.sys.entity.SysUserEntity userEntity = new io.bioaitech.modules.sys.entity.SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
        	new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

	@Override
	public SysUserEntity getByUsername(String username) {
		return this.getOne(new QueryWrapper<SysUserEntity>().eq("username", username));
	}


}

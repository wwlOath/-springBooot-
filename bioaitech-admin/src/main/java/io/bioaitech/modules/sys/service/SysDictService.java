/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.bioaitech.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysDictService extends IService<SysDictEntity> {

    io.bioaitech.common.utils.PageUtils queryPage(Map<String, Object> params);
}


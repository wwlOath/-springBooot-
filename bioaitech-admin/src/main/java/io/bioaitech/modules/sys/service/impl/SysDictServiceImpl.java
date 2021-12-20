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
import io.bioaitech.modules.sys.dao.SysDictDao;
import io.bioaitech.modules.sys.entity.SysDictEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements io.bioaitech.modules.sys.service.SysDictService {

    @Override
    public io.bioaitech.common.utils.PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<SysDictEntity> page = this.page(
            new io.bioaitech.common.utils.Query<SysDictEntity>().getPage(params),
            new QueryWrapper<SysDictEntity>()
                .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new io.bioaitech.common.utils.PageUtils(page);
    }

}

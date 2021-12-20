/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.bioaitech.modules.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private io.bioaitech.modules.sys.service.SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public io.bioaitech.common.utils.R list(@RequestParam Map<String, Object> params){
        io.bioaitech.common.utils.PageUtils page = sysDictService.queryPage(params);

        return io.bioaitech.common.utils.R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public io.bioaitech.common.utils.R info(@PathVariable("id") Long id){
        io.bioaitech.modules.sys.entity.SysDictEntity dict = sysDictService.getById(id);

        return io.bioaitech.common.utils.R.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public io.bioaitech.common.utils.R save(@RequestBody io.bioaitech.modules.sys.entity.SysDictEntity dict){
        //校验类型
        io.bioaitech.common.validator.ValidatorUtils.validateEntity(dict);

        sysDictService.save(dict);

        return io.bioaitech.common.utils.R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public io.bioaitech.common.utils.R update(@RequestBody io.bioaitech.modules.sys.entity.SysDictEntity dict){
        //校验类型
        io.bioaitech.common.validator.ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return io.bioaitech.common.utils.R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public io.bioaitech.common.utils.R delete(@RequestBody Long[] ids){
        sysDictService.removeByIds(Arrays.asList(ids));

        return io.bioaitech.common.utils.R.ok();
    }

}

package io.bioaitech.modules.bioaitech.controller;

import java.util.Arrays;
import java.util.Map;

import io.bioaitech.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.bioaitech.modules.bioaitech.entity.TissueChipTypeEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipTypeService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 组织芯片类型表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@RestController
@RequestMapping("bioaitech/tissuechiptype")
public class TissueChipTypeController {
    @Autowired
    private TissueChipTypeService tissueChipTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:tissuechiptype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tissueChipTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:tissuechiptype:info")
    public R info(@PathVariable("id") Integer id){
        TissueChipTypeEntity tissueChipType = tissueChipTypeService.getById(id);

        return R.ok().put("tissueChipType", tissueChipType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:tissuechiptype:save")
    public R save(@RequestBody TissueChipTypeEntity tissueChipType){
        tissueChipTypeService.save(tissueChipType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:tissuechiptype:update")
    public R update(@RequestBody TissueChipTypeEntity tissueChipType){
        ValidatorUtils.validateEntity(tissueChipType);
        tissueChipTypeService.updateById(tissueChipType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:tissuechiptype:delete")
    public R delete(@RequestBody Integer[] ids){
        tissueChipTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

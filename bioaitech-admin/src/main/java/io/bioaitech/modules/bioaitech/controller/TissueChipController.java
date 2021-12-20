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

import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 组织芯片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@RestController
@RequestMapping("bioaitech/tissuechip")
public class TissueChipController {
    @Autowired
    private TissueChipService tissueChipService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:tissuechip:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tissueChipService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:tissuechip:info")
    public R info(@PathVariable("id") String id){
        TissueChipEntity tissueChip = tissueChipService.getById(id);

        return R.ok().put("tissueChip", tissueChip);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:tissuechip:save")
    public R save(@RequestBody TissueChipEntity tissueChip){
        tissueChipService.save(tissueChip);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:tissuechip:update")
    public R update(@RequestBody TissueChipEntity tissueChip){
        ValidatorUtils.validateEntity(tissueChip);
        tissueChipService.updateById(tissueChip);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:tissuechip:delete")
    public R delete(@RequestBody String[] ids){
        tissueChipService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

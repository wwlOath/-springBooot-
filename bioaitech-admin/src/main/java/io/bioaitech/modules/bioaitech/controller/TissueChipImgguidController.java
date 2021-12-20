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

import io.bioaitech.modules.bioaitech.entity.TissueChipImgguidEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipImgguidService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@RestController
@RequestMapping("bioaitech/tissuechipimgguid")
public class TissueChipImgguidController {
    @Autowired
    private TissueChipImgguidService tissueChipImgguidService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:tissuechipimgguid:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tissueChipImgguidService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:tissuechipimgguid:info")
    public R info(@PathVariable("id") Integer id){
        TissueChipImgguidEntity tissueChipImgguid = tissueChipImgguidService.getById(id);

        return R.ok().put("tissueChipImgguid", tissueChipImgguid);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:tissuechipimgguid:save")
    public R save(@RequestBody TissueChipImgguidEntity tissueChipImgguid){
        tissueChipImgguidService.save(tissueChipImgguid);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:tissuechipimgguid:update")
    public R update(@RequestBody TissueChipImgguidEntity tissueChipImgguid){
        ValidatorUtils.validateEntity(tissueChipImgguid);
        tissueChipImgguidService.updateById(tissueChipImgguid);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:tissuechipimgguid:delete")
    public R delete(@RequestBody Integer[] ids){
        tissueChipImgguidService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

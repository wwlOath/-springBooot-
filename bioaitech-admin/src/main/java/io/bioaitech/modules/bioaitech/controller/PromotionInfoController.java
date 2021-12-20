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

import io.bioaitech.modules.bioaitech.entity.PromotionInfoEntity;
import io.bioaitech.modules.bioaitech.service.PromotionInfoService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 活动信息

 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@RestController
@RequestMapping("bioaitech/promotioninfo")
public class PromotionInfoController {
    @Autowired
    private PromotionInfoService promotionInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:promotioninfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = promotionInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:promotioninfo:info")
    public R info(@PathVariable("id") String id){
        PromotionInfoEntity promotionInfo = promotionInfoService.getById(id);

        return R.ok().put("promotionInfo", promotionInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:promotioninfo:save")
    public R save(@RequestBody PromotionInfoEntity promotionInfo){
        promotionInfoService.save(promotionInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:promotioninfo:update")
    public R update(@RequestBody PromotionInfoEntity promotionInfo){
        ValidatorUtils.validateEntity(promotionInfo);
        promotionInfoService.updateById(promotionInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:promotioninfo:delete")
    public R delete(@RequestBody String[] ids){
        promotionInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

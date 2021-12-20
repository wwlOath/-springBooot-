package io.bioaitech.modules.sys.controller;

import io.bioaitech.modules.sys.entity.TzSkuEntity;
import io.bioaitech.modules.sys.service.TzSkuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 单品SKU表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-09 17:51:40
 */
@RestController
@RequestMapping("sys/tzsku")
public class TzSkuController {
    @Autowired
    private TzSkuService tzSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:tzsku:list")
    public io.bioaitech.common.utils.R list(@RequestParam Map<String, Object> params){
        io.bioaitech.common.utils.PageUtils page = tzSkuService.queryPage(params);

        return io.bioaitech.common.utils.R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{skuId}")
    @RequiresPermissions("sys:tzsku:info")
    public io.bioaitech.common.utils.R info(@PathVariable("skuId") Long skuId){
        TzSkuEntity tzSku = tzSkuService.getById(skuId);

        return io.bioaitech.common.utils.R.ok().put("tzSku", tzSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:tzsku:save")
    public io.bioaitech.common.utils.R save(@RequestBody TzSkuEntity tzSku){
        tzSkuService.save(tzSku);

        return io.bioaitech.common.utils.R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:tzsku:update")
    public io.bioaitech.common.utils.R update(@RequestBody TzSkuEntity tzSku){
        io.bioaitech.common.validator.ValidatorUtils.validateEntity(tzSku);
        tzSkuService.updateById(tzSku);

        return io.bioaitech.common.utils.R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:tzsku:delete")
    public io.bioaitech.common.utils.R delete(@RequestBody Long[] skuIds){
        tzSkuService.removeByIds(Arrays.asList(skuIds));

        return io.bioaitech.common.utils.R.ok();
    }

}

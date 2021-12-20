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

import io.bioaitech.modules.bioaitech.entity.TissueSectionEntity;
import io.bioaitech.modules.bioaitech.service.TissueSectionService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 组织库/组织切片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@RestController
@RequestMapping("bioaitech/tissuesection")
public class TissueSectionController {
    @Autowired
    private TissueSectionService tissueSectionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:tissuesection:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tissueSectionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:tissuesection:info")
    public R info(@PathVariable("id") String id){
        TissueSectionEntity tissueSection = tissueSectionService.getById(id);

        return R.ok().put("tissueSection", tissueSection);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:tissuesection:save")
    public R save(@RequestBody TissueSectionEntity tissueSection){
        tissueSectionService.save(tissueSection);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:tissuesection:update")
    public R update(@RequestBody TissueSectionEntity tissueSection){
        ValidatorUtils.validateEntity(tissueSection);
        tissueSectionService.updateById(tissueSection);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:tissuesection:delete")
    public R delete(@RequestBody String[] ids){
        tissueSectionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

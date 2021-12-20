package io.bioaitech.modules.bioaitech.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.bioaitech.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.bioaitech.modules.bioaitech.entity.CategoryEntity;
import io.bioaitech.modules.bioaitech.service.CategoryService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 分类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@RestController
@RequestMapping("bioaitech/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        CategoryEntity category = categoryService.getById(id);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
        ValidatorUtils.validateEntity(category);
        categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        categoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询分类
     * type == 1 ?  组织芯片分类列表 : 组织库
     *
     * @return
     */
    @RequestMapping("/findChipCategory")
    public R findChipCategory(@RequestParam int type) {
        List<CategoryEntity> categories = categoryService.getChipCategory(type);
        return R.ok().put("category", categories);
    }
}

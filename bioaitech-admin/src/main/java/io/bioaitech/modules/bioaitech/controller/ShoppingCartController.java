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

import io.bioaitech.modules.bioaitech.entity.ShoppingCartEntity;
import io.bioaitech.modules.bioaitech.service.ShoppingCartService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 购物车
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@RestController
@RequestMapping("bioaitech/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:shoppingcart:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shoppingCartService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:shoppingcart:info")
    public R info(@PathVariable("id") String id){
        ShoppingCartEntity shoppingCart = shoppingCartService.getById(id);

        return R.ok().put("shoppingCart", shoppingCart);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:shoppingcart:save")
    public R save(@RequestBody ShoppingCartEntity shoppingCart){
        shoppingCartService.save(shoppingCart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:shoppingcart:update")
    public R update(@RequestBody ShoppingCartEntity shoppingCart){
        ValidatorUtils.validateEntity(shoppingCart);
        shoppingCartService.updateById(shoppingCart);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:shoppingcart:delete")
    public R delete(@RequestBody String[] ids){
        shoppingCartService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

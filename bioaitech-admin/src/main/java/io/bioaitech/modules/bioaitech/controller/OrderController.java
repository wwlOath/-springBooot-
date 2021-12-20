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

import io.bioaitech.modules.bioaitech.entity.OrderEntity;
import io.bioaitech.modules.bioaitech.service.OrderService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 订单
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@RestController
@RequestMapping("bioaitech/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:order:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:order:info")
    public R info(@PathVariable("id") String id){
        OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:order:save")
    public R save(@RequestBody OrderEntity order){
        orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:order:update")
    public R update(@RequestBody OrderEntity order){
        ValidatorUtils.validateEntity(order);
        orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:order:delete")
    public R delete(@RequestBody String[] ids){
        orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

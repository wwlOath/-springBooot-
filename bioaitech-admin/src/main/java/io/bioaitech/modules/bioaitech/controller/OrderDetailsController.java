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

import io.bioaitech.modules.bioaitech.entity.OrderDetailsEntity;
import io.bioaitech.modules.bioaitech.service.OrderDetailsService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



/**
 * 订单详情
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@RestController
@RequestMapping("bioaitech/orderdetails")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bioaitech:orderdetails:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderDetailsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:orderdetails:info")
    public R info(@PathVariable("id") String id){
        OrderDetailsEntity orderDetails = orderDetailsService.getById(id);

        return R.ok().put("orderDetails", orderDetails);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:orderdetails:save")
    public R save(@RequestBody OrderDetailsEntity orderDetails){
        orderDetailsService.save(orderDetails);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:orderdetails:update")
    public R update(@RequestBody OrderDetailsEntity orderDetails){
        ValidatorUtils.validateEntity(orderDetails);
        orderDetailsService.updateById(orderDetails);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:orderdetails:delete")
    public R delete(@RequestBody String[] ids){
        orderDetailsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

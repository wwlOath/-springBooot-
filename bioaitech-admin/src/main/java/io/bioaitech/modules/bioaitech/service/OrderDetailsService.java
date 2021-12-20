package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.OrderDetailsEntity;

import java.util.Map;

/**
 * 订单详情
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
public interface OrderDetailsService extends IService<OrderDetailsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


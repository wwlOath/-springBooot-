package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.ShoppingCartEntity;

import java.util.Map;

/**
 * 购物车
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
public interface ShoppingCartService extends IService<ShoppingCartEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


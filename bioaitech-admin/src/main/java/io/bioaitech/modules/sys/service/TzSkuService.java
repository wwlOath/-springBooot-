package io.bioaitech.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.modules.sys.entity.TzSkuEntity;

import java.util.Map;

/**
 * 单品SKU表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-09 17:51:40
 */
public interface TzSkuService extends IService<TzSkuEntity> {

    io.bioaitech.common.utils.PageUtils queryPage(Map<String, Object> params);
}


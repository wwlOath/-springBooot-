package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.PromotionInfoEntity;

import java.util.Map;

/**
 * 活动信息

 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
public interface PromotionInfoService extends IService<PromotionInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


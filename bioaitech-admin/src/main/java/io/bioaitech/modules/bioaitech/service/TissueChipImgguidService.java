package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.TissueChipImgguidEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
public interface TissueChipImgguidService extends IService<TissueChipImgguidEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


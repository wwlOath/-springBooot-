package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;

import java.util.Map;

/**
 * 组织芯片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
public interface TissueChipService extends IService<TissueChipEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


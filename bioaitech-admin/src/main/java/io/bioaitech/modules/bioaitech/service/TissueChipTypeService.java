package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.TissueChipTypeEntity;

import java.util.Map;

/**
 * 组织芯片类型表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
public interface TissueChipTypeService extends IService<TissueChipTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer getIdByParam(String name, String tissueChipId);
}


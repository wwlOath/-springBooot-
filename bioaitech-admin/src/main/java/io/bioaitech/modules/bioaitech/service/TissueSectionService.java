package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.TissueSectionEntity;

import java.util.Map;

/**
 * 组织库/组织切片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
public interface TissueSectionService extends IService<TissueSectionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


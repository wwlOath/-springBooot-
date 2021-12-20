package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 分类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /*
    * 查询分类列表
    * */
    List<CategoryEntity> getChipCategory(int type);

    /**
     * 找到最大的id
     * @return
     */
    Integer getMaxId();
}


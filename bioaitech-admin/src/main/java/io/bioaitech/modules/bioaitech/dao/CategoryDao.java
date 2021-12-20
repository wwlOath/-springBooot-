package io.bioaitech.modules.bioaitech.dao;

import io.bioaitech.modules.bioaitech.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 分类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    @Select("select Max(id) as id from category")
    Integer getMaxId();
}

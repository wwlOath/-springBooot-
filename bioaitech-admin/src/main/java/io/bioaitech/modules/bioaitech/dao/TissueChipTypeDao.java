package io.bioaitech.modules.bioaitech.dao;

import io.bioaitech.modules.bioaitech.entity.TissueChipTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 组织芯片类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@Mapper
public interface TissueChipTypeDao extends BaseMapper<TissueChipTypeEntity> {
	
}

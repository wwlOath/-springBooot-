package io.bioaitech.modules.bioaitech.dao;

import io.bioaitech.modules.bioaitech.entity.OrderDetailsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Mapper
public interface OrderDetailsDao extends BaseMapper<OrderDetailsEntity> {
	
}

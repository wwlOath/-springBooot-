package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.OrderDetailsDao;
import io.bioaitech.modules.bioaitech.entity.OrderDetailsEntity;
import io.bioaitech.modules.bioaitech.service.OrderDetailsService;


@Service("orderDetailsService")
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsDao, OrderDetailsEntity> implements OrderDetailsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderDetailsEntity> page = this.page(
                new Query<OrderDetailsEntity>().getPage(params),
                new QueryWrapper<OrderDetailsEntity>()
        );

        return new PageUtils(page);
    }

}

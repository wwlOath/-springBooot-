package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.ShoppingCartDao;
import io.bioaitech.modules.bioaitech.entity.ShoppingCartEntity;
import io.bioaitech.modules.bioaitech.service.ShoppingCartService;


@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCartEntity> implements ShoppingCartService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShoppingCartEntity> page = this.page(
                new Query<ShoppingCartEntity>().getPage(params),
                new QueryWrapper<ShoppingCartEntity>()
        );

        return new PageUtils(page);
    }

}

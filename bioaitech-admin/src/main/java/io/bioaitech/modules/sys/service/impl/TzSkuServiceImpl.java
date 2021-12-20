package io.bioaitech.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.modules.sys.dao.TzSkuDao;
import io.bioaitech.modules.sys.entity.TzSkuEntity;
import io.bioaitech.modules.sys.service.TzSkuService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("tzSkuService")
public class TzSkuServiceImpl extends ServiceImpl<TzSkuDao, TzSkuEntity> implements TzSkuService {

    @Override
    public io.bioaitech.common.utils.PageUtils queryPage(Map<String, Object> params) {
        IPage<TzSkuEntity> page = this.page(
                new io.bioaitech.common.utils.Query<TzSkuEntity>().getPage(params),
                new QueryWrapper<TzSkuEntity>()
        );

        return new io.bioaitech.common.utils.PageUtils(page);
    }

}

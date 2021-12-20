package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.PromotionInfoDao;
import io.bioaitech.modules.bioaitech.entity.PromotionInfoEntity;
import io.bioaitech.modules.bioaitech.service.PromotionInfoService;


@Service("promotionInfoService")
public class PromotionInfoServiceImpl extends ServiceImpl<PromotionInfoDao, PromotionInfoEntity> implements PromotionInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PromotionInfoEntity> page = this.page(
                new Query<PromotionInfoEntity>().getPage(params),
                new QueryWrapper<PromotionInfoEntity>()
        );

        return new PageUtils(page);
    }

}

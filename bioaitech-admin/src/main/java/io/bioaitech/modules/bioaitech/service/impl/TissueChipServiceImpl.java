package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.TissueChipDao;
import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipService;


@Service("tissueChipService")
public class TissueChipServiceImpl extends ServiceImpl<TissueChipDao, TissueChipEntity> implements TissueChipService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TissueChipEntity> page = this.page(
                new Query<TissueChipEntity>().getPage(params),
                new QueryWrapper<TissueChipEntity>()
        );

        return new PageUtils(page);
    }

}

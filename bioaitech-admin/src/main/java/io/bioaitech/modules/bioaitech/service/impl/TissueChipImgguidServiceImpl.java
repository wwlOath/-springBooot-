package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.TissueChipImgguidDao;
import io.bioaitech.modules.bioaitech.entity.TissueChipImgguidEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipImgguidService;


@Service("tissueChipImgguidService")
public class TissueChipImgguidServiceImpl extends ServiceImpl<TissueChipImgguidDao, TissueChipImgguidEntity> implements TissueChipImgguidService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TissueChipImgguidEntity> page = this.page(
                new Query<TissueChipImgguidEntity>().getPage(params),
                new QueryWrapper<TissueChipImgguidEntity>()
        );

        return new PageUtils(page);
    }

}

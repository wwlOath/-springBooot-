package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.TissueChipTypeDao;
import io.bioaitech.modules.bioaitech.entity.TissueChipTypeEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipTypeService;


@Service("tissueChipTypeService")
public class TissueChipTypeServiceImpl extends ServiceImpl<TissueChipTypeDao, TissueChipTypeEntity> implements TissueChipTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TissueChipTypeEntity> page = this.page(
                new Query<TissueChipTypeEntity>().getPage(params),
                new QueryWrapper<TissueChipTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer getIdByParam(String name, String tissueChipId) {
        QueryWrapper<TissueChipTypeEntity> ew = new QueryWrapper<>();
        ew.eq("name", name);
        ew.eq("tissue_chip_id", tissueChipId);
        return this.getOne(ew).getId();
    }
}

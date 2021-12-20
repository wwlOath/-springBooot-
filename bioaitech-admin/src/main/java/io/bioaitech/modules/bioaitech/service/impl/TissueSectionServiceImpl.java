package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.TissueSectionDao;
import io.bioaitech.modules.bioaitech.entity.TissueSectionEntity;
import io.bioaitech.modules.bioaitech.service.TissueSectionService;


@Service("tissueSectionService")
public class TissueSectionServiceImpl extends ServiceImpl<TissueSectionDao, TissueSectionEntity> implements TissueSectionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TissueSectionEntity> page = this.page(
                new Query<TissueSectionEntity>().getPage(params),
                new QueryWrapper<TissueSectionEntity>()
        );

        return new PageUtils(page);
    }

}

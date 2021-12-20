package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.MedicalRecordDao;
import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import io.bioaitech.modules.bioaitech.service.MedicalRecordService;


@Service("medicalRecordService")
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordDao, MedicalRecordEntity> implements MedicalRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MedicalRecordEntity> page = this.page(
                new Query<MedicalRecordEntity>().getPage(params),
                new QueryWrapper<MedicalRecordEntity>()
        );

        return new PageUtils(page);
    }

}

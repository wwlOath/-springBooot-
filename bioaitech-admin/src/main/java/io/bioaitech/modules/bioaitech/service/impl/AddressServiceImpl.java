package io.bioaitech.modules.bioaitech.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.AddressDao;
import io.bioaitech.modules.bioaitech.entity.AddressEntity;
import io.bioaitech.modules.bioaitech.service.AddressService;


@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AddressEntity> page = this.page(
                new Query<AddressEntity>().getPage(params),
                new QueryWrapper<AddressEntity>()
        );

        return new PageUtils(page);
    }

}

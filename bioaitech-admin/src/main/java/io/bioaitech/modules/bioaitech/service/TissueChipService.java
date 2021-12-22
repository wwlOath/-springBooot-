package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import io.bioaitech.modules.bioaitech.vo.req.TissueChipReqVO;
import io.bioaitech.modules.bioaitech.vo.res.TissueChipResVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 组织芯片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
public interface TissueChipService extends IService<TissueChipEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /*
    * 保存组织信息
    * */
    boolean saveChip(TissueChipReqVO reqVO);

    /*
    * 导入组织芯片数据
    * */
    void importExcelData(MultipartFile file);

    /*
    * 分页检索
    * */
    IPage<TissueChipResVO> queryPageList(Map<String, Object> params);

    /*
    * 详情
    * */
    TissueChipEntity getInfo(String id);

    /*
    * 修改
    * */
    boolean updateChip(TissueChipReqVO reqVO);
}


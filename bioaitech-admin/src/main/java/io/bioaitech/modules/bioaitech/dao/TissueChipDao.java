package io.bioaitech.modules.bioaitech.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.bioaitech.modules.bioaitech.vo.res.TissueChipResVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织芯片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@Mapper
public interface TissueChipDao extends BaseMapper<TissueChipEntity> {
    /**
     * 高级检索
     *
     * @param page
     * @param categoryId
     * @param from
     * @param to
     * @param query
     * @return
     */
    IPage<TissueChipResVO> queryPageList(IPage<TissueChipEntity> page, @Param("categoryId") Object categoryId, @Param("query") Object query, @Param("from") Object from, @Param("to") Object to);
}

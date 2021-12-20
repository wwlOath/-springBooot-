package io.bioaitech.modules.bioaitech.vo.res;

import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author miaozhuang
 * @Description
 * @Date 2019/9/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TissueChipResVO extends TissueChipEntity implements Serializable {

    private static final long serialVersionUID = -4945698659774549426L;
    /**
     * 实验检测片价格
     */
    private BigDecimal testPrice;
    /**
     * HE片价格
     */
    private BigDecimal HEPrice;
    /**
     * 预实验片价格
     */
    private BigDecimal preTestPrice;
    /**
     * 实验检测片库存
     */
    private Integer testInventory;
    /**
     * HE片库存
     */
    private Integer HEInventory;
    /**
     * 预实验片库存
     */
    private Integer preTestInventory;

    private String category;

}

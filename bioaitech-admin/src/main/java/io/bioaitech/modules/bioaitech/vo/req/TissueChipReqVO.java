package io.bioaitech.modules.bioaitech.vo.req;

import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author miaozhuang
 * @Description
 * @Date 2019/9/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TissueChipReqVO extends TissueChipEntity implements Serializable {

	private static final long serialVersionUID = 872789642713645102L;
    /**
     * 实验检测片价格
     * BigDecimal(int)  创建一个具有参数所指定整数值的对象。
     */
    private BigDecimal testPrice;

    /**
     * HE片价格
     */
    private BigDecimal HEPrice;
    /**
     * 预实验片价格
     */
    private BigDecimal PreTestPrice;
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
    private Integer PreTestInventory;
}

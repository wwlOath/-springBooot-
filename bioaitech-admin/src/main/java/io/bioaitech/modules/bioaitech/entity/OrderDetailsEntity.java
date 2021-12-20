package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Data
@TableName("order_details")
public class OrderDetailsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *
	 */
	private String orderId;
	/**
	 *
	 */
	private String productId;
	/**
	 *
	 */
	private Integer tissueChipTypeId;
	/**
	 *
	 */
	private Integer productNum;
	/**
	 *
	 */
	private BigDecimal productPrice;

	@TableField(exist = false)
	private String productName;
	@TableField(exist = false)
	private String tissueChipType;
	@TableField(exist = false)
	private String imgId;
	@TableField(exist = false)
	private String category;

}

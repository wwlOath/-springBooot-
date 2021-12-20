package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Data
@TableName("tb_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private String buyerName;
	/**
	 * 
	 */
	private String buyerPhone;
	/**
	 * 
	 */
	private String buyerAddress;
	/**
	 * 
	 */
	private Integer orderStatus;
	/**
	 * 
	 */
	private Integer paymentStatus;
	/**
	 * 发货状态
	 */
	private Integer deliveryStatus;
	/**
	 * 
	 */
	private BigDecimal payable;
	/**
	 * 
	 */
	private String remarks;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer isDeleted;

}

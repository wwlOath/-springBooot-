package io.bioaitech.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 单品SKU表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-09 17:51:40
 */
@Data
@TableName("tz_sku")
public class TzSkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 单品ID
	 */
	@TableId
	private Long skuId;
	/**
	 * 商品ID
	 */
	private Long prodId;
	/**
	 * 销售属性组合字符串 格式是p1:v1;p2:v2
	 */
	private String properties;
	/**
	 * 原价
	 */
	private BigDecimal oriPrice;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 商品在付款减库存的状态下，该sku上未付款的订单数量
	 */
	private Integer stocks;
	/**
	 * 实际库存
	 */
	private Integer actualStocks;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 记录时间
	 */
	private Date recTime;
	/**
	 * 商家编码
	 */
	private String partyCode;
	/**
	 * 商品条形码
	 */
	private String modelId;
	/**
	 * sku图片
	 */
	private String pic;
	/**
	 * sku名称
	 */
	private String skuName;
	/**
	 * 商品名称
	 */
	private String prodName;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 商品重量
	 */
	private Double weight;
	/**
	 * 商品体积
	 */
	private Double volume;
	/**
	 * 0 禁用 1 启用
	 */
	private Integer status;
	/**
	 * 0 正常 1 已被删除
	 */
	private Integer isDelete;

}

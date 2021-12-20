package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 组织芯片类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@Data
@TableName("tissue_chip_type")
public class TissueChipTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String tissueChipId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private Integer inventory;

}

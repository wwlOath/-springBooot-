package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址表

 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Data
@TableName("address")
public class AddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 联系人
	 */
	private String consignee;
	/**
	 * 联系电话
	 */
	private String phoneNumber;
	/**
	 * 详细地址
	 */
	private String address;

}

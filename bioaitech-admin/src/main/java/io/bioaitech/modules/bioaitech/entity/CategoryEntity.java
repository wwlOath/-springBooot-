package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Data
@TableName("category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer pid;
	/**
	 *
	 */
	private String name;
	/**
	 *
	 */
	private Integer sort;

	/*
	* @TableField(exist = false) 注解加载bean属性上，表示当前属性不是数据库的字段，但在项目中必须使用，
	* 这样在新增等使用bean的时候，mybatis-plus就会忽略这个，不会报错
	* */
	@TableField(exist = false)
	private Integer count;
}

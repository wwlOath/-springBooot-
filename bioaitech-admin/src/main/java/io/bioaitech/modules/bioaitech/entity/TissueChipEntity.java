package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 组织芯片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@Data
@TableName("tissue_chip")
public class TissueChipEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**
	 * 芯片编号
	 */
	private String chipId;
	/**
	 * 图片编号
	 */
	private String imgId;
	/**
	 * 芯片说明
	 */
	private String chipExplanation;
	/**
	 * 样本描述
	 */
	private String sampleDescription;
	/**
	 * 取样方式
	 */
	private String samplingMethod;
	/**
	 * 行数
	 */
	private Integer rowNumber;
	/**
	 * 列数
	 */
	private Integer colNumber;
	/**
	 * 点数
	 */
	private Integer points;
	/**
	 * 例数
	 */
	private Integer casesNumber;
	/**
	 * 点样直径
	 */
	private String dotDiameter;
	/**
	 * 组织固定方式
	 */
	private String organizationalFixation;
	/**
	 *
	 */
	private String qaQc;
	/**
	 * 种属
	 */
	private String species;
	/**
	 * 所属分类
	 */
	private String categoryId;
	/**
	 * 应用
	 */
	private String useTo;
	/**
	 * TNM说明
	 */
	private String tnmDescription;
	/**
	 * 注意事项
	 */
	private String precautions;
	/**
	 * 是否热卖
	 */
	private Integer isHotSell;
	/**
	 * 是否新品
	 */
	private Integer isNew;
	/**
	 * 是否上架
	 */
	private Integer isPutaway;

	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date createTime;

	/**
	 * 属性
	 */
	@TableField(exist = false)
	private List<TissueChipTypeEntity> attributes;

	@TableField(exist = false)
	private List<MedicalRecordEntity> medicalRecords;

	@TableField(exist = false)
	private List<String> list;

}

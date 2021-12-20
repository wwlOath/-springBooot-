package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织库/组织切片表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@Data
@TableName("tissue_section")
public class TissueSectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 编号
	 */
	private String sectionId;
	/**
	 * 组织器官
	 */
	private String tissuesOrgans;
	/**
	 * 种属
	 */
	private String species;
	/**
	 * 病理诊断
	 */
	private String pathologicDiagnosis;
	/**
	 * 组织类型
	 */
	private String categoryId;
	/**
	 * 规格
	 */
	private String specification;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer isPutaway;

}

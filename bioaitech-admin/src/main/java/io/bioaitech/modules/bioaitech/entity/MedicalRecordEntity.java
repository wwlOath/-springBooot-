package io.bioaitech.modules.bioaitech.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 病历表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@Data
@TableName("medical_record")
public class MedicalRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String medicalRecordId;
	/**
	 * 
	 */
	private String tissueChipId;
	/**
	 * 位置
	 */
	private String position;
	/**
	 * 
	 */
	private String age;
	/**
	 * 
	 */
	private String sex;
	/**
	 * 器官
	 */
	private String organ;
	/**
	 * 病理诊断
	 */
	private String pathologicalDiagnosis;
	/**
	 * 
	 */
	private String grade;
	/**
	 * 
	 */
	private String tnm;
	/**
	 * 
	 */
	private String stage;
	/**
	 * 组织类型
	 */
	private String organizationType;
	/**
	 * 手术日期
	 */
	private String surgeryDate;
	/**
	 * 临床诊断
	 */
	private String clinicalDiagnosis;
	/**
	 * 简要病史
	 */
	private String briefMedicalHistory;
	/**
	 * 肿瘤来自原发/转移
	 */
	private String tumorFrom;
	/**
	 * 远处转移部位
	 */
	private String distantMetastasis;
	/**
	 * 淋巴结转移情况
	 */
	private String lymphNodeMetastasis;
	/**
	 * 阳性淋巴结数
	 */
	private String positiveLymphNodes;
	/**
	 * 肿瘤大小
	 */
	private String tumorSize;
	/**
	 * 肿瘤部位
	 */
	private String tumorSite;
	/**
	 * 取材描述
	 */
	private String materialDescription;
	/**
	 * 
	 */
	private String ihcMarker1;
	/**
	 * 
	 */
	private String ihcMarker2;
	/**
	 * 
	 */
	private String ihcMarker3;
	/**
	 * 
	 */
	private String ihcMarker4;
	/**
	 * 
	 */
	private String ihcMarker5;

}

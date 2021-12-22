package io.bioaitech.modules.bioaitech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 病历表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
public interface MedicalRecordService extends IService<MedicalRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /*
    * 导入病例数据
    * */
    void entryMedicalRecord(MultipartFile file, String id);

    /*
    * 保存病例信息
    *  */
    void saveMedicalRecord(MedicalRecordEntity medicalRecord);

    /*
    * 删除病例信息
    * */
    void deleteMedicalRecord(List<String> ids);

    /*
    * 修改病例信息
    * */
    void updateMedicalRecord(MedicalRecordEntity medicalRecord);
}


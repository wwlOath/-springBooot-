package io.bioaitech.modules.bioaitech.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.IoUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.bioaitech.common.exception.RRException;
import io.bioaitech.common.validator.ValidatorUtils;
import io.bioaitech.modules.bioaitech.util.TemplateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import io.bioaitech.modules.bioaitech.service.MedicalRecordService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * 病历表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:29
 */
@RestController
@RequestMapping("bioaitech/medicalrecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("bioaitech:medicalrecord:list")
    public R list(@RequestBody Map<String, Object> params){
        if (params.get("tissueChipId") == null){
            return R.error(400, "芯片id不能为空！");
        }
        List<MedicalRecordEntity> list = medicalRecordService.list(new QueryWrapper<MedicalRecordEntity>().eq("tissue_chip_id",params.get("tissueChipId")));
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("bioaitech:medicalrecord:info")
    public R info(@PathVariable("id") String id){
        MedicalRecordEntity medicalRecord = medicalRecordService.getById(id);

        return R.ok().put("medicalRecord", medicalRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("bioaitech:medicalrecord:save")
    public R save(@RequestBody MedicalRecordEntity medicalRecord){
        medicalRecordService.saveMedicalRecord(medicalRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("bioaitech:medicalrecord:update")
    public R update(@RequestBody MedicalRecordEntity medicalRecord){
        //ValidatorUtils.validateEntity(medicalRecord);
        medicalRecordService.updateMedicalRecord(medicalRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("bioaitech:medicalrecord:delete")
    public R delete(@RequestBody String[] ids){
        medicalRecordService.deleteMedicalRecord(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 下载模板
     *
     * @param response
     */
    @RequestMapping("/downloadTemp")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            XSSFWorkbook workbook = TemplateUtil.downloadMedicalRecordTemp();
            String fileName = "MedicalRecordTemplate.xlsx";
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("UTF-8");
            response.flushBuffer();
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            //关闭流
            IoUtil.close(out);
        } catch (IOException e) {
            throw new RRException("下载模板失败，请稍后重试！！", 500);
        }

    }

    /**
     * 导入数据
     * @param file
     * @param tissueChipId
     * @return
     */
    @PostMapping("/importData")
    @Transactional(rollbackFor = Exception.class)
    public R entryMedicalRecord(MultipartFile file, String tissueChipId){
        if (StringUtils.isEmpty(tissueChipId)){
            return R.error(400, "请先保存芯片信息！");
        }
        medicalRecordService.entryMedicalRecord(file,tissueChipId);
        return R.ok();
    }
}

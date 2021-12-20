package io.bioaitech.modules.bioaitech.controller;

import java.util.Arrays;
import java.util.Map;

import io.bioaitech.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import io.bioaitech.modules.bioaitech.service.MedicalRecordService;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.R;



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
    @RequiresPermissions("bioaitech:medicalrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = medicalRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bioaitech:medicalrecord:info")
    public R info(@PathVariable("id") String id){
        MedicalRecordEntity medicalRecord = medicalRecordService.getById(id);

        return R.ok().put("medicalRecord", medicalRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bioaitech:medicalrecord:save")
    public R save(@RequestBody MedicalRecordEntity medicalRecord){
        medicalRecordService.save(medicalRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bioaitech:medicalrecord:update")
    public R update(@RequestBody MedicalRecordEntity medicalRecord){
        ValidatorUtils.validateEntity(medicalRecord);
        medicalRecordService.updateById(medicalRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bioaitech:medicalrecord:delete")
    public R delete(@RequestBody String[] ids){
        medicalRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

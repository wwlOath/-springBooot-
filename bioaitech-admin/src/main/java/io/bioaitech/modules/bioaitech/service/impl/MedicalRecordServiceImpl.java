package io.bioaitech.modules.bioaitech.service.impl;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import io.bioaitech.common.exception.RRException;
import io.bioaitech.common.utils.Constant;
import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipService;
import io.bioaitech.modules.bioaitech.util.SplitUtil;
import io.bioaitech.modules.bioaitech.util.TemplateUtil;
import org.apache.commons.lang.ObjectUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.MedicalRecordDao;
import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import io.bioaitech.modules.bioaitech.service.MedicalRecordService;
import org.springframework.web.multipart.MultipartFile;


@Service("medicalRecordService")
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordDao, MedicalRecordEntity> implements MedicalRecordService {

    @Autowired
    private TissueChipService tissueChipService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String id = params.get("tissueChipId").toString();

        if(!StrUtil.isEmpty(params.get(Constant.ORDER_FIELD).toString())) {
            //StrUtil.toSymbolCase将驼峰式命名的字符串转换为使用符号连接方式
            params.put(Constant.ORDER_FIELD, StrUtil.toSymbolCase(params.get(Constant.ORDER_FIELD).toString(), CharUtil.UNDERLINE));
        }
        if("position".equals(params.get(Constant.ORDER_FIELD).toString())&&params.get(Constant.ORDER).equals(Constant.ASC)) {
            params.put(Constant.ORDER_FIELD, "(position+0)");
        }else if("position".equals(params.get(Constant.ORDER_FIELD).toString())&&"desc".equals(params.get(Constant.ORDER))){
            params.put(Constant.ORDER_FIELD,"LEFT (position,1) DESC,CONVERT (substr(position,2,length(position)-1),SINGED)");
        }

        IPage<MedicalRecordEntity> page = this.page(
                new Query<MedicalRecordEntity>().getPage(params),
                new QueryWrapper<MedicalRecordEntity>().eq("tissue_chip_id",id)
        );

        return new PageUtils(page);
    }

    /*
    * 导入病例数据
    * */
    @Override
    public void entryMedicalRecord(MultipartFile file, String id) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (suffix.contains(Constant.XLS) || suffix.contains(Constant.XLSX)) {
            try {
                ExcelReader reader = ExcelUtil.getReader(file.getInputStream(), 0);
                Map<String, Object> map = getMedicalRecords(id, reader);
                if (map.get("error") != null) {
                    throw new RRException(map.get("error").toString());
                }
                Integer row = (Integer) map.get("row");
                Integer col = (Integer) map.get("col");
                if (col==0){
                    row=0;
                }
                Object o = map.get("record");
                TissueChipEntity tissueChipEntity = new TissueChipEntity();
                tissueChipEntity.setId(id);
                tissueChipEntity.setColNumber(col);
                tissueChipEntity.setRowNumber(row);
                tissueChipService.updateById(tissueChipEntity);
                this.saveBatch((Collection<MedicalRecordEntity>) o);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RRException("导入失败！" + e.getMessage(), 400);
            }
        } else {
            throw new RRException("无效的文件,文件后缀需要为.xls/.xlxs", 400);
        }
    }

    /*
    * 保存病历信息
    * */
    @Override
    public void saveMedicalRecord(MedicalRecordEntity medicalRecord) {
        String tissueChipId = medicalRecord.getTissueChipId();

        if (StrUtil.isEmpty(tissueChipId)) {
            throw new RRException("tissueChipId不能为空");
        }
        Set<String> stageList = Constant.getStageList();
        if(StrUtil.isEmpty( medicalRecord.getStage())){
            medicalRecord.setStage("-");
        }else{
            medicalRecord.setStage(medicalRecord.getStage().trim());
        }
        if(!stageList.contains(medicalRecord.getStage())){
            throw new RRException("stage填写有误！");
        }
        boolean b = ReUtil.isMatch("[A-Z]\\d+", medicalRecord.getPosition());
        if (! b) {
            throw new RRException("位置填写有误，行列超出可计算的范围！", 400);
        }
        List<MedicalRecordEntity> chipId = this.list(new QueryWrapper<MedicalRecordEntity>().eq("tissue_chip_id", tissueChipId).select("position"));
        chipId.add(medicalRecord);
        String row = "A";
        int col = 0;
        for (MedicalRecordEntity medicalRecordEntity : chipId) {
            String a = medicalRecordEntity.getPosition().trim();
            String r = SplitUtil.splitNotNumber(a).toUpperCase();
            Integer c = SplitUtil.getNumbers(a);
            //找出最大的行列数
            if (row.compareTo(r) <= 0) {
                row = r;
            }
            if (c > col) {
                col = c;
            }
        }
        TissueChipEntity tissueChipEntity = new TissueChipEntity();
        tissueChipEntity.setId(tissueChipId);
        tissueChipEntity.setColNumber(col);
        tissueChipEntity.setRowNumber(Constant.Mapping.valueOf(row).getValue());
        boolean save = this.save(medicalRecord);
        boolean b2 = tissueChipService.updateById(tissueChipEntity);
        if (! b2 || ! save) {
            throw new RRException("保存失败！");
        }
    }

    /*
    * 删除病历
    * */
    @Override
    public void deleteMedicalRecord(List<String> asList) {
        MedicalRecordEntity byId = this.getById(asList.get(0));
        String tissueChipId = byId.getTissueChipId();
        boolean b = this.removeByIds(asList);
        if (! b) {
            throw new RRException("删除失败");
        }
        List<MedicalRecordEntity> chipId = this.list(new QueryWrapper<MedicalRecordEntity>().eq("tissue_chip_id", tissueChipId).select("position"));
        String row = "A";
        int col = 0;
        for (MedicalRecordEntity medicalRecordEntity : chipId) {
            String a = medicalRecordEntity.getPosition();
            String r = SplitUtil.splitNotNumber(a);
            Integer c = SplitUtil.getNumbers(a);
            //找出最大的行列数
            if (row.compareTo(r) <= 0) {
                row = r;
            }
            if (c > col) {
                col = c;
            }
        }
        TissueChipEntity tissueChipEntity = new TissueChipEntity();
        tissueChipEntity.setId(tissueChipId);
        try {
            tissueChipEntity.setColNumber(col);
            tissueChipEntity.setRowNumber(Constant.Mapping.valueOf(row).getValue());
            tissueChipService.updateById(tissueChipEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("行列计算失败！", 400);
        }
    }

    /*
    * 修改病例信息
    * */
    @Override
    public void updateMedicalRecord(MedicalRecordEntity medicalRecord) {
        MedicalRecordEntity byId = this.getById(medicalRecord.getId());
        String tissueChipId = byId.getTissueChipId();
        boolean b = ReUtil.isMatch("[A-Z]\\d+", medicalRecord.getPosition().trim());
        if (! b) {
            throw new RRException("位置填写有误，行列超出可计算的范围！", 400);
        }
        Set<String> stageList = Constant.getStageList();
        if(StrUtil.isEmpty( medicalRecord.getStage())){
            medicalRecord.setStage("-");
        }else{
            medicalRecord.setStage(medicalRecord.getStage().trim());
        }
        if(!stageList.contains(medicalRecord.getStage())){
            throw new RRException("stage填写有误！");
        }
        boolean b1 = this.updateById(medicalRecord);
        if (! b1) {
            throw new RRException("修改失败");
        }
        List<MedicalRecordEntity> chipId = this.list(new QueryWrapper<MedicalRecordEntity>().eq("tissue_chip_id", tissueChipId).select("position"));
        String row = "A";
        int col = 0;
        for (MedicalRecordEntity medicalRecordEntity : chipId) {
            String a = medicalRecordEntity.getPosition();
            String r = SplitUtil.splitNotNumber(a).toUpperCase();
            Integer c = SplitUtil.getNumbers(a);
            //找出最大的行列数
            if (row.compareTo(r) <= 0) {
                row = r;
            }
            if (c > col) {
                col = c;
            }
        }
        TissueChipEntity tissueChipEntity = new TissueChipEntity();
        tissueChipEntity.setId(tissueChipId);
        tissueChipEntity.setColNumber(col);
        tissueChipEntity.setRowNumber(Constant.Mapping.valueOf(row.toUpperCase()).getValue());
        boolean b2 = tissueChipService.updateById(tissueChipEntity);
        if (! b2) {
            throw new RRException("行列计算失败！", 400);
        }
    }

    /*
    * 读取导入的病历信息
    * */
    private Map<String, Object> getMedicalRecords(String id, ExcelReader reader) {
        List<MedicalRecordEntity> medicalRecords = new ArrayList<>();
        List<Map<String, Object>> read = reader.readAll();
        Set<String> stageList = Constant.getStageList();
        List<String> data = TemplateUtil.getData();
        data.add(16,"IHC1");
        data.add(17,"IHC2");
        data.add(18,"IHC3");
        data.add(19,"IHC4");
        data.add(20,"IHC5");
        Map<String, Object> remap = new HashMap<>(3);
        String row = "A";
        int col = 0;

        for (Map<String, Object> map : read) {
            MedicalRecordEntity record = new MedicalRecordEntity();
            record.setTissueChipId(id);
            String a = map.get(data.get(0)).toString();
            String r = SplitUtil.splitNotNumber(a);
            Integer c = SplitUtil.getNumbers(a);
            //找出最大的行列数
            if (row.compareTo(r) <= 0) {
                row = r;
            }
            if (c > col) {
                col = c;
            }
            String s = map.get(data.get(1)) == null ? null : map.get(data.get(1)).toString();
            record.setAge(String.valueOf(s == null ? null : Double.valueOf(s)));
            record.setPosition(a);
            record.setSex(map.get(data.get(2)) == null ? null : map.get(data.get(2)).toString());
            record.setOrgan(map.get(data.get(3)) == null ? null : map.get(data.get(3)).toString());
            record.setPathologicalDiagnosis(map.get(data.get(4)) == null ? null : map.get(data.get(4)).toString());
            record.setGrade(map.get(data.get(5)) == null ? null : map.get(data.get(5)).toString());
            record.setTnm(map.get(data.get(6)) == null ? null : map.get(data.get(6)).toString());
            String stage = map.get(data.get(7)) == null ? null : map.get(data.get(7)).toString().trim();


            if(StrUtil.isEmpty( stage)){
                stage = "-";
            }

            if (stageList.contains(stage)){
                record.setStage(stage);
            }else {
                remap.putIfAbsent("error", "位置" + row + col +"stage填写错误！");
                return remap;
            }
            record.setOrganizationType(map.get(data.get(8)) == null ? null : map.get(data.get(8)).toString());
            if (map.get(data.get(9)) == null || StrUtil.isEmpty(map.get(data.get(9)).toString())) {
                remap.putIfAbsent("error", "位置" + row + col +"组织编码不可以为空！");
                return remap;
            }
            record.setMedicalRecordId(map.get(data.get(9)).toString());
            record.setSurgeryDate(map.get(data.get(10)) == null ? null : map.get(data.get(10)).toString());
            record.setClinicalDiagnosis(map.get(data.get(11)) == null ? null : map.get(data.get(11)).toString());
            record.setTumorFrom(map.get(data.get(12)) == null ? null : map.get(data.get(12)).toString());
            record.setTumorSize(map.get(data.get(13)) == null ? null : map.get(data.get(13)).toString());
            record.setLymphNodeMetastasis(map.get(data.get(14)) == null ? null : map.get(data.get(14)).toString());
            record.setDistantMetastasis(map.get(data.get(15)) == null ? null : map.get(data.get(15)).toString());
            record.setIhcMarker1(ObjectUtils.toString(map.get(data.get(16))));
            record.setIhcMarker2(ObjectUtils.toString(map.get(data.get(17))));
            record.setIhcMarker3(ObjectUtils.toString(map.get(data.get(18))));
            record.setIhcMarker4(ObjectUtils.toString(map.get(data.get(19))));
            record.setIhcMarker5(ObjectUtils.toString(map.get(data.get(20))));
            medicalRecords.add(record);
        }
        try {
            remap.put("col", col);
            remap.put("row", Constant.Mapping.valueOf(row).getValue());
            remap.put("record", medicalRecords);
        } catch (Exception e) {
            remap.putIfAbsent("error", "位置" + row + col + "填写有误，超出行列可计算的范围！");
        }

        return remap;
    }

}

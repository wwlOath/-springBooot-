package io.bioaitech.modules.bioaitech.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import io.bioaitech.common.exception.RRException;
import io.bioaitech.common.utils.Constant;
import io.bioaitech.modules.bioaitech.entity.CategoryEntity;
import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import io.bioaitech.modules.bioaitech.entity.TissueChipTypeEntity;
import io.bioaitech.modules.bioaitech.service.CategoryService;
import io.bioaitech.modules.bioaitech.service.MedicalRecordService;
import io.bioaitech.modules.bioaitech.service.TissueChipTypeService;
import io.bioaitech.modules.bioaitech.util.SplitUtil;
import io.bioaitech.modules.bioaitech.util.TemplateUtil;
import io.bioaitech.modules.bioaitech.vo.req.TissueChipReqVO;
import io.bioaitech.modules.bioaitech.vo.res.TissueChipResVO;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.TissueChipDao;
import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipService;
import org.springframework.web.multipart.MultipartFile;


@Service("tissueChipService")
public class TissueChipServiceImpl extends ServiceImpl<TissueChipDao, TissueChipEntity> implements TissueChipService {

    @Autowired
    private TissueChipTypeService tissueChipTypeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private TissueChipDao tissueChipDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TissueChipEntity> page = this.page(
                new Query<TissueChipEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /*
    * 保存组织芯片
    * */
    public boolean saveChip(TissueChipReqVO reqVO) {
        String id = reqVO.getId();
        TissueChipEntity tissueChip = new TissueChipEntity();
        BeanUtil.copyProperties(reqVO, tissueChip);
        tissueChip.setId(id);
        tissueChip.setCreateTime(new Date());
        tissueChip.setIsDeleted(Constant.FALSE);
        ArrayList<TissueChipTypeEntity> typeEntities = new ArrayList<>();
        TissueChipTypeEntity typeEntity = new TissueChipTypeEntity();
        typeEntity.setName("检测片");
        typeEntity.setPrice(reqVO.getTestPrice() == null ? BigDecimal.valueOf(0) : reqVO.getTestPrice());
        typeEntity.setTissueChipId(id);
        typeEntities.add(typeEntity);
        TissueChipTypeEntity typeEntity1 = new TissueChipTypeEntity();
        typeEntity.setName("染色片");
        typeEntity.setPrice(reqVO.getTestPrice() == null ? BigDecimal.valueOf(0) : reqVO.getTestPrice());
        typeEntity.setTissueChipId(id);
        typeEntities.add(typeEntity1);
        TissueChipTypeEntity typeEntity2 = new TissueChipTypeEntity();
        typeEntity.setName("预实验片");
        typeEntity.setPrice(reqVO.getTestPrice() == null ? BigDecimal.valueOf(0) : reqVO.getTestPrice());
        typeEntity.setTissueChipId(id);
        typeEntities.add(typeEntity2);
        boolean b = tissueChipTypeService.saveBatch(typeEntities);
        boolean save = this.save(tissueChip);
        return b&&save;
    }

    /*
    * 分页检索组织芯片列表
    * */
    @Override
    public IPage<TissueChipResVO> queryPageList(Map<String, Object> params) {
        Object query = params.get("query");
        Object categoryId = params.get("categoryId");
        Object from = params.get("from");
        Object to = params.get("to");
        if(!StrUtil.isEmpty(params.get(Constant.ORDER_FIELD).toString())) {
            if("heprice".equals(params.get(Constant.ORDER_FIELD).toString())) {
                params.put(Constant.ORDER_FIELD, "heprice");
            }
            params.put(Constant.ORDER_FIELD, StrUtil.toSymbolCase(params.get(Constant.ORDER_FIELD).toString(), CharUtil.UNDERLINE));
        }else{
            params.put(Constant.ORDER_FIELD, "create_time");
            params.put(Constant.ORDER, "desc");
        }

        IPage<TissueChipResVO> page = tissueChipDao.queryPageList(new Query<TissueChipEntity>().getPage(params), categoryId, query, from, to);
        List<TissueChipResVO> records = page.getRecords();
        List<CategoryEntity> chipCategory = categoryService.getChipCategory(1);
        for(TissueChipResVO record: records) {
            List<CategoryEntity> collect = chipCategory.stream().filter(f -> f.getId().toString().equals(record.getCategoryId())).collect(Collectors.toList());
            if(collect.size() > 0) {
                record.setCategory(collect.get(0).getName());
            }
        }
        page.setRecords(records);
        return page;
    }

    /**
     * 修改芯片
     *
     * @param reqVO
     *
     * @return
     */
    @Override
    public boolean updateChip(TissueChipReqVO reqVO) {
        TissueChipEntity tissueChip = new TissueChipEntity();
        BeanUtil.copyProperties(reqVO, tissueChip);
        ArrayList<TissueChipTypeEntity> typeEntities = new ArrayList<>();
        TissueChipTypeEntity typeEntity = new TissueChipTypeEntity();
        typeEntity.setName("检测片");
        typeEntity.setPrice(reqVO.getTestPrice());
        typeEntity.setInventory(reqVO.getTestInventory());
        typeEntity.setTissueChipId(tissueChip.getId());
        typeEntity.setId(tissueChipTypeService.getIdByParam(typeEntity.getName(), typeEntity.getTissueChipId()));
        typeEntities.add(typeEntity);
        TissueChipTypeEntity typeEntity1 = new TissueChipTypeEntity();
        typeEntity1.setName("H&E染色片");
        typeEntity1.setPrice(reqVO.getHEPrice());
        typeEntity1.setInventory(reqVO.getHEInventory());
        typeEntity1.setTissueChipId(tissueChip.getId());
        typeEntity1.setId(tissueChipTypeService.getIdByParam(typeEntity1.getName(), typeEntity1.getTissueChipId()));
        typeEntities.add(typeEntity1);
        TissueChipTypeEntity typeEntity2 = new TissueChipTypeEntity();
        typeEntity2.setName("预实验片");
        typeEntity2.setPrice(reqVO.getPreTestPrice());
        typeEntity2.setInventory(reqVO.getPreTestInventory());
        typeEntity2.setTissueChipId(tissueChip.getId());
        typeEntity2.setId(tissueChipTypeService.getIdByParam(typeEntity2.getName(), typeEntity2.getTissueChipId()));
        typeEntities.add(typeEntity2);
        boolean b = tissueChipTypeService.updateBatchById(typeEntities);
        boolean b1 = this.updateById(tissueChip);
        return b && b1;
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    @Override
    public TissueChipEntity getInfo(String id) {
        TissueChipEntity tissueChip = this.getById(id);
        List<TissueChipTypeEntity> list = tissueChipTypeService.list(new QueryWrapper<TissueChipTypeEntity>().eq("tissue_chip_id", id));
        TissueChipResVO tissueChipResVO = new TissueChipResVO();
        BeanUtil.copyProperties(tissueChip, tissueChipResVO);
        for (TissueChipTypeEntity type : list) {
            if ("H&E染色片".equals(type.getName())) {
                tissueChipResVO.setHEPrice(type.getPrice());
                tissueChipResVO.setHEInventory(type.getInventory());
            } else if ("预实验片".equals(type.getName())) {
                tissueChipResVO.setPreTestPrice(type.getPrice());
                tissueChipResVO.setPreTestInventory(type.getInventory());
            } else {
                tissueChipResVO.setTestPrice(type.getPrice());
                tissueChipResVO.setTestInventory(type.getInventory());
            }
        }
        return tissueChipResVO;
    }

    /**
     * 导入组织芯片
     * @param file
     */
    @Override
    public void importExcelData(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new RRException("不支持的文件");
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (suffix.contains(Constant.XLS) || suffix.contains(Constant.XLSX)) {
            try {
                //组织芯片id
                String id = IdUtil.fastSimpleUUID();
                ExcelReader reader = ExcelUtil.getReader(file.getInputStream(), 0);
                String chipId = reader.getCell(1, 1).getStringCellValue();
                List<TissueChipEntity> chip = this.list(new QueryWrapper<TissueChipEntity>().eq("chip_id", chipId).eq("is_deleted", Constant.FALSE));
                if (chip.size() > 0) {
                    throw new RRException("该芯片已存在！", 400);
                }
                TissueChipEntity tissueChip = getTissueChip(id, reader);
                List<TissueChipTypeEntity> chipTypes = getTissueChipType(id, reader);
                Map<String, Object> map = getMedicalRecords(id, reader);
                tissueChip.setColNumber((Integer) map.get("col"));
                tissueChip.setRowNumber((Integer) map.get("row"));
                tissueChip.setCreateTime(new Date());
                List<MedicalRecordEntity> medicalRecords = (List<MedicalRecordEntity>) map.get("record");
                this.save(tissueChip);
                tissueChipTypeService.saveBatch(chipTypes);
                medicalRecordService.saveBatch(medicalRecords);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RRException("导入组织芯片失败," + e.getMessage(), 400);
            }
        } else {
            throw new RRException("无效的文件,文件后缀需要为.xls/.xlxs", 400);
        }
    }

    /**
     * 读取导入的病例信息
     *
     * @param id
     * @param reader
     *
     * @return
     */
    private Map<String, Object> getMedicalRecords(String id, ExcelReader reader) {
        List<MedicalRecordEntity> medicalRecords = new ArrayList<>();
        List<Map<String, Object>> read = reader.read(23, 24, reader.getRowCount() - 1);
        List<String> data = TemplateUtil.getData();
        data.add(16,"IHC1");
        data.add(17,"IHC2");
        data.add(18,"IHC3");
        data.add(19,"IHC4");
        data.add(20,"IHC5");
        String row = "A";
        int col = 0;
        for (Map<String, Object> map : read) {
            MedicalRecordEntity record = new MedicalRecordEntity();
            record.setTissueChipId(id);
            String a = map.get(data.get(0)).toString().trim();
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
            //record.setAge(s == null ? null : Double.valueOf(s));
            record.setPosition(a);
            record.setSex(map.get(data.get(2)).toString());
            record.setOrgan(map.get(data.get(3)) == null ? null : map.get(data.get(3)).toString());
            record.setPathologicalDiagnosis(map.get(data.get(4)) == null ? null : map.get(data.get(4)).toString());
            record.setGrade(map.get(data.get(5)) == null ? null : map.get(data.get(5)).toString());
            record.setTnm(map.get(data.get(6)) == null ? null : map.get(data.get(6)).toString());
            record.setStage(map.get(data.get(7)) == null ? null : map.get(data.get(7)).toString());
            record.setOrganizationType(map.get(data.get(8)) == null ? null : map.get(data.get(8)).toString());
            if (map.get(data.get(9)) == null) {
                throw new RRException("组织编码不能为空！");
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
        Map<String, Object> remap = new HashMap<>(3);
        try {
            remap.put("col", col);
            remap.put("row", Constant.Mapping.valueOf(row).getValue());
            remap.put("record", medicalRecords);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("位置" + row + "填写有误，超出行列可计算的范围！");
        }
        return remap;
    }

    /**
     * 读取导入的组织芯片规格信息
     *
     * @param id
     * @param reader
     *
     * @return
     */
    private List<TissueChipTypeEntity> getTissueChipType(String id, ExcelReader reader) {
        List<TissueChipTypeEntity> typeEntities = new ArrayList<>();
        TissueChipTypeEntity typeEntity = new TissueChipTypeEntity();
        try {
            reader.getCell(1,11).setCellType(CellType.NUMERIC);
            reader.getCell(1,12).setCellType(CellType.NUMERIC);
            reader.getCell(1,13).setCellType(CellType.NUMERIC);
            reader.getCell(1,14).setCellType(CellType.NUMERIC);
            reader.getCell(1,15).setCellType(CellType.NUMERIC);
            reader.getCell(1,16).setCellType(CellType.NUMERIC);
            typeEntity.setName("检测片");
            typeEntity.setPrice(BigDecimal.valueOf(reader.getCell(1, 11).getNumericCellValue()));
            typeEntity.setInventory((int) reader.getCell(1, 14).getNumericCellValue());
            typeEntity.setTissueChipId(id);
            typeEntities.add(typeEntity);
            TissueChipTypeEntity typeEntity1 = new TissueChipTypeEntity();
            typeEntity1.setName("H&E染色片");
            typeEntity1.setPrice(BigDecimal.valueOf(reader.getCell(1, 13).getNumericCellValue()));
            typeEntity1.setInventory((int) reader.getCell(1, 16).getNumericCellValue());
            typeEntity1.setTissueChipId(id);
            typeEntities.add(typeEntity1);
            TissueChipTypeEntity typeEntity2 = new TissueChipTypeEntity();
            typeEntity2.setName("预实验片");
            typeEntity2.setPrice(BigDecimal.valueOf(reader.getCell(1, 12).getNumericCellValue()));
            typeEntity2.setInventory((int) reader.getCell(1, 15).getNumericCellValue());
            typeEntity2.setTissueChipId(id);
            typeEntities.add(typeEntity2);
        }catch (Exception e){
            e.printStackTrace();
            throw new RRException("价格及库存需要填写数字");
        }

        return typeEntities;
    }

    /**
     * 读取导入的组织芯片信息
     *
     * @param id
     * @param reader
     *
     * @return
     */
    private TissueChipEntity getTissueChip(String id, ExcelReader reader) {
        TissueChipEntity tissueChip = new TissueChipEntity();
        tissueChip.setId(id);
        //芯片编号
        reader.getCell(1,1).setCellType(CellType.STRING);
        tissueChip.setChipId(reader.getCell(1, 1).getStringCellValue());
        //芯片说明
        reader.getCell(1,2).setCellType(CellType.STRING);
        tissueChip.setChipExplanation(reader.getCell(1, 2).getStringCellValue());
        //样本描述
        reader.getCell(1,3).setCellType(CellType.STRING);
        tissueChip.setSampleDescription(reader.getCell(1, 3).getStringCellValue());
        //取样方式
        reader.getCell(1,4).setCellType(CellType.STRING);
        tissueChip.setSamplingMethod(reader.getCell(1, 4).getStringCellValue());
        //点数
        try {
            reader.getCell(1,6).setCellType(CellType.NUMERIC);
            tissueChip.setPoints((int) reader.getCell(1, 5).getNumericCellValue());
        }catch (Exception e){
            throw new RRException("点数需要填数字");
        }
        //例数
        try {
            reader.getCell(1,6).setCellType(CellType.NUMERIC);
            tissueChip.setCasesNumber((int) reader.getCell(1, 6).getNumericCellValue());
        }catch (Exception e){
            throw new RRException("例数需要填数字");
        }
        //点样直径
        reader.getCell(1,7).setCellType(CellType.STRING);
        tissueChip.setDotDiameter(reader.getCell(1, 7).getStringCellValue());
        //种属
        reader.getCell(1,8).setCellType(CellType.STRING);
        tissueChip.setSpecies(reader.getCell(1, 8).getStringCellValue());
        //所属系统
        reader.getCell(1,9).setCellType(CellType.STRING);
        if (reader.getCell(1, 9).getStringCellValue() == null) {
            throw new RRException("所属系统不能为空");
        }
        String cellValue = reader.getCell(1, 9).getStringCellValue();
        List<CategoryEntity> chipCategory = categoryService.getChipCategory(1);
        if (! StrUtil.isEmpty(cellValue)) {
            List<CategoryEntity> collect = chipCategory.stream().filter(f -> cellValue.equals(f.getName())).collect(Collectors.toList());
            if (collect.size() <= 0) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setName(cellValue);
                categoryEntity.setPid(1);
                categoryService.save(categoryEntity);
                tissueChip.setCategoryId(categoryService.getMaxId().toString());
            } else {
                tissueChip.setCategoryId(collect.get(0).getId().toString());
            }
        }
        //图像id
        reader.getCell(1,10).setCellType(CellType.STRING);
        tissueChip.setImgId(reader.getCell(1, 10).getStringCellValue() + "");
        //组织固定方式
        reader.getCell(1,17).setCellType(CellType.STRING);
        tissueChip.setOrganizationalFixation(reader.getCell(1, 17).getStringCellValue());
        //QA/QC
        reader.getCell(1,18).setCellType(CellType.STRING);
        tissueChip.setQaQc(reader.getCell(1, 18).getStringCellValue());
        //应用
        reader.getCell(1,19).setCellType(CellType.STRING);
        tissueChip.setUseTo(reader.getCell(1, 19).getStringCellValue());
        //注意事项
        reader.getCell(1,20).setCellType(CellType.STRING);
        tissueChip.setPrecautions(reader.getCell(1, 20).getStringCellValue());
        //tnm说明
        reader.getCell(1,21).setCellType(CellType.STRING);
        tissueChip.setTnmDescription(reader.getCell(1, 21).getStringCellValue());
        tissueChip.setIsNew(Constant.FALSE);
        tissueChip.setIsPutaway(Constant.FALSE);
        tissueChip.setIsHotSell(Constant.FALSE);
        tissueChip.setIsDeleted(Constant.FALSE);
        return tissueChip;
    }
}

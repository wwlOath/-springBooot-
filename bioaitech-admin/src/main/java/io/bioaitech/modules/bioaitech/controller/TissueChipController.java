package io.bioaitech.modules.bioaitech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.bioaitech.common.exception.RRException;
import io.bioaitech.common.utils.Constant;
import io.bioaitech.modules.bioaitech.entity.CategoryEntity;
import io.bioaitech.modules.bioaitech.entity.MedicalRecordEntity;
import io.bioaitech.modules.bioaitech.service.CategoryService;
import io.bioaitech.modules.bioaitech.service.MedicalRecordService;
import io.bioaitech.modules.bioaitech.util.TemplateUtil;
import io.bioaitech.modules.bioaitech.vo.req.TissueChipReqVO;
import io.bioaitech.modules.bioaitech.vo.res.TissueChipResVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.bioaitech.modules.bioaitech.entity.TissueChipEntity;
import io.bioaitech.modules.bioaitech.service.TissueChipService;
import io.bioaitech.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * 组织芯片表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@RestController
@RequestMapping("/bioaitech/tissuechip")
public class TissueChipController {
    @Autowired
    private TissueChipService tissueChipService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("bioaitech:tissuechip:list")
    public R list(@RequestBody Map<String, Object> params){
        IPage<TissueChipResVO> page = tissueChipService.queryPageList(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("bioaitech:tissuechip:info")
    public R info(@PathVariable("id") String id){
        TissueChipEntity tissueChip = tissueChipService.getById(id);

        return R.ok().put("tissueChip", tissueChip);
    }

    /**
     * 保存
     */
    @RequestMapping("/saveOrUpdate")
    //@RequiresPermissions("bioaitech:tissuechip:save")
    public R save(@RequestBody TissueChipReqVO reqVO){
        //字符创工具 StrUtil
        if(StrUtil.isEmpty(reqVO.getId())) {
            //唯一ID IdUtil
            String s = IdUtil.fastSimpleUUID();
            reqVO.setId(s);
            boolean b = tissueChipService.saveChip(reqVO);
            if(b) {
                return R.ok().put("tissueChipId", s);
            }
            return R.error();
        }else{
            tissueChipService.updateChip(reqVO);
            return R.ok();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("bioaitech:tissuechip:update")
    public R update(@RequestBody TissueChipReqVO reqVO){
        tissueChipService.updateChip(reqVO);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("bioaitech:tissuechip:delete")
    public R delete(@RequestBody String[] ids){
        List<TissueChipEntity> entities = new ArrayList<>();
        for(String id: ids) {
            TissueChipEntity tissueChipEntity = new TissueChipEntity();
            tissueChipEntity.setId(id);
            tissueChipEntity.setIsDeleted(Constant.TRUE);
            tissueChipEntity.setIsPutaway(Constant.FALSE);
            entities.add(tissueChipEntity);
        }
        //Mybatisplus批量更新
        tissueChipService.updateBatchById(entities);
        //queryWrapper是mybatis plus中实现查询的对象封装操作类
        medicalRecordService.remove(new QueryWrapper<MedicalRecordEntity>().in("tissue_chip_id", (Object) ids));
        return R.ok();
    }

    /*
    * 设置上下架
    * @PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
     * */
    @RequestMapping("/isPutaway/{id}")
    public R isPutaway(@PathVariable("id") String id) {
        TissueChipEntity byId = tissueChipService.getById(id);
        TissueChipEntity tissueChipEntity = new TissueChipEntity();
        tissueChipEntity.setId(id);
        if(byId.getIsPutaway().equals(Constant.TRUE)) {
            tissueChipEntity.setIsPutaway(Constant.FALSE);
        }else{
            tissueChipEntity.setIsPutaway(Constant.TRUE);
        }
        tissueChipService.updateById(tissueChipEntity);
        return R.ok();
    }

    /*
     * 设置是否最新
     * @PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
     * */
    @RequestMapping("/isNew/{id}")
    public R isNew(@PathVariable("id") String id) {
        TissueChipEntity byId = tissueChipService.getById(id);
        TissueChipEntity tissueChipEntity = new TissueChipEntity();
        tissueChipEntity.setId(id);
        if(byId.getIsNew().equals(Constant.TRUE)) {
            tissueChipEntity.setIsNew(Constant.FALSE);
        }else{
            tissueChipEntity.setIsNew(Constant.TRUE);
        }
        tissueChipService.updateById(tissueChipEntity);
        return R.ok();
    }

    /*
     * 设置是否最热
     * @PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
     * */
    @RequestMapping("/isHotSell/{id}")
    public R isHotSell(@PathVariable("id") String id) {
        TissueChipEntity byId = tissueChipService.getById(id);
        TissueChipEntity tissueChipEntity = new TissueChipEntity();
        tissueChipEntity.setId(id);
        if(byId.getIsHotSell().equals(Constant.TRUE)) {
            tissueChipEntity.setIsHotSell(Constant.FALSE);
        }else{
            tissueChipEntity.setIsHotSell(Constant.TRUE);
        }
        tissueChipService.updateById(tissueChipEntity);
        return R.ok();
    }

    /**
     * 导入数据
     */
    @RequestMapping("/importData")
    @Transactional(rollbackFor = Exception.class)
    public R importExcelData(MultipartFile file) {
        tissueChipService.importExcelData(file);
        return R.ok();
    }

    /*
    * 下载模板
    * */
    @RequestMapping("/downloadTemp")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            List<CategoryEntity> list = categoryService.getChipCategory(1);
            List<String> select = new ArrayList<>();
            list.forEach(l -> select.add(l.getName()));
            Object[] objects = select.toArray();
            XSSFWorkbook workbook = TemplateUtil.downloadTissueChipTemp(objects);
            String fileName = "TissueChipTemplate.xlxs";
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition", "attachment;fileName="+fileName);
            response.setCharacterEncoding("UTF-8");
            response.flushBuffer();
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            //关闭流
            IoUtil.close(out);
        }catch (IOException e) {
            throw new RRException("下载模板失败，请稍后重试！", 500);
        }
    }

}

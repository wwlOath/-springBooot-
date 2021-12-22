package io.bioaitech.modules.bioaitech.util;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemplateUtil {
    /*
    * 组织芯片模板
    * */
    public static XSSFWorkbook downloadTissueChipTemp(Object[] objects) throws IOException {
        String[] select = new String[objects.length];
        for(int i = 0; i < objects.length; i++) {
            select[i] = objects[i].toString();
        }
        Resource resource = new ClassPathResource("/sourcefile/xlsx/temp.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(resource.getStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        workbook.setSheetName(0, "组织芯片模板");

        //表头样式
        XSSFCellStyle style = getBaseStyle(workbook);
        XSSFCellStyle titleStyle = getTitleStyle(workbook);
        XSSFRow row1 = sheet.createRow(0);

        List<String> list = getTitleData();
        for(int i=1; i < list.size()+1; i++) {
            XSSFRow row = sheet.createRow(i);
            for(int j=0; j < 9; j++) {
                XSSFCell cell = row.createCell(j);
                if(j == 0) {
                    cell.setCellValue(list.get(i-1));
                }
                if(j == 1) {
                    if (i == 17) {
                        cell.setCellValue("福尔马林固定24h");
                    } else if (i == 18) {
                        cell.setCellValue("HE染色和IHC染色");
                    } else if (i == 19) {
                        cell.setCellValue("常规组织学操作包括免疫组织化学（IHC）和原位杂交（ISH），可以在我们的技术支持页面找到操作流程。");
                    } else if (i == 20) {
                        cell.setCellValue("1.收到产品后请在4°保存，并在3个月内使用；\n" + "2.请在实验前60°烤片30分钟；\n" + "3.请选择温和的修复方式，以免造成组织脱落。");
                    }
                }
            }
            if(i == 22) {
                continue;
            }
            //合并单元格
            CellRangeAddress adr = new CellRangeAddress(i, i, 1 ,15);
            sheet.addMergedRegion(adr);
        }
        DataValidationHelper helper = sheet.getDataValidationHelper();
        //设置行列范围
        CellRangeAddressList addressList = new CellRangeAddressList(9,9,1,1);
        //设置下拉框数据
        DataValidationConstraint constraint = helper.createExplicitListConstraint(select);
        //对下拉数据进行验证
        DataValidation validation = helper.createValidation(constraint, addressList);
        //显示下拉箭头
        validation.setShowPromptBox(true);
        //不允许空值
        validation.setEmptyCellAllowed(false);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setFontName("宋体");
        List<String> data = getData();
        for(int i=0; i < data.size(); i++) {
            XSSFCell cell = row1.createCell(i);
            if(i == 0){
                XSSFRichTextString strings = new XSSFRichTextString("组织芯片说明书（仅供科研使用）");
                strings.applyFont(0,7,font);
                cell.setCellValue(strings);
                cell.setCellStyle(titleStyle);
            }
        }
        row1.setHeight((short) 800);
        CellRangeAddress cra = new CellRangeAddress(0,0,0,data.size()-1);
        sheet.addMergedRegion(cra);
        initChapter(data, sheet, style);
        sheet.setColumnWidth(0,5000);
        //自适应宽度
        for(int i=0; i < data.size();i++){
            sheet.autoSizeColumn(i);
        }


        return workbook;
    }

    /*
    * 生成表头样式
    * */
    private static XSSFCellStyle getBaseStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("微软雅黑");
        style.setFont(font);
        //自定义颜色
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(155,194,230)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static XSSFCellStyle getTitleStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private static List<String> getTitleData() {
        List<String> list = new ArrayList<>();
        list.add(0, "芯片编号");
        list.add(1, "芯片名称：");
        list.add(2, "芯片说明：");
        list.add(3, "取样方式：");
        list.add(4, "点数：");
        list.add(5, "例数：");
        list.add(6, "点样直径：");
        list.add(7, "种属");
        list.add(8, "所属系统");
        list.add(9, "病理图像ID");
        list.add(10, "检测片价格");
        list.add(11, "预实验片价格:");
        list.add(12, "H&E染色片价格:");
        list.add(13, "检测片库存:");
        list.add(14, "预实验片库存：");
        list.add(15, "H&E染色片库存:");
        list.add(16, "组织固定方式：");
        list.add(17, "QA/QC：");
        list.add(18, "应用:");
        list.add(19, "注意事项:");
        list.add(20,"TNM说明：");
        list.add(21, "排列方式如下：");
        return list;
    }

    public static List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "位置");
        list.add(1, "年龄");
        list.add(2, "性别");
        list.add(3, "器官");
        list.add(4, "病理诊断");
        list.add(5, "Grade");
        list.add(6, "TNM");
        list.add(7, "Stage");
        list.add(8, "组织类型");
        list.add(9, "组织编码");
        list.add(10, "手术日期");
        list.add(11, "临床诊断");
        list.add(12, "肿块来自原发/转移");
        list.add(13, "肿块大小");
        list.add(14, "淋巴结转移情况");
        list.add(15, "远处转移部位");
        return list;
    }

    //位置信息一行的样式
    private static void initChapter(List<String> data, Sheet sheet, XSSFCellStyle style) {
        for (int i = 23; i < 24; i++) {
            Row row = sheet.createRow(i);
            row.setHeight((short) 500);
            for (int j = 0; j < data.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(data.get(j));
            }
        }
    }

    /**
     * 病例模板
     *
     * @return
     */
    public static XSSFWorkbook downloadMedicalRecordTemp() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("病例模板");
        XSSFCellStyle baseStyle = getBaseStyle(wb);
        List<String> data = getData();
        initTableChapter(1, data.size(), sheet, baseStyle);
        XSSFRow row = sheet.getRow(0);
        for (int i = 0; i < data.size(); i++) {
            row.getCell(i).setCellValue(data.get(i));
        }
        return wb;
    }

    private static void initTableChapter(int r, int c, XSSFSheet sheet, XSSFCellStyle baseStyle) {
        for (int i = 0; i < r; i++) {
            Row row = sheet.createRow(i);
            row.setHeight((short) 500);
            for (int j = 0; j < c; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(baseStyle);
            }
        }
    }
}

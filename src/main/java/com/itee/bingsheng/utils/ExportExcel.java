package com.itee.bingsheng.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel公共方法
 *
 */
public class ExportExcel  {

    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName ;

    private String path;

    private List<Object[]>  dataList = new ArrayList<Object[]>();

    HttpServletResponse  response;

    //构造方法，传入要导出的数据
    public ExportExcel(String path,String title,String[] rowName,List<Object[]>  dataList){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.path = path;
    }

    /*
     * 导出数据
     * */
    public void export(String path) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();						// 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);		 			// 创建工作表

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);					//单元格样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);				// 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell  cellRowName = rowRowName.createCell(n);				//创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);				//设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);									//设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);						//设置列头单元格样式
            }

            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+3);//创建所需的行数

                for(int j=0; j<obj.length; j++){
                    HSSFCell  cell = null;   //设置单元格的数据类型
                    if(j == 0){
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i+1);
                    }else{
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                        if(!"".equals(obj[j]) && obj[j] != null){
                            cell.setCellValue(obj[j].toString());						//设置单元格的值
                        }
                    }
                    cell.setCellStyle(style);									//设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }

            if(workbook !=null){
                try
                {
                    // 通过文件输出流生成Excel文件
                    File file = new File(path);
                    FileOutputStream outStream = new FileOutputStream(file);
                    workbook.write(outStream);
                    outStream.flush();
                    outStream.close();
                    System.out.println("Excel 2003文件导出完成！导出文件路径："+file.getPath());
//
//                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
//                    String headStr = "attachment; filename=\"" + fileName + "\"";
//                    response = getResponse();
//                    response.setContentType("APPLICATION/OCTET-STREAM");
//                    response.setHeader("Content-Disposition", headStr);
//                    OutputStream out = response.getOutputStream();
//                    workbook.write(out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /*
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
   * 列数据信息单元格样式
   */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String []keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名

        Sheet sheet = wb.createSheet("123");

        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    /**
     * 导出数据到excel中,只有一个工作薄
     * 注意: excelHeader[]数组和Object[]长度要一致
     * @param worktableTitle 工作薄的标题
     * @param excelHeader 表头
     * @param list 存在内容的Object数组
     * @param cellTitle 第一行合并单元格内容
     * @return
     * 阳庆文
     * 2016年1月21日 下午3:29:03
     */
    public static HSSFWorkbook exportExcel(String worktableTitle, String cellTitle, String[] excelHeader,String [] column, List<Map<String,Object>> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
                //建立工作薄
                HSSFSheet sheet = wb.createSheet(worktableTitle);
                sheet.createFreezePane( 0, 2, 0, 2);//冻结第二行

                // 设置合并单元格字体
                HSSFFont font0 = wb.createFont();
                font0.setBoldweight(Font.BOLDWEIGHT_BOLD);
                font0.setFontHeightInPoints((short) 15);

                //设置列标题的单元格字体
                HSSFFont font = wb.createFont();
                font.setFontName("黑体");
                font.setFontHeightInPoints((short) 11);//设置字体大小

                //设置合并单元格的样式
                HSSFCellStyle style0 = wb.createCellStyle();
                style0.setAlignment(CellStyle.ALIGN_CENTER);
                style0.setFont(font0);
                //如果后续想用\r\n强制换行, 必须先设置为自动换行
                style0.setWrapText(true);

                //设置列标题样式
                HSSFCellStyle style = wb.createCellStyle();
        //	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setFont(font);

                //创建第一行
                HSSFRow row0 = sheet.createRow(0);
                //设定行高
                row0.setHeight((short)450);
                //合并第一行单元格
                //参数1：行号, 参数2：起始列号, 参数3：行号, 参数4：终止列号
                org.apache.poi.ss.util.CellRangeAddress cellRangeAddress = new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, excelHeader.length - 1);
                sheet.addMergedRegion(cellRangeAddress);
                //给第一行赋值
                HSSFCell cell0 = row0.createCell(0);
                cell0.setCellStyle(style0);
                cell0.setCellValue(new HSSFRichTextString(cellTitle));
/*
                HSSFCell cell1 = row0.createCell(1);
                cell1.setCellStyle(style0);
                cell1.setCellValue(new HSSFRichTextString(cellTitle));*/

                //创建第二行
                HSSFRow row = sheet.createRow(1);
                //给第二行赋值(列标题)
                for (int i = 0; i < excelHeader.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    String value = excelHeader[i];
                    cell.setCellValue(value);
                    cell.setCellStyle(style);
                    sheet.autoSizeColumn((short)i);
                }

                //创建第三行--第四行, 并赋值
                int length = 0;
                for (int i = 0; i < list.size(); i++) {
                    row = sheet.createRow(i + 2);
                    int j = 0;
                    Map<String,Object> obj = list.get(i);
                    for (Map.Entry<String, Object> entry : obj.entrySet()) {
                        Object o = obj.get(column[j]);
                        if(obj != null && o!=null) {
                            length = o.toString().getBytes().length;
                        }
                        int colWidth = sheet.getColumnWidth(j);
                        int col = (length > colWidth/256) ? length*280 : colWidth;
                        sheet.setColumnWidth(j, col);
                        HSSFCell cell = row.createCell(j);
                        if(j == 0) {
                            //第一列一般为序号
                            cell.setCellValue(i + 1);
                        } else {
                            if(o instanceof BigDecimal || o instanceof BigInteger || o instanceof Integer ||
                                    o instanceof Long || o instanceof Double || o instanceof Float) {
                                cell.setCellValue(Double.parseDouble(o.toString()));
                            } else {
                                cell.setCellValue(o != null ? o.toString() : "");
                            }
                        }
                        j++;
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wb;
    }


    /**
     * 导出数据到excel中,只有一个工作薄
     * 注意: excelHeader[]数组和Object[]长度要一致
     * @param worktableTitle 工作薄的标题
     * @param excelHeader 表头
     * @param list 存在内容的Object数组
     * @param cellTitle 第一行合并单元格内容
     * @return
     * 阳庆文
     * 2016年1月21日 下午3:29:03
     */
    public static HSSFWorkbook exportExcelNoLineNum(String worktableTitle, String cellTitle, String[] excelHeader,String [] column, List<Map<String,Object>> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            //建立工作薄
            HSSFSheet sheet = wb.createSheet(worktableTitle);
            sheet.createFreezePane( 0, 2, 0, 2);//冻结第二行

            // 设置合并单元格字体
            HSSFFont font0 = wb.createFont();
            font0.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font0.setFontHeightInPoints((short) 15);

            //设置列标题的单元格字体
            HSSFFont font = wb.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 11);//设置字体大小

            //设置合并单元格的样式
            HSSFCellStyle style0 = wb.createCellStyle();
            style0.setAlignment(CellStyle.ALIGN_CENTER);
            style0.setFont(font0);
            //如果后续想用\r\n强制换行, 必须先设置为自动换行
            style0.setWrapText(true);

            //设置列标题样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setFont(font);

            //创建第一行
            HSSFRow row0 = sheet.createRow(0);
            //设定行高
            row0.setHeight((short)450);
            //合并第一行单元格
            //参数1：行号, 参数2：起始列号, 参数3：行号, 参数4：终止列号
            org.apache.poi.ss.util.CellRangeAddress cellRangeAddress = new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, excelHeader.length - 1);
            sheet.addMergedRegion(cellRangeAddress);
            //给第一行赋值
            HSSFCell cell0 = row0.createCell(0);
            cell0.setCellStyle(style0);
            cell0.setCellValue(new HSSFRichTextString(cellTitle));

            //创建第二行
            HSSFRow row = sheet.createRow(1);
            //给第二行赋值(列标题)
            for (int i = 0; i < excelHeader.length; i++) {
                HSSFCell cell = row.createCell(i);
                String value = excelHeader[i];
                cell.setCellValue(value);
                cell.setCellStyle(style);
                sheet.autoSizeColumn((short)i);
            }

            //创建第三行--第四行, 并赋值
            int length = 0;
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 2);
                int j = 0;
                Map<String,Object> obj = list.get(i);
                for (Map.Entry<String, Object> entry : obj.entrySet()) {
                    Object o = obj.get(column[j]);
                    if(obj != null && o!=null) {
                        length = o.toString().getBytes().length;
                    }
                    int colWidth = sheet.getColumnWidth(j);
                    int col = (length > colWidth/256) ? length*280 : colWidth;
                    sheet.setColumnWidth(j, col);
                    HSSFCell cell = row.createCell(j);
                    if(o instanceof BigDecimal || o instanceof BigInteger || o instanceof Integer ||
                            o instanceof Long || o instanceof Double || o instanceof Float) {
                        cell.setCellValue(Double.parseDouble(o.toString()));
                    } else {
                        cell.setCellValue(o != null ? o.toString() : "");
                    }
                    if(j>=(column.length-1)){
                        continue;
                    }else {
                        j++;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 导出数据到excel中,有多个工作薄
     *
     * @param worktableTile 工作表的标题list
     * @param excelHeader 表头list
     * @param listContent 存放数据的list
     * @return
     * 阳庆文
     * 2015年12月16日 下午5:56:05
     */
    public static HSSFWorkbook exportExcelMore(List<String> worktableTile, List<String[]> excelHeader, List<List<Object[]>> listContent) {
        if(worktableTile == null || excelHeader == null || listContent == null) {
            System.out.println("null");
            return null;
        }

        //判断list大小是否相等, 避免出现空的数据或者空工作薄标题或者空表头
        int worktableTileSize = worktableTile.size();
        int excelHeaderSize = excelHeader.size();
        int listContentSize = listContent.size();
        if(worktableTileSize != excelHeaderSize || worktableTileSize != listContentSize || excelHeaderSize != listContentSize) {
            System.out.println("size not equals");
            return null;
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 11);//设置字体大小

        for (int k=0; k<worktableTileSize && k<excelHeaderSize && k<listContentSize; k++) {
            HSSFSheet sheet = wb.createSheet(worktableTile.get(k));
            HSSFRow row = sheet.createRow(0);
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_LEFT);
            style.setFont(font);//选择需要用到的字体格式

            //设置列标题
            for (int i = 0; i < excelHeader.get(k).length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(excelHeader.get(k)[i]);
                cell.setCellStyle(style);
            }

            //设置每行的值
            for (int i = 0; i < listContent.get(k).size(); i++) {
                row = sheet.createRow(i + 1);
                Object[] obj = listContent.get(k).get(i);
                for(int j=0; j<obj.length; j++) {
                    HSSFCell cell = row.createCell(j);
                    if(obj[j] instanceof BigDecimal || obj[j] instanceof BigInteger || obj[j] instanceof Integer || obj[j] instanceof Long || obj[j] instanceof Double || obj[j] instanceof Float) {
                        cell.setCellValue(Double.parseDouble(obj[j].toString()));
                    } else {
                        cell.setCellValue(obj[j] != null ? obj[j].toString() : "");
                    }
                }
            }
        }
        return wb;
    }

    /**
     * @param path:xls文件路径
     * @return
     * @throws Exception
     */
    public static List<List<String>> readXls(String path)throws Exception {
        InputStream is=new FileInputStream(path);
        //hssfWorkbook表示整个excel
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(is);
        List<List<String>> result=new ArrayList<List<String>>();
        //循环每一页，并处理当前循环页
//		for (int numSheet=0;numSheet<hssfWorkbook.getNumberOfSheets();numSheet++){
        //HSSFSheet表示某一页
        HSSFSheet hssfSheet=hssfWorkbook.getSheetAt(2);
//			if (hssfSheet == null) {
//				continue;
//			}
        //处理当前页，循环读取每一行
        for (int rowNum=2;rowNum<=hssfSheet.getLastRowNum();rowNum++){
            //HSSFRow:表示行
            HSSFRow hssfRow=hssfSheet.getRow(rowNum);
            int minColIx=hssfRow.getFirstCellNum();
            int maxColIx=hssfRow.getLastCellNum();
            List<String> rowList = new ArrayList<String>();
            for (int colIx=minColIx;colIx<maxColIx;colIx++){
                //HSSFCell:表示单元格
                HSSFCell cell=hssfRow.getCell(colIx);
                if (cell == null) {
                    continue;
                }
                rowList.add(rightTrim(cell.toString()));
            }
            result.add(rowList);
        }
//		}
        return result;
    }
    /**
     * 去掉字符串右边的空格
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }
}
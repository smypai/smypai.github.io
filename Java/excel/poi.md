```java
POI HSSFCellStyle 设置 Excel 单元格样式

POI中可能会用到一些需要设置EXCEL单元格格式的操作小结：先获取工作薄对象:
HSSFWorkbook wb = new HSSFWorkbook();
HSSFSheet sheet = wb.createSheet();
HSSFCellStyle setBorder = wb.createCellStyle();
一、设置背景色：
setBorder.setFillForegroundColor((short) 13);// 设置背景色
setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
;二、设置边框:
setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
三、设置居中:
setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
四、设置字体:

四、设置字体:
HSSFFont font = wb.createFont();
font.setFontName("黑体");
font.setFontHeightInPoints((short) 16);//设置字体大小
HSSFFont font2 = wb.createFont();
font2.setFontName("仿宋_GB2312");
font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
font2.setFontHeightInPoints((short) 12);setBorder.setFont(font);
//选择需要用到的字体格式
五、设置列宽:
sheet.setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值
六、设置自动换行:
setBorder.setWrapText(true);//设置自动换行
七、合并单元格:
Region region1 = new Region(0, (short) 0, 0, (short) 6);//参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
sheet.addMergedRegion(region1);
八、加边框  
HSSFCellStyle cellStyle= wookBook.createCellStyle();  
cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
cellStyle.setBorderBottom(HSSFCellStyle.BorderBORDER_MEDIUM);  
cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);  
cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);  
cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);  
cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);  
cellStyle.setRightBorderColor(HSSFColor.BLACK.index);  
cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);  
cellStyle.setTopBorderColor(HSSFColor.BLACK.index);

```
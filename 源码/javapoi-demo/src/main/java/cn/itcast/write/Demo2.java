package cn.itcast.write;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo2 {
    public static void main(String[] args) throws IOException {
        //1.创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建工作表
        XSSFSheet sheet = workbook.createSheet("工作表一");
        //3.创建行
        XSSFRow row = sheet.createRow(0);
        //创建单元格
        row.createCell(0).setCellValue("传智播客");
        row.createCell(1).setCellValue("黑马程序员");
        row.createCell(2).setCellValue("博学谷");


        XSSFRow row1 = sheet.createRow(1);
        //创建单元格
        row1.createCell(0).setCellValue("传智播客");
        row1.createCell(1).setCellValue("黑马程序员");
        row1.createCell(2).setCellValue("博学谷");

        //输出流
        FileOutputStream out=new FileOutputStream("E:\\poitest\\heima.xlsx");
        workbook.write(out);
        out.flush();
        //释放资源
        out.close();
        workbook.close();
        System.out.println("写入成功!");

    }
}

package dataAnalysisAndStorage;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

abstract public class DataFiles {
	
	private static String filePath;
	private String account;
	private String brand;
	private String channel;
	
	public DataFiles(String account, String brand, String channel) {
		this.account = account;
		this.brand = brand;
		this.channel = channel;
	}
	
	//Helper method that writes the new data in to the existing file with data for this year
	private void saveDataInExistingFile(String productSize, String season, String promoType, String comments, Date startDate,
				Date endDate, boolean change) throws IOException {
		String path = this.filePath+this.channel+this.account+this.brand+".xls";
		File openFile = new File(path);
		if(openFile.exists() && !openFile.isDirectory()) {
			FileInputStream inputStream = new FileInputStream(openFile);
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int newRow = sheet.getPhysicalNumberOfRows()+1;
			String promoCode = String.valueOf(newRow)+this.brand+this.channel+this.account+productSize;
			HSSFRow row = sheet.createRow(newRow);
			
			//Because we want to keep the format of the date and boolean values we will exclude them from the 
			//String array of data
			String [] insertData = {promoCode, this.account, this.brand, this.channel, productSize, season, promoType,
					comments};
			for(int i=0; i<8; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(insertData[i]);
			}
			HSSFCell cell2 = row.createCell(8);
			cell2.setCellValue(startDate);
			HSSFCell cell3 = row.createCell(9);
			cell3.setCellValue(endDate);
			HSSFCell cell4 = row.createCell(10);
			cell4.setCellValue(change);
			
			//We write the output stream in order to store the updated data
			FileOutputStream outputStream = new FileOutputStream(openFile);
			workbook.write(outputStream);
			outputStream.close();
		} else {
			this.saveDataInNewFile(productSize, season, promoType, comments, startDate, endDate, change);
		}
		
	}
	
	
	//Helper method that writes the data for a promotion into a file which doesn't exist before. It creates a file and 
	//writes the promo data in it.
	private void saveDataInNewFile(String productSize, String season, String promoType, String comments, Date startDate, 
			Date endDate, boolean change) throws FileNotFoundException {
		String path = this.filePath+this.channel+this.account+this.brand+".xls";
		File newFile = new File(path);
		try {
			FileOutputStream output = new FileOutputStream(newFile);
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow row = sheet.createRow(0);
			String promoCode = "0"+this.brand+this.channel+this.account+productSize;
			String [] inputData = {promoCode, this.account, this.brand, this.channel, productSize, season, promoType, comments};
			for(int i=0; i<8; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(inputData[i]);
			}
			
			//Because we want to preserve the nature of Date and boolean we add them manually
			HSSFCell cell2 = row.createCell(8);
			cell2.setCellValue(startDate);
			HSSFCell cell3 = row.createCell(9);
			cell3.setCellValue(endDate);
			HSSFCell cell4 = row.createCell(10);
			cell4.setCellValue(change);
			
			workbook.write(output);
			output.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Method to write data in existing or new files
	public void writeDataInFile(String productSize, String season, String promoType, String comments, 
			Date start, Date end, boolean change) throws IOException {
		String path = this.filePath+this.channel+this.account+this.brand+".xls";
		
		File check = new File(path);
		if(check.exists() && !check.isDirectory()) {
			this.saveDataInExistingFile(productSize, season, promoType, comments, start, start, change);
		} else {
			this.saveDataInNewFile(productSize, season, promoType, comments, start, end, change);
		}
	}
	
	

}

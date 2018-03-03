package com.incedoinc.blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.incedoinc.dao.PostgreSQLDAO;

public class ExcelFileReader {

	private static final String FILE_NAME = "Blockchain_POC_Dataset.xlsx";

	public static void main(String[] args) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			createPatientVisit(datatypeSheet);
			/*
			 * Iterator<Row> iterator = datatypeSheet.iterator();
			 * 
			 * while (iterator.hasNext()) {
			 * 
			 * Row currentRow = iterator.next(); Iterator<Cell> cellIterator =
			 * currentRow.iterator();
			 * 
			 * while (cellIterator.hasNext()) {
			 * 
			 * Cell currentCell = cellIterator.next(); if (currentCell.getCellTypeEnum() ==
			 * CellType.STRING) { System.out.print(currentCell.getStringCellValue() + "  ");
			 * } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			 * System.out.print(currentCell.getNumericCellValue() + ""); }
			 * 
			 * } System.out.println(); }
			 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean createPatientVisit(Sheet datatypeSheet) {

		Iterator<Row> iterator = datatypeSheet.iterator();
		final PostgreSQLDAO insertDao = new PostgreSQLDAO();
		boolean first = true;
		StringBuilder columnString = new StringBuilder();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			
			StringBuilder columnValue = new StringBuilder("");
			
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				if (currentCell.getCellTypeEnum() == CellType.STRING) {
					final String s = currentCell.getStringCellValue();
					if (first) {
						if (columnString.length() == 0)
							columnString.append(s);
						else
							columnString.append("," + s);
					} else {
						if (columnValue.length() == 0)
							columnValue.append("'" + s + "'");
						else
							columnValue.append(", '" + s + "'");
					}
					// System.out.print(currentCell.getStringCellValue() + " ");
				} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					final double s = currentCell.getNumericCellValue();
					if (columnValue.length() == 0)
						columnValue.append(s);
					else
						columnValue.append("," + s);
					//System.out.print(currentCell.getNumericCellValue() + "");
				}
			}
			if(!first)
			{	
				//System.out.println(columnString);
				//System.out.println(columnValue);
				
				insertDao.insert(columnString, columnValue);
			} else	
				first = false;
		}

		/*
		 * StringBuilder columnString = new StringBuilder();
		 * 
		 * for (String s : column) { if (columnString.length() == 0)
		 * columnString.append(s); else columnString.append("," + s); }
		 * 
		 * StringBuilder columnValue = new StringBuilder("");
		 * 
		 * for (Object[] row : values) { for (Object value : row) { if
		 * (columnValue.length() == 0) columnValue.append(" VALUES ( "); else
		 * columnValue.append("," + s); } }
		 * 
		 * PostgreSQLDAO sqlDao = new PostgreSQLDAO(); sqlDao.
		 */

		return false;
	}
}
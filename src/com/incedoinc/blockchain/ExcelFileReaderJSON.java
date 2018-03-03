package com.incedoinc.blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.incedoinc.dao.PostgreSQLDAO;
import com.incedoinc.dao.PostgreSQLJSONDAO;

public class ExcelFileReaderJSON {

	private static final String FILE_NAME = "Blockchain_POC_Dataset.xlsx";

	public static void main(String[] args) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			createPatientVisit(datatypeSheet);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean createPatientVisit(Sheet datatypeSheet) {

		Iterator<Row> iterator = datatypeSheet.iterator();
		final PostgreSQLJSONDAO insertDao = new PostgreSQLJSONDAO();
		boolean first = true;
		List<String> columnString = new ArrayList<String>();
		if(iterator.hasNext())
		{
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				columnString.add(currentCell.getStringCellValue());
			}
		}
		
		while (iterator.hasNext()) {
			StringBuilder column = new StringBuilder();
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			int i=0;
			column.append("{");
			while (cellIterator.hasNext() && i < columnString.size()) {
				final String s = columnString.get(i);
				Cell currentCell = cellIterator.next();
				if (currentCell.getCellTypeEnum() == CellType.STRING) {
					column.append("\""+s+"\": " + "\"" + currentCell.getStringCellValue() + "\"");
				} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					column.append("\""+s+"\": " + "\"" + currentCell.getNumericCellValue() + "\"");
				}
				i++;
				if(columnString.size() != i)
				{
					column.append(",");
				}	
			}
			column.append("}");
			insertDao.insert(column.toString());
		}
		
		return false;
	}
}
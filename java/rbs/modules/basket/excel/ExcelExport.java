package rbs.modules.basket.excel;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.LoggerFactory;

import com.clipsoft.clipreport.export.lib.xls.basetypes.CellType;
import com.sun.star.io.IOException;

import rbs.modules.basket.web.BasketController;

public class ExcelExport<T> {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelExport.class);

	private static final int ROW_START_INDEX = 0;
	private static final int COLUMN_START_INDEX = 0;

	private SXSSFWorkbook wb;
	private SXSSFSheet sheet;

	public ExcelExport(List<T> data, List<String> list, Class<T> type) throws NoSuchFieldException, SecurityException {
		this.wb = new SXSSFWorkbook();
		renderExcel(data, list, type);
		

	}

	private void renderExcel(List<T> data, List<String> showColumn, Class<T> objectType)
			throws NoSuchFieldException, SecurityException {
		sheet = (SXSSFSheet) wb.createSheet();
		createHeaders(sheet, ROW_START_INDEX, COLUMN_START_INDEX, showColumn, objectType);

		if (data.isEmpty())
			return;

		int rowIndex = ROW_START_INDEX + 1;

		for (T datum : data) {
			createBody(datum, rowIndex++, COLUMN_START_INDEX, showColumn, objectType);
		}
	}

	private void createHeaders(SXSSFSheet sheet, int rowStartIndex, int columnStartIndex, List<String> showColumn,
			Class<T> objectType) {
		SXSSFRow row = (SXSSFRow) sheet.createRow(rowStartIndex);
		
		
		if (showColumn == null) {
			for (Field declaredField : objectType.getDeclaredFields()) {
				ExcelColumn annotation = declaredField.getAnnotation(ExcelColumn.class);
				if (annotation.DefaultColumn().equals("Y")) {
					sheet.setColumnWidth(columnStartIndex, 6000);
					SXSSFCell cell = (SXSSFCell) row.createCell(columnStartIndex++);
					cell.setCellValue(annotation.HeaderName());
				}
			}
		} else {
			for (Field declaredField : objectType.getDeclaredFields()) {
				ExcelColumn annotation = declaredField.getAnnotation(ExcelColumn.class);
				if (annotation.DefaultColumn().equals("Y")) {
					sheet.setColumnWidth(columnStartIndex, 6000);
					SXSSFCell cell = (SXSSFCell) row.createCell(columnStartIndex++);
					cell.setCellValue(annotation.HeaderName());
				} else if (showColumn.contains(annotation.HeaderName())) {
					sheet.setColumnWidth(columnStartIndex, 6000);
					SXSSFCell cell = (SXSSFCell) row.createCell(columnStartIndex++);
					cell.setCellValue(annotation.HeaderName());
				}

			}
		}

	}

	private void createBody(Object obj, int i, int columnStartIndex, List<String> showColumn, Class<T> objectType)
			throws NoSuchFieldException, SecurityException {
		SXSSFRow row = (SXSSFRow) sheet.createRow(i);

		if (showColumn == null) {
			for (Field declaredField : objectType.getDeclaredFields()) {
				ExcelColumn annotation = declaredField.getAnnotation(ExcelColumn.class);
				if (annotation.DefaultColumn().equals("Y")) {
					SXSSFCell cell = (SXSSFCell) row.createCell(columnStartIndex++);
					Field field = getField(obj.getClass(), declaredField.getName());
					field.setAccessible(true);

					try {
						Object value = field.get(obj);
						addCellValueByType(value, cell, declaredField.getType());
					} catch (Exception e) {
						log.info("ERROR : Exception", e.getMessage());
					}
				}

			}
		} else {
			for (Field declaredField : objectType.getDeclaredFields()) {
				ExcelColumn annotation = declaredField.getAnnotation(ExcelColumn.class);
				if (annotation.DefaultColumn().equals("Y")) {
					SXSSFCell cell = (SXSSFCell) row.createCell(columnStartIndex++);
					Field field = getField(obj.getClass(), declaredField.getName());
					field.setAccessible(true);

					try {
						Object value = field.get(obj);
						addCellValueByType(value, cell, declaredField.getType());
					} catch (Exception e) {
						log.info("ERROR : Exception", e.getMessage());
					}
				} else if (showColumn.contains(annotation.HeaderName())) {
					SXSSFCell cell = (SXSSFCell) row.createCell(columnStartIndex++);
					Field field = getField(obj.getClass(), declaredField.getName());
					field.setAccessible(true);

					try {
						Object value = field.get(obj);
						addCellValueByType(value, cell, declaredField.getType());
					} catch (Exception e) {
						log.info("ERROR : Exception", e.getMessage());
					}
				}

			}

		}

	}

	private Field getField(Class<?> aClass, String name) throws NoSuchFieldException, SecurityException {

		return aClass.getDeclaredField(name);

	}

	private void addCellValueByType(Object value, SXSSFCell cell, Class<?> dataType) {
		if (dataType == int.class) {
			cell.setCellValue((int)value);
			
		} else {
			cell.setCellValue(value == null ? "" : value.toString());
		}

	}

	public void write(HttpServletResponse res, String fileName) throws IOException, java.io.IOException {
		res.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8") + ";");
		wb.write(res.getOutputStream());
		res.getOutputStream().flush();
		res.getOutputStream().close();
		wb.dispose();
	}
}

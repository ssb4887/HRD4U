package rbs.egovframework.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.woowonsoft.egovframework.util.DataSecurityUtil;
/**
 * 엑셀파일 다운로드
 * : 대용량의 데이터를 데이터베이스에서 읽어 Excel로 다운로드시 OOM(Out Of Memory) 발생
 *   -> ResultHandler를 이용하여 각각의 Result에 대해 Excel의 Row를 생성하면 OOM 발생 방지
 */
public class RbsHSSExcelResultHandler<T extends Map<String,Object>> implements ResultHandler {
	private T result;
	
	private List<?> columnList;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle bodyStyle;
	private int rownum;
	
	final int HEADER = 0;
	final int BODY = 1;

	public RbsHSSExcelResultHandler() {
		super();
		rownum = 0;
	}
	
	public RbsHSSExcelResultHandler(String title) {
		this();
	}
	
	public RbsHSSExcelResultHandler(List<String[]> columnList, String title, HSSFWorkbook workbook, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle) {
		this(title);
		this.workbook = workbook;
		this.sheet = workbook.createSheet(title);
		this.headerStyle = headerStyle;
		this.bodyStyle = bodyStyle;
		this.columnList = columnList;
	}
	
	/**
	 * row
	 */
	public void handleResult(ResultContext context) {
		result = (T) context.getResultObject();
		excelWrite(rownum);
		rownum ++;
	}
	
	/**
	 * 엑셀 row 생성
	 * @param currentRow
	 */
	private void excelWrite(int currentRow) {
		// header setting
		if(columnList == null && result != null) {
			columnList = new ArrayList<>(result.keySet());
		}
		
		if(columnList == null || columnList.isEmpty()) {
			return;
		}
		
		HSSFRow row = sheet.createRow(currentRow);
		
		int currentCol = 0;
		for(Object columnObj:columnList) {
			String columnId = null;
			String columnName = null;
			int columnType = 0;				// 0:string, 1:seed복호화, 2:date
			String dateFormatStr = null;
			if(columnObj instanceof String[]) {
				String[] columnStrs = (String[])columnObj;
				columnId = columnStrs[0];
				columnName = columnStrs[1];
				columnType = Integer.parseInt(columnStrs[2]);
				dateFormatStr = (columnStrs.length > 3)?columnStrs[3]:"yyyy-MM-dd";
			} else {
				columnId = columnName = (String)columnObj;
			}
			HSSFCell cell = row.createCell(currentCol);
			
			if(currentRow == 0) {
				// header
				cell.setCellValue(columnName);
				cell.setCellStyle(headerStyle);
			} else {
				// body
				if(result.containsKey(columnId)) {
					Object valueObj = result.get(columnId);
					if(columnType == 1) {
						// seed 복호화
						cell.setCellValue(DataSecurityUtil.getSeedDecrypt(valueObj.toString()));
					} else if(columnType == 2) {
						// date
						SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
						cell.setCellValue(sdf.format(valueObj));
					} else {
						cell.setCellValue(result.get(columnId).toString());
					}
					cell.setCellStyle(bodyStyle);
				}
			}
			
			currentCol ++;
		}
		
	}

	public HSSFWorkbook getWorkbook() {
		// TODO Auto-generated method stub
		return workbook;
	}

}
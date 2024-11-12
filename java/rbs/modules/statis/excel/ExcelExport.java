package rbs.modules.statis.excel;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.LoggerFactory;

import com.clipsoft.org.apache.poi.ss.usermodel.IndexedColors;
import com.sun.star.io.IOException;
import com.woowonsoft.egovframework.form.DataMap;

public class ExcelExport<T> {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelExport.class);

	private SXSSFWorkbook workbook;
	private SXSSFSheet sheet;

	public ExcelExport(List<?> data) throws NoSuchFieldException, SecurityException {
		this.workbook = new SXSSFWorkbook();
		sheet = (SXSSFSheet) workbook.createSheet("통계 기업 목록");
		
		// 헤더에 적용할 셀스타일
		CellStyle grayStyle = workbook.createCellStyle();
		grayStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		grayStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		grayStyle.setAlignment(CellStyle.ALIGN_CENTER);
		grayStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		grayStyle.setBorderBottom(CellStyle.BORDER_THIN);
		grayStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		grayStyle.setBorderTop(CellStyle.BORDER_THIN);
		grayStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		grayStyle.setBorderRight(CellStyle.BORDER_THIN);
		grayStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		grayStyle.setBorderLeft(CellStyle.BORDER_THIN);
		grayStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		
		CellStyle orangeStyle = workbook.createCellStyle();
		orangeStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		orangeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		orangeStyle.setAlignment(CellStyle.ALIGN_CENTER);
		orangeStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		orangeStyle.setBorderBottom(CellStyle.BORDER_THIN);
		orangeStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		grayStyle.setBorderTop(CellStyle.BORDER_THIN);
		orangeStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		orangeStyle.setBorderRight(CellStyle.BORDER_THIN);
		orangeStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		orangeStyle.setBorderLeft(CellStyle.BORDER_THIN);
		orangeStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		CellStyle greenStyle = workbook.createCellStyle();
		greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		greenStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		greenStyle.setAlignment(CellStyle.ALIGN_CENTER);
		greenStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		greenStyle.setBorderBottom(CellStyle.BORDER_THIN);
		greenStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		greenStyle.setBorderTop(CellStyle.BORDER_THIN);
		greenStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		greenStyle.setBorderRight(CellStyle.BORDER_THIN);
		greenStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		greenStyle.setBorderLeft(CellStyle.BORDER_THIN);
		greenStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		CellStyle redStyle = workbook.createCellStyle();
		redStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		redStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		redStyle.setAlignment(CellStyle.ALIGN_CENTER);
		redStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		redStyle.setBorderBottom(CellStyle.BORDER_THIN);
		redStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		redStyle.setBorderTop(CellStyle.BORDER_THIN);
		redStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		redStyle.setBorderRight(CellStyle.BORDER_THIN);
		redStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		redStyle.setBorderLeft(CellStyle.BORDER_THIN);
		redStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		CellStyle blueStyle = workbook.createCellStyle();
		blueStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
		blueStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		blueStyle.setAlignment(CellStyle.ALIGN_CENTER);
		blueStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		blueStyle.setBorderBottom(CellStyle.BORDER_THIN);
		blueStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		blueStyle.setBorderTop(CellStyle.BORDER_THIN);
		blueStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		blueStyle.setBorderRight(CellStyle.BORDER_THIN);
		blueStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		blueStyle.setBorderLeft(CellStyle.BORDER_THIN);
		blueStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		CellStyle commonStyle = workbook.createCellStyle();
		commonStyle.setAlignment(CellStyle.ALIGN_CENTER);
		commonStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		commonStyle.setBorderBottom(CellStyle.BORDER_THIN);
		commonStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		commonStyle.setBorderTop(CellStyle.BORDER_THIN);
		commonStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		commonStyle.setBorderRight(CellStyle.BORDER_THIN);
		commonStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		commonStyle.setBorderLeft(CellStyle.BORDER_THIN);
		commonStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		// 셀 넓이 지정하기(31열 추후에 변경사항이 있으면 추가하거나 제거 또는 각 열의 숫자 변경해야 함)
		// 4000 > 125 픽셀, 1000 > 31.25 픽셀, 100 > 3.125 픽셀
		int[] widthArray = {2400, 5500, 12000, 5000, 9000, 4000, 4000, 6500, 6500, 3300, 4000, 3300, 4000, 3300, 4000, 4000, 2400, 2400, 2400, 3300, 4000, 4000, 2800, 1900, 2400, 1900, 5500, 4100, 2400, 3400, 2400};
		for(int colWidth = 0; colWidth < widthArray.length; colWidth++) {
			sheet.setColumnWidth(colWidth, widthArray[colWidth]);
		}
		
		// 헤더 생성(4행 31열 추후에 변경사항이 있으면 숫자 변경해야 함)
		for(int rowNum = 0; rowNum < 4; rowNum++) {
			// 헤더를 입력할 행 생성
			SXSSFRow headerRow = (SXSSFRow) sheet.createRow(rowNum);
			
			for(int colNum = 0; colNum < 31; colNum++) {
				// 헤더 각각의 열 생성
				SXSSFCell cell = (SXSSFCell) headerRow.createCell(colNum);
				
				// 셀 테두리 스타일 적용
				cell.setCellStyle(commonStyle);
				
				// 병합될 셀의 첫셀에만 이름 및 스타일 적용(추후 변경사항이 있으면 수정해야 함)
				if(rowNum == 0) {
					switch(colNum) {
					case 0 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("연번");
						break;
					case 1 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("소속기관명");
						break;
					case 2 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("민간센터명");
						break;
					case 3 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("기업유형");
						break;
					case 4 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("기업명");
						break;
					case 5 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("사업장관리번호");
						break;
					case 6 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("HRD기초진단지");
						break;
					case 7 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("기업 교육훈련 수요 설문지");
						break;
					case 8 :
						cell.setCellStyle(grayStyle);
						cell.setCellValue("기업HRD이음컨설팅보고서");
						break;
					case 9 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("과정개발");
						break;
					case 16 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("당해연도 훈련실시 현황");
						break;
					case 26 :
						cell.setCellStyle(redStyle);
						cell.setCellValue("훈련성과분석");
						break;
					case 28 :
						cell.setCellStyle(blueStyle);
						cell.setCellValue("심화단계 컨설팅");
						break;
					default:
						break;
					}
				} else if(rowNum == 1) {
					switch(colNum) {
					case 9 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("AI추천");
						break;
					case 13 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("맞춤개발");
						break;
					case 16 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("참여여부");
						break;
					case 17 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("훈련분류");
						break;
					case 19 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("자체훈련 분류");
						break;
					case 23 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("위탁훈련 분류");
						break;
					case 26 :
						cell.setCellStyle(redStyle);
						cell.setCellValue("성취도 및 만족도 조사");
						break;
					case 27 :
						cell.setCellStyle(redStyle);
						cell.setCellValue("현업적용도 조사");
						break;
					case 28 :
						cell.setCellStyle(blueStyle);
						cell.setCellValue("심층진단");
						break;
					case 29 :
						cell.setCellStyle(blueStyle);
						cell.setCellValue("교육훈련체계");
						break;
					case 30 :
						cell.setCellStyle(blueStyle);
						cell.setCellValue("현장활동");
						break;
					default:
						break;
					}
					
				} else if(rowNum == 2) {
					switch(colNum) {
					case 9 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("표준개발");
						break;
					case 11 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("자체개발");
						break;
					case 13 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("사업주(자체)");
						break;
					case 14 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("S-OJT 직무전수");
						break;
					case 15 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("S-OJT 현장개선");
						break;
					case 17 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("자체훈련");
						break;
					case 18 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("위탁훈련");
						break;
					case 19 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("사업주(자체)");
						break;
					case 20 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("S-OJT 직무전수");
						break;
					case 21 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("S-OJT 현장개선");
						break;
					case 22 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("일학습병행");
						break;
					case 23 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("사업주");
						break;
					case 24 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("컨소시엄");
						break;
					case 25 :
						cell.setCellStyle(greenStyle);
						cell.setCellValue("지산맞");
						break;
					default:
						break;
					}
				} else {
					switch(colNum) {
					case 9 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("사업주(자체)");
						break;
					case 10 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("S-OJT 직무전수");
						break;
					case 11 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("사업주(자체)");
						break;
					case 12 :
						cell.setCellStyle(orangeStyle);
						cell.setCellValue("S-OJT 직무전수");
						break;
					default:
						break;
					}
				}
				
			}
			
		}
		
		// 헤더 셀 병합하기(추후 변경사항이 있으면 수정해야 함, 엑셀에서 표 만들어서 그거에 따라서 바꾸기)
		// CellRangeAddress(시작행, 끝행, 시작열, 끝열), 세로 병합은 앞의 두 숫자 다르게, 가로 병합은 뒤의 두 숫자 다르게 
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 15));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 16, 25));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 26, 27));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 28, 30));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 12));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 13, 15));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 16, 16));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 17, 18));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 19, 22));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 23, 25));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 26, 26));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 27, 27));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 28, 28));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 29, 29));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 30, 30));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 9, 10));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 11, 12));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 13, 13));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 14, 14));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 15, 15));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 17, 17));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 18, 18));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 19, 19));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 20, 20));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 21, 21));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 22, 22));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 23, 23));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 24, 24));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 25, 25));
		
		// 데이터 입력하기
		// 데이터 컬럼 이름(항목 바뀌면 수정해야 함)
		String[] dataArray = {"NO", "INSTT_NM", "PRVTCNTR_NM", "CORP_TYPE", "CORP_NM", "BPL_NO", "DGNS", "QESTNR", "BSISBNSL", "AI_STD_BPR", "AI_STD_SOJT", "AI_SLF_BPR", "AI_SLF_SOJT", "FIT_BPR", "FIT_SOJT", "FIT_SOJT_SITE", "PARTC_YN", "SLF_TR", "CNSGN_TR", "SLF_TR_BPR", "SLF_TR_SOJT", "SLF_TR_SOJT_SITE", "SLF_TR_WLB", "CNSGN_TR_BRP", "CNSGN_TR_CNSRTM", "CNSGN_TR_RIB", "RSLT_SNST", "RSLT_APPLC", "DEP_DGNS", "EDU_TR_SYS", "SITE_PRC"};
		for(int i = 0; i < data.size(); i++) {
			// 데이터를 입력할 행 성성(헤더가 4행이라 5행부터 생성)
			SXSSFRow dataRow = (SXSSFRow) sheet.createRow(i + 4);
			
			DataMap dt = (DataMap) data.get(i);
			for(int j = 0; j < dataArray.length; j++) {
				// 데이터가 들어갈 열 생성
				SXSSFCell cell = (SXSSFCell) dataRow.createCell(j);
				
				String dtVal = "";
				if(j == 0) {
					// 연번 setting
					dtVal = String.valueOf((i+1));
				} else {
					// dt에서 값 가져오기
					dtVal = (String) dt.get(dataArray[j]);
				}
				// 셀에 스타일 적용 및 데이터 입력하기
				cell.setCellStyle(commonStyle);
				cell.setCellValue(dtVal);
			}
		}
		
	}

	public void write(HttpServletResponse res, String fileName) throws IOException, java.io.IOException {
		res.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8") + ";");
		workbook.write(res.getOutputStream());
		res.getOutputStream().flush();
		res.getOutputStream().close();
		workbook.dispose();
	}
}

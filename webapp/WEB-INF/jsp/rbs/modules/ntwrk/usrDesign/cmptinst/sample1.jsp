<%@ page language="java" contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFWorkbook" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFSheet" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFRow" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFCell" %>
<%@page import="com.woowonsoft.egovframework.util.StringUtil"%>
<%@page import="java.net.URLEncoder"%>

<%
XSSFWorkbook workbook = new XSSFWorkbook();
XSSFSheet sheet = workbook.createSheet("Sheet1");

XSSFRow row0 = sheet.createRow(0);
XSSFRow row1 = sheet.createRow(1);
XSSFRow row2 = sheet.createRow(2);
String[] header = {"협의체명", "소속기관", "대표자명", "담당자명", "담당자 전화번호", "담당자 이메일", "우편번호", "주소", "상세주소", "비고"};
String[] testData = {"테스트협의체A", "울산지사", "홍길동", "이몽룡", "010-1234-5678", "hong@test.com", "00000", "테스트 주소1", "상세주소 2", "비고 테스트"};
String[] etc = {"(상기 테스트 데이터 및 해당 문구가 있는 행은 삭제 후 업로드 해주시기 바랍니다.)"};

for(int i=0;i < header.length ;i++) {
	String data = header[i];
	XSSFCell cell = row0.createCell(i);
	cell.setCellValue(data);
}
for(int i=0;i < testData.length ;i++) {
	String data = testData[i];
	XSSFCell cell = row1.createCell(i);
	cell.setCellValue(data);
}
for(int i=0;i < etc.length ;i++) {
	String data = etc[i];
	XSSFCell cell = row2.createCell(i);
	cell.setCellValue(data);
}

String fileName = "협의체 업로드 샘플 파일.xlsx";
String userAgent = request.getHeader("User-Agent");
boolean ie = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1;
if(ie) {
	fileName = URLEncoder.encode(fileName, "utf-8");
	if(!StringUtil.isEmpty(fileName)) fileName = fileName.replaceAll("[+]", "%20");
} else {
	fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
}

response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

workbook.write(response.getOutputStream());
%>
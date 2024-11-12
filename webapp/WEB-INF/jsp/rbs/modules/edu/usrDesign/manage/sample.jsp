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

XSSFRow row = sheet.createRow(0);
String[] header = {
		"교육명", "소속기관명", "교육장소", "교육시작일(YYYY-MM-DD)", "교육종료일(YYYY-MM-DD)", "총교육시간", 
		"강사명", "강사 연락처", "강사 이메일", "강사 소개글", "공개여부(Y/N)", "접수시작일(YYYY-MM-DD)", "접수종료일(YYYY-MM-DD)", 
		"수료증발급여부(Y/N)", "신청최대인원", "주요내용"};
for(int i=0;i < header.length ;i++) {
	String data = header[i];
	XSSFCell cell = row.createCell(i);
	cell.setCellValue(data);
}

String fileName = "교육과정 업로드 샘플 파일.xlsx";
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
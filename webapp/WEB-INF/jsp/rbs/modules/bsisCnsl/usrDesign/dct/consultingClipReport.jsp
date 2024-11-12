<%@page import="com.clipsoft.clipreport.oof.connection.OOFConnectionHTTP"%>
<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="com.clipsoft.clipreport.oof.connection.*"%>
<%@page import="java.io.File"%>
<%@page import="java.util.*"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.clipsoft.clipreport.export.option.PDFOption"%>
<%@page import="com.clipsoft.clipreport.server.service.ClipReportExport"%>
<%@page import="com.woowonsoft.egovframework.prop.RbsProperties"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%

OOFDocument oof = OOFDocument.newOOF();

// 서식 파일 추가 
// 개발서버 - %root%\\crf\\consulting.crf 운영서버 - %root%/crf/consulting.crf >> 변경해서 반영해야 함
OOFFile file = oof.addFile("crf.root", "%root%\\crf\\consulting.crf");

// 매개변수 필드 추가 (arg1:매개변수명, arg2:값)

// 기초진단지 IDX 가져오기
String bsiscnslIdx = (String) request.getAttribute("BSISCNSL_IDX");
//oof.addField("param1", "value");
oof.addField("BSISCNSL_IDX", bsiscnslIdx);

//서버에 파일로 저장 할 때
//저장할 파일 경로 및 이름 지정
String uploadPath = RbsProperties.getProperty("Globals.upload.file.path");
String fileName = uploadPath + "/bsisCnsl/report/consulting_" + bsiscnslIdx + ".pdf";
File localFileSave = new File(fileName);
OutputStream fileStream = new FileOutputStream(localFileSave);

PDFOption option = new PDFOption();

/* Data Connection */

// 1. DB (arg1:데이터셋명, arg2:DataConnection.properties에 설정한 dbName)
// oof.addConnectionData("데이터셋명","dbName");
oof.addConnectionData("hrddoctor","hrddb");
//oof.addConnectionData("HRD_PORTAL","hrdtest");
//oof.addConnectionData("데이터셋명","hrdtest");

// 2. XML, CSV, JSON
// Memo Type
// OOFConnectionMemo conn = oof.addConnectionMemo("커넥션명", "XMLData");
// Http Type
// OOFConnectionHTTP conn = oof.addConnectionHTTP("커넥션명", "url", "method");

// 2-1. XML 
// 반복노드 지정
//conn.addContentParamXML("데이터셋명", "인코딩", "반복노드");
	
// 2-2. CSV
// 구분자 지정
//conn.addContentParamCSV("데이터셋명", "인코딩", "필드구분자", "레코드구분자", "데이터셋구분자", "데이터셋번호");
	
// 2-3. JSON
// 반복노드 지정
//conn.addContentParamJSON("데이터셋명", "인코딩", "반복노드");


/* Param 동적 처리 샘플 */
/*
Enumeration<String> params = request.getParameterNames();
while(params.hasMoreElements()) {
	String name = (String)params.nextElement(); 
	String value = request.getParameter(name); 
	
	// Report File Add
	if(name.equals("rptname") ){
		file = oof.addFile("crf.root", "%root%/crf/"+value+".crf");
	}
	
	// Param Add
    file.addField(name, value);
}
*/

%><%@include file="/include/js/ClipReport5/Property.jsp"%><%
//세션을 활용하여 리포트키들을 관리하지 않는 옵션
//request.getSession().setAttribute("ClipReport-SessionList-Allow", false);
String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);

//서버에 pdf로 저장하기
int statusType = ClipReportExport.createExportForPDF(request, fileStream, propertyPath, oof, option);

//리포트의 특정 사용자 ID를 부여합니다.
//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
//clipreport5.properties 의 useuserid 옵션이 true 이고 기본 예제[String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);] 사용 했을 때 세션ID가 userID로 사용 됩니다.
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "userID");

//리포트key의 사용자문자열을 추가합니다.(문자숫자만 가능합니다.)
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "", "usetKey");

//리포트를 저장 스토리지를 지정하여 생성합니다.
//String resultKey =  ReportUtil.createReportByStorage(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "rpt1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../../include/js/ClipReport5/css/clipreport5.css">
<link rel="stylesheet" type="text/css" href="../../include/js/ClipReport5/css/UserConfig5.css">
<link rel="stylesheet" type="text/css" href="../../include/js/ClipReport5/css/font.css">

<script type='text/javascript' src='../../include/js/ClipReport5/js/jquery-1.11.1.js'></script>
<script type='text/javascript' src='../../include/js/ClipReport5/js/clipreport5.js'></script>
<script type='text/javascript' src='../../include/js/ClipReport5/js/UserConfig5.js'></script>
<script>
	var urlPath = document.location.protocol + "//" + document.location.host;
	function html2xml(divPath){
		var reportkey = "<%=resultKey%>";
		var report = createReport("${pageContext.request.contextPath}/include/js/ClipReport5/report_server.jsp", reportkey, document.getElementById(divPath));
		
		report.view();
	}
</script>
</head>
<body onload="html2xml('targetDiv1')">
<div id='targetDiv1' style='position:absolute;top:5px;left:5px;right:5px;bottom:5px;'>
	<span style="visibility: hidden; font-family:나눔고딕">.</span>
	<span style="visibility: hidden; font-family:NanumGothic">.</span>
</div>
</body>
</html>
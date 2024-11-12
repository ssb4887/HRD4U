<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="java.io.File"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crf.root", "%root%/crf/confirm_user.crf");

oof.addConnectionData("*", "hrddb");

Calendar todayDate = Calendar.getInstance();
Date cal = new Date();

SimpleDateFormat smpt = new SimpleDateFormat("yyyy-MM-dd");

String curDate = smpt.format(cal.getTime()); 

int month = todayDate.get(Calendar.MONTH)+1 ;
String monthTranse = "";

if(month < 10) {
	monthTranse = "0"+String.valueOf(month);
}

String toDate = todayDate.get(Calendar.YEAR) +""+ monthTranse +"01";
String ftDate  = curDate.replaceAll("-", "");

System.out.print("toDate ::: "+toDate);
System.out.print("ftDate ::: "+ftDate);

String param = (String)request.getParameter("hrpId") == null ? "" : (String)request.getParameter("hrpId");
String cndNum = (String)request.getParameter("cndNum") == null ? "" : (String)request.getParameter("cndNum");
String repCndNum = (String)request.getParameter("repCndNum") == null ? "" : (String)request.getParameter("repCndNum");
String emAppPer1 = (String)request.getParameter("emAppPer1") == null ? toDate : (String)request.getParameter("emAppPer1");
String emAppPer2 = (String)request.getParameter("emAppPer2") == null ? ftDate : (String)request.getParameter("emAppPer2");

if(emAppPer1.equals("")){
	emAppPer1 = toDate;
}

if(emAppPer2.equals("")){
	emAppPer2 = ftDate;
}

emAppPer1 = emAppPer1.replaceAll("-", "");
emAppPer2 = emAppPer2.replaceAll("-", "");

String url = "localhost:8080/ClipReport5/";

System.out.println("emAppPer1 " + emAppPer1);
System.out.println("emAppPer2 " + emAppPer2);

// parameter 
oof.addField("url","http://" + url + "ClipBarcodeVerify.jsp");
oof.addField("HRPID", param);
oof.addField("CNDNUM", cndNum);
oof.addField("REPCNDNUM", repCndNum);
oof.addField("FROMDATE", emAppPer2);
oof.addField("TODATE", emAppPer1);

%>
<%@include file="Property.jsp"%>
<%
//세션을 활용하여 리포트키들을 관리하지 않는 옵션
//request.getSession().setAttribute("ClipReport-SessionList-Allow", false);
String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);
//리포트의 특정 사용자 ID를 부여합니다.
//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
//clipreport5.properties 의 useuserid 옵션이 true 이고 기본 예제[String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);] 사용 했을 때 세션ID가 userID로 사용 됩니다.
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "userID");

//리포트key의 사용자문자열을 추가합니다.(문자숫자만 가능합니다.)
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "", "usetKey");

//리포트를 저장 스토리지를 지정하여 생성합니다.
//String resultKey =  ReportUtil.createReportByStorage(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "rpt1");
%>
<!DOCTYPE html>
<html>
<head>
<title>위촉장 인쇄</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="./css/clipreport5.css">
<link rel="stylesheet" type="text/css" href="./css/UserConfig5.css">
<link rel="stylesheet" type="text/css" href="./css/font.css">

<script type='text/javascript' src='./js/jquery-1.11.1.js'></script>
<script type='text/javascript' src='./js/clipreport5_con.js'></script>
<script type='text/javascript' src='./js/UserConfig5.js'></script>
<script type='text/javascript'>
	
function html2xml(divPath){	
    var reportkey = "<%=resultKey%>";
	var report = createReport("./report_server.jsp", reportkey, document.getElementById(divPath));
   
	//리포트 실행
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

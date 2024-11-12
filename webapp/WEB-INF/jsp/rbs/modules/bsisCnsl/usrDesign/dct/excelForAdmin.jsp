<%@page import="com.woowonsoft.egovframework.form.DataForm"%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../include/commonTop.jsp" %>
<!-- <spring:message var="msgItemCntName" code="item.contact.member"/> -->
<!-- <spring:message var="msgItemLabelName" code="item.menu.point.person.name"/> -->
<%@page import="com.woowonsoft.egovframework.util.StringUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.woowonsoft.egovframework.util.WebsiteDetailsHelper"%>
<%@page import="rbs.egovframework.WebsiteVO"%>
<%@page import="com.woowonsoft.egovframework.util.JSONObjectUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%
Date currentDate = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
String formattedDate = dateFormat.format(currentDate);
String titleName = "능력개발전담주치의 기업훈련컨설팅 관리대장_" + formattedDate + "";
String userAgent = request.getHeader("User-Agent");
String fileName = titleName + ".xls";
titleName = StringUtil.replaceHtmlN(titleName);
boolean ie = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1;
if(ie) {
	fileName = URLEncoder.encode(fileName, "utf-8");
	if(!StringUtil.isEmpty(fileName)) fileName = fileName.replaceAll("[+]", "%20");
} else {
	fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
}

response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
response.setHeader("Content-Transfer-Encoding", "binary");
%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<title><%=titleName%></title>
	<meta http-equiv="Content-Type" content="application/vnd.ms-excel?charset=utf-8">
	<style>
	<!--
	table
		{mso-displayed-decimal-separator:"\.";
		mso-displayed-thousand-separator:"\,";}
	@page
		{margin:.75in .71in .75in .71in;
		mso-header-margin:.31in;
		mso-footer-margin:.31in;
		mso-page-orientation:landscape;}
	
	body {
		font-family:"맑은 고딕";
		color:black;
	}
	
	caption
	{
		text-align            : center;
		font-size             : 12.0pt;
		font-weight			:bold;
		padding               : 10px 0;
		height:50px;
	}
	
	table
	{
		border               :.5pt solid #000;
		border-collapse      : collapse;
	 	text-align			 : center; 
		font-size             : 9.0pt;
	}
	
	thead th
	{
		background            : #DBE5F1;
		padding               : 5px 10px;
		border-right:.5pt solid #A5A5A5;
		border-bottom:.5pt solid black;	
	 	text-align			  : center;
	 	font-weight: normal;
	}
	
	td, tfoot th
	{
		padding               : 5px 10px;
		border-right:.5pt solid #A5A5A5;
		border-bottom:.5pt solid #A5A5A5;
	 	text-align			 : center; 
	}
	.rt{text-align: right;}
	.tlt{text-align: left;}
	//-->
	</style>
</head>
<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
	<table summary="<%=titleName%> 목록을 제공합니다." style="width:100%;">
		<caption><%=titleName%></caption>
		<thead>
			<tr>
				<th rowspan="2">연번</th>
				<th rowspan="2">기관명</th>
				<th colspan="2">발급자(주치의)</th>
				<th rowspan="2">발급일자(컨설팅 완료일자 또는 클리닉 기업 선정일자)</th>
				<th rowspan="2">보고서 종류</th>
				<th rowspan="2">발급번호</th>
				<th colspan="8">보고서 수령자(기업 담당자)</th>
				<th rowspan="2">비고(기타 특이사항)</th>
			</tr>
			<tr>
				<th>직위</th>
				<th>성명</th>
				<th>고용보험관리번호</th>
				<th>기업유형</th>
				<th>기업명</th>
				<th>보고서 전달방법</th>
				<th>직위</th>
				<th>성명</th>
				<th>연락처</th>
				<th>메일주소</th>
			</tr>
		</thead>
		<tbody class="alignC">
			<c:if test="${empty list}">
				<tr>
					<td colspan="16" class="nolist"><spring:message code="message.no.list"/></td>
				</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
				<tr>
					<td><c:out value='${i.count}' /></td>
					<td>
						<c:if test="${not empty listDt.INSTT_NO or not empty listDt.INSTT_NAME}">
							<c:out value='${listDt.INSTT_NO}' />.<c:out value='${listDt.INSTT_NAME}' />
						</c:if>
					</td>
					<td><c:out value='${listDt.DOCTOR_OFCPS}' /></td>
					<td><c:out value='${listDt.DOCTOR_NAME}' /></td>
					<td><c:out value='${listDt.ISSUE_DATE}' /></td>
					<td><c:out value='${listDt.DOC}' /></td>
					<td><c:out value='${listDt.ISSUE_NO}' /></td>
					<td><c:out value='${listDt.BPL_NO}' /></td>
					<td><c:out value='${listDt.PRISUP_CD}' /></td>
					<td><c:out value='${listDt.CORP_NAME}' /></td>
					<td></td>
					<td><c:out value='${listDt.CORP_PIC_OFCPS}' /></td>
					<td><c:out value='${listDt.CORP_PIC_NAME}' /></td>
					<td><c:out value='${listDt.CORP_PIC_TELNO}' /></td>
					<td><c:out value='${listDt.CORP_PIC_EMAIL}' /></td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
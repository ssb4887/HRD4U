<%@page import="com.woowonsoft.egovframework.form.DataForm"%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../../include/commonTop.jsp"%>
<spring:message var="msgItemCntName" code="item.contact.member"/>
<spring:message var="msgItemLabelName" code="item.menu.point.person.name"/>
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
String titleName = "회원관리목록현황_" + formattedDate + "";
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
		<colgroup>
			<col width="500px" />
			<col width="500px" />
			<col width="500px" />
			<col width="500px" />
			<col width="500px" />
			<col width="500px" />
			<col width="500px" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">아이디</th>
				<th scope="col">성명</th>
				<th scope="col">회원구분</th>
				<th scope="col">소속기관(공단)</th>
				<th scope="col">기업(기관)</th>
				<th scope="col" class="end">가입일</th>
			</tr>
		</thead>
		<tbody class="alignC">
			<c:if test="${empty list}">
				<tr>
					<td colspan="7" class="nolist"><spring:message code="message.no.list"/></td>
				</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
				<tr>
					<td class="num">${i.count}</td>
					<td class="subject">${listDt.MEMBER_ID }</td>
					<td>${listDt.MEMBER_NAME }</td>
					<c:choose>
						<c:when test="${listDt.USERTYPE_IDX >= 40}"><td>직원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 30}"><td>소속직원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 20}"><td>민간센터</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 10}"><td>컨설턴트</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 5}"><td>기업회원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 1}"><td>개인회원</td></c:when>
					</c:choose>
					<td>
						<c:choose>
							<c:when test="${listDt.USERTYPE_IDX >= 30}">
								
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						${listDt.REGIMENT}
					</td>
					<td>${listDt.ORG_NAME}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
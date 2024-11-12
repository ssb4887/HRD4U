<%@page import="com.woowonsoft.egovframework.form.DataForm"%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../include/commonTop.jsp"%>
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
String titleName = "방문기업서식목록_"+formattedDate+"";
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
			<col width="500px" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col"><itui:objectItemName itemId="admsfmNm" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="admsfmVer" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="prtbizIdx" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="jobType" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="sgntr" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="regiDate" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="useYn" itemInfo="${itemInfo}"/></th>
			</tr>
		</thead>
		<tbody class="alignC">
			<c:if test="${empty list}">
				<tr>
					<td colspan="8" class="nolist"><spring:message code="message.no.list"/></td>
				</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
				<tr>
					<td class="num">${i.count}</td>
					<td><itui:objectView itemId="admsfmNm" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="admsfmVer" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><c:out value="${listDt.PRTBIZ_TITLE }"/></td>
					<td><c:out value="${listDt.JOB_TYPE }"/></td>
					<td><c:out value="${listDt.SGNTR }"/></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
					<c:choose>
						<c:when test="${listDt.DCSN_YN eq 'Y' && listDt.USE_YN eq 'Y' }">
							<td><span>사용</span></td>
						</c:when>
						<c:when test="${listDt.DCSN_YN eq 'Y' && listDt.USE_YN eq 'N' }">
							<td><span>미사용</span></td>
						</c:when>
						<c:when test="${listDt.DCSN_YN eq 'N' && listDt.TMPR_SAVE_YN eq 'Y'}">
							<td><span>미완료</span></td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
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
<%
// String titleName = null;
// String userAgent = request.getHeader("User-Agent");
// JSONObject crtMenu = JSONObjectUtil.getJSONObject(request.getAttribute("crtMenu"));
// String menuName = JSONObjectUtil.getString(crtMenu, "menu_name");
// WebsiteVO usrSiteVO = (WebsiteVO)WebsiteDetailsHelper.getWebsiteInfo();
// titleName = usrSiteVO.getSiteName() + " " + menuName;
//  = titleName + ".xls";
// titleName = StringUtil.replaceHtmlN(titleName);

String userAgent = request.getHeader("User-Agent");
String fileName = "종합관리_심화컨설팅_목록.xls";
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
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<title>종합관리 심화컨설팅 목록</title>
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
	<table style="width:100%;">
		<caption>종합관리 심화컨설팅 목록</caption>
		<colgroup>
			<col width="500px" />
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
			<th scope="col">연번</th>
			<th scope="col">고용보험 관리번호</th>
			<th scope="col">사업장명</th>
			<th scope="col">소속기관</th>
			<th scope="col">경과일</th>
			<th scope="col">종류</th>
			<th scope="col">접수</th>
			<th scope="col">보고서 승인</th>
			<th scope="col">비용 처리</th>
		</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="item" items="${list}" varStatus="i">
			<tr>
				<td><c:out value="${item.TOTAL_COUNT-item.RN + 1 }" /></td>
				<td><c:out value="${item.BPL_NO }" /></td>
				<td><c:out value="${item.CORP_NAME }" /></td>
				<td><c:out value="${empty item.INSTT_NAME ? '-' : item.INSTT_NAME}" /></td>
				<td>
					<c:out value="D+${empty item.PASSED ? 0 : item.PASSED}" />
				</td>
				<td>
					<c:out value="${item.CNSL_TYPE <= 3 ? '과정개발' : '심화' }" />
				</td>
				<td>
					<c:choose>
						<c:when test="${empty item.CNSL_STATUS }">
							<span>-</span>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${(item.CNSL_STATUS eq 55) or (item.CNSL_STATUS eq 70) or (item.CNSL_STATUS eq 72) or (item.CNSL_STATUS eq 75)}">
									<c:out value="${item.CNSL_DATE }" />
								</c:when>
								<c:when test="${item.CNSL_STATUS eq 80 }">
									<c:out value="${item.CNSL_DATE }" />
								</c:when>
								<c:otherwise>
									<c:out value="진행 중(${item.CNSL_STATUS })" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:set var="passed" value="${item.PASSED >= 10 ? 3 : item.PASSED >= 7 ? 2 : item.PASSED >= 5 ? 1 : 0 }" />
					<c:if test="${(item.NOW == 'cnsl') and (item.PASSED >= 5)}">
					<p>
						<c:forEach begin="1" end="${passed }">
							<span class="fire"></span>
						</c:forEach>
					</p> 
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty item.REPORT_STATUS }">
							<span>-</span>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${(item.REPORT_STATUS eq 55) or (item.REPORT_STATUS eq 70) or (item.REPORT_STATUS eq 72) or (item.REPORT_STATUS eq 75)}">
									<c:out value="${item.REPORT_DATE }" />
								</c:when>
								<c:when test="${item.REPORT_STATUS eq 80 }">
									<c:out value="${item.REPORT_DATE }" />
								</c:when>
								<c:otherwise>
									<c:out value="${confirmProgress.get(item.REPORT_STATUS) }" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:set var="passed" value="${item.PASSED >= 10 ? 3 : item.PASSED >= 7 ? 2 : item.PASSED >= 5 ? 1 : 0 }" />
					<c:if test="${(item.NOW == 'report') and (item.PASSED >= 5)}">
					<p>
						<c:forEach begin="1" end="${passed }">
							<span class="fire"></span>
						</c:forEach>
					</p> 
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty item.COST_STATUS }">
							<span>-</span>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${(item.COST_STATUS eq 55) or (item.COST_STATUS eq 70) or (item.COST_STATUS eq 72) or (item.COST_STATUS eq 75)}">
									<c:out value="${item.COST_DATE }" />
								</c:when>
								<c:when test="${item.COST_STATUS eq 80 }">
									<c:out value="${item.COST_DATE }" />
								</c:when>
								<c:otherwise>
									<c:out value="${confirmProgress.get(item.COST_STATUS) }" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:set var="passed" value="${item.PASSED >= 10 ? 3 : item.PASSED >= 7 ? 2 : item.PASSED >= 5 ? 1 : 0 }" />
					<c:if test="${(item.NOW == 'cost') and (item.PASSED >= 5)}">
					<p>
						<c:forEach begin="1" end="${passed }">
							<span class="fire"></span>
						</c:forEach>
					</p> 
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
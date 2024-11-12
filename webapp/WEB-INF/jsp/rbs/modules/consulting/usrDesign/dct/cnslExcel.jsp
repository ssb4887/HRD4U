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
String fileName = "컨설팅_목록.xls";
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
	<title>컨설팅 목록</title>
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
		<caption>컨설팅 목록</caption>
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
			<th scope="col">기업명</th>
			<th scope="col">사업장관리번호</th>
			<th scope="col">관할기관</th>
			<th scope="col">구분</th>
			<th scope="col">유형</th>
			<th scope="col">등록일</th>
			<th scope="col">상태</th>
			<th scope="col">변경신청상태</th>
		</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
			<tr>
				<td class="num">${i.count}</td>
				<td><c:out value="${listDt.corpNm}" /></td>
				<td><c:out value="${listDt.bplNo}"/></td>
				<td>
					<c:forEach var="instt" items="${insttList}" varStatus="i">
						<c:if test="${listDt.cmptncBrffcIdx eq instt.INSTT_IDX}">
							<c:out value="${instt.INSTT_NAME}" />
						</c:if>
					</c:forEach>
				</td>
				<td>
					<c:choose>
						<c:when test="${listDt.cnslType eq '1' or listDt.cnslType eq '2' or listDt.cnslType eq '3'}">과정개발</c:when>
						<c:when test="${listDt.cnslType eq '4' or listDt.cnslType eq '5' or listDt.cnslType eq '6'}">심화</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${listDt.cnslType eq '1'}">사업주</c:when>
						<c:when test="${listDt.cnslType eq '2'}">일반직무전수 OJT</c:when>
						<c:when test="${listDt.cnslType eq '3'}">과제수행 OJT</c:when>
						<c:when test="${listDt.cnslType eq '4'}">심층진단</c:when>
						<c:when test="${listDt.cnslType eq '5'}">훈련체계수립</c:when>
						<c:when test="${listDt.cnslType eq '6'}">현장활용</c:when>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.regiDate}" />
				</td>
				<td>
					승인
				</td>
				<td>
					<c:choose>
						<c:when test="${listDt.confmStatus eq 70}">
							변경요청
						</c:when>
						<c:when test="${listDt.confmStatus eq 72}">
							승인
						</c:when>
						<c:when test="${listDt.confmStatus eq 53}">
							반려
						</c:when>
						<c:when test="${listDt.confmStatus eq 75}">
							중도포기 요청
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
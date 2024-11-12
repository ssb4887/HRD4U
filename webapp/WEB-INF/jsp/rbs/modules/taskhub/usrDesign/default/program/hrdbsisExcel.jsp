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
String fileName = "종합관리_기업HRD이음컨설팅_목록.xls";
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
	<title>종합관리 HRD 기초 컨설팅 목록</title>
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
		<caption>종합관리 HRD 기초 컨설팅 목록</caption>
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
			<th scope="col">연번</th>
			<th scope="col">고용보험 관리번호</th>
			<th scope="col">사업장명</th>
			<th scope="col">소속기관</th>
			<th scope="col">경과일</th>
			<th scope="col">HRD 기초 진단</th>
			<th scope="col">설문조사</th>
			<th scope="col">기초 컨설팅</th>
		</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
			<tr>
				<td class="num">${listDt.TOTAL_COUNT - listDt.RN + 1}</td>
				<td><c:out value="${listDt.BPL_NO}"/></td>
				<td><c:out value="${listDt.CORP_NAME}"/></td>
				<td><c:out value="${listDt.INSTT_NAME}"/></td>
				<td>
					<c:choose>
						<c:when test="${listDt.BSIS_STATUS == 0 && listDt.BSIS_PASSED >= 5 }">
							D+${listDt.BSIS_PASSED }
						</c:when>
						<c:when test="${listDt.RSLT_STATUS == 0  }">
							D+${listDt.RSLT_PASSED }
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty listDt.BSC_STATUS }">
							-
						</c:when>
						<c:otherwise>
							<c:out value="${listDt.BSC_STATUS eq 1 ? listDt.BSC_ISSUE : '작성 중'}"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty listDt.RSLT_STATUS }">
							<span>-</span>
						</c:when>
						<c:otherwise>
							<c:out value="${listDt.RSLT_STATUS eq 1 ? listDt.RSLT_ISSUE : '작성 중'}" />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${listDt.RSLT_STATUS eq 0 and (listDt.RSLT_PASSED == 5 or listDt.RSLT_PASSED == 6) }">
							<span class="fire"></span>
						</c:when>
						<c:when test="${listDt.RSLT_STATUS eq 0 and (listDt.RSLT_PASSED >= 7 and listDt.RSLT_PASSED <= 9) }">
							<span class="fire"></span><span class="fire"></span>
						</c:when>
						<c:when test="${listDt.RSLT_STATUS eq 0 and (listDt.RSLT_PASSED >= 10) }">
							<span class="fire"></span><span class="fire"></span><span class="fire"></span>
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:out value="${listDt.BSIS_STATUS eq 1 ? listDt.BSIS_ISSUE : '작성 중'}"/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
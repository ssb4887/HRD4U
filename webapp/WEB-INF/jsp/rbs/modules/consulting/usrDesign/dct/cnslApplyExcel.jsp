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
String fileName = "주치의_컨설팅_신청.xls";
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
	<title>주치의 컨설팅 신청 목록</title>
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
		<caption>주치의 컨설팅 신청 목록</caption>
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
			<col width="500px" />
			<col width="500px" />
		</colgroup>
		<thead>
		<tr>
			<th scope="col" class="number">번호</th>
			<th scope="col" class="title">기업명</th>
			<th scope="col" class="writer">사업장관리번호</th>
			<th scope="col" class="date">관할지부지사</th>
			<th scope="col" class="date">담당자명</th>
			<th scope="col" class="date">연락처</th>
			<th scope="col" class="date">이메일</th>
			<th scope="col" class="date">신청일자</th>
			<th scope="col" class="date">처리일자</th>
			<th scope="col" class="date">처리자명</th>
			<th scope="col" class="date">상태</th>
		</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td colspan="11" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:set var="listIdxName" value="${settingInfo.idx_name}" />
			<c:set var="listColumnName" value="${settingInfo.idx_column}" />
			<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
			<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt[listColumnName]}" />
			
				<tr id="column">
					<td class="num">
						<c:out value="${i.count}" />
					</td>
					<td class="title">
						<c:out value="${listDt.CORP_NM}" />
					</td>
					<td class="title">
						<c:out value="${listDt.BPL_NO}" />
					</td>
					<td class="writer">
						<c:out value="${listDt.INSTT_NAME}" />
					</td>	
					<td class="title">
						<c:out value="${listDt.CORP_PIC_NM}" />
					</td>
					<td>
						<c:out value="${listDt.CORP_PIC_TELNO}" />
					</td>
					<td>
						<c:out value="${listDt.CORP_PIC_EMAIL}" />
					</td>
					<td class="date">
						<c:out value="${fn:substring(listDt.REGI_DATE,0,10)}" />
					</td>
					<td class="date">
						<c:out value="${listDt.LAST_MODI_NAME}" />
					</td>
					<td>
						<c:out value="${fn:substring(listDt.LAST_MODI_DATE,0,10)}" />
					</td>
					<td>
						<c:out value="${listDt.STATUS eq '0' ? '미처리' : '처리완료'}" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
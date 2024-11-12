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
String fileName = "협약관리_목록.xls";
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
	<title>협약관리 목록</title>
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
		<caption>협약관리 목록</caption>
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
			<th scope="col">협약번호</th>
			<th scope="col">사업연도</th>
			<th scope="col">지원기관</th>
			<th scope="col">소속기관</th>
			<th scope="col">고용보험 관리번호</th>
			<th scope="col">기업명</th>
			<th scope="col">기업구분</th>
			<th scope="col">현황</th>
			<th scope="col">체결일자</th>
			<th scope="col">완료일자</th>
		</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td colspan="11" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
			<tr>
				<td class="">
					<c:out value="${empty listDt.AGREM_NO ? '-' : listDt.AGREM_NO}"/>
				</td>
				<td>${empty listDt.YEAR ? '-' : listDt.YEAR}</td>
				<td>${empty listDt.PRVTCNTR_NAME ? '-' : listDt.PRVTCNTR_NAME }</td>
				<td>${empty listDt.INSTT_NAME ? '-' : listDt.INSTT_NAME}</td>
				<td data-key="bpl_no" data-value="${listDt.BPL_NO }">${empty listDt.BPL_NO ? '-' : listDt.BPL_NO }</td>	
				<td data-key="bpl_nm" data-value="${listDt.BPL_NM }">${empty listDt.BPL_NM ? '-' : listDt.BPL_NM }</td>
				<td data-key="lrscl_corp_se" data-value="${listDt.LRSCL_CORP_SE }">${empty listDt.LRSCL_CORP_SE ? '-' : listDt.LRSCL_CORP_SE eq 1 ? '대규모기업' : '우선지원기업' }</td>
				<c:choose>
					<c:when test="${loginVO.usertypeIdx eq 20 and (listDt.STATUS eq 7 or listDt.STATUS eq 40) }">
						<td class=""><a id="link" style="text-decoration: underline" href="">${confirmProgress.get(listDt.STATUS) }</a></td>
					</c:when>
					<c:otherwise>
						<td class="">${confirmProgress.get(listDt.STATUS) }</td>
					</c:otherwise>
				</c:choose>
				<td class="date"><fmt:formatDate pattern="yy/MM/dd" value="${empty listDt.CNCLS_DATE ? null : listDt.CNCLS_DATE}"/></td>
				<td class="date"><fmt:formatDate pattern="yy/MM/dd" value="${listDt.END_DATE}"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
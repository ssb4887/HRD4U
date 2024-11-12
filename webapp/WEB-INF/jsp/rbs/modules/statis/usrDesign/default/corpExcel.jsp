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
// StringName = null;
// String userAgent = request.getHeader("User-Agent");
// JSONObject crtMenu = JSONObjectUtil.getJSONObject(request.getAttribute("crtMenu"));
// String menuName = JSONObjectUtil.getString(crtMenu, "menu_name");
// WebsiteVO usrSiteVO = (WebsiteVO)WebsiteDetailsHelper.getWebsiteInfo();
//Name = usrSiteVO.getSiteName() + " " + menuName;
//  =Name + ".xls";
//Name = StringUtil.replaceHtmlN(titleName);

String userAgent = request.getHeader("User-Agent");
String fileName = "통계 기업 목록.xls";
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
	<title>통계 기업 목록</title>
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
		border-bottom:.5pt solid #A5A5A5;	
	 	text-align			  : center;
	 	font-weight: normal;
	}
	
	tbody td:first-child
	{
		border-top:.5pt solid black;	
	}
	
	td, tfoot th
	{
		padding               : 5px 10px;
		border-right:.5pt solid #A5A5A5;
		border-bottom:.5pt solid #A5A5A5;
	 	text-align			 : center; 
	}
	
	.gray{
		background			: #D0CECE;
	}
	.navy{
		background			: #D6DCE4;
	}
	.green{
		background			: #E2EFDA;
	}
	.orange{
		background			: #FFF2CC;
	}
	.red{
		background			: #FCE4D6;
	}
	.blue{
		background		: #DDEBF7;
	}
	.rt{text-align: right;}
	.tlt{text-align: left;}
	//-->
	</style>
</head>
<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
	<table style="width:100%;">
		<caption>통계지원 기업 목록(이름 바꿔야 함)</caption>
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
				<th rowspan="4" class="gray">연번</th>
				<th rowspan="4" class="gray">소속기관명</th>
				<th rowspan="4" class="gray">민간센터명</th>
				<th rowspan="4" class="gray">기업유형</th>
				<th rowspan="4" class="gray">기업명</th>
				<th rowspan="4" class="gray">사업장관리번호</th>
				<th rowspan="4" class="gray">HRD기초진단지</th>
				<th rowspan="4" class="gray">기업 교육훈련 수요 설문지</th>
				<th rowspan="4" class="gray">기업HRD이음컨설팅보고서</th>
				<th colspan="7" class="orange">과정개발</th>
				<th colspan="10" class="green">당해연도 훈련실시 현황</th>
				<th colspan="2" class="red">훈련성과분석</th>
				<th colspan="3" class="blue">심화단계 컨설팅</th>
			</tr>
			<tr>
				<th colspan="4" class="orange">AI추천</th>
				<th colspan="3" class="orange">맞춤형 개발</th>
				<th rowspan="3" class="green">참여여부</th>
				<th colspan="2" class="green">훈련분류</th>
				<th colspan="4" class="green">자체훈련 분류</th>
				<th colspan="3" class="green">위탁훈련 분류</th>
				<th rowspan="3" class="red">성취도 및 만족도 조사</th>
				<th rowspan="3" class="red">현업적용도 조사</th>
				<th rowspan="3" class="blue">심층진단</th>
				<th rowspan="3" class="blue">교육훈련체계</th>
				<th rowspan="3" class="blue">현장활동</th>
			</tr>
			<tr>
				<th colspan="2" class="orange">표준개발</th>
				<th colspan="2" class="orange">자체개발</th>
				<th rowspan="2" class="orange">사업주(자체)</th>
				<th rowspan="2" class="orange">일반직무전수 OJT</th>
				<th rowspan="2" class="orange">과제수행 OJT</th>
				<th rowspan="2" class="green">자체훈련</th>
				<th rowspan="2" class="green">위탁훈련</th>
				<th rowspan="2" class="green">사업주(자체)</th>
				<th rowspan="2" class="green">일반직무전수 OJT</th>
				<th rowspan="2" class="green">과제수행 OJT</th>
				<th rowspan="2" class="green">일학습병행</th>
				<th rowspan="2" class="green">사업주</th>
				<th rowspan="2" class="green">컨소시엄</th>
				<th rowspan="2" class="green">지산맞</th>
			</tr>
			<tr>
				<th class="orange">사업주(자체)</th>
				<th class="orange">일반직무전수 OJT</th>
				<th class="orange">사업주(자체)</th>
				<th class="orange">일반직무전수 OJT</th>
			</tr>
		</thead>
		<tbody class="alignC">
			<c:if test="${empty list}">
				<tr>
					<td colspan="31" class="nolist"><spring:message code="message.no.list"/></td>
				</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${listDt.INSTT_NM}</td>
					<td>${listDt.PRVTCNTR_NM}</td>
					<td>${listDt.CORP_TYPE}</td>
					<td>${listDt.CORP_NM}</td>
					<td>${listDt.BPL_NO}</td>
					<td>${listDt.DGNS}</td>
					<td>${listDt.QESTNR}</td>
					<td>${listDt.BSISBNSL}</td>
					<td>${listDt.AI_STD_BPR}</td>
					<td>${listDt.AI_STD_SOJT}</td>
					<td>${listDt.AI_SLF_BPR}</td>
					<td>${listDt.AI_SLF_SOJT}</td>
					<td>${listDt.FIT_BPR}</td>
					<td>${listDt.FIT_SOJT}</td>
					<td>${listDt.FIT_SOJT_SITE}</td>
					<td>${listDt.PARTC_YN}</td>
					<td>${listDt.SLF_TR}</td>
					<td>${listDt.CNSGN_TR}</td>
					<td>${listDt.SLF_TR_BPR}</td>
					<td>${listDt.SLF_TR_SOJT}</td>
					<td>${listDt.SLF_TR_SOJT_SITE}</td>
					<td>${listDt.SLF_TR_WLB}</td>
					<td>${listDt.CNSGN_TR_BRP}</td>
					<td>${listDt.CNSGN_TR_CNSRTM}</td>
					<td>${listDt.CNSGN_TR_RIB}</td>
					<td>${listDt.RSLT_SNST}</td>
					<td>${listDt.RSLT_APPLC}</td>
					<td>${listDt.DEP_DGNS}</td>
					<td>${listDt.EDU_TR_SYS}</td>
					<td>${listDt.SITE_PRC}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
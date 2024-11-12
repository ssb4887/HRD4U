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
String fileName = "종합관리_기업조회(기업)_목록.xls";
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
	<title>종합관리 기업조회_기업 목록</title>
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
		<caption>종합관리 기업조회_기업 목록</caption>
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
			<th scope="col">연번</th>
			<th scope="col">고용보험 관리번호</th>
			<th scope="col">회원가입</th>
			<th scope="col">사업장명</th>
			<th scope="col">소속기관</th>
			<th scope="col">기업HRD이음컨설팅</th>
			<th scope="col">심화 컨설팅</th>
			<th scope="col">훈련 맞춤 개발 컨설팅</th>
			<th scope="col">S-OJT</th>
			<th scope="col">능력개발클리닉</th>
			<th scope="col">#태그</th>
		</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td colspan="11" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="item" items="${list}" varStatus="i">
			<tr>
				<td>${item.TOTAL_COUNT-item.RN+1 }</td>
				<td>${item.BPL_NO }</td>
				<td>${item.MEMBER_YN }</td>
				<td>${item.BPL_NM }</td>
				<td>${item.INSTT_NAME }</td>
				<td>
					<c:choose>
						<c:when test="${item.BSIS_STAGE == '기초진단'}">
							<c:out value="${item.BSIS_STAGE }"/>
						</c:when>
						<c:when test="${item.BSIS_STAGE == '설문조사'  }">
							<c:out value="${item.BSIS_STAGE }"/>
						</c:when>
						<c:when test="${item.BSIS_STAGE == '기초컨설팅'  }">
							<c:out value="${item.BSIS_STAGE }"/>
						</c:when>
						<c:otherwise>
						-
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${item.BSIS_DELAY >= 5 and item.BSIS_DELAY < 7}">
							<span class="fire" />
						</c:when>
						<c:when test="${item.BSIS_DELAY >= 7 and item.BSIS_DELAY < 10}">
							<span class="fire" /><span class="fire" />
						</c:when>
						<c:when test="${item.BSIS_DELAY >= 10}">
							<span class="fire" /><span class="fire" /><span class="fire" />
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.ADV_STAGE == '컨설팅 신청'}">
							<c:out value="${item.ADV_STAGE }"/>
						</c:when>
						<c:when test="${item.ADV_STAGE == '보고서 작성'  }">
							<c:out value="${item.ADV_STAGE }"/>
						</c:when>
						<c:otherwise>
						-
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${item.ADV_DELAY >= 5 and item.ADV_DELAY < 7}">
							<span class="fire" />
						</c:when>
						<c:when test="${item.ADV_DELAY >= 7 and item.ADV_DELAY < 10}">
							<span class="fire" /><span class="fire" />
						</c:when>
						<c:when test="${item.ADV_DELAY >= 10}">
							<span class="fire" /><span class="fire" /><span class="fire" />
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.CUSTOM_STAGE == '컨설팅 신청'}">
							<c:out value="${item.CUSTOM_STAGE }"/>
						</c:when>
						<c:when test="${item.CUSTOM_STAGE == '보고서 작성'  }">
							<c:out value="${item.CUSTOM_STAGE }"/>
						</c:when>
						<c:when test="${item.CUSTOM_STAGE == '비용 처리'  }">
							<c:out value="${item.CUSTOM_STAGE }"/>
						</c:when>
						<c:otherwise>
						-
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${item.CUSTOM_DELAY >= 5 and item.CUSTOM_DELAY < 7}">
							<span class="fire" />
						</c:when>
						<c:when test="${item.CUSTOM_DELAY >= 7 and item.CUSTOM_DELAY < 10}">
							<span class="fire" /><span class="fire" />
						</c:when>
						<c:when test="${item.CUSTOM_DELAY >= 10}">
							<span class="fire" /><span class="fire" /><span class="fire" />
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.SOJT_DELAY >= 5 and item.SOJT_DELAY < 7}">
							<span class="fire" />
						</c:when>
						<c:when test="${item.SOJT_DELAY >= 7 and item.SOJT_DELAY < 10}">
							<span class="fire" /><span class="fire" />
						</c:when>
						<c:when test="${item.SOJT_DELAY >= 10}">
							<span class="fire" /><span class="fire" /><span class="fire" />
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.CLINIC_STAGE eq '클리닉 신청' }">
							<c:out value="${item.CLINIC_STAGE }"/>
						</c:when>
						<c:when test="${item.CLINIC_STAGE eq '계획 수립' }">
							<c:out value="${item.CLINIC_STAGE }"/>
						</c:when>
						<c:when test="${item.CLINIC_STAGE eq '활동 결과' }">
							<c:out value="${item.CLINIC_STAGE }"/>
						</c:when>
						<c:when test="${item.CLINIC_STAGE eq '비용 처리' }">
							<c:out value="${item.CLINIC_STAGE }"/>
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${item.CLINIC_DELAY >= 5 and item.CLINIC_DELAY < 7}">
							<span class="fire" />
						</c:when>
						<c:when test="${item.CLINIC_DELAY >= 7 and item.CLINIC_DELAY < 10}">
							<span class="fire" /><span class="fire" />
						</c:when>
						<c:when test="${item.CLINIC_DELAY >= 10}">
							<span class="fire" /><span class="fire" /><span class="fire" />
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty item.HASHTAGS }">
							<a href="#" class="btn-hashtag">#${item.HASHTAGS }</a>
						</c:when>
						<c:otherwise>
							<span>-</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
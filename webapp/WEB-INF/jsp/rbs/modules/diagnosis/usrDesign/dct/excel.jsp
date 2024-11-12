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
String titleName = "HRD기초진단_신청현황_" + formattedDate + "";
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
				<th scope="col"><itui:objectItemName itemId="insttName" itemInfo="${itemInfo}"/></th>
				<th scope="col">발급자 직위</th>
				<th scope="col">발급자 성명</th>
				<th scope="col"><itui:objectItemName itemId="issueNo" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="corpName" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
				<th scope="col">기업유형</th>
				<th scope="col">보고서 전달방법</th>
				<th scope="col">기업담당자 직위</th>
				<th scope="col">기업담당자 성명</th>
				<th scope="col">기업담당자 연락처</th>
				<th scope="col">기업담당자 메일주소</th>
				<th scope="col"><itui:objectItemName itemId="issueDate" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="bsisStatus" itemInfo="${itemInfo}"/></th>
			</tr>
		</thead>
		<tbody class="alignC">
			<c:if test="${empty list}">
				<tr>
					<td colspan="15" class="nolist"><spring:message code="message.no.list"/></td>
				</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
				<tr>
					<td class="num"><c:out value="${i.count}"/></td>
					<td><itui:objectView itemId="insttName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><c:out value="${doc.DOCTOR_DEPT_NAME }"/></td>
					<td><c:out value="${doc.MEMBER_NAME }"/></td>
					<td><itui:objectView itemId="issueNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="corpName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><c:out value="${listDt.PRI_SUP_CD eq '1'? '대규모기업' : '우선지원대상기업' }"/></td>
					<%-- 보고서 전달방법(추가예정) --%>
<%-- 			<td><c:out value="${listDt.SUBMIT_MTD eq 'Y'? '대면' : '비대면' }"/></td>  --%>
					<td>대면</td>
					<td><c:out value="${listDt.CORP_PIC_OFCPS }"/></td>
					<td><c:out value="${listDt.CORP_PIC_NAME }"/></td>
					<td><c:out value="${listDt.CORP_PIC_TELNO}"/></td>
					<td><c:out value="${empty listDt.CORP_PIC_EMAIL? '' : listDt.CORP_PIC_EMAIL}"/></td>
					<td class="date">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.ISSUE_DATE}" />
					</td>
					<c:choose>
						<c:when test="${listDt.BSIS_STATUS eq 1}">
							<td>
								<strong class="point-color01">
									<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSIS_ISSUE_DATE}"/>
								</strong>
							</td>
						</c:when>
						<c:when test="${listDt.BSIS_STATUS eq 0}">
							<td>
	                             <span>기업HRD이음컨설팅 진행중</span>
							</td>
						</c:when>
						<c:when test="${empty listDt.BSIS_STATUS && listDt.QUSTNR_STATUS eq 1}">
							<td>
	                             <span>설문조사 완료</span>
							</td>
						</c:when>
						<c:when test="${empty listDt.QUSTNR_STATUS}">
							<td>
	                             <span>설문조사 미진행</span>
							</td>
						</c:when>
						<c:when test="${listDt.QUSTNR_STATUS eq 0}">
							<td>
								<span>설문조사 수정중</span>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<span>설문조사 미진행</span>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
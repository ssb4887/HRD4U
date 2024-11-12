<%@page import="com.woowonsoft.egovframework.form.DataForm"%>
<%-- <%@ page language="java"
	contentType="application/vnd.ms-excel; charset=utf-8"
	pageEncoding="utf-8"%> --%>
<%@ include file="../../../../include/commonTop.jsp"%>
<spring:message var="msgItemCntName" code="item.contact.member" />
<spring:message var="msgItemLabelName"
	code="item.menu.point.person.name" />
<%@page import="com.woowonsoft.egovframework.util.StringUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.woowonsoft.egovframework.util.WebsiteDetailsHelper"%>
<%@page import="rbs.egovframework.WebsiteVO"%>
<%@page import="com.woowonsoft.egovframework.util.JSONObjectUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%
	String titleName = "BasketList";
	String fileName = titleName + ".xls";
	titleName = StringUtil.replaceHtmlN(titleName);

	response.setContentType("application/vnd.ms-xls;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<title><%=titleName%></title>
<meta http-equiv="Content-Type"
	content="application/vnd.ms-excel?charset=utf-8">
<style>
<!--
table {
	mso-displayed-decimal-separator: "\.";
	mso-displayed-thousand-separator: "\,";
}

@page {
	margin: .75in .71in .75in .71in;
	mso-header-margin: .31in;
	mso-footer-margin: .31in;
	mso-page-orientation: landscape;
}

body {
	font-family: "맑은 고딕";
	color: black;
}

caption {
	text-align: center;
	font-size: 12.0pt;
	font-weight: bold;
	padding: 10px 0;
	height: 50px;
}

table {
	border: .5pt solid #000;
	border-collapse: collapse;
	text-align: center;
	font-size: 9.0pt;
}

thead th {
	background: #DBE5F1;
	padding: 5px 10px;
	border-right: .5pt solid #A5A5A5;
	border-bottom: .5pt solid black;
	text-align: center;
	font-weight: normal;
}

td, tfoot th {
	padding: 5px 10px;
	border-right: .5pt solid #A5A5A5;
	border-bottom: .5pt solid #A5A5A5;
	text-align: center;
}

.rt {
	text-align: right;
}

.tlt {
	text-align: left;
}
//
-->
</style>
</head>
<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
	<table summary="<%=titleName%> 목록을 제공합니다." style="width: 100%;">
		<caption><%=titleName%></caption>
		<thead>
			<tr>
				<th scope="col" style="width: 200px">사업장관리번호</th>
				<th scope="col" style="width: 200px">기업명</th>
				<th scope="col" style="width: 200px">고용업종명</th>
				<th scope="col" style="width: 150px">총상시인원</th>
				<th scope="col" style="width: 150px">우선지원구분</th>
				<c:forEach var="showColumn" items="${showColumn}" varStatus="i">
					<th scope="col" style="width: 150px">${showColumn}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody class="alignC">
			<c:if test="${empty list}">
				<tr>
					<td colspan="36" class="nolist"><spring:message
							code="message.no.list" /></td>
				</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
								<tr id="column">
									<td><c:out value="${listDt.bplNo}" /></td>
									<td><c:out value="${listDt.bplNm}" /></td>
									<td><c:out value="${listDt.bplIndustNm}" /></td>
									<td><c:out value="${listDt.totWorkCnt}" /></td>
									<td><c:out value="${listDt.priSupCd eq '1' ? 'Y' : 'N'}" /></td>
									<c:forEach var="showColumn" items="${showColumn}" varStatus="i">
										<c:if test="${showColumn eq '자영업구분'}">
											<td><c:out
													value="${listDt.selfEmpCd eq '1' ? 'Y' : 'N'}" /></td>
										</c:if>
										<c:if test="${showColumn eq '법인등록번호'}">
											<td><c:out value="${listDt.jurirno}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업자등록번호'}">
											<td><c:out value="${listDt.bizrNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '본지사 구분'}">
											<td><c:out
													value="${listDt.headBplCd eq '1' ? 'Y' : 'N' eq '1' ? 'Y' : 'N'}" /></td>
										</c:if>
										<c:if test="${showColumn eq '본사 사업장 관리번호'}">
											<td><c:out value="${listDt.hedofcBplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장명'}">
											<td><c:out value="${listDt.bplNm}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업 우편번호'}">
											<td><c:out value="${listDt.bplZip}" /></td>
										</c:if>
										<c:if test="${showColumn eq '도로명주소'}">
											<td><c:out value="${listDt.bplAddr}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업 상세주소'}">
											<td><c:out value="${listDt.addrDtl}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용성립일자'}">
											<td><c:out value="${listDt.emplymFormatnDe}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용보험 소멸일자'}">
											<td><c:out value="${listDt.empinsExtshDe}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용사업장상태코드'}">
											<td><c:out value="${listDt.bplStatusCd}" /></td>
										</c:if>
										<c:if test="${showColumn eq '총사업장수'}">
											<td><c:out value="${listDt.totBplCnt}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용상시인원수'}">
											<td><c:out value="${listDt.totEmpCnt}" /></td>
										</c:if>
										<c:if test="${showColumn eq '직업훈련 여부'}">
											<td><c:out value="${listDt.occpTrYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '이메일'}">
											<td><c:out value="${listDt.email}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장이메일'}">
											<td><c:out value="${listDt.bplEmail}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장팩스번호'}">
											<td><c:out
													value="${listDt.bplAreaNo} - ${listDt.bplFaxNo1} - ${listDt.bplFaxNo2}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장전화'}">
											<td><c:out
													value="${listDt.bplTelno1} - ${listDt.bplTelno2}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장URL'}">
											<td><c:out value="${listDt.bplUrl}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용특별법적용구분'}">
											<td><c:out value="${listDt.spemplymApplcSe}" /></td>
										</c:if>
										<c:if test="${showColumn eq '우선지원시작일자'}">
											<td><c:out value="${listDt.priSupStartDate}" /></td>
										</c:if>
										<c:if test="${showColumn eq '예술인사업장 여부'}">
											<td><c:out value="${listDt.artbplYn}" /></td>
										</c:if>

										<c:if test="${showColumn eq '우량기업 유무'}">
											<td><c:out value="${listDt.excentYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq 'BEST HRD 선정 유무'}">
											<td><c:out value="${listDt.besthrdYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '산업단지기업 유무'}">
											<td><c:out value="${listDt.idscpxYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '산재다발'}">
											<td><c:out value="${listDt.indacmtYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '임금체불'}">
											<td><c:out value="${listDt.wgdlyYn}" /></td>
										</c:if>

										<c:if test="${showColumn eq '사업주 참여 여부(횟수)'}">
											<td><c:out value="${listDt.bprCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq 'S-OJT 참여 여부(횟수)'}">
											<td><c:out value="${listDt.sojtCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '컨소시엄 참여 여부(횟수)'}">
											<td><c:out value="${listDt.conCount}" /></td>
										</c:if>

										<c:if test="${showColumn eq '일학습 참여 여부(횟수)'}">
											<td><c:out value="${listDt.pdmsCount}" /></td>
										</c:if>

										<c:if test="${showColumn eq '지산맞 참여 여부(횟수)'}">
											<td><c:out value="${listDt.regCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '학습조직화 참여 여부'}">
											<td><c:out value="${listDt.egCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '우수기관 인증 여부'}">
											<td><c:out value="${listDt.certCount}" /></td>
										</c:if>

										<c:if test="${showColumn eq '채용제목'}">
											<td><c:out value="${listDt.title}" /></td>
										</c:if>
										<c:if test="${showColumn eq '임금형태'}">
											<td><c:out value="${listDt.salaryStle}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여'}">
											<td><c:out value="${listDt.salary}" /></td>
										</c:if>
										<c:if test="${showColumn eq '지역'}">
											<td><c:out value="${listDt.region}" /></td>
										</c:if>
										<c:if test="${showColumn eq '근무형태'}">
											<td><c:out value="${listDt.workStle}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업신용등급'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업채무불이행상태'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>


										<c:if test="${showColumn eq '강소기업 여부'}">
											<td><c:out value="${listDt.sgCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '청년친화 강소기업 여부'}">
											<td><c:out value="${listDt.yfCount}" /></td>
										</c:if>
									</c:forEach>
								</tr>
							</c:forEach>
		</tbody>
	</table>
</body>
</html>
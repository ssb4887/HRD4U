<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ page import="java.util.Date" %>
<c:set var="inputFormId" value="fn_supportInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/support/view.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<c:set var="year"><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/></c:set>
<div class="contents-wrapper">
	<div class="contents-area">
		<div class="title-wrapper clear mb0">
			<h3 class="title-type01 ml0 fl">맞춤훈련과정개발 맞춤개발 지원금 신청서</h3>
		</div>
		<div class="title-wrapper">
			<p class="state fl">신청상태<strong>${confirmProgress[dt.CONFM_STATUS]}</strong>
		</div>
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
			<input type="hidden" name="ctIdx" value="${dt.CT_IDX}">
			<input type="hidden" name="cnslIdx" value="${dt.CNSL_IDX}">
			
			<table class="width-type02">
				<caption>맞춤훈련과정개발 맞춤개발 지원금 신청서 작성 폼</caption>
				<colgroup>
					<col width="20%">
					<col width="auto">
					<col width="20%">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" colspan="4">신청기업 정보</th>
					</tr>
					<tr>
						<th scope="row">기업명</th>
						<td class="left"><c:out value="${corpInfo.BPL_NM}"/></td>
						<th scope="row">사업장등록번호</th>
						<td class="left">${corpInfo.BPL_NO}</td>
					</tr>
					<tr>
						<th scope="row">계좌번호</th>
						<td class="left">
							은행명 : <itui:objectView itemId="bankCd" itemInfo="${itemInfo}" objVal="${dt.BANK_CMMN_CD}"/><br>
							계좌번호 : <itui:objectView itemId="acnutNo" itemInfo="${itemInfo}" objVal="${dt.ACNUTNO}"/>
						</td>
						<th scope="row">예금주명</th>
						<td class="left"><c:out value="${dt.DPSTR_NM }"/></td>
					</tr>
					<tr>
						<th scope="row" colspan="4">훈련실시정보</th>
					</tr>
					<tr>
						<th scope="row">과정사업유형</th>
						<td class="left"><c:out value="${cnslReportInfo.CNSL_TYPE_NM}"/></td>
						<th scope="row">과정지원유형</th>
						<td class="left"><c:out value="${cnslReportInfo.CNSL_TP_REQ_TYPE}"/></td>
					</tr>
					<tr>
						<th scope="row">실시훈련과정</th>
						<td class="left">
							<c:out value="${dt.TP_NM }"/>
						</td>
						<th scope="row">지원유형</th>
						<td class="left"><c:out value="${cnslReportInfo.CNSL_REQ_TYPE}"/></td>
					</tr>
					<tr>
						<th scope="row" colspan="4">지원금 신청내용</th>
					</tr>
					<tr>
						<th scope="row">구분</th>
						<th scope="row">성명</th>
						<th scope="row">산출내역</th>
						<th scope="row">금액(원)</th>
					</tr>
					<c:forEach var="listDt" items="${cnslCostSubList}">
						<tr>
							<th scope="row">
								<c:choose>
									<c:when test="${listDt.TEAM_IDX eq '1'}">
										PM
									</c:when>
									<c:when test="${listDt.TEAM_IDX eq '2'}">
										외부전문가
									</c:when>
									<c:when test="${listDt.TEAM_IDX eq '3'}">
										내부전문가
									</c:when>
									<c:when test="${listDt.TEAM_IDX eq '4'}">
										외부훈련교사
									</c:when>
									<c:when test="${listDt.TEAM_IDX eq '5'}">
										내부훈련교사
									</c:when>
								</c:choose>
							</th>
							<td><c:out value="${listDt.TEAM_MBER_NAME}"/></td>
							<td><c:out value="${listDt.COMPUT_DTLS}"/></td>
							<td><fmt:formatNumber value="${listDt.SPT_PAY }" pattern="#,###,###,###,###"/></td>
						</tr>
					</c:forEach>
					<tr>
						<th scope="row" colspan="3">합계(원)</th>
						<td><fmt:formatNumber value="${dt.SPLPC_AMT }" pattern="#,###,###,###,###"/></td>
					</tr>
					<tr>
						<th scope="row">비고</th>
						<td colspan="3"><c:out value="${dt.REMARKS }"/></td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>맞춤훈련과정개발 비용청구 신청서 중 증빙 파일 업로드 폼</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="row">증빙 파일</th>
                    		<td class="left"><itui:objectViewCustom itemId="file" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>맞춤훈련과정개발 비용청구 신청서에 대한 주치의 의견</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="row">주치의 의견</th>
                    		<td class="left">${dt.DOCTOR_OPINION}</td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		<div class="btn-area">
			<div class="btns-right">
				<c:choose>
					<c:when test="${dt.CONFM_STATUS eq '5' || dt.CONFM_STATUS eq '20' || dt.CONFM_STATUS eq '40'}">
						<a href="<c:out value="${SUPPORT_MODIFY_FORM_URL}"/>&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '10'}">
						<a href="<c:out value="${SUPPORT_WITHDRAW_URL}"/>&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">회수</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '30'}">
						<a href="<c:out value="${SUPPORT_RETURNREQUEST_URL}"/>&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">반려요청</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '55'}">
						<a href="<c:out value="${SUPPORT_DROPREQUEST_URL}"/>&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">중도포기</a>
					</c:when>
				</c:choose>
				<a href="<c:out value="${SUPPORT_LIST_FORM_URL}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a>
			</div>
		</div>
		</form>
	</div>
</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ page import="java.util.Date" %>
<c:set var="inputFormId" value="fn_supportInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/support/view.jsp"/>
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
		<c:if test="${dt.CONFM_STATUS gt '10'}">
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
                    		<td class="left">
                    			<c:choose>
	                    			<c:when test="${loginVO.usertypeIdx ne '40' && (dt.CONFM_STATUS eq '30' || dt.CONFM_STATUS eq '35' || dt.CONFM_STATUS eq '50')}">
										<itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/>
									</c:when>        
									<c:otherwise>
										${dt.DOCTOR_OPINION}
									</c:otherwise>
                    			</c:choose>
                    		</td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		</c:if>
		<div class="btn-area">
			<div class="btns-right">
				<c:if test="${loginVO.usertypeIdx ne '40'}">
					<c:choose>
						<c:when test="${dt.CONFM_STATUS eq '10'}">
							<!-- 신청 상태가 신청(10)일 때만 접수 가능 -->
							<a href="${DEVELOP_SUPPORT_ACCEPT_URL}&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 신청서를 접수 처리하시겠습니까?')">${confirmProgress['30']}</a>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '30'}">
							<!-- 신청 상태가 접수(10)일 때 수정, 저장, 1차승인, 반려 -->
							<a href="${DEVELOP_SUPPORT_MODIFY_FORM_URL}&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
							<a href="${DEVELOP_SUPPORT_FIRSTAPPROVE_URL}&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 1차승인하시겠습니까?')">1차승인</a>
							<button type="submit" class="btn-m01 btn-color03 depth2 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '35'}">
							<!-- 신청 상태가 반려요청(35)일 때는 주치의 의견 입력 후 반려만 가능 -->
							<button type="submit" class="btn-m01 btn-color03 depth2 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '50'}">
							<c:choose>
								<c:when test="${loginVO.clsfCd eq 'Y'}">
									<!-- 1차승인 후 부장일 때 최종승인, 반려가능 -->
									<a href="${DEVELOP_SUPPORT_APPROVE_URL}&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 최종승인하시겠습니까?')">${confirmProgress['55']}</a>
									<button type="submit" class="btn-m01 btn-color03 depth2 btn-returnToAccept">반려</button>
								</c:when>
								<c:otherwise>
									<!-- 1차승인 후 전담주치의가 1차승인한 신청서를 회수가능(접수 상태로 되돌림) -->
									<a href="${DEVELOP_SUPPORT_ACCEPT_URL}&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 회수 처리하시겠습니까?')">회수</a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '55' || dt.CONFM_STATUS eq '75'}">
							<!-- 최종승인 후 중도포기 가능  -->
							<a href="${DEVELOP_SUPPORT_DROP_URL}&cnslIdx=${dt.CNSL_IDX}&ctIdx=${dt.CT_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 중도포기 처리하시겠습니까?')">${confirmProgress['80']}</a>
						</c:when>
					</c:choose>
				</c:if>
				<a href="<c:out value="${DEVELOP_SUPPORT_LIST_FORM_URL}"/>" title="목록" class="btn-m01 btn-color01 depth2">목록</a>
			</div>
		</div>
		</form>
	</div>
</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
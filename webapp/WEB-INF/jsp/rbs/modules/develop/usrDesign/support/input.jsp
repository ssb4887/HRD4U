<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ page import="java.util.Date" %>
<c:set var="inputFormId" value="fn_supportInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/support/input.jsp"/>
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
		<c:if test="${submitType eq 'modify'}">
		<div class="title-wrapper">
			<p class="state fl">신청상태<strong>${confirmProgress[dt.CONFM_STATUS]}</strong>
		</div>
		</c:if>
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
			<input type="hidden" name="ctIdx" value="${dt.CT_IDX}">
			<input type="hidden" name="cnslIdx" value="${cnslReportInfo.CNSL_IDX}">
			<input type="hidden" name="teamSubLength" id="teamSubLength" value="${fn:length(cnslTeamList)}">
			
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
						<th scope="row">계좌번호&nbsp;<strong class="point-important">*</strong></th>
						<td style="display:flex;">
							<itui:objectSelect itemId="bankCd" itemInfo="${itemInfo}" objStyle="width:48%; margin-right:5px;"/>
							<itui:objectText itemId="acnutNo" itemInfo="${itemInfo}" objClass="input-width48 onlyNum"/>
						</td>
						<th scope="row">예금주명&nbsp;<strong class="point-important">*</strong></th>
						<td><itui:objectText itemId="dpstrNm" itemInfo="${itemInfo}" objClass="w100"/></td>
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
							<span id="selectedTpNm">
								<c:if test="${submitType eq 'modify'}"><c:out value="${dt.TP_NM}"/></c:if>
							</span>
							<a href="#none" class="btn-m02 btn-color02 ml10" id="open-modal01">선택</a>
							<input type="hidden" name="tpNm" id="tpNm" <c:if test="${submitType eq 'modify'}">value="${dt.TP_NM}"</c:if>/>
							<input type="hidden" name="tpCd" id="tpCd" <c:if test="${submitType eq 'modify'}">value="${dt.TP_CD}"</c:if>/>
							<input type="hidden" name="trOprtnDe" id="trOprtnDe" <c:if test="${submitType eq 'modify'}">value="${dt.TR_OPRTN_DE}"</c:if>/>
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
					<c:choose>
						<c:when test="${cnslReportInfo.CNSL_TYPE eq '3'}">
							<tr>
								<th scope="row">PM<input type="hidden" name="teamIdx1" value="${cnslTeamList[0].TEAM_IDX}"/></th>
								<td><c:out value="${cnslTeamList[0].TEAM_MBER_NAME}"/><input type="hidden" name="teamNm1" value="${cnslTeamList[0].TEAM_MBER_NAME}"></td>
								<td><c:out value="${cnslTeamList[0].COMPUT_DTLS}"/><input type="hidden" name="trOprtnDe1" value="${cnslTeamList[0].COMPUT_DTLS}"></td>
								<td><c:out value="${cnslTeamList[0].SPT_PAY}"/><input type="hidden" name="teamCost1" id="teamCost1" value="${cnslTeamList[0].SPT_PAY}"></td>
							</tr>
							<tr>
								<th scope="row">외부전문가<input type="hidden" name="teamIdx2" value="${cnslTeamList[1].TEAM_IDX}"/></th>
								<td><c:out value="${cnslTeamList[1].TEAM_MBER_NAME}"/><input type="hidden" name="teamNm2" value="${cnslTeamList[1].TEAM_MBER_NAME}"></td>
								<td><c:out value="${cnslTeamList[1].COMPUT_DTLS}"/><input type="hidden" name="trOprtnDe2" value="${cnslTeamList[1].COMPUT_DTLS}"></td>
								<td><c:out value="${cnslTeamList[1].SPT_PAY}"/><input type="hidden" name="teamCost2" id="teamCost2" value="${cnslTeamList[1].SPT_PAY}"></td>
							</tr>
							<tr data-type="sp">
								<th scope="row">내부전문가<input type="hidden" name="teamIdx3" value="${cnslTeamList[2].TEAM_IDX}"/></th>
								<td><c:out value="${cnslTeamList[2].TEAM_MBER_NAME}"/><input type="hidden" name="teamNm3" value="${cnslTeamList[2].TEAM_MBER_NAME}"></td>
								<td><c:out value="${cnslTeamList[2].COMPUT_DTLS}"/><input type="hidden" name="trOprtnDe3" value="${cnslTeamList[2].COMPUT_DTLS}"></td>
								<td><c:out value="${cnslTeamList[2].SPT_PAY}"/><input type="hidden" name="teamCost3" id="teamCost3" value="${cnslTeamList[3].SPT_PAY}"></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr data-type="sp">
								<th scope="row">외부전문가<input type="hidden" name="teamIdx1" value="${cnslTeamList[0].TEAM_IDX}"/></th>
								<td><c:out value="${cnslTeamList[0].TEAM_MBER_NAME}"/><input type="hidden" name="teamNm1" value="${cnslTeamList[0].TEAM_MBER_NAME}"></td>
								<td><c:out value="${cnslTeamList[0].COMPUT_DTLS}"/><input type="hidden" name="trOprtnDe1" value="${cnslTeamList[0].COMPUT_DTLS}"></td>
								<td><c:out value="${cnslTeamList[0].SPT_PAY}"/><input type="hidden" name="teamCost1" id="teamCost1" value="${cnslTeamList[0].SPT_PAY}"></td>
							</tr>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${submitType eq 'write'}">
							<tr id="exButton">
								<td colspan="4"><button type="button" id="addEx" class="btn-m02 btn-color03 mr10">외부훈련교사 추가</button><button type="button" id="delEx" class="btn-m02 btn-color02">외부훈련교사 삭제</button></td>
							</tr>
							<tr id="inButton">
								<td colspan="4"><button type="button" id="addIn" class="btn-m02 btn-color03 mr10">내부훈련교사 추가</button><button type="button" id="delIn" class="btn-m02 btn-color02">내부훈련교사 삭제</button></td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="cnslTeamDt" items="${cnslTeamList}" varStatus="i">
								<c:if test="${cnslTeamDt.TEAM_IDX eq '4'}">
									<tr id="${i.count}" data-type="ex">
										<th scope="row">외부훈련교사<input type="hidden" name="teamIdx${i.count}" value="${cnslTeamDt.TEAM_IDX}"/></th>
										<td><input type="text" name="teamNm${i.count}" id="teamNm${i.count}" class="w100" value="${cnslTeamDt.TEAM_MBER_NAME}"></td>
										<td><input type="text" name="trOprtnDe${i.count}" id="trOprtnDe${i.count}" class="w100" value="${cnslTeamDt.COMPUT_DTLS}"></td>
										<td><input type="text" name="teamCost${i.count}" id="teamCost${i.count}" class="w100 onlyNum cash" value="${cnslTeamDt.SPT_PAY}"></td>
									</tr>
								</c:if>
							</c:forEach>
							<tr id="exButton">
								<td colspan="4"><button type="button" id="addEx" class="btn-m02 btn-color03 mr10">외부훈련교사 추가</button><button type="button" id="delEx" class="btn-m02 btn-color02">외부훈련교사 삭제</button></td>
							</tr>
							<c:forEach var="cnslTeamDt" items="${cnslTeamList}" varStatus="i">
								<c:if test="${cnslTeamDt.TEAM_IDX eq '5'}">
									<tr id="${i.count}" data-type="in">
										<th scope="row">내부훈련교사<input type="hidden" name="teamIdx${i.count}" value="${cnslTeamDt.TEAM_IDX}"/></th>
										<td><input type="text" name="teamNm${i.count}" id="teamNm${i.count}" class="w100" value="${cnslTeamDt.TEAM_MBER_NAME}"></td>
										<td><input type="text" name="trOprtnDe${i.count}" id="trOprtnDe${i.count}" class="w100" value="${cnslTeamDt.COMPUT_DTLS}"></td>
										<td><input type="text" name="teamCost${i.count}" id="teamCost${i.count}" class="w100 onlyNum cash" value="${cnslTeamDt.SPT_PAY}"></td>
									</tr>
								</c:if>
							</c:forEach>
							<tr id="inButton">
								<td colspan="4"><button type="button" id="addIn" class="btn-m02 btn-color03 mr10">내부훈련교사 추가</button><button type="button" id="delIn" class="btn-m02 btn-color02">내부훈련교사 삭제</button></td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th scope="row" colspan="3">합계(원)</th>
						<td><input type="text" name="totPay" class="w100" id="totPay" readonly></td>
					</tr>
					<tr>
						<th scope="row">비고</th>
						<td colspan="3"><itui:objectText itemId="remarks" itemInfo="${itemInfo}" objClass="w100"/></td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="board-write">
				<div class="one-box">
					<dl class="board-write-contents">
						<dt><label for="file">파일</label></dt>
						<dd><itui:objectMultiFileCustom itemId="file" itemInfo="${itemInfo}"/></dd>
					</dl>
				</div>
			</div>
		</div>
		<c:if test="${submitType eq 'modify'}">
		<div class="contents-box pl0">
			<div class="board-write">
				<div class="one-box">
					<dl class="board-write-contents">
						<dt>주치의 의견</dt>
						<dd><c:out value="${dt.DOCTOR_OPINION}"/></dd>
					</dl>
				</div>
			</div>
		</div>
		</c:if>
		<div class="btn-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color02 depth3 fn_btn_submit" data-status="<%= ConfirmProgress.TEMPSAVE.getCode()%>">임시저장</button>
				<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_submit" data-status="<%= ConfirmProgress.APPLY.getCode()%>">신청</button>
				<button type="button" class="btn-m01 btn-color01 depth3 fn_btn_cancel">목록</button>
			</div>
		</div>
		</form>
	</div>
</div>
	<!-- 훈련실시 내역(HRD-NET)선택 모달창 -->
	<div class="mask"></div>
	<div class="modal-wrapper type02" id="modal-action01">
		<h2>훈련실시 내역조회(HRD-NET)</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="contents-box pl0">
					<div class="table-type01 horizontal-scroll">
						<table class="width-type02">
						<caption>훈련실시 내역조회(HRD-NET) 정보표 : 번호, 훈련과정명, 회차, 신청일자, 유형에 관한 정보제공표</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:10%">
							<col style="width:auto">
							<col style="width:10%">
							<col style="width:20%">
						</colgroup>
						<thead>
						<tr>										
							<th scope="col"></th>
							<th scope="col">No</th>
							<th scope="col">훈련과정명</th>
							<th scope="col">회차</th>
							<th scope="col">신청일자</th>
						</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty corpTpList}">
							<tr>
								<td colspan="5" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
							</c:if>
							<c:forEach var="listDt" items="${corpTpList}" varStatus="i">
							<tr>
								<td><button type="button" class="btn-m02 btn-color02 fn-select" id="selectTp${listDt.ROWNUM}" data-cd="${listDt.TP_CD}" data-nm="${listDt.TP_NM}" data-start="${listDt.TR_START_DATE}">선택</button></td>
								<td><c:out value="${listDt.ROWNUM}"/></td>
								<td><c:out value="${listDt.TP_NM}"/></td>
								<td><c:out value="${listDt.TP_TME}"/></td>
								<td>
									<c:choose>
										<c:when test="${!empty listDt.TR_START_DATE}">${listDt.TR_START_DATE}</c:when>
<%-- 										<c:when test="${!empty listDt.TR_START_DATE}"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.TR_START_DATE}"/></c:when> --%>
										<c:otherwise>미정</c:otherwise>
									</c:choose>
								</td>
							</tr>	
							</c:forEach>							
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
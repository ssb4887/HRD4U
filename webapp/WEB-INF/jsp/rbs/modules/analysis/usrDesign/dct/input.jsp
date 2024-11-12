<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<style>
	.modal-wrapper {
		width: 1000px;
		top: 40%;
		left: 40%;
	}
	
	.btns-area {
		text-align: right;
		margin-top: 20px;
	}
	
	#id_tpCd {
		width: 65%;
		margin-right: 10px;
	}
</style>
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/analysis/apply.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">훈련성과분석 작성 <span style="color: red;">※훈련실시 종료 후 작성 부탁드립니다.</span></h3>
		<form id="frm" method="post">
			<c:if test="${devDt.DEVLOP_IDX eq null}">
				<input type="hidden" id="id_cnslIdx" name="cnslIdx" value="<c:out value="${devDt.CNSL_IDX}"/>"/>
			</c:if>
			<c:if test="${devDt.CNSL_IDX eq null}">
				<input type="hidden" id="id_devlopIdx" name="devlopIdx" value="<c:out value="${devDt.DEVLOP_IDX}"/>"/>
			</c:if>
			<input type="hidden" id="id_tpDevlopSe" name="tpDevlopSe" value="<c:out value="${trResultReprtSe}"/>"/>
			<input type="hidden" id="id_bplNo" name="bplNo" value="<c:out value="${param.bplNo}"/>"/>
			<input type="hidden" id="id_status" name="status" value="" />
			<c:if test="${not empty chkType}">
				<input type="hidden" id="id_chkType" name="chkType" value="<c:out value="${chkType}"/>" />
			</c:if>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="15%">
	                        <col width="16%">
	                        <col width="15%">
	                        <col width="16%">
	                        <col width="auto">
	                        <col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									기업명
								</th>
								<td colspan="3">
									<input type="hidden" id="id_trCorpNm" name="trCorpNm" class="w100" value="<c:out value="${not empty devDt.BPL_NM ? devDt.BPL_NM : not empty dt.TR_CORP_NM ? dt.TR_CORP_NM : cnsl.corpNm}"/>" />
									<c:out value="${not empty devDt.BPL_NM ? devDt.BPL_NM : not empty dt.TR_CORP_NM ? dt.TR_CORP_NM : cnsl.corpNm}"/>
								</td>
								<th scope="row">
									훈련기관(기업)
								</th>
								<td>
									<c:choose>
										<c:when test="${empty devDt.TRINSTT_NM}">
											<input type="text" id="id_bizSe" name="bizSe" class="w100" maxlength="50" value="<c:out value="${not empty dt.BIZ_SE ? dt.BIZ_SE : devDt.TRINSTT_NM}"/>" title="훈련기관(기업)"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id_bizSe" name="bizSe" class="w100" value="<c:out value="${devDt.BPL_NM}"/>" />
											<c:out value="${devDt.BPL_NM}"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row">
									훈련과정명
								</th>
								<td colspan="3">
									<c:choose>
										<c:when test="${empty devDt.TP_NM}">
											<input type="text" id="id_tpNm" name="tpNm" class="w100" maxlength="100" value="<c:out value="${empty dt.TP_NM ? data.subject_name[0] : dt.TP_NM}"/>" title="훈련과정명"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id_tpNm" name="tpNm" class="w100" value="<c:out value="${devDt.TP_NM}"/>" />
											<c:out value="${devDt.TP_NM}"/>
										</c:otherwise>
									</c:choose>
								</td>
								
								<th scope="row">
									NCS 소분류
								</th>
								<td>
									<c:choose>
										<c:when test="${empty devDt.NCS_SCLAS_CD}">
											<input type="text" id="id_ncsSclasCd" name="ncsSclasCd" maxlength="10" class="w100 onlyNum" value="<c:out value="${dt.NCS_SCLAS_CD}"/>" title="NCS 소분류"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id_ncsSclasCd" name="ncsSclasCd" class="w100" value="<c:out value="${devDt.NCS_SCLAS_CD}"/>" />
											<c:out value="${devDt.NCS_SCLAS_CD}"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row">
									훈련기간
								</th>
								<td>
									<c:choose>
										<c:when test="${empty devDt.TR_DAYCNT}">
											<input type="text" id="id_Period" name="trPeriod" maxlength="10" class="w100 onlyNum" value="<c:out value="${dt.TR_PERIOD}"/>" title="훈련기간"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id_Period" name="trPeriod" class="w100" value="<c:out value="${devDt.TR_DAYCNT}"/>" />
											<c:out value="${devDt.TR_DAYCNT}"/>일
										</c:otherwise>
									</c:choose>
								</td>
								<th scope="row">
									훈련시간
								</th>
								<td>
									<c:choose>
										<c:when test="${empty devDt.TRTM}">
											<input type="text" id="id_trtm" name="trtm" maxlength="10" class="w100 onlyNum" value="<c:out value="${dt.TRTM}"/>" title="훈련시간"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id_trtm" name="trtm" class="w100" value="<c:out value="${devDt.TRTM}"/>" />
											<c:out value="${devDt.TRTM}"/>시간
										</c:otherwise>
									</c:choose>
								</td>
								<th scope="row">
									훈련방법
								</th>
								<td>
									<c:choose>
										<c:when test="${empty devDt.TR_MTH}">
											<input type="text" id="id_trMth" name="trMth" maxlength="30" class="w100" value="<c:out value="${dt.TR_MTH}"/>" title="훈련방법"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id_trMth" name="trMth" class="w100" value="<c:out value="${devDt.TR_MTH}"/>" />
											<c:out value="${devDt.TR_MTH}"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row">
									평가목적
								</th>
								<td class="left" colspan="3">
									<input type="text" id="id_trPurps" name="trPurps" class="req w100" maxlength="80" value="<c:out value="${dt.TR_PURPS}" />" title="평가목적"/>
								</td>
								<th scope="row">
									훈련생명
								</th>
								<td class="left">
									<input type="text" id="id_trneeNm" name="trneeNm" class="req w100" maxlength="20" value="<c:out value="${dt.TRNEE_NM}" />" title="훈련생명"/>
								</td>
							</tr>
							<tr>
								<th scope="row">
									실시훈련과정코드
								</th>
								<td class="left" colspan="3">
									<input type="text" id="id_tpCd" name="tpCd" class="req" maxlength="30" value="<c:out value="${dt.TP_CD}" />" title="실시훈련과정코드"/>
									<button type="button" class="btn-m01 btn-color01 open-modal01" title="훈련 과정 검색 - 새창열림" onclick="openModal('${param.bplNo}'); return false;">
										훈련 과정 검색
									</button>
								</td>
								<th scope="row">
									작성항목
								</th>
								<td>
									<label for="trResultReprtSe1">만족도 및 성취도 조사</label>
									<input type="radio" id="trResultReprtSe1" name="trResultReprtSe" class="radio-type01 trResultReprtSe" value="1" onclick="applyChk(1);" title="만족도 및 성취도 조사"/><br/>
									<label for="trResultReprtSe2">현업적용</label>
									<input type="radio" id="trResultReprtSe2" name="trResultReprtSe" class="radio-type01 trResultReprtSe" value="2" onclick="applyChk(2);" title="현업적용"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
	
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="auto">
							<col width="auto">
							<col width="auto">
							<col width="auto">
							<col width="auto">
						</colgroup>
						<tbody id="survey">
						
						</tbody>
					</table>
				</div>
			</div>
		</form>
		<div class="btns-area" id="btns-area-0" style="margin-top: 30px;" style="display: none">
			<div class="btns-right">
	            <button type="button" class="btn-m01 btn-color01 depth2 btn-back">
	            	목록
	            </button>
	            <button type="button" class="btn-m01 btn-color02 depth2" id="btn-save">
	            	저장
	            </button>
	            <button type="button" class="btn-m01 btn-color03 depth2" id="btn-apply">
	            	제출
	            </button>
			</div>
        </div>
        
 		<div class="btns-area" id="btns-area-1" style="display: none">
 			<div class="btns-right">
	            <button type="button" class="btn-m01 btn-color02 depth2 btn-back">
	            	목록
	            </button>
 			</div>
        </div>
	</div>
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<!-- 분류 예외 지정 모달 -->
<div class="modal-wrapper" id="modal-action">
	<h2>
            훈련과정 검색
    </h2>
	<div class="modal-area">
		<div class="agreement-area" style="max-height: none">
			<div class="table-type01">
				<table>
					<caption>훈련과정 검색</caption>
					<colgroup>
						<col style="width: 5%" />
						<col style="width: 20%" />
						<col style="width: 10%" />
						<col style="width: 35%" />
						<col style="width: 15%" />
						<col style="width: 15%" />
					</colgroup>
					<tbody id="srchBody">

					</tbody>
				</table>
			</div>
		</div>
		<div class="btns-area">
			<button type="button" onclick="closeModal(this.id)" class="btn-m02 round01 btn-color02">
				<span>
					닫기
				</span>
			</button>
		</div>
	</div>
</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
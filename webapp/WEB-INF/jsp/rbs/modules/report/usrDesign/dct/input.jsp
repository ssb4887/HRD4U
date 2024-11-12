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
		<h3 class="title-type01 ml0">훈련성과분석 작성</h3>
		<form id="frm" method="post">
		<input type="hidden" id="id_devlopIdx" name="devlopIdx" value="<c:out value="${dt.DEVLOP_IDX}" />" />
		<input type="hidden" id="id_status" name="status" value="" />
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
								<th scope="row" colspan="2">
									기업명
								</th>
								<td colspan="2">
									<input type="hidden" id="id_trCorpNm" name="trCorpNm" class="w100" value="<c:out value="${dt.TR_CORP_NM}"/>" />
									<c:out value="${dt.TR_CORP_NM}"/>
								</td>
								<th scope="row">
									훈련기관(기업)
								</th>
								<td>
									<input type="hidden" id="id_bizSe" name="bizSe" class="w100" value="<c:out value="${dt.BIZ_SE}"/>"/>
									<c:out value="${dt.BIZ_SE}"/>
								</td>
							</tr>
							<tr>
								<th scope="row" colspan="2">
									훈련과정명
								</th>
								<td colspan="2">
									<input type="hidden" id="id_tpNm" name="tpNm" class="w100" value="<c:out value="${dt.TP_NM}"/>" />
									<c:out value="${dt.TP_NM}"/>
								</td>
								
								<th scope="row">
									NCS 소분류
								</th>
								<td>
									<input type="hidden" id="id_ncsSclasCd" name="ncsSclasCd" class="w100" value="<c:out value="${dt.NCS_SCLAS_CD}"/>" />
									<c:out value="${dt.NCS_SCLAS_CD}"/>
								</td>
							</tr>
							<tr>
								<th scope="row">
									훈련기간
								</th>
								<td>
									<input type="hidden" id="id_Period" name="trPeriod" class="w100" value="<c:out value="${dt.TR_PERIOD}"/>" />
									<c:out value="${dt.TR_PERIOD}"/>일
								</td>
								<th scope="row">
									훈련시간
								</th>
								<td>
									<input type="hidden" id="id_trtm" name="trtm" class="w100" value="<c:out value="${dt.TRTM}"/>" />
									<c:out value="${dt.TRTM}"/>시간
								</td>
								<th scope="row">
									훈련방법
								</th>
								<td>
									<input type="hidden" id="id_trMth" name="trMth" class="w100" value="<c:out value="${dt.TR_MTH}"/>" />
									<c:out value="${dt.TR_MTH}"/>
								</td>
							</tr>
							<tr>
								<th scope="row">
									평가목적
								</th>
								<td class="left" colspan="5">
									<input type="text" id="id_trPurps" name="trPurps" class="req w100" maxlength="100" value="<c:out value="${dt.TR_PURPS}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									실시훈련과정코드
								</th>
								<td class="left" colspan="3">
									<input type="text" id="id_tpCd" name="tpCd" class="req" maxlength="30" value="<c:out value="${dt.TP_CD}" />" />
									<button type="button" class="btn-m01 btn-color01 open-modal01"  onclick="openModal('${dt.BPL_NO}'); return false;">
										훈련 과정 검색
									</button>
								</td>
								<th scope="row">
									작성항목
								</th>
								<td>
										만족도 및 성취도 조사
									<input type="radio" id="trResultReprtSe1" name="trResultReprtSe" class="radio-type01 trResultReprtSe" value="1" onclick="return false;" /><br/>
										현업적용
									<input type="radio" id="trResultReprtSe2" name="trResultReprtSe" class="radio-type01 trResultReprtSe" value="2" onclick="return false;" />
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
	            <button type="button" class="btn-m01 btn-color02 depth2" id="btn-save" style="display: none">
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
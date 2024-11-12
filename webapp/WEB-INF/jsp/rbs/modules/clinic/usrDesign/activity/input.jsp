<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/activity/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="contents-area">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="#" target="submit_target" enctype="multipart/form-data">
			<div class="title-wrapper clear">
				<h3 class="title-type01 ml0 fl">능력개발클리닉 활동일지</h3>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 활동일지 입력 표 : 기업명에 대한 정보와 구분, 일시, 장소, 능력개발활동 내역, 증빙 사진, 증빙 내역, 활동지원금에 대한 입력 표</caption>
						<colgroup>
							<col width="14%">
							<col width="auto">
							<col width="14%">
							<col width="auto">
							<col width="10%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기업명</th>
								<td colspan="4" class="left">${corpInfo.BPL_NM}</td>
							</tr>
							<tr>
								<th scope="row">구분&nbsp;<strong class="point-important">*</strong><input type="hidden" name="actCd" id="actCd" <c:if test="${submitType eq 'modify'}">value="${dt.ACMSLT_ACT_CD}"</c:if>></th>
								<td colspan="3" class="left">
									<div class="input-checkbox-wrapper ratio type03">
										<div class="input-checkbox-area">
											<input type="radio" id="checkbox0102" name="actType" value="02" class="checkbox-type01" data-sport="N" data-req="${planSubList[0].REQST_YN}" <c:if test="${submitType eq 'modify' && dt.ACMSLT_TYPE eq '02'}">checked</c:if>> 
											<label for="checkbox0102" class="point-color06"> &nbsp;HRD담당자 역량개발 </label>
										</div>
									</div>
									<div class="input-checkbox-wrapper ratio type03">
										<div class="input-checkbox-area">
											<input type="radio" id="checkbox0103" name="actType" value="03" class="checkbox-type01" data-sport="Y" data-req="${planSubList[3].REQST_YN}" <c:if test="${submitType eq 'modify' && dt.ACMSLT_TYPE eq '03'}">checked</c:if>> 
											<label for="checkbox0103" class="point-color04"> &nbsp;판로개척 </label>
										</div>
										<div class="input-checkbox-area">
											<input type="radio" id="checkbox0104" name="actType" value="04" class="checkbox-type01" data-sport="Y" data-req="${planSubList[3].REQST_YN}" <c:if test="${submitType eq 'modify' && dt.ACMSLT_TYPE eq '04'}">checked</c:if>> 
											<label for="checkbox0104" class="point-color04"> &nbsp;인력채용 </label>
										</div>
									</div>
									<div class="input-checkbox-wrapper ratio type03">
										<div class="input-checkbox-area">
											<input type="radio" id="checkbox0105" name="actType" value="05" class="checkbox-type01" data-sport="N" data-req="${planSubList[4].REQST_YN}" <c:if test="${submitType eq 'modify' && dt.ACMSLT_TYPE eq '05'}">checked</c:if>> 
											<label for="checkbox0105"> &nbsp;HRD 성과 교류(대내) </label>
										</div>
										<div class="input-checkbox-area">
											<input type="radio" id="checkbox0106" name="actType" value="06" class="checkbox-type01" data-sport="N" data-req="${planSubList[4].REQST_YN}" <c:if test="${submitType eq 'modify' && dt.ACMSLT_TYPE eq '06'}">checked</c:if>>
											 <label for="checkbox0106"> &nbsp;HRD 성과 교류(대외) </label>
										</div>
									</div>
								</td>
								<td class="left">
									<p class="point-color06">정액/실비</p>
									<p class="point-color04">실비</p>
									<p>정액</p>
								</td>
							</tr>
							<tr>
								<th scope="row">일시&nbsp;<strong class="point-important">*</strong></th>
								<td class="left">
									<div class="input-calendar-wrapper">
										<itui:objectYMDCustom itemId="startDate" itemInfo="${itemInfo}" inputTypeName="start" objStyle="height:50px;"/>
										<div class="word-unit">~</div>
										<itui:objectYMDCustom itemId="endDate" itemInfo="${itemInfo}" inputTypeName="end" objStyle="height:50px;"/>
									</div>
								</td>
								<th scope="row">장소&nbsp;<strong class="point-important">*</strong></th>
								<td class="left" colspan="2"><itui:objectText itemId="place" itemInfo="${itemInfo}" objClass="w100"/></td>
							</tr>
							<tr>
								<th scope="row" colspan="5">능력개발활동 내역&nbsp;<strong class="point-important">*</strong></th>
							</tr>
							<tr>
								<td colspan="5" class="left">
									<div class="fr"><span class="cnCount">0</span>/1000자</div>
									<itui:objectTextarea itemId="cn" itemInfo="${itemInfo}"/>
								</td>
							</tr>
							<tr>
								<th scope="row">증빙 사진</th>
								<td colspan="4"><itui:objectMultiFileCustom itemId="photo" itemInfo="${itemInfo}" objClass="${objClass}"/></td>
							</tr>
							<tr>
								<th scope="row">증빙 내역</th>
								<td colspan="4"><itui:objectMultiFileCustom itemId="bill" itemInfo="${itemInfo}" objClass="${objClass}"/></td>
							</tr>
							<tr>
								<th scope="row" rowspan="2">
									활동지원금&nbsp;<strong class="point-important">*</strong>
									<input type="hidden" name="sportYn" id="sportYn" value="<c:if test="${submitType eq 'modify'}">${dt.ACEXP_SPORT_YN}</c:if>">
								</th>
								<th scope="row" class="left">
									<c:if test="${submitType eq 'modify' && dt.ACEXP_SPORT_YN eq 'N'}"><c:set var="check" value="checked"/></c:if>
									<input type="radio" name="sportType" value="N" class="checkbox-type01 ml10 mr10" id="sportCheck1" ${check} disabled>
									<span id="sport">정액</span>
								</th>
								<td colspan="3" class="left"><input type="text" name="sportAmt" id="sportType1" class="w100"></td>
							</tr>
							<tr>
								<th scope="row" class="left">
									<c:if test="${submitType eq 'modify' && dt.ACEXP_SPORT_YN eq 'N'}"><c:set var="check2" value="checked"/></c:if>
									<input type="radio" name="sportType" value="Y" class="checkbox-type01 ml10 mr10" id="sportCheck2" ${check2} disabled>
									<span id="sport">실비</span>
									<span><a href="#" class="word-linked-notice02 mr10" id="open-modal01"></a></span>
									<span class="point-color04">※ 증빙 내역에 증빙서류 첨부 필수</span>
								</th>
								<td colspan="3" class="left"><input type="text" name="sportAmt" id="sportType2" class="w100"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="btns-area">
				<div class="btns-right">
					<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_apply">저장</button>
					<a href="${ACTIVITY_LIST_FORM_URL}" class="btn-m01 btn-color01 fn_btn_cancel">목록</a>
				</div>
			</div>
		</form>
	</div>
	<!-- 모달 창 -->
    <div class="mask"></div>
    <div class="modal-wrapper" id="modal-action01">
        <h2>알림</h2>
        <div class="modal-area">
            <div class="modal-alert type02">
                <p>HRD담당자 역량개발에 10만원 초과 청구 시<br>1) 실비 체크 후 기입<br>2) 증빙서류 첨부</p>
            </div>
        </div>
        <button type="button" class="btn-modal-close">모달 창 닫기</button>
    </div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/activity/view.jsp"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="contents-area">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
			<div class="title-wrapper clear mb0">
				<h3 class="title-type01 ml0 fl">능력개발클리닉 활동일지</h3>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 활동일지 정보표 : 기업명, 구분, 일시, 장소, 능력개발활동 내역, 증빙사진, 증빙내역, 활동지원금에 대한 정보 제공표</caption>
						<colgroup>
							<col width="14%">
							<col width="auto">
							<col width="auto">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기업명</th>
								<td colspan="3" class="left">${corpInfo.BPL_NM}</td>
							</tr>
							<tr>
								<th scope="row">구분</th>
								<td colspan="3" class="left"><itui:objectViewCustom itemId="actType" itemInfo="${itemInfo}"/></td>
							</tr>
							<tr>
								<th scope="row">일시</th>
								<td class="left"><fmt:formatDate pattern="yyyy-MM-dd" value="${dt.ACMSLT_START_DT}"/> ~ <fmt:formatDate pattern="yyyy-MM-dd" value="${dt.ACMSLT_END_DT}"/></td>
								<th scope="row">장소</th>
								<td class="left">${dt.ACMSLT_PLACE}</td>
							</tr>
							<tr>
								<th scope="row" colspan="4">능력개발활동 내역</th>
							</tr>
							<tr>
								<td colspan="4" class="left">${dt.ACMSLT_CN}</td>
							</tr>
							<tr>
								<th scope="row">증빙 사진</th>
								<td colspan="3" class="left">
									<itui:objectViewCustom itemId="photo" itemInfo="${itemInfo}"/>
								</td>
							</tr>
							<tr>
								<th scope="row">증빙 내역</th>
								<td colspan="3" class="left"><itui:objectViewCustom itemId="bill" itemInfo="${itemInfo}"/></td>
							</tr>
							<tr>
								<th scope="row">활동지원금</th>
								<th scope="row">
									<c:choose>
										<c:when test="${dt.ACEXP_SPORT_YN eq 'Y'}">실비</c:when>
										<c:otherwise>정액</c:otherwise>
									</c:choose>
								</th>
								<td colspan="2" class="left"><fmt:formatNumber value="${dt.ACMSLT_SPORT_AMT}"/>원</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="btns-area">
				<div class="btns-right">
					<a href="${ACTIVITY_MODIFY_FORM_URL}&acmsltIdx=${dt.ACMSLT_IDX}" class="btn-m01 btn-color03">수정</a>
					<a href="${ACTIVITY_LIST_FORM_URL}" class="btn-m01 btn-color01">목록</a>
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
                <p>HRD담당자 역량개발에 10만원 이상 청구 시<br>1) 실비 체크 후 기입<br>2) 증빙서류 첨부</p>
            </div>
        </div>
        <button type="button" class="btn-modal-close">모달 창 닫기</button>
    </div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/total/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<!-- search -->
	<itui:searchFormItemForDct divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${TOTAL_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" contextPath="${contextPath}" imgPath="${imgPath}"/>
	<!-- //search -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">맞춤훈련 과정개발 종합이력</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
		</div>
		<div class="table-type01 horizontal-scroll">
		<table summary="능력개발클리닉 신청 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption>능력개발클리닉 신청 목록표 : 체크, 번호, 기업명, 담당자, 소속기관, 신청일자, 신청 상태, 주치의 지정, 접수에 관한 정보 제공표</caption>
			<colgroup>
				<col style="width:6%">
				<col style="width:12%">
				<col style="width:10%">
				<col style="width:10%">
				<col style="width:auto">
				<col style="width:8%">
				<col style="width:8%">
				<col style="width:10%">
				<col style="width:10%">
			</colgroup>
			<thead>
			<tr>		
				<th scope="col">No</th>
				<th scope="col">사업유형</th>
				<th scope="col">훈련유형</th>
				<th scope="col">NCS대분류</th>
				<th scope="col">훈련과정명</th>
				<th scope="col">유형</th>
				<th scope="col">신청자</th>
				<th scope="col">신청일자</th>
				<th scope="col">상태</th>
			</tr>
			</thead>
			<tbody>
				<c:if test="${empty totalList}">
				<tr>
					<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${totalList }" varStatus="i">
				<tr>
					<td class="num">${listNo}</td>
					<td><c:out value="${listDt.BIZ_TYPE}"></c:out></td>
					<td><c:out value="${listDt.TR_MTH}"></c:out></td>
					<td><c:out value="${listDt.NCS_LCLAS_NM}"></c:out></td>
					<td>
					<strong class="point-color01">
						<c:if test="${listDt.TYPE eq '표준' }">
							<a href="<c:out value="${contextPath}/${crtSiteId}/develop/develop_view_form.do?mId=59&devlopIdx=${listDt.IDX}"/>" target="_blank">
								<c:out value="${listDt.TP_NM}"></c:out>
							</a>
						</c:if>
						<c:if test="${listDt.TYPE eq '맞춤' }">
							<c:choose>
								<c:when test="${listDt.CONFM_STATUS eq '55'}">
									<a href="<c:out value="${contextPath}/${crtSiteId}/consulting/cnslViewForm.do?mId=85&cnslIdx=${listDt.IDX}"/>" target="_blank">
										<c:out value="${listDt.TP_NM}"></c:out>
									</a>
								</c:when>
								<c:otherwise>
									<a href="<c:out value="${contextPath}/${crtSiteId}/consulting/viewAll.do?mId=95&cnslIdx=${listDt.IDX}"/>" target="_blank">
										<c:out value="${listDt.TP_NM}"></c:out>
									</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</strong>
					</td>
					<td><c:out value="${listDt.TYPE}"></c:out></td>
					<td><c:out value="${listDt.REGI_NAME}"></c:out></td>
					<td><c:out value="${listDt.REGI_DATE}"></c:out></td>
					<td>${confirmProgress[listDt.CONFM_STATUS]}</td>											
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${TOTAL_LIST_FORM_URL}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
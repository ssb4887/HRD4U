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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="contents-area02">
		<!-- search -->
		<itui:searchFormItemForDct2 divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DETAILLIST}" itemListSearch="${itemInfo.detailList_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" contextPath="${contextPath}" imgPath="${imgPath}"/>
		<!-- //search -->
	</div>
	<div class="contents-area">
		<h3 class="title-type01 ml0">최근 직업훈련 실시 현황 상세 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <b>${paginationInfo.totalRecordCount}</b>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<a href="${URL_LIST}" class="btn-m01 btn-color01">목록</a>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02">
				<caption>최근 직업훈련 실시 현황 : 번호, 연도, 업종, 상시 인원수, 참여기업, 참여인원에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width:8%">
					<col style="width:10%">
					<col style="width:auto">
					<col style="width:20%">
					<col style="width:10%">
					<col style="width:10%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">연도</th>
					<th scope="col">업종</th>
					<th scope="col">상시 인원수</th>
					<th scope="col">참여기업(개)</th>
					<th scope="col">참여인원(명)</th>
				</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
					<tr>
						<td colspan="6" class="bllist"><spring:message code="message.no.list"/></td>
					</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}"/>
					<tr>
						<td>${listNo}</td>
						<td>${listDt.YEAR}년</td>
						<td><itui:objectView itemId="indutyCode" itemInfo="${itemInfo}" objVal="${listDt.INDUTY_CD}"/></td>
						<td><itui:objectView itemId="rangeForList" itemInfo="${itemInfo}" objVal="${listDt.TOT_WORK_CNT_SCOPE}"/></td>
						<td><fmt:formatNumber value="${listDt.NMCORP}"/></td>
						<td><fmt:formatNumber value="${listDt.TOTAL_NMPR}"/></td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<!-- 페이지 내비게이션 -->
			<div class="paging-navigation-wrapper">
				<pgui:pagination listUrl="${URL_DETAILLIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
		</div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
<%@ include file="../../../../../include/commonTop.jsp"%>
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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/support/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<!-- search -->
	<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${DEVELOP_SUPPORT_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
	<!-- //search -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">훈련과정 맞춤개발 비용신청 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
            	<c:set var="searchList" value=""/>
            	<!-- 검색조건이 있을 때 -->
            	<c:if test="${!empty param.keyField || !empty param.is_confmStatus || !empty param.is_cnslType || !empty param.is_startYear}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}&is_cnslType=${param.is_cnslType}&is_startYear=${param.is_startYear}"/></c:if>
            	<a href="${DEVELOP_SUPPORT_EXCEL_URL}${searchList}" class="btn-m01 btn-color01">엑셀다운로드</a>
	        </div>		
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02">
				<caption>훈련과정 맞춤개발 비용신청 목록 : 순번, 기업명, 사업연도, 사업유형, 훈련과정명, 훈련과정명, 훈련회차, 상태, 신청일자, 처리일자, 처리자, 소속기관, 비용청구 신청서에 대한 정보 제공표 </caption>
				<colgroup>
					<col style="width:5%">
					<col style="width:15%">
					<col style="width:6%">
					<col style="width:8%">
					<col style="width:auto">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:9%">
					<col style="width:9%">
					<col style="width:6%">
					<col style="width:8%">
					<col style="width:8%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">기업명</th>
						<th scope="col">사업연도</th>
						<th scope="col">사업유형</th>
						<th scope="col">훈련과정명</th>
						<th scope="col">훈련회차</th>
						<th scope="col">상태</th>
						<th scope="col">등록일</th>
						<th scope="col">처리일</th>
						<th scope="col">처리자</th>
						<th scope="col">지부지사</th>
						<th scope="col">신청서</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
					<tr>
						<td colspan="12" class="bllist"><spring:message code="message.no.list"/></td>
					</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}"/>
					<tr>
						<td class="num"><c:out value="${listNo}"/></td>
						<td><c:out value="${listDt.BPL_NM}"/></td>
						<td><c:out value="${listDt.START_YEAR}"/></td>
						<td><c:out value="${listDt.BIZ_TYPE}"/></td>
						<td><strong class="point-color01"><a href="${DEVELOP_SUPPORT_VIEW_FORM_URL}&cnslIdx=${listDt.CNSL_IDX}&ctIdx=${listKey}&bplNo=${listDt.BPL_NO}"><c:out value="${listDt.START_YEAR}년 ${listDt.CNSL_TME}회차 맞춤개발"/></a></strong></td>
						<td><c:out value="${listDt.CNSL_TME}"/></td>
						<td><c:out value="${confirmProgress[listDt.CONFM_STATUS]}"/></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
						<td><c:if test="${listDt.CONFM_STATUS ne '10' && listDt.CONFM_STATUS ne '35' && listDt.CONFM_STATUS ne '75'}"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.LAST_MODI_DATE}"/></c:if></td>
						<td><c:if test="${listDt.CONFM_STATUS ne '10' && listDt.CONFM_STATUS ne '35' && listDt.CONFM_STATUS ne '75'}">${listDt.LAST_MODI_NAME}</c:if></td>
						<td><c:out value="${listDt.INSTT_NAME}"/></td>
						<td><strong class="point-color01"><a href="${DEVELOP_SUPPORT_VIEW_FORM_URL}&cnslIdx=${listDt.CNSL_IDX}&ctIdx=${listKey}&bplNo=${listDt.BPL_NO}">조회</a></strong></td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${DEVELOP_SUPPORT_LIST_FORM_URL}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/total/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<h3 class="title-type01 ml0">능력개발클리닉 기업 종합 이력 조회</h3>
	<!-- search -->
	<!-- 본부 직원(최고관리자 포함)과 지부지사 직원일 때 검색항목이 다름 -->
	<c:set var="searchList" value=""/>
	<c:choose>
		<c:when test="${loginVO.usertypeIdx eq '30'}">
			<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${TOTAL_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
			<!-- 검색조건이 있을 때 -->
            <c:if test="${!empty param.key || !empty param.is_year}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_year=${param.is_year}"/></c:if>
		</c:when>
		<c:otherwise>
			<itui:searchFormItemForDct2 contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${TOTAL_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search_head}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
			<!-- 검색조건이 있을 때 -->
				<c:if test="${!empty param.key || !empty param.is_year || !empty param.is_cliInsttIdx}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_year=${param.is_year}&is_cliInsttIdx=${param.is_cliInsttIdx}"/></c:if>
		</c:otherwise>
	</c:choose>
	<!-- //search -->
	<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
				<table>	
					<caption>능력개발클리닉 기업 목록표 : 기업명, 연차, 주치의, 등록일에 대한 정보 제공표</caption>		
					<colgroup>
						<col style="width: 40%">
						<col style="width: 20%">
						<col style="width: 20%">
						<col style="width: 20%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">기업명</th>
							<th scope="col">연차</th>
							<th scope="col">주치의</th>
							<th scope="col">등록일</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="cliCorp" items="${list}">
						<tr>
							<td>
								<a href="${TOTAL_DETAIL_FORM_URL}&bpl_no=${cliCorp.BPL_NO}" class="btn-linked" style="display:inline;"><strong class="point-color01">${cliCorp.BPL_NM}&nbsp;</strong><img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;" alt="${cliCorp.BPL_NM} 기업정보 상세보기"></a>
							</td>
							<td>${cliCorp.YEARLY}</td>
							<td>${cliCorp.DOCTOR_NAME}</td>
							<td>${cliCorp.VALID_YEAR}</td>
						</tr>				
					</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 페이지 내비게이션 -->
			<div class="paging-navigation-wrapper">
				<pgui:pagination listUrl="${TOTAL_LIST_FORM_URL}${searchList}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
			<!-- //페이지 내비게이션 -->
		</div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
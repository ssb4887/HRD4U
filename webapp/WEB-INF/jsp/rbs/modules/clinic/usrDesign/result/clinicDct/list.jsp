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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/result/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<!-- search -->
	<!-- 본부 직원(최고관리자 포함)과 지부지사 직원일 때 검색항목이 다름 -->
	<c:set var="searchList" value=""/>
	<c:choose>
		<c:when test="${loginVO.usertypeIdx eq '30'}">
			<itui:searchFormItemForDct2 contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${RESULT_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
           	<!-- 검색조건이 있을 때 -->
           	<c:if test="${!empty param.key || !empty param.is_confmStatus || !empty param.is_year}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}&is_year=${param.is_year}"/></c:if>
		</c:when>
		<c:otherwise>
			<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${RESULT_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search_head}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
			<!-- 검색조건이 있을 때 -->
            <c:if test="${!empty param.key || !empty param.is_confmStatus || !empty param.is_year || !empty param.is_cliInsttIdx}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}&is_year=${param.is_year}&is_cliInsttIdx=${param.is_cliInsttIdx}"/></c:if>
		</c:otherwise>
	</c:choose>
	<!-- //search -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 성과보고 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<c:if test="${loginVO.usertypeIdx ne '40'}">
	                <a href="#" class="btn-m01 btn-color01 fn_acceptAll">일괄접수</a>
	           	</c:if>	
	           	<a href="${RESULT_EXCEL_URL}${searchList}" class="btn-m01 btn-color01">엑셀다운로드</a>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02">
				<caption>능력개발클리닉 성과보고서 목록표 : No, 기업명, 연차, 제목, 상태, 주치의, 등록일에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width:5%">
					<col style="width:5%">
					<col style="width:20%">
					<col style="width:5%">
					<col style="width:16%">
					<col style="width:auto">
					<col style="width:auto">
					<col style="width:auto">
					<col style="width:auto">
					<col style="width:auto">
				</colgroup>
				<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>" class="checkbox-type01"/></th>
					<th scope="col">No</th>
					<th scope="col">기업명</th>
					<th scope="col">연차</th>
					<th scope="col">성과보고서</th>
					<th scope="col">주치의</th>
					<th scope="col">상태</th>
					<th scope="col">등록일</th>
					<th scope="col">처리일</th>
					<th scope="col">처리자</th>
				</tr>
				</thead>
				<tbody class="width-type02">
					<c:if test="${empty list}">
					<tr>
						<td colspan="10" class="bllist"><spring:message code="message.no.list"/></td>
					</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}"/>
					<tr>
						<td><c:if test="${listDt.CONFM_STATUS eq '10'}"><input type="checkbox" name="select" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listKey}" class="checkbox-type01" id="resultCheck${listNo}" data-cli="${listDt.CLI_IDX}" /></c:if></td>
						<td>${listNo}</td>
						<td>${listDt.BPL_NM}</td>
						<td>${listDt.YEARLY}년</td>
						<td><strong class="point-color01"><a href="<c:out value="${RESULT_VIEW_FORM_URL}"/>&${listIdxName}=${listKey}&bplNo=${listDt.BPL_NO}"><strong class="point-color01">능력개발클리닉 성과보고서</strong></a></strong></td>
						<td>${confirmProgress[listDt.CONFM_STATUS]}</td>
						<td>${listDt.DOCTOR_NAME}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
						<!-- 주치의가 처리했을 때 처리일자, 처리자 정보 보여주기(신청, 반려요청, 중도포기요청은 기업이 하는거라 제외) -->
						<c:choose>
							<c:when test="${listDt.CONFM_STATUS ne '10' && listDt.CONFM_STATUS ne '35' && listDt.CONFM_STATUS ne '75'}">
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.LAST_MODI_DATE}"/></td>
								<td>${listDt.LAST_MODI_NAME}</td>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<!-- 페이지 내비게이션 -->
			<div class="paging-navigation-wrapper">
				<pgui:pagination listUrl="${RESULT_LIST_FORM_URL}${searchList}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
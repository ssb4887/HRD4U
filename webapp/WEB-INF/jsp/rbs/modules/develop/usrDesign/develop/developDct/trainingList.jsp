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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/trainingList.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
		
	<!-- search -->
	<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${DEVELOP_TRAINING_LIST_FORM_URL}&isFromDevelop=${isFromDevelop}" itemListSearch="${itemInfo.training_list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${DEVELOP_TRAINING_LIST_FORM_URL}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
	<!-- //search -->
	<div class="contents-area">

		<h3 class="title-type01 ml0">표준과정 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong><fmt:formatNumber value="${paginationInfo.totalRecordCount}"/></strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<c:if test="${isFromDevelop eq 'Y' }">
			<a href="#none" class="btn-m01 btn-color01 fr" id="open-modal02">신규 등록</a>
			</c:if>
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col style="width:6%">
					<col style="width:12%">
					<col style="width:auto">
					<col style="width:8%">
					<col style="width:20%">
					<col style="width:10%">
					<c:if test="${isFromDevelop eq 'Y' }">
					<col style="width:10%">
					</c:if>
				</colgroup>
				<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">사업구분</th>
					<th scope="col">훈련과정명</th>
					<th scope="col">훈련시간</th>
					<th scope="col">NCS 대분류</th>
					<th scope="col">훈련방식</th>
					<c:if test="${isFromDevelop eq 'Y' }">
					<th scope="col">양식활용</th>
					</c:if>
				</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
					<tr>
						<c:if test="${isFromDevelop eq 'Y' }">
						<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
						</c:if>
						<c:if test="${isFromDevelop eq 'N' }">
						<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
						</c:if>
					</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
<%-- 					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" /> --%>
					<c:set var="listNo" value="${paginationInfo.firstRecordIndex + 1}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}"/>
					<tr>
						<td class="num"><c:out value="${listNo}"/></td>
						<td><c:out value="${listDt.BIZ_TYPE2}"/></td>
						<td>
						<strong class="point-color01">
                        	 <a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx=${listDt.TP_IDX}&prtbizIdx=${listDt.PRTBIZ_IDX}&source=${listDt.SOURCE}&devlopIdx=${devlopIdx}&bplNo=${bplNo}&isFromDevelop=${isFromDevelop}"><c:out value="${listDt.TP_NAME}"/></a>
						</strong>
						</td>
						<td>
							<c:out value="${listDt.TRTM}"/>
						</td>
						<td><c:out value="${listDt.L_NCS_NM}"/></td>
						<td><c:out value="${listDt.TRAINING_TYPE}"/></td>
						<c:if test="${isFromDevelop eq 'Y' }">
                        <td>                        
                        	 <a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx=${listDt.TP_IDX}&prtbizIdx=${listDt.PRTBIZ_IDX}&source=${listDt.SOURCE}&devlopIdx=${devlopIdx}&bplNo=${bplNo}&isFromDevelop=${isFromDevelop}" class="btn-m02 btn-color03">선택</a>
                        </td>	
                        </c:if>
					</tr>
					<c:set var="listNo" value="${listNo + 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<c:set var="searchList" value=""/>
           	<!-- 검색조건이 있을 때 -->
           	<c:if test="${!empty param.keyField || !empty param.is_prtbiz || !empty param.is_ncsLclasCd || !empty param.is_trTm1 || !empty param.is_trTm2}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_prtbiz=${param.is_prtbiz}&is_ncsLclasCd=${param.is_ncsLclasCd}&is_trTm1=${param.is_trTm1}&is_trTm2=${param.is_trTm2}"/></c:if>
			<pgui:pagination listUrl="${DEVELOP_TRAINING_LIST_FORM_URL}&tpList=${tpList}${searchList}&isFromDevelop=${isFromDevelop }" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>

<%@ include file="newWriteModal.jsp" %>	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
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
	<div id="cms_board_article" class="subConBox contents-wrapper">
		<!-- search -->
		<%-- <itui:searchFormItemForDct4 contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/> --%>
		<!-- //search -->
		<div class="contents-area">
			<div class="title-wrapper">
				<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			</div>
			<div class="table-type01 horizontal-scroll">
			<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
				<input type="hidden" name="aracList" id="aracList" value=""/>
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
					<colgroup>
						<col style="width:8%">
						<col style="width:10%">
						<col>
						<col style="width:8%">
						<col style="width:15%">
						<col style="width:15%">
					</colgroup>
					<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col"><spring:message code="item.modify.name"/></th>
						<th scope="col">서버명</th>
						<th scope="col">서버아이피</th>
						<th scope="col">서버포트</th>
						<th scope="col">유저ID</th>
						<th scope="col">패스워드</th>
					</tr>
					</thead>
					<tbody class="alignC">
						<c:if test="${empty list}">
						<tr>
							<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
						<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
						<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
						<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}"/>
						<tr>
							<td class="num">${listNo}</td>
							<td><a href="<c:out value="${URL_MODIFY}"/>&${listIdxName}=${listKey}" class="btn-m03 btn-color03">수정</a></td>
							<td><c:out value="${listDt.SERVER_NM }"/></td>
							<td><c:out value="${listDt.SERVER_IP }"/></td>
							<td><c:out value="${listDt.SERVER_PORT }"/></td>
							<td><c:out value="${listDt.USER_ID }"/></td>
							<td><c:out value="${listDt.PASSWORD }"/></td>
						</tr>
						<c:set var="listNo" value="${listNo - 1}"/>
						</c:forEach>
					</tbody>
				</table>
				</form>
			</div>
			<!-- 페이지 내비게이션 -->
			<div class="page">
				<pgui:pagination listUrl="${URL_PAGE_LIST}&is_date1=${OPERT_START_DT}&is_date2=${OPERT_END_DT}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
			<!-- //페이지 내비게이션 -->
			
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
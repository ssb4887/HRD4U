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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article" class="subConBox contents-wrapper">
		<h2></h2>
		<!-- search -->
		<itui:searchFormItemForAuthGroup contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<!-- 검색조건이 있을 때 -->
				<c:if test="${!empty param.is_site_id || !empty param.is_authName}"><c:set var="searchList" value="&is_site_id=${param.is_site_id}&is_authName=${param.is_authName}"/></c:if>
				<button type="button" class="btn-m01 btn-color03" id="excel_download" onclick="location.href='excel.do?mId=${crtMenu.menu_idx}${searchList}'" style="margin-right: 10px;">엑셀 다운로드</button>
				<a href="<c:out value="${URL_INPUT}"/>" class="btn-m01 btn-color01">등록</a>
				<a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btnTD fn_btn_delete btn-m01 btn-color01">삭제</a>
			</div>
		</div>
		<div class="table-type01 horizontal-scroll">
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col style="width:50px">
				<col style="width:50px">
				<col style="width:50px">
				<col style="width:200px">
				<col style="width:200px">
				<col style="width:100px">
				<col style="width:150px">
			</colgroup>
			<thead>
			<tr>
				<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>"/></th>
				<th scope="col">No</th>
				<th scope="col"><spring:message code="item.modify.name"/></th>
				<th scope="col"><itui:objectItemName itemId="siteId" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="authName" itemInfo="${itemInfo}"/></th>
				<th scope="col"><spring:message code="item.reginame1.name"/></th>
				<th scope="col" class="end"><spring:message code="item.regidate.name"/></th>
			</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
				<tr>
					<td colspan="6" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt.AUTH_IDX}"/>
				<tr>
					<td><input type="checkbox" name="select" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listKey}"/></td>
					<td class="num">${listNo}</td>
					<td><a href="<c:out value="${URL_MODIFY}"/>&${listIdxName}=${listKey}" class="btn-m03 btn-color03 open-modal04">수정</a></td>
					<td class="subject"><itui:objectView itemId="siteId" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="authName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td>${listDt.REGI_NAME}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="page">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
		<div class="btnList">
			
		</div>
	</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
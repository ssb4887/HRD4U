<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="searchFormId" value="fn_memberGrupSearchForm"/>
<c:set var="listFormId" value="fn_memberGrupListForm"/>
<jsp:include page="${jspSiteIncPath}/dialog_top.jsp" flush="false">
	<jsp:param name="javascript_page" value="${moduleJspRPath}/usrSearchList.jsp"/>
	<jsp:param name="searchFormId" value="${searchFormId}"/>
	<jsp:param name="listFormId" value="${listFormId}"/>
</jsp:include>

<style>
.basic-search-wrapper dl>dd select {
    
    background-image: url();
}

</style>
	<div id="cms_board_article">
		<!-- search -->
		<itui:searchFormItemForDct3 divClass="contents-area02" contextPath="${contextPath}" imgPath="${imgPath}" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.sublist_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03" />
		<!-- //search -->
	
		<!-- //button -->
		<div class="table-type01 horizontal-scroll" style="margin: 5% 0 0 0;">
		<form id="${listFormId}" name="${listFormId}" method="post" target="submit_target" >
		<table class="tbListA" style="width:100%;" summary="<c:out value="${settingInfo.list_title}"/> 검색 목록을 볼 수 있습니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 검색 목록</caption>
			<colgroup>
				<col width="10%" />
				<col width="10%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="25%" />
				<!-- <col width="25%" /> -->
			</colgroup>
			<thead>
			<tr>
				<th scope="col">NO	</th>
				<th scope="col">선택</th>
				<th scope="col">아이디</th>
				<th scope="col">성명</th>
				<th scope="col">연락처</th>
				<th scope="col">이메일</th>
				<th scope="col" class="end">소속</th>
				<!-- <th scope="col"class="end">회원구분</th> -->
				<!-- 마지막 th에 class="end" -->
			</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
				<tr>
					<td colspan="7" class="bllist"><spring:message code="message.no.search.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listIdxColumn" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt[listIdxColumn]}"/>
				<tr>
					<td class="num">${listNo}</td>
					<td><button type="button" title="등록" class="btn-m03 btn-color03" value="${listKey}" data-name="<c:out value="${listDt.MEMBER_NAME}"/>">선택</button></td>
					<td><c:out value="${listDt.MEMBER_ID }"/></td>
					<td><itui:objectView itemId="mbrName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td>${listDt.MOBILE_PHONE}</td>
					<td>${listDt.MEMBER_EMAIL}</td>
					<td>${listDt.ORG_NAME}</td>
					<%-- <td>
						<c:choose>
							<c:when test="${listDt.USERTYPE_IDX == '40'}">본부</c:when>
							<c:when test="${listDt.USERTYPE_IDX == '30'}">소속기관</c:when>
							<c:when test="${listDt.USERTYPE_IDX == '20'}">민간센터</c:when>
							<c:when test="${listDt.USERTYPE_IDX == '10'}">컨설턴트</c:when>
							<c:when test="${listDt.USERTYPE_IDX == '5'}">기업회원</c:when>
							<c:when test="${listDt.USERTYPE_IDX == '3'}">개인회원</c:when>
						</c:choose>
						<c:out value="${listDt.REGI_NAME }"/> &nbsp; 
						<c:if test="${listDt.CLSF_CD ==  'Y'}">팀장</c:if>
						<c:if test="${listDt.CLSF_CD ==  'N'}">팀원</c:if> 
					</td>--%>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		</div>
		<!-- paging -->
		<div class="page">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //paging -->
	</div>
<jsp:include page="${jspSiteIncPath}/dialog_bottom.jsp" flush="false"/>
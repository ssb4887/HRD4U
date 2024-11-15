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
	<div id="cms_board_article">
		<!-- search -->
		<itui:searchFormItem divClass="tbMSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo[itemListSearchId]}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/>
		<!-- //search -->
	
		<!-- button -->
		<div class="btnTopFull">
			<div class="right">
				<button type="button" title="등록" class="btnTW fn_btn_select">선택</button>
			</div>
		</div>
		<!-- //button -->
		<form id="${listFormId}" name="${listFormId}" method="post" target="submit_target">
		<table class="tbListA" summary="<c:out value="${settingInfo.list_title}"/> 검색 목록을 볼 수 있습니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 검색 목록</caption>
			<colgroup>
				<col width="30px" />
				<col width="50px" />
				<col width="150px" />
				<col />
				<col width="150px" />
			</colgroup>
			<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">번호</th>
				<th scope="col">아이디</th>
				<th scope="col">이름</th>
				<th scope="col"class="end">사용자유형</th>
				<!-- 마지막 th에 class="end" -->
			</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
				<tr>
					<td colspan="5" class="bllist"><spring:message code="message.no.search.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listIdxColumn" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt[listIdxColumn]}"/>
				<tr>
					<td><input type="radio" id="select" name="select" title="<spring:message code="item.select"/>" value="${listKey}" data-name="<c:out value="${listDt.MEMBER_NAME}"/>"/></td>
					<td class="num">${listNo}</td>
					<td><itui:objectView itemId="mbrId" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="mbrName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><c:out value="${listDt.USERTYPE_IDX_NAME}"/></td>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		
		<!-- paging -->
		<div class="paginate mgt15">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //paging -->
	</div>
<jsp:include page="${jspSiteIncPath}/dialog_bottom.jsp" flush="false"/>
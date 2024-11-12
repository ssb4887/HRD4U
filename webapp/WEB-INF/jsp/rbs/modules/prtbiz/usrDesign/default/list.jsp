<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
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
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
<%@ include file="../../listBtnsScript.jsp"%>
	<div id="cms_board_article" class="contents-wrapper">
		<!-- search -->
		<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
		<!-- //search -->
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<a href="<c:out value="${URL_INPUT}"/>" class="btn-m01 btn-color03 fn_btn_write">등록</a>
				<a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btn-m01 btn-color02 fn_btn_delete">삭제</a>
			</div>
		</div>
		<div class="table-type01 horizontal-scroll">
			<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
			<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col width="5%" />
					<col width="5%" />
					<col width="10%" />
					<col />
					<col width="12%" />
					<col width="15%" />
				</colgroup>
				<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>" class="checkbox-type01"/></th>
					<th scope="col">No</th>
					<th scope="col">수정</th>
					<th scope="col"><itui:objectItemName itemId="prtbizName" itemInfo="${itemInfo}"/></th>
					<th scope="col">작성자</th>
					<th scope="col"><spring:message code="item.regidate.name"/></th>
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
					<c:set var="listKey" value="${listDt[listColumnName]}"/>
					<tr>
						<td><input type="checkbox" id="select" name="select" title="<spring:message code="item.select"/>" value="${listKey}" class="checkbox-type01"/></td>
						<td class="num">${listNo}</td>
						<td><a href="<c:out value="${URL_MODIFY}"/>&${listIdxName}=${listKey}" class="btn-m03 btn-color03 ${btnModifyClass}">수정</a></td>
						<td class="tlt">
							<a href="<c:out value="${URL_VIEW}"/>&${listIdxName}=${listKey}" class="fn_btn_view"><strong class="title"><itui:objectView itemId="prtbizName" itemInfo="${itemInfo}" objDt="${listDt}"/></strong>
							<c:if test="${listDt.REGI_DATE_FORMAT eq today}">
								<img src="${contextPath}/${crtSiteId}/images/board/icon_new01.png" alt="new" class="icon-new">
							</c:if>
							</a>
						</td>
						<td>${listDt.REGI_NAME}</td>
						<td class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</form>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
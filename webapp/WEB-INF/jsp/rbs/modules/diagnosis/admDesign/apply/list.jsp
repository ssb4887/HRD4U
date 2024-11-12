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
	<div id="cms_board_article" class="subConBox">
		<h2 class="title">기초진단 신청(사용자)</h2>
		<h3>기초 진단 소개 콘텐츠 첨부 예정</h3>
		<!-- search -->
<%-- 		<itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/> --%>
		<!-- //search -->
<!-- 		<div class="bbs_top"> -->
<%-- 			<p class="listNum">총 <b>${paginationInfo.totalRecordCount}</b>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p> --%>
<!-- 		</div> -->
<%-- 		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target"> --%>
<%-- 		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다."> --%>
<%-- 			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption> --%>
<!-- 			<colgroup> -->
<!-- 				<col width="50px" /> -->
<!-- 				<col width="100px" /> -->
<!-- 				<col width="100px" /> -->
<!-- 				<col width="150px" /> -->
<!-- 				<col width=150px" /> -->
<!-- 				<col width="100px" /> -->
<!-- 				<col width="100px" /> -->
<!-- 				<col width="100px" /> -->
<!-- 			</colgroup> -->
<!-- 			<thead> -->
<!-- 			<tr> -->
<!-- 				<th scope="col">No</th> -->
<%-- 				<th scope="col"><itui:objectItemName itemId="issueNo" itemInfo="${itemInfo}"/></th> --%>
<%-- 				<th scope="col"><itui:objectItemName itemId="bizNm" itemInfo="${itemInfo}"/></th> --%>
<%-- 				<th scope="col"><itui:objectItemName itemId="bizrNo" itemInfo="${itemInfo}"/></th> --%>
<%-- 				<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th> --%>
<%-- 				<th scope="col"><itui:objectItemName itemId="issueDate" itemInfo="${itemInfo}"/></th> --%>
<!-- 				<th scope="col">기초진단서 출력</th> -->
<!-- 				<th scope="col">HRD기초컨설팅</th> -->
<!-- 			</tr> -->
<!-- 			</thead> -->
<!-- 			<tbody class="alignC"> -->
<%-- 				<c:if test="${empty list}"> --%>
<!-- 				<tr> -->
<%-- 					<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td> --%>
<!-- 				</tr> -->
<%-- 				</c:if> --%>
<%-- 				<c:set var="listIdxName" value="${settingInfo.idx_name}"/> --%>
<%-- 				<c:set var="listColumnName" value="${settingInfo.idx_column}"/> --%>
<%-- 				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" /> --%>
<%-- 				<c:forEach var="listDt" items="${list}" varStatus="i"> --%>
<%-- 				<c:set var="listKey" value="${listDt[listColumnName]}"/> --%>
<!-- 				<tr> -->
<%-- 					<td class="num">${listNo}</td> --%>
<%-- 					<td><itui:objectView itemId="issueNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td> --%>
<%-- 					<td><itui:objectView itemId="bizNm" itemInfo="${itemInfo}" objDt="${listDt}"/></td> --%>
<%-- 					<td><itui:objectView itemId="bizrNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td> --%>
<%-- 					<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td> --%>
<%-- 					<td class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.ISSUE_DATE}"/></td> --%>
<!-- 					<td><button>dd</button></td> -->
<%-- 					<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td> --%>
<%-- 					<c:if test="${itemInfo.issueNo }"> --%>
<!-- 						<td><button></button></td> -->
<%-- 					</c:if> --%>
<!-- 				</tr> -->
<%-- 				<c:set var="listNo" value="${listNo - 1}"/> --%>
<%-- 				</c:forEach> --%>
<!-- 			</tbody> -->
<!-- 		</table> -->
<!-- 		</form> -->
		<!-- 페이지 내비게이션 -->
<!-- 		<div class="page"> -->
<%-- 			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/> --%>
<!-- 		</div> -->
		<!-- //페이지 내비게이션 -->
<!-- 		<div class="btnList"> -->
<%-- 			<c:if test="${wrtAuth}"> --%>
<%-- 			<a href="<c:out value="${URL_INPUT}"/>" class="fn_btn_write">등록</a> --%>
<%-- 			</c:if> --%>
<!-- 		</div> -->
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
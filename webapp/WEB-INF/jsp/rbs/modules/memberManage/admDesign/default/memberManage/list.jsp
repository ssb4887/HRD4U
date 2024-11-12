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
	<div id="cms_board_article" class="subConBox">
		<h2></h2>
		<!-- search -->
		<itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/>
		<!-- //search -->
		<div class="bbs_top">
			<p class="listNum">총 <b>${paginationInfo.totalRecordCount}</b>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<c:if test="${wrtAuth}">
			<a href="<c:out value="${URL_INPUT}"/>" target="_blank" class="fn_btn_write">등록</a>
			<a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btnTD fn_btn_delete">삭제</a>
			</c:if>
		</div>
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col style="width:50px">
				<col style="width:50px">
				<col style="width:100px">
				<col style="width:100px">
				<col style="width:100px">
				<col style="width:150px">
				<col style="width:200px">
				<col style="width:100px">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col"><spring:message code="item.modify.name"/></th>
				<th scope="col">아이디</th>
				<th scope="col">성명</th>
				<th scope="col">회원구분</th>
				<th scope="col">소속기관(공단)</th>
				<th scope="col">기업(기관)</th>
				<th scope="col" class="end">가입일</th>
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
				<c:set var="listKey" value="${listDt.MEMBER_IDX}"/>
				<tr>
					<td class="num">${listNo}</td>
					<c:choose>
						<c:when test="${listDt.USERTYPE_IDX >= 30}"><td><a href="<c:out value="${URL_VIEWEMPLOY}"/>&${listIdxName}=${listKey}" class="btnTypeF fn_btn_modify">수정</a></td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 20}"><td><a href="<c:out value="${URL_VIEWCONSULT}"/>&${listIdxName}=${listKey}" class="btnTypeF fn_btn_modify">수정</a></td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 10}"><td><a href="<c:out value="${URL_VIEWCENTER}"/>&${listIdxName}=${listKey}" class="btnTypeF fn_btn_modify">수정</a></td></c:when>
						<c:otherwise><td><a href="<c:out value="${URL_VIEWCORP}"/>&${listIdxName}=${listKey}" class="btnTypeF fn_btn_modify">수정</a></td></c:otherwise>
					</c:choose>
					<td class="subject">${listDt.MEMBER_ID }</td>
					<td>${listDt.MEMBER_NAME }</td>
					<c:choose>
						<c:when test="${listDt.USERTYPE_IDX >= 40}"><td>직원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 30}"><td>소속직원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 20}"><td>컨설턴트</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 10}"><td>민간센터</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 5}"><td>기업회원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 1}"><td>개인회원</td></c:when>
					</c:choose>
					<td>${listDt.REGIMENT}</td>
					<td>${listDt.ORG_NAME}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<!-- 페이지 내비게이션 -->
		<div class="page">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
		<div class="btnList">
			
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
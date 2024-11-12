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
		<itui:searchFormItemForMem contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<!-- 검색조건이 있을 때 -->
            	<c:if test="${!empty param.key || !empty param.is_region || !empty param.is_utpIdx }"><c:set var="searchList" value="&is_utpIdx=${param.is_utpIdx}&is_region=${param.is_region}&keyField=${param.keyField}&key=${param.key}"/></c:if>
                <button type="button" class="btn-m01 btn-color03" id="excel_download" onclick="location.href='excel.do?mId=${crtMenu.menu_idx}${searchList}'" style="margin-right: 10px;">엑셀 다운로드</button>
            </div>
		</div>
		<div class="table-type01 horizontal-scroll">
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col style="width:8%">
				<col style="width:8%">
				<col style="width:12%">
				<col style="width:10%">
				<col style="width:12%">
				<col style="width:15%">
				<col style="width:20%">
				<col style="width:15%">
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
					<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt.MEMBER_IDX}"/>
				<c:set var="corpIdxName" value="corpNum"/>
				<c:set var="corpKey" value="${listDt.CORP_NUM}"/>
				<tr>
					<td class="num">${listNo}</td>
					<c:choose>
						<c:when test="${listDt.USERTYPE_IDX >= 30}"><td><a href="<c:out value="${URL_INPUTSECEMPLOY}"/>&${listIdxName}=${listKey}" class="fn_btn_modify btn-m03 btn-color03">수정</a></td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 20}"><td><a href="<c:out value="${URL_INPUTCENTER}"/>&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}" class="fn_btn_modify btn-m03 btn-color03">수정</a></td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 10}"><td><a href="<c:out value="${URL_INPUTCONSULT}"/>&${listIdxName}=${listKey}" class="fn_btn_modify btn-m03 btn-color03">수정</a></td></c:when>
						<c:otherwise><td><a href="<c:out value="${URL_INPUTCORP}"/>&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}" class="fn_btn_modify btn-m03 btn-color03">수정</a></td></c:otherwise>
					</c:choose>
					<td class="subject">${listDt.MEMBER_ID }</td>
					<td>${listDt.MEMBER_NAME }</td>
					<c:choose>
						<c:when test="${listDt.USERTYPE_IDX >= 40}"><td>직원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 30}"><td>소속직원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 20}"><td>민간센터</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 10}"><td>컨설턴트</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 5}"><td>기업회원</td></c:when>
						<c:when test="${listDt.USERTYPE_IDX >= 1}"><td>개인회원</td></c:when>
					</c:choose>
					<td>
						<c:choose>
							<c:when test="${listDt.USERTYPE_IDX >= 30}">
								${listDt.INSTT_IDX_1}
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						${listDt.REGIMENT}
					</td>
					<td>${listDt.ORG_NAME}</td>
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
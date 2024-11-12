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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/plan/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 훈련계획서 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<a href="<c:out value="${PLAN_WRITE_FORM_URL}"/>" class="btn-m01 btn-color01 fn_btn_write">등록</a>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02" summary="능력개발클리닉 신청 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
				<caption>능력개발클리닉 훈련계획서 목록표 : No, 기업명, 수정, 제목, 상태, 주치의, 등록일에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width:6%">
					<col style="width:8%">
					<col style="width:auto">
					<col style="width:35%">
					<col style="width:auto">
					<col style="width:auto">
					<col style="width:auto">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">연차</th>
					<th scope="col">수정</th>
					<th scope="col">제목</th>
					<th scope="col">상태</th>
					<th scope="col">주치의</th>
					<th scope="col">등록일</th>
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
						<td>${listDt.YEARLY}년</td>
						<td>
						<c:choose>
							<c:when test="${listDt.CONFM_STATUS eq '10' }">							
								<!-- 신청 상태일 때만 회수할 수 있음 -->
								<a href="<c:out value="${PLAN_WITHDRAW_URL}"/>&planIdx=${listKey}&cliIdx=${listDt.CLI_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">회수</a>
							</c:when>
							<c:when test="${listDt.CONFM_STATUS eq '30' }">
								<!-- 접수 상태일 때만 반려요청할 수 있음 -->
								<a href="<c:out value="${PLAN_RETURNREQUEST_URL}"/>&planIdx=${listKey}&cliIdx=${listDt.CLI_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">반려요청</a>
							</c:when>
							<c:when test="${listDt.CONFM_STATUS eq '5' || listDt.CONFM_STATUS eq '20' || listDt.CONFM_STATUS eq '40' || listDt.CONFM_STATUS eq '55' || listDt.CONFM_STATUS eq '53'}">
								<!-- 임시저장 ,회수, 반려, 최종승인 상태일 때만 수정할 수 있음 -->
								<!-- 최종승인일 때 수정은 변경요청 하는 것 -->
								<a href="<c:out value="${PLAN_MODIFY_FORM_URL}"/>&planIdx=${listKey}" class="btn-m02 btn-color03 ${btnModifyClass}">수정</a>
							</c:when>						
							<c:otherwise></c:otherwise>												
						</c:choose>
						</td>
						<td class="subject"><strong class="point-color01"><a href="<c:out value="${PLAN_VIEW_FORM_URL}"/>&${listIdxName}=${listKey}" class="fn_btn_view"><fmt:formatDate pattern="yyyy" value="${listDt.REGI_DATE}"/>년 능력개발클리닉 훈련계획서</a></strong></td>
						<td>${confirmProgressMap[listDt.CONFM_STATUS]}</td>
						<td>${listDt.DOCTOR_NAME }</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${PLAN_LIST_FORM_URL}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
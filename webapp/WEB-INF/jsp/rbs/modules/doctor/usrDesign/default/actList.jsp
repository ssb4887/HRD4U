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
<%@ include file="../../listBtnsScript.jsp"%>
	<div id="cms_board_article" class="contents-wrapper">
		<!-- search -->
		<itui:searchFormItemForDct divClass="contents-area02" insttList="${insttList}" imgPath="${imgPath}" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_ACTLIST}" itemListSearch="${itemInfo.actList_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
			<div class="title-wrapper">
				<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
				<div class="fr">
				<!-- 검색조건이 있을 때 -->
				<c:if test="${!empty param.key || !empty param.is_instt_idx}"><c:set var="searchList" value="&is_instt_idx=${param.is_instt_idx}&keyField=${param.keyField}&key=${param.key}"/></c:if>
				<button type="button" class="btn-m01 btn-color03" id="excel_download" onclick="location.href='excel.do?mId=${crtMenu.menu_idx}${searchList}'" style="margin-right: 10px;">엑셀 다운로드</button>
            </div>
			</div>
			<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
			<div class="table-type01 horizontal-scroll">
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
					<colgroup>
						<col style="width: 5%" />
                        <col style="width: 9%" />
                        <col style="width: 9%" />
                        <col style="width: 9%" />
                        <col style="width: 11%" />
                        <col style="width: 11%" />
                        <col style="width: 11%" />
                        <col style="width: 11%" />
                        <col style="width: 13%" />
                        <col style="width: 11%" />
						<col />
					</colgroup>
					<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">현소속기관</th>
						<th scope="col">성명</th>
						<th scope="col">주치의여부</th>
						<th scope="col">관리기업</th>
						<th scope="col">기업HRD이음컨설팅</th>
						<th scope="col">클리닉기업</th>
						<th scope="col">훈련성과분석</th>
						<th scope="col">컨설팅</th>
						<th scope="col">주치의교육</th>
					</tr>
					</thead>
					<tbody>
						<c:if test="${empty list}">
						<tr>
							<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:set var="index" value="1"/>
						<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
						<c:forEach var="listDt" items="${list}" varStatus="i">
							<tr>
								<td>${listNo}</td>
								<td>${listDt.INSTT_NAME}</td>
								<td>${listDt.MEMBER_NAME}</td>
								<td>
									<c:choose>
										<c:when test="${listDt.APPLY_YN == 'Y'}">주치의</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
								<td><strong class="point-color01">${listDt.CORP}</strong><a href="<%=MenuUtil.getMenuUrl(42) %>&isDoctorIdx=${listDt.DOCTOR_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td>
								<td><strong class="point-color01">${listDt.DGNS}</strong><a href="<%=MenuUtil.getMenuUrl(37) %>&is_doctorIdx=${listDt.DOCTOR_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td>
								<td><strong class="point-color01">${listDt.CLINIC}</strong><a href="<%=MenuUtil.getMenuUrl(66) %>&is_doctorIdx=${listDt.DOCTOR_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td>
								<td><strong class="point-color01">${listDt.ANALYSIS}</strong><a href="<%=MenuUtil.getMenuUrl(153) %>&insttIdx=${listDt.INSTT_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td>
								<td><strong class="point-color01">${listDt.CONSULT}</strong><a href="<%=MenuUtil.getMenuUrl(126) %>&memberIdx=${listDt.MEMBER_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td>
								<td><strong class="point-color01">${listDt.EDU}</strong><a href="<%=MenuUtil.getMenuUrl(139) %>&is_doctorIdx=${listDt.MEMBER_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td>
							</tr>
							<c:set var="listNo" value="${listNo - 1}"/>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</form>
			<!-- 페이지 내비게이션 -->
			<div class="page">
				<pgui:pagination listUrl="${URL_ACTLIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
			<!-- //페이지 내비게이션 -->
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
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
		<itui:searchFormItemForDct divClass="contents-area02" insttList="${insttList}" contextPath="${contextPath}" imgPath="${imgPath}" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
			<div class="title-wrapper">
				<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
				<%-- <div class="fr">
					<a href="<c:out value="${URL_INPUT}"/>" class="btn-m01 btn-color01 fn_btn_write">등록</a>
					<a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btn-m01 btn-color01 fn_btn_delete">삭제</a>
				</div> --%>
			</div>
			<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
			<div class="table-type01 horizontal-scroll">
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
					<colgroup>
						<!-- <col style="width:5%"> -->
						<col style="width:5%">
						<!-- <col style="width:8%"> -->
						<col style="width:15%">
						<col style="width:20%">
						<col >
						<!-- <col style="width:14%"> -->
						<col />
					</colgroup>
					<thead>
					<tr>
						<%-- <th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>" class="checkbox-type01"/></th> --%>
						<th scope="col">No</th>
						<!-- <th scope="col">수정</th> -->
						<th scope="col">아이디</th>
						<th scope="col">성명</th>
						<th scope="col">소재지</th>
						<th scope="col">소속기관</th> 
						<!-- <th scope="col">관할구역</th> -->
					</tr>
					</thead>
					<tbody>
						<c:if test="${empty list}">
						<tr>
							<td colspan="5" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:set var="index" value="1"/>
						<c:forEach var="listDt" items="${list}" varStatus="i">
							<tr>
								<%-- <td><input type="checkbox" id="select" name="select" title="<spring:message code="item.select"/>" value="${listDt.DOCTOR_IDX}" class="checkbox-type01"/></td> --%>
								<td class="num">${i.count}</td>
								<%-- <td><a href="<c:out value="${URL_MODIFY}"/>&doctorIdx=${listDt.DOCTOR_IDX}&insttIdx=${listDt.INSTT_IDX}" class="btn-m03 btn-color03 ${btnModifyClass}">수정</a></td> --%>
								<%-- <td class="subject"><a href="<c:out value="${URL_VIEW}"/>&${listIdxName}=${listKey}" class="fn_btn_view">${listDt.HNAME}</a></td> --%>
								<td class="subject">${listDt.LOGINID}</td>
								<td>${listDt.HNAME}</td>
								<%-- <td><strong class="point-color01">${listDt.HNAME}</strong><a href="<c:out value="${URL_VIEW}"/>&doctorIdx=${listDt.DOCTOR_IDX}" class="fn_btn_view btn-linked"><img src="${contextPath}${imgPath}/icon/icon_search04.png"/></a></td> --%>
								<td>${listDt.SIDO}</td>
								<td>${listDt.INSTT_NAME}</td>
								<%-- <td class="left">
									<c:choose>
										<c:when test="${listDt.ISCHANGE eq '1'}"><span style="color:red;"><c:out value="관할구역 변경 필요"/></span></c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${fn:contains(listDt.GUNGU, '-')}"><c:out value="-"/></c:when>
												<c:otherwise>${fn:replace(listDt.GUNGU, '/', '<br/>')}</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</td> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</form>
			<!-- 페이지 내비게이션 -->
			<div class="page">
				<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
			<!-- //페이지 내비게이션 -->
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
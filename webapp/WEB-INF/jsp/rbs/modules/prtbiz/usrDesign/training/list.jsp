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
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
<div class="contents-area">
	<p class="total">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
		<%@ include file="../../listBtnsScript.jsp"%>
		<div class="table-type01 horizontal-scroll">
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col width="5%" />
				<col width="5%" />
				<col width="10%" />
				<col width="30%"/>
				<col width="30%" />
				<col width="20%" />
			</colgroup>
			<thead>
			<tr>
				<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>" class="checkbox-type01"/></th>
				<th scope="col">No</th>
				<th scope="col">수정</th>
				<th scope="col">훈련과정명</th>
				<th scope="col">훈련기관명</th>
				<th scope="col">NCS 분류</th>
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
				<c:set var="listKey" value="${listDt.TP_IDX}"/>
				<tr>
					<td><input type="checkbox" id="select" name="select" title="<spring:message code="item.select"/>" value="${listKey}" class="checkbox-type01"/></td>
					<td class="num">${listNo}</td>
					<td><a href="${contextPath}/${crtSiteId}/prtbiz/input.do?mId=90&mode=m&tpIdx=${listDt.TP_IDX}" class="btn-m03 btn-color03 ${btnModifyClass}">수정</a></td>
					<td class="left"><a href="${contextPath}/${crtSiteId}/prtbiz/view.do?mId=90&tpIdx=${listDt.TP_IDX}" class="fn_btn_view"><strong class="title">${listDt.TP_NAME}</strong></a>
						<c:if test="${listDt.REGI_DATE_FORMAT eq today}">
							<img src="${contextPath}/${crtSiteId}/images/board/icon_new01.png" alt="new" class="icon-new">
						</c:if>
					</td>
					<td>${listDt.TP_OPRINST}</td>
					<td><itui:objectMultiSelectView itemId="ncsSclasCode" itemInfo="${itemInfo}" objDt="${listDt}" optnHashMap="${optnHashMap}"/></td>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		</div>
		<div class="btns-area mt60">
			<div class="btns-right">
				<c:if test="${wrtAuth}">
					<a href="${contextPath}/${crtSiteId}/prtbiz/input.do?mId=90&prtbizIdx=${dt.PRTBIZ_IDX}" class="btn-m01 btn-color03">등록</a>
					<a href="${contextPath}/${crtSiteId}/prtbiz/deleteProc.do?mId=90" title="삭제" class="btn-m01 btn-color02 depth3 fn_btn_delete">삭제</a>
				</c:if>
			</div>
		</div>
		<!-- paging -->
		<div class="paginate mgt15">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //paging -->
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>

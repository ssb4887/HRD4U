<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
		<div class="bbs_top">
			<p class="listNum">총 <b>${paginationInfo.totalRecordCount}</b>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
		</div>
		<%@ include file="../../listBtnsScript.jsp"%>
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table class="tbListA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col width="50px" />
				<col width="60px" />
				<col width="60px" />
				<col />
				<col width="200px" />
				<col width="200px" />
			</colgroup>
			<thead>
			<tr>
				<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>"/></th>
				<th scope="col">번호</th>
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
					<td><c:if test="${mngAuth || wrtAuth && listDt.AUTH_MNG == '1'}"><input type="checkbox" id="select" name="select" title="<spring:message code="item.select"/>" value="${listKey}"/></c:if></td>
					<td class="num">${listNo}</td>
					<td><c:if test="${mngAuth || wrtAuth && listDt.AUTH_MNG == '1'}"><a href="${contextPath}/${crtSiteId}/menuContents/dct/prtbiz/input.do?mId=90&mode=m&tpIdx=${listDt.TP_IDX}" class="btnTypeF ${btnModifyClass}">수정</a></c:if></td>
					<td class="tlt"><a href="${contextPath}/${crtSiteId}/menuContents/dct/prtbiz/view.do?mId=90&tpIdx=${listDt.TP_IDX}" class="fn_btn_view">${listDt.NAME}</a></td>
					<td>${listDt.CRDNS}</td>
					<td><itui:objectMultiSelectView itemId="ncsSclsCd" itemInfo="${itemInfo}" objDt="${listDt}" optnHashMap="${optnHashMap}"/></td>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<div class="btnTopFull">
			<div class="right">
				<a href="${contextPath}/${crtSiteId}/menuContents/dct/prtbiz/input.do?mId=90&prtbizIdx=${dt.PRTBIZ_IDX}" class="btnTypeA">등록</a>
				<a href="${contextPath}/${crtSiteId}/menuContents/dct/prtbiz/deleteProc.do?mId=90" title="삭제" class="btnTypeA fn_btn_delete">삭제</a>
			</div>
		</div>
		<!-- paging -->
		<div class="paginate mgt15">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //paging -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
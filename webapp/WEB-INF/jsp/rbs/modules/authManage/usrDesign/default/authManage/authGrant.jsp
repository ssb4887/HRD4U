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
	<div id="contents-area">
		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>권한부여현황표 : 분류, 권한그룹, 사용자수에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width:20%">
					<col style="width:40%">
					<col style="width:20%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">분류</th>
					<th scope="col">권한그룹</th>
					<th scope="col" class="end">사용자수</th>
				</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
					<tr>
						<td colspan="3" class="bllist"><spring:message code="message.no.list"/></td>
					</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt.AUTH_IDX}"/>
					<c:set var="dctNo" value="${listDt.USR_COUNT}"/>
					<c:set var="authIdx" value="is_authIdx"/>
					<c:choose>
						<c:when test="${listDt.SITE_ID == '1' && i.index == 0}">
							<tr>
								<th rowspan="${listDt.USR_COUNT }" class="bg01">사용자</th>
								<td><c:out value="${listDt.AUTH_NAME }"/></td>
								<td><strong class="point-color01"><c:out value="${listDt.AUTH_COUNT }"/></strong><a href="${URL_LIST }&${authIdx }=${listKey}" class="btn-linked"><img src="../images/icon/icon_search04.png" alt="" /></a></td>
							</tr>
						</c:when>
						<c:when test="${listDt.SITE_ID == '1'}">
							<tr>
								<td><c:out value="${listDt.AUTH_NAME }"/></td>
								<td><strong class="point-color01"><c:out value="${listDt.AUTH_COUNT }"/></strong><a href="${URL_LIST }&${authIdx }=${listKey}" class="btn-linked"><img src="../images/icon/icon_search04.png" alt="" /></a></td>
							</tr>
						</c:when>
						<c:when test="${listDt.SITE_ID == '2'  && i.index == dctNo}">
							<tr>
								<th rowspan="${listDt.DCT_COUNT }" class="bg01">관리자</th>
								<td><c:out value="${listDt.AUTH_NAME }"/></td>
								<td><strong class="point-color01"><c:out value="${listDt.AUTH_COUNT }"/></strong><a href="${URL_LIST }&${authIdx }=${listKey}" class="btn-linked"><img src="../images/icon/icon_search04.png" alt="" /></a></td>
							</tr>
						</c:when>
						<c:when test="${listDt.SITE_ID == '2' }">
							<tr>
								<td><c:out value="${listDt.AUTH_NAME }"/></td>
								<td><strong class="point-color01"><c:out value="${listDt.AUTH_COUNT }"/></strong><a href="${URL_LIST }&${authIdx }=${listKey}" class="btn-linked"><img src="../images/icon/icon_search04.png" alt="" /></a></td>
							</tr>
						</c:when>
					</c:choose>
					
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
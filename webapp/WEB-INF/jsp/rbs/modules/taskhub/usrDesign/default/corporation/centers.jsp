<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page }" />	
<c:set var="start" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
<c:set var="end" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<jsp:include page="../header.jsp" flush="false">
		<jsp:param name="usertype_idx" value="${usertype_idx }" />
		<jsp:param name="cName" value="corporation" />
	</jsp:include>
	<div class="tabmenu-wrapper01 depth6">
		<ul>
			<li style="width: 33.333%">
				<a href="${contextPath}/dct/taskhub/corp/corps.do?mId=48">기업</a>
			</li>
			<li style="width: 33.333%">
				<a href="${contextPath}/dct/taskhub/corp/centers.do?mId=48" class="active">민간 센터</a>
			</li>
			<li style="width: 33.333%">
				<a href="${contextPath}/dct/taskhub/corp/agreement.do?mId=48">협약</a>
			</li>
		</ul>
	</div>
	<div class="contents-area">
		<div class="table-type01 horizontal-scroll">
			<table>
				<caption>관할 센터표 : 연번, 센터명, 소재지, 연락처, 담당자, 협약 기업 수에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 10%">
					<col style="width: 26%">
					<col style="width: 15%">
					<col style="width: 20%">
					<col style="width: 15%">
					<col style="width: 14%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">연번</th>
						<th scope="col">센터명</th>
						<th scope="col">소재지</th>
						<th scope="col">연락처</th>
						<th scope="col">담당자</th>
						<th scope="col">협약 기업 수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="item">
					<tr>
						<td>${item.RN }</td>
						<td>${item.NAME }</td>
						<td>${item.LOCATION }</td>
						<td>${item.MOBILE_PHONE }</td>
						<td>${item.CHRGR_NM }</td>
						<td>
							<c:choose>
								<c:when test="${item.CNT > 0 }">
									<a href="${contextPath }/${siteId}/taskhub/corp/agreement.do?mId=48&sign=Y&center=${item.NAME}">${item.CNT }</a>
								</c:when>
								<c:otherwise>
									${item.CNT }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
		<!-- //CMS 끝 -->
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>

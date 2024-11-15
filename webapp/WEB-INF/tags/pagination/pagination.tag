<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="imgPath"%>																		<% /* 이전/다음 이미지 root 경로 */ %>
<%@ attribute name="listUrl"%>																		<% /* 페이징 목록 경로 */ %>
<%@ attribute name="pageName"%>																		<% /* 페이징 parameter명 */ %>
<%@ attribute name="pgInfo" type="egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>	<% /* 페이징 정보 */ %>
<%@ attribute name="usePaging"%>
<%@ attribute name="addParam"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${!empty addParam}"><c:set var="addParam" value="&${addParam}"/></c:if>
<c:if test="${empty usePaging}"><c:set var="usePaging" value="${settingInfo.use_paging}"/></c:if>

<c:if test="${!empty pgInfo and usePaging eq '1'}">
<c:set var="currentPageNo" value="${pgInfo.currentPageNo}"/>
<c:set var="totalPageCount" value="${pgInfo.totalPageCount}"/>
<c:set var="pageSize" value="${pgInfo.pageSize}"/>
<c:set var="firstPageNo" value="${pgInfo.firstPageNo}"/>
<c:set var="lastPageNo" value="${pgInfo.lastPageNo}"/>
<c:set var="firstPageNoOnPageList" value="${pgInfo.firstPageNoOnPageList}"/>
<c:set var="lastPageNoOnPageList" value="${pgInfo.lastPageNoOnPageList}"/>
<c:set var="listUrl" value="${listUrl}${addParam}"/>
<c:set var="defaultListUrl" value="${listUrl}&${pageName}="/>

	<c:if test="${totalPageCount > pageSize}">
		<c:choose>
		<c:when test="${firstPageNoOnPageList > pageSize}">
			<c:set var="prevPageNo" value="${firstPageNoOnPageList - 1}"/>
		</c:when>
		<c:otherwise>
			<c:set var="prevPageNo" value="${firstPageNo}"/>
		</c:otherwise>
		</c:choose>
		
	</c:if>
	
	<!-- 페이지네이션 구간 -->
	<p class="paging-navigation">
	<c:if test="${currentPageNo-1 ne 0}">
	<a href="<c:out value="${defaultListUrl}${firstPageNo}"/>" class="btn-first" title="처음 페이지"></a>
	<a href="<c:out value="${defaultListUrl}${currentPageNo-1}"/>" class="btn-preview" title="이전 페이지"></a>
	</c:if>
	<!-- 반복구간 -->
	<c:forEach var="pageNo" begin="${firstPageNoOnPageList}" end="${lastPageNoOnPageList}">
		<c:choose>
			<c:when test="${pageNo == currentPageNo}"><strong>${pageNo}</strong></c:when>
			<c:otherwise><a href="<c:out value="${defaultListUrl}${pageNo}"/>" title="${pageNo} 페이지">${pageNo}</a></c:otherwise>
		</c:choose>
	</c:forEach>
	<!-- 반복구간 -->
	<c:if test="${totalPageCount > currentPageNo}">
	<a href="<c:out value="${defaultListUrl}${currentPageNo+1}"/>" class="btn-next" title="다음 페이지"></a>
	<a href="<c:out value="${defaultListUrl}${lastPageNo}"/>" class="btn-last" title="끝 페이지"></a>
	</c:if>
	</p>
<!-- 페이지네이션 구간 -->

	<c:if test="${totalPageCount > pageSize}">
		<c:choose>
		<c:when test="${lastPageNoOnPageList < totalPageCount}">
			<c:set var="nextPageNo" value="${firstPageNoOnPageList + pageSize}"/>
		</c:when>
		<c:otherwise>
			<c:set var="nextPageNo" value="${lastPageNo}"/>
		</c:otherwise>
		</c:choose>
	</c:if>
</c:if>
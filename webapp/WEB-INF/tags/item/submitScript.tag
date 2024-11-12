<%@tag import="net.sf.json.JSONObject"%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="itemOrder" type="net.sf.json.JSONArray"%>
<%@ attribute name="items" type="net.sf.json.JSONObject"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="inputFlag" value="${inputTypeName}"/>
<c:if test="${empty inputFlag}"><c:set var="inputFlag" value="${submitType}"/></c:if>
<c:if test="${!empty items && !empty itemOrder}">
<c:set var="requiredName" value="required_${inputFlag}"/>
<c:set var="inputTypeItemName" value="${inputFlag}_type"/>
<c:forEach var="itemId" items="${itemOrder}">
	<c:set var="itemObj" value="${items[itemId]}"/>
	<c:set var="inputType" value="${itemObj[inputTypeItemName]}"/>
	<c:if test="${isAdmMode || inputType != 20}">
		<c:set var="formatType" value="${itemObj['format_type']}"/>
		<c:set var="objectType" value="${itemObj['object_type']}"/>
	<c:choose>
		<c:when test="${formatType == 4}">
		<%/* 주소 */%>
		<c:choose>
			<c:when test="${itemObj['search_type'] == 'map'}">
				<c:set var="addrMap" value="${itemObj['search_type']}"/>
			</c:when>
			<c:otherwise>
				<c:set var="address" value="1"/>
			</c:otherwise>
		</c:choose>
		</c:when>
		<c:when test="${formatType >= 5 && formatType <= 8 || formatType >= 15 && formatType <= 18}">
		<%/* 날짜 */%>
		<c:set var="calendar" value="1"/>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${objectType == 6 || objectType == 9}">
				<%/* file */%>
				<c:set var="file" value="1"/>
				</c:when>
				<c:when test="${objectType == 1}">
				<%/* textarea */%>
				<c:if test="${itemObj['editor'] == '1' && (itemObj['editor_utype'] == '1' || isAdmMode && (empty itemObj['editor_utype'] || itemObj['editor_utype'] == '0'))}">
				<%/* 네이버 스마트에디터 사용하는 경우 */%>
				<c:set var="editor" value="1"/>
				</c:if>
				</c:when>
				<c:otherwise>
				<%/* 그외 입력  */%>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	</c:if>
</c:forEach>
<c:if test="${!empty calendar}">
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/calendar.js"/>"></script>
</c:if>
<c:if test="${!empty addrMap}">
	<%/* daum 지도 API */%>
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/daumMap.js"/>"></script>
</c:if>
<c:if test="${!empty address}">
	<spring:message var="addrSearchType" code="Globals.address.search.type" text="0"/>
	<c:choose>
	<c:when test="${addrSearchType == '1'}">
		<%/* 공공데이터포털 새우편번호 도로명주소조회 서비스 */%>
		<script type="text/javascript" src="<c:out value="${contextPath}/include/js/epostAddr.js"/>"></script>
	</c:when>
	<c:when test="${addrSearchType == '2'}">
		<%/* www.juso.go.kr 도로명주소 API */%>
		<script type="text/javascript" src="<c:out value="${contextPath}/include/js/jusoAddr.js"/>"></script>
	</c:when>
	<c:otherwise>
		<%/* daum 우편번호 검색 도로명 주소 API */%>
		<script type="text/javascript" src="<c:out value="${contextPath}/include/js/daumAddr.js"/>"></script>
	</c:otherwise>
	</c:choose>
</c:if>
<c:if test="${!empty file}">
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/jquery.form.min.js"/>"></script>
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/fileCheck.js"/>"></script>
</c:if>
<c:if test="${editor == 1}">
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/editor/js/HuskyEZCreator.js"/>" charset="utf-8"></script>
</c:if>
</c:if>
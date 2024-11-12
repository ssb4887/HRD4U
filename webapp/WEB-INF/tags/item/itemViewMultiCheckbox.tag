<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="colspan" type="java.lang.Integer"%>
<%@ attribute name="itemId"%>
<%@ attribute name="id"%>
<%@ attribute name="name"%>
<%@ attribute name="objValList" type="java.util.List"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="objVal"%>
<%@ attribute name="defVal"%>
<%@ attribute name="objStyle"%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>
<%@ attribute name="addOrder" type="java.lang.Boolean"%>
<%@ attribute name="idxColumnId"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<%@ attribute name="optionInfo" type="java.util.List"%>
<c:if test="${empty addOrder}">
	<c:set var="addOrder" value="false"/>
</c:if>
<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
	<c:set var="inputFlag" value="${inputTypeName}"/>
	<c:if test="${empty inputFlag}"><c:set var="inputFlag" value="${submitType}"/></c:if>
	<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
	<c:set var="itemColumnId" value="${itemObj['column_id']}"/>
	<c:if test="${objVal == null}"><c:set var="objVal" value="${objDt[itemColumnId]}"/></c:if>
	<c:set var="requiredName" value="required_${inputFlag}"/>
	<c:set var="required" value="${itemObj[requiredName]}"/>
	<c:set var="optListKey" value="_class_${itemObj['master_code']}"/>
	<c:set var="optnOptList" value="${optnHashMap[optListKey]}"/>
	<c:if test="${empty objTitle}"><c:set var="objTitle" value="${elfn:getItemName(itemObj)}"/></c:if>
	<c:choose>
		<c:when test="${fn:contains(dt.PARENT_BLOCK_CD, ',')}">
			<c:set var="codeData" value="${fn:split(dt.PARENT_BLOCK_CD, ',')}"/>
		</c:when>
		<c:otherwise>
			<c:set var="codeData" value="${dt.PARENT_BLOCK_CD}"/>
		</c:otherwise>
	</c:choose>
	<c:set var="parentCodeData" value="${fn:split(objVal, ',')}"/>
	<c:forEach var="optionCode" items="${codeData}" varStatus="j">
		<c:forEach var="optnDt" items="${optnOptList}" varStatus="i">
			<c:if test="${optnDt.OPTION_CODE eq optionCode && optnDt.OPTION_LEVEL eq 1}">
				<dl>
					<dt><c:out value="${optnDt.OPTION_NAME}" /></dt>
					<dd id="gungu-${optionCode}"><itui:objectViewMultiCheckbox2 itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" defVal="${optnDt.OPTION_CODE}" objVal="${objVal}" optnHashMap="${optnHashMap}" downloadUrl="${downloadUrl}" classSplitStr="${classSplitStr}"/></dd>
				</dl>
			</c:if>
		</c:forEach>
	<%-- <c:forEach var="optnDt" items="${optnOptList}" varStatus="i">
		<c:choose>
			<c:when test="${optionCode ne '0'}">
				<c:if test="${optnDt.OPTION_CODE eq optionCode && optnDt.OPTION_LEVEL eq 1}">
					<dl>
						<dt><c:out value="${optnDt.OPTION_NAME}" /></dt>
						<dd><itui:objectViewMultiCheckbox2 itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" defVal="${optnDt.OPTION_CODE}" objVal="${objVal}" optnHashMap="${optnHashMap}" downloadUrl="${downloadUrl}" classSplitStr="${classSplitStr}"/></dd>
					</dl>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:forEach var="parentCode" items="${parentCodeData}" varStatus="k">
				<c:if test="${fn:length(parentCode) eq 2 && optnDt.OPTION_CODE eq parentCode}" >
					<dl>
						<dt><c:out value="${optnDt.OPTION_NAME}" /></dt>
						<dd><c:out value="전 지역" /></dd>
					</dl>
				</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</c:forEach> --%>
	</c:forEach>
</c:if>
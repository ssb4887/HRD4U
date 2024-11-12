<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="itemId"%>
<%@ attribute name="id"%>
<%@ attribute name="name"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="objTotalList" type="java.util.List"%>
<%@ attribute name="objOptionList" type="java.util.List"%>
<%@ attribute name="objVal"%>
<%@ attribute name="defVal"%>
<%@ attribute name="objStyle"%>
<%@ attribute name="objClass"%>
<%@ attribute name="submitType"%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>
<%@ attribute name="addOrder" type="java.lang.Boolean"%>
<%@ attribute name="idxColumnId"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<c:if test="${empty addOrder}">
	<c:set var="addOrder" value="false"/>
</c:if>
<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
	<c:set var="inputFlag" value="${inputTypeName}"/>
	<c:if test="${empty inputFlag}"><c:set var="inputFlag" value="${submitType}"/></c:if>
	<c:set var="itemName" value="${elfn:getItemName(itemObj)}"/>
	<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
	<c:if test="${empty optnHashMap}"><c:set var="optnHashMap" value="${optnHashMap}"/></c:if>
	<c:set var="itemColumnId" value="${itemObj['column_id']}"/>
	<c:if test="${objVal == null}"><c:set var="objVal" value="${objDt[itemColumnId]}"/></c:if>
	<c:set var="itemOptionType" value="${itemObj['option_type']}"/>
	<c:if test="${!empty idxColumnId}"><c:set var="objVal" value="${dt[idxColumnId]}"/></c:if>
	<c:if test="${empty defVal}"><c:set var="defVal" value="${itemObj['default_value']}"/></c:if>
	<c:set var="inputTypeItemName" value="${inputFlag}_type"/>
	<c:set var="inputType" value="${itemObj[inputTypeItemName]}"/>
	<c:set var="requiredName" value="required_${inputFlag}"/>
	<c:set var="required" value="${itemObj[requiredName]}"/>
	<c:if test="${empty name}"><c:set var="name" value="${itemId}"/></c:if>
	<c:if test="${empty id}"><c:set var="id" value="${itemId}"/></c:if>
	<c:set var="optMaxLevelKey" value="_class_${itemObj['master_code']}_max_level"/>
	<c:set var="optListKey" value="_class_${itemObj['master_code']}"/>
	<c:set var="optnOptList" value="${optnHashMap[optListKey]}"/>
	<c:set var="optMaxLevelDt" value="${optnHashMap[optMaxLevelKey]}"/>
	<c:set var="optMaxLevel" value="${optMaxLevelDt.MAX_LEVEL}"/>
	<c:set var="className" value=""/>
	<c:forEach var="optionDt" items="${objOptionList}" varStatus="k">
		<c:if test="${optionDt.PARENT_CLASS_IDX eq id}">
			<c:choose>
				<c:when test="${!empty objDt && submitType eq 'modify'}">
					<c:choose>
						<c:when test="${fn:contains(objDt.BLOCK_CD, optionDt.CLASS_IDX)}"><c:set var="className" value=""/></c:when>
						<c:when test="${optionDt.ISBLOCK eq '1'}"><c:set var="className" value="checkDisable"/></c:when>
						<c:otherwise><c:set var="className" value=""/></c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="${optionDt.ISBLOCK eq '1'}"><c:set var="className" value="checkDisable"/></c:if>
				</c:otherwise>
			</c:choose>
			<div class="input-checkbox-area">
				<input type="checkbox" name="${itemId}" id="${itemId}_child_${optionDt.CLASS_IDX}" class="${objClass} margin10 ${className}" value="${optionDt.CLASS_IDX}" data-pcode="${optionDt.PARENT_CLASS_IDX}" data-level="${optionDt.CLASS_LEVEL}" <c:if test="${fn:contains(objDt.BLOCK_CD, optionDt.CLASS_IDX)}"> checked = "checked" </c:if>>
				<label for="${itemId}_child_${optionDt.CLASS_IDX}"><c:out value="${optionDt.CLASS_NAME}"/></label>
			</div>
		</c:if>
		<c:set var="className" value=""/>
	</c:forEach>
</c:if>
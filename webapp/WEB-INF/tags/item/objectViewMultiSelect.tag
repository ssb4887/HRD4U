<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="itemId"%>
<%@ attribute name="id"%>
<%@ attribute name="name"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="objVal"%>
<%@ attribute name="defVal"%>
<%@ attribute name="objStyle"%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>
<%@ attribute name="addOrder" type="java.lang.Boolean"%>
<%@ attribute name="idxColumnId"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<%@ attribute name="industList" type="java.util.ArrayList"%>
<%@ attribute name="prtbizList" type="com.woowonsoft.egovframework.form.DataMap"%>
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
	<c:set var="applyIndustIdxs" value="${fn:split(dt.APPLY_INDUTY, ',') }" />
	<c:choose>
		<c:when test="${applyIndustIdxs[0] eq 'AA' || applyIndustIdxs[0] eq 'ZZ'}" >
			<c:choose>
				<c:when test="${applyIndustIdxs[0] eq 'AA'}" >
					<c:out value="전 업종" />
				</c:when>
				<c:otherwise>
					<c:out value="해당 없음" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:forEach var="industDt" items="${industList}" varStatus="k">
				<c:if test="${industDt.LCLAS eq applyIndustIdxs[0] && industDt.OPTION_CODE eq applyIndustIdxs[1]}" >
					<c:out value="${industDt.OPTION_NAME}" />
				</c:if>
			</c:forEach>	
		</c:otherwise>
	</c:choose>
	
</c:if>
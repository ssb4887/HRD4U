<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="itemId"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="defVal"%>
<%@ attribute name="objVal" type="java.lang.Object"%>
<%@ attribute name="objNameVal" type="java.lang.Object"%>
<%@ attribute name="isDisplayImg" type="java.lang.Boolean"%>	<%/* 이미지 파일인 경우 display 여부 */%>
<%@ attribute name="imgDisplayType" type="java.lang.Integer"%>	<%/* 이미지항목인 경우 display방식: null,0 - 이미지만 / 1 - 링크만 / 2 - 이미지,링크 둘 다 display */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>
<%@ attribute name="multiFileHashMap" type="java.util.HashMap"%>
<%@ attribute name="multiDataHashMap" type="java.util.HashMap"%>
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
<c:set var="optListKey" value="_class_NCS_CODE"/>
<c:set var="optnOptList" value="${optnHashMap[optListKey]}"/>
<c:set var="optMaxLevelDt" value="${optnHashMap[optMaxLevelKey]}"/>
<c:set var="optMaxLevel" value="${optMaxLevelDt.MAX_LEVEL}"/>
<c:forEach var="optnDt" items="${optnOptList}" varStatus="i">
	<c:if test="${optnDt.OPTION_CODE eq objDt.NCS_SCLAS_CODE}">
		<c:out value="${optnDt.OPTION_NAME}" />(<c:out value="${optnDt.OPTION_CODE}" />)
	</c:if>
</c:forEach>

<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="thColspan" type="java.lang.Integer"%>
<%@ attribute name="colspan" type="java.lang.Integer"%>
<%@ attribute name="itemId"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="objVal"%>
<%@ attribute name="defVal"%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>
<%@ attribute name="objOptionStyle"%>
<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
	<c:set var="inputFlag" value="${inputTypeName}"/>
	<c:if test="${empty inputFlag}"><c:set var="inputFlag" value="${submitType}"/></c:if>
	<c:set var="itemColumnId" value="${itemObj['column_id']}"/>
	<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
	<c:if test="${objVal == null}"><c:set var="objVal" value="${objDt[itemColumnId]}"/></c:if>
	<c:set var="requiredName" value="required_${inputFlag}"/>
	<c:set var="required" value="${itemObj[requiredName]}"/>
<dt<c:if test="${thColspan > 0}"> colspan="${thColspan}"</c:if>><itui:objectLabelForArea itemId="${itemId}" nextItemId="1" required="${required}" itemInfo="${itemInfo}"/></dt>
<dd<c:if test="${colspan > 0}"> colspan="${colspan}"</c:if>>
	<div class="input-checkbox-wrapper ratio">
		<itui:objectCheckboxArea itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" defVal="${defVal}" objVal="${objVal}" optnHashMap="${optnHashMap}" objOptionStyle="${objOptionStyle}"/>
		<c:if test="${!empty itemObj['comment']}"><span class="comment"><c:out value="${itemObj['comment']}" escapeXml="false"/></span></c:if>
	</div>
</dd>
</c:if>
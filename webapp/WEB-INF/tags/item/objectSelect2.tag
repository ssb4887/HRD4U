<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="itemId"%>
<%@ attribute name="name"%>
<%@ attribute name="id"%>
<%@ attribute name="optnOptList" type="java.util.List"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="objVal"%>
<%@ attribute name="defVal"%>
<%@ attribute name="minDefVal" type="java.lang.Integer" %>
<%@ attribute name="objNameVal"%>
<%@ attribute name="objStyle"%>
<%@ attribute name="objClass"%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>
<%@ attribute name="addOrder" type="java.lang.Boolean"%>
<%@ attribute name="idxColumnId"%>
<%@ attribute name="itemRequried"%>
<%@ attribute name="itemOptBlankTitle" %>
<%@ attribute name="disabled" type="java.lang.Boolean"%>
<%@ attribute name="disabledId" type="java.lang.Boolean"%>
<%@ attribute name="limitCnt" type="java.lang.Integer"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<%@ attribute name="idx"%>
<c:if test="${empty addOrder}">
	<c:set var="addOrder" value="false"/>
</c:if>
<c:if test="${empty disabled}">
	<c:set var="disabled" value="false"/>
</c:if>
<c:if test="${empty disabledId}">
	<c:set var="disabledId" value="false"/>
</c:if>
<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
	<c:set var="inputFlag" value="${inputTypeName}"/>
	<c:if test="${empty inputFlag}"><c:set var="inputFlag" value="${submitType}"/></c:if>
	<c:set var="itemName" value="${elfn:getItemName(itemObj)}"/>
	<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
	<c:if test="${empty optnHashMap}"><c:set var="optnHashMap" value="${optnHashMap}"/></c:if>
	<c:set var="itemColumnId" value="${itemObj['column_id']}"/>
	<c:set var="nameColumnId" value="${itemObj['name_column_id']}"/>
	<c:if test="${empty nameColumnId}"><c:set var="nameColumnId" value="${itemObj['column_id']}_NAME"/></c:if>
	<c:if test="${objVal == null}"><c:set var="objVal" value="${objDt[itemColumnId]}"/></c:if>
	<c:if test="${objNameVal == null}"><c:set var="objNameVal" value="${objDt[nameColumnId]}"/></c:if>
	<c:set var="itemObjectType" value="${itemObj['object_type']}"/>
	<c:set var="itemOptionType" value="${itemObj['option_type']}"/>
	<c:if test="${!empty idxColumnId}"><c:set var="objVal" value="${dt[idxColumnId]}"/></c:if>
	<c:if test="${empty defVal}"><c:set var="defVal" value="${itemObj['default_value']}"/></c:if>
	<c:set var="inputTypeItemName" value="${inputFlag}_type"/>
	<c:set var="inputType" value="${itemObj[inputTypeItemName]}"/>
	<c:set var="optionEtc" value="${itemObj['option_etc']}"/>
	<c:set var="prtbizIdxList" value="${fn:split(objDt.PRTBIZ_IDX_LIST, ',')}" />
	<c:set var="prtbizNameList" value="${fn:split(objDt.PRTBIZ_NAME_LIST, ',')}" />
	<c:choose>
		<c:when test="${objVal eq 'view'}">
			<c:forEach var="prtbizIdx" items="${prtbizIdxList}" varStatus="i">
				<c:if test="${prtbizIdx eq dt.PRTBIZ_IDX}">
					<c:out value="${prtbizNameList[i.index]}" />
				</c:if>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<select name="${itemId}" id="${itemId}" class="select ${objClass}" title="${itemName}">
				<option value>참여가능사업 선택</option>
				<c:forEach var="prtbizIdx" items="${prtbizIdxList}" varStatus="i">
					<option value="${prtbizIdx}" <c:if test="${(objVal eq 'modify' && prtbizIdx eq dt.PRTBIZ_IDX) || (objVal eq 'write' && !empty idx && prtbizIdx eq idx)}"> selected </c:if>><c:out value="${prtbizNameList[i.index]}" /></option>
				</c:forEach>
			</select>
		</c:otherwise>
	</c:choose>
</c:if>
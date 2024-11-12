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
<%@ attribute name="objListDt" type="java.util.List"%>
<%@ attribute name="downloadUrl"%>
<%@ attribute name="classSplitStr"%>								<%/* 분류 구분자 */%>
<%@ attribute name="imgDefaultUrl"%>										<%/* 이미지기본경로 */%>
<c:if test="${empty isDisplayImg}">
	<c:set var="isDisplayImg" value="false"/>
</c:if>

<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
<c:if test="${empty optnHashMap}"><c:set var="optnHashMap" value="${optnHashMap}"/></c:if>
<c:set var="itemFormatType" value="${itemObj['format_type']}"/>
<c:set var="itemObjectType" value="${itemObj['object_type']}"/>
<c:set var="itemColumnId" value="${itemObj['column_id']}"/>
<c:set var="nameColumnId" value="${itemObj['name_column_id']}"/>
<c:if test="${empty nameColumnId}"><c:set var="nameColumnId" value="${itemObj['column_id']}_NAME"/></c:if>
<c:set var="usePrivSec" value="${itemObj['use_privsec']}"/>
<c:if test="${objVal == null}"><c:set var="objVal" value="${objDt[itemColumnId]}"/></c:if>
<c:if test="${objNameVal == null}"><c:set var="objNameVal" value="${objDt[nameColumnId]}"/></c:if>
<c:if test="${!empty objVal && usePrivSec == 1}"><c:set var="objVal" value="${elfn:privDecrypt(objVal)}"/></c:if>
<c:set var="optListKey" value="_class_${itemObj['master_code']}"/>
<c:set var="optnOptList" value="${optnHashMap[optListKey]}"/>
<c:set var="codeData" value="${fn:split(objVal, ',')}"/>
<c:set var="loop" value="false"/>
<c:forEach var="optionCode" items="${objListDt}" varStatus="j">
	<c:if test="${optionCode.INSTT_IDX eq defVal}">
		<c:set var="codeData" value="${fn:split(optionCode.BLOCK_CODE, ',')}"/>
		<c:choose>
			<c:when test="${optionCode.ALLSELECTEDFLAG eq 1}">
				<c:forEach var="optnDt" items="${optnOptList}" varStatus="i">
					<c:if test="${not loop}">
						<c:if test="${optnDt.OPTION_CODE eq codeData[0]}">
							<c:out value="${optnDt.OPTION_NAME} 전 지역"/>&nbsp;<c:set var="loop" value="true"/>
							<input type="hidden" name="allSelectedName" id="allSelectedName${optnDt.OPTION_CODE}" value="${optnDt.OPTION_NAME}" data-pcode="${optnDt.OPTION_CODE}"/>
						</c:if>
					</c:if>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach var="blockCode" items="${codeData}" varStatus="k">
					<c:forEach var="optnDt" items="${optnOptList}" varStatus="i">
							<c:if test="${optnDt.OPTION_CODE eq blockCode}"><c:out value="${optnDt.OPTION_NAME}"/>&nbsp;</c:if>
					</c:forEach>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<br/>
	</c:if>
</c:forEach>
</c:if>
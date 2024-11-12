<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="itemId"%>
<%@ attribute name="objVal"%>
<%@ attribute name="objStyle"%>
<%@ attribute name="textStyle"%>
<%@ attribute name="ulClass"%>
<%@ attribute name="objClass"%>
<%@ attribute name="selectClass"%>
<%@ attribute name="btnId"%>
<%@ attribute name="btnClass"%>
<%@ attribute name="fileList" type="java.util.List"%>
<%@ attribute name="downLink" type="java.lang.Boolean"%>
<%@ attribute name="inputTypeName"%>											<%/* inputType, required 입력구분 (write,modify) */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<c:if test="${empty downLink}">
	<c:set var="downLink" value="false"/>
</c:if>
<% /* 다중 파일 */ %>
<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
	<c:set var="inputFlag" value="${inputTypeName}"/>
	<c:if test="${empty inputFlag}"><c:set var="inputFlag" value="${submitType}"/></c:if>
	<c:set var="itemName" value="${elfn:getItemName(itemObj)}"/>
	<c:if test="${fileList == null}"><c:set var="fileList" value="${multiFileHashMap[itemId]}"/></c:if>
	<c:set var="itemColumnId" value="${itemObj['column_id']}"/>

	<c:if test="${empty selectClass}"><c:set var="selectClass" value="selectMultiFile"/></c:if>
	<c:if test="${empty objClass}"><c:set var="objClass" value="inputTxt"/></c:if>
	
	<c:if test="${empty btnId}"><c:set var="btnId" value="fn_btn_${itemId}"/></c:if>
	
	<c:set var="requiredName" value="required_${inputFlag}"/>
	<c:set var="required" value="${itemObj[requiredName]}"/>

	<c:set var="minimum" value="${itemObj['minimum']}"/>
	<c:set var="maximum" value="${itemObj['maximum']}"/>
	<c:if test="${empty maximum || maximum == 0}"><spring:message var="maximum" code="Globals.upload.file.maximum"/></c:if>
	<c:choose>
		<c:when test="${!empty minimum && minimum > 0}">
			<spring:message var="fileCntText" code="item.file.attach.min.max" arguments="${minimum},${maximum}" argumentSeparator=","/>
		</c:when>
		<c:otherwise>
			<spring:message var="fileCntText" code="item.file.attach.max" arguments="${maximum}"/>
		</c:otherwise>
	</c:choose>
	<c:if test="${!empty fileList}">
		<c:set var="keyItemId" value="${settingInfo.idx_name}"/>
		<c:set var="keyColumnId" value="${settingInfo.idx_column}"/>
		<tr>
			<th scope="row">첨부파일</th>
			<td class="left">
				<c:forEach var="optnDt" items="${fileList}" varStatus="i">
					<li><a href="reportDownload.do?mId=100&${keyItemId}=<c:out value="${optnDt[keyColumnId]}"/>&fidx=<c:out value="${optnDt.FLE_IDX}"/>&itId=<c:out value="${itemId}"/>"><img src="../images/common/ico_file.gif" alt="파일"><c:out value="${optnDt.FILE_ORIGIN_NAME}"/></a></li>
				</c:forEach>
			</td>
		</tr>
	</c:if>
	<c:if test="${empty fileList}">
		<tr>
			<th scope="row">첨부파일</th>
			<td class="left">
				첨부파일이 존재하지 않습니다.
			</td>
		</tr>
	</c:if>
	<input type="hidden" id="${itemId}_deleted_idxs" name="${itemId}_deleted_idxs"/>
</c:if>
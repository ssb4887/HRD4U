<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="colspan" type="java.lang.Integer"%>
<%@ attribute name="itemId"%>
<%@ attribute name="id"%>
<%@ attribute name="name"%>
<%@ attribute name="objValList" type="java.util.List"%>
<%@ attribute name="objTotalList" type="java.util.List"%>
<%@ attribute name="objOptionList" type="java.util.List"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
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
<c:if test="${!empty objOptionList}">
	<c:forEach var="optionDt" items="${objOptionList}" varStatus="i">
		<c:if test="${optionDt.CLASS_LEVEL eq 1}">
			<div class="one-box">
				<dl>
					<dt>
						<input type="checkbox" id="${itemId}_master_${optionDt.CLASS_IDX}" class="${objClass} codeLevel <c:if test="${optionDt.ISBLOCK eq 1}"> checkDisable</c:if>" data-code="${optionDt.CLASS_IDX}" data-pcode="${optionDt.PARENT_CLASS_IDX}" data-level="${optionDt.CLASS_LEVEL}">
						<label for="${itemId}_master_${optionDt.CLASS_IDX}"><c:out value="${optionDt.CLASS_NAME}" /></label>
					</dt>
					<dd>
						<div id="${itemId}_dmaster_${optionDt.CLASS_IDX}" class="input-checkbox-wrapper ratio type02"><itui:objectMultiCheckbox itemId="${itemId}" id="${optionDt.CLASS_IDX}" name="${name}" itemInfo="${itemInfo}" objClass="${objClass}" objDt="${objDt}" objVal="${objVal}" optnHashMap="${optnHashMap}" objTotalList="${objTotalList}" objOptionList="${objOptionList}"  submitType="${submitType}"/></div>
					</dd>
				</dl>
			</div>
		</c:if>
	</c:forEach>
</c:if>
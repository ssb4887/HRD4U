<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="colspan" type="java.lang.Integer"%>
<%@ attribute name="itemId"%>													<%/* 항목ID */%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>	<%/* 저장 값 */%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<%@ attribute name="optnHashMap" type="java.util.HashMap"%>						<%/* 코드값 정보 */%>
<%@ attribute name="multiFileHashMap" type="java.util.HashMap"%>				<%/* multi파일값 정보 */%>
<%@ attribute name="itemLangList" type="java.util.List"%>						<%/* 언어목록 */%>
<%@ attribute name="inputTypeName"%>											<%/* 페이지구분 (write,modify) */%>
<% /* 항목(th,td) 설정별 출력 */ %>
<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
<c:if test="${!empty itemObj}">
	<c:if test="${empty inputTypeName}">
		<c:set var="inputTypeName" value="${submitType}_type"/>
	</c:if>
	<c:set var="inputType" value="${itemObj[inputTypeName]}"/>
	<c:if test="${isAdmMode || inputType != 20}">
	<%/* 관리시스템  || 관리시스템에서만 저장하는 항목 아닌 경우 */ %>
		<c:set var="itemFormatType" value="${itemObj['format_type']}"/>
		<c:set var="itemObjectType" value="${itemObj['object_type']}"/>
		<c:set var="itemColumnId" value="${itemObj['column_id']}"/>
		<c:set var="itemObjStyle" value="width:100%;"/>
		<c:set var="itemObjClass" value="${itemObj['object_class']}"/>				<%/* 현재 미사용 - 추 후 사용가능성 */%>
		<c:set var="itemMaximum" value="${itemObj['maximum']}"/>
		
		<c:set var="requiredName" value="required_${submitType}"/>
		<c:set var="required" value="${itemObj[requiredName]}"/>
		<c:set var="labelForId" value="${itemId}"/>
		<c:set var="labelId" value=""/>
		<c:choose>
			<c:when test="${inputType == 10 && (empty itemFormatType ||itemFormatType == 0) && (itemObjectType == 8 || itemObjectType == 98)}">
				<%/* text + button 출력만하는 경우 */%>
				<c:set var="labelForId" value="${itemId}_name"/>
				<c:set var="labelId" value=" id='fn_name_${itemId}'"/>
				<c:set var="required" value="0"/>
			</c:when>
			<c:when test="${inputType == 10}">
				<%/* 출력만하는 경우 */%>
				<c:set var="labelForId" value="${itemId}"/>
				<c:set var="required" value="0"/>
			</c:when>
			<c:when test="${itemFormatType == 1 || itemFormatType == 2 || itemFormatType == 4 || itemFormatType == 6 || itemFormatType == 7 || itemFormatType == 8 || itemFormatType == 20 || itemFormatType == 21 || itemFormatType == 22 || ((empty itemFormatType ||itemFormatType == 0) && (itemObjectType == 4 || itemObjectType == 5))}">
				<c:set var="labelForId" value="${itemId}1"/>
			</c:when>
			<c:when test="${itemFormatType == 15 || itemFormatType == 16 || itemFormatType == 17 || itemFormatType == 18 || itemFormatType == 20 || itemFormatType == 30 || itemFormatType == 61}">
				<c:set var="labelForId" value="${itemId}11"/>
			</c:when>
		</c:choose>
		<dt><label for="${labelForId}"${labelId}<c:if test="${required == 1}"> class="required"</c:if>><itui:objectItemName itemId="${itemId}" itemInfo="${itemInfo}"/><c:if test="${!isAdmMode && required == 1}"><em class="point-important">*</em></c:if></label></dt>
		<dd<c:if test="${colspan > 0}"> colspan="${colspan}"</c:if>>
		<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
		<c:if test="${empty optnHashMap}"><c:set var="optnHashMap" value="${optnHashMap}"/></c:if>
		<c:choose>
			<c:when test="${!empty itemObj.option_type && itemObj.option_type != 0}"><c:set var="optnOptList" value="${optnHashMap[itemId]}"/></c:when>
			<c:otherwise><c:set var="optnOptList" value="${optnHashMap[itemObj['master_code']]}"/></c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${itemFormatType == 1 || itemFormatType == 2}">
				<%/* 전화번호, 휴대폰 */%>
				<c:set var="itemObjType" value="${(1 + itemFormatType) % 2}"/>
				<itui:objectPHONE itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objType="${itemObjType}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 3}">
				<%/* 이메일 */%>
				<itui:objectEMAIL itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle3="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 4}">
				<%/* 주소 */%>
				<itui:objectADDR itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle3="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 12}">
				<%/* 사업자번호 */%>
				<itui:objectBUSINUMB itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 13}">
				<%/* 법인번호 */%>
				<itui:objectCORPNUMB itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 5}">
				<%/* 날짜 */%>
				<itui:objectYMD itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 7}">
				<%/* 날짜 시:분 */%>
				<itui:objectYMDHM itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 8}">
				<%/* 날짜 시 */%>
				<itui:objectYMDH itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 15}">
				<%/* 날짜(기간) */%>
				<itui:objectPYMD itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 17}">
				<%/* 날짜 시:분(기간) */%>
				<itui:objectPYMDHM itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 18}">
				<%/* 날짜 시(기간) */%>
				<itui:objectPYMDH itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 19}">
				<%/* 년도 */%>
				<itui:objectYEAR itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 20}">
				<%/* 년도 월 */%>
				<itui:objectYEARM itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 21}">
				<%/* 년도 분기 */%>
				<itui:objectYEARQ itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 22}">
				<%/* 년도 월 일 */%>
				<itui:objectYEARMD itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 29}">
				<%/* 년도(기간) */%>
				<itui:objectPYEAR itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 30}">
				<%/* 년도 월(기간) */%>
				<itui:objectPYEARM itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${itemFormatType == 61}">
				<%/* 시:분(기간) */%>
				<itui:objectPHM itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
			</c:when>
			<c:when test="${empty itemFormatType || itemFormatType == 0}">
				<%/* 일반 : object_type 검사 */%>
				<c:choose>
				<c:when test="${itemObjectType == 2}">
					<%/* select */%>
					<itui:objectSelect itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" optnHashMap="${optnHashMap}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:when test="${itemObjectType == 3}">
					<%/* select multi - 진행중... */%>
					<itui:objectMultiSelect itemId="${itemId}" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:when test="${itemObjectType == 22}">
					<%/* select(3차) */%>
					<itui:objectSelectClass itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" optnHashMap="${optnHashMap}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:when test="${itemObjectType == 14}">
					<%/* checkbox(단일) */%>
					<itui:objectCheckboxSG itemId="${itemId}" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}"/>
				</c:when>
				<c:when test="${itemObjectType == 4}">
					<%/* checkbox */%>
					<itui:objectCheckbox itemId="${itemId}" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}"/>
				</c:when>
				<c:when test="${itemObjectType == 5}">
					<%/* radio */%>
					<itui:objectRadio itemId="${itemId}" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}"/>
				</c:when>
				<c:when test="${itemObjectType == 8 || itemObjectType == 98}">
					<%/* text + button */%>
					<itui:objectTextButton itemId="${itemId}" itemInfo="${itemInfo}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:when test="${itemObjectType == 6}">
					<%/* file */%>
					<itui:objectFile itemId="${itemId}" itemInfo="${itemInfo}" objStyle="${itemObjStyle}" downLink="${isAdmMode}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:when test="${itemObjectType == 9}">
					<%/* multi file */%>
					<itui:objectMultiFileForDct itemId="${itemId}" itemInfo="${itemInfo}" objStyle="${itemObjStyle}" downLink="${isAdmMode}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:when test="${itemObjectType == 1}">
					<%/* textarea */%>
					<itui:objectTextarea itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
				</c:when>
				<c:otherwise>
					<%/* text, text(암/복호화) */%>
					<c:if test="${empty itemObjClass && itemMaximum >= 100}"><c:set var="itemObjClass" value="wFull"/></c:if>
					<itui:objectText itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" itemLangList="${itemLangList}" objClass="${itemObjClass}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
					<c:if test="${!empty itemObj['comment']}"><span class="comment"><c:out value="${itemObj['comment']}" escapeXml="false"/></span></c:if>
				</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%/* text */%>
				<c:if test="${empty itemObjClass && itemMaximum >= 100 || itemFormatType == 31}"><c:set var="itemObjClass" value="wFull"/></c:if>
				<itui:objectText itemId="${itemId}" itemInfo="${itemInfo}" objDt="${objDt}" itemLangList="${itemLangList}" objClass="${itemObjClass}" objStyle="${itemObjStyle}" inputTypeName="${inputFlag}"/>
				<c:if test="${empty itemObj['comment'] && itemFormatType == 31}"><span class="comment"><spring:message code="item.module.input.format_type.url_link"/></span></c:if>
				<c:if test="${!empty itemObj['comment']}"><span class="comment"><c:out value="${itemObj['comment']}" escapeXml="false"/></span></c:if>
			</c:otherwise>
		</c:choose>
		</dd>
	</c:if>
</c:if>
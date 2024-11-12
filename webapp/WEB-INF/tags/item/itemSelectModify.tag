<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="colspan" type="java.lang.Integer"%>
<%@ attribute name="itemId"%>
<%@ attribute name="objDt" type="java.util.ArrayList"%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>					<%/* 항목설정정보 */%>
<%@ attribute name="selectYear"%>
<%@ attribute name="itemList" type="java.util.ArrayList"%>
<%@ attribute name="moduleId"%>
<%@ attribute name="indutyCd"%>
<%@ attribute name="year"%>
<c:if test="${!empty objDt}">
	<select id="year_tmp" name="year_tmp" class="select" style="display:none;">
		<c:forEach var="inYearDt" items="${objDt}" varStatus="i">
			<option value="<c:out value="${inYearDt.YEAR}"/>" data-industCd="${inYearDt.INDUTY_CD}"><c:out value="${inYearDt.YEAR}"/></option>
		</c:forEach>
	</select>
</c:if>
<tr>
	<th scope="col"><itui:objectItemName itemId="indutyCode" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></th>
	<td <c:if test="${!empty moduleId && moduleId eq 'traing'}">colspan="2"</c:if>>
		<select id="${itemId}" name="${itemId}" class="mt10 w100">
			<option value="">업종 선택</option>
			<c:forEach var="optnDt" items="${itemList}" varStatus="i">
				<option value="<c:out value="${optnDt.INDUTY_CD}"/>" <c:if test="${!empty indutyCd && optnDt.INDUTY_CD eq indutyCd}">selected</c:if>>${optnDt.LCLAS_NM}</option>
			</c:forEach>
		</select>
	</td>
</tr>
<tr>
	<th scope="col"><itui:objectItemName itemId="year" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></th>
	<td <c:if test="${!empty moduleId && moduleId eq 'traing'}">colspan="2"</c:if>>
		<select id="${selectYear}" name="${selectYear}" class="mt10 w100">
			<option value="" data-pcode="00">연도 선택</option>
		</select>
	</td>
</tr>
<script>
$(function(){
	var itemId = "${itemId}";
	var theId = "${selectYear}"; 
	
	// 상세보기 화면에서 수정으로 넘어올 때 year세팅
	var indutyCd = "${indutyCd}";
	var year = "${year}";
	if(indutyCd != "" && year != ""){
		var varClassIdx = $("#" + itemId).find("option:selected").val();
		var varClassOpts = $("#" + theId + "_tmp option[data-industCd='" + varClassIdx + "']");
		$("#" + theId).append(varClassOpts.clone());
	}
	
	// 3차 select option 초기 setting
	$("#" + itemId).change(function(){
		// 업종 선택 변동에 따른 연도 setting
		$("#" + theId + " option:gt(0)").remove();
		var varClassIdx = $("#" + itemId).find("option:selected").val();
		var varClassOpts = $("#" + theId + "_tmp option[data-industCd='" + varClassIdx + "']");
		$("#" + theId).append(varClassOpts.clone());
	});
});
</script>

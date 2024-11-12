<%@tag import="java.util.Calendar"%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ attribute name="itemListSearch" type="net.sf.json.JSONObject"%>
<%@ attribute name="searchOptnHashMap" type="java.util.HashMap"%>
<%@ attribute name="selectClass"%>
<%@ attribute name="idx"%>
<spring:message var="itemSearchPreFlag" code="Globals.item.search.pre.flag"/>
<c:if test="${!empty itemListSearch}">
	<c:set var="itemSearchOrder" value="${itemListSearch.searchForm_order}"/>
	<c:if test="${!empty itemSearchOrder}">
	<c:set var="keyItemName" value="key"/>
	<c:set var="keyInfo" value="${itemListSearch[keyItemName]}"/>
		<c:forEach var="itemSearchId" items="${itemSearchOrder}">
			<c:if test="${!empty keyInfo && !empty keyInfo.items}">
				<!-- 단일 -->
				<c:set var="keyItem" value="${keyInfo.items[itemSearchId]}"/>
				<c:if test="${!empty keyItem}">
				<c:set var="itemKeyId" value="${itemSearchPreFlag}${itemSearchId}"/>
				<c:set var="itemKeyName" value="${elfn:getItemName(keyItem)}"/>
				<c:set var="namePt" value=""/>
				<c:set var="mergeOrder" value=""/>
				<c:set var="formatType" value="${keyItem.format_type}"/>
				<c:set var="objectType" value="${keyItem.object_type}"/>
				<c:set var="optionType" value="${keyItem.option_type}"/>
				<% /* 3차 select */ %>
					<c:set var="optMaxLevelKey" value="_class_${keyItem['master_code']}_max_level"/>
					<c:set var="optListKey" value="_class_${keyItem['master_code']}"/>
					<c:set var="optMaxLevelDt" value="${searchOptnHashMap[optMaxLevelKey]}"/>
					<c:set var="optMaxLevel" value="${optMaxLevelDt.MAX_LEVEL}"/>
					<c:if test="${optMaxLevel >= 1 && keyItem.option_split == 1}">
					<c:set var="optionTitle" value="${keyItem['option_title']}"/>
					<c:set var="optionTitleLen" value="${0}"/>
					<c:if test="${!empty optionTitle}">
						<c:set var="optionTitles" value="${fn:split(optionTitle, ',')}"/>
						<c:set var="optionTitleLen" value="${fn:length(optionTitles)}"/>
					</c:if>
					<c:forEach var="levelIdx" begin="1" end="${optMaxLevel}">
						<c:choose>
							<c:when test="${!empty optionTitles && optionTitleLen >= levelIdx}">
								<spring:message var="itemTitle" code="item.class.custom.select.title.name" arguments="${optionTitles[levelIdx - 1]}"/>
								<spring:message var="itemOptTitle" code="item.class.custom.select.name" arguments="${optionTitles[levelIdx - 1]}"/>
							</c:when>
							<c:otherwise>
								<spring:message var="itemTitle" code="item.class.select.title.name" arguments="${levelIdx},${itemKeyName}" argumentSeparator=","/>
								<spring:message var="itemOptTitle" code="item.class.select.name" arguments="${levelIdx},${itemKeyName}"/>
							</c:otherwise>
						</c:choose>
						<select id="${itemKeyId}${levelIdx}_${idx}" name="${itemKeyId}_tmp" title="${itemTitle}" style="${objStyle}" class="mt10 two-select ${selectClass} <c:if test="${levelIdx eq 1}"> codeLevel</c:if>"<c:if test="${required == 1}"> required="required"</c:if>>
							<option value="">${itemOptTitle}</option>
						</select>
					</c:forEach>
					</c:if>
				<c:set var="optnOptList" value="${searchOptnHashMap[optListKey]}"/>
				<%-- <select name="${itemKeyId}" id="${itemKeyId}" class="select t_select_full" title="${itemKeyName}"<c:if test="${!empty keyItem.style_width}"> style="width:${keyItem.style_width}px;"</c:if>>--%>
				<select name="${itemKeyId}" id="${itemKeyId}_${idx}" class="select t_select_full<c:if test="${keyItem.option_split == 1}"> fn_search_select_class</c:if>" title="${itemKeyName}"<c:choose><c:when test="${keyItem.option_split == 1}"> style="display:none;" data-max="${optMaxLevel}"</c:when><c:when test="${!empty keyItem.style_width}"> style="width:${keyItem.style_width}px;"</c:when></c:choose>>
					<option value="">${itemKeyName} 선택</option>
					<c:forEach var="optnDt" items="${optnOptList}">
					<option value="${optnDt.OPTION_CODE}" data-pcode="${optnDt.PARENT_OPTION_CODE}" data-level="${optnDt.OPTION_LEVEL}"<c:if test="${!empty optnDt.P_OPT_CD}"> data-p="<c:out value="${optnDt.P_OPT_CD}"/>"</c:if><c:if test="${queryString[itemKeyId] == optnDt.OPTION_CODE}"> selected="selected"</c:if>><c:if test="${optnDt.OPTION_LEVEL > 1 && keyItem.option_split != 1}"><c:forEach var="optLevel" begin="2" end="${optnDt.OPTION_LEVEL}">&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach></c:if>${optnDt.OPTION_NAME}</option>
					</c:forEach>
				</select>
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>
</c:if>
<script type="text/javascript">
$(function(){
	var previousValue = "Z";
	var idx = "${idx}";
	
	$("#is_viewStandard_" + idx + " option[value='Z']").prop("selected", true);
	$("#is_viewStandard2_" + idx).css("display", "none");
	fn_setInitItemOrderOption("is_viewStandard", idx);
	fn_setItemOrderOption("is_viewStandard", idx);
	
	$("#is_viewStandard1_" + idx).change(function() {
		if(($(this).val() == "Z")){
			$("#is_viewStandard2_" + idx).css("display", "none");
		}
		
		if(previousValue == "Z" && $(this).val() != "Z"){
			$("#is_viewStandard2_" + idx).css("display", "");
		}
		previousValue = $(this).val();
	});
});

// 3차 select option 초기 setting

function fn_setInitItemOrderOption(theId, idx){
	try{
		var varMaxLevel = new Number($("#" + theId + "_" + idx).attr("data-max"));
		for(var varIdx = 1 ; varIdx <= varMaxLevel ; varIdx ++) {
			(function(varIdx){
				$("#" + theId + varIdx + "_" + idx).change(function(){
					// 저장값 setting
					var varClassIdx;
					for(var varSIdx = varIdx ; varSIdx >= 1 ; varSIdx --) {
						varClassIdx = $("#" + theId + varSIdx + "_" + idx).find("option:selected").val();
						if(varClassIdx != '') break;
					}
					$("#" + theId + "_" + idx + " option[value='" + varClassIdx + "']").prop("selected", true);
					
					// 다음 level setting
					if(varIdx < varMaxLevel) {
						for(var varSIdx = varIdx + 1 ; varSIdx <= varMaxLevel ; varSIdx ++) {
							$("#" + theId + varSIdx + "_" + idx + " option:gt(0)").remove();
						}
						
						var varPClassIdx = $(this).find("option:selected").val();
						var varClassOpts = $("#" + theId + "_" + idx + " option[data-pcode='" + varPClassIdx + "']");
						$("#" + theId + (varIdx + 1) + "_" + idx).append(varClassOpts.clone());
					}
				});
			})(varIdx);
		}
	}catch(e){}
}

/*****************************************************
3차 select option 채우기
******************************************************/
function fn_setItemOrderOption(theId, idx){
	try {
		var varSelOptObj = $("#" + theId + "_" + idx).find("option:selected");
		var varSelClassIdx = varSelOptObj.val();
		var varClassIdx = varSelClassIdx;
		if(varClassIdx == '') {
			var varClassOpts = $("#" + theId + "_" + idx + " option[data-pcode='0']");
			$("#" + theId + "1" + "_" + idx).append(varClassOpts.clone());
		} else {
			var varDataLevel = new Number(varSelOptObj.attr("data-level"));
			var varPCode;
			for(var varIdx = varDataLevel ; varIdx >= 1 ; varIdx --) {
				varSelOptObj = $("#" + theId + "_" + idx + " option[value='" + varClassIdx + "']");
				varPCode = varSelOptObj.attr("data-pcode");
				var varClassOpts = $("#" + theId + "_" + idx + " option[data-pcode='" + varPCode + "']");
				$("#" + theId + varIdx + "_" + idx).append(varClassOpts.clone());
				$("#" + theId + varIdx + "_" + idx).find("option[value='" + varClassIdx + "']").prop("selected", true);
				varClassIdx = varPCode;
			}

			var varMaxLevel = new Number($("#" + theId + "_" + idx).attr("data-max"));
			if(varDataLevel < varMaxLevel) {
				varClassIdx = varSelClassIdx;
				var varIdx = varDataLevel + 1
				var varObj = $("#" + theId + varIdx + "_" + idx);
				var varPClassIdx = varClassIdx;
				var varClassOpts = $("#" + theId + "_" + idx + " option[data-pcode='" + varPClassIdx + "']");
				$("#" + theId + varIdx + "_" + idx).append(varClassOpts.clone());
			}
		}
	} catch(e){}
}
</script>
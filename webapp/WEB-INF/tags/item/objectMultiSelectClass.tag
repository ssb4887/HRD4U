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
<%@ attribute name="masterCode" type="java.util.ArrayList"%>			
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
	<c:set var="applyIndutyIdxs" value="${fn:split(dt.APPLY_INDUTY, ',')}"/>
	<!-- 업종제한이 있는 사업 목록 idx -->
	<c:set var="prtbizIdxList" value="${fn:split(prtbizList.PRTBIZ_IDX_LIST, ',')}" />
	<c:set var="prtbizNameList" value="${fn:split(prtbizList.PRTBIZ_NAME_LIST, ',')}" />
	<c:set var="optTitle" value=""/>
	<c:forEach var="prtbizIdx" items="${prtbizNameList}" varStatus="i">
		<c:choose>
			<c:when test="${fn:contains(prtbizIdx, 'S-OJT')}">
				<c:set var="sojt" value="${prtbizIdxList[i.index]}" />
			</c:when>
			<c:when test="${fn:contains(prtbizIdx, '학습조직화')}">
				<c:set var="learning" value="${prtbizIdxList[i.index]}" />
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</c:forEach>
	<c:forEach var="levelIdx" begin="1" end="4">
		<select id="${itemId}${levelIdx}" name="${itemId}_tmp" title="${levelIdx}차 적용업종" class="select t_select four-select" <c:if test="${required == 1}"> required="required"</c:if>>
			<c:choose>
				<c:when test="${levelIdx eq 1}"><c:set var="optTitle" value="대"/></c:when>
				<c:when test="${levelIdx eq 2}"><c:set var="optTitle" value="중"/></c:when>
				<c:when test="${levelIdx eq 3}"><c:set var="optTitle" value="소"/></c:when>
				<c:otherwise><c:set var="optTitle" value="세"/></c:otherwise>
			</c:choose>
			<option value=""><c:out value="적용업종 ${optTitle}분류" /></option>
		</select>
	</c:forEach>
	<c:forEach var="masterDt" items="${masterCode}" varStatus="k">
	</c:forEach>
	<select id="${itemId}" name="${itemId}" title="적용업종" style="display:none;" class="select t_select">
		<option value=""><c:out value="적용업종 선택" /></option>
		<option value="AA" data-pcode="0" data-level="1" data-maxcode="AA" data-sojt="1" data-learning="1"><c:out value="전 업종" /></option>
		<c:forEach var="masterDt" items="${masterCode}" varStatus="k">
			<option value="<c:out value="${masterDt.OPTION_CODE}"/>" data-pcode="${masterDt.PARENT_OPTION_CODE}" data-level="${masterDt.OPTION_LEVEL}" data-maxcode="${masterDt.OPTION_CODE}" data-sojt="${masterDt.SOJT}" data-learning="${masterDt.LEARNING}"><c:out value="${masterDt.OPTION_NAME}"/></option>
		</c:forEach>
		<option value="ZZ" data-pcode="0" data-level="1" data-maxcode="ZZ" data-sojt="1" data-learning="1"><c:out value="해당 없음" /></option>
	</select>
	<select id="${itemId}list" name="${itemId}" title="적용업종" style="display:none;" class="select t_select">
		<option value=""><c:out value="적용업종 선택" /></option>
		<option value="00000" data-pcode="AA" data-level="2" data-maxcode="AA" data-sojt="1" data-learning="1" style="display:none;" <c:if test="${applyIndutyIdxs[0] eq 'AA' && applyIndutyIdxs[1] eq '00000'}">selected="selected"</c:if>><c:out value="전 업종" /></option>
		<c:forEach var="industDt" items="${industList}" varStatus="k">
			<option value="<c:out value="${industDt.OPTION_CODE}"/>" data-pcode="${industDt.PARENT_OPTION_CODE}" data-level="${industDt.OPTION_LEVEL}" id="${industDt.LCLAS}" data-sojt="${industDt.SOJT}" data-learning="${industDt.LEARNING}" <c:if test="${industDt.LCLAS eq applyIndutyIdxs[0] && industDt.OPTION_CODE eq applyIndutyIdxs[1]}">selected="selected"</c:if>><c:out value="${industDt.OPTION_NAME}"/></option>
		</c:forEach>
		<option value="99999" data-pcode="ZZ" data-level="2" data-maxcode="AA" data-sojt="1" data-learning="1" style="display:none;" <c:if test="${applyIndutyIdxs[0] eq 'ZZ' && applyIndutyIdxs[1] eq '99999'}">selected="selected"</c:if>><c:out value="해당 없음" /></option>
	</select>
</c:if>
<script type="text/javascript">
	var varMaxLevel = 4;
	var theId = "${itemId}";
	var listId = "${itemId}list";
	var previousValue = "";
	
	fn_init();
	fn_isSojtisLearning();
	
	// 참여가능사업 변경 감지
	$("#prtbizIdx").change(function(){
		$("#applyInduty").find("option:selected").removeAttr("selected");
		$("#applyIndutylist").find("option:selected").removeAttr("selected");
		for(var i = 1; i <= 4; i++){
			var selectName = "applyInduty" + i;
			$("#" + selectName + " option:gt(0)").remove();
		}
		fn_init();
		fn_isSojtisLearning();
	});
	
	$("#applyInduty1").change(function() {
		if(($(this).val() == "AA") || ($(this).val() == "ZZ")){
			$("#applyInduty2, #applyInduty3, #applyInduty4").css("display", "none");
			if($(this).val() == "AA"){
				$("#applyIndutylist option[data-pcode='AA']").prop("selected", true);
			} else {
				$("#applyIndutylist option[data-pcode='ZZ']").prop("selected", true);
			}
		}
		
		if((previousValue == "AA" || previousValue == "ZZ") && ($(this).val() == "AA" || $(this).val() == "ZZ")){
			$("#applyInduty2, #applyInduty3, #applyInduty4").css("display", "none");
		} else if((previousValue == "AA" || previousValue == "ZZ") && ($(this).val() != "AA" || $(this).val() != "ZZ")){
			$("#applyInduty2, #applyInduty3, #applyInduty4").css("display", "");
		} 
		previousValue = $(this).val();
	});
	
	$("#applyInduty3").change(function() {
		var selectValue = $(this).find("option:selected").val();
		var selectPValue = $(this).find("option:selected").attr("id");
		
		// 세분류가 없을 때 세분류 select box 숨기기
		if($("#applyIndutylist option[data-pcode='" + selectValue + "'][id='" + selectPValue + "']").length == 0){
			$("#applyInduty4").css("display", "none");
		} else {
			$("#applyInduty4").css("display", "");
		}
	});
	
	function fn_init(){
		var limitName = "";
		
		// 3차 select option 초기 setting
		for(var varIdx = 1; varIdx <= 4; varIdx++){
			(function(varIdx){
				$("#" + theId + varIdx).change(function(){
					$("#applyInduty2, #applyInduty3, #applyInduty4").css("display", "");
					// 저장값 setting
					var varClassIdx;
					for(var varSIdx = varIdx ; varSIdx >= 1 ; varSIdx --) {
						varClassIdx = $("#" + theId + varSIdx).find("option:selected").val();
						if(varClassIdx != '') break;
					}
					if(varIdx == 1){
						$("#" + theId + " option[value='" + varClassIdx + "']").prop("selected", true);
					} else {
						$("#" + listId + " option[value='" + varClassIdx + "']").prop("selected", true);
					}
					
					// 다음 level setting
					if(varIdx < varMaxLevel) {
						for(var varSIdx = varIdx + 1 ; varSIdx <= varMaxLevel ; varSIdx ++) {
							$("#" + theId + varSIdx + " option:gt(0)").remove();
						}
						
						var varPClassIdx = $(this).find("option:selected").val();
						var varClassOpts = $("#" + listId + " option[data-pcode='" + varPClassIdx + "']");
						
						$("#" + theId + (varIdx + 1)).append(varClassOpts.clone());
						if(varIdx == 2){
							var var2ParentClassCode = $("#" + theId + (varIdx -1)).find("option:selected").val();
							var varParentClassPcode = $("#" + theId + varIdx).find("option:selected").attr("data-pcode");
							for(var i = 0; i < varClassOpts.length; i++){
								var option = varClassOpts[i];
								var maxCode = option.id;
								if((maxCode != var2ParentClassCode) || (maxCode != varParentClassPcode)){
									$("#" + theId + (varIdx + 1) + " option[id='"+ maxCode +"']").remove();
								}
							}
						} else if(varIdx == 3){
							var var3ParentClassCode = $("#" + theId + (varIdx -2)).find("option:selected").val();
							var var2ParentClassCode = $("#" + theId + (varIdx -1)).find("option:selected").attr("data-pcode");
							var varParentClassPcode = $("#" + theId + varIdx).find("option:selected").attr("id");
							for(var i = 0; i < varClassOpts.length; i++){
								var option = varClassOpts[i];
								var maxCode = option.id;
								if((maxCode != var3ParentClassCode) || (maxCode != var2ParentClassCode) || (maxCode != varParentClassPcode)){
									$("#" + theId + (varIdx + 1) + " option[id='"+ maxCode +"']").remove();
								}
							}
						}
					}
				});
			})(varIdx);
		}
		
		// 3차 select option 채우기
		var varSelOptObj = $("#" + listId).find("option:selected");
		var varSelClassIdx = varSelOptObj.val();
		var varClassIdx = varSelClassIdx;
		if(varClassIdx == '') {
			var varClassOpts = $("#" + theId + " option[data-pcode='0']");
			$("#" + theId + "1").append(varClassOpts.clone());
		} else {
			if(varClassIdx == '00000' || varClassIdx == '99999'){
				var varClassOpts = $("#" + theId + " option[data-pcode='0']");
				$("#" + theId + "1").append(varClassOpts.clone());
				if(varClassIdx == '00000'){
					$("#" + theId + "1").find("option[value='AA']").prop("selected", true);
				} else {
					$("#" + theId + "1").find("option[value='ZZ']").prop("selected", true);
				}
				$("#applyInduty2, #applyInduty3, #applyInduty4").css("display", "none");
			} else {
				var varSelOptObj2 = $("#" + listId).find("option:selected");
				var varSelClassIdx2 = varSelOptObj.val();
				var varClassIdx2 = varSelClassIdx;
				
				var varDataLevel = new Number(varSelOptObj.attr("data-level"));
				var varPCode;
				for(var varIdx = varDataLevel ; varIdx >= 1 ; varIdx --) {
					if(varIdx != 1){
						varSelOptObj = $("#" + listId + " option[value='" + varClassIdx + "']");
						varPCode = varSelOptObj.attr("data-pcode");
						var varClassOpts = $("#" + listId + " option[data-pcode='" + varPCode + "']");
						$("#" + theId + varIdx).append(varClassOpts.clone());
						$("#" + theId + varIdx).find("option[value='" + varClassIdx + "']").prop("selected", true);
						varClassIdx = varPCode;
					} else {
						varSelOptObj = $("#" + theId + " option[value='" + varClassIdx + "']");
						varPCode = varSelOptObj.attr("data-pcode");
						var varClassOpts = $("#" + theId + " option[data-pcode='" + varPCode + "']");
						$("#" + theId + varIdx).append(varClassOpts.clone());
						$("#" + theId + varIdx).find("option[value='" + varClassIdx + "']").prop("selected", true);
					}
					
				}

				var maxLevel = 4;
				if(varDataLevel < maxLevel) {
					if(varDataLevel != 1){
						varClassIdx = varSelClassIdx;
						var varIdx = varDataLevel + 1
						var varObj = $("#" + theId + varIdx);
						var varPClassIdx = varClassIdx;
						var varClassOpts = $("#" + listId + " option[data-pcode='" + varPClassIdx + "']");
						$("#" + theId + varIdx).append(varClassOpts.clone());
					} else {
						varClassIdx = varSelClassIdx;
						var varIdx = varDataLevel + 1
						var varObj = $("#" + theId + varIdx);
						var varPClassIdx = varClassIdx;
						var varClassOpts = $("#" + theId + " option[data-pcode='" + varPClassIdx + "']");
						$("#" + theId + varIdx).append(varClassOpts.clone());
					}
				}
			}
		}
	}
	
	function fn_reSetting(newPrtbiz){
		var varClassOpts = $("#applyInduty option");
		$("#applyInduty1").append(varClassOpts.clone());
	}
	
	function fn_isSojtisLearning(){
		var nowPrtbiz = $("#prtbizIdx").val();
		
		if(nowPrtbiz){
			if(nowPrtbiz == "${sojt}"){
				limitName = "sojt";
				fn_removeLimitOption(limitName);
			} else if(nowPrtbiz == "${learning}"){
				limitName = "learning";
				fn_removeLimitOption(limitName);
			} else {
				fn_removeOption();
			}
		} else {
			fn_removeOption();
		}
	}
	
	// 대분류(masterDt)에 중복된 데이터 제거 
	function fn_removeOption(){
		var varSelect = document.getElementById("applyInduty1");
		var varOptions = varSelect.getElementsByTagName("option");
		var existingValues = {}; // 중복된 value를 체크하기 위한 객체
		for(var i = 0; i < varOptions.length; i++){
			var option = varOptions[i];
			var value = option.value;
			if(i > 0 && value == varOptions[i-1].value){
				varSelect.removeChild(option);
			}
			
		}
	}
	// S-OJT, 학습조직화 제한업종 제거	
	function fn_removeLimitOption(limitName){
		var limitValue = new Array();
		
		$("#applyInduty1").find("option").map(function(){
			if(limitName == "sojt"){
				if($(this).attr("data-sojt") == 2){
					limitValue.push($(this).val());
					$(this).css("display", "none");	
				}
			} else {
				if($(this).attr("data-learning") == 2){
					limitValue.push($(this).val());
					$(this).css("display", "none");	
				}
			}
		}).get();
		
		// 중복된 option 제거
		$("#applyInduty1").find("option").map(function(){
			for(var i = 0; i < limitValue.length; i++){
				if($(this).val() == limitValue[i]){
					$(this).css("display", "none");
				}	
			}
		}).get();
	}
</script>
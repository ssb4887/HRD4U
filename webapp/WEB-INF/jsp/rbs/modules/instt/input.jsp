<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_insttInputReset();

	// reset
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_insttInputReset();
		}catch(e){alert(e); return false;}
	});
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
		try {
			<c:choose>
				<c:when test="${param.dl == '1'}">
				self.close();
				</c:when>
				<c:otherwise>
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			return fn_insttInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 숫자만 입력
	$("#<c:out value="${param.inputFormId}"/> #insttNo, #<c:out value="${param.inputFormId}"/> #orderIdx").keydown(function(event){
		fn_preventHan(event, $(this));
		return fn_onlyNum(event);
	});
	
	// 전화번호 입력
	$("#<c:out value="${param.inputFormId}"/> #phone").keydown(function(event){
		fn_preventHan(event, $(this));
		return fn_onlyNumDash(event, $(this));
	});
	
	$("#<c:out value="${param.inputFormId}"/> #insttNo, #<c:out value="${param.inputFormId}"/> #orderIdx").attr("placeholder", "숫자만 입력해 주세요");
	$("#<c:out value="${param.inputFormId}"/> #phone").attr("placeholder", "숫자와'-'만 입력해 주세요");
	
	$(".checkDisable").prop("disabled", true);
	
	// 특정 시의 모든 군구가  다른 지부지사에 등록되어 있으면 해당 시 체크박스 비활성화
	$("[id^='blockCode_child_']").each(function(index, item){
		if($(this).is(':checked')){
			var pcode = $(this).attr("data-pcode");
			$("#blockCode_master_" + pcode).prop("disabled", false);
		} 
	});
	
	// 시를 클릭했을 때 해당 시의 선택 가능한 군구 전체 선택 or 해제
	$("[id^='blockCode_master_']").click(function(){
		var masterCode = $(this).attr('data-code');
		if($(this).is(':checked')){
			$("[id^='blockCode_child_" + masterCode + "']").prop("checked", true);
		} else {
			$("[id^='blockCode_child_" + masterCode + "']").prop("checked", false);
		}
	});

	// 특정 군구를 모두 선택했을 때 해당 시 체크박스 선택
	$("dd div input[type='checkbox']").on('change', function() {
		var masterCode = $(this).attr("data-pcode");
		updateMasterCheck(masterCode);
	});
	
	init();
});

function init(){
	var submitType = "${submitType}";
	// 모든 관할구역이 등록이 되어 있는지 확인
	var totalCheckCount = $("input[type='checkbox']").length;
	var disableCheckCount = $("input[type='checkbox']:disabled").length;
	
	if(submitType == "write" && totalCheckCount == disableCheckCount){
		alert("현재 모든 관할구역이 다른 지부지사에 등록되어 있습니다.");
		history.back();
		return false;		
	}
	
	$("dd div input[type='checkbox']").each(function(index, item) {
		var masterCode = $(this).attr("data-pcode");
		var totalCount = $("dd div input[data-pcode='" + masterCode + "'][type='checkbox']").length;
		var disableCount = $("dd div input[data-pcode='" + masterCode + "'][type='checkbox']:disabled").length;
		var count = totalCount - disableCount;
		var allCheck = $("dd div input[data-pcode='" + masterCode + "'][type='checkbox']:checked").length == count;
		$("#blockCode_master_" + masterCode).prop("checked", allCheck);
	});
}

function fn_insttInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_insttInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}

//숫자 입력
function fn_onlyNum(e, theObj) {
	if(e.shiftKey) {
		return false;
	}
	
	var varKeyCode = e.keyCode;
	if(varKeyCode == 17) {
		varOnlyNumKDKeyCode = varKeyCode;
		return true;
	}
	//alert(varOnlyNumKDKeyCode +":" + varKeyCode);
	if(varOnlyNumKDKeyCode == 17 && varKeyCode == 86) {
		// 붙여넣기
		varOnlyNumKDKeyCode = false;
		return true; 
	}
	if (varKeyCode != 0 && varKeyCode != 8 && varKeyCode != 9 && varKeyCode != 13 && varKeyCode != 46 && (varKeyCode < 35 || varKeyCode > 40 && varKeyCode < 48 || varKeyCode > 57 && varKeyCode < 96 || varKeyCode > 105)){
		return false;
	}
	varOnlyNumKDKeyCode = false;
	return true;
}

//전화번호 입력 
function fn_onlyNumDash(e, theObj) {

	if(e.shiftKey) {
		return false;
	}

	var varKeyCode = e.keyCode;
	var varEventElement = theObj;
	var varVal = $(varEventElement).val();
	if(varKeyCode == 37 || varKeyCode == 39) return true;
	if (varVal.trim() == '' && varKeyCode == 189 || (varKeyCode != 189 && varKeyCode != 109 && varKeyCode != 0 && varKeyCode != 8 && varKeyCode != 9 && varKeyCode != 13 && 
			(varKeyCode != 45 && varKeyCode != 46 && (varKeyCode < 48 || varKeyCode > 57 && varKeyCode < 96 || varKeyCode > 105) || 
			((varVal == '' || varVal.indexOf('-') != -1) && varKeyCode == 46) || 
			(varVal != '' && varKeyCode == 46) /*||
			(varVal == '0' && varKeyCode != 46)*/))){
		return false;
	}
	
	return true;
}
function updateMasterCheck(masterCode) {
	var totalCount = $("dd div input[data-pcode='" + masterCode + "'][type='checkbox']").length;
	var disableCount = $("dd div input[data-pcode='" + masterCode + "'][type='checkbox']:disabled").length;
	var count = totalCount - disableCount;
	var allCheck = $("dd div input[data-pcode='" + masterCode + "'][type='checkbox']:checked").length == count;
	$("#blockCode_master_" + masterCode).prop("checked", allCheck);
}
</script>
<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
	$("#<c:out value="${param.inputFormId}"/> #trDaycnt, #trtm").attr("placeholder", "숫자만 입력해 주세요");
	
	// reset
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_sampleInputReset();
		}catch(e){alert(e); return false;}
	});
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
		try {
			/* var submitType = "${submitType}";
			if(submitType == "modify"){
				location.href= "${URL_VIEW}&${keyColumnName}=${keyIdx}";
			} else {
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
			} */
			history.back();
		}catch(e){return false;}
	});
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 숫자만 입력
	$("#<c:out value="${param.inputFormId}"/> #trDaycnt, #trtm, [id^='subTime']").keydown(function(event){
		fn_preventHan(event, $(this));
		return fn_onlyNum(event);
	});
	
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
  	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	// 적용업종 필수값 체크(추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용)
	/* var inputName = ["적용업종 대분류와 세부 업종을 선택해 주세요.", "적용업종 중분류와 세부 업종을 선택해 주세요.", "적용업종 소분류와 세분류를 선택해 주세요.", "적용업종 세분류를 선택해 주세요."];
	for(var i = 0; i < 4; i++){
		if(!$("#applyInduty" + (i+1)).val() && $("#applyInduty" + (i+1)).is(":visible")){
			alert(inputName[i]);
			$("#applyInduty" + (i+1)).focus();
			return false;
		}
	} */
	
	// 훈련과정 항목 중 하나라도 입력이 안되어 있으면 등록 불가
	var check = true;
	$("#tpSubContent tbody tr:last td").find("input").each(function(){
		if(!$(this).val()){
			alert("훈련과정을 정확하게 입력해 주세요.");
			check = false;
			return false;
		}
	});
	
	// 에디터 ZWS 삭제
	$(".board-write").find("textarea").each(function() {
		var inputText = $(this).val().replace(/\u200B/g, '');
		//alert(inputText);
		$(this).val(inputText);
	});
	
	if(!check){
		return false;
	}
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
</script>
<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
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
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 숫자만 입력
	$("#<c:out value="${param.inputFormId}"/> #year, [id^='corp'], [id^='tot']").keydown(function(event){
		return fn_onlyNum(event);
	});
	$("#<c:out value="${param.inputFormId}"/> [id^='corp'], [id^='tot']").attr("placeholder", "숫자만 입력해 주세요");
	
	$("#indutyCode").addClass("w100");
	
	$("[id^='corp'], [id^='tot']").keyup(function(e) {
		const regExp = /[0-9]/g;
		const ele = event.target;
		if(regExp.test(ele.value)){
			let value = e.target.value;
			value = Number(value.replaceAll(/[^0-9]/g, ''));
			const formatValue = value.toLocaleString('ko-KR');
			$(this).val(formatValue);
		}
	});
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
	if(!$("#indutyCode").val()){
		alert("업종코드를 선택하세요");
		$("#indutyCode").focus();
		return false;
	}
	
	if(!$("#year").val()){
		alert("연도를 선택하세요");
		$("#year").focus();
		return false;
	}
	
	var trLength = $("#traing > tbody > [id^='range']").length;
	var inputList = ["corp", "tot"];
	var inputName = ["참여 기업", "참여 인원수"];
	var rangeName = ["30인 이하", "30인 이상 100인 이하", "100인 이상 300인 이하", "300인 이상 1000인 이하", "1000인 이상"];
	for(var i = 0; i < trLength; i++){
		for(var j = 0; j < 2; j++){
			if(!$("#range" + (i+1)).find("#" + inputList[j]).val()){
				alert(rangeName[i] + " 항목의 " + inputName[j] + "을 입력해 주세요.");
				$("#range" + (i+1)).find("#" + inputList[j]).focus();
				return false;
			}	
		}
	}
	fn_createMaskLayer();
	return true;
}
//숫자 입력
function fn_onlyNum(event) {
	const regExp = /[^-\,0-9]/g;
	const ele = event.target;
	if(regExp.test(ele.value)){
		ele.value = ele.value.replace(regExp, '');
	}
}
</script>
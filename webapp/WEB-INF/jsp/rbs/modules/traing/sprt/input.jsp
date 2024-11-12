<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	$(".contents-title").text("훈련동향");
	
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
	
	$("#indutyCode").addClass("w100");
	
	$("[id^='pay']").keyup(function(e) {
		const regExp = /[0-9]/g;
		const ele = event.target;
		if(regExp.test(ele.value)){
			let value = e.target.value;
			value = Number(value.replaceAll(/[^0-9]/g, ''));
			const formatValue = value.toLocaleString('ko-KR');
			$(this).val(formatValue);
		}
	});
	
	$("#indutyCode").change(function() {
		$("#year").val("");
		$("[id^='pay']").val("");
	});
	
	// 숫자만 입력
	$("#<c:out value="${param.inputFormId}"/> #year, [id^='pay']").keydown(function(event){
		return fn_onlyNum(event);
	});
	$("#<c:out value="${param.inputFormId}"/> [id^='pay']").attr("placeholder", "숫자만 입력해 주세요");
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
		alert("연도를 입력하세요");
		$("#year").focus();
		return false;
	}
	
	var trLength = $("#sprt > tbody > [id^='trRange']").length;
	var rangeName = ["30인 이하", "30인 이상 100인 이하", "100인 이상 300인 이하", "300인 이상 1000인 이하", "1000인 이상"];
	for(var i = 0; i < trLength; i++){
		if(!$("#trRange" + (i+1)).find("#pay" + (i+1)).val()){
			alert(rangeName[i] + " 항목의 지원금액을 입력해 주세요.");
			$("#trRange" + (i+1)).find("#pay" + (i+1)).focus();
			return false;
		}	
	}
	
	var varName = $("#indutyCode").find("option:selected").text();
	$("#industCdName").val(varName);
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
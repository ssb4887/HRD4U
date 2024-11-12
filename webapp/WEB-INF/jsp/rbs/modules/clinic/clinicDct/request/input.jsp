<%@ include file="../../../../include/commonTop.jsp"%>
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
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
		try {
			var varConfirm = confirm("목록으로 이동하시겠습니까? \n임시저장을 하지 않으면 작성한 내용은 저장되지 않습니다.");
			if(!varConfirm) return false;
				
			location.href="${elfn:replaceScriptLink(REQUEST_LIST_FORM_URL)}";
		}catch(e){return false;}
	});
	</c:if>
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	var submitType = "${submitType}";
	
	// 수정모드일 때 HRD담당자 인원수와 결격사유 입력값이 있으면 보여주기
	$("[name^='selfEmpCn']").each(function(index, item){
		if(submitType == "modify" && $(this).val() != ""){
			$(this).parent().css("display", "inline");
		}
	});
	
	// HRD담당자 보유 여부값이 Y일 때 인원 입력하는 텍스트 보여주기
	var peopleNo = $("#peopleCount").find("input").attr("data-pcode");
	$("[id^='isSelfEmp" + peopleNo + "']").change(function (){
		if($("[id^='isSelfEmp" + peopleNo + "']:checked").val() == "Y"){
			$("#peopleCount").css("display", "inline");
		} else {
			$("#peopleCount").find("input").val("");
			$("#peopleCount").css("display", "none");
		}
	});
	// 결격사유 값이 N일 때 결격사유 입력하는 텍스트 보여주기
	var disqualNo = $("#disqualify").find("input").attr("data-pcode");
	$("[id^='isSelfEmp" + disqualNo + "']").change(function (){
		if($("[id^='isSelfEmp" + disqualNo + "']:checked").val() == "N"){
			$("#disqualify").css("display", "inline");
		} else {
			$("#disqualify").find("input").val("");
			$("#disqualify").css("display", "none");
		}
	});
	
	// 기업 선정 심사표가 모두 pass 이면 선정결과 pass 아니면 fail
	$("[id^='isJdgMn']").change(function (){
		if($("[id^='isJdgMn']:checked[value='P']").length == 7){
			$("#slctnResult").val("P");
			$("#jdgResult").text("PASS");
		} else if($("[id^='isJdgMn']:checked").length == 7 && $("[id^='isJdgMn']:checked[value='P']").length != 7) {
			$("#slctnResult").val("F");
			$("#jdgResult").text("FAIL");
		}
	});
	
	// 지부지사 이름 setting
	var cliInsttIdx = "${dt.CLI_INSTT_IDX}";
	var insttName = $("#insttIdx option[value='" + cliInsttIdx + "']:eq(0)").text();
	$("#insttName").text(insttName);
	
	// email 아이디 체크하기
	$("#email1").keyup(function(e) {
		var regExp = /^[a-zA-Z0-9-_]+$/;
		var inputId = $(this).val();
		
		if(!regExp.test(inputId)){
			alert("이메일 아이디는 영어 대소문자, 숫자, '-', '_'만 입력가능합니다.");
			const regExp = /[^a-zA-Z0-9-_]+$/g;
			const ele = e.target;
			ele.value = ele.value.replace(regExp, '');
		}
	});
	
	// placeholder 추가
	$("input.onlyNum").attr("placeholder", "숫자만 입력하세요");
	$("input.onlyNumDash").attr("placeholder", "숫자와 '-'만 입력하세요");
	
	// 경력 1~99까지 입력
	$("[id^='hffcCareer']").keyup(function(event) {
		if($(this).val() > 99){
			alert("99이하로 입력해 주세요.");
			$(this).val(99);
		}
	});
	
	// 인원수 99999명까지 입력
	$(".hrdPeople").keyup(function(event) {
		if($(this).val() > 99999){
			alert("99999이하로 입력해 주세요.");
			$(this).val(99999);
		}
	});
	
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
		fn_onlyNumNoCopy(event);
	});
	
	// 전화번호 입력
	$(".onlyNumDash").keyup(function(event){
		if($(this).val().length > 13){
			$(this).val($(this).val().substr(0, 13));
		}
		fn_onlyNumDashNoCopy(event);
	});
	
	// 복사 붙여넣기 시 숫자만 입력
	$(".onlyNum").change(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
		fn_onlyNumNoCopy(event);
	});
	
	// 복사 붙여넣기 시 전화번호 입력
	$(".onlyNumDash").change(function(event){
		if($(this).val().length > 13){
			$(this).val($(this).val().substr(0, 13));
		}		
		fn_onlyNumDashNoCopy(event);
	});
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	// 기업 자가 체크리스트 체크 확인
	for(var i = 1; i < 5; i++){
		var radioCheck = $("[name='isSelfEmp" + i + "']").is(":checked");
		if(!radioCheck){
			alert("기업 자가 확인 체크리스트 항목을 모두 체크해 주세요.");
			$("[name='isSelfEmp" + i + "']").focus();
			return false;
		}
		if(i == 3 && $("[name='isSelfEmp" + i + "']:checked").val() == "Y"){
			var inputCheck = $("[name='selfEmpCn" + i + "']").val();
			if(inputCheck == ""){
				alert("HRD 담당자 인원을 입력하세요");
				$("[name='selfEmpCn" + i + "']").focus();
				return false;
			}
		}
		if(i == 4 && $("[name='isSelfEmp" + i + "']:checked").val() == "N"){
			var textCheck = $("[name='selfEmpCn" + i + "']").val();
			if(textCheck == ""){
				alert("결격사유를 입력하세요");
				$("[name='selfEmpCn" + i + "']").focus();
				return false;
			}
		}
	}
	
	// 기업 선정 심사표 확인
	var result = $("#slctnResult").val();
	if(result == ""){
		alert("기업 선정 심사표 항목을 모두 체크해 주세요.");
		return false;
	}
	
	fn_createMaskLayer();
	return true;
}
</script>
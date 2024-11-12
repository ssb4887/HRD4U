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
	<c:if test="${isAdmMode}">
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
	</c:if>
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(id){
	const form = document.getElementById('fn_sampleInputForm');
	var progress = document.createElement('input');
	progress.setAttribute("type", "hidden");
	progress.setAttribute("name", "progress");
	progress.setAttribute("value", id);
	
	if(id == 10) {
		if($("#cnsl_necessity").val() == "") {
	 		alert("현장활용 컨설팅 필요성 항목 입력이 완료되지 않았습니다.");
			$("#cnsl_necessity").focus();
			return false;
		}
		
		if($("#main_result").val() == "") {
	 		alert("현장활용 컨설팅 주요 결과 항목 입력이 완료되지 않았습니다.");
			$("#main_result").focus();
			return false;
		}
		
		if($("#main_content").val() == "") {
	 		alert("현장훈련 주요 내용 항목 입력이 완료되지 않았습니다.");
			$("#main_content").focus();
			return false;
		}
		
		if($("#cnsl_analysis").val() == "") {
	 		alert("현장 현황 분석 항목 입력이 완료되지 않았습니다.");
			$("#cnsl_analysis").focus();
			return false;
		}
		
		if($("#imp_result").val() == "") {
	 		alert("현장활용 컨설팅 개선결과 항목 입력이 완료되지 않았습니다.");
			$("#imp_result").focus();
			return false;
		}
		
		if($("#imp_method").val() == "") {
	 		alert("개선방향 및 세부방안 항목 입력이 완료되지 않았습니다.");
			$("#imp_method").focus();
			return false;
		}
		
		if($("#add_plan").val() == "") {
	 		alert("추가 훈련 계획 항목 입력이 완료되지 않았습니다.");
			$("#add_plan").focus();
			return false;
		}
		
		if($("#subject_name").val() == "") {
	 		alert("과정명 입력이 완료되지 않았습니다.");
			$("#subject_name").focus();
			return false;
		}
		
		if($("#training_type").val() == "") {
	 		alert("훈련 형태 입력이 완료되지 않았습니다.");
			$("#training_type").focus();
			return false;
		}
		
		if($("#rec_training").val() == "") {
	 		alert("추천 훈련사업 입력이 완료되지 않았습니다.");
			$("#rec_training").focus();
			return false;
		}
		
		if($("#training_goal").val() == "") {
	 		alert("훈련 목표 입력이 완료되지 않았습니다.");
			$("#training_goal").focus();
			return false;
		}
		
		if($("#main_training").val() == "") {
	 		alert("주요 훈련 내용 입력이 완료되지 않았습니다.");
			$("#main_training").focus();
			return false;
		}
		
		if($("#training_target").val() == "") {
	 		alert("훈련 대상 입력이 완료되지 않았습니다.");
			$("#training_target").focus();
			return false;
		}
		
		var $dataMap = $(".trContent");
		for(var i=0; i < $dataMap.length; i ++){
			if($dataMap.eq(i).val() == null || $dataMap.eq(i).val() == "" || $dataMap.eq(i).val() == " "){
				alert('훈련 내용 항목 중 누락된 항목이 있습니다.');
				return false;
			}
		}
	}
	
	<itui:submitValidConsult items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	showLoading();
	
	form.appendChild(progress);
	document.body.appendChild(form);
	form.submit();
	return true;
}

</script>
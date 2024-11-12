<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
var pageNumber = 1;
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
				location.href="${elfn:replaceScriptLink(RESULT_LIST_FORM_URL)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	</c:if>
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			// 승인 상태에 따라 confirm창에 다른 내용이 나오도록 설정
			var confmStatus = $(this).attr("data-status");
			var statusName = "";
			switch (confmStatus){
			case "5":
				statusName = "임시저장";
				$("#fn_resultInputForm").attr("action", "${RESULT_TEMPSAVE_URL}");
				break;
			case "10":
				statusName = "신청";
				$("#fn_resultInputForm").attr("action", "${RESULT_APPLY_URL}");
				break;
			}
			console.log(JSON.stringify($("#inputFormId")))
			
			var varConfirm = confirm("능력개발클리닉 참여 성과보고서를 " + statusName + "하시겠습니까?");
			if(!varConfirm) return false;
			
			return fn_resultInputFormSubmit();
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
	
	//화면 이동 제어
	$("#goBefore").click(function(){
		$("#" + pageNumber).addClass("hide");
		pageNumber = pageNumber - 1;
		$("#" + pageNumber).removeClass("hide");
		pageMoveControl(pageNumber);
	});
	
	$("#goNext").click(function(){
		$("#" + pageNumber).addClass("hide");
		pageNumber = pageNumber + 1;
		$("#" + pageNumber).removeClass("hide");
		pageMoveControl(pageNumber);
	});
	
	function pageMoveControl(pageNumber){
		if(pageNumber == 1){
			$("#goBefore").addClass("hide");
		}else{
			$("#goBefore").removeClass("hide");
		}
		if(pageNumber == 4){
			$("#goNext").addClass("hide");
		}else{
			$("#goNext").removeClass("hide")
		}
	};
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_resultInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>
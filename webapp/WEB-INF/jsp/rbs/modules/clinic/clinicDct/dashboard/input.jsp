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
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>
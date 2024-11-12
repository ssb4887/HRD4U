<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
		try {
			var varConfirm = confirm("목록으로 이동하시겠습니까? \n임시저장을 하지 않으면 작성한 내용은 저장되지 않습니다.");
			if(!varConfirm) return false;
				
			location.href="${elfn:replaceScriptLink(SUPPORT_LIST_FORM_URL)}";
		}catch(e){return false;}
	});
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			// 승인 상태에 따라 confirm창에 다른 내용이 나오도록 설정
			var confmStatus = $(this).attr("data-status");
			var statusName = "";
			switch (confmStatus){
			case "5":
				statusName = "임시저장";
				$("#fn_supportInputForm").attr("action", "${SUPPORT_TEMPSAVE_URL}");
				break;
			case "10":
				statusName = "신청";
				$("#fn_supportInputForm").attr("action", "${SUPPORT_APPLY_URL}");
				break;
			}
			
			var varConfirm = confirm("맞춤훈련과정개발 맟춤개발 지원금 신청서를 " + statusName + "하시겠습니까?");
			if(!varConfirm) return false;
			
			return fn_requestInputFormSubmit(confmStatus);
		}catch(e){alert(e); return false;}
	});
	
	// placeholder 추가
	$("input.onlyNum").attr("placeholder", "숫자만 입력하세요");
	
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		fn_preventHan(event, $(this));
		return fn_onlyNumNoCopy(event, $(this));
	});
	
	// 복사 붙여넣기 시 숫자만 입력
	$(".onlyNum").change(function(event){
		fn_onlyNumNoCopy(event);
	});
	
	// 금액 0만 입력하는거 방지
	$(".cash").keyup(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
	});
	
	// 복사 붙여넣기 시 금액 0만 입력하는거 방지
	$(".cash").change(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
	});
	
	// 모달창 열기
	$("#open-modal01").on("click", function() {
        $(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
        
    });
	
	// 모달창 닫기
	$("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
	// 훈련실시 내역(HRD-NET) 모달창에서 훈련과정 선택
	$("#modal-action01 [id^='selectTp']").click(function() {
		var tpNm = $(this).attr("data-nm");
		var tpCd = $(this).attr("data-cd");
		var trOprtnDe = $(this).attr("data-start");
		
		$("#tpNm").val(tpNm);
		$("#tpCd").val(tpCd);
		$("#trOprtnDe").val(trOprtnDe);
		
		alert("실시훈련과정이 선택되었습니다.");
		$("#selectedTpNm").text(tpNm);
		
		$("#modal-action01").hide();
        $(".mask").fadeOut(150);
	});
	
	var teamLength = $("#teamSubLength").val();
	
	if(teamLength == (1 || 3)){
		$("#delEx, #delIn").hide();
	}
	
	// 합계 기본 setting
	fn_sumTotalPayInit();
	
	// 외부훈련교사 추가 및 삭제
	$("#addEx").click(function() {
		var teamSubLength = Number($("#teamSubLength").val());
		var index = $("#exButton").prev().attr("id");
		var type = $("#exButton").prev().attr("data-type");
		
		var inputList = ["teamNm", "trOprtnDe", "teamCost"];
		var inputName = ["성명", "산출내역", "금액"];
		
		if(type == "ex"){
			for(var i = 0; i < inputList.length; i++){
				if($("#" + inputList[i] + index).val() == ""){
					alert(inputName[i] + "을 입력해 주세요.");
					$("#" + inputList[i] + index).focus();
					return false;
				}
			}
		}
		
		$("#teamSubLength").val(teamSubLength + 1);
		
		var innerHtml = '<tr id=' + (teamSubLength + 1) + ' data-type="ex"><th scope="row">외부훈련교사<input type="hidden" name="teamIdx' + (teamSubLength + 1) + '" value="4"/></th>' +
						'<td><input type="text" name="teamNm' + (teamSubLength + 1) + '" id="teamNm' + (teamSubLength + 1) + '" class="w100"/></td>' + 
						'<td><input type="text" name="trOprtnDe' + (teamSubLength + 1) + '" id="trOprtnDe' + (teamSubLength + 1) + '" class="w100"/></td>' + 
						'<td><input type="text" name="teamCost' + (teamSubLength + 1) + '" id="teamCost' + (teamSubLength + 1) + '" class="w100 onlyNum cash" placeholder="숫자만 입력하세요."/></td></tr>';
		
		$("#exButton").before(innerHtml);
		$("#delEx").show();
		fn_sumTotalPay();
		fn_checkNumber();
	});
	
	$("#delEx").click(function() {
		var teamSubLength = Number($("#teamSubLength").val());
		var removeId = $("#exButton").prev().attr("id");
		
		$("#exButton").prev().remove();
		$("#teamSubLength").val(teamSubLength - 1);
		$("#addEx").attr("data-idx", (removeId - 1));
		
		var type = $("#exButton").prev().attr("data-type");
		if(type != "ex"){
			$("#addEx").attr("data-idx", 0);
			$("#delEx").hide();
		}
		fn_sumTotalPayInit();
	});
	
	// 내부훈련교사 추가 및 삭제
	$("#addIn").click(function() {
		var teamSubLength = Number($("#teamSubLength").val());
		var index = $("#inButton").prev().attr("id");
		var type = $("#inButton").prev().attr("data-type");
		
		var inputList = ["teamNm", "trOprtnDe", "teamCost"];
		var inputName = ["성명", "산출내역", "금액"];
		
		if(type == 'in'){
			for(var i = 0; i < inputList.length; i++){
				if($("#" + inputList[i] + index).val() == ""){
					alert(inputName[i] + "을 입력해 주세요.");
					$("#" + inputList[i] + index).focus();
					return false;
				}
			}
		}
		
		$("#teamSubLength").val(teamSubLength + 1);
		
		var innerHtml = '<tr id=' + (teamSubLength + 1) + ' data-type="in"><th scope="row">내부훈련교사<input type="hidden" name="teamIdx' + (teamSubLength + 1) + '" value="5"/></th>' +
						'<td><input type="text" name="teamNm' + (teamSubLength + 1) + '" id="teamNm' + (teamSubLength + 1) + '" class="w100"/></td>' + 
						'<td><input type="text" name="trOprtnDe' + (teamSubLength + 1) + '" id="trOprtnDe' + (teamSubLength + 1) + '" class="w100"/></td>' + 
						'<td><input type="text" name="teamCost' + (teamSubLength + 1) + '" id="teamCost' + (teamSubLength + 1) + '" class="w100 onlyNum cash"/></td></tr>';
		
		$("#inButton").before(innerHtml);
		$("#delIn").show();
		fn_sumTotalPay();
		fn_checkNumber();
	});
	
	$("#delIn").click(function() {
		var teamSubLength = Number($("#teamSubLength").val());
		var removeId = $("#inButton").prev().attr("id");
		
		$("#inButton").prev().remove();
		$("#teamSubLength").val(teamSubLength - 1);
		$("#addIn").attr("data-idx", (removeId - 1));	
		
		var type = $("#inButton").prev().attr("data-type");
		if(type != "in"){
			$("#addIn").attr("data-idx", 0);	
			$("#delIn").hide();
		}
		
		fn_sumTotalPayInit();
	});
	
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_requestInputFormSubmit(confmStatus){
	if(confmStatus == '5'){		
		<itui:submitValidCustom confirmStatus='5' items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	} else {		
		<itui:submitValidCustom confirmStatus="10" items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	}
	
	// 훈련과정 입력 체크
	if($("#selectedTpNm").text() == ""){
		alert("실시훈련과정을 선택해 주세요.");
		$("#open-modal01").focus();
		return false;
	}
	
	if(confmStatus != "5"){
		var subInputList = ["teamNm", "trOprtnDe", "teamCost"];
		var subInputName = ["성명", "산출내역", "금액(원)"];
		
		// 외부훈련교사 입력 체크
		var exCheck = true;
		var exItemId = "";
		$("tr[data-type='ex']").each(function() {
			var id = $(this).attr("id");
			for(var i = 0; i < subInputList.length; i++){
				if(!$("#" + subInputList[i] + id).val()){
					alert("외부훈련교사 " + subInputName[i] + "을 입력해 주세요.");
					exItemId = subInputList[i] + id;
					exCheck = false;
					return false;
				}
			}
		});
		
		if(!exCheck){
			alert(exItemId);
			$("#" + exItemId).focus();
			return false;
		}
		
		// 내부훈련교사 입력 체크
		var inCheck = true;
		var inItemId = "";
		$("tr[data-type='in']").each(function() {
			var id = $(this).attr("id");
			for(var i = 0; i < subInputList.length; i++){
				if(!$("#" + subInputList[i] + id).val()){
					alert("내부훈련교사 " + subInputName[i] + "을 입력해 주세요.");
					inItemId = subInputList[i] + id;
					inCheck = false;
					return false;
				}
			}
		});
		
		if(!inCheck){
			$("#" + inItemId).focus();	
			return false;
		}
	}
	
	fn_createMaskLayer();
	return true;
}
function fn_sumTotalPay(){
	$("[id^='teamCost']").change(function() {
		var teamLength = $("#teamSubLength").val();
		var totPay = 0;
		for(var i = 1; i <= teamLength; i++){
			totPay += Number($("#teamCost" + i).val());
		}
		$("#totPay").val(totPay);
	});
}
function fn_sumTotalPayInit(){
	var teamLength = $("#teamSubLength").val();
	var totPay = 0;
	for(var i = 1; i <= teamLength; i++){
		totPay += Number($("#teamCost" + i).val());
	}
	$("#totPay").val(totPay);
}
function fn_checkNumber(){
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		fn_preventHan(event, $(this));
		return fn_onlyNumNoCopy(event, $(this));
	});
	
	// 복사 붙여넣기 시 숫자만 입력
	$(".onlyNum").change(function(event){
		fn_onlyNumNoCopy(event);
	});
	
	// 금액 0만 입력하는거 방지
	$(".cash").keyup(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
	});
	
	// 복사 붙여넣기 시 금액 0만 입력하는거 방지
	$(".cash").change(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
	});
}
</script>
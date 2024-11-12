<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	fn_changeSportType();
	
	var submitType = "${submitType}";
	
	if(submitType == "write"){
		$("#fn_sampleInputForm").attr("action", "${ACTIVITY_APPLY_URL}");
	} else {
		$("#fn_sampleInputForm").attr("action", "${ACTIVITY_MODIFY_URL}&acmsltIdx=${dt.ACMSLT_IDX}");
	}
	
	$(".ui-datepicker-trigger").css("width", "50px");
	$("#startDate, #endDate").css("width", "102%");
	
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
			
			location.href="${elfn:replaceScriptLink(ACTIVITY_LIST_FORM_URL)}";
		}catch(e){return false;}
	});
	</c:if>
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_apply").click(function(){
		try {
			var varConfirm = confirm("능력개발클리닉 활동일지를 저장 하시겠습니까?");
			if(!varConfirm) return false;
			
			return fn_requestInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 모달창 컨트롤
	$("#open-modal01").on("click", function() {
	    $(".mask").fadeIn(150, function() {
	        $("#modal-action01").show();
	    });
	});
	
	$("#modal-action01 .btn-modal-close").on("click", function() {
	    $("#modal-action01").hide();
	    $(".mask").fadeOut(150);
	});
	
	 // 글자 수 채워넣기
	var cn = $("#cn").val();
	if(cn != ""){
		$(".cnCount").text(cn.length);
	}
	
	// 능력개발활동 내역 1000자까지 입력 및 현재 글자 수 보여주기
	$("#cn").keyup(function(e) {
		var content = $(this).val();
		if(content.length == 0 || content == ""){
			$(".cnCount").text("0");
		} else {
			$(".cnCount").text(content.length);
		}
		
		if(content.length > 1000){
			alert("능력개발활동 내역은 1000자까지 입력가능합니다.");
			$(this).val($(this).val().substr(0, 1000));
		}
	});
	
	// 복사 붙여넣기할 때 글자수 체크
	$("#cn").change(function(e) {
		var content = $(this).val();
		if(content.length == 0 || content == ""){
			$(".cnCount").text("0");
		} else {
			$(".cnCount").text(content.length);
		}
		
		if(content.length > 1000){
			alert("능력개발활동 내역은 1000자까지 입력가능합니다.");
			$(this).val($(this).val().substr(0, 1000));
		}
	});
	
	// 훈련계획서에서 체크안한 지원항목 선택 막기
	$("[id^='checkbox01']").click(function() {
		var req = $(this).attr("data-req");
		if(req == "N"){
			alert("훈련계획서에서 신청하지 않은 지원유형은 선택할 수 없습니다. 훈련계획서에서 지원유형을 변경하고 변경승인을 받은 후 선택해 주세요.");
			return false;
		}
	});
	
	// 활동구분 선택 전에는 정액,실비 금액 입력창 둘 다 입력 못하게 막기 
	$("input[name='sportAmt']").attr("readonly", true);
	
	// 활동구분을 선택한 값에 따라 실비/정액 구분 변경 및 지원항목코드 변경 
	$("input[name='actType']").change(function () {
		var actType = $("input[name='actType']:checked").val();
		var itemCd = "";
		var sportAmt = "";
		switch (actType){
		case "02":
			itemCd = "04";
			sportAmt = 100000;
			break;
		case "03":
			itemCd = "08";
			break;
		case "04":
			itemCd = "08";
			break;
		case "05":
			itemCd = "09";
			sportAmt = 200000;
			break;
		case "06":
			itemCd = "09";
			sportAmt = 200000;
			break;
		}
		// 자리수 구분하기
		const formatValue = sportAmt.toLocaleString('ko-KR');
		
		$("#actCd").val(itemCd);
		
		var sportYn = $("input[name='actType']:checked").attr("data-sport");
		$("#sportYn").val(sportYn);
		// 정액/실비 구분하기(1:정액, 2:실비)
		// HRD담당자 역량개발은 정액 실비 둘다 가능해서 따로 처리
		if(actType == "02"){
			$("#sportCheck1").attr("disabled", false);
			$("#sportCheck2").attr("disabled", false);
			$("#sportCheck1").prop("checked", false);
			$("#sportCheck2").prop("checked", false);
			$("#sportType1").attr("disabled", false);
			$("#sportType2").attr("disabled", false);
			$("#sportType2").attr("readonly", false);
			// 정액 금액 setting
			$("#sportType1").val(formatValue);
		} else {
			if(sportYn == "Y"){
				// 실비(정액 체크박스 비활성화 + 실비 체크박스 체크 + 정액 disabled 처리 + 정액 금액 지우기)
	 			$("#sportType2").attr("disabled", false);
	 			$("#sportCheck2").attr("disabled", false);
	 			$("#sportCheck2").prop("checked", true);
	 			$("#sportType2").attr("readonly", false);
	 			$("#sportType1").attr("disabled", true);
	 			$("#sportCheck1").prop("checked", false);
	 			$("#sportType1").val("");
	 			$("#sportCheck1").attr("disabled", true);
			} else {
				// 정액(실비 체크박스 비활성화  + 정액 체크박스 체크 + 실비 disabled 처리 + 실비 금액 지우기 + 정액 금액 추가 + 정액 금액 입력창 수정 못하게 막기)
				$("#sportType1").val(formatValue);
	 			$("#sportType1").attr("disabled", false);
	 			$("#sportCheck1").prop("checked", true);
	 			$("#sportCheck1").attr("disabled", false);
	 			$("#sportType2").attr("disabled", true);
	 			$("#sportCheck2").prop("checked", false);
	 			$("#sportType2").val("");
	 			$("#sportCheck2").attr("disabled", true);
			}
		}
	});
	
	// 수정모드일 때 실적지원액 setting
	if(submitType == 'modify'){
		var sportYn = "${dt.ACEXP_SPORT_YN}";
		var actType = "${dt.ACMSLT_TYPE}";
		var sportAmt = "${dt.ACMSLT_SPORT_AMT}";
		var localeAmt = Number(sportAmt).toLocaleString("ko-KR");
		
		if(sportYn == "Y"){
			$("#sportCheck2").prop("checked", true);
			$("#sportType2").val(localeAmt);
			$("#sportType2").attr("readonly", false);
			$("#sportType1").attr("disabled", true);
 			$("#sportCheck1").prop("checked", false);
 			$("#sportCheck1").attr("disabled", true);
		} else {
			$("#sportCheck1").prop("checked", true);
			$("#sportType1").val(localeAmt);
			$("#sportType2").attr("disabled", true);
 			$("#sportCheck2").prop("checked", false);
 			$("#sportCheck2").attr("disabled", true);
		}
		
		// HRD 담당자역량개발은 정액/실비 둘다 가능해서  체크박스 둘 다 열어두기 + 정액 금액 setting
		if(actType == "02"){
			$("#sportType1").val("100,000");
			$("#sportCheck1").attr("disabled", false);
			$("#sportCheck2").attr("disabled", false);
		}
		
	}
	// 실비 입력 시 자리수 구분하기 및 숫자만 입력받기
	$("#sportType2").keydown(function(e) {
		fn_preventHan(event, $(this));
		return fn_onlyNum(event, $(this));
	});
	$("#sportType2").keyup(function(e) {
		var index = $("#actCd").val();
		var amount = $(this).val();
		 
		// 실비 0 입력 막기
		if(amount == 0){
			alert("0원은 입력할 수 없습니다.");
			$(this).val("");
		}
		
		amount = Number(amount.replaceAll(/[^0-9]/g, ''));
		
		if(index == "04"){
			if(amount > 1000000){
				alert("HRD 역량개발은 연 최대 1,000,000원까지 활동 지원금을 입력할 수 있습니다.");
				$(this).val(100000);
			}
		} else if(index == "08"){
			if(amount > 2000000){
				alert("판로개척/인력채용은 연 최대 2,000,000원까지 활동 지원금을 입력할 수 있습니다.");
				$(this).val("");
			}
		}
		const regExp = /[0-9]/g;
		const ele = event.target;
		if(regExp.test(ele.value)){
			let value = e.target.value;
			value = Number(value.replaceAll(/[^0-9]/g, ''));
			const formatValue = value.toLocaleString('ko-KR');
			$(this).val(formatValue);
		}
	});
	
	// 일시 직접 입력 막기
	$("#startDate, #endDate").keydown(function(event) {
		return false;
	});
	
	// 시작일시가 종료일시보다 늦으면 입력 막기
	$("#startDate").change(function(event) {
		var startDate = $(this).val();
		var endDate = $("#endDate").val();
		
		if(startDate != "" && endDate != ""){
			var startDate2 = new Date(startDate);
			var endDate2 = new Date(endDate);
			
			if(startDate2 > endDate2){
				alert("시작 일시는 종료 일시보다 빨라야 합니다.");
				$(this).val("");
				return false;
			}
		}
	});
	
	// 시작일시가 종료일시보다 늦으면 입력 막기
	$("#endDate").change(function(event) {
		var startDate = $("#startDate").val();
		var endDate = $(this).val();
		
		if(startDate != "" && endDate != ""){
			var startDate2 = new Date(startDate);
			var endDate2 = new Date(endDate);
			
			if(startDate2 > endDate2){
				alert("종료 일시는 시작 일시보다 늦어야 합니다.");
				$(this).val("");
				return false;
			}
		}
	});
});

function fn_changeSportType(){
	$("input[name='sportType']").change(function () {
		var yn = $("input[name='sportType']:checked").val();
		if(yn == "Y"){
			$("#sportYn").val("Y");
			$("#sportType1").attr("disabled", true);
			$("#sportType2").attr("disabled", false);
		} else {
			$("#sportYn").val("N");
			$("#sportType1").attr("disabled", false);
 			$("#sportType2").attr("disabled", true);
 			$("#sportType2").val("");
		}
	});
}

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_requestInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	var startDate = $("#startDate").val();
	if(startDate == ""){
		alert("시작일을 선택해 주세요.");
		$("#startDate").focus();
		return false;
	}
	
	var endDate = $("#endDate").val();
	if(endDate == ""){
		alert("종료일을 선택해 주세요.");
		$("#endDate").focus();
		return false;
	}
	// 시작일 종료일 올해만 선택가능
	var today = new Date();
	var year = today.getFullYear();
	if(startDate.substr(0,4) != year || endDate.substr(0,4) != year){
		alert("올해의 활동만 등록할 수 있습니다.(" + year + "-01-01 ~ " + year + "-12-31) 시작일 또는 종료일을 수정해 주세요.");
		return false;
	}
	
	// 활동지원금 실비/정액 체크
	var sportType = $("input[name='sportType']:checked").val();
	if(sportType == undefined){
		alert("활동지원금 유형을 선택해 주세요.");
		$("#sportCheck1").focus();
		return false;
	}
	
	// 활동지원금이 실비일 때 금액을 입력했는지 확인
	if(sportType == "Y"){
		sportVal = $("#sportType2").val();
		if(sportVal == ""){
			alert("실비 금액을 입력해 주세요.");
			$("#sportType2").focus();
			return false;
		}
	}
	
	var targetYn = $("#sportYn").val();
	
	// 실비일 때 증빙내역 확인
	if(targetYn == "Y"){
		var varObjLayer = $("#bill_total_layer li");
		if(varObjLayer.length == 0){
			alert("활동지원금 실비 청구시 증빙 내역은 필수입니다. 증빙서류를 첨부해 주세요.");
			$("#findFile_bill").focus();
			return false;
		}
	}
	
	var targetValue = $("input[name='sportAmt']:not(:disabled)").val();
	var parsedNumber = Number(targetValue.replace(/[^\d]/g, ''));
	$("input[name='sportAmt']:not(:disabled)").val(parsedNumber);
	
	fn_createMaskLayer();
	return true;
}
</script>
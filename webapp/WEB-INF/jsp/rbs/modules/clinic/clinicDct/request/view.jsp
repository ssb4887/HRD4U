<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	// 주치의가 지정이 되어 있을 때 주치의 지정 버튼 삭제(주치의 변경은 목록화면에서 하기)
	if($("#doctor").val() != ""){
		$(".appointDoc").hide();
	}
	// 주치의 지정 클릭 시 모달창 열기
	$("#open-modal01").on("click", function() {
		var doctorIdx = $("#doctor").val();
		if(doctorIdx != null){
			$("input[type='radio'][value='" + doctorIdx + "']").prop("checked", true);
		}
        $(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
    });
	
	// 전담주치의 지정하기
	$(".fn-search-submit").click(function () {
		if($("input[type='radio']:checked").length == 0){
			alert("전담주치의를 선택해 주세요.");
			return false;
		}
		
		var check = confirm("해당 주치의로 지정하시겠습니까?");
		if(!check) return false;
		
		var cliIdx = $("#cliIdx").val();
		var doctorIdx = $("input[type='radio']:checked").val();
		
		// 전담주치의 지정하기
		appointDoc(cliIdx, doctorIdx);
	});
	
	// 주치의 일괄지정 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("input[type='radio']").prop("checked", false);
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
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
	
	// 저장
	$(".fn_btn_submit").click(function(){
		try {
			var result = $("#slctnResult").val();
			if(result == ""){
				alert("기업 선정 심사표 항목을 모두 체크해 주세요.");
				return false;
			}
			var modifyUrl = "${REQUEST_MODIFY_URL}&reqIdx=${dt.REQ_IDX}&isUpateAll=n";
			$("#clinic_request").attr("action", modifyUrl);
		}catch(e){return false;}
		return true;
	});
	
	// 접수
	$(".btn-fn-accept").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 신청서를 접수하시겠습니까?");
			if(!varConfirm) return false;
			
			var doctorIdx = $("#doctor").val();
			if(doctorIdx == ""){
				alert("전담주치의가 지정되지 않았습니다. 전담주치의를 지정 후 접수해 주세요.");
				return false;
			}
			
			var accpetUrl = "${REQUEST_ACCEPT_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}&isWithdraw=n";
			$("#clinic_request").attr("action", accpetUrl);
		}catch(e){return false;}
		return true;
	});
 	
	// 반려
	$(".btn-fn-return").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 신청서를 반려처리 하시겠습니까?");
			if(!varConfirm) return false;
			if($("#doctorOpinion").val() == ""){
				alert("주치의 의견을 입력 후 반려처리해 주세요.");
				$("#doctorOpinion").focus();
				return false;
			}
			var returnUrl = "${REQUEST_RETURN_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}";
			$("#clinic_request").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
	
	// 부장이 최종승인 안하고 반려
	$(".btn-returnToAccept").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 신청서를 반려처리 하시겠습니까? 반려처리를 하면 해당 능력개발클리닉 신청서는 접수 상태로 돌아갑니다.");
			if(!varConfirm) return false;
			if($("#doctorOpinion").val() == ""){
				alert("주치의 의견을 입력 후 반려처리해 주세요.");
				$("#doctorOpinion").focus();
				return false;
			}
			var returnUrl = "${REQUEST_RETURN_TO_ACCEPT_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}";
			$("#clinic_request").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
	
	// 1차승인
	$(".btn-fn-firstApprove").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 신청서를 1차승인 하시겠습니까?");
			if(!varConfirm) return false;
			
			var slctnResult = $("#slctnResult").val();
			if(slctnResult == "F"){
				alert("기업 선정 심사결과가 FAIL인 기업은 1차 승인을 할 수 없습니다.");
				return false;
			}
			
			var returnUrl = "${REQUEST_FIRSTAPPROVE_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}";
			$("#clinic_request").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
	
	// HRD부서 주소 setting
	var blockCd = "${dt.CLI_LOCPLC_CD}";
	if(blockCd != ""){
		var pBlockCd = blockCd.substr(0, 2);
		var sidoSigungu = "";
		
		sidoSigungu += $("#sido option[value='" + pBlockCd + "']:eq(0)").text();
		sidoSigungu += " " + $("#sido option[value='" + blockCd + "']").text();
		$(".clinicAddr").text(sidoSigungu);
	}
	
	// 지부지사 이름 setting
	var insttIdx = "${dt.CLI_INSTT_IDX}";
	var insttName = $("#insttIdx option[value='" + insttIdx + "']:eq(0)").text();
	$("#insttName").text(insttName);

});

//선택한 신청서의 전담주치의 지정하기
function appointDoc(cliIdx, doctorIdx){
	$.ajax({
		url:'${REQUEST_APPOINTDOC_URL}',
		type: 'GET',
		data: {
			cliIdx : cliIdx,
			doctorIdx : doctorIdx
		},success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("해당 능력개발클리닉 신청서의 전담주치의가 지정되었습니다.");
		        location.reload();
			} else {
				alert("해당 능력개발클리닉 신청서 전담주치의 지정에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}
</script>
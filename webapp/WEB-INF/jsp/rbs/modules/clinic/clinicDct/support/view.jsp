<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	// 반려
	$(".btn-fn-return").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 지원금 신청서를 반려처리 하시겠습니까?");
			if(!varConfirm) return false;
			var returnUrl = "${SUPPORT_RETURN_URL}&sportIdx=${dt.SPORT_IDX}&cliIdx=${dt.CLI_IDX}";
			$("#clinic_support").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
	
	// 부장이 최종승인 안하고 반려
	$(".btn-returnToAccept").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 지원금 신청서를 반려처리 하시겠습니까? 반려처리를 하면 해당 능력개발클리닉 지원금 신청서는 접수 상태로 돌아갑니다.");
			if(!varConfirm) return false;
			if($("#doctorOpinion").val() == ""){
				alert("주치의 의견을 입력 후 반려처리해 주세요.");
				$("#doctorOpinion").focus();
				return false;
			}
			var returnUrl = "${SUPPORT_RETURN_TO_ACCEPT_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}";
			$("#clinic_support").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
	
});

</script>
<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	// 반려
	$(".btn-return").click(function(){
		try {
			var varConfirm = confirm("해당 훈련과정 표준개발 신청서를 반려 처리하시겠습니까?");
			if(!varConfirm) return false;
			
			if($("#doctorOpinion").val() == ""){
				alert("주치의 의견을 입력해 주세요.");
				$("#doctorOpinion").focus();
				return false;
			}
			
			var returnUrl = "${DEVELOP_RETURN_URL}";
			$("#fn_developInputForm").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
});
</script>
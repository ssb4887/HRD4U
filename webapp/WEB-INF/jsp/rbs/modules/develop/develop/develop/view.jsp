<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	<c:if test="${mngAuth || wrtAuth && dt.AUTH_MNG == '1'}">	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			var varConfirm = confirm("<spring:message code="message.select.delete.confirm"/>");
			if(!varConfirm) return false;
		}catch(e){return false;}
		return true;
	});
	</c:if>
	
	$(".accordion-wrapper > button").on("click", function() {
        if ($(this).next().css("display") == "none") {
            $(".accordion-wrapper > button").removeClass('active');
            $(".accordion-wrapper .accordion-list").slideUp(150);
            $(this).next().slideDown(150);
            $(this).addClass('active');

        } else {
            $(".accordion-wrapper>button").removeClass('active');
            $(".accordion-wrapper .accordion-list").slideUp(150);
        }
    });
	
	
	// 변경요청에 대한 반려
	$("#btn_return").click(function(){
		try {
			var varConfirm = confirm("해당 검토요청을 반려하시겠습니까?");
			if(!varConfirm) return false;
			
			if($("#corpOpinion").val() == ""){
				alert("기업 의견을 입력해 주세요.");
				$("#corpOpinion").focus();
				return false;
			}
			
			var returnUrl = "${DEVELOP_CONSIDERRETURN_URL}";
			$("#fn_developInputForm").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
});
</script>
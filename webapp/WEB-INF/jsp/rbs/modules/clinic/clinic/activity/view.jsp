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
});
</script>
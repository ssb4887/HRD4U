<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
var pageNumber = 1;
var index = 1;
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
	}
});
</script>
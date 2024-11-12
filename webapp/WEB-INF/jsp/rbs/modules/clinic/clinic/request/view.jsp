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
	
	// HRD부서 주소 setting
	var blockCd = "${dt.CLI_LOCPLC_CD}";
	if(blockCd != ""){
		var pBlockCd = blockCd.substr(0, 2);
		var sidoSigungu = "";
		
		sidoSigungu += $("#sido option[value='" + pBlockCd + "']").text();
		sidoSigungu += " " + $("#sido option[value='" + blockCd + "']").text();
		$(".clinicAddr").text(sidoSigungu);
	}
	
	// 지부지사 이름 setting
	var insttIdx = "${dt.CLI_INSTT_IDX}";
	var insttName = $("#insttIdx option[value='" + insttIdx + "']:eq(0)").text();
	$("#insttName").text(insttName);
});
</script>
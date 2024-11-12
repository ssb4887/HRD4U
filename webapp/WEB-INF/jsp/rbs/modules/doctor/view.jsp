<%@ include file="../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	// 주치의 소속기관 변경여부 확인
	var isChange = "${isChange}";
	
	if(isChange == 1){
		alert("해당 주치의의 소속기관이 변경되었습니다. 공단소속을 확인하고 관할구역을 변경해 주세요.");
	}
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			var varConfirm = confirm("<spring:message code="message.select.delete.confirm"/>");
			if(!varConfirm) return false;
		}catch(e){return false;}
		return true;
	});
});
</script>
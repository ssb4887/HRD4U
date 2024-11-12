<%@ include file="../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			var varConfirm = confirm("지부지사를 삭제하면 소속된 기업회원 및 주치의 소속이 삭제됩니다. 삭제하시겠습니까?");
			console.log(varConfirm);
			if(!varConfirm) return false;
		}catch(e){alert(e); return false;}
		return true;
	});
	
	// 하나의 지부지사가 특정 시의 모든 군/구를 관할할 때 관할구역 문구를 전 지역으로 변경
	$("#allSelected").parent().text("전 지역");
});
</script>
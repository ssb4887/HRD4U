<%@ include file="../../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
	
	$("#fn_btn_search").click(function(){
		var minTrTm = $("#is_trtm1").val();
		var maxTrTm = $("#is_trtm2").val();
		

		if( minTrTm == '0' && maxTrTm == '0'){
			alert("최소 훈련시간과 최대 훈련시간은 둘다 0을 입력할 수 없습니다.");
			return false;
		}else if(minTrTm != '0' && maxTrTm == '0'){
			alert("최대 훈련시간은 0을 입력할 수 없습니다.");
			return false;
		}else if(maxTrTm != '' && Number(minTrTm) > Number(maxTrTm)){
			alert("최대 훈련시간에 최소 훈련시간보다 큰 값을 입력할 수 없습니다.");
			return false;
		};	
	});
	
	$(".contents-title").text("표준과정 목록");
});
</script>
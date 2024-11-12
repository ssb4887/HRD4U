<%@ include file="../../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
	// 안쓰는 신청상태 삭제
	$("#is_confmStatus").find("option[value='5']").hide();
	$("#is_confmStatus").find("option[value='7']").hide();
	$("#is_confmStatus").find("option[value='20']").hide();
	$("#is_confmStatus").find("option[value='35']").hide();
	$("#is_confmStatus").find("option[value='50']").hide();
	$("#is_confmStatus").find("option[value='53']").hide();
	$("#is_confmStatus").find("option[value='70']").hide();
	$("#is_confmStatus").find("option[value='72']").hide();
	$("#is_confmStatus").find("option[value='73']").hide();
	
	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
});

</script>
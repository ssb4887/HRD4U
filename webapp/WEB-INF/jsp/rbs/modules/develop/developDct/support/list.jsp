<%@ include file="../../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
	// 안쓰는 신청상태 삭제
	$("#is_confmStatus").find("option[value='5']").hide();
	$("#is_confmStatus").find("option[value='7']").hide();
	$("#is_confmStatus").find("option[value='9']").hide();
	$("#is_confmStatus").find("option[value='20']").hide();
	$("#is_confmStatus").find("option[value='28']").hide();
	$("#is_confmStatus").find("option[value='33']").hide();
	$("#is_confmStatus").find("option[value='37']").hide();
	$("#is_confmStatus").find("option[value='42']").hide();
	$("#is_confmStatus").find("option[value='53']").hide();
	$("#is_confmStatus").find("option[value='70']").hide();
	$("#is_confmStatus").find("option[value='72']").hide();
	$("#is_confmStatus").find("option[value='73']").hide();
	
	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
	
	// 안쓰는 사업 우형 삭제
	$("#is_cnslType").find("option[value='4'], option[value='5'], option[value='6']").hide();
});

</script>
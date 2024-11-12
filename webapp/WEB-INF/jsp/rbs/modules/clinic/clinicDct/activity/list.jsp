<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	var year = "${year}";
	
	// 연차 검색 항목 채우기
	for(var i = 1; i <= 3; i++){
		$("#is_year").append($('<option>', {value: i, text: i + "년"}));
		if(year != "" && i == year){
			$("#is_year option[value='" + i + "']").prop("selected", true);
		}
	}
	
	// 지부지사 검색창에서 본부 항목 삭제
	$("#is_cliInsttIdx").find("option[value='33']").hide();
	
	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
});
</script>

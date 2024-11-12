<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">

$(function(){
	// 상태 검색 목록에서 임시저장, 요청, 회수, 변경반려, 변경승인, 중도포기 삭제
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
	
	// 지부지사 검색창에서 본부 항목 삭제
	$("#is_cliInsttIdx").find("option[value='33']").hide();

	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
		}catch(e){
			alert("에러 발생 - 담당자에게 문의하시기 바랍니다.")
		}
	});
	
	var year = "${year}";
	
	// 연차 검색 항목 채우기
	for(var i = 1; i <= 3; i++){
		$("#is_year").append($('<option>', {value: i, text: i + "년"}));
		if(year != "" && i == year){
			$("#is_year option[value='" + i + "']").prop("selected", true);
		}
	}
	
	// 일괄접수 버튼 클릭
	$(".fn_acceptAll").click(function () {
		var cliList = [];
		var sptList = [];
		
		if($("input[type='checkbox']:checked").length == 0){
			alert("일괄 접수할 신청서를 선택해 주세요.");
			return false;
		}
		$("input[type='checkbox']:checked").each(function () {
			var cliIdx = $(this).attr("data-cli");
			var sptIdx = $(this).val();
			
			cliList.push(cliIdx);
			sptList.push(sptIdx);
			
		});
		
		// 일괄 접수 처리하기
		acceptProc(cliList, sptList);
		
	});
})


//선택한 신청서 모두 접수 처리하기
function acceptProc(cliList, sptList){
	$.ajax({
		url:'${SUPPORT_ACCEPT_ALL_URL}',
		type: 'GET',
		data: {
			cliList : cliList.toString(),
			sptList : sptList.toString()
		},success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("능력개발클리닉 지원금 신청서가 일괄로 접수되었습니다.");
		        location.reload();
			} else {
				alert("능력개발클리닉 성과보고서 일괄 접수에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}
</script>

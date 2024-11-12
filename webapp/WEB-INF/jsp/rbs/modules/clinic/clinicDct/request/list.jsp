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
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
		}catch(e){
			alert("에러 발생 - 담당자에게 문의하시기 바랍니다.")
		}
	});
	
	var changeCliIdx = 0;
	
	var usertypeIdx = "${loginVO.usertypeIdx}";
	
	// 주치의 변경 버튼 클릭
	$("[id^='accept']").on("click", function() {
		// 이미 지정된 주치의가 있을 때 해당 주치의 배정 여부에 check 하기
		var docIdx = $(this).attr("data-doc");
		changeCliIdx = $(this).attr("data-cli");
		
		if(usertypeIdx == "55"){
			// 최고관리자가 변경 버튼을 클릭할 때 선택한 신청서의 지부지사에 따라 주치의 목록 변경하기
			var insttIdx = $(this).attr("data-instt");
			
			$("[id^='docInfo'][data-instt='" + insttIdx + "']").show();
			$("[id^='docInfo'][data-instt!='" + insttIdx + "']").hide();
		}
		
		if(docIdx != ""){
			$("[id^='doctorCheckChange']").each(function () {
				if($(this).val() == docIdx){
					$(this).prop("checked", true);
				}
			});
		}
		// 모달창 열기
		$(".mask2").fadeIn(150, function() {
        	$("#modal-action02").show();
        });
	});
	
	// 주치의 일괄지정 클릭 시 모달창 열기
	$("#open-modal01").on("click", function() {
		if($("input[type='checkbox']:checked").length == 0){
			alert("전담주치의을 지정할 기업을 선택해 주세요.");
			return false;
		}

		if(usertypeIdx == "55"){
			// 최고관리자가 주치의 지정 버튼을 클릭할 때(일괄지정) 선택한 신청서의 지부지사가 다르면 return false
			var isInsttEqual = true;
			var firstInstt = "";
			$("input[type='checkbox']:checked").each(function (index, element) {
				if(index == 0){
					firstInstt = $(this).attr("data-instt");
				} else {
					if($(this).attr("data-instt") != firstVal){
						isInsttEqual = false;
						return false;
					}
				}				
			});
			
			if(!isInsttEqual){
				alert("선택한 기업의 클리닉 지부지사가 다릅니다. 클리닉 지부지사가 동일한 기업만 선택해 주세요.");
				return false;
			}
			
			// 신청서의 지부지사에 따라 주치의 목록 변경하기
			$("[id^='docInfo'][data-instt='" + firstInstt + "']").show();
			$("[id^='docInfo'][data-instt!='" + firstInstt + "']").hide();
		}
		
		// 일괄지정할 기업들이 지정된 주치의가 모두 같은지 확인
		var isAllEqual = true;
		var firstVal = "";
		$("input[type='checkbox']:checked").each(function (index, element) {
			if(index == 0){
				firstVal = $(this).attr("data-doc");
			} else {
				if($(this).attr("data-doc") != firstVal){
					isAllEqual = false;
					return false;
				}
			}				
		});
		
		// 일괄지정할 기업들의 지정된 주치의가 모두 같으면 해당 주치의를 체크하기
		if(isAllEqual){
			$("[id^='doctorCheckAppoint'][value='" + firstVal + "']").prop("checked", true);
		}
		
        $(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
    });
	
	// 전담주치의 일괄지정하기
	$(".fn-search-submit").click(function () {
		if($("[id^='doctorCheckAppoint']:checked").length == 0){
			alert("전담주치의를 선택해 주세요.");
			return false;
		}
		
		var check = confirm("해당 주치의로 지정하시겠습니까?");
		if(!check) return false;
		var cliList = [];
		var doctorList = [];
		
		$("input[type='checkbox']:checked").each(function () {
			var cliIdx = $(this).attr("data-cli");
			var doctorIdx = $("[id^='doctorCheckAppoint']:checked").val();
			
			cliList.push(cliIdx);
			doctorList.push(doctorIdx);
		});
		
		// 전담주치의 지정하기
		appointDoc(cliList, doctorList);
	});
	
	// 전담주치의 변경하기
	$(".fn-search-submit2").click(function () {
		if($("[id^='doctorCheckChange']:checked").length == 0){
			alert("전담주치의를 선택해 주세요.");
			return false;
		}
		
		var check = confirm("해당 주치의로 지정하시겠습니까?");
		if(!check) return false;
		var cliList = [];
		var doctorList = [];
		
		var doctorIdx = $("[id^='doctorCheckChange']:checked").val();
		
		cliList.push(changeCliIdx);
		doctorList.push(doctorIdx);
		
		// 전담주치의 지정하기
		appointDoc(cliList, doctorList);
	});
	
	// 주치의 일괄지정 모달창 닫기
    $("#modal-action01 .modal-close, .fn-search-cancel").on("click", function() {
    	$("input[type='radio']").prop("checked", false);
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
	// 주치의 변경 모달창 닫기
    $("#modal-action02 .modal-close2, .fn-search-cancel2").on("click", function() {
    	$("input[type='radio']").prop("checked", false);
        $("#modal-action02").hide();
        $(".mask2").fadeOut(150);
    });
	
	// 신청서 목록 컬럼에 접수 버튼이 추가될 때 아래 코드 사용
	/* $(".fn_accept").click(function () {
		var cliList = [];
		var reqList = [];
		
		var cliIdx = checkbox에 있는 data-cli 가져오기;
		var reqIdx = checkbox에 있는 value 가져오기;
		
		cliList.push(cliIdx);
		reqList.push(reqIdx);
		
		acceptProc(cliList, reqList);
	}); */
	
	// 일괄접수 버튼 클릭
	$(".fn_acceptAll").click(function () {
		var cliList = [];
		var reqList = [];
		var docCheck = true;
		
		var varConfirm = confirm("선택한 능력개발클리닉 신청서를 일괄 접수하시겠습니까?");
		if(!varConfirm) return false;
		
		if($("input[type='checkbox']:checked").length == 0){
			alert("일괄 접수할 신청서를 선택해 주세요.");
			return false;
		}
		$("input[type='checkbox']:checked").each(function () {
			var docIdx = $(this).attr("data-doc");
			var bplNm = $(this).parent().next().next().find("#bplNm").text();
			// 전담주치의가 지정되지 않은 신청서는 접수할 수 없음
			if(docIdx == ""){
				alert(bplNm + "은/는 전담주치의가 지정되지 않았습니다. 전담주치의를 지정하고 접수해 주세요.");
				$(this).prop("checked", false);
				docCheck = false;
				return false;
			}
			var confmStatus = $(this).attr("data-status");
			if(confmStatus != '10'){
				alert("'신청' 상태인 신청서만 접수할 수 있습니다. '신청' 상태가 아닌 신청서는 체크를 해제해 주세요.");
			}
			var cliIdx = $(this).attr("data-cli");
			var reqIdx = $(this).val();
			
			cliList.push(cliIdx);
			reqList.push(reqIdx);
			
		});
		
		if(!docCheck) return false;
		// 일괄 접수 처리하기
		acceptProc(cliList, reqList);
		
	});
})

$("#modal-action01").show();

// 선택한 신청서 모두 접수 처리하기
function acceptProc(cliList, reqList){
	$.ajax({
		url:'${REQUEST_ACCEPT_ALL_URL}',
		type: 'GET',
		data: {
			cliList : cliList.toString(),
			reqList : reqList.toString()
		},success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("능력개발클리닉 신청서가 일괄로 접수되었습니다.");
				$("input[type='radio']").prop("checked", false);
		        $("#modal-action01").hide();
		        $(".mask").fadeOut(150);
		        location.reload();
			} else {
				alert("능력개발클리닉 신청서 일괄 접수에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}

//선택한 신청서의 전담주치의 모두 지정하기
function appointDoc(cliList, doctorList){
	$.ajax({
		url:'${REQUEST_APPOINTDOCAll_URL}',
		type: 'GET',
		data: {
			cliList : cliList.toString(),
			doctorList : doctorList.toString()
		},success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("능력개발클리닉 신청서의 전담주치의가 지정되었습니다.");
		        location.reload();
			} else {
				alert("능력개발클리닉 신청서 전담주치의 지정에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}
</script>

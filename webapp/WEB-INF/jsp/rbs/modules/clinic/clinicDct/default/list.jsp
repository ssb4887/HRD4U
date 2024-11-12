<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">

$(function(){
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	
	// 신청서 목록 컬럼에 주치의 지정이 추가될 때 아래 코드 사용
	/* $("open-modal02").on("click", function() {
		// 이미 지정된 주치의가 있을 때 해당 주치의 배정 여부에 check 하기
		var docIdx = $(this).parent().next("td").find("a").attr("data-doc");
		if(docIdx != ""){
			$("input[type='radio']").each(function () {
				if($(this).attr("data-idx") == docIdx){
					$(this).prop("checked", true);
				}
			});
		}
		// 모달창 열기
		$(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
	}); */
	
	// 주치의 일괄지정 클릭 시 모달창 열기
	$("#open-modal01").on("click", function() {
		if($("input[type='checkbox']:checked").length == 0){
			alert("전담주치의을 지정할 기업을 선택해 주세요.");
			return false;
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
			$("input[type='radio'][value='" + firstVal + "']").prop("checked", true);
		}
		
        $(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
    });
	
	// 전담주치의 일괄지정하기
	$(".fn-search-submit").click(function () {
		if($("input[type='radio']:checked").length == 0){
			alert("전담주치의를 선택해 주세요.");
			return false;
		}
		
		var check = confirm("해당 주치의로 지정하시겠습니까?");
		if(!check) return false;
		var cliList = [];
		var doctorList = [];
		
		$("input[type='checkbox']:checked").each(function () {
			var cliIdx = $(this).attr("data-cli");
			var doctorIdx = $("input[type='radio']:checked").val();
			
			cliList.push(cliIdx);
			doctorList.push(doctorIdx);
		});
		
		// 전담주치의 지정하기
		appointDoc(cliList, doctorList);
	});
	
	// 주치의 일괄지정 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("input[type='radio']").prop("checked", false);
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
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
		
		if($("input[type='checkbox']:checked").length == 0){
			alert("일괄 접수할 신청서를 선택해 주세요.");
			return false;
		}
		$("input[type='checkbox']:checked").each(function () {
			var confmStatus = $(this).attr("data-status");
			if(confmStatus != '10'){
				alert("'신청' 상태인 신청서만 접수할 수 있습니다. '신청' 상태가 아닌 신청서는 체크를 해제해 주세요.")
			}
			var cliIdx = $(this).attr("data-cli");
			var reqIdx = $(this).val();
			
			cliList.push(cliIdx);
			reqList.push(reqIdx);
			
		});
		
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
				alert("능력개발클리닉 신청서의 전담주치의가 일괄 지정되었습니다.");
		        location.reload();
			} else {
				alert("능력개발클리닉 신청서 전담주치의 일괄 지정에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}
</script>

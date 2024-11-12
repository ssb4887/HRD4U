<%@ include file="../../../include/commonTop.jsp"%>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<script type="text/javascript">
$(function(){	
	// dialog
	fn_dialog.init("fn_list");
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			console.log("오는가");
			fn_listDeleteFormSubmit("<c:out value="${listFormId}"/>", $(this).attr("href"));
			console.log("아닌가");
		}catch(e){}
		return false;
	});

	// 소속 일괄변경 모달창 열기
	$("#open-modal01").on("click", function() {
		if($("input[type='checkbox']:checked").length == 0){
			alert("소속을 변경할 직원을 선택해 주세요.");
			return false;
		}
        $(".mask").fadeIn(150, function() {
        	var innerHtml = "";
        	$("input[type='checkbox']:checked").each(function() {
				var memberIdx = $(this).val();
				var doctorIdx = $(this).attr("data-doc");
				var memName = $(this).parent().parent().find("td").eq(4).text();
				var nowInfo = $(this).parent().parent().find("td").eq(7).text();
				
				innerHtml += '<p id="member_' + memberIdx + '" class="word" data-idx="' + memberIdx + '" data-doc="' + doctorIdx + '">' + memName + ' : ' + nowInfo + '<button type="button" id="fn_member_delete" class="btn-delete" onclick="fn_member_delete(' + memberIdx + ');"></button></p>';
        	});
        	$("#modal-action01").find("#memberList").append(innerHtml);
        	$("#memberList").find("#member_on").remove();
        	$("#modal-action01").show();
        });
    });
	
	// 권한 일괄변경 저장하기
	$(".fn-search-submit").click(function () {
		var count = $("p[id^='member_']").length;
		var check = confirm(count + "건의 직원 공단소속을 변경하시겠습니까?");
		if(!check) return false;
		
		var memberList = [];
		var doctorList = [];
		$("#memberList").find("p").each(function () {
			var memberIdx = $(this).attr("data-idx");
			var doctorIdx = $(this).attr("data-doc");
			memberList.push(memberIdx);
			doctorList.push(doctorIdx);
		});
		updateAll(memberList, doctorList);		
	});
	
	// 권한 일괄변경 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("#status option:eq(0)").prop("selected", true);
    	$("#memberList").empty();
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
});

//일괄변경할 직원 삭제
function fn_member_delete(memberIdx){
	$("#member_" + memberIdx).remove();
}

// 민간센터의 권한 일괄변경하기
function updateAll(memberList, doctorList){
	var status = $("#status option:selected").val();
	
	$.ajax({
		url:'${URL_LISTEMPLOYPROC}',
		type: 'GET',
		data: {
			status : status,
			memberList : memberList.toString(),
			doctorList : doctorList.toString()
		},
		success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("공단소속이 일괄로 변경되었습니다.");
				$("#status option:eq(0)").prop("selected", true);
		    	$("#memberList").empty();
		        $("#modal-action01").hide();
		        $(".mask").fadeOut(150);
		        location.reload();
			} else {
				alert("공단소속이 일괄 변경에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}

//삭제
function fn_listDeleteFormSubmit(theFormId, theAction){
	try {
		if(!fn_isValFill(theFormId) || !fn_isValFill(theAction)) return false;
		// 선택
		if(!fn_checkElementChecked("select", "삭제")) return false;
		// 삭제여부
		var varConfirm = confirm("<spring:message code="message.select.all.delete.confirm"/>");
		if(!varConfirm) return false;
		
		$("#" + theFormId).attr("action", theAction);

		$("input[name='select']").prop("disabled", false);
		$("#" + theFormId).submit();
	}catch(e){}
	
	return true;
}

</script>
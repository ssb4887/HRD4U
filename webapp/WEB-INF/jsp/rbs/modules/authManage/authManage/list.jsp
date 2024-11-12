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
			fn_listDeleteFormSubmit("<c:out value="${listFormId}"/>", $(this).attr("href"));
		}catch(e){}
		return false;
	});
	
	// 권한 일괄변경 모달창 열기
	$("#open-modal01").on("click", function() {
		if($("input[type='checkbox']:checked").length == 0){
			alert("권한을 변경할 회원을 선택해 주세요.");
			return false;
		}
        $(".mask").fadeIn(150, function() {
        	var innerHtml = "";
        	$("input[type='checkbox']:checked").each(function() {
				var authMemberIdx = $(this).val();
				var memberName = $(this).parent().parent().find("td").eq(4).text();
				var regiment = $(this).parent().parent().find("td").eq(5).text();
				/* var position = $(this).parent().parent().find("td").eq(6).text(); */
				
				innerHtml += '<p id="member_' + authMemberIdx + '" class="word" data-idx="' + authMemberIdx + '">' + '<strong>' + memberName + ' : ' + regiment + '</strong><button type="button" id="fn_member_delete" class="btn-delete" onclick="fn_member_delete(' + authMemberIdx + ');"></button></p>';
        	});
        	$("#modal-action01").find("#memberList").append(innerHtml);
        	$("#memberList").find("#member_on").remove();
        	$("#modal-action01").show();
        });
    });
	
	// 권한 일괄변경 저장하기
	$(".fn-search-submit").click(function () {
		if(!$("#authGroup option:selected").val()){
			alert("권한 그룹을 선택해 주세요.");
			$("#authGroup").focus();
			return false;
		}
		var memberList = [];
		$("#memberList").find("p").each(function () {
			var memberIdx = $(this).attr("data-idx");
			memberList.push(memberIdx);
		});
		updateAll(memberList);		
	});
	
	// 권한 일괄변경 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("#authGroup option:eq(0)").prop("selected", true);
    	$("#memberList").empty();
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });

	
});
//등록
function fn_memSearch(){
	try{
		fn_dialog.open("","${URL_INPUT}", 800,700,"mbrs");		
	}catch(e){}
	
	return false;
}
//일괄등록
function fn_inputGroup(){
	try{
		fn_dialog.open("","${URL_INPUTGROUP}", 800,700,"mbrs");		
	}catch(e){}
	
	return false;
}
//수정
function fn_modiMem(item){
	try{
		fn_dialog.open("", "${URL_MODIFY}&${settingInfo.idx_name}=" + item  , 800,700, "modi");
	}catch(e){}
	
	return false;
}

//삭제
function fn_listDeleteFormSubmit(theFormId, theAction){
	try {
		console.log("여긴가");
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

// 일괄변경할 직원 삭제
function fn_member_delete(authMemberIdx){
	$("#member_" + authMemberIdx).remove();
}
// 일괄변경할 직원 목록 가져오기
function updateAll(memberList){
	var authIdx = $("#authGroup option:selected").val();
	$.ajax({
		url:'${URL_UPDATEALL}',
		type: 'GET',
		data: {
			authIdx : authIdx,
			memberList : memberList.toString(),
		},
		success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("권한그룹이 일괄로 변경되었습니다.");
				$("#authGroup option:eq(0)").prop("selected", true);
		    	$("#memberList").empty();
		        $("#modal-action01").hide();
		        $(".mask").fadeOut(150);
		        location.reload();
			} else {
				alert("권한그룹이 일괄 변경에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}
</script>
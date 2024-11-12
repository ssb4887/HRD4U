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
	
	// 권한 일괄변경 모달창 열기
	$("#open-modal01").on("click", function() {
		if($("input[type='checkbox']:checked").length == 0){
			alert("소속기관 변경할 회원을 선택해 주세요.");
			return false;
		}
        $(".mask").fadeIn(150, function() {
        	var innerHtml = "";
        	$("input[type='checkbox']:checked").each(function() {
				var psitnIdx = $(this).val();
				var bplNo = $(this).attr("data-auth");
				var memberIdx = $(this).attr("data-member");
				var memberName = $(this).parent().parent().find("td").eq(4).text();
				
				innerHtml += '<p id="psitn_' + psitnIdx + '" class="word" data-member="' + memberIdx + '"data-idx="' + psitnIdx  + '" data-auth="' + bplNo + '">' + '<strong>' + memberName + '</strong><button type="button" id="fn_member_delete" class="btn-delete" onclick="fn_member_delete(' + psitnIdx + ');"></button></p>';
        	});
        	$("#modal-action01").find("#memberList").append(innerHtml);
        	$("#memberList").find("#psitn_on").remove();
        	$("#modal-action01").show();
        });
    });
	
	// 소속기관 일괄변경 저장하기
	$(".fn-search-submit").click(function () {
		if(!$("#status option:selected").val()){
			alert("승인 및 반려를 선택해 주세요.");
			$("#status").focus();
			return false;
		}
		
		var count = $("p[id^='psitn_']").length;
		var check = confirm(count + "건의 기업회원 소속기관을 변경하시겠습니까?");
		if(!check) return false;
		
		var psitnList = [];
		var bplList = [];
		var memberList = [];
		$("#memberList").find("p").each(function () {
			var psitnIdx = $(this).attr("data-idx");
			var bolNo = $(this).attr("data-auth");
			var memberIdx = $(this).attr("data-member");
			psitnList.push(psitnIdx);
			bplList.push(bolNo);
			memberList.push(memberIdx);
		});
		updateAll(psitnList, bplList,memberList);		
	});
	
	// 소속기관 일괄변경 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("#status option:eq(0)").prop("selected", true);
    	$("#memberList").empty();
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
	

	
});


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

//일괄변경할 직원 삭제
function fn_member_delete(bplNoIdx){
	$("#psitn_" + bplNoIdx).remove();
}

// 민간센터의 권한 일괄변경하기
function updateAll(psitnList, bplList, memberList){
	var status = $("#status option:selected").val();
	
	$.ajax({
		url:'${URL_LISTCORPPROC}',
		type: 'GET',
		data: {
			status : status,
			psitnList : psitnList.toString(),
			bplList : bplList.toString(),
			memberList : memberList.toString()
		},
		success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("소속기관이 일괄로 변경되었습니다.");
				$("#status option:eq(0)").prop("selected", true);
		    	$("#memberList").empty();
		        $("#modal-action01").hide();
		        $(".mask").fadeOut(150);
		        location.reload();
			} else {
				alert("소속기관 일괄 변경에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}

</script>
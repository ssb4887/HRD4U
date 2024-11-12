<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${centerItemInfo.items}" itemOrder="${centerItemInfo[itemOrderName]}"/>
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
			alert("권한을 변경할 민간센터를 선택해 주세요.");
			return false;
		}
        $(".mask").fadeIn(150, function() {
        	var innerHtml = "";
        	$("input[type='checkbox']:checked").each(function() {
				var memberIdx = $(this).val();
				var authReqIdx = $(this).attr("data-auth");
				var memberName = $(this).parent().parent().find("td").eq(4).text();
				var centerName = $(this).parent().parent().find("td").eq(5).text();
				
				innerHtml += '<p id="member_' + memberIdx + '" class="word" data-idx="' + memberIdx + '" data-auth="' + authReqIdx + '">' + '<strong>' + memberName + '</strong><button type="button" id="fn_member_delete" class="btn-delete" onclick="fn_member_delete(' + memberIdx + ');"></button></p>';
        	});
        	$("#modal-action01").find("#centerList").append(innerHtml);
        	$("#centerList").find("#member_on").remove();
        	$("#modal-action01").show();
        });
    });
	
	// 권한 일괄변경 저장하기
	$(".fn-search-submit").click(function () {
		if($("#status").val() == '1'){
			var sdate = $("#startDate").val();
			var edate = $("#endDate").val();
			var auth = $("#authList option:selected").val();
			
			if(sdate == ""){
				alert("시작일 값을 입력해 주십시오.");
				return false;
			}
			if(edate == ""){
				alert("종료일 값을 입력해 주십시오.");
				return false;
			}
			if(auth == ""){
				alert("권한그룹을 선택해 주십시오.");
				return false;
			} 
		}
		
		var count = $("p[id^='member_']").length;
		var check = confirm(count + "건의 민간센터 권한을 변경하시겠습니까?");
		if(!check) return false;
		
		var centerList = [];
		var authReqList = [];
		$("#centerList").find("p").each(function () {
			var memberIdx = $(this).attr("data-idx");
			var authReqIdx = $(this).attr("data-auth");
			centerList.push(memberIdx);
			authReqList.push(authReqIdx);
		});
		updateAll(centerList, authReqList);		
	});
	
	// 권한 일괄변경 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("#authGroup option:eq(0)").prop("selected", true);
    	$("#status option:eq(0)").prop("selected", true);
    	$("#startDate").val("");
    	$("#endDate").val("");
    	$("#centerList").empty();
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
	//달력 기간 입력시 기간 전체 입력
	$("#fn_btn_search").click(function(){
		var dateRegi1 = $("#is_dateRegi1").val();
		var dateRegi2 = $("#is_dateRegi2").val();
		var date1 = $("#is_date1").val();
		var date2 = $("#is_date2").val();
		
		if(dateRegi1 != "" && dateRegi2 == ""){
			alert("신청 종료일을 입력해주세요.");
			return false;
		}
		
		if(dateRegi1 == "" && dateRegi2 != ""){
			alert("신청 시작일을 입력해주세요.");
			return false;
		}
		
		if(date1 != "" && date2 == ""){
			alert("기간 종료일을 입력해주세요.");
			return false;
		}
		
		if(date1 == "" && date2 != ""){
			alert("기간 시작일을 입력해주세요.");
			return false;
		}
		
	});
	
 	// 달력 선택
	try{$("#fn_btn_startDate").click(function(event){displayCalendar($('#startDate'),'', this);return false;});}catch(e){}
	try{$("#fn_btn_endDate").click(function(event){displayCalendar($('#endDate'),'', this);return false;});}catch(e){}
	
	// 검색에서 달력선택
	try{$("[id^='fn_btn_is']").click(function(event){
		var itemId = $(this).attr("data-idName");
		displayCalendar($('#'+itemId),'', this);
		return false;
		});
	}catch(e){}
	
});

//일괄변경할 직원 삭제
function fn_member_delete(memberIdx){
	$("#member_" + memberIdx).remove();
}

// 민간센터의 권한 일괄변경하기
function updateAll(memberList, authReqList){
	var authIdx = $("#authList option:selected").val();
	var status = $("#status").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val()
	
	$.ajax({
		url:'${URL_LISTCENTERPROC}',
		type: 'GET',
		data: {
			authIdx : authIdx,
			status : status,
			startDate : startDate,
			endDate : endDate,
			memberList : memberList.toString(),
			authReqList : authReqList.toString()
		},
		success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("권한그룹이 일괄로 변경되었습니다.");
				$("#authList option:eq(0)").prop("selected", true);
				$("#status option:eq(0)").prop("selected", true);
				$("#startDate").val("");
		    	$("#endDate").val("");
		    	$("#centerList").empty();
		        $("#modal-action01").hide();
		        $(".mask").fadeOut(150);
		        location.reload();
			} else {
				alert("권한그룹 일괄 변경에 실패했습니다.");
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
<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="listFormId" value="fn_areaActListForm"/>
<c:set var="itemOrderName" value="regiproc_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	/* <itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_regiInputReset(); */
	
	// reset
	$("#regiProc .fn_btn_reset").click(function(){
		try {
			$("#regiProc").reset();
			fn_regiInputReset();
		}catch(e){alert(e); return false;}
	});
	
	
		
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
	
	
	// 등록기간 모달창 열기
	$("#open-modal01").on("click", function() {
		/* if($("input[type='checkbox']:checked").length == 0){
			alert("소속기관 변경할 회원을 선택해 주세요.");
			return false;
		} */
        $(".mask").fadeIn(150, function() {
        	var innerHtml = "";
        	
        	var baseStart = $("#startDate").val();
        	var baseEnd = $("#endDate").val()
        	console.log(baseStart + "/////" + baseEnd)
        	$("#startDate").attr("value",baseStart);
        	$("#endDate").text(baseEnd);
        	
        	$("#modal-action01").show();
        });
    });
	
	// 등록기간 저장하기
	 $(".fn-search-submit").click(function () {
		var preStart = $("#startDate").val();
		var preEnd = $("#endDate").val()
		var start = preStart.replaceAll("-","")
		var end = preEnd.replaceAll("-","")
		$("#start").attr("value", start);
		$("#end").attr("value", end);
		fn_regiInputFormSubmit()		
	}); 
	
	// 등록기간 모달창 닫기
    $("#modal-action01 .btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("#status option:eq(0)").prop("selected", true);
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
	
 	// pdf 일괄다운로드(clipReport)
	$(".clipReport").on("click", function() {
		try {
			if($("input[type='checkbox']:checked").length == 0){
				alert("다운로드 원하는 게시물을 선택해 주세요.");
				return false;
			}
			
			 fn_report("<c:out value="${listFormId}"/>", $(".clipReport").attr("href"));
				
		}catch(e){}
		return false;
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

//일괄다운로드
async function fn_report(theFormId, theAction){
	
	var aracList = [];
	var nameList = [];
	$("input[type='checkbox']:checked").each(function() {
		var aractplnIdx = $(this).val();
		var aracName = $(this).attr("data-name");
		if(aractplnIdx !="on"){
			aracList.push(aractplnIdx);
			nameList.push(aracName);
		}
	});
	let form_ = document.getElementById(theFormId);
	console.log('form id: ', theFormId)
	console.log('form: ', form_)
	
	
	
	for(var i = 0; i < aracList.length; i++){
	 	$("#aracList").prop("value",aracList[i]);
	 	var name = nameList[i];
	 	let response = await fetch(theAction, {
			method: 'POST',
			body: new FormData(form_)
		})
		.then( res => res.blob())
		.then( blob => {
			let file_ = window.URL.createObjectURL(blob);
			let a_ = document.createElement('a');
			a_.href= file_;
			a_.setAttribute('download', name +'.pdf');
			document.body.appendChild(a_);
			a_.click();
			a_.remove();
		})	
	 }
}

function fn_regiInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_regiInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>
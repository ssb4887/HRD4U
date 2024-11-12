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

</script>
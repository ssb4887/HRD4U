<script type="text/javascript">
$(function(){	
	// dialog
	fn_dialog.init("fn_list");
	
	// 상태 선택 목록에서 임시저장, 회수 삭제
	$("#is_confmStatus").find("option[value='5']").hide();
	$("#is_confmStatus").find("option[value='20']").hide();
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	// 휴지통
	$(".fn_btn_deleteList").click(function(){
		try {
			<c:choose>
				<c:when test="${!empty deleteList_dialog_title}">
				var varTitle = "<c:out value="${deleteList_dialog_title}"/>";
				</c:when>
				<c:otherwise>
				var varTitle = "<c:out value="${settingInfo.deleteList_title}"/>";
				</c:otherwise>
			</c:choose>
			<c:if test="${!empty deleteList_dialog_addTitle}">
			varTitle += " (<c:out value="${deleteList_dialog_addTitle}"/>)";
			</c:if>
			fn_dialog.open(varTitle, $(this).attr("href"), 0, 0, "fn_deleteList");
		}catch(e){/*alert(e);*/}
		return false;
	});
	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			fn_listDeleteFormSubmit("<c:out value="${listFormId}"/>", $(this).attr("href"));
		}catch(e){}
		return false;
	});

	// 등록/수정
	$(".fn_btn_write_open, .fn_btn_modify_open").click(function(){
		try {
			<c:choose>
				<c:when test="${!empty input_dialog_title}">
				var varTitle = "<c:out value="${input_dialog_title}"/>";
				</c:when>
				<c:otherwise>
				var varTitle = "<c:out value="${settingInfo.input_title}"/>";
				//<c:if test="${mngAuth}">
				if($(this).hasClass('fn_btn_modify_open')) varTitle += " 수정";
				else /* </c:if> */varTitle += " 등록";
				</c:otherwise>
			</c:choose>
			<c:if test="${!empty input_dialog_addTitle}">
			varTitle += " (<c:out value="${input_dialog_addTitle}"/>)";
			</c:if>
			<c:set var="dialogWidth" value="0"/>
			<c:if test="${!empty input_dialog_width}"><c:set var="dialogWidth" value="${input_dialog_width}"/></c:if>
			fn_dialog.open(varTitle, $(this).attr("href") + "&dl=1", ${dialogWidth}<c:if test="${!empty input_dialog_height}">, ${input_dialog_height}</c:if>);
		}catch(e){}
		return false;
	});

	<c:if test="${!empty URL_EXCELINPUT}">
	// 엑셀등록
	$(".fn_btn_excelwrite").click(function(){
		try {
			var varTitle = "<c:out value="${settingInfo.input_title}"/> 엑셀등록";
			fn_dialog.open(varTitle, $(this).attr("href") + "&dl=1", 0, 300);
		}catch(e){}
		return false;
	});
	</c:if>
	
	// 목록수 적용
	$(".fn_btn_lunit").click(function(){
		location.href=$(this).attr("data-url") + "&lunit=" + $("#lunit option:selected").val();
	});
	
});

//삭제
function fn_listDeleteFormSubmit(theFormId, theAction){
	try {
		if(!fn_isValFill(theFormId) || !fn_isValFill(theAction)) return false;
		// 선택
		if(!fn_checkElementChecked("select", "삭제")) return false;
		// 삭제여부
		var fnIdx = ${crtMenu.fn_idx};
		var deleteMessage = "";
		var isPrtbiz = theAction.indexOf("mId=90");
		if(fnIdx == 1 && isPrtbiz == -1){
			deleteMessage = "참여가능사업을 삭제하면 관련된 훈련과정도 모두 삭제됩니다. 해당 참여가능사업을 삭제하시겠습니까?";
		} else {
			deleteMessage = "해당 훈련과정을 삭제하시겠습니까?";
		}
		var varConfirm = confirm(deleteMessage);
		if(!varConfirm) return false;
		
		$("#" + theFormId).attr("action", theAction);

		$("input[name='select']").prop("disabled", false);
		$("#" + theFormId).submit();
	}catch(e){}
	
	return true;
}
</script>
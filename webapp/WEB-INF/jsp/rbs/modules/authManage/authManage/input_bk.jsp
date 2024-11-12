<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_authInputReset();
	
	// reset
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_sampleInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
		try {
			<c:choose>
				<c:when test="${param.dl == '1'}">
				self.close();
				</c:when>
				<c:otherwise>
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	</c:if>
	
	//체크박스
	$("input:checkbox").change(function(){
		
		if(this.checked){
			$(this).attr('value', 'Y');
		} else{
			$(this).attr('value', 'N');
		}
	});
	
	$("input:checkbox").each(function(){
		var chkVal = $(this).prop("value");
		if(chkVal == 'Y'){
			$(this).prop("checked", true);
		}
	});
	
	
	if($("#siteId1").is(":checked")){
		$("#web").prop("style", "display:block");
		$("#dct").prop("style", "display:none");
	} else if ($("#siteId2").is(":checked")){
		$("#dct").prop("style", "display:block");
		$("#web").prop("style", "display:none");
	}
	
	$("input:radio").change(function(){
		if($("#siteId1").is(":checked")){
			console.log("사용자");
			$("#web").prop("style", "display:block");
			$("#dct").prop("style", "display:none");
		} else if ($("#siteId2").is(":checked")){
			console.log("관리자");
			$("#dct").prop("style", "display:block");
			$("#web").prop("style", "display:none");
		}
	});

	
	
	$(".lineChk").click(function(){
		var chkAll = $(this).prop('name');
		if($(this).is(":checked")){
			$("input[name=" + chkAll + "]").prop("checked", true);
			$("input[name=" + chkAll + "]").prop("value", 'Y');
		} else{
			$("input[name=" + chkAll + "]").prop("checked", false);
			$("input[name=" + chkAll + "]").prop("value", 'N');
		}
		
	});
	
	$(".webAllCheck").click(function(){
		if($(this).is(":checked")){
			$("#web > .checkList").find('input').prop("checked", true);
			$("#web > .checkList").find('input').prop("value", 'Y');
		} else{
			$("#web > .checkList").find('input').prop("checked", false);
			$("#web > .checkList").find('input').prop("value", 'N');
		}
	});
	
	$(".dctAllCheck").click(function(){
		if($(this).is(":checked")){
			$("#dct > .checkList").find('input').prop("checked", true);
			$("#dct > .checkList").find('input').prop("value", 'Y');
		} else{
			$("#dct > .checkList").find('input').prop("checked", false);
			$("#dct > .checkList").find('input').prop("value", 'N');
		}
	});
	
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			getAuthList();
			console.log($("#authList").prop('value'));
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	
console.log("여기타고잇ㄴ;");
	
});

function getAuthList(){
	
	var list = new Array();
	
	$(".checkList > tr").each(function(index){
		 list.push($(this).children('td:eq(4)').find('input').prop('id'));
		 list.push($(this).children('td:eq(4)').find('span').text());
		 list.push($(this).children('td:eq(5)').find('span').text());
		 list.push($(this).children('td:eq(5)').find('input').prop('value'));
		 list.push($(this).children('td:eq(6)').find('input').prop('value'));
		 list.push($(this).children('td:eq(7)').find('input').prop('value'));
		 list.push($(this).children('td:eq(8)').find('input').prop('value'));
		 list.push($(this).children('td:eq(9)').find('input').prop('value'));
		 list.push($(this).children('td:eq(10)').find('input').prop('value'));
	});
	console.log(list);
	$("#authList").attr("value",list);
}
function fn_memSearchSubmit(objThis){
	try{
		 var itemId = $(objThis).attr("data-id");
		fn_dialog.open("","${URL_MBRSEARCHLIST}&dl=1&itemId=" + itemId,800,700,"mbrs");		
	}catch(e){}
	
	return false;
}


function fn_setMemberMmbrInfo(objItemId, obj){
	var mbrName = $(obj).attr("data-name");
	var mbrIdx = $(obj).val();
	
	$("#" + objItemId + "Name").val(mbrName);
	$("#" + objItemId).val(mbrIdx);
	
	return false;
}

function fn_authInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_authInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>
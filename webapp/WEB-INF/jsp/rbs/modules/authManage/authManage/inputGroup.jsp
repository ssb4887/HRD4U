<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_authInputReset();
	
	// reset
	$("#<c:out value="${inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${inputFormId}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId}"/> .fn_btn_cancel").click(function(){
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


	<c:if test="${!empty dt}">
		$("#memberIdxName").attr("value", "${dt.MEMBER_NAME}");
		$(".select").val('${dt.AUTH_IDX }').prop("selected",true);
	</c:if>
	
	<c:if test="${submitType == 'modify'}">
	console.log("fdsfsdf");
		$("#fn_btn_memberIdx").prop("style", "display:none");
		$("#fn_btn_del_memberIdx").prop("style", "display:none");
	</c:if>
	
	// 등록/수정
	$("#<c:out value="${inputFormId}"/>").submit(function(){
		try {
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	
console.log("여기타고잇ㄴ;");
	
});

function fn_memSearchSubmit(objThis){
	try{
		 var itemId = $(objThis).attr("data-id");
		fn_dialog.open("","${URL_MBRSEARCHLIST}&dl=1&itemId=" + itemId,800,700,"input");		
	}catch(e){}
	
	return false;
}


function fn_setMemberMmbrInfo(objItemId, obj, list){
	var mbrName = $(obj).attr("data-name");
	var mbrIdx = $(obj).val();

	
	$("#" + objItemId + "Name").val(mbrName);
	$("#" + objItemId).val(mbrIdx);
	$("#id").text(list[0]);
	$("#tel").text(list[1]);
	$("#email").text(list[2]);
	$("#position").text(list[3]);
	
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
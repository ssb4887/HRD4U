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
	
	window.open();
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			getAuthList();
			console.log($("#authList").prop('value'));
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	

	
});

function getAuthList(){
	
	var list = new Array();
	
	$(".checkList > tr").each(function(index){
		 list.push($(this).children('td:eq(3)').find('input').prop('id'));
		 list.push($(this).children('td:eq(3)').find('span').text());
		 list.push($(this).children('td:eq(4)').find('span').text());
		 list.push($(this).children('td:eq(4)').find('input').prop('value'));
		 list.push($(this).children('td:eq(5)').find('input').prop('value'));
		 list.push($(this).children('td:eq(6)').find('input').prop('value'));
		 list.push($(this).children('td:eq(7)').find('input').prop('value'));
		 list.push($(this).children('td:eq(8)').find('input').prop('value'));
		 list.push($(this).children('td:eq(9)').find('input').prop('value')); 
	});
	console.log(list);
	$("#authList").attr("value",list);
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
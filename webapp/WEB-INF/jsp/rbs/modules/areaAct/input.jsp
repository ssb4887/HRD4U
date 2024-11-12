<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	
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
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 소속기관 선택 시 총괄소속기관 목록에 추가
	$("input[name='insttIdx']").change(function() {
		
		var innerHtml = "";
		
		var checkVal = $(this).is(':checked');
		var insttVal = $(this).prop("value");
		var insttId = $(this).prop("id");
		var insttName = $(this).attr("data-regi");
		
		console.log(insttId);	
		if(checkVal){
			console.log("밸류값이 " + insttVal + "밸류명이 " + insttName + "체크값이 " + checkVal);
			innerHtml += '<div class="input-checkbox-area" id="div' + insttId + '"><label for="total' + insttId + '"> <input type="radio" id="total' + insttId  + '" name="totalinsttIdx" class="radio-type01" value="' + insttVal + '">' + insttName + '</label></div>';
			$("#regi > .input-checkbox-wrapper").append(innerHtml);
		} else {
			console.log("밸류값이 " + insttVal + "밸류명이 " + insttName + "체크값이 " + checkVal);	
			$("div[id='div" + insttId + "'").remove();
		}
		
	});
	
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>
<%@ include file="../../../include/commonTop.jsp"%>
<c:if test="${!isAdmMode}">
	<%@ include file="joinStep.jsp"%>
	<%@ include file="captcha.jsp"%>
</c:if>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	if(opener) fn_window.resizeTo(0, 650);
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_memberInputReset();
	
	// reset
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_memberInputReset();
		}catch(e){alert(e); return false;}
	});
	
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
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			return fn_memberInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 그룹 추가
	$(".fn_btn_add").click(function(){
		fn_dialog.init("fn_search");
		var varItemId = $(this).attr("data-id");
		try {
			var varTitle = "회원그룹 검색";
			fn_dialog.open(varTitle, "<c:out value="${URL_GRUPSEARCHLIST}"/>&itemId=" + varItemId +"&type=1&dl=1", 650, 500);
		}catch(e){}
	});
	
	// 그룹 삭제
	$(".fn_btn_del").click(function(){
		try {
			var varItemId = $(this).attr("data-id");
			var varObj = $("#" + varItemId);
			if(!fn_checkSelected(varObj, "삭제")) return false;
			
			varObj.find("option:selected").remove();
		}catch(e){}
	});
	
	<spring:message var="useSnsLogin" code="Globals.sns.login.use" text="0"/>
	<spring:message var="useSnsLoginRegi" code="Globals.sns.login.register.use" text="0"/>
	<c:if test="${submitType == 'myinfo' && useSnsLogin == '1' && useSnsLoginRegi != '1'}">
	$(".fn_btn_snsLogin").click(function(){
		try{if($("#snsMbrPwd").size() > 0 && !fn_checkFill($("#snsMbrPwd"), "비밀번호")) return false;}catch(e){}
		
		var varUrl = $(this).attr("data-url");
		fn_dialog.open('', varUrl, 500, 300, 'fn_win_snsLogin');
		return false;
	});
	</c:if>
});

function fn_memberInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	$("#mbrGrp option").remove();
	<c:forEach var="objVal" items="${memberGrupList}" varStatus="i">
	<c:set var="optnDt" value="${elfn:getMatchHashMap(optnHashMap['mbrGrp'], objVal, 'OPTION_CODE')}"/>
	<c:if test="${!empty optnDt}">$("#mbrGrp").append('<option value="<c:out value="${optnDt.OPTION_CODE}"/>"<c:if test="${!empty optnDt.P_OPTION_CODE}"> data-p="<c:out value="${optnDt.P_OPTION_CODE}"/>"</c:if>><c:out value="${optnDt.OPTION_NAME}"/></option>');</c:if>
	</c:forEach>
}

function fn_memberInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}

//사용자그룹 setting
function fn_setMemberGrupInfo(theItemId, theChkedObjs){
	$.each(theChkedObjs, function(){
		var varCd = $(this).val();
		var varObj =  $("select[name='" + theItemId + "']");
		var varPreChkedObj = varObj.find("option[value='" + varCd + "']");
		if(!varPreChkedObj.is("option")){
			var varBName = $(this).attr("data-name");
			varObj.append("<option value=\"" + varCd + "\">" + varBName + "</option>");
		}
	});
}
<c:if test="${submitType == 'myinfo' && useSnsLogin == '1' && useSnsLoginRegi != '1'}">
function fn_snsLoginFormSubmit() {
	$("#fn_snsLoginForm").attr("action", "${URL_SNSJOINOK}");
	$("#fn_snsLoginForm").submit();
}
</c:if>
</script>
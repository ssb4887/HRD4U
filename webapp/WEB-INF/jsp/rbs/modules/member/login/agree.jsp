<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="items" value="${itemInfo.items}"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<itui:submitScript items="${items}" itemOrder="${itemOrder}"/>	<% /* javascript include */ %>
<script type="text/javascript">
$(function(){	
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
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
			
			if($("input:radio[name='agreement01']:checked").length < 1) {
				alert("개인정보 수집 및 이용 동의가 체크되지 않았습니다.");
				$("#y_agreement01").focus();
				return false;
			}
			
			if($("input:radio[name='agreement02']:checked").length < 1) {
				alert("동의 없이 수집·이용하는 개인정보 내역 고지가 체크되지 않았습니다.");
				$("#y_agreement02").focus();
				return false;
			}
			
			if($("input:radio[name='agreement03']:checked").length < 1) {
				alert("외부위원 위촉관련 정보공유 동의서가 체크되지 않았습니다.");
				$("#y_agreement03").focus();
				return false;
			}
			
			if($("input:radio[name='agreement04']:checked").length < 1) {
				alert("현장맞춤형 체계적훈련지원사업 참여서약서가 체크되지 않았습니다.");
				$("#y_agreement04").focus();
				return false;
			}
			
			if($("#n_agreement01:checked").length == 1) {
				alert("개인정보 수집 및 이용 동의안함에 체크 하였습니다. \n다시 확인 해주십시오.");
				$("#n_agreement01").focus();
				return false;
			}
			
			if($("#n_agreement02:checked").length == 1) {
				alert("개인정보 수집 및 이용 동의안함에 체크 하였습니다. \n다시 확인 해주십시오.");
				$("#n_agreement02").focus();
				return false;
			}
			
			if($("#n_agreement03:checked").length == 1) {
				alert("개인정보 수집 및 이용 동의안함에 체크 하였습니다. \n다시 확인 해주십시오.");
				$("#n_agreement03").focus();
				return false;
			}
			
			if($("#n_agreement04:checked").length == 1) {
				alert("개인정보 수집 및 이용 동의안함에 체크 하였습니다. \n다시 확인 해주십시오.");
				$("#n_agreement04").focus();
				return false;
			}
			
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	/* $('#selectAll').change(function(){
		if($(this).prop("checked")) $('#<c:out value="${param.inputFormId}"/> input[id$=Agree1]').prop('checked', true);
		else $('#<c:out value="${param.inputFormId}"/> input[id$=Agree2]').prop('checked', true);
	}); */
	
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
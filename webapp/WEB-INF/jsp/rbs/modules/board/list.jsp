<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="listFormId" value="fn_boardListForm"/>
<script type="text/javascript">
$(function(){	
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	
	
	// 상세보기
	$(".fn_btn_view").click(function(){
		var varIsNMChk = $(this).attr("data-nm");
		if(fn_isValFill(varIsNMChk)) {
			<%-- 비회원 글쓰기/댓글쓰기 권한 --%>
			<c:choose>
			<%--
				<c:when test="${(elfn:isNmAuthPage('WRT') and elfn:isNoMemberAuthPage('RWT')) or (elfn:isNmAuthPage('RWT') and elfn:isNoMemberAuthPage('WRT'))}">
			--%>
				<c:when test="${elfn:isNoMemberAuthPage('RWT') or elfn:isNoMemberAuthPage('WRT')}">
					if(confirm('비밀번호를 입력하시겠습니까?'))
						fn_showPwdDialog(varIsNMChk, $(this).attr("href"));
					else
						return false;//top.location.href = $(this).attr("href");
				</c:when>
				<c:otherwise>
					fn_showPwdDialog(varIsNMChk, $(this).attr("href"));
				</c:otherwise>
			</c:choose>
			return false;
		}
		return true;
	});
	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			fn_listDeleteFormSubmit("<c:out value="${listFormId}"/>", $(this).attr("href"));
		}catch(e){}
		return false;
	});
	
	<c:if test="${settingInfo.design_type eq 'memo'}">
		// 삭제
		$(".fn_btn_delete_list").click(function(){
			try {
				var varIsNMChk = $(this).attr("data-nm");
				if(fn_isValFill(varIsNMChk)) {
					<%-- 비회원 글쓰기/댓글쓰기 권한 --%>
					fn_showPwdDialog(varIsNMChk, $(this).attr("href"), "l");
					return false;
				} else {
					try {
						// 삭제여부
						var varConfirm = confirm("<spring:message code="message.select.delete.confirm"/>");
						if(!varConfirm) return false;
					}catch(e){return false;}
					return true;
				}
			}catch(e){}
			return true;
		});
		// 등록/수정
		$(".fn_btn_write_list, .fn_btn_modify_list").click(function(){
			var varAction = $(this).attr('href');
			try {
				var varIsNMChk = $(this).attr("data-nm");
				if(fn_isValFill(varIsNMChk)) {
				<c:choose>
					<c:when test="${elfn:isNoMemberAuthPage('WRT')}">
						if(confirm('비밀번호를 입력하시겠습니까?'))
							fn_showPwdDialog(varIsNMChk, $(this).attr("href"), "l");
						else
						{
							$("#<c:out value="${param.inputFormId}"/> .fn_btn_write_list").click();
							return false;
						}
					</c:when>
					<c:otherwise>
						fn_showPwdDialog(varIsNMChk, $(this).attr("href"), "l");
					</c:otherwise>
				</c:choose>
				} else {
					getAjaxMemoInputForm(varAction);
				}
			}catch(e){alert(e);}
			return false;
		});
		

		function getAjaxMemoInputForm(varAction){

			$.ajax({
				beforeSend:function(request){request.setRequestHeader('Ajax', 'true');}, 
				url:varAction,
				success:function(data){
					eval(data.replace(/<br\/>/g, '\\n'));
				}
			});
		}

		$('.fn_btn_write_list').trigger('click');
</c:if>
	
	<c:if test="${settingInfo.design_type eq 'faq'}">
		$(".fn_faq_list dl dt").css("cursor","pointer").click(function(e){
			e.preventDefault();
			var varNext = $(this).next();
			var varDisplay = $(varNext).css("display");
			if(varDisplay == 'none') $(varNext).slideDown();
			else $(varNext).slideUp();
		});
	
		//전체열림/닫기
		
		$(".qna-wrapper02>dl>dt>button").on("click", function() {
			if($(this).parent().next().css("display") == "none") {
				$(this).parent().next().slideDown(150);											
				$(this).addClass('active');
			} else {
				$(this).parent().next().slideUp(150);				
				$(this).removeClass('active');							
			}
		});

		$("#btn-answer").on("click", function() {
			if($(this).hasClass('active') == true) {
				$(this).removeClass('active').text('답변모두열기');
				$(".qna-wrapper02>dl>dd").slideUp(150);
				$(".qna-wrapper02>dl>dt>button").removeClass('active');
			} else {
				$(this).addClass('active').text('답변모두닫기');
				$(".qna-wrapper02>dl>dd").slideDown(150);
				$(".qna-wrapper02>dl>dt>button").addClass('active');
			}
		});
		/* $(".fn_btn_toggle").click(function(){
			var $Num = ($(this).text() == "답변 모두 열기") ? 1 : 0;
	
			$(".fn_faq_list dl dd").each(function(){
				if($Num == 1){
					$(this).slideDown();
					$(".fn_btn_toggle").css("backgroundPosition","87px -394px").html("답변 모두 닫기");
				}else{
					$(this).slideUp();
					$(".fn_btn_toggle").css("backgroundPosition","87px -294px").html("답변 모두 열기");
				}
			});
	
		}); */
	</c:if>
	
	//주치의게시판
	<c:if test="${crtMenu.fn_idx eq '8'}">
	$(".fn_btn_view").on("click", function(){
		//if(${listDt.NAME}   ${loginVO.usertypeIdx}
		
		var secret = $(this).attr("data-secret");
		var name = $(this).attr("data-name");
		var origin = "${loginVO.memberNameOrg}"
		
		if(secret == '1' ){
			if(name == origin || ${loginVO.usertypeIdx} >= 40){
				
			} else{
				alert("권한이 없습니다.");
				return false;	
			}
			
		}
		
	});

	//전체열림/닫기
	
	

	$("#btn-answer").on("click", function() {
		if($(this).hasClass('active') == true) {
			$(this).removeClass('active').text('답변모두열기');
			$(".qna-wrapper02>dl>dd").slideUp(150);
			$(".qna-wrapper02>dl>dt>button").removeClass('active');
		} else {
			$(this).addClass('active').text('답변모두닫기');
			$(".qna-wrapper02>dl>dd").slideDown(150);
			$(".qna-wrapper02>dl>dt>button").addClass('active');
		}
	});
	
</c:if>

	<c:if test="${settingInfo.use_filecomt eq '1'}">
	<%-- 파일다운로드 사유목록 --%>
	$(".fn_btn_fileCmtList").click(function(){
		fn_dialog.open("파일다운로드 사유", $(this).attr("href"), 900, 800, "fn_fileCmtList");
		return false;
	});
	</c:if>
	
});

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
<c:set var="pageType" value="v"/>
<%@ include file="password.jsp"%>
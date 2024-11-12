<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="listFormId" value="fn_boardListForm"/>
<c:set var="inputWinFlag" value="_view"/>		
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="mwtAuth" value="${elfn:isAuth('MWT')}"/>
<c:set var="conMmgUTP"><spring:message code="Globals.code.USERTYPE_ADMIN"/></c:set>
<c:set var="wrtUTPAuth" value="${elfn:getModuleUTP('MWT')}"/>

var varSource = '';
varSource += '<div class="btnTopFull" style="margin-top:20px;">';
varSource += '<div class="right">';
varSource += '<div class="resultCount mgb5" style="width:100%">총 <strong>${paginationInfo.totalRecordCount}</strong>건</div>';
varSource += '</div>';
varSource += '</div>';
<c:set var="listIdxName" value="${settingInfo.memo_idx_name}"/>
<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
<c:forEach var="listDt" items="${list}" varStatus="i">
<c:set var="listBrdKey" value="${listDt[settingInfo.idx_column]}"/>
<c:set var="listKey" value="${listDt[settingInfo.memo_idx_column]}"/>
varSource += '<div class="comment-form-area">';
varSource += '<div class="comment-header-wapper"><div class="left"><p class="name"><c:out value="${elfn:memberItemOrgValue('mbrName', listDt.REGI_NAME)}"/></p><p class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></p></div>';
varSource += '<div class="right">';
varSource += '<div class="small-btns">';
<c:if test="${loginVO.memberIdx == listDt.REGI_IDX || loginVO.usertypeIdx eq 55}">
<c:if test="${wrtUTPAuth < conMmgUTP}">
<c:if test="${elfn:isDisplayAuth(crtMenu.fn_idx, listBrdKey, listKey, false, listDt['REGI_IDX'], listDt['MEMBER_DUP'], listDt['PWD'])}">
<c:set var="isNoMemberAuthPage" value="${elfn:isNoMemberAuthPage('MWT')}"/>
varSource += '<a href="<c:out value="${URL_IDX_MEMOMODIFY}"/>&${listIdxName}=${listKey}" class="fn_btn_modify${inputWinFlag}"<c:if test="${isNoMemberAuthPage}"> data-nm="<c:out value="${listBrdKey}"/>|<c:out value="${listKey}"/>"</c:if>><spring:message code="item.modify.name"/></a>';
varSource += '<a href="<c:out value="${URL_IDX_MEMODELETEPROC}"/>&${listIdxName}=${listKey}" class="fn_btn_delete${inputWinFlag}"<c:if test="${isNoMemberAuthPage}"> data-nm="<c:out value="${listBrdKey}"/>|<c:out value="${listKey}"/>"</c:if>><spring:message code="item.delete.name"/></a>';
</c:if>
</c:if>
</c:if>
varSource += '</div>';
varSource += '</div>';
varSource += '</div>';
varSource += '</div>';
varSource += '<p class="comment-information">';
varSource += '<c:out value="${elfn:getNLinetoBR(listDt.CONTENTS)}"/>';
varSource += '</p>';
<c:set var="listNo" value="${listNo - 1}"/>
</c:forEach>
varSource += '</tbody>';
varSource += '</table>';
varSource += '<div class="paginate mgt15">';
<c:set var="paging"><pgui:pagination listUrl="${URL_IDX_MEMOLIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.memo_page_name, 'page')}"/></c:set>
varSource += '${elfn:getInline(paging)}';
varSource += '</div>';
$('#${listFormId}').html(varSource);
$(function(){
	fn_dialog.init("fn_list");
	
	$('.paginate .page-num a').click(function(){
		$.get($(this).prop('href'), function(data){
			eval(data.replace(/&lt;br\/&gt;/g, '\\n'));
		});
		return false;
	});
	
	// 삭제
	$(".fn_btn_delete${inputWinFlag}").click(function(){
		var varIsNMChk = $(this).attr("data-nm");
		if(fn_isValFill(varIsNMChk)) {
			<%-- 비회원 글쓰기/댓글쓰기 권한 --%>
			fn_showPwdDialog(varIsNMChk, $(this).attr("href"));
			return false;
		} else {
			try {
				var varConfirm = confirm("<spring:message code="message.select.delete.confirm"/>");
				if(!varConfirm) return false;
			}catch(e){return false;}
			return true;
		}
	});
	
	// 등록/수정
	$(".fn_btn_modify${inputWinFlag}").click(function(){
		var varIsNMChk = $(this).attr("data-nm");
		var varAction = $(this).attr('href');
		if(fn_isValFill(varIsNMChk)) {
			<%-- 비회원 글쓰기/댓글쓰기 권한 --%>
			<c:choose>
				<c:when test="${elfn:isNoMemberAuthPage('MWT')}">
					if(confirm('비밀번호를 입력하시겠습니까?'))
						fn_showPwdDialog(varIsNMChk, varAction);
					else {
						$(".fn_btn_write_view").click();
					}
				</c:when>
				<c:otherwise>
					fn_showPwdDialog(varIsNMChk, varAction);
				</c:otherwise>
			</c:choose>
		} else {
			try {
				getAjaxMemoInputForm(varAction);
			}catch(e){alert(e);}
		}
		return false;
	});
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
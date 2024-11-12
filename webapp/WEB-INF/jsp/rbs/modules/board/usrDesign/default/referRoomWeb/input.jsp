<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_boardInputForm"/>
<c:set var="chkAuthName" value="WRT"/>
<c:if test="${((settingInfo.use_reply eq '1' or settingInfo.use_qna eq '1') and param.mode eq 'r') or dt.RE_LEVEL > 1}">
	<c:set var="chkAuthName" value="RWT"/>
</c:if>
<c:set var="isNoMemberAuthPage" value="${elfn:isNoMemberAuthPage(chkAuthName)}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
		<jsp:param name="isNoMemberAuthPage" value="${isNoMemberAuthPage}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
	<div class="contents-wrapper">
		<c:if test="${((settingInfo.use_reply eq '1' or settingInfo.use_qna eq '1') and param.mode eq 'r') or dt.RE_LEVEL > 1}">
			<jsp:include page="../../common/pntList.jsp" flush="false">
				<jsp:param name="summary" value="${summary}"/>
				<jsp:param name="hnoMargin" value="${true}"/>
			</jsp:include>
		</c:if>
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" name="pntIdx" id="pntIdx" value="<c:out value="${dt.PNT_IDX}"/>"/>
		<input type="hidden" name="reStep" id="reStep" value="<c:out value="${dt.RE_STEP}"/>"/>
		<input type="hidden" name="reLevel" id="reLevel" value="<c:out value="${dt.RE_LEVEL}"/>"/>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
		<fieldset>
		<legend class="bind">글쓰기</legend>
		<div class="board-write">
		<itui:itemInputAllForDct itemInfo="${itemInfo}" itemOrderName="${submitType}_order" exceptIds="${exceptIds}"/>
		
		
		</div>
		<div class="btns-right">
			<input type="submit" class="btn-m01 btn-color03 depth3" value="저장" title="저장"/>
			<a href="#" title="취소" class="btn-m01 btn-color02 depth3">취소</a>
		</div>
		</fieldset>
		</form>
		<%-- 비회원글쓰기/댓글쓰기권한 : 비밀번호 확인  --%>
		<%@ include file="../../common/password.jsp"%>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
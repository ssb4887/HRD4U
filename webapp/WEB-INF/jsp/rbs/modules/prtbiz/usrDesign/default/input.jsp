<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
		
		<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
		<div class="board-write">
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="prtbizName" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectText itemId="prtbizName" itemInfo="${itemInfo}" objClass="w100"/></dd>
				</dl>
				<dl>
					<dt>작성자&nbsp;<strong class="point-important">*</strong></dt>
					<dd>${nameDt.NAME}</dd>
				</dl>
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="intro" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTextarea itemId="intro" itemInfo="${itemInfo}"/></dd>
				</dl>
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="consider" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTextarea itemId="consider" itemInfo="${itemInfo}"/></dd>
				</dl>
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="url" itemInfo="${itemInfo}"/></dt>
					<dd><itui:objectText itemId="url" itemInfo="${itemInfo}" objClass="w100"/></dd>
				</dl>
			</div>
		</div>
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_submit">저장</button>
				<button type="button" class="btn-m01 btn-color02 depth3 fn_btn_cancel">취소</button>
				<a href="<c:out value="${URL_LIST}"/>" title="목록" class="btn-m01 btn-color01 depth3 fn_btn_write">목록</a>
			</div>
		</div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
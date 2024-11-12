<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mwtAuth" value="${elfn:isAuth('MWT')}"/>
<c:set var="listFormId" value="fn_boardListForm"/>
<c:set var="inputFormId" value="fn_boardInputForm"/>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="items" value="${itemInfo.items}"/>
<c:set var="useQna" value="${settingInfo.use_qna eq '1'}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article">
		<div class="board-view">
		<c:set var="useFile" value="${settingInfo.use_file eq '1'}"/>
		<c:set var="useReply" value="${settingInfo.use_reply eq '1'}"/>
		<c:set var="dsetCateListId" value="${settingInfo.dset_cate_list_id}"/>
		<c:set var="exceptIdDStr">name,notice,subject,file,contents<c:if test="${!empty dsetCateListId}">,${dsetCateListId}</c:if></c:set>
		<c:forEach var="listDt" items="${pntList}" varStatus="i">
			<c:set var="useQNAItem" value="${false}"/>
			<c:set var="itemOrderType" value="${submitType}"/>
			<c:set var="exceptIdStr" value="${exceptIdDStr}"/>
			<c:set var="colCnt" value="${6}"/>
			<c:set var="qnaTitle" value="질문글"/>
			<c:set var="pntOptnHashMap" value="${optnHashMap}"/>
			<c:if test="${listDt.BRD_IDX != listDt.PNT_IDX}">
				<%/* 답변글인 경우 */%>
				<c:set var="qnaTitle" value="답변글"/>
				<c:set var="useQNAItem" value="${true}"/>
				<c:set var="itemOrderType" value="reply"/>
				<c:set var="pntOptnHashMap" value="${replyOptnHashMap}"/>
				<c:set var="exceptIdStr" value="${exceptIdDStr},replyState"/>
				<c:set var="colCnt" value="${4}"/>
			</c:if>
			<c:set var="itemOrderName" value="${itemOrderType}_order"/>
			<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
			<c:set var="itemObjs" value="${itemInfo.items}"/>
			<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		<h3><c:out value="${listDt.SUBJECT}"/></h3>
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt><spring:message code="item.reginame1.name"/></dt><dd><c:out value="${listDt.REGI_NAME}"/></dd>
						
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt><spring:message code="item.regidate1.name"/></dt><dd><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${listDt.REGI_DATE}"/></dd>
					</dl>
				</div>
			</div>
			<div class="one-box">
				<dl>
					<itui:itemViewDct itemId="category" itemInfo="${itemInfo}"/>
				</dl>
			</div>
			<c:if test="${settingInfo.use_file == '1'}">
			<div class="one-box">
				<dl>
					<dt><spring:message code="item.file.name"/></dt><dd><itui:objectView itemId="file" multiFileHashMap="${pntMultiFileHashMap[elfn:toString(listDt.BRD_IDX)]}"/></dd>
				</dl>
			</div>
			</c:if>
			
			<div class="one-box">
			<dl>
				<dt><itui:objectItemName itemId="contents" itemInfo="${itemInfo}"/></dt>
				<dd>
					<itui:objectView itemId="contents" objDt="${listDt}"/>
				</dd>
			</dl>
			</div>
			
			
		<c:set var="chkListDt" value="${listDt}"/>
		<%@ include file="../../common/viewBtnsQna.jsp"%>
		</c:forEach>
		<c:if test="${settingInfo.use_memo eq '1'}">
		<%@ include file="../../common/viewMemo.jsp"%>
		</c:if>
		<%-- 비회원글쓰기/댓글쓰기권한 : 비밀번호 확인  --%>
		<%@ include file="../../common/password.jsp"%>
		<c:if test="${settingInfo.use_filecomt eq '1'}">
		<%-- 파일다운로드 사유등록  --%>
		<%-- <%@ include file="../../common/fileComment.jsp"%> --%>
		</c:if>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
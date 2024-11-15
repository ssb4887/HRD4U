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
		<h4>${qnaTitle}////</h4>
		<table class="tbViewA" summary="${summary}">
			<caption>
			게시글 읽기
			</caption>
			<colgroup>
				<col style="width:100px;" />
				<col />
				<col style="width:100px;" />
				<col />
				<c:if test="${!useQNAItem}">
				<col style="width:100px;" />
				<col style="width:60px;" />
				</c:if>
			</colgroup>
			<thead>
				<tr>
					<th colspan="${colCnt}" class="viewTlt">
					<c:if test="${settingInfo.use_notice eq '1' and listDt.NOTICE eq '1'}">[<spring:message code="item.notice.name"/>] </c:if>
					<c:if test="${!empty dsetCateListId}">
						<c:set var="dsetCateListVal"><itui:objectView itemId="${dsetCateListId}"/></c:set>
						<c:if test="${!empty dsetCateListVal}">[${dsetCateListVal}]</c:if>
					</c:if>
					<c:out value="${listDt.SUBJECT}"/>
					<c:if test="${settingInfo.use_new eq '1' and elfn:getNewTime(listDt.REGI_DATE, 1)}"> <img src="<c:out value="${contextPath}${imgPath}/common/icon_new.gif"/>" alt="새글"/></c:if>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row"><spring:message code="item.reginame1.name"/></th><td><c:out value="${listDt.NAME}"/></td>
					<th scope="row"><spring:message code="item.regidate1.name"/></th><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${listDt.REGI_DATE}"/></td>
					<c:if test="${!useQNAItem}"><th scope="row"><spring:message code="item.board.views.name"/></th><td><c:out value="${dt.VIEWS}"/></td></c:if>
				</tr>
				<c:if test="${settingInfo.use_file == '1'}">
				<tr>
					<th scope="row"><spring:message code="item.file.name"/></th><td colspan="${colCnt - 1}"><itui:objectView itemId="file" multiFileHashMap="${pntMultiFileHashMap[elfn:toString(listDt.BRD_IDX)]}"/></td>
				</tr>
				</c:if>
				<itui:itemViewAll colspan="${colCnt - 1}" itemInfo="${itemInfo}" itemOrderName="${itemOrderName}" exceptIds="${exceptIds}" objDt="${listDt}" optnHashMap="${pntOptnHashMap}" multiFileHashMap="${pntMultiFileHashMap[elfn:toString(listDt.BRD_IDX)]}" multiDataHashMap="${pntMultiDataHashMap[elfn:toString(listDt.BRD_IDX)]}"/>
				<c:set var="itemId" value="contents"/>
				<c:if test="${!elfn:isItemClosedArr(itemOrder, itemObjs[itemId])}">
				<tr>
					<th><itui:objectItemName itemId="contents" itemInfo="${itemInfo}"/></th>
					<td colspan="${colCnt - 1}">
						<itui:objectView itemId="contents" objDt="${listDt}"/>
					</td>
				</tr>
				</c:if>
			</tbody>
		</table>
		<c:set var="chkListDt" value="${listDt}"/>
		<%@ include file="../../common/viewBtns.jsp"%>
		</c:forEach>
		<c:if test="${settingInfo.use_memo eq '1'}">
		<%@ include file="../../common/viewMemo.jsp"%>
		</c:if>
		<%-- 비회원글쓰기/댓글쓰기권한 : 비밀번호 확인  --%>
		<%@ include file="../../common/password.jsp"%>
		<c:if test="${settingInfo.use_filecomt eq '1'}">
		<%-- 파일다운로드 사유등록  --%>
		<%@ include file="../../common/fileComment.jsp"%>
		</c:if>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
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
			<c:set var="exceptIdStr">name,notice,subject,file,contents,listImg<c:if test="${!empty dsetCateListId}">,${dsetCateListId}</c:if></c:set>
			<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
			<h3><c:out value="${dt.SUBJECT}"/></h3>
			
			<table class="tbViewA" summary="${summary}">
				<caption>
				게시글 읽기
				</caption>
				<colgroup>
				<col style="width:100px;" />
				<col />
				<col style="width:100px;" />
				<col />
				<col style="width:100px;" />
				<col style="width:60px;" />
				</colgroup>
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt><spring:message code="item.reginame1.name"/></dt><dd><c:out value="${dt.NAME}"/></dd>
								
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt><spring:message code="item.regidate1.name"/></dt><dd><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dt.REGI_DATE}"/></dd>
							</dl>
						</div>
					</div>
					<div class="one-box">
						<dl>
							<dt><spring:message code="item.board.views.name"/></dt><dd><c:out value="${dt.VIEWS}"/></dd>
						</dl>
					</div>
					<c:if test="${settingInfo.use_file == '1'}">
					<div class="one-box">
						<dl>
							<dt><spring:message code="item.file.name"/></dt><dd><itui:objectView itemId="file" multiFileHashMap="${multiFileHashMap}"/></dd>
						</dl>
					</div>
					</c:if>
					<div class="board-contents-wrapper">
							<itui:objectView itemId="contents"/>
					</div>
			</table>
			<c:if test="${!empty viewList}">
				<%@ include file="../../common/viewListForNew.jsp"%>
			</c:if>
			<%@ include file="../../common/viewBtnsDct.jsp"%>
			<c:if test="${!useQna and useReply}">
				<jsp:include page="../../common/pntList.jsp" flush="false">
					<jsp:param name="itemInfo" value="${itemInfo}"/>
					<jsp:param name="summary" value="${summary}"/>
				</jsp:include>
			</c:if>
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
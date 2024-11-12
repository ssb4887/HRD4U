<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="searchFormId" value="fn_boardSearchForm"/>
<c:set var="listFormId" value="fn_boardListForm"/>
<c:set var="btnModifyClass" value="fn_btn_modify"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article" class="usbConBox contents-wrapper">
		<!-- search -->
		<itui:searchFormItemForDct2 contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area pl0">
			<%@ include file="../../common/listTabDct.jsp"%>
			
			<div class="title-wrapper">
					<%@ include file="../../common/listInfoDct.jsp"%>
					<button type="button" class="btn-m03 btn-color03 fr" id="btn-answer">답변모두열기</button>
					<!-- <a href="#none" class="btnMore fn_btn_toggle">답변 모두 열기</a> -->
			</div>
		
			<div class="qna-wrapper02">
					<c:set var="colSpan" value="7"/>
					<c:set var="useFile" value="${settingInfo.use_file eq '1'}"/>
					<c:set var="useNotice" value="${settingInfo.use_notice eq '1'}"/>
					<c:if test="${empty list}">
					<ul data-role="listview" class="tbListA">
						<li class="bllist"><spring:message code="message.no.list"/></li>
					</ul>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt.BRD_IDX}"/>
					<c:set var="isNotice" value="${useNotice and listDt.NOTICE eq '1'}"/>
					<dl>
						<dt>
							<button type="button">
								<span class="sr-only">
									질문
								</span>
								<itui:objectView itemId="contents" objDt="${listDt}"/>
							</button>
						</dt>
						<dd style="display:none">
							<span class="sr-only">
								답변
							</span>
							<itui:objectView itemId="replyCon" objDt="${listDt}"/>
						</dd>
					</dl>				
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
			</div>
		</div>
		<!-- paging -->
		<div class="paginate mgt15">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //paging -->
		
		<%@ include file="../../common/listBtns.jsp"%>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
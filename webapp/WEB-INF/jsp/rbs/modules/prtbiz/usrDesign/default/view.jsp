<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ page import="java.util.Date" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<%-- 
			table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
			디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
			예시)
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		--%>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/>을 제공하는 표</c:set>
		
		<div class="board-view">
			<h3>
				<itui:objectView itemId="prtbizName" itemInfo="${itemInfo}"/>
			</h3>
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="intro" itemInfo="${itemInfo}"/></dt>
					<dd><itui:objectView itemId="intro" itemInfo="${itemInfo}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="consider" itemInfo="${itemInfo}"/></dt>
					<dd><itui:objectView itemId="consider" itemInfo="${itemInfo}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="url" itemInfo="${itemInfo}"/></dt>
					<dd><strong class="point-color01"><a href="${dt.URL}" target="_blank" style="display:inline;" class="btn-linked"><c:out value="${dt.URL}"/>&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></dd>
				</dl>
			</div>
		</div>
		<div class="btns-area mt60">
			<div class="btns-right">
				<a href="<c:out value="${URL_MODIFY}"/>&prtbizIdx=${dt.PRTBIZ_IDX}" class="btn-m01 btn-color03 depth3">수정</a>
				<a href="<c:out value="${URL_DELETEPROC}&prtbizIdx=${dt.PRTBIZ_IDX}"/>" title="삭제" class="btn-m01 btn-color02 depth3 fn_btn_delete">삭제</a>
				<a href="<c:out value="${URL_LIST}"/>" title="목록" class="btn-m01 btn-color01 depth3 fn_btn_write">목록</a>
			</div>
		</div>
		<div class="contents-area">
			<h3 class="title-type02 ml0">훈련과정</h3>
			<p class="total">총 <strong></strong>건 (<span id="pageInfo"></span>페이지)</p>
			<%-- <p class="total">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (<span id="pageInfo">${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}</span>페이지)</p> --%>
				<div class="table-type01 horizontal-scroll">
				<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
					<colgroup>
						<col width="5%" />
						<col width="5%" />
						<col width="10%" />
						<col width="30%"/>
						<col width="30%" />
						<col width="20%" />
					</colgroup>
					<thead>
					<tr>
						<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>" class="checkbox-type01"/></th>
						<th scope="col">No</th>
						<th scope="col">수정</th>
						<th scope="col">훈련과정명</th>
						<th scope="col">훈련기관명</th>
						<th scope="col">NCS 분류</th>
					</tr>
					</thead>
					<tbody class="alignC" id="tpListBody">
					</tbody>
				</table>
				</form>
				</div>
				<div class="btns-area mt60">
					<div class="btns-right">
						<a href="${contextPath}/${crtSiteId}/prtbiz/input.do?mId=90&prtbizIdx=${dt.PRTBIZ_IDX}" class="btn-m01 btn-color03">등록</a>
						<a href="${contextPath}/${crtSiteId}/prtbiz/deleteProc.do?mId=90" title="삭제" class="btn-m01 btn-color02 depth3 fn_btn_delete2">삭제</a>
					</div>
				</div>
				<!-- paging -->
				<div class="paginate mgt15">
					<p class="paging-navigation"></p>
				</div>
				<!-- //paging -->
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* ë±ë¡/ìì ì°½ ìì°½ì¼ë¡ ëì¸ ê²½ì° ì¬ì© */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* ìì ë²í¼ class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article">
		<!-- search -->
		<itui:searchFormItem divClass="tbMSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/>
		<!-- //search -->
		
		<!-- button -->
		<div class="btnTopFull">
			<c:set var="input_dialog_height" value="670"/>
			<%@ include file="../../../../adm/include/module/listBtns.jsp"%>
		</div>
		<!-- //button -->
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table class="tbListA" summary="<c:out value="${settingInfo.list_title}"/> ëª©ë¡ì ë³¼ ì ìê³  ìì  ë§í¬ë¥¼ íµí´ì ìì íì´ì§ë¡ ì´ëí©ëë¤.">
			<caption><c:out value="${settingInfo.list_title}"/> ëª©ë¡</caption>
			<colgroup>
				<col width="50px" />
				<col width="200px" />
				<col width="100px" />
				<col width="200px" />
				<col width="200px" />
				<col width=200px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
			</colgroup>
			<thead>
			<tr>
<%-- 				<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>"/></th> --%>
				<th scope="col">No</th>
				<th scope="col"><itui:objectItemName itemId="issueNo" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="bizNm" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="bizrNo" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="issueDate" itemInfo="${itemInfo}"/></th>
				<th scope="col">기초진단서 출력</th>
				<th scope="col">기업HRD이음컨설팅</th>
<%-- 				<th scope="col"><spring:message code="item.regidate.name"/></th> --%>
			</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
				<tr>
					<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt[listColumnName]}"/>
				<tr>
					<td><c:if test="${mngAuth || wrtAuth && listDt.AUTH_MNG == '1'}"><input type="checkbox" id="select" name="select" title="<spring:message code="item.select"/>" value="${listKey}"/></c:if></td>
					<td class="num">${listNo}</td>
<%-- 					<td><c:if test="${mngAuth || wrtAuth && listDt.AUTH_MNG == '1'}"><a href="<c:out value="${URL_MODIFY}"/>&${listIdxName}=${listKey}" class="btnTypeF ${btnModifyClass}">ìì </a></c:if></td> --%>
					<td class="tlt"><a href="<c:out value="${URL_VIEW}"/>&${listIdxName}=${listKey}" class="fn_btn_view"><itui:objectView itemId="sampleItemId" itemInfo="${itemInfo}" objDt="${listDt}"/></a></td>
					<td><itui:objectView itemId="issueNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="bizNm" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="bizrNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td><itui:objectView itemId="issueDate" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
					<td>기초진단지</td>
					<td>기업HRD이음컨설팅</td>
					<td class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.ISSUE_DATE}"/></td>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</form>
		
		<!-- paging -->
		<div class="paginate mgt15">
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //paging -->
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
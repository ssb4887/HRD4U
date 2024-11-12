<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_areaActSearchForm"/>
<c:set var="listFormId" value="fn_areaActListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="subConBox contents-wrapper">
		<h2></h2>
		<!-- search -->
		<itui:searchFormItemForArea contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
			<div class="title-wrapper">
				<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
				<div class="fr">
					<a href="<c:out value="${URL_CLIPREPORT}"/>" class="btn-m01 btn-color01 clipReport" id="">일괄다운로드</a>
					<!-- 등록기간 설정권한 [본부직원(40), 소속기관(30)] -->
					<c:if test="${loginVO.usertypeIdx >= 40 }">
				 		<a href="#" class="btn-m01 btn-color01 open-modal02" id="open-modal01" >등록기간</a>
				 	</c:if>
				 	<c:if test="${buttonUse gt 0 }">
				 		<a href="<c:out value="${URL_INPUT}"/>" class="btn-m01 btn-color01">등록</a>
						<a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btnTD fn_btn_delete btn-m01 btn-color01">삭제</a>
				 	</c:if>
				</div>
			</div>
			<div class="table-type01 horizontal-scroll">
			<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
				<input type="hidden" name="aracList" id="aracList" value=""/>
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
					<colgroup>
						<col style="width:6%">
						<col style="width:8%">
						<col style="width:8%">
						<col style="width:8%">
						<col style="width:10%">
						<col>
						<col style="width:15%">
						<col style="width:12%">
						<col style="width:15%">
					</colgroup>
					<thead>
					<tr>
						<th scope="col"><input type="checkbox" id="selectAll" class="checkbox-type01" name="selectAll" title="<spring:message code="item.select.all"/>"/></th>
						<th scope="col">No</th>
						<th scope="col"><spring:message code="item.modify.name"/></th>
						<th scope="col">작성</th>
						<th scope="col"><itui:objectItemName itemId="year" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="title" itemInfo="${itemInfo}" /></th>
						<th scope="col">소속기관</th>
						<th scope="col"><spring:message code="item.reginame1.name"/></th>
						<th scope="col"><spring:message code="item.regidate.name"/></th>
					</tr>
					</thead>
					<tbody class="alignC">
						<c:if test="${empty list}">
						<tr>
							<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
						<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
						<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
						<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}"/>
						<tr>
							<td><input type="checkbox" class="checkbox-type01" name="select" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listKey}" data-name="${listDt.TITLE }"/></td>
							<td class="num">${listNo}</td>
							<td><c:if test="${buttonUse gt 0 }"><a href="<c:out value="${URL_MODIFY}"/>&${listIdxName}=${listKey}" class="btn-m03 btn-color02 open-modal04">수정</a></c:if></td>
							<td><c:if test="${buttonUse gt 0 }"><a href="<c:out value="${URL_PLANWRITE}"/>&${listIdxName}=${listKey}" class="btn-m03 btn-color03 open-modal04">작성</a></c:if></td>
							<td><itui:objectView itemId="year" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td class="subject"><a href="<c:out value="${URL_VIEW}"/>&${listIdxName}=${listKey}" class="fn_btn_view"><itui:objectView itemId="title" itemInfo="${itemInfo}" objDt="${listDt}"/></a></td>
							<td><c:out value="${listDt.INSTT_NAME }"/></td>
							<td><c:out value="${listDt.REGI_NAME }"/></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
						</tr>
						<c:set var="listNo" value="${listNo - 1}"/>
						</c:forEach>
					</tbody>
				</table>
				</form>
			</div>
			<!-- 페이지 내비게이션 -->
			<div class="page">
				<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
			<!-- //페이지 내비게이션 -->
			
		</div>
	</div>
	
	
	<!-- 등록기간 모달창 -->
	<div class="mask"></div>
	<div class="modal-wrapper" id="modal-action01">	
		<h2>등록기간 설정</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<form id="regiProc" name="regiProc" method="post" action="${URL_INPUTREGI}" target="submit_target" enctype="multipart/form-data">
			<input type="hidden" name=startDate id="start" value=""/>
	        <input type="hidden" name="endDate" id="end" value=""/>
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>등록기간</dt>
							<dd>
								<itui:objectYMDForCenStart itemId="startDate" itemInfo="${itemInfo }"  objStyle="width:120px;"/>
								<p>&nbsp;~&nbsp;</p>
								<itui:objectYMDForCenEnd itemId="endDate" itemInfo="${itemInfo }"  objStyle="width:120px;"/>
							</dd>
						</dl>
						
					</div>
					<div class="btns-area">
						<button type="submit" class="btn-m02 btn-color03 round01 fn-search-submit" form="regiProc">저장</button>
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-cancel">취소</button>
					</div>
				</div>
			</div>
			</form>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- 등록기간 모달창 끝 -->
	
	<form id="clipReport" method="post" action="<c:out value="${URL_CLIPREPORT}"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" name="aracList" id="aracList" value=""/>
	</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
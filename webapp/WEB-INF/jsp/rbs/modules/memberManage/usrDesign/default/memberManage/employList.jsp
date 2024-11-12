<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/employList.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article" class="subConBox contents-wrapper">
		<h2></h2>
		<!-- search -->
		<itui:searchFormItemForDct divClass="contents-area02" insttList="${insttList}" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_LISTEMPLOY}" itemListSearch="${itemInfo.employList_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_LISTEMPLOY}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
		<!-- //search -->
		<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<!-- 검색조건이 있을 때 -->
				<c:if test="${!empty param.key || !empty param.is_region}"><c:set var="searchList" value="&is_region=${param.is_region}&keyField=${param.keyField}&key=${param.key}"/></c:if>
				<button type="button" class="btn-m01 btn-color03" id="excel_download" onclick="location.href='employExcel.do?mId=${crtMenu.menu_idx}${searchList}'" style="margin-right: 10px;">엑셀 다운로드</button>
                <a href="#" class="btn-m01 btn-color01 open-modal02" id="open-modal01" >일괄변경</a>
            </div>
		</div>
		<div class="table-type01 horizontal-scroll">
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col width="60px" />
				<col style="width:50px">
				<col style="width:50px">
				<col style="width:100px">
				<col style="width:100px">
				<col style="width:100px">
				<col style="width:150px">
				<col style="width:200px">
				<col style="width:100px">
				<col style="width:100px">
			</colgroup>
			<thead>
			<tr>
				<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>"/></th>
				<th scope="col">No</th>
				<th scope="col"><spring:message code="item.modify.name"/></th>
				<th scope="col">아이디</th>
				<th scope="col">성명</th>
				<th scope="col">신청일</th>
				<th scope="col">변경전</th>
				<th scope="col">변경후</th>
				<th scope="col">상태</th>
				<th scope="col" class="end">승인자(ID)</th>
			</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
				<tr>
					<td colspan="10" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt.MEMBER_IDX}"/>
				<c:set var="docIdxName" value="docIdx"/>
				<c:set var="docKey" value="${listDt.DOCTOR_IDX}"/>
				<tr>
					<td><c:if test="${listDt.STATUS eq '2'}"><input type="checkbox" name="select" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listKey}" data-doc="${docKey}"/></c:if></td>
					<td class="num">${listNo}</td>
					<td><a href="<c:out value="${URL_INPUTSECEMPLOY}"/>&${listIdxName}=${listKey}&${docIdxName}=${docKey}" class="fn_btn_modify btn-m03 btn-color03">수정</a></td>
					<td class="subject">${listDt.MEMBER_ID}</td>
					<td id="memName">${listDt.MEMBER_NAME}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
					<td id="preInfo"><c:if test="${!empty listDt.PRE_INSTT_NAME }">
						${listDt.PRE_INSTT_NAME}&nbsp;
						<c:choose>
							<c:when test="${listDt.PRE_CLSF_CD eq 'Y'}">팀장</c:when>
							<c:when test="${listDt.PRE_CLSF_CD eq 'N'}">팀원</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						</c:if>
					</td>
					<td id="nowInfo">
						${listDt.INSTT_NAME}&nbsp;
						<c:choose>
							<c:when test="${listDt.CLSF_CD eq 'Y'}">팀장</c:when>
							<c:when test="${listDt.CLSF_CD eq 'N'}">팀원</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${listDt.STATUS eq '0'}">반려</c:when>
							<c:when test="${listDt.STATUS eq '2'}">신청</c:when>
							<c:otherwise>승인</c:otherwise>
						</c:choose>
					</td>
					<td><c:if test="${!empty listDt.CONFMER_NAME}">${listDt.CONFMER_NAME}(${listDt.CONFMER_ID })</c:if></td>						
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
		<div class="btnList">
			
		</div>
	</div>
</div>
	<!-- 일괄 변경 모달창 -->
	<div class="mask"></div>
	<div class="modal-wrapper" id="modal-action01">
		<h2>공단소속 일괄변경</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>승인상태</dt>
							<dd>
								<select name="status" id="status">
									<option value="3">승인</option>
									<option value="0">반려</option>
								</select>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>직원</dt>
							<dd id="memberList" style="display:grid;"></dd>
						</dl>
					</div>
					<div class="btns-area">
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-submit">저장</button>
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-cancel">취소</button>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- 일괄 변경 모달창 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
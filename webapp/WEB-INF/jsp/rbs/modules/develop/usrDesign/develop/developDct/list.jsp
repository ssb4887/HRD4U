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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<!-- search -->
	<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${DEVELOP_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
	<!-- //search -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">훈련과정 표준개발 신청 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong><fmt:formatNumber value="${paginationInfo.totalRecordCount}"/></strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
            	<c:set var="searchList" value=""/>
            	<!-- 검색조건이 있을 때 -->
            	<c:if test="${!empty param.key || !empty param.is_confmStatus || !empty param.is_prtbiz || !empty param.is_ncsLclasCd}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}&is_prtbiz=${param.is_prtbiz}&is_ncsLclasCd=${param.is_ncsLclasCd}"/></c:if>
            	<a href="${DEVELOP_EXCEL_URL}${searchList}" class="btn-m01 btn-color01">엑셀다운로드</a>
	        </div>		
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col style="width:5%">
					<col style="width:14%">
					<col style="width:auto">
					<col style="width:8%">
					<col style="width:10%">
					<col style="width:6%">
					<col style="width:8%">
					<col style="width:9%">
					<col style="width:9%">
					<col style="width:6%">
					<col style="width:10%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">기업명</th>
					<th scope="col">훈련과정명</th>
					<th scope="col">사업유형</th>
					<th scope="col">NCS 대분류</th>
					<th scope="col">신청자</th>
					<th scope="col">상태</th>
					<th scope="col">등록일</th>
					<th scope="col">처리일</th>
					<th scope="col">처리자</th>
					<th scope="col">주치의 지원</th>
				</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
					<tr>
						<td colspan="11" class="bllist"><spring:message code="message.no.list"/></td>
					</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}"/>
					<tr>
						<td class="num"><c:out value="${listNo}"/></td>
						<td><c:out value="${listDt.BPL_NM}"/></td>
						<td><strong class="point-color01"><a href="<c:out value="${DEVELOP_VIEW_FORM_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}&bplNo=${listDt.BPL_NO}"><c:out value="${listDt.TP_NM}"/></a></strong></td>
						<td>
							<c:if test="${listDt.CONFM_STATUS ne '9' and listDt.CONFM_STATUS ne '42' and listDt.CONFM_STATUS ne '28'}">
							<c:choose>
								<c:when test="${listDt.PRTBIZ_IDX eq '1'}">S-OJT</c:when>
								<c:otherwise>사업주</c:otherwise>
							</c:choose>
							</c:if>
						</td>
						<td><c:out value="${listDt.NCS_LCLAS_NM}"/></td>
						<td><c:out value="${listDt.REGI_NAME}"/></td>
						<td><c:out value="${confirmProgress[listDt.CONFM_STATUS]}"/></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
						<!-- 주치의가 처리했을 때 처리일자, 처리자 정보 보여주기(신청, 반려요청, 주치의지원요청, 검토요청 반려은 기업이 하는거라 제외) -->
						<c:choose>
							<c:when test="${listDt.CONFM_STATUS ne '10' && listDt.CONFM_STATUS ne '35' && listDt.CONFM_STATUS ne '9' && listDt.CONFM_STATUS ne '37'}">
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.LAST_MODI_DATE}"/></td>
								<td>${listDt.LAST_MODI_NAME}</td>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td>
							<c:choose>
<%-- 								<c:when test="${listDt.CONFM_STATUS eq '9'}"><a href="#" class="btn-m02 btn-color03" id="open-modal${listDt.DEVLOP_IDX}" data-devIdx="${listDt.DEVLOP_IDX}">요청</a></c:when> --%>
<%-- 								<c:when test="${listDt.CONFM_STATUS eq '42'}"><a href="#" class="btn-m02 btn-color02" id="open-modal${listDt.DEVLOP_IDX}" data-devIdx="${listDt.DEVLOP_IDX}" data-return="true">반려</a></c:when> --%>
								<c:when test="${listDt.CONFM_STATUS eq '9'}"><a href="#" class="btn-m02 btn-color03" id="open-modal${listDt.DEVLOP_IDX}" data-devIdx="${listDt.DEVLOP_IDX}">보기</a></c:when>
								<c:when test="${listDt.CONFM_STATUS eq '28'}"><a href="${DEVELOP_RECOMMEND_FORM_URL}&bplNo=${listDt.BPL_NO}&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03" data-devIdx="${listDt.DEVLOP_IDX}" data-return="true">개발</a></c:when>
								<c:when test="${listDt.CONFM_STATUS eq '37'}"><a href="${DEVELOP_MODIFY_FORM_URL}&bplNo=${listDt.BPL_NO}&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03" data-devIdx="${listDt.DEVLOP_IDX}" data-return="true">수정</a></c:when>
								<c:when test="${listDt.CONFM_STATUS eq '42'}"><a href="#" class="btn-m02 btn-color02" id="open-modal${listDt.DEVLOP_IDX}" data-devIdx="${listDt.DEVLOP_IDX}" data-return="true">보기</a></c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${DEVELOP_LIST_FORM_URL}${searchList}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>

<!-- 주치의 지원요청 모달창 -->
	<div class="mask zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-action01">
		<h2>주치의 지원요청</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<form name="doctorHelpForm" id="doctorHelpForm" action="#" method="post">
			<input type="hidden" name="devlopIdx" id="devlopIdx">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0202"> 훈련실시주소 </label>
							</dt>
							<dd id="sidoName"></dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0202"> 지부지사 </label>
							</dt>
							<dd id="insttName"></dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0203"> 훈련직무 </label>
							</dt>
							<dd id="ncsName"></dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0204"> 기타요구사항 </label>
							</dt>
							<dd id="cn"></dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0204"> 주치의 의견 </label>
							</dt>
							<dd><itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}"/></dd>
						</dl>
					</div>
				</div>
			</div>
			<c:if test="${loginVO.usertypeIdx ne '40'}">
			<div class="btns-area">
				<button type="submit" class="btn-b02 round01 btn-color03 left btn-approve">
					<span> 승인 </span> <img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
				</button>
				<button type="submit" class="btn-b02 round01 btn-color02 left btn-return">
					<span> 반려 </span> <img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
				</button>
			</div>
			</c:if>
		</form>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- 주치의 지원요청 모달창 끝 -->	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
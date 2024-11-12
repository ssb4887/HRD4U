<%@ include file="../../../../include/commonTop.jsp"%>
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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/develop/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="contents-area">

		<h3 class="title-type01 ml0">훈련과정 표준개발 신청 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong><fmt:formatNumber value="${paginationInfo.totalRecordCount}"/></strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>				
			<div class="fr">
				<a href="#none" class="btn-m01 btn-color01 depth2" id="open-modal01">주치의 지원 (HELP)</a>		
				<a href="${DEVELOP_RECOMMEND_FORM_URL}" class="btn-m01 btn-color01 depth2">신청</a>		
			</div>	
		</div>
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
			<table class="width-type02">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col style="width:6%">
					<col style="width:15%">
					<col style="width:10%">
					<col style="width:auto">
					<col style="width:8%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:10%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">기업명</th>
					<th scope="col">수정</th>
					<th scope="col">훈련과정명</th>
					<th scope="col">사업유형</th>
					<th scope="col">NCS 대분류</th>
					<th scope="col">신청자</th>
					<th scope="col">상태</th>
					<th scope="col">등록일</th>
				</tr>
				</thead>
				<tbody>
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
						<td class="num"><c:out value="${listNo}"/></td>
						<td><c:out value="${listDt.BPL_NM}"/></td>
						<td>
							<c:choose>
								<c:when test="${listDt.CONFM_STATUS eq '10'}">							
									<!-- 신청 상태일 때만 회수할 수 있음 -->
									<a href="<c:out value="${DEVELOP_WITHDRAW_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">회수</a>
								</c:when>
								<c:when test="${listDt.CONFM_STATUS eq '30'}">
									<!-- 접수 상태일 때만 반려요청할 수 있음 -->
									<a href="<c:out value="${DEVELOP_RETURNREQUEST_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">반려요청</a>
								</c:when>
								<c:when test="${listDt.CONFM_STATUS eq '33'}">
									<!-- 검토요청 상태일 때는 상세보기화면으로 이동 -->
									<a href="<c:out value="${DEVELOP_VIEW_FORM_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">검토</a>
								</c:when>
								<c:when test="${listDt.CONFM_STATUS eq '42'}">
									<!-- 주치의지원 반려 상태일 때는 모달창으로 이동해서 주치의 의견 확인가능 -->
									<a href="#" class="btn-m02 btn-color02" id="open-help${listDt.DEVLOP_IDX}" data-devIdx="${listDt.DEVLOP_IDX}" data-return="true">보기</a>
								</c:when>
								<c:when test="${listDt.CONFM_STATUS eq '55'}">
									<!-- 최종승인일 때만 중도포기를 할 수 있음 -->
									<a href="<c:out value="${DEVELOP_DROPREQUEST_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">중도 포기</a>
								</c:when>
								<c:when test="${listDt.CONFM_STATUS eq '5' || listDt.CONFM_STATUS eq '20' || listDt.CONFM_STATUS eq '40'}">
									<!-- 임시저장 ,회수, 반려 상태일 때만 수정할 수 있음 -->
									<a href="<c:out value="${DEVELOP_MODIFY_FORM_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}" class="btn-m02 btn-color03 ${btnModifyClass}">수정</a>
								</c:when>						
								<c:otherwise></c:otherwise>												
							</c:choose>
						</td>			
						<td>
							<c:if test="${listDt.CONFM_STATUS ne '9' and listDt.CONFM_STATUS ne '42' and listDt.CONFM_STATUS ne '28'}">
							<strong class="point-color01">
								<a href="<c:out value="${DEVELOP_VIEW_FORM_URL}"/>&devlopIdx=${listDt.DEVLOP_IDX}">
								<c:if test="${!empty listDt.TP_NM}">
									<c:out value="${listDt.TP_NM}"/>
								</c:if>
								</a>
							</strong>
							</c:if>
						</td>
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
					</tr>
					<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${DEVELOP_LIST_FORM_URL}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
<!-- 주치의 지원요청 모달창 -->
	<div class="mask2 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-action02">
		<h2>주치의 지원요청</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<form name="doctorHelpForm" id="doctorHelpForm" action="#" method="post">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0202"> 상태 </label>
							</dt>
							<dd id="status"></dd>
						</dl>
					</div>
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
							<dd id="doctorOpinion"></dd>
						</dl>
					</div>
				</div>
			</div>
		</form>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- 주치의 지원요청 모달창 끝 -->	

<%@ include file="doctorHelpModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />
<%
	/* 등록/수정창 새창으로 띄울 경우 사용 */
%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}" />
<%
	/* 수정버튼 class */
%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>

<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/analysis/apply.js"/>"></script>
<div class="contents-area2">
	<form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
	<input type="hidden" name="mId" value="<c:out value="${mId}" />" />
	<fieldset>
		<legend class="blind"> 기업훈련 성과리포트 검색양식 </legend>
			<div class="basic-search-wrapper">
				<c:if test="${loginVO.usertypeIdx eq '40'}">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isInsttIdx"> 소속기관 </label>
							</dt>
							<dd>
								<select id="isInsttIdx" name="insttIdx">
									<option value="">소속기관 선택</option>
									<c:forEach var="instt" items="${insttList}">
										<option value="${instt.INSTT_IDX}"
											<c:if test="${instt.INSTT_IDX eq param.insttIdx}">selected</c:if>><c:out
												value="${instt.INSTT_NAME}" /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
				</div>
				</c:if>
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="id_schBplNo"> 사업장관리번호 </label>
							</dt>
							<dd>
								<input type="text" id="id_schBplNo" name="schBplNo" value="<c:out value="${param.schBplNo}" />" title="사업장관리번호 입력" placeholder="사업장관리번호">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="id_trCorpNm"> 기업명 </label>
							</dt>
							<dd>
								<input type="search" id="id_trCorpNm" name="trCorpNm" value="<c:out value="${param.trCorpNm}" />" title="기업명" placeholder="기업명">
							</dd>
						</dl>
					</div>
				</div>
			</div>
		
			<div class="btns-area">
		<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
			<img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" /> 
				<strong>
					검색 
				</strong>
		</button>
	</div>
	</fieldset>
	</form>
</div>

	<div class="title-wrapper">
		<p class="total fl">
			총 <strong><c:out value="${totalCount}" /></strong> 건 ( <c:out value="${paginationInfo.currentPageNo}" /> / <c:out value="${paginationInfo.lastPageNo}" /> 페이지 )
		</p>
	</div>
	<div class="table-type02 horizontal-scroll">
		<table>
			<caption>
				분류 관리 적극 발굴 정보표 : No, 제목, 등록자, 등록일에 관한 정보표
			</caption>
			<colgroup>
				<col style="width: 5%" />
				<col style="width: 20%" />
				<col style="width: 10%" />
				<col style="width: 15%" />
				<col style="width: 10%" />
				<col style="width: 10%" />
				<col style="width: 10%" />
				<col style="width: 10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">기업명</th>
					<th scope="col">사업장관리번호</th>
					<th scope="col">소속기관</th>
					<th scope="col">구분</th>
					<th scope="col">작성일</th>
					<th scope="col">기업확인</th>
					<th scope="col">상태</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${empty list}">
				<tr>
					<td colspan="8" class="bllist">
						<spring:message code="message.no.list" />
					</td>
				</tr>
			</c:if>
			<c:set var="listIdxName" value="${settingInfo.idx_name}" />
			<c:set var="listColumnName" value="${settingInfo.idx_column}" />
			<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}" />
					<tr id="column">
						<td class="num">
							<c:out value="${listNo}" />
						</td>
						<td class="corpNm">
							<c:out value="${cnsl.corpNm}" />
						</td>
						<td class="bplNo">
							<c:out value="${cnsl.bplNo}" />
						</td>	
						<td class="cmptncBrffcNm">
							<c:out value="${cnsl.cmptncBrffcNm}" />
						</td>
						<td class="cnslType">
							<c:out value="${cnsl.cnslType eq '1' ? '사업주' : cnsl.cnslType eq '2' ? '일반직무전수 OJT' : cnsl.cnslType eq '3' ? '과제수행 OJT' : cnsl.cnslType eq '4' ? '심층진단' : cnsl.cnslType eq '5' ? '훈련체계수립' : cnsl.cnslType eq '6' ? '현장활용' : ''}" />
						</td>
						<td class="regiDate">
							<c:out value="${fn:substring(listDt.REGI_DATE,0,10)}" />
						</td>
						<td class="confmStatus">
							<c:choose>
								<c:when test="${listDt.CONFM_STATUS eq 40}">
									<button type="button" class="btn-m02 btn-color03" onclick="openViewReject('openViewReject', `${listDt.CNSL_IDX}`)"><span>반려사유</span></button>
								</c:when>
								<c:otherwise>
									<c:out value="${listDt.CONFM_STATUS eq 50 || listDt.CONFM_STATUS eq 55 ? '기업승인' : listDt.CONFM_STATUS eq 40 ? '반려' : '결재 전'}" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/dct/report/input.do?mId=134&reprtIdx=${listDt.REPRT_IDX}&cnslIdx=${param.cnslIdx}'">
								<c:out value="${listDt.CONFM_STATUS eq 5 ? '작성중' : listDt.CONFM_STATUS eq 10 ? '제출' : listDt.CONFM_STATUS eq 40 ? '반려' : listDt.CONFM_STATUS eq 50 ? '기업승인' : listDt.CONFM_STATUS eq 55 ? '최종승인' : '-'}" />
							</button>
						</td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}" />
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="paging-navigation-wrapper">
		<form action="list.do" name="pageForm" method="get">
			<!-- 페이징 네비게이션 -->
			<p class="paging-navigation" onclick="paginationHandler('pageForm')">
				<pgui:pagination
					listUrl="${contextPath}/dct/analysis/list.do?mId=${mId}"
					pgInfo="${paginationInfo}" imgPath="${imgPath}"
					pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
			</p>
			<!-- //페이징 네비게이션 -->
		</form>
	</div>

	<div class="btns-area mt20">
		<div class="btns-right">
			<a href="#" class="btn-m01 btn-color02 m-w100"
				onclick="delAnalysis(this.id);"> 삭제 </a>
		</div>
	</div>
	

<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
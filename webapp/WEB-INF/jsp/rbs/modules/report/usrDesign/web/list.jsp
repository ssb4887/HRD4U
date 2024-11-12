<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
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

	<div class="contents-area">
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
								<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/input.do?mId=${mId}&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}'">
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
					<pgui:pagination listUrl="${contextPath}/web/report/list.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
		<c:if test="${empty list && loginVO.usertypeIdx eq '10'}">
		<div class="btn-area">
			<div class="btns-right">
				<a href="${contextPath}/web/report/input.do?mId=${mId}&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}" class="btn-m01 btn-color01">
					보고서 작성
				</a>
			</div>
		</div>
		</c:if>
	</div>

<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="openViewReject">
	<h2>
    	반려의견 조회
    </h2>
	<div class="modal-area">
    	<div class="contents-box pl0">
           	<div class="basic-search-wrapper">
           		<c:forEach var="cs" items="${confmStatus}" varStatus="i">
           		<div class="one-box">
           			<dl>
           				<dt>
							<label>
								의견
							</label>
           				</dt>
           				<dd>
           					<textarea id="id_confmCn" name="confmCn" rows="4" placeholder="의견을 입력하세요"><c:out value="${cs.CONFM_CN}" /></textarea>
           				</dd>
           			</dl>
           		</div>
           		</c:forEach>
           </div>
           <div class="btns-area">
			<button type="button" id="closeBtn_05" onclick="closeViewReject('openViewReject')" class="btn-m02 btn-color02 three-select">
				<span>
					취소
				</span>
			</button>
			</div>
		</div>
	</div>
</div>

<!-- //CMS 끝 -->
<script type="text/javascript" src="${contextPath}${jsPath}/report.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
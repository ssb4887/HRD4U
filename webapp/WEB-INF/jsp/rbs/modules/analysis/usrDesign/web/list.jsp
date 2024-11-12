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
				<caption>훈련성과분석 : 번호, 소속기관, 기업명, 훈련과정명, 훈련회차, 리포트유형, 훈련유형, 신청일, 작성에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 5%" />
					<col style="width: 12%" />
					<col style="width: 15%" />
					<col style="width: 15%" />
					<col style="width: 7%" />
					<col style="width: 15%" />
					<col style="width: 10%" />
					<col style="width: 10%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">소속기관</th>
						<th scope="col">훈련기업명</th>
						<th scope="col">훈련과정명</th>
						<th scope="col">훈련구분</th>
						<th scope="col"><itui:objectItemName itemId="trResultReprtSe" itemInfo="${itemInfo}" /></th>
						<th scope="col">등록일</th>
						<th scope="col"><itui:objectItemName itemId="status" itemInfo="${itemInfo}" /></th>
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
							<td class="insttName">
								 <c:out value="${listDt.INSTT_NAME}" />
							</td>
							<td class="trCorpNm">
								<c:choose>
									<c:when test="${not empty listDt.STATUS && listDt.ISDELETE eq '0'}">
										<a href="<c:out value="${URL_VIEW}"/>&${listIdxName}=${listKey}" class="fn_btn_view">
											<strong class="point-color01">	
												<c:out value="${listDt.TR_CORP_NM}" />
											</strong>
										</a>
									</c:when>
									<c:otherwise>
										<c:out value="${listDt.TR_CORP_NM}" />
									</c:otherwise>
								</c:choose>
							</td>	
							<td class="tpNm">
								<c:out value="${listDt.TP_NM}" />
							</td>
							<td>
								<c:out value="${listDt.CNSL_IDX eq null ? '과정개발' : '컨설팅'}"/>
							</td>
							<td class="trResultReprtSe">
								<c:out value="${listDt.TR_RESULT_REPRT_SE eq '1' ? '만족도 및 성취도 조사' : listDt.TR_RESULT_REPRT_SE eq '2' ? '현업적용' : '-'}" />
							</td>
							<td class="regiDate">
								<c:out value="${fn:substring(listDt.REGI_DATE,0,10)}" />
							</td>
							<td>
								<c:choose>
									<c:when test="${loginVO.usertypeIdx ne '5'}">
										미작성
									</c:when>
									<c:when test="${listDt.STATUS eq '0' && loginVO.usertypeIdx eq '5'}">
										<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/analysis/input.do?mId=58&<c:if test="${listDt.DEVLOP_IDX eq null}">cnslIdx=${listDt.CNSL_IDX}</c:if><c:if test="${listDt.CNSL_IDX eq null}">devlopIdx=${listDt.DEVLOP_IDX}</c:if>&rsltAnalsIdx=${listDt.RSLT_ANALS_IDX}&bplNo=${listDt.BPL_NO}&mode=m'">
											작성중
										</button>
									</c:when>
									<c:when test="${listDt.STATUS eq '0' && loginVO.usertypeIdx ne '5'}">
										작성중
									</c:when>
									<c:otherwise>
										제출
									</c:otherwise>
								</c:choose>
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
					<pgui:pagination listUrl="${contextPath}/web/analysis/list.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
	</div>

<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
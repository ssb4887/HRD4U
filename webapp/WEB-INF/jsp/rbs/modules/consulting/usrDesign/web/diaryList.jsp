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

	<div class="contents-area pl0">
		<h3 class="title-type01 ml0">
              	컨설팅 수행일지
        </h3>
         	<p class="total fl">
         		총 <strong><c:out value="${totalCount}" /></strong> 
         		건 ( <c:out value="${paginationInfo.currentPageNo}" /> / <c:out value="${paginationInfo.lastPageNo}" /> 페이지 )
         	</p>
            
      <div class="board-list">
          <table>
              <caption>
                  신청한 컨설팅 팀 수행일지 목록 정보표 : No, 제목, 등록자, 등록일에 관한 정보 제공표
              </caption>
			<colgroup>
	            <col style="width: 5%" />
	            <col style="width: 45%" />
	            <col style="width: 20%" />
	            <col style="width: 10%" />
	            <col style="width: 10%" />
	            <col style="width: 10%" />
            </colgroup>
				<thead class="no-thead">
					<tr>
						<th scope="col" class="number">연번</th>
						<th scope="col" class="title">기업명</th>
						<th scope="col" class="title">훈련유형</th>
						<th scope="col" class="writer">작성자</th>
						<th scope="col" class="date">작성일</th>
						<th scope="col" class="date">상태</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty list}">
					<tr>
						<td colspan="6" class="bllist">
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
							<td class="title">
								<a href="diaryView.do?mId=99&cnslIdx=${listDt.CNSL_IDX}&diaryIdx=${listDt.DIARY_IDX}&mode=m" class="fn_btn_view">
									<strong class="point-color01">	
										<c:out value="${listDt.BPL_NM}" />
									</strong>
								</a>
							</td>
							<td>
								<c:out value="${listDt.SPORT_TYPE eq 1 ? '사업주자체 과정' : listDt.SPORT_TYPE eq 2 ? '일반직무전수 OJT' : listDt.SPORT_TYPE eq 3 ? '과제수행 OJT' : listDt.SPORT_TYPE eq 4 ? '심층진단' : listDt.SPORT_TYPE eq 5 ? '훈련체계수립' : '현장활용'}" />
							</td>
							<td class="writer">
								<c:out value="${not empty listDt.LAST_MODI_NAME ? listDt.LAST_MODI_NAME : listDt.REGI_NAME}" />
							</td>
							<td class="date">
								<c:out value="${not empty listDt.LAST_MODI_DATE ? fn:substring(listDt.LAST_MODI_DATE,0,10) : fn:substring(listDt.REGI_DATE,0,10)}" />
							</td>
							<td>
								<c:choose>
									<c:when test="${listDt.STATUS eq '0' && loginVO.usertypeIdx eq '10'}">
										<a href="diaryInput.do?mId=99&${listIdxName}=${listKey}&diaryIdx=${listDt.DIARY_IDX}&mode=m" class="btn-m02 btn-color03">
											작성중
										</a>
									</c:when>
									<c:when test="${listDt.STATUS eq '0' && loginVO.usertypeIdx ne '10'}">
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
					<pgui:pagination listUrl="${contextPath}/web/consult/diaryList.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
		<c:if test="${loginVO.usertypeIdx eq '10' and (list[0].CONFM_STATUS ne 10 and list[0].CONFM_STATUS ne 50 and list[0].CONFM_STATUS ne 55)}">
		<div class="btn-area">
			<div class="btns-right">
				<a href="${contextPath}/web/consulting/diaryInput.do?mId=${mId}&bplNo=${map.bplNo}&cnslIdx=${map.cnslIdx}" class="btn-m01 btn-color01">
					수행일지 작성
				</a>
			</div>
		</div>
		</c:if>
	</div>


<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
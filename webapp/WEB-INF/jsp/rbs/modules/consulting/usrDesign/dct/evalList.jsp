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
              	컨설턴트 평가
        </h3>
      <div class="title-wrapper">
	      <p class="total fl">
	        	총 <strong><c:out value="${totalCount}" /></strong> 
		    	건 ( <c:out value="${paginationInfo.currentPageNo}" /> / <c:out value="${paginationInfo.lastPageNo}" /> 페이지 )
	      </p>
	      <div class="fr">
	           	<a href="${contextPath}/dct/consulting/evalExcel.do?mId=${mId}<c:if test="${not empty param.cnslIdx}">&cnslIdx=${param.cnslIdx}</c:if><c:if test="${not empty param.bplNo}">&bplNo=${param.bplNo}</c:if>" class="btn-m01 btn-color01">엑셀다운로드</a>
	      </div>
	  </div>      
      <div class="board-list">
          <table>
              <caption>
                 	 컨설턴트 평가표 : 순번, 사업명, 성명, 소속기관, 활동내역, 활동일자, 비고, 상세보기에 관한 정보 제공표
              </caption>
              <colgroup>
                  <col style="width: 8%" />
                  <col style="width: 28%" />
                  <col style="width: 10%" />
                  <col style="width: 17%" />
                  <col style="width: 15%" />
                  <col style="width: 10%" />
                  <col style="width: 12%" />
              </colgroup>
              <thead class="no-thead">
			  <tr>
				  <th scope="col">순번</th>
				  <th scope="col">사업명</th>
				  <th scope="col">성명</th>
				  <th scope="col">소속기관</th>
				  <th scope="col">활동내역</th>
				  <th scope="col">활동일자</th>
				  <th scope="col">상세보기</th>
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
							<td>
								<c:out value="${listNo}" />
							</td>
							<td class="left">
								사업명 : <c:out value="${listDt.SPORT_TYPE eq '1' ? '사업주훈련' : listDt.SPORT_TYPE eq '2' ? '일반직무전수 OJT' : listDt.SPORT_TYPE eq '3' ? '과제수행 OJT' : listDt.SPORT_TYPE eq '4' ? '심층진단' : listDt.SPORT_TYPE eq '5' ? '훈련체계수립' : '현장활용'}" />
								<br /> 회의명 : <c:out value="${listDt.MTG_WEEK_EXPLSN}" />
							</td>
							<td>
								<c:out value="${not empty cnsl.cnslTeam.members[0].mberName ? cnsl.cnslTeam.members[0].mberName : listDt.MBER_NAME}"/>
							</td>	
							<td>
								<c:out value="${not empty listDt.INSTT_NAME ? listDt.INSTT_NAME : listDt.PIC_NM}" />
							</td>
							<td>
								<c:out value="${listDt.ACT_ROLE_CD eq '1' ? '현장심사' : listDt.ACT_ROLE_CD eq '2' ? '과정인정' : listDt.ACT_ROLE_CD eq '4' ? '인정심사' : listDt.ACT_ROLE_CD eq '5' ? '검토회의' : listDt.ACT_ROLE_CD eq '6' ? '서면심사' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(listDt.EVL_DE,0,10)}" />
							</td>
							<td>
								<a href="evalInput.do?mId=127&cnslIdx=${listDt.CNSL_IDX}&evlIdx=${listDt.EVL_IDX}<c:if test="${not empty listDt.EVL_IDX}">&postSeq=<c:out value="${listDt.POST_SEQ}"/>&mode=m</c:if>" class="btn-m02 btn-color03" title="상세보기 이동">
                                 	평가: <c:out value="${listDt.EVL_GRAD}" />
                            	</a>
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
					<pgui:pagination listUrl="${contextPath}/web/consult/evalList.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
	</div>


<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
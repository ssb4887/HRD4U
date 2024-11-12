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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/total/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
<div class="contents-wrapper">
	<div class="management-title-area">
		<span>${today} 기준</span>
	</div>
	<c:choose>
		<c:when test="${loginVO.usertypeIdx eq '40'}">
			<div class="enterprise-case-wrapper">
				<div class="enterprise-case-area">
					<h3 class="title">전제 지부지사 기준 현황</h3>
					<div class="enterprise-case-box">
						<div class="enterprise-case-list type02 depth3">
							<div class="case-list-box">
								<p>당해연도 달성률</p>
								<div class="result">
									<strong>
										<c:choose>
											<c:when test="${totalSummary.COMPLETE_RATIO eq '0'}">0%</c:when>
											<c:otherwise><fmt:formatNumber type="percent" value="${totalSummary.COMPLETE_RATIO}" pattern="0.#%"/></c:otherwise>
										</c:choose>
									</strong>
								</div>
							</div>
							<div class="case-list-box">
								<p>당월 미처리/임박 건수</p>
								<div class="result type02">
									<strong>${totalSummary.NO_HANDLING_CT}</strong>
									<div class="impend"><strong>${totalSummary.NO_HANDLING_IMPEND_CT}</strong><span>임박</span></div>
								</div>
							</div>
							<div class="case-list-box">
								<p>전체 개발 건수</p>
								<div class="result"><strong>${totalSummary.COMPLETE_CT}</strong></div>
							</div>
							<div class="case-list-box">
								<p>당해연도 개발 건수</p>
								<div class="result"><strong>${totalSummary.YEAR_CT}</strong></div>
							</div>
							<div class="case-list-box w2 br0">
								<p class="mb20">당월 개발 건수</p>
								<div class="result type03">
									<strong>${totalSummary.MONTH_CT}</strong>
									<ul class="result-info">
										<li>
											<p>표준</p>
											${totalSummary.DEVLOP_MONTH_CT}
											<span>건</span>
										</li>
										<li>
											<p>맞춤</p>
											${totalSummary.CNSL_MONTH_CT}
											<span>건</span>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="enterprise-case-wrapper">
				<div class="enterprise-case-area">
					<h3 class="title">소속기관 기준 현황</h3>
					<div class="enterprise-case-box">
						<div class="enterprise-case-list type02 depth3">
							<div class="case-list-box">
								<p>당해연도 달성률</p>
								<div class="result">
									<strong>
										<c:choose>
											<c:when test="${insttSummary.COMPLETE_RATIO eq '0'}">0%</c:when>
											<c:otherwise><fmt:formatNumber type="percent" value="${insttSummary.COMPLETE_RATIO}" pattern="0.#%"/></c:otherwise>
										</c:choose>
									</strong>
								</div>
							</div>
							<div class="case-list-box">
								<p>당월 미처리/임박 건수</p>
								<div class="result type02">
									<strong>${insttSummary.NO_HANDLING_CT}</strong>
									<div class="impend"><strong>${insttSummary.NO_HANDLING_IMPEND_CT}</strong><span>임박</span></div>
								</div>
							</div>
							<div class="case-list-box">
								<p>전체 개발 건수</p>
								<div class="result"><strong>${insttSummary.COMPLETE_CT}</strong></div>
							</div>
							<div class="case-list-box">
								<p>당해연도 개발 건수</p>
								<div class="result"><strong>${insttSummary.YEAR_CT}</strong></div>
							</div>
							<div class="case-list-box w2 br0">
								<p class="mb20">당월 개발 건수</p>
								<div class="result type03">
									<strong>${insttSummary.MONTH_CT}</strong>
									<ul class="result-info">
										<li>
											<p>표준</p>
											${insttSummary.DEVLOP_MONTH_CT}
											<span>건</span>
										</li>
										<li>
											<p>맞춤</p>
											${insttSummary.CNSL_MONTH_CT}
											<span>건</span>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
<!-- 			<div class="enterprise-case-wrapper"> -->
<!-- 				<div class="enterprise-case-area"> -->
<!-- 					<div class="title-wrapper"> -->
<!-- 						<h3 class="title01 fl">주치의 기준 현황</h3> -->
<!-- 						<div class="fr"> -->
<!-- 							<a href="#" class="btn-m01 btn-color01 depth3" id="open-modal10">미처리 목록</a> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="enterprise-case-box"> -->
<!-- 						<div class="enterprise-case-list type02 depth3"> -->
<!-- 							<div class="case-list-box"> -->
<!-- 								<p>당해연도 달성률</p> -->
<!-- 								<div class="result"> -->
<!-- 									<strong> -->
<%-- 										<c:choose> --%>
<%-- 											<c:when test="${doctorSummary.COMPLETE_RATIO eq '0'}">0%</c:when> --%>
<%-- 											<c:otherwise><fmt:formatNumber type="percent" value="${doctorSummary.COMPLETE_RATIO}" pattern="0.#%"/></c:otherwise> --%>
<%-- 										</c:choose> --%>
<!-- 									</strong> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="case-list-box"> -->
<!-- 								<p>당월 미처리/임박 건수</p> -->
<!-- 								<div class="result type02"> -->
<%-- 									<strong>${doctorSummary.NO_HANDLING_CT}</strong> --%>
<%-- 									<div class="impend"><strong>${doctorSummary.NO_HANDLING_IMPEND_CT}</strong><span>임박</span></div> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="case-list-box"> -->
<!-- 								<p>전체 개발 건수</p> -->
<%-- 								<div class="result"><strong>${doctorSummary.COMPLETE_CT}</strong></div> --%>
<!-- 							</div> -->
<!-- 							<div class="case-list-box"> -->
<!-- 								<p>당해연도 개발 건수</p> -->
<%-- 								<div class="result"><strong>${doctorSummary.YEAR_CT}</strong></div> --%>
<!-- 							</div> -->
<!-- 							<div class="case-list-box w2 br0"> -->
<!-- 								<p class="mb20">당월 개발 건수</p> -->
<!-- 								<div class="result type03"> -->
<%-- 									<strong>${doctorSummary.MONTH_CT}</strong> --%>
<!-- 									<ul class="result-info"> -->
<!-- 										<li> -->
<!-- 											<p>표준</p> -->
<%-- 											${doctorSummary.DEVLOP_MONTH_CT} --%>
<!-- 											<span>건</span> -->
<!-- 										</li> -->
<!-- 										<li> -->
<!-- 											<p>맞춤</p> -->
<%-- 											${doctorSummary.CNSL_MONTH_CT} --%>
<!-- 											<span>건</span> -->
<!-- 										</li> -->
<!-- 									</ul> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
		</c:otherwise>		
	</c:choose>
	<h3 class="title-type01 ml0 mt80 mb50">맞춤훈련과정개발 종합이력 조회</h3>
	<!-- search -->
	<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${DEVELOP_TOTAL_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
	<!-- //search -->
	<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
            	<c:set var="searchList" value=""/>
            	<!-- 검색조건이 있을 때 -->
            	<c:if test="${!empty param.keyField || !empty param.is_confmStatus || !empty param.is_prtbiz || !empty param.is_ncsLclasCd}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}&is_prtbiz=${param.is_prtbiz}&is_ncsLclasCd=${param.is_ncsLclasCd}"/></c:if>
            	<a href="${DEVELOP_TOTAL_EXCEL_URL}${searchList}" class="btn-m01 btn-color01 depth3">엑셀다운로드</a>
	           </div>		
		</div>
		<div class="table-type01 horizontal-scroll">
		<table summary="능력개발클리닉 신청 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption>능력개발클리닉 신청 목록표 : 체크, 번호, 기업명, 담당자, 소속기관, 신청일자, 신청 상태, 주치의 지정, 접수에 관한 정보 제공표</caption>
			<colgroup>
				<col style="width:6%">
				<col style="width:12%">
				<col style="width:10%">
				<col style="width:15%">
				<col style="width:auto">
				<col style="width:8%">
				<col style="width:10%">
				<col style="width:10%">
			</colgroup>
			<thead>
			<tr>		
				<th scope="col">No</th>
				<th scope="col">사업유형</th>
				<th scope="col">훈련유형</th>
				<th scope="col">NCS대분류</th>
				<th scope="col">기업명 : 훈련과정명</th>
				<th scope="col">유형</th>
				<th scope="col">신청일자</th>
				<th scope="col">상태</th>
			</tr>
			</thead>
			<tbody>
				<c:if test="${empty totalList}">
				<tr>
					<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${totalList }" varStatus="i">
				<tr>
					<td class="num">${listNo}</td>
					<td><c:out value="${listDt.BIZ_TYPE}"></c:out></td>
					<td><c:out value="${listDt.TR_MTH}"></c:out></td>
					<td><c:out value="${listDt.NCS_LCLAS_NM}"></c:out></td>
					<td>
					<strong class="point-color01">
						<c:if test="${listDt.TYPE eq '표준' }">
							<a href="${contextPath}/${crtSiteId}/developDct/develop_view_form.do?mId=62&devlopIdx=${listDt.IDX}&bplNo=${listDt.BPL_NO}" target="_blank"><c:out value="${listDt.TP_NM}"></c:out></a>
						</c:if>
						<c:if test="${listDt.TYPE eq '맞춤' }">
							<c:choose>
								<c:when test="${listDt.CONFM_STATUS eq '55'}">
									<a href="<c:out value="${contextPath}/${crtSiteId}/consulting/viewAll.do?mId=126&cnslIdx=${listDt.IDX}"/>">
										<c:out value="${listDt.TP_NM}"></c:out>
									</a>
								</c:when>
								<c:otherwise>
									<a href="<c:out value="${contextPath}/${crtSiteId}/consulting/cnslViewForm.do?mId=124&cnslIdx=${listDt.IDX}"/>">
										<c:out value="${listDt.TP_NM}"></c:out>
									</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</strong>
					</td>
					<td><c:out value="${listDt.TYPE}"></c:out></td>
					<td><c:out value="${listDt.REGI_DATE}"></c:out></td>
					<td>${confirmProgress[listDt.CONFM_STATUS]}</td>											
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${DEVELOP_TOTAL_LIST_FORM_URL}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
<!-- HRD역량개발 활동일지 선택 모달창 -->
	<div class="mask10"></div>
	<div class="modal-wrapper type02" id="modal-action10">
		<h2>훈련과정개발 미처리 목록</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="contents-box pl0">
					<div class="table-type01 horizontal-scroll">
						<table class="width-type02">
						<colgroup>
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:auto">
							<col style="width:20%">
							<col style="width:20%">
							<col style="width:12%">
						</colgroup>
						<thead>
						<tr>										
							<th scope="col">조회</th>
							<th scope="col">구분</th>
							<th scope="col">훈련명</th>
							<th scope="col">신청상태</th>
							<th scope="col">신청일</th>
							<th scope="col">경과일</th>
						</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty noHandlingList}">
							<tr>
								<td colspan="6" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
							</c:if>
							<c:forEach var="listDt" items="${noHandlingList}" varStatus="i">
							<tr>
								<td>
									<c:choose>
										<c:when test="${listDt.DEV_TYPE eq '표준개발'}">
											<a href="${contextPath}/${crtSiteId}/developDct/develop_view_form.do?mId=62&devlopIdx=${listDt.IDX}&bplNo=${listDt.BPL_NO}" class="btn-m03 btn-color03" target="_blank">이동</a>
										</c:when>
										<c:otherwise>
											<a href="<c:out value="${contextPath}/${crtSiteId}/consulting/viewAll.do?mId=95&cnslIdx=${listDt.IDX}"/>">" class="btn-m03 btn-color03" target="_blank">이동</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td><c:out value="${listDt.DEV_TYPE}"/></td>
								<td><c:out value="${listDt.TP_NM}"/></td>
								<td><c:out value="${confirmProgress[listDt.CONFM_STATUS]}"/></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
								<td>
									<c:choose>
										<c:when test="${listDt.DAY_CT gt '7'}"><span class="point-color04">${listDt.DAY_CT}일</span></c:when>
										<c:otherwise><c:out value="${listDt.DAY_CT}일"/></c:otherwise>
									</c:choose>
								</td>
							</tr>	
							</c:forEach>							
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="3">모달 창 닫기</button>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
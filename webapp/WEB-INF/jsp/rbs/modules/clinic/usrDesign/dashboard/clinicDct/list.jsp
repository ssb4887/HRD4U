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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/dashboard/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 기업 현황</h3>
		<div class="contents-box pl0">
			<div class="table-type02">
			<table>
				<caption>능력개발클리닉 기업 현황 정보표 : 지부지사의 전체/1년차/2년차/3년차 능력개발클리닉 기업 현황과 나의 클리닉 기업 전체/1년차/2년차/3년차 능력개발클리닉 기업 현황에 대한 정보 제공표</caption>				
				<colgroup>
					<col style="width: 22%">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
				</colgroup>
				<thead>
					<tr>
						<th scope="col"></th>
						<th scope="col">전체</th>
						<th scope="col">1년차</th>
						<th scope="col">2년차</th>
						<th scope="col">3년차</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="allCliCorpCount" items="${allCliCorpCountList }">
						<tr>
							<td class="bg01">지부지사</td>
							<td class="bg02">${allCliCorpCount.ALLCNT}&nbsp;(중도포기 : <span class="point-color04">${allDropCliCorpCountList[0].ALLCNT}</span>)</td>
							<td>
								<c:if test="${allCliCorpCount.YEARLY  eq '1'}">
									<c:choose>
										<c:when test="${empty allCliCorpCount.YEARLYCOUNT}">0</c:when>
										<c:otherwise>${allCliCorpCount.YEARLYCOUNT}&nbsp;(중도포기 : <span class="point-color04">${allDropCliCorpCountList[0].YEARLYCOUNT}</span>)</c:otherwise>
									</c:choose>									
								</c:if>
							</td>
							<td>
								<c:if test="${allCliCorpCount.YEARLY  eq '2'}">									
									<c:choose>
										<c:when test="${empty allCliCorpCount.YEARLYCOUNT}">0</c:when>
										<c:otherwise>${allCliCorpCount.YEARLYCOUNT}&nbsp;(중도포기 : <span class="point-color04">${allDropCliCorpCountList[1].YEARLYCOUNT}</span>)</c:otherwise>
									</c:choose>		
								</c:if>
							</td>
							<td>
								<c:if test="${allCliCorpCount.YEARLY  eq '3'}">									
									<c:choose>
										<c:when test="${empty allCliCorpCount.YEARLYCOUNT}">0</c:when>
										<c:otherwise>${allCliCorpCount.YEARLYCOUNT}&nbsp;(중도포기 : <span class="point-color04">${allDropCliCorpCountList[2].YEARLYCOUNT}</span>)</c:otherwise>
									</c:choose>		
								</c:if>
							</td>
						</tr>
					</c:forEach>
						<c:if test="${loginVO.usertypeIdx ne 40 }">
						<c:forEach var="myCliCorpCount" items="${myCliCorpCountList }">
							<tr>
								<td class="bg01">나의 클리닉 기업</td>
								<td class="bg02">${myCliCorpCount.ALLCNT }</td>
							<td>
								<c:if test="${myCliCorpCount.YEARLY  eq '1'}">									
									<c:choose>
										<c:when test="${empty myCliCorpCount.YEARLYCOUNT}">0</c:when>
										<c:otherwise>${myCliCorpCount.YEARLYCOUNT}&nbsp;(중도포기 : <span class="point-color04">${myDropCliCorpCountList[0].YEARLYCOUNT}</span>)</c:otherwise>
									</c:choose>		
								</c:if>
							</td>
							<td>
								<c:if test="${myCliCorpCount.YEARLY  eq '2'}">									
									<c:choose>
										<c:when test="${empty myCliCorpCount.YEARLYCOUNT}">0</c:when>
										<c:otherwise>${myCliCorpCount.YEARLYCOUNT}&nbsp;(중도포기 : <span class="point-color04">${myDropCliCorpCountList[1].YEARLYCOUNT}</span>)</c:otherwise>
									</c:choose>		
								</c:if>
							</td>
							<td>
								<c:if test="${myCliCorpCount.YEARLY  eq '3'}">									
									<c:choose>
										<c:when test="${empty myCliCorpCount.YEARLYCOUNT}">0</c:when>
										<c:otherwise>${myCliCorpCount.YEARLYCOUNT}&nbsp;(중도포기 : <span class="point-color04">${myDropCliCorpCountList[2].YEARLYCOUNT}</span>)</c:otherwise>
									</c:choose>		
								</c:if>
							</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 기업 조회</h3>
		<!-- search -->
		<!-- 본부 직원(최고관리자 포함)과 지부지사 직원일 때 검색항목이 다름 -->
		<c:set var="searchList" value=""/>
		<c:choose>
			<c:when test="${loginVO.usertypeIdx eq '30'}">
				<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${DASHBOARD_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
				<!-- 검색조건이 있을 때 -->
            	<c:if test="${!empty param.key || !empty param.is_year}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_year=${param.is_year}"/></c:if>
			</c:when>
			<c:otherwise>
				<itui:searchFormItemForDct2 contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${DASHBOARD_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search_head}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
				<!-- 검색조건이 있을 때 -->
				<c:if test="${!empty param.key || !empty param.is_year || !empty param.is_cliInsttIdx}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_year=${param.is_year}&is_cliInsttIdx=${param.is_cliInsttIdx}"/></c:if>
			</c:otherwise>
		</c:choose>
		<!-- //search -->
	</div>
	<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
		</div>
		<div class="table-type01 horizontal-scroll">
			<table>	
				<caption>능력개발클리닉 기업 목록표 : 기업명, 연차, 주치의, 등록일에 대한 정보 제공표</caption>			
				<colgroup>
					<col style="width:40%">
					<col style="width:20%">
					<col style="width:20%">
					<col style="width:20%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">기업명</th>
						<th scope="col">연차</th>
						<th scope="col">주치의</th>
						<th scope="col">등록일</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="cliCorp" items="${list}">
					<tr>
						<td>
							<a href="${DASHBOARD_DETAIL_FORM_URL}&bpl_no=${cliCorp.BPL_NO}" class="btn-linked" style="display:inline;"><strong class="point-color01">${cliCorp.BPL_NM}&nbsp;</strong><img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;" alt="${cliCorp.BPL_NM} 기업정보 상세보기"></a>
						</td>
						<td>${cliCorp.YEARLY}</td>
						<td>${cliCorp.DOCTOR_NAME}</td>
						<td>${cliCorp.VALID_YEAR}</td>
					</tr>				
				</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${DASHBOARD_LIST_FORM_URL}${searchList}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
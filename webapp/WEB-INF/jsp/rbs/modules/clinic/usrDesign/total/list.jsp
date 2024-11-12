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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/total/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div class="contents-wrapper">

		<div class="contents-area02">
			<h3 class="title-type01 ml0">${corpInfo.BPL_NM} 종합이력</h3>
			<div class="management-search-wrapper">
				
<!-- 					<input type="hidden" name="mId" value="66"/> -->
					<fieldset class="clear">
						<legend class="blind">능력개발클리닉 종합이력 조건별 검색</legend>				
						<div class="search-area">
							<select id="is_year" title="연차 조건 검색창">
								<option value="0">전체</option>
								<option value="1">1년차</option>
								<option value="2">2년차</option>
								<option value="3">3년차</option>
							</select>
							<select id="is_essntl" title="필수항목 검색창">
								<option value="0">전체</option>
								<option value="Y">필수</option>
								<option value="N">선택</option>
							</select>
							<select id="is_actType" title="활동타입 검색창">
								<option value="0">전체</option>
								<option value="acmslt">능력개발활동</option>
								<option value="cnsl">컨설팅</option>
								<option value="plan">훈련계획</option>
								<option value="rslt">성과보고</option>
							</select>
							<div class="search-box depth">
								<button class="btn-m01 btn-color02" id="search" value="search">검색</button>
								<button class="btn-m01 btn-color03" id="searchAll" value="searchAll" >전체목록</button>
							</div>
						</div>	
					</fieldset>
<!-- 				</form> -->
			</div>
			<div class="table-type02 horizontal-scroll">
				<table>
					<caption>능력개발클리닉 주요 활동일지 상세표 : 구분, 지원항목, 신청상태, 연차, 신청일자, 신청 및 활동일지, 비용신청상태, 지급여부에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 7%">
						<col style="width: 9%">
						<col style="width: 13%">
						<col style="width: 9%">
						<col style="width: 6%">
						<col style="width: 10%">
						<col style="width: 16%">
						<col style="width: 12%">
						<col style="width: 12%">
						<col style="width: 9%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" colspan="2">구분</th>
							<th scope="col">지원 항목</th>
							<th scope="col">신청 상태</th>
							<th scope="col">연차</th>
							<th scope="col">신청일자</th>
							<th scope="col">신청/활동일지</th>
							<th scope="col">지원금액</th>
							<th scope="col">비용 신청 상태</th>
							<th scope="col">지급 여부</th>
						</tr>
					</thead>
					<tbody id="activityList">
						<c:if test="${empty activityList}">
						<tr>
							<td colspan="10" class="bllist">등록된 내용이 없습니다.</td>
						</tr>
						</c:if>
						<c:forEach var="listDt" items="${activityList}" varStatus="i" >	
							<tr>
								<td class="bg01">${listDt.ESSNTL_SE_NM}</td>
								<td class="bg02">${listDt.GUBUNNM}</td>
								<td>${listDt.SPORT_ITEM_NM}</td>
								<td>${confirmProgress[listDt.CONFM_STATUS]}</td>
								<td>${listDt.YEARLY}년</td>
								<td>${listDt.REGI_DATE}</td>
								<td>
									<strong class="point-color01">
									<c:choose>
										<c:when test="${listDt.GUBUNCD eq 'acmslt' || listDt.SPORT_ITEM_CD eq '07'}">
											<a href="${contextPath}/${crtSiteId}/clinic/activity_view_form.do?mId=68&acmsltIdx=${listDt.IDX}" class="btn-linked" style="display:inline;" target="_blank">활동 일지&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;" alt="활동일지 상세보기"></a>
										</c:when>
										<c:when test="${listDt.GUBUNCD eq 'plan' }">
											<a href="${contextPath}/${crtSiteId}/clinic/plan_view_form.do?mId=67&planIdx=${listDt.IDX}" class="btn-linked" style="display:inline;" target="_blank">훈련 계획서&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;" alt="훈련계획서 상세보기"></a>										
										</c:when>
										<c:when test="${listDt.GUBUNCD eq 'rslt' }">
											<a href="${contextPath}/${crtSiteId}/clinic/result_view_form.do?mId=87&resltIdx=${listDt.IDX}" class="btn-linked" style="display:inline;" target="_blank">성과 보고서&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;" alt="성과보고서 상세보기"></a>										
										</c:when>
										<c:when test="${listDt.GUBUNCD eq 'cnsl' }">
											<a href="<c:out value="${contextPath}/${crtSiteId}/consulting/viewAll.do?mId=95&cnslIdx=${listDt.IDX}"/>" class="btn-linked" style="display:inline;" target="_blank"><c:out value="${listDt.SPORT_ITEM_NM}"></c:out>&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;" alt="컨설팅 상세보기"></a>										
										</c:when>										
									</c:choose>
									</strong>
								</td>
								<td><fmt:formatNumber value="${listDt.SUPPORT_COST}" pattern="#,###,###,###,###"/>원</td>
								<td>${listDt.SPRMNY_REQST_YN}</td>
								<td>${listDt.SPRMNY_PYMNT_YN}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="8">합계</td>
							<td colspan="2"><fmt:formatNumber value="${activityList[0].SUM_SUPPORT_COST }" pattern="#,###,###,###,###"/>원</td>
						</tr>
					</tbody>
				</table>
			</div>		
		</div>		
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
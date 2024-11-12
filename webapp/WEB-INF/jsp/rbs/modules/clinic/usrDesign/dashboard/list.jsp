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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/dashboard/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div class="contents-wrapper">
		<h3 class="title-type01 ml0">${corpInfo.BPL_NM} 능력개발클리닉 활동 현황</h3>
		<div class="enterprise-board-wrapper depth3">
			<div class="enterprise-board-area01 type02">
				<h2 class="enterprise-board-title">훈련계획 수립</h2>
				<div class="enterprise-board-btn">
					<a href="${contextPath}/${crtSiteId}/clinic/plan_view_form.do?mId=67&planIdx=${planProgress[0].PLAN_IDX }" class="btn-m02 btn-color03" target="_blank"><span>조회</span></a>
				</div>
				<div class="enterprise-step-wrapper depth3">
					<ul class="enterprise-step-area">
						<li class="progressbar bar01">
							<a class="<c:if test="${planProgress[0].CONFM_STATUS ge 10 }">active</c:if>"></a>
								<span class="radio-bar" title="신청"></span>
								<p>신청</p>
							</a>
						</li>
						<li class="progressbar bar02">
							<a class="<c:if test="${planProgress[0].CONFM_STATUS ge 30 }">active</c:if>">
								<span class="radio-bar" title="접수"></span>
								<p>접수</p>
							</a>
						</li>
						<li class="progressbar bar03">
							<a class="<c:if test="${planProgress[0].CONFM_STATUS ge 55 }">active</c:if>">
								<span class="radio-bar" title="승인"></span>
								<p>승인</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="enterprise-board-area01 type02">
				<h2 class="enterprise-board-title">활동일지</h2>
				<div class="enterprise-board-btn">
					<a href="${contextPath}/${crtSiteId}/clinic/activity_list_form.do?mId=68" class="btn-m02 btn-color02" target="_blank"><span>일지목록</span></a>
				</div>
				<div class="enterprise-step-txt">
					활동일지 <span class="point-color01">${activityCount}</span>건 작성
				</div>
			</div>
			<div class="enterprise-board-area01 type02">
				<h2 class="enterprise-board-title">성과보고</h2>
				<div class="enterprise-board-btn">
<%-- 					<a href="${RESULT_VIEW_FORM_URL}&resltIdx=${resultProgress[0].RESLT_IDX}" class="btn-m02 btn-color03"><span>조회</span></a> --%>
					<a href="${contextPath}/${crtSiteId}/clinic/result_view_form.do?mId=87&resltIdx=${resultProgress[0].RESLT_IDX}" class="btn-m02 btn-color03" target="_blank"><span>조회</span></a>
				</div>
				<div class="enterprise-step-wrapper depth3">
					<ul class="enterprise-step-area">
						<li class="progressbar bar01">
							<a class="<c:if test="${resultProgress[0].CONFM_STATUS ge 10 }">active</c:if>">
								<span class="radio-bar" title="신청"></span>
								<p>신청</p>
							</a>
						</li>
						<li class="progressbar bar02">
							<a class="<c:if test="${resultProgress[0].CONFM_STATUS ge 30 }">active</c:if>">
								<span class="radio-bar" title="접수"></span>
								<p>접수</p>
							</a>
						</li>
						<li class="progressbar bar03">
							<a class="<c:if test="${resultProgress[0].CONFM_STATUS ge 55 }">active</c:if>">
								<span class="radio-bar" title="승인"></span>
								<p>승인</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="enterprise-board-area01 type02">
				<h2 class="enterprise-board-title">비용청구</h2>
				<div class="enterprise-board-btn">
					<a href="${contextPath}/${crtSiteId}/clinic/support_list_form.do?mId=88" class="btn-m02 btn-color02" target="_blank"><span>목록</span></a>
				</div>
				<div class="depth3">
					<ul class="enterprise-step-area">
						<li class="progressbar bar01">
							<p>신청</p>
							<div class="mt25 fs18"><strong class="point-color01 fs36">${supportProgress[0].STATUSCT}</strong>건</div>
						</li>
						<li class="progressbar bar02">
							<p>접수</p>
							<div class="mt25 fs18"><strong class="point-color01 fs36">${supportProgress[1].STATUSCT}</strong>건</div>
						</li>
						<li class="progressbar bar03">
							<p>승인</p>
							<div class="mt25 fs18"><strong class="point-color01 fs36">${supportProgress[2].STATUSCT}</strong>건</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="contents-area02 mt40">
			<h3 class="title-type01 ml0">${corpInfo.BPL_NM} 능력개발클리닉 주요 활동일지</h3>
			<div class="management-search-wrapper">
				
<%-- 				<form id="dashboardList" name="dashboardList" action="${DASHBOARD_LIST_FORM_URL}" method="get"> --%>
<!-- 					<input type="hidden" name="mId" value="66"/> -->
					<fieldset class="clear">
						<legend class="blind">능력개발클리닉 종합관리 조건별 검색</legend>				
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
					<caption>능력개발클리닉 주요 활동일지 상세표 : 구분, 지원항목, 신청상태, 연차, 활동명, 활동일시, 비용신청여부, 지급여부에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 7%">
						<col style="width: 15%">
						<col style="width: 12%">
						<col style="width: 10%">
						<col style="width: 9%">
						<col style="width: 21%">
						<col style="width: 12%">
						<col style="width: 13%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" colspan="2">구분</th>
							<th scope="col">지원 항목</th>
							<th scope="col">신청 상태</th>
							<th scope="col">연차</th>
							<th scope="col">활동일</th>
							<th scope="col">비용 신청 상태</th>
							<th scope="col">지급 여부</th>
						</tr>
					</thead>
					<tbody id="activityList">
						<c:if test="${empty activityList}">
						<tr>
							<td colspan="8" class="bllist">등록된 내용이 없습니다.</td>
						</tr>
						</c:if>
						<c:forEach var="listDt" items="${activityList}" varStatus="i" >	
							<tr>
								<td class="bg01">${listDt.ESSNTL_SE_NM}</td>
								<td class="bg02">${listDt.GUBUNNM}</td>
								<td>${listDt.SPORT_ITEM_NM}</td>
								<td>${confirmProgress[listDt.CONFM_STATUS]}</td>
								<td>${listDt.YEARLY}년</td>
								<td>${listDt.ACT_DATE}</td>
								<td>${listDt.SPRMNY_REQST_YN}</td>
								<td>${listDt.SPRMNY_PYMNT_YN}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
<!-- <script type="text/javascript" src="/web/js/develop/recommended_training.js"></script>	 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
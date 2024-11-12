<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />

<c:set var="today" value="${cnsl.regiDate}" />
<c:set var="year">
	<fmt:formatDate value="${today}" pattern="yyyy" />
</c:set>
<c:set var="month">
	<fmt:formatDate value="${today}" pattern="MM" />
</c:set>
<c:set var="day">
	<fmt:formatDate value="${today}" pattern="dd" />
</c:set>

<style>
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
	background-color: #fff;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%
}

.close {
	color: #888;
	float: right;
	font-size: 20px;
	cursor: pointer;
}

.tabcontent {
	margin-bottom: 20px;
}

.hidden {
	display: none;
}
</style>

<!-- CMS 시작 -->

<div class="tabmenu-wrapper">
	<ul class="tabs">
		<li><a href="#tab1" class="tablinks active"
			onclick="showTab(this, 1)"> 컨설팅 신청 </a></li>
		<li><a href="#tab2" class="tablinks"
			onclick="showTab(this, 2)"> 변경 신청 </a></li>
		<li><a href="#tab3" class="tablinks"
			onclick="showTab(this, 3)"> 수행일지 관리 </a></li>
		<li><a href="#tab4" class="tablinks"
			onclick="showTab(this, 4)"> 보고서 관리 </a></li>
	</ul>
</div>

<input type="hidden" id="id_mId" value="95" />

<div id='tab1' class="tabcontent">

	<c:choose>
		<c:when
			test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
			<%@ include file="../corp/cnslTypeA/cnslViewFormTypeA.jsp"%>
		</c:when>
		<c:when
			test="${cnsl.cnslType eq '4' or cnsl.cnslType eq '5' or cnsl.cnslType eq '6'}">
			<%@ include file="../corp/cnslTypeB/cnslViewFormTypeB.jsp"%>
		</c:when>
	</c:choose>
	<c:if test="${loginVO.usertypeIdx eq 5}">
		<c:choose>
			<c:when test="${(cnsl.confmStatus eq 55 or cnsl.confmStatus eq 72) and listReport[0].CONFM_STATUS ne 55}">
				<div class="btns-right">
					<button type="button" class="btn-m01 btn-color04 depth3"
						onclick="reqDropOut(`${cnsl.cnslIdx}`, 75)">중도포기</button>
				</div>
			</c:when>
			<c:when test="${cnsl.confmStatus eq 75}">
				<div class="btns-right">
					<button type="button" class="btn-m01 btn-color03 depth3">승인
						대기중</button>
				</div>
			</c:when>
		</c:choose>
	</c:if>
	<c:if test="${loginVO.usertypeIdx eq 20}">
		<c:choose>
			<c:when test="${(cnsl.confmStatus eq 55 or cnsl.confmStatus eq 72) and listReport[0].CONFM_STATUS ne 55}">
				<div class="btns-right">
					<button type="button" class="btn-m01 btn-color04 depth3"
						onclick="reqDropOut(`${cnsl.cnslIdx}`, 80)">중도포기</button>
				</div>
			</c:when>
			<c:when test="${cnsl.confmStatus eq 75}">
				<div class="btns-right">
					<button type="button" class="btn-m01 btn-color04 depth3"
						onclick="reqDropOut(`${cnsl.cnslIdx}`, 80)">중도포기 승인</button>
				</div>
			</c:when>
		</c:choose>
	</c:if>
</div>

<div id='tab2' class="tabcontent hidden">
	<c:choose>
		<c:when test="${(empty changeCnsl and listReport[0].CONFM_STATUS ne 55) or (changeCnsl.confmStatus eq '53' and listReport[0].CONFM_STATUS ne 55)}">
			<c:choose>
				<c:when test="${loginVO.usertypeIdx eq 5}"><%@ include
						file="cnslChangeForm.jsp"%></c:when>
				<c:when test="${loginVO.usertypeIdx eq 20}">
					<div class="contents-area pl0">
					<h3 class="title-type01 ml0">등록된 변경신청 내역이 없습니다.</h3>
					</div>
				</c:when>
			</c:choose>
		</c:when>
		<c:when test="${!empty changeCnsl}">
			<c:choose>
				<c:when test="${loginVO.usertypeIdx eq 5}"><%@ include
						file="cnslChangeViewForm.jsp"%></c:when>
				<c:when test="${loginVO.usertypeIdx eq 20}"><%@ include
						file="cnslChangeAdminView.jsp"%></c:when>
			</c:choose>
		</c:when>
	</c:choose>


</div>



<!-- //CMS 끝 -->
<div id='tab3' class="tabcontent hidden">
	<div class="contents-area pl0">
		<h3 class="title-type01 ml0">컨설팅 수행일지</h3>
		<p class="total fl">
			총 <strong><c:out value="${totalCount}" /></strong> 건 (
			<c:out value="${paginationInfo.currentPageNo}" />
			/
			<c:out value="${paginationInfo.lastPageNo}" />
			페이지 )
		</p>

		<div class="board-list">
			<table>
				<caption>신청한 컨설팅 팀 수행일지 목록 정보표 : No, 제목, 등록자, 등록일에 관한 정보
					제공표
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
								<c:out value="${i.count}" />
							</td>
							<td class="title">
							<a href="diaryView.do?mId=99&${listIdxName}=${listKey}&diaryIdx=${listDt.DIARY_IDX}&mode=m" class="fn_btn_view"> 
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
					<pgui:paginationDiary listUrl="${contextPath}/web/consulting/viewAll.do?mId=${mId}&cnslIdx=${map.cnslIdx}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
		<c:if test="${loginVO.usertypeIdx eq '10' and (list[0].CONFM_STATUS ne 10 and list[0].CONFM_STATUS ne 50 and list[0].CONFM_STATUS ne 55)}">
			<div class="btn-area">
				<div class="btns-right">
					<a href="${contextPath}/web/consulting/diaryInput.do?mId=99&bplNo=${map.bplNo}&cnslIdx=${map.cnslIdx}" class="btn-m01 btn-color01"> 수행일지 작성 </a>
				</div>
			</div>
		</c:if>
	</div>
</div>

<div id='tab4' class="tabcontent hidden">
	<div class="contents-area">
		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>분류 관리 적극 발굴 정보표 : No, 제목, 등록자, 등록일에 관한 정보표</caption>
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
					<c:if test="${empty listReport}">
						<tr>
							<td colspan="8" class="bllist"><spring:message
									code="message.no.list" /></td>
						</tr>
					</c:if>
					<c:set var="listIdxName" value="${settingInfo.idx_name}" />
					<c:set var="listColumnName" value="${settingInfo.idx_column}" />
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${listReport}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}" />
						<tr id="column">
							<td class="num">
								<c:out value="${i.count}" />
							</td>
							<td class="corpNm"><c:out value="${cnsl.corpNm}" /></td>
							<td class="bplNo"><c:out value="${cnsl.bplNo}" /></td>
							<td class="cmptncBrffcNm"><c:out
									value="${cnsl.cmptncBrffcNm}" /></td>
							<td class="cnslType"><c:out
									value="${cnsl.cnslType eq '1' ? '사업주' : cnsl.cnslType eq '2' ? '일반직무전수 OJT' : cnsl.cnslType eq '3' ? '과제수행 OJT' : cnsl.cnslType eq '4' ? '심층진단' : cnsl.cnslType eq '5' ? '훈련체계수립' : cnsl.cnslType eq '6' ? '현장활용' : ''}" />
							</td>
							<td class="regiDate"><c:out
									value="${fn:substring(listDt.REGI_DATE,0,10)}" /></td>
							<td class="confmStatus"><c:choose>
									<c:when test="${listDt.CONFM_STATUS eq 40}">
										<button type="button" class="btn-m02 btn-color03"
											onclick="openViewReject('openViewReject', `${listDt.CNSL_IDX}`)">
											<span>반려사유</span>
										</button>
									</c:when>
									<c:otherwise>
										<c:out
											value="${listDt.CONFM_STATUS eq 50 || listDt.CONFM_STATUS eq 55 ? '기업승인' : listDt.CONFM_STATUS eq 40 ? '반려' : '결재 전'}" />
									</c:otherwise>
								</c:choose></td>
							<td>
								<c:choose>
									<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '6'}">
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 5 or listDt.CONFM_STATUS eq 40}">
												<button type="button" class="btn-ing btn-m02 btn-color03"
													onclick="location.href='${contextPath}/web/report/input.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}&reprtIdx=${listDt.REPRT_IDX}'">
													<c:out value="${listDt.CONFM_STATUS eq 5 ? '작성중' : listDt.CONFM_STATUS eq 10 ? '제출' : listDt.CONFM_STATUS eq 40 ? '반려' : listDt.CONFM_STATUS eq 50 ? '기업승인' : listDt.CONFM_STATUS eq 55 ? '최종승인' : '-'}" />
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/view.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}&reprtIdx=${listDt.REPRT_IDX}'">
													<c:out value="${listDt.CONFM_STATUS eq 5 ? '작성중' : listDt.CONFM_STATUS eq 10 ? '제출' : listDt.CONFM_STATUS eq 40 ? '반려' : listDt.CONFM_STATUS eq 50 ? '기업승인' : listDt.CONFM_STATUS eq 55 ? '최종승인' : '-'}" />
												</button>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 5}">
												<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/inputReport.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}'">작성중</button>
											</c:when>
											<c:when test="${listDt.CONFM_STATUS eq 10}">
												<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/viewReport.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}'">제출</button>
											</c:when>
											<c:when test="${listDt.CONFM_STATUS eq 40}">
												<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/inputReport.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}'">반려</button>
											</c:when>
											<c:when test="${listDt.CONFM_STATUS eq 50}">
												<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/viewReport.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}'">기업 승인</button>
											</c:when>
											<c:when test="${listDt.CONFM_STATUS eq 55}">
												<button type="button" class="btn-ing btn-m02 btn-color03" onclick="location.href='${contextPath}/web/report/viewReport.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}'">최종 승인</button>
											</c:when>
										</c:choose>
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
					<pgui:pagination
						listUrl="${contextPath}/web/report/list.do?mId=${mId}"
						pgInfo="${paginationInfo}" imgPath="${imgPath}"
						pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
		<c:if test="${empty listReport && loginVO.usertypeIdx eq '10'}">
			<div class="btn-area">
				<div class="btns-right">
					<c:choose>
						<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '6'}">
							<a href="${contextPath}/web/report/input.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}"
								class="btn-m01 btn-color01"> 보고서 작성 </a>
						</c:when>
						<c:otherwise>
							<a href="${contextPath}/web/report/inputReport.do?mId=100&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}"
								class="btn-m01 btn-color01"> 보고서 작성 </a>
						</c:otherwise>

					</c:choose>

				</div>
			</div>
		</c:if>
	</div>
</div>


<!-- //CMS 끝 -->

<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="myModal">
	<h2>반려의견 입력</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<form id="rejectionForm">
				<input type="hidden" id="cnslIdxByModal" value="${cnsl.cnslIdx}">
				<input type="hidden" id="chgIdxByModal" value="${changeCnsl.chgIdx}">
				<input type="hidden" id="statusByModal" value="53">
				<textarea id="confmCn" name="confmCn" row="4"> </textarea>
			</form>
			
		<div class="btns-area">
		<button class="btn-m02 btn-color03" onclick="reqChangeCnslReject()">반려</button>
		<button type="button" id="closeBtn_05" onclick="closeModal('myModal')" class="btn-m02 btn-color02 three-select">
				<span> 닫기 </span>
			</button>
				</div>
		</div>
	</div>
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="openViewReject">
	<h2>반려의견 조회</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper">
				<c:forEach var="cs" items="${confmStatus}" varStatus="i">
					<div class="one-box">
						<dl>
							<dt>
								<label> 날짜</label>
							</dt>
							<dd>
								<c:out value="${fn:substring(cs.REGI_DATE,0,16)}"/>
							</dd>
						</dl>
												<dl>
							<dt>
								<label> 작성자</label>
							</dt>
							<dd>
								<c:out value="${cs.REGI_NAME}"/>
							</dd>
						</dl>
						<dl>
							<dt>
								<label> 의견 </label>
							</dt>

							<dd>
								<textarea id="id_confmCn" name="confmCn" rows="4"
									placeholder="의견을 입력하세요" readOnly><c:out
										value="${cs.CONFM_CN}" /></textarea>
							</dd>
						</dl>
					</div>
				</c:forEach>
			</div>
			<div class="btns-area">
				<button type="button" id="closeBtn_05"
					onclick="closeViewReject('openViewReject')"
					class="btn-m02 btn-color02 three-select">
					<span> 닫기 </span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="mask"></div>
<div class="modal-wrapper" id="selectNcsModal">
	<h2>직무분류 검색</h2>
	<div class="modal-area">
		<div id="overlay"></div>
		<div class="loader"></div>

		<div class="contents-box pl0">
			<div class="basic-search-wrapper">
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield04"> NCS 대분류 </label>
						</dt>
						<dd>
							<select id="ncsDepth1" class="w50"
								onChange="ncsSelectHandler(this,`ncsDepth2`)">
								<option value="">선택</option>
								<c:forEach var="ncs" items="${ncsInfoDepth1}">
									<option value="${ncs.NCS_CODE}"><c:out
											value="${ncs.NCS_NAME}" /></option>
								</c:forEach>
							</select>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield04"> NCS 중분류 </label>
						</dt>
						<dd>
							<select id="ncsDepth2" class="w50"
								onChange="ncsSelectHandler(this,`ncsDepth3`)">
								<option value="">선택</option>
							</select>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield04"> 검색 </label>
						</dt>
						<dd>
							<input type="text" id ="searchNcsInput" class="w50" placeholder="">
							<button type="button" class="btn-m03 btn-color01" onclick="searchNcs(`searchNcsInput`)">검색</button>
						</dd>
					</dl>
				</div>
			</div>
		</div>



		<div class="contents-box pl0">

			<div class="table-type01 horizontal-scroll table-container">
				<table class="width-type03 modal-table">
					<caption>업체정보표 : 직무분류 에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 25%" />
						<col style="width: 25%" />
						<col style="width: 50%" />
					</colgroup>
					<thead class="modal-thead">
						<tr>
							<th scope="col">NCS코드</th>
							<th scope="col">직무분류명</th>
							<th scope="col">선택</th>
						</tr>
					</thead>
					<tbody id="ncsInfoTbody">
					<tr>
						<td colspan="3" class="bllist"><span>검색된 내용이 없습니다.</span></td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<button type="button" class="btn-modal-close"
		onclick="closeModal('selectNcsModal')">모달 창 닫기</button>


</div>

<script type="text/javascript" src="${contextPath}${jsPath}/report.js"></script>
<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<script type="text/javascript" src="${contextPath}${jsPath}/tabHandler.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
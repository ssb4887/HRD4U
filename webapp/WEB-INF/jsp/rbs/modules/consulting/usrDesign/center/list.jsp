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
</style>

<!-- contents  -->
<article>
	<div class="contents" id="contents">
		<div class="contents-wrapper">


			<!-- CMS 시작 -->

			<div class="contents-area02">
				<form action="<c:out value=" ${formAction}"/>
				" method="get"
					id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
					<input type="hidden" name="mId" value="64">

					<legend class="blind"> 컨설팅 신청 검색 </legend>
					<div class="basic-search-wrapper">
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_cnslType"> 종류 </label>
									</dt>
									<dd>
										<select id="is_cnslType" name="is_cnslType">
											<option value="">선택</option>
											<option value="1">사업주훈련</option>
											<option value="2">일반직무전수 OJT</option>
											<option value="3">과제수행 OJT</option>
											<option value="4">심층진단</option>
											<option value="5">훈련체계수립</option>
											<option value="6">현장활용</option>
										</select>
									</dd>
								</dl>
							</div>
							

							
							<div class="half-box">
								<dl>
									<dt>
										<label for="calendar0101"> 기간 </label>
									</dt>
									<dd>
										<div class="input-calendar-wrapper">
											<div class="input-calendar-area">
												<input type="text" id="startDate" name="startDate"
													class="sdate" placeholder="기준 시작일" autocomplete="off">
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="endDate" name="endDate" class="edate" placeholder="기준  종료일" autocomplete="off">
											</div>
										</div>
									</dd>
								</dl>
							</div>
						</div>


					</div>

					<div class="btns-area">
						<button type="submit" class="btn-search02">
							<img src="../img/icon/icon_search03.png" alt="" /> <strong>
								검색 </strong>
						</button>
						<button type="button" class="btn-search02" style="margin-left: 15px;" onclick="initSearhParams()">
							<strong>초기화</strong>
						</button>
					</div>
				</form>
			</div>



			<div class="contents-area">
				<div class="title-wrapper">
					<p class="total fl">
						총 <strong>${paginationInfo.totalRecordCount}</strong> 건 (
						${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지
						)
					</p>
					
									
				<div class="btnList">
					<div class="btns-right">
						<a href="${contextPath}/web/consulting/cnslApplyForm.do?mId=85&bplNo=${bplNo}&type=B" class="btn-m01 btn-color01">신청서 작성</a>
					</div>
				</div>
				</div>

				
				<div class="table-type01 horizontal-scroll" style="padding-bottom: 30px;">
					<table>
						<caption>컨설팅 신청 내역 접수표 : 체크, 번호, 기업명, 사업장관리번호, 소속기관,
							신청일, 상태, 접수에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 6%">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: 20%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col"><itui:objectItemName itemId="corpNm"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="cnslType"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="cnslTme"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><spring:message code="item.regidate.name" /></th>
								<th scope="col"><itui:objectItemName itemId="confmStatus"
										itemInfo="${itemInfo}" /></th>
								<th scope="col">회수 및 반려요청</th>
							</tr>
						</thead>


						<tbody class="alignC">
							<c:if test="${empty list}">
								<tr>
									<td colspan="7" class="bllist"><spring:message
											code="message.no.list" /></td>
								</tr>
							</c:if>
							<c:set var="listIdxName" value="${settingInfo.idx_name}" />
							<c:set var="listColumnName" value="${settingInfo.idx_column}" />
							<c:set var="listNo"
								value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
							<c:forEach var="listDt" items="${list}" varStatus="i">
								<c:set var="listKey" value="${listDt[listColumnName]}" />
								
								<tr>
									<td class="num">${listNo}</td>
									<td>
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 5 or listDt.CONFM_STATUS eq 20 or listDt.CONFM_STATUS eq 40}">
												<c:choose>
													<c:when test="${listDt.CNSL_TYPE eq 1 or listDt.CNSL_TYPE eq 2 or listDt.CNSL_TYPE eq 3 }">
														<a href="cnslModifyForm.do?mId=85&cnslIdx=${listDt.CNSL_IDX}" />
													</c:when>
													<c:otherwise>
														<a href="cnslModifyForm.do?mId=85&cnslIdx=${listDt.CNSL_IDX}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${listDt.CNSL_TYPE eq 1 or listDt.CNSL_TYPE eq 2 or listDt.CNSL_TYPE eq 3 }">
														<a href="cnslViewForm.do?mId=85&cnslIdx=${listDt.CNSL_IDX}" />
													</c:when>
													<c:otherwise>
														<a href="cnslViewForm.do?mId=85&cnslIdx=${listDt.CNSL_IDX}" />
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<strong class="point-color01"> 
											<itui:objectView itemId="corpNm" itemInfo="${itemInfo}" objDt="${listDt}" /></strong>
									</td>
									<td>
										<c:choose>
											<c:when test="${listDt.CNSL_TYPE eq '1'}">과정개발(사업주)</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '2'}">과정개발(일반직무전수 OJT)</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '3'}">과정개발(과제수행 OJT)</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '4'}">심층진단</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '5'}">훈련체계수립</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '6'}">현장개산</c:when>
										</c:choose> 
									</td>
									<td>
										<itui:objectView itemId="cnslTme" itemInfo="${itemInfo}" objDt="${listDt}" />
									</td>

									<td>
										<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}" />
									</td>
									<td>
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq '5'}">저장</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '10'}">신청</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '20'}">회수</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '30'}">접수</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '40'}">반려</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '35'}">반려요청</c:when>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 10}">
												<button type="button" class="btn-m02 btn-color03" onclick="cancelCnsl(`${listDt.CNSL_IDX}`,`20`)"><span>회수</span></button>
											</c:when>
											<c:when test="${listDt.CONFM_STATUS eq 30}">
												<button type="button" class="btn-m02 btn-color03" onclick="reqRejectCnsl(`${listDt.CNSL_IDX}`,`35`)"><span>반려요청</span></button>
											</c:when>
											<c:when test="${listDt.CONFM_STATUS eq 40}">
												<button type="button" class="btn-m02 btn-color03" onclick="openModal('reject-modal', `${listDt.CNSL_IDX}`)"><span>반려사유</span></button>
											</c:when>
										</c:choose>
									</td>
									
								</tr>
								<c:set var="listNo" value="${listNo - 1}" />
							</c:forEach>
						</tbody>

					</table>
				</div>
				

					<div class="paging-navigation-wrapper">
						<pgui:pagination listUrl="list.do?mId=64" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
					</div>
					<!-- //페이징 네비게이션 -->
				</div>
			</div>


			<!-- //CMS 끝 -->

<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="reject-modal">
	<h2>반려의견 조회</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper" id="contentsWrapper">

			</div>
			<div class="btns-area">
				<button type="button" id="closeBtn_05"
					onclick="closeModal('reject-modal')"
					class="btn-m02 btn-color02 three-select">
					<span> 닫기 </span>
				</button>
			</div>
		</div>
	</div>
</div>







<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<script type="text/javascript" src="${contextPath}${jsPath}/list.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
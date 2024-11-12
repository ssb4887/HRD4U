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


<!-- CMS 시작 -->

<div class="contents-area02">
	<form action="<c:out value=" ${formAction}"/>
				" method="get"
		id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
		<input type="hidden" name="mId" value="95">
		<legend class="blind"> 컨설팅 관리 검색양식 </legend>
		<div class="basic-search-wrapper">
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label for="is_bplNo"> 사업장관리번호 </label>
						</dt>
						<dd>
							<input type="text" id="is_bplNo" name="is_bplNo" value=""
								title="사업장관리번호 입력" placeholder="사업장관리번호">
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label for="is_cmptncBrffcIdx"> 소속기관 </label>
						</dt>
						<dd>
							</select> <select id="is_cmptncBrffcIdx" name="is_cmptncBrffcIdx"
								class="w200">
								<option value="">선택</option>
								<c:forEach var="instt" items="${insttList}" varStatus="i">
									<option value="${instt.INSTT_IDX}">${instt.INSTT_NAME}</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
				</div>
			</div>
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label for="is_confmStatus"> 상태 </label>
						</dt>
						<dd>
							<select id="is_confmStatus" name="is_confmStatus">
								<option value="">선택</option>
								<option value="40">반려</option>
								<option value="55">승인</option>
								<option value="70">변경요청</option>
								<option value="75">중도포기요청</option>
							</select>
						</dd>
					</dl>
				</div>


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
			</div>
		</div>

		<div class="btns-area">
			<button type="submit" class="btn-search02">
				<img src="../img/icon/icon_search03.png" alt="" /> <strong>
					검색 </strong>
			</button>
			<button type="button" class="btn-search02" style="margin-left: 15px;"
				onclick="initSearhParams()">
				<strong>초기화</strong>
			</button>
		</div>
	</form>
</div>



<div class="contents-area">
	<div class="title-wrapper">
		<p class="total fl">
			총 <strong>${paginationInfo.totalRecordCount}</strong> 건 (
			${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지 )
		</p>
	</div>
	<div class="table-type01 horizontal-scroll">
		<table>

			<colgroup>
				<col style="width: 5%">
				<col style="width: auto">
				<col style="width: auto">
				<col style="width: auto">
				<col style="width: auto">
				<col style="width: auto">
				<col style="width: auto">
				<col style="width: auto">
				<col style="width: 7%">
				<col style="width: 7%">
				<col style="width: 7%">
				<col style="width: 7%">
				<col style="width: 7%">
				<c:if test="${userTypeIdx eq 20}">
					<col style="width: 7%">
					<col style="width: 7%">
				</c:if>
			</colgroup>
			<thead>
				<tr>
					<th scope="col">연번</th>
					<th scope="col"><itui:objectItemName itemId="corpNm"
							itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="bplNo"
							itemInfo="${itemInfo}" /></th>
					<th scope="col">관할기관</th>
					<th scope="col">구분</th>
					<th scope="col">유형</th>
					<th scope="col">신청일</th>
					<th scope="col"><itui:objectItemName itemId="confmStatus"
							itemInfo="${itemInfo}" /></th>
					<th scope="col">변경<br>상태</th>
					<th scope="col">처리자</th>
					<th scope="col">처리일</th>
					<th scope="col">컨설팅<br>수행일지</th>
					<th scope="col">컨설팅<br>보고서</th>
					<c:if test="${userTypeIdx eq 20}">
						<th scope="col">컨설팅<br>팀평가</th>
						<th scope="col">추가신청</th>
					</c:if>

				</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
					<tr>
						<td colspan="12" class="bllist"><spring:message
								code="message.no.list" /></td>
					</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}" />
				<c:set var="listColumnName" value="${settingInfo.idx_column}" />
				<c:set var="listNo"
					value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
					<tr>
						<td class="num">${listNo}</td>
						<td><a
							href="${contextPath}/web/consulting/viewAll.do?mId=95&cnslIdx=${listDt.cnslIdx}" />
							<strong class="point-color01"> <c:out
									value="${listDt.corpNm}" /></strong></a></td>
						<td class="subject"><c:out value="${listDt.bplNo}" /></td>
						<td><c:forEach var="instt" items="${insttList}" varStatus="i">
								<c:if test="${listDt.cmptncBrffcIdx eq instt.INSTT_IDX}">
									<c:out value="${instt.INSTT_NAME}" />
								</c:if>
							</c:forEach></td>
						<td><c:choose>
								<c:when
									test="${listDt.cnslType eq '1' or listDt.cnslType eq '2' or listDt.cnslType eq '3'}">과정개발</c:when>
								<c:when
									test="${listDt.cnslType eq '4' or listDt.cnslType eq '5' or listDt.cnslType eq '6'}">심화</c:when>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${listDt.cnslType eq '1'}">사업주</c:when>
								<c:when test="${listDt.cnslType eq '2'}">일반직무전수 OJT</c:when>
								<c:when test="${listDt.cnslType eq '3'}">과제수행 OJT</c:when>
								<c:when test="${listDt.cnslType eq '4'}">심층진단</c:when>
								<c:when test="${listDt.cnslType eq '5'}">훈련체계수립</c:when>
								<c:when test="${listDt.cnslType eq '6'}">현장활용</c:when>
							</c:choose></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${listDt.regiDate}" /></td>
						<td>
						<c:choose>
							<c:when test="${listDt.confmStatus eq 40}"><span onclick="openModal('reject-modal', `${listDt.cnslIdx}`)"><strong class="point-color03"> 반려</strong></span></c:when>
							<c:otherwise><strong class="point-color02"> 승인</strong></c:otherwise>
						</c:choose>
						
						</td>
						<td><c:choose>
								<c:when test="${listDt.confmStatus eq 70}">
									<a
										href="${contextPath}/web/consulting/viewAll.do?mId=95&cnslIdx=${listDt.cnslIdx}#tab2"><strong
										class="point-color01">변경 요청</strong></a>
								</c:when>
								<c:when test="${listDt.confmStatus eq 72}">
									<a href="#"><strong class="point-color02"> 승인</strong></a>
								</c:when>
								<c:when test="${listDt.confmStatus eq 53}">
									<span onclick="openModal('changeReject-modal', `${listDt.cnslIdx}`)"><strong class="point-color03"> 반려</strong></span>			
									
								</c:when>
								<c:when test="${listDt.confmStatus eq 75}">
									<a
										href="${contextPath}/web/consulting/viewAll.do?mId=95&cnslIdx=${listDt.cnslIdx}#tab1"><strong
										class="point-color01">중도포기 요청</strong></a>
								</c:when>


								<c:otherwise>
									<a href="#"><strong class="point-color01"> - </strong></a>
								</c:otherwise>
							</c:choose></td>
							
							<td><c:out value="${listDt.lastModiName}"/></td>
							<td><fmt:formatDate value="${listDt.lastModiDate}" pattern="yyyy-MM-dd"/></td>
						<td>
								
						<c:choose>
								<c:when test="${listDt.confmStatus eq 40}"><strong class="point-color01">-</strong></c:when>
								<c:otherwise>
									<a href="${contextPath}/web/consulting/viewAll.do?mId=95&cnslIdx=<c:out value="${listDt.cnslIdx}"/>&bplNo=<c:out value="${listDt.bplNo}"/>#tab3"><strong
								class="point-color01"> 이동</strong></a>
								</c:otherwise>
							
						</c:choose>		
								
								
								
						</td>
						<td>
						
						
						
						<c:choose>
								<c:when test="${listDt.confmStatus eq 40}"><strong class="point-color01">-</strong></c:when>
								<c:otherwise>
									<a href="${contextPath}/web/consulting/viewAll.do?mId=95&cnslIdx=<c:out value="${listDt.cnslIdx}"/>&bplNo=<c:out value="${listDt.bplNo}"/>#tab4">
								<c:choose>
									<c:when test="${listDt.report.confmStatus eq 5}">
										<strong class="point-color01">작성중</strong>
									</c:when>
									<c:when test="${listDt.report.confmStatus eq 10}">
										<strong class="point-color02">제출</strong>
									</c:when>
									<c:when test="${listDt.report.confmStatus eq 40}">
										<strong class="point-color03">반려</strong>
									</c:when>
									<c:when test="${listDt.report.confmStatus eq 50}">
										<strong class="point-color02">기업승인</strong>
									</c:when>
									<c:when test="${listDt.report.confmStatus eq 55}">
										<strong class="point-color02">최종승인</strong>
									</c:when>
									<c:otherwise>
										<strong class="point-color01"> 이동</strong>
									</c:otherwise>
								</c:choose>
						</a>
								</c:otherwise>
							
						</c:choose>	
						
						
						</td>
							<c:if test="${userTypeIdx eq 20}">
							<td>
								<c:choose>
									<c:when test="${listDt.confmStatus eq 40}"><strong class="point-color01">-</strong></c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${userTypeIdx eq 20}">
												<a href="${contextPath}/web/consulting/evalList.do?mId=94&cnslIdx=<c:out value="${listDt.cnslIdx}"/>&bplNo=<c:out value="${listDt.bplNo}"/>">
													<strong class="point-color01">이동</strong>
												</a>
											</c:when>
											<c:otherwise>
												<a href="#">
													<strong class="point-color01"> - </strong>
												</a>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>			
							</td>
							<td>
								<c:if test="${listDt.confmStatus ne 40 and userTypeIdx eq 20 and listDt.cnslType eq 4 and listDt.limitCount < 3}">
									<a href="${contextPath}/web/consulting/cnslApplyFormByCenter.do?mId=95&cnslIdx=${listDt.cnslIdx}">
										<strong class="point-color01">신청</strong>
									</a>
								</c:if>
							</td>
						</c:if>
					</tr>
					<c:set var="listNo" value="${listNo - 1}" />
				</c:forEach>
			</tbody>
		</table>
	</div>


	<div class="paging-navigation-wrapper">
		<pgui:pagination listUrl="cnslListAll.do?mId=95"
			pgInfo="${paginationInfo}" imgPath="${imgPath}"
			pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
	</div>


</div>

	<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="changeReject-modal">
	<h2>반려의견 조회</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper" id="contentsWrapper">

			</div>
			<div class="btns-area">
				<button type="button" id="closeBtn_05"
					onclick="closeModal('changeReject-modal')"
					class="btn-m02 btn-color02 three-select">
					<span> 닫기 </span>
				</button>
			</div>
		</div>
		<button type="button" class="btn-modal-close" onclick="closeModal('changeReject-modal')">모달창 닫기</button>
	</div>
</div>

<!-- //CMS 끝 -->

<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<script type="text/javascript" src="${contextPath}${jsPath}/list.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
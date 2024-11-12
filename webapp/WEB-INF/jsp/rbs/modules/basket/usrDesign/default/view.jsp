<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp" />
	</jsp:include>
</c:if>
<script type="text/javascript" src="${contextPath}${jsPath}/basket.js"></script>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />

<style>
.tabcontent {
	margin-bottom: 20px;
}

.hidden {
	display: none;
}
</style>
<!-- CMS 시작 -->

<div class="tabmenu-wrapper">
	<ul>
		<li><a href="#" class="tablinks active" onclick="showTab(this, 1)">
				기업 정보 </a></li>
		<li><a href="#" class="tablinks" onclick="showTab(this, 2)">
				분류 정보 </a></li>
		<li><a href="#" class="tablinks" onclick="showTab(this, 3)">
				참여 이력 </a></li>
		<li><a href="#" class="tablinks" onclick="showTab(this, 4)">
				채용, 재무 정보 </a></li>
	</ul>
</div>


<!-- 기업 정보 탭 -->
<input type="hidden" name="mId" id="id_mId" value="42">

<div class="tabcontent" id="tab1">
	<div class="contents-area">
		<h3 class="title-type02 ml0">기업 기본 정보</h3>

		<div class="table-type02">
			<table>
				<caption>기업 기본 정보표</caption>
				<colgroup>
					<col style="width: 20%" />
					<col style="width: 80%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">기업명</th>
						<td class="left"><c:out value="${basket.bplNm}" /></td>
					</tr>
					<tr>
						<th scope="row">사업장 관리번호</th>
						<td class="left"><c:out value="${basket.bplNo}" /></td>
					</tr>
					<tr>
						<th scope="row">사업자 등록번호</th>
						<td class="left"><c:out value="${basket.bizrNo}" /></td>
					</tr>
					<tr>
						<th scope="row">우선지원구분</th>
						<td class="left">
										<c:choose>
											<c:when test="${listDt.priSupCd eq 1}">우선지원대상</c:when>
											<c:when test="${listDt.priSupCd eq 2}">대규모기업</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									
						</td>
					</tr>
					<tr>
						<th scope="row">소재지</th>
						<td class="left"><c:out value="${basket.bplAddr}" /></td>
					</tr>
					<tr>
						<th scope="row">상세주소</th>
						<td class="left"><c:out value="${basket.addrDtl}" /></td>
					</tr>

					<tr>
						<th scope="row">업종</th>
						<td class="left"><c:out value="${basket.bplIndustNm}" /></td>
					</tr>
					<tr>
						<th scope="row">업종코드</th>
						<td class="left"><c:out value="${basket.indutyCd}" /></td>
					</tr>
					<tr>
						<fmt:parseDate value="${basket.emplymFormatnDe}" var="dateValue" pattern="yyyyMMdd"/>
						<th scope="row">고용보험 성립 일자</th>
						<td class="left"><fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<th scope="row">총 상시인원</th>
						<td class="left"><c:out value="${basket.totWorkCnt}" /></td>
					</tr>
					<tr>
						<th scope="row">고용상시인원</th>
						<td class="left"><c:out value="${basket.totEmpCnt}" /></td>
					</tr>
					<tr>
						<th scope="row">총 사업장수</th>
						<td class="left"><c:out value="${basket.totBplCnt}" /></td>
					</tr>
					<tr>
						<th scope="row">본사여부</th>
						<td class="left"><c:out
								value="${basket.headBplCd eq '1' ? 'Y' : 'N'}" /></td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>
</div>

<!-- 기업 정보 탭 end -->

<!--  분류 정보 탭 -->
<div class="tabcontent hidden" id="tab2">
	<div class="contents-area">
		<h3 class="title-type02 ml0">기업 분류 및 해시태그</h3>

		<div class="table-type02">
			<table>
				<caption>기업 분류 및 해시태그 정보표</caption>
				<colgroup>
					<col style="width: 20%" />
					<col style="width: 80%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">자동 대분류</th>
						<td class="left"><c:out value="${bskClAtmcs.lclasNm}" /></td>
					</tr>
					<tr>
						<th scope="row">자동 소분류</th>
						<td class="left"><c:forEach
								items="${bskClAtmcs.bskClAtmcSclases}" var="bskClAtmcSclas">
								<c:out value="${bskClAtmcSclas.sclasNm}" />
							</c:forEach></td>
					</tr>
					<tr>
						<th scope="row">수동 대분류</th>
						<td class="left"><strong><c:out
									value="${bskClpassiv[0].lclasNm}" /></strong></td>
					</tr>
					<tr>
						<th scope="row">수동 소분류</th>
						<td class="left"><c:forEach items="${bskClpassiv}" var="item">
								<strong><c:out value="${item.sclasNm}" /></strong>
							</c:forEach></td>
					</tr>
					<tr>
						<th scope="row">해시태그</th>
						<td class="left">
							<div class="hashtag-wrapper">

								<c:forEach items="${hashtags}" var="item">
									<p class="hashtag">
										<a href="#" data-value="${item.hashtagIdx}"> <c:out
												value="${item.hashtagNm}" />
										</a>
										<button type="button" class="btn-delete"
											onclick="bskHashtagDeleteHandler(this)">삭제</button>
									</p>
								</c:forEach>


							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>


	<div class="contents-area">
		<h3 class="title-type02 ml0">기업 분류 이력</h3>

		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>기업 분류 이력 정보표</caption>
				<colgroup>
					<col style="width: 7%" />
					<col style="width: 25%" />
					<col style="width: 10%" />
					<col style="width: 20%" />
					<col style="width: 19%" />
					<col style="width: 19%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">일시</th>
						<th scope="col">구분</th>
						<th scope="col">대분류</th>
						<th scope="col">소분류</th>
						<th scope="col">변경자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bskClLogs}" var="item" varStatus="status">
						<tr>
							<td><c:out value="${status.count}" /></td>
							<td><fmt:formatDate value="${item.regiDate}"
									pattern="yyyy-MM-dd" /></td>
							<td><c:out value="${item.sort}" /></td>
							<td><c:out value="${item.lclasNm}" /></td>
							<td><c:out value="${item.sclasNm}" /></td>
							<td><c:out value="${item.regiName}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- 분류 정보 탭 end -->

<!--  참여 이력 탭 -->


<div class="tabcontent hidden" id="tab3">
	<div class="contents-area">
		<h3 class="title-type02 ml0">기업 훈련 실시 이력</h3>

		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>기업 훈련 실시 이력 정보표 : No, 참여사업, 훈련과정명, 훈련방법, 훈련기간에 관한
					정보표</caption>
				<colgroup>
					<col style="width: 7%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
					<col style="width: 23.25%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">참여사업</th>
						<th scope="col">훈련과정명</th>
						<th scope="col">훈련방법</th>
						<th scope="col">훈련주체</th>
						<th scope="col">회차</th>
						<th scope="col">훈련시작일</th>
						<th scope="col">훈련종료일</th>
						<th scope="col">지원금액</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty trDatas}">
					<tr>
						<td colspan="9" class="bllist"><spring:message
								code="message.no.list" /></td>
					</tr>
				</c:if>
					<c:forEach items="${trDatas}" var="item" varStatus="status">
						<tr>
							<td><c:out value="${status.count}" /></td>
							<td><c:out value="${item.bizrNm}" /></td>
							<td><c:out value="${item.tpNm}" /></td>
							<td><c:out value="${item.trMth}" /></td>
							<td><c:out value="${item.trMby}" /></td>
							<td><c:out value="${item.tpTme}" /></td>
							<td><c:out value="${fn:substring(item.trStartDate,0,10)}" /></td>						
							<td><c:out value="${fn:substring(item.trEndDate,0,10)}" /></td>
							<td><c:out value="${item.totTrCt}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="paging-navigation-wrapper">
			<form action="view.do" name="pageForm3" method="GET">
				<!-- 페이징 네비게이션 -->
				<p class="paging-navigation">
					<c:if test="${pageVO.prev}">
						<a href="#none" class="btn-first" data-pagenum='1'
							onclick="paginationHandler('pageForm3')">맨 처음 페이지로 이동</a>
					</c:if>
					<c:if test="${pageVO.prev}">
						<a href="#" class="btn-preview"
							data-pagenum='${pageVO.startPage - 1}'
							onclick="paginationHandler('pageForm3')">이전 페이지로 이동</a>
					</c:if>
					<c:forEach var="num" begin="${pageVO.startPage}"
						end="${pageVO.endPage}">
						<c:if test="${pageVO.cri.pageNum == num}">
							<strong><c:out value="${num}" /></strong>
						</c:if>
						<c:if test="${pageVO.cri.pageNum != num && num != 0}">
							<a href="#" data-pagenum='${num}'
								onclick="paginationHandler('pageForm3')"><c:out
									value="${num}" /></a>
						</c:if>
					</c:forEach>
					<c:if test="${pageVO.next}">
						<a href="#" class="btn-next" data-pagenum='${pageVO.endPage + 1}'
							onclick="paginationHandler('pageForm3')">다음 페이지로 이동</a>
					</c:if>
					<c:if test="${pageVO.next}">
						<a href="#none" class="btn-last" data-pagenum='${pageVO.realEnd}'
							onclick="paginationHandler('pageForm3')">맨마지막 페이지로 이동</a>
					</c:if>
				</p>
				<!-- //페이징 네비게이션 -->

				<input type="hidden" name="mId" value="42"> 
				<input type="hidden" name="bplNo" value="${basket.bplNo}">
				<input type="hidden" name="pageNum" value="${pageVO.cri.pageNum}">
				<input type="hidden" name="amount" value="${pageVO.cri.amount}">

			</form>

		</div>
	</div>




	<div class="contents-area">
		<h3 class="title-type02 ml0">컨설팅 실시 이력</h3>

		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>컨설팅 실시 이력 정보표 : No, 컨설팅종류, 주요내용, 컨설팅시행자에 관한 정보표</caption>
				<colgroup>
					<col style="width: 7%" />
					<col style="width: 20%" />
					<col style="width: 50%" />
					<col style="width: 23%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">컨설팅종류</th>
						<th scope="col">주요내용</th>
						<th scope="col">컨설팅시행자</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4" class="bllist"><spring:message
								code="message.no.list" /></td>


				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- 참여이력 탭 end -->

<!--  채용 재무 정보 탭 -->
<div class="tabcontent hidden" id="tab4">
	<div class="contents-area">
		<h3 class="title-type02 ml0">기업 채용 현황</h3>

		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>기업 채용 현황 정보표 : No, 모집 직종, 모집 인원, 모집 종료일에 관한 정보표</caption>
				<colgroup>
					<col style="width: 7%" />
					<col style="width: 40%" />
					<col style="width: 10%" />
					<col style="width: 13%" />
					<col style="width: 20%" />
					<col style="width: 10%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">채용 제목</th>
						<th scope="col">임금 형태</th>
						<th scope="col">급여</th>
						<th scope="col">지역</th>
						<th scope="col">근무 형태</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty bskRecList}">
					<tr>
						<td colspan="6" class="bllist"><spring:message
								code="message.no.list" /></td>
					</tr>
				</c:if>
					<c:forEach var="item" items="${bskRecList}" varStatus="i">
						<tr>
							<td><c:out value="${i.count}" /></td>
							<td><c:out value="${item.title}" /></td>
							<td><c:out value="${item.salaryStle}" /></td>
							<td><c:out value="${item.salaryScope}" /></td>
							<td><c:out value="${item.region}" /></td>
							<td><c:out value="${item.workStle}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>




	<div class="contents-area">
		<h3 class="title-type02 ml0">기업 재무 정보</h3>

		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>기업 재무 정보표 : No,매출액, 영업이익에 관한 정보표</caption>
				<colgroup>
					<col style="width: 15%" />
					<col style="width: 35%" />
					<col style="width: 50%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">매출액 (단위 : 천원)</th>
						<th scope="col">영업이익 (단위 : 천원)</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty fncInfo}">
					<tr>
						<td colspan="3" class="bllist"><spring:message
								code="message.no.list" /></td>
					</tr>
				</c:if>
				
					<c:forEach var="fnc" items="${fncInfo}" varStatus="i">
					<tr>
						<td><c:out value="${fn:substring(fnc.STACNT_STDR_DE, 0, 4)}"/></td>
						<td class="right"><fmt:formatNumber value="${fnc.SELNG_AMT}" type="number" maxFractionDigits="3"/></td>
						<td class="right"><fmt:formatNumber value="${fnc.BSN_PROFIT}" type="number" maxFractionDigits="3"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>




<!-- //CMS 끝 -->




<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>

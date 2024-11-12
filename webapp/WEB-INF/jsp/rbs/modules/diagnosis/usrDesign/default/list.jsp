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

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<input type="hidden" class="siteId" name="siteId" value="${siteId }">
	<div class="contents-area02">
		<form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
			<input type="hidden" name="mId" value="54">
			<fieldset>
				<legend class="blind"> HRD기초진단관리 검색양식 </legend>
				<div class="basic-search-wrapper">

					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label> 진단일 </label>
								</dt>
								<dd>
									<div class="input-calendar-wrapper">
										<div class="input-calendar-area">
											<input type="text" id="is_issueDate1" name="is_issueDate1" class="sdate" title="기준 시작일 입력" placeholder="기준 시작일" autocomplete="off" />
										</div>
										<div class="word-unit">~</div>
										<div class="input-calendar-area">
											<input type="text" id="is_issueDate2" name="is_issueDate2" class="edate" title="기준 시작일 입력" placeholder="기준 종료일" autocomplete="off" />
										</div>
									</div>
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="is_bsisStatus"> 기업HRD이음컨설팅 여부 </label>
								</dt>
								<dd>
									<select id="is_bsisStatus" name="is_bsisStatus">
										<option value="">선택</option>
										<option value="Y">완료</option>
										<option value="N">미완료</option>
									</select>
								</dd>
							</dl>
						</div>
					</div>
					
					<c:if test="${usertype eq 20}">
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label>협약기업</label>
									</dt>
									<dd>
										<select id="is_bplNo" name="is_bplNo">
											<option value="">전체</option>
											<c:forEach items="${bizList}" var="biz" >
												<option value="${biz.bplNo}"><c:out value='${biz.bplNm}' /></option>
											</c:forEach>
										</select>
									</dd>
								</dl>
							</div>
						</div>
					</c:if>
				</div>
				<div class="btns-area">
					<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
						<img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" />
						<strong>검색</strong>
					</button>
					<button type="button" class="btn-search02" style="margin-left: 15px;" onclick="initSearhParams()">
						<strong>초기화</strong>
					</button>
				</div>
			</fieldset>
		</form>
	</div>
	<!-- //search -->

	<p class="total">
		총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
	</p>

	<div class="table-type01 horizontal-scroll">
		<%-- 							<form id="${listFormId}" name="${listFormId}" method="post" target="list_target"> --%>
		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption>HRD기초진단관리표 : 번호, 발급번호, 기업명, 사업장관리번호, HRD기초진단, HRD기초진단지 출력, 기업HRD이음컨설팅에 관한 정보 제공표</caption>
			<colgroup>
				<col style="width: 7%" />
				<col style="width: 15%" />
				<col style="width: 12%" />
				<col style="width: 13%" />
				<col style="width: 15%" />
				<col style="width: 12%" />
				<col style="width: 15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col"><itui:objectItemName itemId="issueNo" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="corpName" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="issueDate" itemInfo="${itemInfo}" /></th>
					<th scope="col">HRD기초진단지 출력</th>
					<th scope="col"><itui:objectItemName itemId="bsisStatus" itemInfo="${itemInfo}" /></th>
				</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
					<tr>
						<td colspan="7" class="bllist">
							<spring:message code="message.no.list" />
						</td>
					</tr>
				</c:if>
				<c:if test="${login eq false}">
					<script>
						alert("로그인 후 HRD기초진단 이력 열람이 가능합니다.");
						window.location.href = `${contextPath}/web/login/login.do?mId=3`;
					</script>
				</c:if>

				<c:set var="listIdxName" value="${settingInfo.idx_name}" />
				<c:set var="listColumnName" value="${settingInfo.idx_column}" />
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
					<%-- 				<c:out value="${listDt}" /> --%>
					<c:set var="listKey" value="${listDt[listColumnName]}" />

					<tr>
						<td class="num">${listNo}</td>
						<td><itui:objectView itemId="issueNo" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
						<td><itui:objectView itemId="corpName" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
						<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}" /></td>

						<!-- HRD기초진단 -->
						<td class="date">
							<a href="javascript:void(0);" class="btn-idx" data-idx="${listDt.BSC_IDX}">
								<strong class="point-color01">
									<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.ISSUE_DATE}" />
								</strong>
							</a> 
							<a href="javascript:void(0);" class="btn-linked btn-idx"  data-idx="${listDt.BSC_IDX}">
								<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="HRD기초진단 상세보기">
							</a>
						</td>


						<!-- HRD기초진단 Report -->
						<td>
							<a href="<c:out value="${URL_CLIPREPORT}&bscIdx=${listKey }"/>" target="_blank" title="출력하기">
								<button type="button" class="btn-m02 btn-color02 w100">출력하기</button>
							</a>
						</td>

						<!-- 기업HRD이음컨설팅 -->
						<c:choose>
							<c:when test="${listDt.BSIS_STATUS eq 1}">
								<td>
									<a href="javascript:void(0);" class="btn-cnslt" data-rslt="${listDt.BSIS_RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
										<strong class="point-color01">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSIS_ISSUE_DATE}" />
										</strong>
									</a>
									<a href="javascript:void(0);" class="fn_btn_view btn-linked btn-cnslt" data-rslt="${listDt.BSIS_RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
										<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="기초컨설팅 상세보기">
									</a>
								</td>
							</c:when>
							<c:when test="${listDt.BSIS_STATUS eq 0}">
								<td>
									<span class="point-color02">진행중</span>
								</td>
							</c:when>
							<c:when test="${empty listDt.BSIS_STATUS && listDt.QUSTNR_STATUS eq 1}">
								<td>
									<span class="point-color03">대기중</span>
								</td>
							</c:when>
							<c:when test="${empty listDt.QUSTNR_STATUS and usertype ne 20}">
								<td>
									<button type="button" class="btn-m02 btn-color03 w100 btn-qustnr" data-bsc="${listDt.BSC_IDX}">
										<span>신청하기</span>
									</button>
								</td>
							</c:when>
							<c:when test="${empty listDt.QUSTNR_STATUS and usertype eq 20}">
								<td>
									<span class="point-color03">신청전</span>
								</td>
							</c:when>
							<c:when test="${listDt.QUSTNR_STATUS eq 0 and usertype ne 20}">
								<td>
									<button type="button" class="btn-m02 btn-color01 w100 btn-qustnr" data-rslt="${listDt.RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
										<span>수정하기</span>
									</button>
								</td>
							</c:when>
							<c:when test="${listDt.QUSTNR_STATUS eq 0 and usertype eq 20}">
								<td>
									<span class="point-color02">작성중</span>
								</td>
							</c:when>
							<c:otherwise>
								<td>
									<button type="button" class="btn-m02 btn-color03 w100 btn-qustnr" data-bsc="${listDt.BSC_IDX}">
										<span>신청하기</span>
									</button>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<c:set var="listNo" value="${listNo - 1}" />
				</c:forEach>
			</tbody>
		</table>
		<!-- 							</form> -->
	</div>

	<!-- 페이지 내비게이션 -->
	<div class="paging-navigation-wrapper">
		<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
	</div>
	<!-- //페이지 내비게이션 -->

	<!-- //CMS 끝 -->
</div>
<!-- //contents -->
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="rslt" id="rslt" value="" />
	<input type="hidden" name="bsc" id="bsc" value="" />
	<input type="hidden" name="idx" id="idx" value="" />
</form>

<script src="${contextPath}/web/js/diagnosis/diagnosis.js"></script>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
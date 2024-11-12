<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>

<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/bsisCnsl/list.js"/>"></script>

	<!-- contents  -->
	<div class="contents-wrapper">

		<!-- CMS 시작 -->
		
		<!-- search -->
<%-- 		<itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/> --%>
		<!-- //search -->

		<div class="contents-area02">
			<form action="" method="get" id="" name="">
				<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
				<fieldset>
					<legend class="blind"> 기업HRD이음컨설팅 이력 검색양식 </legend>
					<%-- <itui:searchFormItemIn itemListSearch="${itemListSearch}" searchOptnHashMap="${searchOptnHashMap}" isUseRadio="${isUseRadio}" isUseMoreItem="${isUseMoreItem}"/>
					 --%>
					<div class="basic-search-wrapper">

						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_bsiscnslIssueNo">발급번호</label>
									</dt>
									<dd>
										<input type="text" id="is_bsiscnslIssueNo" name="is_bsiscnslIssueNo" value="" title="발급번호 입력" placeholder="발급번호" />
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_bsiscnslStatus">기업HRD이음컨설팅 진행상태</label>
									</dt>
									<dd>
										<select id="is_bsiscnslStatus" name="is_bsiscnslStatus">
											<option value="">전체</option>
											<option value="1">완료</option>
											<option value="0">진행중</option>
										</select>
									</dd>
								</dl>
							</div>
						</div>

						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label>기초진단일</label>
									</dt>
									<dd>
										<div class="input-calendar-wrapper">
											<div class="input-calendar-area">
												<input type="text" id="is_bscIssueDate1" name="is_bscIssueDate1" class="sdate" title="기준 시작일 입력" placeholder="기준 시작일" autocomplete="off" />
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="is_bscIssueDate2" name="is_bscIssueDate2" class="edate" title="기준 종료일 입력" placeholder="기준 종료일" autocomplete="off" />
											</div>
										</div>
									</dd>
								</dl>
							</div>
							
							<c:if test="${not empty bizList}">
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
							</c:if>
						</div>
					</div>
					<div class="btns-area">
						<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search" >
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


		<p class="total">
			총 <strong><c:out value="${paginationInfo.totalRecordCount}" /></strong> 건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
		</p>

		<div class="table-type01 horizontal-scroll">
			<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
				<caption>기업HRD이음컨설팅 이력표 : 번호, 발급번호, 기업명, 사업장관리번호, 기초진단, 설문조사, 진행현황, 출력에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 7%" />
					<col style="width: 15%" />
					<col style="width: 12%" />
					<col style="width: 13%" />
					<col style="width: 15%" />
					<col style="width: 15%" />
					<col style="width: 10%" />
					<col style="width: 10%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col"><itui:objectItemName itemId="bsiscnslIssueDate" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="corpName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="bscIssueDate" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="qustnrIssueDate" itemInfo="${itemInfo}"/></th>
						<th scope="col">
							<itui:objectItemName itemId="bsiscnslStatus" itemInfo="${itemInfo}"/>
							<a href="#" class="word-linked-notice02" onclick="openModalForDesc();"></a>
						</th>
						<th scope="col">출력</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
						<tr>
							<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
					</c:if>
					
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					<c:set var="bscIdxName" value="${settingInfo.bsc_idx_name}"/>
					<c:set var="bscColumnName" value="${settingInfo.bsc_idx_column}"/>
					<c:set var="qustnrIdxName" value="${settingInfo.qustnr_idx_name}"/>
					<c:set var="qustnrColumnName" value="${settingInfo.qustnr_idx_column}"/>
					<c:set var="rsltIdxName" value="${settingInfo.rslt_idx_name}"/>
					<c:set var="rsltColumnName" value="${settingInfo.rslt_idx_column}"/>
					<c:set var="bsisIdxName" value="${settingInfo.bsis_idx_name}"/>
					<c:set var="bsisColumnName" value="${settingInfo.bsis_idx_column}"/>
					
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					
					<c:if test="${login eq false}">
						<script>
							alert("로그인 후 기업HRD이음컨설팅 이력 열람이 가능합니다.");
							window.location.href = `${contextPath}/web/login/login.do?mId=3`;
						</script>
					</c:if>
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}"/>
						<c:set var="bscKey" value="${listDt[bscColumnName]}"/>
						<c:set var="rsltKey" value="${listDt[rsltColumnName]}"/>
						<c:set var="bsisKey" value="${listDt[bsisColumnName]}"/>
						
						<tr>
							<td class="num">${listNo}</td>
							<td>
								<c:choose>
									<c:when test="${not empty listDt.BSISCNSL_ISSUE_NO && listDt.BSISCNSL_STATUS eq 1}">
										<a class="btn-cnslt" href="javascript:void(0);" data-rslt="${rsltKey}" data-bsc="${bscKey}">
											<strong class="point-color01">
												<itui:objectView itemId="bsiscnslIssueNo" itemInfo="${itemInfo}" objDt="${listDt}"/>
											</strong>
										</a>
										<a class="btn-linked btn-cnslt" href="javascript:void(0);" data-rslt="${rsltKey}" data-bsc="${bscKey}">
											<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="기초컨설팅 상세보기" />
										</a>
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</td>
							<td><itui:objectView itemId="corpName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							
							<!-- 기초진단 -->
							<td>
								<c:if test="${not empty listDt.BSC_ISSUE_DATE}">
									<a href="javascript:void(0);" class="btn-bsc" data-idx="${bscKey}">
										<strong class="point-color01">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSC_ISSUE_DATE}" />
										</strong>
									</a>
									<a class="btn-linked btn-bsc" href="javascript:void(0);" data-idx="${bscKey}">
										<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="기초진단 상세보기" />
									</a>
								</c:if>
							</td>
							
							<!-- 설문조사 -->
							<td>
								<c:choose>
									<c:when test="${not empty listDt.QUSTNR_ISSUE_DATE}">
										<a href="javascript:void(0);" class="fn_btn_view btn-qustnr" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											<strong class="point-color01">
												<fmt:formatDate pattern="20YY-MM-dd" value="${listDt.QUSTNR_ISSUE_DATE}" />
											</strong>
										</a>
										<a href="javascript:void(0);" class="fn_btn_view btn-linked btn-qustnr" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="설문조사 상세보기" />
										</a>
									</c:when>
									<c:when test="${listDt.QUSTNR_STATUS eq 0}">
										<c:if test="${empty bizList}">
											<button type="button" class="btn-m02 btn-color03 w100 btn-qustnr" data-rslt="${rsltKey}" data-bsc="${bscKey}">
												<span>수정하기</span>
											</button>
										</c:if>
										<c:if test="${not empty bizList}">
											<span class="point-color03">작성중</span>
										</c:if>
									</c:when>
								</c:choose>
							</td>
							
							<!-- 진행상태 -->
							<td>
								<c:choose>
									<c:when test="${listDt.BSISCNSL_STATUS eq 1 && not empty listDt.BSISCNSL_ISSUE_NO}">
										<span class="point-color01">완료</span>
									</c:when>
									<c:when test="${listDt.BSISCNSL_STATUS eq 0}">
										<span class="point-color02">진행중</span>
									</c:when>
									<c:when test="${listDt.QUSTNR_STATUS eq 1 && empty listDt.BSISCNSL_ISSUE_NO}">
										<span class="point-color03">대기중</span>
									</c:when>
									<c:otherwise>
										<span class="point-color04">신청필요</span>
									</c:otherwise>
								</c:choose>
							</td>
							
							<!-- 출력 -->
							<td>
								<c:if test="${listDt.BSISCNSL_STATUS eq 1 && not empty listDt.BSISCNSL_ISSUE_NO}">
									<a href="<c:out value="${URL_CONSULTINGCLIPREPORT}&bsiscnslIdx=${listDt.BSISCNSL_IDX}"/>" target="_blank">
										<button type="button" class="btn-m02 btn-color02 w100">
											<span>출력하기</span>
										</button>
									</a>
								</c:if>
							</td>
						</tr>
						
						<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>

				</tbody>
			</table>
		</div>

		<div class="paging-navigation-wrapper">
			<!-- 페이징 네비게이션 -->
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			
			<!-- <p class="paging-navigation">
				<a href="#none" class="btn-first">맨 처음 페이지로 이동</a> <a
					href="#none" class="btn-preview">이전 페이지로 이동</a> <strong>1</strong>
				<a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a
					href="#">5</a> <a href="#none" class="btn-next">다음 페이지로
					이동</a> <a href="#none" class="btn-last">맨 마지막 페이지로 이동</a>
			</p> -->

			<!-- //페이징 네비게이션 -->
		</div>


		<!-- //CMS 끝 -->
	</div>
<!-- //contents  -->
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="rslt" id="rslt" value="" />
	<input type="hidden" name="bsc" id="bsc" value="" />
	<input type="hidden" name="idx" id="idx" value="" />
</form>

<!-- 모달 창 -->
	<div class="mask"></div>
	<div class="modal-wrapper" id="modal-action01">
		<h2>기업HRD이음컨설팅 진행상태 설명</h2>
		<div class="modal-area">
			<div class="contents-box pl0" style="margin-bottom:0px;">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type03">
						<caption>기업HRD이음컨설팅 진행상태 설명표 : 기업HRD이음컨설팅 진행상태에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 30%" />
							<col style="width: 70%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">진행 상태</th>
								<th scope="col">설명</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<span class="point-color04">신청필요</span>
								</td>
								<td class="left">
									<span>기업HRD이음컨설팅 신청 전 상태입니다.<br/>설문조사를 완료하셔야 합니다.</span>
								</td>
							</tr>
							<tr>
								<td>
									<span class="point-color03">대기중</span>
								</td>
								<td class="left">
									<span>기업HRD이음컨설팅을 신청 후 발급 대기 상태입니다.<br/>주치의 확인 전 상태입니다.</span>
								</td>
							</tr>
							<tr>
								<td>
									<span class="point-color02">진행중</span>
								</td>
								<td class="left">
									<span>기업HRD이음컨설팅 신청이 접수되어<br/>현재 주치의 확인 중입니다.</span>
								</td>
							</tr>
							<tr>
								<td>
									<span class="point-color03">완료</span>
								</td>
								<td class="left">
									<span>기업HRD이음컨설팅이 완료되었습니다.<br/>발급된 기업HRD이음컨설팅을 확인하실 수 있습니다.</span>
								</td>
							</tr>
						</tbody>
                      </table>
                  </div>
			</div>
			<button type="button" class="btn-modal-close">모달 창 닫기</button>
		</div>
	</div>
<!-- //모달 창 -->
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
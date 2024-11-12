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
		<%-- <itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/> --%>
		<!-- //search -->

		<div class="contents-area02"> 
			<form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
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
										<label for="is_corpName">기업명 </label>
									</dt>
									<dd>
										<input type="text" id="is_corpName" name="is_corpName" value="" title="기업명 입력" placeholder="기업명" />
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_bplNo"> 사업장 관리번호 </label>
									</dt>
									<dd>
										<input type="text" id="is_bplNo" name="is_bplNo" value="" title="사업장 관리번호 입력" placeholder="사업장 관리번호" />
									</dd>
								</dl>
							</div>
						</div>

						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label> 기초진단일 </label>
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
							
							<c:if test="${usertype >= 40}">
								<div class="half-box">
									<dl>
										<dt>
											<label>소속기관</label>
										</dt>
										<dd>
											<select id="is_insttIdx" name="is_insttIdx">
												<option value="">전체</option>
												<c:forEach items="${insttList}" var="instt">
													<option value="<c:out value='${instt.INSTT_IDX}' />"><c:out value='${instt.INSTT_NAME}'/></option>
												</c:forEach>
												<option value="empty">미정</option>
											</select>
										</dd>
									</dl>
								</div>
							</c:if>
						</div>
					</div>
					<div class="btns-area">
						<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search" >
							<img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="검색" />
								<strong>검색</strong>
						</button>
						<button type="button" class="btn-search02" id="btn-init" style="margin-left: 15px;">
							<strong>초기화</strong>
						</button>	
					</div>
				</fieldset>
			</form>
		</div>


		<div class="title-wrapper">
			<p class="total fl">
				총 <strong><c:out value="${paginationInfo.totalRecordCount}" /></strong> 건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
			</p>
			<div class="fr">
				<c:if test="${paginationInfo.totalRecordCount > 0}">
					<a href="javascript:void(0);" class="btn-m01 btn-color03" id="btn-excel">엑셀다운로드</a>
				</c:if>
<%-- 				<c:if test="${usertype >= 40}"> --%>
<!-- 					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="downloadExcelForAdmin()">관리대장 다운로드</a> -->
<%-- 				</c:if> --%>
			</div>
		</div>

		<div class="table-type01 horizontal-scroll">
			<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col style="width: 6%" />
					<col style="width: 8%" />
					<col style="width: 8%" />
					<col style="width: 14%" />
					<col style="width: 14%" />
					<col style="width: 8%" />
					<col style="width: 14%" />
					<col style="width: 14%" />
					<col style="width: 14%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">소속기관</th>
						<th scope="col">공단 담당자</th>
						<th scope="col"><itui:objectItemName itemId="corpName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="corpPicName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="bscIssueDate" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="qustnrIssueDate" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="bsiscnslIssueDate" itemInfo="${itemInfo}"/></th>
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
					<c:set var="bsisrColumnName" value="${settingInfo.bsis_idx_column}"/>
					
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}"/>
						<c:set var="bscKey" value="${listDt[bscColumnName]}"/>
						<c:set var="rsltKey" value="${listDt[rsltColumnName]}"/>
						<c:set var="bsisKey" value="${listDt[bsisColumnName]}"/>
						
						<tr>
							<%-- <td class="num">${listDt}</td> --%>
							<td class="num">${listNo}</td>
							<td>
								<c:if test="${not empty listDt.BSISCNSL_INSTT_NAME}">${listDt.BSISCNSL_INSTT_NAME}</c:if>
								<c:if test="${empty listDt.BSISCNSL_INSTT_NAME}">${listDt.INSTT_NAME}</c:if>
							</td>
							<td>
								<c:choose>
									<c:when test="${not empty listDt.BSISCNSL_DOCTOR_NAME}"><c:out value='${listDt.BSISCNSL_DOCTOR_NAME}' /></c:when>
									<c:otherwise><c:out value='${listDt.DOCTOR_NAME}' /></c:otherwise>
								</c:choose>
							</td>
							<td class="subject"><itui:objectView itemId="corpName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td><itui:objectView itemId="corpPicName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<!-- 기초진단지 -->
							<c:choose>
								<c:when test="${empty listDt.BSC_ISSUE_DATE}">
									<td></td>
								</c:when>
								<c:otherwise>
									<td>
										<a href="javascript:void(0);" class="btn-bsc" data-idx="${bscKey}">
											<strong class="point-color01">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSC_ISSUE_DATE}"/>
											</strong>
										</a>
										<a href="javascript:void(0);" class="fn_btn_view btn-linked btn-bsc" data-idx="${bscKey}">
											<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="기초진단 상세보기" />
										</a>
									</td>
								</c:otherwise>
							</c:choose>
							<!-- 설문조사 -->
							<c:choose>
								<c:when test="${empty listDt.QUSTNR_STATUS}">
									<td></td>
								</c:when>
								<c:when test="${listDt.QUSTNR_STATUS == 0}">
									<td>
										<button type="button" class="fn_btn_view btn-m02 btn-color03 w100 btn-qustnr" data-rslt="${rsltKey}" data-bsc="${bscKey}">
											수정하기
										</button>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<a href="javascript:void(0);" class="fn_btn_view btn-qustnr" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											<strong class="point-color01">
												<fmt:formatDate pattern="20YY-MM-dd" value="${listDt.QUSTNR_ISSUE_DATE}"/>
											</strong>
										</a>
										<a href="javascript:void(0);" class="fn_btn_view btn-linked btn-qustnr" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="설문조사 상세보기" />
										</a>
									</td>
								</c:otherwise>
							</c:choose>
							
							<!-- 기업HRD이음컨설팅 -->
							<c:choose>
								<c:when test="${empty listDt.BSISCNSL_STATUS && listDt.QUSTNR_STATUS eq 0}">
									<td>
										<span>설문필요</span>
									</td>
								</c:when>
								<c:when test="${empty listDt.BSISCNSL_STATUS}">
									<td>
										<button type="button" class="fn_btn_view btn-m02 btn-color02 w100 btn-cnslt" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											<span>작성하기</span>
										</button>										
									</td>
								</c:when>
								<c:when test="${listDt.BSISCNSL_STATUS == 0}">
									<td>
										<button type="button" class="fn_btn_view btn-m02 btn-color03 w100 btn-cnslt" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											수정하기
										</button>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<a href="javascript:void(0);" class="btn-cnslt" data-rslt="${rsltKey}" data-bsc="${bscKey}" >
											<strong class="point-color01">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSISCNSL_ISSUE_DATE}"/>
											</strong>
										</a>
										<a href="javascript:void(0);" class="fn_btn_view btn-linked btn-cnslt" data-rslt="${rsltKey}" data-bsc="${bscKey}">
											<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="기초컨설팅 상세보기" />
										</a>
									</td>
								</c:otherwise>
							</c:choose>
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
		
		<form action="" method="post" style="display: none;" id="form-box">
			<input type="hidden" name="rslt" id="rslt" value="" />
			<input type="hidden" name="bsc" id="bsc" value="" />
			<input type="hidden" name="idx" id="idx" value="" />
		</form>
	</div>
<!-- //contents  -->
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
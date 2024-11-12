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
<%-- <script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/bsisCnsl/list.js"/>"></script> --%>

	<!-- contents  -->
	<div class="contents-wrapper">

		<!-- CMS 시작 -->
		
		<!-- search -->
<%-- 		<itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/> --%>
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
						
					</div>
					<div class="btns-area">
						<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search" >
							<img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" />
								<strong>검색</strong>
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
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
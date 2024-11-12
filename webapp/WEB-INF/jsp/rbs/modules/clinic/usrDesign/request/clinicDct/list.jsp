<%@ include file="../../../../../include/commonTop.jsp"%>
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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/request/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<%@ include file="../../../clinicDct/request/listBtnsScript.jsp"%>
<div class="contents-wrapper">
	<!-- search -->
	<!-- 본부 직원(최고관리자 포함)과 지부지사 직원일 때 검색항목이 다름 -->
    <c:set var="searchList" value=""/>
	<c:choose>
		<c:when test="${loginVO.usertypeIdx eq '30'}">
			<itui:searchFormItemForDct contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${REQUEST_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
			<!-- 검색조건이 있을 때 -->
            <c:if test="${!empty param.key || !empty param.is_confmStatus}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}"/></c:if>
		</c:when>
		<c:otherwise>
			<itui:searchFormItemForDct2 contextPath="${contextPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${REQUEST_LIST_FORM_URL}" itemListSearch="${itemInfo.list_search_head}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btn-search02 btn-color03" imgPath="${imgPath}"/>
			<!-- 검색조건이 있을 때 -->
            <c:if test="${!empty param.key || !empty param.is_confmStatus || !empty param.is_cliInsttIdx}"><c:set var="searchList" value="&keyField=${param.keyField}&key=${param.key}&is_confmStatus=${param.is_confmStatus}&is_cliInsttIdx=${param.is_cliInsttIdx}"/></c:if>
		</c:otherwise>
	</c:choose>
	<!-- //search -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 신청 목록</h3>
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<c:if test="${loginVO.usertypeIdx ne '40'}">
	                <a href="#" class="btn-m01 btn-color01 depth3 open-modal02 fn_doctorAll" id="open-modal01" >주치의 지정</a>
	                <a href="#" class="btn-m01 btn-color01 depth3 fn_acceptAll">접수</a>
            	</c:if>	
            	<a href="${REQUEST_EXCEL_URL}${searchList}" class="btn-m01 btn-color01 depth3">엑셀다운로드</a>
	           </div>		
		</div>
		<div class="table-type01 horizontal-scroll">
		<table class="width-type02">
			<caption>능력개발클리닉 신청 목록표 : No, 기업명, 주치의 변경, 소속기관, 신청 상태, 신청일자에 관한 정보 제공표</caption>
			<colgroup>
				<col style="width:6%">
				<col style="width:6%">
				<col style="width:25%">
				<col style="width:auto">
				<col style="width:auto">
				<col style="width:auto">
				<col style="width:auto">
				<col style="width:auto">
				<col style="width:auto">
			</colgroup>
			<thead>
			<tr>		
				<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" class="checkbox-type01" title="<spring:message code="item.select.all"/>"/></th>
				<th scope="col">No</th>
				<th scope="col">기업명</th>
				<th scope="col">주치의 변경</th>
				<th scope="col">소속기관</th>
				<th scope="col">상태</th>
				<th scope="col">등록일</th>
				<th scope="col">처리일</th>
				<th scope="col">처리자</th>
			</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
				<tr>
					<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<c:set var="listKey" value="${listDt[listColumnName]}"/>
				<tr>
					<td><c:if test="${listDt.CONFM_STATUS eq '10'}"><input type="checkbox" name="select" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listKey}" id="reqCheck${listNo}" class="checkbox-type01" data-cli="${listDt.CLI_IDX}" data-doc="${listDt.DOCTOR_IDX}" data-status="${listDt.CONFM_STATUS}" data-instt="${listDt.CLI_INSTT_IDX}"/></c:if></td>
					<td class="num">${listNo}</td>
					<td><strong class="point-color01"><a href="<c:out value="${REQUEST_VIEW_FORM_URL}"/>&reqIdx=${listDt.REQ_IDX}&bplNo=${listDt.BPL_NO}" id="bplNm"><c:out value="${listDt.BPL_NM}"></c:out></a></strong></td>
					<td>
						<c:if test="${loginVO.usertypeIdx ne '40' && !empty listDt.DOCTOR_IDX}">
							<a href="#" class="fn_accept btn-m02 btn-color03" id="accept${i.count}" data-cli="${listDt.CLI_IDX}" data-req="${listDt.REQ_IDX}" data-doc="${listDt.DOCTOR_IDX}" data-instt="${listDt.CLI_INSTT_IDX}">변경</a>
						</c:if>
					</td>
					<td><c:out value="${listDt.INSTT_NAME}"></c:out></td>
					<td>${confirmProgress[listDt.CONFM_STATUS]}</td>		
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
					<!-- 주치의가 처리했을 때 처리일자, 처리자 정보 보여주기(신청, 반려요청, 중도포기요청은 기업이 하는거라 제외) -->
					<c:choose>
						<c:when test="${listDt.CONFM_STATUS ne '10' && listDt.CONFM_STATUS ne '35' && listDt.CONFM_STATUS ne '75'}">
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.LAST_MODI_DATE}"/></td>
							<td>${listDt.LAST_MODI_NAME}</td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${REQUEST_LIST_FORM_URL}${searchList}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
		<!-- //페이지 내비게이션 -->
	</div>
</div>
	
	<!-- 주치의 일괄 지정 모달창 -->
	<div class="mask zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-action01">
		<h2>주치의 지정</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="table-type01 horizontal-scroll">
					<table class="width-type02">
					<caption>주치의 지정을 위한 주치의 정보 제공표 : No, 지부지사, 부서, 직책, 이름, 최종배정에 관한 정보 제공표</caption>
					<colgroup> 
						<col style="width:8%">
						<col style="width:auto">
						<col style="width:auto">
						<col style="width:15%">
						<col style="width:15%">
						<col style="width:15%">
					</colgroup>
					<thead>
					<tr>										
						<th scope="col">No</th>
						<th scope="col">지부지사</th>
						<th scope="col">부서</th>
						<th scope="col">직책</th>
						<th scope="col">이름</th>
						<th scope="col">최종배정</th>
					</tr>
					</thead>
					<tbody class="alignC">
						<c:if test="${empty doctorList}">
						<tr>
							<td colspan="6" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:forEach var="doctorListDt" items="${doctorList}" varStatus="i">
						<c:set var="insttIdx" value="${doctorListDt.INSTT_IDX}"/>																																
						<tr id="docInfo${i.count}" data-instt="${insttIdx}">
							<td class="num">${i.count}</td>
							<td><c:out value="${doctorListDt.INSTT_NAME}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_DEPT_NAME}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_OFCPS}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_NAME}"></c:out></td>
							<td><input type="radio" name="selectDoc" title="<spring:message code="item.select"/><c:out value="${i.count}"/>" value="${doctorListDt.DOCTOR_IDX}" id="doctorCheckAppoint${i.count}" /></td>
						</tr>	
						</c:forEach>							
					</tbody>
					</table>
					<div class="btns-area mt20">
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-submit">주치의 지정</button>
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-cancel">취소</button>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close modal-close">모달 창 닫기</button>
	</div>
	<!-- 주치의 일괄 지정 모달창 끝 -->	
	
	<!-- 주치의 변경 모달창 -->
	<div class="mask2 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-action02">
		<h2>주치의 변경</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="table-type01 horizontal-scroll">
					<table class="width-type02">
					<caption>주치의 변경을 위한 주치의 정보 제공표 : No, 지부지사, 부서, 직책, 이름, 최종배정에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width:8%">
						<col style="width:auto">
						<col style="width:auto">
						<col style="width:15%">
						<col style="width:15%">
						<col style="width:15%">
					</colgroup>
					<thead>
					<tr>										
						<th scope="col">No</th>
						<th scope="col">지부지사</th>
						<th scope="col">부서</th>
						<th scope="col">직책</th>
						<th scope="col">이름</th>
						<th scope="col">최종배정</th>
					</tr>
					</thead>
					<tbody class="alignC">
						<c:if test="${empty doctorList}">
						<tr>
							<td colspan="6" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:forEach var="doctorListDt" items="${doctorList}" varStatus="i">
						<c:set var="insttIdx" value="${doctorListDt.INSTT_IDX}"/>																																
						<tr id="docInfo${i.count}" data-instt="${insttIdx}">
							<td class="num">${i.count}</td>
							<td><c:out value="${doctorListDt.INSTT_NAME}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_DEPT_NAME}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_OFCPS}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_NAME}"></c:out></td>
							<td><input type="radio" name="selectDoc" title="<spring:message code="item.select"/><c:out value="${i.count}"/>" value="${doctorListDt.DOCTOR_IDX}" id="doctorCheckChange${i.count}" /></td>
						</tr>	
						</c:forEach>							
					</tbody>
					</table>
					<div class="btns-area mt20">
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-submit2">주치의 지정</button>
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-cancel2">취소</button>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close modal-close2">모달 창 닫기</button>
	</div>
	<!-- 주치의 변경 모달창 끝 -->	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
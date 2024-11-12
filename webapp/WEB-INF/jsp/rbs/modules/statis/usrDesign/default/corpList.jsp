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
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article" class="contents-wrapper">
		<!-- search -->
		<itui:searchFormItemForStatis contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${CORP_LIST_URL}" itemListSearch="${itemInfo.corplist_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${CORP_LIST_URL}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
			<div class="title-wrapper">
				<div class="fr">
				<%-- <a href="${CORP_EXCEL_URL}&is_year=${param.is_year }&is_month=${month }&is_instt=${param.is_instt }&is_center=${param.is_center }&is_type=${param.is_type }&is_corp=${param.is_corp }&is_bplNo=${param.is_bplNo }" class="btn-m01 btn-color01">엑셀다운로드</a> --%>
				<a href="${CORP_EXCEL2_URL}&is_year=${param.is_year }&is_month=${month }&is_instt=${param.is_instt }&is_center=${param.is_center }&is_type=${param.is_type }&is_corp=${param.is_corp }&is_bplNo=${param.is_bplNo }" class="btn-m01 btn-color01">엑셀다운로드</a>
				</div>
			</div>
			<div class="table-type02 horizontal-scroll-pc width-auto">
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption> 기업별 훈련참여정보 정보표 목록</caption>
					<colgroup>
						<col style="width: 80px">
						<col style="width: 130px">
						<col style="width: 180px">
						<col style="width: 120px">
						<col style="width: 200px">
						<col style="width: 180px">
						<col style="width: 180px">
						<col style="width: 120px">
						<col style="width: 200px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
						<col style="width: 100px">
					</colgroup>
					<thead>
					<tr>
						<th scope="col" rowspan="5">연번</th>
						<th scope="col" rowspan="5">소속기관명</th>
						<th scope="col" rowspan="5">민간센터명</th>
						<th scope="col" rowspan="5">기업유형</th>
						<th scope="col" rowspan="5">기업명</th>
						<th scope="col" rowspan="5">사업장관리번호</th>
						<th scope="col" rowspan="5">HRD기초진단지</th>
						<th scope="col" rowspan="5">기업 교육훈련 수요 설문지</th>
						<th scope="col" rowspan="5">기업HRD이음컨설팅보고서</th>
						<th scope="col" colspan="7">과정개발</th>
						<th scope="col" colspan="10">당해년도 훈련실시 현황</th>
						<th scope="col" colspan="2">훈련성과분석</th>
						<th scope="col" colspan="3">심화단계 컨설팅</th>
					</tr>
					<tr>
						<th scope="col" colspan="4">AI추천</th>
						<th scope="col" colspan="3">맞춤형 개발</th>
						<th scope="col" rowspan="4">참여여부</th>
						<th scope="col" rowspan="2" colspan="2">훈련 분류</th>
						<th scope="col" rowspan="2" colspan="4">자체훈련 분류</th>
						<th scope="col" rowspan="2" colspan="3">위탁훈련 분류</th>
						<th scope="col" rowspan="4">성취도 만족도</th>
						<th scope="col" rowspan="4">현업적용</th>
						<th scope="col" rowspan="4">심층진단</th>
						<th scope="col" rowspan="4">교육 훈련체계</th>
						<th scope="col" rowspan="4">현장활용</th>
					</tr>
					<tr>
						<th scope="col" colspan="2">표준개발</th>
						<th scope="col" colspan="2">자체개발</th>
						<th scope="col" rowspan="3">사업주 자체</th>
						<th scope="col" rowspan="3">S-OJT 직무전수</th>
						<th scope="col" rowspan="3">S-OJT 현장개선</th>
					</tr>
					<tr>
						<th scope="col" rowspan="2">사업주 자체</th>
						<th scope="col" rowspan="2">S-OJT 직무전수</th>
						<th scope="col" rowspan="2">사업주(자체)</th>
						<th scope="col" rowspan="2">S-OJT 직무전수</th>
						<th scope="col" rowspan="2">자체훈련</th>
						<th scope="col" rowspan="2">위탁훈련</th>
						<th scope="col" rowspan="2">사업주(자체)</th>
						<th scope="col" rowspan="2">S-OJT 직무전수</th>
						<th scope="col" rowspan="2">S-OJT 현장개선</th>
						<th scope="col" rowspan="2">일학습병행</th>
						<th scope="col" rowspan="2">사업주</th>
						<th scope="col" rowspan="2">컨소시엄</th>
						<th scope="col" rowspan="2">지산맞</th>
					</tr>
					<tr>
						
					</tr>
					</thead>
					<tbody>
<%-- 						<c:if test="${empty list}"> --%>
<!-- 						<tr> -->
<%-- 							<td colspan="31" class="bllist"><spring:message code="message.no.list"/></td> --%>
<!-- 						</tr> -->
<%-- 						</c:if> --%>
						<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
						<c:forEach var="listDt" items="${list}" varStatus="i">
                        <tr>
                        	<td>${listNo}</td>
                            <td>${listDt.INSTT_NM }</td>
                            <td>${listDt.PRVTCNTR_NM}</td>
                            <td>${listDt.CORP_TYPE }</td>
                            <td>${listDt.CORP_NM }</td>
                            <td>${listDt.BPL_NO }</td>
                            <td>${listDt.DGNS }</td>
                            <td>${listDt.QESTNR }</td>
                            <td>${listDt.BSISBNSL }</td>
                            <td>${listDt.AI_STD_BPR }</td>
                            <td>${listDt.AI_STD_SOJT }</td>
                            <td>${listDt.AI_SLF_BPR }</td>
                            <td>${listDt.AI_SLF_SOJT }</td>
                            <td>${listDt.FIT_BPR }</td>
                            <td>${listDt.FIT_SOJT }</td>
                            <td>${listDt.FIT_SOJT_SITE }</td>
                            <td>${listDt.PARTC_YN }</td>
                            <td>${listDt.SLF_TR }</td>
                            <td>${listDt.CNSGN_TR }</td>
                            <td>${listDt.SLF_TR_BPR }</td>
                            <td>${listDt.SLF_TR_SOJT }</td>
                            <td>${listDt.SLF_TR_SOJT_SITE }</td>
                            <td>${listDt.SLF_TR_WLB }</td>
                            <td>${listDt.CNSGN_TR_BRP }</td>
                            <td>${listDt.CNSGN_TR_CNSRTM }</td>
                            <td>${listDt.CNSGN_TR_RIB }</td>
                            <td>${listDt.RSLT_SNST }</td>
                            <td>${listDt.RSLT_APPLC }</td>
                            <td>${listDt.DEP_DGNS }</td>
                            <td>${listDt.EDU_TR_SYS }</td>
                            <td>${listDt.SITE_PRC }</td>
                            
                        </tr>
                        <c:set var="listNo" value="${listNo - 1}"/>
                        </c:forEach>
                    </tbody>
				</table>
			</div>
			<!-- 페이지 내비게이션 -->
			<%-- <div class="page">
				<pgui:pagination listUrl="${URL_PAGE_LIST}&is_date1=${OPERT_START_DT}&is_date2=${OPERT_END_DT}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div> --%>
			<!-- //페이지 내비게이션 -->
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
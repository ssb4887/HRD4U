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
	<div id="cms_board_article" class="usbConBox contents-wrapper" >
		<!-- search -->
		<itui:searchFormItemForDct4 contextPath="${contextPath}" imgPath="${imgPath}" divClass="contents-area02" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="contents-area">
			
			<div class="table-type02 horizontal-scroll-pc width-auto">
				<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<caption>총괄 통계 정보표</caption>
					<colgroup>
						<col style="width: 120px">
						<col>
						<col style="width: 150px">
						<col style="width: 200px">
						<col style="width: 200px">
						<col style="width: 130px">
						<col style="width: 200px">
						<col style="width: 180px">
						<col>
						<col>
						<col>
						<col>
						<col>
						<col>
						<col>
						<col style="width: 80px">
						<col style="width: 80px">
						<col>
						<col style="width: 100px">
						<col>
					</colgroup>
					<thead>
					<tr>
						<th scope="col" rowspan="5">기관</th>
						<th scope="col" rowspan="5">구분</th>
						<th scope="col" colspan="3">보고서 발급 현황(총계)</th>
						<th scope="col" colspan="15">컨설팅 현황(우선지원대상기업 기준)</th>
					</tr>
					<tr>
						<th scope="col" rowspan="4">HRD 기초진단지</th>
						<th scope="col" rowspan="4">기업 교육훈련 수요 설문지</th>
						<th scope="col" rowspan="4">기업 HRD 이음컨설팅 보고서</th>
						<th scope="col" rowspan="4">HRD 기초진단</th>
						<th scope="col" rowspan="4">기업 교육훈련 수요 설문</th>
						<th scope="col" rowspan="4">기업HRD 이음컨설팅</th>
						<th scope="col" colspan="7">과정개발</th>
						<th scope="col" colspan="2">훈련성과분석</th>
						<th scope="col" colspan="3">심화단계 컨설팅</th>
					</tr>
					<tr>
						<th scope="col" colspan="4">AI추천</th>
						<th scope="col" colspan="3">맞춤형 개발</th>
						<th scope="col" rowspan="3">성취도 만족도</th>
						<th scope="col" rowspan="3">현업 적용</th>
						<th scope="col" rowspan="3">심층진단</th>
						<th scope="col" rowspan="3">교육훈련체계</th>
						<th scope="col" rowspan="3">현장활용</th>
					</tr>
					<tr>
						<th scope="col" colspan="2">표준개발</th>
						<th scope="col" colspan="2">자체개발</th>
						<th scope="col" rowspan="2">사업주자체</th>
						<th scope="col" rowspan="2">S-OJT 직무전수</th>
						<th scope="col" rowspan="2">S-OJT 현장개선</th>
					</tr>
					<tr>
						<th scope="col">사업주자체</th>
						<th scope="col">S-OJT 직무전수</th>
						<th scope="col">사업주자체</th>
						<th scope="col">S-OJT 직무전수</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="listDt" items="${list}" varStatus="i">
                        <tr>
                        	<c:if test="${i.index == 0 }">
                            <td scope="col" rowspan="2"><div style="width: 100px">${listDt.INSTT_NM }</div></td>
                            <td><div style="width: 60px">${listDt.GUBUN }</div></td>
                            <td><div style="width: 180px">-</div></td>
                            <td><div style="width: 100px">-</div></td>
                            <td><div style="width: 160px">-</div></td>
                            <td><div style="width: 180px"><fmt:formatNumber value="${listDt.DGNS}" /></div></td>
                            <td><div style="width: 100px"><fmt:formatNumber value="${listDt.QESTNR}" /></div></td>
                            <td><div style="width: 160px"><fmt:formatNumber value="${listDt.BSISBNSL}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_STD_BPR}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_STD_SOJT}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_SLF_BPR}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_SLF_SOJT}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.FIT_BPR}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.FIT_SOJT}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.FIT_SOJT_SITE}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.RSLT_SNST}" /></div></td>
                            <td><div style="width: 100px"><fmt:formatNumber value="${listDt.RSLT_APPLC}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.DEP_DGNS}" /></div></td>
                            <td><div style="width: 100px"><fmt:formatNumber value="${listDt.EDU_TR_SYS}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.SITE_PRC}" /></div></td>
                            </c:if>
                            <c:if test="${i.index == 1 }">
                            <td><div style="width: 60px">${listDt.GUBUN }</div></td>
                            <td><div style="width: 180px"><fmt:formatNumber value="${listDt.DGNS}" /></div></td>
                            <td><div style="width: 100px"><fmt:formatNumber value="${listDt.QESTNR}" /></div></td>
                            <td><div style="width: 160px"><fmt:formatNumber value="${listDt.BSISBNSL}" /></div></td>
                            <td><div style="width: 180px">-</div></td>
                            <td><div style="width: 100px">-</div></td>
                            <td><div style="width: 160px">-</div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_STD_BPR}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_STD_SOJT}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_SLF_BPR}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.AI_SLF_SOJT}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.FIT_BPR}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.FIT_SOJT}" /></div></td>
                            <td><div style="width: 80px"><fmt:formatNumber value="${listDt.FIT_SOJT_SITE}" /></div></td>
                            <td><div style="width: 80px">-</div></td>
                            <td><div style="width: 100px"><fmt:formatNumber value="${listDt.RSLT_APPLC}" /></div></td>
                            <td><div style="width: 80px">-</div></td>
                            <td><div style="width: 100px">-</div></td>
                            <td><div style="width: 80px">-</div></td>
                            </c:if>
                        </tr>
                        </c:forEach>
                    </tbody>
				</table>
			</div>
			<!-- 페이지 내비게이션 -->
			<div class="page">
				<pgui:pagination listUrl="${URL_PAGE_LIST}&is_date1=${OPERT_START_DT}&is_date2=${OPERT_END_DT}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			</div>
			<!-- //페이지 내비게이션 -->
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
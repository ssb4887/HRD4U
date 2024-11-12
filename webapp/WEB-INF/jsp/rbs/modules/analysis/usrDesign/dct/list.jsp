<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
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

<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/analysis/apply.js"/>"></script>
<div class="contents-area2">
	<form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
	<input type="hidden" name="mId" value="<c:out value="${mId}" />" />
	<fieldset>
		<legend class="blind"> 기업훈련 성과리포트 검색양식 </legend>
			<div class="basic-search-wrapper">
				<c:if test="${loginVO.usertypeIdx eq '40'}">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isInsttIdx"> 소속기관 </label>
							</dt>
							<dd>
								<select id="isInsttIdx" name="insttIdx" title="소속기관">
									<option value="">소속기관 선택</option>
									<c:forEach var="instt" items="${insttList}">
										<option value="${instt.INSTT_IDX}"
											<c:if test="${instt.INSTT_IDX eq param.insttIdx}">selected</c:if>><c:out
												value="${instt.INSTT_NAME}" /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
				</div>
				</c:if>
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="id_schBplNo"> 사업장관리번호 </label>
							</dt>
							<dd>
								<input type="text" id="id_schBplNo" name="schBplNo" value="<c:out value="${param.schBplNo}" />" title="사업장관리번호 입력" placeholder="사업장관리번호">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="id_trCorpNm"> 기업명 </label>
							</dt>
							<dd>
								<input type="search" id="id_trCorpNm" name="trCorpNm" value="<c:out value="${param.trCorpNm}" />" title="기업명" placeholder="기업명">
							</dd>
						</dl>
					</div>
				</div>
			</div>
		
			<div class="btns-area">
		<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
			<img src="${contextPath}/dct/images/icon/icon_search03.png" alt="" /> 
				<strong>
					검색 
				</strong>
		</button>
	</div>
	</fieldset>
	</form>
</div>

	<div class="title-wrapper">
		<p class="total fl">
			총 <strong><c:out value="${totalCount}" /></strong> 건 ( <c:out value="${paginationInfo.currentPageNo}" /> / <c:out value="${paginationInfo.lastPageNo}" /> 페이지 )
		</p>
		<div class="fr">
           	<a href="${contextPath}/dct/analysis/analysisExcel.do?mId=${mId}<c:if test="${not empty param.insttIdx}">&insttIdx=${param.insttIdx}</c:if><c:if test="${not empty param.schBplNo}">&schBplNo=${param.schBplNo}</c:if><c:if test="${not empty param.trCorpNm}">&trCorpNm=${param.trCorpNm}</c:if>" class="btn-m01 btn-color01">엑셀다운로드</a>
        </div>
	</div>
	<div class="table-type01 horizontal-scroll">
		<table>
			<caption>훈련성과분석 : 번호, 소속기관, 기업명, 훈련과정명, 훈련회차, 리포트유형, 훈련유형, 신청일, 작성에 관한 정보 제공표</caption>
			<colgroup>
				<col style="width: 5%;">
				<col style="width: 7%" />
				<col style="width: 15%" />
				<col style="width: 15%" />
				<col style="width: 15%" />
				<col style="width: 10%" />
				<col style="width: 13%" />
				<col style="width: 10%" />
				<col style="width: 10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="allchkAnals" id="allchkAnals" class="checkbox-type01"></th>
					<th scope="col">No</th>
					<th scope="col">소속기관</th>
					<th scope="col"><itui:objectItemName itemId="trCorpNm" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="tpNm" itemInfo="${itemInfo}" /></th>
					<th scope="col">훈련구분</th>
					<th scope="col"><itui:objectItemName itemId="trResultReprtSe" itemInfo="${itemInfo}" /></th>
					<th scope="col">등록일</th>
					<th scope="col"><itui:objectItemName itemId="status" itemInfo="${itemInfo}" /></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr>
						<td colspan="9" class="bllist">
							<spring:message code="message.no.list" />
						</td>
					</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}" />
				<c:set var="listColumnName" value="${settingInfo.idx_column}" />
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
					<c:set var="listKey" value="${listDt[listColumnName]}" />
					<tr id="column">
						<td>
							<input type="checkbox" name="rsltAnalsIdx" id="id_rsltAnalsIdx" value="${listDt.RSLT_ANALS_IDX}" class="checkbox-type01" />
						</td>
						<td class="num"><c:out value="${listNo}" /></td>
						<td class="tpNm"><c:out value="${listDt.INSTT_NAME}" /></td>
						<td class="trCorpNm">
							<a href="#" onclick="location.href='${contextPath}/dct/analysis/input.do?mId=130&<c:if test="${listDt.DEVLOP_IDX eq null}">cnslIdx=${listDt.CNSL_IDX}</c:if><c:if test="${listDt.CNSL_IDX eq null}">devlopIdx=${listDt.DEVLOP_IDX}</c:if>&rsltAnalsIdx=${listDt.RSLT_ANALS_IDX}&bplNo=${listDt.BPL_NO}&mode=m'" 
							class="fn_btn_view"> 
								<strong class="point-color01">
									<itui:objectView itemId="trCorpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
								</strong>
							</a>
						</td>
						<td class="tpNm">
							<itui:objectView itemId="tpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
						</td>
						<td>
							<c:out value="${listDt.CNSL_IDX eq null ? '과정개발' : '컨설팅'}"/>
						</td>
						<td class="trResultReprtSe">
							<c:out value="${listDt.TR_RESULT_REPRT_SE eq '1' ? '만족도 및 성취도 조사' : '현업적용'}" />
						</td>
						<td class="regiDate">
							<c:out value="${fn:substring(listDt.REGI_DATE,0,10)}" />
						</td>
						<td>
							<c:out value="${listDt.STATUS eq '0' ? '작성중' : '제출'}"/>
						</td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}" />
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="paging-navigation-wrapper">
		<form action="list.do" name="pageForm" method="get">
			<!-- 페이징 네비게이션 -->
			<p class="paging-navigation" onclick="paginationHandler('pageForm')">
				<pgui:pagination
					listUrl="${contextPath}/dct/analysis/list.do?mId=${mId}"
					pgInfo="${paginationInfo}" imgPath="${imgPath}"
					pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
			</p>
			<!-- //페이징 네비게이션 -->
		</form>
	</div>

	<div class="btns-area mt20">
		<div class="btns-right">
			<a href="#" class="btn-m01 btn-color02 m-w100"
				onclick="delAnalysis(this.id);"> 삭제 </a>
		</div>
	</div>

<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
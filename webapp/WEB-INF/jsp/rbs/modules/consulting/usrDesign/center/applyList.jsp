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

<!-- contents  -->
<article>
	<div class="contents" id="contents">
		<div class="contents-wrapper">


			<!-- CMS 시작 -->

			<div class="contents-area02">
				<form action="<c:out value=" ${formAction}"/>
				" method="get"
					id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
					<input type="hidden" name="mId" value="96">

					<legend class="blind"> 컨설팅 신청 내역 검색양식 </legend>
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
										<select id="is_cmptncBrffcIdx" name="is_cmptncBrffcIdx" class="w200">
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
											<option value="">상태</option>
											<option value="10">신청</option>
											<option value="30">접수</option>
										</select>
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="calendar0101"> 기간 </label>
									</dt>
									<dd>
										<div class="input-calendar-wrapper">
											<div class="input-calendar-area">
												<input type="text" id="startDate" name="startDate"
													class="sdate" placeholder="기준 시작일" autocomplete="off">
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="endDate" name="endDate" class="edate" placeholder="기준 종료일" autocomplete="off">
											</div>
										</div>
									</dd>
								</dl>
							</div>
						</div>


						<div class="one-box">
							<dl>
								<dt>
									<label for="is_corpNm"> 기업명 </label>
								</dt>
								<dd>
									<input type="text" id="is_corpNm" name="is_corpNm" value=""
										title="기업명 입력" placeholder="기업명">
								</dd>
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
						${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지
						)
					</p>
				</div>
				<div class="table-type01 horizontal-scroll">
					<table>
						<caption>컨설팅 신청 내역 접수표 : 체크, 번호, 기업명, 사업장관리번호, 소속기관,
							신청일, 상태, 접수에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col"><itui:objectItemName itemId="corpNm"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="bplNo"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="cmptncBrffcIdx"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="cnslType"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><spring:message code="item.regidate.name" /></th>
								<th scope="col"><itui:objectItemName itemId="confmStatus"
										itemInfo="${itemInfo}" /></th>
								<th scope="col">접수</th>
								<th scope="col">기업HRD이음컨설팅</th>
							</tr>
						</thead>


						<tbody class="alignC">
							<c:if test="${empty list}">
								<tr>
									<td colspan="9" class="bllist"><spring:message
											code="message.no.list" /></td>
								</tr>
							</c:if>
							<c:set var="listIdxName" value="${settingInfo.idx_name}" />
							<c:set var="listColumnName" value="${settingInfo.idx_column}" />
							<c:set var="listNo"
								value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
							<c:forEach var="listDt" items="${list}" varStatus="i">
								<c:set var="listKey" value="${listDt[listColumnName]}" />
								<tr>
									<td class="num">${listNo}</td>
									<td>
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 10}">
												<a href="${contextPath}/web/consulting/cnslViewForm.do?mId=96&cnslIdx=${listDt.CNSL_IDX}" />				
											</c:when>
											<c:otherwise>
												<a href="${contextPath}/web/consulting/cnslAdminView.do?mId=96&cnslIdx=${listDt.CNSL_IDX}" />
											</c:otherwise>
										</c:choose>
									
										<strong class="point-color01"> <itui:objectView
												itemId="corpNm" itemInfo="${itemInfo}" objDt="${listDt}" /></strong></a></td>
									<td class="subject"><itui:objectView itemId="bplNo"
											itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td>
										<c:forEach var="instt" items="${insttList}" varStatus="i">
                    						<c:if test="${listDt.CMPTNC_BRFFC_IDX eq instt.INSTT_IDX}"><c:out value="${instt.INSTT_NAME}"/></c:if>
                    					</c:forEach>
									</td>
									<td>
										<c:choose>
											<c:when test="${listDt.CNSL_TYPE eq '1'}">과정개발(사업주)</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '2'}">과정개발(일반직무전수 OJT)</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '3'}">과정개발(과제수행 OJT)</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '4'}">심층진단</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '5'}">훈련체계수립</c:when>
											<c:when test="${listDt.CNSL_TYPE eq '6'}">현장개산</c:when>
										</c:choose> 
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd"
											value="${listDt.REGI_DATE}" />
									</td>

									<td>
										<c:choose>
											<c:when test="${listDt.CONFM_STATUS eq '10'}">신청</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '30'}">접수</c:when>
											<c:when test="${listDt.CONFM_STATUS eq '35'}">반려요청</c:when>
										</c:choose>
									</td>
									<td><c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 10}">
												<button type="button" class="btn-m02 btn-color03"
													onClick="receiveCnsl(`${listDt.CNSL_IDX}`, 30)">
													<span> 접수 </span>
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn-m02 btn-color03" 
													onClick="location.href='${contextPath}/web/consulting/cnslAdminView.do?mId=96&cnslIdx=${listDt.CNSL_IDX}'">상세보기</button>
											</c:otherwise>
										</c:choose></td>
									<td>
										<button type="button" class="btn-m02 btn-color03"
											onClick="window.open('${contextPath}/web/bsisCnsl/consultingClipReport.do?mId=56&bsiscnslIdx=${listDt.BSISCNSL_IDX}', '_blank')">
											<span> 열기 </span>
										</button>
									</td>
								</tr>
								<c:set var="listNo" value="${listNo - 1}" />
							</c:forEach>
						</tbody>

					</table>
				</div>
				
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="applyList.do?mId=96" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>


			</div>


			<!-- //CMS 끝 -->
		</div>
	</div>
</article>



<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<script type="text/javascript" src="${contextPath}${jsPath}/list.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
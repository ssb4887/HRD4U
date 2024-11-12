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
<style>
	#overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.5);
		display: none;
		z-index: 9999;
	}
	
	.loader {
		border:4px solid #f3f3f3;
		border-top: 4px solid #3498db;
		border-radius: 50%;
		width: 50px;
		height: 50px;
		animation: spin 2s linear infinite;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 10000;
	}
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/edu/eduUsr.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

	<div class="contents-wrapper">
		<!-- CMS 시작 -->
		<div class="contents-area02">
			<form action="" method="">
				<fieldset>
					<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
					<legend class="blind"> 교육과정 신청 검색양식 </legend>
					<div class="basic-search-wrapper">
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_instt"> 소속기관 </label>
									</dt>
									<dd>
										<select id="is_instt" name="is_instt">
											<option value="">전체</option>
											<c:forEach items="${searchOptnHashMap.ORG_CODE}" var="org">
												<option value="<c:out value='${org.OPTION_CODE}' />"><c:out value='${org.OPTION_NAME}' /></option>
											</c:forEach>
										</select>
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_edcConfmStatus"> 접수상태 </label>
									</dt>
									<dd>
										<select id="is_edcConfmStatus" name="is_edcConfmStatus">
											<option value="">전체</option>
											<c:forEach items="${searchOptnHashMap.EDC_CONFM_STATUS}" var="confmStatus">
												<option value="<c:out value='${confmStatus.OPTION_CODE}' />"><c:out value='${confmStatus.OPTION_NAME}' /></option>
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
										<label for="is_edcDate1"> 교육기간 </label>
									</dt>
									<dd>
										<div class="input-calendar-wrapper">
											<div class="input-calendar-area">
												<input type="text" id="is_edcDate1" name="is_edcDate1" class="sdate" title="시작일 입력" placeholder="시작일" autocomplete="off" />
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="is_edcDate2" name="is_edcDate2" class="edate" title="종료일 입력" placeholder="종료일" autocomplete="off" />
											</div>
										</div>
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_recptDate1"> 접수기간 </label>
									</dt>
									<dd>
										<div class="input-calendar-wrapper">
											<div class="input-calendar-area">
												<input type="text" id="is_recptDate1" name="is_recptDate1" class="sdate1" title="시작일 입력" placeholder="시작일" autocomplete="off" />
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="is_recptDate2" name="is_recptDate2" class="edate1" title="시작일 입력" placeholder="종료일" autocomplete="off" />
											</div>
										</div>
									</dd>
								</dl>
							</div>
	
	
						</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="keyField">검색</label>
								</dt>
								<dd>
									<div class="input-search-wrapper">
										<select id="keyField" name="keyField">
											<option value="edcName">교육명</option>
											<option value="edcPlace">교육장소</option>
										</select>
										<input type="search" id="key" name="key" value="" class="" placeholder="입력해주세요">
									</div>
								</dd>
							</dl>
						</div>
						<c:if test="${usertype >= 40 and settingInfo.edc_cd eq 'DCT'}">
							<div class="one-box">
								<div class="half-box">
									<dl>
										<dt>
											<label for="is_doctorIdx">주치의</label>
										</dt>
										<dd>
											<div class="input-search-wrapper">
												<input type="text" name="is_doctorIdx" id="is_doctorIdx" value="" readonly placeholder="주치의 입력" onclick="openModalForMember()" />
											</div>
										</dd>
									</dl>
								</div>
							</div>
						</c:if>
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
	
		<div class="title-wrapper">
			<p class="total fl">
				총 <strong><c:out value="${paginationInfo.totalRecordCount}" /></strong> 건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
			</p>
		</div>
		
		<div class="table-type01 horizontal-scroll">
			<table>
				<caption>교육과정 신청 이력표 : 번호, 교육과정명, 소속기관명, 교육기간, 접수기간, 교육장소, 신청상태, 참석여부, 수료증에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 5%">
					<col style="width: 20%">
					<col style="width: 8%">
					<col style="width: 15%">
					<col style="width: 15%">
					<col style="width: 10%">
					<col style="width: 8%">
					<col style="width: 8%">
					<col style="width: 8%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">교육과정명</th>
						<th scope="col">소속기관</th>
						<th scope="col">교육기간</th>
						<th scope="col">접수기간</th>
						<th scope="col">교육장소</th>
						<th scope="col">신청상태</th>
						<th scope="col">참석여부</th>
						<th scope="col">수료증</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
						<tr>
							<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
					</c:if>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<tr>
							<td class="num"><c:out value='${listNo}' /></td>
							<td>
								<a href="javascript:void(0);" data-idx="<c:out value='${listDt.EDC_IDX}' />" onclick="goView(event)">
									<strong class="point-color01">
										<c:out value='${listDt.EDC_NAME}' />
									</strong>
								</a>
								<a href="javascript:void(0);" class="btn-linked" data-idx="<c:out value='${listDt.EDC_IDX}' />" onclick="goView(event)">
									<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="" />
								</a>
							</td>
							<td>
								<c:out value='${listDt.INSTT_NAME}' />
							</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.EDC_START_DATE}"/>
								~<br/>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.EDC_END_DATE}"/> 
							</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.RECPT_BGNDT}"/>
								~<br/>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.RECPT_ENDDT}"/> 
							</td>
							<td><c:out value='${listDt.EDC_PLACE}' /></td>
							<td><c:out value='${listDt.CONFM_STATUS_NAME}' /></td>
							<td>
								<c:if test="${empty listDt.ATT_YN}"></c:if>
								<c:if test="${listDt.ATT_YN eq 'Y'}">참석</c:if>
								<c:if test="${listDt.ATT_YN eq 'N'}">불참</c:if>
							</td>
							<td>
								<c:choose>
									<c:when test="${listDt.CTFHV_ISSUE_YN eq 'N' or empty listDt.CTFHV_ISSUE_YN}">대상아님</c:when>
									<c:when test="${listDt.CTFHV_ISSUE_YN eq 'Y' and listDt.ATT_YN eq 'Y' and empty listDt.CTFHV_NO}">발급대기</c:when>
									<c:when test="${listDt.CTFHV_ISSUE_YN eq 'Y' and listDt.ATT_YN eq 'N'}">참석필요</c:when>
									<c:when test="${listDt.CTFHV_ISSUE_YN eq 'Y' and empty listDt.ATT_YN}">참석필요</c:when>
									<c:when test="${listDt.CTFHV_ISSUE_YN eq 'Y' and listDt.ATT_YN eq 'Y' and not empty listDt.CTFHV_NO}">
										<button type="button" class="btn-m02 btn-color03 w100" data-member="<c:out value='${listDt.MEMBER_IDX}' />" data-idx="<c:out value='${listDt.EDC_IDX}' />" onclick="printCertificate(event)">
											<span>출력하기</span>
										</button>
									</c:when>
									<c:otherwise>대상아님</c:otherwise>
								</c:choose>
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
			<!-- //페이징 네비게이션 -->
		</div>
		<!-- //CMS 끝 -->
	</div>

<div class="mask"></div>

<!-- 주치의 검색 모달(doctor) -->
<c:if test="${usertype >= 40 and settingInfo.edc_cd eq 'DCT'}">
<div class="modal-wrapper" id="modal-doctor">
	<h2>회원 검색</h2>
	<div class="modal-area">
		<form action="" id="form-doctor">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="insttIdx">소속기관</label>
							</dt>
							<dd>
								<select id="insttIdx" name="insttIdx" class="w100">
									<option value="">전체</option>
									<c:forEach items="${searchOptnHashMap.ORG_CODE}" var="org">
										<option value="<c:out value='${org.OPTION_CODE}' />"><c:out value='${org.OPTION_NAME}' /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="keyField">검색</label>
							</dt>
							<dd>
								<div class="input-search-wrapper">
									<select id="keyField" name="keyField">
										<option value="loginId">아이디</option>
										<option value="hName">이름</option>
									</select>
									<input type="search" id="key" name="key" placeholder="입력해주세요">
								</div>
							</dd>
						</dl>
					</div>
				</div>
				<div class="btns-area">
					<button type="button" class="btn-m02 btn-color02 round01" onclick="searchDoctor()">검색</button>
				</div>
			</div>

			<div class="contents-box pl0">
				<p class="total mb05">
					총 <strong id="doctorCount">0</strong> 건
				</p>

				<div class="table-type01 horizontal-scroll">
					<table class="width-type03">
						<caption>주치의 검색표 : 번호, 아이디, 성명, 소재지, 소속기관, 관할구역에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 5%">
							<col style="width: 10%">
							<col style="width: 10%">
							<col style="width: 15%">
							<col style="width: 10%">
							<col style="width: 10%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col">아이디</th>
								<th scope="col">성명</th>
								<th scope="col">소재지</th>
								<th scope="col">소속기관</th>
								<th scope="col">선택</th>
							</tr>
						</thead>
						<tbody id="modal-doctor-tbody">
							<tr>
								<td colspan="6">검색결과가 없습니다.</td>
							</tr>
						</tbody>
					</table>

				</div>
			</div>

		</form>
	</div>

	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<!-- // 주치의 검색 모달 끝 -->
</c:if>
<!-- // contents -->
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
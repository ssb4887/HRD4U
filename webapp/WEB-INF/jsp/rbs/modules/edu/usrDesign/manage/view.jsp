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
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/edu/eduDct.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	
	<div class="tabmenu-wrapper">
		<ul>
			<li>
				<a href="javascript:void(0);" id="tab1-btn" class="tabmenu topmenu4-6-1" onclick="openTab(event, 'tab1')">교육과정 정보</a>
			</li>
			<li>
				<a href="javascript:void(0);" id="tab2-btn" class="tabmenu topmenu4-6-2" onclick="openTab(event, 'tab2')">교육생 정보</a>
			</li>
		</ul>
	</div>
	
	<!-- CMS 시작 -->
	<div id="tab1" class="tabcontents">
		<div class="contents-area">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>교육과정 정보표 : 교육명, 교육 장소, 교육기간, 총교육시간, 강사정보, 게시여부,
						접수기간, 수료증발급여부, 신청최대인원, 첨부파일, 주요내용에 관한 정보 제공표</caption>
					<colgroup>
						<col width="20%">
						<col width="20%">
						<col width="60%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">교육명</th>
							<td colspan="2" class="left"><c:out value='${edc.EDC_NAME}' /></td>
						</tr>
						<tr>
							<th scope="row">교육 장소</th>
							<td colspan="2" class="left"><c:out value='${edc.EDC_PLACE}' /></td>
						</tr>
						<tr>
							<th scope="row">교육기간</th>
							<td colspan="2" class="left">
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.EDC_START_DATE}"/>
								~
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.EDC_END_DATE}"/>
							</td>
						</tr>
						<tr>
							<th scope="row">총교육시간</th>
							<td colspan="2" class="left"><c:out value='${edc.TOT_EDC_TIME}' /> 시간</td>
						</tr>
						<tr>
							<th rowspan="4" scope="row">강사 정보</th>
							<th class="bg01">강사</th>
							<td class="left">
								<c:out value='${edc.INSTRCTR_NAME}' />
							</td>
						</tr>
						<tr>
							<th class="bg01">연락처</th>
							<td class="left js-format-phone">
								<c:out value='${edc.INSTRCTR_TELNO}' />
							</td>
						</tr>
						<tr>
							<th class="bg01">이메일</th>
							<td class="left">
								<c:out value='${edc.INSTRCTR_EMAIL}' />
							</td>
						</tr>
						<tr>
							<th class="bg01">강사 소개</th>
							<td class="left">
								<c:out value='${edc.INSTRCTR_INTRCN}' />
							</td>
						</tr>
						<tr>
							<th scope="row">공개여부</th>
							<td colspan="2" class="left">
								<c:if test="${edc.OTHBC_YN eq 'Y'}">공개</c:if>
								<c:if test="${edc.OTHBC_YN eq 'N'}">비공개</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">접수기간</th>
							<td colspan="2" class="left">
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.RECPT_BGNDT}"/>
								~
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.RECPT_ENDDT}"/>
							</td>
						</tr>
						<tr>
							<th scope="row">수료증 발급여부</th>
							<td colspan="2" class="left">
								<c:if test="${edc.CTFHV_ISSUE_YN eq 'Y'}">발급</c:if>
								<c:if test="${edc.CTFHV_ISSUE_YN eq 'N'}">미발급</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">신청최대인원</th>
							<td colspan="2" class="left">
								<c:if test="${edc.MAX_RECPT_NMPR eq 0 or empty edc.MAX_RECPT_NMPR}">제한없음</c:if>
								<c:if test="${edc.MAX_RECPT_NMPR ne 0}">${edc.MAX_RECPT_NMPR}</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">첨부파일</th>
							<td colspan="2" class="left">
								<c:if test="${empty fileList}">등록된 첨부파일이 없습니다.</c:if>
								<c:forEach items="${fileList}" var="file">
									<p class="attached-file">
										<a href="javascript:void(0);" data-idx="<c:out value='${file.FLE_IDX}' />" class="fn_filedown" onclick="downloadFile(event);" >${file.FILE_ORIGIN_NAME}</a>
									</p>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th scope="row">주요 내용</th>
							<td colspan="2" class="left">
								<c:out value='${edc.CN}' />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	
		<div class="btns-area">
			<div class="btns-right">
				<a href="${URL_INPUT}&idx=<c:out value='${edc.EDC_IDX}' />" class="btn-m01 btn-color02" > 수정 </a>
				<a href="${URL_LIST}" class="btn-m01 btn-color01"> 목록 </a>
			</div>
		</div>
	</div>
	
	<div id="tab2" class="tabcontents">
		<div class="contents-area02">
		
<%-- 		<itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_VIEW}" itemListSearch="${itemInfo.view_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_VIEW}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/> --%>
		
			<form action="<c:out value="${URL_VIEW}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
				<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
				<legend class="blind"> 교육과정 신청자 검색 양식 </legend>
				<div class="basic-search-wrapper">
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="is_instt"> 소속기관 </label>
								</dt>
								<dd>
									<select id="is_instt" name="is_instt" class="select" title="소속기관">
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
									<label for="is_edcConfmStatus"> 신청 상태 </label>
								</dt>
								<dd>
									<select id="is_edcConfmStatus" name="is_edcConfmStatus">
										<option value="">전체</option>
										<c:forEach items="${searchOptnHashMap.EDC_CONFM_STATUS}" var="edcConfmStatus">
											<option value="<c:out value='${edcConfmStatus.OPTION_CODE}' />"><c:out value='${edcConfmStatus.OPTION_NAME}' /></option>
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
									<label for="is_attYn"> 참석여부 </label>
								</dt>
								<dd>
									<select id="is_attYn" name="is_attYn">	
										<option value="">전체</option>
										<option value="Y">참석</option>
										<option value="N">불참석</option>
									</select>
								</dd>
							</dl>
	
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label> 신청일자 </label>
								</dt>
								<dd>
									<div class="input-calendar-wrapper">
										<div class="input-calendar-area">
											<input type="text" id="is_regiDate1" name="is_regiDate1" class="sdate" title="시작일 입력" placeholder="시작일" autocomplete="off" />
										</div>
										<div class="word-unit">~</div>
										<div class="input-calendar-area">
											<input type="text" id="is_regiDate2" name="is_regiDate2" class="edate" title="종료일 입력" placeholder="종료일" autocomplete="off" />
										</div>
									</div>
								</dd>
							</dl>
						</div>
					</div>
	
					<div class="one-box">
						<dl>
							<dt>
								<label for="keyField"> 검색 </label>
							</dt>
							<dd>
								<div class="input-search-wrapper">
									<select id="keyField" name="keyField">
										<option value="memberName">성명</option>
										<option value="corpName">기업명</option>
									</select>
									<input type="search" id="key" name="key" value="" placeholder="입력해주세요" >
								</div>
							</dd>
						</dl>
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
			</form>
		</div>
		
<!-- 		<div class="tabmenu-wrapper01"> -->
<!-- 			<div class="table-type01"> -->
<!-- 				<table> -->
<!-- 					<caption></caption> -->
<!-- 					<colgroup></colgroup> -->
<!-- 					<thead> -->
<!-- 						<tr> -->
<!-- 							<th class="bg01" scope="col">총 인원</th> -->
<!-- 							<th class="bg01" scope="col">신청 수</th> -->
<!-- 							<th class="bg01" scope="col">승인 수</th> -->
<!-- 							<th class="bg01" scope="col">반려 수</th> -->
<!-- 							<th class="bg01" scope="col">참석 수</th> -->
<!-- 						</tr> -->
<!-- 					</thead> -->
<!-- 					<tbody> -->
<!-- 						<tr> -->
<%-- 							<td><c:out value='${edc.TOTAL_MEMBER_COUNT}' /> 명</td> --%>
<%-- 							<td><c:out value='${edc.MEMBER_COUNT_0}' /> 명</td> --%>
<%-- 							<td><c:out value='${edc.MEMBER_COUNT_1}' /> 명</td> --%>
<%-- 							<td><c:out value='${edc.MEMBER_COUNT_2}' /> 명</td> --%>
<%-- 							<td><c:out value='${edc.MEMBER_ATT_Y_COUNT}' /> 명</td> --%>
<!-- 						</tr> -->
<!-- 					</tbody> -->
<!-- 				</table> -->
<!-- 			</div> -->
<!-- 		</div> -->

		<div class="contents-area">
			<div class="title-wrapper">
				<p class="total fl">
					총 <strong><c:out value="${paginationInfo.totalRecordCount}" /></strong> 건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
				</p>
	
				<div class="fr btns-area">
					<div class="btns-left mobile-space01">
						<a href="javascript:void(0);" class="btn-m01 btn-color01 depth2" onclick="openModalForExcel()"> 엑셀업로드 </a>
						<a href="javascript:void(0);" class="btn-m01 btn-color01 depth2" onclick="downloadEdcMembers()"> 엑셀다운로드 </a>
					</div>
					<div class="btns-left">
						<a href="javascript:void(0);" class="btn-m01 btn-color01 depth3" onclick="openModalForChange()">일괄변경</a>
						<a href="javascript:void(0);" class="btn-m01 btn-color01 depth3" onclick="openModalForRegister()">등록</a>
						<a href="javascript:void(0);" class="btn-m01 btn-color02 depth3" onclick="deleteEdcMember()">삭제</a>
					</div>
				</div>
			</div>
			<div class="table-type01 horizontal-scroll">
				<table>
					<caption>공지사항표 : 체크박스, 번호, 소속기관, 기업명, 신청방법, 신청일자, 성명, 연락처, 신청상태, 참석여부, 수료증에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 3%">
						<col style="width: 5%">
						<col style="width: 8%">
						<col style="width: 9%">
						<col style="width: 9%">
						<col style="width: 9%">
						<col style="width: 9%">
						<col style="width: 9%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 10%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" id="checkbox-all" name="checkbox-member" value="" class="checkbox-type01"></th>
							<th scope="col">번호</th>
							<th scope="col">소속기관</th>
							<th scope="col">기업명</th>
							<th scope="col">신청방법</th>
							<th scope="col">신청일자</th>
							<th scope="col">성명</th>
							<th scope="col">연락처</th>
							<th scope="col">신청상태</th>
							<th scope="col">참석여부</th>
							<th scope="col">수료증</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty edcMemberList}">
							<tr>
								<td colspan="11" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
						</c:if>
						
						<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
						<c:forEach var="listDt" items="${edcMemberList}" varStatus="i">
							<tr>
								<td>
									<input type="checkbox" class="checkbox-type01" name="checkbox-member" data-member="<c:out value='${listDt.MEMBER_NAME}' />" value="<c:out value='${listDt.MEMBER_IDX}' />">
								</td>
								<td><c:out value='${listNo}' /></td>
								<td><c:out value='${listDt.INSTT_NAME}' /></td>
								<td><c:out value='${listDt.ORG_NAME}' /></td>
								<td><c:out value='${listDt.REGI_WAY}' /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
								<td>
									<a href="javascript:void(0);" onclick="openModalForMember('${listDt.MEMBER_IDX}');">
										<strong class="point-color01">
											<c:out value='${listDt.MEMBER_NAME}' />
										</strong>
									</a>
								</td>
								<td class="js-format-phone"><c:out value='${listDt.MOBILE_PHONE}' /></td>
								<td>
									<select class="w100 open-modal01" data-name="edcConfmStatus" data-memberIdx="<c:out value='${listDt.MEMBER_IDX}' />" onchange="changeMemberStatus(event)">
										<c:forEach items="${searchOptnHashMap.EDC_CONFM_STATUS}" var="edcConfmStatus">
											<c:set var="selected" value="${(edcConfmStatus.OPTION_CODE eq listDt.CONFM_STATUS) ? 'selected' : ''}" />
											<option value="<c:out value='${edcConfmStatus.OPTION_CODE}' />" ${selected}><c:out value='${edcConfmStatus.OPTION_NAME}' /></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select class="w100 open-modal01" data-name="attYn" data-memberIdx="<c:out value='${listDt.MEMBER_IDX}' />" onchange="changeMemberStatus(event)">
										<option value="" <c:if test="${listDt.ATT_YN eq ''}">selected</c:if>>선택</option>
										<option value="Y" <c:if test="${listDt.ATT_YN eq 'Y'}">selected</c:if>>참석</option>
										<option value="N" <c:if test="${listDt.ATT_YN eq 'N'}">selected</c:if>>불참</option>
									</select>
								</td>
								<td>
									<c:if test="${edc.CTFHV_ISSUE_YN eq 'N'}">대상아님</c:if>
									<c:choose>
										<c:when test="${edc.CTFHV_ISSUE_YN eq 'Y' and listDt.ATT_YN ne 'Y'}">참석필요</c:when>
										<c:when test="${edc.CTFHV_ISSUE_YN eq 'Y' and not empty listDt.CTFHV_NO}">
											<button type="button" class="btn-m02 btn-color02 w100" data-idx="<c:out value='${listDt.MEMBER_IDX}' />" onclick="printCertificate(event)">
												<span>출력하기</span>
											</button>
											<button type="button" class="btn-m02 btn-color04 w100" data-idx="<c:out value='${listDt.MEMBER_IDX}' />" data-name="<c:out value='${listDt.MEMBER_NAME}' />" onclick="deleteCertificate(event)">
												<span>발급취소</span>
											</button>
										</c:when>
										<c:when test="${edc.CTFHV_ISSUE_YN eq 'Y' and empty listDt.CTFHV_NO and listDt.ATT_YN eq 'Y'}">
											<button type="button" class="btn-m02 btn-color03 w100" data-idx="<c:out value='${listDt.MEMBER_IDX}' />" data-name="<c:out value='${listDt.MEMBER_NAME}' />" onclick="issueCertificate(event)">
												<span>발급하기</span>
											</button>
										</c:when>
									</c:choose>
								</td>
							</tr>
							<c:set var="listNo" value="${listNo - 1}"/>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${edc.CTFHV_ISSUE_YN eq 'Y'}">
				<p class="word-type02 mt10">※ 참석한 기업에 한해 수료증 발급이 가능합니다.</p>
			</c:if>
			<div class="paging-navigation-wrapper">
				<!-- 페이징 네비게이션 -->
				<pgui:pagination listUrl="${URL_VIEW}&idx=${edc.EDC_IDX}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
				<!-- //페이징 네비게이션 -->
			</div>
			
		</div>
	</div>
	<!-- //CMS 끝 -->
</div>

<!-- 회원 검색 모달(register) -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-register">
	<h2>회원 검색</h2>
	<div class="modal-area">
		<form action="" id="form-register">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box mt0">
						<dl>
							<dt>
								<label for="reg_usertype">사용자유형</label>
							</dt>
							<dd>
								<select id="reg_usertype" name="groupCode">
									<option value="">전체</option>
									<c:forEach items="${userGroupList}" var="userGroup">
										<option value="<c:out value='${userGroup.OPTION_CODE}' />"><c:out value='${userGroup.OPTION_NAME}' /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="reg_instt"> 소속기관 </label>
							</dt>
							<dd>
								<select id="reg_instt" name="instt" class="w100">
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
								<label for="reg_search">검색</label>
							</dt>
							<dd>
								<div class="input-search-wrapper">
									<select id="reg_search" name="keyField">
										<option value="userid">아이디</option>
										<option value="username">이름</option>
									</select>
									<input type="search" id="keyword" name="keyword" value="">
								</div>
							</dd>
						</dl>
					</div>
				</div>
				<div class="btns-area">
					<button type="button" class="btn-m02 btn-color02 round01" onclick="searchMember()">검색</button>
				</div>
			</div>

			<div class="contents-box pl0">
				<p class="total mb05">
					총 <strong id="memberCount">0</strong> 건
				</p>

				<div class="table-type01 horizontal-scroll">
					<table class="width-type03">
						<caption>회원 검색표 : 아이디, 성명, 회원구분, 소속기업 혹은 소속기관명에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 20%">
							<col style="width: 10%">
							<col style="width: 20%">
							<col style="width: 10%">
							<col style="width: 10%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">성명</th>
								<th scope="col">회원구분</th>
								<th scope="col">기업(기관)</th>
								<th scope="col">소속기관</th>
								<th scope="col">선택</th>
							</tr>
						</thead>
						<tbody id="modal-register-tbody">
							<tr>
								<td colspan="5">신청할 회원을 검색해주세요.</td>
							</tr>
						</tbody>
					</table>

				</div>
			</div>

		</form>
	</div>

	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<!-- // 회원 검색 모달 끝 -->

<!-- 일괄 변경 모달 -->
<div class="modal-wrapper" id="modal-change">
	<h2>일괄변경</h2>
	<div class="modal-area">
		<form id="form-change">
			<div class="contents-box pl0">
				<div class="board-write">
					<div class="one-box">
						<dl>
							<dt>
								<label for="">변경할 상태</label>
							</dt>
							<dd>
								<div class="input-checkbox-area">
									<input type="checkbox" id="checkbox-confmStatus" data-target="confmStatus-box" class="checkbox-type01" checked onclick="toggleDisplay(event)" />
									<label for="checkbox-confmStatus">신청상태</label>
									<input type="checkbox" id="checkbox-attYn" data-target="attYn-box" class="checkbox-type01 ml20" checked onclick="toggleDisplay(event)" />
									<label for="checkbox-attYn">참석여부</label>
								</div>
							</dd>
						</dl>
					</div>
					<div class="one-box" id="confmStatus-box">
						<dl>
							<dt>
								<label for="change-confmStatus"> 신청상태 </label>
							</dt>
							<dd>
								<select id="change-confmStatus" name="confmStatus" class="w50">
									<c:forEach items="${searchOptnHashMap.EDC_CONFM_STATUS}" var="edcConfmStatus">
										<option value="<c:out value='${edcConfmStatus.OPTION_CODE}' />"><c:out value='${edcConfmStatus.OPTION_NAME}' /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
					<div class="one-box" id="attYn-box">
						<dl>
							<dt>
								<label for="change-attYn"> 참석여부 </label>
							</dt>
							<dd>
								<select id="change-attYn" name="attYn" class="w50">
									<option value="">선택</option>
									<option value="Y">참석</option>
									<option value="N">불참석</option>
								</select>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label> 교육생 </label>
							</dt>
							<dd>
								<div class="btn-delete-wrapper" id="modal-checkedMember">
<!-- 								<p class="word"> -->
<!-- 									<strong> 기업명1 </strong> -->
<!-- 									<button type="button" class="btn-delete">삭제</button> -->
<!-- 								</p> -->
								</div>
							</dd>
						</dl>
					</div>
				</div>
				<div class="btns-area">
					<button type="button" class="btn-m02 round01 btn-color03" onclick="changeStatusAll()">
						<span> 저장 </span>
					</button>

					<button type="button" class="btn-m02 round01 btn-color02" onclick="document.querySelector('.btn-modal-close').click()">
						<span> 취소 </span>
					</button>
				</div>
			</div>
		</form>
	</div>

	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<!-- // 일괄 변경 모달 끝 -->

<!-- 엑셀 업로드 모달 -->
<div class="modal-wrapper" id="modal-excelupload">
   	<h2>교육생 엑셀 업로드</h2>
       <div class="modal-area">
       	<form action="" id="excelUploadForm">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="sample">샘플 파일</label>
							</dt>
							<dd class="block">
								<p class="word-linked01" id="sample">
									<a href="${URL_SAMPLE}&sample=2">교육생 업로드 샘플 파일.xlsx</a>
								</p>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="excelFile">첨부 파일</label>
							</dt>
							<dd class="block">
								<div class="fileBox">
									<input type="text" id="fileName" class="fileName" readonly="readonly" placeholder="파일찾기">
									<label for="input-file" class="btn_file">찾아보기</label>
									<input type="file" id="input-file" class="input-file" required onchange="javascript:document.getElementById('fileName').value = this.value">
								</div>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label> 비고 </label>
							</dt>
							<dd class="block">
								<p class="word">
									<strong>엑셀 업로드를 이용한 데이터입력은 다운받은 엑셀 서식을 이용하여 업로드해주시기 바랍니다.</strong>
								</p>
							</dd>
						</dl>
					</div>
				</div>
			</div>

			<div class="btns-area">
				<button type="button" class="btn-b02 round01 btn-color03 left" onclick="uploadMemberExcel();">
					<span>업로드</span>
					<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="업로드 버튼 아이콘" class="arrow01" />
				</button>
			</div>
		</form>
	</div>

       <button type="button" class="btn-modal-close">모달 창 닫기</button>
 </div>
<!-- // 엑셀 업로드 모달 끝 -->

<!-- 회원 조회 모달 -->
<div class="modal-wrapper" id="modal-member">
	<h2>회원 기본정보</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="board-write">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="memberId">아이디</label>
							</dt>
							<dd>
								<span id="memberId"></span>
							</dd>
						</dl>

					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="memberName">이름</label>
							</dt>
							<dd>
								<span id="memberName"></span>
							</dd>
						</dl>
					</div>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="memberType">사용자 유형</label>
						</dt>
						<dd>
							<span id="memberType"></span>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="mobilePhone">연락처</label>
						</dt>
						<dd>
							<span id="mobilePhone"></span>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="memberEmail">이메일</label>
						</dt>
						<dd>
							<span id="memberEmail"></span>
						</dd>
					</dl>
				</div>
			</div>
			
			<c:if test="${usertype >= 40}">
				<div class="btns-area">
					<a href="#" class="btn-m01 btn-color03" id="memberLink" target="_blank">더보기</a>
				</div>
			</c:if>
		</div>
	</div>

	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<!-- // 회원 조회 모달 끝 -->
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
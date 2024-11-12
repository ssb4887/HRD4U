<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />

<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/bsisCnsl/init.js"/>"></script>
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

	<div id="cms_board_article" class="subConBox">
		<!-- contents  -->
		<article>
			<div class="contents" id="contents">
				<div class="contents-wrapper">
	
					<!-- CMS 시작 -->
	
					<div class="contents-area">
						<div class="contents-box pl0">
							<dl class="introduction-wrapper001">
								<dt>
									<img src="${contextPath}${imgPath}/icon/icon_symbol02.png" alt="" />
									<strong>기업HRD이음컨설팅이란?</strong>
								</dt>
								<dd>
	                                                        기업이 응답한 설문 내용을 분석하여, HRD전문가(능력개발전담주치의)가 <span class="span-br"></span> 우리 기업에 적합한 <strong class="point-color08">교육훈련 프로그램을 추천</strong>해 드립니다.<span class="span-br"></span> HRD기초진단을 받은 기업이라면 누구나 참여 가능합니다.
	                                                        지금 바로 신청하세요.
	                            </dd>
                            </dl>
                        </div>
                        
                        <c:if test="${userType ne 20 and not empty list}">
	                        <div class="contents-box pl0">
								<div class="table-type01 horizontal-scroll">
									<table class="width-type02">
										<caption>기업HRD이음컨설팅 소개 정보표 : 선택, 기초진단서 발급번호, 발급일자, 업체명,
											사업장번호에 관한 정보 제공표</caption>
										<colgroup>
											<col style="width: 10%" />
											<col style="width: 30%" />
											<col style="width: 20%" />
											<col style="width: 20%" />
											<col style="width: 20%" />
										</colgroup>
										<thead>
											<tr>
												<th scope="col">선택</th>
												<th scope="col"><itui:objectItemName itemId="bscIssueNo" itemInfo="${itemInfo}"/></th>
												<th scope="col"><itui:objectItemName itemId="bscIssueDate" itemInfo="${itemInfo}"/></th>
												<th scope="col"><itui:objectItemName itemId="corpName" itemInfo="${itemInfo}"/></th>
												<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${login eq true && userType ne 10}">
												<c:forEach var="listDt" items="${list}" varStatus="status">
													<tr>
														<td><input type="radio" id="radio01${status.index}" name="radio-bsc" value="${listDt.BSC_IDX}" class="radio-type01"></td>
														<td><label for="radio01${status.index}"><itui:objectView itemId="bscIssueNo" itemInfo="${itemInfo}" objDt="${listDt}"/></label></td>
														<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSC_ISSUE_DATE}" /></td>
														<td><span class="point-color01"><itui:objectView itemId="corpName" itemInfo="${itemInfo}" objDt="${listDt}"/></span></td>
														<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
													</tr>
													<c:set var="listNo" value="${listNo - 1}"/>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
								
								<!-- 페이지 내비게이션 -->
								<c:if test="${not empty list}">
									<div class="paging-navigation-wrapper">
										<pgui:pagination listUrl="init.do?mId=55" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
									</div>
								</c:if>
								<!-- //페이지 내비게이션 -->
								
							</div>
                        </c:if>
                        
                        <c:if test="${userType eq 20}">
                        	<p class="center word-type01 mb10"><strong class="point-color08">협약맺은 기업에 한해</strong> 기업HRD이음컨설팅을 조회할 수 있습니다.<br/>기업HRD이음컨설팅을 신청하려면 기업회원으로 로그인해주세요.</p>
                        	
                        	<div class="btns-area">
								<button type="button" class="btn-b01 round01 btn-color03 left" onclick="location.href='${contextPath}/web/bsisCnsl/list.do?mId=56'">
									<span>협약기업 조회하기</span>
									<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" />
								</button>
							</div>
                        </c:if>
						
						<c:if test="${not empty list && userType ne 10 && userType ne 20}">
							<div class="btns-area">
								<button type="button" class="btn-b01 round01 btn-color03 left" id="btn-qustnr">
									<span>신청하기</span>
									<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" />
								</button>
							</div>
						</c:if>
						
						<c:if test="${empty list && userType ne 20}">
							<p class="center word-type01 mb10"><strong class="point-color08">기초진단</strong> 후 기업HRD이음컨설팅을 신청할 수 있습니다.</p>
                        	
                        	<div class="btns-area">
								<button type="button" class="btn-b01 round01 btn-color03 left" onclick="location.href='${contextPath}/web/diagnosis/apply.do?mId=53'">
									<span>기초진단 이동</span>
									<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" />
								</button>
							</div>
						</c:if>
					</div>
	
					<!-- //CMS 끝 -->
	
				</div>
			</div>
		</article>
	</div>
	<!-- //contents  -->
	
	<form action="" method="post" style="display: none;" id="form-box">
		<input type="hidden" name="bsc" id="bsc" value="" />
	</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include	page="${BOTTOM_PAGE}" flush="false" /></c:if>
		
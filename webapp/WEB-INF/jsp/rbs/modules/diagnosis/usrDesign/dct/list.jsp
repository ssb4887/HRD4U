<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
	
	/* modal table style */
	.table-container {
		min-height: 300px;
		max-height: calc(100vh - 300px);
		overflow: auto;
	}
	
	.modal-table {
		table-layout: fixed;
	}
	
	.modal-thead {
		position: sticky;
		top:0;
		z-index:1;
	}
</style>

                <div class="contents-wrapper">
                    <!-- CMS 시작 -->
                    <div class="contents-area02">
                        <form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
                        <input type="hidden" name="mId" value="36">
                        <input type="hidden" class="siteId" name="siteId" value="${siteId }">
                            <fieldset>
                                <legend class="blind">
                                    기초진단관리 검색양식
                                </legend>
                                <div class="basic-search-wrapper">

                                    <div class="one-box">
                                        <div class="half-box">
                                            <dl>
                                                <dt>
                                                   <label for="is_issueNo">
                                                       발급번호
                                                   </label>
                                                </dt>
                                               <dd>
                                                   <input type="text" id="is_issueNo" name="is_issueNo" value="" title="발급번호 입력" placeholder="발급번호" />
                                               </dd>
                                           </dl>
                                       </div>
                                       <div class="half-box">
                                           <dl>
                                               <dt>
                                                    <label for="is_corpName">
                                                        기업명
                                                    </label>
                                                 </dt>
                                               <dd>
                                                   <input type="text" id="is_corpName" name="is_corpName" value="" title="기업명 입력" placeholder="기업명" />
                                               </dd>
                                           </dl>
                                       </div>
                                   </div>

                                   <div class="one-box">
                                       <div class="half-box">
                                           <dl>
                                               <dt>
                                                    <label for="is_bplNo">
                                                        사업장관리번호
                                                    </label>
                                                </dt>
                                               <dd>
                                                   <input type="text" id="is_bplNo" name="is_bplNo" value="" title="사업장관리번호 입력" placeholder="사업장관리번호" />
                                               </dd>
                                           </dl>
                                       </div>
                                      <div class="half-box">
                                          <dl>
                                              <dt>
	                                              <label for="is_bsisStatus">
	                                                  기업HRD이음컨설팅 여부
	                                              </label>
                                              </dt>
                                              <dd>
													<select id="is_bsisStatus" name="is_bsisStatus" >
														<option value="">선택</option>
														<option value="Y">완료</option>
														<option value="N">미완료</option>
													</select>
                                              </dd>
                                          </dl>
                                      </div>
                                   </div>

                                   <div class="one-box">
                                       <div class="half-box">
                                           <dl>
                                               <dt>
                                                    <label>
                                          					진단일
                                                    </label>
                                                </dt>
                                               <dd>
                                                   <div class="input-calendar-wrapper">
                                                       <div class="input-calendar-area">
                                                           <input type="text" id="is_issueDate1" name="is_issueDate1" class="sdate" title="기준 시작일 입력" placeholder="기준 시작일" autocomplete="off" />
                                                       </div>
                                                       <div class="word-unit">
                                                           ~
                                                       </div>
                                                       <div class="input-calendar-area">
                                                           <input type="text" id="is_issueDate2" name="is_issueDate2" class="edate" title="기준 시작일 입력" placeholder="기준 종료일" autocomplete="off" />
                                                       </div>
                                                   </div>
                                               </dd>
                                           </dl>
                                       </div>
                                       <c:if test="${usertype ge 40}">
											<div class="half-box">
												<dl>
													<dt>
														<label>HRD기초진단<br/>소속기관</label>
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
                             		<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
	                                    <img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" />
	                                    <strong>검색</strong>
                               		</button>
									<button type="button" class="btn-search02" id="btn-init" style="margin-left: 15px;">
										<strong>초기화</strong>
									</button>							
                                </div>
                                       
		                        </fieldset>
		                    </form>
		                </div>
						<!-- //search -->
						
						<div class="title-wrapper">
							<div class="fl"><p class="total">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p></div>
						
							<div class="fr">
								<a href="javascript:void(0);" class="btn-m01 btn-color03" id="btn-excel" style="margin-right: 10px;">엑셀다운로드</a>
								<a href="#none" class="btn-m01 btn-color01" id="open-modal01">
					                <!-- 클릭 시 모달창 오픈 (기초진단 실행) -->
					                등록
					            </a>
							</div>
						</div>
						
						<div class="table-type01 horizontal-scroll">
<%-- 							<form id="${listFormId}" name="${listFormId}" method="post" target="list_target"> --%>
						<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
							<caption>
				                HRD기초진단관리표 : 번호, 발급번호, 기업명, 사업장관리번호, HRD기초진단, HRD기초진단지 출력, 기업HRD이음컨설팅에 관한 정보 제공표
				            </caption>
				            <colgroup>
				                <col style="width: 7%" />
				                <col style="width: 12%" />
				                <col style="width: 10%" />
				                <col style="width: 15%" />
				                <col style="width: 15%" />
				                <col style="width: 13%" />
				                <col style="width: 15%" />
				                <col style="width: 10%" />
				                <col style="width: 12%" />
				                <col style="width: 15%" />
				            </colgroup>
							<thead>
							<tr>
								<th scope="col">No</th>
								<th scope="col"><itui:objectItemName itemId="insttName" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="docName" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="issueNo" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="corpName" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="issueDate" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="ftfYn" itemInfo="${itemInfo}"/></th>
								<th scope="col">HRD기초진단지 출력</th>
								<th scope="col"><itui:objectItemName itemId="bsisStatus" itemInfo="${itemInfo}"/></th>
							</tr>
							</thead>
							<tbody class="alignC">
								<c:if test="${empty list}">
								<tr>
									<td colspan="10" class="bllist"><spring:message code="message.no.list"/></td>
								</tr>
								</c:if>
								
								<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
								<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
								
								<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
								<c:forEach var="listDt" items="${list}" varStatus="i">
								<c:set var="listKey" value="${listDt[listColumnName]}"/>
								
								<tr>
									<td class="num">${listNo}</td>
									<td><itui:objectView itemId="insttName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									<td><itui:objectView itemId="docName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									<td><itui:objectView itemId="issueNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									<td><itui:objectView itemId="corpName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									<td><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									
									<!-- 기초진단 -->
									<td class="date">
										<a href="javascript:void(0);" class="btn-idx" data-idx="${listDt.BSC_IDX}">
											<strong class="point-color01">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.ISSUE_DATE}" />
											</strong>
										</a> 
										<a href="javascript:void(0);" class="btn-linked btn-idx"  data-idx="${listDt.BSC_IDX}">
											<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="HRD기초진단 상세보기">
										</a>
									</td>
									
									<c:choose>
										<c:when test="${listDt.FTF_YN eq 'Y' }">
											<td>대면</td>
										</c:when>
										<c:otherwise>
											<td>비대면</td>
										</c:otherwise>
									</c:choose>
                                                 
									<!-- 기초진단 Report -->
									<td>
										<a href="<c:out value="${URL_CLIPREPORT}&bscIdx=${listKey }"/>" target="_blank">
											<button type="button" class="btn-m02 btn-color02 w100">출력하기</button>
										</a>
									</td>
									
									<!-- 기업HRD이음컨설팅 -->
									<c:choose>
									<c:when test="${listDt.BSIS_STATUS eq 1}">
										<td>
											<a href="javascript:void(0);" class="btn-cnslt" data-rslt="${listDt.BSIS_RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
												<strong class="point-color01">
													<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BSIS_ISSUE_DATE}"/>
												</strong>
											</a>
											<a href="javascript:void(0);" class="fn_btn_view btn-linked btn-cnslt" data-rslt="${listDt.BSIS_RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
												<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="기초컨설팅 상세보기">
											</a>
										</td>
									</c:when>
									<c:when test="${listDt.BSIS_STATUS eq 0}">
										<td>
											<button type="button" class="btn-m02 btn-color01 w100 btn-cnslt" data-rslt="${listDt.BSIS_RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
					                             <span>수정하기</span>
				                             </button>
										</td>
									</c:when>
									<c:when test="${empty listDt.BSIS_STATUS && listDt.QUSTNR_STATUS eq 1}">
										<td>
											<button type="button" class="btn-m02 btn-color03 w100 btn-cnslt" data-rslt="${listDt.RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
					                             <span>신청하기</span>
				                             </button>
										</td>
									</c:when>
									<c:when test="${empty listDt.QUSTNR_STATUS}">
										<td>
											<button type="button" class="btn-m02 btn-color03 w100 btn-qustnr" data-bsc="${listDt.BSC_IDX}">
					                             <span>작성하기</span>
				                             </button>
										</td>
									</c:when>
									<c:when test="${listDt.QUSTNR_STATUS eq 0}">
										<td>
											<button type="button" class="btn-m02 btn-color01 w100 btn-qustnr" data-rslt="${listDt.RSLT_IDX}" data-bsc="${listDt.BSC_IDX}">
												<span>설문조사 수정하기</span>
											</button>
										</td>
									</c:when>
									<c:otherwise>
										<td>
											<button type="button" class="btn-m02 btn-color03 w100 btn-qustnr" data-bsc="${listDt.BSC_IDX}">
												<span>작성하기</span>
											</button>
										</td>
									</c:otherwise>
									</c:choose>
								</tr>
								<c:set var="listNo" value="${listNo - 1}"/>
								</c:forEach>
							</tbody>
						</table>
<!-- 							</form> -->
						</div>
						
						<!-- 페이지 내비게이션 -->
						<div class="paging-navigation-wrapper">
							<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
						</div>
						<!-- //페이지 내비게이션 -->
				
					<!-- //CMS 끝 -->
					
					</div>
				</div>
			</article>
                 <!-- //contents  -->
            </div>
		</div>
	</section>
		
	<!-- 기업바구니 모달 창 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-action01" >
        <h2>
            HRD기초진단 실행
        </h2>
        <div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
<!--             <form action="/recommend/basic2.do" method="POST"> -->
				<!-- 검색존 -->
                <div class="contents-box pl0">
	                    <div class="basic-search-wrapper">
	                        <div class="one-box">
	                            <dl>
	                            <dt>
	                                <label for="modal-textfield01">
	                                    업체명
	                                </label>
	                            </dt>
	                                <dd>
	                                    <input type="text" id="modal-textfield01" class="bplNm" value="" title="업체명 입력" placeholder="업체명" />
	                                </dd>
	                            </dl>
	                        </div>
	                        <div class="one-box">
	                            <dl>
	                                <dt>
	                                <label for="modal-textfield02">
	                                    사업장 관리번호
	                                </label>
	                            </dt>
	                                <dd>
	                                    <input type="text" id="modal-textfield02" class="bplNo" value="" title="사업장 관리번호 입력" placeholder="사업장 관리번호" />
	                                </dd>
	                            </dl>
	                        </div>
	                    </div>

	                    <div class="btns-area">
	                        <button type="button" class="btn-m02 btn-color02 round01"  onclick="searchBtn();">
	                            검색
	                        </button>
	                    </div>
                </div>
				<!-- 검색존 -->
				
				<!-- 기업바구니 검색 결과 목록 -->
                <div class="contents-box pl0">
                    <p class="total mb05">
                        총 <strong id="cnt">0</strong> 건 ( 1 / 1 페이지 )
                    </p>

                    <div class="table-type01 horizontal-scroll table-container">
                        <table class="width-type03 modal-table">
                            <caption>
                                업체정보표 : 번호, 선택, 업체명, 사업장번호, 소속기관에 관한 정보 제공표
                            </caption>
                            <colgroup>
                                <col style="width: 10%" />
                                <col style="width: 15%" />
                                <col style="width: 25%" />
                                <col style="width: 25%" />
                                <col style="width: 25%" />
                            </colgroup>
                            <thead class="modal-thead">
                                <tr>
								<th scope="col">번호</th>
								<th scope="col">선택</th>
								<th scope="col">업체명</th>
								<th scope="col">사업장관리번호</th>
								<th scope="col">소속기관</th>
							</tr>
                            </thead>
                            <tbody id="searchBskList">
                            	<tr id="empty" >
                            		<td colspan="5">검색된 기업이 없습니다. </td>
                           		</tr>
                           	</tbody>
                        </table>
                    </div>
                    
                     <p class="paging-navigation mt20" id="pagnationBox"></p>
                </div>
  				<!-- 기업바구니 검색 결과 목록 -->

  			<!-- 기업담당자 정보 조회 -->	
			<div id="infoBox"></div>
  			<!-- 기업담당자 정보 조회 -->	
  			
        </div>


        <button type="button" class="btn-modal-close">
            모달 창 닫기
        </button>
    </div>
    
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="rslt" id="rslt" value="" />
	<input type="hidden" name="bsc" id="bsc" value="" />
	<input type="hidden" name="idx" id="idx" value="" />
</form>

<c:if test="${login eq false}">
	<script>
		alert("로그인이 필요합니다.");
		window.location.href = `${contextPath}/dct/login/login.do?mId=3`;
	</script>
</c:if>
<script src="${contextPath}/dct/js/diagnosis/diagnosis.js"></script>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
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

                <div class="contents-wrapper">
                    <!-- CMS 시작 -->
                    <div class="contents-area02">
                        <form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
<!--                         <form action="supportList.do?mId=117" method="GET" > -->
                        <input type="hidden" name="mId" value="117">
                        <input type="hidden" class="siteId" name="siteId" value="${siteId }">
                            <fieldset>
                                <legend class="blind">
                   					서식그룹관리 검색양식
                                </legend>
                                <div class="basic-search-wrapper">
                                    <div class="one-box">
	                                    <div class="half-box">
	                                           <dl>
	                                               <dt>
	                                                    <labe id="is_regiDate">
	                                          					등록일
	                                                    </label>
	                                                </dt>
	                                               <dd>
	                                                   <div class="input-calendar-wrapper">
	                                                       <div class="input-calendar-area">
	                                                           <input type="text" id="is_regiDate1" name="is_regiDate1" class="sdate" title="기준 시작일 입력" placeholder="기준 시작일" />
	                                                       </div>
	                                                       <div class="word-unit">
	                                                           ~
	                                                       </div>
	                                                       <div class="input-calendar-area">
	                                                           <input type="text" id="is_regiDate2" name="is_regiDate2" class="edate" title="기준 시작일 입력" placeholder="기준 종료일" />
	                                                       </div>
	                                                   </div>
	                                               </dd>
	                                           </dl>
	                                       </div>
	                                       <div class="half-box">
	                                           <dl>
	                                              <dt>
		                                              <label for="is_sptjName">
	                                   						키워드 검색
		                                              </label>
	                                             </dt>
	                                               <dd>
	                                                   <input type="text" id="is_sptjName" name="is_sptjName" value="" title="검색어 입력" placeholder="검색어를 입력하세요." />
	                                               </dd>
	                                           </dl>
	                                       </div>
                                   </div>
                                </div>
                                
                                <div class="btns-area">
                                    <button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
                                         <img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" />
                                         <strong>검색</strong>
                                     </button>
                                </div>
                            </fieldset>
                        </form>
                    </div>
					<!-- //search -->
						
						<div class="title-wrapper">
							<p class="total fl">
								총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
							</p>
						</div>
						<div class="table-type01 horizontal-scroll">
<%-- 							<form id="${listFormId}" name="${listFormId}" method="post" target="list_target"> --%>
						<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
                            <caption>
                                서식그룹관리 표 : 번호, 지원업무명, 참조 요구서식, 등록일, 사용여부에 관한 정보 제공표
                            </caption>
                            <colgroup>
	                            <col style="width: 10%" />
	                            <col style="width: 40%" />
	                            <col style="width: 15%" />
	                            <col style="width: 15%" />
	                            <col style="width: 10%" />
                            </colgroup>
							<thead>
							<tr>
								<th scope="col">No</th>
								<th scope="col">서식그룹명</th>
								<th scope="col">참조 서식부수</th>
								<th scope="col">등록일</th>
								<th scope="col">방문기업관리</th>
							</tr>
							</thead>
							<tbody class="alignC">
								<c:if test="${empty list}">
								<tr>
									<td colspan="5" class="bllist"><spring:message code="message.no.list"/></td>
								</tr>
								</c:if>
								
								<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
								<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
								
								<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
								<c:forEach var="listDt" items="${list}" varStatus="i">
								<c:set var="listKey" value="${listDt[listColumnName]}"/>
								
								<tr>
									<td class="num">${listNo}</td>
									<td>
										<strong class="point-color01">
											<c:out value="${listDt.SPTJ_NAME }"/>
										</strong>
<!-- 										<a href="#" class="btn-linked"> -->
<%--                                            <img src="${pageContext.request.contextPath}/pub/img/icon/icon_search04.png" alt=""> --%>
<!--                                        </a> -->
									</td>
									<td><c:out value="${listDt.ADMSFMCOUNT }"/>부</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
									<td>
										<a href="javascript:void(0);" class="btn-m02 btn-color03 w100 btn-select" data-idx="${listDt.SPTJ_IDX}">
											<span>작성하기</span>
										</a>
									</td>
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
	
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="bplNo" id="bplNo" value="" />
	<input type="hidden" name="sptjIdx" id="idx" value="" />
</form>

<script src="${contextPath}/dct/js/busisSelect/supportList.js"></script>
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
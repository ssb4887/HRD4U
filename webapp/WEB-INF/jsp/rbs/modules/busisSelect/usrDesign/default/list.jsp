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
                        <input type="hidden" name="mId" value="118">
                        <input type="hidden" class="siteId" name="siteId" value="${siteId }">
                            <fieldset>
                                <legend class="blind">
                   					방문기업관리 이력 검색양식
                                </legend>
                                <div class="basic-search-wrapper">
                                    <div class="one-box">
										<div class="half-box">
											<dl>
												<dt>
													<label for="is_bplNm"> 
														기업명 검색 
													</label>
												</dt>
												<dd>
													<input type="text" id="is_bplNm" name="is_bplNm" value="" title="기업명 입력" placeholder="기업명을 입력하세요." />
												</dd>
											</dl>
										</div>
										<div class="half-box">
											<dl>
												<dt>
													<label for="is_sptjName"> 서식그룹명 검색 </label>
												</dt>
												<dd>
													<input type="text" id="is_sptjName" name="is_sptjName" value="" title="서식그룹명 입력" placeholder="서식그룹명을 입력하세요." />
												</dd>
											</dl>
										</div>
									</div>
                                   <div class="one-box">
                                       <div class="half-box">
                                           <dl>
                                               <dt>
                                                    <label for="is_regiDate">
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
                                  </div>
                                </div>
                                
                                <div class="btns-area">
                                    <button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
                                         <img src="${contextPath}/pub/img/icon/icon_search03.png" alt="" />
                                         <strong>
                             				검색
                                         </strong>
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
								<a href="javascript:void(0);" class="btn-m01 btn-color03" id="btn-excel">엑셀다운로드</a>
							</div>
						</div>
						
						<div class="table-type01 horizontal-scroll">
<%-- 							<form id="${listFormId}" name="${listFormId}" method="post" target="list_target"> --%>
						<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
                             <caption>
                                 방문기업관리 이력 표 : 번호, 방문기업 문서명, 참조서식부수, 기업명, 등록일, PDF출력에 관한 정보 제공표
                             </caption>
	                             <colgroup>
	                                 <col style="width: 10%" />
	                                 <col style="width: 30%" />
	                                 <col style="width: 30%" />
	                                 <col style="width: 15%" />
	                                 <col style="width: 15%" />
	                                 <col style="width: 25%" />
	                                 <col style="width: 15%" />
	                             </colgroup>
							<thead>
							<tr>
								<th scope="col">No</th>
								<th scope="col"><itui:objectItemName itemId="bplNm" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="sptjName" itemInfo="${itemInfo}"/></th>
								<th scope="col">참조 서식부수</th>
								<th scope="col"><itui:objectItemName itemId="regiName" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="regiDate" itemInfo="${itemInfo}"/></th>
								<th scope="col">PDF 파일 다운로드</th>
							</tr>
							</thead>
							<tbody class="alignC">
								<c:if test="${empty list}">
								<tr>
									<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
								</tr>
								</c:if>
								
								<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
								<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
								
								<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
								<c:forEach var="listDt" items="${list}" varStatus="i">
								<c:set var="listKey" value="${listDt[listColumnName]}"/>
<%-- 								<input type="hidden" id="sptdgnsIdx" name="sptdgnsIdx" value="${listDt.SPTDGNS_IDX }"/> --%>
								
								<tr>
									<td class="num">${listNo}</td>
									<td><itui:objectView itemId="bplNm" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									<td>
									<c:choose>
										<c:when test="${listDt.TMPR_SAVE_YN eq 'N'}"> 
										<a href="javascript:void(0);" class="btn-idx" data-idx="${listDt.SPTDGNS_IDX}" data-bsk="${listDt.BPL_NO}" data-sptj="${listDt.SPTJ_IDX }">
<!-- 											<strong class="point-color01"> -->
												<c:out value="${listDt.SPTJ_NAME }"/>
<!-- 											</strong> -->
										</a>
                                        </c:when>
                                        <c:otherwise>
                                       		<c:out value="${listDt.SPTJ_NAME }"/>
										</c:otherwise>
									</c:choose>
									</td>
									<td><c:out value="${listDt.ADMSFMCOUNT }"/>부</td>
									<td><itui:objectView itemId="regiName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
									<c:choose>
										<c:when test="${listDt.TMPR_SAVE_YN eq 'N'}"> 
											<td class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}" /></td>
										</c:when>
										<c:otherwise>
											<td>
												<button type="button" class="btn-m02 btn-color03 btn-input" data-tmp="${listDt.SPTDGNS_IDX}" data-bsk="${listDt.BPL_NO}" data-sptj="${listDt.SPTJ_IDX }">
													수정
												</button>
											</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${listDt.TMPR_SAVE_YN eq 'N'}"> 
											<td>
												<button type="button" class="btn-m02 btn-color02 w100" onclick="pdf(${listDt.SPTDGNS_IDX });">
													다운로드
												</button>
											</td>
										</c:when>
										<c:otherwise>
											<td>
												<button type="button" class="btn-m02 btn-color06 w100">
													다운로드
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
	
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
	<input type="hidden" name="bplNo" id="bsk" value="" />
	<input type="hidden" name="sptjIdx" id="sptjIdx" value="" />
</form>
<script src="${contextPath}/dct/js/busisSelect/list.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
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
	                        <input type="hidden" name="mId" value="117">
	                        <input type="hidden" class="siteId" name="siteId" value="${siteId }">
								<legend class="blind">
									기업 검색양식
								</legend>
								<div class="basic-search-wrapper">

									<div class="one-box">
										<div class="half-box">
											<dl>
												<dt>
													<label for="is_bplNm">
														기업명
													</label>
												</dt>
												<dd>
													<input type="text" id="is_bplNm" name="is_bplNm" value="" title="기업명 입력" placeholder="기업명">
												</dd>
											</dl>
										</div>
										<div class="half-box">
											<dl>
												<dt>
													<label for="is_industCode">
														업종
													</label>
												</dt>
												<dd>
													<select id="is_indutyCd" name="is_indutyCd"  title="업종선택">
														<option value="">업종선택</option>
							                            <c:forEach var="item" items="${indutyCd }">
															<option value="${item.CODE }"><c:out value="${item.NAME }"/></option>
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
													<label for="is_bplNo">
														사업장관리번호
													</label>
												</dt>
												<dd>
													<input type="text" id="is_bplNo" name="is_bplNo" value="" title="사업장관리번호 입력" placeholder="사업장관리번호">
												</dd>
											</dl>
										</div>
										<div class="half-box">
											<dl>
												<dt>
													<label for="is_bizrNo">
														사업자등록번호
													</label>
												</dt>
												<dd>
													<input type="text" id="is_bizrNo" name="is_bizrNo" value="" title="사업자등록번호 입력" placeholder="사업자등록번호">
												</dd>
											</dl>
										</div>
									</div>
									
									<div class="one-box">
										<c:if test="${userTypeIdx ge 40  }">
											<div class="half-box">
												<dl>
													<dt>
														<label for="is_insttIdx">
															소속기관
														</label>
													</dt>
													<dd>
														<select id="is_insttIdx" name="is_insttIdx">
															<option value="">소속기관 선택</option>
								                            <c:forEach var="item" items="${insttList }">
																<option value="${item.CODE }"><c:out value="${item.NAME }"/></option>
								                            </c:forEach>
														</select>
													</dd>
												</dl>
											</div>
										</c:if>
										<div class="half-box">
											<dl>
												<dt>
													<label for="is_headbplCd">
														본사/지사 구분
													</label>
												</dt>
												<dd>
													<select id="is_headbplCd" name="is_headbplCd">
														<option value="">본사/지사 선택</option>
														<option value="1">본사</option>
														<option value="2">지사</option>
													</select>
												</dd>
											</dl>
										</div>
									</div>
								</div>
								
								<div class="btns-area">
									<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
										<img src="${contextPath}/pub/img/icon/icon_search03.png" alt="" />
										<strong>검색</strong>
									</button>
									<button type="button" class="btn-search02" id="btn-init" style="margin-left: 15px;">
										<strong>초기화</strong>
									</button>	
								</div>
							</form>
						</div>
				
						<!-- 목록표시 -->
						<div id="resultForm">
							<form id="formBox" action="${contextPath}/dct/busisSelect/supportList.do?mId=117" method="post">	
							<div class="title-wrapper">
	                           	<p class="total fl ">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
									<div class="fr">
		                               <button type="button" class="btn-m01 btn-color01" onclick="apply();">
	                                  		실행
		                               </button>
		                           </div>
	                       </div>
						
							<div class="table-type01 horizontal-scroll">
	                        <table>
	                            <caption>
	                                기업 표 : 선택, 사업장관리번호, 기업명, 소속기관, 대분류, 소분류, 본사 구분에 관한 정보 제공표
	                            </caption>
	                            <colgroup>
	                                <col style="width: 7%" />
	                                <col style="width: 15%" />
	                                <col style="width: 15%" />
	                                <col style="width: 18%" />
	                                <col style="width: 15%" />
	                                <col style="width: 10%" />
	                                <col style="width: 25%" />
	                               </colgroup>
	                               <thead>
	                                   <tr>
											<th scope="col">선택</th>
											<th scope="col"><itui:objectItemName itemId="bplNo" itemInfo="${itemInfo}"/></th>
											<th scope="col"><itui:objectItemName itemId="bizrNo" itemInfo="${itemInfo}"/></th>
											<th scope="col"><itui:objectItemName itemId="bplNm" itemInfo="${itemInfo}"/></th>
											<th scope="col"><itui:objectItemName itemId="insttIdx" itemInfo="${itemInfo}"/></th>
											<th scope="col"><itui:objectItemName itemId="headbplCd" itemInfo="${itemInfo}"/></th>
											<th scope="col"><itui:objectItemName itemId="indutyCd" itemInfo="${itemInfo}"/></th>
	                                   </tr>
	                               </thead>
	                               <tbody id="searchBskList">
	                               	<c:if test="${empty list }">
										<tr><td colspan="7">검색된 기업이 없습니다. </td></tr>
									</c:if>
									
									<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
									<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
									
									<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
									<c:forEach var="listDt" items="${list}" varStatus="i">
									<c:set var="listKey" value="${listDt[listColumnName]}"/>
								
                                   	<tr>
                                        <td>
                                           <input type="checkbox" id="checkbox_${listNo }" class="checkbox-type01" name="selectCheckbox" value="${listDt.BPL_NO }" onclick="handleCheck(${listNo});">
                                        </td>
										<td><c:out value="${listDt.BPL_NO }"/></td>
										<td><c:out value="${listDt.BIZR_NO }"/></td>
                                        <td>
	                                        <a href="javascript:void(0);" class="btn-idx" data-idx="${listDt.BPL_NO}">
	                                      		<strong class="point-color01">
	                                      			<c:out value="${listDt.BPL_NM }"/>
	                                    		</strong>
                                    		</a>
                                        </td>
										<td><c:out value="${listDt.INSTT_NAME }"/></td>
										<td>
											<c:if test="${listDt.HEADBPL_CD eq '1'}">본사</c:if>
											<c:if test="${listDt.HEADBPL_CD eq '2'}">지사</c:if>
										</td>
										<td>
											<input type="hidden" value="${listDt.INDUTY_CD }">
											<c:out value="${listDt.NAME }"/>
										</td>
									</tr>
									<c:set var="listNo" value="${listNo - 1}"/>
									</c:forEach>
	                               </tbody>
	                           </table>
                           </form>
	                       </div>

							<!-- //페이지 내비게이션 -->
							<div class="paging-navigation-wrapper">
								<pgui:pagination listUrl="${URL_BSK}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
							</div>
							<!-- //페이지 내비게이션 -->
                       </div>

						<!-- //CMS 끝 -->
					</div>
				</div>
			</article>
		</div>
		<!-- //contents  -->

	</div>
</section>

<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="bplNo" id="bplNo" value="" />
</form>
	
<script src="${contextPath}/dct/js/busisSelect/bskList.js"></script>	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
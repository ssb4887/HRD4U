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

		              <div class="contents-wrapper">
		
		                  <!-- CMS 시작 -->
		                  <form action="" method="">
		                  <c:set var="sptdgnsIdx" value="${publishList.SPTDGNS_IDX  }"/>
		                      <legend class="blind">방문기업관리 이력</legend>
		                      <div class="contents-area">
		                          <div class="contents-box pl0">
		                              <div class="board-write">
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
		                                   					서식그룹명
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                              <c:out value="${sptjList.SPTJ_NAME }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
														방문기업서식
		                                          </dt>
		                                          <dd>
		                                              <ul class="ul-list02">
														<c:forEach var="sptjNames" items="${sptjNmList }">
															<c:set var="sptjList" value="${fn:split(sptjNames, ',') }"/>
															<c:forEach var="item" items="${sptjList }">
																<li>
																	<c:out value="${item }"/><br/>
																</li>				
															</c:forEach>																
														</c:forEach>
		                                              </ul>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                              </div>
		                          </div>
		
		                          <div class="contents-box pl0">
		                              <div class="board-write">
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                                기업명
		                                              </label>
		                                          </dt>
		                                          <dd>
                                      					<c:out value="${bplList.BPL_NM }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                         					사업장관리번호
		                                              </label>
		                                          </dt>
		                                          <dd>
	                                              		<c:out value="${bplList.BPL_NO }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                         					사업자등록번호
		                                              </label>
		                                          </dt>
		                                          <dd>
	                                              		<c:out value="${bplList.BIZR_NO }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                       						소재지
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                              <c:out value="${bplList.CORP_LOCATION }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                    						업종
		                                              </label>
		                                          </dt>
		                                          <dd>
                         			    				<c:out value="${bplList.INDUTY_NAME }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                          					지부지사
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                              <c:out value="${bplList.INSTT_NAME }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                        					대분류
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                          <c:set var="LCLAS_NM" value="${pLSclas.LCLAS_NM eq null? aLSclas.LCLAS_NM : pLSclas.LCLAS_NM}"/>
		                                          	<c:out value="${LCLAS_NM }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                        					소분류
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                          <c:set var="SCLAS_NM" value="${pLSclas.SCLAS_NM eq null? aLSclas.SCLAS_NM : pLSclas.SCLAS_NM}"/>
		                                          	<c:out value="${SCLAS_NM }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                         					훈련이력
		                                              </label>
		                                          </dt>
		                                          <dd>
	                                          			<c:out value="${trList.PROGRAM }"/>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
                                       						고용상시인원
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                              <c:out value="${bplList.TOT_WORK_CNT }"/>명
		                                          </dd>
		                                      </dl>
		                                  </div>
		
		                              </div>
		                          </div>
		                          
  		                          <div class="contents-box pl0">
		                              <div class="board-write">
		                                  <div class="one-box">
		                                      <dl>
		                                          <dt>
		                                              <label>
		                                   					최종 등록자 정보
		                                              </label>
		                                          </dt>
		                                          <dd>
						                            <ul class="ul-list02">
						                                <li>
						                                    <strong>소속부서</strong>
						                                    <c:out value="${publishList.DOCTOR_DEPT_NAME}"/>
						                                </li>
						                                <li>
						                                    <strong>등록자명</strong>
						                                    <c:out value="${publishList.LAST_MODI_NAME}"/>
						                                </li>
						                            </ul>
		                                          </dd>
		                                      </dl>
		                                  </div>
		                                  <div>
		                                  	<dl>
		                                          <dt>
		                                              <label>
		                                   					최종 등록일
		                                              </label>
		                                          </dt>
		                                          <dd>
		                                          	<fmt:formatDate pattern="yyyy-MM-dd" value="${publishList.LAST_MODI_DATE}" />
		                                          </dd>
		                                  </div>
		                              </div>
		                          </div>
		                          
                                   	<div class="btns-area">
                                        <div class="btns-right">
											<button type="button" class="btn-m01 btn-color03 depth6" onclick="busisSelectView(${sptdgnsIdx });">
                          						문서 상세조회
                                            </button>
											<button type="button" class="btn-m01 btn-color02 depth6" onclick="pdf(${sptdgnsIdx });">
                                       			PDF 파일 다운로드
                                            </button>
                                            <a href="${URL_LIST }" class="btn-m01 btn-color01 depth6">
                                				목록
                                            </a>
                                        </div>
                                    </div>
                                </form>
                                <!-- //CMS 끝 -->
                            </div>
                        </div>
                    </article>
                </div>
                <!-- //contents  -->

            </div>
        </section>
        <!-- //container -->
<script src="${contextPath}/dct/js/busisSelect/view.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
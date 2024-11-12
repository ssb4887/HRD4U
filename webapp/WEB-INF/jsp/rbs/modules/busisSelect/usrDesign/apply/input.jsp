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
		                  <form id="inputForm" name="inputForm">
		                  <input type="hidden" id="sptdgnsIdx" name="sptdgnsIdx" value="${sptdgnsIdx }"/>
		                  <input type="hidden" name="sptjIdx" value="${sptjIdx}"/>
		                  <input type="hidden" name="bplNo" value="${bplNo }"/>
		                  <input type="hidden" id="reqSetKeyList" name="reqSetKeyList" value="${reqSetKeyList }"/>
		                  <input type="hidden" id="publishedYn" name="publishedYn" value="${publishedYn }"/>
		                      <legend class="blind">방문기업관리 이력 등록</legend>
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
														요구서식
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
		                          
                                   	<div class="btns-area">
                                        <div class="btns-right">
                                        <c:choose>
                                        	<c:when test="${publishedYn eq 'Y' }">
												<button type="button" class="apply btn-m01 btn-color03 depth6" onclick="busisSelect();">
	                                    			 	문서 작성
	                                            </button>
                                        	</c:when>
                                        	<c:otherwise>
												<button type="button" class="publish btn-m01 btn-color05 depth6 open-modal01">
	                                    			 	문서 생성
	                                            </button>
                                        	</c:otherwise>
                                        </c:choose>
											<button type="button" id="doneBtn" class="btn-m01 btn-color02 depth6 open-modal02">
                                      				완료
                                            </button>
                                            <c:choose>
                                            	<c:when test="${not empty sptdgnsIdx }">
		                                            <a href="list.do?mId=118" class="btn-m01 btn-color01 depth6">
                                          					목록
		                                            </a>
                                            	</c:when>
                                            	<c:otherwise>
		                                            <a href="${URL_BSK }" class="btn-m01 btn-color01 depth6">
                                        					목록
		                                            </a>
                                            	</c:otherwise>
                                            </c:choose>
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
        
  	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-alert01">
        <h2>
            알림
        </h2>
        <input type="hidden" id="actionValue" value=""/>
        <div class="modal-area">
            <div class="modal-alert">
                <p id="comment"></p>
                <p class="ul-list02 point-color04">확인버튼 클릭시 요청하신 작업이 실행됩니다.</p>
            </div>

            <div class="btns-area">
                <button type="button" class="btn-m02 round01 btn-color03" onclick="save();">
                    <span>
                        확인
                    </span>
                </button>

                <button type="button" class="btn-m02 round01 btn-color02 modal-close">
                    <span>
                        취소
                    </span>
                </button>
            </div>
        </div>

        <button type="button" class="btn-modal-close">
            모달 창 닫기
        </button>
    </div>
        <!-- //container -->
<script src="${contextPath}/dct/js/busisSelect/input.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
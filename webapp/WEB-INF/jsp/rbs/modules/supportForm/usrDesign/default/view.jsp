<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>

						<div class="contents-wrapper">
								<!-- CMS 시작 -->
			                    <input type="hidden" class="idx" name="idx" value="${idx }">
								<c:set var="listDt" value="${dt }"/>
								<c:set var="listIt" value="${it }"/>
								<div class="contents-area">
	                                <div class="table-type02 horizontal-scroll">
	                                    <table class="width-type02">
	                                        <caption>
                                  				방문기업서식 상세조회표 : 사업구분, 서식 사용여부, 업무 구분, 서명 대상자, 서식명, 서식 원본파일 첨부, 최초 등록자 정보에 관한 정보 제공표
	                                        </caption>
	                                        <colgroup>
	                                            <col width="20%">
	                                            <col width="80%">
	                                        </colgroup>
											<tbody>
												<tr>
													<th scope="row">지원업무명</th>
													<td class="left"><c:out value="${listDt.SPTJ_NAME }" /></td>
												</tr>
							
												<tr>
													<th scope="row">사용여부</th>
													<td class="left" colspan="3">
														<c:if test="${listDt.USE_YN eq 'Y' }">
															<span>사용</span>
														</c:if>
														<c:if test="${listDt.USE_YN eq 'N' }">
															<span>미사용</span>
														</c:if>
													</td>
												</tr>
												<tr>
													<th scope="row" rowspan="11">방문기업서식</th>
												</tr>
												<c:forEach var="item" items="${listIt }" >
												<tr>
													<td class="left"><c:out value="${item.ADMSFM_NM}" /></td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
	                                </div>
								</div>
                                
                                <div class="btns-area mt60">
                                    <div class="btns-right">
                                        <a href="#" class="btn-m01 btn-color03 depth3 btn-input" data-tmp="${idx}">
                                            수정
                                        </a>
                                        <a href="#" class="btn-m01 btn-color02 depth3 open-modal01">
                                            삭제
                                        </a>
                                        <a href="${URL_LIST }" class="btn-m01 btn-color01 depth3">
                                            목록
                                        </a>
                                    </div>
                                </div>
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
        <div class="modal-area">
            <div class="modal-alert">
                <p>
                해당 서식을 삭제하시겠습니까?<br /> 
					삭제 시 복구가 불가능 합니다.
                </p>
            </div>


            <div class="btns-area">
                <button type="button" class="btn-m02 round01 btn-color03" onclick="deleteForm();">
                    <span>
                        삭제
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
        		
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>
<script src="${contextPath}/dct/js/supportForm/view.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
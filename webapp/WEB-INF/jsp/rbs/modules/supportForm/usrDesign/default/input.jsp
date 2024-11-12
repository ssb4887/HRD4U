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
	
/* 	input[readonly] { */
/* 		background-color: #f2f2f2; */
/* 	} */
</style>

<div id="overlay"></div>
<div class="loader"></div>
	
				            <div class="contents-wrapper">
				                <!-- CMS 시작 -->
				                      <div class="title-wrapper">
				                          <div class="fr">
				                              <button type="button" class="btn-m01 btn-color01" onclick="addRow();">
                               						방문기업서식 추가
				                              </button>
				                          </div>
				                      </div>
				                      <form id="supportForm" name="supportForm">
										<input type="hidden" id="mId" value="115"/>
										<input type="hidden" id="idx" name="sptjIdx"  value="${idx }"/>
										<input type="hidden" id="useYn" value="${support.USE_YN }"/>
				                          <legend class="blind">글쓰기</legend>
				
				                          <div class="board-write">
				                              <div class="one-box">
				                                  <dl>
				                                      <dt>
				                                          <label for="sptjNm">
                                           						서식그룹명
				                                          </label>
				                                      </dt>
				                                      <dd>
				                                          <input type="text" id="sptjNm" name="sptjNm" value="${support.SPTJ_NAME }" placeholder="서식그룹명" class="w100" size="100" maxlength="100" >
				                                      </dd>
				                                  </dl>
				                              </div>
				                              <div class="one-box">
				                                  <dl>
				                                      <dt>
				                                          <label for="useYn">
                                       							사용여부
				                                          </label>
				                                      </dt>
				                                      <dd>
				                                          <div class="input-radio-wrapper ratio">
				                                              <div class="input-radio-area">
				                                                  <input type="radio" class="radio-type01" id="radio0101" name="useYn" value="Y">
				                                                  <label for="radio0101">
                                                    					사용
				                                                  </label>
				                                              </div>
				
				                                              <div class="input-radio-area">
				                                                  <input type="radio" class="radio-type01" id="radio0102" name="useYn" value="N">
				                                                  <label for="radio0102">
                                          								미사용
				                                                  </label>
				                                              </div>
				                                          </div>
				                                      </dd>
				                                  </dl>
			                              	</div>
	
											<div class="one-box">
                                       			<dl>
                                           			<dt>
                                                  		<label for="textfield01">
                                    							방문기업서식
                                                   		</label>
                                                  	</dt>
													<dd id="input-area">
													<c:choose>
													<c:when test="${not empty formIdx }">
	                                                	<!-- 고정된 2개의 방문기업서식 항목 -->
	                                                	<c:forEach var="item" items="${formIdx }"  varStatus="status" end="1">
	                                                	<c:set var="idx" value="${status.index+1 }"/>
														<div class="input-add-btns-wrapper03 pd-button mt05">
	                                                    	<input type="hidden" class="hidden-value" name="hidden-value" value="${item.ADMSFM_IDX }" data-idx="${idx }">
	                                                    	<input type="text" class="form" name="form" value="${item.ADMSFM_NM }" data-idx="${idx }" readonly>
		                                                    <div class="btns">
	                                                       		<button type="button" class="btn-m01 btn-color02 open-modal01 w100" onclick="openModal(${idx });">
		                                        					불러오기
		                                                        </button>
		                                                    </div>
		                                                </div>
	                                                	</c:forEach>
	                                                	
	                                                	<!-- 추가된 방문기업서식 항목-->
	                                                	<c:forEach var="item" items="${formIdx }"  varStatus="status" begin="2">
	                                                	<c:set var="idx" value="${status.index+1 }"/>
														<div class="input-add-btns-wrapper03 pd-button mt05 add${idx }">
	                                                    	<input type="hidden" class="hidden-value" name="hidden-value" value="${item.ADMSFM_IDX }" data-idx="${idx }">
	                                                    	<input type="text" class="form" name="form" value="${item.ADMSFM_NM }" data-idx="${idx }" readonly>
		                                                    <div class="btns">
	                                                       		<button type="button" class="btn-m01 btn-color02 open-modal01" onclick="openModal(${idx});">
		                                        					불러오기
		                                                        </button>
		                                                        <button type="button" class="btn-m01 btn-color03" onclick="removeRow(${idx});">
                                                       삭제
                                                       			</button>
		                                                    </div>
		                                                </div>
	                                                	</c:forEach>
	                                                </c:when>
	                                                <c:otherwise>
														<div class="input-add-btns-wrapper03 pd-button">
	                                                    	<input type="hidden" class="hidden-value1" name="hidden-value1" value="" data-idx="1">
	                                                    	<input type="text" class="form1" name="form1" value="" readonly>
		                                                    <div class="btns">
	                                                       		<button type="button" class="btn-m01 btn-color02 open-modal01 w100" onclick="openModal(1);">
		                                        					불러오기
		                                                        </button>
		                                                    </div>
		                                                </div>
														<div class="input-add-btns-wrapper03 pd-button mt05">
	                                                    	<input type="hidden" class="hidden-value2" name="hidden-value2" value="" data-idx="2">
		                                                    <input type="text" class="form2" name="form2" value="" readonly>
		                                                    <div class="btns">
		                                                        <button type="button" class="btn-m01 btn-color02 open-modal01 w100" onclick="openModal(2);">
	                                        						불러오기
		                                                        </button>
		                                                    </div>
	                                                	</div>
	                                                </c:otherwise>
                                               	</c:choose>
	                                            </dd>
	                                        </dl>
	                                    </div>
                                    </div>

                                    <div class="btns-area">
                                        <div class="btns-right">
                                            <button type="button" class="btn-m01 btn-color02 depth3"onclick="save('temp');">
                               						저장
                                            </button>
                                            <button type="button" class="btn-m01 btn-color03 depth3" onclick="save('done');">
                                   					완료
                                            </button>
                                            <a href=" ${URL_LIST }" class="btn-m01 btn-color01 depth3">
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

	<!-- 방문기업서식 불러오기 모듈창 -->
    <div class="mask"></div>
    <div class="modal-wrapper" id="modal-member">
        <h2>
            방문기업서식 불러오기
        </h2>
        <div class="modal-area">
        <form action="" id="search-form" onsubmit="return false;">
            <div class="contents-box pl0">
                <div class="basic-search-wrapper">

                    <div class="one-box">
                        <div class="half-box">
                            <dl>
                                <dt>
                                    <label class="is_prtbizIdx">
                                  사업 구분
                                    </label>
                                </dt>
                                <dd>
									<select id="is_prtbizIdx" name="prtbizIdx" >
										<option value="">사업 구분</option>
			                            <c:forEach var="item" items="${prtbizIdx }">
											<option value="${item.key }"><c:out value="${item.value }"/></option>
			                            </c:forEach>
									</select>
                                </dd>
                            </dl>
                        </div>
                        <div class="half-box">
                            <dl>
                                <dt>
                                    <label for="is_jobType">
                                  업무 구분
                                    </label>
                                </dt>
                                <dd>
									<select id="is_jobType" name="jobType" >
										<option value="">업무 구분</option>
		                            	<c:forEach var="item" items="${jobType }">
											<option value="${item.value }">${item.value }</option>
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
                                    <label class="is_sgntr">
                                 			서명 대상자
                                    </label>
                                </dt>
                                <dd>
									<select id="is_sgntr" name="sgntr" >
										<option value="">서명 대상자</option>
			                            <c:forEach var="item" items="${sgntr }">
											<option value="${item.value }">${item.value }</option>
			                            </c:forEach>
									</select>
                                </dd>
                            </dl>
                        </div>
                        <div class="half-box">
                            <dl>
                                <dt>
                                    <label for="is_admsfmNm">
                   							키워드 검색
                                    </label>
                                </dt>
                                <dd>
                                    <input type="text" id="is_admsfmNm" name="admsfmNm" value="" placeholder="키워드 검색" onkeyup="handleEnterKeyPress(event);">
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>

                <div class="btns-area">
                    <button type="button" class="btn-search02" onclick="searchForm();">
                        <img src="${pageContext.request.contextPath}/pub/img/icon/icon_search03.png" alt="">
                        <strong>
                            검색
                        </strong>
                    </button>
                </div>
            </div>
            </form>

            <div id="contents-box" class="contents-box">
                <div class="title-wrapper">
                    <p class="total fl">
                     총 <strong id="cnt">0</strong> 건
                    </p>
                </div>
                <div class="table-type02 horizontal-scroll table-container">
                    <table class="width-type03 modal-table">
                        <caption>
                            방문기업서식 불러오기 정보표 : 선택, 서식명, 버전, 사업 구분, 업부 구분, 서명대상자에 관한 정보제공표
                        </caption>
                        <colgroup>
                            <col style="width: 8%">
                            <col style="width: 37%">
                            <col style="width: 10%">
                            <col style="width: 15%">
                            <col style="width: 15%">
                            <col style="width: 15%">
                        </colgroup>
                        <thead>
						<tr>
							<th scope="col">선택</th>
							<th scope="col">서식명</th>
							<th scope="col">버전</th>
							<th scope="col">사업 구분</th>
							<th scope="col">업무 구분</th>
							<th scope="col">서명대상자</th>
						</tr>
					</thead>
                        <tbody id="formList">
                          	<tr id="empty" >
                        		<td colspan="6">방문기업서식을 검색해주세요.</td>
                       		</tr>
                        </tbody>
                    </table>
                </div>

				<div class="paging-navigation-wrapper"> </div>
                
				<div class="btns-area">
		                <button type="button" class="btn-m02 round01 btn-color03"  id="ok">
		                    <span>
		               			확인
		                    </span>
		                </button>
		            </div>
		       </div>

        <button type="button" class="btn-modal-close" onclick="closeModal();">
            모달 창 닫기
        </button>
    </div>
<script src="${contextPath}/dct/js/supportForm/input.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
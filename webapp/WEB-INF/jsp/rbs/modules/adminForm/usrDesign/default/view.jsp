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

<div id="overlay"></div>
<div class="loader"></div>

				<div class="contents-wrapper">
					<!-- CMS 시작 -->
					<c:set var="keyItemId" value="${settingInfo.idx_name}"/>
					<c:set var="keyColumnId" value="${settingInfo.idx_column}"/>
					<c:set var="listDt" value="${dt }"/>
					<c:set var="listFt" value="${ft }"/>
					<c:set var="formatId" value="${listDt.ELCTRN_FORMAT_ID  }"/>
                    <input type="hidden" class="idx" name="idx" value="${idx }">
                    <input type="hidden" id="admsfmNm" value="${listDt.ADMSFM_NM }"/>
                    <input type="hidden" id="formatId" value="${formatId }"/>
					<div class="contents-area">
						<div class="table-type02 horizontal-scroll">
							<table class="width-type02">
								<caption>방문기업서식 상세조회표 : 사업구분, 서식 사용여부, 업무 구분, 서명 대상자, 서식명, 서식 원본파일 첨부, 최초 등록자 정보에 관한 정보 제공표</caption>
								<colgroup>
									<col width="15%">
									<col width="35%">
									<col width="15%">
									<col width="35%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">사업 구분</th>
										<td class="left"><c:out value="${listDt.PRTBIZ_TITLE }"/></td>
										<th scope="row">서식 사용여부</th>
										<td class="left">
											<c:if test="${listDt.USE_YN eq 'Y' }">
												<span>사용</span>
											</c:if>
											<c:if test="${listDt.USE_YN eq 'N' }">
												<span>미사용</span>
											</c:if>
										</td>
									</tr>
					
									<tr>
										<th scope="row">업무 구분</th>
										<td class="left" colspan="3"><c:out value="${listDt.JOB_TYPE }"/></td>
									</tr>
					
									<tr>
										<th scope="row">서명 대상자</th>
										<td class="left" colspan="3"><c:out value="${listDt.SGNTR }"/></td>
									</tr>
									<tr>
										<th scope="row">서식명</th>
										<td class="left" colspan="3"><c:out value="${listDt.ADMSFM_NM}"/></td>
									</tr>
									<tr>
										<th scope="row">전자서식 조회</th>
										<td class="left point-color04" colspan="3">
										<c:choose>
											<c:when test="${not empty formatId }">
												<button type="button" class="btn-m03 btn-color01" onclick="saveCookies()"><c:out value="${listDt.ADMSFM_NM}"/></button>
											</c:when>
											<c:otherwise>
												전자서식이 존재하지 않습니다.
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
									<tr>
										<th scope="row">서식 원본파일 첨부</th>
										<td class="left" colspan="3">
											<p class="attached-file"><a href="download.do?mId=114&${keyItemId}=<c:out value="${listFt.ADMSFM_IDX}"/>&fidx=<c:out value="${listFt.FLE_IDX}"/>&itId=<c:out value="${listFt.ITEM_ID}"/>" class="fn_filedown"><c:out value="${listFt.FILE_ORIGIN_NAME}"/></a></p>
										</td>
									</tr>
									<tr>
										<th scope="row">최초 등록자 정보</th>
										<td class="left" colspan="3">
			                            <ul class="ul-list02">
			                                <li>
			                                    <strong>소속부서</strong>
			                                    <c:out value="${listDt.DOCTOR_DEPT_NAME}"/>
			                                </li>
			                                <li>
			                                    <strong>등록자명</strong>
			                                    <c:out value="${listDt.REGI_NAME}"/>
			                                </li>
			                            </ul>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					
					</div>


					<div class="btns-area mt60">
	                    <div class="btns-right">
							<c:if test="${userTypeIdx ge 40 }">
		                        <button type="button" class="btn-m01 btn-color02 depth4 btn-input" data-tmp="${idx}">
	                    			수정
		                        </button>
		                        <a href="#" class="btn-m01 btn-color02 depth4 open-modal01">
	                       			삭제
		                        </a>
	                        </c:if>
	                        <a href="${URL_LIST }" class="btn-m01 btn-color01 depth4">
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
<script src="${contextPath}/dct/js/adminForm/view.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
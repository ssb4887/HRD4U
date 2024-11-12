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

<div id="overlay"></div>
<div class="loader"></div>

		 		<div class="contents-wrapper">
			        <!-- CMS 시작 -->
			        <form id="adminForm" name="adminForm">
						<input type="hidden" id="idx" name="admsfmIdx"  value="${idx}"/>
						<input type="hidden" id="version" name="version"  value="${form.ADMSFM_VER }"/>
						<input type="hidden" id="useYn" value="${form.USE_YN }"/>
						<input type="hidden" name="tmprSaveYn"  value="${form.TMPR_SAVE_YN }"/>
						<input type="hidden" name="dcsnYn"  value="${form.DCSN_YN }"/>
						<c:set var="formatId" value="${form.ELCTRN_FORMAT_ID }"/>
			            <legend class="blind">방문기업서식 등록</legend>
			
			            <div class="board-write">
			                <div class="one-box">
			                    <dl>
			                        <dt>
			                            <label for="prtbizidx">
			                                사업 구분
			                            </label>
			                        </dt>
			                        <dd>
			                            <select name="prtbizIdx" id="prtbizIdx" class="w100">
											<option value="${form.PRTBIZ_IDX }">${empty form? '사업 구분' : form.PRTBIZ_TITLE }</option>
				                            <c:forEach var="item" items="${prtbizIdx }">
												<option value="${item.key }"><c:out value="${item.value }"/></option>
				                            </c:forEach>
			                            </select>
			                        </dd>
			                    </dl>
			                </div>
			
			                <div class="one-box">
			                    <dl>
			                        <dt>
			                            <label for="useYn">
			                                서식 사용여부
			                            </label>
			                        </dt>
			                        <dd>
			                            <div class="input-radio-wrapper ratio">
			                                <div class="input-radio-area">
			                                    <input type="radio" class="radio-type01" id="radio0101" name="useYn" value="Y" >
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
			                            <label for="jobType">
			                                업무 구분
			                            </label>
			                        </dt>
			                        <dd>
			                            <select name="jobType" id="jobType" class="w100">
											<option value="${form.JOB_TYPE }">${empty form? '업무 구분' : form.JOB_TYPE }</option>
				                            <c:forEach var="item" items="${jobType }">
												<option value="${item.value }">${item.value }</option>
				                            </c:forEach>
			                            </select>
			                        </dd>
			                    </dl>
			                </div>
			                <div class="one-box">
			                    <dl>
			                        <dt>
			                            <label for="sgntr">
			                                서명 대상자
			                            </label>
			                        </dt>
			                        <dd>
			                            <select name="sgntr" id="sgntr" class="w100">
											<option value="${form.SGNTR }">${empty form? '서명 대상자' : form.SGNTR }</option>
				                            <c:forEach var="item" items="${sgntr }">
												<option value="${item.value }">${item.value }</option>
				                            </c:forEach>
			                            </select>
			                        </dd>
			                    </dl>
			                </div>
			
			                <div class="one-box">
			                    <dl>
			                        <dt>
			                            <label for="admsfmNm">
			                                서식명
			                            </label>
			                        </dt>
			                        <dd>
			                            <input type="text" id="admsfmNm" name="admsfmNm" value="${form.ADMSFM_NM }" placeholder="서식명" class="w100" size="100" maxlength="100" >
		                            	<ul class="ul-list02"><li class="point-color02">서식명에 대괄호(" [ " 와 " ] " )는 사용할 수 없습니다.  일반 괄호 ( " ( " 와 " ) " ) 로 대체해주세요.</li></ul>
			                        </dd>
			                    </dl>
			                </div>
			                <div class="one-box">
			                    <dl class="board-write-contents">
			                        <dt>
			                            <label for="uploadBtn">
			                                서식 원본파일 첨부
			                            </label>
			                        </dt>
			                        <dd>
		                            	<input type="hidden"  id="defaultValue" value="${fileForm.FILE_ORIGIN_NAME }"/> 
			                            <div class="fileBox">
			                                <input type="text" class="fileName" value="${fileForm.FILE_ORIGIN_NAME }" placeholder="파일찾기" readonly>
			                                <input type="file" id="file" name="file" class="uploadBtn"  value="${fileForm.FLE_IDX }" onchange="displayFileName();">
			                                <label for="file" class="btn_file">찾아보기</label>
			                            </div>
			                            <ul class="ul-list02"><li class="point-color02">PDF형식의 파일만 등록가능합니다.</li></ul>
			                        </dd>
			                    </dl>
			                </div>
			
			                <div class="one-box">
			                    <dl>
			                        <dt>
			                            최초 등록자 정보
			                        </dt>
			                        <dd>
			                            <ul class="ul-list02">
			                                <li>
			                                    <strong>소속부서</strong>
			                                    <c:out value="${doc.DOCTOR_DEPT_NAME}"/>
			                                </li>
			                                <li>
			                                    <strong>등록자명</strong>
			                                    <c:out value="${doc.MEMBER_NAME}"/>
			                                </li>
			                            </ul>

			                        </dd>
			                    </dl>
			                </div>
			            </div>
			
			            <div class="btns-area">
			                <div class="btns-right">
			                <c:set var="editUrl" value="${form.ELCTRN_FORMAT_COURS}"/>
				                <c:choose>
				                	<c:when test="${not empty formatId }">
										<button type="button" class="edit btn-m01 btn-color05" onclick="saveCookies();">
											전자서식 편집
										</button>
									</c:when>
				                	<c:otherwise>
										<button type="button" class="uploadEFile btn-m01 btn-color04" onclick="eFormSave();">
											전자서식 생성
										</button>
									</c:otherwise>
								</c:choose>
			                    <button type="button" id="tempBtn" class="btn-m01 btn-color02 depth3 open-modal01">
		                        저장
			                    </button>
			                    <button type="button" class="btn-m01 btn-color03 depth3 open-modal02">
		                        완료
			                    </button>
			                    <a href="${URL_LIST }" class="btn-m01 btn-color01 depth3">
		                        목록
			                    </a>
			                </div>
			            </div>
       					<input type="hidden" id="formatId" name="formatId"  value="${formatId }"/>
						<input type="hidden" id="formatCours" name="formatCours"  value="${form.ELCTRN_FORMAT_COURS }"/>
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
        <div class="modal-area">
            <div class="modal-alert">
                <p>
                	전자서식을 생성하셨습니까? <br /> 
                	미생성시 전자서식은 자동 생성 되지않습니다.
                </p>
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
	
<script src="${contextPath}/dct/js/adminForm/input.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
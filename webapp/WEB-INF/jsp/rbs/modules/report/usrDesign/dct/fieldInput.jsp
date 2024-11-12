<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<style>
	.modal-wrapper {
		width: 1000px;
		top: 40%;
		left: 40%;
	}
	
	.btns-area {
		text-align: right;
		margin-top: 20px;
	}
	
	#id_tpCd {
		width: 65%;
		margin-right: 10px;
	}
	
	.page {margin-bottom: 20px;}
	.hidden {display: none;}
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
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/report.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<form id="${inputFormId}" name="${inputFormId}" method="post" action="${contextPath}/dct/report/inputProc.do?mId=134<c:out value="${modify}"/>" target="submit_target" enctype="multipart/form-data">
	<input type="hidden" name="cnslType" value="<c:out value="${cnsl.cnslType}"/>"/>
	<input type="hidden" name="cnslIdx" value="<c:out value="${cnsl.cnslIdx}"/>"/>
	<input type="hidden" name="bplNo" value="<c:out value="${cnsl.bplNo}"/>"/>
	<c:if test="${not empty dt}"><input type="hidden" name="reprtIdx" value="<c:out value="${dt.REPRT_IDX}"/>"/></c:if>
    <div class="contents-area">
    	<!-- page1 -->
	    <div class="page" id="page1">
	        <div class="contents-box">
	            <h3 class="title-type01">
	               	 Ⅰ. 개요
	            </h3>
	
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							본 장은 현장활용 컨설팅 필요성, 주요활동, 주요 결과를 한 눈에 제시하고자 하는 목적 가지고 있음
	                    </li>
	                </ul>
	            </div>
	        </div>
	
	        <div class="contents-box">
	            <h4 class="title-type02">
	                1. 현장활용 컨설팅 필요성
	            </h4>
	            <div class="text-box mt20 mb20">
	                <textarea id="cnsl_necessity" name="cnsl_necessity" cols="50" rows="5" placeholder="현장활용 컨설팅 필요성" class="w100" title="현장활용 컨설팅 필요성 입력"><c:out value="${data.cnsl_necessity[0]}"/></textarea>
	            </div>
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							현장훈련 후 현장활용 컨설팅 필요성과 컨설팅 추진방향 제시
	                        <ul>
	                            <li class="line-bullet">
									공정개선, 품질개선, 생산성 개선, 시스템 구축(스마트팩토리 등), 신제품 개발 또는 생산 등의 목적으로 훈련과 연계
	                            </li>
	                        </ul>
	                    </li>
	                </ul>
	            </div>
	        </div>
	        <div class="contents-box">
	            <h4 class="title-type02">
	                2. 현장활용 컨설팅 주요 활동
	            </h4>
	            <div class="table-type02 horizontal-scroll mb20">
	                <table>
	                    <caption>
							현장활용 컨설팅 주요 활동표 : 회차, 일시, 회의내용, 방법, 참석자에 관한 정보 제공표
	                    </caption>
	                    <colgroup>
	                        <col style="width: 8%" />
	                        <col style="width: 30%" />
	                        <col style="width: 30%" />
	                        <col style="width: 7%" />
	                        <col style="width: 25%" />
	                    </colgroup>
	                    <thead>
	                        <tr>
	                            <th scope="col">회차</th>
	                            <th scope="col">일시</th>
	                            <th scope="col">회의내용</th>
	                            <th scope="col">방법</th>
	                            <th scope="col">참석자</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    	<c:forEach var="diary" items="${diaryList}" varStatus="i">
	                        <tr>
	                            <td>
	                            	<c:out value="${diary.EXC_ODR}"/>
	                            </td>
	                            <td>
	                            	수행일자 : <fmt:formatDate value="${diary.MTG_START_DT}" pattern="yyyy년 MM월 dd일" /> <br/>
									시작시간 : <fmt:formatDate value="${diary.MTG_START_DT}" type="time" /> <br/>
									종료시간 : <fmt:formatDate value="${diary.MTG_END_DT}" type="time" />
	                            </td>
	                            <td>
	                            	<c:out value="${diary.MTG_CN1}"/>
	                            </td>
	                            <td>
	                            	<c:out value="${diary.OPER_MTHD eq '1' ? '대면' : '비대면'}"/>
	                            </td>
	                            <td>
	                            	컨설팅책임자(PM) : <c:out value="${diary.PM_NM}"/><br/>
	                            	내용전문가 : <c:out value="${diary.CN_EXPERT}"/><br/>
	                            	기업 내부전문가 : <c:out value="${diary.CORP_INNER_EXPERT}"/><br/>
	                            	기타 참석자(기업 등) : <c:out value="${diary.SPNT_PIC}"/>
	                            </td>
	                        </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							컨설팅 수행일지 주요내용 반영하여 시스템에서 자동생성됨
	                    </li>
	                </ul>
	            </div>
	        </div>
	
	        <div class="contents-box">
	            <h4 class="title-type02">
	                3. 현장활용 컨설팅 주요 결과
	            </h4>
	
	            <div class="text-box mt20 mb20">
	                <textarea id="main_result" name="main_result" cols="50" rows="5" placeholder="현장활용 컨설팅 주요 결과" class="w100" title="현장활용 컨설팅 주요 결과 입력"><c:out value="${data.main_result[0]}"/></textarea>
	            </div>
	
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							컨설팅 주요 결과를 요약하여 제시함.
	                    </li>
	                </ul>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- page2 -->
	<div class="page hidden" id="page2">
	    <div class="contents-area">
	        <div class="contents-box">
	            <h3 class="title-type01">
	                2. 현장분석
	            </h3>
	
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							목차별 작성안내에 따라 내용 작성
	                    </li>
	                    <li>
							기술한 내용 외 구체적 내용은 별도 파일 작성하여 업로드
	                    </li>
	                </ul>
	            </div>
	        </div>
	
	        <div class="contents-box">
	            <h4 class="title-type02">
	                1. 현장훈련 주요 내용
	            </h4>
	
	            <div class="text-box mt20 mb20">
	                <textarea id="main_content" name="main_content" cols="50" rows="5" placeholder="현장훈련 주요 내용" class="w100" title="현장훈련 주요 내용 입력"><c:out value="${data.main_content[0]}"/></textarea>
	            </div>
	
	            <div class="table-type02 horizontal-scroll mb20">
	                <table class="width-type02">
	                    <caption>첨부파일</caption>
	                    <colgroup>
	                        <col width="15%">
	                        <col width="85%">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <itui:itemMultiReprtFile itemId="fileContent" itemInfo="${itemInfo}" colspan="2" />
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							참여한 직업능력개발 훈련내용을 기술하고 필요할 경우, 별도 파일을 업로드 함
	                    </li>
	                </ul>
	            </div>
	        </div>
	        <div class="contents-box">
	            <h4 class="title-type02">
	                2. 현장 현황 분석
	            </h4>
	            <div class="text-box mt20 mb20">
	                <textarea id="cnsl_analysis" name="cnsl_analysis" cols="50" rows="5" placeholder="현장 현황 분석" class="w100" title="현장 현황 분석 입력"><c:out value="${data.cnsl_analysis[0]}"/></textarea>
	            </div>
	            <div class="table-type02 horizontal-scroll mb20">
	                <table class="width-type02">
	                    <caption>첨부파일</caption>
	                    <colgroup>
	                        <col width="15%">
	                        <col width="85%">
	                    </colgroup>
	                    <tbody>
	                    	<itui:itemMultiReprtFile itemId="fileAnalysis" itemInfo="${itemInfo}" colspan="2" />
	                    </tbody>
	                </table>
	            </div>
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							훈련으로 해결하지 못한 부분을 분석하여 문제원인도출
	                    </li>
	                    <li>
							추가적인 현장현황분석 및 요구사항 분석
	                    </li>
	                    <li>
							이외 분석 관련 세부내용은 별도의 파일 작성하여 업로드. 분석은 상황에 맞는 다양한 분석 툴을 사용할 수 있고 이에 세부 목차 구성도 자유롭게 구성할 수 있음.
	                    </li>
	                </ul>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- page3 -->
	<div class="page hidden" id="page3">    
	    <div class="contents-area">
	        <div class="contents-box">
	            <h3 class="title-type01">
	                3. 현장활용 컨설팅 결과
	            </h3>
	
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							목차별 작성안내에 따라 내용 작성
	                    </li>
	                    <li>
							기술한 내용 외 구체적 내용은 별도 파일 작성하여 업로드
	                    </li>
	                </ul>
	            </div>
	        </div>
	
	        <div class="contents-box">
	            <h4 class="title-type02">
	                1. 현장활용 컨설팅 개선결과
	            </h4>
	            <div class="text-box mt20 mb20">
	                <textarea id="imp_result" name="imp_result" cols="50" rows="5" placeholder="현장활용 컨설팅 개선결과" class="w100" title="현장활용 컨설팅 개선결과 입력"><c:out value="${data.imp_result[0]}"/></textarea>
	            </div>
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							컨설팅 기간 중 단기과제 해결된 경우, 해당 개선 결과 제시 (ex. 공정개선, 품질개선, 스마트팩토리 구축 결과)
	                    </li>
	                </ul>
	            </div>
	        </div>
	
	
	        <div class="contents-box">
	            <h4 class="title-type02">
	                2. 개선방향 및 세부방안
	            </h4>
	            <div class="text-box mt20 mb20">
	                <textarea id="imp_method" name="imp_method" cols="50" rows="5" placeholder="개선방향 및 세부방안" class="w100" title="개선방향 및 세부방안 입력"><c:out value="${data.imp_method[0]}"/></textarea>
	            </div>
	            <div class="gray-box01">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							컨설팅을 통해 도출된 개선방안(장기과제 또는 훈련 외 사항) 제시
	                        <ul>
	                            <li class="line-bullet">
									개선방향, 목표, 도입과제 정의, 과제 우선순위, 시행시기 등
	                            </li>
	                        </ul>
	                    </li>
	                </ul>
	            </div>
	        </div>
	        <div class="contents-box">
	            <h4 class="title-type02">
	                3. 추가 훈련 계획
	            </h4>
	            <h5 class="title-type05">
					추가 훈련 필요성
	            </h5>
	            <div class="text-box mt20 mb20">
	                <textarea id="add_plan" name="add_plan" cols="50" rows="5" placeholder="개선방향 및 세부방안" class="w100" title="개선방향 및 세부방안 입력"><c:out value="${data.add_plan[0]}"/></textarea>
	            </div>
	            <div class="gray-box01 mb20">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							본 컨설팅 이후 추가 훈련의 필요성 제시
	                    </li>
	                </ul>
	            </div>
	            <h5 class="title-type05 page-avoid">
					훈련과정 명세서
	            </h5>
	            <div class="table-type02 horizontal-scroll mb20">
	                <table>
	                    <tbody>
	                        <tr>
	                            <th scope="row">과정명</th>
	                            <td colspan="3">
	                            	<input type="text" id="subject_name" name="subject_name" class="w100" title="과정명" value="<c:out value="${data.subject_name[0]}"/>"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련 형태</th>
	                            <td colspan="3">
	                            	<input type="text" id="training_type" name="training_type" class="w100" title="훈련 형태" value="<c:out value="${data.training_type[0]}"/>"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">추천 훈련사업</th>
	                            <td colspan="3">
	                            	<input type="text" id="rec_training" name="rec_training" class="w100" title="추천 훈련사업" value="<c:out value="${data.rec_training[0]}"/>"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련 목표</th>
	                            <td colspan="3">
	                            	<input type="text" id="training_goal" name="training_goal" class="w100" title="훈련 목표" value="<c:out value="${data.training_goal[0]}"/>"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">주요 훈련 내용</th>
	                            <td colspan="3">
	                            	<input type="text" id="main_training" name="main_training" class="w100" title="주요 훈련 내용" value="<c:out value="${data.main_training[0]}"/>"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련 대상</th>
	                            <td colspan="3">
	                            	<input type="text" id="training_target" name="training_target" class="w100" title="훈련 대상" value="<c:out value="${data.training_target[0]}"/>"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row" id="id_traContent" rowspan="5">훈련 내용</th>
	                            <th scope="row" class="bg01">교과목명</th>
	                            <th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
	                            <th scope="row" class="bg01">추천 훈련시간</th>
	                        </tr>
	                        <c:choose>
	                        	<c:when test="${fn:length(data.study_name) eq 0}">
	                        		<c:forEach var="list" begin="0" end="3" varStatus="i">
		                        		<tr>
		                        			<td>
				                            	<input type="text" name="study_name" class="w100 trContent" title="교과목명" value="" />
				                            </td>
				                            <td>
				                            	<input type="text" name="detail_content" class="w100 trContent" title="세부내용" value="" />
				                            </td>
				                            <td>
				                            	<input type="text" name="training_time" class="w100 trContent" title="훈련내용" value="" />
				                            </td>
				                        </tr>
	                        		</c:forEach>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<c:forEach var="list" items="${data.study_name}" varStatus="i">
		                        		<tr class="chkData">
		                        			<td>
				                            	<input type="text" name="study_name" class="w100 trContent" title="교과목명" value="<c:out value="${data.study_name[i.index]}"/>"/>
				                            </td>
				                            <td>
				                            	<input type="text" name="detail_content" class="w100 trContent" title="세부내용" value="<c:out value="${data.detail_content[i.index]}"/>"/>
				                            </td>
				                            <td>
				                            	<input type="text" name="training_time" class="w100 trContent" title="훈련내용" value="<c:out value="${data.training_time[i.index]}"/>"/>
				                            </td>
		                        		</tr>
	                        		</c:forEach>
	                        	</c:otherwise>
	                        </c:choose>
	                    </tbody>
	                    <tfoot>
							<tr>
								<td colspan="4">
									<a href="javascript:void(0);" class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
									<a href="javascript:void(0);" class="btn-m02 btn-color01" onclick="deleteRow(this)">삭제</a>
								</td>
							</tr>
						</tfoot>
	                </table>
	            </div>
	            <div class="gray-box01 mb20">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							컨설팅 결과 관련 추가 필요한 훈련계획을 위에 제시된 훈련명세서 형태로 작성
	                        <ul>
	                            <li class="line-bullet">
									훈련 대상에는 훈련 대상 직무와 해당 직무 수행자 수준 제시(ex. 영업지원 직무 대리 이상)
	                            </li>
	                            <li class="line-bullet">
									훈련형태에는 사내집체, 사내 현장훈련, 사내 학습모임, 외부위탁(이론), 외부위탁(실습) 등 필요 훈련형태 제시
	                            </li>
	                            <li class="line-bullet">
									해당 훈련 과정 시행 위한 추천훈련 사업 제시
	                            </li>
	                            <li class="line-bullet">
									이외 훈련목표, 훈련내용, 훈련 시간 제시
	                            </li>
	                        </ul>
	                    </li>
	                </ul>
	            </div>
	        </div>
	    </div>
	
	    <div class="contents-area">
	        <div class="contents-box">
	            <h3 class="title-type01">
	                [별첨] 분석 세부 내용
	            </h3>
	            <div class="table-type02 horizontal-scroll mb20">
	                <table class="width-type02">
	                    <caption>첨부파일</caption>
	                    <colgroup>
	                        <col width="15%">
	                        <col width="85%">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                        	<itui:itemMultiReprtFile itemId="fileDetail" itemInfo="${itemInfo}" colspan="2" />
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	
	            <div class="gray-box01 mb20">
	                <h3>작성안내</h3>
	                <ul class="ul-list01">
	                    <li>
							별첨 파일에 대한 설명 제시하고 별첨 파일 업로드
	                    </li>
	                    <li>
							별첨 파일은 형식을 특정하지 않고 본문에 제시한 내용 외 분석한 내용을 별도 파일 형태로 업로드 함.
	                    </li>
	                </ul>
	            </div>
	        </div>
	    </div>
	</div>
	<!-- //CMS 끝 -->
	<div class="paging-navigation-wrapper">
		<p class="paging-navigation">
			<strong>1</strong>
			<a href="javascript:void(0);" id="js-page2-btn" onclick="showPage(this, 2);">2</a>
			<a href="javascript:void(0);" id="js-page3-btn" onclick="showPage(this, 3);">3</a>
		</p>
	</div>
	<div class="fr mt50">
		<c:if test="${loginVO.usertypeIdx eq 5 and progress eq 10}">
			<button type="button" class="btn-m01 btn-color03 depth2 fn_btn_submit" onclick="approve();">승인</button>
			<button type="button" class="btn-m01 btn-color02 depth2 fn_btn_submit" onclick="openReject('openReject')">반려</button>
		</c:if>
		<c:if test="${loginVO.usertypeIdx eq 20 and progress eq 50}">
			<button type="button" class="btn-m01 btn-color03 depth2 fn_btn_submit" onclick="approve();">승인</button>
			<button type="button" class="btn-m01 btn-color02 depth2 fn_btn_submit" onclick="openReject('openReject')">반려</button>
		</c:if>
		<c:if test="${loginVO.usertypeIdx eq 10 and (progress eq 5 or empty data.progress or progress eq 40)}">
		<a href="javascript:void(0);" class="btn-m01 btn-color02 save" onclick="fn_sampleInputFormSubmit(5);">저장</a>
		<a href="javascript:void(0);" class="btn-m01 btn-color03 receipt hidden" onclick="fn_sampleInputFormSubmit(10);">제출</a>
		</c:if>
		<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
	</div>
	</form>
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="openReject">
	<h2>
    	반려의견 등록
    </h2>
	<div class="modal-area">
    	<div class="contents-box pl0">
           	<div class="basic-search-wrapper">
           		<div class="one-box">
           			<dl>
           				<dt>
							<label>
								의견
							</label>
           				</dt>
           				<dd>
           					<textarea id="id_confmCn" name="confmCn" rows="4" title="반려의견" placeholder="의견을 입력하세요"></textarea>
           				</dd>
           			</dl>
           		</div>
           </div>
           <div class="btns-area">
			<button type="button" id="id_progress" name="progress" onclick="reject();" class="btn-m02 btn-color03 three-select codeLevel" data-status="<%= ConfirmProgress.RETURN.getCode()%>" value="<%= ConfirmProgress.RETURN.getCode()%>">
				<span title="반려">
					반려
				</span>
			</button>
			<button type="button" id="closeBtn_05" onclick="closeReject('openReject')" class="btn-m02 btn-color02 three-select">
				<span title="취소">
					취소
				</span>
			</button>
			</div>
		</div>
	</div>
</div>
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>





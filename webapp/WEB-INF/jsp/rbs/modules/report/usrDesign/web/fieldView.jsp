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
#id_tpCd {
	width: 65%;
	margin-right: 10px;
}

.page {
	margin-bottom: 20px;
}

.hidden {
	display: none;
}

#overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	display: none;
	z-index: 9999;
}

.loader {
	border: 4px solid #f3f3f3;
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

span.update-max {
	margin-left: 32px;
	border: solid 1px;
	padding: 4px;
	border-radius: 8px;
	color: white;
	background-color: gray;
	cursor: pointer;
}

@
keyframes spin { 0% {
	transform: translate(-50%, -50%) rotate(0deg);
}

100%
{
transform




:






translate




(-50%
,
-50%)
rotate




(360
deg


);
}
}

.print-cover-list ol li:before {
	background-image: none !important;
}
@media print {
	* {
		-webkit-print-color-adjust: exact !important;
	}
	body {
		width: 210mm;
		height: 297mm;
		margin: 0;
		padding: 20mm;
	}
	@page :first {
		size: A4;
		margin: 0;
	}
	@page {
		size: A4;
		margin: 20mm 0;
	}
	table {
		width: 100%;
	}
	.page-avoid {
		page-break-inside: avoid;
	}
	.page-start {
		page-break-before: always;
	}
	tbody tr {
		page-break-inside: avoid;
	}
	.exclude-from-print, .sub-visual, header, .contents-title, footer,
		.contents-navigation-wrapper {
		display: none;
	}
	.wrapper, .container {
		padding-top: 0;
	}
	.hidden {
		display: block;
	}
}

.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
	background-color: #fff;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%
}

.close {
	color: #888;
	float: right;
	font-size: 20px;
	cursor: pointer;
}

span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }

@keyframes spin {
	0% { transform: translate(-50%, -50%) rotate(0deg); }
	100% { transform: translate(-50%, -50%) rotate(360deg); }
}
</style>
<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/report.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<div class="contents-area">
		<div class="print-cover-wrapper hidden">
			<div class="print-cover-area">
				<div class="title" style="margin-top:150px;">
					<h3>직업능력개발 현장활용 컨설팅 보고서</h3>
					<h4><c:out value='${cnsl.corpNm}' /></h4>
				</div>
	
				<p class="date">
					<span class="yyyy"><c:out value="${fn:substring(progress.REGI_DATE,0,4)}"/></span>. 
					<span class="mm" id="now-mm"><c:out value="${fn:substring(progress.REGI_DATE,5,7)}"/></span>.
					<span class="dd" id="now-dd"><c:out value="${fn:substring(progress.REGI_DATE,8,10)}"/></span>
				</p>
				
				<div class="table-wrapper">
					<div class="">
						<table>
							<caption>직업능력개발 심층 진단 보고서 기재표 : 구분, 소속, 성명에 관한 정보표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 50%" />
								<col style="width: 20%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">구 분</th>
									<th scope="col">소 속</th>
									<th scope="col">성명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td style="height : 10px">컨설팅 책임자(PM)</td>
									<td style="height : 10px">
										<c:forEach var="member" items="${cnsl.cnslTeam.members}">
											<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if>
										</c:forEach>
									</td>
									<td style="height : 10px">
										<c:forEach var="member" items="${cnsl.cnslTeam.members}">
											<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberName}</c:if>
										</c:forEach>
									</td>
								</tr>
								<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
									<c:if test="${member.teamIdx eq 2}">
										<tr>
											<td style="height : 10px">외부 내용전문가${member.teamOrderIdx}</td>
											<td style="height : 10px"><c:out value="${member.mberPsitn}" /></td>
											<td style="height : 10px"><c:out value="${member.mberName}" /></td>
										</tr>
									</c:if>
								</c:forEach>
								<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
									<c:if test="${member.teamIdx eq 3}">
										<tr>
											<td style="height : 10px">기업 내부전문가${member.teamOrderIdx}</td>
											<td style="height : 10px"><c:out value="${member.mberPsitn}" /></td>
											<td style="height : 10px"><c:out value="${member.mberName}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
	<!-- 				<div class="right"> -->
	<!-- 					<table> -->
	<!-- 						<caption>기업체 확인표 : 기업체 확인 정보표</caption> -->
	<!-- 						<thead> -->
	<!-- 							<tr> -->
	<!-- 								<th scope="col">기업체 확인</th> -->
	<!-- 							</tr> -->
	<!-- 						</thead> -->
	<!-- 						<tbody> -->
	<!-- 							<tr> -->
	<!-- 								<td> -->
	<!-- 									<p>컨설팅이<br /> 완료되었음을 확인함</p> -->
	<!-- 									<p>(대표 날인)</p> -->
	<!-- 								</td> -->
	<!-- 							</tr> -->
	<!-- 						</tbody> -->
	<!-- 					</table> -->
	<!-- 				</div> -->
				</div>
				<div class="page-logo-wrapper">
					<img src="${contextPath}${imgPath}/contents/ci01.png" alt="고용노동부" />
					<img src="${contextPath}${imgPath}/contents/ci02.png" alt="HRDK한국산업인력공단" />
					<img src="${contextPath}${imgPath}/contents/ci03.png" alt="대한상공회의소 중소기업 훈련지원 센터" />
				</div>
			</div>
			<div class="print-cover-list">
				<h3>[ 목 차 ]</h3>
				<ol>
					<li>
						<span class="number"> I. </span> 
						<strong>개요</strong>
	<!-- 					<span class="page-number"> 3 </span> -->
						<ol>
							<li>
								<span class="number"> 1. </span>
								<strong>현장활용 컨설팅 필요성</strong>
	<!-- 							<span class="page-number"> 3 </span> -->
							</li>
							<li>
								<span class="number"> 2. </span>
								<strong>현장활용 컨설팅 주요 활동</strong>
	<!-- 							<span class="page-number"> 3 </span> -->
							</li>
							<li>
								<span class="number"> 3. </span>
								<strong>현장활용 컨설팅 주요 결과</strong>
	<!-- 							<span class="page-number"> 4 </span> -->
							</li>
						</ol>
					</li>
					<li>
						<span class="number"> II. </span>
						<strong>현장분석</strong>
	<!-- 					<span class="page-number"> 5 </span> -->
	
						<ol>
							<li>
								<span class="number"> 1. </span>
								<strong>현장훈련 주요 내용</strong>
	<!-- 							<span class="page-number"> 6 </span> -->
							</li>
							<li>
								<span class="number"> 2. </span>
								<strong>현장 현황 분석</strong>
	<!-- 							<span class="page-number"> 7 </span> -->
							</li>
						</ol>
					</li>
					<li>
						<span class="number"> III. </span>
						<strong>현장활용 컨설팅 결과</strong>
	<!-- 					<span class="page-number"> 9 </span> -->
						<ol>
							<li>
								<span class="number"> 1. </span>
								<strong>현장활용 컨설팅 개선결과</strong>
	<!-- 							<span class="page-number"> 9 </span> -->
							</li>
							<li>
								<span class="number"> 2. </span>
								<strong>개선방향 및 세부방안</strong>
	<!-- 							<span class="page-number"> 16 </span> -->
							</li>
						</ol>
					</li>
				</ol>
				<dl>
					<dt>[ 별 첨]</dt>
					<dd>분석 세부 내용 : 주요 사업 분야, 주요 경영성과 현황</dd>
					<dd>컨설팅 수행일지</dd>
				</dl>
			</div>
		</div>
	</div>
	
		<!-- CMS 시작 -->
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="${contextPath}/web/report/inputProc.do?mId=100&mode=m" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" name="cnslType" value="<c:out value="${cnsl.cnslType}"/>"/>
		<input type="hidden" name="cnslIdx" value="<c:out value="${cnsl.cnslIdx}"/>"/>
		<input type="hidden" name="bplNo" value="<c:out value="${cnsl.bplNo}"/>"/>
		<c:if test="${not empty progress}"><input type="hidden" name="reprtIdx" value="<c:out value="${progress.REPRT_IDX}"/>"/></c:if>
    	
    	<div class="page" id="page1">
		    <div class="contents-area">
		        <div class="contents-box">
		            <h3 class="title-type01">
		               	 Ⅰ. 개요
		            </h3>
		        </div>
		
		        <div class="contents-box">
		            <h4 class="title-type02">
		                1. 현장활용 컨설팅 필요성
		            </h4>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.cnsl_necessity[0]}" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                2. 현장활용 컨설팅 주요 활동
		            </h4>
		            <div class="table-type02 mb20">
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
		                            	<c:out value="${fn:substring(diary.MTG_START_DT,0,16)}"/> ~ <c:out value="${fn:substring(diary.MTG_END_DT,0,16)}"/>
		                            </td>
		                            <td>
		                            	<c:out value="${diary.MTG_CN1}"/>
		                            </td>
		                            <td>
		                            	<c:out value="${diary.OPER_MTHD eq '1' ? '대면' : '비대면'}"/>
		                            </td>
		                            <td>
		                            	<c:out value="${diary.PM_NM}"/>, <c:out value="${diary.CN_EXPERT}"/>, <c:out value="${diary.CORP_INNER_EXPERT}"/>, <c:out value="${diary.SPNT_PIC}"/>
		                            </td>
		                        </tr>
		                        </c:forEach>
		                    </tbody>
		                </table>
		            </div>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                3. 현장활용 컨설팅 주요 결과
		            </h4>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.main_result[0]}"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		        </div>
		    </div>
		</div>
			<!-- page2 -->
		<div class="page hidden page-start" id="page2">
		    <div class="contents-area">
		        <div class="contents-box">
		            <h3 class="title-type01">
		                2. 현장분석
		            </h3>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                1. 현장훈련 주요 내용
		            </h4>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
							<col style="width: 15%" />
							<col style="width: 85%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.main_content[0]}"/>
									</td>
								</tr>
								<itui:objectMultiReprtFile2 itemId="fileContent" itemInfo="${itemInfo}" objClass="${objClass}"/>
							</tbody>
						</table>
					</div>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                2. 현장 현황 분석
		            </h4>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.cnsl_analysis[0]}"/>
									</td>
								</tr>
								<itui:objectMultiReprtFile2 itemId="fileAnalysis" itemInfo="${itemInfo}" objClass="${objClass}"/>
							</tbody>
						</table>
					</div>
		        </div>
		    </div>
		</div>
		
		<!-- page3 -->
		<div class="page hidden page-start" id="page3">    
		    <div class="contents-area">
		        <div class="contents-box">
		            <h3 class="title-type01">
		                3. 현장활용 컨설팅 결과
		            </h3>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                1. 현장활용 컨설팅 개선결과
		            </h4>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.imp_result[0]}"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                2. 개선방향 및 세부방안
		            </h4>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.imp_method[0]}"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		        </div>
		        <div class="contents-box">
		            <h4 class="title-type02">
		                3. 추가 훈련 계획
		            </h4>
		            <h5 class="title-type05">
						추가 훈련 필요성
		            </h5>
		            <div class="table-type02 mb20">
						<table>
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value="${data.add_plan[0]}"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		            <h5 class="title-type05 page-start">
						훈련과정 명세서
		            </h5>
		            <div class="table-type02 mb20">
		                <table>
		                    <tbody>
		                        <tr>
		                            <th scope="row">과정명</th>
		                            <td colspan="3">
		                            	<c:out value="${data.subject_name[0]}"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <th scope="row">훈련 형태</th>
		                            <td colspan="3">
		                            	<c:out value="${data.training_type[0]}"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <th scope="row">추천 훈련사업</th>
		                            <td colspan="3">
		                            	<c:out value="${data.rec_training[0]}"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <th scope="row">훈련 목표</th>
		                            <td colspan="3">
		                            	<c:out value="${data.training_goal[0]}"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <th scope="row">주요 훈련 내용</th>
		                            <td colspan="3">
		                            	<c:out value="${data.main_training[0]}"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <th scope="row">훈련 대상</th>
		                            <td colspan="3">
		                            	<c:out value="${data.training_target[0]}"/>
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
					                            	<input type="text" name="study_name" class="w100" title="교과목명" value="" />
					                            </td>
					                            <td>
					                            	<input type="text" name="detail_content" class="w100" title="세부내용" value="" />
					                            </td>
					                            <td>
					                            	<input type="text" name="training_time" class="w100" title="훈련내용" value="" />
					                            </td>
					                        </tr>
		                        		</c:forEach>
		                        	</c:when>
		                        	<c:otherwise>
		                        		<c:forEach var="list" items="${data.study_name}" varStatus="i">
			                        		<tr>
			                        			<td>
			                        				<c:out value="${data.study_name[i.index]}"/>
					                            </td>
					                            <td>
					                            	<c:out value="${data.detail_content[i.index]}"/>
					                            </td>
					                            <td>
					                            	<c:out value="${data.training_time[i.index]}"/>
					                            </td>
			                        		</tr>
		                        		</c:forEach>
		                        	</c:otherwise>
		                        </c:choose>
		                    </tbody>
		                </table>
		            </div>
		        </div>
		    </div>
		
		    <div class="contents-area">
		        <div class="contents-box">
		            <h3 class="title-type01">
		                [별첨] 분석 세부 내용
		            </h3>
		            <div class="table-type02 mb20">
		                <table class="width-type02">
		                    <caption>첨부파일</caption>
		                    <colgroup>
		                        <col width="15%">
		                        <col width="85%">
		                    </colgroup>
		                    <tbody>
		                        <tr>
		                        	<itui:objectMultiReprtFile2 itemId="fileDetail" itemInfo="${itemInfo}" objClass="${objClass}"/>
		                        </tr>
		                    </tbody>
		                </table>
		            </div>
		    	</div>
		    </div>
		</div>
		</form>
	
		<div class="paging-navigation-wrapper exclude-from-print">
			<p class="paging-navigation">
				<strong>1</strong>
				<a href="javascript:void(0);" id="js-page2-btn" onclick="showPage(this, 2);">2</a>
				<a href="javascript:void(0);" id="js-page3-btn" onclick="showPage(this, 3);">3</a>
			</p>
		</div>
		
		<!-- //CMS 끝 -->
		<div class="fr mt50 exclude-from-print">
			<c:if test="${loginVO.usertypeIdx eq 5 and progress.CONFM_STATUS eq '10'}">
				<button type="button" class="btn-m01 btn-color03 depth2 fn_btn_submit" onclick="approve(50);">승인</button>
				<button type="button" class="btn-m01 btn-color04 depth2 fn_btn_submit" onclick="openReject('openReject')">반려</button>
			</c:if>
			<c:if test="${loginVO.usertypeIdx eq 20 and progress.CONFM_STATUS eq '50'}">
				<button type="button" class="btn-m01 btn-color03 depth2 fn_btn_submit" onclick="approve(55);">승인</button>
				<button type="button" class="btn-m01 btn-color04 depth2 fn_btn_submit" onclick="openReject('openReject')">반려</button>
			</c:if>
			<c:if test="${progress.CONFM_STATUS eq '50' or progress.CONFM_STATUS eq '55'}">
				<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="printReport();">출력</a>
			</c:if>
			<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
		</div>
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
           					<textarea id="id_confmCn" name="confmCn" rows="4" title="반려 의견" placeholder="의견을 입력하세요"></textarea>
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





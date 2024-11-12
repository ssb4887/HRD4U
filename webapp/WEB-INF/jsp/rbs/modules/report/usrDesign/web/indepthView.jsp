<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<style>
.page {margin-bottom: 20px;}
.hidden {display: none;}
.textarea-content {white-space: pre-wrap;}
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

.print-cover-list ol li:before {
	background-image: none !important;
}

@media print {
	* {-webkit-print-color-adjust: exact !important;}
	body {
		width: 210mm;
		height: 297mm;
		margin: 0;
		padding: 20mm;
	}
	@page :first {size: A4;margin: 0;}
	@page {
		size: A4;
		margin: 20mm 0;
	}
	table {width: 100%;}
	.page-avoid {page-break-inside: avoid;}
	.page-start {page-break-before: always;}
	tbody tr {page-break-inside: avoid;}
	.exclude-from-print, .sub-visual, header, .contents-title, footer, .contents-navigation-wrapper {display: none;}
	.wrapper, .container {padding-top: 0;}
	.hidden {display: block;}
	
	.table-type02 {border-top: 0;}
	.table-type02 table thead th {
		border-top: 1px solid #000;
		border-left: 1px solid #000;
		border-right: 1px solid #000;
		color: #000;
	}
	.table-type02 table tbody th,
	.table-type02 table tbody td {
		border: 1px solid #000;
		color: #000;
	}
}
</style>
<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/report/indepthDgns.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

<!-- 프린트용 표지, 목차 -->
<div class="contents-area hidden">
	<div class="print-cover-wrapper">
		<div class="print-cover-area">
			<div class="title" style="margin-top:150px;">
				<h3>직업능력개발 심층 진단 보고서</h3>
				<h4><c:out value='${cnsl.corpNm}' /></h4>
			</div>

			<p class="date">
				<span class="yyyy" id="now-yyyy">2023</span>. 
				<span class="mm" id="now-mm"></span>.
				<span class="dd" id="now-dd"></span>
			</p>
			
			<div class="table-wrapper">
				<div class="">
					<table>
						<caption>직업능력개발 심층 진단 보고서 기재표 : 구분, 소속, 성명에 관한 정보표</caption>
						<colgroup>
							<col style="width: 35%" />
							<col style="width: 40%" />
							<col style="width: 25%" />
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
								<td>컨설팅 책임자(PM)</td>
								<td><c:out value="${pm.mberPsitn}"/></td>
								<td><c:out value="${pm.mberName}"/></td>
							</tr>						
							<c:forEach var="member" items="${exExperts}">
								<c:if test="${member.teamOrderIdx eq 1}">
									<tr>
										<td rowspan="${fn:length(exExperts)}">외부 내용전문가</td>
										<td><c:out value="${member.mberPsitn}"/></td>
										<td><c:out value="${member.mberName}"/></td>
									</tr>
								</c:if>
							</c:forEach>
							
							<c:forEach var="member" items="${exExperts}" varStatus="status">
								<c:if test="${member.teamOrderIdx ne 1}">
									<tr>
										<td><c:out value="${member.mberPsitn}"/></td>
										<td><c:out value="${member.mberName}"/></td>
									</tr>
								</c:if>
							</c:forEach>
							
							<c:forEach var="member" items="${inExperts}">
								<c:if test="${member.teamOrderIdx eq 1}">
									<tr>
										<td rowspan="${fn:length(inExperts)}">기업 내부전문가</td>
										<td><c:out value="${member.mberPsitn}"/></td>
										<td><c:out value="${member.mberName}"/></td>
									</tr>
								</c:if>
							</c:forEach>
							<c:forEach var="member" items="${inExperts}" varStatus="status">
								<c:if test="${member.teamOrderIdx ne 1}">
									<tr>
										<td><c:out value="${member.mberPsitn}" /></td>
										<td><c:out value="${member.mberName}" /></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="page-logo-wrapper">
				<img src="${contextPath}${imgPath}/contents/ci01.png" alt="고용노동부" />
				<img src="${contextPath}${imgPath}/contents/ci02.png" alt="HRDK한국산업인력공단" />
				
				<c:choose>
					<c:when test="${cnsl.spntNm eq 37}">
						<img src="${contextPath}${imgPath}/contents/outline_img_01.jpg" alt="한국공학대학교" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 40}">
						<img src="${contextPath}${imgPath}/contents/outline_img_02.png" alt="전북산학융합원" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 35}">
						<img src="${contextPath}${imgPath}/contents/outline_img_03.jpg" alt="경기경영자총협회" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 36}">
						<img src="${contextPath}${imgPath}/contents/outline_img_04.jpg" alt="대한상공회의소 경기인력개발원" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 43}">
						<img src="${contextPath}${imgPath}/contents/outline_img_05.gif" alt="경북경영자총협회" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 42}">
						<img src="${contextPath}${imgPath}/contents/outline_img_06.jpg" alt="경남경영자총협회" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 41}">
						<img src="${contextPath}${imgPath}/contents/outline_img_07.jpg" alt="대한상공회의소 부산인력개발원" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 38}">
						<img src="${contextPath}${imgPath}/contents/outline_img_08.jpg" alt="대한상공회의소 충남인력개발원" >
					</c:when>
					<c:when test="${cnsl.spntNm eq 39}">
						<img src="${contextPath}${imgPath}/contents/outline_img_09.jpg" alt="한국문화산업협회" >
					</c:when>
					<c:otherwise>
						<img src="${contextPath}${imgPath}/contents/print_img_jungso.png" alt="중소기업훈련지원센터" >
					</c:otherwise>
				</c:choose>
			</div>

		</div>

		<div class="print-cover-list">
			<h3>[ 목 차 ]</h3>
			<ol>
				<li>
					<span class="number"> I. </span> 
					<strong>심층 진단 개요</strong>
<!-- 					<span class="page-number"> 3 </span> -->

					<ol>
						<li>
							<span class="number"> 1. </span>
							<strong>심층 진단 필요성 </strong>
<!-- 							<span class="page-number"> 3 </span> -->
						</li>
						<li>
							<span class="number"> 2. </span>
							<strong>심층 진단 주요 활동 </strong>
<!-- 							<span class="page-number"> 3 </span> -->
						</li>
						<li>
							<span class="number"> 3. </span>
							<strong>심층 진단 주요 결과 </strong>
<!-- 							<span class="page-number"> 4 </span> -->
						</li>
					</ol>
				</li>


				<li>
					<span class="number"> II. </span>
					<strong>현장 이슈 및 원인 도출 </strong>
<!-- 					<span class="page-number"> 5 </span> -->

					<ol>
						<li>
							<span class="number"> 1. </span>
							<strong>현장 이슈 도출 </strong>
<!-- 							<span class="page-number"> 6 </span> -->
						</li>
						<li>
							<span class="number"> 2. </span>
							<strong>현장 이슈별 원인 도출 </strong>
<!-- 							<span class="page-number"> 7 </span> -->
						</li>
					</ol>
				</li>

				<li>
					<span class="number"> III. </span>
					<strong>과제 도출 </strong>
<!-- 					<span class="page-number"> 9 </span> -->

					<ol>
						<li>
							<span class="number"> 1. </span>
							<strong>현장 이슈 원인별 과제 도출 </strong>
<!-- 							<span class="page-number"> 9 </span> -->
						</li>
						<li>
							<span class="number"> 2. </span>
							<strong>과제 기술서 도출 </strong>
<!-- 							<span class="page-number"> 16 </span> -->
						</li>
					</ol>
				</li>
			</ol>

			<dl>
				<dt>[별 첨]</dt>
				<dd>분석 세부 내용 : 주요 사업 분야, 주요 경영성과 현황</dd>
				<dd>컨설팅 수행일지</dd>
			</dl>

		</div>

	</div>

</div>
<!-- 프린트용 표지, 목차 -->
	
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area02">
		<input type="hidden" name="mId" value="${crtMenu.menu_idx}" />
		<form id="report-form">
			<input type="hidden" class="exclude-from-print" id="cnslIdx" name="cnslIdx" value="<c:out value='${cnsl.cnslIdx}' />" />
			<input type="hidden" class="exclude-from-print" id="reprtIdx" name="reprtIdx" value="<c:out value='${report.reprtIdx}' />" />
			<!-- page1 -->
			<div class="page" id="page1">
				<div class="contents-box">
					<h3 class="title-type01">1. 심층 진단개요</h3>
				</div>
	
				<div class="contents-box">
					<h4 class="title-type02">1. 심층 진단 필요성</h4>
	
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>필요성</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">필요성</th>
									<td class="left textarea-content"><c:out value='${data.necessity[0]}' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h4 class="title-type02">2. 심층 진단 주요 활동</h4>
					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table>
							<caption>심층 진단 주요 활동표 : 수행 차수, 수행 일자, 수행 내용, 수행방법, 참석자에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 8%" />
								<col style="width: 12%" />
								<col style="width: 30%" />
								<col style="width: 15%" />
								<col style="width: 20%" />
								<col style="width: 15%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">수행 차수</th>
									<th scope="col">수행 일자</th>
									<th scope="col">수행 내용</th>
									<th scope="col">수행 방법</th>
									<th scope="col" colspan="2">참석자</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${empty diaryList}">
									<tr>
										<td colspan="6">작성된 수행일지가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach var="list" items="${diaryList}">
								<tr>
									<td rowspan="3"><c:out value="${list.excOdr}"/></td>
									<td rowspan="3"><fmt:formatDate value="${list.mtgStartDt}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="left" rowspan="3"><c:out value="${list.mtgWeekExplsn1}"/>, <c:out value="${list.mtgWeekExplsn2}"/></td>
									<td rowspan="3">
										<c:choose>
											<c:when test="${list.excMth eq 1}">회의</c:when>
											<c:when test="${list.excMth eq 2}">워크숍</c:when>
											<c:when test="${list.excMth eq 3}">FGI</c:when>
										</c:choose>
									</td>
									<td class="bg01">컨설팅 책임자(PM)</td>
									<td><c:out value="${list.pmNm}"/></td>
								</tr>
	
								<tr>
									<td class="bg01">외부 내용전문가</td>
									<td><c:out value="${list.cnExpert}"/></td>
								</tr>
								<tr>
									<td class="bg01">기업내부 전문가</td>
									<td><c:out value="${list.corpInnerExpert}"/></td>
								</tr>
	
								</c:forEach>
							</tbody>
						</table>
					</div>
	
				</div>
	
	
				<div class="contents-box page-start">
					<h4 class="title-type02">3. 심층 진단 주요 결과</h4>
	
					<div class="table-type02 mb20">
						<table>
							<caption>심층 진단 주요 결과표 : 진단대상에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 20%" />
								<col style="width: 80%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">진단 대상</th>
									<td class="left">
										<c:out value='${data.result_target[0]}' />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table class="width-type02">
							<caption>심층 진단 주요 결과표02 : 우선순위, 유형, 과제, 관련 현장 이슈에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 8%" />
								<col style="width: 22%" />
								<col style="width: 35%" />
								<col style="width: 35%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">우선순위</th>
									<th scope="col">유형</th>
									<th scope="col">과제</th>
									<th scope="col">관련 현장 이슈</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty data.result_rank[0]}">
									<c:forEach var="i" begin="0" end="${data.result_rank.size() -1}">
										<tr>
											<td>
												<c:out value='${data.result_rank[i]}' />
											</td>
											<td>
												<c:out value='${data.result_type[i]}' />
											</td>
											<td class="left">
												<c:out value='${data.result_task[i]}' />
											</td>
											<td>
												<c:out value='${data.result_spt_issue[i]}' />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.result_rank[0]}">
									<tr>
										<td colspan="4">
											<spring:message code="message.no.list"/>
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll page-avoid">
						<table class="width-type02">
							<caption>결과 요약</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">결과 요약</th>
									<td class="left textarea-content"><c:out value='${data.result_outline[0]}' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page2 -->
			<div class="page hidden page-start" id="page2">
				<div class="contents-box">
					<h3 class="title-type01">2. 현장 이슈 및 원인 도출</h3>
				</div>
	
				<div class="contents-box">
					<h4 class="title-type02">1. 현장 이슈 도출</h4>
					<div class="table-type02">
						<table>
							<caption>현장 이슈 도출표 : 현장 이슈, 이슈 관련 세부 내용</caption>
							<colgroup>
								<col style="width: 40%" />
								<col style="width: 60%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">현장 이슈</th>
									<th scope="col">이슈 관련 세부 내용</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty data.spt_issue[0]}">
									<c:forEach var="i" begin="0" end="${data.spt_issue.size() - 1}">
										<tr>
											<td>
												<c:out value='${data.spt_issue[i]}' />
											</td>
											<td class="left">
												<c:out value='${data.spt_desc[i]}' />
											</td>
										</tr>
									
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_issue[0]}">
									<tr>
										<td>
											-
										</td>
										<td>
											-
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
	
				</div>
	
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table class="width-type02">
							<caption>도출 과정 및 방법</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">도출 과정 및 방법</th>
									<td class="left textarea-content"><c:out value='${data.spt_cn[0]}' /></td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>붙임</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">붙임</th>
									<td class="left">
										<div class="change-application-wrapper">
											<div class="js-file-area-1">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq 'spt_file'}">
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					
				</div>
	
				<div class="contents-box page-start">
					<h4 class="title-type02">2. 현장 이슈별 원인 도출</h4>
	
					<div class="table-type02 mb20">
						<table>
							<caption>현장 이슈별 원인 도출표 : 현장 이슈, 유형, 원인에 관한 세부내용 정보표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 15%" />
								<col style="width: 55%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">현장 이슈</th>
									<th scope="col">유형</th>
									<th scope="col">원인</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty data.spt_cause_issue[0]}">
									<c:forEach var="i" begin="0" end="${data.spt_cause_issue.size() - 1}">
										<tr>
											<td>
												<c:out value='${data.spt_cause_issue[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_cause_type[i]}' />
											</td>
											<td class="left">
												<c:out value='${data.spt_cause[i]}' />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_cause_issue[0]}">
									<tr>
										<td colspan="3">
											<spring:message code="message.no.list" />
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>도출 과정 및 방법</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">도출 과정 및 방법</th>
									<td class="left textarea-content"><c:out value='${data.spt_cause_cn[0]}' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>붙임</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">붙임</th>
									<td class="left">
										<div class="change-application-wrapper">
											<div class="js-file-area-2">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_cause_file'}">
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
			</div>
			
			<!-- page3 -->
			<div class="page hidden page-start" id="page3">
				<div class="contents-box">
					<h3 class="title-type01">3. 과제 도출</h3>
					
					<h4 class="title-type02">1. 현장 이슈 원인별 과제 도출</h4>
	
					<h5 class="title-type05">현장 이슈 원인별 과제 도출</h5>
	
					<div class="table-type02 mb20">
						<table>
							<caption>현장 이슈별 원인 도출표 : 현장 이슈, 유형, 원인에 관한 세부내용 정보표</caption>
							<colgroup>
								<col style="width: 25%">
								<col style="width: 15%">
								<col style="width: 30%">
								<col style="width: 30%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">현장 이슈</th>
									<th scope="col">유형</th>
									<th scope="col">원인</th>
									<th scope="col">과제</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty data.spt_task_issue[0]}">
									<c:forEach var="i" begin="0" end="${data.spt_task_issue.size() - 1}">
										<tr>
											<td>
												<c:out value='${data.spt_task_issue[i]}' />
											</td>
											<td>
												<c:out value="${data.spt_task_type[i]}" />
											</td>
											<td class="left">
												<c:out value='${data.spt_task_cause[i]}' />
											</td>
											<td class="left">
												<c:out value='${data.spt_task[i]}' />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_task_issue[0]}">
									<tr>
										<td colspan="4">
											<spring:message code="message.no.list" />
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table class="width-type02">
							<caption>도출 과정 및 방법</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">도출 과정 및 방법</th>
									<td class="left textarea-content"><c:out value='${data.spt_task_cn[0]}' /></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>붙임</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">붙임</th>
									<td class="left">
										<div class="change-application-wrapper">
											<div class="js-file-area-3">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_task_file'}">
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
	
				<div class="contents-box page-avoid">
					<h5 class="title-type05">과제 타당성 검토 결과</h5>

					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table>
							<caption>과제 타당성 검토 결과표 : 과제, 적합성, 경제성, 실행가능성, 조직수용성, 평균에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 40%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">과제</th>
									<th scope="col">적합성</th>
									<th scope="col">경제성</th>
									<th scope="col">실행가능성</th>
									<th scope="col">조직수용성</th>
									<th scope="col">평균</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty data.spt_validity_task[0]}">
									<c:forEach var="i" begin="0" end="${data.spt_validity_task.size() - 1}">
										<tr>
											<td class="left">
												<c:out value='${data.spt_validity_task[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_validity_slblt[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_validity_eyfcy[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_validity_exec[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_validity_acpt[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_validity_avrg[i]}' />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_validity_task[0]}">
									<tr>
										<td colspan="4">
											<spring:message code="message.no.list" />
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table class="width-type02">
							<caption>검토 과정 및 방법</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">검토 과정 및 방법</th>
									<td class="left textarea-content"><c:out value='${data.spt_validity_cn[0]}' /></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>붙임</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">붙임</th>
									<td class="left">
										<div class="change-application-wrapper">
											<div class="js-file-area-4">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_validity_file'}">
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type05">선정 과제</h5>

					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table>
							<caption>선정 과제표 : 유형, 과제, 우선순위 정보표</caption>
							<colgroup>
								<col style="width: 20%" />
								<col style="width: 65%" />
								<col style="width: 15%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">유형</th>
									<th scope="col">과제</th>
									<th scope="col">우선순위</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty data.spt_slctntask_type[0]}">
									<c:forEach var="i" begin="0" end="${data.spt_slctntask_type.size() - 1}">
										<tr>
											<td>
												<c:out value='${data.spt_slctntask_type[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_slctntask_task[i]}' />
											</td>
											<td>
												<c:out value='${data.spt_slctntask_rank[i]}' />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_slctntask_type[0]}">
									<tr>
										<td>
											<spring:message code="message.no.list" />
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
	
				</div>
	
				
				<div class="contents-box page-start">
					<h4 class="title-type02">과제기술서 도출</h4>
	
					<c:if test="${not empty data.taskdesc_task[0]}">
						<c:forEach var="i" begin="0" end="${data.taskdesc_task.size() - 1}">
							<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20 js-table-box-1 js-start-event">
								<table>
									<caption>과제기술서 도출표</caption>
									<colgroup>
										<col style="width: 20%" />
										<col style="width: 80%" />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="bg01">과제</th>
											<td class="left">
												<c:out value='${data.taskdesc_task[i]}' />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">참여 검토 지원사업</th>
											<td class="left">
												<c:out value='${data.taskdesc_biz[i]}' />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">기대효과</th>
											<td class="left">
												<c:out value='${data.taskdesc_effect[i]}' />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">추진 내용</th>
											<td class="left">
												<c:out value='${data.taskdesc_desc[i]}' />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">고려사항</th>
											<td class="left">
												<c:out value='${data.taskdesc_consider[i]}' />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">담당부서</th>
											<td class="left">
												<c:out value='${data.taskdesc_dept[i]}' />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">추진일정</th>
											<td class="left">
												<c:out value='${data.taskdesc_schdul[i]}' />
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty data.taskdesc_task[0]}">
						<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20 js-table-box-1">
							<table>
								<caption>과제기술서 도출표</caption>
								<colgroup>
									<col style="width: 20%" />
									<col style="width: 80%" />
								</colgroup>
								<tbody>
									<tr>
										
									</tr>
								</tbody>
							</table>
						</div>
					</c:if>
	
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>붙임</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">붙임</th>
									<td class="left">
										<div class="change-application-wrapper">
											<div class="js-file-area-5">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'taskdesc_file'}">
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
			</div>
			
			<!-- page4 -->
			<div class="page hidden page-start" id="page4">
				<div class="contents-box">
					<h3 class="title-type01">[별첨] 분석 세부 내용</h3>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll mb20">
						<table class="width-type02">
							<caption>별첨 분석 세부 내용</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">내용</th>
									<td class="left">
										<c:out value='${data.attac_desc[0]}' />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="table-type02 horizontal-scroll js-horizontal-scroll">
						<table class="width-type02">
							<caption>별첨 첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">붙임</th>
									<td class="left">
										<div class="change-application-wrapper">
											<div class="js-file-area-6">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId.contains('attac_file')}">
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form>
		
		<div class="paging-navigation-wrapper exclude-from-print">
			<p class="paging-navigation">
				<strong>1</strong>
				<a href="javascript:void(0);" id="js-page2-btn" onclick="showPage(this, 2);">2</a>
				<a href="javascript:void(0);" id="js-page3-btn" onclick="showPage(this, 3);">3</a>
				<a href="javascript:void(0);" id="js-page4-btn" onclick="showPage(this, 4);">4</a>
			</p>
		</div>

		<div class="fr mt50 exclude-from-print">
			<a href="javascript:void(0);" class="btn-m01 btn-color03" onclick="printReport()">출력</a>
			<c:choose>
				<c:when test="${loginVO.usertypeIdx eq 5 and report.confmStatus eq 10}">
					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="reportStatusUpdate(50)">기업승인</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="openModal('reportRejectModal')">반려</a>
				</c:when>
				<c:when test="${loginVO.usertypeIdx eq 20 and report.confmStatus eq 50}">
					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="reportStatusUpdate(55)">최종승인</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="openModal('reportRejectModal')">반려</a>
				</c:when>
			</c:choose>
			<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
		</div>
	</div>
</div>
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="reportRejectModal">
	<h2>보고서 반려</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper">
				<div class="one-box">
					<dl>
						<dt>
							<label> 의견 </label>
						</dt>
						<dd>
							<textarea id="confmCn" name="confmCn" rows="4" placeholder="의견을 입력하세요"></textarea>
						</dd>
					</dl>
				</div>
			</div>
			<div class="btns-area">
				<button type="button" class="btn-m02 btn-color01 three-select" onclick="reportStatusUpdate(40)">반려</button>
				<button type="button" id="closeBtn_05" onclick="closeModal('reportRejectModal')" class="btn-m02 btn-color02 three-select">
					<span> 닫기 </span>
				</button>
			</div>
		</div>
	</div>
</div>
<!-- // 모달창 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
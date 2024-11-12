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
	table {width: 100%;page-break-inside: avoid;}
	.page-avoid {page-break-inside: avoid;}
	.page-start {page-break-before: always;}
	tbody tr {page-break-inside: avoid;}
	.exclude-from-print, .sub-visual, header, .contents-title, footer, .contents-navigation-wrapper {display: none;}
	.wrapper, .container {padding-top: 0;}
	.hidden {display: block;}
	.table-type02 table tbody td {padding: 3px;}
	.board-write { margin-bottom: 24px; }
	.board-write dl dd { min-height:28px; padding:4px; }
	.board-write dl.board-write-contents dt { min-height:28px; padding:4px 14px; }
	.table-type02 table tbody th {padding: 3px 3px;}
}
pre { text-align: left; white-space: pre-wrap;}
</style>
<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/report/indepthDgns.js"/>"></script>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/report/pblView.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

<!-- 프린트용 표지, 목차 -->
<div class="contents-area hidden">
	<div class="print-cover-wrapper">
		<div class="print-cover-area">
			<div class="title" style="margin-top:120px;">
				<h3>
					체계적 현장훈련(S-OJT)<br>
					과제수행 OJT 과정개발 컨설팅 보고서<br>
					(${data.profileName[0] })
				</h3>
				<h4><span class="">${cnsl.corpNm }</span></h4>
			</div>

			<p class="date">
				<span class="yyyy" id="now-yyyy">2023</span>. <span class="mm" id="now-mm">  </span>.
				<span class="dd" id="now-dd">  </span>
			</p>

			<div class="table-wrapper">
				<div class="left">
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
								<td>${pm.mberPsitn }</td>
								<td>${pm.mberName }</td>
							</tr>
							<tr>
								<td>기업 내부전문가</td>
								<td>
									<c:forEach var="item" items="${inExperts }">
										<p>${item.mberPsitn }</p>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="item" items="${inExperts }">
										<p>${item.mberPsitn }</p>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td>외부 전문가(내용)</td>
								<td>
									<c:forEach var="item" items="${exExperts }">
										<p>${item.mberPsitn }</p>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="item" items="${exExperts }">
										<p>${item.mberName }</p>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="right">
					<table>
						<caption>기업체 확인표 : 기업체 확인 정보표</caption>
						<thead>
							<tr>
								<th scope="col">기업체 확인</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<p>컨설팅이<br /> 완료되었음을 확인함</p>
									<p>(대표 날인)</p>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="page-logo-wrapper">
				<img src="${empty contextPath ? '' : contextPath }${imgPath}/contents/ci01.png" alt="고용노동부" />
				<img src="${empty contextPath ? '' : contextPath }${imgPath}/contents/ci02.png" alt="HRDK한국산업인력공단" />
			</div>

		</div>

		<div class="print-cover-list">
			<h3>[ 목 차 ]</h3>
			<ol>
				<li>
					<span class="number"> I. </span> 
					<strong>훈련과정 개요</strong>
				</li>
				<li>
					<span class="number"> II. </span> 
					<strong>컨설팅 개요</strong>
					<ol>
						<li>
							<span class="number"> 1. </span>
							<strong>과정개발 필요성 </strong>
						</li>
						<li>
							<span class="number"> 2. </span>
							<strong>과정개발 주요 활동 </strong>
						</li>
						<li>
							<span class="number"> 3. </span>
							<strong>과정개발 주요 결과 </strong>
						</li>
					</ol>
				</li>


				<li>
					<span class="number"> III. </span>
					<strong>훈련 요구분석 </strong>

					<ol>
						<li>
							<span class="number"> 1. </span>
							<strong>기업 현황 분석 </strong>
						</li>
						<li>
							<span class="number"> 2. </span>
							<strong>훈련대상 업무 선정 및 분석 </strong>
						</li>
						<li>
							<span class="number"> 3. </span>
							<strong>기업 훈련환경 분석</strong>
						</li>
					</ol>
				</li>

				<li>
					<span class="number"> IV. </span>
					<strong>PBL 운영계획 수립 </strong>

					<ol>
						<li>
							<span class="number"> 1. </span>
							<strong>운영계획서 </strong>
						</li>
						<li>
							<span class="number"> 2. </span>
							<strong>평가 계획 </strong>
						</li>
					</ol>
				</li>
			</ol>

			<dl>
				<dt>[ 별 첨]</dt>
				<dd>컨설팅 수행일지 1부</dd>
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
					<h3 class="title-type01">훈련과정 개요</h3>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>훈련과정 개요 정보표</caption>
							<colgroup>
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" colspan="2">기업명</th>
									<td class="left" colspan="3">
										<c:out value="${cnsl.corpNm }" />
									</td>
									<th scope="row" colspan="2">사업장관리번호</th>
									<td class="left" colspan="3">
										<c:out value="${cnsl.bplNo }" />
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">주요 업종</th>
									<th scope="row" class="bg01">업종코드</th>
									<td colspan="3" class="left">
										<c:out value="${bsc.base.INDUTY_CD }" />
									</td>
									<th scope="row" class="bg01">
										주업종<br />(주된 사업)
									</th>
									<td class="left" colspan="4">
										<c:out value="${cnsl.indutyNm}" />
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">주소</th>
									<td colspan="8" class="left">
										<c:out value="${bsc.base.CORP_LOCATION }" />
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련실시주소</th>
									<td colspan="8" class="left">
										<c:out value="(${cnsl.trOprtnRegionZip }) ${cnsl.trOprtnRegionAddr } ${cnsl.trOprtnRegionAddrDtl}" />
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">관할 지부·지사</th>
									<td class="left" colspan="8">
										<c:out value="${bsc.base.INSTT_NAME }" />
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2" colspan="2">담당자 연락처</th>
									<th scope="row" class="bg01">직위</th>
									<td class="left" colspan="3">
										<c:out value="${cnsl.corpPicOfcps }" />
									</td>
									<th scope="row" class="bg01">성명</th>
									<td colspan="3" class="left">
										<c:out value="${cnsl.corpPicNm }" />
									</td>
								</tr>
								<tr>
									<th scope="row" class="bg01">연락처</th>
									<td class="left" colspan="3">
										<c:out value="${cnsl.corpPicTelno }" />
									</td>
									<th scope="row" class="bg01">e-mail</th>
									<td colspan="3" class="left">
										<div class="input-email-wrapper type02">
											<c:out value="${cnsl.corpPicEmail }" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련과정명</th>
									<td class="left" colspan="8">
										<c:out value="${data.profileName[0] }" />
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">NCS 분류</th>
									<td class="left" colspan="8">
										<div class="flex-box">
											<c:out value="${cnsl.dtyClNm }(${cnsl.dtyCl })" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련시간</th>
									<td class="left" colspan="8">
										<div class="flex-box">
											<c:out value="${data.totalTime[0] } 명" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련근로자</th>
									<td class="left" colspan="8">
										<div class="flex-box">
											<c:out value="${data.numTrainee[0] } 명" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련 직무</th>
									<td class="left" colspan="8">
										<c:out value="${data.programDescription[0] }"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page2 -->
			<div class="page hidden page-start" id="page2">
				<h3 class="title-type01 ml0">
				컨설팅 개요
				</h3>
				<div class="contents-box">
					<h4 class="title-type02">
						과정개발 필요성
					</h4>
					<h5 class="title-type05">
						가. 기업HRD이음컨설팅 결과
					</h5>
					<div class="table-type02 horizontal-scroll mb50">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="10%">
								<col width="8%">
								<col width="18%">
								<col width="18%">
								<col width="13%">
								<col width="11%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" rowspan="9" class="bg01">기업훈련현황</th>
									<th scope="row" rowspan="4">
										훈련<br>실시<br>이력
									</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02">참여사업</th>
									<th scope="col" class="bg02">훈련과정명</th>
									<th scope="col" class="bg02">훈련방법</th>
									<th scope="col" class="bg02">훈련기간(일)</th>
								</tr>
								<c:forEach var="item" items="${bsc.trainHis }">
								<tr>
									<td><c:out value="${item.YEAR }" /></td>
									<td><c:out value="${item.RCTBIZ_NAME }" /></td>
									<td><c:out value="${item.TP_NAME }" /></td>
									<td><c:out value="${item.METHOD }" /></td>
									<td><c:out value="${empty item.PERIOD ? '-' : item.PERIOD }" /></td>
								</tr>
								</c:forEach>
								<tr>
									<th scope="row" rowspan="5" class="line">
										훈련<br>지원<br>이력
									</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02" colspan="1">연간 정부지원 한도금액(원) (A)</th>
									<th scope="col" class="bg02" colspan="2">지원받은 금액(원) (B)</th>
									<th scope="col" class="bg02">비율(B/A)</th>
								</tr>
								<c:forEach var="item" items="${bsc.fundHis }">
								<tr>
									<td><c:out value="${item.YEAR }" /></td>
									<td colspan="2"><c:out value="${item.FUND_LIMIT }" /></td>
									<td><c:out value="${item.SPT_PAY }" /></td>
									<td><c:out value="${(item.SPT_PAY/item.FUND_LIMIT)*100 }" /></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption></caption>
							<colgroup>
								<col width="16%">
								<col width="">
								<col width="">
								<col width="">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" rowspan="3">추천훈련사업</th>
									<th scope="col">추천 1순위</th>
									<th scope="col">추천 2순위</th>
									<th scope="col">추천 3순위</th>
								</tr>
								<tr>
									<th scope="col"><c:out value="${bsc.rctr[0].RCTR_NAME }" /></th>
									<th scope="col"><c:out value="${bsc.rctr[1].RCTR_NAME }" /></th>
									<th scope="col"><c:out value="${bsc.rctr[2].RCTR_NAME }" /></th>
								</tr>
								<tr>
									<td class="line left">
										${bsc.rctr[0].INTRO }
									</td>
									<td class="left">
										${bsc.rctr[1].INTRO }
									</td>
									<td class="left">
										${bsc.rctr[2].INTRO }
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">
										HRD 제안<br>(적합 훈련 및 과정 제안)
									</th>
									<td class="line left">
										<c:out value="${bsc.rctr[0].HRD_SGST }" />
									</td>
									<td class="left">
										<c:out value="${bsc.rctr[1].HRD_SGST }" />
									</td>
									<td class="left">
										<c:out value="${bsc.rctr[2].HRD_SGST }" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type05">나. 과정개발 필요성</h5>
					<div class="text-box">
						<pre><c:out value="${data.programNecessity[0] }" /></pre>
					</div>
				</div>
				<div class="contents-box page-start">
					<h4 class="title-type02">
						과정개발 주요 활동
					</h4>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="10%">
								<col width="auto">
								<col width="15%">
								<col width="15%">
								<col width="15%">
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
								<c:forEach items="${diary }" var="item">
									<tr>
										<td rowspan="3"><c:out value="${item.EXC_ODR }" />차</td>
										<td rowspan="3"><c:out value="${item.MTG_START_DT }" /></td>
										<td rowspan="3">
											<if test="${not empty item.MTG_WEEK_EXPLSN1 }">
												<p>- <c:out value="${item.MTG_WEEK_EXPLSN1 }" /></p>
											</if>
											<if test="${not empty item.MTG_WEEK_EXPLSN2 }">
												<p>- <c:out value="${item.MTG_WEEK_EXPLSN2 }" /></p>
											</if>
										</td>
										<td rowspan="3">
											<c:out value="${item.EXC_MTH }" />
										</td>
										<td class="bg01">컨설팅책임자(PM)</td>
										<td><c:out value="${item.PM_NM }" /></td>
									</tr>
									<tr>
										<td class="bg01">외부 내용전문가</td>
										<td><c:out value="${item.CN_EXPERT }" /></td>
									</tr>
									<tr>
										<td class="bg01">기업 내부전문가</td>
										<td><c:out value="${item.CORP_INNER_EXPERT }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<h4 class="title-type02">과정개발 주요 결과</h4>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="20%">
								<col width="26%">
								<col width="14%">
								<col width="auto">
								<col width="auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">훈련대상 업무 선정 결과</th>
									<th scope="col">훈련과정명</th>
									<th scope="col">훈련시간</th>
									<th scope="col">평가방법</th>
									<th scope="col">훈련 결과물</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td id="targets-join-list">
										<c:forEach var="item" items="${data.targetsName }">
											<p><c:out value="${item }"/></p>
										</c:forEach>
									</td>
									<td class="profile-name"><c:out value="${data.profileName[0] }" /></td>
									<td class="profile-total-time"><c:out value="${data.profileTotalTime[0] }" /></td>
									<td><c:out value="${data.profileMethod[0] }" /></td>
									<td><c:out value="${data.profileResult[0] }" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page3 -->
			<div class="page hidden page-start" id="page3">
				<h3 class="title-type01 ml0">훈련 요구분석</h3>
				<div class="contents-box">
					<h4 class="title-type02">
						기업 현황 분석
					</h4>
					<h5 class="title-type05">
						가. 기업 경영 이슈
					</h5>
					<div class="text-box">
						<pre><c:out value="${data.businessIssue[0] }" /></pre>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type05">나. 조직 및 주요 업무</h5>
					<h5 class="title-type03" style="color:#333333;">조직도</h5>											
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="30%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr class="organ-group" id="organ-group-0">
									<th scope="row" rowspan="1">
										부서(팀)명
									</th>
									<td>
										<span class="organ-group-00-team" id="organ-group-00-team-00"></span>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page4 -->
			<div class="page hidden" id="page4">
				<h3 class="title-type01 ml0 exclude-from-print">훈련 요구분석</h3>
				<div class="contents-box">
					<h4 class="title-type02">훈련대상 업무 선정 및 분석</h4>
					<h5 class="title-type05">
						가. 훈련대상 업무 선정
					</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="25%">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="15%">
							</colgroup>
							<tbody id="train-target">
								<tr id="train-target-header">
									<th scope="col">업무명</th>
									<th scope="col" colspan="5">
										훈련과정 개발 필요성<br>
										낮음 < ------------------- > 높음
									</th>
									<th scope="col">훈련대상 업무 선정 여부</th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="text-box mt20">
						<pre><c:out value="${data.targetRemark[0] }" /></pre>
					</div>
				</div>
				<h3 class="title-type01 ml0 page-start">훈련 요구분석</h3>
				<div class="contents-box">
					<h4 class="title-type02">훈련대상 업무 선정 및 분석</h4>
					<h5 class="title-type05">나. 훈련대상 업무 세부내용</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="25%">
								<col width="auto">
								<col width="auto">
								<col width="auto">
							</colgroup>
							<tbody id="target-detail">
								<tr>
									<th scope="col">업무명</th>
									<th scope="col">세부내용</th>
									<th scope="col">지식</th>
									<th scope="col">기술</th>
								</tr>
								<c:forEach var="item" items="${data.targetsName }" varStatus="i">
								<tr>
									<td><c:out value="${data.targetsName[i.index] }"/></td>
									<td><c:out value="${data.targetDetail[i.index] }"/></td>
									<td><c:out value="${data.targetKnowledge[i.index] }"/></td>
									<td><c:out value="${data.targetTechnology[i.index] }"/></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page5 -->
			<div class="page hidden page-start" id="page5">
				<h3 class="title-type01 ml0">
					훈련 요구분석
				</h3>
				<div class="contents-box">
					<h4 class="title-type02">
						기업 훈련환경 분석
					</h4>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="6%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="20%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col" colspan="2">구분</th>
									<th scope="col" colspan="4">내용</th>
								</tr>
								<tr>
									<th scope="row" rowspan="7">훈련 여건</th>
									<th scope="row">적정 훈련시간</th>
									<td colspan="4">
										<div class="flex-box">
											<c:out value="${data.properTrainingTime[0] } 시간" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">적정 훈련장소</th>
									<td>
										<label for="checkbox0101">사내</label>
										<c:out value="${data.checkboxProperLocation[0] eq 'Y' ? '⩗' : '' }" />
									</td>
									<td colspan="3">
										<div class="flex-box">
											<span class="mr10">* 훈련장소</span>
											<c:out value="(${cnsl.trOprtnRegionZip }) ${cnsl.trOprtnRegionAddr } ${cnsl.trOprtnRegionAddrDtl}" />
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<label for="checkbox0102">사외</label>
										<c:out value="${data.checkboxProperLocation[1] eq 'Y' ? '⩗' : '' }" />
									</td>
									<td colspan="3">
										<p class="word-type02 left mb10">* 훈련장소 특이사항</p>
										<pre>${data.properLocationReason[0] }</pre>
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">사내 강사 활용 여부</th>
									<td rowspan="2">
										<label for="checkbox0101">예</label>
										<c:out value="${data.insiderTeacherYN[0] eq 'Y' ? '⩗': '' }" />
									</td>
									<th scope="col">이름</th>
									<td>
										<c:out value="${data.insiderTeacherName[0] }" />
									</td>
									<td rowspan="2">
										<label for="checkbox0101">아니오</label>
										<c:out value="${data.insiderTeacherYN[0] eq 'N' ? '⩗': '' }" />
									</td>
								</tr>
								<tr>
									<th scope="col">직책</th>
									<td>
										<c:out value="${data.insiderTeacherPosition[0] }" />
									</td>
								</tr>
								<tr>
									<th scope="col">대상 인원</th>
									<td colspan="4">
										<div class="flex-box">
											<c:out value="${data.numberTrainees[0] } 명" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col">대상자 특성</th>
									<td colspan="4">
										<pre><c:out value="${data.targetOverview[0] }" /></pre>
									</td>
								</tr>
								<tr>
									<th scope="col" colspan="2">훈련 요구분석 결과</th>
									<td colspan="4">
										<pre id="requirement"><c:out value="${data.trainingRequirement[0] }" /></pre>
									</td>
								</tr>
								<tr>
									<th scope="col" colspan="2" rowspan="2">훈련을 통한 기대효과</th>
									<th scope="col" colspan="3">As-is (현재 상황)</th>
									<th scope="col">To-be (원하는 상황)</th>
								</tr>
								<tr>
									<td colspan="3">
										<pre><c:out value="${data.expectationASIS[0] }" /></pre>
									</td>
									<td>
										<pre><c:out value="${data.expectationTOBE[0] }" /></pre>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page6 -->
			<div class="page hidden" id="page6">
				<h3 class="title-type01 ml0">PBL 운영계획 수립</h3>
				<div class="contents-box">
					<h4 class="title-type02">
						운영계획서
					</h4>
					<h5 class="title-type05">
						가. 훈련과정 개요
					</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="25%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">과정명</th>
									<td class="profile-name"><c:out value="${data.profileName[0] }" /></td>
								</tr>
								<tr>
									<th scope="col">훈련기간</th>
									<td>
										<c:out value="${data.profilePeriod[0] }" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type05">나. 훈련 실시배경</h5>
					<div class="gray-box" id="background">
						
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type05">
						다. 학습그룹 구성
					</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="10%">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" colspan="2">구분</th>
									<th scope="col" >역할</th>
									<th scope="col">소속(부서)</th>
									<th scope="col">직위</th>
									<th scope="col">성명</th>
								</tr>
							</thead>
							<tbody id="edugroup">
								<tr class="edugroup-trainer-outer-header">
									<th id="trainer-total-header" scope="row" rowspan="${fn:length(data.edugroupOuterRole)+fn:length(data.edugroupInnerRole) }">훈련 교사</th>
									<td id="trainer-outer-header" rowspan="${fn:length(data.edugroupOuterRole) }">
										외부
									</td>
									<c:choose>
										<c:when test="${fn:length(data.edugroupOuterRole) eq 0 }">
											<td>-</td>
											<td>-</td>
											<td>-</td>
											<td>-</td>
										</c:when>
										<c:otherwise>
											<td><c:out value="${data.edugroupOuterRole[0] }" /></td>
											<td><c:out value="${data.edugroupOuterTeam[0] }" /></td>
											<td><c:out value="${data.edugroupOuterPosition[0] }" /></td>
											<td><c:out value="${data.edugroupOuterName[0] }" /></td>
										</c:otherwise>
									</c:choose>
								</tr>
								<c:if test="${fn:length(data.edugroupOuterRole) > 1 }">
									<c:forEach varStatus="i" begin="1" end="${fn:length(data.edugroupOuterRole)-1}">
										<tr>
											<td><c:out value="${data.edugroupOuterRole[i.index] }" /></td>
											<td><c:out value="${data.edugroupOuterTeam[i.index] }" /></td>
											<td><c:out value="${data.edugroupOuterPosition[i.index] }" /></td>
											<td><c:out value="${data.edugroupOuterName[i.index] }" /></td>
										</tr>
									</c:forEach>
								</c:if>
								<tr class="edugroup-trainer-inner-header">
									<td id="trainer-inner-header" rowspan="${fn:length(data.edugroupInnerRole) }">
										내부
									</td>
									<c:choose>
										<c:when test="${fn:length(data.edugroupInnerRole) eq 0 }">
											<td>-</td>
											<td>-</td>
											<td>-</td>
											<td>-</td>
										</c:when>
										<c:otherwise>
											<td><c:out value="${data.edugroupInnerRole[0] }" /></td>
											<td><c:out value="${data.edugroupInnerTeam[0] }" /></td>
											<td><c:out value="${data.edugroupInnerPosition[0] }" /></td>
											<td><c:out value="${data.edugroupInnerName[0] }" /></td>
										</c:otherwise>
									</c:choose>
								</tr>
								<c:if test="${fn:length(data.endugroupInnerRole) > 1 }">
									<c:forEach varStatus="i" begin="1" end="${fn:length(data.edugroupInnerRole)-1}">
										<tr>
											<td><c:out value="${data.edugroupInnerRole[i.index] }" /></td>
											<td><c:out value="${data.edugroupInnerTeam[i.index] }" /></td>
											<td><c:out value="${data.edugroupInnerPosition[i.index] }" /></td>
											<td><c:out value="${data.edugroupInnerName[i.index] }" /></td>
										</tr>
									</c:forEach>
								</c:if>
								<tr class="edugroup-trainee-header">
									<th id="trainee-total-header" scope="row" rowspan="${fn:length(data.traineeName) }">훈련근로자</th>
									<td id="trainee-inner-header" rowspan="${fn:length(data.traineeName) }">
										내부
									</td>
									<c:choose>
										<c:when test="${fn:length(data.traineeName) eq 0 }">
											<td>-</td>
											<td>-</td>
											<td>-</td>
											<td>-</td>
										</c:when>
										<c:otherwise>
											<td><c:out value="${data.traineeRole[0] }" /></td>
											<td><c:out value="${data.traineeTeam[0] }" /></td>
											<td><c:out value="${data.traineePosition[0] }" /></td>
											<td><c:out value="${data.traineeName[0] }" /></td>
										</c:otherwise>
									</c:choose>
								</tr>
								<c:if test="${fn:length(data.traineeName) > 1 }">
									<c:forEach varStatus="i" begin="1" end="${fn:length(data.traineeName)-1}">
										<tr>
											<td><c:out value="${data.traineeRole[i.index] }" /></td>
											<td><c:out value="${data.traineeTeam[i.index] }" /></td>
											<td><c:out value="${data.traineePosition[i.index] }" /></td>
											<td><c:out value="${data.traineeName[i.index] }" /></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box page-start">
					<h5 class="title-type05">
						라. 훈련 교과목 프로파일
					</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="12%">
								<col width="auto">
								<col width="10%">
								<col width="12%">
								<col width="11%">
							</colgroup>
							<tbody id="profile">
								<tr>
									<th scope="col">과정명</th>
									<td colspan="2">
										<c:out value="${data.profileName[0] }" />
									</td>
									<th scope="col">총 훈련시간(h)</th>
									<td colspan="2">
										<c:out value="${data.profileTotalTime[0] }" />
									</td>
								</tr>
								<tr id="profile-header-start">
									<th scope="col">훈련목표</th>
									<td colspan="5">
										<pre>${data.profileTarget[0] }</pre>
									</td>
								</tr>
								<tr class="profile-header">
									<th scope="row" rowspan="4" id="rowspaner">훈련내용</th>
									<th scope="row" rowspan="2">업무(단원)명</th>
									<th scope="row" rowspan="2">세부 내용</th>
									<th scope="row" rowspan="2">훈련 시간(H)</th>
									<th scope="col" colspan="2">교사 투입시간(H)</th>
								</tr>
								<tr class="profile-header">
									<th scope="col">외부</th>
									<th scope="col">내부</th>
								</tr>
								<c:forEach var="item" items="${data.targetsName }" varStatus="i">
								<tr class="profile-detail">
									<td>
										<c:out value="${data.targetsName[i.index] }" />
									</td>
									<td>
										<pre><c:out value="${data.profileDetailContent[i.index] }" /></pre>
									</td>
									<td><c:out value="${data.profileDetailTime[i.index] }" /></td>
									<td><c:out value="${data.profileDetailOuter[i.index] }" /></td>
									<td><c:out value="${data.profileDetailInner[i.index] }" /></td>
								</tr>
								</c:forEach>
								<tr class="profile-detail">
									<td>결과 공유 및 피드백</td>
									<td>
										<pre><c:out value="${data.profileFeedbackContent[0] }"/></pre>
									</td>
									<td>
										<c:out value="${data.profileFeedbackTime[0] }" />
									</td>
									<td>
										<c:out value="${data.profileFeedbackOuter[0] }" />
									</td>
									<td>
										<c:out value="${data.profileFeedbackInner[0] }" />
									</td>
								</tr>
								<tr>
									<th scope="row">유사도 검사 결과</th>
									<td>
										<span id="plagiarism-rate"></span>
									</td>
									<td colspan="3"></td>
								</tr>
								<tr class="profile-footer">
									<td colspan="2">전체시간</td>
									<td class="profile-total-time">
										<c:out value="${data.profileTotalTime[0] }" />
									</td>
									<td><span id="total-outer"></span></td>
									<td><span id="total-inner"></span></td>
								</tr>
								<tr>
									<th scope="row" rowspan="4">평가방법</th>
									<th scope="col" colspan="2">구분</th>
									<th scope="col">대상</th>
									<th scope="col">방법</th>
									<th scope="col">결과물</th>
								</tr>
								<tr>
									<td colspan="2">①과정평가</td>
									<td>훈련근로자</td>
									<td>
										<c:out value="${data.profileMethod[0] }" />
									</td>
									<td>
										<c:out value="${data.profileResult[0] }" />
									</td>
								</tr>
								<tr>
									<td rowspan="2">②결과평가</td>
									<td>만족도·성취도 조사</td>
									<td>훈련근로자</td>
									<td>설문조사</td>
									<td>-</td>
								</tr>
								<tr>
									<td>현업적용도 조사</td>
									<td>부서(팀)장</td>
									<td>설문조사</td>
									<td>-</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box page-start">
					<div class="people-add-wrapper">
						<h5 class="title-type05">마. 시설·장비</h5>
					</div>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">연번</th>
									<th scope="col">구분</th>
									<th scope="col">시설명</th>
									<th scope="col">규격(사양)</th>
									<th scope="col">위치</th>
								</tr>
							</thead>
							<tbody id="facility">
								<c:forEach var="item" items="${data.facilityName }" varStatus="i">
								<tr class="facility-item">
									<td><c:out value="${i.index+1 }" /></td>
									<td><c:out value="${data.facilityType[i.index] }" /></td>
									<td><c:out value="${data.facilityName[i.index] }" /></td>
									<td><c:out value="${data.facilitySpec[i.index] }" /></td>
									<td><c:out value="${data.facilityLocation[i.index] }" /></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="people-add-wrapper">
						<h5 class="title-type05">바. 훈련교사</h5>
					</div>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">성명</th>
									<th scope="col">내·외부</th>
									<th scope="col">업무경력</th>
									<th scope="col">업무명</th>
									<th scope="col">세부 교육훈련 내용</th>
								</tr>
							</thead>
							<tbody id="trainer">
								<c:forEach var="item" items="${data.trainerName }" varStatus="i">
								<tr class="trainer-item">
									<td>
										<c:out value="${data.trainerName[i.index] }" />
									</td>
									<td>
										<c:out value="${data.trainerLocation[i.index] }" />
									</td>
									<td>
										<c:out value="${data.trainerExperience[i.index] }" />
									</td>
									<td>
										<c:out value="${data.trainerJob[i.index] }" />
									</td>
									<td>
										<pre><c:out value="${data.trainerCourse[i.index] }" /></pre>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page7 -->
			<div class="page hidden page-start" id="page7">
				<h3 class="title-type01 ml0">PBL 운영계획 수립</h3>
				<h4 class="title-type02 ml0">평가 계획</h4>
				<div class="contents-box">
					<h5 class="title-type05">가. 과정평가 계획</h5>
					<div class="board-write">
						<div class="one-box">
							<dl>
								<dt>
									<label for="textfield01">과정명</label>
								</dt>
								<dd class="profile-name"><c:out value="${data.profileName[0] }" /></dd>
							</dl>
						</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="textfield02">평가방법</label>
								</dt>
								<dd>
									<div class="input-checkbox-wrapper ratio">
										<c:out value="${data.profileMethod[0] }" />
									</div>
								</dd>
							</dl>
						</div>
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="textfield03">
											평가일자
										</label>
									</dt>
									<dd>
										<c:out value="${data.evaluationDate[0] }" />
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl class="board-write-contents">
									<dt>
										<label for="textfield04">
											평가기준
										</label>
									</dt>
									<dd>
										<c:out value="${data.evaluationStandard[0] }" />
									</dd>
								</dl>
							</div>
						</div>
						<div class="one-box">
							<dl class="board-write-contents">
								<dt>
									<label for="textfield05">
										평가결과
									</label>
								</dt>
								<dd>
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="radio0101" name="radio01" value="" class="radio-type01" checked disabled>
											<label for="radio0101">
											    Pass
											</label>
										</div>
										<div class="input-radio-area">
											<input type="radio" id="radio0102" name="radio01" value="" class="radio-type01" disabled>
											<label for="radio0102">
											    Fail
											</label>
										</div>
									</div>
								</dd>
							</dl>
						</div>
					</div>
				</div>
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="18%">
								<col width="auto">
								<col width="6%">
								<col width="6%">
								<col width="6%">
								<col width="6%">
								<col width="6%">
							</colgroup>
							<thead>
								<tr>
									<th scope="row" rowspan="2">
										업무(단원)명
									</th>
									<th scope="row" rowspan="2">평가기준</th>
									<th scope="col" colspan="5">수행 수준</th>
								</tr>
								<tr>
									<th scope="col">1</th>
									<th scope="col">2</th>
									<th scope="col">3</th>
									<th scope="col">4</th>
									<th scope="col">5</th>
								</tr>
							</thead>
							<tbody id="targets-evaluation">
								<c:forEach var="item" items="${data.targetsName }" varStatus="i">
								<tr id="eva-${i.index }" class="targets-select">
									<td><c:out value="${item }" /></td>
									<td><c:out value="${data.targetsCriteria[i.index] }"/></td>
									<td id="eval-0" class="eval"></td>
									<td id="eval-1" class="eval"></td>
									<td id="eval-2" class="eval"></td>
									<td id="eval-3" class="eval"></td>
									<td id="eval-4" class="eval"></td>
								</tr>
								</c:forEach>
								<tr>
									<th scope="col">총평</th>
									<td class="left" colspan="6">
										<pre>${data.targetsFinal[0] }</pre>
									</td>

								</tr>
								<tr>
									<th scope="col">평가 방법</th>
									<td class="left" colspan="6">
										<div class="table-type02 horizontal-scroll">
											<table class="width-type02">
											    <colgroup>
											        <col width="15%">
											        <col width="auto">
											    </colgroup>
											    <thead>
											        <tr>
														<th scope="col">평가척도</th>
														<th scope="col">수행 수준 정도</th>
											        </tr>
											    </thead>
											    <tbody>
											        <tr>
														<td>1</td>
														<td class="left">
															업무를 수행하는데 필요한 지식과 기술이 부족함
														</td>
											        </tr>
											        <tr>
														<td>2</td>
														<td class="left">
															업무를 수행할 때 적절한 피드백이 필요한 수행 수준임
														</td>
											        </tr>
											        <tr>
														<td>3</td>
														<td class="left">
															업무를 쉽고 기술적으로 수행할 수 있는 지식과 기술 습득된 수행 수준임
														</td>
											        </tr>
											        <tr>
														<td>4</td>
														<td class="left">
															수행에 필요한 지식과 기술이 충분히 함양되어 있는 수준임
														</td>
											        </tr>
													<tr>
														<td>5</td>
														<td class="left">
															다른 사람들에게 표준과 기준을 제시해 줄 수 있는 탁월한 수행 수준임
														</td>
											        </tr>
											    </tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box page-start">
					<h5 class="title-type05">
						나. 결과평가 계획
					</h5>
					<h5 class="title-type03" style="color:#333333;">만족도 및 성취도 조사</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="auto">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">학습자명</th>
									<td class="left">○○○</td>
									<th scope="col">평가자명</th>
									<td colspan="4" class="left">(훈련근로자)</td>
								</tr>
								<tr>
									<th scope="col">과정명</th>
									<td class="left">○○○</td>
									<th scope="col">평가방법</th>
									<td colspan="4" class="left">온라인 설문조사</td>
								</tr>
								<tr>
									<th scope="col">평가일자</th>
									<td class="left">○○○○년 ○○월 ○○일</td>
									<th scope="col">평가시기</th>
									<td colspan="4" class="left">훈련 종료 직후</td>
								</tr>
								<tr>
									<th scope="col">구분</th>
									<th scope="col">항목</th>
									<th scope="col">매우<br>아니다</th>
									<th scope="col">아니다</th>
									<th scope="col">보통</th>
									<th scope="col">그렇다</th>
									<th scope="col">매우<br>그렇다</th>
								</tr>
								<tr>
									<td rowspan="5">만족도 조사</td>
									<td class="left">본 훈련과정의 교육내용에 대해 만족하십니까?<br>(기업 요구와의 부합여부, 구성 내용, 교육 수준 등)</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio03" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio03" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio03" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio03" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio03" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정의 교육방법에 대해 만족하십니까?<br>(이론, 실습, PBL 등)</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio04" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio04" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio04" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio04" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio04" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정의 교육강사에 대해 만족하십니까?<br>(강사 전문성, 강의 내용 전달능력 등)</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio05" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio05" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio05" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio05" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio05" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정의 교육시간에 대해 만족하십니까?<br>(교육 내용 대비 시간 부족, 과다 등)</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio06" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio06" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio06" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio06" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio06" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정의 교육환경에 대해 만족하십니까?<br>(교육 장소, 시설, 장비, 교육자료 등)</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio07" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio07" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio07" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio07" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio07" value="" disabled></td>
								</tr>
								<tr>
									<td rowspan="3">성취도 조사</td>
									<td class="left">본 훈련과정에서 훈련목표로 제시된 지식 및 기술을 습득하였습니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio08" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio08" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio08" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio08" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio08" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정을 통해 습득한 지식 및 기술을 실무에 적용할 수 있으십니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio09" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio09" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio09" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio09" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio09" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정을 통해 업무 전문성 및 업무 수행 자신감이 향상되셨습니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio10" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio10" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio10" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio10" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio10" value="" disabled></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type03">현업적용도 조사</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="auto">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">학습자명</th>
									<td class="left">○○○</td>
									<th scope="col">평가자명</th>
									<td colspan="4" class="left">(부장/팀장) ○○○</td>
								</tr>
								<tr>
									<th scope="col">과정명</th>
									<td class="left">○○○</td>
									<th scope="col">평가방법</th>
									<td colspan="4" class="left">온라인 설문조사</td>
								</tr>
								<tr>
									<th scope="col">평가일자</th>
									<td class="left">○○○○년 ○○월 ○○일</td>
									<th scope="col">평가시기</th>
									<td colspan="4" class="left">훈련 종료 이후 1개월 이내</td>
								</tr>
								<tr>
									<th scope="col">구분</th>
									<th scope="col">항목</th>
									<th scope="col">매우<br>아니다</th>
									<th scope="col">아니다</th>
									<th scope="col">보통</th>
									<th scope="col">그렇다</th>
									<th scope="col">매우<br>그렇다</th>
								</tr>
								<tr>
									<td rowspan="4">현업적용도<br>조사</td>
									<td class="left">본 훈련과정 이수 후 훈련생에게 관련 업무를 수행할 기회를 제공하였습니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio11" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio11" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio11" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio11" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio11" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">훈련생은 본 훈련과정에서 습득한 지식 및 기술을 실무에 적용하였습니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio12" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio12" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio12" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio12" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio12" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정은 훈련생의 업무 수행에 실질적인 도움이 되었습니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio13" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio13" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio13" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio13" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio13" value="" disabled></td>
								</tr>
								<tr>
									<td class="left">본 훈련과정이 경영상 이슈(예. 불량률 감소/매출액 증대 등) 해결에 기여했다고 생각하십니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio14" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio14" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio14" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio14" value="" disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio14" value="" disabled></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page8 -->
			<div class="page hidden page-start" id="page8">
				<h3 class="title-type01 ml0">
					PBL 운영계획 수립
				</h3>
				<h4 class="title-type02 ml0">
					평가 계획
				</h4>
				<div class="contents-box">
					<h5 class="title-type05">
						나. 결과평가 계획
					</h5>
					<h5 class="title-type03" style="color:#333333;">학습활동 수행일지(‘업무(단원)명’ 중심으로 작성)</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">훈련과정명</th>
									<td class="left">
										<c:out value="${data.profileName[0] }" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="table-type02 horizontal-scroll mt20">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">
										구분
									</th>
									<th scope="col">수행내용</th>
								</tr>
							</thead>
							<tbody id="diary">
								<c:forEach var="item" items="${data.diaryMonth }" varStatus="i">
								<tr class="diary-item">
									<th scope="col">
										<c:out value="${item }" />
									</th>
									<td class="left">
										<pre><c:out value="${data.diaryCN[i.index] }" /></pre>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type03" style="color:#333333;">수행 결과물(‘과제’ 중심으로 제시)</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">훈련과정명</th>
									<td class="left">
										<c:out value="${data.profileName[0] }" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll mt10">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="20%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">연번</th>
									<th scope="col">산출물 유형</th>
									<th scope="col">결과물 예시</th>
								</tr>
								<c:set var="i" value="1" />
								<c:forEach var="file" items="${report.files }">
								<c:if test="${fn:startsWith(file.itemId, 'img_file')}">
								<tr>
									<td>${i}</td>
									<td>
										<div class="change-application-wrapper">
											<div class="js-file-area-${i }">
												<p class="ml0 mt10">
													<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
														<span class="mr05">${file.fileOriginName}</span>
													</a>
												</p>
											</div>
										</div>
									</td>
									<td>
										<p class="attached-file">
											<img src="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
										</p>
									</td>
								</tr>
								<c:set var="i" value="${i+1 }" />
								</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll mt10">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">연번</th>
									<th scope="col">첨부파일</th>
								</tr>
								<c:set var="i" value="1" />
								<c:forEach var="file" items="${report.files }">
								<c:if test="${fn:startsWith(file.itemId, 'pbl_file')}">
								<tr>
									<td>${i}</td>
									<td>
										<div class="change-application-wrapper">
											<div class="js-file-area-${ㅑ }">
												<p class="ml0 mt10 attached-file">
													<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
														<span class="mr05">${file.fileOriginName}</span>
													</a>
												</p>
											</div>
										</div>
									</td>
								</tr>
								<c:set var="i" value="${i+1 }" />
								</c:if>
								</c:forEach>
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
				<a href="javascript:void(0);" id="js-page5-btn" onclick="showPage(this, 5);">5</a>
				<a href="javascript:void(0);" id="js-page6-btn" onclick="showPage(this, 6);">6</a>
				<a href="javascript:void(0);" id="js-page7-btn" onclick="showPage(this, 7);">7</a>
				<a href="javascript:void(0);" id="js-page8-btn" onclick="showPage(this, 8);">8</a>
			</p>
		</div>

		<div class="fr mt50 exclude-from-print">
			<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="printReport();">출력</a>
			<c:choose>
				<c:when test="${loginVO.usertypeIdx eq 5 and report.confmStatus eq 10}">
					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="reportStatusUpdate(50)">기업승인</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="openModal('reportRejectModal')">반려</a>
				</c:when>
				<c:when test="${loginVO.usertypeIdx eq 30 and report.confmStatus eq 50}">
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
<script>
	var text_json = '${json}';
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
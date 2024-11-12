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

.toast {
	visibility: hidden;
	min-width: 250px;
	margin-left: -125px;
	background-color: green;
	color:white;
	text-align: center;
	border-radius: 2px;
	padding: 16px;
	position: fixed;
	z-index: 1;
	left:50%;
	bottom: 30px;
	font-size: 17px;
}

.show {
	visibility: visible;
	-webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
	animation: fadein 0.5s fadeout 0.5s 2.5s;
}

@-webkit-keyframes fadein {
	from {bottom: 0; opacity: 0;}
	to {bottom: 30px; opacity: 1;}
}
@keyframes fadein {
	from {bottom: 0; opacity: 0; }
	to {bottom: 30px; opacity: 1;}
}
@-webkit-keyframes fadeout {
	from {bottom: 30px; opacity: 1; }
	to {bottom: 0; opacity: 0; }
}
@keyframes fadeout {
	from {bottom: 30px; opacity: 1; }
	to {bottom: 0; opacity: 0; }
}
</style>
<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/report/indepthDgns.js"/>"></script>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/report/pbl.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area02">
		<input type="hidden" name="mId" value="${crtMenu.menu_idx}" />
		<form id="report-form">
			<input type="hidden" name="cnslIdx" value="<c:out value='${cnsl.cnslIdx}' />" />
			<input type="hidden" name="reprtIdx" value="<c:out value='${report.reprtIdx}' />" />
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
										<input type="text" name="" value="${cnsl.corpNm }" class="w100" disabled>
									</td>
									<th scope="row" colspan="2">사업장관리번호</th>
									<td class="left" colspan="3">
										<input type="text" name="" value="${cnsl.bplNo }" class="w100" disabled>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">주요 업종</th>
									<th scope="row" class="bg01">업종코드</th>
									<td colspan="3" class="left">
										<input type="text" name="" value="${bsc.base.INDUTY_CD }" class="w100" disabled>
									</td>
									<th scope="row" class="bg01">
										주업종<br />(주된 사업)
									</th>
									<td class="left" colspan="4">
										<input type="text" name="" value="${cnsl.indutyNm }" class="w100" disabled>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">주소</th>
									<td colspan="8" class="left">
										<input type="text" name="" value="${bsc.base.CORP_LOCATION }" class="w100" disabled>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련실시주소</th>
									<td colspan="8" class="left">
										<input type="text" name="" value="(${cnsl.trOprtnRegionZip }) ${cnsl.trOprtnRegionAddr } ${cnsl.trOprtnRegionAddrDtl}" class="w100" disabled>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">관할 지부·지사</th>
									<td class="left" colspan="8">
										<input type="text" id="" name="" value="${bsc.base.INSTT_NAME }" title="관할 지부·지사 입력" class="w100" />
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2" colspan="2">담당자 연락처</th>
									<th scope="row" class="bg01">직위</th>
									<td class="left" colspan="3">
										<input type="text" id="" name="" value="${cnsl.corpPicOfcps }" title="직위 입력" class="w100">
									</td>
									<th scope="row" class="bg01">성명</th>
									<td colspan="3" class="left">
										<input type="text" id="" name="" value="${cnsl.corpPicNm }" title="성명 입력" class="w100">
									</td>
								</tr>
								<tr>
									<th scope="row" class="bg01">연락처</th>
									<td class="left" colspan="3">
										<input type="text" name="" value="${cnsl.corpPicTelno }" class="w100" >
									</td>
									<th scope="row" class="bg01">e-mail</th>
									<td colspan="3" class="left">
										<div class="input-email-wrapper type02">
											<input type="text" name="" value="${cnsl.corpPicEmail }" class="w100" >
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련과정명</th>
									<td class="left" colspan="8">
										<input type="text" id="" name="" value="" title="훈련과정명 입력" class="w100 profile-name" disabled />
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">NCS 분류</th>
									<td class="left" colspan="8">
										<div class="flex-box">
											<input type="text" id="dty-cl" title="NCS 분류 코드 선택" value="${cnsl.dtyCl }" class="w100" >
											<input type="text" id="dty-cl-nm" title="NCS 분류 코드 이름" value="${cnsl.dtyClNm }" class="w100" >
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련시간</th>
									<td class="left" colspan="8">
										<div class="flex-box">
											<input type="text" id="" name="totalTime" value="${data.totalTime[0] }" title="훈련시간 입력" class="w50 mr5 profile-total-time" disabled />
											<span>시간</span>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련근로자</th>
									<td class="left" colspan="8">
										<div class="flex-box">
											<input type="text" id="edugroup-number-trainee" name="numTrainee" value="${data.numTrainee[0] }" title="훈련근로자 입력" class="w50 mr5" disabled />
											<span>명</span>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">훈련 직무</th>
									<td class="left" colspan="8">
										<input type="text" id="program-description" name="programDescription" value="${data.programDescription[0] }" title="훈련 직무 입력" class="w100"  />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(기업명/사업장관리번호/주요 업종/주소/훈련실시주소/관할 지부·지사) <span class="point-color02 unberline">신청서 기준</span>으로 자동 불러옴 처리되며, <span class="point-color02 unberline">내용 수정이 불가</span>함.
							</li>
							<li>
								(담당자 연락처/NCS 분류) <span class="point-color02 unberline">신청서 기준</span>으로 자동 불러옴 처리되며, <span class="point-color02 unberline">내용 수정 가능</span>함.
							</li>
							<li>
								(훈련과정명/훈련시간/훈련근로자) <span class="point-color02 unberline">훈련 프로파일 기준</span>으로 자동 불러옴 처리되며, <span class="point-color02 unberline">내용 수정이 불가</span>함.
							</li>
							<li>
								(훈련직무) <span class="point-color02 unberline">직접 입력</span>해야 함.
							</li>
						</ul>
					</div>
				</div>
			</div>
			
			<!-- page2 -->
			<div class="page hidden" id="page2">
				<h3 class="title-type01 ml0">
				컨설팅 개요
				</h3>
				<div class="contents-box">
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								본 장은, Ⅲ장 이하의 내용(과정개발, 컨설팅 수행일지 등)을 완료한 후에 작성 가능함. 대다수 항목은 작성한 내용을 기반으로 자동 불러옴 처리할 예정임.
							</li>
						</ul>
					</div>
				</div>
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
										<!-- 
										<ul class="ul-list01">
											<li>&nbsp;</li>
										</ul>
										-->
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
						<textarea id="program-necessity" name="programNecessity"  cols="50" rows="5" placeholder="(작성 예시) · HRD기초진단컨설팅 결과, 추천훈련사업 1순위는 ○○○(업무명)에 대한 체계적 현장훈련임.
· 훈련 요구분석 또한 ○○○에 대한 훈련이 가장 필요한 것으로 나타났으며, 기업 경영 이슈 해결에 본 과정이 일부 기여할 것으로 판단하여 선정됨.">${data.programNecessity[0] }</textarea>
					</div>
				</div>
				<div class="contents-box">
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								HRD기초진단컨설팅에서 제시된 결과와 연계하여 작성 필요함
							</li>
							<li>
								HRD기초진단컨설팅 외에도 <span class="point-color02">‘Ⅲ. 훈련 요구분석’에서 파악한 내용(기업 경영 이슈, 훈련대상 업무 등)을 과정개발의 필요성으로 함께 제시함.</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="contents-box">
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
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법) <span class="point-color02 underline">작성한 ‘컨설팅 수행일지’의 주요 내용을 기준</span>으로 자동 불러옴 처리되며, <span class="point-color02 underline">내용 수정이 불가</span>함.
							</li>
							<li>
								(목적) 본 과정이 어떠한 활동을 통해 개발되었는지 한눈에 볼 수 있도록 도식화 하여 기업의 이해를 도움.
							</li>
						</ul>
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
											<p><c:out value="${item }" /></p>
										</c:forEach>
									</td>
									<td class="profile-name"><c:out value="${data.profileName[0] }" /></td>
									<td class="profile-total-time"><c:out value="${data.profileTotalTime[0] }" /></td>
									<td class="profile-method"><c:out value="${data.profileMethod[0] }" /></td>
									<td class="profil-result"><c:out value="${data.profileResult[0] }" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법)
								<ul>
									<li class="line-bullet">
										‘훈련대상 업무 선정 결과’는 Ⅲ. 훈련 요구분석의 2. 훈련대상 업무<span class="point-color02 underline">불가</span>함.
									</li>
									<li class="line-bullet">
										‘훈련과정명’은 Ⅳ. 훈련 과정 개발의 1. 훈련 교과목 프로파일 중 <span class="point-color02 underline">과정명</span>에서 자동 불러옴 처리되며, <span class="point-color02 underline">내용 수정이 불가</span>함.
									</li>
								</ul>
							</li>
							<li>
								(목적) 컨설팅의 핵심 결과물인 훈련대상 업무와 훈련과정명을 제시하여, 기업이 본 항목만 봐도 어떤 업무에 대해 무슨 훈련을 받는지 확인할 수 있음.
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="page hidden" id="page3">
				<h3 class="title-type01 ml0">훈련 요구분석</h3>
				<div class="contents-box">
					<h4 class="title-type02">
						기업 현황 분석
					</h4>
					<h5 class="title-type05">
						가. 기업 경영 이슈
					</h5>
					<div class="text-box">
						<textarea id="business-issue" name="businessIssue" cols="50" rows="5" placeholder="" class="w100" >${data.businessIssue[0] }</textarea>
					</div>
					<div class="gray-box01 mt20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법) 기업담당자와의 인터뷰를 통해 기업의 내·외부 환경에 대해 파악하고 기업이 마주한 어려움이 무엇인지를 작성함. (예. “기업이 속한 산업에서 주요 변화가 있었나요?”, “그로 인해 당면한 문제는 어떤 것들이 있나요?”, <span class="point-color02">“문제해결이 필요한 부서 또는 업무가 있나요?”</span>)
							</li>
							<li>
								(목적) 훈련대상 업무 선정 시 기업의 전략적 요구가 반영될 수 있도록 기업의 경영 이슈 파악
							</li>
						</ul>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type05">나. 조직 및 주요 업무</h5>
					<h5 class="title-type03" style="color:#333333;">조직도</h5>											
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="30%">
								<col width="auto">
							</colgroup>
							<tbody>
								<tr class="organ-group" id="organ-group-0">
									<th class="organ-group-header" scope="row" rowspan="1"><input type="checkbox" id="checkbox00" name="checkboxOrgan" value="Y" class="checkbox-type01 organ-check" checked></th>
									<th scope="row" rowspan="1">
										부서(팀)명
										<div class="people-add-box">
											<button type="button" class="add add-organ-team">추가</button>
											<button type="button" class="delete delete-organ-team">삭제</button>
										</div>
									</th>
									<td>
										<input type="text" class="organ-group-00-team w100 nullable" id="organ-group-00-team-00" name="organGroup_00_team" value="" />
									</td>
								</tr>
								<tr>
									<td scope="row" colspan="3">
										<div class="people-add-box">
											<button type="button" class="add add-organ-group">그룹 추가</button>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="gray-box01 mt20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법) 공정분석 대신 조직도를 기반으로 업무 현황을 파악함. NCS 능력단위(요소) DB를 활용하여 부서(팀)별 업무를 작성함. 
							</li>
							<li>
								(목적) 훈련<span class="point-color02">대상</span> 업무 선정 시 활용하기 위한 기초 정보 조사
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="page hidden" id="page4">
				<h3 class="title-type01 ml0">훈련 요구분석</h3>
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
								<tr>
									<td>영상 데이터 수집</td>
									<td>
										<input type="radio" class="radio-type01" id="radio0101" name="radio01" value="" >
									</td>
									<td>
										<input type="radio" class="radio-type01" id="radio0102" name="radio01" value="" >
									</td>
									<td>
										<input type="radio" class="radio-type01" id="radio0103" name="radio01" value="" >
									</td>
									<td>
										<input type="radio" class="radio-type01" id="radio0104" name="radio01" value="" >
									</td>
									<td>
										<input type="radio" class="radio-type01" id="radio0105" name="radio01" value="" >
									</td>
									<td>
										<input type="checkbox" id="checkbox0101" name="checkbox01" value="" class="checkbox-type01" disabled checked="">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="text-box mt20">
						<textarea id="" name="targetRemark" cols="50" rows="5" placeholder="" class="w100" >${data.targetRemark[0] }</textarea>
					</div>
					<div class="gray-box01 mt20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법)
								<ul>
									<li class="line-bullet">
										‘업무명’은 <span class="point-color02 underline">조직도에서 선택한 부서명을 기반</span>으로 자동 불러옴 처리하며, <span class="point-color02 underline">내용 수정이 불가</span>함. 
									</li>
									<li class="line-bullet">
										‘훈련과정 개발 필요성’은 업무별로 판단하며, △기업 경영 이슈와 연계되는지, △교육훈련 및 평가가 가능한 업무인지, △교육훈련이 시급한 업무인지, △기존에 개발되어 있는 훈련과정을 활용 가능한 업무인지 등을 종합적으로 고려해야 함.
									</li>
									<li class="line-bullet">
										‘훈련대상 업무 선정 여부’는 최종적으로 기업담당자와 논의하여 선정함.
									</li>
								</ul>
							</li>
							<li>
								(목적) 조직, 개인, 업무, 환경의 요구사항을 반영하여 훈련과정 개발 우선순위를 선정
							</li>
						</ul>
					</div>
				</div>
				<h3 class="title-type01 ml0">훈련 요구분석</h3>
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
							</tbody>
						</table>
					</div>
					<div class="gray-box01 mt20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법)
								<ul>
									<li class="line-bullet">
										‘업무명’은 <span class="point-color02 underline">훈련대상 업무 선정 여부에서 선택된 업무</span>를 자동 불러옴 처리하며, <span class="point-color02 underline">내용 수정이 불가</span>함.
									</li>
									<li class="line-bullet">
										‘세부내용/지식/기술’은 해당 업무를 수행하기 위해 어떠한 과업(task)을 수행해야 하는지, 어떤 지식과 기술이 요구되는지 인터뷰를 통해 도출함. 2개 이상의 과업으로 이루어지는 경우 행 추가하여 작성 필요함.
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="page hidden" id="page5">
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
											<input type="text" id="" name="properTrainingTime" value="${data.properTrainingTime[0] }" title="" class="w50 mr5" >
											<span>시간</span>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">적정 훈련장소</th>
									<td>
										<input type="checkbox" id="checkbox0101" name="checkboxProperLocation" value="Y" class="radio-type01" ${data.checkboxProperLocation[0] eq 'Y' ? 'checked' : '' }>
										<label for="checkbox0101">사내</label>
									</td>
									<td colspan="3">
										<div class="flex-box">
											<span class="mr10">* 훈련장소</span>
											<input type="text" id="" name="" value="(${cnsl.trOprtnRegionZip }) ${cnsl.trOprtnRegionAddr } ${cnsl.trOprtnRegionAddrDtl}" title="" class="w80" disabled>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" id="checkbox0102" name="checkboxProperLocation" value="Y" class="checkbox-type01" ${data.checkboxProperLocation[1] eq 'Y' ? 'checked' : '' }>
										<label for="checkbox0102">사외</label>
									</td>
									<td colspan="3">
										<p class="word-type02 left mb10">* 훈련장소 특이사항</p>
										<textarea  id="" class="w100" name="properLocationReason" cols="50" rows="5" placeholder="추가 훈련장 필요 사유 기재 (예 : 승강기 정비 업무를 수행하는 작업 현장은 서울 00곳에 분포되어 있으며, 하나의 장소로 한정할경우 원활한 현장훈련 실시가 어려움.)">${data.properLocationReason[0] }</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">사내 강사 활용 여부</th>
									<td rowspan="2">
										<input type="radio" id="checkbox0101" name="insiderTeacherYN" value="Y" class="checkbox-type01" ${data.insiderTeacherYN[0] eq 'Y' ? 'checked': '' }>
										<label for="checkbox0101">예</label>
									</td>
									<th scope="col">이름</th>
									<td>
										<input type="text" id="" name="insiderTeacherName" value="${data.insiderTeacherName[0] }" title="" class="w100"  ${data.insiderTeacherYN[0] eq 'Y' ? 'required': '' }>
									</td>
									<td rowspan="2">
										<input type="radio" id="checkbox0102" name="insiderTeacherYN" value="N" class="checkbox-type01" ${data.insdierTeacherYN[0] eq 'N' ? 'checked' : '' }>
										<label for="checkbox0101">아니오</label>
									</td>
								</tr>
								<tr>
									<th scope="col">직책</th>
									<td>
										<input type="text" id="" name="insiderTeacherPosition" value="${data.insiderTeacherPosition[0] }" title="" class="w100"  ${data.insiderTeacherYN[0] eq 'Y' ? 'required': '' }>
									</td>
								</tr>
								<tr>
									<th scope="col">대상 인원</th>
									<td colspan="4">
										<div class="flex-box">
											<input type="text" id="" name="numberTrainees" value="${data.numberTrainees[0] }" title="" class="w50 mr5" >
											<span>명</span>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col">대상자 특성</th>
									<td colspan="4">
										<textarea  id="" class="w100 h100" name="targetOverview" cols="50" rows="5" placeholder="· (업무 경력) 예. 직책별(사원/대리급, 과·차장급 등), 연차(1~2년차 등)
· (수준) 예. 기초-실무-심화 등">${data.targetOverview[0] }</textarea>
									</td>
								</tr>
								<tr>
									<th scope="col" colspan="2">훈련 요구분석 결과</th>
									<td colspan="4">
										<textarea  id="requirement" class="w100" name="trainingRequirement" cols="50" rows="5" placeholder="· ㈜○○○는 CCTV 운영 시스템 시장을 타겟으로 사업을 진행하고 있음.
최근 CCTV의 질적 고도화가 트렌드로 떠오름에 따라 초고화질 카메라로의 교체, 지능형 솔루션의 도입 등 관련 신기술의 활용 수준이 매우 중요해질 것으로 보임.
반면, 사내 관련 훈련경험이나 전문가 양성을 위한 교육체계 등 훈련을 위한 기반이 미흡함에 따라 HRD 진단, 교육 및 훈련과정 개발을 진행할 필요성을 느끼고 있음. 특히 CCTV 영상 데이터 분석을 통해 영상 패턴을 확인하고 이를 바탕으로 새로운 추천 서비스를 개발하여, ㈜○○○의 질적·양적 성장을 도모할 필요가 있음.">${data.trainingRequirement[0] }</textarea>
									</td>
								</tr>
								<tr>
									<th scope="col" colspan="2" rowspan="2">훈련을 통한 기대효과</th>
									<th scope="col" colspan="3">As-is (현재 상황)</th>
									<th scope="col">To-be (원하는 상황)</th>
								</tr>
								<tr>
									<td colspan="3">
										<textarea  id="" class="w100 h100" name="expectationASIS" cols="50" rows="5" placeholder="·">${data.expectationASIS[0] }</textarea>
									</td>
									<td>
										<textarea id="" class="w100 h100" name="expectationTOBE" cols="50" rows="5" placeholder="·">${data.expectationTOBE[0] }</textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="gray-box01 mt20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법)
								<ul>
									<li class="line-bullet">‘훈련 대상자 특성’은 훈련업무를 수행하는 직원들의 특징 기술</li>
									<li class="line-bullet">‘훈련 여건’은 훈련과정 개발·운영을 위해 활용 가능한 기업 자원을 파악</li>
									<li class="line-bullet">
										<span class="point-color02">‘훈련을 통한 기대효과’는 업무수행 시 애로사항(As-is)과 해당 업무를 우수하게 수행하기 위해 요구되는 수준(To-be)을 작성(gap 분석)하고, 이를 훈련 목표 및 내용에 연계</span>
									</li>
								</ul>
							</li>
							<li>
								(목적) 과정개발 시 기업의 훈련에 대한 요구사항을 반영하기 위함. 
							</li>
						</ul>
					</div>
				</div>
			</div>
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
									<td class="profile-name"></td>
								</tr>
								<tr>
									<th scope="col">훈련기간</th>
									<td><input type="text" id="profile-period" name="profilePeriod" class="w100 nullable" value="${data.profilePeriod[0] }" /></td>
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
									<th scope="col">역할</th>
									<th scope="col">소속(부서)</th>
									<th scope="col">직위</th>
									<th scope="col">성명</th>
								</tr>
							</thead>
							<tbody id="edugroup">
								<tr class="edugroup-trainer-outer-header">
									<th id="trainer-total-header" scope="row" rowspan="2">훈련 교사</th>
									<td id="trainer-outer-header" rowspan="1">
										외부
										<div class="people-add-box">
											<button id="edugroup-trainer-outer-add-btn" type="button" class="add">추가</button>
											<button id="edugroup-trainer-outer-delete-btn" type="button" class="delete">삭제</button>
										</div>
									</td>
									<td>
										<select id="edugroup-outer00-role" name="edugroupOuterRole" class="w100" >
											<option value="팀장" selected>팀장</option>
											<option value="팀원">팀원</option>
										</select>
									</td>
									<td><input id="edugroup-outer00-team" name="edugroupOuterTeam" type="text" class="w100 nullable" ></td>
									<td><input id="edugroup-outer00-position" name="edugroupOuterPosition" type="text" class="w100 nullable" ></td>
									<td><input id="edugroup-outer00-name" name="edugroupOuterName" type="text" class="w100 nullable" ></td>
								</tr>
								<tr class="edugroup-trainer-inner-header">
									<td id="trainer-inner-header" rowspan="1">
										내부
										<div class="people-add-box">
											<button id="edugroup-trainer-inner-add-btn" type="button" class="add">추가</button>
											<button id="edugroup-trainer-inner-delete-btn" type="button" class="delete">삭제</button>
										</div>
									</td>
									<td>
										<select id="edugroup-inner00-role" name="edugroupInnerRole" class="w100" >
											<option value="팀장" selected>팀장</option>
											<option value="팀원">팀원</option>
										</select>
									</td>
									<td><input type="text" id="edugroup-inner00-team" name="edugroupInnerTeam" class="w100 nullable" ></td>
									<td><input type="text" id="edugroup-inner00-position" name="edugroupInnerPosition" class="w100 nullable" ></td>
									<td><input type="text" id="edugroup-inner00-name" name="edugroupInnerName" class="w100 nullable" ></td>
								</tr>
								<tr class="edugroup-trainee-header">
									<th id="trainee-total-header" scope="row" rowspan="1">훈련근로자</th>
									<td id="trainee-inner-header" rowspan="1">
										내부
										<div class="people-add-box">
											<button id="edugroup-trainee-add-btn" type="button" class="add">추가</button>
											<button id="edugroup-trainee-delete-btn" type="button" class="delete">삭제</button>
										</div>
									</td>
									<td>
										<select id="edugroup-trainee00-role" name="traineeRole" class="w100" >
											<option value="팀장" selected>팀장</option>
											<option value="팀원">팀원</option>
										</select>
									</td>
									<td><input type="text" id="edugroup-trainee00-team" name="traineeTeam" class="w100 nullable" ></td>
									<td><input type="text" id="edugroup-trainee00-position" name="traineePosition" class="w100 nullable" ></td>
									<td><input type="text" id="edugroup-trainee00-name" name="traineeName" class="w100 edugroup-trainee-name nullable"  required></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<h5 class="title-type05">
						<div class="people-add-wrapper">
							라. 훈련 교과목 프로파일
							<div class="people-add-box">
								<span style="font-size:16px;margin-right:8px;text-decoration:underline;">프로파일 작성 후 유사도 검사를 진행해주세요.</span>
								<a href="javascript:void(0);" class="btn-m03 btn-color02" onclick="checkPBL();">유사도 검사</a>
							</div>
						</div>
					</h5>
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="10%">
								<col width="auto">
								<col width="auto">
								<col width="auto">
								<col width="17%">
								<col width="15%">
							</colgroup>
							<tbody id="profile">
								<tr>
									<th scope="col">과정명</th>
									<td colspan="2">
										<input type="text" id="profile-name" name="profileName" value="${data.profileName[0] }" title="" class="w100" >
									</td>
									<th scope="col">총 훈련시간(h)</th>
									<td colspan="2">
										<input type="text" id="profile-total-time" name="profileTotalTime" value="${data.profileTotalTime[0] }" title="" class="w100 profile-total-time" disabled>
									</td>
								</tr>
								<tr id="profile-header-start">
									<th scope="col">훈련목표</th>
									<td colspan="5">
										<textarea  id="profile-target" class="w100 h100" name="profileTarget" cols="50" rows="5" placeholder="">${data.profileTarget[0] }</textarea>
									</td>
								</tr>
								<tr class="profile-header">
									<th scope="row" rowspan="4">훈련내용</th>
									<th scope="row" rowspan="2">업무(단원)명</th>
									<th scope="row" rowspan="2">세부 내용</th>
									<th scope="row" rowspan="2">훈련 시간(H)</th>
									<th scope="col" colspan="2">교사 투입시간(H)</th>
								</tr>
								<tr class="profile-header">
									<th scope="col">외부</th>
									<th scope="col">내부</th>
								</tr>
								<tr>
									<th scope="row">유사도 검사 결과</th>
									<td>
										<span id="similar-rate">${data.plagiarismRated[0] }</span>
										<input type="hidden" id="plagiarism-rate" name="plagiarismRated" value="${data.plagiarismRated[0] }" />
										<input type="hidden" id="plagiarism-rate" name="plagiarismRate" value="${data.plagiarismRate[0] }" />
									</td>
									<td colspan="3"></td>
								</tr>
								<tr class="profile-footer">
									<td colspan="2">전체시간</td>
									<td class="profile-total-time"></td>
									<td></td>
									<td></td>
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
										<select id="profile-method" name="profileMethod" class="w100" >
											<option value="포트폴리오" ${data.profileMethod[0] eq '포트폴리오' ? 'selected' : '' }>
												포트폴리오
											</option>
											<option value="문제해결시나리오" ${data.profileMethod[0] eq '문제해결시나리오' ? 'selected' : '' }>
												문제해결시나리오
											</option>
											<option value="사례연구" ${data.profileMethod[0] eq '사례연구' ? 'selected' : '' }>
												사례연구
											</option>
										</select>
									</td>
									<td>
										<input type="text" id="profile-result" name="profileResult" value="${data.profileResult[0] }" title="" class="w100" >
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
					<div class="gray-box01 mt20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								(작성방법)
								<ul>
									<li class="line-bullet">
										‘과정명’, ‘총 훈련시간’, ‘훈련목표’는 PM 및/또는 외부전문가가 개발한 내용을 기반으로 작성함. 훈련목표의 경우, Ⅲ장 기업 훈련요구에서 파악한 ‘훈련을 통한 기대효과’의 내용을 반영해야 함.
									</li>
									<li class="line-bullet">
										‘업무(단원)명’의 필수 작성항목은 △훈련대상 업무, △결과 공유 및 피드백임.
									</li>
									<li class="line-bullet">
										‘세부 내용’은 기업에 특화된 핵심 훈련내용을 상세히 작성하며, PBL 운영계획서의 ‘단원별 목표 및 세부내용’으로 연계함. 표현은 <span class="point-color02">명사 형태로 끝맺음(예. ~~ 수집, ~~ 분석 등)</span>하며, ‘과정평가 계획’의 평가기준과 연계될 수 있도록 함.
									</li>
									<li class="line-bullet">
										‘훈련시간’은 업무(단원)명별 훈련이 필요한 시간을 기재함.
									</li>
									<li class="line-bullet">
										‘교사 투입시간’은 단원별 목표를 달성하기 위해 요구되는 외부/내부 전문가의 투입시간을 작성함. 투입 가능한 최대시간이 아닌 목표달성을 위해 요구되는 최소한의 시간을 기준으로 작성해야 함.
									</li>
									<li class="line-bullet">
										‘전체시간’은 업무(단원)명별 훈련시간을 기재하면 자동 산출되며, 내용 수정이 불가함.
									</li>
									<li class="line-bullet">
										‘과정평가’는 수행과정을 주요 평가대상으로 설정하는 평가를 의미함. 평가방법은 포트폴리오, 문제해결시나리오, 사례연구 중 선택하며, 평가자 체크리스트로 평가표를 개발함.
									</li>
									<li class="line-bullet">
										‘결과평가’는 수행결과를 주요 평가대상으로 설정하는 평가를 의미함. 만족도·성취도·현업적용도를 웹 기반 설문조사로 실시하며, 조사 대상/내용/ 시기는 수정 불가함.
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<h3 class="title-type01 ml0">PBL 운영계획 수립</h3>
				<h4 class="title-type02 ml0">운영계획서</h4>
				<div class="contents-box">
					<div class="people-add-wrapper">
						<h5 class="title-type05">마. 시설·장비</h5>
						<div class="people-add-box">
							<button id="facility-add-btn" type="button" class="add">추가</button>
							<button id="facility-delete-btn" type="button" class="delete">삭제</button>
						</div>
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
								<tr class="facility-item">
									<td><input type="text" id="facility-idx-0" name="" value="1" title="" class="w100"></td>
									<td>
										<select id="facility-type-0" name="facilityType" class="w100" >
											<option value="시설">시설</option>
											<option value="장비">장비</option>
										</select>
									</td>
									<td><input type="text" id="facility-name-0" name="facilityName" value="" title="" class="w100 nullable" ></td>
									<td><input type="text" id="facility-spec-0" name="facilitySpec" value="" title="" class="w100 nullable" ></td>
									<td><input type="text" id="facility-location-0" name="facilityLocation" value="" title="" class="w100 nullable" ></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="people-add-wrapper">
						<h5 class="title-type05">바. 훈련교사</h5>
						<div class="people-add-box">
							<button id="trainer-add-btn" type="button" class="add">추가</button>
							<button id="trainer-delete-btn" type="button" class="delete">삭제</button>
						</div>
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
								<tr class="trainer-item">
									<td><input type="text" id="trainer-name-0" name="trainerName" value="" title="" class="w100" ></td>
									<td>
										<select id="trainer-location-0" name="trainerLocation" class="w100" >
											<option value="내부">내부</option>
											<option value="외부">외부</option>
										</select>
									</td>
									<td><input type="text" id="trainer-experience-0" name="trainerExperience" value="00년" title="" class="w100" ></td>
									<td><input type="text" id="trainer-job-0" name="trainerJob" value="○○○" title="" class="w100" ></td>
									<td><textarea id="trainer-course-0" class="w100 h100" name="trainerCourse" cols="50" rows="5" placeholder="" ></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="page hidden" id="page7">
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
								<dd class="profile-name"></dd>
							</dl>
						</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="textfield02">평가방법</label>
								</dt>
								<dd>
									<div class="input-checkbox-wrapper ratio">
										<div class="input-checkbox-area">
											<input type="checkbox" id="checkbox0101" name="" value="포트폴리오" class="checkbox-type01 profile-method">
											<label for="checkbox0101">포트폴리오</label>
										</div>
										<div class="input-checkbox-area">
											<input type="checkbox" id="checkbox0102" name="" value="문제해결시나리오" class="checkbox-type01 profile-method">
											<label for="checkbox0102">문제해결시나리오</label>
										</div>
										<div class="input-checkbox-area">
											<input type="checkbox" id="checkbox0103" name="" value="사례연구" class="checkbox-type01 profile-method">
											<label for="checkbox0103">사례연구</label>
										</div>
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
										<input type="text" id="evaluation-date" name="evaluationDate" value="${data.evaluationDate[0] }" title="" class="w100">
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
										<input type="text" id="evaluation-standard" name="evaluationStandard" value="${data.evaluationStandard[0] }" title="" placeholder="14개 중 수행 수준 3 이상 8개(60%) 이상시 PASS" class="w100">
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
										<div class="people-add-box">
											<button id="evaluation-add-btn" type="button" class="add">추가</button>
											<button id="evaluation-delete-btn" type="button" class="delete">삭제</button>
										</div>
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
								<tr>
									<th scope="col">총평</th>
									<td class="left" colspan="6">
										<textarea id="" class="w100" name="targetsFinal" cols="50" rows="5" placeholder="(작성 예시) 
- 위 훈련근로자는 모든 평가지표에서 양호한 결과를 얻어 Pass함. (전반적 평가결과) 
- 분석계획에 따라 데이터를 분류하고 시각화 하는 능력과 필요한 의미를 도출하는 능력 우수함. (단원별 주요 평가결과)
- 기존에 데이터 양이 방대하여 분석의 어려움을 겪고 있었으나, 본 훈련과정을 통해 빅데이터의 분석 기법을 이해하고 적용할 수 있었음. (as-is – to-be)">${data.targetsFinal[0] }</textarea>
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
				<div class="contents-box">
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
					<h5 class="title-type03" style="color:#333333;">현업적용도 조사</h5>
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
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio11" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio11" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio11" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio11" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio11" value=""  disabled></td>
								</tr>
								<tr>
									<td class="left">훈련생은 본 훈련과정에서 습득한 지식 및 기술을 실무에 적용하였습니까?</td>
									<td><input type="radio" class="radio-type01" id="radio0101" name="radio12" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0102" name="radio12" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0103" name="radio12" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0104" name="radio12" value=""  disabled></td>
									<td><input type="radio" class="radio-type01" id="radio0105" name="radio12" value=""  disabled></td>
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
			<div class="page hidden" id="page8">
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
										<input type="text" id="" name="" value="" title="" class="w100 profile-name" disabled>
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
										<div class="people-add-box">
											<button id="diary-add-btn" type="button" class="add">추가</button>
											<button id="diary-delete-btn" type="button" class="delete">삭제</button>
										</div>
									</th>
									<th scope="col">수행내용</th>
								</tr>
							</thead>
							<tbody id="diary">
								<tr class="diary-item">
									<th scope="col">
										<input type="text" id="diary-month-0" class="w100 diary-month nullable" name="diaryMonth" placeholder="${cnsl.regiDate }">
									</th>
									<td class="left">
										<textarea id="diary-cn-0" class="w100 h100 diary-cn nullable" name="diaryCN" cols="50" rows="5" placeholder=""></textarea>
									</td>
								</tr>
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
										<input type="text" id="" name="" value="" title="" class="w100 profile-name" disabled>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="people-add-wrapper flex-right">
						<div class="people-add-box">
							<button type="button" id="imgfile-add" class="add">추가</button>
							<button type="button" id="imgfile-delete" class="delete">삭제</button>
						</div>
					</div>
					<div class="table-type02 horizontal-scroll mt10">
						<table class="width-type02">
							<colgroup>
								<col width="5%">
								<col width="30%">
								<col width="auto%">
							</colgroup>
							<tbody id="imgfiles">
								<tr>
									<th scope="col">연번</th>
									<th scope="col">산출물 유형</th>
									<th scope="col">결과물 예시</th>
								</tr>
								<c:set var="i" value="1" />
								<c:forEach var="file" items="${report.files}">
								<c:if test="${fn:startsWith(file.itemId, 'img_file')}">
								<tr class="imgfile-item">
									<td>${i}</td>
									<td>
										<div class="fileBox">
											<input type="text" id="fileName0${i}" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file0${i}" class="btn_file" name="img_file" onclick="addFile(event, ${i}, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-${i}">	
												<p class="ml0 mt10">
													<input type="file" name="img_file${i }" class="hidden" />
													<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
														<span class="mr05">${file.fileOriginName}</span>
													</a>
													<button type="button" class="btn-m03 btn-color02" onclick="deleteFile(this);">삭제</button>
												</p>
											</div>
										</div>
									</td>
									<td>
										<c:if test="${fn:startsWith(file.itemId, 'img_file')}">
										<p class="attached-file">
											<img src="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
										</p>
										</c:if>
									</td>
								</tr>
								</c:if>
								<c:set var="i" value="${i+1 }" />
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents-box">
					<div class="table-type02 horizontal-scroll mt10">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="auto">
								<col width="20%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col">연번</th>
									<th scope="col">산출물 유형</th>
									<th scope="col">결과물 예시</th>
								</tr>
								<c:set var="i" value="0" />
								
								
								<tr>
									<td>1</td>
									<td>
										<div class="fileBox">
											<input type="text" id="fileName06" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file06" class="btn_file" name="pbl_file" onclick="addFile(event, 6, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-6">
												<c:forEach var="file" items="${report.files}">
												<c:if test="${file.itemId eq  'pbl_file'}">	
												<p class="ml0 mt10">
													<input type="file" name="pbl_file" class="hidden" />
														<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
															<span class="mr05">${file.fileOriginName}</span>
														</a>
													<button type="button" class="btn-m03 btn-color02" onclick="deleteFile(this);">삭제</button>
												</p>
												</c:if>
												</c:forEach>		
											</div>
										</div>
									</td>
									<td>
										<c:forEach var="file" items="${report.files}">
										<c:if test="${file.itemId eq  'pbl_file'}">
										<p class="attached-file">
											<a href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}">
												<span class="mr05">${file.fileOriginName}</span>
											</a>
										</p>
										</c:if>
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form>
		
		<div class="paging-navigation-wrapper">
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

		<div class="fr mt50">
			<a href="javascript:void(0);" class="btn-m01 btn-color03 save" onclick="sendReport(5);">저장</a>
			<a href="javascript:void(0);" class="btn-m01 btn-color03 save" onclick="sendReport(10);">제출</a>
			<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
		</div>
	</div>
</div>
<div class="toast" id="toast" style="display:none;">저장 완료!</div>
<script src=""></script>
<script>
	var organization = []
	var team = []
	var json_obj;
	var toast = document.querySelector("div#toast");
	var profile = {
			targets: [],
			name: '',
			requirement: '',
			time: 0,
			time_outer: 0,
			time_inner: 0,
			numTrainee: 0,
			plagiarism: { 'rate': 100, }
	}
	var profileProxy = new Proxy(profile, {
		set(target, property, value) {
			target[property] = value;
			if(property === 'name') {
				// 과정명 입력
				let pe_ = document.querySelectorAll('.profile-name')
				Array.from(pe_).forEach(e => {
					if(e.tagName === 'INPUT') {
						e.value = value;
					} else {
						e.textContent = value;
					}
				})
			} else if(property === 'targets') {
				let td_e = document.querySelector('td#targets-join-list');
				// 훈련대상 과정 입력
				td_e.textContent = value.map(e => e.value).join(', ');
				
				// 훈련대상 과정으로 테이블 만들기
				setTargetDetail();
				
				// 과정평가 계획 테이블 만들기
				updateEvaluationList();
			} else if(property === 'time') {
				let times_e = document.querySelectorAll('.profile-total-time');
				Array.from(times_e).forEach(e => {
					if(e.tagName === 'INPUT') {
						e.value = value;
					} else {
						e.textContent = value;
					}
				})
			} else if(property === 'time_outer') {
				const e_ = document.querySelector('td#profile-time-outer')
				e_.textContent = value;
			} else if(property === 'time_inner') {
				const e_ = document.querySelector('td#profile-time-inner')
				e_.textContent = value;
			} else if(property === 'requirement') {
				if(value == null || value === '') return true;
				const text_ = value.split('\n').map(item => {
					const pe_ = document.createElement('p')
					pe_.classList.add('word-type02')
					pe_.classList.add('mb10')
					pe_.textContent = item;
					return pe_;
				})
				text_[text_.length-1].classList.remove('mb10')
				const background_ = document.querySelector('div#background')
				background_.innerHTML = '';
				text_.forEach(txt => background.appendChild(txt))
			} else if(property === 'numTrainee') {
				const elem_ = document.querySelector('input#edugroup-number-trainee');
				elem_.value = value;
			} else if(property === 'plagiarism') {
				console.log(`plagiarism set... \${value}`)
				const { rate } = value;
				const rate_e = document.querySelector('span#similar-rate')
				const plagiarism_input = document.querySelector('input#plagiarism-rate');
				const rate_value = rate ? `\${parseFloat(rate).toFixed(1)}%` : '-';
				rate_e.textContent = rate_value;
				plagiarism_input.value = rate_value;
			} else if(property === 'method') {
				const elem = document.querySelectorAll('input.profile-method');
				elem.forEach(e => {
					if(e.value === value) {
						e.checked = true;
					} else {
						e.checked = false;
					}
				})
			}
			else {
				
			}
			return true;
		}
	})
	
	window.onload = () => {
		let json_text = '${json}'
		json_text = json_text.replaceAll('\n', '\\n')
		json_text = json_text.replaceAll('\r', '\\r')
		json_obj = JSON.parse(json_text);
		debug_data = json_obj
		loadSavedData();
		let result = init(json_obj);
		render(result)
		setEvents();
		// organization에서 checked 된 group 찾기
		updateTeam() 
		updateTargets();
		
		// profileProxy.targets 데이터는 여기서 복원 되야함.
		// updateTeam -> updateTargets 가 실행되어야 데이터가 생김.
		targetedDOMupdate();
		
		// profile 이름
		let profile_name_input = document.querySelector('input#profile-name')
		profile_name_input.addEventListener('change', e => {
			profileProxy.name = e.target.value;
		})
		
		initList(json_obj);
		
		// 초기 profile 세팅
		const { plagiarismTotal, plagiarismRate, plagiarismSimilar, plagiarismMatched, plagiarismSource, profileMethod, plagiarismRated } = json_obj;
		const plagiarism = {'total': plagiarismTotal ? plagiarismTotal?.[0] : plagiarismTotal, 'rate': plagiarismRate ? plagiarismRate?.[0] : plagiarismRated?.[0], 'similar': plagiarismSimilar ? plagiarismSimilar[0] : plagiarismSimilar, 'source': plagiarismSource ? plagiarismSource[0] : plagiarismSource, 'matched': plagiarismMatched ? plagiarismMatched[0] : plagiarismMatched}
		console.log(plagiarism)
		profileProxy.plagiarism = plagiarism;
		
		profileProxy.method = profileMethod?.[0];
		
	}
	
	function targetedDOMupdate() {
		// radioTarget으로 시작하는 key 꺼내기
		let filtered = Object.keys(json_obj).filter(e => e.startsWith('radioTarget'));
		// filtered에서 radio 찾아서 check 하기
		filtered.forEach(e => {
			const class_name = e;
			const inputs_ = document.querySelectorAll(`input.\${class_name}`);
			const value = json_obj[e].filter(k => k !== '-')[0]
			Array.from(inputs_).filter(item => item.value === value).forEach(k => k.checked = true);
		})
	}
	
	function loadSavedData() {
		const profile_name = document.querySelector('input#profile-name').value;
		profileProxy.name = profile_name;
		profileProxy.requirement = json_obj.trainingRequirement != null ? json_obj.trainingRequirement[0] : '';
	}
	
	function init(json_obj) {
		// tab#3 나. 조직 및 주요 업무
		// 데이터 받아와서 조직도 자료구조 만들기 (nested array)
		let filtered = Object.keys(json_obj).filter(e => e.startsWith('organGroup'));
		let organs = filtered.map(e => ({'id': parseInt(e.split('_')[1]), 'key': e}))
		organs = organs.sort((a,b) => a.id-b.id)
		let result = []
		organs.forEach(e => {
			if(result[e.id] == null) {
				result.push({'id': e.id, 'items': [], 'checked': json_obj.checkboxOrgan[e.id] === 'Y' ? true : false})
			}
			json_obj[e.key].forEach(item => result[e.id].items.push(item));
		})
		return result
	}
	
	function initList(json_obj) {
		// tab#6 다. 학습그룹 구성
		let trainees = Array.from({length: json_obj.traineeName?.length}, (_,i) => ({'name': json_obj.traineeName[i], 'position': json_obj.traineePosition[i], 'team': json_obj.traineeTeam[i], 'role': json_obj.traineeRole[i]}))
		let edugroup_outers = Array.from({length: json_obj.edugroupOuterName?.length}, (_,i) => ({'name': json_obj.edugroupOuterName[i], 'position': json_obj.edugroupOuterPosition[i], 'team': json_obj.edugroupOuterTeam[i], 'role': json_obj.edugroupOuterRole[i]}))
		let edugroup_inners = Array.from({length: json_obj.edugroupInnerName?.length}, (_,i) => ({'name': json_obj.edugroupInnerName[i], 'position': json_obj.edugroupInnerPosition[i], 'team': json_obj.edugroupInnerTeam[i], 'role': json_obj.edugroupInnerRole[i]}))
		edugroup_outers.forEach((e,i) => {
			if(i == 0) {
				let role_e = document.querySelector('select#edugroup-outer00-role')
				let team_e = document.querySelector('input#edugroup-outer00-team')
				let position_e = document.querySelector('input#edugroup-outer00-position')
				let name_e = document.querySelector('input#edugroup-outer00-name')
				role_e.value = e.role ?? '-';
				team_e.value = e.team ?? '-';
				position_e.value = e.position ?? '-';
				name_e.value = e.name ?? '-';
			} else {
				add_trainone('outer', e)
			}
		})
		edugroup_inners.forEach((e,i) => {
			if(i == 0) {
				let role_e = document.querySelector('select#edugroup-inner00-role')
				let team_e = document.querySelector('input#edugroup-inner00-team')
				let position_e = document.querySelector('input#edugroup-inner00-position')
				let name_e = document.querySelector('input#edugroup-inner00-name')
				role_e.value = e.role ?? '-';
				team_e.value = e.team ?? '-';
				position_e.value = e.position ?? '-';
				name_e.value = e.name ?? '-';
			} else {
				add_trainone('inner', e)
			}
		})
		trainees.forEach((e,i) => {
			if(i == 0) {
				let role_e = document.querySelector('select#edugroup-trainee00-role')
				let team_e = document.querySelector('input#edugroup-trainee00-team')
				let position_e = document.querySelector('input#edugroup-trainee00-position')
				let name_e = document.querySelector('input#edugroup-trainee00-name')
				role_e.value = e.role ?? '-';
				team_e.value = e.team ?? '-';
				position_e.value = e.position ?? '-';
				name_e.value = e.name ?? '-';
			} else {
				add_trainone('trainee', e)
			}
		})
		
		// 시설 장비 List 만들기
		const {facilityType, facilityName, facilitySpec, facilityLocation} = json_obj
		const facility = Array.from({length: facilityType?.length}, (_,i) => ({'rn': i+1, 'type': facilityType[i], 'name': facilityName[i], 'spec': facilitySpec[i], 'location': facilityLocation[i]}))
		facility.forEach((e,i) => {
			if(i == 0) {
				const {name, type, spec, location} = e;
				const name_e = document.querySelector('input#facility-name-0');
				const type_e = document.querySelector('select#facility-type-0');
				const spec_e = document.querySelector('input#facility-spec-0');
				const location_e = document.querySelector('input#facility-location-0');
				name_e.value = name;
				type_e.value = type;
				spec_e.value = spec;
				location_e.value = location;
			} else {
				add_facility(e);
			}
		})
		
		// 훈련교사 List 만들기
		const {trainerName, trainerLocation, trainerExperience, trainerJob, trainerCourse} = json_obj;
		const trainers = Array.from({length: trainerName?.length}, (_,i) => ({'name': trainerName[i], 'location': trainerLocation[i], 'experience': trainerExperience[i], 'job': trainerJob[i], 'course': trainerCourse[i]}));
		trainers.forEach((e,i)=>{
			if(i === 0) {
				const {name, location, experience, job, course} = e;
				const name_e = document.querySelector('input#trainer-name-0');
				const location_e = document.querySelector('select#trainer-location-0');
				const experience_e = document.querySelector('input#trainer-experience-0');
				const job_e = document.querySelector('input#trainer-job-0');
				const course_e = document.querySelector('textarea#trainer-course-0');
				name_e.value = name;
				location_e.value = location;
				experience_e.value = experience;
				job_e.value = job;
				course_e.value = course;
			} else {
				add_trainer(e);
			}
		})
		
		// 과정평가 List 만들기
		// maybe I should be clean the tr.targets-select before creating belows. let's see the output of this code.
		const targets_trs = document.querySelectorAll('tr.targets-select');
		const targets_tbody = document.querySelector('tbody#targets-evaluation')
		Array.from(targets_trs).filter((_,i)=>i!==0).forEach(e => targets_tbody.removeChild(e));
		
		const { targetsName, targetsCriteria} = json_obj;
		let filteredKey = Object.keys(json_obj).filter(e => e.startsWith('targetsEval')).sort((a,b) => parseInt(a.replace('targetsEval',''))-parseInt(b.replace('targetsEval','')));
		const selects = Array.from({length: targetsName?.length}, (_,i) => ({'name': targetsName?.[i], 'criteria': targetsCriteria?.[i]}));
		selects.forEach((e,i) => {
			if(i === 0) {
				const {name, criteria} = e;
				const name_e = document.querySelector('select#targets-select-0');
				const criteria_e = document.querySelector('input#targets-criteria-0');
				name_e.value = name;
				criteria_e.value = criteria;
				const radio_ = document.getElementsByName('targetsEval0');
				const { targetsEval0 } = json_obj;
				const evaluation_ = Array.from(json_obj[filteredKey[0]]).filter(e => e !== '-')[0]
				Array.from(radio_).filter(e => e.value === evaluation_).forEach(e => e.checked = true);
			} else {
				add_targetsSelect({...e, 'filtered': filteredKey[i]});
			}
		})
		
		// 수행일지 List 만들기
		const {diaryMonth, diaryCN} = json_obj;
		const diaries = Array.from({length: diaryMonth?.length}, (_,i) => ({'month': diaryMonth?.[i], 'cn': diaryCN?.[i]}));
		diaries.forEach((e,i)=>{
			if(i == 0) {
				const {month, cn} = e;
				const month_e = document.querySelector('input#diary-month-0')
				const cn_e = document.querySelector('textarea#diary-cn-0')
				month_e.value = month;
				cn_e.value = cn;
			} else {
				add_diary(e);
			}
		})
	}
	
	function render(result) {
		// 조직도 자료구조에서 html DOM 그리기
		organize();
		organization = organization.sort((a,b) => a.id - b.id)
		organization.forEach(e => {
			e.team = e.team.sort((a,b) => a.id - b.id)
		})
		result.forEach( (e,i) => {
			if(organization[i] == null) {
				addGroup(e.checked)
				organize();
			}
			e.items.forEach((item,j) => {
				if(organization[i].team[j] == null) {
					addTeam(i)
					organize();
				}
				organization[i].team[j].element.value = item;
			})
		})
		
		// 최초 실행에서 targets 업데이트
		let organ_checks = document.querySelectorAll('.organ-check');
		Array.from(organ_checks).filter(e => e.checked).forEach(elem => {
			let group_id_ = parseInt(elem.id.split('checkbox')[1])
			organization.forEach(item => {
				if(item.id == group_id_) {
					item.team.forEach(t_ => {
						if(t_.element.value !== '') {
							team.push({'group_id': group_id_, 'value': t_.element.value})
							updateTrainingTarget()
							}
					})
				}
			})
		})
		
		// 수행일지 첫 칸을 시작ㅇㄹ으로 고름
		const diary_e = document.querySelectorAll('tr.diary-item')[0].querySelector('input.diary-month');
		const cnsl_register_day = diary_e.placeholder;
		const d_ = cnsl_register_day.split(' ')
		const mills = `\${d_[0]}, \${d_[2]} \${d_[1]} \${d_[5]} \${d_[3]} GMT+0900`
		const date_ = new Date(mills);
		diary_e.value = `\${date_.getMonth()+1} 월`;
		
		setProfile()
	}
	
	function setEvents() {
		// 조직도 DOM에 이벤트 달기
		let add_team_btns = document.querySelectorAll('.add-organ-team');
		add_team_btns.forEach((elem, i) => {
			if(!elem.addTeamEventadded) {
				elem.addEventListener('click', e => {
					addTeam(i)
				})
				elem.addTeamEventadded = true;
			}
		})
		let delete_team_btns = document.querySelectorAll('.delete-organ-team');
		delete_team_btns.forEach((elem, i)=>{
			if(!elem.deleteTeamEventadded) {
				elem.addEventListener('click', e => {
					deleteTeam(i)
				})
				elem.deleteTeamEventadded = true;
			}
		})
		let add_organ_btn = document.querySelector('.add-organ-group');
		add_organ_btn.addEventListener('click', e=> {
			addGroup();
		})
		// checkbox에도 이벤트 걸어서 체크 될 때마다 team 배열을 갱신하도록 만들면 끝!
		let organ_checks = document.querySelectorAll('.organ-check');
		organ_checks.forEach(elem => {
			elem.addEventListener('change', e => {
				updateTeam();
				updateTrainingTarget();
			})
		})
		//요구사항 분석 event
		const requirement_textarea = document.querySelector('textarea#requirement');
		requirement_textarea.addEventListener('change', e => {
			if(e.target.value != null) {
				
			}
			profileProxy.requirement = e.target.value;
		})
		
		// profile 평가방법 event
		const s2 = document.querySelector('select#profile-method')
		s2.addEventListener('change', (e) => {
			const {value} = e.target;
			profileProxy.method = value;
		})
		
		// 시설 +-버튼
		const facility_add_btn = document.querySelector('button#facility-add-btn')
		const facility_delete_btn = document.querySelector('button#facility-delete-btn')
		facility_add_btn.addEventListener('click', (e,idx) => {
			const trs_ = document.querySelectorAll('tr.facility-item');
			if(trs_.length < 8) {
				const tr_ = document.createElement('tr')
				tr_.classList.add('facility-item');
				const row_ = `
				    <td><input type="text" id="facility-idx-\${trs_.length}" name="" value="\${trs_.length+1}" title="" class="w100"></td>
				    <td>
				        <select id="" name="facilityType" class="w100">
				            <option value="시설">시설</option>
				            <option value="장비">장비</option>
				        </select>
				    </td>
				    <td><input type="text" id="facility-name-\${trs_.length}" name="facilityName" value="" title="" class="w100"></td>
				    <td><input type="text" id="facility-spec-\${trs_.length}" name="facilitySpec" value="" title="" class="w100"></td>
				    <td><input type="text" id="facility-location-\${trs_.length}" name="facilityLocation" value="" title="" class="w100"></td>
				`;
				tr_.innerHTML = row_;
				tbody = document.querySelector('tbody#facility')
				tbody.appendChild(tr_);
			}
		})
		facility_delete_btn.addEventListener('click', e => {
			const tbody = document.querySelector('tbody#facility')
			const trs_ = tbody.querySelectorAll('tr.facility-item');
			if(trs_.length > 1) {
				const last_tr = trs_[trs_.length-1];
				tbody.removeChild(last_tr);
			}
		})
		
		// 훈련교사 +-버튼
		const trainer_add_btn = document.querySelector('button#trainer-add-btn')
		const trainer_delete_btn = document.querySelector('button#trainer-delete-btn')
		trainer_add_btn.addEventListener('click', e => {
			const trs_ = document.querySelectorAll('tr.trainer-item');
			if(trs_.length < 5) {
				const tr_ = document.createElement('tr')
				tr_.classList.add('trainer-item');
				const row_ = `
				    <td><input type="text" id="trainer-name-\${trs_.length}" name="trainerName" value="" title="" class="w100"></td>
				    <td>
				        <select id="trainer-location-\${trs_.length}" name="trainerLocation" class="w100">
				            <option value="내부">내부</option>
				            <option value="외부">외부</option>
				        </select>
				    </td>
				    <td><input type="text" id="trainer-experience-\${trs_.length}" name="trainerExperience" value="" title="" class="w100"></td>
				    <td><input type="text" id="trainer-job-\${trs_.length}" name="trainerJob" value="" title="" class="w100"></td>
				    <td><textarea id="trainer-course-\${trs_.length}" class="w100 h100" name="trainerCourse" cols="50" rows="5" placeholder=""></textarea></td>
				`;
				tr_.innerHTML = row_;
				tbody = document.querySelector('tbody#trainer')
				tbody.appendChild(tr_);
			}
		})
		trainer_delete_btn.addEventListener('click', e => {
			const tbody = document.querySelector('tbody#trainer')
			const trs_ = tbody.querySelectorAll('tr.trainer-item');
			if(trs_.length > 1) {
				const last_tr = trs_[trs_.length-1];
				tbody.removeChild(last_tr);
			}
		})
		
		//과정평가 +- 버튼 최대 15개, 항목당 최소 1개 따라서 최소 갯수는 항목 갯수
		const evaluation_add_btn = document.querySelector('button#evaluation-add-btn');
		const evaluation_delete_btn = document.querySelector('button#evaluation-delete-btn');
		evaluation_add_btn.addEventListener('click', e=>{
			const trs_ = document.querySelectorAll('tr.targets-select');
			let num_ = trs_.length
			if(profileProxy.targets.length > 0 && num_ < 15) {
				const tr_ = document.createElement('tr')
				tr_.classList.add('targets-select');
				const options_ = profileProxy.targets.map((item,j) => `<option value="\${item.value}">\${item.value}</opiton>`).join(' ')
				const row_ = `
				    <td>
			        	<select id="targets-select-\${num_}" name="targetsName" class="w100 targets-select">
			            	\${options_}
			        	</select>
			    	</td>
			    	<td class="left">
			        	<input type="text" id="targets-criteria-\${num_}" name="targetsCriteria" class="w100 nullable">
			    	</td>
			    	<td><input type="radio" class="radio-type01 targetsEval nullable" id="radio\${num_}" name="targetsEval\${num_}" value="1"></td>
			    	<td><input type="radio" class="radio-type01 targetsEval nullable" id="radio\${num_}" name="targetsEval\${num_}" value="2"></td>
			    	<td><input type="radio" class="radio-type01 targetsEval nullable" id="radio\${num_}" name="targetsEval\${num_}" value="3"></td>
			    	<td><input type="radio" class="radio-type01 targetsEval nullable" id="radio\${num_}" name="targetsEval\${num_}" value="4"></td>
			    	<td><input type="radio" class="radio-type01 targetsEval nullable" id="radio\${num_}" name="targetsEval\${num_}" value="5"></td>
				`;
				tr_.innerHTML = row_;
				const last_tr = trs_[num_-1]
				last_tr.insertAdjacentElement('afterend', tr_);
			}
		})
		evaluation_delete_btn.addEventListener('click', e=>{
			tbody = document.querySelector('tbody#targets-evaluation')
			const trs_ = tbody.querySelectorAll('tr.targets-select');
			const min = profileProxy.targets.length;
			if(trs_.length > min) {
				const last_tr = trs_[trs_.length-1];
				tbody.removeChild(last_tr);
			}
		})
		
		//학습그룹 구성 중 훈련교사 외부 +- 버튼
		//특이한 점은 header 클래스는 삭제 되지 않는다.
		const edugroup_trainer_outer_add_btn = document.querySelector('button#edugroup-trainer-outer-add-btn');
		const edugroup_trainer_outer_delete_btn = document.querySelector('button#edugroup-trainer-outer-delete-btn');
		edugroup_trainer_outer_add_btn.addEventListener('click', e=>{
			add_trainone('outer', {'role':'', 'name':'', 'team': '', 'position': ''})
		})
		edugroup_trainer_outer_delete_btn.addEventListener('click', e=>{
			const trs_ = document.querySelectorAll('tr.edugroup-trainer-outer');
			const length = trs_.length
			if(length > 0) {
				const last_tr = trs_[length-1];
				const total_header = document.querySelector('th#trainer-total-header')
				const outer_header = document.querySelector('td#trainer-outer-header')
				const total_rowspan = total_header.getAttribute('rowspan') != null ? parseInt(total_header.getAttribute('rowspan')) : 1;
				const outer_rowspan = outer_header.getAttribute('rowspan') != null ? parseInt(outer_header.getAttribute('rowspan')) : 1;
				total_header.setAttribute("rowspan", total_rowspan-1);
				outer_header.setAttribute("rowspan", outer_rowspan-1);
				last_tr.parentElement.removeChild(last_tr);
			}
		})
		// 학습그룹 구성 중 훈련교사 내부 +- 버튼
		const edugroup_trainer_inner_add_btn = document.querySelector('button#edugroup-trainer-inner-add-btn');
		const edugroup_trainer_inner_delete_btn = document.querySelector('button#edugroup-trainer-inner-delete-btn');
		edugroup_trainer_inner_add_btn.addEventListener('click', e=>{
			add_trainone('inner', {'role':'', 'name':'', 'team': '', 'position': ''})
		})
		edugroup_trainer_inner_delete_btn.addEventListener('click', e=>{
			const trs_ = document.querySelectorAll('tr.edugroup-trainer-inner');
			const length = trs_.length
			if(length > 0) {
				const last_tr = trs_[length-1];
				const total_header = document.querySelector('th#trainer-total-header')
				const inner_header = document.querySelector('td#trainer-inner-header')
				const total_rowspan = total_header.getAttribute('rowspan') != null ? parseInt(total_header.getAttribute('rowspan')) : 1;
				const inner_rowspan = inner_header.getAttribute('rowspan') != null ? parseInt(inner_header.getAttribute('rowspan')) : 1;
				total_header.setAttribute("rowspan", total_rowspan-1);
				inner_header.setAttribute("rowspan", inner_rowspan-1);
				last_tr.parentElement.removeChild(last_tr);
			}
		})
		//학습그룹 구성중 훈련근로자 +- 버튼
		const edugroup_trainee_add_btn = document.querySelector('button#edugroup-trainee-add-btn');
		const edugroup_trainee_delete_btn = document.querySelector('button#edugroup-trainee-delete-btn');
		edugroup_trainee_add_btn.addEventListener('click', e=>{
			add_trainone('trainee', {'role':'', 'name':'', 'team': '', 'position': ''})
		})
		edugroup_trainee_delete_btn.addEventListener('click', e=>{
			const trs_ = document.querySelectorAll('tr.edugroup-trainee');
			const length = trs_.length
			if(length > 0) {
				const last_tr = trs_[length-1];
				const total_header = document.querySelector('th#trainee-total-header')
				const inner_header = document.querySelector('td#trainee-inner-header')
				const total_rowspan = total_header.getAttribute('rowspan') != null ? parseInt(total_header.getAttribute('rowspan')) : 1;
				const inner_rowspan = inner_header.getAttribute('rowspan') != null ? parseInt(inner_header.getAttribute('rowspan')) : 1;
				total_header.setAttribute("rowspan", total_rowspan-1);
				inner_header.setAttribute("rowspan", inner_rowspan-1);
				last_tr.parentElement.removeChild(last_tr);
			}
		})
		
		// 수행일지 +- 버튼
		const diary_add_btn = document.querySelector('button#diary-add-btn')
		const diary_delete_btn = document.querySelector('button#diary-delete-btn')
		diary_add_btn.addEventListener('click', e => {
			const trs = document.querySelectorAll('tr.diary-item');
			const length = trs.length
			if(length < 12) {
				const tr_ = document.createElement('tr')
				tr_.classList.add('diary-item')
				const row_ = `
				    <th scope="col">
			        	<input type="text" name="diaryMonth" class="w100 diary-month" placeholder="">
			    	</th>
			    	<td class="left">
			        	<textarea id="" name="diaryCN" class="w100 h100 diary-cn nullable" name="" cols="50" rows="5" placeholder=""></textarea>
			    	</td>
				`;
				tr_.innerHTML = row_;
				const tbody = document.querySelector('tbody#diary')
				tbody.appendChild(tr_)
			}
		})
		diary_delete_btn.addEventListener('click', e => {
			const trs = document.querySelectorAll('tr.diary-item');
			const length = trs.length;
			if(length > 1) {
				const last_tr = trs[length-1];
				last_tr.parentElement.removeChild(last_tr);
			}
		})
		
		// 학습그룹 구성 훈련근로자 수 체크용 이벤트 추가
		const edugroup_trainee = document.querySelector('input#edugroup-trainee00-name')
		edugroup_trainee.addEventListener('change', e => {
			const trs_ = document.querySelectorAll('input.edugroup-trainee-name');
			const names = Array.from(trs_).filter(e => e.value != null && e.value !== '')
			profileProxy.numTrainee = names.length;
		})
		
		// 파일첨부 추가/제거 버튼 이벤트
		const file_add_btn = document.querySelector('button#imgfile-add')
		const file_delete_btn = document.querySelector('button#imgfile-delete')
		file_add_btn.addEventListener('click', e=>{
			const tbody_ = document.querySelector('tbody#imgfiles');
			const trs_ = document.querySelectorAll('tr.imgfile-item');
			const len_ = trs_.length;
			if(len_ < 5) {
				const tr_ = document.createElement('tr')
				tr_.classList.add('imgfile-item')
				const id_ = len_ + 1;
				const row_ = `
					<tr class="imgfile-item">
						<td>\${id_}</td>
						<td>
							<div class="fileBox">
								<input type="text" id="fileName0\${id_}" class="fileName" readonly="readonly" placeholder="파일찾기" />
								<button type="button" id="upload-file0\${id_}" class="btn_file" name="img_file\${id_}" onclick="addFile(event, \${id_}, 1)">찾아보기</button>
							</div>
							<div class="change-application-wrapper">
								<div class="js-file-area-\${id_}"></div>
							</div>
						</td>
						<td></td>`;
				tr_.innerHTML = row_;
				tbody_.appendChild(tr_);
			}
		})
		file_delete_btn.addEventListener('click', e=>{
			const tbody_ = document.querySelector('tbody#imgfiles');
			const trs_ = document.querySelectorAll('tr.imgfile-item');
			const len_ = trs_.length;
			tbody_.removeChild(trs_[len_-1]);
		})
	}
	
	function add_trainone(type_name, data) {
		const parent_id = type_name === 'outer' ? 'edugroup-trainer-outer' : type_name === 'inner' ? 'edugroup-trainer-inner' : 'edugroup-trainee'
	    const trs_ = document.querySelectorAll(`tr.\${parent_id}`);
	    const nums = trs_.length;
	    const num_id = String(nums+1).padStart(2, 0)
	    let dom_id, dom_name, total_header_id, header_id, last_idx, max_len, class_name = '';
	    switch(type_name) {
	        case 'outer':
	            dom_id = `edugroup-outer\${num_id}`
	            dom_name = 'edugroupOuter'
	            	total_header_id = 'trainer-total-header'
	            header_id = 'trainer-outer-header';
	            last_idx = 0;
	            max_len = 2;
	            break;
	        case 'inner':
	            dom_id = `edugroup-inner\${num_id}`
	            dom_name = 'edugroupInner'
	            total_header_id = 'trainer-total-header'
	            header_id = 'trainer-inner-header';
	            last_idx = 0;
	            max_len = 2;
	            break;
	        case 'trainee':
	            dom_id = `edugroup-trainee\${num_id}`
	            dom_name = 'trainee'
	            total_header_id = 'trainee-total-header'
	            header_id = 'trainee-inner-header';
	            last_idx = nums-1;
	            max_len = 9;
	            class_name = 'edugroup-trainee-name';
	            break;
	        default:
	        	break;
	    }
	    
	    if(nums < max_len) {
	        const tr_ = document.createElement('tr')
	        tr_.classList.add(parent_id)
	        const row_ = `
	            <td>
	                <select id="\${dom_id}-role" name="\${dom_name}Role" class="w100" value="\${data.role}">
	                    <option value="팀장" \${data.role === '팀장' ? 'selected' : ''}>팀장</option>
	                    <option value="팀원" \${data.role === '팀원' ? 'selected' : ''}>팀원</option>
	                </select>
	            </td>
	            <td><input id="\${dom_id}-team" name="\${dom_name}Team" type="text" value="\${data.team}" class="w100 nullable"></td>
	            <td><input id="\${dom_id}-position" name="\${dom_name}Position" type="text" value="\${data.position}" class="w100 nullable"></td>
	            <td><input id="\${dom_id}-name" name="\${dom_name}Name" type="text" value="\${data.name}" class="w100 nullable \${class_name}"></td>
	        `;
	        tr_.innerHTML = row_;
	        const total_header = document.querySelector(`th#\${total_header_id}`)
	        const inner_header = document.querySelector(`td#\${header_id}`)
	        const total_rowspan = total_header.getAttribute('rowspan') != null ? parseInt(total_header.getAttribute('rowspan')) : 1;
	        const inner_rowspan = inner_header.getAttribute('rowspan') != null ? parseInt(inner_header.getAttribute('rowspan')) : 1;
	        total_header.setAttribute("rowspan", total_rowspan+1);
	        inner_header.setAttribute("rowspan", inner_rowspan+1);
	        if(nums === 0) {
	            inner_header.parentElement.insertAdjacentElement('afterend', tr_);
	        } else {
	            trs_[last_idx].insertAdjacentElement('afterend', tr_);
	        }
	        if(type_name === 'trainee') {
	        	const name_e = tr_.querySelector(`input#\${dom_id}-name`)
		        name_e.addEventListener('change', e=> {
		        	const trs_ = document.querySelectorAll('input.edugroup-trainee-name');
					const names = Array.from(trs_).filter(e => e.value != null && e.value !== '')
					profileProxy.numTrainee = names.length;
		        })
	        }
	    }
	}
	
	function organize() {
		// DOM으로 부터 organization을 만드는 함수
		let groups_ = document.querySelectorAll('tr.organ-group')
		let groups = Array.from({length: groups_.length}, (_, i) => ({'type': 'group', 'id': i, 'element': groups_[i], 'team': []}))
		groups.forEach(e => {
			let id_ = String(e.id).padStart(2,0)
			let class_name = `organ-group-\${id_}-team`;
			let tbody = e.element.parentElement
			let inputs = tbody.querySelectorAll(`input.\${class_name}`)
			e.team = Array.from({length:inputs.length}, (_,i) => ({'type': 'team', 'id': i, 'element': inputs[i]}));
			
		})
		organization = groups;
	}
	
	function addTeam(group_idx, elem) {
		// group-idx에 team을 추가하려면, group의 header의 rowspan을 1추가하고, team input의 tr 바로 뒤에 추가한다
		// 그룹보다 큰 idx를 넣으면 안됨
		if(group_idx > organization.length-1) return;
		let group_id = organization[group_idx].id
		let ths_ = organization[group_idx].element.querySelectorAll('th');
		ths_.forEach(e => {
			let rowspan_ = e.getAttribute('rowspan') != null ? parseInt(e.getAttribute('rowspan')) : 1;
			e.setAttribute('rowspan', rowspan_+1)
		})
		// 추가할 엘리먼트가 없다면 생성
		let idx_ = organization[group_idx].team.length-1
		if(elem == null) {
			let gid_ = String(group_id).padStart(2,0)
			let tid_ = String(idx_+1).padStart(2,0)
			let tr_ = document.createElement('tr')
			let td_ = document.createElement('td')
			let input_ = document.createElement('input')
			let id_ = `organ-group-\${gid_}-team-\${tid_}`;
			input_.setAttribute('type', 'text')
			input_.setAttribute('id', id_);
			input_.setAttribute('name', `organGroup_\${gid_}_team`)
			input_.classList.add('w100')
			input_.classList.add(`organ-group-\${gid_}-team`)
			td_.appendChild(input_)
			tr_.appendChild(td_)
			elem = tr_;
		}
		organization[group_idx].team[idx_].element.parentElement.parentElement.insertAdjacentElement('afterend', elem);
		
		let input_e = elem.querySelector('input')
		input_e.addEventListener('change', e=>{
			toast.classList.add("show");
			toast.style.display = "block";
			
			setTimeout(function() {
				toast.style.display = 'none';
				toast.className = toast.className.replace('show', '')
			}, 3000)
			organize();
			updateTeam();
			updateTrainingTarget();
		})
		organize();
		updateTrainingTarget();
	}
	function deleteTeam(group_idx) {
		if(group_idx < 0 || group_idx > organization.length-1) return;
		// 두가지 경우가 있다. team이 1개만 있을 때와 2개 이상인 경우.
		// team이 1개 인 경우 -> group까지 없앤다.
		let team_ = organization[group_idx].team
		let group_e = organization[group_idx].element
		let parent_ = group_e.parentElement;
		if(team_.length <= 1) {
			parent_.removeChild(group_e);
		} else {
			let ths = group_e.querySelectorAll('th')
			ths.forEach(e => {
				let rowspan_ = parseInt(e.getAttribute('rowspan'))
				e.setAttribute('rowspan', rowspan_-1)
			})
			let team_e = team_[team_.length-1].element.parentElement.parentElement
			parent_.removeChild(team_e)
		}
		organize();
		updateTrainingTarget();
	}
	function addGroup(checked) {
		// 추가할 위치 찾기
		const is_checked = checked;
		let last_group_idx = organization.length-1;
		let teams_ = organization[last_group_idx].team;
		let teams_idx = teams_.length-1;
		let last_team = teams_[teams_idx];
		// group element 생성
		let tr_ = document.createElement('tr')
		let gid_ = String(last_group_idx+1).padStart(2,0);
		let tid_ = '00';
		tr_.classList.add('organ-group')
		tr_.setAttribute('id', `organ-group-\${gid_}`)
		tr_.innerHTML = `
			<th scope="row" class="organ-group-header">
				<input type="checkbox" id="checkbox\${gid_}" name="checkboxOrgan" value="Y" class="checkbox-type01 organ-check nullable" \${is_checked ? 'checked' : ''}>
			</th>
			<th scope="row">
				부서 팀 명
				<div class="people-add-box">
					<button type="button" class="add add-organ-team">팀 추가</button>
					<button type="button" class="delete delete-organ-team">팀 삭제</button>
				</div>
			</th>
			<td>
				<input class="organ-group-\${gid_}-team w100 nullable" type="text" id="organ-group-\${gid_}-team-\${tid_}" name="organGroup_\${gid_}_team" value="">
			</td>
		`;
		last_team.element.parentElement.parentElement.insertAdjacentElement('afterend', tr_);
		organize();
		let tr_btns = tr_.querySelectorAll('.add-organ-team');
		tr_btns.forEach((elem) => {
			if(!elem.addTeamEventadded) {
				elem.addEventListener('click', e => {
					addTeam(last_group_idx+1)
				})
				elem.addTeamEventadded= true;
			}
		})
		let tr_btns2 = tr_.querySelectorAll('.delete-organ-team');
		tr_btns2.forEach((elem) => {
			if(!elem.deleteTeamEventadded) {
				elem.addEventListener('click', e => {
					deleteTeam(last_group_idx+1)
				})
				elem.deleteTeamEventadded= true;
			}
		})
		let input_e = tr_.querySelector("input[type='text']");
		input_e.addEventListener('change', e=>{
			toast.classList.add("show");
			toast.style.display = "block";
			
			setTimeout(function() {
				toast.style.display = 'none';
				toast.className = toast.className.replace('show', '')
			}, 3000)
		})
	}
	
	function updateTrainingTarget() {
		let tbody = document.querySelector('tbody#train-target')
		tbody.innerHTML = '';
		let header_ = getTrainTargetHeaderElement();
		tbody.appendChild(header_);
		team.forEach(e => {
			// 기존 row가 있으면 삭제
			let idx_ = team.findIndex(item => item.value == e.value)
			let row_old = tbody.querySelector(`tr#target-\${idx_}`)
			if(row_old != null) tbody.removeChild(row_old);
			
			// 새로 생성한거 추가
			let row_ = getTrainTargetRowElement(e.value)
			tbody.appendChild(row_);
		})
	}
	function getTrainTargetHeaderElement() {
		let content_ = `
			<th scope="col">업무명</th>
			<th scope="col" colspan="5">
				훈련과정 개발 필요성<br>
				낮음 < ------------------- > 높음
			</th>
			<th scope="col">훈련대상 업무 선정 여부</th>
		`;
		let tr_ = document.createElement('tr')
		tr_.id = "train-target-header";
		tr_.innerHTML = content_
		return tr_;
	}
	function getTrainTargetRowElement(name) {
		let idx = team.findIndex(e => e.value === name)
		let checkbox_name = `checkbox-target-\${idx}`;
		let target_added = profileProxy.targets.findIndex(e => e.value === name)
		let is_checked = (target_added !== -1) || ((json_obj.checkboxTarget != null) && (name == json_obj.checkboxTarget[idx])) ? 'checked' : ''
		let row_ = `
			<td class="target-name">\${name}</td>
			<td>
				<input type="radio" class="radio-type01 radioTarget\${idx}" id="radio-\${idx}" name="radioTarget\${idx}" value="1">
			</td>
			<td>
				<input type="radio" class="radio-type01 radioTarget\${idx}" id="radio-\${idx}" name="radioTarget\${idx}" value="2">
			</td>
			<td>
				<input type="radio" class="radio-type01 radioTarget\${idx}" id="radio-\${idx}" name="radioTarget\${idx}" value="3">
			</td>
			<td>
				<input type="radio" class="radio-type01 radioTarget\${idx}" id="radio-\${idx}" name="radioTarget\${idx}" value="4">
			</td>
			<td>
				<input type="radio" class="radio-type01 radioTarget\${idx}" id="radio-\${idx}" name="radioTarget\${idx}" value="5">
			</td>
			<td>
				<input type="checkbox" id="\${checkbox_name}" name="checkboxTarget" value="\${name}" class="checkbox-type01" \${is_checked}>
			</td>`;
		let tr_ = document.createElement('tr')
		tr_.id = `target-\${idx}`
		tr_.innerHTML = row_;
		let cbox_ = tr_.querySelector(`input#\${checkbox_name}`);
		cbox_.addEventListener('change', e => {
			updateTargets();
			setTargetDetail();
		})
		return tr_;
	}
	function updateTargets() {
		const tds = document.querySelectorAll('td.target-name');
		let tmp_ = Array.from(tds).filter(e => {
			const name_ = e.textContent;
			const idx_ = team.findIndex(e => e.value === name_);
			const isChecked = e.parentElement.querySelector('input[type="checkbox"]').checked
			return idx_ != -1 && isChecked
			// return {'id': idx_, 'value': name_}
		}).map(e => {
			const name_ = e.textContent;
			const idx_ = team.findIndex(e => e.value === name_);
			return {'id': idx_, 'value': name_}
		})
		tmp_.sort((a,b) => a.id-b.id)
		profileProxy['targets'] = tmp_;
	}
	function setTargetDetail() {
		// targetdetail 전체 삭제
		let tbody = document.querySelector('tbody#target-detail')
		let tr_olds = tbody.querySelectorAll('tr.target-detail-item');
		Array.from(tr_olds).forEach(e => tbody.removeChild(e));
		profileProxy.targets.forEach((e,i) => {
			const detail_ = json_obj.targetDetail != null ? json_obj.targetDetail[i] : '';
			const knowledge_ = json_obj.targetKnowledge != null ? json_obj.targetKnowledge[i] : '';
			const technology_ = json_obj.targetTechnology != null ? json_obj.targetTechnology[i] : '';
			let row_ = `
				<td>\${e.value}</td>
				<td>
					<textarea id="" name="targetDetail" cols="50" rows="5" placeholder="" class="w100 h100">\${detail_}</textarea>
				</td>
				<td>
					<textarea id="" name="targetKnowledge" cols="50" rows="5" placeholder="" class="w100 h100">\${knowledge_}</textarea>
				</td>
				<td>
					<textarea id="" name="targetTechnology" cols="50" rows="5" placeholder="" class="w100 h100">\${technology_}</textarea>
				</td>
			</tr>
			`
			let tr_ = document.createElement('tr')
			tr_.classList.add('target-detail-item')
			tr_.innerHTML = row_;
			tr_.id = `target-detail-\${e.id}`
			let tbody = document.querySelector('tbody#target-detail')
			let tr_old = tbody.querySelector(`tr#target-detail-\${e.id}`);
			if(tr_old != null) {
				tbody.removeChild(tr_old)
			}
			tbody.appendChild(tr_)
		})
		setProfile()
	}
	function setProfile() {
		// profile 테이블에서 기존 내용 삭제
		let tbody_ = document.querySelector('tbody#profile');
		let zdel_headers = tbody_.querySelectorAll('tr.profile-header');
		let zdel_details = tbody_.querySelectorAll('tr.profile-detail');
		let zdel_footers = tbody_.querySelectorAll('tr.profile-footer');
		zdel_headers.forEach(e => tbody_.removeChild(e))
		zdel_details.forEach(e => tbody_.removeChild(e))
		zdel_footers.forEach(e => tbody_.removeChild(e))
		
		// profile 테이블 요소 생성
		let targets_length = profileProxy.targets.length;
		let tr_header_ = document.createElement('tr')
		tr_header_.classList.add('profile-header')
		tr_header_.innerHTML = `
			<th scope="row" rowspan="\${5+targets_length}" id="profile-header-rowspan">훈련내용</th>
			<th scope="row" rowspan="2">업무(단원)명</th>
			<th scope="row" rowspan="2">세부 내용</th>
			<th scope="row" rowspan="2">훈련 시간(H)</th>
			<th scope="col" colspan="2">교사 투입시간(H)</th>
		`;
		let tr_header2_ = document.createElement('tr')
		tr_header2_.classList.add('profile-header')
		tr_header2_.innerHTML = `
			<th scope="col">외부</th>
		    <th scope="col">내부</th>
		`;
		let total_time = 0
		let total_outer = 0;
		let total_inner = 0;
		let tr_targets = profileProxy.targets.map((e,idx) => {
			let tr_ = document.createElement('tr');
			tr_.classList.add('profile-detail');
			const content_ = json_obj.profileDetailContent != null ? json_obj.profileDetailContent[idx] : '';
			const time_ = json_obj.profileDetailTime != null ? json_obj.profileDetailTime[idx] : '';
			const outer_ = json_obj.profileDetailOuter != null ? json_obj.profileDetailOuter[idx] : '';
			const inner_ = json_obj.profileDetailInner != null ? json_obj.profileDetailInner[idx] : '';
			total_time = total_time + parseInt(time_);
			total_outer = total_outer + parseInt(outer_);
			total_inner = total_inner + parseInt(inner_);
			tr_.innerHTML = `
				<td>\${e.value}</td>
			    <td>
			        <textarea id="profile-detail-\${e.id}-content" class="w100 h100" name="profileDetailContent" cols="50" rows="5" placeholder="" required>\${content_}</textarea>
			    </td>
			    <td><input type="text" id="profile-detail-\${e.id}-time" name="profileDetailTime" value="\${time_}" title="" class="w100 profile-time"></td>
			    <td><input type="text" id="profile-detail-\${e.id}-outer" name="profileDetailOuter" value="\${outer_}" title="" class="w100 profile-outer"></td>
			    <td><input type="text" id="profile-detail-\${e.id}-inner" name="profileDetailInner" value="\${inner_}" title="" class="w100 profile-inner"></td>
			`;
			return tr_
		})
		let tr_feedback = document.createElement('tr')
		tr_feedback.classList.add('profile-detail')
		const fcontent_ = json_obj.profileFeedbackContent != null ? json_obj.profileFeedbackContent[0] : '';
		const ftime_ = json_obj.profileFeedbackTime != null ? json_obj.profileFeedbackTime[0] : '';
		const fouter_ = json_obj.profileFeedbackOuter != null ? json_obj.profileFeedbackOuter[0] : '';
		const finner_ = json_obj.profileFeedbackInner != null ? json_obj.profileFeedbackInner[0] : '';
		total_time = total_time + parseInt(ftime_);
		total_outer = total_outer + parseInt(fouter_);
		total_inner = total_inner + parseInt(finner_);
		tr_feedback.innerHTML = `
			<td>결과 공유 및 피드백</td>
			<td>
				<textarea id="profile-detail-feedback-content" class="w100 h100" name="profileFeedbackContent" cols="50" rows="5" placeholder="">\${fcontent_}</textarea>
			</td>
			<td><input type="text" id="profile-detail-feedback-time" name="profileFeedbackTime" value="\${ftime_}" title="" class="w100 profile-time"></td>
			<td><input type="text" id="profile-detail-feedback-outer" name="profileFeedbackOuter" value="\${fouter_}" title="" class="w100 profile-outer"></td>
			<td><input type="text" id="profile-detail-feedback-inner" name="profileFeedbackInner" value="\${finner_}" title="" class="w100 profile-inner"></td>
			`;
		
		let tr_footer_ = document.createElement('tr')
		tr_footer_.classList.add('profile-footer')
		tr_footer_.innerHTML = `
			<td colspan="2">전체시간</td>
		    <td id="profile-time-total" class="profile-total-time">\${total_time}</td>
		    <td id="profile-time-outer">\${total_outer}</td>
		    <td id="profile-time-inner">\${total_inner}</td>
		`
		
		// 생성한 프로파일 요소 붙이기
		let start_ = document.querySelector('tr#profile-header-start')
		tr_targets = [...tr_targets, tr_feedback]
		let trs_ = [tr_header_, tr_header2_, ...tr_targets, tr_footer_];
		let elems_ = [start_, ...trs_]
		trs_.forEach((e,i) => {
			elems_[i].insertAdjacentElement('afterend', e);
		})
		
		tr_targets.forEach(e => {
			const times_ = e.querySelector('input.profile-time');
			const outer_ = e.querySelector('input.profile-outer');
			const inner_ = e.querySelector('input.profile-inner');
			
			[times_, outer_, inner_].forEach(elem => {
				elem.addEventListener('change', item => {
					const t_ = parseInt(times_.value);
					const o_ = parseInt(outer_.value);
					const i_ = parseInt(inner_.value);
					
					if(t_ != o_ + i_) {
						times_.style.backgroundColor = '#0e7ec3';
						outer_.style.backgroundColor = '#0e7ec3';
						inner_.style.backgroundColor = '#0e7ec3';
						times_.style.color = 'white'
						outer_.style.color = 'white'
						inner_.style.color = 'white'
					} else {
						times_.style.backgroundColor = 'white';
						outer_.style.backgroundColor = 'white';
						inner_.style.backgroundColor = 'white';
						times_.style.color = '#666669';
						outer_.style.color = '#666669';
						inner_.style.color = '#666669';
					}
				})
			})
		})
		tr_times = tr_targets.map(e => (e.querySelector('input.profile-time')));
		tr_times.forEach(e => {			
			e.addEventListener('change', item => {
				const times_all = document.querySelectorAll('input.profile-time');
				const total = Array.from(times_all).map(e=> {
						let v_ = parseInt(e.value);
						if(isNaN(v_)) {
							v_ = 0;
						}
						return v_;
				}).reduce((a,b) => a+b)
				profileProxy['time'] = total;
			})
		});
		tr_outers = tr_targets.map(e => (e.querySelector('input.profile-outer')));
		tr_outers.forEach(e => {			
			e.addEventListener('change', item => {
				const times_all = document.querySelectorAll('input.profile-outer');
				const total = Array.from(times_all).map(e=> {
						let v_ = parseInt(e.value);
						if(isNaN(v_)) {
							v_ = 0;
						}
						return v_;
				}).reduce((a,b) => a+b)
				profileProxy['time_outer'] = total;
			})
		});
		tr_inner = tr_targets.map(e => (e.querySelector('input.profile-inner')));
		tr_inner.forEach(e => {			
			e.addEventListener('change', item => {
				const times_all = document.querySelectorAll('input.profile-inner');
				const total = Array.from(times_all).map(e=> {
						let v_ = parseInt(e.value);
						if(isNaN(v_)) {
							v_ = 0;
						}
						return v_;
				}).reduce((a,b) => a+b)
				profileProxy['time_inner'] = total;
			})
		});
	}
	function updateTeam() {
		let organ_checks = document.querySelectorAll('.organ-check');
		team = []
		Array.from(organ_checks).forEach(e => {
			if(e.checked) {
				let group_id_ = parseInt(e.id.split('checkbox')[1]);
				organization.forEach(item => {
					if(item.id == group_id_) {
						item.team.forEach(t_ => {
							if(t_.element.value !== '') {
								team.push({'group_id': group_id_, 'value': t_.element.value})
								updateTrainingTarget()
							}
						})
					}
				})
			} else {
				let group_id_ = parseInt(e.id.split('checkbox')[1]);
				team = team.filter(team_ => team_.group_id != group_id_)
				updateTrainingTarget()
			}
		})
	}
	function updateEvaluationList() {
		// profileProxy.targets
		const tbody = document.querySelector('tbody#targets-evaluation');
		// 기존에 있던거 지우기
		const row_olds = tbody.querySelectorAll('tr.targets-select');
		Array.from(row_olds).forEach(e => tbody.removeChild(e));
		
		// 새롭게 생성해서 평가 결과에 넣기
		const trs = profileProxy.targets.map((e, i) => {
			const options_ = profileProxy.targets.map((item,j) => `<option value="\${item.value}" \${i == j ? 'selected' : ''}>\${item.value}</opiton>`).join(' ')
			const radios_ = Array.from({length: 5}, (_,item) => (`<td><input type="radio" class="radio-type01 targetsEval" id="radio\${item}" name="targetsEval\${i}" value="\${item+1}" ></td>`)).join(' ')
			const row_ = `
					<td>
						<select id="targets-select-\${i}" name="targetsName" class="w100">
							\${options_}
						</select>
					</td>
					<td class="left">
						<input type="text" id="targets-criteria-\${i}" name="targetsCriteria" value="" class="w100 nullable">
					</td>
					\${radios_}
			`;
			const tr_ = document.createElement('tr')
			tr_.classList.add('targets-select');
			tr_.innerHTML = row_;
			return tr_;
		})
		if(trs.length > 0) {
			const start_ = [tbody, ...trs];
			tbody.insertAdjacentElement('afterbegin', trs[0]);
			trs2 = trs.filter((_,i) => i !== 0)
			trs2.forEach((e,i) => {
				trs[i].insertAdjacentElement('afterend', e);
			})			
		}	
	}
	function sendReport(n) {
		const { rate } = profileProxy.plagiarism;
		console.log(`유사도 검사 결과 \${rate}, \${parseFloat(rate)}`)
		if(n == 10) {
			if(parseFloat(rate) >= 50) {
				alert('유사도 검사 결과 50% 이상입니다.')
				return;
			} else if(rate === undefined) {
				alert('유사도 검사를 진행해주세요.')
				return;
			}
		}
		checkInputs();
		saveReport(n);
	}
	function checkInputs() {
		const nulls = document.querySelectorAll('.nullable');
		Array.from(nulls).forEach(e => {
			if(e.value == null || e.value === '') {
				e.value = '-';
			}
		})
	}
	function add_facility(data) {
		const {name, type, spec, location} = data;
		const trs_ = document.querySelectorAll('tr.facility-item');
		if(trs_.length < 8) {
			const tr_ = document.createElement('tr')
			tr_.classList.add('facility-item');
			const row_ = `
			    <td><input type="text" id="facility-idx-\${trs_.length}" name="" value="\${trs_.length+1}" title="" class="w100"></td>
			    <td>
			        <select id="" name="facilityType" class="w100">
			            <option value="시설" \${type === '시설' ? 'selected':''}>시설</option>
			            <option value="장비" \${type === '장비' ? 'selected':''}>장비</option>
			        </select>
			    </td>
			    <td><input type="text" id="facility-name-\${trs_.length}" name="facilityName" value="\${name}" title="" class="w100"></td>
			    <td><input type="text" id="facility-spec-\${trs_.length}" name="facilitySpec" value="\${spec}" title="" class="w100"></td>
			    <td><input type="text" id="facility-location-\${trs_.length}" name="facilityLocation" value="\${location}" title="" class="w100"></td>
			`;
			tr_.innerHTML = row_;
			tbody = document.querySelector('tbody#facility')
			tbody.appendChild(tr_);
		}
	}
	function add_trainer(data) {
		const {name, location, experience, job, course} = data;
		const trs_ = document.querySelectorAll('tr.trainer-item');
		if(trs_.length < 5) {
			const tr_ = document.createElement('tr')
			tr_.classList.add('trainer-item');
			const row_ = `
			    <td><input type="text" id="trainer-name-\${trs_.length}" name="trainerName" value="\${name}" title="" class="w100"></td>
			    <td>
			        <select id="trainer-location-\${trs_.length}" name="trainerLocation" class="w100">
			            <option value="내부" \${location === '내부' ? 'selected':''}>내부</option>
			            <option value="외부" \${location === '외부' ? 'selected':''}>외부</option>
			        </select>
			    </td>
			    <td><input type="text" id="trainer-experience-\${trs_.length}" name="trainerExperience" value="\${experience}" title="" class="w100"></td>
			    <td><input type="text" id="trainer-job-\${trs_.length}" name="trainerJob" value="\${job}" title="" class="w100"></td>
			    <td><textarea id="trainer-course-\${trs_.length}" class="w100 h100" name="trainerCourse" cols="50" rows="5" placeholder="">\${course}</textarea></td>
			`;
			tr_.innerHTML = row_;
			tbody = document.querySelector('tbody#trainer')
			tbody.appendChild(tr_);
		}
	}
	function add_targetsSelect(data) {
		const { name, criteria, filtered } = data;
		const trs_ = document.querySelectorAll('tr.targets-select');
		const checked_value = json_obj[filtered].filter(e => e !== '-')[0];
		let num_ = trs_.length
		if(profileProxy.targets.length > 0 && num_ < 15) {
			const tr_ = document.createElement('tr')
			tr_.classList.add('targets-select');
			const idx_ = num_;
			const options_ = profileProxy.targets.map((item,j) => `<option value="\${item.value}" \${item.value == name ? 'selected' : ''}>\${item.value}</opiton>`).join(' ')
			const row_ = `
			    <td>
		        	<select id="targets-select-\${num_}" name="targetsName" value="\${name}" class="w100 targets-select">
		            	\${options_}
		        	</select>
		    	</td>
		    	<td class="left">
		        	<input type="text" id="targets-criteria-0" name="targetsCriteria" value="\${criteria}" class="w100 nullable">
		    	</td>
		    	<td><input type="radio" class="radio-type01 targetsEval" id="radio\${idx_}" name="targetsEval\${idx_}" value="1" \${checked_value == '1' ? 'checked' : ''}></td>
		    	<td><input type="radio" class="radio-type01 targetsEval" id="radio\${idx_}" name="targetsEval\${idx_}" value="2" \${checked_value == '2' ? 'checked' : ''}></td>
		    	<td><input type="radio" class="radio-type01 targetsEval" id="radio\${idx_}" name="targetsEval\${idx_}" value="3" \${checked_value == '3' ? 'checked' : ''}></td>
		    	<td><input type="radio" class="radio-type01 targetsEval" id="radio\${idx_}" name="targetsEval\${idx_}" value="4" \${checked_value == '4' ? 'checked' : ''}></td>
		    	<td><input type="radio" class="radio-type01 targetsEval" id="radio\${idx_}" name="targetsEval\${idx_}" value="5" \${checked_value == '5' ? 'checked' : ''}></td>
			`;
			tr_.innerHTML = row_;
			const last_tr = trs_[num_-1]
			last_tr.insertAdjacentElement('afterend', tr_);
		}
	}
	function add_diary(data) {
		const { month, cn } = data;
		const trs = document.querySelectorAll('tr.diary-item');
		const length = trs.length
		if(length < 12) {
			const tr_ = document.createElement('tr')
			tr_.classList.add('diary-item')
			const row_ = `
			    <th scope="col">
		        	<input type="text" id="diary-month-\${trs.length}" name="diaryMonth" class="w100 diary-month" value="\${month}" placeholder="">
		    	</th>
		    	<td class="left">
		        	<textarea id="diary-cn-\${trs.length}" name="diaryCN" class="w100 h100 diary-cn" cols="50" rows="5" placeholder="">\${cn}</textarea>
		    	</td>
			`;
			tr_.innerHTML = row_;
			const tbody = document.querySelector('tbody#diary')
			tbody.appendChild(tr_)
		}
	}
</script>
<script type="text/script" src="${contextPath}<c:out value="${jsPath}/report/pbl.js"/>"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
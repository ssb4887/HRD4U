<%@ include file="../../../../../include/commonTop.jsp"%>
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
	.table-type02 table thead tr th {
		border: 1px solid #000;
		color: #000;
	}
	.table-type02 table tbody tr th,
	.table-type02 table tbody tr td {
		border: 1px solid #000;
		color: #000;
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
</style>


<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">


<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">

	<!-- CMS 시작 -->
	<!-- 프린트용 표지, 목차 -->
	<div class="contents-area">
		<div class="print-cover-wrapper hidden">
			<div class="print-cover-area">
				<div class="title" style="margin-top: 150px;">
					<h3>직업능력개발 훈련체계 수립 보고서</h3>
					<h4>
						<c:out value="${cnsl.corpNm}" />
					</h4>
				</div>

				<p class="date">
					<span class="yyyy">2023</span>. <span class="mm">12</span>. <span
						class="dd">21</span>
				</p>

				<div class="table-wrapper">
					<div>
						<table>
							<caption>직업능력개발 훈련체계 수립 보고서 기재표 : 구분, 소속, 성명에 관한 정보표</caption>
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
									<td>컨설팅 책임자(PM)</td>
									<td style="word-break : keep-all"><c:out value="${pm.mberPsitn}"/></td>
									<td style="word-break : keep-all"><c:out value="${pm.mberName}"/></td>
								</tr>						
								<c:forEach var="member" items="${exExperts}">
									<c:if test="${member.teamOrderIdx eq 1}">
										<tr>
											<td rowspan="${fn:length(exExperts)}">외부 내용전문가</td>
											<td style="word-break : keep-all"><c:out value="${member.mberPsitn}"/></td>
											<td style="word-break : keep-all"><c:out value="${member.mberName}"/></td>
										</tr>
									</c:if>
								</c:forEach>
								
								<c:forEach var="member" items="${exExperts}" varStatus="status">
									<c:if test="${member.teamOrderIdx ne 1}">
										<tr>
											<td style="word-break : keep-all"><c:out value="${member.mberPsitn}"/></td>
											<td style="word-break : keep-all"><c:out value="${member.mberName}"/></td>
										</tr>
									</c:if>
								</c:forEach>
								
								<c:forEach var="member" items="${inExperts}">
									<c:if test="${member.teamOrderIdx eq 1}">
										<tr>
											<td rowspan="${fn:length(inExperts)}">기업 내부전문가</td>
											<td style="word-break : keep-all"><c:out value="${member.mberPsitn}"/></td>
											<td style="word-break : keep-all"><c:out value="${member.mberName}"/></td>
										</tr>
									</c:if>
								</c:forEach>
								<c:forEach var="member" items="${inExperts}"
									varStatus="status">
									<c:if
										test="${member.teamOrderIdx ne 1}">
										<tr>
											<td style="word-break : keep-all"><c:out value="${member.mberPsitn}" /></td>
											<td style="word-break : keep-all"><c:out value="${member.mberName}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="page-logo-wrapper">
					<img src="../img/sub03/ci01.png" alt="고용노동부" /> <img
						src="../img/sub03/ci02.png" alt="HRDK한국산업인력공단" /> <img
						src="../img/sub03/ci03.png" alt="대한상공회의소 중소기업 훈련지원 센터" />
				</div>

			</div>

			<div class="print-cover-list">
				<h3>[ 목 차 ]</h3>
				<ol>
					<li><span class="number"> I. </span> <strong> 개요 </strong> 

						<ol>
							<li><span class="number"> 1. </span> <strong> 훈련체계
									수립 컨설팅 필요성 </strong> </li>
							<li><span class="number"> 2. </span> <strong> 훈련체계
									수립 컨설팅 주요 활동 </strong> </li>
							<li><span class="number"> 3 </span> <strong> 훈련체계
									수립 컨설팅 주요 결과 </strong> </li>
						</ol></li>


					<li><span class="number"> II. </span> <strong> 훈련요구 및
							직무분석 </strong> 

						<ol>
							<li><span class="number"> 1. </span> <strong> 훈련요구
									분석 </strong> </li>
							<li><span class="number"> 2. </span> <strong> 직무분류
							</strong> </li>
							<li><span class="number"> 3. </span> <strong> 훈련대상
									직무선정 및 분석 </strong> </li>
						</ol></li>


					<li><span class="number"> III. </span> <strong> 훈련체계
							수립 </strong> 

						<ol>
							<li><span class="number"> 1. </span> <strong> 훈련체계도
									도출 </strong> </li>
							<li><span class="number"> 2. </span> <strong> 훈련계획
									수립 </strong> </li>
						</ol></li>
				</ol>

				<dl>
					<dt>[ 별 첨]</dt>
					<dd>분석 세부 내용: 내부현황 분석, 외부환경 분석, 직무역량 모델링 등</dd>
					<dd>컨설팅 수행일지</dd>
				</dl>

			</div>

		</div>



	</div>
	<!-- 프린트용 표지, 목차 -->

	<!-- CMS 시작 -->
	<input type="hidden" id="cnslIdx" name="cnslIdx"
		value="${cnsl.cnslIdx}" /> <input type="hidden" id="reprtIdx"
		name="reprtIdx" value="${report.reprtIdx}" />
	<div class="page" id="page1">
		<div class="contents-area">
			<div class="contents-box">
				<h3 class="title-type01">1. 개요</h3>
			</div>

			<div class="contents-box">
				<h4 class="title-type02">1. 훈련체계 수립 필요성</h4>
				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>결과 요약</caption>
						<colgroup>
							<col width="15%">
							<col width="85%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="left"><c:out value="${data.necessity[0]}" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="contents-box">
				<h4 class="title-type02">2. 훈련체계 주요 활동</h4>
				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>훈련체계 주요 활동표 : 회차, 일시, 수행내용, 방법, 참석자에관한 정보 제공표</caption>
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
								<th scope="col">수행내용</th>
								<th scope="col">방법</th>
								<th scope="col">참석자</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${diaryList}">
								<tr>
		                            <td>
		                            	<c:out value="${list.excOdr}"/>
		                            </td>
		                            <td>
		                            	수행일자 : <fmt:formatDate value="${list.mtgStartDt}" pattern="yyyy년 MM월 dd일" /> <br/>
										시작시간 : <fmt:formatDate value="${list.mtgStartDt}" type="time" /> <br/>
										종료시간 : <fmt:formatDate value="${list.mtgEndDt}" type="time" />
		                            </td>
		                            <td>
		                            	<c:out value="${list.mtgCn1}"/>
		                            </td>
		                            <td>
		                            	<c:out value="${list.operMthd eq '1' ? '대면' : '비대면'}"/>
		                            </td>
		                            <td>
		                            	컨설팅책임자(PM) : <c:out value="${list.pmNm}"/><br/>
		                            	내용전문가 : <c:out value="${list.cnExpert}"/><br/>
		                            	기업 내부전문가 : <c:out value="${list.corpInnerExpert}"/><br/>
		                            	기타 참석자(기업 등) : <c:out value="${list.spntPic}"/>
		                            </td>
								</tr>
								
								</c:forEach>
						</tbody>
					</table>
				</div>
			</div>


			<div class="contents-box page-start">
				<h4 class="title-type02">3. 훈련체계 수립 주요 결과</h4>
				<div class="images-box mb20 line">
					<img src="../img/sub03/img130201.gif" alt="훈련체계 수립 주요 결과표" />
				</div>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>결과 요약</caption>
						<colgroup>
							<col width="15%">
							<col width="85%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="left"><c:out value="${data.result[0]}" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="page hidden page-start" id="page2">
		<div class="contents-area page-start">
			<div class="contents-box">
				<h3 class="title-type01">2. 훈련요구 및 직무분석</h3>
				<h4 class="title-type02">1. 훈련요구 분석</h4>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>훈련요구 분석표 : 구분, 내용의 정보를 제공하는 정보표</caption>
						<colgroup>
							<col style="width: 30%" />
							<col style="width: 70%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">구분</th>
								<th scope="col">내용</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${data.trsystm_analysis_title}"
								varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out value="${data.trsystm_analysis_value[i.index]}" /></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>



			<div class="contents-box page-start">
				<h4 class="title-type02 ">2. 직무분류</h4>
				<h5 class="title-type03">직무분류표</h5>
				<div class="table-type02 horizontal-scroll mb20">

					<table>
						<caption>직무분류표 : 직군, 직무, 부서에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 33.33%" />
							<col style="width: 33.34%" />
							<col style="width: 33.33%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">직군</th>
								<th scope="col">직무</th>
								<th scope="col">부서</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="list" items="${data.jobPosition}" varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out value="${data.jobRole[i.index]}" /></td>
									<td><c:out value="${data.department[i.index]}" /></td>
								</tr>
							</c:forEach>


						</tbody>
					</table>
				</div>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>결과 요약</caption>
						<colgroup>
							<col width="15%">
							<col width="85%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="left"><c:out value="${data.jobClassify[0]}" /></td>
							</tr>
						</tbody>
					</table>
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
								<th scope="row">첨부파일</th>
								<td class="left"><c:forEach var="file"
										items="${report.files}">
										<c:if test="${file.itemId eq 'jobClassify_file'}">
											<strong class="point-color01">
												${file.fileOriginName} </strong>
											<a
												href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}"
												class="btn-linked"> <img
												src="${contextPath}/web/images/icon/icon_search04.png"
												alt="" />
											</a>
										</c:if>
									</c:forEach></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="contents-box page-start">
				<h4 class="title-type02">3. 훈련대상 직무선정 및 분석</h4>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>훈련대상 직무선정 및 분석표</caption>
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 85%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">방법 및 결과</th>
								<td class="left"><c:out
										value="${data.jobSelectionResult[0]}" /></td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>훈련대상 직무선정 및 분석표 : 직무명, 업무, 수행준거, 중요도, 난이도에 관한
							정보 제공표</caption>
						<colgroup>
							<col style="width: 20%" />
							<col style="width: 20%" />
							<col style="width: 60%" />
							<col style="width: 10%" />
							<col style="width: 10%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">직무명</th>
								<th scope="col">업무</th>
								<th scope="col">수행준거</th>
								<th scope="col">중요도</th>
								<th scope="col">난이도</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${data.jobTitle}" varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out value="${data.tasks[i.index]}" /></td>
									<td><c:out value="${data.performanceCriteria[i.index]}" /></td>
									<td><c:out value="${data.priority[i.index]}" /></td>
									<td><c:out value="${data.difficulty[i.index]}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>NCS 활용방법</caption>
						<colgroup>
							<col style="width: 20%" />
							<col style="width: 80%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">NCS 활용방법<br /> <span class="point-color01">(필요
										시)</span>
								</th>
								<td class="left"><c:out value="${data.ncsMO[0]}" /></td>
							</tr>
						</tbody>
					</table>
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
								<th scope="row">첨부파일</th>
								<td class="left"><c:forEach var="file"
										items="${report.files}">
										<c:if test="${file.itemId eq 'ncs_file'}">
											<strong class="point-color01">
												${file.fileOriginName} </strong>
											<a
												href="${contextPath}/web/report/download.do?mId=100&cnslIdx=${file.cnslIdx}&reprtIdx=${file.reprtIdx}&fleIdx=${file.fleIdx}"
												class="btn-linked"> <img
												src="${contextPath}/web/images/icon/icon_search04.png"
												alt="" />
											</a>
										</c:if>
									</c:forEach></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>



	<div class="page hidden page-start" id="page3">
		<div class="contents-area page-start">
			<div class="contents-box">
				<h3 class="title-type01">3. 훈련체계 수립</h3>
				<h4 class="title-type02">1. 훈련체계도 도출</h4>

				<h5 class="title-type05">훈련체계 수립 방법</h5>
				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>훈련체계 수립 방법</caption>
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 85%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="left"><c:out value="${data.tsMethod[0]}" /></td>
							</tr>
						</tbody>
					</table>
				</div>


				<h5 class="title-type05">훈련체계표</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>훈련체계표 : 구분(직위/부서), 공통, 생산, 생산, 생산, 생산기술, 품질에 관한
							정보 제공표</caption>
						<thead>
							<tr>
								<th scope="col">구분(직위/부서)</th>
								<th scope="col"><c:out value="${data.tsHeader01[0]}" /></th>
								<th scope="col"><c:out value="${data.tsHeader02[0]}" /></th>
								<th scope="col"><c:out value="${data.tsHeader03[0]}" /></th>
								<th scope="col"><c:out value="${data.tsHeader04[0]}" /></th>
								<th scope="col"><c:out value="${data.tsHeader05[0]}" /></th>
								<th scope="col"><c:out value="${data.tsHeader06[0]}" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${data.tsValue01}" varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out value="${data.tsValue02[i.index]}" /></td>
									<td><c:out value="${data.tsValue02[i.index]}" /></td>
									<td><c:out value="${data.tsValue02[i.index]}" /></td>
									<td><c:out value="${data.tsValue02[i.index]}" /></td>
									<td><c:out value="${data.tsValue02[i.index]}" /></td>
									<td><c:out value="${data.tsValue02[i.index]}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<h5 class="title-type05 page-start">도출 훈련과정 리스트</h5>

				<div class="table-type02 horizontal-scroll">
					<table>
						<caption>도출 훈련과정 리스트 : 구분, 과정명, 수준, 형태에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 20%" />
							<col style="width: 50%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">구분</th>
								<th scope="col">과정명</th>
								<th scope="col">수준</th>
								<th scope="col">형태</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${data.tcList_Class}" varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out value="${data.tcList_CourseName[i.index]}" /></td>
									<td><c:out value="${data.tcList_Level[i.index]}" /></td>
									<td><c:out value="${data.tcList_Form[i.index]}" /></td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>

				<h5 class="title-type05">활용 방안</h5>
				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>활용 방안</caption>
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 85%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="left"><c:out value="${data.utilization[0]}" /></td>
							</tr>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>




	<div class="page hidden page-start" id="page4">
		<div class="contents-box page-start">
			<h4 class="title-type02">2. 훈련계획 수립</h4>
			<h5 class="title-type03">훈련과정 명세서1</h5>

			<div class="table-type02 horizontal-scroll mb20">
				<table>
					<tbody>
						<tr>
							<th scope="col">과정명</th>
							<td colspan="3"><c:out value="${data.tp_name01[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 형태</th>
							<td colspan="3"><c:out value="${data.tp_format01[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">추천 훈련사업</th>
							<td colspan="3"><c:out value="${data.tp_recommend01[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 목표</th>
							<td colspan="3"><c:out value="${data.tp_goal01[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">주요 훈련 내용</th>
							<td colspan="3"><c:out value="${data.tp_contents01[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 대상</th>
							<td colspan="3"><c:out value="${data.tp_target01[0]}" /></td>
						</tr>
						<tr>
							<th scope="row" rowspan="11">훈련 내용</th>
							<th scope="row" class="bg01">교과목명</th>
							<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
							<th scope="row" class="bg01">추천 훈련시간</th>
						</tr>
						<c:forEach var="list" items="${data.tp_courseName01}"
							varStatus="i">
							<tr>
								<td><c:out value="${list}" /></td>
								<td><c:out value="${data.tp_details01[i.index]}" /></td>
								<td><c:out value="${data.tp_recommendTime01[i.index]}" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>



			<h5 class="title-type03">훈련과정 명세서2</h5>

			<div class="table-type02 horizontal-scroll mb20">
				<table>
					<tbody>
						<tr>
							<th scope="col">과정명</th>
							<td colspan="3"><c:out value="${data.tp_name02[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 형태</th>
							<td colspan="3"><c:out value="${data.tp_format02[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">추천 훈련사업</th>
							<td colspan="3"><c:out value="${data.tp_recommend02[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 목표</th>
							<td colspan="3"><c:out value="${data.tp_goal02[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">주요 훈련 내용</th>
							<td colspan="3"><c:out value="${data.tp_contents02[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 대상</th>
							<td colspan="3"><c:out value="${data.tp_target02[0]}" /></td>
						</tr>
						<tr>
							<th scope="row" rowspan="11">훈련 내용</th>
							<th scope="row" class="bg01">교과목명</th>
							<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
							<th scope="row" class="bg01">추천 훈련시간</th>
						</tr>

						<c:forEach var="list" items="${data.tp_courseName02}"
							varStatus="i">
							<tr>
								<td><c:out value="${list}" /></td>
								<td><c:out value="${data.tp_details02[i.index]}" /></td>
								<td><c:out value="${data.tp_recommendTime02[i.index]}" /></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>


			<h5 class="title-type03 page-start">훈련과정 명세서3</h5>

			<div class="table-type02 horizontal-scroll mb20">
				<table>
					<tbody>
						<tr>
							<th scope="col">과정명</th>
							<td colspan="3"><c:out value="${data.tp_name03[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 형태</th>
							<td colspan="3"><c:out value="${data.tp_format03[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">추천 훈련사업</th>
							<td colspan="3"><c:out value="${data.tp_recommend03[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 목표</th>
							<td colspan="3"><c:out value="${data.tp_goal03[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">주요 훈련 내용</th>
							<td colspan="3"><c:out value="${data.tp_contents03[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 대상</th>
							<td colspan="3"><c:out value="${data.tp_target03[0]}" /></td>
						</tr>
						<tr>
							<th scope="row" rowspan="11">훈련 내용</th>
							<th scope="row" class="bg01">교과목명</th>
							<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
							<th scope="row" class="bg01">추천 훈련시간</th>
						</tr>
						<c:forEach var="list" items="${data.tp_courseName03}"
							varStatus="i">
							<tr>
								<td><c:out value="${list}" /></td>
								<td><c:out value="${data.tp_details03[i.index]}" /></td>
								<td><c:out value="${data.tp_recommendTime03[i.index]}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>


			<h5 class="title-type03">훈련과정 명세서4</h5>

			<div class="table-type02 horizontal-scroll mb20">
				<table>
					<tbody>
						<tr>
							<th scope="col">과정명</th>
							<td colspan="3"><c:out value="${data.tp_name04[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 형태</th>
							<td colspan="3"><c:out value="${data.tp_format04[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">추천 훈련사업</th>
							<td colspan="3"><c:out value="${data.tp_recommend04[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 목표</th>
							<td colspan="3"><c:out value="${data.tp_goal04[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">주요 훈련 내용</th>
							<td colspan="3"><c:out value="${data.tp_contents04[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 대상</th>
							<td colspan="3"><c:out value="${data.tp_target04[0]}" /></td>
						</tr>
						<tr>
							<th scope="row" rowspan="11">훈련 내용</th>
							<th scope="row" class="bg01">교과목명</th>
							<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
							<th scope="row" class="bg01">추천 훈련시간</th>
						</tr>
						<c:forEach var="list" items="${data.tp_courseName04}"
							varStatus="i">
							<tr>
								<td><c:out value="${list}" /></td>
								<td><c:out value="${data.tp_details01[i.index]}" /></td>
								<td><c:out value="${data.tp_recommendTime01[i.index]}" /></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>


			<h5 class="title-type03 page-start">훈련과정 명세서5</h5>

			<div class="table-type02 horizontal-scroll mb20">
				<table>
					<tbody>
						<tr>
							<th scope="col">과정명</th>
							<td colspan="3"><c:out value="${data.tp_name05[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 형태</th>
							<td colspan="3"><c:out value="${data.tp_format05[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">추천 훈련사업</th>
							<td colspan="3"><c:out value="${data.tp_recommend05[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 목표</th>
							<td colspan="3"><c:out value="${data.tp_goal05[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">주요 훈련 내용</th>
							<td colspan="3"><c:out value="${data.tp_contents05[0]}" /></td>
						</tr>
						<tr>
							<th scope="col">훈련 대상</th>
							<td colspan="3"><c:out value="${data.tp_target05[0]}" /></td>
						</tr>
						<tr>
							<th scope="row" rowspan="11">훈련 내용</th>
							<th scope="row" class="bg01">교과목명</th>
							<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
							<th scope="row" class="bg01">추천 훈련시간</th>
						</tr>
						<c:forEach var="list" items="${data.tp_courseName05}"
							varStatus="i">
							<tr>
								<td><c:out value="${list}" /></td>
								<td><c:out value="${data.tp_details05[i.index]}" /></td>
								<td><c:out value="${data.tp_recommendTime05[i.index]}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>


	<div class="page hidden page-start" id="page5">
		<div class="contents-area page-start">
			<div class="contents-box">
				<h3 class="title-type01">[별첨] 분석 세부 내용</h3>
			</div>

			<div class="contents-box">
				<h4 class="title-type02">1. 내부현황 분석</h4>
				<h5 class="title-type03">자체훈련 운영(정부 지원 사업 외 훈련·교육 이력 포함) 등 훈련
					운영 이력</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>자체훈련 운영(정부 지원 사업 외 훈련·교육 이력 포함) 등 훈련 운영 이력표 :
							훈련 실시 이력(연번, 참여 사업, 훈련과정명, 훈련방법, 훈련시간)에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 10%" />
							<col style="width: 10%" />
							<col style="width: 15%" />
							<col style="width: 30%" />
							<col style="width: 20%" />
							<col style="width: 15%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">구분</th>
								<th scope="col">연번</th>
								<th scope="col">참여&nbsp;사업</th>
								<th scope="col">훈련과정명</th>
								<th scope="col">훈련방법</th>
								<th scope="col">훈련시간</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="12">훈련실시이력</td>
							<tr>
							<c:forEach var="list" items="${data.tp_his_seq}" varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out value="${data.tp_his_name[i.index]}" /></td>
									<td><c:out value="${data.tp_his_courseName[i.index]}" /></td>
									<td><c:out value="${data.tp_his_method[i.index]}" /></td>
									<td><c:out value="${data.tp_his_time[i.index]}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>

			<div class="contents-box page-start">
				<h4 class="title-type02">2. 외부환경 분석</h4>
				
				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<caption>외부환경 분석</caption>
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 85%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="left"><c:out value="${data.uilization02[0]}" /></td>
							</tr>
						</tbody>
					</table>
				</div>

			</div>


			<div class="contents-box">
				<h4 class="title-type02">3. 직무역량 모델링</h4>
				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<thead>
							<tr>
								<th scope="col" rowspan="2">직무명</th>
								<th scope="col" rowspan="2">수행 준거</th>
								<th scope="col" colspan="3">필요지식ㆍ스킬ㆍ능력</th>
							</tr>
							<tr>
								<th scope="col">지식(학술, 직무지식)</th>
								<th scope="col">스킬(기능)</th>
								<th scope="col">태도</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="list" items="${data.modeling_jobTitle}"
								varStatus="i">
								<tr>
									<td><c:out value="${list}" /></td>
									<td><c:out
											value="${data.modeling_performanceCriteria[i.index]}" /></td>
									<td><c:out value="${data.modeling_Knowledge[i.index]}" /></td>
									<td><c:out value="${data.modeling_skill[i.index]}" /></td>
									<td><c:out value="${data.modeling_attitude[i.index]}" /></td>
								</tr>
							</c:forEach>
						</tbody>
						
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="paging-navigation-wrapper exclude-from-print">
		<p class="paging-navigation">
			<strong>1</strong> <a href="javascript:void(0);" id="js-page2-btn"
				onclick="showPage(this, 2);">2</a> <a href="javascript:void(0);"
				id="js-page3-btn" onclick="showPage(this, 3);">3</a> <a
				href="javascript:void(0);" id="js-page4-btn"
				onclick="showPage(this, 4);">4</a> <a href="javascript:void(0);"
				id="js-page5-btn" onclick="showPage(this, 5);">5</a>
		</p>
	</div>

	<div class="fr mt50 exclude-from-print">
		<a href="javascript:void(0);" class="btn-m01 btn-color03"
			onclick="printReport()">출력</a>
		<c:choose>
			<c:when
				test="${loginVO.usertypeIdx eq 5 and report.confmStatus eq 10}">
				<a href="javascript:void(0);" class="btn-m01 btn-color02"
					onclick="reportStatusUpdate(50)">기업승인</a>
				<a href="javascript:void(0);" class="btn-m01 btn-color02"
					onclick="openModal('reportRejectModal')">반려</a>
			</c:when>
			<c:when
				test="${loginVO.usertypeIdx eq 20 and report.confmStatus eq 50}">
				<a href="javascript:void(0);" class="btn-m01 btn-color02"
					onclick="reportStatusUpdate(55)">최종승인</a>
				<a href="javascript:void(0);" class="btn-m01 btn-color02"
					onclick="openModal('reportRejectModal')">반려</a>
			</c:when>
		</c:choose>
		<a href="javascript:void(0);" class="btn-m01 btn-color01"
			onclick="history.go(-1);">목록</a>
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
							<textarea id="confmCn" name="confmCn" rows="4"
								placeholder="의견을 입력하세요"></textarea>
						</dd>
					</dl>
				</div>
			</div>
			<div class="btns-area">
				<button type="button" class="btn-m02 btn-color01 three-select"
					onclick="reportStatusUpdate(40)">반려</button>
				<button type="button" id="closeBtn_05"
					onclick="closeModal('reportRejectModal')"
					class="btn-m02 btn-color02 three-select">
					<span> 닫기 </span>
				</button>
			</div>
		</div>
	</div>
</div>
<!-- //CMS 끝 -->
<script type="text/javascript"
	src="${contextPath}${jsPath}/report/indepthDgns.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
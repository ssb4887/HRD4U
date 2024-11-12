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
</style>
<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/report/indepthDgns.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area02">
		<input type="hidden" name="mId" value="${crtMenu.menu_idx}" />
		<form id="report-form">
			<input type="hidden" name="cnslIdx" value="<c:out value='${cnsl.cnslIdx}' />" />
			<input type="hidden" id="bplNo" name="bplNo" value="<c:out value='${cnsl.bplNo}' />" />
			<input type="hidden" name="reprtIdx" value="<c:out value='${report.reprtIdx}' />" />
			<!-- page1 -->
			<div class="page" id="page1">
				<div class="contents-box">
					<h3 class="title-type01">1. 심층 진단개요</h3>
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>본 장은 심층 진단의 필요성, 주요 활동, 주요 결과를 한 눈에 제시하고자 하는 목적을 가지고 있음</li>
						</ul>
					</div>
				</div>
	
				<div class="contents-box">
					<h4 class="title-type02">1. 심층 진단 필요성</h4>
	
					<div class="text-box mt20 mb20">
						<textarea id="" name="necessity" cols="50" rows="5" placeholder="심층 진단 필요성을 입력해주세요." class="w100" title="심층 진단 필요성 입력" required ><c:out value='${data.necessity[0]}' /></textarea>
					</div>
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>컨설팅 대상 기업의 대표 또는 경영진 이상 관계자와의 회의를 통해 파악된 심층 진단의 필요성 작성 (3줄 넘지 않도록 간단히 기술)</li>
						</ul>
					</div>
				</div>
	
				<div class="contents-box">
					<h4 class="title-type02">2. 심층 진단 주요 활동</h4>
					<div class="table-type02 horizontal-scroll mb20">
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
									<td class="left" rowspan="3">
										<c:out value="${list.mtgWeekExplsn1}"/>
										<c:if test="${not empty list.mtgWeekExplsn2}">
											, <c:out value="${list.mtgWeekExplsn2}"/>
										</c:if>
									</td>
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
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>컨설팅 수행일지 주요내용 반영하여 시스템에서 자동생성됨</li>
						</ul>
					</div>
				</div>
	
	
				<div class="contents-box">
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
										<input type="text" class="w100" name="result_target" placeholder="진단 대상 입력" required value="<c:out value='${data.result_target[0]}' />" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="table-type02 horizontal-scroll mb20">
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
									<c:forEach var="i" begin="0" end="${data.result_rank.size() - 1}">
										<tr>
											<td>
												<input type="text" class="w100 rank center" readonly name="result_rank" required value="<c:out value='${data.result_rank[i]}' />" />
											</td>
											<td>
												<select class="w100" name="result_type">
													<option value="역량차원" <c:if test="${data.result_type[i] eq '역량차원'}">selected</c:if>>역량차원</option>
													<option value="환경차원" <c:if test="${data.result_type[i] eq '환경차원'}">selected</c:if>>환경차원</option>
												</select>
											</td>
											<td class="left">
												<input type="text" class="w100" name="result_task" placeholder="과제 입력" required value="<c:out value='${data.result_task[i]}' />" />
											</td>
											<td>
												<input type="text" class="w100" name="result_spt_issue" placeholder="관련 현장 이슈 입력" required value="<c:out value='${data.result_spt_issue[i]}' />" />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.result_rank[0]}">
									<tr>
										<td>
											<input type="text" class="w100 rank center" readonly name="result_rank" value="1" required />
										</td>
										<td>
											<select class="w100" name="result_type">
												<option value="역량차원">역량차원</option>
												<option value="환경차원">환경차원</option>
											</select>
										</td>
										<td class="left">
											<input type="text" class="w100" name="result_task" placeholder="과제 입력" required />
										</td>
										<td>
											<input type="text" class="w100" name="result_spt_issue" placeholder="관련 현장 이슈 입력" required />
										</td>
									</tr>
								</c:if>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
										<a href="javascript:void(0);" class="btn-m02 btn-color03" onclick="addRow(this, 5)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01" onclick="deleteRow(this)">삭제</a>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>심층 진단 대상 부서(또는 직무) 입력, 필요 시 전사가 진단 대상이 될 수도 있음</li>
							<li>도출된 과제를 우선순위별로 제시하고 해당 과제 추진과 관련된 현장 이슈 제시</li>
							<li>역량차원, 환경차원 과제 중에 해당 과제 없을 경우는 정보 제시하지 않음</li>
						</ul>
					</div>
				</div>
	
	
				<div class="contents-box">
					<div class="text-box mt20 mb20">
						<textarea cols="50" rows="5" name="result_outline" placeholder="심증진단의 최종 과제와 향후 운영 방안 입력(약술)" class="w100" title="심증진단의 최종 과제와 향후 운영 방안 입력(약술)" required><c:out value='${data.result_outline[0]}' /></textarea>
					</div>
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>심층진단의 최종 제시된 과제와 향후 운영 방안 제언 약술</li>
						</ul>
					</div>
				</div>
			</div>
			
			<!-- page2 -->
			<div class="page hidden" id="page2">
				<div class="contents-box">
					<h3 class="title-type01">2. 현장 이슈 및 원인 도출</h3>
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								본 단계는 심층 진단 대상 부서/직무의 현장 이슈를 도출하고 이슈가 되고 있는 문제의
								원인을 역량차원과 작업 환경차원으로 크게 나누어 도출하는 단계임
							</li>
							<li>
								본 진단 방식은 워크숍을 통해 참여한 기업 관계자들이 컨설턴트의 퍼실리테이팅에 따라 스스로
								문제와 원인을 도출하는 방식으로 기업 관계자의 참여와 본 워크숍을 기획하고 진행하는 컨설턴트의 문제해결
								퍼실리테이팅 역량이 중요함
							</li>
							<li>
								기업 내부상황으로 워크숍 진행이 어려운 경우, 내부 관계자 대상 인터뷰와 설문 그리고
								기업내부 담당자와의 회의 등 다른 방법의 진행도 가능함
							</li>
							<li>
								요구 작성 내용 외 제시 필요 내용은 관련 자료를 업로드 하는 방식으로 보고서에 추가 제시
							</li>
						</ul>
					</div>
				</div>
	
				<div class="contents-box">
					<h4 class="title-type02">1. 현장 이슈 도출</h4>
					<div class="table-type02 mb20">
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
												<input type="text" class="w100" name="spt_issue" placeholder="현장 이슈 입력" required value="<c:out value='${data.spt_issue[i]}' />" />
											</td>
											<td>
												<input type="text" class="w100" name="spt_desc" placeholder="이슈 관련 세부 내용 입력" required value="<c:out value='${data.spt_desc[i]}' />" />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_issue[0]}">
									<tr>
										<td>
											<input type="text" class="w100" name="spt_issue" placeholder="현장 이슈 입력" required />
										</td>
										<td>
											<input type="text" class="w100" name="spt_desc" placeholder="이슈 관련 세부 내용 입력" required />
										</td>
									</tr>
								</c:if>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="2">
										<a href="javascript:void(0);" class="btn-m02 btn-color03" onclick="addRow(this, 5)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01" onclick="deleteRow(this)">삭제</a>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>도출된 현장 이슈와 각 이슈별 세부내용 작성</li>
						</ul>
					</div>
				</div>
	
				<div class="contents-box">
					<div class="text-box mt20 mb20">
						<textarea cols="50" rows="5"  name="spt_cn" placeholder="현장 이슈 도출 과정 및 방법 기술" class="w100" title="현장 이슈 도출 과정 및 방법 기술" required><c:out value='${data.spt_cn[0]}' /></textarea>
					</div>
	
					<div class="gray-box01 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>현장 이슈 도출 과정 및 방법에 대한 내용 기술</li>
							<li>이슈 도출을 위한 워크숍 진행방법
								<ul>
									<li class="line-bullet">
										워크숍 시작 시, 워크숍의 목적/배경, 진행방식에
										대해 설명. 워크숍 사전 진행된 회의의 주요 내용도 참석자들에게 설명함
									</li>
									<li class="line-bullet">
										컨설턴트는 이슈가 되는 문제의 개념과 문제
										도출방식에 대해 참석자들에게 설명하고 참석자들 스스로 문제를 도출할 수 있도록 퍼실리테이팅함
									</li>
									<li class="line-bullet">
										참석자 규모가 1개 조일 경우는 해당 조의 문제
										도출과정을 컨설턴트가 직접 퍼실리테이팅하고 여러 조일 경우에는 각 조의 조장을 선발하여 각 조에서
										진행하도록 하되 컨설턴트는 전체 상황을 진행함
									</li>
									<li class="line-bullet">
										심층 진단에서 다루는 이슈는 직원들의 업무 수행상의 문제임
									</li>
								</ul>
							</li>
							<li>작성한 내용 외 제시 원하는 파일 있을 시 업로드</li>
						</ul>
					</div>
	
	
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">첨부파일</th>
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName01" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file01" class="btn_file" name="spt_file" onclick="addFile(event, 1, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-1">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_file'}">
														<p class="ml0 mt10">
															<input type="file" name="spt_file" class="hidden" />
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
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
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
												<input type="text" class="w100" name="spt_cause_issue" placeholder="현장 이슈 입력" required value="<c:out value='${data.spt_cause_issue[i]}' />" />
											</td>
											<td>
												<select class="w100" name="spt_cause_type">
													<option value="역량차원" <c:if test="${data.spt_cause_type[i] eq '역량차원'}">selected</c:if>>역량차원</option>
													<option value="환경차원" <c:if test="${data.spt_cause_type[i] eq '환경차원'}">selected</c:if>>환경차원</option>
												</select>
											</td>
											<td>
												<input type="text" class="w100" name="spt_cause" placeholder="원인 입력" required value="<c:out value='${data.spt_cause[i]}' />" />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_cause_issue[0]}">
									<tr>
										<td>
											<input type="text" class="w100" name="spt_cause_issue" placeholder="현장 이슈 입력" required />
										</td>
										<td>
											<select class="w100" name="spt_cause_type">
												<option value="역량차원">역량차원</option>
												<option value="환경차원">환경차원</option>
											</select>
										</td>
										<td>
											<input type="text" class="w100" name="spt_cause" placeholder="원인 입력" required />
										</td>
									</tr>
								</c:if>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="3">
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
							<li>현장이슈별 원인 유형으로 나누어 작성</li>
							<li>원인 제시 방법
								<ul>
									<li class="line-bullet">역량 또는 환경 차원 둘 다 원인이 있을 경우 둘다 제시하고, 역량 또는 환경 차원의 원인이 없을 경우는 ‘-’으로 표기</li>
									<li class="line-bullet">원인이 2개 이상일 경우는 쉼표로 기술. 원인은 1줄 넘지 않도록 간단명료하게 제시</li>
									<li class="line-bullet">위 표 내용 외에 세부내용은 아래에 추가 기술하고, 제시 원하는 파일 있을 시 업로드</li>
								</ul>
							</li>
						</ul>
					</div>
	
					<div class="text-box">
						<textarea cols="50" rows="5" class="w100" name="spt_cause_cn" placeholder="현장 이슈별 원인 도출 과정/방법에 대한 내용 기술"><c:out value='${data.spt_cause_cn[0]}' /></textarea>
					</div>
				</div>
	
				<div class="contents-box">
					<div class="gray-box01 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>현장 이슈별 원인 도출 과정/방법에 대한 내용 기술</li>
							<li>원인 분석 방법
								<ul>
									<li class="line-bullet">
										앞 단계에서 도출된 해당 현장 이슈별로 이슈의
										원인을 해당 업무수행자의 역량(지식/기술)차원과 작업 환경차원(인센티브, 직무 프로세스 등)으로
										나누어 파악함
									</li>
									<li class="line-bullet">
										원인분석 방법은 Gilbert 행동공학모형, 갭
										재퍼, Mager& Pipe 수행문제 분석모형 등 진단 대상 부서/직무의 문제상황 특성을 고려해 해당
										모델들을 참고하여 다양한 질문 풀을 구성해 원인을 파악함
									</li>
									<li class="line-bullet">
										원인 분석 방법론(툴)은 다음 페이지부터 제시된
										특정 툴을 제시해도 되고, 컨설턴트의 노하우와 기업의 문제 상황에 따라 자유롭게 적용할 수 있음.
										다만 어떤 방법이던 ‘수행진단’의 취지에는 부합해야 함
									</li>
									<li class="line-bullet">
										원인 파악의 절차는 5WHY나 프로세스 매핑 등 문제해결 절차를 활용함
									</li>
								</ul>
							</li>
							<li>작성한 내용 외 제시 원하는 파일 있을 시 업로드</li>
						</ul>
					</div>
	
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">첨부파일</th>
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName02" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file02" class="btn_file" name="spt_cause_file" onclick="addFile(event, 2, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-2">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_cause_file'}">
														<p class="ml0 mt10">
															<input type="file" name="spt_cause_file" class="hidden" />
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
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
	
				<div class="contents-box">
					<h5 class="title-type03">(참고) 원인분석 방법: Gilbert 행동공학모형</h5>
	
					<div class="table-type02 horizontal-scroll">
						<table>
							<caption>(참고) 원인분석 방법: Gilbert 행동공학모형표 : 환경차원(자료, 도구, 인센티브), 역량 차원(지식, 능력)에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 10%" />
								<col style="width: 30%" />
								<col style="width: 30%" />
								<col style="width: 30%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" rowspan="4">환경 차원</th>
									<th scope="row" class="bg01">자료</th>
									<th scope="row" class="bg01">도구</th>
									<th scope="row" class="bg01">인센티브</th>
								</tr>
								<tr>
									<td class="left">직원들은 각자 자신에게 무엇이 기대 되는지를 알고 있는가?</td>
									<td class="left">직원들의 수행활동에 적합한 일관련 도구 및 절차가 마련되어있는가?</td>
									<td class="left">직원들의 수행성과에 연동된 금전적 인센티브가 제공되고 있는가?</td>
								</tr>
								<tr>
									<td class="left">직원들은 각자 자신들이 얼마나 일을 잘하고 있는지를 알고있는가?</td>
									<td class="left">직원들의 수행 활동에 필요한 자원이 적절하게 제공되고 있는가?</td>
									<td class="left">직원들의 수행성과에 연동된 비금전적 인센티브가 제공되고 있는가?</td>
								</tr>
								<tr>
									<td class="left">직원들은 각자 자신들의 수행성과에 대한 피드백을 받고 있는가?</td>
									<td class="left"></td>
									<td class="left">직원들에게 경력개발의 기회가 제공되는가?</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">역량 차원</th>
									<th scope="row" class="bg01">지식</th>
									<th scope="row" class="bg01">능력</th>
									<td rowspan="2" class="bg01">&nbsp;</td>
								</tr>
								<tr>
									<td class="left">직원들은 기대되는 수행 기준을 충족시키기 위한 지식을 보유하고 있는가?</td>
									<td class="left">직원들은 직무 수행을 위한 신체적 능력과 적성을 보유하고 있는가?</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type03">(참고) 원인분석 방법: 갭 재퍼</h5>
					<div class="table-type02 horizontal-scroll">
						<table>
							<caption>원인분석 방법: 갭 재퍼 표 : 구분, 내용에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 10%" />
								<col style="width: 45%" />
								<col style="width: 45%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">구분</th>
									<th scope="col" colspan="2">내용</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td rowspan="6">직무 환경</td>
									<th scope="row" class="bg01">1. 역할과 기대의 명확하</th>
									<th scope="row" class="bg01">2. 코칭과 강화</th>
								</tr>
								<tr>
									<td class="left">
										<ul class="ul-list01">
											<li>수행수준 기대와 표준을 명확화</li>
											<li>업무 영역 및 직무 결과물을 포함한 역할 명료화</li>
											<li>직무에 필요한 충분한 인적자원</li>
											<li>직무구조</li>
											<li>충분한 권한</li>
											<li>직위에 적절한 업무 부담</li>
										</ul>
									</td>
	
									<td class="left">
										<ul class="ul-list01">
											<li>직무에 대한 코칭과 강화</li>
											<li>적절한 전문가의 지원</li>
											<li>‘올바른 일의 수행’여부 확인</li>
										</ul>
									</td>
								</tr>
	
								<tr>
									<th scope="row" class="bg01">3. 인센티브</th>
									<th scope="row" class="bg01">4. 직무 시스템과 프로세스</th>
								</tr>
								<tr>
									<td class="left">
										<ul class="ul-list01">
											<li>금전적 인센티브</li>
											<li>의미 있는 보상</li>
											<li>기대 성과 미달성시 결과</li>
											<li>기대 성과의 가치</li>
											<li>지원적인 문화와 조직 규준</li>
										</ul>
									</td>
	
									<td class="left">
										<ul class="ul-list01">
											<li>기술 정보 시스템</li>
											<li>조직 시스템</li>
											<li>효과적으로 정의된 프로세스</li>
											<li>도구와 작업 환경</li>
										</ul>
									</td>
								</tr>
								<tr>
									<th scope="row" class="bg01" colspan="2">5. 정보, 사람, 도구, 업무 보조도구의 접근성</th>
								</tr>
								<tr>
									<td class="left" colspan="2">
										<ul class="ul-list01">
											<li>수행에 필요한 정보의 정확성, 신뢰성, 유용성</li>
											<li>데이터 베이스</li>
											<li>문서화</li>
											<li>전자 직무 수행 자원 시스템/직무 보조 도구</li>
											<li>전문가</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>역량</td>
									<td>지식</td>
									<td>기술</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type03">(참고) 원인분석 방법: Mager &amp; Pipe 수행문제 분석모형</h5>
					<div class="images-box">
						<img src="${contextPath}${imgPath}/contents/img130101.gif" alt="Mager & Pipe 수행문제 분석모형표" />
						<div class="blind">
							수행차이 규명 문제 중 중요한가? 중요할 경우 지식, 기술, 태도 결여로 이동, 중요하지 않을 경우
							무시한다 지식, 기술, 태도 결여일 경우 과거 사용경험이 있으면 사용빈도 정도를 체크하여 피드백을 받고,
							없을 경우는 실습/연습을 받는다. 과거 사용경험이 없으면 교육훈련을 받는다.<br /> 지식, 기술,
							태도 결여가 없을 경우 수행성과 불이익이 있으면 불이익 저거하고, 수행성과 불이익이 없으면 비(非)수행
							성과 보상이 있으면 적정 보상을 하고 없으면 수행성과 관심 여부를 체크하여 보상 실시하고 없으면 장애물
							존재 여부를 확인하여 있으면 장애물 제거를 한다.<br /> 간단한 방법으로는 직무설계, OJT가 있으며
							잠재력 보유가 없으면 전보, 해고한다. 최적해결책을 선택하여 해결책 실행을 한다.
						</div>
					</div>
				</div>
			</div>
			
			<!-- page3 -->
			<div class="page hidden" id="page3">
				<div class="contents-box">
					<h3 class="title-type01">3. 과제 도출</h3>
					
					<h4 class="title-type02">1. 현장 이슈 원인별 과제 도출</h4>
	
					<h5 class="title-type05">현장 이슈 원인별 과제 도출</h5>
	
					<div class="table-type02 mb20">
						<table>
							<caption>현장 이슈별 원인 도출표 : 현장 이슈, 유형, 원인에 관한 세부
								내용정보표</caption>
							<colgroup>
								<col style="width: 20%">
								<col style="width: 20%">
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
												<input type="text" class="w100" name="spt_task_issue" placeholder="현장 이슈 입력" required value="<c:out value='${data.spt_task_issue[i]}' />" />
											</td>
											<td>
												<select class="w100" name="spt_task_type">
													<option value="역량차원" <c:if test="${data.spt_task_type[i] eq '역량차원'}">selected</c:if>>역량차원</option>
													<option value="환경차원" <c:if test="${data.spt_task_type[i] eq '환경차원'}">selected</c:if>>환경차원</option>
												</select>
											</td>
											<td>
												<input type="text" class="w100" name="spt_task_cause" placeholder="원인 입력" required value="<c:out value='${data.spt_task_cause[i]}' />" />
											</td>
											<td>
												<input type="text" class="w100" name="spt_task" placeholder="과제 입력" required value="<c:out value='${data.spt_task[i]}' />" />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_task_issue[0]}">
									<tr>
										<td>
											<input type="text" class="w100" name="spt_task_issue" placeholder="현장 이슈 입력" required />
										</td>
										<td>
											<select class="w100" name="spt_task_type">
												<option value="역량차원">역량차원</option>
												<option value="환경차원">환경차원</option>
											</select>
										</td>
										<td>
											<input type="text" class="w100" name="spt_task_cause" placeholder="원인 입력" required />
										</td>
										<td>
											<input type="text" class="w100" name="spt_task" placeholder="과제 입력" required />
										</td>
									</tr>
								</c:if>
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
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>
								각 현장이슈와 현장이슈별 원인은 앞에서 도출한 내용을 재작성하고, 원인별 해결과제를
								역량차원과 환경차원으로 나누어 작성함. 역량차원과 환경차원 중 1가지 유형의 과제가 나와도 상관 없음
								<ul>
									<li class="line-bullet">2개 이상의 원인이 하나의 해결과제로 해결되는 경우, 각 원인마다 동일한 해결과제로 제시</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
	
				<div class="contents-box">
					<div class="text-box mt20 mb20">
						<textarea cols="50" rows="5" class="w100" name="spt_task_cn" placeholder="현장 이슈 원인별 후보 과제 도출 과정/방법에 대한 내용 기술" title="심층 진단 필요성 입력" required ><c:out value='${data.spt_task_cn[0]}' /></textarea>
					</div>
	
					<div class="gray-box01 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>현장 이슈 원인별 후보 과제 도출 과정/방법에 대한 내용 기술</li>
							<li>역량차원의 과제 도출
								<ul>
									<li class="line-bullet">
										본 진단에서의 과제란 구체적인 해결방안을 의미하지
										않음. 과제기술서 수준 정도의 구체성을 가진 과제를 의미함
									</li>
									<li class="line-bullet">
										역량차원의 해결 과제는 기본적으로 본 중소기업
										컨설팅의 유형인 과정개발, 훈련체계 수립 그리고 공단의 여러 훈련 관련 사업을 포함하여 컨설턴트의
										적극적인 제시 필요함
									</li>
									<li class="line-bullet">
										특정분야의 과정개발이 시급한지 아니면 전체적인
										훈련체계 수립이 시급한지 정도를 결정하며, 과정개발이 시급한 경우 어떤 분야의 과정개발이 필요한지
										정도를 도출함
									</li>
								</ul>
							</li>
							<li>환경차원의 과제 도출
								<ul>
									<li class="line-bullet">
										작업 환경차원의 과제는 훈련을 통한 해결 이외의
										방법을 의미함. 원인 파악 단계에서 도출된 다양한 원인(ex. 동기부여 미흡, 공정 프로세스 상의
										문제, R&R의 모호성 등)에 대한 해결 과제를 워크숍 참석자들이 스스로 도출할 수 있도록 진행함
									</li>
									<li class="line-bullet">
										각 원인 유형에 따른 다양한 해결방안이 참고정보로 제시되어 있음
									</li>
								</ul>
							</li>
							<li>작성한 내용 외 제시 원하는 파일 있을 시 업로드</li>
						</ul>
					</div>
	
	
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">첨부파일</th>
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName03" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file03" class="btn_file" name="spt_task_file" onclick="addFile(event, 3, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-3">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_task_file'}">
														<p class="ml0 mt10">
															<input type="file" name="spt_task_file" class="hidden" />
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
								</tr>
							</tbody>
						</table>
					</div>
	
				</div>
	
	
				<div class="contents-box">
					<h5 class="title-type05">과제 타당성 검토 결과</h5>
	
					<div class="table-type02 horizontal-scroll mb20">
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
											<td>
												<input type="text" class="w100" name="spt_validity_task" placeholder="과제 입력" required value="<c:out value='${data.spt_validity_task[i]}' />" />
											</td>
											<td>
												<input type="number" min="0" max="5" class="w100" name="spt_validity_slblt" placeholder="적합성 점수 입력" required oninput="handleInputNumber(this);" value="<c:out value='${data.spt_validity_slblt[i]}' />" />
											</td>
											<td>
												<input type="number" min="0" max="5" class="w100" name="spt_validity_eyfcy" placeholder="경제성 점수 입력" required oninput="handleInputNumber(this);" value="<c:out value='${data.spt_validity_eyfcy[i]}' />" />
											</td>
											<td>
												<input type="number" min="0" max="5" class="w100" name="spt_validity_exec" placeholder="실행가능성 점수 입력" required oninput="handleInputNumber(this);" value="<c:out value='${data.spt_validity_exec[i]}' />" />
											</td>
											<td>
												<input type="number" min="0" max="5" class="w100" name="spt_validity_acpt" placeholder="조직수용성 점수 입력" required oninput="handleInputNumber(this);" value="<c:out value='${data.spt_validity_acpt[i]}' />" />
											</td>
											<td>
												<input type="number" class="js-average w100" name="spt_validity_avrg" readonly value="<c:out value='${data.spt_validity_avrg[i]}' />"/>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_validity_task[0]}">
									<tr>
										<td>
											<input type="text" class="w100" name="spt_validity_task" placeholder="과제 입력" required />
										</td>
										<td>
											<input type="number" min="0" max="5" class="w100" name="spt_validity_slblt" placeholder="적합성 점수 입력" required oninput="handleInputNumber(this);" />
										</td>
										<td>
											<input type="number" min="0" max="5" class="w100" name="spt_validity_eyfcy" placeholder="경제성 점수 입력" required oninput="handleInputNumber(this);" />
										</td>
										<td>
											<input type="number" min="0" max="5" class="w100" name="spt_validity_exec" placeholder="실행가능성 점수 입력" required oninput="handleInputNumber(this);" />
										</td>
										<td>
											<input type="number" min="0" max="5" class="w100" name="spt_validity_acpt" placeholder="조직수용성 점수 입력" required oninput="handleInputNumber(this);" />
										</td>
										<td>
											<input type="number" class="js-average w100" name="spt_validity_avrg" readonly/>
										</td>
									</tr>
								</c:if>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6">
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
							<li>과제 타당성 검토
								<ul>
									<li class="line-bullet">
										도출된 과제는 적합성, 경제성, 실행 가능성, 조직수용성 기준을 토대로 적합한지 최종 검토함
									</li>
									<li class="line-bullet">
										과제별로 5점 리커르트 척도[5점(매우그렇다)~1점(전혀 그렇지 않다)]로 과제 타당성 검토 기준별로 평가하고, 평균 점수 4점 미만일
										경우에는 과제에서 제외함
										<div class="table-type02 mt10" style="background-color: #fff">
											<table>
												<caption>과제 타당성 검토 결과 - 작성안내표 : 기준, 기준 설명에 관한 정보 제공표</caption>
												<colgroup>
													<col style="width: 30%" />
													<col style="width: 70%" />
												</colgroup>
												<thead>
													<tr>
														<th scope="col">기준</th>
														<th scope="col">기준 설명</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>적합성<br /> (Appropriate)</td>
														<td class="left">과제는 해당 원인을 해소하기에 적합한가?</td>
													</tr>
													<tr>
														<td>경제성<br /> (Economics)</td>
														<td class="left">과제 실행 비용과 해당 문제에 따른 비용을 고려할 때 경제적인가?</td>
													</tr>
													<tr>
														<td>실행 가능성<br /> (Feasibility)</td>
														<td class="left">해당 과제 실행을 위해 직원들에게 필요한 역량과 시간을 고려할 때 실행 가능한가?</td>
													</tr>
													<tr>
														<td>조직 수용성<br /> (Organizational acceptability)</td>
														<td class="left">해당 과제는 조직문화적으로 수용성이 있는가?</td>
													</tr>
												</tbody>
											</table>
										</div>
									</li>
								</ul>
							</li>
						</ul>
					</div>
	
					<div class="text-box">
						<textarea cols="50" rows="5" class="w100" name="spt_validity_cn" placeholder="과제 타당성 검토 과정/방법에 대한 내용 기술"  title="과제 타당성 검토 과정/방법에 대한 내용 기술" required><c:out value='${data.spt_validity_cn[0]}' /></textarea>
					</div>
				</div>
	
				<div class="contents-area">
					<div class="gray-box01 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>과제 타당성 검토 과정/방법에 대한 내용 기술</li>
							<li>작성한 내용 외 제시 원하는 파일 있을 시 업로드</li>
						</ul>
					</div>
	
	
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">첨부파일</th>
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName04" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file04" class="btn_file" name="spt_validity_file" onclick="addFile(event, 4, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-4">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'spt_validity_file'}">
														<p class="ml0 mt10">
															<input type="file" name="spt_validity_file" class="hidden" />
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
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type05">선정 과제</h5>

					<div class="table-type02 horizontal-scroll mb20">
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
												<select class="w100" name="spt_slctntask_type">
													<option value="역량차원" <c:if test="${data.spt_slctntask_type[i] eq '역량차원'}">selected</c:if>>역량차원</option>
													<option value="환경차원" <c:if test="${data.spt_slctntask_type[i] eq '환경차원'}">selected</c:if>>환경차원</option>
												</select>
											</td>
											<td>
												<input type="text" class="w100" name="spt_slctntask_task" placeholder="과제 입력" required value="<c:out value='${data.spt_slctntask_task[i]}' />" />
											</td>
											<td>
												<input type="text" class="w100 rank center" readonly name="spt_slctntask_rank" required value="<c:out value='${data.spt_slctntask_rank[i]}' />" />
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty data.spt_slctntask_type[0]}">
									<tr>
										<td>
											<select class="w100" name="spt_slctntask_type">
												<option value="역량차원">역량차원</option>
												<option value="환경차원">환경차원</option>
											</select>
										</td>
										<td>
											<input type="text" class="w100" name="spt_slctntask_task" placeholder="과제 입력" required />
										</td>
										<td>
											<input type="text" class="w100 rank center" readonly name="spt_slctntask_rank" value="1" required />
										</td>
									</tr>
								</c:if>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6">
										<a href="javascript:void(0);" class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01" onclick="deleteRow(this)">삭제</a>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
	
	
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>과제 타당성 검토 결과 평균 4점 이상 받은 과제를 중복 없이 유형(역량차원/환경차원)으로 나누어 우선순위별로 제사함</li>
						</ul>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type03">(참고) 역량차원 해결안 도출 예시</h5>
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>(참고) 역량차원 해결안 도출 예시표 : 직무역량요인_조직원 직무역량교육(예시)표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" colspan="2">직무역량요인_조직원 직무 역량교육(예시)</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">규명된 원인</th>
									<th scope="row" class="bg01">가능한 해결안/조치</th>
								</tr>
								<tr>
									<td>조직원별 직무역량 관련 지식, 기술 불균형</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직무 수행에 필요한 역량의 지식, 기술을 향상시키기 위해 직무 역량교육을 실시</li>
											<li>조직 내부 고성과자가 주체가 되어 교육 실시</li>
										</ul>
									</td>
								</tr>
	
								<tr>
									<td>신규입직자가 많아 설비 관련 역량 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직무 관련 외부 위탁교육</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="table-type02 horizontal-scroll">
						<table>
							<caption>직무역량요인_역량 향상을 위한 과정개발(예시)표</caption>
							<colgroup>
								<col style="width: 25%" />
								<col style="width: 25%" />
								<col style="width: 25%" />
								<col style="width: 25%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" colspan="4">직무역량요인_역량 향상을 위한 과정개발(예시)</th>
								</tr>
								<tr>
									<td>과정명</td>
									<td>공정 설비 관리</td>
									<td>총 훈련시간(h)</td>
									<td>16</td>
								</tr>
								<tr>
									<td>훈련목표</td>
									<td colspan="3">설비가 보유하고 있는 본래의 기능을 유지하기 위해 설비 점검 계획수립, 설비 일상 점검 실시, 설비 자주 보전 실시를 수행할 수 있다.</td>
								</tr>
								<tr>
									<th scope="row" colspan="4" class="bg02">훈련내용</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">학습모듈</th>
									<th scope="row" colspan="2" class="bg01">학습내용</th>
									<th scope="row" class="bg01">시간(h)</th>
								</tr>
								<tr>
									<td>1. 설비 점검계획 수립하기</td>
									<td colspan="2" class="left">
										<ul class="ul-list01">
											<li>기준에 충실한 일상 점검 항목 설정</li>
											<li>일상 점검 항목의 자주·계획 보전 역할 구분</li>
											<li>효율적인 설비 일상 점검 계획서 작성</li>
										</ul>
									</td>
									<td>6</td>
								</tr>
								<tr>
									<td>2. 설비 일상점검 실시하기</td>
									<td colspan="2" class="left">
										<ul class="ul-list01">
											<li>일상 점검 체크리스트 작성</li>
											<li>일상 점검 실시 및 이상 징후 발견 시 조치</li>
										</ul>
									</td>
									<td>4</td>
								</tr>
								<tr>
									<td>3. 설비 자주보전 실시하기</td>
									<td colspan="2" class="left">
										<ul class="ul-list01">
											<li>설비의 특성에 맞는 자주 보전 계획수립</li>
											<li>설비의 기본 조건 유지 및 소정비 실시</li>
											<li>불합리 발견과 개선(안) 수립, 실시 및 일상 점검 항목의 개정</li>
										</ul>
									</td>
									<td>6</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
	
				<div class="contents-box">
					<h5 class="title-type03">(참고) 환경차원 해결안 도출 예시</h5>
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>환경차원 해결안 도출 예시 - 직무환경요인_역할 기대의 명확화(예시)표
							</caption>
							<colgroup>
								<col style="width: 30%">
								<col style="width: 70%">
							</colgroup>
							<tbody>
								<tr>
									<th colspan="2" scope="row">직무환경요인_역할 기대의 명확화(예시)</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">규명된 원인</th>
									<th scope="row" class="bg01">가능한 해결안/조치</th>
								</tr>
								<tr>
									<td>성과 기대 및 표준의 불명확성 - 목표의 명확성 결여</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직무 성과와 표준에 대한 기대감 형성</li>
											<li>비전, 미션, 전략의 명확화</li>
											<li>비전, 목표 및 전략을 논의할 커뮤니케이션 계획을 수립하고 실행</li>
											<li>직원들의 기대를 명확히 하기 위해 관리자들이 그들과 미팅을 갖도록 독려</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>직무가 다른 직원들 간의 역할 혼돈</td>
									<td class="left">
										<ul class="ul-list01">
											<li>역할을 명확히 하기 위해 경영자와의 파트너쉽</li>
											<li>직무 프로세스의 재설계와 역할의 재정의</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>희망하는 수행의 수준을 달성하기 위해 구조화한 업무 방식을 벗어나 일함</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직무를 위해 직무 흐름 분석을 수행</li>
											<li>직무 재설계 프로세스를 수행</li>
											<li>직무환경 장애물의 제거</li>
											<li>차선택을 표준 업무 관행으로 제도화</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>직무 수행에 필요한 권한의 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>업무 영역을 정의하기 위해 관리자/팀장과 협업하고 업무 권한을 재정의</li>
											<li>새로운 업무 권한 내에서 올바른 의사 결정을 할 수 있도록 직원들의 능력개발</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>직원이 직무를 완수하기에는 업무 부담이 너무 큼</td>
									<td class="left">
										<ul class="ul-list01">
											<li>업무 부담, 우선순위, 자원의 분석: 필요한 만큼 재분배, 외주 또는 자원의 총원</li>
											<li>선택사항으로 사무 자동화/컴퓨터화 검토</li>
											<li>직무 프로세스의 간결화 방안 검토</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>환경차원 해결안 도출 예시 - 직무환경요인_코칭과 강화(예시)표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th colspan="2" scope="row">직무환경요인_코칭과 강화(예시)</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">규명된 원인</th>
									<th scope="row" class="bg01">가능한 해결안/조치</th>
								</tr>
								<tr>
									<td>직무에 대한 불충분한 코칭과 강화</td>
									<td class="left">
										<ul class="ul-list01">
											<li>일부 팀/직무 그룹 직원들에게 코칭 시스템과 코칭 역량을 구축</li>
											<li>일부 팀/직무 그룹 직원들에게 강화 시스템과 피드백 역량을 구축</li>
											<li>관리자들로 하여금 코칭의 책임을 부여하고, 직원들에게 피드백을 제공하도록 함</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>적절한 전문가의 지원 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>기술적인 지원 확대</li>
											<li>전자 작업 지원 시스템의 구축</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>&lsquo;올바른 일의 수행&rsquo;에 대한 인식 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직원들을 좀 더 인정하자는 요구에 대한 인식의 확산</li>
											<li>긍정적인 사건을 인식하기 위한 도구 또는 기술의 제공</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>환경차원 해결안 도출 예시 - 직무환경요인_인센티브(예시)표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" colspan="2">직무환경요인_인센티브(예시)</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">규명된 원인</th>
									<th scope="row" class="bg01">가능한 해결안/조치</th>
								</tr>
								<tr>
									<td>부적절하고 비효과적인 금전적 인센티브</td>
									<td class="left">
										<ul class="ul-list01">
											<li>보상 시스템의 개정</li>
											<li>비금전적 보상의 제공을 결정, 새로운 보상 지급 계획의 수립</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>직원에게 의미 있는 보상의 부족: 요구 성과에 대한 처벌 존재 및 보상 시스템이 불공정하고 불공평함</td>
									<td class="left">
										<ul class="ul-list01">
											<li>인센티브 그리고 성과 시스템의 개정</li>
											<li>비금전적 보상 제공의 결정</li>
											<li>유사 조직의 벤치마킹</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>기대 성과를 달성하지 못함에 따른 결과의 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>기대 성과 미달성에 따른 결과 조치 개발</li>
											<li>저성과자 관리를 위한 안내서를 관리자에 제공</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>기대 성과에 대한 가치 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>동기 부여 시스템의 구축: 긍정적 강화의 제공</li>
											<li>기대 성과에 대한 사업/기타 이익을 명시화</li>
											<li>멘토와 역할 모델의 제공</li>
											<li>최상의 성과에 대한 인정</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>무관심, 따분한 업무</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직무와 업무 흐름의 재설계</li>
											<li>선택사항으로 사무 자동화/컴퓨터화 검토</li>
											<li>선택사항으로 따분한 업무에 직원 순환 배치 검토</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
	
					</div>
	
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>환경차원 해결안 도출 예시 - 직무환경요인_직무 시스템과 프로세스(예시)표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" colspan="2">직무환경요인_직무 시스템과 프로세스(예시)</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">규명된 원인</th>
									<th scope="row" class="bg01">가능한 해결안/조치</th>
								</tr>
								<tr>
									<td>성과를 가로막는 조직 시스템</td>
									<td class="left">
										<ul class="ul-list01">
											<li>요구되는 성과를 달성할 수 있도록 조직 시스템을 재구축</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>정의된 프로세스의 부족: 프로세스가 비효과적이고 기대 성과를 가로막음</td>
									<td class="left">
										<ul class="ul-list01">
											<li>프로세스 평가의 실시</li>
											<li>프로세스 디자인/재디자인</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>요구 직무를 수행하기에 불충분한 직원 수</td>
									<td class="left">
										<ul class="ul-list01">
											<li>외주나 고용을 통한 추가적인 자원의 확보</li>
											<li>제거 가능한 직무 파악</li>
											<li>다양한 직위 간 업무의 재분배</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>성과를 저해하는 물리적 장애물</td>
									<td class="left">
										<ul class="ul-list01">
											<li>환경 연구 실행</li>
											<li>물리적 장애물 제거</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>기대 성과를 막는 장애물</td>
									<td class="left">
										<ul class="ul-list01">
											<li>업무 방해물의 파악과 제거</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>환경차원 해결안 도출 예시 - 직무환경요인_정보, 사람, 도구, 그리고 업무 보조도구의 접긍성(예시)표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" colspan="2">직무환경요인_정보, 사람, 도구, 그리고 업무 보조도구의 접긍성(예시)</th>
								</tr>
								<tr>
									<th scope="row" class="bg01">규명된 원인</th>
									<th scope="row" class="bg01">가능한 해결안/조치</th>
								</tr>
								<tr>
									<td>수행에 필요한 정보의 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>요구되는 정보의 문서화</li>
											<li>직원에게 정보 저장소 접근 허용</li>
											<li>업무 보조도구 개발</li>
											<li>전자 작업 지원 시스템(EPSS) 제공</li>
											<li>지식 관리 시스템 구축</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>데이터베이스 또는 전문가에 접근 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>직원에 대한 접근 허용</li>
											<li>필요한 데이터베이스 구축</li>
											<li>지식 실천 공동체(communities of practice) 구축</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>쉽게 참조할 수 있는 절차나 지식의 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>업무 보조물이나 도구 제공</li>
											<li>전자 작업 지원 시스템(EPSS) 제공</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>도구, 자료의 부족</td>
									<td class="left">
										<ul class="ul-list01">
											<li>적절한 도구의 구매 또는 임대</li>
											<li>적절한 자료 제공</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="contents-box">
					<h4 class="title-type02">과제기술서 도출</h4>
					<c:if test="${not empty data.taskdesc_task[0]}">
						<c:forEach var="i" begin="0" end="${data.taskdesc_task.size() - 1}">
							<div class="table-type02 horizontal-scroll mb20 js-table-box-1">
								<table>
									<caption>과제기술서 도출표</caption>
									<colgroup>
										<col style="width: 20%" />
										<col style="width: 80%" />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="bg01">과제</th>
											<td>
												<input type="text" class="w100" name="taskdesc_task" placeholder="과제 입력" required value="<c:out value='${data.taskdesc_task[i]}' />" />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">참여 검토 지원사업</th>
											<td>
												<input type="text" class="w100" name="taskdesc_biz" placeholder="참여 검토 지원사업 입력" required value="<c:out value='${data.taskdesc_biz[i]}' />" />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">기대효과</th>
											<td>
												<input type="text" class="w100" name="taskdesc_effect" placeholder="기대효과 입력" required value="<c:out value='${data.taskdesc_effect[i]}' />" />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">추진 내용</th>
											<td class="editor-area">
												<textarea name="taskdesc_desc" placeholder="추진내용 입력" required><c:out value='${data.taskdesc_desc[i]}' /></textarea>
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">고려사항</th>
											<td>
												<input type="text" class="w100" name="taskdesc_consider" placeholder="고려사항 입력" required value="<c:out value='${data.taskdesc_consider[i]}' />" />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">담당부서</th>
											<td>
												<input type="text" class="w100" name="taskdesc_dept" placeholder="담당부서 입력" required value="<c:out value='${data.taskdesc_dept[i]}' />" />
											</td>
										</tr>
										<tr>
											<th scope="row" class="bg01">추진일정</th>
											<td>
												<input type="text" class="w100" name="taskdesc_schdul" placeholder="추진일정 입력" required value="<c:out value='${data.taskdesc_schdul[i]}' />" />
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty data.taskdesc_task[0]}">
						<div class="table-type02 horizontal-scroll mb20 js-table-box-1">
							<table>
								<caption>과제기술서 도출표</caption>
								<colgroup>
									<col style="width: 20%" />
									<col style="width: 80%" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" class="bg01">과제</th>
										<td>
											<input type="text" class="w100" name="taskdesc_task" placeholder="과제 입력" required />
										</td>
									</tr>
									<tr>
										<th scope="row" class="bg01">참여 검토 지원사업</th>
										<td>
											<input type="text" class="w100" name="taskdesc_biz" placeholder="참여 검토 지원사업 입력" required />
										</td>
									</tr>
									<tr>
										<th scope="row" class="bg01">기대효과</th>
										<td>
											<input type="text" class="w100" name="taskdesc_effect" placeholder="기대효과 입력" required/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="bg01">추진 내용</th>
										<td class="editor-area"><textarea name="taskdesc_desc" placeholder="추진내용 입력" required></textarea></td>
									</tr>
									<tr>
										<th scope="row" class="bg01">고려사항</th>
										<td>
											<input type="text" class="w100" name="taskdesc_consider" placeholder="고려사항 입력" required />
										</td>
									</tr>
									<tr>
										<th scope="row" class="bg01">담당부서</th>
										<td>
											<input type="text" class="w100" name="taskdesc_dept" placeholder="담당부서 입력" required />
										</td>
									</tr>
									<tr>
										<th scope="row" class="bg01">추진일정</th>
										<td>
											<input type="text" class="w100" name="taskdesc_schdul" placeholder="추진일정 입력" required />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:if>
					
					<div class="center mb20">
						<a href="javascript:void(0);" class="btn-m02 btn-color03" onclick="addTable(1, 5);">과제기술서 추가</a>
						<a href="javascript:void(0);" class="btn-m02 btn-color02" onclick="removeTable(1);">과제기술서 삭제</a>
					</div>
	
					<div class="gray-box01 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>앞 단계에서 도출된 과제 중 우선순위 1~5까지 과제 기술서 도출</li>
							<li>앞 단계에서 도출된 과제의 실행력을 높이기 위해 과제별로 과제명, 기대효과, 추진내용, 추진일정, 담당부서, 참여 검토 지원사업 등 정보 작성함
								<ul>
									<li>참여 검토 지원사업이 없는 경우, ‘-’로 표기</li>
								</ul>
							</li>
							<li>작성한 내용 외 제시 원하는 파일 있을 시 업로드</li>
						</ul>
					</div>
	
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">첨부파일</th>
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName05" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file05" class="btn_file" name="taskdesc_file" onclick="addFile(event, 5, 1)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-5">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId eq  'taskdesc_file'}">
														<p class="ml0 mt10">
															<input type="file" name="taskdesc_file" class="hidden" />
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
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type03">[참고] 작업환경 차원 관련 정부지원 사업 안내</h5>
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>작업환경 차원 관련 정부지원 사업 안내표 : 주관기관, 사업, 서비스유형, 관련 해결방안에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 15%" />
								<col style="width: 25%" />
								<col style="width: 30%" />
								<col style="width: 30%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">주관기관</th>
									<th scope="col">사업</th>
									<th scope="col">서비스 유형</th>
									<th scope="col">관련 해결방안</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td rowspan="3">한국 산업인력 공단</td>
									<td rowspan="2">중소기업 직업능력개발 과제수행 컨설팅</td>
									<td>공정개선 컨설팅, 품질개선 컨설팅</td>
									<td>업무프로세스 재설계, 작업장 환경개선 전반</td>
								</tr>
								<tr>
									<td>스마트팩토리 구축 컨설팅</td>
									<td>작업장 위험요소 제거, 반복작업 자동화 등</td>
								</tr>
								<tr>
									<td>공정채용 컨설팅</td>
									<td>공정채용 컨설팅</td>
									<td>적합인력 채용</td>
								</tr>
								<tr>
									<td>다수 기관</td>
									<td>채용 지원 다양한 사업</td>
									<td>인력매칭, 임금지원 등</td>
									<td>적합인력 채용</td>
								</tr>
								<tr>
									<td rowspan="3">노사발전재단</td>
									<td rowspan="3">일터혁신 컨설팅</td>
									<td>장시간근로개선 컨설팅, 고용문화 개선 컨설팅</td>
									<td>비전/미션 재정립</td>
								</tr>
								<tr>
									<td>임금체계, 평가체계 개선 컨설팅</td>
									<td>임금/평가체계 개선을 통한 동기부여 재고</td>
								</tr>
								<tr>
									<td>작업조직/작업환경개선 컨설팅, 안전일터 컨설팅</td>
									<td>업무프로세스 재설계, 작업장 위험요소 제거, 작업장 환경 개선 전반</td>
								</tr>
								<tr>
									<td>고용노동부</td>
									<td>재택근무종합 컨설팅</td>
									<td>인사제도, 재택근무 인프라 구축</td>
									<td>인사제도 개선, 정보화전략 수립</td>
								</tr>
								<tr>
									<td rowspan="2">한국산업안전보건공단</td>
									<td>건강일터 조정지원 사업</td>
									<td>공학적 개선 설비 설치비용 지원</td>
									<td>작업환경 개선</td>
								</tr>
								<tr>
									<td>중소기업 쿠폰제 컨설팅</td>
									<td>HR전략, 정보화 전략, 생산성 등 다양한 주제</td>
									<td>인사제도 개선, 정보화, 생산성 관련 전반 개선</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box">
					<h5 class="title-type03">[참고] 역량 차원 관련 정부지원 사업 안내</h5>
	
					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>[참고] 역량 차원 관련 정부지원 사업 안내표 : 주관기관, 사업, 서비스 유형에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width: 15%" />
								<col style="width: 25%" />
								<col style="width: 60%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">주관기관</th>
									<th scope="col">사업</th>
									<th scope="col">서비스 유형</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td rowspan="2">한국 산업인력 공단</td>
									<td>사업주 직업능력개발훈련</td>
									<td>자체훈련 훈련비, 위탁훈련 훈련비, 훈련수당, 숙식비, 임금 일부</td>
								</tr>
								<tr>
									<td>현장맞춤형 체계적 훈련(S-OJT)</td>
									<td>훈련과정 개발, 훈련비, 훈련교사, 직업능력개발훈련 컨설팅</td>
								</tr>
								<tr>
									<td>고용노동부</td>
									<td>국가인적자원개발컨소시엄</td>
									<td>훈련 및 훈련비 지원</td>
								</tr>
								<tr>
									<td>한국산업단지 공단</td>
									<td>산업단지별 특성와 안전보건교육</td>
									<td>안전보건교육 및 교육교재지원</td>
								</tr>
								<tr>
									<td rowspan="2">중소벤처기업 진흥공단</td>
									<td>중소기업 연수사업</td>
									<td>직무역량향상연수, 기업현장맞춤연수, 정부정책연계 교육, 온라인 연수</td>
								</tr>
								<tr>
									<td>현장코칭 숙련인력 양성사업</td>
									<td>훈련수당, 직무교육, 전문가 현장코칭 지원</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- page4 -->
			<div class="page hidden" id="page4">
				<div class="contents-box">
					<h3 class="title-type01">[별첨] 분석 세부 내용</h3>
	
					<div class="text-box mb20">
						<textarea name="attac_desc" cols="50" rows="5" class="w100" title="분석 세부 내용" placeholder="분석 세부 내용 입력"><c:out value='${data.attac_desc[0]}' /></textarea>
					</div>
	
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption>첨부파일</caption>
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">첨부파일</th>
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName06" class="fileName" readonly="readonly" placeholder="파일찾기" />
											<button type="button" id="upload-file06" class="btn_file" name="attac_file" onclick="addFile(event, 6, 3)">찾아보기</button>
										</div>
										<div class="change-application-wrapper">
											<div class="js-file-area-6">
												<c:forEach var="file" items="${report.files}">
													<c:if test="${file.itemId.contains('attac_file')}">
														<p class="ml0 mt10">
															<input type="file" name="attac_file" class="hidden" />
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
			</p>
		</div>

		
		<div class="fr mt50">
			<c:if test="${empty report || (report.confmStatus eq '5' && loginVO.usertypeIdx eq '10') || (report.confmStatus eq '40' && loginVO.usertypeIdx eq '10')}">
				<a href="javascript:void(0);" class="btn-m01 btn-color03" onclick="saveReport(5);">저장</a>
				<a href="javascript:void(0);" class="btn-m01 btn-color03" onclick="saveReport(10);">제출</a>
			</c:if>
			<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
		</div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
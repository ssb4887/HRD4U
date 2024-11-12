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
</style>
<link rel="stylesheet" href="${contextPath}${cssPath}/contents02.css">


<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">

	<!-- CMS 시작 -->
	<form id="report-form">
		<input type="hidden" id="cnslIdx" name="cnslIdx" value="${cnsl.cnslIdx}" /> 
		<input type="hidden" id="bplNo" name="bplNo" value="${cnsl.bplNo}" />
		<input type="hidden" id="reprtIdx" name="reprtIdx" value="${report.reprtIdx}" />
		<div class="page" id="page1">
			<div class="contents-area">
				<div class="contents-box">
					<h3 class="title-type01">1. 개요</h3>

					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>본 장은 심층 진단의 필요성, 주요 활동, 주요 결과를 한 눈에 제시하고자 하는 목적을 가지고 있음</li>
						</ul>
					</div>
				</div>

				<div class="contents-box">
					<h4 class="title-type02">1. 훈련체계 수립 필요성</h4>
					<div class="text-box mt20 mb20">
						<textarea id="" name="necessity" cols="50" rows="5" placeholder=""
							class="w100" title="훈련체계 수립 필요성 입력" required><c:out
								value="${data.necessity[0]}" /></textarea>
					</div>

					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>심층진단에서 제시된 훈련의 필요성과 연계한 내용 기술</li>
							<li>심층진단 외에 본 컨설팅에서 추가로 파악된 훈련체계 수립 필요성 있으면 함께 제시 (3줄 넘지 않도록
								간단히 기술)</li>
						</ul>
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
								<c:if test="${empty diaryList}">
									<tr>
										<td colspan="5">작성된 컨설팅 수행일지가 없습니다.</td>
									</tr>
								</c:if>
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

					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>컨설팅 수행일지 주요내용 반영하여 시스템에서 자동생성됨</li>
						</ul>
					</div>
				</div>


				<div class="contents-box">
					<h4 class="title-type02">3. 훈련체계 수립 주요 결과</h4>
					<div class="images-box mb20 line">
						<img src="../img/sub03/img130201.gif" alt="훈련체계 수립 주요 결과표" />

						<div class="blind">
							<table>
								<caption>훈련체계 수립 주요 결과표</caption>
								<tbody>
									<tr>
										<th scope="row">부장</th>
										<td rowspan="2">공정관리 고급</td>
										<td rowspan="3">생산관리 고급</td>
										<td rowspan="2" colspan="2">설비보전</td>
										<td rowspan="2">공정혁신</td>
										<td rowspan="2">품질관리 고급</td>
									</tr>

									<tr>
										<th scope="row">차장</th>
									</tr>
									<tr>
										<th scope="row">과장</th>
										<td>공정관리 중급</td>
										<td rowspan="2">치공구 관리</td>
										<td rowspan="2">설비관리</td>
										<td rowspan="2">공정설계</td>
										<td>품질관리 중급</td>
									</tr>

									<tr>
										<th scope="row">대리</th>
										<td>공정관리 초급</td>
										<td rowspan="3">생산관리 초급</td>
										<td rowspan="3">품질관리 초급</td>
									</tr>

									<tr>
										<th scope="row" rowspan="2">사원</th>
										<td>베어링 공정이해</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>

									<tr>
										<td>베어링 제품 특성 이해</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<th scope="row"></th>
										<td>공통</td>
										<td colspan="3">생산</td>
										<td>생산기술</td>
										<td>품질</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="text-box mt20 mb20">
						<textarea id="" name="result" cols="50" rows="5" placeholder=""
							class="w100" title="훈련체계 수립 주요 결과 입력" required><c:out
								value="${data.result[0]}" /></textarea>



						<div class="gray-box01">
							<h3>작성안내</h3>
							<ul class="ul-list01">
								<li>훈련체계 수립 컨설팅과 관련 있는 직급과 업무로 집중하여 작성</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="page hidden" id="page2">
			<div class="contents-area">
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

								<c:choose>
									<c:when test="${fn:length(data.trsystm_analysis_title) eq 0}">
										<tr>
											<td><input type="text" id=""
												name="trsystm_analysis_title" value="" title="" class="w100" required></td>
											<td><textarea id="" name="trsystm_analysis_value"
													cols="50" rows="5" placeholder="" class="w100 h100" required></textarea></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="list" items="${data.trsystm_analysis_title}"
											varStatus="i">
											<tr>
												<td><input type="text" id=""
													name="trsystm_analysis_title" value="${list}" title=""
													class="w100" required></td>
												<td><textarea id="" name="trsystm_analysis_value"
														cols="50" rows="5" placeholder="" class="w100 h100" required><c:out
															value="${data.trsystm_analysis_value[i.index]}" /></textarea></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="2"><a href="javascript:void(0);"
										class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01"
										onclick="deleteRow(this)">삭제</a></td>
								</tr>
							</tfoot>
						</table>
					</div>


					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>경영진과 훈련 대상이 되는 근로자 대표를 대상으로 파악한 요구사항을 제시함.
								<ul>
									<li class="line-bullet">경영진과 근로자 대표의 요구사항을 구분하여 제시</li>
								</ul>
							</li>
							<li>요구사항은 훈련의 내용, 방법, 수준 등을 의미함.</li>
							<li>훈련 요구분석에서 우선적으로 훈련의 시행이 필요한 직무는 필수적으로 파악해야 함. 본 내용은 훈련
								대상 직무 선정의 논리적 근거가 됨.</li>
							<li>훈련의 여건 또는 애로사항 등 훈련 요구사항 외 내용은 별첨의 내부환경 부분에 제시함.</li>
						</ul>
					</div>
				</div>



				<div class="contents-box">
					<h4 class="title-type02">2. 직무분류</h4>
					<h5 class="title-type03">직무분류표 (내용은 예시)</h5>
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
								<c:choose>
									<c:when test="${fn:length(data.jobPosition) eq 0}">
										<tr>
											<td><input type="text" id="" name="jobPosition" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="jobRole" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="department" value=""
												title="" class="w100" required></td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="list" items="${data.jobPosition}"
											varStatus="i">
											<tr>
												<td><input type="text" id="" name="jobPosition"
													value="${list}" title="" class="w100" required></td>
												<td><input type="text" id="" name="jobRole"
													value="${data.jobRole[i.index]}" title="" class="w100" required></td>
												<td><input type="text" id="" name="department"
													value="${data.department[i.index]}" title="" class="w100" required></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>

							</tbody>
							<tfoot>
								<tr>
									<td colspan="4"><a href="javascript:void(0);"
										class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01"
										onclick="deleteRow(this)">삭제</a></td>
								</tr>
							</tfoot>
						</table>
					</div>

					<div class="text-box mt20 mb20">
						<textarea id="" name="jobClassify" cols="50" rows="5"
							placeholder="" class="w100" title="직무분류표 (내용은 예시) 입력" required><c:out
								value="${data.jobClassify[0]}" /></textarea>
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
									<td class="left">
										<div class="fileBox">
											<input type="text" id="fileName01" class="fileName"
												value="<c:forEach var="file" items="${report.files}"><c:if test="${file.itemId eq 'jobClassify_file'}">${file.fileOriginName}</c:if></c:forEach>" readonly="readonly"
												placeholder="파일찾기"> <label for="input-file01"
												class="btn_file">찾아보기</label> <input type="file"
												id="input-file01" name="jobClassify_file" class="input-file"
												onchange="javascript:document.getElementById('fileName01').value = this.value">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>


					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>본 훈련체계는 직무 훈련체계가 핵심으로 전사 훈련체계 제시를 위해서는 전체적인 직무의 조망이 필요함.
								<ul>
									<li class="line-bullet">훈련체계 수립을 위한 직무의 단위는 요구되는 지식과 기술의
										유사성을 기준으로 함.</li>
									<li class="line-bullet">직무와 부서의 관계는 1:1, 1:多, 多:1 모두 가능함.</li>
									<li class="line-bullet">직무명은 수행형 동사로 완료되어야 함. 직무명 부적합
										사례:‘기술연구소’</li>
								</ul>
							</li>
							<li>직무분류 관련 세부내용을 기술</li>
							<li>직무 도출 과정 등 직무분류 관련 제시할 추가 자료(ex. 조직체계 또는 공정과 관련하여 직무의
								구성을 파악할 수 있는 자료 등)은 파일 형태로 시스템에 업로드 함</li>
						</ul>
					</div>
					<h5 class="title-type05">(추가 업로드 자료 예시 1) 업무 프로세스 분석</h5>
					<div class="images-box line mb20">
						<img src="../img/sub03/img130202.gif"
							alt="XXX 사업부, 조직편제 : A부서(1파트, 2파트, 3파트 - 직무분해), B부서(1파트, 2파트 - 직무분해), . . . Z부서(1파트, 2파트 - 직무분해) 프로세스 A, 프로세스 B" />
					</div>

					<h5 class="title-type05">(추가 업로드 자료 예시 2) 공정 분석</h5>
					<div class="images-box line mb20">
						<img src="../img/sub03/img130203.gif"
							alt="Incoming(원자재), Machining(가공), Kitting(자재 공급), Brazing(용접), Inspection(검사), Warehouse(창고)" />
					</div>
				</div>

				<div class="contents-box">
					<h4 class="title-type02">3. 훈련대상 직무선정 및 분석</h4>

					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>훈련대상 직무선정 및 분석표</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">훈련대상 직무선정 방법 및 결과</th>
									<td><textarea id="" name="jobSelectionResult" cols="50"
											rows="5" placeholder="" class="w100"
											title="훈련대상 직무선정 방법 및 결과" required><c:out
												value="${data.jobSelectionResult[0]}" /></textarea></td>
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
								<c:choose>
									<c:when test="${fn:length(data.jobTitle) eq 0}">
										<tr>
											<td><input type="text" id="" name="jobTitle" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tasks" value=""
												title="" class="w100" required></td>
											<td><textarea id="" name="performanceCriteria" cols="3"
													rows="3" placeholder="" required></textarea></td>
											<td><select id="" name="priority" class="w100" required>
													<option value="">선택</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
											</select></td>
											<td><select id="" name="difficulty" class="w100" required>
													<option value="">선택</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
											</select></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="list" items="${data.jobTitle}" varStatus="i">
											<tr>
												<td><input type="text" id="" name="jobTitle"
													value="${list}" title="" class="w100" required></td>
												<td><input type="text" id="" name="tasks"
													value="${data.tasks[i.index]}" title="" class="w100" required></td>
												<td><textarea id="" name="performanceCriteria" cols="3"
														rows="3" placeholder="" required>${data.performanceCriteria[i.index]}</textarea></td>
												<td><select id="" name="priority" class="w100" required>
														<option value="">선택</option>
														<option value="1"
															<c:if test ="${data.priority[i.index] eq 1}">selected</c:if>>1</option>
														<option value="2"
															<c:if test ="${data.priority[i.index] eq 2}">selected</c:if>>2</option>
														<option value="3"
															<c:if test ="${data.priority[i.index] eq 3}">selected</c:if>>3</option>
														<option value="4"
															<c:if test ="${data.priority[i.index] eq 4}">selected</c:if>>4</option>
														<option value="5"
															<c:if test ="${data.priority[i.index] eq 5}">selected</c:if>>5</option>
												</select></td>
												<td><select id="" name="difficulty" class="w100" required>
														<option value="">선택</option>
														<option value="1"
															<c:if test ="${data.difficulty[i.index] eq 1}">selected</c:if>>1</option>
														<option value="2"
															<c:if test ="${data.difficulty[i.index] eq 2}">selected</c:if>>2</option>
														<option value="3"
															<c:if test ="${data.difficulty[i.index] eq 3}">selected</c:if>>3</option>
														<option value="4"
															<c:if test ="${data.difficulty[i.index] eq 4}">selected</c:if>>4</option>
														<option value="5"
															<c:if test ="${data.difficulty[i.index] eq 5}">selected</c:if>>5</option>
												</select></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5"><a href="javascript:void(0);"
										class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01"
										onclick="deleteRow(this)">삭제</a></td>
								</tr>
							</tfoot>
						</table>
					</div>

					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>NCS 활용방법</caption>
							<colgroup>
								<col style="width: 30%" />
								<col style="width: 70%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">NCS 활용방법<br /> <span
										class="point-color01">(필요 시)</span>
									</th>
									<td><textarea id="" name="ncsMO" cols="50" rows="15"
											placeholder="" class="w100" title="NCS 활용방법 내용"><c:out
												value="${data.ncsMO[0]}" /></textarea></td>
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
									<td class="left">
									<div class="fileBox">
										<input type="text" id="fileName02"
										class="fileName" value="<c:forEach var="file" items="${report.files}"><c:if test="${file.itemId eq 'ncs_file'}">${file.fileOriginName}</c:if></c:forEach>"
										readonly="readonly" placeholder="파일찾기"> <label
										for="input-file02" class="btn_file">찾아보기</label> <input
										type="file" id="input-file02" name="ncs_file"
										class="input-file" onchange="javascript:document.getElementById('fileName02').value = this.value">
										</div></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>분류된 직무 중 훈련요구 결과를 참고하고 별도의 협의를 통해 우선적으로 훈련이 필요한 직무를 3개
								이상 선정함. 훈련대상 필요 직무를 선정한 방법을 기술하고 선정된 직무 클릭하면 직무명에 자동 입력됨</li>
							<li>직무분석 시 관련 국가직무능력표준(NCS)가 있으면, 참고하여 활용 가능함. 다만 NCS를 활용하는
								경우, NCS에서 제시하고 있는 내용이 컨설팅 대상 기업의 직무 특성에 부합하는지 검토한 방법과 해당 기업의 특성에
								맞게 수정하여 적용한 내용에 대한 기술</li>
							<li>선정된 직무의 단위업무와 수행준거를 도출하고 업무 단위로 중요도와 난이도 평가한 결과 제시

								<ul>
									<li class="line-bullet">중요도/난이도는 회의 참석자들의 의견을 수렴하여 평가하며,
										필요하고 가능 시, 직원 대상 별도 설문을 통해 평가하는 등 다양한 방식의 적용 가능함.</li>
									<li class="line-bullet">중요도/난이도의 척도는 5점 리커르트 척도 사용 [1점(전혀
										그렇지 않다)~5점(매우 그렇다)]</li>
								</ul>
							</li>
							<li>본 직무분석은 훈련과정 설계의 주요한 토대가 되는 작업임.</li>
							<li>위 직무분석 내용 외 직무분석의 과정 또는 추가로 분석한 내용 등 추가 필요 자료 있으면 업로드 가능</li>
						</ul>
					</div>
				</div>
			</div>
		</div>



		<div class="page hidden" id="page3">
			<div class="contents-area">
				<div class="contents-box">
					<h3 class="title-type01">3. 훈련체계 수립</h3>
					<h4 class="title-type02">1. 훈련체계도 도출</h4>

					<h5 class="title-type05">훈련체계 수립 방법</h5>
					<div class="text-box mb20">
						<textarea id="" name="tsMethod" cols="50" rows="5" placeholder=""
							class="w100" title="훈련체계 수립 방법 입력" required><c:out
								value="${data.tsMethod[0]}" /></textarea>
					</div>
					<h5 class="title-type05">훈련체계표</h5>

					<div class="table-type02 horizontal-scroll mb20">
						<table>
							<caption>훈련체계표 : 구분(직위/부서), 공통, 생산, 생산, 생산, 생산기술, 품질에
								관한 정보 제공표</caption>
							<thead>
								<tr>
									<th scope="col">구분(직위/부서)</th>
									<th scope="col"><input type="text" id="" name="tsHeader01"
										value="${data.tsHeader01[0]}" title="" class="w100" required></th>
									<th scope="col"><input type="text" id="" name="tsHeader02"
										value="${data.tsHeader02[0]}" title="" class="w100" required></th>
									<th scope="col"><input type="text" id="" name="tsHeader03"
										value="${data.tsHeader03[0]}" title="" class="w100" required></th>
									<th scope="col"><input type="text" id="" name="tsHeader04"
										value="${data.tsHeader04[0]}" title="" class="w100" required></th>
									<th scope="col"><input type="text" id="" name="tsHeader05"
										value="${data.tsHeader05[0]}" title="" class="w100" required></th>
									<th scope="col"><input type="text" id="" name="tsHeader06"
										value="${data.tsHeader06[0]}" title="" class="w100" required></th>
								</tr>
							</thead>
							<tbody>
													<c:choose>
									<c:when test="${fn:length(data.tsValue01) eq 0}">
										<tr>
											<td><input type="text" id="" name="tsValue01" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue02" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue03" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue04" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue05" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue06" value=""
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue07" value=""
												title="" class="w100" required></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="list" items="${data.tsValue01}" varStatus="i">
											<tr>
											<td><input type="text" id="" name="tsValue01" value="${list}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue02" value="${data.tsValue02[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue03" value="${data.tsValue02[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue04" value="${data.tsValue02[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue05" value="${data.tsValue02[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue06" value="${data.tsValue02[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tsValue07" value="${data.tsValue02[i.index]}"
												title="" class="w100" required></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
		
							</tbody>
							<tfoot>
								<tr>
									<td colspan="7">
									<a href="javascript:void(0);" class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01" onclick="deleteRow(this)">삭제</a></td>
								</tr>
							</tfoot>
						</table>
					</div>

					<h5 class="title-type05">도출 훈련과정 리스트</h5>

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
							<c:choose>
								<c:when test="${fn:length(data.tcList_Class) eq 0}">
									<tr>
										<td><input type="text" id="" name="tcList_Class" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tcList_CourseName" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tcList_Level" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tcList_Form" value=""
											title="" class="w100" required></td>
									</tr>
								</c:when>
							<c:otherwise>
								<c:forEach var="list" items="${data.tcList_Class}" varStatus="i">
									<tr>
										<td><input type="text" id="" name="tcList_Class" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tcList_CourseName" value="${data.tcList_CourseName[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tcList_Level" value="${data.tcList_Level[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tcList_Form" value="${data.tcList_Form[i.index]}"
											title="" class="w100" required></td>
									</tr>
								</c:forEach>
							</c:otherwise>
							</c:choose>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4"><a href="javascript:void(0);"
										class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01"
										onclick="deleteRow(this)">삭제</a></td>
								</tr>
							</tfoot>
						</table>
					</div>

					<h5 class="title-type05">활용 방안</h5>
					<div class="text-box mb20">
						<textarea id="" name="utilization" cols="50" rows="5"
							placeholder="" class="w100" title="활용 방안 입력" required><c:out value="${data.utilization[0]}"/></textarea>
					</div>



					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01 mb30">
							<li>훈련체계도 도출 시 고려했던 사항, 방법 등의 훈련체계도 도출 관련 내용을 훈련체계 수립 방법란에
								기술함</li>
							<li>훈련체계도를 바탕으로 훈련체계표를 작성하여야 함</li>
							<li>훈련대상 직무로 선정한 직무 관련 훈련과정은 필수로 제시해야 하며 이외 직무 훈련과정과 공통, 리더십
								훈련과정은 필요한 경우 제시해도 되며 필수는 아님</li>
							<li>활용방안에서는 도출한 훈련체계도 및 훈련과정 리스트의 활용 방안 및 우선순위 도출을 기술</li>
						</ul>

						<h4 class="title-type03 mb05">도출 훈련과정 리스트 예시</h4>
						<div class="table-type02 horizontal-scroll"
							style="background-color: #fff">
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
									<tr>
										<td>경영관리/인사</td>
										<td>인사관리 실무</td>
										<td>대리 이하</td>
										<td>외부위탁</td>
									</tr>

									<tr>
										<td>경영관리/인사</td>
										<td>인사관리 실무</td>
										<td>대리 이하</td>
										<td>외부위탁</td>
									</tr>

									<tr>
										<td>경영관리/인사</td>
										<td>인사관리 실무</td>
										<td>대리 이하</td>
										<td>외부위탁</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>




		<div class="page hidden" id="page4">
			<div class="contents-box">
				<h4 class="title-type02">2. 훈련계획 수립</h4>
				<h5 class="title-type03">훈련과정 명세서1</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<tbody>
							<tr>
								<th scope="col">과정명</th>
								<td colspan="3"><input type="text" id="" name="tp_name01"
									value="${data.tp_name01[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 형태</th>
								<td colspan="3"><input type="text" id="" name="tp_format01"
									value="${data.tp_format01[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">추천 훈련사업</th>
								<td colspan="3"><input type="text" id="" name="tp_recommend01"
									value="${data.tp_recommend01[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 목표</th>
								<td colspan="3"><input type="text" id="" name="tp_goal01"
									value="${data.tp_goal01[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">주요 훈련 내용</th>
								<td colspan="3"><input type="text" id="" name="tp_contents01"
									value="${data.tp_contents01[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 대상</th>
								<td colspan="3"><input type="text" id="" name="tp_target01"
									value="${data.tp_target01[0]}" title="" class="w100" required></td>
							</tr>
							</tbody>
							</table>
							
							<table>
							<thead>
							<tr>
								<th scope="row" rowspan="5">구분</th>
								<th scope="row" class="bg01">교과목명</th>
								<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
								<th scope="row" class="bg01">추천 훈련시간</th>
							</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${fn:length(data.tp_courseName01) eq 0}">
									<tr>
										<th>훈련내용</th>
										<td><input type="text" id="" name="tp_courseName01" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details01" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime01" value=""
											title="" class="w100" required></td>
		
									</tr>
								</c:when>
								<c:otherwise>
								<c:forEach var="list" items="${data.tp_courseName01}" varStatus="i">
									<tr>
										<th>훈련내용</th>
										<td><input type="text" id="" name="tp_courseName01" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details01" value="${data.tp_details01[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime01" value="${data.tp_recommendTime01[i.index]}"
											title="" class="w100" required></td>
		
									</tr>
								</c:forEach>
								</c:otherwise>
							
							</c:choose>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="4"><a href="javascript:void(0);"
									class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a> <a
									href="javascript:void(0);" class="btn-m02 btn-color01"
									onclick="deleteRow(this)">삭제</a></td>
							</tr>
						</tfoot>
					</table>
				</div>


				<div class="contents-box">
					<div class="gray-box01">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>훈련체계도에 제시된 훈련과정 중 훈련대상 직무로 선정된 직무의 훈련과정별 정보 시스템에 입력하면 훈련
								운영계획서 산출</li>
							<li>훈련 대상에는 훈련 대상 직무와 해당 직무 수행자 수준 제시(ex. 영업지원 직무 대리 이상)</li>
							<li>훈련형태에는 외부위탁, 일반직무전수 OJT, 과제수행 OJT 등 필요 훈련형태 제시</li>
							<li>해당 훈련 과정 시행 위한 추천훈련 사업 제시</li>
							<li>이외 훈련목표, 훈련내용, 훈련 시간 제시</li>
							<li>필요한 훈련과정별 명세서 5개 제시 필요</li>
						</ul>
					</div>
				</div>


				<h5 class="title-type03">훈련과정 명세서2</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<tbody>
							<tr>
								<th scope="col">과정명</th>
								<td colspan="3"><input type="text" id="" name="tp_name02"
									value="${data.tp_name02[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 형태</th>
								<td colspan="3"><input type="text" id="" name="tp_format02"
									value="${data.tp_format02[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">추천 훈련사업</th>
								<td colspan="3"><input type="text" id="" name="tp_recommend02"
									value="${data.tp_recommend02[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 목표</th>
								<td colspan="3"><input type="text" id="" name="tp_goal02"
									value="${data.tp_goal02[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">주요 훈련 내용</th>
								<td colspan="3"><input type="text" id="" name="tp_contents02"
									value="${data.tp_contents02[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 대상</th>
								<td colspan="3"><input type="text" id="" name="tp_target02"
									value="${data.tp_target02[0]}" title="" class="w100" required></td>
							</tr>
							</tbody>
							</table>
							
							<table>
							<thead>
							<tr>
								<th scope="row" rowspan="5">구분</th>
								<th scope="row" class="bg01">교과목명</th>
								<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
								<th scope="row" class="bg01">추천 훈련시간</th>
							</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${fn:length(data.tp_courseName02) eq 0}">
									<tr>
										<th>훈련내용</th>
										<td><input type="text" id="" name="tp_courseName02" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details02" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime02" value=""
											title="" class="w100" required></td>
		
									</tr>
								</c:when>
								<c:otherwise>
								<c:forEach var="list" items="${data.tp_courseName02}" varStatus="i">
									<tr>
										<th>훈련내용</th>
										<td><input type="text" id="" name="tp_courseName02" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details02" value="${data.tp_details02[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime02" value="${data.tp_recommendTime02[i.index]}"
											title="" class="w100" required></td>
		
									</tr>
								</c:forEach>
								</c:otherwise>
							
							</c:choose>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="4"><a href="javascript:void(0);"
									class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a> <a
									href="javascript:void(0);" class="btn-m02 btn-color01"
									onclick="deleteRow(this)">삭제</a></td>
							</tr>
						</tfoot>
					</table>
				</div>


				<h5 class="title-type03">훈련과정 명세서3</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<tbody>
							<tr>
								<th scope="col">과정명</th>
								<td colspan="3"><input type="text" id="" name="tp_name03"
									value="${data.tp_name03[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 형태</th>
								<td colspan="3"><input type="text" id="" name="tp_format03"
									value="${data.tp_format03[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">추천 훈련사업</th>
								<td colspan="3"><input type="text" id="" name="tp_recommend03"
									value="${data.tp_recommend03[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 목표</th>
								<td colspan="3"><input type="text" id="" name="tp_goal03"
									value="${data.tp_goal03[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">주요 훈련 내용</th>
								<td colspan="3"><input type="text" id="" name="tp_contents03"
									value="${data.tp_contents03[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 대상</th>
								<td colspan="3"><input type="text" id="" name="tp_target03"
									value="${data.tp_target03[0]}" title="" class="w100" required></td>
							</tr>
														</tbody>
							</table>
							
							<table>
							<thead>
							<tr>
								<th scope="row" rowspan="5">구분</th>
								<th scope="row" class="bg01">교과목명</th>
								<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
								<th scope="row" class="bg01">추천 훈련시간</th>
							</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${fn:length(data.tp_courseName03) eq 0}">
									<tr>
										<th>훈련내용</th>
										<td><input type="text" id="" name="tp_courseName03" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details03" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime03" value=""
											title="" class="w100" required></td>
		
									</tr>
								</c:when>
								<c:otherwise>
								<c:forEach var="list" items="${data.tp_courseName03}" varStatus="i">
									<tr>
										<th>훈련내용</th>
										<td><input type="text" id="" name="tp_courseName03" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details03" value="${data.tp_details03[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime03" value="${data.tp_recommendTime03[i.index]}"
											title="" class="w100" required></td>
		
									</tr>
								</c:forEach>
								</c:otherwise>
							
							</c:choose>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="4"><a href="javascript:void(0);"
									class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a> <a
									href="javascript:void(0);" class="btn-m02 btn-color01"
									onclick="deleteRow(this)">삭제</a></td>
							</tr>
						</tfoot>
					</table>
				</div>


				<h5 class="title-type03">훈련과정 명세서4</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<tbody>
							<tr>
								<th scope="col">과정명</th>
								<td colspan="3"><input type="text" id="" name="tp_name04"
									value="${data.tp_name04[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 형태</th>
								<td colspan="3"><input type="text" id="" name="tp_format04"
									value="${data.tp_format04[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">추천 훈련사업</th>
								<td colspan="3"><input type="text" id="" name="tp_recommend04"
									value="${data.tp_recommend04[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 목표</th>
								<td colspan="3"><input type="text" id="" name="tp_goal04"
									value="${data.tp_goal04[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">주요 훈련 내용</th>
								<td colspan="3"><input type="text" id="" name="tp_contents04"
									value="${data.tp_contents04[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 대상</th>
								<td colspan="3"><input type="text" id="" name="tp_target04"
									value="${data.tp_target04[0]}" title="" class="w100" required></td>
							</tr>
																					</tbody>
							</table>
							
							<table>
							<thead>
							<tr>
								<th scope="row" rowspan="5">구분</th>
								<th scope="row" class="bg01">교과목명</th>
								<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
								<th scope="row" class="bg01">추천 훈련시간</th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${fn:length(data.tp_courseName04) eq 0}">
									<tr>
										<th>훈련 내용</th>
										<td><input type="text" id="" name="tp_courseName04" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details04" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime04" value=""
											title="" class="w100" required></td>
		
									</tr>
								</c:when>
								<c:otherwise>
								<c:forEach var="list" items="${data.tp_courseName04}" varStatus="i">
									<tr>
										<th>훈련 내용</th>
										<td><input type="text" id="" name="tp_courseName04" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details04" value="${data.tp_details01[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime04" value="${data.tp_recommendTime01[i.index]}"
											title="" class="w100" required></td>
		
									</tr>
								</c:forEach>
								</c:otherwise>
							
							</c:choose>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="4"><a href="javascript:void(0);"
									class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a> <a
									href="javascript:void(0);" class="btn-m02 btn-color01"
									onclick="deleteRow(this)">삭제</a></td>
							</tr>
						</tfoot>
					</table>
				</div>


				<h5 class="title-type03">훈련과정 명세서5</h5>

				<div class="table-type02 horizontal-scroll mb20">
					<table>
						<tbody>
							<tr>
								<th scope="col">과정명</th>
								<td colspan="3"><input type="text" id="" name="tp_name05"
									value="${data.tp_name05[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 형태</th>
								<td colspan="3"><input type="text" id="" name="tp_format05"
									value="${data.tp_format05[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">추천 훈련사업</th>
								<td colspan="3"><input type="text" id="" name="tp_recommend05"
									value="${data.tp_recommend05[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 목표</th>
								<td colspan="3"><input type="text" id="" name="tp_goal05"
									value="${data.tp_goal05[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">주요 훈련 내용</th>
								<td colspan="3"><input type="text" id="" name="tp_contents05"
									value="${data.tp_contents05[0]}" title="" class="w100" required></td>
							</tr>
							<tr>
								<th scope="col">훈련 대상</th>
								<td colspan="3"><input type="text" id="" name="tp_target05"
									value="${data.tp_target05[0]}" title="" class="w100" required></td>
							</tr>
							</tbody>
							</table>
							
							<table>
							<thead>
							<tr>
								<th scope="row" rowspan="5">구분</th>
								<th scope="row" class="bg01">교과목명</th>
								<th scope="row" class="bg01">세부 내용(단원, 과제명)</th>
								<th scope="row" class="bg01">추천 훈련시간</th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${fn:length(data.tp_courseName05) eq 0}">
									<tr>
										<th>훈련 내용</th>
										<td><input type="text" id="" name="tp_courseName05" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details05" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime05" value=""
											title="" class="w100" required></td>
		
									</tr>
								</c:when>
								<c:otherwise>
								<c:forEach var="list" items="${data.tp_courseName05}" varStatus="i">
									<tr>
										<th>훈련 내용</th>
										<td><input type="text" id="" name="tp_courseName05" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_details05" value="${data.tp_details05[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_recommendTime05" value="${data.tp_recommendTime05[i.index]}"
											title="" class="w100" required></td>
		
									</tr>
								</c:forEach>
								</c:otherwise>
							
							</c:choose>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="4"><a href="javascript:void(0);"
									class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a> <a
									href="javascript:void(0);" class="btn-m02 btn-color01"
									onclick="deleteRow(this)">삭제</a></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>


		<div class="page hidden" id="page5">
			<div class="contents-area">
				<div class="contents-box">
					<h3 class="title-type01">[별첨] 분석 세부 내용</h3>

					<div class="gray-box01 mt20 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>별첨 파일은 형식을 특정하지 않고 본문에 제시한 내용 외 분석한 내용을 자유롭게 구성하여 시스템에
								업로드 함</li>
							<li>대상 기업의 내부현황 분석, 직무 역량모델링 결과 등 본분에서 제시하지 못한 세부 내용 제시하기를
								권고함. 외부환경 분석은 외부환경의 변화가 훈련체계 수립에 영향을 줄 경우에만 필요에 따라 제시</li>
						</ul>
					</div>
				</div>

				<div class="contents-box">
					<h4 class="title-type02">1. 내부현황 분석</h4>
					<h5 class="title-type03">&lt;예시&gt; 자체훈련 운영(정부 지원 사업 외 훈련·교육
						이력 포함) 등 훈련 운영 이력</h5>

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
									<th scope="col">&nbsp;</th>
									<th scope="col">연번</th>
									<th scope="col">참여&nbsp;사업</th>
									<th scope="col">훈련과정명</th>
									<th scope="col">훈련방법</th>
									<th scope="col">훈련시간</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${fn:length(data.tp_his_seq) eq 0}">
									<tr>
										<th scope="col" colspan="1">훈련실시이력</th>
										<td><input type="text" id="" name="tp_his_seq" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_his_name" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_his_courseName" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_his_method" value=""
											title="" class="w100" required></td>
										<td><input type="text" id="" name="tp_his_time" value=""
											title="" class="w100" required></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="list" items="${data.tp_his_seq}" varStatus="i">
										<tr>
											<th scope="col" colspan="1">훈련실시이력</th>
											<td><input type="text" id="" name="tp_his_seq" value="${list}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tp_his_name" value="${data.tp_his_name[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tp_his_courseName" value="${data.tp_his_courseName[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tp_his_method" value="${data.tp_his_method[i.index]}"
												title="" class="w100" required></td>
											<td><input type="text" id="" name="tp_his_time" value="${data.tp_his_time[i.index]}" title="" class="w100" required></td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6"><a href="javascript:void(0);"
										class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a>
										<a href="javascript:void(0);" class="btn-m02 btn-color01"
										onclick="deleteRow(this)">삭제</a></td>
								</tr>
							</tfoot>
						</table>
					</div>

					<div class="gray-box01 mt20 mb20">
						<h3>작성안내</h3>
						<ul class="ul-list01">
							<li>내부현황은 크게 조직의 기본현황과 훈련현황으로 구분할 수 있음.</li>
							<li>조직 기본현황은 주요 사업분야, 주요 공정(업무) 및 장비, 조직도(부서 업무 내용, 인적자원 현황
								등) 등을 의미함.</li>
							<li>훈련현황은 자체훈련 운영이력, 훈련성과, 훈련 운영 조직(전담자) 운영 여부, 기업 내 훈련 교사
								참여 가능 인력 현황, 기업 내 훈련 운영 가능 시설 장비 보유 현황 등을 의미함.</li>
						</ul>
					</div>
				</div>


				<h4 class="title-type02">2. 외부환경 분석</h4>

				<div class="text-box mb20">
					<textarea id="" name="uilization02" cols="50" rows="5" placeholder=""
						class="w100" title="활용 방안 입력" required><c:out value="${data.uilization02[0]}"/></textarea>
				</div>

				<div class="gray-box01 mt20 mb20">
					<h3>작성안내</h3>
					<ul class="ul-list01">
						<li>외부환경은 외부환경의 변화가 훈련체계 수립에 영향을 주는 경우에만 선택적으로 제시하면 됨.</li>
						<li>외부환경은 산업환경, 기술환경, 정책환경, 사회환경, 동종업계 동향 등의 분석이 될 수 있음.</li>
					</ul>
				</div>
			</div>

			<div class="contents-box">
				<h4 class="title-type02">3. 직무역량 모델링</h4>

				<h5 class="title-type03">&lt;예시&gt; 직무역량 모델링 예시</h5>

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
						<c:choose>
							<c:when test="${fn:length(data.modeling_jobTitle) eq 0}">
								<tr>
									<td><input type="text" id="" name="modeling_jobTitle" value=""
										title="" class="w100" required></td>
									<td><input type="text" id="" name="modeling_performanceCriteria" value=""
										title="" class="w100" required></td>
									<td><input type="text" id="" name="modeling_Knowledge" value=""
										title="" class="w100" required></td>
									<td><input type="text" id="" name="modeling_skill" value=""
										title="" class="w100" required></td>
									<td><input type="text" id="" name="modeling_attitude" value=""
										title="" class="w100" required></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="list" items="${data.modeling_jobTitle}" varStatus="i">
									<tr>
										<td><input type="text" id="" name="modeling_jobTitle" value="${list}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="modeling_performanceCriteria" value="${data.modeling_performanceCriteria[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="modeling_Knowledge" value="${data.modeling_Knowledge[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="modeling_skill" value="${data.modeling_skill[i.index]}"
											title="" class="w100" required></td>
										<td><input type="text" id="" name="modeling_attitude" value="${data.modeling_attitude[i.index]}"
											title="" class="w100" required></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						
						</c:choose>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="5"><a href="javascript:void(0);"
									class="btn-m02 btn-color03" onclick="addRow(this, 10)">추가</a> <a
									href="javascript:void(0);" class="btn-m02 btn-color01"
									onclick="deleteRow(this)">삭제</a></td>
							</tr>
						</tfoot>
					</table>
				</div>

				<div class="gray-box01 mt20 mb20">
					<h3>작성안내</h3>
					<ul class="ul-list01">
						<li>직무역량 모델링은 직무별 필요 지식, 스킬, 태도 등 해당 직무수행을 위해 필요한 구체적인 내용 도출</li>
					</ul>
				</div>
			</div>
		</div>

	</form>

	<div class="paging-navigation-wrapper">
		<p class="paging-navigation">
			<strong>1</strong> <a href="javascript:void(0);" id="js-page2-btn"
				onclick="showPage(this, 2);">2</a> <a href="javascript:void(0);"
				id="js-page3-btn" onclick="showPage(this, 3);">3</a> <a
				href="javascript:void(0);" id="js-page4-btn"
				onclick="showPage(this, 4);">4</a> <a href="javascript:void(0);"
				id="js-page5-btn" onclick="showPage(this, 5);">5</a>
		</p>
	</div>

	<div class="fr mt50">
		<c:if test="${empty report || (report.confmStatus eq '5' && loginVO.usertypeIdx eq '10') || (report.confmStatus eq '40' && loginVO.usertypeIdx eq '10')}">
			<a href="javascript:void(0);" class="btn-m01 btn-color03" onclick="saveReport(5);">저장</a>
			<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="saveReport(10);">제출</a>
		</c:if>
		<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
	</div>

</div>
<!-- //CMS 끝 -->
<script type="text/javascript"
	src="${contextPath}${jsPath}/report/indepthDgns.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
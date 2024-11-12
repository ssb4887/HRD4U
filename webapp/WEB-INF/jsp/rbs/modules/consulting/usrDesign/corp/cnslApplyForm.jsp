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

<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="year">
	<fmt:formatDate value="${today}" pattern="yyyy" />
</c:set>
<c:set var="month">
	<fmt:formatDate value="${today}" pattern="MM" />
</c:set>
<c:set var="day">
	<fmt:formatDate value="${today}" pattern="dd" />
</c:set>



<c:choose>
	<c:when test="${type eq 'A'}">
		<%@ include file="cnslTypeA/cnslApplyFormTypeA.jsp"%>
	</c:when>
	<c:when test="${type eq 'B'}">
		<%@ include file="cnslTypeB/cnslApplyFormTypeB.jsp"%>
	</c:when>
</c:choose>


<div class="btns-right">
	<button type="button" class="btn-m01 btn-color03 depth3"
		onclick="saveCnsl(5)">저장</button>
	<button type="button" class="btn-m01 btn-color03 depth3"
		onclick="saveCnsl(10)">제출</button>
</div>
<!-- //CMS 끝 -->

<div class="mask"></div>
<div class="modal-wrapper" id="centerModal" style="width: 700px;">
	<h2>민간센터 선택</h2>
	<div class="modal-area">
		<div id="overlay"></div>
		<div class="loader"></div>

		<div class="contents-box pl0">

			<div class="table-type01 horizontal-scroll table-container">
				<table class="width-type03 modal-table">
					<caption>업체정보표 : 권역, 사업지역, 지원센터 에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 30%" />
						<col style="width: 30%" />
						<col style="width: 50%" />
					</colgroup>
					<thead class="modal-thead">
						<tr>
							<th scope="col">권역</th>
							<th scope="col">사업지역</th>
							<th scope="col">지원센터</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>서울-경인권(통합 권역)</th>
							<td>서울, 강원, 인천, 경기</td>
							<td>
								<ul>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="34" onclick="centerInfoInputHanlder(this.value)">경기경영자총협회</button>
									</li>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="35" onclick="centerInfoInputHanlder(this.value)">대한상의
											경기인력개발원</button>
									</li>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="36" onclick="centerInfoInputHanlder(this.value)">한국공학대학교</button>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>대전권</th>
							<td>대전, 세종, 충남, 충복</td>
							<td>
								<ul>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="37" onclick="centerInfoInputHanlder(this.value)">대한상의
											충남인력개발원</button>
									</li>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="38" onclick="centerInfoInputHanlder(this.value)">한국문화산업협회</button>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>광주권</th>
							<td>광주, 전북, 전남</td>
							<td>
								<ul>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="39" onclick="centerInfoInputHanlder(this.value)">전북산학융합원</button>

									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>부산권</th>
							<td>부산, 울산, 경남</td>
							<td>
								<ul>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="41" onclick="centerInfoInputHanlder(this.value)">경남경영자총협회</button>
									</li>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="40" onclick="centerInfoInputHanlder(this.value)">대한상의
											부산인력개발원</button>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>서대구권</th>
							<td>대구, 경북</td>
							<td>
								<ul>
									<li style="margin: 5px;">
										<button type="button" class="btn-m01 btn-color03 depth2"
											value="42">경북경영자총협회</button>
									</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>


	<button type="button" class="btn-modal-close"
		onclick="closeModal('centerModal')">모달 창 닫기</button>
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="consentModal">
	<h2>직업능력개발훈련 정보 활용 표준 동의서</h2>
	<div class="modal-area">
		<form action="">
			<div class="contents-box pl0">
				<div class="agreement-wrapper modal">
					<h4>직업능력개발훈련 정보 활용 표준 동의서</h4>
					<p class="word-type03 mb20 left">
						<strong>지원센터는 직업능력개발훈련 사업 참여</strong>를 위하여 <strong>귀 기관</strong>의
						소중한 <strong class="underline">정보<span
							class="point-color06">(고유식별정보 포함)</span></strong>를 <strong
							class="underline">수집·이용</strong>하고자 하오니 아래의 내용을 확인하신 후 <strong
							class="underline">동의 여부를 결정</strong>하여 주시기 바랍니다.
					</p>
					<p class="word-type03 mb10 left">
						<strong>1. 정보 수집 및 이용 동의</strong>
					</p>
					<div class="table-type02">
						<table class="width-type03">
							<caption>정보 수집 및 이용 동의표 : 수집항목, 수집·이용 목적, 보유기간에 관한 정보
								제공표</caption>
							<colgroup>
								<col style="width: auto">
								<col style="width: auto">
								<col style="width: auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">수집항목</th>
									<th scope="col">수집·이용 목적</th>
									<th scope="col">보유기간</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="point-color06">기관명, 법인등록번호, 고용보험관리번호, 사업자등록번호,
										기업규모, 주소, 전화번호(휴대폰/사무실, FAX), 상시근로자수, 이메일</td>
									<td class="point-color06">직업능력개발훈련 사업참여에 관한 기업 정보 수집</td>
									<td class="point-color06 underline">신청일부터 10년</td>
								</tr>
							</tbody>
						</table>
					</div>

					<p class="word-type03 left mt10">※ 위의 기업 정보 수집·이용에 대한 동의를 거부할
						권리가 있습니다. 그러나 기업 정보 수집·이용에 대하여 동의를 거부할 경우 직업능력개발훈련에 관한 국가지원이 제한될 수
						있습니다.</p>
				</div>

				<div class="agree-check-box modal mt30 mb20">
					<p class="word-type03 mr15">정보 수집 및 이용에 동의합니다.</p>
					<div class="input-checkbox-wrapper">
						<div class="input-checkbox-area">
							<input type="checkbox" id="censentCheckboxInModal"
								name="checkbox01" value="" class="checkbox-type01"
								onclick="consentHandler()"> <label for="checkbox0101">
								동의</label>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<button type="button" class="btn-modal-close"
		onclick="closeModal('consentModal')">모달 창 닫기</button>

</div>


<!-- //모달 창 -->


<div class="mask"></div>
<div class="modal-wrapper" id="selectNcsModal">
	<h2>직무분류 검색</h2>
	<div class="modal-area">
		<div id="overlay"></div>
		<div class="loader"></div>

		<div class="contents-box pl0">
			<div class="basic-search-wrapper">
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield04"> NCS 대분류 </label>
						</dt>
						<dd>
							<select id="ncsDepth1" class="w50"
								onChange="ncsSelectHandler(this,`ncsDepth2`)">
								<option value="">선택</option>
								<c:forEach var="ncs" items="${ncsInfoDepth1}">
									<option value="${ncs.NCS_CODE}"><c:out
											value="${ncs.NCS_NAME}" /></option>
								</c:forEach>
							</select>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield04"> NCS 중분류 </label>
						</dt>
						<dd>
							<select id="ncsDepth2" class="w50"
								onChange="ncsSelectHandler(this,`ncsDepth3`)">
								<option value="">선택</option>
							</select>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield04"> 검색 </label>
						</dt>
						<dd>
							<input type="text" id ="searchNcsInput" class="w50" placeholder="">
							<button type="button" class="btn-m03 btn-color01" onclick="searchNcs(`searchNcsInput`)">검색</button>
						</dd>
					</dl>
				</div>
			</div>
		</div>



		<div class="contents-box pl0">

			<div class="table-type01 horizontal-scroll table-container">
				<table class="width-type03 modal-table">
					<caption>업체정보표 : 직무분류 에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 25%" />
						<col style="width: 25%" />
						<col style="width: 50%" />
					</colgroup>
					<thead class="modal-thead">
						<tr>
							<th scope="col">NCS코드</th>
							<th scope="col">직무분류명</th>
							<th scope="col">선택</th>
						</tr>
					</thead>
					<tbody id="ncsInfoTbody">
					<tr>
						<td colspan="3" class="bllist"><span>검색된 내용이 없습니다.</span></td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<button type="button" class="btn-modal-close"
		onclick="closeModal('selectNcsModal')">모달 창 닫기</button>


</div>


<script type="text/javascript" src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
function findAddr() {
	new daum.Postcode({
		oncomplete: function (data) {
			console.log(data);
			
			var roadAddr = data.roadAddress;
			var jibunAddr = data.jibunAddress;
			var extraRoadAddr = '';
			
			if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
				extraRoadAddr += data.bname;
			}
			
			if(data.buildingName !== '' && data.apartment === 'Y') {
				extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
			}
			
			if(extraRoadAddr !== '') {
				extraRoadAddr = '(' + extraRoadAddr + ')';
			}
			
			document.getElementById('trOprtnRegionZip').value = data.zonecode;
			insttMappingHandler(data.zonecode);
			if(roadAddr !== '') {
				document.getElementById('trOprtnRegionAddrDtl').value = extraRoadAddr;
				document.getElementById('trOprtnRegionAddr').value = roadAddr;
				
			
			}
			
			if(jibunAddr !== '') {
				document.getElementById('trOprtnRegionAddrDtl').value = extraRoadAddr;
				document.getElementById('trOprtnRegionAddr').value = jibunAddr;
			}
		}
	}).open();
}

</script>
<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
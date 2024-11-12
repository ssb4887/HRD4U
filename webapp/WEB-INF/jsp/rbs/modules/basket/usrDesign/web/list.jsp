<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>

<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />
<%
	/* 등록/수정창 새창으로 띄울 경우 사용 */
%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}" />
<%
	/* 수정버튼 class */
%>
<script type="text/javascript" src="${contextPath}${jsPath}/basket.js"></script>
<style>
	#content {
    max-width: 100%;
    padding: 0 20px;
    margin: 0 auto;
    !important
	}
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>


<link rel="stylesheet" href="${contextPath}${cssPath}/modal.css" />

<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>

<!-- tab1 시작 -->

	<div>
		<form method="GET" action="list.do?mId=71">
			<legend class="blind"> 기업 정보 조회 검색양식 </legend>
			<div class="basic-search-wrapper">
				<input type="hidden" name="mId" id="id_mId" value="71">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isBplNm"> 기업명 </label>
							</dt>
							<dd>
								<input type="text" id="isBplNm" name="isBplNm"
									value="${pageVO.cri.isBplNm}" title="기업명 입력" placeholder="기업명"
									maxlength="30" />
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield02"> 주소 </label>
							</dt>
							<dd>
								<select id="ctprvnSelect" name="isCtprvn" class="w50" title="주소 시/도"
									onchange="handleSelectCtprvn(this.id,'signguSelect')">
									<option value="">시/도</option>
									<c:forEach var="sidoData" items="${zips}">
										<option value="${sidoData.ctprvn}"
											<c:if test="${sidoData.ctprvn eq pageVO.cri.isCtprvn}"> selected</c:if>><c:out
												value="${sidoData.ctprvn}" /></option>


									</c:forEach>
								</select> <select id="signguSelect" name="isSigngu" class="w50" title="주소 군/구">
									<option value="">군/구</option>
								</select>
							</dd>
						</dl>
					</div>
				</div>

				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isBplNo"> 사업장관리번호 </label>
							</dt>
							<dd>
								<input type="text" id="isBplNo" name="isBplNo"
									value="${pageVO.cri.isBplNo}" title="사업자등록번호 입력"
									placeholder="사업장관리번호" maxlength="11" />
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="isIndustCd"> 업종 </label>
							</dt>
							<dd>
								<select id="isIndustCd" name="isIndustCd" title="업종">
									<option value="">업종 선택</option>
									<c:forEach var="industCd" items="${industCd}">
										<option value="${industCd.CODE}"
											<c:if test="${industCd.CODE eq pageVO.cri.isIndustCd}">selected</c:if>><c:out
												value="${industCd.NAME}" /></option>
									</c:forEach>

								</select>
							</dd>
						</dl>
					</div>
				</div>
				
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isBizrNo"> 사업자등록번호 </label>
							</dt>
							<dd>
								<input type="text" id="isBizrNo" name="isBizrNo"
									value="${pageVO.cri.isBizrNo}" title="사업자등록번호 입력"
									placeholder="사업자등록번호" maxlength="11" />
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="isPriSupCd"> 우선지원구분</label>
							</dt>
							<dd>
								<select id="isPriSupCd" name="isPriSupCd" title="업종">
									<option value="">선택</option>
										<option value="1">우선지원대상</option>
										<option value="2">대규모기업</option>
								</select>
							</dd>
						</dl>
					</div>
				</div>



				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isInsttIdx"> 소속기관 </label>
							</dt>
							<dd>
								<select id="isInsttIdx" name="isInsttIdx" title="소속기관">
									<option value="">소속기관 선택</option>
									<c:forEach var="instt" items="${insttList}">
										<option value="${instt.INSTT_IDX}"
											<c:if test="${instt.INSTT_IDX eq pageVO.cri.isInsttIdx}">selected</c:if>><c:out
												value="${instt.INSTT_NAME}" /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>

					<div class="half-box">
						<dl>
							<dt>
								<label for="isLclas"> 분류 </label>
							</dt>
							<dd>
								<select id="isLclas" name="isLclas" class="w50" title="대분류"
									onchange="handleClSelectChange(this.id, 'isSclas')">
									<option value="">대분류</option>
									<c:forEach var="clLclasList" items="${clLclasList}">
										<option value="${clLclasList.lclasCd}"
											<c:if test="${clLclasList.lclasCd eq pageVO.cri.isLclas}">selected</c:if>><c:out
												value="${clLclasList.lclasNm}" /></option>
									</c:forEach>
								</select> <select id="isSclas" name="isSclas" class="w50" title="소분류">
									<option value="">소분류</option>
								</select>
							</dd>
						</dl>
					</div>
				</div>

				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label> 훈련이력 </label>
							</dt>
							<dd>
								<div class="w30">
									<input type="checkbox" id="isTrRecord1" name="isTrRecord1" title="사업주훈련"
										value="01" class="checkbox-type01"
										<c:if test ="${!empty pageVO.cri.isTrRecord1}"> checked</c:if> />
									<label for="isTrRecord1"> 사업주훈련 </label>




								</div>
								<div class="w30">
									<input type="checkbox" id="isTrRecord2" name="isTrRecord2" title="지역산업맞춤형훈련"
										value="02" class="checkbox-type01"
										<c:if test ="${!empty pageVO.cri.isTrRecord2}"> checked</c:if> />
									<label for="isTrRecord2"> 지역산업맞춤형훈련 </label>
								</div>
								<div class="w30">
									<input type="checkbox" id="isTrRecord3" name="isTrRecord3" title="컨소시엄훈련"
										value="03" class="checkbox-type01"
										<c:if test ="${!empty pageVO.cri.isTrRecord3}"> checked</c:if> />
									<label for="isTrRecord3"> 컨소시엄훈련 </label>
								</div>
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="isTotEmpCnt"> 고용상시인원 </label>
							</dt>
							<dd>
								<div class="input-term-wrapper">
									<input type="text" id="isTotEmpCntMin" name="isTotEmpCntMin" title="고용상시인원"
										value="${pageVO.cri.isTotEmpCntMin}" maxlength="8" /> <span
										class="word-unit"> ~ </span> <input type="text"
										id="isTotEmpCntMax" name="isTotEmpCntMax"
										value="${pageVO.cri.isTotEmpCntMax}" maxlength="8" />
								</div>
							</dd>
						</dl>
					</div>
				</div>

				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isHashTag"> 해시태그 </label>
							</dt>
							<dd>
								<select id="isBranch1" name="isBranch" class="w50" title="해시태그 소속기관 선택"
									onchange="handleSelectInstt(this.id,'isHashTag1')">
									<option value="">소속기관 선택</option>
									<c:forEach var="instt" items="${insttList}">
										<option value="${instt.INSTT_IDX}"
											<c:if test="${instt.INSTT_IDX eq pageVO.cri.isBranch}">
												selected
											</c:if>><c:out
												value="${instt.INSTT_NAME}" /></option>
									</c:forEach>
								</select>
								<c:if test="${pageVO.cri.isHashTag}">
									<span>${pageVO.cri.isHashTag}</span>
								</c:if>
								<select id="isHashTag1" name="isHashTag" class="w50" title="해시태그 선택">
									<option value="">해시태그 선택</option>

								</select>
							</dd>
						</dl>
					</div>
				</div>
			</div>



			<div class="btns-area">
				<button type="submit" class="btn-search02" onclick="loadingBar()">
					<img src="${contextPath}/dct/images/icon/icon_search03.png" alt="" />
					<strong> 검색 </strong>
				</button>
			</div>
			<input type="hidden" name="pageNum" value="1"> <input
				type="hidden" name="amount" value="10">
	</div>


	<div class="contents-area">
		<div class="print-column">
			<div class="print-column-wraper">
				<h3>출력칼럼</h3>

				<div class="print-column-area">
					<ul>

						<li><input type="checkbox" id="column0099" name="showColumn"
							value="대분류" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '대분류'}"> checked </c:if>
							</c:forEach> />
							<label for="column0099"> 대분류 </label></li>

						<li><input type="checkbox" id="column0100" name="showColumn"
							value="소분류" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '소분류'}"> checked </c:if>
							</c:forEach> />
							<label for="column0100"> 소분류 </label></li>


						<!-- 고용 보험 데이터 -->

						<li><input type="checkbox" id="column0101" name="showColumn"
							value="자영업구분" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자영업구분'}"> checked </c:if>
							</c:forEach> />
							<label for="column0101"> 자영업구분 </label></li>
						<li><input type="checkbox" id="column0102" name="showColumn"
							value="법인등록번호" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '법인등록번호'}"> checked </c:if>
							</c:forEach> />
							<label for="column0102"> 법인등록번호 </label></li>
						<li><input type="checkbox" id="column0103" name="showColumn"
							value="사업자등록번호" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업자등록번호'}"> checked </c:if>
							</c:forEach> />
							<label for="column0103"> 사업자등록번호 </label></li>
						<li><input type="checkbox" id="column0104" name="showColumn"
							value="본지사 구분" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '본지사 구분'}"> checked </c:if>
							</c:forEach> />
							<label for="column0104"> 본지사 구분 </label></li>
						<li><input type="checkbox" id="column0105" name="showColumn"
							value="본사 사업장 관리번호" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '본사 사업장 관리번호'}"> checked </c:if>
							</c:forEach> />
							<label for="column0105"> 본사 사업장 관리번호 </label></li>
						
	<%-- 					<li><input type="checkbox" id="column0106" name="showColumn"
							value="사업장명" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업장명'}"> checked </c:if>
							</c:forEach> />
							<label for="column0106"> 사업장명 </label></li> --%>

						<li><input type="checkbox" id="column0107" name="showColumn"
							value="기업 우편번호" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '기업 우편번호'}"> checked </c:if>
							</c:forEach> />
							<label for="column0107"> 기업 우편번호 </label></li>

						<li><input type="checkbox" id="column0108" name="showColumn"
							value="도로명주소" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '도로명주소'}"> checked </c:if>
							</c:forEach> />
							<label for="column0108"> 도로명주소 </label></li>

						<li><input type="checkbox" id="column0109" name="showColumn"
							value="기업 상세주소" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '기업 상세주소'}"> checked </c:if>
							</c:forEach> />
							<label for="column0109"> 기업 상세주소 </label></li>
						<li><input type="checkbox" id="column0110" name="showColumn"
							value="고용성립일자" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '고용성립일자'}"> checked </c:if>
							</c:forEach> />
							<label for="column0110"> 고용성립일자 </label></li>

						<li><input type="checkbox" id="column0111" name="showColumn"
							value="고용보험 소멸일자" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '고용보험 소멸일자'}"> checked </c:if>
							</c:forEach> />
							<label for="column0111"> 고용보험 소멸일자 </label></li>

						<li><input type="checkbox" id="column0112" name="showColumn"
							value="고용사업장상태코드" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '고용사업장상태코드'}"> checked </c:if>
							</c:forEach> />
							<label for="column0112"> 고용사업장상태코드 </label></li>


						<li><input type="checkbox" id="column0113" name="showColumn"
							value="총사업장수" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '총사업장수'}"> checked </c:if>
							</c:forEach> />
							<label for="column0113"> 총사업장수 </label></li>


						<li><input type="checkbox" id="column0114" name="showColumn"
							value="총상시인원수" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '총상시인원수'}"> checked </c:if>
							</c:forEach> />
							<label for="column0114"> 총상시인원수 </label></li>


	<%-- 					<li><input type="checkbox" id="column0115" name="showColumn"
							value="직업훈련 여부" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '직업훈련 여부'}"> checked </c:if>
							</c:forEach> />
							<label for="column0115"> 직업훈련 여부 </label></li>
 --%>

						<li><input type="checkbox" id="column0116" name="showColumn"
							value="이메일" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '이메일'}"> checked </c:if>
							</c:forEach> />
							<label for="column0116"> 이메일 </label></li>


						<li><input type="checkbox" id="column0117" name="showColumn"
							value="사업장이메일" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업장이메일'}"> checked </c:if>
							</c:forEach> />
							<label for="column0117"> 사업장이메일 </label></li>


						<li><input type="checkbox" id="column0118" name="showColumn"
							value="사업장팩스번호" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업장팩스번호'}"> checked </c:if>
							</c:forEach> />
							<label for="column0118"> 사업장팩스번호 </label></li>


						<li><input type="checkbox" id="column0119" name="showColumn"
							value="사업장전화번호" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업장전화번호'}"> checked </c:if>
							</c:forEach> />
							<label for="column0119"> 사업장전화번호 </label></li>


						<li><input type="checkbox" id="column0120" name="showColumn"
							value="사업장URL" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업장URL'}"> checked </c:if>
							</c:forEach> />
							<label for="column0120"> 사업장URL </label></li>


						<li><input type="checkbox" id="column0121" name="showColumn"
							value="고용특별법적용구분" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '고용특별법적용구분'}"> checked </c:if>
							</c:forEach> />
							<label for="column0121"> 고용특별법적용구분 </label></li>


						<li><input type="checkbox" id="column0122" name="showColumn"
							value="우선지원시작일자" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '우선지원시작일자'}"> checked </c:if>
							</c:forEach> />
							<label for="column0122"> 우선지원시작일자 </label></li>

						<li><input type="checkbox" id="column0123" name="showColumn"
							value="예술인사업장 여부" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '예술인사업장 여부'}"> checked </c:if>
							</c:forEach> />
							<label for="column0123"> 예술인사업장 여부 </label></li>
						<!-- 고용 보험 데이터 끝 -->

						<!-- 기업 추가 정보 데이터 -->
<%-- 						<li><input type="checkbox" id="column0124" name="showColumn"
							value="우량기업 유무" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '우량기업 유무'}"> checked </c:if>
							</c:forEach> />
							<label for="column0124"> 우량기업 유무 </label></li>
 --%>
						<li><input type="checkbox" id="column0125" name="showColumn"
							value="BEST HRD 선정 유무" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq 'BEST HRD 선정 유무'}"> checked </c:if>
							</c:forEach> />
							<label for="column0125"> BEST HRD 선정 유무 </label></li>

		<%-- 				<li><input type="checkbox" id="column0126" name="showColumn"
							value="산업단지기업 유무" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '산업단지기업 유무'}"> checked </c:if>
							</c:forEach> />
							<label for="column0126"> 산업단지기업 유무 </label></li> --%>

						<li><input type="checkbox" id="column0127" name="showColumn"
							value="산재다발" class="checkbox-type01" onclick="upcomingColumn()"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '산재다발'}"> checked </c:if>
							</c:forEach> />
							<label for="column0127"> 산재다발 </label></li>

						<li><input type="checkbox" id="column0128" name="showColumn"
							value="임금체불" class="checkbox-type01" onclick="upcomingColumn()"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '임금체불'}"> checked </c:if>
							</c:forEach> />
							<label for="column0128"> 임금체불 </label></li>
						<!-- 기업 추가 정보 데이터 끝-->


						<!-- HRD-NET 데이터 -->
						<li><input type="checkbox" id="column0129" name="showColumn"
							value="사업주 참여 여부(횟수)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '사업주 참여 여부(횟수)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0129"> 사업주 참여 여부(횟수) </label></li>

						<li><input type="checkbox" id="column0130" name="showColumn"
							value="S-OJT 참여 여부(횟수)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq 'S-OJT 참여 여부(횟수)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0130"> S-OJT 참여 여부(횟수) </label></li>

						<li><input type="checkbox" id="column0131" name="showColumn"
							value="컨소시엄 참여 여부(횟수)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '컨소시엄 참여 여부(횟수)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0131"> 컨소시엄 참여 여부(횟수) </label></li>

						<li><input type="checkbox" id="column0132" name="showColumn"
							value="일학습 참여 여부(횟수)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '일학습 참여 여부(횟수)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0132"> 일학습 참여 여부(횟수) </label></li>


						<li><input type="checkbox" id="column0133" name="showColumn"
							value="지산맞 참여 여부(횟수)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '지산맞 참여 여부(횟수)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0133"> 지산맞 참여 여부(횟수) </label></li>

						<li><input type="checkbox" id="column0134" name="showColumn"
							value="학습조직화 참여 여부" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '학습조직화 참여 여부'}"> checked </c:if>
							</c:forEach> />
							<label for="column0134"> 학습조직화 참여 여부 </label></li>

						<li><input type="checkbox" id="column0135" name="showColumn"
							value="우수기관 인증 여부" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '우수기관 인증 여부'}"> checked </c:if>
							</c:forEach> />
							<label for="column0135"> 우수기관 인증 여부 </label></li>
						<!-- HRD-NET 데이터 끝-->


						<!-- 워크넷 데이터 -->
						<li><input type="checkbox" id="column0136" name="showColumn"
							value="채용제목" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '채용제목'}"> checked </c:if>
							</c:forEach> />
							<label for="column0136"> 채용제목 </label></li>

						<li><input type="checkbox" id="column0137" name="showColumn"
							value="임금형태" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '임금형태'}"> checked </c:if>
							</c:forEach> />
							<label for="column0137"> 임금형태 </label></li>

						<li><input type="checkbox" id="column0138" name="showColumn"
							value="급여" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '급여'}"> checked </c:if>
							</c:forEach> />
							<label for="column0138"> 급여 </label></li>

						<li><input type="checkbox" id="column0139" name="showColumn"
							value="지역" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '지역'}"> checked </c:if>
							</c:forEach> />
							<label for="column0139"> 지역 </label></li>

						<li><input type="checkbox" id="column0140" name="showColumn"
							value="근무형태" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '근무형태'}"> checked </c:if>
							</c:forEach> />
							<label for="column0140"> 근무형태 </label></li>
						<!-- 워크넷 데이터 끝 -->

						<!-- 재무 정보 데이터 -->

						<li><input type="checkbox" id="column0141" name="showColumn"
							value="기업신용등급" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '기업신용등급'}"> checked </c:if>
							</c:forEach> />
							<label for="column0141"> 기업신용등급 </label></li>
							
					<%-- 	<li><input type="checkbox" id="column0142" name="showColumn"
							value="기업채무불이행상태" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '기업채무불이행상태'}"> checked </c:if>
							</c:forEach> />
							<label for="column0142"> 기업채무불이행상태 </label></li> --%>
							
						<li><input type="checkbox" id="column0143" name="showColumn"
							value="자산총계(2022년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자산총계(2022년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0143"> 자산총계(2022년) </label></li>
						<li><input type="checkbox" id="column0144" name="showColumn"
							value="자산총계(2021년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자산총계(2021년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0144"> 자산총계(2021년)</label></li>
						<li><input type="checkbox" id="column0145" name="showColumn"
							value="자산총계(2020년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자산총계(2020년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0145"> 자산총계(2020년) </label></li>
						<li><input type="checkbox" id="column0146" name="showColumn"
							value="자산총계(2019년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자산총계(2019년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0146"> 자산총계(2019년) </label></li>
					<%-- 	<li><input type="checkbox" id="column0148" name="showColumn"
							value="부채총계(최근)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '부채총계(최근)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0148"> 부채총계(최근) </label></li>
						<li><input type="checkbox" id="column0149" name="showColumn"
							value="부채총계(최근-1년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '부채총계(최근-1년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0149"> 부채총계(최근-1년) </label></li>
						<li><input type="checkbox" id="column0150" name="showColumn"
							value="부채총계(최근-2년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '부채총계(최근-2년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0150"> 부채총계(최근-2년) </label></li>
						<li><input type="checkbox" id="column0151" name="showColumn"
							value="부채총계(최근-3년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '부채총계(최근-3년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0151"> 부채총계(최근-3년) </label></li>
						<li><input type="checkbox" id="column0152" name="showColumn"
							value="부채총계(최근-4년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '부채총계(최근-4년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0152"> 부채총계(최근-4년) </label></li> --%>
						
						
						<li><input type="checkbox" id="column0153" name="showColumn"
							value="자본총계(2022년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자본총계(2022년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0153"> 자본총계(2022년) </label></li>
						<li><input type="checkbox" id="column0154" name="showColumn"
							value="자본총계(2021년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자본총계(2021년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0154"> 자본총계(2021년) </label></li>
						<li><input type="checkbox" id="column0155" name="showColumn"
							value="자본총계(2020년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자본총계(2020년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0155"> 자본총계(2020년) </label></li>
						<li><input type="checkbox" id="column0156" name="showColumn"
							value="자본총계(2019년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '자본총계(2019년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0156"> 자본총계(2019년) </label></li>
						
						<li><input type="checkbox" id="column0158" name="showColumn"
							value="매출액(2022년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '매출액(2022년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0158"> 매출액(2022년) </label></li>
						<li><input type="checkbox" id="column0159" name="showColumn"
							value="매출액(2021년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '매출액(2021년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0159"> 매출액(2021년) </label></li>
						<li><input type="checkbox" id="column0160" name="showColumn"
							value="매출액(2020년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '매출액(2020년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0160"> 매출액(2020년) </label></li>
						<li><input type="checkbox" id="column0161" name="showColumn"
							value="매출액(2019년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '매출액(2019년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0161"> 매출액(2019년) </label></li>
						


						<li><input type="checkbox" id="column0163" name="showColumn"
							value="영업이익(2022년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '영업이익(2022년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0163"> 영업이익(2022년) </label></li>
						<li><input type="checkbox" id="column0164" name="showColumn"
							value="영업이익(2021년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '영업이익(2021년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0164"> 영업이익(2021년) </label></li>
						<li><input type="checkbox" id="column0165" name="showColumn"
							value="영업이익(2020년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '영업이익(2020년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0165"> 영업이익(2020년) </label></li>
						<li><input type="checkbox" id="column0166" name="showColumn"
							value="영업이익(2019년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '영업이익(2019년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0166"> 영업이익(2019년) </label></li>

						<li><input type="checkbox" id="column0168" name="showColumn"
							value="당기순이익(2022년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '당기순이익(2022년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0168"> 당기순이익(2022년) </label></li>
						<li><input type="checkbox" id="column0169" name="showColumn"
							value="당기순이익(2021년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '당기순이익(2021년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0169"> 당기순이익(2021년) </label></li>
						<li><input type="checkbox" id="column0170" name="showColumn"
							value="당기순이익(2020년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '당기순이익(2020년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0170"> 당기순이익(2020년) </label></li>
						<li><input type="checkbox" id="column0171" name="showColumn"
							value="당기순이익(2019년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '당기순이익(2019년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0171"> 당기순이익(2019년) </label></li>
					
						<%-- <li><input type="checkbox" id="column0173" name="showColumn"
							value="급여(최근)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '급여(최근)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0173"> 급여(최근) </label></li>
						<li><input type="checkbox" id="column0174" name="showColumn"
							value="급여(최근-1년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '급여(최근-1년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0174"> 급여(최근-1년) </label></li>
						<li><input type="checkbox" id="column0175" name="showColumn"
							value="급여(최근-2년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '급여(최근-2년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0175"> 급여(최근-2년) </label></li>
						<li><input type="checkbox" id="column0176" name="showColumn"
							value="급여(최근-3년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '급여(최근-3년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0176"> 급여(최근-3년) </label></li>
						<li><input type="checkbox" id="column0177" name="showColumn"
							value="급여(최근-4년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '급여(최근-4년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0177"> 급여(최근-4년) </label></li>
						<li><input type="checkbox" id="column0178" name="showColumn"
							value="교육훈련비(최근)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '교육훈련비(최근)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0178"> 교육훈련비(최근) </label></li>
						<li><input type="checkbox" id="column0179" name="showColumn"
							value="교육훈련비(최근-1년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '교육훈련비(최근-1년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0179"> 교육훈련비(최근-1년) </label></li>
						<li><input type="checkbox" id="column0180" name="showColumn"
							value="교육훈련비(최근-2년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '교육훈련비(최근-2년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0180"> 교육훈련비(최근-2년) </label></li>
						<li><input type="checkbox" id="column0181" name="showColumn"
							value="교육훈련비(최근-3년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '교육훈련비(최근-3년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0181"> 교육훈련비(최근-3년) </label></li>
						<li><input type="checkbox" id="column0182" name="showColumn"
							value="교육훈련비(최근-4년)" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '교육훈련비(최근-4년)'}"> checked </c:if>
							</c:forEach> />
							<label for="column0182"> 교육훈련비(최근-4년) </label></li> --%>
						<!-- 재무정보 데이터 끝 -->
						<!-- 워크넷 데이터 -->
						<li><input type="checkbox" id="column0183" name="showColumn"
							value="강소기업 여부" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '강소기업 여부'}"> checked </c:if>
							</c:forEach> />
							<label for="column0183"> 강소기업 여부 </label></li>
						<li><input type="checkbox" id="column0184" name="showColumn"
							value="청년친화 강소기업 여부" class="checkbox-type01"
							<c:forEach var="item" items="${pageVO.cri.showColumn}">
								<c:if test="${item eq '청년친화 강소기업 여부'}"> checked </c:if>
							</c:forEach> />
							<label for="column0184"> 청년친화 강소기업 여부 </label></li>
						<!-- 워크넷 데이터 끝 -->



					</ul>
					<div class="mobile-btns-wrapper">
						<button type="button" class="btn-color02">닫기</button>
					</div>
				</div>
			</div>
			</form>
			<div class="print-column-table-wrapper">




				<div class="title-wrapper">
					<p class="total fl">
						총 기업 수 <strong><c:out value="${pageVO.total}" /></strong> 건 (
						<c:out value="${pageVO.pageNum}" />
						/
						<c:out value="${pageVO.realEnd}" />
						페이지 )
					</p>
					<c:if test="${totalCount > 1}" >
					<div class="fr">
						<a
							href="${contextPath}/dct/basket/excel.do?${queryString}&totalCount=${totalCount}"
							class="btn-m01 btn-color03" onClick="loadingBarTimeOut()">엑셀
							다운로드</a>
					</div>
					</c:if>
				</div>

				<div class="table-type01 horizontal-scroll">
					<table>
						<caption>기초진단관리표 : No, 사업장관리번호, 기업명, 소속기관, 대분류, 소분류, 본사
							구분에 관한 정보 제공표</caption>
						<thead>
							<tr>
								<th scope="col" style="width: 200px">사업장관리번호</th>
								<th scope="col" style="width: 200px">기업명</th>
								<th scope="col" style="width: 200px">소속기관</th>
								<th scope="col" style="width: 200px">고용업종명</th>
								<th scope="col" style="width: 150px">고용상시인원</th>
								<th scope="col" style="width: 150px">우선지원구분</th>
								<c:forEach var="showColumn" items="${showColumn}" varStatus="i">
									<th scope="col" style="width: 150px">${showColumn}</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
														<c:if test="${empty list}">
								<tr>
									<td colspan="6" class="bllist"><span>검색된 내용이 없습니다.</span></td>
								</tr>
							</c:if>
							<c:forEach var="listDt" items="${list}" varStatus="i">
								<tr id="column">
									<td class="bplNo"><strong class="point-color01">
											${listDt.bplNo} </strong> <a href=view.do?mId=71&bplNo=${listDt.bplNo}
										class="btn-linked"> <img
											src="${contextPath}/dct/images/icon/icon_search04.png" alt="" />
									</a></td>
									<td class="bplNm"><c:out value="${listDt.bplNm}" /></td>
									<td><c:out value="${listDt.insttIdx}" /></td>
									<td><c:out value="${listDt.bplIndustNm}" /></td>
									<td><c:out value="${listDt.totEmpCnt}" /></td>
									<td>
										<c:choose>
											<c:when test="${listDt.priSupCd eq 1}">우선지원대상</c:when>
											<c:when test="${listDt.priSupCd eq 2}">대규모기업</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									
									</td>
									<c:forEach var="showColumn" items="${showColumn}" varStatus="i">
										<c:if test="${showColumn eq '자영업구분'}">
											<td><c:out
													value="${listDt.selfEmpCd eq '1' ? 'Y' : 'N'}" /></td>
										</c:if>
										<c:if test="${showColumn eq '대분류'}">
											<td><c:out value="${listDt.lclasNm}" /></td>
										</c:if>
										<c:if test="${showColumn eq '소분류'}">
											<td><c:out value="${listDt.sclasNm}" /></td>
										</c:if>
										<c:if test="${showColumn eq '법인등록번호'}">
											<td><c:out value="${listDt.jurirno}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업자등록번호'}">
											<td><c:out value="${listDt.bizrNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '본지사 구분'}">
											<td><c:out
													value="${listDt.headBplCd eq '1' ? 'Y' : 'N' eq '1' ? 'Y' : 'N'}" /></td>
										</c:if>
										<c:if test="${showColumn eq '본사 사업장 관리번호'}">
											<td><c:out value="${listDt.hedofcBplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장명'}">
											<td><c:out value="${listDt.bplNm}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업 우편번호'}">
											<td><c:out value="${listDt.bplZip}" /></td>
										</c:if>
										<c:if test="${showColumn eq '도로명주소'}">
											<td><c:out value="${listDt.bplAddr}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업 상세주소'}">
											<td><c:out value="${listDt.addrDtl}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용성립일자'}">
											<td><c:out value="${listDt.emplymFormatnDe}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용보험 소멸일자'}">
											<td><c:out value="${listDt.empinsExtshDe}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용사업장상태코드'}">
											<td><c:out value="${listDt.bplStatusCd}" /></td>
										</c:if>
										<c:if test="${showColumn eq '총사업장수'}">
											<td><c:out value="${listDt.totBplCnt}" /></td>
										</c:if>
										<c:if test="${showColumn eq '총상시인원수'}">
											<td><c:out value="${listDt.totWorkCnt}" /></td>
										</c:if>
										<c:if test="${showColumn eq '직업훈련 여부'}">
											<td><c:out value="${listDt.occpTrYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '이메일'}">
											<td><c:out value="${listDt.email}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장이메일'}">
											<td><c:out value="${listDt.bplEmail}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장팩스번호'}">
											<td><c:out
													value="${listDt.bplAreaNo} - ${listDt.bplFaxNo1} - ${listDt.bplFaxNo2}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장전화번호'}">
											<td><c:out
													value="${listDt.bplAreaNo} - ${listDt.bplTelno1} - ${listDt.bplTelno2}" /></td>
										</c:if>
										<c:if test="${showColumn eq '사업장URL'}">
											<td><c:out value="${listDt.bplUrl}" /></td>
										</c:if>
										<c:if test="${showColumn eq '고용특별법적용구분'}">
											<td><c:out value="${listDt.spemplymApplcSe}" /></td>
										</c:if>
										<c:if test="${showColumn eq '우선지원시작일자'}">
											<td><c:out value="${listDt.priSupStartDate}" /></td>
										</c:if>
										<c:if test="${showColumn eq '예술인사업장 여부'}">
											<td><c:out value="${listDt.artbplYn}" /></td>
										</c:if>

										<c:if test="${showColumn eq '우량기업 유무'}">
											<td><c:out value="${listDt.excentYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq 'BEST HRD 선정 유무'}">
											<td><c:out value="${listDt.besthrdYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '산업단지기업 유무'}">
											<td><c:out value="${listDt.idscpxYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '산재다발'}">
											<td><c:out value="${listDt.indacmtYn}" /></td>
										</c:if>
										<c:if test="${showColumn eq '임금체불'}">
											<td><c:out value="${listDt.wgdlyYn}" /></td>
										</c:if>

										<c:if test="${showColumn eq '사업주 참여 여부(횟수)'}">
											<td><c:out value="${listDt.bprCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq 'S-OJT 참여 여부(횟수)'}">
											<td><c:out value="${listDt.sojtCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '컨소시엄 참여 여부(횟수)'}">
											<td><c:out value="${listDt.conCount}" /></td>
										</c:if>

										<c:if test="${showColumn eq '일학습 참여 여부(횟수)'}">
											<td><c:out value="${listDt.pdmsCount}" /></td>
										</c:if>

										<c:if test="${showColumn eq '지산맞 참여 여부(횟수)'}">
											<td><c:out value="${listDt.regCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '학습조직화 참여 여부'}">
											<td><c:out value="${listDt.egCount}" /></td>
										</c:if>
										<c:if test="${showColumn eq '우수기관 인증 여부'}">
											<td><c:out value="${listDt.certCount}" /></td>
										</c:if>

										<c:if test="${showColumn eq '채용제목'}">
											<td><c:out value="${listDt.title}" /></td>
										</c:if>
										<c:if test="${showColumn eq '임금형태'}">
											<td><c:out value="${listDt.salaryStle}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여'}">
											<td><c:out value="${listDt.salaryScope}" /></td>
										</c:if>
										<c:if test="${showColumn eq '지역'}">
											<td><c:out value="${listDt.region}" /></td>
										</c:if>
										<c:if test="${showColumn eq '근무형태'}">
											<td><c:out value="${listDt.workStle}" /></td>
										</c:if>
										<c:if test="${showColumn eq '기업신용등급'}">
											<td><c:choose>
													<c:when test="${listDt.credtGrad eq '01'}">
													AAA
												</c:when>
													<c:when test="${listDt.credtGrad eq '02'}">
													AA+
												</c:when>
													<c:when test="${listDt.credtGrad eq '03'}">
													AA
												</c:when>
													<c:when test="${listDt.credtGrad eq '04'}">
													AA-
												</c:when>
													<c:when test="${listDt.credtGrad eq '05'}">
													A+
												</c:when>
													<c:when test="${listDt.credtGrad eq '06'}">
													A
												</c:when>
													<c:when test="${listDt.credtGrad eq '07'}">
													A-
												</c:when>
													<c:when test="${listDt.credtGrad eq '08'}">
													BBB+
												</c:when>
													<c:when test="${listDt.credtGrad eq '09'}">
													BBB
												</c:when>
													<c:when test="${listDt.credtGrad eq '10'}">
													BBB-
												</c:when>
													<c:when test="${listDt.credtGrad eq '11'}">
													BB+
												</c:when>
													<c:when test="${listDt.credtGrad eq '12'}">
													BB
												</c:when>
													<c:when test="${listDt.credtGrad eq '13'}">
													BB-
												</c:when>
													<c:when test="${listDt.credtGrad eq '14'}">
													B+
												</c:when>
													<c:when test="${listDt.credtGrad eq '15'}">
													B
												</c:when>
													<c:when test="${listDt.credtGrad eq '16'}">
													B-
												</c:when>
													<c:when test="${listDt.credtGrad eq '17'}">
													CCC+
												</c:when>
													<c:when test="${listDt.credtGrad eq '18'}">
													CCC
												</c:when>
													<c:when test="${listDt.credtGrad eq '19'}">
													CCC-
												</c:when>
													<c:when test="${listDt.credtGrad eq '20'}">
													CC
												</c:when>
													<c:when test="${listDt.credtGrad eq '21'}">
													C
												</c:when>
													<c:when test="${listDt.credtGrad eq '22'}">
													D
												</c:when>
													<c:when test="${listDt.credtGrad eq '91'}">
													R1
												</c:when>
													<c:when test="${listDt.credtGrad eq '92'}">
													R2
												</c:when>
													<c:when test="${listDt.credtGrad eq '93'}">
													R3
												</c:when>
													<c:when test="${listDt.credtGrad eq '94'}">
													F1
												</c:when>
													<c:when test="${listDt.credtGrad eq '95'}">
													F2
												</c:when>
													<c:when test="${listDt.credtGrad eq '99'}">
													NR
												</c:when>
													<c:otherwise>
													-
												</c:otherwise>
												</c:choose></td>
										</c:if>
							<%-- 			 <c:if test="${showColumn eq '기업채무불이행상태'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if> --%>
										<c:if test="${showColumn eq '자산총계(2022년)'}">
											<td><fmt:formatNumber value="${listDt.totAssets2022}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(2021년)'}">
											<td><fmt:formatNumber value="${listDt.totAssets2021}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(2020년)'}">
											<td><fmt:formatNumber value="${listDt.totAssets2020}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '자산총계(2019년)'}">
											<td><fmt:formatNumber value="${listDt.totAssets2019}" type="currency" currencyCode="KRW"/></td>
										</c:if>
							<%-- 			<c:if test="${showColumn eq '부채총계(2022년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(2021년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(2020년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '부채총계(2019년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if> --%>
										<c:if test="${showColumn eq '자본총계(2022년)'}">
											<td><fmt:formatNumber value="${listDt.totCaplAmt2022}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(2021년)'}">
											<td><fmt:formatNumber value="${listDt.totCaplAmt2021}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(2020년)'}">
											<td><fmt:formatNumber value="${listDt.totCaplAmt2020}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '자본총계(2019년)'}">
											<td><fmt:formatNumber value="${listDt.totCaplAmt2019}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(2022년)'}">
											<td><fmt:formatNumber value="${listDt.selngAmt2022}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(2021년)'}">
											<td><fmt:formatNumber value="${listDt.selngAmt2021}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(2020년)'}">
											<td><fmt:formatNumber value="${listDt.selngAmt2020}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '매출액(2019년)'}">
											<td><fmt:formatNumber value="${listDt.selngAmt2019}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										
										<c:if test="${showColumn eq '영업이익(2022년)'}">
											<td><fmt:formatNumber value="${listDt.bsnProfit2022}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(2021년)'}">
											<td><fmt:formatNumber value="${listDt.bsnProfit2021}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(2020년)'}">
											<td><fmt:formatNumber value="${listDt.bsnProfit2020}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '영업이익(2019년)'}">
											<td><fmt:formatNumber value="${listDt.bsnProfit2019}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										
										<c:if test="${showColumn eq '당기순이익(2022년)'}">
											<td><fmt:formatNumber value="${listDt.thstrmNtpf2022}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(2021년)'}">
											<td><fmt:formatNumber value="${listDt.thstrmNtpf2021}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(2020년)'}">
											<td><fmt:formatNumber value="${listDt.thstrmNtpf2020}" type="currency" currencyCode="KRW"/></td>
										</c:if>
										<c:if test="${showColumn eq '당기순이익(2019년)'}">
											<td><fmt:formatNumber value="${listDt.thstrmNtpf2019}" type="currency" currencyCode="KRW"/></td>
										</c:if>

										<%-- <c:if test="${showColumn eq '급여(최근)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '급여(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-1년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-2년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-3년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if>
										<c:if test="${showColumn eq '교육훈련비(최근-4년)'}">
											<td><c:out value="${listDt.bplNo}" /></td>
										</c:if> --%>


										<c:if test="${showColumn eq '강소기업 여부'}">
											<td>
											<c:choose>
												<c:when test="${listDt.sgCount > 0}">
												Y
												</c:when>
												<c:otherwise>
												N
												</c:otherwise>
											</c:choose>
											</td>
										</c:if>
										<c:if test="${showColumn eq '청년친화 강소기업 여부'}">
											<td>
												<c:choose>
												<c:when test="${listDt.yfCount > 0}">
												Y
												</c:when>
												
												<c:otherwise>
												N
												</c:otherwise>
											</c:choose>
											
											</td>
										</c:if>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


				<div class="paging-navigation-wrapper">
					<form action="list.do" name="pageForm" method="GET">
						<!-- 페이징 네비게이션 -->
						<p class="paging-navigation">
							<c:if test="${pageVO.prev}">
								<a href="#none" class="btn-first" data-pagenum='1'
									onclick="paginationHandler('pageForm')">맨 처음 페이지로 이동</a>
							</c:if>
							<c:if test="${pageVO.prev}">
								<a href="#" class="btn-preview"
									data-pagenum='${pageVO.startPage - 1}'
									onclick="paginationHandler('pageForm')">이전 페이지로 이동</a>
							</c:if>
							<c:forEach var="num" begin="${pageVO.startPage}"
								end="${pageVO.endPage}">
								<c:if test="${pageVO.cri.pageNum == num}">
									<strong><c:out value="${num}" /></strong>
								</c:if>
								<c:if test="${pageVO.cri.pageNum != num && num != 0}">
									<a href="#" data-pagenum='${num}'
										onclick="paginationHandler('pageForm')"><c:out
											value="${num}" /></a>
								</c:if>
							</c:forEach>
							<c:if test="${pageVO.next}">
								<a href="#" class="btn-next"
									data-pagenum='${pageVO.endPage + 1}'
									onclick="paginationHandler('pageForm')">다음 페이지로 이동</a>
							</c:if>
							<c:if test="${pageVO.next}">
								<a href="#none" class="btn-last"
									data-pagenum='${pageVO.realEnd}'
									onclick="paginationHandler('pageForm')">맨마지막 페이지로 이동</a>
							</c:if>
						</p>
						<!-- //페이징 네비게이션 -->
						<c:if test="${!empty pageVO.cri.pageNum}">

							<div class="sort-page-wrapper">
								<select onchange="paginationHandler('amount')">
									<option value='10'
										<c:if test = "${pageVO.cri.amount eq 10}">selected</c:if>>10건씩
										보기</option>
									<option value='20'
										<c:if test = "${pageVO.cri.amount eq 20}">selected</c:if>>20건씩
										보기</option>
									<option value='30'
										<c:if test = "${pageVO.cri.amount eq 30}">selected</c:if>>30건씩
										보기</option>
									<option value='40'
										<c:if test = "${pageVO.cri.amount eq 40}">selected</c:if>>40건씩
										보기</option>
									<option value='50'
										<c:if test = "${pageVO.cri.amount eq 50}">selected</c:if>>50건씩
										보기</option>
								</select>
							</div>
						</c:if>
						<input type="hidden" name="mId" value="71"> <input
							type="hidden" name="pageNum" value="${pageVO.cri.pageNum}">
						<input type="hidden" name="amount" value="${pageVO.cri.amount}">
						<input type="hidden" name="isBplNm" value="${pageVO.cri.isBplNm}">
						<input type="hidden" name="isBplNo" value="${pageVO.cri.isBplNo}">
						<input type="hidden" name="isInsttIdx"
							value="${pageVO.cri.isInsttIdx}"> <input type="hidden"
							name="isIndustCd" value="${pageVO.cri.isIndustCd}"> <input
							type="hidden" name="isTotEmpCntMin"
							value="${pageVO.cri.isTotEmpCntMin}"> <input
							type="hidden" name="isTotEmpCntMax"
							value="${pageVO.cri.isTotEmpCntMax}"> <input
							type="hidden" name="isAddr" value="${pageVO.cri.isAddr}">
						<input type="hidden" name="isSrRecord"
							value="${pageVO.cri.isSrRecord}"> <input type="hidden"
							name="isBranch" value="${pageVO.cri.isBranch}"> <input
							type="hidden" name="isHashTag" value="${pageVO.cri.isHashTag}">
						<input type="hidden" name="isLclas" value="${pageVO.cri.isLclas}">
						<input type="hidden" name="isSclas" value="${pageVO.cri.isSclas}">
						<input type="hidden" name="isCtprvn"
							value="${pageVO.cri.isCtprvn}"> <input type="hidden"
							name="isSigngu" value="${pageVO.cri.isSigngu}"> <input
							type="hidden" name="isTrRecord1"
							value="${pageVO.cri.isTrRecord1}"> <input type="hidden"
							name="isTrRecord2" value="${pageVO.cri.isTrRecord2}"> <input
							type="hidden" name="isTrRecord3"
							value="${pageVO.cri.isTrRecord3}">

						<c:forEach var="showColumn" items="${pageVO.cri.showColumn}">
							<input type="hidden" name="showColumn" value="${showColumn}">
						</c:forEach>




					</form>
				</div>

			</div>
		</div>
	</div>

<!-- //CMS 끝 -->


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
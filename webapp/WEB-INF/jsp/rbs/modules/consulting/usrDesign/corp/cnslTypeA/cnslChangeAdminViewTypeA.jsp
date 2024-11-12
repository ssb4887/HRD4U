
<!-- CMS 시작 -->

<div class="contents-area pl0">
	<h3 class="title-type01 ml0">맞춤 훈련과정 변경 신청서
		<div class="btns-right">
		<button type="button" class="btn-m01 btn-color03 depth3" onclick="openModal('changeReject-modal', `${cnsl.cnslIdx}`)">반려사유 조회</button>
		</div>
	</h3>
	<div class="contents-box pl0">
		<div class="table-type02 horizontal-scroll">
			<table class="width-type02">
				<colgroup>
					<col width="15%">
					<col width="auto">
					<col width="auto">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="4">1. 훈련과정 개요</th>
					</tr>
					<tr>
						<th scope="row" rowspan="2" <c:if test="${cnsl.cnslType ne changeCnsl.cnslType}">style="color: blue;"</c:if>>훈련과정 유형</th>
						<th scope="row">사업주훈련</th>
						<th scope="row">일반직무전수 OJT</th>
						<th scope="row">과제수행 OJT</th>
					</tr>
					<tr>
						<td class="left">
							<div class="input-radio-area">
								<input type="radio" id="radio0107" name="changeCnslType" value="1"
									class="radio-type01"
									<c:if test="${changeCnsl.cnslType eq 1}">checked</c:if>
									onclick="event.preventDefault()"> <label
									for="radio0101"></label>
							</div>
						</td>
						<td class="left">
							<div class="input-radio-area">
										<input type="radio" id="radio0108" name="changeCnslType" value="2"
											class="radio-type01"
											<c:if test="${changeCnsl.cnslType eq 2}">checked</c:if>
											onclick="event.preventDefault()">
										<label for="radio0102"></label>
							</div>
						</td>
						<td class="left">
							<div class="input-radio-area">
										<input type="radio" id="radio0109" name="changeCnslType" value="3"
											class="radio-type01"
											<c:if test="${changeCnsl.cnslType eq 3}">checked</c:if>
											onclick="event.preventDefault()">
										<label for="radio0103"></label>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.cnslDemandMatter ne changeCnsl.cnslDemandMatter}">style="color: blue;"</c:if>>훈련직무 및 개발 요구사항</th>
						<td class="left" colspan="3"><c:out
								value="${changeCnsl.cnslDemandMatter}" /></td>

					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.dtyClNm ne changeCnsl.dtyClNm}">style="color: blue;"</c:if>>직무분류</th>
						<td class="left" colspan="3">
							<c:out value="${changeCnsl.dtyClNm}" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>





	<div class="contents-box pl0">
		<div class="table-type02 horizontal-scroll">
			<table class="width-type02">
				<colgroup>
					<col width="15%">
					<col width="auto">
					<col width="15%">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="4">2. 신청기업 현황</th>
					</tr>
					<tr>
						<th scope="row">기업명</th>
						<td class="left"><c:out value="${changeCnsl.corpNm}" /></td>
						<th scope="row">대표자명</th>
						<td class="left"><c:out value="${changeCnsl.reperNm}" /></td>
					</tr>
					<tr>
						<th scope="row">사업자등록번호</th>
						<td class="left"><c:out value="${changeCnsl.bizrNo}" /></td>
						<th scope="row">고용보험관리번호</th>
						<td class="left"><c:out value="${changeCnsl.bplNo}" /></td>
					</tr>
					<tr>
						<th scope="row">업종</th>
						<td colspan="3" class="left"><c:out
								value="${changeCnsl.indutyNm}" /></td>
					</tr>
					<tr>
						<th scope="row">주소</th>
						<td class="left"><c:out value="${changeCnsl.bplAddr}" /></td>
						<th scope="row">상시근로자수</th>
						<td class="left"><c:out value="${changeCnsl.totWorkCnt}" /></td>
					</tr>
					<tr>
						<th scope="row" rowspan="4" <c:if test="${cnsl.trOprtnRegionZip ne changeCnsl.trOprtnRegionZip}">style="color: blue;"</c:if>>훈련실시주소</th>
						<td class="left" rowspan="4">
								<div class=" flex-box"><c:out value="${cnsl.trOprtnRegionZip}" /></div>
								<div class=" flex-box"><c:out value="${cnsl.trOprtnRegionAddr}" /></div>
								<div class=" flex-box"><c:out value="${cnsl.trOprtnRegionAddrDtl}" /></div>
						</td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.recomendInsttNm ne changeCnsl.recomendInsttNm}">style="color: blue;"</c:if>>추천기관명</th>
						<td class="left"><c:out value="${changeCnsl.recomendInsttNm}" /></td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.cmptncBrffcNm ne changeCnsl.cmptncBrffcNm}">style="color: blue;"</c:if>>관할 지부·지사</th>
						<td class="left"><c:out value="${changeCnsl.cmptncBrffcNm}" /></td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.cmptncBrffcPicTelno ne changeCnsl.cmptncBrffcPicTelno}">style="color: blue;"</c:if>>연락처</th>
						<td class="left"><c:out value="${changeCnsl.cmptncBrffcPicTelno}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="contents-box pl0">
		<div class="table-type02 horizontal-scroll">
			<table class="width-type02">
				<colgroup>
					<col width="15%">
					<col width="auto">
					<col width="15%">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="4">3. 담당자 정보</th>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.corpPicNm ne changeCnsl.corpPicNm}">style="color: blue;"</c:if>>성명</th>
						<td class="left"><c:out value="${changeCnsl.corpPicNm}" /></td>
						<th scope="row" <c:if test="${cnsl.corpPicOfcps ne changeCnsl.corpPicOfcps}">style="color: blue;"</c:if>>직위</th>
						<td class="left"><c:out value="${changeCnsl.corpPicOfcps}" /></td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.corpPicTelno ne changeCnsl.corpPicTelno}">style="color: blue;"</c:if>>전화번호</th>
						<td class="left"><c:out value="${changeCnsl.corpPicTelno}" /></td>
						<th scope="row" <c:if test="${cnsl.corpPicEmail ne changeCnsl.corpPicEmail}">style="color: blue;"</c:if>>E-mail</th>
						<td class="left"><c:out value="${changeCnsl.corpPicEmail}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="contents-box pl0">
		<div class="table-type02 horizontal-scroll">
			<table class="width-type02" id="teamTable">
				<colgroup>
					<col width="15%">
					<col width="auto">
					<col width="auto">
					<col width="auto">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="9">3. 컨설팅 팀 구성 및 개요</th>
					</tr>
					<tr>
						<th scope="row">컨설팅 팀</th>
						<td class="">HRD4U ID</td>
						<td class="">성명</td>
						<td class="">소속</td>
						<td class="">직위</td>
						<td class="">연락처</td>
						<td class="">적합여부</td>
						<td class="">HRD4U 인력풀</td>
						<td class="">사업장 한도</td>
					</tr>
					<tr class="pm">

						<th scope="row" 
						<c:forEach var="chagneCnslmember" items="${changeCnsl.cnslTeam.members}">
						<c:if test="${chagneCnslmember.teamIdx eq 1 and chagneCnslmember.teamOrderIdx eq 1}">
							<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}">
							<c:if test="${cnslMember.teamIdx eq 1 and cnslMember.teamOrderIdx eq 1}">
								<c:if test="${chagneCnslmember.loginId ne cnslMember.loginId}">style="color: blue;"</c:if>
							</c:if>
							<c:if test="${not (cnslMember.teamIdx eq 1 and cnslMember.teamOrderIdx eq 1)}">style="color: blue;"</c:if>
						
						</c:forEach>
						</c:if></c:forEach>>컨설팅 책임자 (PM)</th>
						<td class="mberIdTd"><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.loginId}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberName}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberPsitn}" />
								</c:if>
							</c:forEach></td>

						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberOfcps}" />
								</c:if>
							</c:forEach></td>

						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberTelno}" />
								</c:if>
							</c:forEach></td>

						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pmId_suitableY" name="radio02" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>

								</div>

								<div class="input-radio-area">
									<input type="radio" id="pmId_suitableN" name="radio02" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pmId_isHrpY" name="radio0301" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="pmId_isHrpN" name="radio0301" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd"><input type="text" id="" name=""
							value="" class="w100" readOnly></td>
						</td>
					</tr>

					<tr class="exExpert" id="exExpert_01">
						<th scope="row" 
						
						<c:forEach var="changeCnslmember" items="${changeCnsl.cnslTeam.members}">
							<c:if test="${changeCnslmember.teamIdx eq 2 and changeCnslmember.teamOrderIdx eq 1}">
								<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}">
									<c:if test="${cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx eq 1}">
										<c:if test="${changeCnslmember.loginId ne cnslMember.loginId }">style="color: blue;"</c:if>
									</c:if>
									<c:if test="${not (cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx eq 1)}">style="color: blue;"</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>>외부 내용전문가</th>
						<td class="mberIdTd"><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.loginId}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberName}" />
								</c:if>
							</c:forEach></td>

						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberPsitn}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberOfcps}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberTelno}" />
								</c:if>
							</c:forEach></td>
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_suitableY" name="radio0401" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_suitableN" name="radio0401" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpY" name="radio0501"
										value="" class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpN" name="radio0501"
										value="" class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd"><input type="text" id="" name=""
							value="" class="w100" readOnly></td>
						</td>

					</tr>

					<tr class="inExpert" id="inExpert_01">
						<th scope="row" 
						<c:forEach var="changeCnslmember" items="${changeCnsl.cnslTeam.members}">
							<c:if test="${changeCnslmember.teamIdx eq 3 and changeCnslmember.teamOrderIdx eq 1}">
								<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}">
									<c:if test="${cnslMember.teamIdx eq 3 and cnslMember.teamOrderIdx eq 1}">
										<c:if test="${changeCnslmember.loginId ne cnslMember.loginId}">style="color: blue;"</c:if>
									</c:if>
									<c:if test="${not (cnslMember.teamIdx eq 3 and cnslMember.teamOrderIdx eq 1)}">style="color: blue;"</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>>기업 내부전문가</th>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.loginId}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberName}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberPsitn}" />
								</c:if>
							</c:forEach></td>
						</td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberOfcps}" />
								</c:if>
							</c:forEach></td>
						<td class=""><c:forEach var="member"
								items="${changeCnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberTelno}" />
								</c:if>
							</c:forEach></td>

						<td class=""><c:out value="-" /></td>
						<td class=""><c:out value="-" /></td>
						<td class=""><c:out value="-" /></td>

					</tr>


				</tbody>
			</table>
		</div>
	</div>

	<div class="contents-box pl0">
		<div class="table-type02 horizontal-scroll">
			<table class="width-type02" id="attachedTable">
				<colgroup>
					<col width="20%">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="2">첨부파일</th>
					</tr>
					<tr>
						<th scope="col">구분</th>
						<th scope="col">경력증명서</th>
					</tr>

					<tr class="pmFile" id="pmId_file">
						<td class="" <c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq 'pmFile'}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq 'pmFile' or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>컨설팅 책임자(PM)</td>
						<td class="left"><c:forEach var="file"
								items="${changeCnsl.cnslFiles}">
								<c:if test="${file.itemId eq 'pmFile'}">
									<strong class="point-color01"> ${file.fileOriginName}</strong>
									<a
										href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
										class="btn-linked"> <img
										src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
									</a>
								</c:if>
							</c:forEach></td>
					</tr>

					<tr class="exExpertFile" id="exExpertId_01_file">
						<td class="" <c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq 'exFile_01'}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq 'exFile_01' or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>외부 내용전문가</td>
						<td class="left"><c:forEach var="file"
								items="${changeCnsl.cnslFiles}">
								<c:if test="${file.itemId eq 'exFile_01'}">
									<strong class="point-color01"> ${file.fileOriginName}
									</strong>
									<a
										href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
										class="btn-linked"> <img
										src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
									</a>
								</c:if>
							</c:forEach></td>
					</tr>

					<tr class="inExpertFile" id="inExpertId_01_file">
						<td class="" <c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq 'inFile_01'}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq 'inFile_01' or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>기업 내부전문가</td>
						<td class="left"><c:forEach var="file"
								items="${changeCnsl.cnslFiles}">
								<c:if test="${file.itemId eq 'inFile_01'}">
									<strong class="point-color01"> ${file.fileOriginName}
									</strong>
									<a
										href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
										class="btn-linked"> <img
										src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
									</a>
								</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td class="" 
						<c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq 'etcFile'}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq 'etcFile' or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>기타</td>
						<td class="left">
						<c:forEach
									var="file" items="${changeCnsl.cnslFiles}">
									<c:if test="${file.itemId eq 'etcFile'}">
						<strong class="point-color01"> ${file.fileOriginName}
						</strong> <a
							href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
							class="btn-linked"> <img
								src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
						</a>
						</c:if></c:forEach></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="contents-box pl0">
		<div class="table-type02 horizontal-scroll">
			<table class="width-type02">
				<colgroup>
					<col width="20%">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<td colspan="2">
							<div class="sign-wrapper">
								<div class="sign-name">
									<p style="font-size: 20px;">위와 같이 과정개발 지원을 신청합니다.</p>
									<p style="font-size: 20px;">
										<fmt:formatDate value="${changeCnsl.regiDate}" pattern="yyyy년 MM월 dd일"/>
									</p>
								</div>
								<strong>한국산업인력공단 이사장 귀하</strong>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</form>
</div>

	<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="changeReject-modal">
	<h2>반려의견 조회</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper" id="contentsWrapper2">

			</div>
			<div class="btns-area">
				<button type="button" id="closeBtn_05"
					onclick="closeModal('changeReject-modal')"
					class="btn-m02 btn-color02 three-select">
					<span> 닫기 </span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- //CMS 끝 -->

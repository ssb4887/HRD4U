
<!-- CMS 시작 -->

<div class="contents-area pl0">
		<h3 class="title-type01 ml0">맞춤 훈련과정 개발 신청서</h3>
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
							<th scope="row" rowspan="2">훈련과정 유형</th>
							<th scope="row">사업주훈련</th>
							<th scope="row">일반직무전수 OJT</th>
							<th scope="row">과제수행 OJT</th>
						</tr>
						<tr>
							<td class="left">
								<div class="input-radio-area">
									<input type="radio" id="radio0901" name="cnslType" value="1"
										class="radio-type01"
										onclick="event.preventDefault()" <c:if test="${cnsl.cnslType eq 1}">checked</c:if>> 
										<label for="radio0101"></label>
								</div>
							</td>
							<td class="left">
								<div class="input-radio-area">
											<input type="radio" id="radio0902" name="cnslType" value="2"
												class="radio-type01"
												<c:if test="${cnsl.cnslType eq 2}">checked</c:if>
												onclick="event.preventDefault()">
											<label for="radio0102"></label>
								</div>
							</td>
							<td class="left">
								<div class="input-radio-area">
											<input type="radio" id="radio0903" name="cnslType" value="3"
												class="radio-type01"
												<c:if test="${cnsl.cnslType eq 3}">checked</c:if>
												onclick="event.preventDefault()">
											<label for="radio0103"></label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">훈련직무 및 개발 요구사항</th>
							<td class="left" colspan="3"><c:out
									value="${cnsl.cnslDemandMatter}" /></td>

						</tr>
						<tr>
							<th scope="row">직무분류</th>
							<td class="left" colspan="3">
								<c:out value="${cnsl.dtyClNm}" />
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
							<td class="left"><c:out value="${cnsl.corpNm}" /></td>
							<th scope="row">대표자명</th>
							<td class="left"><c:out value="${cnsl.reperNm}" /></td>
						</tr>
						<tr>
							<th scope="row">사업자등록번호</th>
							<td class="left"><c:out value="${cnsl.bizrNo}" /></td>
							<th scope="row">고용보험관리번호</th>
							<td class="left"><c:out value="${cnsl.bplNo}" /></td>
						</tr>
						<tr>
							<th scope="row">업종</th>
							<td colspan="3" class="left"><c:out value="${cnsl.indutyNm}" /></td>
						</tr>
						<tr>
							<th scope="row">주소</th>
							<td class="left"><c:out value="${cnsl.bplAddr}" /></td>
							<th scope="row">상시근로자수</th>
							<td class="left"><c:out value="${cnsl.totWorkCnt}" /></td>
						</tr>
						<tr>
							<th scope="row" rowspan="4">훈련실시주소</th>
							<td class="left" rowspan="4">
								<div class=" flex-box"><c:out value="${cnsl.trOprtnRegionZip}" /></div>
								<div class=" flex-box"><c:out value="${cnsl.trOprtnRegionAddr}" /></div>
								<div class=" flex-box"><c:out value="${cnsl.trOprtnRegionAddrDtl}" /></div>
							</td>
						</tr>
						<tr>
							<th scope="row">추천기관명</th>
							<td class="left">
								<c:out value="${cnsl.recomendInsttNm}" />
							</td>
						</tr>
						<tr>
							<th scope="row">관할 지부·지사</th>
							<td class="left"><c:out value="${cnsl.cmptncBrffcNm}" /></td>
						</tr>
						<tr>
							<th scope="row">연락처</th>
							<td class="left">
								<c:out value="${cnsl.cmptncBrffcPicTelno}"/>	
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
							<th scope="col" colspan="4">3. 담당자 정보</th>
						</tr>
						<tr>
							<th scope="row">성명</th>
							<td class="left"><c:out value="${cnsl.corpPicNm}" /></td>
							<th scope="row">직위</th>
							<td class="left"><c:out value="${cnsl.corpPicOfcps}" /></td>
						</tr>
						<tr>
							<th scope="row">전화번호</th>
							<td class="left"><c:out value="${cnsl.corpPicTelno}" /></td>
							<th scope="row">E-mail</th>
							<td class="left"><c:out value="${cnsl.corpPicEmail}" /></td>
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
					<col width="auto">
					<col width="auto">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="5">3. 컨설팅 팀 구성 및 개요</th>
					</tr>
					<tr>
						<th scope="row">컨설팅 팀</th>
						<td class="">HRD4U ID</td>
						<td class="" colspan="2">성명</td>
						<td class="">HRD4U 인력풀</td>
					</tr>
					<tr>
						<th scope="row">컨설팅 책임자 (PM)</th>
						<td class="mberIdTd"><c:forEach var="member"
								items="${cnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.loginId}" />
								</c:if>
							</c:forEach></td>
						<td class="mberName" colspan="2"><c:forEach var="member"
								items="${cnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberName}" />
								</c:if>
							</c:forEach></td>

						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pm_isHrpY" name="radio03" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="pm_isHrpN" name="radio03" value=""
										class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
					</tr>

					<tr>
						<th scope="row">외부 내용전문가</th>
						<td class="mberIdTd"><c:forEach var="member"
								items="${cnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.loginId}" />
								</c:if>
							</c:forEach></td>
						<td class="" colspan="2"><c:forEach var="member"
								items="${cnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberName}" />
								</c:if>
							</c:forEach></td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpY" name="radio05"
										value="" class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpN" name="radio05"
										value="" class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row">기업 내부전문가
						</th>
						<td class="">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.loginId}"/></c:if></c:forEach></td>
						<td class="" colspan="3">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberName}"/></c:if></c:forEach></td>
					
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
						<th scope="col" colspan="2">첨부파일</th>
					</tr>
					<tr>
						<th scope="col">구분</th>
						<th scope="col">경력증명서</th>
					</tr>

					<tr>
						<td class="">컨설팅 책임자(PM)</td>
						<td class="left">
							<c:forEach var="file" items="${cnsl.cnslFiles}"> <c:if test="${file.itemId eq 'pmFile'}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if></c:forEach>
						</td>
					</tr>
					
					<tr>
						<td class="">외부 내용전문가</td>
						<td class="left">
							<c:forEach var="file" items="${cnsl.cnslFiles}"> <c:if test="${file.itemId eq 'exFile_01'}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if></c:forEach>
						</td>
					</tr>					
						<tr>
						<td class="">기업 내부전문가</td>
						<td class="left">
						<c:forEach var="file" items="${cnsl.cnslFiles}"> <c:if test="${file.itemId eq 'inFile_01'}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if></c:forEach>
						</td>
					</tr>

					<tr>
						<td class="">기타</td>
						<td class="left">
						<c:forEach var="file" items="${cnsl.cnslFiles}">
							<c:if test="${file.itemId eq 'etcFile'}">
						<strong class="point-color01"> ${file.fileOriginName}
						</strong> <a
							href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
							class="btn-linked"> <img
								src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
						</a>
						</c:if>
								</c:forEach>
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
											<c:out value="${year}"/>년 <c:out value="${month}"/>월 <c:out value="${day}"/>일
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




</div>


<!-- //CMS 끝 -->

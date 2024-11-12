
<!-- CMS 시작 -->

<div class="contents-area pl0">
	<h3 class="title-type01 ml0">
		<c:choose>
							<c:when test="${cnsl.cnslType eq 4}">심층진단 신청서</c:when>
							<c:when test="${cnsl.cnslType eq 5}">훈련체계수립 신청서</c:when>
							<c:when test="${cnsl.cnslType eq 6}">현장활용 신청서</c:when>
						</c:choose>	
	</h3>

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
						<th scope="col" colspan="4">1. 신청기업 현황</th>
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
					<th scope="row">컨설팅 종류</th>
						<td class="left">
						<c:choose>
							<c:when test="${cnsl.cnslType eq 4}">심층진단</c:when>
							<c:when test="${cnsl.cnslType eq 5}">훈련체계수립</c:when>
							<c:when test="${cnsl.cnslType eq 6}">현장활용</c:when>
						</c:choose>			
						</td>
						<th scope="row">업종</th>
						<td class="left"><c:out value="${cnsl.indutyNm}" /></td>
					</tr>
					<tr>
						<th scope="row">주소</th>
						<td class="left"><c:out value="${cnsl.bplAddr}" /></td>
						<th scope="row">상시근로자수</th>
						<td class="left"><c:out value="${cnsl.totWorkCnt}명" /></td>
					</tr>
					<tr>
						<th scope="row">컨설팅 실시주소</th>
						<td colspan="3" class="left">
							<c:out value="${cnsl.trOprtnRegionZip}"/> 
							<c:out value="${cnsl.trOprtnRegionAddr}"/>
							<c:out value="${cnsl.trOprtnRegionAddrDtl}"/>
							</td>

					</tr>
					<tr>
						<th scope="row">관할 지부·지사</th>
						<td class="left"><c:out value="${cnsl.cmptncBrffcNm}" /></td>
						<th scope="row">추천기관명</th>
						<td class="left"><c:out value="${cnsl.recomendInsttNm}" /></td>
					</tr>
					<tr>
						<th scope="row">지원센터</th>
						<td class="left"><c:out value="${cnsl.spnt}" /></td>

						<th scope="row">연락처</th>
						<td class="left"><c:out value="${cnsl.spntTelno}" /></td>
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
						<th scope="col" colspan="4">2. 담당자 정보</th>
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
					<tr>
						<th scope="row">컨설팅 요구사항</th>
						<td class="left" colspan="3"><c:out
								value="${cnsl.cnslDemandMatter}" /></td>
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

					<c:forEach var="member" items="${cnsl.cnslTeam.members}"
						varStatus="status">
						<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
							<tr>
								<th scope="row">외부 내용전문가${member.teamOrderIdx}</th>
								<td class="mberIdTd"><c:out value="${member.loginId}" /></td>
								<td class="mberName" colspan="2"><c:out
										value="${member.mberName}" /></td>
								<td class="checkHrpRadioTd">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio"
												id="exExpertId_0${member.teamOrderIdx}_isHrpY" name=""
												value="" class="radio-type01"
												onclick="event.preventDefault()"> <label
												for="radio0101"> Y </label>
										</div>

										<div class="input-radio-area">
											<input type="radio"
												id="exExpertId_0${member.teamOrderIdx}_isHrpN" name=""
												value="" class="radio-type01"
												onclick="event.preventDefault()"> <label
												for="radio0102"> N </label>
										</div>
									</div>
								</td>
							</tr>

						</c:if>
					</c:forEach>

					<tr>
						<th scope="row">기업 내부전문가</th>
						<td class=""><c:forEach var="member"
								items="${cnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.loginId}" />
								</c:if>
							</c:forEach></td>
						<td class="" colspan="3"><c:forEach var="member"
								items="${cnsl.cnslTeam.members}">
								<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">
									<c:out value="${member.mberName}" />
								</c:if>
							</c:forEach></td>

					</tr>

					<c:forEach var="member" items="${cnsl.cnslTeam.members}"
						varStatus="status">
						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
							<tr>
								<th scope="row">기업 내부전문가${member.teamOrderIdx} <c:if
										test="${member.teamOrderIdx eq 1}">
									</c:if>
								</th>
								<td class=""><c:out value="${member.loginId}" /></td>
								<td class="" colspan="3"><c:out value="${member.mberName}" /></td>
							</tr>
						</c:if>


					</c:forEach>
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
							<c:forEach var="file" items="${cnsl.cnslFiles}">
									<c:if test="${file.itemId eq 'pmFile'}">
						<strong class="point-color01"> ${file.fileOriginName}
						</strong> <a href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
							class="btn-linked"> <img
								src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
						</a>
						</c:if></c:forEach>
						</td>
					</tr>

					<tr>
						<td class="">외부 내용전문가</td>
						<td class="left">
						<c:forEach var="file" items="${cnsl.cnslFiles}">
									<c:if test="${file.itemId eq 'exFile_01'}">
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

					<c:forEach var="member" items="${cnsl.cnslTeam.members}"
						varStatus="status">
						<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
							<c:set value="exFile_0${member.teamOrderIdx}" var="exFile" />
							<tr>
								<td class="">외부 내용전문가${member.teamOrderIdx}</td>
								<td class="left">
									<c:forEach var="file" items="${cnsl.cnslFiles}">
										<c:if test="${file.itemId eq exFile}">
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
						</c:if>
					</c:forEach>

					<tr>
						<td class="">기업 내부전문가</td>
						<td class="left">
							<c:forEach var="file" items="${cnsl.cnslFiles}">
									<c:if test="${file.itemId eq 'inFile_01'}">
						<strong class="point-color01"> ${file.fileOriginName}
						</strong> <a
							href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
							class="btn-linked"> <img
								src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
						</a>
						</c:if></c:forEach>
						</td>
					</tr>



					<c:forEach var="member" items="${cnsl.cnslTeam.members}"
						varStatus="status">

						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
							<c:set value="inFile_0${member.teamOrderIdx}" var="inFile" />
							<tr>
								<td class="">기업 내부전문가${member.teamOrderIdx}</td>
								<td class="left">
									<c:forEach var="file" items="${cnsl.cnslFiles}">
											<c:if test="${file.itemId eq inFile}">
								<strong class="point-color01"> ${file.fileOriginName}</strong> 
								<a href="${contextPath}/${siteId}/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
									class="btn-linked"> <img
										src="${contextPath}/${siteId}/images/icon/icon_search04.png" alt="" />
								</a>
								</c:if>
								</c:forEach>
								</td>
							</tr>
						</c:if>
					</c:forEach>

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
									<p style="font-size: 20px;">위와 같이 컨설팅을 신청합니다.</p>
									<p style="font-size: 20px;">
										<c:out value="${year}" />
										년
										<c:out value="${month}" />
										월
										<c:out value="${day}" />
										일
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
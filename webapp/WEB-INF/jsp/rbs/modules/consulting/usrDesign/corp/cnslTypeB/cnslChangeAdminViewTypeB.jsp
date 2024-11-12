
<!-- CMS 시작 -->

<div class="contents-area pl0">
	<h3 class="title-type01 ml0">심화컨설팅 변경 신청서 
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
					<col width="15%">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="col" colspan="4">1. 신청기업 현황</th>
					</tr>
					<tr>
						<th scope="row">기업명</th>
						<td class="left" <c:if test="${cnsl.corpNm ne changeCnsl.corpNm}">style="color: blue;"</c:if>><c:out value="${changeCnsl.corpNm}" /></td>
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
						<td colspan="3" class="left"><c:out value="${changeCnsl.indutyNm}" /></td>
					</tr>
					<tr>
						<th scope="row">주소</th>
						<td class="left"><c:out value="${changeCnsl.bplAddr}" /></td>
						<th scope="row">상시근로자수</th>
						<td class="left"><c:out value="${changeCnsl.totWorkCnt}" /></td>
					</tr>
					<tr>
						<th scope="row">컨설팅 실시주소</th>
						<td colspan="3" class="left">
						<c:out value="${changeCnsl.trOprtnRegionZip}" />
						<c:out value="${changeCnsl.trOprtnRegionAddr}" />
						<c:out value="${changeCnsl.trOprtnRegionAddrDtl}" />
						</td>

					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.cmptncBrffcNm ne changeCnsl.cmptncBrffcNm}">style="color: blue;"</c:if>>관할 지부·지사</th>
						<td class="left"><c:out value="${changeCnsl.cmptncBrffcNm}" /></td>
						<th scope="row" <c:if test="${cnsl.recomendInsttNm ne changeCnsl.recomendInsttNm}">style="color: blue;"</c:if>>추천기관명</th>
						<td class="left">${changeCnsl.recomendInsttNm}</td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.spnt ne changeCnsl.spnt}">style="color: blue;"</c:if>>지원센터</th>
						<td class="left"><c:out value="${changeCnsl.spnt}" /></td>

						<th scope="row" <c:if test="${cnsl.spntTelno ne changeCnsl.spntTelno}">style="color: blue;"</c:if>>연락처</th>
						<td class="left"><c:out value="${changeCnsl.spntTelno}" /></td>
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
						<th scope="row" <c:if test="${cnsl.corpPicNm ne changeCnsl.corpPicNm}">style="color: blue;"</c:if>>성명</th>
						<td class="left"><c:out value="${changeCnsl.corpPicNm}"/></td>
						<th scope="row" <c:if test="${cnsl.corpPicOfcps ne changeCnsl.corpPicOfcps}">style="color: blue;"</c:if>>직위</th>
						<td class="left"><c:out value="${changeCnsl.corpPicOfcps}"/></td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.corpPicTelno ne changeCnsl.corpPicTelno}">style="color: blue;"</c:if>>전화번호</th>
						<td class="left"><c:out value="${changeCnsl.corpPicTelno}"/></td>
						<th scope="row" <c:if test="${cnsl.corpPicEmail ne changeCnsl.corpPicEmail}">style="color: blue;"</c:if>>E-mail</th>
						<td class="left"><c:out value="${changeCnsl.corpPicEmail}"/></td>
					</tr>
					<tr>
						<th scope="row" <c:if test="${cnsl.cnslDemandMatter ne changeCnsl.cnslDemandMatter}">style="color: blue;"</c:if>>컨설팅 요구사항</th>
						<td class="left" colspan="3">
							<c:out value="${changeCnsl.cnslDemandMatter}"/>
						</td>
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
						<c:forEach var="chagneCnslmember" items="${changeCnsl.cnslTeam.members}"><c:if test="${chagneCnslmember.teamIdx eq 1 and chagneCnslmember.teamOrderIdx eq 1}">
							<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}">
							<c:if test="${cnslMember.teamIdx eq 1 and cnslMember.teamOrderIdx eq 1}">
								<c:if test="${chagneCnslmember.loginId ne cnslMember.loginId}">style="color: blue;"</c:if>
							</c:if>
							<c:if test="${not (cnslMember.teamIdx eq 1 and cnslMember.teamOrderIdx eq 1)}">style="color: blue;"</c:if>
						</c:forEach>
						</c:if></c:forEach>>컨설팅 책임자 (PM)</th>
						<td class="mberIdTd">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}"><c:out value="${member.loginId}"/></c:if></c:forEach>
							</td>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}"><c:out value="${member.mberName}"/></c:if></c:forEach>
						</td>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}"><c:out value="${member.mberPsitn}"/></c:if></c:forEach>
						</td>
						
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}"><c:out value="${member.mberOfcps}"/></c:if></c:forEach>
						</td>
						
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}"><c:out value="${member.mberTelno}"/></c:if></c:forEach>
						</td>
						
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pmId_suitableY" name="radio0201" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0201"> Y </label>

								</div>

								<div class="input-radio-area">
									<input type="radio" id="pmId_suitableN" name="radio0202" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0202"> N </label>
								</div>
							</div>
						</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pmId_isHrpY" name="radio0301" value="" class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0301"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="pmId_isHrpN" name="radio0302" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0302"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd">
							<input type="text" id="" name="" value="" class="w100" readOnly></td>
						</td>
					</tr>
					
					<tr class="exExpert" id="exExpert_01">
						<th scope="row" <c:forEach var="changeCnslmember" items="${changeCnsl.cnslTeam.members}"><c:if test="${chagneCnslmember.teamIdx eq 2 and chagneCnslmember.teamOrderIdx eq 1}">
							<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}"><c:if test="${cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx eq 1}">
							<c:if test="${changeCnslmember.loginId ne cnslMember.loginId}">style="color: blue;"</c:if>
						</c:if>
							<c:if test="${not (cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx eq 1)}">style="color: blue;"</c:if>
						</c:forEach>
						</c:if></c:forEach>>외부 내용전문가</th>
						<td class="mberIdTd">
						 	<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}"><c:out value="${member.loginId}"/></c:if></c:forEach></td>
						<td class="">
						 	<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}"><c:out value="${member.mberName}"/></c:if></c:forEach>
						</td>
						
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}"><c:out value="${member.mberPsitn}"/></c:if></c:forEach>
						</td>	
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}"><c:out value="${member.mberOfcps}"/></c:if></c:forEach>
						</td>
						<td class="">	
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}"><c:out value="${member.mberTelno}"/></c:if></c:forEach>
						</td>	
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpert_01_suitableY" name="radio04" value=""
										class="radio-type01" onclick="event.preventDefault()"> 
										<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpert_01_suitableN" name="radio04" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">
										N </label>
								</div>
							</div>
						</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpY" name="radio0501" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0501"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpN" name="radio0502" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0502"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd">
							<input type="text" id="" name="" value="" class="w100" readOnly></td>
						</td>
						
					</tr>

					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
						<tr class ="exExpert" id="exExpert_0${member.teamOrderIdx}">
						<th scope="row" <c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}"> 
								
								<c:if test="${cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx ne 1}">
								<c:if test="${member.loginId ne cnslMember.loginId}">style="color: blue;"</c:if>
								
								</c:if>
								<c:if test="${not (cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx ne 1)}">style="color: blue;"</c:if>
								</c:forEach>>외부 내용전문가${member.teamOrderIdx}
						</th>
						<td class="mberIdTd">
							<c:out value="${member.loginId}"/>
						</td>
						<td class="">
						 	<c:out value="${member.mberName}"/>
						</td>
						<td class="">
							<c:out value="${member.mberPsitn}"/>
						</td>
						<td class="">	
							<c:out value="${member.mberOfcps}"/>
						</td>
						<td class="">	
							<c:out value="${member.mberTelno}"/>
						</td>
							
							
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpert_0${member.teamOrderIdx}_suitableY" value="" class="radio-type01" onclick="event.preventDefault()">  <label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpert_0${member.teamOrderIdx}_suitableN" value="" class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">N </label>
								</div>
							</div>
						</td>
							
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${member.teamOrderIdx}_isHrpY" name="" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${member.teamOrderIdx}_isHrpN" name="" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd">
							<input type="text" id="" name="" value="" class="w100" readOnly></td>
						</td>
					</tr>
					
						</c:if>
					</c:forEach>
					
					<tr class="inExpert" id="inExpert_01">
						<th scope="row" <c:forEach var="changeCnslmember" items="${changeCnsl.cnslTeam.members}"><c:if test="${chagneCnslmember.teamIdx eq 3 and chagneCnslmember.teamOrderIdx eq 1}">
							<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}">
							<c:if test="${cnslMember.teamIdx eq 3 and cnslMember.teamOrderIdx eq 1}">
							<c:if test="${(changeCnslmember.loginId ne cnslMember.loginId)}">style="color: blue;"</c:if>
						</c:if>
						<c:if test="${not (cnslMember.teamIdx eq 3 and cnslMember.teamOrderIdx eq 1)}">style="color: blue;"</c:if>
						</c:forEach>
						</c:if></c:forEach>>기업 내부전문가
						</th>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.loginId}"/></c:if></c:forEach></td>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberName}"/></c:if></c:forEach></td>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberPsitn}"/></c:if></c:forEach></td>	
						</td>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberOfcps}"/></c:if></c:forEach>				
						</td>
						<td class="">
							<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberTelno}"/></c:if></c:forEach>
						</td>
						
						<td class="">
							<c:out value="-" />
						</td>
						<td class="">
							<c:out value="-" />
						</td>
						<td class="">
							<c:out value="-"/>
						</td>
					
					</tr>

					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"
						varStatus="status">
						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
							<tr class="inExpert" id="inExpert_0${member.teamOrderIdx}">
								<th scope="row" 
								<c:forEach var="cnslMember" items="${cnsl.cnslTeam.members}"> 
								
								<c:if test="${cnslMember.teamIdx eq 3 and cnslMember.teamOrderIdx ne 1}">
								<c:if test="${(member.loginId ne cnslMember.loginId)}">style="color: blue;"</c:if>
								</c:if>
								<c:if test="${not (cnslMember.teamIdx eq 2 and cnslMember.teamOrderIdx ne 1)}">style="color: blue;"</c:if>
								</c:forEach>>기업 내부전문가${member.teamOrderIdx}</th>
								<td class="">
									<c:out value="${member.loginId}"/>
								</td>
								<td class="">
									<c:out value="${member.mberName}"/>
								</td>
								<td class="">
									<c:out value="${member.mberPsitn}" />
								</td>
								<td class="">
									<c:out value="${member.mberOfcps}" />
								</td>
								<td class="">
									<c:out value="${member.mberTelno}" />
								</td>
								<td class="">
									<c:out value="-" />
								</td>
								<td class="">
									<c:out value="-" />
								</td>
								<td class="">
									<c:out value="-"/>
								</td>
							</tr>
						</c:if>


					</c:forEach>
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

					<tr class="pm" id="pmId_file">
						<td class="" 
							<c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq 'pmFile'}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq 'pmFile' or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>
							컨설팅 책임자(PM)</td>
						<td class="left">
							<c:forEach var="file" items="${changeCnsl.cnslFiles}"> <c:if test="${file.itemId eq 'pmFile'}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if>
							</c:forEach>
						</td>
					</tr>
					
					<tr class="exExpertFile" id="exExpertId_01_file">
						<td class="" 
							<c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq 'exFile_01'}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq 'exFile_01' or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>외부 내용전문가</td>
						<td class="left">
							<c:forEach var="file" items="${changeCnsl.cnslFiles}"> <c:if test="${file.itemId eq 'exFile_01'}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if>
							</c:forEach>
						</td>
					</tr>
					
					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
					<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
					<c:set value="exFile_0${member.teamOrderIdx}" var="exFile" />
					<tr id="exExpertId_0${member.teamOrderIdx}_file">
						<td class="" 
							<c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq exFile}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq exFile or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>외부 내용전문가${member.teamOrderIdx}</td>
						<td class="left">
							<c:forEach var="file" items="${changeCnsl.cnslFiles}"> <c:if test="${file.itemId eq exFile}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if></c:forEach>
						</td>
					</tr>
					</c:if>
					</c:forEach>
						
						
						
						<tr id="inExpertId_01_file">
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
						<td class="left">
							<c:forEach var="file" items="${changeCnsl.cnslFiles}"> <c:if test="${file.itemId eq 'inFile_01'}">
							<strong class="point-color01"> 
								${file.fileOriginName}
							</strong> 
							<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
								class="btn-linked"> 
								<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
							</a>
							</c:if>
							</c:forEach>
						</td>
					</tr>
	
					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
						<c:set value="inFile_0${member.teamOrderIdx}" var="inFile" />
							<tr id="inExpertId_0${member.teamOrderIdx}_file">
								<td class="" 	<c:forEach var="changeFile" items="${changeCnsl.cnslFiles}"> 
						<c:if test="${changeFile.itemId eq inFile}">
						<c:forEach var="cnslFile" items="${cnsl.cnslFiles}"> 
						<c:if test="${cnslFile.itemId eq inFile or empty cnslFile.itemId}">
						<c:if test="${cnslFile.fileOriginName ne changeFile.fileOriginName}">style="color: blue;"</c:if>
						<c:if test="${empty cnslFile.fileOriginName and !empty changeFile.fileOriginName}">style="color: blue;"</c:if>
						</c:if>
							</c:forEach>
						</c:if>
							</c:forEach>>기업 내부전문가${member.teamOrderIdx}</td>
								<td class="left">
								<c:forEach var="file" items="${changeCnsl.cnslFiles}">
											<c:if test="${file.itemId eq inFile}">
								<strong class="point-color01"> ${file.fileOriginName}</strong> 
								<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}" class="btn-linked"> 
								<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
								</a>
								</c:if>
								</c:forEach>
								</td>
							</tr>
						</c:if>
					</c:forEach>

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
							</c:forEach> >기타</td>
						<td class="left">
							<c:forEach var="file" items="${changeCnsl.cnslFiles}">
									<c:if test="${file.itemId eq 'etcFile'}">
						<strong class="point-color01"> ${file.fileOriginName} </strong> 
						<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}" class="btn-linked"> 
						<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
						</a>
						</c:if></c:forEach>
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
	</div>
	
		<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="changeReject-modal">
	<h2>반려의견 조회</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper" id="contentsWrapper">

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
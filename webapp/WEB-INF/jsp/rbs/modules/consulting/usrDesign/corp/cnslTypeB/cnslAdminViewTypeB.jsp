
<!-- CMS 시작 -->

<div class="contents-area pl0">
	<form id="applyForm">
	<input type="hidden" id="cnslIdx" name="cnslIdx" value="${cnsl.cnslIdx}"> 
	<input type="hidden" id="bscIdx" name="bscIdx" value="${cnsl.bscIdx}"> 
	<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx" value="${cnsl.bsiscnslIdx}"> 
	<input type="hidden" id="cnslType" name="cnslType" value="${cnsl.cnslType}"> 
	<h3 class="title-type01 ml0">심화컨설팅 지원 신청서</h3>
	
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
						<th scope="row">컨설팅 실시주소</th>
						<td colspan="3" class="left">
						<c:out value="${cnsl.trOprtnRegionZip}" />
						<c:out value="${cnsl.trOprtnRegionAddr}" />
						<c:out value="${cnsl.trOprtnRegionAddrDtl}" />
					</td>

					</tr>
					<tr>
						<th scope="row">관할 지부·지사</th>
						<td class="left"><c:out value="${cnsl.cmptncBrffcNm}" /></td>
						<th scope="row">추천기관명</th>
						<td class="left"></td>
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
						
						<th scope="row">컨설팅 책임자 (PM)</th>
						<td class="mberIdTd">
							<input type="text" id="pmId" name="cnslTeam.members[0].loginId" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" onblur="hrpUserSearchHandler(this)" required></td>
						<td class="">
							<input type="text" id="pmId_name" name="cnslTeam.members[0].mberName" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100" required>
						</td>
						
							<input type="hidden" name="cnslTeam.members[0].rspnberYn" data-value="Y" value="Y">
							<input type="hidden" name="cnslTeam.members[0].teamIdx" data-value="1" value="1">
							<input type="hidden" name="cnslTeam.members[0].teamOrderIdx" data-value="1" value="1">
							<input type="hidden" id="pmId_memberIdx" name="cnslTeam.members[0].memberIdx" data-value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
						
						<td class="">
							<input type="text" id="pmId_psitn" name="cnslTeam.members[0].mberPsitn" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>" class="w100" required>
						</td>
						
						<td class="">
							<input type="text" id="pmId_ofcps" name="cnslTeam.members[0].mberOfcps" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>" class="w100" required>
						</td>
						
						<td class="">
							<input type="text" id="pmId_telno" name="cnslTeam.members[0].mberTelno" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>" class="w100" required>
						</td>
						
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pmId_suitableY" name="radio02" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0101"> Y </label>

								</div>

								<div class="input-radio-area">
									<input type="radio" id="pmId_suitableN" name="radio02" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="pmId_isHrpY" name="radio03" value="" class="radio-type01" onclick="event.preventDefault()">
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="pmId_isHrpN" name="radio03" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd">
							<input type="text" id="" name="" value="" class="w100"></td>
						</td>
					</tr>
					
					<tr class="exExpert" id="exExpert_01">
						<th scope="row">외부 내용전문가<br>
							<input type="checkbox" id="id_chk" onclick="toggleSelection()" class="checkbox-type01">
							<span id="toggleButton">
								<strong class="point-color01 right" >선택 안함</strong>
							</span>
							<div class="people-add-box">
								<button type="button" class="add" onclick="addExExpertByAdmin(this)">추가</button>
								<button type="button" class="delete" onclick="deleteExRow(this)">삭제</button>
							</div>
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_01" name="cnslTeam.members[1].loginId" onblur="hrpUserSearchHandler(this)" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" required></td>
						<td class="">
						 	<input type="text" id="exExpertId_01_name" name="cnslTeam.members[1].mberName" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100" required>
							<input type="hidden" name="cnslTeam.members[1].rspnberYn" data-value="N" value="N"> 
							<input type="hidden" name="cnslTeam.members[1].teamIdx" data-value="2" value="2"> 
							<input type="hidden" name="cnslTeam.members[1].teamOrderIdx" data-value="1" value="1">
							<input type="hidden" id="exExpertId_01_memberIdx" name="cnslTeam.members[1].memberIdx"  value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
							
						</td>
						
						<td class="">
							<input type="text" id="exExpertId_01_psitn" name="cnslTeam.members[1].mberPsitn" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>" class="w100" required>
						</td>	
						<td class="">
							<input type="text" id="exExpertId_01_ofcps" name="cnslTeam.members[1].mberOfcps" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>" class="w100" required>
						</td>
						<td class="">	
							<input type="text" id="exExpertId_01_telno" name="cnslTeam.members[1].mberTelno" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>" class="w100" required>
						</td>	
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_suitableY" name="radio04" value=""
										class="radio-type01" onclick="event.preventDefault()"> 
										<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_suitableN" name="radio04" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">
										N </label>
								</div>
							</div>
						</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpY" name="radio05" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_isHrpN" name="radio05" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0102"> N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd">
							<input type="text" id="" name="" value="" class="w100" readOnly></td>
						</td>
						
					</tr>

					<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
						<tr class ="exExpert" id="exExpert_0${member.teamOrderIdx}">
						<th scope="row">외부 내용전문가${member.teamOrderIdx}
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_0${member.teamOrderIdx}" name="cnslTeam.members[${member.teamOrderIdx}].loginId" onblur="hrpUserSearchHandler(this)" value="${member.loginId}" class="w100" required></td>
						<td class="">
						 <input type="text" id="exExpertId_0${member.teamOrderIdx}_name" name="cnslTeam.members[${member.teamOrderIdx}].mberName" value="${member.mberName}" class="w100" required>
							<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx}].rspnberYn" data-value="N" value="N"> 
							<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx}].teamIdx" data-value="2" value="2"> 
							<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx}].teamOrderIdx" data-value="${member.teamOrderIdx}" value="${member.teamOrderIdx}">
							<input  type="hidden" id="exExpertId_0${member.teamOrderIdx}_memberIdx" name="cnslTeam.members[${member.teamOrderIdx}].memberIdx" data-value="${member.memberIdx}" value="${member.memberIdx}">
						</td>
						<td class="">
							<input  type="text" id="exExpertId_0${member.teamOrderIdx}_psitn" name="cnslTeam.members[${member.teamOrderIdx}].mberPsitn" value="${member.mberPsitn}"  class="w100" required>
						</td>
						<td class="">	
							<input  type="text" id="exExpertId_0${member.teamOrderIdx}_ofcps" name="cnslTeam.members[${member.teamOrderIdx}].mberOfcps" value="${member.mberOfcps}"  class="w100" required>
						</td>
						<td class="">	
							<input  type="text" id="exExpertId_0${member.teamOrderIdx}_telno" name="cnslTeam.members[${member.teamOrderIdx}].mberTelno" value="${member.mberTelno}"  class="w100" required>
						</td>
							
							
						<td class="checkSuitableRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="radio0101" name="radio04" value="" class="radio-type01" onclick="event.preventDefault()">  <label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="radio0102" name="radio04" value="" class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">N </label>
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
							<input type="text" id="" name="" value="" class="w100"></td>
						</td>
					</tr>
					
						</c:if>
					</c:forEach>
					
					<tr class="inExpert" id="inExpert_01">
						<th scope="row">기업 내부전문가
						</th>
						<td class="">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.loginId}"/></c:if></c:forEach></td>
						<td class="">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberName}"/></c:if></c:forEach></td>
						<td class="">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberPsitn}"/></c:if></c:forEach></td>	
						</td>
						<td class="">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberOfcps}"/></c:if></c:forEach>				
						</td>
						<td class="">
							<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}"><c:out value="${member.mberTelno}"/></c:if></c:forEach>
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

					<c:forEach var="member" items="${cnsl.cnslTeam.members}"
						varStatus="status">
						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
							<tr class="inExpert" id="inExpert_0${member.teamOrderIdx}">
								<th scope="row">기업 내부전문가${member.teamOrderIdx} <c:if
										test="${member.teamOrderIdx eq 1}">
									</c:if>
								</th>
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

					<tr class="pmFile" id="pmId_file">
						<td class="">컨설팅 책임자(PM)</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_01" class="fileName"
									placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'pmFile'}">${file.fileOriginName}</c:if></c:forEach>" required> <label
									for="pmFile" class="btn_file">찾아보기</label> 
									<input type="file" id="pmFile" name="pmFile" class="uploadBtn" onchange="javascript:document.getElementById('fileName_01').value = this.value">
							</div>
						</td>
					</tr>
					
					<tr class="exExpertFile" id="exExpertId_01_file">
						<td class="">외부 내용전문가</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_02" class="fileName"
									placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'exFile_01'}">${file.fileOriginName}</c:if></c:forEach>" required> <label
									for="exFile_01" class="btn_file">찾아보기</label> <input
									type="file" id="exFile_01" name="exFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_02').value = this.value">
							</div>
						</td>
					</tr>
					
					<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
					<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
					<c:set value="exFile_0${member.teamOrderIdx}" var="exFile" />
					<tr class ="exExpert" id="exExpertId_0${member.teamOrderIdx}_file">
						<td class="">외부 내용전문가${member.teamOrderIdx}</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${member.teamOrderIdx+1}" class="fileName"
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq exFile}">${file.fileOriginName}</c:if></c:forEach>"> <label
									for="exFile_0${member.teamOrderIdx}" class="btn_file">찾아보기</label> <input
									type="file" id="exFile_0${member.teamOrderIdx}" name="exFile_0${member.teamOrderIdx}" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_0${member.teamOrderIdx+1}').value = this.value">
							</div>
						</td>
					</tr>
					</c:if>
					</c:forEach>
						
						
						
						<tr class="inExpert" id="inExpertId_01_file">
						<td class="">기업 내부전문가</td>
						<td class="left">
							<strong class="point-color01"> 
								<c:forEach var="file" items="${cnsl.cnslFiles}"> <c:if test="${file.itemId eq 'inFile_01'}">${file.fileOriginName}</c:if></c:forEach>
							</strong> 
							<a href="${contextPath}/web/consulting/download.do?mId=85&fileName=<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'inFile_01'}">${file.fileSavedName}</c:if></c:forEach>"
								class="btn-linked"> 
								<img src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
							</a>
						</td>
					</tr>
					
						
						
						<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
						<c:set value="inFile_0${member.teamOrderIdx}" var="inFile" />
							<tr class ="inExpert" id="inExpertId_0${member.teamOrderIdx}_file">
								<td class="">기업 내부전문가${member.teamOrderIdx}</td>
								<td class="left"><strong class="point-color01"> <c:forEach
											var="file" items="${cnsl.cnslFiles}">
											<c:if test="${file.itemId eq inFile}">${file.fileOriginName}</c:if>
										</c:forEach>
								</strong> <a
									href="${contextPath}/web/consulting/download.do?mId=85&fileName=<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq inFile}">${file.fileSavedName}</c:if></c:forEach>"
									class="btn-linked"> <img
										src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
								</a></td>
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
							href="${contextPath}/web/consulting/download.do?mId=85&fileName=${file.fileSavedName}"
							class="btn-linked"> <img
								src="${contextPath}/web/images/icon/icon_search04.png" alt="" />
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
</form>
	</div>


	<!-- //CMS 끝 -->
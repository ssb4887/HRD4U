<!-- CMS 시작 -->

<div class="contents-area pl0">
	<form id="applyForm">
	<input type="hidden" id="cnslIdx" name="cnslIdx" value="${cnsl.cnslIdx}"> 
	<input type="hidden" id="bscIdx" name="bscIdx" value="${cnsl.bscIdx}"> 
	<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx" value="${cnsl.bsiscnslIdx}"> 
	<input type="hidden" id="cnslType" name="cnslType" value="${cnsl.cnslType}"> 
	<input type="hidden" id="cnslTme" name="cnslTme" value="${cnsl.cnslTme}"> 

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
						<td class="left"><input type="text" id="corpNm" name="corpNm"
							value="${cnsl.corpNm}" class="w100" readOnly></td>
						<th scope="row">대표자명</th>
						<td class="left"><input type="text" id="reperNm"
							name="reperNm" value="${cnsl.reperNm}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">사업자등록번호</th>
						<td class="left"><input type="text" id="bizrNo" name="bizrNo"
							value="${cnsl.bizrNo}" class="w100" readOnly></td>
						<th scope="row">고용보험관리번호</th>
						<td class="left"><input type="text" id="bplNo" name="bplNo"
							value="${cnsl.bplNo}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">업종</th>
						<td colspan="3" class="left"><input type="text" id="indutyNm"
							name="indutyNm" value="${cnsl.indutyNm}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">주소</th>
						<td class="left"><input type="text" id="bplAddr"
							name="bplAddr" value="${cnsl.bplAddr}" class="w100" readOnly></td>
						<th scope="row">상시근로자수</th>
						<td class="left"><input type="text" id="totWorkCnt"
							name="totWorkCnt" value="${cnsl.totWorkCnt}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">훈련실시주소  <strong class="point-important">*</strong></th>
						<td colspan="3" class="left">
							<input type="text" id="trOprtnRegionZip" name="trOprtnRegionZip" value="${cnsl.trOprtnRegionZip}" class="w50" placeholder="우편번호 입력" data-checkValidity="실시주소" readOnly required>
							<input type="button" class="btn-m03 btn-color01" onclick="findAddr()"" value="우편번호 찾기">
							<input type="text" id="trOprtnRegionAddr" name="trOprtnRegionAddr" value="${cnsl.trOprtnRegionAddr}" class="w100" placeholder="훈련실시주소" readOnly required>
							<input type="text" id="trOprtnRegionAddrDtl" name="trOprtnRegionAddrDtl" value="${cnsl.trOprtnRegionAddrDtl}" class="w100" placeholder="훈련실시상세주소" readOnly>
						</td>
					<tr>
					
					<tr>
						<th scope="row">관할 지부·지사</th>
						<td class="left">
							<input type="text" id="cmptncBrffcNm"  value="${cnsl.cmptncBrffcNm}" class="w100" required>
							<input type="hidden" id="cmptncBrffcIdx" name="cmptncBrffcIdx" value="${cnsl.cmptncBrffcIdx}" class="w100" required>
							
							</td>
						
						<th scope="row">추천기관명</th>
						<td class="left">
							<input type="text" name="recomendInsttNm" value="${cnsl.recomendInsttNm}" class="w100">
						</td>
					</tr>
					
					<tr>
						<th scope="row">지원센터</th>
						<td class="left">
							<select name="spntNm" class="w100" data-checkValidity="지원센터" required>
							<c:forEach var="center" items="${completedAgremList}">
								<option value="${center.PRVTCNTR_NO}" <c:if test="${center.PRVTCNTR_NO eq cnsl.spntNm}">selected</c:if>><c:out value="${center.PRVTCNTR_NAME}"/></option>
							</c:forEach>
							</select>	
						</td>
						
						<th scope="row">연락처</th>
						<td class="left"><input type="text" id="spntTelno" name="spntTelno"
							value="${cnsl.spntTelno}" class="w100" readOnly></td>
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
						<td class="left"><input type="text" id="corpPicNm"
							name="corpPicNm" value="${cnsl.corpPicNm}" class="w100" required></td>
						<th scope="row">직위</th>
						<td class="left"><input type="text" id="corpPicOfcps"
							name="corpPicOfcps" value="${cnsl.corpPicOfcps}" class="w100" required></td>
					</tr>
					<tr>
						<th scope="row">전화번호</th>
						<td class="left"><input type="text" id="corpPicTelno"
							name="corpPicTelno" value="${cnsl.corpPicTelno}" class="w100" maxlength="13" onblur="formatPhoneNumber(this);" required></td>
						<th scope="row">E-mail</th>
						<td class="left"><input type="text" id="corpPicEmail"
							name="corpPicEmail" value="${cnsl.corpPicEmail}" class="w100" required></td>
					</tr>
					
					<tr>
						<th scope="row">컨설팅 요구사항</th>
						<td class="left" colspan="3">
							<textarea id="textfield01" name="cnslDemandMatter" rows="5" class="w100 h100"><c:out value="${cnsl.cnslDemandMatter}"/></textarea>
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
						<th scope="col" colspan="1"><label for="teamCheck">컨설팅팀 추천</label><input type="checkbox" id="teamCheck" name="teamCheck" onchange="checkTeamCreationStatus()"></th>
						<th scope="col" colspan="4">4. 컨설팅 팀 구성 및 개요</th>
					</tr>
					<tr>
						<th scope="row">컨설팅 팀</th>
						<td class="">HRD4U ID</td>
						<td class="">성명</td>
						<td class="">적합여부</td>
						<td class="">HRD4U 인력풀</td>
					</tr>
					<tr class="pm">
						<th scope="row">컨설팅 책임자 (PM)</th>
						<td class="mberIdTd">
							<input type="text" id="pmId" name="cnslTeam.members[0].loginId" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" onblur="hrpUserSearchHandler(this)" requried></td>
						<td class="">
							<input type="text" id="pmId_name" name="cnslTeam.members[0].mberName" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100">
						</td>
						
						<input type="hidden" name="cnslTeam.members[0].rspnberYn" data-value="Y" value="Y">
						<input type="hidden" name="cnslTeam.members[0].teamIdx" data-value="1" value="1">
						<input type="hidden" name="cnslTeam.members[0].teamOrderIdx" data-value="1" value="1">
						<input type="hidden" id="pmId_memberIdx" name="cnslTeam.members[0].memberIdx" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
						<input type="hidden" id="pmId_psitn" name="cnslTeam.members[0].mberPsitn" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>">
						<input type="hidden" id="pmId_ofcps" name="cnslTeam.members[0].mberOfcps" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>">
						<input type="hidden" id="pmId_telno" name="cnslTeam.members[0].mberTelno" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>">
						
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
					</tr>
					
					<tr class="exExpert" id="exExpert_01">
						<th scope="row">외부 내용전문가<br> <br>
							<input type="checkbox" id="id_chk" onclick="toggleSelection()" class="checkbox-type01">
							<span id="toggleButton">
								<strong class="point-color01 right" >선택 안함</strong>
							</span><div class="people-add-box">
								<button type="button" class="add" onclick="addExExpert(this)">추가</button>
								<button type="button" class="delete" onclick="deleteExRow(this)">삭제</button>
							</div>
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_01" name="cnslTeam.members[1].loginId" onblur="hrpUserSearchHandler(this)" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" required></td>
						<td class="">
						 <input type="text" id="exExpertId_01_name" name="cnslTeam.members[1].mberName" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100" required readOnly></td>
						<td class="checkSuitableRadioTd">
							<input type="hidden" name="cnslTeam.members[1].rspnberYn" data-value="N" value="N"> 
							<input type="hidden" name="cnslTeam.members[1].teamIdx" data-value="2" value="2"> 
							<input type="hidden" name="cnslTeam.members[1].teamOrderIdx" data-value="1" value="1"> 
							<input type="hidden" id="exExpertId_01_memberIdx" name="cnslTeam.members[1].memberIdx" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
							<input type="hidden" id="exExpertId_01_psitn" name="cnslTeam.members[1].mberPsitn" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>">
							<input type="hidden" id="exExpertId_01_ofcps" name="cnslTeam.members[1].mberOfcps" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>">
							<input type="hidden" id="exExpertId_01_telno" name="cnslTeam.members[1].mberTelno" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>">
							
							
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
							</div></td>
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
					</tr>

					<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
						<tr class ="exExpert" id="exExpert_0${member.teamOrderIdx}">
						<th scope="row">외부 내용전문가${member.teamOrderIdx}
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_0${member.teamOrderIdx}" name="cnslTeam.members[${member.teamOrderIdx}].loginId" onblur="hrpUserSearchHandler(this)" value="${member.loginId}" class="w100" requried></td>
						<td class="">
						 <input type="text" id="exExpertId_0${member.teamOrderIdx}_name" name="cnslTeam.members[${member.teamOrderIdx}].mberName" value="${member.mberName}" class="w100" readOnly></td>
						
						<td class="checkSuitableRadioTd">
							<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx}].rspnberYn" data-value="N" value="N"> 
							<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx}].teamIdx" data-value="2" value="2"> 
							<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx}].teamOrderIdx" data-value="${member.teamOrderIdx}" value="${member.teamOrderIdx}">
							<input  type="hidden" id="exExpertId_0${member.teamOrderIdx}_memberIdx" name="cnslTeam.members[${member.teamOrderIdx}].memberIdx" value="${member.memberIdx}">
							<input  type="hidden" id="exExpertId_0${member.teamOrderIdx}_psitn" name="cnslTeam.members[${member.teamOrderIdx}].mberPsitn" value="${member.mberPsitn}">
							<input  type="hidden" id="exExpertId_0${member.teamOrderIdx}_ofcps" name="cnslTeam.members[${member.teamOrderIdx}].mberOfcps" value="${member.mberOfcps}">
							<input  type="hidden" id="exExpertId_0${member.teamOrderIdx}_telno" name="cnslTeam.members[${member.teamOrderIdx}].mberTelno" value="${member.mberTelno}">
							
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="radio0101" value="" class="radio-type01" onclick="event.preventDefault()">  <label for="radio0101"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="radio0102" value="" class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">N </label>
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
					</tr>
					
						</c:if>
					</c:forEach>
					
					<tr class="inExpert" id="inExpert_01">
						<th scope="row">기업 내부전문가
							<div class="people-add-box">
								<button type="button" class="add" onclick="addInExpert(this)">추가</button>
								<button type="button" class="delete" onclick="deleteInRow(this)">삭제</button>
								
							</div>
						</th>
						<td class="mberIdTd">
							<input type="text" id="inExpertId_01" name="cnslTeam.members[4].loginId" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" placeholder="HRD4U ID" 	onblur="userSearchHandler(this)" required></td>
						<td class="" colspan="2">
							<input type="text" id="inExpertId_01_name" name="cnslTeam.members[4].mberName" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100" placeholder="성명" required readOnly></td>
						<td class="">
							<input type="text" id="inExpertId_01_ofcps" name="cnslTeam.members[4].mberOfcps" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>" class="w100" placeholder="직위"></td>
							
							<input  type="hidden" name="cnslTeam.members[4].rspnberYn" data-value="N" value="N"> 
							<input  type="hidden" name="cnslTeam.members[4].teamIdx" data-value="3" value="3"> 
							<input  type="hidden" name="cnslTeam.members[4].teamOrderIdx" data-value="1" value="1">
							<input  type="hidden" id="inExpertId_01_memberIdx" name="cnslTeam.members[4].memberIdx" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
							<input  type="hidden" id="inExpertId_01_psitn" name="cnslTeam.members[4].mberPsitn" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>">
							<input  type="hidden" id="inExpertId_01_telno" name="cnslTeam.members[4].mberTelno" value="<c:forEach var="member" items="${cnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>">
					</tr>
					
					
					
					
					<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
							<tr class ="inExpert" id="inExpert_0${member.teamOrderIdx}">
								<th scope="row">기업 내부전문가${member.teamOrderIdx}
								</th>
								<td class="mberIdTd">
									<input type="text" id="inExpertId_0${member.teamOrderIdx}" name="cnslTeam.members[${member.teamOrderIdx +3}].loginId" value="${member.loginId}" class="w100" placeholder="HRD4U ID" 	onblur="userSearchHandler(this)"></td>
								<td class="" colspan="2">
									<input type="text" id="inExpertId_0${member.teamOrderIdx}_name" name="cnslTeam.members[${member.teamOrderIdx +3}].mberName" value="${member.mberName}" class="w100" placeholder="성명"></td>
								<td class="">
									<input type="text" id="inExpertId_0${member.teamOrderIdx}_ofcps" name="cnslTeam.members[${member.teamOrderIdx +3}].mberOfcps" value="${member.mberOfcps}" value="" class="w100" placeholder="직위"></td>
									
									<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx +3}].rspnberYn" data-value="N" value="N"> 
									<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx +3}].teamIdx" data-value="3" value="3"> 
									<input  type="hidden" name="cnslTeam.members[${member.teamOrderIdx +3}].teamOrderIdx" data-value="${member.teamOrderIdx}" value="${member.teamOrderIdx}">
									<input  type="hidden" id="inExpertId_0${member.teamOrderIdx}_memberIdx" name="cnslTeam.members[${member.teamOrderIdx +3}].mberPsitn" value="${member.mberPsitn}">
									<input  type="hidden" id="inExpertId_0${member.teamOrderIdx}_psitn" name="cnslTeam.members[${member.teamOrderIdx +3}].mberPsitn" value="${member.mberPsitn}">
									<input  type="hidden" id="inExpertId_0${member.teamOrderIdx}_ofcps" name="cnslTeam.members[${member.teamOrderIdx +3}].mberOfcps" value="${member.mberOfcps}">
									<input  type="hidden" id="inExpertId_0${member.teamOrderIdx}_telno" name="cnslTeam.members[${member.teamOrderIdx +3}].mberTelno" value="${member.mberTelno}">
									
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
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'pmFile'}">${file.fileOriginName}</c:if></c:forEach>" required> <label
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
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'exFile_01'}">${file.fileOriginName}</c:if></c:forEach>"> <label
									for="exFile_01" class="btn_file">찾아보기</label> <input
									type="file" id="exFile_01" name="exFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_02').value = this.value">
							</div>
						</td>
					</tr>
					
					<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
					<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
					<c:set value="exFile_0${member.teamOrderIdx}" var="exFile" />
					<tr id="exExpertId_0${member.teamOrderIdx}_file">
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
					
					<tr id="inExpertId_01_file">
						<td class="">기업 내부전문가</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_05" class="fileName"
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'inFile_01'}">${file.fileOriginName}</c:if></c:forEach>"> <label
									for="inFile_01" class="btn_file">찾아보기</label> <input
									type="file" id="inFile_01" name="inFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_05').value = this.value">
							</div>
						</td>
					</tr>
					
					
					
					<c:forEach var="member" items="${cnsl.cnslTeam.members}" varStatus="status">
					<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
					<c:set value="inFile_0${member.teamOrderIdx}" var="inFile" />
					<tr id="inExpertId_0${member.teamOrderIdx}_file">
						<td class="">기업 내부전문가${member.teamOrderIdx}</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${member.teamOrderIdx+4}" class="fileName"
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq inFile}">${file.fileOriginName}</c:if></c:forEach>"> <label
									for="inFile_0${member.teamOrderIdx}" class="btn_file">찾아보기</label> <input
									type="file" id="inFile_0${member.teamOrderIdx}" name="inFile_0${member.teamOrderIdx}" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_0${member.teamOrderIdx+4}').value = this.value">
							</div>
						</td>
					</tr>
					</c:if>
					</c:forEach>
					
					<tr>
						<td class="">기타</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_etc" class="fileName" readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${cnsl.cnslFiles}"><c:if test="${file.itemId eq 'etcFile'}">${file.fileOriginName}</c:if></c:forEach>"> 
									<label for="etcFile" class="btn_file">찾아보기</label> 
								<input type="file" id="etcFile" name="etcFile" class="uploadBtn" onchange="javascript:document.getElementById('fileName_etc').value = this.value">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="agree-check-box modal mt30 mb20">
         <p class="word-type03 mr15">정보 수집 및 이용에 동의합니다.</p>
         <div class="input-checkbox-wrapper">
             <div class="input-checkbox-area">
                 <input type="checkbox" id="consentCheckbox" name="checkbox01" value="" class="checkbox-type01" onclick="openModal('consentModal')">
                 <label for="checkbox0101">동의</label>
             </div>
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

</form>
</div>
<!-- //CMS 끝 -->

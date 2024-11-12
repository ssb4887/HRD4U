<!-- CMS 시작 -->

<div class="contents-area pl0">
	<form id="changeForm">
	<input type="hidden" id="cnslIdx" name="cnslIdx" value="${changeCnsl.cnslIdx}"> 
	<input type="hidden" id="bscIdx" name="bscIdx" value="${changeCnsl.bscIdx}"> 
	<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx" value="${changeCnsl.bsiscnslIdx}"> 
	<input type="hidden" id="cnslType" name="cnslType" value="${changeCnsl.cnslType}"> 

	<h3 class="title-type01 ml0">심화컨설팅 변경 신청서</h3>
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
							value="${changeCnsl.corpNm}" class="w100" readOnly></td>
						<th scope="row">대표자명</th>
						<td class="left"><input type="text" id="reperNm"
							name="reperNm" value="${changeCnsl.reperNm}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">사업자등록번호</th>
						<td class="left"><input type="text" id="bizrNo" name="bizrNo"
							value="${changeCnsl.bizrNo}" class="w100" readOnly></td>
						<th scope="row">고용보험관리번호</th>
						<td class="left"><input type="text" id="bplNo" name="bplNo"
							value="${changeCnsl.bplNo}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">업종</th>
						<td colspan="3" class="left"><input type="text" id="indutyNm"
							name="indutyNm" value="${changeCnsl.indutyNm}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">주소</th>
						<td class="left"><input type="text" id="bplAddr"
							name="bplAddr" value="${changeCnsl.bplAddr}" class="w100" readOnly></td>
						<th scope="row">상시근로자수</th>
						<td class="left"><input type="text" id="totWorkCnt"
							name="totWorkCnt" value="${changeCnsl.totWorkCnt}" class="w100" readOnly></td>
					</tr>
					
					<tr>
						<th scope="row">컨설팅 실시주소 <strong class="point-important">*</strong></th>
						<td colspan="3" class="left">
							<input type="text" id="trOprtnRegionZip" name="trOprtnRegionZip" value="${changeCnsl.trOprtnRegionZip}" class="w50" placeholder="우편번호 입력" data-checkValidity="우편번호" readOnly required>
							<input type="button" class="btn-m03 btn-color01" onclick="findAddr()"" value="우편번호 찾기">
							<input type="text" id="trOprtnRegionAddr" name="trOprtnRegionAddr" value="${changeCnsl.trOprtnRegionAddr}" class="w100" placeholder="훈련실시주소" data-checkValidity="실시주소" required readOnly>
							<input type="text" id="trOprtnRegionAddrDtl" name="trOprtnRegionAddrDtl" value="${changeCnsl.trOprtnRegionAddrDtl}" class="w100" placeholder="훈련실시상세주소" readOnly>
						</td>
					</tr>
					
					<tr>
						<th scope="row">관할 지부·지사 <strong class="point-important">*</strong></th>
						<td class="left">
							<input type="text" id="cmptncBrffcNm"  value="${changeCnsl.cmptncBrffcNm}" class="w100" data-checkValidity="관할 지부지사" required readOnly>
							<input type="hidden" id="cmptncBrffcIdx" name="cmptncBrffcIdx" value="${changeCnsl.cmptncBrffcIdx}" class="w100" required>
							
							</td>
						
						<th scope="row">추천기관명</th>
						<td class="left">
							<input type="text" name="recomendInsttNm" value="${changeCnsl.recomendInsttNm}" class="w100">
						</td>
					</tr>
					
					<tr>
						<th scope="row">지원센터</th>
						<td class="left">
							<input type="text" id="spnt" value="${changeCnsl.spnt}" class="w100" readOnly>
							<input type="hidden" id="spntNm" name="spntNm" value="${changeCnsl.spntNm}" class="w100" readOnly></td>
						
						<th scope="row">연락처</th>
						<td class="left"><input type="text" id="spntTelno" name="spntTelno"
							value="${changeCnsl.spntTelno}" class="w100" readOnly></td>
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
						<th scope="row">성명 <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicNm"
							name="corpPicNm" value="${changeCnsl.corpPicNm}" class="w100" data-checkValidity="담당자 성명" required></td>
						<th scope="row">직위 <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicOfcps"
							name="corpPicOfcps" value="${changeCnsl.corpPicOfcps}" class="w100" data-checkValidity="담당자 직위" required></td>
					</tr>
					<tr>
						<th scope="row">전화번호 <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicTelno"
							name="corpPicTelno" value="${changeCnsl.corpPicTelno}" class="w100" data-checkValidity="담당자 전화번호" required></td>
						<th scope="row">E-mail <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicEmail"
							name="corpPicEmail" value="${changeCnsl.corpPicEmail}" class="w100" data-checkValidity="담당자 E-mail" required></td>
					</tr>
					
					<tr>
						<th scope="row">컨설팅 요구사항 <strong class="point-important">*</strong></th>
						<td class="left" colspan="3">
							<textarea id="textfield01" name="cnslDemandMatter" rows="5" class="w100 h100" data-checkValidity="컨설팅 요구사항" required><c:out value="${changeCnsl.cnslDemandMatter}"/></textarea>
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
						<th scope="col" colspan="5">3. 컨설팅 팀 구성 및 개요</th>
					</tr>
					<tr>
						<th scope="row">컨설팅 팀 <strong class="point-important">*</strong></th>
						<td class="">HRD4U ID</td>
						<td class="">성명</td>
						<td class="">적합여부</td>
						<td class="">HRD4U 인력풀</td>
					</tr>
					<tr class="pm">
						<th scope="row">컨설팅 책임자 (PM)</th>
						<td class="mberIdTd">
							<input type="text" id="pmId" name="cnslTeam.members[0].loginId" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" onblur="hrpUserSearchHandler(this)" data-checkValidity="컨설팅 책임자 (PM)" required></td>
						<td class="">
							<input type="text" id="pmId_name" name="cnslTeam.members[0].mberName" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100">
						</td>
						
						<input type="hidden" name="cnslTeam.members[0].rspnberYn" data-value="Y" value="Y">
						<input type="hidden" name="cnslTeam.members[0].teamIdx" data-value="1" value="1">
						<input type="hidden" name="cnslTeam.members[0].teamOrderIdx" data-value="1" value="1">
						<input type="hidden" id="pmId_memberIdx" name="cnslTeam.members[0].memberIdx" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
						<input type="hidden" id="pmId_psitn" name="cnslTeam.members[0].mberPsitn" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>">
						<input type="hidden" id="pmId_ofcps" name="cnslTeam.members[0].mberOfcps" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>">
						<input type="hidden" id="pmId_telno" name="cnslTeam.members[0].mberTelno" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 1 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>">
						
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
						<th scope="row">외부 내용전문가<br>
							<input type="checkbox" id="id_chk" onclick="toggleSelection()" class="checkbox-type01">
							<span id="toggleButton">
								<strong class="point-color01 right" >선택 안함</strong>
							</span><div class="people-add-box">
								<button type="button" class="add" onclick="addExExpert(this)">추가</button>
								<button type="button" class="delete" onclick="deleteExRow(this)">삭제</button>
							</div>
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_01" name="cnslTeam.members[1].loginId" onblur="hrpUserSearchHandler(this)" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" data-checkValidity="외부 내용전문가" required></td>
						<td class="">
						 <input type="text" id="exExpertId_01_name" name="cnslTeam.members[1].mberName" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100"></td>
						<td class="checkSuitableRadioTd">
							<input type="hidden" name="cnslTeam.members[1].rspnberYn" data-value="N" value="N"> 
							<input type="hidden" name="cnslTeam.members[1].teamIdx" data-value="2" value="2"> 
							<input type="hidden" name="cnslTeam.members[1].teamOrderIdx" data-value="1" value="1"> 
							<input type="hidden" id="exExpertId_01_memberIdx" name="cnslTeam.members[1].memberIdx" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
							<input type="hidden" id="exExpertId_01_psitn" name="cnslTeam.members[1].mberPsitn" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>">
							<input type="hidden" id="exExpertId_01_ofcps" name="cnslTeam.members[1].mberOfcps" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>">
							<input type="hidden" id="exExpertId_01_telno" name="cnslTeam.members[1].mberTelno" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 2 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>">
							
							
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

					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
						<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
						<tr class ="exExpert" id="exExpert_0${member.teamOrderIdx}">
						<th scope="row">외부 내용전문가${member.teamOrderIdx}
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_0${member.teamOrderIdx}" name="cnslTeam.members[${member.teamOrderIdx}].loginId" onblur="hrpUserSearchHandler(this)" value="${member.loginId}" class="w100" data-checkValidity="외부 내용전문가" required></td>
						<td class="">
						 <input type="text" id="exExpertId_0${member.teamOrderIdx}_name" name="cnslTeam.members[${member.teamOrderIdx}].mberName" value="${member.mberName}" class="w100"></td>
						
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
									<input type="radio" id="exExpertId_01_suitableY" value="" class="radio-type01" onclick="event.preventDefault()">  <label for="radio0${member.teamOrderIdx}01"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_01_suitableN" value="" class="radio-type01" onclick="event.preventDefault()"> <label for="radio0${member.teamOrderIdx}02">N </label>
								</div>
							</div>
						</td>
							
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${member.teamOrderIdx}_isHrpY" name="" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0${member.teamOrderIdx}01"> Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${member.teamOrderIdx}_isHrpN" name="" value="" class="radio-type01" onclick="event.preventDefault()"> 
									<label for="radio0${member.teamOrderIdx}02"> N </label>
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
							<input type="text" id="inExpertId_01" name="cnslTeam.members[4].loginId" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.loginId}</c:if></c:forEach>" class="w100" placeholder="HRD4U ID" 	onblur="userSearchHandler(this)" required></td>
						<td class="" colspan="2">
							<input type="text" id="inExpertId_01_name" name="cnslTeam.members[4].mberName" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberName}</c:if></c:forEach>" class="w100" placeholder="성명" required></td>
						<td class="">
							<input type="text" id="inExpertId_01_ofcps" name="cnslTeam.members[4].mberOfcps" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberOfcps}</c:if></c:forEach>" class="w100" placeholder="직위"></td>
							
							<input  type="hidden" name="cnslTeam.members[4].rspnberYn" data-value="N" value="N"> 
							<input  type="hidden" name="cnslTeam.members[4].teamIdx" data-value="3" value="3"> 
							<input  type="hidden" name="cnslTeam.members[4].teamOrderIdx" data-value="1" value="1">
							<input  type="hidden" id="inExpertId_01_memberIdx" name="cnslTeam.members[4].memberIdx" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.memberIdx}</c:if></c:forEach>">
							<input  type="hidden" id="inExpertId_01_psitn" name="cnslTeam.members[4].mberPsitn" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberPsitn}</c:if></c:forEach>">
							<input  type="hidden" id="inExpertId_01_telno" name="cnslTeam.members[4].mberTelno" value="<c:forEach var="member" items="${changeCnsl.cnslTeam.members}"><c:if test="${member.teamIdx eq 3 and member.teamOrderIdx eq 1}">${member.mberTelno}</c:if></c:forEach>">
					</tr>
					
					
					
					
					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
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
									<input  type="hidden" id="inExpertId_0${member.teamOrderIdx}_memberIdx" name="cnslTeam.members[${member.teamOrderIdx +3}].memberIdx" value="${member.mberPsitn}">
									<input  type="hidden" id="inExpertId_0${member.teamOrderIdx}_psitn" name="cnslTeam.members[${member.teamOrderIdx +3}].mberPsitn" value="${member.mberPsitn}">
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
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${changeCnsl.cnslFiles}"><c:if test="${file.itemId eq 'pmFile'}">${file.fileOriginName}</c:if></c:forEach>" required> <label
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
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${changeCnsl.cnslFiles}"><c:if test="${file.itemId eq 'exFile_01'}">${file.fileOriginName}</c:if></c:forEach>"> <label
									for="exFile_01" class="btn_file">찾아보기</label> <input
									type="file" id="exFile_01" name="exFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_02').value = this.value">
							</div>
						</td>
					</tr>
					
					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
					<c:if test="${member.teamIdx eq 2 and member.teamOrderIdx ne 1}">
					<c:set value="exFile_0${member.teamOrderIdx}" var="exFile" />
					<tr id="exExpertId_0${member.teamOrderIdx}_file">
						<td class="">외부 내용전문가${member.teamOrderIdx}</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${member.teamOrderIdx+1}" class="fileName"
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${changeCnsl.cnslFiles}"><c:if test="${file.itemId eq exFile}">${file.fileOriginName}</c:if></c:forEach>"> <label
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
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${changeCnsl.cnslFiles}"><c:if test="${file.itemId eq 'inFile_01'}">${file.fileOriginName}</c:if></c:forEach>"> <label
									for="inFile_01" class="btn_file">찾아보기</label> <input
									type="file" id="inFile_01" name="inFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_05').value = this.value">
							</div>
						</td>
					</tr>
					
					
					
					<c:forEach var="member" items="${changeCnsl.cnslTeam.members}" varStatus="status">
					<c:if test="${member.teamIdx eq 3 and member.teamOrderIdx ne 1}">
					<c:set value="inFile_0${member.teamOrderIdx}" var="inFile" />
					<tr id="inExpertId_0${member.teamOrderIdx}_file">
						<td class="">기업 내부전문가${member.teamOrderIdx}</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${member.teamOrderIdx+4}" class="fileName"
									readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${changeCnsl.cnslFiles}"><c:if test="${file.itemId eq inFile}">${file.fileOriginName}</c:if></c:forEach>"> <label
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
								<input type="text" id="fileName_etc" class="fileName" readonly="readonly" placeholder="파일찾기" value="<c:forEach var="file" items="${changeCnsl.cnslFiles}"><c:if test="${file.itemId eq 'etcFile'}">${file.fileOriginName}</c:if></c:forEach>"> 
									<label for="etcFile" class="btn_file">찾아보기</label> 
								<input type="file" id="etcFile" name="etcFile" class="uploadBtn" onchange="javascript:document.getElementById('fileName_etc').value = this.value">
							</div>
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
									<p style="font-size: 20px;">위와 같이 컨설팅 변경을 신청합니다.</p>
									<p style="font-size: 20px;">
										<c:out value="${year}"/>년 
										<c:out value="${month}"/>월 
										<c:out value="${day}"/>일
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
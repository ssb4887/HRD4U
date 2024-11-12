<!-- CMS 시작 -->

<div class="contents-area pl0">
	<form id="applyForm">
	<input type="hidden" id="bscIdx" name="bscIdx" value="${bscIdx}"> 
	<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx" value="${bsiscnslIdx}"> 

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
						<th scope="row" rowspan="2">훈련과정 유형 <strong class="point-important">*</strong></th>
						<th scope="row">사업주훈련</th>
						<th scope="row">일반직무전수 OJT</th>
						<th scope="row">과제수행 OJT</th>
					</tr>
					<tr>
						<td class="left">
							<div class="input-radio-area">
								<input type="radio" id="cnslType1" name="cnslType" value="1" class="radio-type01" onclick="sojtLimitCheck(this, `${basket.bplNo}`)" data-checkValidity="훈련과정 유형" required> 
								<label for="radio0101"></label>
							</div>
						</td>
						<td class="left">
							<div class="input-radio-area">
									<input type="radio" id="cnslType2" name="cnslType" value="2" class="radio-type01" onclick="sojtLimitCheck(this, `${basket.bplNo}`)" data-checkValidity="훈련과정 유형" required> 
									<label for="radio0102"></label>
							</div>
						</td>
						<td class="left">
							<div class="input-radio-area">
									<input type="radio" id="cnslType3" name="cnslType" value="3" class="radio-type01" onclick="sojtLimitCheck(this, `${basket.bplNo}`)" data-checkValidity="훈련과정 유형" required> <label for="radio0103"></label>					
								</div>
						</td>
					</tr>
					<tr>
						<th scope="row">훈련직무 및 개발 요구사항 <strong class="point-important">*</strong></th>
						<td class="left" colspan="3"><textarea id="textfield01"
								name="cnslDemandMatter" cols="50" rows="5" class="w100 h100" placeholder="내용"
								data-checkValidity="훈련직무 및 개발 요구사항" required></textarea></td>
					</tr>
					<tr>
						<th scope="row">직무분류  <strong class="point-important">*</strong></th>
						<td class="left" colspan="3">
							<div class="input-add-btns">
								<input type="hidden" id="ncsCode" name="dtyCl" class="" placeholder="" data-checkValidity="직무분류" required>
								<input type="text" id ="ncsName" class="" placeholder="유사 직무 선택(검색 버튼을 눌러주세요.)">
								<button type="button" onclick="openModal('selectNcsModal', this)">검색</button>
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
						<td class="left"><input type="text" id="corpNm" name="corpNm"
							value="${basket.bplNm}" class="w100" readOnly></td>
						<th scope="row">대표자명</th>
						<td class="left"><input type="text" id="reperNm"
							name="reperNm" value="${reperNm}" class="w100" readOnly></td>
					</tr>
					<tr>
						<th scope="row">사업자등록번호</th>
						<td class="left"><input type="text" id="bizrNo" name="bizrNo"
							value="${basket.bizrNo}" class="w100" readOnly></td>
						<th scope="row">고용보험관리번호</th>
						<td class="left"><input type="text" id="bplNo" name="bplNo"
							value="${basket.bplNo}" class="w100" readOnly></td>
					</tr>
					<tr>
						<th scope="row">업종</th>
						<td colspan="3" class="left"><input type="text" id="indutyNm"
							name="indutyNm" value="${basket.bplIndustNm}" class="w100" readOnly></td>
					</tr>
					<tr>
						<th scope="row">주소</th>
						<td class="left"><input type="text" id="bplAddr"
							name="bplAddr" value="${basket.bplAddr}" class="w100" readOnly></td>
						<th scope="row">상시근로자수</th>
						<td class="left"><input type="text" id="totWorkCnt"
							name="totWorkCnt" value="${basket.totWorkCnt}" class="w100" readOnly></td>
					</tr>
					<tr>
						<th scope="row" rowspan="4">훈련실시주소  <strong class="point-important">*</strong></th>
						<td class="left" rowspan="4" style="border-right: 1px solid #ddd !important;">
							<input type="text" id="trOprtnRegionZip" name="trOprtnRegionZip" value="" class="w50" placeholder="우편번호 입력" data-checkValidity="실시주소" readOnly required>
							<input type="button" class="btn-m03 btn-color01" onclick="findAddr()"" value="우편번호 찾기">
	
							<input type="text" id="trOprtnRegionAddr" name="trOprtnRegionAddr" value="" class="w100" placeholder="훈련실시주소" readOnly required>
							<input type="text" id="trOprtnRegionAddrDtl" name="trOprtnRegionAddrDtl" value="" class="w100" placeholder="훈련실시상세주소" readOnly>
						</td>
						
					</tr>
					<tr>
						<th scope="row">추천기관명</th>
						<td class="left">
							<select name="recomendInsttNm" class="w100">
								<option value="">선택</option>
								<option value="경기경영자총협회" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '경기경영자총협회'}">selected</c:if>>경기경영자총협회</option>
								<option value="대한상공회의소 경기인력개발원" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '대한상공회의소 경기인력개발원'}">selected</c:if>>대한상공회의소 경기인력개발원</option>
								<option value="한국공학대학교" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '한국공학대학교'}">selected</c:if>>한국공학대학교</option>
								<option value="대한상공회의소 충남인력개발원" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '대한상공회의소 충남인력개발원'}">selected</c:if>>대한상공회의소 충남인력개발원</option>
								<option value="한국문화산업협회" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '한국문화산업협회'}">selected</c:if>>한국문화산업협회</option>
								<option value="전북산학융합원" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '전북산학융합원'}">selected</c:if>>전북산학융합원</option>
								<option value="경남경영자총협회" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '경남경영자총협회'}">selected</c:if>>경남경영자총협회</option>
								<option value="대한상공회의소 부산인력개발원" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '대한상공회의소 부산인력개발원'}">selected</c:if>>대한상공회의소 부산인력개발원</option>
								<option value="경북경영자총협회" <c:if test="${sojtFormInfo.RECOMEND_INSTT_NM eq '경북경영자총협회'}">selected</c:if>>경북경영자총협회</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">관할 지부·지사</th>
						<td class="left">
							<input type="text" id="cmptncBrffcNm" value="" class="w100" readOnly>
							<input type="hidden" id="cmptncBrffcIdx" name="cmptncBrffcIdx" value="" class="w100" data-checkValidity="관할 지부·지사" required>
							<input type="hidden" id="cmptncBrffcPicIdx" name="cmptncBrffcPicIdx" value="" class="w100" required>
						</td>
					</tr>
					<tr>
						<th scope="row">연락처</th>
						<td class="left">
							<input type="text" id="cmptncBrffcPicTelno" name="cmptncBrffcPicTelno" value="" class="w100" readOnly>
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
						<th scope="row">성명  <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicNm"
							name="corpPicNm" value="${sojtFormInfo.CORP_PIC_NAME}" class="w100" placeholder="담당자 성명" data-checkValidity="담당자 성명" required></td>
						<th scope="row">직위  <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicOfcps"
							name="corpPicOfcps" value="${sojtFormInfo.CORP_PIC_OFCPS}" class="w100" placeholder="담당자 직위" data-checkValidity="담당자 직위" required></td>
					</tr>
					<tr>
						<th scope="row">전화번호  <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicTelno"
							name="corpPicTelno" value="${sojtFormInfo.CORP_PIC_TELNO}" class="w100" placeholder="담당자 연락처" data-checkValidity="담당자 연락처" required></td>
						<th scope="row">E-mail  <strong class="point-important">*</strong></th>
						<td class="left"><input type="text" id="corpPicEmail"
							name="corpPicEmail" value="${sojtFormInfo.CORP_PIC_EMAIL}" class="w100" placeholder="담당자 E-mail" data-checkValidity="담당자 E-mail" required></td>
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
						<th scope="col" colspan="1">
							<input type="checkbox" id="teamCheck" name="teamCheck" onchange="checkTeamCreationStatus()" class="checkbox-type01">&nbsp;팀구성 추천요망</th>				
						<th scope="col" colspan="4">4. 컨설팅 팀 구성 및 개요</th>
					</tr>
					<tr>
						<th scope="row">컨설팅 팀  <strong class="point-important">*</strong></th>
						<td class="">HRD4U ID</td>
						<td class="">성명</td>
						<td class="">적합여부</td>
						<td class="">HRD4U 인력풀</td>
					</tr>
					<tr class="pm">
						<th scope="row">컨설팅 책임자 (PM)</th>
						<td class="mberIdTd">
							<input type="text" id="pmId" name="cnslTeam.members[0].loginId" value="" class="w100" onblur="hrpUserSearchHandler(this)" placeholder="HRD4U ID" data-checkValidity="컨설팅 책임자" required></td>
						<td class="">
							<input type="text" id="pmId_name" name="cnslTeam.members[0].mberName" value="" class="w100" readOnly required>
						</td>
						
						<input type="hidden" name="cnslTeam.members[0].rspnberYn" data-value="Y" value="Y">
						<input type="hidden" name="cnslTeam.members[0].teamIdx" data-value="1" value="1">
						<input type="hidden" name="cnslTeam.members[0].teamOrderIdx" data-value="1" value="1">
						<input type="hidden" id="pmId_memberIdx" name="cnslTeam.members[0].memberIdx" value="">
						<input type="hidden" id="pmId_psitn" name="cnslTeam.members[0].mberPsitn" value="">
						<input type="hidden" id="pmId_ofcps" name="cnslTeam.members[0].mberOfcps" value="">
						<input type="hidden" id="pmId_telno" name="cnslTeam.members[0].mberTelno" value="">
						
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
							</span>
						</th>
						<td class="mberIdTd">
						 <input type="text" id="exExpertId_01" name="cnslTeam.members[1].loginId" onblur="hrpUserSearchHandler(this)" value="" class="w100" placeholder="HRD4U ID" data-checkValidity="외부내용전문가" required></td>
						<td class="">
						 <input type="text" id="exExpertId_01_name" name="cnslTeam.members[1].mberName" value="" class="w100" readOnly></td>
						<td class="checkSuitableRadioTd">
							<input type="hidden" name="cnslTeam.members[1].rspnberYn" data-value="N" value="N"> 
							<input type="hidden" name="cnslTeam.members[1].teamIdx" data-value="2" value="2"> 
							<input type="hidden" name="cnslTeam.members[1].teamOrderIdx" data-value="1" value="1"> 
							<input type="hidden" id="exExpertId_01_memberIdx" name="cnslTeam.members[1].memberIdx" value="">
							<input type="hidden" name="cnslTeam.members[1].innerExtrlCd" value="1">
							<input type="hidden" id="exExpertId_01_psitn" name="cnslTeam.members[1].mberPsitn" value="">
							<input type="hidden" id="exExpertId_01_ofcps" name="cnslTeam.members[1].mberOfcps" value="">
							<input type="hidden" id="exExpertId_01_telno" name="cnslTeam.members[1].mberTelno" value="">
							
							
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





					<tr class="inExpert" id="inExpert_01">
						<th scope="row">기업 내부전문가
						</th>
						<td class="mberIdTd">
							<input type="text" id="inExpertId_01" name="cnslTeam.members[4].loginId" value="" class="w100" placeholder="HRD4U ID" 	onblur="userSearchHandler(this)" data-checkValidity="기업 내부전문가" required></td>
						<td class="" colspan="2">
							<input type="text" id="inExpertId_01_name" name="cnslTeam.members[4].mberName" value="" class="w100" readOnly required></td>
						<td class="">
							<input type="text" id="inExpertId_01_ofcps" name="cnslTeam.members[4].mberOfcps" value="" class="w100" placeholder="직위"></td>
							
							<input type="hidden" name="cnslTeam.members[4].rspnberYn" data-value="N" value="N"> 
							<input type="hidden" name="cnslTeam.members[4].teamIdx" data-value="3" value="3"> 
							<input type="hidden" name="cnslTeam.members[4].teamOrderIdx" data-value="1" value="1"> 
							<input type="hidden" id="inExpertId_01_memberIdx" name="cnslTeam.members[4].memberIdx" value="">
							<input type="hidden" name="cnslTeam.members[4].innerExtrlCd" value="2">
							<input type="hidden" id="inExpertId_01_psitn" name="cnslTeam.members[4].mberPsitn" value="">
							<input type="hidden" id="inExpertId_01_telno" name="cnslTeam.members[4].mberTelno" value="">
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
						<th scope="col" colspan="2">첨부파일  <strong class="point-important">*</strong></th>
					</tr>
					<tr>
						<th scope="col">구분</th>
						<th scope="col">경력증명서</th>
					</tr>
					<tr class="pmFile" id="pmId_file">
						<td class="">컨설팅 책임자(PM)</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_01" class="fileName" placeholder="파일찾기" value="" data-checkValidity="컨설팅 책임자 첨부파일" required> <label
									for="pm_file" class="btn_file" >찾아보기</label> 
									<input type="file" id="pm_file" name="pmFile" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_01').value = this.value">
							</div>
						</td>
					</tr>
					<tr class ="exExpertFile" id="exExpertId_01_file">
						<td class="">외부 내용전문가</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_02" class="fileName"
									 placeholder="파일찾기" value="" required> <label
									for="exExpert_01_file" class="btn_file">찾아보기</label> <input
									type="file" id="exExpert_01_file" name="exFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_02').value = this.value" data-checkValidity="외부 내용전문가 첨부파일" required>
							</div>
						</td>
					</tr>
					
					<tr class="inExpertFile" id="inExpertId_01_file">
						<td class="">기업 내부전문가</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_03" class="fileName"
									 placeholder="파일찾기" value="" data-checkValidity="기업 내부전문가 첨부파일" required> <label
									for="inExpert_01_file" class="btn_file">찾아보기</label> <input
									type="file" id="inExpert_01_file" name="inFile_01" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_03').value = this.value">
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="">기타</td>
						<td class="left">
								<div class="fileBox">
								<input type="text" id="fileName_etc" class="fileName"
									 placeholder="파일찾기" value=""> <label
									for="etcFile" class="btn_file">찾아보기</label> <input
									type="file" id="etcFile" name="etcFile" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_etc').value = this.value">
							</div>
						</td>
					</tr>
					
				</tbody>
			</table>
		</div>
	</div>

	<div class="contents-box pl0" style="margin-bottom: 40px;">
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
	</form>
</div>


<!-- //CMS 끝 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>				
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<style>
	button#send { background-color: #1e89e9; color: white; }
	button#send:disabled { background-color: #a3c2e9; color: white; cursor: default; }
</style>
	<!-- CMS 시작 -->
	<div class="contents-area pl0">
		<h3 class="title-type01 ml0">체계적 현장훈련 참여신청서</h3>
		<form id="main" action="" method="POST">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<input type="hidden" name="bplNo" value="${content.BPL_NO }" >
					<input type="hidden" name="bsiscnslIdx" value="${bsiscnsl_idx }" >
					<c:if test="${not empty amIapplying}">
						<input type="hidden" name="sojt_idx" value="${amIapplying[0].SOJT_IDX}" >
					</c:if>
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
									<td class="left">
										${content.CORP_NAME }
									</td>
								<th scope="row">대표자명</th>
									<td class="left">
										${content.REPVE_NM }
									</td>
							</tr>
							<tr>
								<th scope="row">사업자등록번호</th>
									<td class="left">
										${content.BIZR_NO }
									</td>
								<th scope="row">고용보험관리번호</th>
								<td class="left">
									${content.BPL_NO }
								</td>
							</tr>
							<tr>
								<th scope="row" rowspan="2">업종</th>
								<td colspan="3" class="left">업종코드 : ${content.INDUTY_CD }</td>
							</tr>
							<tr>
								<td colspan="3" class="left">주업종(주된 사업) : ${content.INDUTY_NAME }</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td class="left">${content.CORP_LOCATION }</td>
								<th scope="row">상시근로자수</th>
								<td class="left">${content.TOT_WORK_CNT } 명</td>
							</tr>
							<!-- 2023.12.04 수정 -->
							<tr>
								<th scope="row">훈련실시주소</th>
								<td class="left" colspan="3">
									<div class="zipcode">
										<input type="text" id="postcode" name="postcode" value="<c:out value="${not empty accept_content ? fn:split(accept_content.TR_OPRTN_ADDR,'||')[0] : ''}"/>" title="우편번호" readonly />
										<button onclick="execDaumPostcode()" type="button" class="btn-m01 btn-color02">우편번호</button>
									</div>
									<input type="text" id="train_addr" name="trainAddr" value="<c:out value="${not empty accept_content ? fn:split(accept_content.TR_OPRTN_ADDR,'||')[1] : ''}"/>" class="w100 mt10" />
									<input type="text" id="train_addr_detail" name="trainAddrDetail" value="<c:out value="${not empty accept_content ? fn:split(accept_content.TR_OPRTN_ADDR,'||')[2] : ''}"/>" class="w100 mt10" />
								</td>
							</tr>
							<tr>
								<th scope="row">관할 지부·지사</th>
								<td class="left">
									<input type="text" id="brffcCd" value="" title="관할 지부·지사" readonly />
									<input type="hidden" id="id_insttIdx" name="insttIdx" value="" readonly />
								</td>
								<th scope="row">연락처</th>
								<td class="left">
									<input type="text" id="brffcTel" value="" title="관할 지부·지사 연락처" readonly />
									<input type="hidden" id="id_doctorTelno" name="doctorTelno" value="" title="관할 지부·지사 연락처" readonly />
								</td>
							</tr>
							<tr>
								<th scope="row">추천기관명</th>
								<td class="left" colspan="3">
								<select id="id_recommendName" name="recommendName" class="w100">
									<option value="">없음</option>
										<c:forEach items="${content.prvtcntr_list }" var="item">
											<option value="${item.PRVTCNTR_NAME}" <c:if test="${accept_content.RECOMMEND_CENTER eq item.PRVTCNTR_NAME}">selected</c:if>>${item.PRVTCNTR_NAME }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<!-- //2023.12.04 수정 -->
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
								<td class="left"><input type="text" id="id_corpName" name="corpName" value="<c:out value="${not empty accept_content ? accept_content.CORP_NAME : ''}"/>" class="w100" pattern=".*\s.*" title="공백을 제외하고 입력하세요." required></td>
								<th scope="row">직위</th>
								<td class="left"><input type="text" id="id_corpOfcps" name="corpOfcps" value="<c:out value="${not empty accept_content ? accept_content.CORP_PIC_OFCPS : ''}"/>" class="w100" required></td>
							</tr>
							<tr>
								<th scope="row">전화번호</th>
								<td class="left">
									<input type="text" id="id_corpTel" name="corpTelno" value="<c:out value="${not empty accept_content ? accept_content.CORP_PIC_TELNO : ''}"/>" class="w100 onlyNumDash" pattern="\d{2,3}-\d{3,4}-\d{4}" title="전화번호를 입력하세요." onkeyup="validation();" oninput="formatPhoneNumber(this);" placeholder="연락처(000-0000-000)" size="30" maxlength="30" required />
									<span id="telError" style="display: none; color: red;"></span>
								</td>
								<th scope="row">E-mail</th>
								<td class="left">
									<input type="text" id="id_corpEmail" name="corpEmail" value="<c:out value="${not empty accept_content ? accept_content.CORP_PIC_EMAIL : ''}"/>" class="w100" title="이메일 입력" onkeyup="validation();" placeholder="이메일(@mail.com)" size="40" maxlength="40">
									<span id="emailError" style="display: none; color: red;"></span>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="sign-wrapper">
										<p class="notice">※ 지원대상 요건(기업 규모·업종·상태 등)에 따라 참여 제한이 될 수 있습니다.</p>
										<div class="sign-name">
											<p style="font-size:15px;">위와 같이 2024년도 체계적 현장훈련 참여를 신청합니다.</p>
											<p id="last-date">
												<c:out value="${year}"/>년 
												<c:out value="${month}"/>월 
												<c:out value="${day}"/>일
											</p>
										</div>
										<strong>한국산업인력공단 이사장 귀하</strong>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<button type="button" id="send" class="btn-m01" disabled>신청</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>
	<!-- //CMS 끝 -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
	var inputs;
	var button;
	$(function() {
		inputs = document.querySelectorAll('input[required]')
		button = document.querySelector('button#send')
		getSbnm(49437);
		checkInputs();
		inputs.forEach((input) => {
			input.addEventListener('keyup', checkInputs);
		});
		$('button#send').click(function() {
			const formData = new FormData(document.getElementById('main'));
			fetch('${contextPath }/web/sojt/apply.do?mId=110', {
				method: 'POST',
				body: formData
			})
			.then(response => response.json())
			.then(data => {
				if(data.status === "success") {
					window.location.href = '${contextPath }/web/sojt/applyList.do?mId=110'
				} else {
					alert('제출 실패')
				}
			})
			.catch(error => {
				console.error('오류 발생', error);
			})
		})
		
		let last_date_ = '${content.LAST_DATE}';
		let date_ = last_date_.split('-')
		let p_e = document.querySelector('p#last-date')
		let result = `\${date_[0]}년 \${date_[1]}월 \${date_[2]}일`;
		p_e.textContent = result;
	})
	
	function checkInputs() {
		let allFilled = true;
		inputs.forEach(function(input) {
			if(!input.value) {
				allFilled = false;
			}
		})
		button.disabled = !allFilled;
	}
	
	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete: function(data) {
				var addr = '';
				var extraAddr = '';
				
				if(data.userSelectedType === 'R') {
					addr = data.roadAddress;
				} else {
					addr = data.jibunAddress;
				}
				
				if(data.userSelectedType === 'R') {
					if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname
					}
					if(data.buildingName !== '' && data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName)
					}
					if(extraAddr !== '') {
						extraAddr = ' (' + extraAddr + ')';
					}
				}
				
				document.getElementById('postcode').value = data.zonecode;
				document.getElementById('train_addr').value = addr + ' ' + extraAddr;
				document.getElementById('train_addr_detail').focus();
				
				getSbnm(data.zonecode);
			}
		}).open();
	}
	
	//우편번호에 따른 관할 지부 지사 가져오기
	function getSbnm(param){
		var data ={
			postNo : param
		}
		$.ajax({
			url : `${contextPath}/web/sojt/getBrffcCd.do?mId=110`,
			type : "POST", 
			data : data,
			success: function(data) {
				var brchCode = data.result.body.INSTT_IDX;
				var sbNm = data.result.body.SB_NM;
				$("#brffcCd").val(sbNm);
				$("#id_insttIdx").val(brchCode);
				getBranchTel(brchCode);
			}, error: function(err){
				alert(err);
			}
		});
	}
	
	//선택된 관할지부지사에 따라 전화번호 가져오기
	function getBranchTel(val){
		var data = {insttIdx : val}
		$.ajax({
			url : `${contextPath}/web/sojt/getBrffcTel.do?mId=110`,
			type : "POST", 
			data : data,
			success: function(data) {
				$("#brffcTel").val(data.brffcTel);
				$("#id_doctorTelno").val(data.brffcTel);
			}, error: function(err){
				alert(err);
			}
		});
	}
		
	
	function fn_validationTp(){
		$(".number").keyup(function(event) {
			if($(this).val() > 99999){
				alert("99999이하로 입력해 주세요.");
				$(this).val(99999);
			}
		});
	}
	
	/**
	 * 유효성 검사
	 */
	function validation() {
		let emailRegex = /^[0-9a-zA-Z._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		let telRegex = /^([0-9]{2,3})-?([0-9]{3,4})-?([0-9]{4})$/;
		
	// 휴대전화번호
		let telInput = document.querySelector("#id_corpTel");
		let telError = document.getElementById("telError");
		let enteredTel = telInput.value;
		telError.style.display = 'block';
		
		if(!telRegex.test(enteredTel) && enteredTel.length != 0) {
			// telInput.style.border = "2px solid #1e89e9";
			telError.innerHTML = "유효한 연락처 형식이 아닙니다.";
			return false;
		}else {
			telInput.style.border = "1px solid #f1f3f5 font-size: 13px";
			telError.innerHTML = "";
			telError.style.display = 'none';
		}
		
		// 이메일
		let emailInput = document.querySelector("#id_corpEmail");
		let emailError = document.getElementById("emailError");
		let enteredEmail = emailInput.value;
		
		emailError.style.display = 'block';
		
		if(!emailRegex.test(enteredEmail) && enteredEmail.length != 0) {
			emailError.innerHTML = "유효한 이메일 형식이 아닙니다.";
			return false;
		}else {
			emailInput.style.border = "1px solid #f1f3f5 font-size: 13px";
			emailError.innerHTML = "";
			emailError.style.display = 'none';
		}
	}
	
	// 기업담당자 연락처 입력시 전화번호 입력 포맷팅
	function formatPhoneNumber(input) {
		let inputValue = input.value.replace(/\D/g, '');
		
		if(inputValue.length == 9) {
			input.value = inputValue.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
		}else if(inputValue.length == 10) {
			input.value = inputValue.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
		}else if(inputValue.length == 11) {
			input.value = inputValue.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		}else {
			input.value = inputValue;
		}
	}
	
	</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
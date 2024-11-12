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
		<form id="main" method="POST">
			<input type="hidden" name="id" id="id_sojtIdx" value="<c:out value="${sojt_idx }" />">
			<input type="hidden" name="bsiscnslIdx" id="id_bsiscnslIdx" value="<c:out value="${content.BSISCNSL_IDX }"/>">
			<input type="hidden" name="status" value="${content.CONFM_STATUS }">
			
			<c:if test="${not empty content.CONFM_CN}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="15%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									반려사유
								</th>
								<td class="left">
									<c:out value="${content.CONFM_CN }" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
			
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
								<td class="left">${content.CORP_NAME }</td>
								<th scope="row">대표자명</th>
								<td class="left">${content.REPVE_NM }</td>
							</tr>
							<tr>
								<th scope="row">사업자등록번호</th>
								<td class="left">${content.BIZR_NO }</td>
								<th scope="row">고용보험관리번호</th>
								<td class="left">${content.BPL_NO }</td>
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
							<tr>
								<th scope="row">훈련실시주소</th>
								<td class="left">
									<div class=" flex-box">
										<span id="train-addr"></span>
									</div>
								</td>
								<th scope="row">추천기관명</th>
								<td class="left">${empty content.RECOMMEND_CENTER ? '없음' : content.RECOMMEND_CENTER }</td>
							</tr>
							<tr>
								<th scope="row">관할 지부·지사</th>
								<td class="left">${content.INSTT_NAME }</td>
								<th scope="row">연락처</th>
								<td class="left">${content.DOCTOR_TELNO}</td>
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
								<td class="left"><c:out value="${content.CORP_PIC_NM }" /></td>
								<th scope="row">직위</th>
								<td class="left"><c:out value="${content.CORP_PIC_OFCPS }" /></td>
							</tr>
							<tr>
								<th scope="row">전화번호</th>
								<td class="left"><c:out value="${content.CORP_PIC_TELNO }" /></td>
								<th scope="row">E-mail</th>
								<td class="left"><c:out value="${content.CORP_PIC_EMAIL }" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<c:if test= "${(loginVO.usertypeIdx eq 5) and (content.CONFM_STATUS eq 10) }">
				<div>
					<button type="button" id="withdraw" class="btn-m01 btn-color02">회수</button>
				</div>
			</c:if>
			<c:if test= "${(loginVO.usertypeIdx eq 5) and (content.CONFM_STATUS eq 40) }">
				<div>
					<button type="button" id="btn-newApply" class="btn-m01 btn-color02">재신청</button>
				</div>
			</c:if>
			
			<c:if test="${loginVO.usertypeIdx ne 5}">

			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="20%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="col" colspan="2">참여요건 체크리스트</th>
							</tr>
							<c:choose>
							<c:when test="${content.CONFM_STATUS eq 30 }">
							<tr>
								<th scope="row">우선지원대상기업 여부</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="prisup_cd" name="priSupTrgetCorpYn" value="2" class="radio-type01" ${content.PRI_SUP_TRGET_CORP_YN eq 2 ? 'checked' : '' }  <c:out value="${validity eq 1 ? '' : ' disabled' }"/>>
											<label for="prisup_cd">우선지원 기업</label>
										</div>
										<div class="input-radio-area">
											<input type="radio" id="prisup_cd2" name="priSupTrgetCorpYn" value="1" class="radio-type01" ${content.PRI_SUP_TRGET_CORP_YN eq 1 ? 'checked' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/>>
											<label for="prisup_cd2">대규모 기업</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">휴·폐업 사업장 여부</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="close_yn" name="spcssBplYn" value="1" class="radio-type01 disabledChk" <c:out value="${content.BPL_STATUS_CD ne 1 ? 'checked ' : '' } " /> <c:out value="${validity eq 1 ? ' ' : ' disabled' }"/> >
											<label for="close_yn">해당 </label>
										</div>
										<div class="input-radio-area">
											<input type="radio" id="close_yn2" name="spcssBplYn" value="0" class="radio-type01 abledChk" <c:out value="${content.BPL_STATUS_CD eq 1 ? 'checked ' : '' } " /> <c:out value="${validity eq 1 ? '' : ' disabled' }"/> >
											<label for="close_yn2">미해당</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">임금체불명단 공개 사업장 여부</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="wgdly_yn" name="wgdlyNmStgOthbcBplYn" value="1" class="radio-type01 disabledChk" ${content.WGDLY_NM_STG_OTHBC_BPL_YN eq 1 ? 'checked ' : '' } <c:out value="${validity eq 1 ? '' : 'disabled' }"/> >
											<label for="wgdly_yn">해당</label>
										</div>
										<div class="input-radio-area">
											<input type="radio" id="wgdly_yn2" name="wgdlyNmStgOthbcBplYn" value="0" class="radio-type01 abledChk"  ${content.WGDLY_NM_STG_OTHBC_BPL_YN eq 0 ? 'checked ' : '' } <c:out value="${validity eq 1 ? '' : 'disabled' }"/>>
											<label for="wgdly_yn2">미해당</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">산재다발 사업장 여부</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="indacmt_yn" name="indacmtBplYn" value="1" class="radio-type01 disabledChk" ${content.INDACMT_BPL_YN eq 1 ? 'checked ' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/> >
											<label for="radio0101">해당</label>
										</div>
										<div class="input-radio-area">
											<input type="radio" id="indac2_yn" name="indacmtBplYn" value="0" class="radio-type01 abledChk" ${content.INDACMT_BPL_YN eq 0 ? 'checked ' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/> >
											<label for="radio0102">미해당</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">지원 제외 업종 여부</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="nosup_yn" name="sportExclIndutyYn" value="1" class="radio-type01" ${content.SPORT_EXCL_INDUTY_YN eq 1 ? 'checked' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/> >
											<label for="nosup_yn">Y</label>
										</div>
										<div class="input-radio-area">
											<input type="radio" id="nosup_yn2" name="sportExclIndutyYn" value="0" class="radio-type01" ${content.SPORT_EXCL_INDUTY_YN eq 0 ? 'checked' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/> >
											<label for="nosup_yn2">N</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" colspan="2">3. 선정결과</th>
							</tr>
							<tr>
								<th scope="row">선정결과</th>
								<td scope="row">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" id="passfail" name="passfail" value="pass" class="radio-type01" ${content.CONFM_STATUS eq 50 ? 'checked' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/>>
											<label for="passfail">
												Pass
											</label>
										</div>

										<div class="input-radio-area">
											<input type="radio" id="passfail2" name="passfail" value="fail" class="radio-type01" ${content.CONFM_STATUS eq 40 ? 'checked' : '' } <c:out value="${validity eq 1 ? '' : ' disabled' }"/>>
											<label for="passfail2">
												Fail 
											</label>
										</div>
									</div>
								</td>
							</tr>
							</c:when>
							<c:otherwise>
							<tr>
								<th scope="row">우선지원대상기업 여부</th>
								<td class="left">
									<c:out value="${content.PRI_SUP_TRGET_CORP_YN eq 2 ? '우선지원대상기업' : '대규모 기업' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">휴·폐업 사업장 여부</th>
								<td class="left">
									<c:out value="${content.SPCSS_BPL_YN eq 1 ? 'Y' : 'N' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">임금체불명단 공개 사업장 여부</th>
								<td class="left">
									<c:out value="${content.WGDLY_NM_STG_OTHBC_BPL_YN eq 1 ? 'Y' : 'N' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">산재다발 사업장 여부</th>
								<td class="left">
									<c:out value="${content.INDACMT_BPL_YN eq 1 ? 'Y' : 'N' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">지원 제외 업종 여부</th>
								<td class="left">
									<c:out value="${content.SPORT_EXCL_INDUTY_YN eq 1 ? 'Y' : 'N' }" />
								</td>
							</tr>
							<tr>
								<th scope="row" colspan="2">3. 선정결과</th>
							</tr>
							<tr>
								<th scope="row">선정결과</th>
								<td scope="row" class="left">
									<c:out value="Pass" />
								</td>
							</tr>
							</c:otherwise>
							</c:choose>
							
							<tr>
								<td colspan="2">
									<div class="sign-wrapper">
										<p class="notice" style="text-align:center;font-size:17px;margin-bottom:8px;">
											※ 심사 결과가 다른 경우 고용보험시스템(EI) 또는 고용노동부 홈페이지를 통해 한번 더 확인을 해주시기 바랍니다.
										</p>
										<div class="sign-name">
											<p id="last-date"></p>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<c:if test="${(validity eq 1) and (content.CONFM_STATUS eq 30)}">
									<button type="button" id="send" class="btn-m01" disabled>승인</button>
									<button type="button" id="open-modal01" class="btn-m01 btn-color04">반려</button>
									</c:if>
									<c:if test="${(validity eq 1) and (content.CONFM_STATUS eq 50)}">
									<button type="button" id="send" class="btn-m01">승인</button>
									<button type="button" id="open-modal01" class="btn-m01 btn-color04">반려</button>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
						
		</c:if>
		</form>
	</div>
	<!-- //CMS 끝 -->
	<!-- //모달창 시작 -->
	<div class="mask"></div>
	<div class="modal-wrapper" id="modal-action01">
		<h2>반려</h2>
		<div class="modal-area">
			<form action="">
				<div class="modal-alert">
					<p><span id="item"></span>반려하시겠습니까?</p>
				</div>
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl class="pl0">
							<dt style="display:none">
								<label for="modal-textfield02"></label>
							</dt>
							<dd>
								<textarea id="modal-textfield02" name="content" placeholder="반려 사유"></textarea>
							</dd>
						</dl>
					</div>
				</div>
				<div class="btns-area">
					<button type="button" class="btn-m02 round01 btn-color04" id="reject">
						<span>반려</span>
					</button>
	 				<button type="button" class="btn-m02 round01 btn-color02 modal-close" id="cancel">
	 					<span>취소</span>
					</button>
				</div>
			</form>
		</div>
 		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<script>
	var inputs;
	var button;
	$(function() {
		/* $("#open-modal01").attr("disabled",true);
		
		$(".disabledChk").on("click", function(e) {
			if($(".abledChk:checked").length < 1) {
				$("#passfail").attr("checked",false);
				$("#passfail2").attr("checked",true);
				$("#send").attr("disabled",true);
				$("#open-modal01").attr("disabled",false);
			}
		});
		
		$(".abledChk").on("click", function(e) {
			if($(".abledChk:checked").length == 3) {
				$("#passfail").attr("checked",true);
				$("#passfail2").attr("checked",false);
				$("#send").attr("disabled",false);
				$("#open-modal01").attr("disabled",true);
			}
		}); */
		
		$("#open-modal01").on("click", function() {
			if($("input:radio[name='priSupTrgetCorpYn']:checked").length < 1) {
				alert("우선지원대상기업 여부가 체크되지 않았습니다.");
				$("input:radio[name='priSupTrgetCorpYn']").focus();
				return false;
			}
			
			if($("input:radio[name='spcssBplYn']:checked").length < 1) {
				alert("휴·폐업 사업장 여부가 체크되지 않았습니다.");
				$("input:radio[name='spcssBplYn']").focus();
				return false;
			}
			
			if($("input:radio[name='wgdlyNmStgOthbcBplYn']:checked").length < 1) {
				alert("임금체불명단 공개 사업장 여부가 체크되지 않았습니다.");
				$("input:radio[name='wgdlyNmStgOthbcBplYn']").focus();
				return false;
			}
			
			if($("input:radio[name='indacmtBplYn']:checked").length < 1) {
				alert("산재다발 사업장 여부가 체크되지 않았습니다.");
				$("input:radio[name='indacmtBplYn']").focus();
				return false;
			}
			
			if($("input:radio[name='sportExclIndutyYn']:checked").length < 1) {
				alert("지원 제외 업종 여부가 체크되지 않았습니다.");
				$("input:radio[name='sportExclIndutyYn']").focus();
				return false;
			}
			
			if($("input:radio[name='passfail']:checked").length < 1) {
				alert("선정결과가 체크되지 않았습니다.");
				$("input:radio[name='passfail']").focus();
				return false;
			}
			
			if($("#passfail").is(":checked") == true) {
				alert("선정결과가 Pass인 경우 반려 할 수 없습니다.");
				return false;
			}
			
			$(".mask").fadeIn(150, function() {
				$("#modal-action01").show();
	 		});
	 	});
		$("#modal-action01 .btn-modal-close").on("click", function() {
			$("#modal-action01").hide();
			$(".mask").fadeOut(150);
	 	});
		$("#modal-action01 .modal-close").on("click", function() {
			$("#modal-action01").hide();
			$(".mask").fadeOut(150);
	 	});
		
		inputs = document.querySelectorAll('input[required]')
		let radios = document.querySelectorAll('input[type="radio"]')
		button = document.querySelector('button#send')
		
		radios.forEach((input) => {
			input.addEventListener('change', checkInputs);
		});
		inputs.forEach((input) => {
			input.addEventListener('change', checkInputs);
		});

		$("#open-modal02").on("click", function() {
			$(".mask").fadeIn(150, function() {
				$("#modal-action02").show();
	 		});
		});
		var train_addr_e = $('span#train-addr');
		var train_addr = '${content.TR_OPRTN_ADDR}';
		addrs = train_addr.split('||')
		addr_text = `(\${addrs[0] != null ? addrs[0] : ''}) \${addrs[1] != null ? addrs[1] : ''} \${addrs[2] != null ? addrs[2] : ''}`;
		train_addr_e.text(addr_text);
		$('button#send').click(function() {
			if($("#passfail2").is(":checked") == true) {
				alert("선정결과가 Fail인 경우 승인 할 수 없습니다.");
				$("#passfail").focus();
				return false;
			}
			
			if($("#close_y:checked").length == 1) {
				alert("휴·폐업 사업장 여부가 해당인 경우 승인 할 수 없습니다.");
				$("#close_yn").focus();
				return false;
			}
			
			if($("#wgdly_y:checked").length == 1) {
				alert("임금체불명단 공개 여부가 해당인 경우 승인 할 수 없습니다.");
				$("#wgdly_yn").focus();
				return false;
			}
			
			if($("#indacmt_y:checked").length == 1) {
				alert("산재다발 사업장 여부가 해당인 경우 승인 할 수 없습니다.");
				$("#indacmt_yn").focus();
				return false;
			}
			
			const formData = new FormData(document.getElementById('main'));
			fetch('${contextPath }/dct/sojt/accept.do?mId=136', {
				method: 'POST',
				body: formData
			})
			.then(response => response.json())
			.then(data => {
				if(data.status === "success") {
					window.location.href = '${contextPath }/dct/sojt/applyList.do?mId=136'
				} else {
					alert('제출 실패: ' + data.message)
				}
			})
			.catch(error => {
				console.error('오류 발생', error);
			})
		})
		$('button#reject').click(function() {
			let formData = new FormData();
			let data_ = {
					content: $('textarea#modal-textfield02').val(),
					id: '${param.id}',
					status: ${content.CONFM_STATUS}
			}
			console.log(data_);
			for(let key in data_) {
				formData.append(key, data_[key])
			}
			fetch('${contextPath }/dct/sojt/reject.do?mId=136', {
				method: 'POST',
				body: formData
			})
			.then(response => response.json())
			.then(data => {
				if(data.status === "success") {
					window.location.href = '${contextPath }/dct/sojt/applyList.do?mId=136'
				} else {
					alert('제출 실패: ' + data.message)
				}
			})
			.catch(error => {
				console.error('오류 발생', error);
			})
		})
		
		$('button#withdraw').click(function() {
			let formData = new FormData();
			let data_ = {
					id: '${param.id}'
			}
			for(let key in data_) {
				formData.append(key, data_[key])
			}
			fetch('${contextPath}/web/sojt/withdraw.do?mId=121', {
				method: 'POST',
				body: formData
			}).then(response => response.json())
			.then(data => {
				if(data.status === 'success') {
					window.location.href = '${contextPath}/web/sojt/applyList.do?mId=121'
				} else {
					alert('제출 실패: ' + data.message);
				}
			})
		})
		
		$('button#btn-newApply').click(function(e) {
			var form = document.createElement("form");
			form.setAttribute("charset", "UTF-8");
			form.setAttribute("method", "Post");
			form.setAttribute("action", "${contextPath}/web/sojt/applyForm.do?mId=110");
			
			var bscIdx = document.createElement('input');
			bscIdx.setAttribute("type", "hidden");
			bscIdx.setAttribute("name", "id");
			bscIdx.setAttribute("value", $("#id_bsiscnslIdx").val());
			
			var sojtIdx = document.createElement('input');
			sojtIdx.setAttribute("type", "hidden");
			sojtIdx.setAttribute("name", "sojtIdx");
			sojtIdx.setAttribute("value", $("#id_sojtIdx").val());
			
			form.appendChild(bscIdx);
			form.appendChild(sojtIdx);
			document.body.appendChild(form);
			form.submit();
		});
		
		let last_date_ = '${content.LAST_DATE}';
		let date_ = last_date_.split('-')
		let p_e = document.querySelector('p#last-date')
		if(p_e) {
			let result = `\${date_[0]}년 \${date_[1]}월 \${date_[2]}일`;
			p_e.textContent = result;
		}
	})
	
	
	function checkInputs() {
		const prisup = checkRadioGroup('priSupTrgetCorpYn');
		const spcss = checkRadioGroup('spcssBplYn');
		const wgdly = checkRadioGroup('wgdlyNmStgOthbcBplYn');
		const indac = checkRadioGroup('indacmtBplYn');
		const sport = checkRadioGroup('sportExclIndutyYn');
		const passfail = checkRadioGroup('passfail');
		const allPassed = prisup === '2' && spcss === '0' && wgdly === '0' && indac === '0' && sport === '0' && passfail === 'pass'
		button.disabled = !allPassed;
	}
	
	function checkRadioGroup(groupName) {
		let radios = document.getElementsByName(groupName);
		for(let i=0; i<radios.length; i++) {
			if(radios[i].checked) {
				return radios[i].value;
			}
		}
	}
	</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
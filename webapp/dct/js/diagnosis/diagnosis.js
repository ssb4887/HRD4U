var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
var siteId = document.querySelector(".siteId").value;

if(siteId != "dct") {
	var mId = 55
}else {
	var mId = 37
}

// 검색 파라미터 유지
const parameters = new URLSearchParams(window.location.search);
for(const [key, value] of parameters) {
	const element = document.querySelector(`#${key}`);
	if(element) element.value = value;
}

//검색조건 초기화
const initBtn = document.getElementById('btn-init');
initBtn.addEventListener('click', function() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],select');
	textInputs.forEach(elem => {
		elem.value = '';
	});
});


// 기초진단 등록 모달창

$(document).ready(function(){
	hideLoading();

	// 모달창 OPEN/CLOESE
    $(function() {
        $("#open-modal01").on("click", function() {
            $(".mask").fadeIn(150, function() {
                $("#modal-action01").show();
            });
        });

        $("#modal-action01 .btn-modal-close").on("click", function() {
            $("#modal-action01").hide();
            $(".mask").fadeOut(150);
        });
    })
});

/**
 * 엔터키 옵션
 */
document.addEventListener("keyup", function handleEnterKeyPress(event) {
	if(event.code == 'Enter') {
		const divElement =document.querySelector(".mask");
		
		if(divElement && window.getComputedStyle(divElement).display === "block") {
			searchBtn();
			document.removeEventListener("keyup", handleEnterKeyPress);
		}
		
	}
	
	document.addEventListener("keyup", handleEnterKeyPress);
});

/**
 * 기업명 검색
 */
function searchBtn() {
	
		var bplNm =  document.querySelector('.bplNm');
		var bplNo =  document.querySelector('.bplNo');
		
		let check1 = bplNm.value.length
		let check2 = bplNo.value.length
		
		
		if(check1 != 0 || check2 != 0) {
				
				$("#empty").hide();
				
				showLoading();
				
				$.ajax({
					url: `${contextPath}/dct/diagnosis/bskList.do?mId=36`,
					type: 'GET',
					data: {
						searchText1 : bplNm.value,
						searchText2 : bplNo.value
					},
					success: function(data) {
						
						let bplNm = data.searchText1;
						let bplNo = data.searchText2;
						let listItem = data.bskList;
						totalCnt = data.totalCnt;
						
						$("#searchBskList").empty();
						$("#infoBox").empty();
						
						let html = "";
						
						if(listItem != null && listItem.length > 0) {
							listItem.forEach(function(item,idx){
								
								if(item.INSTT_NAME == null) {
									item.INSTT_NAME = '없음';
								}
								
								html += `	
									<tr>
									<td>${idx+1}</td>
									<td><input type="radio" id="radio0101" class="checkbox-type02" name="radio01" onclick="javascript:choiceBtn('${item.BPL_NM}', '${item.BPL_NO}');"></td>
									<td>${item.BPL_NM}</td>
									<td>${item.BPL_NO}</td>
									<td>${item.INSTT_NAME}</td>
									</tr> `
							});
						}else {
							html	+= `<tr><td colspan="5">검색된 기업이 없습니다. </td></tr>`;
						}
						$("#cnt").text(totalCnt);
						$("#searchBskList").append(html);
						
						hideLoading();
					},
					error: function() {
						alert("기업 검색에 실패하였습니다.");
					}
				});
			
		}else{
			alert("기업 검색조건을 입력해주세요.");
			
		}
		
}


/**
 * 특정 기업 선택 및 실행
 */
function choiceBtn(value1, value2) {
	
	let param = {
			'BPL_NM' : value1,
			'BPL_NO' : value2,
	
	}
	
	let BPL_NM = value1;
	let BPL_NO = value2;
	
	$("#infoBox").empty();
	
	let html = "";
		html += `	
		<form id="infoForm" name="infoForm" action="${contextPath}/dct/diagnosis/detailView.do?mId=36" method="POST" onsubmit="return validation()" accept-charset="utf-8">
		<div>
            <div class="contents-box pl0" >
                <h3 class="title-type04">
                    ${BPL_NM} (${BPL_NO})
                </h3>

                <div class="basic-search-wrapper">
                    <div class="one-box">
                        <dl>
                            <dt>
                            <label for="modal-textfield04">
                                직위
                            </label>
                        </dt>
                            <dd>
                                <input type="text" id="modal-textfield03" class="corpOfcps" name="corpOfcps" value="" title="기업담당자 직위 입력" placeholder="기업담당자 직위" size="30" maxlength="30" required>
                            </dd>
                        </dl>
                    </div>
                    <div class="one-box">
                        <dl>
                            <dt>
                            <label for="modal-textfield04">
                                기업담당자
                            </label>
                        </dt>
                            <dd>
                                <input type="text" id="modal-textfield04" class="corpPicNm" name="corpPicNm" value="" title="기업담당자 입력" placeholder="기업담당자" size="30" maxlength="30" required>
                            </dd>
                        </dl>
                    </div>
                    <div class="one-box">
                        <dl>
                            <dt>
                            <label for="modal-textfield05">
                                연락처
                            </label>
                        </dt>
                            <dd>
                                <input type="text" id="modal-textfield05" class="corpPicTel" name="corpPicTel" value="" title="연락처 입력" onkeyup="validation();" oninput="formatPhoneNumber(this);" placeholder="연락처(000-0000-000)" size="30" maxlength="30" required>
                            </dd>
                            <dd id="telError" style="display: none; color: red;"></dd>
                        </dl>
                    </div>
                    <div class="one-box">
                        <dl>
                            <dt>
                            <label for="modal-textfield06">
                                이메일
                            </label>
                        </dt>
                            <dd>
                                <input type="text" id="modal-textfield06" class="corpPicEmail" name="corpPicEmail" value="" title="이메일 입력" onkeyup="validation();" placeholder="이메일(@mail.com)" size="40" maxlength="40">
                            </dd>
							<dd id="emailError" style="display: none; color: red;"></dd>
                        </dl>
                    </div>
                </div>
            </div>
			<div class="basic-search-wrapper">
			    <div class="one-box">
			        <dl>
			            <dt>
			                <label for="submitMtd">보고서 전달방법</label>
			            </dt>
			            <dd>
			                <select id="submitMtd" name="submitMtd" required>
			                    <option value="">선택</option>
			                    <option value="Y">대면</option>
			                    <option value="N">비대면</option>
			                </select>
			            </dd>
			        </dl>
			    </div>
			</div>
            
            <input type="hidden" name="BPL_NM" value="${BPL_NM}">
			<input type="hidden" name="BPL_NO" value="${BPL_NO}">
            
		 <div class="btns-area">
          <button type="submit" id="apply" class="btn-b02 round01 btn-color03 left">
              <span>
                  기초진단 실행
              </span>
              <img src="${contextPath}/dct/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
          </button>
		</div>
		</div>
		</form>`
		
		$("#infoBox").append(html);
		
//		document.getElementById('infoBox').scrollIntoView({behavior:'auto'});
				
}


function showLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	loader.style.display = 'block';
	overlay.style.display = 'block';
	
}


function hideLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	loader ? loader.style.display = 'none' : null;
	overlay ? overlay.style.display = 'none' : null;
	
}

/**
 * 유효성 검사
 */
function validation() {
	
	let subYN = document.getElementById('submitMtd').value;
	console.log("subYN : "+subYN);
	
	let emailRegex = /^[0-9a-zA-Z._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	let telRegex = /^([0-9]{2,3})-?([0-9]{3,4})-?([0-9]{4})$/;
	
	// 휴대전화번호
		let telInput = document.querySelector(".corpPicTel");
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
		let emailInput = document.querySelector(".corpPicEmail");
		let emailError = document.getElementById("emailError");
		let enteredEmail = emailInput.value;
		
		emailError.style.display = 'block';
		
		if(!emailRegex.test(enteredEmail) && enteredEmail.length != 0) {
// emailInput.style.border = "2px solid #1e89e9";
			emailError.innerHTML = "유효한 이메일 형식이 아닙니다.";
			return false;
		}else {
			emailInput.style.border = "1px solid #f1f3f5 font-size: 13px";
			emailError.innerHTML = "";
			emailError.style.display = 'none';
		}

}

// 기업검색시 사업장관리번호 입력값 제한 설정
document.querySelector('.bplNo').oninput = function() {
	this.value = this.value.replace(/\D/g, '');
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

//기초진단
const bscButtons = document.querySelectorAll('.btn-idx');
bscButtons.forEach(function(bscBtn) {
	bscBtn.addEventListener('click', function(event) {
		const idx = bscBtn.getAttribute('data-idx');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/diagnosis/view.do?mId=36`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});

// 설문조사
const qustnrButtons = document.querySelectorAll('.btn-qustnr');
qustnrButtons.forEach(function(qustnrBtn) {
	qustnrBtn.addEventListener('click', function(event) {
		const rslt = qustnrBtn.getAttribute('data-rslt');
		const bsc = qustnrBtn.getAttribute('data-bsc');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/bsisCnsl/qustnr.do?mId=`+mId;
		form.action = actionUrl;
		document.getElementById('rslt').value = rslt;
		document.getElementById('bsc').value = bsc;
		form.submit();
	});
});

// 기초컨설팅
const cnsltButtons = document.querySelectorAll('.btn-cnslt');
cnsltButtons.forEach(function(cnsltBtn) {
	cnsltBtn.addEventListener('click', function(event) {
		const rslt = cnsltBtn.getAttribute('data-rslt');
		const bsc = cnsltBtn.getAttribute('data-bsc');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/bsisCnsl/cnslt.do?mId=`+mId;
		form.action = actionUrl;
		document.getElementById('rslt').value = rslt;
		document.getElementById('bsc').value = bsc;
		form.submit();
	});
});

// 엑셀 다운로드
const excelBtn = document.getElementById('btn-excel');
if(excelBtn) {
	excelBtn.addEventListener('click', function() {
		const form = document.getElementById('form-box');
		const queryString = window.location.search;
		const actionUrl = `${contextPath}/`+siteId+`/diagnosis/excel.do` + queryString;
		form.action = actionUrl;
		form.submit();
	});
}
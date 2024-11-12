var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;

// 기초진단 신청>기업담당자 정보 등록 모달창

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

function applyBtn(values) {
	
	const valueArray = values.split(',');
	
	let BPL_NO = valueArray[0];
	let MEMBER_NAME = valueArray[1];
	let MEMBER_EMAIL = valueArray[2];
	
	
	$("#infoBox").empty();
	
	let html = "";
		html += `	
		<form id="infoForm" name="infoForm" action="${contextPath}/web/diagnosis/detailView.do?mId=53" method="POST" onsubmit="return validation()" >
		<div>
            <div class="contents-box pl0" >
                <div class="basic-search-wrapper">
                    <div class="one-box">
                        <dl>
                            <dt>
                            <label for="modal-textfield04">
                                직위
                            </label>
                        </dt>
                            <dd>
                                <input type="text" id="modal-textfield03" class="corpOfcps" name="corpOfcps" value="" title="기업담당자 직위 입력" placeholder="기업담당자 직위" size="30" maxlength="30">
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
                                <input type="text" id="modal-textfield04" class="corpPicNm" name="corpPicNm" value="${MEMBER_NAME}" title="기업담당자 입력" placeholder="기업담당자" size="30" maxlength="30">
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
                                <input type="text" id="modal-textfield05" class="corpPicTel" name="corpPicTel" value="" title="연락처 입력" onkeyup="validation();" oninput="formatPhoneNumber(this);" placeholder="연락처(000-0000-000)" size="30" maxlength="30">
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
                                <input type="text" id="modal-textfield06" class="corpPicEmail" name="corpPicEmail" value="${MEMBER_EMAIL}" title="이메일 입력" onkeyup="validation();" placeholder="이메일(@mail.com)" size="40" maxlength="40">
                            </dd>
							<dd id="emailError" style="display: none; color: red;"></dd>
                        </dl>
                    </div>
                </div>
            </div>
            
            <input type="hidden" name="BPL_NO" value="${BPL_NO}">
            
		 <div class="btns-area">
          <button type="submit" id="apply" class="btn-b02 round01 btn-color03 left">
              <span>
                  기초진단 실행
              </span>
              <img src="${contextPath}/web/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
          </button>
		</div>
		</div>
		</form>`
		
		$("#infoBox").append(html);
	
}

/**
 * 유효성 검사
 */
function validation() {
	
	let emailRegex = /^[0-9a-zA-Z._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	let telRegex = /^([0-9]{2,3})-?([0-9]{3,4})-?([0-9]{4})$/;
	
	// 휴대전화번호
		let telInput = document.querySelector(".corpPicTel");
		let telError = document.getElementById("telError");
		let enteredTel = telInput.value;
		
		telError.style.display = 'block';
		
		if(!telRegex.test(enteredTel) && enteredTel.length != 0) {
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
			emailError.innerHTML = "유효한 이메일 형식이 아닙니다.";
			return false;
		}else {
			emailInput.style.border = "1px solid #f1f3f5 font-size: 13px";
			emailError.innerHTML = "";
			emailError.style.display = 'none';
		}

}

//기업담당자 연락처 입력시 전화번호 입력 포맷팅
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
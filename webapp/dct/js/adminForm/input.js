var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

hideLoading();

// 필수 입력값 검사
const inputItem = document.getElementById('admsfmNm');
const fileItem = document.querySelector('.fileName');

/**
 * 저장/완료 모달창
 * 
 */

let action = null;

// 저장 모달창 열기
$(".open-modal01").on("click", function() {
	action = "temp";
   $(".modal-wrapper").hide();
   $(".mask").fadeIn(150, function() {
       $("#modal-alert01").show();
   });
});

// 완료 모달창 열기
$(".open-modal02").on("click", function() {
	action = "done";
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-alert01").show();
	});
});


// 모달창 닫기
let closeBtn = document.querySelectorAll('.btn-modal-close, .modal-close ');
closeBtn.forEach(function(button) {
	button.addEventListener('click', function() {
		$(".modal-wrapper").hide();
		$(".mask").hide();
	});
});

// radio 사용/미사용 표시
let useYn = document.getElementById('useYn').value;
if(useYn != null) {
	if(useYn != 'N') {
		document.getElementById("radio0101").checked = true;
	}else {
		document.getElementById("radio0102").checked = true;
	}
}

// 파일 업로드 유효성 검사
let fileChange = null;
function displayFileName() {
	const fileInput = document.getElementById('file');
	let fileNameInput = document.querySelector('.fileName');
	
	let file = fileInput.files[0];
	let allowedExtensions = /(\.pdf)$/i;
	
	if(!allowedExtensions.exec(file.name)) {
		alert('PDF형식의 파일만 등록이 가능합니다. ');
		fileInput.value = '';
		return false;
	}
	
	let defaultValue = document.getElementById('defaultValue').value;
	let btnDiv = document.querySelector('.btns-right');
	let button = document.createElement("button");
	let tempBtn = document.getElementById('tempBtn');
	let editBtn = document.querySelector('.edit');
	let newBtn = document.querySelector('.uploadEFile');
	
	
	if(defaultValue != null && defaultValue.length != 0 ) {
		if(defaultValue != file.name) {
			fileChange = 'true';
			
			if(editBtn != null) {
				editBtn.style.display = "none";
			
				button.type= "button";
				button.className= "uploadEFile btn-m01 btn-color04";
				button.style="margin-right: 5px";
				button.innerHTML= "전자서식 생성";
				button.onclick= eFormSave;
				
				btnDiv.insertBefore(button, tempBtn);
			}		
			
		}else {
			fileChange = 'false';
			editBtn.style.display = "";
			
			if(newBtn !== null) {
				btnDiv.removeChild(newBtn);
			}
		}
	}
	
	fileNameInput.value = file? file.name : '';
	
}

// 유효성 검사
function validationCheck() {
	
	const selectItem_prtbiz = document.getElementById('prtbizIdx');
	const selectItem_jobType = document.getElementById('jobType');
	const selectItem_sgntr = document.getElementById('sgntr');
	
	var isValid = true;
	
	if(selectItem_prtbiz.value.length == "") {
		alert("사업 구분을 선택해주세요.");
		selectItem_prtbiz.focus();
		return false;
	}else if(selectItem_jobType.value.length == "") {
		alert("업무 구분을 선택해주세요.");
		selectItem_jobType.focus();
		return false;
	}else if(selectItem_sgntr.value.length == "") {
		alert("서명 대상자를 선택해주세요.");
		selectItem_sgntr.focus();
		return false;
	}
	
	let forbiddenChar = [ '[', ']' ];
	
	if(inputItem.value.length == 0 ) {
		alert("서식명을 입력해주세요.");
		inputItem.focus();
		return false;
	}else {
		for(var i=0; i < forbiddenChar.length; i++) {
			if(inputItem.value.includes(forbiddenChar[i])) {
				alert('서식명에 특수문자 "[" 또는 "]" 를 사용할 수 없습니다.');
				
				inputItem.focus()
				return false;
			}
			
		}
	}
		
	if(!fileItem.value) {
		alert("서식 원본파일을 첨부해주세요.");
		fileItem.focus();
		return false;
	}
	
	return isValid;
	
}

// 전자서식 저장 처리
function eFormSave() {
	
	if(inputItem.value.length == 0 ) {
		alert("서식명을 입력해주세요.");
		inputItem.focus();
		return false;
	}
	
	
	if(!fileItem.value) {
		alert("서식 원본파일을 첨부해주세요.");
		fileItem.focus();
		return false;
	}

	var docKey = document.getElementById('admsfmNm').value;
	var fileInput = document.getElementById('file');
	var file = fileInput.files[0];
	
	var formData = new FormData();
	formData.append('file', file);
	formData.append('docKey', docKey);
	
	fetch(`${contextPath}/dct/simpleSign/input.do?mId=142`, {
		method: 'POST',
		body: formData
	})
	.then(response => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then(data => {
		let SSSIMPLESIGN_URL = "http://165.213.109.69:18080/ssign";
		
		let formId = data.formId;
		let editUrl = SSSIMPLESIGN_URL + data.editUrl;
		let token = data.token;
		console.log(JSON.stringify(data,2,null))
		
		let formatId = document.getElementById('formatId');
		let formatCours = document.getElementById('formatCours');
//		console.log(editUrl)
		
		formatId.value = formId;
		formatCours.value = editUrl;
		
		// 전자서식 편집 버튼 생성
		let btnDiv = document.querySelector('.btns-right');
		let button = document.createElement("button");
		let tempBtn = document.getElementById('tempBtn');
		
		document.querySelector('.uploadEFile').style.display = "none";
		
		button.type= "button";
		button.className= "edit btn-m01 btn-color05";
		button.style="margin-right: 5px";
		button.innerHTML= "전자서식 편집";
		button.onclick= () => saveCookies();
		
		btnDiv.insertBefore(button, tempBtn);

		alert("전자서식이 생성되었습니다.");

		openNewWindow(token,editUrl);
		
		
	})
	.catch(error => {
		console.log("Fetch error : ", error)
	})
}

// 저장||완료 처리
let isProgress = false;
function save() {
	var isValid = validationCheck();
	
	if(isProgress) {
//		console.log('중복클릭!!!');
		return;
	}
	
	isProgress = true;
	if(isValid) {
		let msg = '';
		if(action === 'temp' || action === 'done') {
			const formData = new FormData(document.getElementById('adminForm'));
			formData.append('action', action);
			
			$.ajax({
				type: 'POST',
				url: `${contextPath}/dct/adminForm/inputProc.do?mId=114`,
				data: formData,
				processData: false,
				contentType: false,
				success: function(response) {
					isProgress = false;
					if(action == 'temp') {
						msg ='정상적으로 저장되었습니다.';
						window.location.href=`${contextPath}/dct/adminForm/list.do?mId=114`
					}else {
						msg ='정상적으로 등록되었습니다.'
						window.location.href=`${contextPath}/dct/adminForm/list.do?mId=114`
					}
					alert(msg);
				},
				error: function(xhr, status, error) {
					if(action == 'temp') {
						msg ='저장에 실패했습니다.';
					}else {
						msg ='등록에 실패했습니다.'
					}
					alert(msg);
				}
			});
		}
	}
}

//토큰 및 주소정보 조회
function saveCookies() {
	
	let formatId = document.getElementById('formatId').value;
//	console.log(formatId)
	
	const url = `${contextPath}/dct/simpleSign/edit.do?mId=142`;
	
	showLoading();

	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type' : 'application/json; charset=utf-8',
		},
		body: formatId
	})
	.then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		const url = data.editUrl;
		const token = data.token;
		
		hideLoading();
		openNewWindow(token, url);
	})
	.catch(error => {
		console.log("saveCookie error : ", error)
	})
	
}

function openNewWindow(token,url) {
	
	if(url == undefined) {
		url = document.getElementById('formatCours').value;
		console.log("undefined url : " +url);
		
	}

	// 팝업창 크기 및 위치 설정
	let popupWidth = 1450;
	let popupHeight = 780;
	let leftPosition = (window.innerWidth - popupWidth) / 2;
	let topPosition = (window.innerHeight - popupHeight) / 2;

	if(!token) {
		console.log("토큰이 없습니다.");
		return;
	}

    const newWindow = window.open(url+'?t='+token, '_blank', 'width='+popupWidth+', height='+popupHeight+', left='+leftPosition+', top='+topPosition);
    if(newWindow) {
        newWindow.focus();
	}else {
		console.log("팝업열기 오류");
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
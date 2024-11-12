var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

document.addEventListener('DOMContentLoaded', function() {
	hideLoading();
	
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
	}
});

// modal open, close
$('#btn-excelUpload').on('click', function() {
	$('.mask').fadeIn(150, function() {
		$('#modal-excelupload').show();
	});
	
	$('#modal-excelupload .btn-modal-close').on('click', function() {
		$('#modal-excelupload').hide();
		$('.mask').fadeOut(150);
	});
});

// 엑셀 업로드
const btnUpload = document.getElementById('btn-upload');
if(btnUpload) {
	btnUpload.addEventListener('click', function() {
		// excel 확장자 제한
		const uploadFile = document.getElementById('input-file');
		const allowedExtensions = ['.xlsx'];
		
		const excelUploadForm = document.getElementById('excelUploadForm');
		const formData = new FormData();
		
		formData.append('excelfile', uploadFile.files[0]);
		
		if(uploadFile.files.length > 0) {
			const fileName = uploadFile.files[0].name;
			const fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
			if(!allowedExtensions.includes(fileExtension)) {
				alert('허용되지 않는 확장자 입니다.\n업로드 가능한 확장자는 [.xlsx] 입니다.')
				return ;
			}
			
			const maxFileNameLength = 240;
			if(fileName.length > maxFileNameLength) {
				alert(`파일명이 너무 깁니다. 240자 내로 변경 후 다시 업로드해주세요.\n(파일명: ${fileName})`);
				return;
			}
		}
		
		showLoading();
		
		fetch(`${contextPath}/dct/crdns/uploadExcel.do?mId=70`, {
			method: 'POST',
			body: formData
			
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				hideLoading();
			}
			return response.json();
		})
		.then((data) => {
			hideLoading();
			if(data.result === 'fail') {
				alert(data.msg);
			} else if(data.result === 'success') {
				alert(data.msg);
			} else {
				alert('엑셀 업로드에 실패하였습니다. 엑셀 재확인 후 다시 시도해주세요.');
			}
			window.location.reload();
		});
	});
}

// 선택삭제
const btnDelete = document.getElementById('btn-delete');
if(btnDelete) {
	btnDelete.addEventListener('click', function() {
		const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-cmptinst"]:not(#checkbox-all)');
		const selectedValues = [];
		checkboxes.forEach(checkbox => {
			if(checkbox.checked) {
				selectedValues.push(checkbox.value);
			}
		});
		
		if(selectedValues.length < 1) {
			alert('삭제할 유관기관을 선택해주세요.');
			return;
		}
		
		const formData = new FormData();
		formData.append('selectedValues', JSON.stringify(selectedValues));
		
		showLoading();
		fetch(`${contextPath}/dct/crdns/delete.do?mId=70`, {
			method: 'POST',
			body: formData
			
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			alert(`${data.result}건의 삭제가 완료되었습니다.`);
			window.location.reload();
			hideLoading();
		});
		
	});
}

// 엑셀다운로드
const btnExceldownload = document.getElementById('btn-excelDownload');
if(btnExceldownload) {
	btnExceldownload.addEventListener('click', function() {
		const form = document.getElementById('form-box');
		const queryString = window.location.search;
		const actionUrl = `${contextPath}/dct/crdns/downloadExcel.do${queryString}`;
		form.action = actionUrl;
		form.submit();
	});
}

// 검색 초기화
const initBtn = document.getElementById('btn-init');
if(initBtn) {
	initBtn.addEventListener('click', function() {
		const form = document.querySelector('.basic-search-wrapper');
		const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
		textInputs.forEach(elem => {
			elem.value = '';
		});
	});
}

// 상세 페이지
const viewButtons = document.querySelectorAll('.btn-view');
viewButtons.forEach(function(viewBtn) {
	viewBtn.addEventListener('click', function(event) {
		const idx = viewBtn.getAttribute('data-idx');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/dct/crdns/view.do?mId=70`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});

// checkbox
const checkAllBtn = document.getElementById('checkbox-all');
const checkboxBtns = document.querySelectorAll('input[name="checkbox-cmptinst"]:not(#checkbox-all)');
if(checkAllBtn) {
	checkAllBtn.addEventListener('click', function() {
		for(let i=0;i < checkboxBtns.length ;i++) {
			checkboxBtns[i].checked = checkAllBtn.checked;
		}
	});
}

for(var i=0;i<checkboxBtns.length ;i++) {
	checkboxBtns[i].addEventListener('click', function() {
		const allChecked = Array.from(checkboxBtns).every(checkbox => checkbox.checked);
		const allUnchecked = Array.from(checkboxBtns).every(checkbox => !checkbox.checked);
		
		if(allChecked) {
			checkAllBtn.checked = true;
		} else if(allUnchecked) {
			checkAllBtn.checked = false;
		} else {
			checkAllBtn.checked = false;
		}
	});
}

const insertBtn = document.getElementById('btn-insert');
if(insertBtn) {
	insertBtn.addEventListener('click', function() {
		const form = document.getElementById('form-box');
		const originFormData = new FormData(form);
		const formData = new FormData();
		
		for(const pair of originFormData) {
			const key = pair[0];
			const value = pair[1];
			const trimedValue = value.trim();
			formData.append(key, trimedValue);
		}
		
		const zip = document.querySelector('input[name="zip"]').value;
		const addr = document.querySelector('input[name="addr"]').value;
		formData.append('zip', zip);
		formData.append('addr', addr);
		
		const need = document.getElementById('textfield01');
		const needValue = need.value.trim();
		if(needValue === '') {
			alert('기관명을 입력해주세요.');
			need.value = '';
			need.focus();
			return;
		}
		
		// TODO: validation check(validateTextLength)
		
		fetch(`${contextPath}/dct/crdns/insert.do?mId=70`, {
			method : 'POST',
			body : formData
		})
		.then((response) => {
			if(!response.ok) {
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			console.log(`data :: ${JSON.stringify(data, null, 2)}`);
			if(data.success > 0) {
				alert("유관기관 등록에 성공하였습니다.");
				if(data.flag === 'INSERT') {
					const form = document.createElement('form');
					form.method = 'POST';
					form.action = `${contextPath}/dct/crdns/view.do?mId=70`;
					const input = document.createElement('input');
					input.value = data.idx;
					input.name = "idx";
					form.appendChild(input);
					document.body.appendChild(form);
					form.submit();
				} else {
					window.location.reload(true);
				}
			} else {
				alert("유관기관 등록 중 에러가 발생하였습니다.\n관리자에게 문의해주세요.")
			}
			hideLoading();
		})
	});
}

const btnAddr = document.getElementById('btn-addr');
if(btnAddr) {
	btnAddr.addEventListener('click', function() {
		new daum.Postcode({
			oncomplete: function(data) {
				let addr = '';
				
				if(data.userSelectedType === 'R') {
					addr = data.roadAddress;
				} else {
					addr = data.jibunAddress;
				}
				
				document.getElementById('zip').value = data.zonecode;
				document.getElementById('addr').value = addr;
				document.getElementById('textfield02').focus();
			}
		}).open();	
	});
}

function goNtwrk(idx) {
	const form = document.getElementById('form-box');
	const actionUrl = `${contextPath}/dct/ntwrk/view.do?mId=71`;
	form.action = actionUrl;
	if(idx !== undefined && idx !== null && idx !== '') {
		document.getElementById('idx').value = idx;
	} else {
		document.getElementById('idx').value = '';
	}
	form.submit();
}

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

function validationEmail(input) {
	let emailRegex = /^[0-9a-zA-Z._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	let inputValue = input.value;
	let emailError = document.getElementById('emailError');
	if(!emailRegex.test(inputValue) && inputValue.length != 0) {
		emailError.style.display = 'block';
		emailError.innerHTML = '유효한 이메일 형식이 아닙니다.';
		return false;
	} else {
		emailError.innerHTML = '';
		emailError.style.display = 'none';
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
	loader.style.display = 'none';
	overlay.style.display = 'none';
}

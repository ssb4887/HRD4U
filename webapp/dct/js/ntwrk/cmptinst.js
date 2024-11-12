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
				alert('허용되지 않는 확장자 입니다.\n업로드 가능한 확장자는 [.xlsx] 입니다.');
				return ;
			}
			
			const maxFileNameLength = 240;
			if(fileName.length > maxFileNameLength) {
				alert('파일명이 너무 깁니다. 240자 내로 변경 후 다시 업로드해주세요.');
				return;
			}
		}
		
		showLoading();
		
		fetch(`${contextPath}/dct/cmptinst/uploadExcel.do?mId=69`, {
			method: 'POST',
			body: formData
			
		}).then((response) => {
			if(!response.ok){
				alert('엑셀 업로드 중 오류가 발생하였습니다.\n관리자에게 문의해주세요.');
				hideLoading();
				throw new Error("HttpStatus is not ok");
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
			alert('삭제할 협의체를 선택해주세요.');
			return;
		}
		
		const formData = new FormData();
		formData.append('selectedValues', JSON.stringify(selectedValues));
		
		showLoading();
		fetch(`${contextPath}/dct/cmptinst/delete.do?mId=69`, {
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
		const actionUrl = `${contextPath}/dct/cmptinst/downloadExcel.do${queryString}`;
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
		const actionUrl = `${contextPath}/dct/cmptinst/view.do?mId=69`;
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
		
		for(const [key, value] of formData) {
			console.log(key, value);
		}
		
		showLoading();
		fetch(`${contextPath}/dct/cmptinst/insert.do?mId=69`, {
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
				alert("협의체 등록에 성공하였습니다.");
				if(data.flag === 'INSERT') {
					const form = document.createElement('form');
					form.method = 'POST';
					form.action = `${contextPath}/dct/cmptinst/view.do?mId=69`;
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
				alert("협의체 등록 중 에러가 발생하였습니다.\n관리자에게 문의해주세요.")
			}
			hideLoading();
		});
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

// 협약기업 모달
function openAgremCorpModal() {
	$('.mask').fadeIn(150, function() {
		$('#modal-excelupload').show();
	});
	
	$('#modal-excelupload .btn-modal-close').on('click', function() {
		$('#modal-excelupload').hide();
		$('.mask').fadeOut(150);
	});
}

// 협약기업 엑셀 업로드
function uploadAgremCorpExcel() {
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
	}
	
	const cmptinstIdx = document.querySelector('input[name="cmptinstIdx"]').value;
	formData.append('cmptinstIdx', cmptinstIdx);
	
	showLoading();
	
	fetch(`${contextPath}/dct/cmptinst/uploadAgremExcel.do?mId=69`, {
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
		hideLoading();
		console.log(`data :: ${JSON.stringify(data, null, 2)}`);
		if(data.result > 0) {
			alert(`업로드가 완료되었습니다.\n총 ${data.result}건의 협약기업이 추가되었습니다.`);
			window.location.reload();
		}
	});
}

function openAgremCorpAddModal() {
	$('.mask').fadeIn(150, function() {
		$('#modal-agremCorp').show();
		const modal = document.querySelector('#modal-agremCorp');
		const modalInputs = modal.querySelectorAll('input[type="text"],input[type="hidden"],input[type="number"],select');
		modalInputs.forEach(elem => {
			if(elem.tagName.toLowerCase() === 'select') {
				elem.selectedIndex = 0;
			} else {
				elem.value = '';
			}
		});
	});
	
	$('#modal-agremCorp .btn-modal-close').on('click', function() {
		$('#modal-agremCorp').hide();
		$('.mask').fadeOut(150);
	});
}

function saveAgremCorp() {
	const form = document.getElementById('agremCorpForm');
	const formData = new FormData(form);
	
	const cmpnyNm = document.getElementById('agrem-textfield01').value;
	if(cmpnyNm.trim() === '') {
		alert('회사명을 입력해주세요.');
		return;
	}
	
	const bplNo = document.querySelector('input[name="bplNo"]').value;
	if(bplNo.length !== 11) {
		alert("사업장 관리번호는 11자리 숫자여야 합니다.");
		return;
	}

	const bizrNo = document.querySelector('input[name="bizrNo"]').value;
	if(bizrNo.length !== 10) {
		alert("사업자 번호는 10자리 숫자여야 합니다.");
		return;
	}
	
	const agremNo = document.querySelector('input[name="agremNo"]').value;
	if(agremNo.length !== 10) {
		alert("협약번호는 10자리 숫자여야 합니다.");
		return;
	}

	const agremYear = document.querySelector('input[name="agremYear"]').value;
	if(agremYear.length !== 4) {
		alert("협약년도는 4자리 숫자여야 합니다.");
		return;
	}
		
	const cmptinstIdx = document.querySelector('input[name="cmptinstIdx"]').value;
	formData.append('cmptinstIdx', cmptinstIdx);
	
	const agremCorpIdx = document.getElementById('agremCorpIdx').value;
	if(!isEmpty(agremCorpIdx)) {
		formData.append('agremCorpIdx', agremCorpIdx);
	}
	
	fetch(`${contextPath}/dct/cmptinst/insertAgremCorp.do?mId=69`, {
		method: 'POST',
		body: formData
		
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			window.location.reload();
		}
		return response.json();
	})
	.then((data) => {
		hideLoading();
		if(data.result > 0) {
			alert(`저장이 완료되었습니다.`);
			window.location.reload();
		} else {
			alert('저장에 실패하였습니다.\n다시 시도해주세요.');
		}
	});
}

function deleteSelectedAgremCorp() {
	const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-cmptinst"]:not(#checkbox-all)');
	const selectedValues = [];
	checkboxes.forEach(checkbox => {
		if(checkbox.checked) {
			selectedValues.push(checkbox.value);
		}
	});
	
	if(selectedValues.length < 1) {
		alert('삭제할 협약기업을 선택해주세요.');
		return;
	}
	
	const formData = new FormData();
	formData.append('selectedValues', JSON.stringify(selectedValues));
	for(const[key, value] of formData) {
		console.log(key, value);
	}
	
	showLoading();
	fetch(`${contextPath}/dct/cmptinst/deleteAgremCorp.do?mId=69`, {
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

function openModalForModify(e) {
	const agremCorpIdx = e.currentTarget.getAttribute('data-idx');
	$('.mask').fadeIn(150, function() {
		$('#modal-agremCorp').show();
		const modal = document.querySelector('#modal-agremCorp');
		const modalInputs = modal.querySelectorAll('input[type="text"],input[type="hidden"],input[type="number"],select');
		modalInputs.forEach(elem => {
			if(elem.tagName.toLowerCase() === 'select') {
				elem.selectedIndex = 0;
			} else {
				elem.value = '';
			}
		});
		
		const wrapperId = `agremCorp_${agremCorpIdx}`;
		const wrapper = document.getElementById(wrapperId);
		if(wrapper) {
			document.getElementById('agremCorpIdx').value = agremCorpIdx;
			
			let elementsWithDataKey = wrapper.querySelectorAll('[data-key]');
			elementsWithDataKey.forEach(function(element) {
				const attrKey = element.getAttribute('data-key');
				const attrValue = element.textContent;
				const formElement = document.querySelector(`#agremCorpForm [name="${attrKey}"]`);
				if(formElement) {
					if(formElement.tagName.toLowerCase() === 'input') {
						formElement.value = attrValue;
					} else {
						selectOptionByText(formElement, attrValue);
					}
				}
			});
		}
	});
	
	$('#modal-agremCorp .btn-modal-close').on('click', function() {
		$('#modal-agremCorp').hide();
		$('.mask').fadeOut(150);
	});
}

function selectOptionByText(selectElement, text) {
	for(let i=0;i < selectElement.options.length; i++) {
		if(selectElement.options[i].text === text) {
			selectElement.options[i].selected = true;
			return;
		}
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

const isEmpty = (input) => {
	if(typeof input === "undefined" ||
		input === null || 
		input === "" || 
		input === "null" || 
		input.length === 0 || 
		(typeof input === "object" && !Object.keys(input).length)
	) {
		return true;
	} else {
		return false;
	}
}

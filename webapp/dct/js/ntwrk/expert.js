var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
const ALLOWED_EXTENSIONS = ['.txt', '.xlsx', '.xls', '.pdf', '.hwp', '.doc', '.ppt', '.pptx', '.jpeg', '.jpg', '.png'];

document.addEventListener('DOMContentLoaded', function() {
	hideLoading();
	
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
	}
});

function initSearhParams() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
	textInputs.forEach(elem => {
		elem.value = '';
	});
}

//checkbox
const checkAllBtn = document.getElementById('checkbox-all');
const checkboxBtns = document.querySelectorAll('input[name="checkbox-expert"]:not(#checkbox-all)');
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

function goView() {
	const button = event.currentTarget;
	const idx = button.getAttribute('data-idx');
	const form = document.getElementById('form-box');
	if(idx) {
		document.getElementById('idx').value = idx;
	}
	const actionUrl = `${contextPath}/dct/expert/view.do?mId=107`;
	form.action = actionUrl;
	form.submit();
}

function searchAddr() {
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
			document.getElementById('addrDtl').focus();
		}
	}).open();
}

function addFile() {
	const fileContainer = document.getElementById('add-file-area');
	const listItemCount = fileContainer.childElementCount;
	
	if(listItemCount > 4) {
		alert('첨부파일은 최대 5개까지 업로드 가능합니다.');
		return;
	}
	
	const newFileInput = document.createElement('input');
	newFileInput.type="file";
	newFileInput.name="files";
	newFileInput.style.display="none";
	
	const lastId = document.querySelectorAll('input[type="file"]').length;
	newFileInput.addEventListener('change', function() {
		if(newFileInput.files.length > 0) {
			const fileName = newFileInput.files[0].name;
			const fileId = `add-file${lastId}`;
			
			const newLi = document.createElement('li');
			const checkbox = document.createElement('input');
			checkbox.type = "checkbox";
			checkbox.id = fileId;
			checkbox.className = 'checkbox-type01';
			
			const label = document.createElement('label');
			label.htmlFor = fileId;
			label.textContent = fileName;
			
			if(isSameFileExists(fileName)) {
				alert('이미 같은 이름의 파일이 추가되어있습니다.');
				return;
			}
			
			const maxFileNameLength = 240;
			if(fileName.length > maxFileNameLength) {
				alert(`파일명이 너무 깁니다. 240자 내로 변경 후 다시 업로드해주세요.\n(파일명: ${fileName})`);
				return;
			}
			
			newLi.appendChild(checkbox);
			newLi.appendChild(label);
			newLi.appendChild(newFileInput);
			
			fileContainer.appendChild(newLi);
		} else {
			console.log('File selection canceled');
			fileContainer.removeChild(newFileInput);
		}
	});
	
	newFileInput.click();
}

function isSameFileExists(newLabelText) {
	const labelElements = document.querySelectorAll('#add-file-area li label');
	for(const labelElement of labelElements) {
		if(labelElement.textContent.trim() === newLabelText.trim()) {
			return true;
		}
	}
	return false;
}

function moveUp() {
	const fileList = document.getElementById('add-file-area');
	const checkedItems = getCheckedItems();
	checkedItems.forEach(item => {
		const prevSibling = item.previousElementSibling;
		if(prevSibling) {
			fileList.insertBefore(item, prevSibling);
		}
	});
}

function moveDown() {
	const fileList = document.getElementById('add-file-area');
	const checkedItems = getCheckedItems();
	checkedItems.reverse().forEach(item => {
		const nextSibling = item.nextElementSibling;
		if(nextSibling) {
			fileList.insertBefore(nextSibling, item);
		}
	});
}

function getCheckedItems() {
	const fileList = document.getElementById('add-file-area');
	const items = fileList.querySelectorAll('li');
	const checkedItems = [];
	items.forEach(item => {
		const checkbox = item.querySelector('input[type="checkbox"]');
		if(checkbox.checked) {
			checkedItems.push(item);
		}
	});
	
	return checkedItems;
}

function deleteChecked() {
	const fileList = document.getElementById('add-file-area');
	const checkedItems = getCheckedItems();
	checkedItems.forEach(item => {
		fileList.removeChild(item);
	});
}

function validateFileExtension(fileName) {
	if(fileName) {
		const fileExtension = fileName.slice(((fileName.lastIndexOf('.') -1) >>> 0) + 2);
		return ALLOWED_EXTENSIONS.includes('.' + fileExtension.toLowerCase());
	}
	
	return false;
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

function addInstt() {
	const selectElement = document.getElementById('partnerInsttIdxs');
	const selectedValue = selectElement.value;
	const selectedText = selectElement.options[selectElement.selectedIndex].textContent;
	
	if(isEmpty(selectedValue)) {
		alert('추가할 소속기관을 선택해주세요.');
		return;
	}
	
	const existingElement = document.querySelector(`#partnerInstt-wrapper .word button[data-key="${selectedValue}"]`);
	if(!existingElement) {
		let partnerInstt = `
			<p class="word">
				<strong>${selectedText}</strong>
				<button type="button" class="btn-delete" name="partnerInstt" data-key="${selectedValue}" onclick="deletePartnerInstt();" >삭제</button>
			</p>`;
		const wrapper = document.getElementById('partnerInstt-wrapper');
		wrapper.innerHTML += partnerInstt;
	} else {
		alert('이미 추가된 기관입니다.');
	}
}

function deletePartnerInstt() {
	const clickedBtn = event.target;
	const key = clickedBtn.getAttribute('data-key');
	
	const wordElement = clickedBtn.closest('.word');
	if(wordElement) wordElement.remove();
}

function saveExpert() {
	const expertNameEl = document.getElementById('name');
	const expertName = expertNameEl.value.trim();
	console.log(expertName);
	if(isEmpty(expertName)) {
		alert('전문가 성명을 입력해주세요.');
		expertNameEl.focus();
		return;
	}
	
	const spcltyRealmEl = document.getElementById('spcltyRealm');
	const spcltyRealm = spcltyRealmEl.value.trim();
	if(isEmpty(spcltyRealm)) {
		alert('전문 분야를 입력해주세요.');
		spcltyRealmEl.focus();
		return;
	}
	
	const form = document.getElementById('form-expert');
	const formData = new FormData(form);
	
	const fileInputs = document.querySelectorAll('input[name="files"]');
	let allFilesValid = true;
	let invalidFileNames = [];
	fileInputs.forEach(fileInput => {
		if(fileInput.files.length > 0) {
			const files = fileInput.files;
			for(let i=0;i < files.length; i++) {
				const fileName = files[i].name;
				if(!validateFileExtension(fileName)) {
					invalidFileNames.push(fileName);
					allFilesValid = false;
				}
			}
			formData.append('fileSeq', '');
		} else {
			const fleIdx = fileInput.getAttribute('data-idx');
			formData.append('fileSeq', fleIdx);
		}
	});
	
	if(!allFilesValid) {
		invalidFileNameText = invalidFileNames.join(',');
		console.log(invalidFileNameText);
		alert(`첨부파일이 업로드 가능한 확장자에 포함되는지 확인해주세요.\n*업로드 가능 확장자 : ${ALLOWED_EXTENSIONS.join(', ')}`);
	}
	
	const partnerInsttElements = document.querySelectorAll('button[name="partnerInstt"]');
	partnerInsttElements.forEach((elem) => {
		const key = elem.getAttribute('data-key');
		formData.append('partnerInstt', key);
	});
	
	for(const [key, value] of formData.entries()) {
		console.log(key, value);
	}
	
	showLoading();
	fetch(`${contextPath}/dct/expert/insert.do?mId=107`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok) {
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
//		console.log(`data :: ${JSON.stringify(data, null, 2)}`);
		alert('저장이 완료되었습니다.');
		window.location.reload();
	});
	hideLoading();
}

function deleteSelectedExpert() {
	const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-expert"]:not(#checkbox-all)');
	const selectedValues = [];
	checkboxes.forEach(checkbox => {
		if(checkbox.checked) {
			selectedValues.push(checkbox.value);
		}
	});
	
	if(selectedValues.length < 1) {
		alert('삭제할 전문가를 선택해주세요.');
		return;
	}
	
	const formData = new FormData();
	formData.append('selectedValues', JSON.stringify(selectedValues));
	console.log(selectedValues);
	
	showLoading();
	fetch(`${contextPath}/dct/expert/delete.do?mId=107`, {
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

function downloadExcel() {
	const form = document.getElementById('form-box');
	const queryString = window.location.search;
	const actionUrl = `${contextPath}/dct/expert/downloadExcel.do${queryString}`;
	form.action = actionUrl;
	form.submit();
}

function openModalForUpload() {
	$('.mask').fadeIn(150, function() {
		$('#modal-excelupload').show();
	});
	
	$('#modal-excelupload .btn-modal-close').on('click', function() {
		$('#modal-excelupload').hide();
		$('.mask').fadeOut(150);
	});
}

function uploadExcel() {
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
	
	fetch(`${contextPath}/dct/expert/uploadExcel.do?mId=107`, {
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
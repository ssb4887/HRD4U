var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
let tempCmpt = new Map();
let cmpt;
const ALLOWED_EXTENSIONS = ['.txt', '.xlsx', '.xls', '.pdf', '.hwp', '.doc', '.ppt', '.pptx', '.jpeg', '.jpg', '.png'];

document.addEventListener('DOMContentLoaded', function() {
	hideLoading();
	
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
//		console.log(`element : ${element}, value: ${value}`);
	}
	
	// modal show&hide
	$(".open-modal01").on("click", function() {
		$(".modal-wrapper").hide();
		$(".mask").fadeIn(150, function() {
			$("#modal-search").show();
		});
		
	});

	$(".btn-modal-close").on("click", function() {
		$(".modal-wrapper").hide();
		$(".mask").hide();
	});
	
	createCmptMap();
});

/**
 * 엔터키 옵션
 */
document.addEventListener("keyup", function handleEnterKeyPress(event) {
	if(event.code == 'Enter') {
		const divElement =document.querySelector(".mask");
		
		if(divElement && window.getComputedStyle(divElement).display === "block") {
			searchCmptinst();
			document.removeEventListener("keyup", handleEnterKeyPress);
		}
		
	}
	
	document.addEventListener("keyup", handleEnterKeyPress);
});

function initSearhParams() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
	textInputs.forEach(elem => {
		elem.value = '';
	});
}


// 상세 페이지
const viewButtons = document.querySelectorAll('.btn-view');
viewButtons.forEach(function(viewBtn) {
	viewBtn.addEventListener('click', function(event) {
		const idx = viewBtn.getAttribute('data-idx');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/dct/ntwrk/view.do?mId=71`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});

// checkbox
const checkAllBtn = document.getElementById('checkbox-all');
const checkboxBtns = document.querySelectorAll('input[name="checkbox-network"]:not(#checkbox-all)');
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

// modal checkbox
$(document).on('click', '#cmptinst-all', function() {
	const checkboxBtns = document.querySelectorAll('input[name="checkbox-cmptinst"]:not(#cmptinst-all)');
	for(let i=0;i < checkboxBtns.length ;i++) {
		checkboxBtns[i].checked = $(this).is(':checked');
		changeSelectedCmptinst($(this).is(':checked'), checkboxBtns[i].value, checkboxBtns[i].getAttribute('data-name'));
	}
});
$(document).on('change', 'input[name="checkbox-cmptinst"]:not(#cmptinst-all)', function() {
	changeSelectedCmptinst($(this).is(':checked'), this.value, $(this).attr('data-name'));
});

function changeSelectedCmptinst(flag, value, name) {
	if(flag && !tempCmpt.has(value)) {
		tempCmpt.set(value, name);
	} else if(!flag && tempCmpt.has(value)) {
		tempCmpt.delete(value);
	}
	
	const text = Array.from(tempCmpt.values()).join(', ');
	document.getElementById('selectedCmptinst').textContent = text;
}

// 네트워크 기관 추가
const addBtn = document.getElementById('btn-add');
if(addBtn) {
	addBtn.addEventListener('click', function() {
		document.querySelector('.btn-modal-close').click();
		cmpt = new Map(tempCmpt);
		let html = '';
		for(const [key, value] of cmpt) {
			html += `
				<p class="word">
					<strong>${value}</strong>
					<button type="button" class="btn-delete" data-key="${key}">삭제</button>
				</p>`;
		}
		const btnWrapper = document.getElementById('btn-wrapper');
		btnWrapper.innerHTML = html;
	});
}

// 네트워크 기관 추가 취소
const cancelBtn = document.getElementById('btn-cancel');
if(cancelBtn) {
	cancelBtn.addEventListener('click', function() {
		document.querySelector('.btn-modal-close').click();
		tempCmpt = new Map(cmpt);
		const text = Array.from(tempCmpt.values()).join(', ');
		document.getElementById('selectedCmptinst').textContent = text;
		const checkboxes = document.querySelectorAll('#modal-tbody input[type="checkbox"]');
		checkboxes.forEach(checkbox => {
			if(tempCmpt.has(checkbox.value)) {
				checkbox.checked = true;
			} else {
				checkbox.checked = false;
			}
		});
	});
}

// 네트워크 기관 개별 취소
$(document).on('click', '.btn-delete', function(e) {
	const key = this.getAttribute('data-key');

	const parentElement = this.closest('.word');
	if(parentElement) {
		parentElement.remove();
	}

	cmpt.delete(String(key));
	tempCmpt = new Map(cmpt);
	const text = Array.from(tempCmpt.values()).join(', ');
	document.getElementById('selectedCmptinst').textContent = text;
	const checkboxes = document.querySelectorAll('#modal-tbody input[type="checkbox"]');
	checkboxes.forEach(checkbox => {
		if(tempCmpt.has(checkbox.value)) {
			checkbox.checked = true;
		} else {
			checkbox.checked = false;
		}
	});
});

// 등록
function saveNtwrk() {
	const insttIdx = document.getElementById('insttIdx');
	if(insttIdx.selectedIndex === 0) {
		alert('주관기관을 선택해주세요.');
		insttIdx.focus();
		return;
	}
	
	const cmptinstName = document.getElementById('cmptinstName');
	if(cmptinstName.value.trim() === '') {
		alert('기관명을 입력해주세요.');
		cmptinstName.focus();
		return;
	}
	
	const agremName = document.getElementById('agremName');
	if(agremName.value.trim() === '') {
		alert('협약명을 입력해주세요.');
		agremName.focus();
		return;
	}
	
	const form = document.getElementById('form-network');
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
		alert(`첨부파일이 업로드 가능한 확장자에 포함되는지 확인해주세요.\n*업로드 가능 확장자 : ${ALLOWED_EXTENSIONS.join(', ')}`);
	}
	
	const cmptKeys = Array.from(cmpt.keys());
	cmptKeys.forEach((key, idx) => {
		formData.append(`cmptList`, key);
	});
	
	showLoading();
	fetch(`${contextPath}/dct/ntwrk/insert.do?mId=71`, {
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
		if(data.result.NTWRK_RESULT > 0) {
			alert('저장이 완료되었습니다.');
			window.location.href = `${contextPath}/dct/ntwrk/list.do?mId=71`;
		} else {
			let msg = '저장에 실패하였습니다.';
			alert(msg);
			window.location.reload();
		}
	});
	hideLoading();
}

// 모달 네트워크 기관 검색
const searchBtn = document.getElementById('btn-search');
if(searchBtn) {
	searchBtn.addEventListener('click', function() {
		searchCmptinst();
	});
}

/******************** 모달 페이징 ********************/
const itemsPerPage = 5; // 페이지 당 아이템 갯수
let currentPage = 1; // 현재 페이지
let searchResult; // 검색 결과
function searchCmptinst() {
	const cmptinstName = document.getElementById('modal-searchName').value;
	const insttIdx = document.getElementById('modal-searchInstt').value;
	const formData = new FormData();
	formData.append('cmptinstName', cmptinstName);
	if(insttIdx) formData.append('insttIdx', insttIdx);
	fetch(`${contextPath}/dct/ntwrk/networkSearch.do?mId=71`, {
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
		console.log(data);
		document.getElementById('searchCount').textContent = data.count;
		searchResult = data.result;
		if(searchResult != null && searchResult.length > 0) {
			handlePageClick(1);
			const totalPage = Math.ceil(searchResult.length / itemsPerPage);
			console.log(`total: ${totalPage}`);
			document.getElementById('totalPage').textContent = totalPage;
		} else {
			html = 
				`<tr>
					<td colspan="4">검색 결과가 없습니다.</td>
				</tr>`;
			const tbody = document.getElementById('modal-tbody');
			tbody.innerHTML = '';
			tbody.innerHTML += html;
		}
		
		createPageButton();
	})
}

// 특정 페이지의 아이템을 가져오기
function getItemsByPage(page) {
	const startIndex = (page -1) * itemsPerPage;
	const endIndex = startIndex + itemsPerPage;
	return searchResult.slice(startIndex, endIndex);
}

// 특정 페이지의 아이템을 tbody에 추가함
function renderPage(page) {
	document.getElementById('currentPage').textContent = page;
	const tbody = document.getElementById('modal-tbody');
	const items = getItemsByPage(page);
	tbody.innerHTML = '';
	
	let html = '';
	const count = searchResult.length - ((page - 1)*itemsPerPage);
	items.forEach((item, idx) => {
		let checked  = (tempCmpt.has(String(item.CMPTINST_IDX))) ? 'checked' : '';
		html += 
			`<tr>
				<td>${count - idx}</td>
				<td>${item.CMPTINST_NAME}</td>
				<td>${item.INSTT_NAME}</td>
				<td><input type="checkbox" name="checkbox-cmptinst" value="${item.CMPTINST_IDX}" class="checkbox-type01" data-name="${item.CMPTINST_NAME}" ${checked}></td>
			</tr>`;
	});
	tbody.innerHTML = html;
}

// 페이지 버튼 클릭 이벤트 핸들러
function handlePageClick(clickedPage) {
	currentPage = clickedPage;
	renderPage(currentPage);
	updatePaginationButtons();
}

// 페이지 이동 버튼 등록
const pagingNavigation = document.getElementById('modal-paging-navigation');
function createPageButton(page, className) {
	let button = document.createElement('a');
	button.href='#'; // 현재는 빈 링크

	if(page === currentPage) {
		button = document.createElement('strong');
		button.appendChild(document.createTextNode(page));
	} else if(className) {
		button.textContent = page;
		button.classList.add(className);
		button.addEventListener('click', () => handlePageClick(page));
	} else {
		button.textContent = page;
		button.addEventListener('click', () => handlePageClick(page));
	}

	return button;
}

// 페이징 버튼 업데이트
function updatePaginationButtons() {
	const maxButtonToShow = 5; // 노출할 최대 버튼 갯수
	const totalButtons = Math.ceil(searchResult.length / itemsPerPage);
	
	// 버튼을 생성할 범위 개선
	let startButton = Math.max(1, currentPage - Math.floor(maxButtonToShow / 2));
	let endButton = Math.min(startButton + maxButtonToShow - 1, totalButtons);
	
	if(endButton - startButton + 1 < maxButtonToShow) {
		startButton = Math.max(1, endButton - maxButtonToShow + 1);
	}
	
	if(endButton - startButton + 1 < maxButtonToShow) {
		endButton = Math.min(totalButtons, startButton + maxButtonToShow - 1);
	}
	
	pagingNavigation.innerHTML = '';
	
	if(startButton != 1) {
		const button = createPageButton(1, 'btn-first');
		pagingNavigation.appendChild(button);
	}
	
	for(let i=startButton; i <= endButton; i++) {
		const button = createPageButton(i);
		pagingNavigation.appendChild(button);
	}
	
	if(endButton != totalButtons) {
		const button = createPageButton(totalButtons, 'btn-last');
		pagingNavigation.appendChild(button);
	}
}

function deleteSelectedNtwrk() {
	const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-network"]:not(#checkbox-all)');
	const selectedValues = [];
	checkboxes.forEach(checkbox => {
		if(checkbox.checked) {
			selectedValues.push(checkbox.value);
		}
	});
	
	if(selectedValues.length < 1) {
		alert('삭제할 네트워크 이력을 선택해주세요.');
		return;
	}
	
	const formData = new FormData();
	formData.append('selectedValues', JSON.stringify(selectedValues));
	console.log(selectedValues);
	
	showLoading();
	fetch(`${contextPath}/dct/ntwrk/delete.do?mId=71`, {
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

function createCmptMap() {
	const cmptBtnWrapper = document.getElementById('btn-wrapper');
	const wordElements = cmptBtnWrapper.querySelectorAll('.word');
	cmpt = new Map();
	wordElements.forEach(wordElement => {
		const strong = wordElement.querySelector('strong');
		const button = wordElement.querySelector('button');
		if(strong && button) {
			const key = button.getAttribute('data-key');
			const value = strong.textContent.trim();
			cmpt.set(key, value);
		}
	});
	
	tempCmpt = new Map(cmpt);
	
	const text = Array.from(tempCmpt.values()).join(', ');
	document.getElementById('selectedCmptinst').textContent = text;
}

function downloadExcel() {
	const form = document.getElementById('form-box');
	const queryString = window.location.search;
	const actionUrl = `${contextPath}/dct/ntwrk/downloadExcel.do${queryString}`;
	form.action = actionUrl;
	form.submit();
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
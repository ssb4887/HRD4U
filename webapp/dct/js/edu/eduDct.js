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
	
	const flag = document.getElementById('tab1-btn');
	if(flag) {
		let lastActiveTabId = localStorage.getItem('lastActiveTab');
		let defaultTabId = 'tab1';
		if(lastActiveTabId) {
			defaultTabId = lastActiveTabId;
		}
		let defaultTab = document.getElementById(`${defaultTabId}-btn`);
		defaultTab.classList.add('active');
		openTab({currentTarget: defaultTab }, defaultTabId);
	} else {
		localStorage.removeItem('lastActiveTab');
	}
	
	const phoneElements = document.querySelectorAll('.js-format-phone');
	if(phoneElements.length > 0) {
		formatPhoneNumbers(phoneElements);
	}
	
	// modal show&hide
	$(".btn-modal-close").on("click", function() {
		$(".modal-wrapper").hide();
		$(".mask").hide();
	});
	
	/**
	 * 엔터키 옵션
	 */
	document.addEventListener("keyup", function handleEnterKeyPress(event) {
		if(event.code == 'Enter') {
			const divElement =document.querySelector(".mask");
			
			if(divElement && window.getComputedStyle(divElement).display === "block") {
				document.removeEventListener("keyup", handleEnterKeyPress);
				searchMember();
			}
		}
		document.addEventListener("keyup", handleEnterKeyPress);
	});
	
	// checkbox
	const checkAllBtn = document.getElementById('checkbox-all');
	const checkboxBtns = document.querySelectorAll('input[name="checkbox-member"]:not(#checkbox-all)');
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
});

//checkbox
const checkAllBtn = document.getElementById('checkbox-all');
const checkboxBtns = document.querySelectorAll('input[name="checkbox-edc"]:not(#checkbox-all)');
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

function deleteCheckedItem() {
	const mId = document.querySelector('input[name="mId"]').value;
	const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-edc"]:not(#checkbox-all)');
	const selectedValues = [];
	checkboxes.forEach(checkbox => {
		if(checkbox.checked) {
			selectedValues.push(checkbox.value);
		}
	});
	
	if(selectedValues.length < 1) {
		alert('삭제할 교육과정을 선택해주세요.');
		return;
	}
	
	const formData = new FormData();
	formData.append('selectedValues', JSON.stringify(selectedValues));
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/delete.do?mId=${mId}`, {
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

function initSearhParams() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
	textInputs.forEach(elem => {
		elem.value = '';
	});
}

function goInput() {
	const mId = document.querySelector('input[name="mId"]').value;
	const btn = event.target;
	const idx = btn.getAttribute('data-idx');
	const form = document.getElementById('form-box');
	const actionUrl = `${contextPath}/dct/eduDct/input.do?mId=${mId}`;
	form.action = actionUrl;
	document.getElementById('idx').value = idx;
	form.submit();
}

function goView(e) {
	const mId = document.querySelector('input[name="mId"]').value;
	const idx = e.currentTarget.getAttribute('data-idx');
	window.location.href = `${contextPath}/dct/eduDct/view.do?mId=${mId}&idx=${idx}`;
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

function saveEdc() {
	if(!checkEssentialValues()) {
		return;
	}
	
	const form = document.getElementById('form-input');
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
		alert(`첨부파일이 업로드 가능한 확장자에 포함되는지 확인해주세요.${invalidFileNameText}\n*업로드 가능 확장자 : ${ALLOWED_EXTENSIONS.join(', ')}`);
		return;
	}
	
	combineStringToDate(formData);
	
	showLoading();
	const mId = document.querySelector('input[name="mId"]').value;
	fetch(`${contextPath}/dct/eduDct/insert.do?mId=${mId}`, {
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
		if(data.INSERT_EDC_RESULT.EDC_INSERT_RESULT > 0) {
			let msg = '저장이 완료되었습니다.';
			if(!isEmpty(data.INSERT_EDC_FILE_RESULT) && data.INSERT_EDC_FILE_RESULT < 1) {
				msg += `\n첨부파일 중 업로드 불가한 파일 확장자를 가진 파일이 포함되어있습니다. 다시 첨부해주세요.`;
			}
			alert(msg);
			window.location.href = `${contextPath}/dct/eduDct/list.do?mId=${mId}`;
		} else {
			let msg = `저장 중 오류가 발생하였습니다. 새로고침 후 다시 시도해주세요.(성공 : ${data.INSERT_EDC_RESULT.EDC_INSERT_RESULT})`;
			if(fileInputs.length != data.INSERT_EDC_FILE_RESULT.FILE_UPLOAD_RESULT) {
				msg += `\n파일 업로드에 실패하였습니다.(${data.INSERT_EDC_FILE_RESULT.FILE_UPLOAD_RESULT})`;
				msg += `\n${JSON.stringify(data.INSERT_EDC_FILE_RESULT, null, 2)}`;
			}
			alert(msg);
			window.location.reload();
		}
	});
	hideLoading();
}

function downloadFile(e) {
	const fleIdx = e.currentTarget.getAttribute('data-idx');
	const mId = document.querySelector('input[name="mId"]').value;
	window.location.href = `${contextPath}/dct/eduDct/download.do?mId=${mId}&idx=${fleIdx}`;
}

function checkEssentialValues() {
	const edcName = document.getElementById('edcName');
	if(isEmpty(edcName.value)) {
		alert('교육명을 입력해주세요.');
		edcName.focus();
		return false;
	}
	
	const insttIdx = document.getElementById('insttIdx');
	if(isEmpty(insttIdx.value)) {
		alert('소속기관을 선택해주세요.');
		insttIdx.focus();
		return false;
	}
	
	const edcPlace = document.getElementById('edcPlace');
	if(isEmpty(edcPlace.value)) {
		alert('교육 장소를 입력해주세요.');
		edcPlace.focus();
		return false;
	}
	
	const edcStartDate = document.getElementById('edcStartDate');
	if(isEmpty(edcStartDate.value)) {
		alert('교육시작일을 입력해주세요.');
		edcStartDate.focus();
		return false;
	}
	
	const edcEndDate = document.getElementById('edcEndDate');
	if(isEmpty(edcEndDate.value)) {
		alert('교육종료일을 입력해주세요.');
		edcEndDate.focus();
		return false;
	}
	
	const edcStartDateValue = document.getElementById('edcStartDate').value;
	const edcStartHour = document.getElementById('edcStartHour').value;
	const edcStartMin = document.getElementById('edcStartMin').value;
	
	const edcEndDateValue = document.getElementById('edcEndDate').value;
	const edcEndHour = document.getElementById('edcEndHour').value;
	const edcEndMin = document.getElementById('edcEndMin').value;
	
	const combinedEdcStartDate = new Date(edcStartDateValue + 'T' + edcStartHour.padStart(2, '0') + ':' + edcStartMin.padStart(2, '0'));
	const combinedEdcEndDate = new Date(edcEndDateValue + 'T' + edcEndHour.padStart(2, '0') + ':' + edcEndMin.padStart(2, '0'));
	
	if(combinedEdcStartDate > combinedEdcEndDate) {
		alert("교육시작일은 교육종료일 보다 이전 날짜여야 합니다.");
		edcStartDate.focus();
		return false;
	}
	
	const othbcYnOptions = document.getElementsByName('othbcYn');
	let othbcYnChecked = false;
	for(let i=0;i < othbcYnOptions.length;i++) {
		if(othbcYnOptions[i].checked) {
			othbcYnChecked = true;
			break;
		}
	}
	if(!othbcYnChecked) {
		alert("공개여부를 선택해주세요.");
		othbcYnOptions[0].focus();
		return;
	}

	const recptBgndt = document.getElementById('recptBgndt');
	if(isEmpty(recptBgndt.value)) {
		alert('접수시작일을 입력해주세요.');
		recptBgndt.focus();
		return false;
	}

	const recptEnddt = document.getElementById('recptEnddt');
	if(isEmpty(recptEnddt.value)) {
		alert('접수종료일을 입력해주세요.');
		recptEnddt.focus();
		return false;
	}

	const recptBgndtValue = document.getElementById('recptBgndt').value;
	const recptBgndtHour = document.getElementById('recptBgndtHour').value;
	const recptBgndtMin = document.getElementById('recptBgndtMin').value;
	
	const recptEnddtValue = document.getElementById('recptEnddt').value;
	const recptEnddtHour = document.getElementById('recptEnddtHour').value;
	const recptEnddtMin = document.getElementById('recptEnddtMin').value;
	
	const combinedrecptBgndt = new Date(recptBgndtValue + 'T' + recptBgndtHour.padStart(2, '0') + ':' + recptBgndtMin.padStart(2, '0'));
	const combinedrecptEnddt = new Date(recptEnddtValue + 'T' + recptEnddtHour.padStart(2, '0') + ':' + recptEnddtMin.padStart(2, '0'));
	
	if(combinedrecptBgndt > combinedrecptEnddt) {
		alert("접수시작일은 접수종료일 보다 이전 날짜여야 합니다.");
		recptBgndt.focus();
		return false;
	}
	
	if(combinedrecptEnddt > combinedEdcStartDate) {
		alert("접수종료일은 교육시작일 보다 이전 날짜여야 합니다.");
		recptEnddt.focus();
		return false;
	}
	
	const ctfhvIssueYnOptions = document.getElementsByName('ctfhvIssueYn');
	let ctfhvIssueYnChecked = false;
	for(let i=0;i < ctfhvIssueYnOptions.length;i++) {
		if(ctfhvIssueYnOptions[i].checked) {
			ctfhvIssueYnChecked = true;
			break;
		}
	}
	if(!ctfhvIssueYnChecked) {
		alert("수료증 발급여부를 선택해주세요.");
		ctfhvIssueYnOptions[0].focus();
		return;
	}
	
	
	return true;
}

function combineStringToDate(formData) {
	const edcStartDate = formData.get('edcStartDate');
	const edcStartHour = formData.get('edcStartHour');
	const edcStartMin = formData.get('edcStartMin');
	if(!isEmpty(edcStartDate) && !isEmpty(edcStartHour) && !isEmpty(edcStartMin)) {
		let combinedDateTimeString = edcStartDate + " " + edcStartHour + ":" + edcStartMin;
		formData.set('edcStartDate', combinedDateTimeString);
	} else {
		formData.delete('edcStartDate');
	}
	
	const edcEndDate = formData.get('edcEndDate');
	const edcEndHour = formData.get('edcEndHour');
	const edcEndMin = formData.get('edcEndMin');
	if(!isEmpty(edcEndDate) && !isEmpty(edcEndHour) && !isEmpty(edcEndMin)) {
		let combinedDateTimeString = edcEndDate + " " + edcEndHour + ":" + edcEndMin;
		formData.set('edcEndDate', combinedDateTimeString);
	} else {
		formData.delete('edcEndDate');
	}
	
	const recptBgndt = formData.get('recptBgndt');
	const recptBgndtHour = formData.get('recptBgndtHour');
	const recptBgndtMin = formData.get('recptBgndtMin');
	if(!isEmpty(recptBgndt) && !isEmpty(recptBgndtHour) && !isEmpty(recptBgndtMin)) {
		let combinedDateTimeString = recptBgndt + " " + recptBgndtHour + ":" + recptBgndtMin;
		formData.set('recptBgndt', combinedDateTimeString);
	} else {
		formData.delete('recptBgndt');
	}
	
	const recptEnddt = formData.get('recptEnddt');
	const recptEnddtHour = formData.get('recptEnddtHour');
	const recptEnddtMin = formData.get('recptEnddtMin');
	if(!isEmpty(recptEnddt) && !isEmpty(recptEnddtHour) && !isEmpty(recptEnddtMin)) {
		let combinedDateTimeString = recptEnddt + " " + recptEnddtHour + ":" + recptEnddtMin;
		formData.set('recptEnddt', combinedDateTimeString);
	} else {
		formData.delete('recptEnddt');
	}
}

function openTab(event, tabId) {
	const tabcontents = document.querySelectorAll('.tabcontents');
	tabcontents.forEach(function(tab) {
		tab.style.display = 'none';
	});
	
	const buttons = document.querySelectorAll('.tabmenu');
	buttons.forEach(function(button) {
		button.classList.remove('active');
	});
	
	const selectedTab = document.getElementById(tabId);
	selectedTab.style.display='block';
	event.currentTarget.classList.add('active');
	
	localStorage.setItem('lastActiveTab', tabId);
}

function openModalForRegister() {
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-register").show();
	});
}

function openModalForChange() {
	const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-member"]:checked:not(#checkbox-all)');
	if(checkboxes.length < 1) {
		alert('일괄변경할 대상자를 선택해주세요.');
		return;
	}
	
	const area = document.getElementById('modal-checkedMember');
	area.innerHTML = '';
	checkboxes.forEach(checkbox => {
		const memberName = checkbox.getAttribute('data-member');
		let html = `
			<p class="word">
				<strong>${memberName}</strong>
				<button type="button" class="btn-delete" data-member="${checkbox.value}" onclick="uncheckMember(event)">삭제</button>
			</p>`;
		area.innerHTML += html;
	});
	
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-change").show();
	});
}

function openModalForMember(memberIdx) {
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-member").show();
	});
	
	const mId = document.querySelector('input[name="mId"]').value;
	const formData = new FormData();
	formData.append('memberIdx', memberIdx);
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/getMemberData.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok) {
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			hideLoading();
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result === 'fail') {
			alert(data.msg);
			
		} else if(data.result === 'success') {
			document.getElementById('memberId').textContent = data.member.MEMBER_ID;
			document.getElementById('memberName').textContent = data.member.MEMBER_NAME;
			document.getElementById('memberEmail').textContent = data.member.MEMBER_EMAIL;
			
			let phoneNumber = data.member.MOBILE_PHONE;
			phoneNumber = phoneNumber.replace(/\D/g, '');
			if(phoneNumber.length == 9) {
				phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
			}else if(phoneNumber.length == 10) {
				phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
			}else if(phoneNumber.length == 11) {
				phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			}else {
				phoneNumber = phoneNumber;
			}
			document.getElementById('mobilePhone').textContent = phoneNumber;
			
			const usertypeIdx = data.member.USERTYPE_IDX;
			let link = "";
			if(usertypeIdx >= 40) {
				document.getElementById('memberType').textContent = '직원';
				link = `${contextPath}/dct/memberManage/inputSecEmploy.do?mId=78&memberIdx=${data.member.MEMBER_IDX}`;

			} else if(usertypeIdx >= 30) {
				document.getElementById('memberType').textContent = '소속직원';
				link = `${contextPath}/dct/memberManage/inputSecEmploy.do?mId=78&memberIdx=${data.member.MEMBER_IDX}`;
				
			} else if(usertypeIdx >= 20) {
				document.getElementById('memberType').textContent = '민간센터';
				link = `${contextPath}/dct/memberManage/inputCenter.do?mId=78&memberIdx=${data.member.MEMBER_IDX}&corpNum=${data.member.CORP_NUM}`;
				
			} else if(usertypeIdx >= 10) {
				document.getElementById('memberType').textContent = '컨설턴트';
				link = `${contextPath}/dct/memberManage/inputConsult.do?mId=78&memberIdx=${data.member.MEMBER_IDX}`;
				
			} else if(usertypeIdx >= 5) {
				document.getElementById('memberType').textContent = '기업회원';
				link = `${contextPath}/dct/memberManage/inputCorp.do?mId=78&memberIdx=${data.member.MEMBER_IDX}&corpNum=${data.member.CORP_NUM}`;
				
			} else if(usertypeIdx >= 1) {
				document.getElementById('memberType').textContent = '개인회원';
				link = `${contextPath}/dct/memberManage/inputCorp.do?mId=78&memberIdx=${data.member.MEMBER_IDX}&corpNum=${data.member.CORP_NUM}`;
			}
			
			const memberLink = document.getElementById('memberLink');
			if(memberLink) memberLink.href = link;
			
		} else {
			alert('회원 정보를 가져오는 데 실패하였습니다.새로고침 후 다시 시도해주세요.');
		}
		hideLoading();
	});
}

function uncheckMember(e) {
	const target = e.currentTarget;
	const parentElement = target.closest('.word');
	
	const remainingElements = document.querySelectorAll('#modal-checkedMember .word');
	if(remainingElements.length === 1) {
		const msg = `삭제 하시겠습니까?`;
		if(confirm(msg)) {
			parentElement.remove();
			document.querySelector('.btn-modal-close').click();
		}
	} else {
		if(parentElement) {
			parentElement.remove();
		}
	}
}

function changeStatusAll() {
	const formData = new FormData();
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	formData.append('mId', mId);
	formData.append('edcIdx', edcIdx);
	const members = document.querySelectorAll('#modal-checkedMember button');
	const selectedValues = [];
	members.forEach(function(member) {
		const memberIdx = member.getAttribute('data-member');
		selectedValues.push(memberIdx);
	});
	formData.append('memberIdx', JSON.stringify(selectedValues));
	
	const isCheckedConfnStatus = document.getElementById('checkbox-confmStatus').checked;
	const isCheckedAttYn = document.getElementById('checkbox-attYn').checked;
	if(isCheckedConfnStatus) {
		const confmStatus = document.getElementById('change-confmStatus').value;
		formData.append('confmStatus', confmStatus);
	}
	if(isCheckedAttYn) {
		const attYn = document.getElementById('change-attYn').value;
		formData.append('attYn', attYn);
	}
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/changeMemberStatusAll.do?mId=${mId}`, {
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
		if(data.result > 0) {
			alert(`${data.result} 건의 상태 일괄변경이 완료되었습니다.`);
			window.location.reload();
		} else {
			alert('상태 일괄변경에 실패하였습니다. 다시 시도해주세요.');
			window.location.reload();
		}
	});
	hideLoading();
}

function searchMember() {
	const form = document.getElementById('form-register');
	const mId = document.querySelector('input[name="mId"]').value;
	const formData = new FormData(form);
	if(!isEmpty(formData.get("keyword"))) {
		const newKeyword = '%' + formData.get("keyword") + '%';
		formData.set("keyword", newKeyword);
	}
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/member.do?mId=${mId}`, {
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
		let tbody = document.querySelector('tbody#modal-register-tbody');
		tbody.innerHTML = '';
		let list = data.memberList;
		let html;
		document.getElementById('memberCount').textContent = list.length;
		if(list.length > 0) {
			list.forEach((item, idx) => {
				let insttName = (!isEmpty(item.INSTT_NAME)) ? item.INSTT_NAME : '';
				let orgName = (!isEmpty(item.ORG_NAME)) ? item.ORG_NAME : '';
				let row = document.createElement('tr');
				row.innerHTML = `
					<td>${item.USER_NAME}<br/>(${item.USER_ID})</td>
					<td>${item.GROUP_NAME}</td>
					<td>${orgName}</td>
					<td>${insttName}</td>
					<td>
						<button type="button" class="btn-m03 btn-color03 w100">선택</button>
					</td>`;
				
				let button = row.querySelector('button');
				button.addEventListener('click', function() {
					registerMember(item);
				});
				tbody.appendChild(row);
			});
		} else {
			let row = document.createElement('tr');
			row.innerHTML = `<td colspan="6">검색결과가 없습니다.</td>`;
			tbody.appendChild(row);
		}
		hideLoading();
	});
}

function registerMember(user) {
	let msg = `해당 회원을 교육과정에 등록하시겠습니까?\n`;
	msg += `회원명(아이디): ${user.USER_NAME} (${user.USER_ID})`;
	if(!confirm(msg)) {
		return;
	}
	
	document.querySelector('.btn-modal-close').click();
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	formData.append('userIdx', user.USER_IDX);
	
	fetch(`${contextPath}/dct/eduDct/register.do?mId=${mId}`, {
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
		if(data.result > 0) {
			alert('등록이 완료되었습니다.');
		} else {
			alert('등록에 실패하였습니다. 다시 시도해주세요.');
		}
		window.location.reload();
	});
}

function deleteEdcMember() {
	const mId = document.querySelector('input[name="mId"]').value;
	const checkboxes = document.querySelectorAll('input[type="checkbox"][name="checkbox-member"]:not(#checkbox-all)');
	const selectedValues = [];
	checkboxes.forEach(checkbox => {
		if(checkbox.checked) {
			selectedValues.push(checkbox.value);
		}
	});
	
	if(selectedValues.length < 1) {
		alert('삭제할 신청자를 선택해주세요.');
		return;
	}
	
	const msg = `${selectedValues.length}건을 삭제하시겠습니까?`;
	if(!confirm(msg)) {
		return;
	}
	
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const formData = new FormData();
	formData.append('selectedValues', JSON.stringify(selectedValues));
	formData.append('edcIdx', edcIdx);
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/deleteMember.do?mId=${mId}`, {
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

function changeMemberStatus(e) {
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const statusName = e.currentTarget.getAttribute('data-name');
	const memberIdx = e.currentTarget.getAttribute('data-memberIdx');
	const status = e.currentTarget.value;
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	formData.append('statusName', statusName);
	formData.append('memberIdx', memberIdx);
	formData.append('status', status);
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/changeMemberStatus.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			hideLoading();
		}
		return response.json();
	})
	.then((data) => {
		if(data.result > 0) {
			alert('상태 변경이 완료되었습니다.');
		} else {
			alert('상태 변경에 실패하였습니다. 다시 시도해주세요.');
		}
		window.location.reload();
		hideLoading();
	});
}

function downloadEdcMembers() {
	const form = document.getElementById('form-box');
	const queryString = window.location.search;
	const actionUrl = `${contextPath}/dct/eduDct/downloadEdcMembers.do${queryString}`;
	form.action = actionUrl;
	form.submit();
}

function printCertificate(e) {
	const form = document.getElementById('form-box');
	const queryString = window.location.search;
	const memberIdx = e.currentTarget.getAttribute('data-idx');
	const actionUrl = `${contextPath}/dct/eduDct/printCertificate.do${queryString}&member=${memberIdx}`;
	form.action = actionUrl;
	form.submit();
}

function deleteCertificate(e) {
	const memberName = e.currentTarget.getAttribute('data-name');
	const msg = `"${memberName}"님의 수료증 발급을 취소하시겠습니까?(취소 후에도 재발급이 가능합니다)`;
	if(!confirm(msg)) {
		return;
	}
	
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const memberIdx = e.currentTarget.getAttribute('data-idx');
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	formData.append('memberIdx', memberIdx);
	formData.append('memberName', memberName);
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/deleteCertificate.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			hideLoading();
		}
		return response.json();
	})
	.then((data) => {
		if(data.result === 'success') {
			alert('수료증 발급이 취소되었습니다.');
			window.location.reload();
		} else {
			alert(data.msg);
			window.location.reload();
		}
		hideLoading();
	});
}

function issueCertificate(e) {
	const memberName = e.currentTarget.getAttribute('data-name');
	const msg = `"${memberName}"님의 교육 수료증을 발급하시겠습니까?`;
	if(!confirm(msg)) {
		return;
	}
	
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const memberIdx = e.currentTarget.getAttribute('data-idx');
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	formData.append('memberIdx', memberIdx);
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/issueCertificate.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			hideLoading();
		}
		return response.json();
	})
	.then((data) => {
		if(data.result > 0) {
			alert('수료증 발급이 완료되었습니다.');
			window.location.reload();
		} else {
			alert('수료증 발급에 실패하였습니다. 다시 시도해주세요.');
			window.location.reload();
		}
		hideLoading();
	});
}

function toggleDisplay(e) {
	const checkbox = e.currentTarget;
	const targetId = checkbox.getAttribute('data-target');
	const targetElement = document.getElementById(targetId);
	
	if(targetElement) {
		if(checkbox.checked) {
			targetElement.style.display = 'block';
		} else {
			targetElement.style.display = 'none';
		}
	}
}

function openModalForExcel() {
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-excelupload").show();
	});
}

function uploadMemberExcel() {
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
		
	} else {
		alert('업로드할 파일을 첨부해주세요.');
		return;
	}
	
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	
	showLoading();
	fetch(`${contextPath}/dct/eduDct/uploadMemberExcel.do?mId=${mId}&idx=${edcIdx}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			hideLoading();
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		hideLoading();
		if(data.result === 'fail') {
			alert(data.msg);
		} else {
			alert(`${data.total}명의 교육생이 등록에 성공하였습니다.`);
		}
		window.location.reload();
	});
}

function uploadExcel(e) {
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
	
	const mId = e.currentTarget.getAttribute('data-mId');
	showLoading();
	fetch(`${contextPath}/dct/eduDct/uploadExcel.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		hideLoading();
		if(data.result === 'success') {
			const msg = `${data.total}건의 업로드가 완료되었습니다.`;
			alert(msg);
		} else {
			const msg = `업로드에 실패하였습니다.\n(실패메시지: ${data.msg})`;
			alert(msg);
		}
		window.location.reload();
	});
	
}

function downloadExcel() {
	const queryString = window.location.search;
	window.location.href = `${contextPath}/dct/eduDct/downloadExcel.do${queryString}`;
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

function formatPhoneNumbers(elements) {
	elements.forEach(function (element) {
		let phoneNumber = element.textContent.replace(/\D/g, '');
		
		if(phoneNumber.length === 9) {
			element.textContent = phoneNumber.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
		} else if(phoneNumber.length == 10) {
			element.textContent = phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
		} else if(phoneNumber.length == 11) {
			element.textContent = phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		} else {
			element.textContent = phoneNumber;
		}
	});
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
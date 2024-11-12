var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
const ALLOWED_EXTENSIONS = ['.txt', '.xlsx', '.xls', '.pdf', '.hwp', '.doc', '.ppt', '.pptx', '.jpeg', '.jpg', '.png'];

document.addEventListener('DOMContentLoaded', function() {
	hideLoading();
});

function addRow(button, max) {
	const table = button.closest('table');
	const tbody = table.querySelector('tbody');
	const allRows = tbody.querySelectorAll('tr');
	const lastRow = allRows[allRows.length -1];
	
	if(allRows.length === max) {
		alert(`최대 ${max}행까지 추가할 수 있습니다.`);
		return;
	}
	
	let newRow = lastRow.cloneNode(true);
	let tdElements = newRow.getElementsByTagName('td');
	for(let i=0;i < tdElements.length; i++) {
		const td = tdElements[i];
		td.removeAttribute('value');
		
		const inputElement = td.querySelector('input');
		if(inputElement && inputElement.classList.contains('rank')) {
			inputElement.value = allRows.length + 1;
		} else if(inputElement) {
			inputElement.value = '';
		}
	}
	
	tbody.appendChild(newRow);
}

function deleteRow(button) {
	const table = button.closest('table');
	const tbody = table.querySelector('tbody');
	const rows = tbody.getElementsByTagName('tr');
	if(rows.length > 1) {
		tbody.removeChild(rows[rows.length - 1]);
	} else {
		alert('더이상 삭제할 수 없습니다.');
		return;
	}
}

function handleInputNumber(input) {
	if(!/^\d+$/.test(input.value)) {
		input.value = '';
		return;
	}
	
	const integerValue = parseInt(input.value, 10);
	if(integerValue < 0) {
		input.value = '0';
	}
	
	const min = parseInt(input.getAttribute('min'), 10) || Number.MIN_SAFE_INTEGER;
	const max = parseInt(input.getAttribute('max'), 10) || Number.MAX_SAFE_INTEGER;
	if(integerValue < min) {
		input.value = min;
	} else if(integerValue > max) {
		input.value = max;
	}
	
	updateAverage(input);
}

function updateAverage(input) {
	const tr = input.closest('tr');
	const averageElement = tr.querySelector('.js-average');
	if(tr && averageElement) {
		const inputs = tr.querySelectorAll('input[type="number"]');
		console.log(`inputs len :: ${inputs.length}`);
		let sum = 0;
		for(let i=0;i < inputs.length;i++) {
			const value = parseInt(inputs[i].value, 10) || 0;
			sum += value;
		}
		
		let count = Array.from(inputs).filter(input => input.value !== '').length;
		let average = count > 0 ? sum / count : 0;
		const averageElement = tr.querySelector('.js-average');
		if(averageElement) {
			averageElement.value = average.toFixed(2);
		}
	}
}

function addTable(boxId, max) {
	const tableBoxes = document.querySelectorAll(`.js-table-box-${boxId}`);
	
	if(tableBoxes.length === max) {
		alert(`최대 ${max}개까지 추가할 수 있습니다.`);
		return;
	}
	
	const lastTableBox = tableBoxes[tableBoxes.length - 1];
	const newTableBox = lastTableBox.cloneNode(true);
	const tdElements = newTableBox.getElementsByTagName('td');
	for(let i=0;i < tdElements.length;i++) {
		const td = tdElements[i];
		const inputElements = td.querySelectorAll('input, textarea');
		if(inputElements) {
			inputElements.forEach((inputElement) => {
				inputElement.value = '';
			});
		}
	}
	lastTableBox.parentNode.insertBefore(newTableBox, lastTableBox.nextSibling);
}

function removeTable(boxId) {
	const tableBoxes = document.querySelectorAll(`.js-table-box-${boxId}`);
	if(tableBoxes.length > 1) {
		const lastTableBox = tableBoxes[tableBoxes.length - 1];
		lastTableBox.parentNode.removeChild(lastTableBox);
	} else {
		alert('더이상 삭제할 수 없습니다.');
		return;
	}
}

function scrollToSection(sectionId) {
	const section = document.querySelector(sectionId);
	if(section) section.scrollIntoView({behavior: 'smooth'});
}

function addFile(event, areaId, max) {
	const fileListArea = document.querySelector(`.js-file-area-${areaId}`);
	
	const currentFileCount = fileListArea.querySelectorAll('p').length;
	if(currentFileCount >= max) {
		alert(`최대 ${max}개의 파일만 업로드 가능합니다.`);
		return;
	}
	
	const fileItem = document.createElement('p');
	fileItem.classList.add('ml0', 'mt10');
	
	const newFileInput = document.createElement('input');
	newFileInput.type = 'file';
	newFileInput.name = event.target.getAttribute('name');
	newFileInput.style.display = 'none';

	newFileInput.addEventListener('change', function() {
		const selectedFile = this.files[0];
		const spanElement = document.createElement('span');
		spanElement.className = 'mr05';
		spanElement.textContent = `${selectedFile.name}`;
		
		if(isSameFileExists(selectedFile.name, areaId)) {
			alert(`이미 같은 파일명이 존재합니다.`);
			return;
		}
		
		const deleteButton = document.createElement('button');
		deleteButton.type = 'button';
		deleteButton.textContent = '삭제';
		deleteButton.classList.add('btn-m03', 'btn-color02');
		deleteButton.addEventListener('click', function() {
			fileItem.remove();
		});
		
		fileItem.appendChild(spanElement);
		fileItem.appendChild(deleteButton);
		fileItem.appendChild(newFileInput);
		fileListArea.appendChild(fileItem);
	});
	
	newFileInput.click();
}

function isSameFileExists(newLabelText, areaId) {
	const labelElements = document.querySelectorAll(`.js-file-area-${areaId} p span`);
	for(const labelElement of labelElements) {
		if(labelElement.textContent.trim() === newLabelText.trim()) {
			return true;
		}
	}
	return false;
}

function deleteFile(button) {
	button.parentElement.remove();
}

function saveReport(confmStatus) {
	console.log("진입", confmStatus)
	const reportForm = document.getElementById('report-form');
	const formData = new FormData();
	
	// 빈값 제거
	let inputs = Array.from(reportForm.querySelectorAll('input[type="text"], input[type="number"], input[type="hidden"], input[type="checkbox"], input[type="radio"], textarea, select'));
	let inputFiles = Array.from(reportForm.querySelectorAll('input[type="file"]'));
	inputs.filter(input => input.value.trim() !== '').forEach(input => {
		if(input.type === 'checkbox' || input.type === 'radio') {
			if(input.checked) {
				formData.append(input.name, input.value);
			} else {
				formData.append(input.name, '-');
			}
		} else {
			formData.append(input.name, input.value);
		}
	});
	inputFiles.forEach(input => {
		console.log(input)
		console.log(input.files[0])
		if(input.files.length > 0) {
			formData.append(input.name, input.files[0]);
		} else {
			formData.append(input.name, '');
		}
	});

	formData.append("confmStatus", confmStatus);
	
	let index = 0;
	for(const [key, value] of formData) {
		if(key === 'attac_file') {
			console.log(value)
			formData.append(`attac_file${index}`, value);
			index++;
		}
	}
	formData.delete('attac_file');
	
	if(confmStatus == '5') {
		const result = confirm('해당 보고서를 저장하시겠습니까?');
		if(result) {
			showLoading();
			
			fetch(`${contextPath}/dct/report/save.do?mId=134`, {
				method: 'POST',
				body : formData
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					hideLoading();
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				if(data.result.body === 'fail') {
					alert('보고서 저장에 실패하였습니다.\n관리자에게 문의해주세요.');
				} else {
					alert('보고서 저장이 완료되었습니다.');
				}
				
				location.reload();
				hideLoading();
			});
		}
		
	} else if(confmStatus == '10') {
		if(reportForm.checkValidity()) {
			const result = confirm('해당 보고서를 제출하시겠습니까?');
			if(result) {
				showLoading();

				fetch(`${contextPath}/dct/report/save.do?mId=134`, {
					method: 'POST',
					body : formData
				}).then((response) => {
					if(!response.ok){
						alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
						hideLoading();
						throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
					}
					return response.json();
				})
				.then((data) => {
					if(data.result.body === 'fail') {
						alert('보고서 제출에 실패하였습니다.\n관리자에게 문의해주세요.');
						location.reload();
					} else {
						alert('보고서 제출이 완료되었습니다.');
						const newURL = `${contextPath}/dct/consulting/cnslListAll.do?mId=95`;
						history.replaceState({}, '', newURL);
						window.location.href = newURL;
					}
					hideLoading();
				});
			}
		}else {
			alert('빈 입력란을 확인 해주세요.');
			const requiredElements = reportForm.querySelectorAll('[required]');
			requiredElements.forEach(element => {
				if(!element.checkValidity()) {
					const isVisible = !!element.offsetParent;
					if(isVisible) {
						element.focus();
					} else {
						let parent = element.parentElement;
						let page;
						while(parent) {
							if(parent.classList.contains('page')) {
								page = parent.id;
							}
							parent = parent.parentElement;
						}
						
						document.getElementById(`js-${page}-btn`).click();
						element.focus();
					}
				}
			});
		}
	}
}

window.onbeforeprint = function() {
	const elements = document.querySelectorAll('.horizontal-scroll');
	elements.forEach(function(element) {
		element.classList.remove('horizontal-scroll');
	});
	
	const today = new Date();
	const year = today.getFullYear();
	const month = today.getMonth() + 1;
	const day = today.getDate();
	document.getElementById('now-yyyy').textContent = year;
	document.getElementById('now-mm').textContent = month;
	document.getElementById('now-dd').textContent = day;
	
	const targetElements = document.querySelectorAll('.js-start-event');
	const elmentArray = Array.from(targetElements);
	elmentArray.forEach((elem, index) => {
		if(index > 0 && (index % 2 === 0)) {
			elem.classList.add('page-start');
		}
	});
}

window.onafterprint = function() {
	const elements = document.querySelectorAll('.js-horizontal-scroll');
	elements.forEach(function(element) {
		element.classList.add('horizontal-scroll');
	});
}

function printReport() {
	window.print();
}

function showPage(button, pageNumber) {
	const btns = document.querySelectorAll('.paging-navigation *');
	btns.forEach(btn => {
		if(btn.tagName === 'STRONG') {
			const aTag = document.createElement('a');
			aTag.textContent = btn.textContent;
			aTag.href = "javascript:void(0);";
			aTag.id = `js-page${btn.textContent}-btn`;
			aTag.setAttribute('onclick', `showPage(this, ${btn.textContent})`);
			btn.parentNode.replaceChild(aTag, btn);
		}
	});
	
	const pageCount = document.querySelectorAll('.page').length;
	for(let i=0;i < pageCount;i++) {
		if((i+1) === pageNumber) {
			const strongTag = document.createElement('strong');
			strongTag.textContent = button.textContent;
			button.parentNode.replaceChild(strongTag, button);
		}
		document.getElementById(`page${i+1}`).classList.add('hidden');
	}
	document.getElementById(`page${pageNumber}`).classList.remove('hidden');
	
	window.scrollTo({top: 0, behavior: 'smooth'});
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

function reportStatusUpdate(confmStatus) {
	const cnslIdx = document.getElementById('cnslIdx').value;
	const reprtIdx = document.getElementById('reprtIdx').value;
	
	
	if(confmStatus == '50' || confmStatus == '55') {
		const result = confirm('해당 보고서를 승인 하시겠습니까?')
		if(result){
			let data = {"confmStatus" : confmStatus,
						"cnslIdx" : cnslIdx,
						"reprtIdx" : reprtIdx}
			
			
			fetch(`${contextPath}/dct/report/updateReportStatus.do?mId=134`, {
				method: 'POST',
				body : JSON.stringify(data)
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
					const newURL = `${contextPath}/dct/consulting/cnslListAll.do?mId=124`;
					history.replaceState({}, '', newURL);
					window.location.href = newURL;
			});
		}
	}else if(confmStatus == '40') {
		const result = confirm('해당 보고서를 반려 하시겠습니까?')
		if(result){
			const confmCn = document.getElementById('confmCn').value;
			let data = {"confmStatus" : confmStatus,
						"cnslIdx" : cnslIdx,
						"reprtIdx" : reprtIdx,
						"confmCn" : confmCn}
			
			
			fetch(`${contextPath}/dct/report/updateReportStatus.do?mId=134`, {
				method: 'POST',
				body : JSON.stringify(data)
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
					const newURL = `${contextPath}/dct/consulting/cnslListAll.do?mId=95`;
					history.replaceState({}, '', newURL);
					window.location.href = newURL;
			});
		}
	}
	
	
}

function openModal(modalId) {
	document.getElementById(modalId).style.display = "block";
}



function closeModal(modalId) {
	document.getElementById(modalId).style.display = "none";
	
	if(document.querySelector("#confmCn")){
		document.querySelector("#confmCn").value = '';
	}
	
	
}
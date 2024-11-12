var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

hideLoading();

document.getElementById('contents-box').scrollIntoView({behavior:'auto'});

// 요구서식 추가
let maxDivs = 10;
let addRowIndex = 1;
let matches = document.querySelectorAll('[class*=hidden-value]');
let lastMatch = matches.length > 0? matches[matches.length - 1].className.match(/hidden-value(\d+)/) : null;
let rowIndex = lastMatch? parseInt(lastMatch[1], 10)+1 : (matches.length+1);
function addRow() {
	let addRow = document.createElement('div');
	let inputArea = document.getElementById('input-area');
	let divElement = inputArea.closest('div');
	let hiddenInputs = divElement.querySelectorAll('input[type="hidden"]');
	let checkLen = hiddenInputs.length;

	if(checkLen < maxDivs) {
		addRow.className = 'input-add-btns-wrapper03 mt05 pd-button add'+addRowIndex;
		
		
		addRow.innerHTML = '<input type="hidden" class="hidden-value'+rowIndex+'" name="hidden-value'+rowIndex+'"  value="">'+
											'<input type="text" class="form'+rowIndex+'" name="form'+rowIndex+'" value="" readonly>'+
	                                        '<div class="btns">'+
	                                            '<button type="button" class="btn-m01 btn-color02 open-modal01" onclick="openModal('+rowIndex+');">불러오기</button>'+
	                                            '<button type="button" class="btn-m01 btn-color03" onclick="removeRow('+addRowIndex+')">삭제</button>'+
	                                        '</div>';
		
		addRowIndex++;
		rowIndex++;
		checkLen++;
		
		
	}else {
		alert("요구서식 추가는 최대 10개까지 가능합니다.");
	}                    						
	inputArea.appendChild(addRow);
	
}


// 추가된 요구서식 삭제
function removeRow(idx) {
	let rowToRemove = document.querySelector('.add'+idx);
	
	if(rowToRemove) {
		rowToRemove.remove();
		checkLen--;
	}
}



// 요구서식 idx 동적 설정
const hiddenElements = document.querySelectorAll('.hidden-value');
hiddenElements.forEach(function(hiddenElement) {
	let idx = hiddenElement.getAttribute('data-idx');
	
	hiddenElement.className ='hidden-value'+idx;
	hiddenElement.name = 'hidden-value'+idx;
})

const formElements = document.querySelectorAll('.form');
formElements.forEach(function(formElement) {
	let idx = formElement.getAttribute('data-idx');

	formElement.className = 'form'+idx;
	formElement.name = 'form'+idx;
})


// 방문기업서식 불러오기 모달창 열기
function openModal(idx) {
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-member").show();
	});
	
	ok(idx);
}


//방문기업서식 불러오기 모달창 닫기
function closeModal() {
	$(".modal-wrapper").hide();
	$(".mask").hide();
}


// 엔터키 옵션
function handleEnterKeyPress(event) {
	if(event.key === 'Enter') {
		searchForm();
	}
}

// 방문기업서식 검색
function searchForm() {
	
	const form = document.getElementById('search-form');
	const mId = document.getElementById('mId').value;
	const formData = new FormData(form);
	
	showLoading();
	fetch(`${contextPath}/dct/supportForm/formList.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok) {
			throw new Error('HttpStatus is not ok');
		}
		return response.json();
	})
	.then((data) => {
		let tbody = document.getElementById('formList');
		tbody.innerHTML = '';
		let list = data.formList;
		let html;
		
		document.getElementById('cnt').textContent = list.length;
		if(list.length > 0) {
			list.forEach((item, idx) => {
				let admsfmIdx = item.ADMSFM_IDX;
				let row = document.createElement('tr');
				row.innerHTML = `
					<td>
						<input type="hidden"  id="hidden_${admsfmIdx}" value="${admsfmIdx}">
						<input type="checkbox" id="checkbox_${admsfmIdx}" class="checkbox-type01" value=""  onclick="handleCheck(${admsfmIdx})">
					</td>
					<td>
						<strong class="point-color01">
							${item.ADMSFM_NM}
						</strong>
					</td>
					<td>${item.ADMSFM_VER}</td>
					<td>${item.PRTBIZ_TITLE}</td>
					<td>${item.JOB_TYPE}</td>
					<td>${item.SGNTR}</td>`;
				
				tbody.appendChild(row);
			});
			tbody.scrollIntoView({behavior:'smooth'});
		} else {
			let row = document.createElement('tr');
			row.innerHTML = `<td colspan="6">검색결과가 없습니다.</td>`;
			tbody.appendChild(row);
		}
		hideLoading();
	});
}


// 체크박스 선택 제어
var selectItem;
var hiddenValue;

function handleCheck(item) {
	let checkBoxes = document.querySelectorAll('.checkbox-type01');
	let checkItem = document.getElementById('checkbox_'+item);
	let hiddenInput = document.getElementById('hidden_'+item);
	let rowData =[];
	
	let parentTr = checkItem.closest('tr');
	let cells = parentTr.querySelectorAll('td');
	
	// 하나만 선택가능
	checkBoxes.forEach(function(checkBox) {
		if(checkBox.id !== 'checkbox_' + item) {
			checkBox.checked = false;
		}
	});
	
	// 선택된 행의 데이터 가져오기
	cells.forEach(function(cell) {
		rowData.push(cell.innerText);  
	});
	
	
	
	if(checkItem.checked) {
		hiddenValue = hiddenInput.value;
		selectItem = rowData[1];
	}
}


// 방문기업서식 선택 후 확인
function ok(idx) {
	let okBtn = document.getElementById('ok');
	
	okBtn.onclick = function() {
		let inputBox =document.querySelector('.form'+idx);
		let inputHiddenBox =document.querySelector('.hidden-value'+idx);
		let checkBoxes = document.querySelectorAll('input[type="checkbox"]:checked');

		if(checkBoxes.length == 0) {
			alert("방문기업서식을 선택해주세요.");
		}
		
		checkBoxes.forEach(function(checkbox) {
			var tdElement = checkbox.closest('td');
			var hiddenValue = tdElement.querySelector('input[type="hidden"]').value;
			
			inputBox.value = selectItem;
			inputHiddenBox.value = hiddenValue;
			
			closeModal();
			checkbox.checked = false; // 다음 서식 선택위해 선택값 초기화
		})
		
	}
}

// radio 사용/미사용 표시
let useYn = document.getElementById('useYn').value;
if(useYn != null) {
	if(useYn != 'N') {
		document.getElementById("radio0101").checked = true;
	}else {
		document.getElementById("radio0102").checked = true;
	}
}


// 저장/완료 처리
let isProgress = false;
function save(action) {

	if(isProgress) {
		console.log('중복클릭');
		return;
	}
	
	// 유효성 검사
	const inputItem = document.getElementById('sptjNm');
	if(inputItem.value.length == 0 ) {
		alert("서식그룹명을 입력해주세요.");
		inputItem.focus();
		return false;
	}
	
	let ddElement = document.getElementById('input-area');
	let divElement = ddElement.closest('div');
	let hiddenInputs = divElement.querySelectorAll('input[type="hidden"]');
	let seenValues = {};
	for(var i = 0 ; i <  hiddenInputs.length ; i++) {
		var hiddenInput = hiddenInputs[i]
		var hiddenValue = hiddenInput.value
		var num  = i+1
		if(hiddenValue.length < 1 ) {
			alert(num+"번째 방문기업서식을 선택해주세요.");
			return false;
		}

		if(seenValues[hiddenValue]) {
				alert("중복된 서식이 있습니다. 선택된 서식을 확인해주세요.");
				hiddenInput.focus();
				return false;
		}else {
			seenValues[hiddenValue] = true;
		}
	}
		
	
	// 처리
	isProgress = true;
	let msg = '';
	if(action === 'temp' || action === 'done') {
		const formData = new FormData(document.getElementById('supportForm'));
		formData.append('action', action);
		
		$.ajax({
			type: 'POST',
			url: `${contextPath}/dct/supportForm/inputProc.do?mId=115`,
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				isProgress = false;
				if(action == 'temp') {
					msg ='정상적으로 저장되었습니다.';
					window.location.href=`${contextPath}/dct/supportForm/list.do?mId=115`
				}else {
					msg ='정상적으로 등록되었습니다.'
					window.location.href=`${contextPath}/dct/supportForm/list.do?mId=115`
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
		})
	}
}	
	

// 로딩 표시/미표시
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
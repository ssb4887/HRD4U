var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
var mId = 42;

//검색 파라미터 유지
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

var selectItem;
//체크박스 선택 제어
function handleCheck(item) {
	let checkBoxes = document.querySelectorAll('.checkbox-type01');
	let checkItem = document.getElementById('checkbox_'+item);
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
		selectItem = rowData[1];
	}
	
}

// 요구서식 페이지 이동
function apply() {
	
	let form = document.getElementById("formBox");
	let checkboxes =document.querySelectorAll("input[name='selectCheckbox']");
	
	if(selectItem != undefined || selectItem != null) {
		
		checkboxes.forEach(function(checkbox) {
			if(checkbox.checked) {
				sessionStorage.setItem("checkboxValue", checkbox.value);
				let input = document.createElement("input");
				input.id="checkboxValue"
				input.type="hidden";
				input.name="checkboxValues";
				input.value=sessionStorage.getItem("checkboxValue");
				form.appendChild(input);
			}
		});
		
		form.submit();
	}else {
		alert("방문기업관리를 진행할 기업을 선택해주세요.");
	}
		
}

// 기업바구니 - 기업정보 관리 페이지로 이동
const bskButtons = document.querySelectorAll('.btn-idx');
bskButtons.forEach(function(bskBtn) {
	bskBtn.addEventListener('click', function(event) {
		const bplNo = bskBtn.getAttribute('data-idx');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/dct/basket/view.do?mId=`+mId;
		form.action = actionUrl;
		document.getElementById('bplNo').value = bplNo;
		form.submit();
	});
});

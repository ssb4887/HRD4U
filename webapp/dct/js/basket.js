const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);	
var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

// 분류예외지정 모달 open
const addClExcpetionModalOpen = (id) => {
	var checkboxes = document.querySelectorAll('.checkbox');
	var selectedRows = [];
	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			var tdElements = checkbox.parentElement.parentElement;
			var rowData = {};
			rowData['bplNo'] = tdElements.getElementsByClassName("bplNo")[0].querySelector('strong').innerText;
			rowData['bplNm'] = tdElements.getElementsByClassName("bplNm")[0].innerText;
			selectedRows.push(rowData);
		}
	})
	if(selectedRows.length == 0) {
		alert("체크 박스를 확인해 주세요")
	}else{
			const modal = document.getElementById('modal-action01');
			const modalData = document.getElementById('modalData');
			selectedRows.forEach(function(item,index) {
				var listItem = document.createElement('p');
				listItem.className = 'word';
				listItem.innerHTML = `
				<strong data-value="${item['bplNo']}" id="chkbplNo">${item['bplNm']}</strong>
				
				<button type="button" class="btn-delete" onclick="delListItem(this)">삭제</button>`;
				modalData.appendChild(listItem);
			})
			modal.style.display = 'block';
			
			document.getElementById('start_date').min = getCurrentDate();
			document.getElementById('end_date').min = getCurrentDate();
			
			
		}
}

// 분류선택 변경 모달 오픈 + 대분류 불러오기
const changeClModalOpen = (id) => {
	var tab1 = document.getElementById('tab1');
	
	var checkboxes = tab1.querySelectorAll('.checkbox');
	var selectedRows = [];
	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			var tdElements = checkbox.parentElement.parentElement;
			var rowData = {};
			rowData['bplNo'] = tdElements.getElementsByClassName("bplNo")[0].querySelector('strong').innerText;
			rowData['bplNm'] = tdElements.getElementsByClassName("bplNm")[0].innerText;
			selectedRows.push(rowData);
		}
	})
	if(selectedRows.length == 0) {
		alert("체크 박스를 확인해 주세요")
	}else{
		
		const modal = document.getElementById('modal-action02');
		const modalData = document.getElementById('modalData_02');
		selectedRows.forEach(function(item,index) {
			var listItem = document.createElement('p');
			listItem.className = 'word';
			listItem.innerHTML = `
				<strong data-value="${item['bplNo']}" id="chkbplNo">${item['bplNm']}</strong>
				<button type="button" class="btn-delete" onclick="delListItem(this)">삭제</button>`;
			modalData.appendChild(listItem);
			})		
			modal.style.display = 'block';
		
		document.getElementById('start_date2').min = getCurrentDate();
		document.getElementById('end_date2').min = getCurrentDate();
	}

}

var bplNoListByAll = [];
// 분류일괄 변경 모달 오픈 + 대분류 불러오기
const allChangeClModalOpen = (id) => {
	const modal = document.getElementById('modal-action03');
	const searchForm = document.getElementById('searchForm');
	const formData = new FormData(searchForm);
	showLoading();
	
	
	fetch(`${contextPath}`+'/dct/basket/getBplNoList.do?mId=42', {
		method: 'POST',
		body : formData
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		bplNoListByAll = data.result.body;	
		const bplNoListLengthData = document.getElementById('modalData_03');
		const strongBplNo = document.createElement('strong');
		strongBplNo.innerText = bplNoListByAll.length + "개";
		bplNoListLengthData.appendChild(strongBplNo);
		hideLoading();
		modal.style.display = 'block';
		
		
		document.getElementById('start_date3').min = getCurrentDate();
		document.getElementById('end_date3').min = getCurrentDate();
		
		
	});
	
		

		
}


// [분류 선택 변경 + 분류 일괄 변경 모달] 대분류 선택시 소분류 출력
const handleSelectChange = (selectId, containerId) => {
	const supClSelectBox = document.getElementById(selectId);
	var selectedValue = supClSelectBox.value;
	const checkboxContainer = document.getElementById(containerId);
	checkboxContainer.innerHTML =``;
	
	
	
	fetch(`${contextPath}`+'/dct/basket/getClSclas.do?mId=42', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body : selectedValue
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		var clSclasData = data.result.body;
		
		clSclasData.forEach(item => {
			var div = document.createElement('div');
			div.className = "input-checkbox-area02";
			var checkbox = document.createElement('input');
			checkbox.type = "checkbox";
			checkbox.name = "clSclascheckbox"
			checkbox.value = item.sclasCd;
			
			var label = document.createElement('label');
			label.appendChild(document.createTextNode(item.sclasNm));
			
			div.appendChild(checkbox);		
			div.appendChild(label);		
			checkboxContainer.appendChild(div);
		})
		
	});
	

}



// 분류 선택 변경 + 분류 일괄변경
const handleClChange = (id) => {
	
	var jsonArray =[];
	var data = [];
	
	// 카테시안 - 모든 경우의수 찾기 jsonArray 만들기
	const cartesianProduct = (arr1, arr2, selectedOption, startDate, endDate) => {
		return arr1.flatMap(item1 => {
			if(arr2.length != 0) {
					return {
						bplNo : item1,
						sclasCd : arr2,
						lclasCd : selectedOption,
						startDate : startDate,
						endDate : endDate,
					};
			
			}else {
				return {
					bplNo : item1,
					lclasCd : selectedOption,
					startDate : startDate,
					endDate : endDate
				};
			}
		});
	};
	
	
	// 분류 선택 변경
	if(id == 'clChangeBtn_02'){
		const clChangeForm = document.getElementById('clChangeForm_02');
		
		const formData = new FormData(clChangeForm);
		const selectedOption = formData.get('supClSelect_02');
		if(selectedOption == "") {
			alert("대분류를 선택하세요.")
			return;
		}
		const selectedCheckboxes = Array.from(formData.getAll("clSclascheckbox"));
		const bplNoNodes = document.querySelectorAll('#chkbplNo');
		const bplNoList = [];
		bplNoNodes.forEach(item => {
			bplNoList.push(item.getAttribute('data-value'));
		});
		const startDate = document.getElementById("start_date2").value;
		const endDate = document.getElementById("end_date2").value;
		
		

		jsonArray = cartesianProduct(bplNoList,selectedCheckboxes,selectedOption, startDate, endDate);
		

	
		// 분류 일괄 변경
	}else if(id == 'clChangeBtn_03'){
		const clChangeForm = document.getElementById('clChangeForm_03');
		
		
		
		const formData = new FormData(clChangeForm);
		const selectedOption = formData.get('supClSelect_03');
		if(selectedOption == "") {
			alert("대분류를 선택하세요.")                                                                                          
			return;
		}
		const selectedCheckboxes = Array.from(formData.getAll("clSclascheckbox"));
		
		const startDate = document.getElementById("start_date2").value;
		const endDate = document.getElementById("end_date2").value;

		jsonArray = cartesianProduct(bplNoListByAll,selectedCheckboxes,selectedOption, startDate, endDate);
		
		
	}
	
	showLoading();
	
	fetch(`${contextPath}`+'/dct/basket/insertClPassiv.do?mId=42', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(jsonArray)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			if(id == 'clChangeBtn_02') {
				hideLoading();
				alert("선택 수동분류 완료")
				closeModal('closeBtn_02');
				
			}else if(id == 'clChangeBtn_03') {
				hideLoading();
				alert("일괄 수동분류 완료")
				closeModal('closeBtn_03');
				
			}

		}
	});
	
}


// 모달 Close + Data 삭제
const closeModal = (id) => {
	$('.mask').fadeOut(150);
	
	if(id == 'closeBtn_01'){
		const modal = document.getElementById('modal-action01');
		const modalData = document.getElementById('modalData');
		const startDate = document.getElementById('start_date');
		const endData = document.getElementById('end_date');
		modalData.innerHTML = ``;
		startDate.value = ``;
		endData.value = ``;
		modal.style.display = 'none';
	}else if(id == 'closeBtn_02') {
		const modal = document.getElementById('modal-action02');
		const modalData = document.getElementById('modalData_02');
		const selectedData = document.getElementById('supClSelect_02');
		const checkboxData = document.getElementById('checkboxContainer_02');
		const startDate = document.getElementById('start_date2');
		const endData = document.getElementById('end_date2');
		startDate.value = ``;
		endData.value = ``;
		modalData.innerHTML = ``;
		selectedData.value = ``;
		checkboxData.innerHTML = ``;
		modal.style.display = 'none';
	}else if(id == 'closeBtn_03') {
		const modal = document.getElementById('modal-action03');
		const modalData = document.getElementById('modalData_03');
		const startDate = document.getElementById('start_date2');
		const endData = document.getElementById('end_date2');
		startDate.value = ``;
		endData.value = ``;
		modalData.innerHTML = ``;

		modal.style.display = 'none';
	}else if(id == 'closeBtn_04') {
		const modal = document.getElementById('modal-action04');
		modal.style.display = 'none';
		const fileData = document.getElementById('uploadBtn');
		const fileName = document.getElementById('fileName');
		fileData.value = '';
		fileName.value = '';
	}else if(id == 'closeBtn_05') {
		const modal = document.getElementById('modal-action05');
		modal.style.display = 'none';
	}else if(id == 'closeBtn_06') {
		const modal = document.getElementById('modal-action06');
		modal.style.display = 'none';
	}
}


window.addEventListener('click', function(event) {
const modals = document.querySelectorAll('.modal-wrapper');

modals.forEach((modal) => {
		if(event.target === modal) {
			modal.style.display = 'none';
		}
}
	)
});

/*
// 탭 유지를 위한 코드
document.addEventListener("DOMContentLoaded", function() {
	var mId = document.getElementById("id_mId").value;
	if(mId == 42) {
		var preTab = sessionStorage.getItem("activeTab");
		if(preTab) {
			document.getElementById(preTab).style.display = "block";
			switch(preTab) {
			case "tab1":
				document.getElementsByClassName("tablinks")[0].className += " active";
				break;
			case "tab2":
				document.getElementsByClassName("tablinks")[1].className += " active";
				break;
			case "tab3":
				document.getElementsByClassName("tablinks")[2].className += " active";
				break;
			case "tab4":
				document.getElementsByClassName("tablinks")[3].className += " active";
				break;
			}
		}else {
			var defaultTab = "tab1";
			document.getElementById(defaultTab).style.display = "block";
			document.getElementsByClassName("tablinks")[0].className += " active";
		}
	}else{
		sessionStorage.clear();
	}
	
	
})

// 탭 변경
function openTab(evt, tabName) {
	var i, tabcontent, tablinks;
	
	tabcontent = document.getElementsByClassName("tabcontent");
	for(i = 0; i< tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	
	tablinks = document.getElementsByClassName("tablinks");
	

	for(i = 0; i< tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");	
	}
	

	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.className += " active";
	
	sessionStorage.setItem("activeTab",tabName);
	
	



	
}*/

function showTab(button, tabNumber) {
	const btns = document.querySelectorAll('.tablinks');
	btns.forEach(btn => {
		btn.classList.remove('active')
		console.log('111');
		button.setAttribute('title', '선택되지않음');
	})
	button.classList.add('active')
	button.setAttribute('title', '선택됨');
	
	const tabCount = document.querySelectorAll('.tabcontent').length;
	for(let i=0;i < tabCount;i++) {
		document.getElementById(`tab${i+1}`).classList.add('hidden');
	}
	document.getElementById(`tab${tabNumber}`).classList.remove('hidden');
	
	// 탭 이동시 데이터 초기화(새로고침)


}
// 삭제 버튼
const delListItem = (param) => {
	var deleteList = param.parentElement;
	deleteList.remove();
}

// 분류 예외 지정 form submit
const  addClExcept = () => {
	
	var tab2 = document.getElementById('tab2');
	 
	var startDate = document.getElementById('start_date').value;
	var endDate = document.getElementById('end_date').value;
	var bplNoNode = document.querySelectorAll('#chkbplNo');
	var bplList = [];
	
	bplNoNode.forEach(function(bplNode) {
		bplList.push(bplNode.getAttribute('data-value'));
	})
	
	if(!startDate || !endDate) {
		alert("시작일과 종료일을 설정해 주세요")
		return;
	}
	
	
	var formData = {"startDate" : startDate,
					"endDate" : endDate,
					"bplList" : bplList
	};
	
	
	fetch(`${contextPath}`+'/dct/basket/addExcept.do?mId=42', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("예외 기업 등록 완료");
			document.getElementById('closeBtn_01').click();
		}
	});

};

// 지부 지사 선택시 해시태그 출력
const handleSelectInstt = (id,id2) => {
	const selectedBranch = document.getElementById(id).value;
	const hashTagSelect = document.getElementById(id2);
	hashTagSelect.options.length = 0;
	
	
	const isHashTag = urlParams.get("isHashTag");
	
	fetch(`${contextPath}`+'/dct/basket/getHashTag.do?mId=42', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: selectedBranch
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		const hashTagData = data.result.body;
		localStorage.setItem('hashTagData', JSON.stringify(hashTagData));
		hashTagData.forEach(function(hashTag) {
			const hashTagOption = document.createElement('option');
			hashTagOption.value = hashTag.hashtagCd;
			hashTagOption.textContent = hashTag.hashtagNm;
			if(isHashTag == hashTagOption.value) {
				hashTagOption.selected = true;
			}
			
			hashTagSelect.appendChild(hashTagOption);
		
		})
	});
}


window.onload = function() {
	var signguData = localStorage.getItem('signguData');
	var hashTagData = localStorage.getItem('hashTagData');
	var clSclasData = localStorage.getItem('clSclasData');
	
	$("#allchkPrint").click(function(){
	    // 클릭되었으면
	    if($("#allchkPrint").prop("checked")){
	        // input태그의 name이 chk인 태그들을 찾아서 checked옵션을 true로 정의
	        $("input[name=hashtagCd]").prop("checked",true);
	        // 클릭이 안되있으면
	    }else{
	        // input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
	        $("input[name=hashtagCd]").prop("checked",false);
	    }
	});
	
	if(signguData) {
			var jsonSignguData = JSON.parse(signguData);
			
			var parents = ['signguSelect', 'signguSelect2','signguSelect3'].map(id => document.getElementById(id));
			for(i=0; i < parents.length; i++) {
				jsonSignguData.forEach(function(signgu) {
					const sigugnOption = document.createElement('option');
					sigugnOption.value = signgu.signgu;
					sigugnOption.textContent = signgu.signgu;
					parents[i].appendChild(sigugnOption);
			})
			
		}


	}
	
	if(hashTagData) {
			var jsonHashTagData = JSON.parse(hashTagData);
			
			var parents = ['isHashTag1', 'isHashTag2','isHashTag3'].map(id => document.getElementById(id));
			for(i=0; i < parents.length; i++) {
			jsonHashTagData.forEach(function(hashTag) {
			const hashTagOption = document.createElement('option');
			hashTagOption.value = hashTag.hashtagCd;
			hashTagOption.textContent = hashTag.hashtagNm;
			parents[i].appendChild(hashTagOption);

		})
			}
	}
	
	if(clSclasData) {
			var jsonClSclasData = JSON.parse(clSclasData);
			
			var parents = ['isSclas', 'isSclas2','isSclas3'].map(id => document.getElementById(id));
			
			for(i=0; i < parents.length; i++) {
				const sclasDefaultOption = document.createElement('option');
				sclasDefaultOption.value = '';
				sclasDefaultOption.textContent = '전체';
				parents[i].appendChild(sclasDefaultOption);
				
			jsonClSclasData.forEach(function(sclas) {	
			const sclasOption = document.createElement('option');
			sclasOption.value = sclas.sclasCd;
			sclasOption.textContent = sclas.sclasNm;
			parents[i].appendChild(sclasOption);

			})
		}
	}
	
	
	
}

// 해시태그 선택 할당
const addHashTagSelectedCorp = () => {
	var checkboxes = document.querySelectorAll('.checkbox');
	var selectedbplNoList = [];
	var selectedBranch = document.getElementById('branchSelect').value;
	var selectedHashTag = document.getElementById('hashTagSelect').value;
	var selectedBrachNm = document.getElementById('branchSelect').options[document.getElementById('branchSelect').selectedIndex].innerText;
	var selectedHashTagNm = document.getElementById('hashTagSelect').options[document.getElementById('hashTagSelect').selectedIndex].innerText;
	
	
	
	if(selectedBranch == '') {
		alert('소속기관을 선택하세요.')
		return ;
	}
	
	var jsonArray = [];
	
	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			var tdElements = checkbox.parentElement.parentElement;
			var selectedBplNo = tdElements.getElementsByClassName("bplNo")[0].innerText.trim();
			selectedbplNoList.push(selectedBplNo);
		}
	})
	
	for(var i = 0; i <selectedbplNoList.length; i++) {
		var json = {"bplNo" : selectedbplNoList[i],
					"insttIdx" : selectedBranch,
					"hashtagCd" : selectedHashTag
		} ;
		jsonArray.push(json);
	}
	
	if(selectedbplNoList.length == 0) {
		alert("체크 박스를 확인해 주세요")
	}else{
		const result = confirm(`선택된 ` +selectedbplNoList.length+ `개의 기업을 `+selectedBrachNm +`/` + selectedHashTagNm + `으로 할당하시겠습니까?`)

		if(result) {
			
			fetch(`${contextPath}`+'/dct/basket/assignHashTag.do?mId=42', {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(jsonArray)
			}).then((response) => {
				if(!response.ok){
					throw new Error("HttpStatus is not ok")
				}
				return response.json();
			})
			.then((data) => {
				if(data.result.code == 1) {
					alert("해시태그 선택 할당 성공");
				}
			});
			

		}else {
			alert('취소 되었습니다.')
		}
		
	}	
}


// 해시태그 일괄 할당
const addHashTagAllCorp = () => {

	var selectedBranch = document.getElementById('branchSelect').value;
	var selectedHashTag = document.getElementById('hashTagSelect').value;
	var selectedBrachNm = document.getElementById('branchSelect').options[document.getElementById('branchSelect').selectedIndex].innerText;
	var selectedHashTagNm = document.getElementById('hashTagSelect').options[document.getElementById('hashTagSelect').selectedIndex].innerText;
	
	
	if(selectedBranch == '') {
		alert('소속기관을 선택하세요.')
		return ;
	}
	
	const searchForm = document.getElementById('searchForm03');
	const formData = new FormData(searchForm);
	fetch(`${contextPath}`+'/dct/basket/getBplNoList.do?mId=42', {
		method: 'POST',
		body : formData
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		bplNoListByAll = data.result.body;	
		
		
		var jsonArray = [];
		
		
		
		for(var i = 0; i <bplNoListByAll.length; i++) {
			var json = {"bplNo" : bplNoListByAll[i],
						"insttIdx" : selectedBranch,
						"hashtagCd" : selectedHashTag
			} ;
			jsonArray.push(json);
		}
		
		hideLoading();
		const result = confirm(`선택된 ` +bplNoListByAll.length+ `개의 기업을 `+selectedBrachNm +`/` + selectedHashTagNm + `으로 할당하시겠습니까?`)
		
		if(result) {
			
			fetch(`${contextPath}`+'/dct/basket/assignHashTag.do?mId=42', {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(jsonArray)
			}).then((response) => {
				if(!response.ok){
					throw new Error("HttpStatus is not ok")
				}
				return response.json();
			})
			.then((data) => {
				if(data.result.code == 1) {
					alert("해시태그 일괄 할당 성공");
				}
			});
			

		}else {
			alert('취소 되었습니다.')
		}
		
		
	});
	

		
		
	}



const paginationHandler = (formId) => {
	event.preventDefault();

	switch(formId) {
		case 'pageForm':
			document.pageForm.pageNum.value = event.target.dataset.pagenum;
			document.pageForm.submit();
			break;
		case 'pageForm2':
			document.pageForm2.pageNum.value = event.target.dataset.pagenum;
			document.pageForm2.submit();
			break;
		case 'pageForm3':
			document.pageForm3.pageNum.value = event.target.dataset.pagenum;
			document.pageForm3.submit();
			break;
		case 'pageForm4':
			document.pageForm4.pageNum.value = event.target.dataset.pagenum;
			document.pageForm4.submit();
			break;
		case 'amount':
			document.pageForm.amount.value = event.target.options[event.target.selectedIndex].getAttribute('value');
			document.pageForm.pageNum.value = '1';
			document.pageForm.submit();
			break;
	}
	
}

// 기업 데이터 업로드 모달
const dataUploadModalOpen = () =>{
	$('.mask').fadeIn(150);
	const modal = document.getElementById('modal-action04');

	modal.style.display = 'block';
	
	document.getElementById('createdAt').innerText = new Date().toISOString().substring(0,10);
	
}

// 기업 데이터 엑셀파일 업로드

const dataUploadHandler = () => {
	// excel 확장자 제한
	const excelFile = document.getElementById('uploadBtn');
	const createdBy = document.getElementById('createdBy');
	const createdAt = document.getElementById('createdAt');
	const allowedExtensions = ['.xlsx'];
	
	const excelUploadForm = document.getElementById('excelUploadForm');
	const formData = new FormData();
	
	formData.append('createdBy', createdBy.value)
	formData.append('createdAt', createdAt.innerText)
	formData.append('excelfile', excelFile.files[0]);
	
	
	if(excelFile.files.length > 0) {
		const fileName = excelFile.files[0].name;
		const fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
		if(!allowedExtensions.includes(fileExtension)) {
			alert('허용되지 않는 확장자 입니다.\n업로드 가능한 확장자는 [.xlsx] 입니다.')
			return ;
		}
	}
	
	fetch(`${contextPath}`+'/dct/basket/excelUpload.do?mId=42', {
		method: 'POST',
		body: formData

	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.body == 1) {
			alert('엑셀파일 업로드 완료');
			closeModal('closeBtn_04');
			window.location.reload();
		}

		})
	
}

// 분류예약조회
const reserveModalEdit = (id, no) => {
	$('.mask').fadeIn(150);
	const modal = document.getElementById('modal-action05');
		modal.style.display = 'block';
		document.getElementById('resveIdx').value = id;
		
		fetch(`${contextPath}`+'/dct/basket/resGet.do?mId=44&resveIdx='+id, {
			method: 'GET',
		}).then((response) => {
			if(!response.ok){
				throw new Error("HttpStatus is not ok")
				alert("분류예약 조회에 실패하였습니다.");
			}
			return response.json();
		})
		.then((data) => {
			if(data.result.code == 1) {
				const idx = document.getElementById('editNo');
				idx.innerHTML = no;
				
				const name = document.getElementById('editName');
				name.innerHTML = data.result.body.REGI_NAME;
				
				const date = document.getElementById('resve_date');
				const year = data.result.body.RESVE_DATE.substr(0,4);
				const month = data.result.body.RESVE_DATE.substr(4,2);
				const day = data.result.body.RESVE_DATE.substr(6,2);
				document.getElementById('resve_date').value = year+"-"+month+"-"+day
				
				const hour = data.result.body.RESVE_HOUR
				const minute = data.result.body.RESVE_MINUTE.substr(0,2);
				const second = data.result.body.RESVE_MINUTE.substr(2,2);
				
				document.getElementById('resve_time').value = hour+":"+minute+":"+second
				
				const status = data.result.body.STATUS
				const btn = document.getElementById('editExceptPostBtn');
				if(status == '완료') {
					btn.style.display = 'none';
				} else {
					btn.style.display = 'inline-block';
				}
			}
		});
}

// 분류예약등록
const reserveModalOpen = (id, status) => {
	if(status != '완료' && status != '') {
		alert("예약 내역이 존재 할 시 추가 등록은 불가합니다.");
		document.getElementById('closeBtn_06').click();
		return;
	}
	$('.mask').fadeIn(150);
	const modal = document.getElementById('modal-action06');
		modal.style.display = 'block';
}

// 분류예약등록
const  reserveExcept = () => {
	var modal = document.getElementById('modal-action05').style.display;
	if(modal == '') {
		var resveDate = document.getElementById('resve_date2').value.replace(/-/g,"");
		var resveTime = document.getElementById('resve_time2').value.replace(/:/g,"");
	} else {
		var resveDate = document.getElementById('resve_date').value.replace(/-/g,"");
		var resveTime = document.getElementById('resve_time').value.replace(/:/g,"");
	}
	
	var resveHour = resveTime.substr(0,2);
	var resveMinute = resveTime.substr(2,6);
	
	var bplList = [];
	
	if(!resveDate || !resveTime) {
		alert("예약일시를 설정해 주세요")
		return;
	}
	
	var formData = {
		"resveDate" : resveDate,
		"resveHour" : resveHour,
		"resveMinute" : resveMinute,
		"status" : "예약"
	};
	
	fetch(`${contextPath}`+'/dct/basket/resAddProc.do?mId=44', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("분류예약 등록에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("분류 예약이 완료되었습니다.");
			document.getElementById('closeBtn_06').click();
			window.location.reload();
		}
	});

}

// 분류예약수정
const  reserveEdit = () => {
	var modal = document.getElementById('modal-action05').style.display;
	if(modal == '') {
		var resveDate = document.getElementById('resve_date2').value.replace(/-/g,"");
		var resveTime = document.getElementById('resve_time2').value.replace(/:/g,"");
	} else {
		var resveDate = document.getElementById('resve_date').value.replace(/-/g,"");
		var resveTime = document.getElementById('resve_time').value.replace(/:/g,"");
	}
	var resveHour = resveTime.substr(0,2);
	var resveMinute = resveTime.substr(2,6);
	var bplList = [];
	
	if(!resveDate || !resveTime) {
		alert("예약일시를 설정해 주세요")
		return;
	}
	
	var formData = {
		"resveDate" : resveDate,
		"resveHour" : resveHour,
		"resveMinute" : resveMinute,
		"resveIdx" : document.getElementById('resveIdx').value
	};
	
	fetch(`${contextPath}`+'/dct/basket/resEditProc.do?mId=44', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("분류예약 수정에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("분류 예약이 수정이 완료되었습니다.");
			document.getElementById('closeBtn_05').click();
			window.location.reload();
		}
	});

}

// 분류예약삭제
const  reserveDel = () => {
	var modal = document.getElementById('modal-action05').style.display;
	if(modal == '') {
		var resveDate = document.getElementById('resve_date2').value.replace(/-/g,"");
		var resveTime = document.getElementById('resve_time2').value.replace(/:/g,"");
	} else {
		var resveDate = document.getElementById('resve_date').value.replace(/-/g,"");
		var resveTime = document.getElementById('resve_time').value.replace(/:/g,"");
	}
	var resveHour = resveTime.substr(0,2);
	var resveMinute = resveTime.substr(3,5);
	var bplList = [];
	
	if(!resveDate || !resveTime) {
		alert("예약일시를 설정해 주세요")
		return;
	}
	
	var formData = {
		"resveDate" : resveDate,
		"resveHour" : resveHour,
		"resveMinute" : resveMinute,
		"resveIdx" : document.getElementById('resveIdx').value
	};
	
	fetch(`${contextPath}`+'/dct/basket/resDelProc.do?mId=44', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("분류예약 수정에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("분류 예약 삭제가 완료되었습니다.");
			document.getElementById('closeBtn_05').click();
			window.location.reload();
		}
	});

}

const tabMove = (mId,lclasCd) => {
	var tab = document.forms.tab;
	document.getElementById('id_mId').value = mId;
	document.getElementById('id_lclasCd').value = lclasCd;
	tab.action=`${contextPath}`+'/dct/basket/resList.do';
	tab.submit();
}

const searchList = () => {
	var mId = document.getElementById('id_mId').value;
	var lunit = document.getElementById('lunit').value;
	if(mId == 44) {
		location.href=`${contextPath}`+'/dct/basket/resList.do?mId='+mId+'&lunit='+lunit
	} else {
		var lclasCd = document.getElementById("id_lclasCd").value;
		location.href=`${contextPath}`+'/dct/basket/resList.do?mId='+mId+'&lunit='+lunit+'&lclasCd='+lclasCd
	}
}

// 분류관리조회
const openClassification = (snum,lnum) => {
	$('.mask').fadeIn(150);
	const modal = document.getElementById('modal-action05');
		modal.style.display = 'block';
		document.getElementById('id_lclasCd').value = lnum;
		document.getElementById('id_sclasCd').value = snum;
		fetch(`${contextPath}`+'/dct/basket/classGet.do?mId=43&sclasCd='+snum+'&lclasCd='+lnum, {
			method: 'GET',
		}).then((response) => {
			if(!response.ok){
				throw new Error("HttpStatus is not ok")
				alert("분류예약 조회에 실패하였습니다.");
			}
			return response.json();
		})
		.then((data) => {
			if(data.result.code == 1) {
				const name = document.getElementById('regiName');
				name.innerHTML = data.result.body.REGI_NAME;
				
				const date = document.getElementById('regiDate');
				date.innerHTML = "";
				let dateformat = new Date(data.result.body.REGI_DATE);
				date.innerHTML += dateformat.getFullYear()+`-`+( (dateformat.getMonth()+1) < 10 ? `0` + (dateformat.getMonth()+1) : (dateformat.getMonth()+1) )+`-`;
				date.innerHTML += ( (dateformat.getDate()) < 10 ? `0` + (dateformat.getDate()) : (dateformat.getDate()) );
				
				const lclasNm = document.getElementById('lclasNm');
				lclasNm.innerHTML = data.result.body.LCLAS_NM;
				
				const sclasNm = document.getElementById('sclasNm');
				sclasNm.innerHTML = data.result.body.SCLAS_NM;
				
				const sclasVal = document.getElementById('sclasVal');
				var val = data.result.body.SCLAS_CD;
				sclasVal.innerHTML = "";
				switch(val) {
					case 1: // 일학습병행
						option = `<p class="word point-color01">`;
						option += "상시근로자 50인 이상 및 신용등급 B등급 이상 <br/>";
						option += "청년친화 강소기업 및 강소기업 <br/>";
						option += "best hrd 선정기업";
						option += `</p>`;
						sclasVal.innerHTML += option;
						break;
					case 2:
						sclasVal.innerHTML = `<input type="text" id="id_sclasVal" name="sclasVal" onkeypress="isNumber();" maxlength="4" value="" style="width: 100px;" />`;
						document.getElementById('id_sclasVal').value = data.result.body.SCLAS_VAL;
						break;
					case 3:
						option = "최근 3개년 기준 훈련실시 업종 상위권 안에 드는 업종기업이나 훈련 미참여";
						sclasVal.innerHTML += option;
						break;
					case 4:
						sclasVal.innerHTML = `<input type="text" id="id_sclasVal" name="sclasVal" onkeypress="isNumber();" maxlength="4" value="" style="width: 100px;" />`;
						document.getElementById('id_sclasVal').value = data.result.body.SCLAS_VAL;
						
						break;
					case 5:
						sclasVal.innerHTML = `<input type="text" id="id_sclasVal" name="sclasVal" onkeypress="isNumber();" maxlength="4" value="" style="width: 100px;" />`;
						document.getElementById('id_sclasVal').value = data.result.body.SCLAS_VAL;
						break;
					case 6:
						option = "산업단지에 위치하는 기업";
						sclasVal.innerHTML = option;
						break;
					case 7:
						option = "S-OJT만 참여한 기업군";
						sclasVal.innerHTML = option;
						break;
					case 8:
						option = "일학습병행만 참여한 기업군";
						sclasVal.innerHTML = option;
						break;
					case 9:
						option = "사업주훈련만 참여한 기업군";
						sclasVal.innerHTML = option;
						break;
					case 10:
						option = "기타사항";
						sclasVal.innerHTML = option;
						break;
				}
				
				const sclasOp = document.getElementById('id_sclasCnd');
				document.getElementById('id_sclasCnd').value = data.result.body.SCLAS_CND;
			}
		});
}

// 소분류 수정
const classifyEdit = () => {
	if(document.getElementById('id_sclasVal') != null) {
		var sclasVal = document.getElementById('id_sclasVal').value;
	}
	var formData = {
			"lclasCd" : document.getElementById('id_lclasCd').value,
			"sclasCd" : document.getElementById('id_sclasCd').value,
			"sclasVal" : sclasVal,
			"sclasCnd" : document.getElementById('id_sclasCnd').value
		};
		
		fetch(`${contextPath}`+'/dct/basket/editClassProc.do?mId=43', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("소분류 수정에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("소분류 수정이 완료되었습니다.");
			document.getElementById('closeBtn_05').click();
			window.location.reload();
		}
	});

}

// 소분류 삭제
const classifyDel = () => {
	var formData = {
			"lclasCd" : document.getElementById('id_lclasCd').value,
			"sclasCd" : document.getElementById('id_sclasCd').value
};
	
	fetch(`${contextPath}`+'/dct/basket/delClassProc.do?mId=43', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("소분류 삭제에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("소분류 삭제가 완료되었습니다.");
			document.getElementById('closeBtn_05').click();
			window.location.reload();
		}
	});

}

// 시도 선택시 구군 출력
const handleSelectCtprvn = (id, id2) => {
	const selectedCtprvn = document.getElementById(id).value;
	const sigugnSelect = document.getElementById(id2);
	sigugnSelect.options.length = 0;
	
	const isSigngu = urlParams.get('isSigngu');
	
	fetch(`${contextPath}`+'/dct/basket/getSigngu.do?mId=42', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: selectedCtprvn
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		const signguData = data.result.body;
		localStorage.setItem('signguData', JSON.stringify(signguData));
		signguData.forEach(function(signgu) {
			const sigugnOption = document.createElement('option');
			sigugnOption.value = signgu.signgu;
			sigugnOption.textContent = signgu.signgu;
			if(isSigngu == sigugnOption.value) {
				sigugnOption.selected = true;
			}
			sigugnSelect.appendChild(sigugnOption);


		})
	});
}

// 검색 존에서 대분류 선택시 소분류 출력
const handleClSelectChange = (id, id2) => {
	const lclasSelect = document.getElementById(id);
	var selectedValue = lclasSelect.value;
	const sclasSelect = document.getElementById(id2);
	sclasSelect.options.length = 0;
	
	const isSclas = urlParams.get('isSclas');
	
	fetch(`${contextPath}`+'/dct/basket/getClSclas.do?mId=42', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body : selectedValue
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		var clSclasData = data.result.body;
		
		localStorage.setItem('clSclasData', JSON.stringify(clSclasData));
		const sclasDefaultOption = document.createElement('option');
		sclasDefaultOption.value = '';
		sclasDefaultOption.textContent = '전체';
		
		sclasSelect.appendChild(sclasDefaultOption);
			
		clSclasData.forEach(function(sclas) {
			const sclasOption = document.createElement('option');
			sclasOption.value = sclas.sclasCd;
			sclasOption.textContent = sclas.sclasNm;
			if(isSclas == sclasOption.value) {
				sclasOption.selected = true;
			}
			
			sclasSelect.appendChild(sclasOption);

		})
	});
}


// 분류 정보 페이지에서 해시태그 삭제
const bskHashtagDeleteHandler = (node) => {
	const hashtagNode = node.previousElementSibling;
	const hashtagIdx = hashtagNode.dataset.value;
	
	if(confirm("해당 해시태그를 삭제 하시겠습니까?")) {
		
		const json = {"hashtagIdx" : hashtagIdx};
		fetch(`${contextPath}`+'/dct/basket/deleteHashtag.do?mId=42', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : JSON.stringify(json)
		}).then((response) => {
			if(!response.ok){
				throw new Error("HttpStatus is not ok")
			}
			return response.json();
		})
		.then((data) => {
			if(data.result.code == 1) {
				alert("해시태그 삭제 성공");
				location.reload();
			}
			})
		}else{
		alert("취소");
	}
	}

// 해시태그 등록
const hashTagModalOpen = (id) => {
	modal = "";
	if(id != 'open-modal07') {
		$('.mask').fadeIn(150);
		modal = document.getElementById('modal-action06');
		modal.style.display = 'block';
		
		fetch(`${contextPath}`+'/dct/basket/hashGet.do?mId=86&hashtagCd='+id, {
			method: 'GET',
		}).then((response) => {
			if(!response.ok){
				throw new Error("HttpStatus is not ok")
				alert("분류예약 조회에 실패하였습니다.");
			}
			return response.json();
		})
		.then((data) => {
			if(data.result.code == 1) {
				const date = document.getElementById('id_editDate');
				date.innerHTML = "";
				let dateformat = new Date(data.result.body.REGI_DATE);
				date.innerHTML += dateformat.getFullYear()+`-`+( (dateformat.getMonth()+1) < 10 ? `0` + (dateformat.getMonth()+1) : (dateformat.getMonth()+1) )+`-`;
				date.innerHTML += ( (dateformat.getDate()) < 10 ? `0` + (dateformat.getDate()) : (dateformat.getDate()) );
				
				const hashNm = document.getElementById('id_editHashNm');
				hashNm.value = data.result.body.HASHTAG_NM;
				
				const comment = document.getElementById('id_editRemarks');
				comment.value = data.result.body.HASHTAG_REMARKS;
				
				const hashCd = document.getElementById('id_editHash');
				hashCd.value = id;
			}
		});
		
	} else {
		$('.mask').fadeIn(150);
		modal = document.getElementById('modal-action05');
		modal.style.display = 'block';
		var regiDate = document.getElementById('id_regiDate');
		regiDate.innerHTML = "";
		let dateformat = new Date();
		regiDate.innerHTML += dateformat.getFullYear()+`-`+( (dateformat.getMonth()+1) < 10 ? `0` + (dateformat.getMonth()+1) : (dateformat.getMonth()+1) )+`-`;
		regiDate.innerHTML += ( (dateformat.getDate()) < 10 ? `0` + (dateformat.getDate()) : (dateformat.getDate()) );
	}
}

// 해시태그등록
const regiHash = () => {
	$('.mask').fadeIn(150);
	var modal = document.getElementById('modal-action05').style.display;
	var hashtagNm = document.getElementById('id_hashtagNm').value;
	var hashtagRemarks = document.getElementById('id_remarks').value;
	
	if(!hashtagNm) {
		alert("해시태그를 입력해 주세요")
		return;
	}
	
	var formData = {
		"hashtagNm" : hashtagNm,
		"hashtagRemarks" : hashtagRemarks,
	}
	
	fetch(`${contextPath}`+'/dct/basket/hashAddProc.do?mId=86', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("해시태그 등록에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("해시태그 등록이 완료되었습니다.");
			document.getElementById('closeBtn_05').click();
			window.location.reload();
		}
	});

}


const delHash = () => {
	if($("input:checkbox[name='hashtagCd']:checked").length<1){
		alert("하나 이상의 체크박스를 선택 하시기 바랍니다."); 
		return false;
	}
	
	$("input[name=hashtagCd]").each(function(index){
		if($(this).is(":checked")){
			hashtagCd = $(this).val();
			var formData = {
				"hashtagCd" : hashtagCd
			}
			
			fetch(`${contextPath}`+'/dct/basket/hashDelProc.do?mId=86', {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(formData)
			}).then((response) => {
				if(!response.ok){
					throw new Error("HttpStatus is not ok");
				}
				return response.json();
			})
			.then((data) => {
				if(data.result.code == 1) {
					window.location.reload();
				}
			});
			
		}
	});
	
}


// 해시태그 수정
const editHash = (id) => {
	var modal = document.getElementById('modal-action06').style.display;
	var hashtagNm = document.getElementById('id_editHashNm').value;
	var hashtagRemarks = document.getElementById('id_editRemarks').value;
	var hashtagCd = document.getElementById('id_editHash').value;
	
	if(!hashtagNm) {
		alert("해시태그를 입력해 주세요")
		return;
	}
	
	var formData = {
		"hashtagNm" : hashtagNm,
		"hashtagRemarks" : hashtagRemarks,
		"hashtagCd" : hashtagCd
	}
	
	fetch(`${contextPath}`+'/dct/basket/hashEditProc.do?mId=86', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
			alert("해시태그 수정에 실패하였습니다.");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			alert("해시태그 수정이 완료되었습니다.");
			document.getElementById('closeBtn_06').click();
			window.location.reload();
		}
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

function isNumber() {
	if(event.keyCode < 48 || event.keyCode>57) {
		event.returnValue=false;
	}
}

function loadingBarTimeOut() {
	showLoading();
	
	setTimeout(() => {
		hideLoading()
	}, 8000);
}

function loadingBar() {
	showLoading();
}

function getCurrentDate() {
	var now = new Date();
	var year = now.getFullYear();
	var month = (now.getMonth() + 1).toString().padStart(2, '0');
	var day = now.getDate().toString().padStart(2, '0');
	return year + '-' + month + '-' + day;
}

function upcomingColumn() {
	event.preventDefault();
	alert('향후 업데이트 예정입니다.');
	return ;
}



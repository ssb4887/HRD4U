const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);	
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;



//지부 지사 선택시 해시태그 출력
const handleSelectInstt = (id,id2) => {
	const selectedBranch = document.getElementById(id).value;
	console.log('selectedBranch', selectedBranch);
	const hashTagSelect = document.getElementById(id2);
	hashTagSelect.options.length = 0;
	
	
	const isHashTag = urlParams.get("isHashTag");
	
	fetch(`${contextPath}`+'/web/basket/getHashTag.do?mId=42', {
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

function loadingBar() {
	showLoading();
}

const paginationHandler = (formId) => {
	event.preventDefault();

	console.log(formId);
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
			document.pageForm.submit();
			break;
	}
	
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


// 시도 선택시 구군 출력
const handleSelectCtprvn = (id, id2) => {
	const selectedCtprvn = document.getElementById(id).value;
	console.log('selectedCtprvn', selectedCtprvn);
	const sigugnSelect = document.getElementById(id2);
	sigugnSelect.options.length = 0;
	
	const isSigngu = urlParams.get('isSigngu');
	
	fetch(`${contextPath}`+'/web/basket/getSigngu.do?mId=42', {
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
		console.log("signguData", signguData);
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
	console.log('실행');
	const lclasSelect = document.getElementById(id);
	var selectedValue = lclasSelect.value;
	const sclasSelect = document.getElementById(id2);
	sclasSelect.options.length = 0;
	
	const isSclas = urlParams.get('isSclas');
	
	fetch(`${contextPath}`+'/web/basket/getClSclas.do?mId=42', {
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
		
		console.log("clSclasData",clSclasData)
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


//탭 유지를 위한 코드
document.addEventListener("DOMContentLoaded", function() {
	var mId = document.getElementById("id_mId").value;
	if(mId == 71) {
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
	}
	
	
})

//탭 변경
function openTab(evt, tabName) {
	console.log(evt);
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
	
	
	var pathName =window.location.pathname;
	console.log(pathName);
	var tabArray = ['tab1','tab2','tab3','tab4'];

	// 탭 이동시 데이터 초기화(새로고침)
	
	if(pathName == `/web/basket/list.do` && tabArray.includes(tabName)) {
		console.log('실행');
		const newUrl = window.location.origin + window.location.pathname +'?mId=71';
		window.location.href = newUrl;
		
	}
	
	
}

function upcomingColumn() {
	event.preventDefault();
	alert('향후 업데이트 예정입니다.');
	return ;
}



const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);	
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;



// 인력풀 조회
function hrpUserSearchHandler(obj) {
	const hrd4uId = obj.value;
	const thisElementId = obj.id;
	
	const name = document.getElementById(thisElementId+"_name");
	const isHrpY = document.getElementById(thisElementId+"_isHrpY");
	const isHrpN = document.getElementById(thisElementId+"_isHrpN");
	const attached = document.getElementById(thisElementId+"_file")
	
	//4UID 존재여부 를 조회
	fetch(`${contextPath}`+'/web/consulting/searchHRP.do?mId=85', {
		method: 'POST',
		body : hrd4uId
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		console.log(data);
		if(data.result.code == '-1') {
			document.getElementById(obj.id).style.borderColor = 'red';
			isHrpN.checked = true;
			name.value = '';
		}else if(data.result.code == '1') {
			document.getElementById(obj.id).style.borderColor = '';
			isHrpY.checked = true;
			name.value = data.result.body.hrpNm;
			attached.disabled = true;
			
			
		}else if(data.result.code == '2') {
			document.getElementById(obj.id).style.borderColor = '';
			isHrpN.checked = true;
			name.value = data.result.body.MBERNAME;
		}

	});
	
}

//회원 조회	
function userSearchHandler(obj) {
	const hrd4uId = obj.value;
	const thisElementId = obj.id;
	
	const name = document.getElementById(thisElementId+"_name");
	
	//4UID 존재여부 를 조회
	fetch(`${contextPath}`+'/web/consulting/searchHRP.do?mId=85', {
		method: 'POST',
		body : hrd4uId
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		console.log(data);
		if(data.result.code == '-1') {
			document.getElementById(obj.id).style.borderColor = 'red';
			name.value = '';
		}else if(data.result.code == '2') {
			document.getElementById(obj.id).style.borderColor = '';
			name.value = data.result.body.MBERNAME;
		}else if(data.result.code == '1') {
			document.getElementById(obj.id).style.borderColor = '';
			name.value = data.result.body.hrpNm;
		}

	});
	
}



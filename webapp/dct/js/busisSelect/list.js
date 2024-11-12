var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
var siteId = document.querySelector(".siteId").value;


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

// 상세조회 페이지 이동
const viewButtons = document.querySelectorAll('.btn-idx');
viewButtons.forEach(function(viewBtn) {
	viewBtn.addEventListener('click', function(event) {
		const idx = viewBtn.getAttribute('data-idx');
		const bsk = viewBtn.getAttribute('data-bsk');
		const sptjIdx = viewBtn.getAttribute('data-sptj');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/busisSelect/view.do?mId=118`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		document.getElementById('bsk').value = bsk;
		document.getElementById('sptjIdx').value = sptjIdx;
		form.submit();
	});
});

//수정페이지로 이동
const inputButtons = document.querySelectorAll('.btn-input');
inputButtons.forEach(function(inputBtn) {
	inputBtn.addEventListener('click', function(event) {
		const idx = inputBtn.getAttribute('data-tmp');
		const bsk = inputBtn.getAttribute('data-bsk');
		const sptjIdx = inputBtn.getAttribute('data-sptj');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/busisSelect/input.do?mId=117`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		document.getElementById('bsk').value = bsk;
		document.getElementById('sptjIdx').value = sptjIdx;
		console.log(idx);
		form.submit();
	});
});

// PDF 출력가능 여부
function pdf(sptdgnsIdx) {
	
	fetch(`${contextPath}/dct/simpleSign/signView.do?mId=142`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8',
		},
		body: sptdgnsIdx,
	})
	.then(response => response.json())
	.then(data => {
		const urlList = data.viewUrlList;
		
		if(urlList == undefined) {
			alert("완료된 방문기업관리 문서가 없습니다.");
		}else {
			pdfFile(sptdgnsIdx);
		}
		
	})
	.catch(error => console.log("Error : "+error));
	
}

// 방문기업문서 PDF 출력
function pdfFile(sptdgnsIdx) {
	
	fetch(`${contextPath}/dct/simpleSign/pdfDownload.do?mId=142`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8',
		},
		body: sptdgnsIdx,
	})
	.then(response => response.blob())
	.then(blob => {
		const reader = new FileReader();
		reader.onload = () => {
			const dataUrl = reader.result;
			const link = document.createElement('a');
			link.href = dataUrl;
			link.click();
			
		};
		
		reader.readAsDataURL(blob);
		
	})
	.catch(error => console.log("Error : "+error));
	
}

//엑셀 다운로드
const excelBtn = document.getElementById('btn-excel');
if(excelBtn) {
	excelBtn.addEventListener('click', function() {
		const form = document.getElementById('form-box');
		const queryString = window.location.search;
		const actionUrl = `${contextPath}/dct/busisSelect/excel.do` + queryString;
		form.action = actionUrl;
		form.submit();
	});
}

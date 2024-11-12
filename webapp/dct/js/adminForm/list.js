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
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/adminForm/view.do?mId=114`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});


// 수정페이지로 이동
const inputButtons = document.querySelectorAll('.btn-input');
inputButtons.forEach(function(inputBtn) {
	inputBtn.addEventListener('click', function(event) {
		const idx = inputBtn.getAttribute('data-tmp');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/`+siteId+`/adminForm/input.do?mId=114`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		console.log(idx);
		form.submit();
	});
});

//엑셀 다운로드
const excelBtn = document.getElementById('btn-excel');
if(excelBtn) {
	excelBtn.addEventListener('click', function() {
		const form = document.getElementById('form-box');
		const queryString = window.location.search;
		const actionUrl = `${contextPath}/dct/adminForm/excel.do` + queryString;
		form.action = actionUrl;
		form.submit();
	});
}
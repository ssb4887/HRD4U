var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

document.addEventListener('DOMContentLoaded', function() {
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
	}
	
	// 기초컨설팅
	const bscButtons = document.querySelectorAll('.btn-bsc');
	bscButtons.forEach(function(bscBtn) {
		bscBtn.addEventListener('click', function(event) {
			const idx = bscBtn.getAttribute('data-idx');
			const form = document.getElementById('form-box');
			const actionUrl = `${contextPath}/dct/diagnosis/view.do?mId=36`;
			form.action = actionUrl;
			document.getElementById('idx').value = idx;
			form.submit(); 
		});
	});
	
	// 설문조사 
	const qustnrButtons = document.querySelectorAll('.btn-qustnr');
	qustnrButtons.forEach(function(qustnrBtn) {
		qustnrBtn.addEventListener('click', function(event) {
			const rslt = qustnrBtn.getAttribute('data-rslt');
			const bsc = qustnrBtn.getAttribute('data-bsc');
			const form = document.getElementById('form-box');
			const actionUrl = `${contextPath}/dct/bsisCnsl/qustnr.do?mId=37`;
			form.action = actionUrl;
			document.getElementById('rslt').value = rslt;
			document.getElementById('bsc').value = bsc;
			form.submit();
		});
	});
	
	// 기초컨설팅
	const cnsltButtons = document.querySelectorAll('.btn-cnslt');
	cnsltButtons.forEach(function(cnsltBtn) {
		cnsltBtn.addEventListener('click', function(event) {
			const rslt = cnsltBtn.getAttribute('data-rslt');
			const bsc = cnsltBtn.getAttribute('data-bsc');
			const form = document.getElementById('form-box');
			const actionUrl = `${contextPath}/dct/bsisCnsl/cnslt.do?mId=37`;
			form.action = actionUrl;
			document.getElementById('rslt').value = rslt;
			document.getElementById('bsc').value = bsc;
			form.submit();
		});
	});
});

const excelBtn = document.getElementById('btn-excel');
if(excelBtn) {
	excelBtn.addEventListener('click', function() {
		const form = document.getElementById('form-box');
		const queryString = window.location.search;
		const actionUrl = `${contextPath}/dct/bsisCnsl/excel.do` + queryString;
		form.action = actionUrl;
		form.submit();
	});
}

const initBtn = document.getElementById('btn-init');
initBtn.addEventListener('click', function() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"], select');
	textInputs.forEach(elem => {
		elem.value = '';
	});
});

function downloadExcelForAdmin() {
	const inputDate = prompt(`발급 기준일을 입력하세요.(YYYY-MM-DD)\n(예시:2023-01-01 입력 시, '2023-01-01'이후에 발급된 데이터를 다운로드합니다.)`);
	if(inputDate !== null) {
		const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
		if(dateRegex.test(inputDate)) {
			window.location.href = `${contextPath}/dct/bsisCnsl/excelForAdmin.do?mId=37&date=${inputDate}`;
		} else {
			alert("올바른 날짜 형식을 입력하세요.(예:2023-01-01)");
		}
	} else {
		alert('발급기준일을 입력해주세요.');
	}
}
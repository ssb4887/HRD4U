var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;
let applyQustnr = document.querySelector("#btn-qustnr");
applyQustnr.addEventListener("click", function() {
	let selectedIdx;
	let bsc = document.getElementsByName('radio-bsc');
	bsc.forEach(elem => {
		if(elem.checked) {
			selectedIdx = elem.value;
		}
	});
	
	if(!selectedIdx) {
		alert('기초진단서를 선택해주세요.');
	} else {
		// selected. 설문조사 페이지로 이동
//		window.location.href= contextPath + "/web/bsisCnsl/qustnr.do?mId=55&bsc=" + selectedIdx;

		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/web/bsisCnsl/qustnr.do?mId=55`;
		form.action = actionUrl;
		document.getElementById('bsc').value = selectedIdx;
		form.submit();
	}
	
});

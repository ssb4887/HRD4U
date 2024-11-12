var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

var bplNoSession = sessionStorage.getItem("checkboxValue");
var bplNo = document.getElementById('bplNo');
bplNo.value = bplNoSession;


// 방문기업관리 페이지로 이동
const selectButtons = document.querySelectorAll('.btn-select');
selectButtons.forEach(function(selectBtn) {
	selectBtn.addEventListener('click', function(event) {
		const idx = selectBtn.getAttribute('data-idx');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/dct/busisSelect/input.do?mId=117`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});
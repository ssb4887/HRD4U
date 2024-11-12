var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;

document.addEventListener('DOMContentLoaded', function() {
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
	}
	
	// 기초진단
	const bscButtons = document.querySelectorAll('.btn-bsc');
	bscButtons.forEach(function(bscBtn) {
		bscBtn.addEventListener('click', function(event) {
			const idx = bscBtn.getAttribute('data-idx');
			const form = document.getElementById('form-box');
			const actionUrl = `${contextPath}/web/bsisCnsl/view.do?mId=56`;
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
			const actionUrl = `${contextPath}/web/bsisCnsl/qustnr.do?mId=56`;
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
			const actionUrl = `${contextPath}/web/bsisCnsl/cnslt.do?mId=56`;
			form.action = actionUrl;
			document.getElementById('rslt').value = rslt;
			document.getElementById('bsc').value = bsc;
			form.submit();
		});
	});
	
	
});

function initSearhParams() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
	textInputs.forEach(elem => {
		elem.value = '';
	});
}

function openModalForDesc() {
	$(".mask").fadeIn(150, function() {
        $("#modal-action01").show();
    });
	
	$("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
}
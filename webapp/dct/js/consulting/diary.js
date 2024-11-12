const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);	
var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

$('.btn-back').on('click', function() {	
	history.back();
});

function isNumber() {
	if(event.keyCode < 48 || event.keyCode>57) {
		event.returnValue=false;
	}
}

//수행일지 삭제
const deleteDiary = () => {
	var f = document.forms.fn_sampleInputForm;
	let url = `${contextPath}`+"/dct/consulting/deleteProc.do?mId=133";
	
	f.method="post";
	var result = confirm("삭제하시겠습니까?");
	if (result) {
		f.action = url;
		f.submit();
	} else {
		return;
	}
	
	f.action = url;
	f.submit();
}






















var context = location.pathname.split('/')[1]
var contextPath = '';
if(context == 'web' || context == 'dct') {
	contextPath = '';
}else {
	contextPath = `/${context}`;
}

document.addEventListener('DOMContentLoaded', function() {
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
	}
});

function initSearhParams() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
	textInputs.forEach(elem => {
		elem.value = '';
	});
}
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;
const parameters = new URLSearchParams(window.location.search);

let currentPage = 1;

const allElements = document.querySelectorAll('[id^="page"]');
let maxPage = allElements.length;
function showNextPage() {
	if(currentPage < maxPage) {
		document.getElementById('page' + currentPage).style.display = 'none';
		currentPage ++;
		document.getElementById('page' + currentPage).style.display = 'block';
	}
}

function showPreviousPage() {
	if(currentPage > 1) {
		document.getElementById('page' + currentPage).style.display = 'none';
		currentPage --;
		document.getElementById('page' + currentPage).style.display = 'block';
	}
}



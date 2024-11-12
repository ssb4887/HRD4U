var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

hideLoading();


/**
  * 삭제 모달창
  * 
  */
// 삭제 모달창 열기
$(".open-modal01").on("click", function() {
    $(".modal-wrapper").hide();
    $(".mask").fadeIn(150, function() {
        $("#modal-alert01").show();
    });
});

// 삭제 모달창 닫기
let closeBtn = document.querySelectorAll('.btn-modal-close, .modal-close ');
closeBtn.forEach(function(button) {
	button.addEventListener('click', function() {
		$(".modal-wrapper").hide();
		$(".mask").hide();
	});
});

// 삭제 처리
function deleteForm() {
	
	let idx = document.querySelector('.idx').value;
	
	fetch(`${contextPath}/dct/adminForm/delete.do?mId=114`, {
		method: 'POST',
		headers: {
			'Content-Type' : 'application/x-www-form-urlencoded',
		},
		body: 'idx='+idx

	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.text();
	})
	.then((data) => {
		msg ='정상적으로 삭제되었습니다.';
		alert(msg)
		window.location.href=`${contextPath}/dct/adminForm/list.do?mId=114`
	})
	.catch(error => {
		msg="정상적으로 삭제되지 못했습니다.";
		alert(msg)
	})

}

//수정페이지로 이동
const inputButtons = document.querySelectorAll('.btn-input');
inputButtons.forEach(function(inputBtn) {
	inputBtn.addEventListener('click', function(event) {
		const idx = inputBtn.getAttribute('data-tmp');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/dct/adminForm/input.do?mId=114`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});

// 토큰 및 주소정보 조회
function saveCookies() {
	
	let formatId = document.getElementById('formatId').value;
	
	const url = `${contextPath}/dct/simpleSign/view.do?mId=142`;
	
	showLoading();

	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type' : 'application/json; charset=utf-8',
		},
		body: formatId
	})
	.then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		
		return response.json();
	})
	.then((data) => {
		const url = data.viewUrl;
		const token = data.token;
		
		hideLoading();
		openUrl(token, url);
	})
	.catch(error => {
		console.log("saveCookie error : ", error)
	})
	
}

function openUrl(token, url) {

	// 팝업창 크기 및 위치 설정
	let popupWidth = 1450;
	let popupHeight = 780;
	let leftPosition = (window.innerWidth - popupWidth) / 2;
	let topPosition = (window.innerHeight - popupHeight) / 2;

	if(!token) {
		console.log("토큰이 없습니다.");
		return;
	}
	
    const newWindow = window.open(url+'?t='+token, '_blank', 'width='+popupWidth+', height='+popupHeight+', left='+leftPosition+', top='+topPosition);
    if(newWindow) {
        newWindow.focus();
	}else {
		console.log("팝업열기 오류");
	}
}

function showLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	loader.style.display = 'block';
	overlay.style.display = 'block';
	
}

function hideLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	loader ? loader.style.display = 'none' : null;
	overlay ? overlay.style.display = 'none' : null;
	
}
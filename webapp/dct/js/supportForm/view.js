var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

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
	
	fetch(`${contextPath}/dct/supportForm/delete.do?mId=115`, {
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
		window.location.href=`${contextPath}/dct/supportForm/list.do?mId=115`
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
		const actionUrl = `${contextPath}/dct/supportForm/input.do?mId=115`;
		form.action = actionUrl;
		document.getElementById('idx').value = idx;
		form.submit();
	});
});

//해당 서식 상세조회(심플사인 페이지 접속)
function openPopup() {
//	let popupUrl = document.getElementById('formUrl').value;
	let popupUrl = "http://165.213.109.69:18080/ssign/viewer/V/28809e24-4107-4edc-9c63-f669cb6576ca";
	
	// 팝업창 크기 및 위치 설정
	let popupWidth = 1450;
	let popupHeight = 780;
	let leftPosition = (window.innerWidth - popupWidth) / 2;
	let topPosition = (window.innerHeight - popupHeight) / 2;
	console.log("check : "+window.innerWidth)
	
	window.open(popupUrl, '_blank', 'width='+popupWidth+', height='+popupHeight+', left='+leftPosition+', top='+topPosition);
	
}
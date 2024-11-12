var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;

let action = null;
let html ="";

//저장 모달창 열기
$(".open-modal01").on("click", function() {
	action = "temp";
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
	    $("#modal-alert01").show();
	    
	    html ="방문기업관리 문서를 생성하시겠습니까? <br />" +
	    		"생성된 문서는 자동 임시 저장됩니다.";
	    
	    $("#comment").html(html);
	});
	
});

//완료 모달창 열기
$(".open-modal02").on("click", function() {
	action = "done";
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-alert01").show();
		
		html = "방문기업관리 문서 작성을 완료하셨습니까? <br />" +
				"미작성시 생성된 문서 내용은 <br />" +
				"자동 저장 되지않습니다.";
		
		$("#comment").html(html);
	});
	
});

//모달창 닫기
let closeBtn = document.querySelectorAll('.btn-modal-close, .modal-close ');
closeBtn.forEach(function(button) {
	button.addEventListener('click', function() {
		$(".modal-wrapper").hide();
		$(".mask").hide();
	});
});

function closeEvent() {
	$(".modal-wrapper").hide();
	$(".mask").hide();
}

let publishBtn = document.querySelector('.publish');
let publishedYn= document.getElementById('publishedYn');
let reqSetKeyList;
let isProgress = false;
//저장||완료 처리
function save() {
	
	if(isProgress) {
		console.log('중복클릭');
		return;
	}
	
	isProgress = true;
	let msg = '';
	if(action === 'temp' || action === 'done') {
		const formData = new FormData(document.getElementById('inputForm'));
		formData.append('action', action);
		
		fetch(`${contextPath}/dct/busisSelect/inputProc.do?mId=117`, {
			method: 'POST',
			body: formData,
		})
		.then(response => {
			if(!response.ok) {
				throw new Error ('Not ok');
			}
			isProgress = false;
			return response.json();
		})
		.then(data => {
			let dataMap = data.linkedHashMap;
			
			if(action == 'temp') {
				alert('정상적으로 생성되었습니다.');
				
				reqSetKeyList = dataMap.reqSetKeyList;
				let publishedYnValue = dataMap.publishedYn;
				let sptdgnsIdx = data.sptdgnsIdx;
				
				document.getElementById('sptdgnsIdx').value = sptdgnsIdx;
				
				
				// 새로고침시 추가한 값 소멸 방지 수정해야함
//				sessionStorage.setItem("publishedYnValue", publishedYnValue);
//				publishedYn.value = publishedYnValue;
				closeEvent();
				createBtn();
			}else {
				alert('정상적으로 등록되었습니다.');
				window.location.href=`${contextPath}/dct/busisSelect/list.do?mId=118`
			}
		})
		.catch(error => {
			if(action == 'temp') {
				msg ='저장에 실패했습니다.';
			}else {
				msg ='등록에 실패했습니다.'
			}
			alert(msg);
			console.log("Error : "+error);
			
		})
	}
}

// 방문기업 문서 작성
function createBtn() {
	
	publishBtn.style.display = "none";
	
	// 방문기업 문서 작성 버튼 생성
	let btnDiv = document.querySelector('.btns-right');
	let button = document.createElement("button");
	let doneBtn = document.getElementById('doneBtn');
	
	button.type= "button";
	button.className= "apply btn-m01 btn-color03 depth6";
	button.style="margin-right: 5px";
	button.innerHTML= "문서 작성";
	button.onclick= () => busisSelect();
	
	btnDiv.insertBefore(button, doneBtn);
}

// 방문기업 문서 작성
function busisSelect() {
	
	if(reqSetKeyList != null) {
		console.log("ok")
	}else {
		reqSetKeyList = document.getElementById("reqSetKeyList").value;
		
	}
	
	fetch(`${contextPath}/dct/simpleSign/apply.do?mId=142`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8',
		},
		body: JSON.stringify(reqSetKeyList),
	})
	.then(response => response.json())
	.then(data => {
		const urlList = data.applyUrlList;
		const token = data.token;
		const progressStatus = data.progressStatus;
		
		if(urlList != null || urlList != undefined) {
			openNewWindow(token, urlList);
			
		}else if(progressStatus == "done") {
			alert("해당 문서 작성이 완료되었습니다.");
			
		}else {
			console.log("data check")
		}
	})
	.catch(error => console.log("Error : "+error));
	
}

// 전자서식 편집창 출력
function openNewWindow(token, urlList) {

	let newWindow;
	
	// 팝업창 크기 및 위치 설정
	let popupWidth = 1450;
	let popupHeight = 780;
	let leftPosition = (window.innerWidth - popupWidth) / 2;
	let topPosition = (window.innerHeight - popupHeight) / 2;
	
	if(urlList != '' && urlList != null) {
		for(let url of urlList) {
			newWindow = window.open(url+'?t='+token, '_blank', 'width='+popupWidth+', height='+popupHeight+', left='+leftPosition+', top='+topPosition);
		}
		
		if(newWindow) {
			newWindow.focus();
		}else {
			console.log("팝업열기 오류");
		}
	}else {
		alert("해당 전자서식이 존재하지않습니다.");
	}
}


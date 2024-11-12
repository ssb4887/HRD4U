var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;


// 방문기업관리 문서 상세조회
function busisSelectView(sptdgnsIdx) {
	
	fetch(`${contextPath}/dct/simpleSign/signView.do?mId=142`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8',
		},
		body: sptdgnsIdx,
	})
	.then(response => response.json())
	.then(data => {
		const urlList = data.viewUrlList;
		
		if(urlList == undefined) {
			alert("완료된 방문기업관리 문서가 없습니다.");
		}
		
		openNewWindow(urlList);
	})
	.catch(error => console.log("Error : "+error));
	
}

//전자서식 상세조회창 출력
let newWindow;
function openNewWindow(urlList) {

	// 팝업창 크기 및 위치 설정
	let popupWidth = 1450;
	let popupHeight = 780;
	let leftPosition = (window.innerWidth - popupWidth) / 2;
	let topPosition = (window.innerHeight - popupHeight) / 2;
	
	for(let url of urlList) {
		newWindow = window.open(url, '_blank', 'width='+popupWidth+', height='+popupHeight+', left='+leftPosition+', top='+topPosition);
	}
	
	if(newWindow) {
		newWindow.focus();
	}else {
		console.log("팝업열기 오류");
	}
}

// PDF 출력가능 여부
function pdf(sptdgnsIdx) {
	
	fetch(`${contextPath}/dct/simpleSign/signView.do?mId=142`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8',
		},
		body: sptdgnsIdx,
	})
	.then(response => response.json())
	.then(data => {
		const urlList = data.viewUrlList;
		
		if(urlList == undefined) {
			alert("완료된 방문기업관리 문서가 없습니다.");
		}else {
			pdfFile(sptdgnsIdx);
		}
		
	})
	.catch(error => console.log("Error : "+error));
	
}

//방문기업관리 PDF 출력
function pdfFile(sptdgnsIdx) {
	
	fetch(`${contextPath}/dct/simpleSign/pdfDownload.do?mId=142`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8',
		},
		body: sptdgnsIdx,
	})
	.then(response => response.blob())
	.then(blob => {
		const reader = new FileReader();
		reader.onload = () => {
			const dataUrl = reader.result;
			const link = document.createElement('a');
			link.href = dataUrl;
			link.click();
			
		};
		
		reader.readAsDataURL(blob);
		
	})
	.catch(error => console.log("Error : "+error));
	
}
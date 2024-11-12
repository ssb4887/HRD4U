
function checkPBL() {
	const confmStatus = 5;
	const reportForm = document.getElementById('report-form');
	const formData = new FormData();
	
	// 빈값 제거
	let inputs = Array.from(reportForm.querySelectorAll('input[type="text"], input[type="number"], input[type="hidden"], input[type="checkbox"], input[type="radio"], textarea, select'));
	let inputFiles = Array.from(reportForm.querySelectorAll('input[type="file"]'));
	inputs.filter(input => input.value.trim() !== '').forEach(input => {
		if(input.type === 'checkbox' || input.type === 'radio') {
			if(input.checked) {
				formData.append(input.name, input.value);
			} else {
				formData.append(input.name, '-');
			}
		} else {
			formData.append(input.name, input.value);
		}
	});
	inputFiles.forEach(input => {
		console.log(input)
		console.log(input.files[0])
		if(input.files.length > 0) {
			formData.append(input.name, input.files[0]);
		} else {
			formData.append(input.name, '');
		}
	});

	formData.append("confmStatus", confmStatus);
	
	let index = 0;
	for(const [key, value] of formData) {
		if(key === 'attac_file') {
			console.log(value)
			formData.append(`attac_file${index}`, value);
			index++;
		}
	}
	formData.delete('attac_file');
	
	if(confmStatus == '5') {
		const result = confirm('표절 검사를 진행하시겠습니까?');
		if(result) {
			if(reportForm.checkValidity()) {
				showLoading();
				// contextPath는 indepthDgns.js에서 정의됨.
				fetch(`${contextPath}/web/report/checkPBL.do?mId=100`, {
					method: 'POST',
					body : formData
				}).then((response) => {
					if(!response.ok){
						alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
						hideLoading();
						throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
					}
					return response.json();
				})
				.then((data) => {
					if(data.result.body === 'fail') {
						alert('보고서 저장에 실패하였습니다.\n관리자에게 문의해주세요.');
						hideLoading();
					} else {
						alert('표절 검사 완료');
						hideLoading();
						const obj_ = JSON.parse(data.result.msg);
						json_obj = obj_;
						const { plagiarismTotal, plagiarismRate, plagiarismSimilar, plagiarismMatched, plagiarismSource } = json_obj;
						const plagiarism = {'total': plagiarismTotal[0], 'rate': plagiarismRate[0], 'similar': plagiarismSimilar[0], 'source': plagiarismSource[0], 'matched': plagiarismMatched}
						profileProxy.plagiarism = plagiarism;
						localStorage.setItem('plagiarism', JSON.stringify(plagiarism))
						showPBLPopup(plagiarism);
					}
				});
			} else {
				alert('빈 입력란을 확인 해주세요.');
				const requiredElements = reportForm.querySelectorAll('[required]');
				requiredElements.forEach(element => {
					if(!element.checkValidity()) {
						const isVisible = !!element.offsetParent;
						if(isVisible) {
							element.focus();
						} else {
							let parent = element.parentElement;
							let page;
							while(parent) {
								if(parent.classList.contains('page')) {
									page = parent.id;
								}
								parent = parent.parentElement;
							}
							
							document.getElementById(`js-${page}-btn`).click();
							element.focus();
						}
					}
				});
			}
		}
		
	}
}

function showPBLPopup(json) {
	const popup = window.open(`${contextPath}/web/report/viewPBL.do?mId=100`, "popup", "menubar=1, resizable=1, width=600, height=800")
}
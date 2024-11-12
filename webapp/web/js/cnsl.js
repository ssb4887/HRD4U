const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);	
var context = location.pathname.split('/')[1]
var contextPath = '';
if(context == 'web' || context == 'dct') {
	contextPath = '';
}else {
	contextPath = `/${context}`;
}

let maxExIdx = 3;
let nowExIdx = 1;
let maxInIdx = 3;
let nowInIdx = 1;

	
document.addEventListener('DOMContentLoaded', function() {
	checkTeamMemberIdsFromHrp(checkCnslTypeWithInputDisabled);
	
})

function checkCnslTypeWithInputDisabled() {
	const elements = document.querySelectorAll('input[type="radio"][name="cnslType"]');
	if(elements.length > 0) {
		const teamInfoInputboxs = document.querySelectorAll('.inExpert, .pm, .inExpertFile, .pmFile');
		if(teamInfoInputboxs.length > 0) {
		elements.forEach(element => {
			if(element.checked) {
				if(element.value == 1 || element.value == 2) {
					teamInfoInputboxs.forEach(input => {
						disabledAllChildren(input)
						});
				}
			}
		})
		}
		
	}
}
const requiredElements = document.querySelectorAll('[required]');
requiredElements.forEach(element => {
	element.addEventListener('focus', () => {
		element.style.borderColor = '';
	})
})


function checkTeamCreationStatus() {
	const noTeamCreationCheckbox = document.getElementById('teamCheck');
	const teamInfoInputboxs = document.querySelectorAll('.exExpert, .exExpertFile, .pm, .pmFile');
	const teamInfoInputboxsCsnlType1 = document.querySelectorAll('.exExpert, .exExpertFile');
	const cnslType1 = document.getElementById('cnslType1');
	
	if(cnslType1 && cnslType1.checked){
		if(noTeamCreationCheckbox && teamInfoInputboxs.length >0) {
			if(noTeamCreationCheckbox.checked) {	
				teamInfoInputboxsCsnlType1.forEach(element => {
					disabledAllChildren(element);

				});
			}else if(!noTeamCreationCheckbox.checked) {
				teamInfoInputboxsCsnlType1.forEach(element => {
				inabledAllChildren(element);
				});
			}
		}
	}else {
		if(noTeamCreationCheckbox && teamInfoInputboxs.length >0) {
			if(noTeamCreationCheckbox.checked) {	
				teamInfoInputboxs.forEach(element => {
					disabledAllChildren(element);

				});
			}else if(!noTeamCreationCheckbox.checked) {
				teamInfoInputboxs.forEach(element => {
				inabledAllChildren(element);
				});
			}
		}
		
	}

}

function disabledAllChildren(element) {
	element.disabled = true
	const children = element.children;
	for(let i= 0; i < children.length; i++) {
		disabledAllChildren(children[i]);
		children[i].removeAttribute('placeholder')
		children[i].value ='';
	}
}

function inabledAllChildren(element) {
	element.disabled = false;
	element.value = element.getAttribute("data-value");
	const children = element.children;
	for(let i= 0; i < children.length; i++) {
		inabledAllChildren(children[i]);
	}
}

// 페이지 로딩시 인력풀 체크
function checkTeamMemberIdsFromHrp(callback) {
	const elements = document.querySelectorAll('.mberIdTd');

	if(elements.length > 0) {
		elements.forEach(element => {
			let mberId = '';
			if(element.children.length > 0) {
				mberId = element.children[0].value;
			}else{
				mberId = element.textContent.trim();
			}
			const siblingWithSuitableClass = element.parentElement.querySelector('.checkSuitableRadioTd ');
			const siblingWithHrpClass = element.parentElement.querySelector('.checkHrpRadioTd');
			const siblingWithLimitClass = element.parentElement.querySelector('.checkLimitTd');
			
			if(siblingWithSuitableClass) {
				const radios = siblingWithSuitableClass.querySelectorAll('input[type="radio"]');
				fetch(`${contextPath}`+'/web/consulting/searchSuitable.do?mId=85', {
					method: 'POST',
					body : mberId
				}).then((response) => {
					if(!response.ok){
						alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
						throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
					}
					return response.json();
				})
				.then((data) => {
					if(data.result.body) {
						if(siblingWithLimitClass) {
						const limitInput = siblingWithLimitClass.querySelector('input');
						if(limitInput) {
							limitInput.value = 20 - (data.result.body.TOTALCOUNT);
						}	
						}
						if(data.result.msg == "true") {
							radios[0].checked = true;
						} else {
							radios[1].checked = true;
						}
					}else{
						if(data.result.msg == "true") {
							radios[0].checked = true;
						} else {
							radios[1].checked = true;
						}
						radios[1].checked = true;
					}
				});
				
			}
			
			
			if(siblingWithHrpClass) {
				const radios = siblingWithHrpClass.querySelectorAll('input[type="radio"]')
				fetch(`${contextPath}`+'/web/consulting/searchHRP.do?mId=85', {
					method: 'POST',
					body : mberId
				}).then((response) => {
					if(!response.ok){
						alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
						throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
					}
					return response.json();
				})
				.then((data) => {
					if(data.result.code == '1') {
						radios[0].checked = true;
						if(element.children.length>0) {
							const elementId = element.children[0].id;
							const elementFileId = document.getElementById(elementId+"_file");
							if(elementFileId){
								disabledAllChildren(elementFileId);
							}
							
						}
					}else {
						radios[1].checked = true;
						if(element.children.length>0) {
							const elementId = element.children[0].id;
							const elementFileId = document.getElementById(elementId+"_file");
							if(elementFileId){
								inabledAllChildren(elementFileId);
							}
							
						}
					}
			}) 
			}
		})
	}
	setTimeout(function() {
		callback();
	}, 2000);
}

// 기업담당자 연락처 입력시 전화번호 입력 포맷팅
function formatPhoneNumber(input) {
	let inputValue = input.value.replace(/\D/g, '');
	
	if(inputValue.length == 9) {
		input.value = inputValue.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
	}else if(inputValue.length == 10) {
		input.value = inputValue.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
	}else if(inputValue.length == 11) {
		input.value = inputValue.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
	}else {
		input.value = inputValue;
	}
}


// 컨설팅 (임시)저장,제출
function saveCnsl(confmStatus) {
	
	const cnslApplyForm = document.getElementById('applyForm');
	
	const formData = new FormData(cnslApplyForm);
	formData.set('confmStatus', confmStatus);
	
	var pmId = document.getElementById('pmId');
	var exExpertId = document.getElementById('exExpertId');
	
	
	// 적합여부 체크
	const elementsOfsuitableN = document.querySelectorAll('input[type="radio"][id$="suitableN"]');
	for(var i = 0 ; i< elementsOfsuitableN.length; i ++) {
		if(!elementsOfsuitableN[i].disabled){
			if(elementsOfsuitableN[i].checked) {	
				alert("적합여부가 N인 회원은 추가할 수 없습니다.")
				elementsOfsuitableN[i].focus();
				return ;
			}
		}
	}
	
	// 동의 여부 체크
	const consentCheckbox = document.getElementById('consentCheckbox');
	if(consentCheckbox && !consentCheckbox.checked) {
		alert('정보 활용 표준 동의서에 동의하셔야 합니다.') 
		consentCheckbox.focus();
		return ;
		
	}
	

	
	if(cnslApplyForm.checkValidity()){
			fetch(`${contextPath}`+'/web/consulting/save.do?mId=85', {
				method: 'POST',
				body : formData
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				const newURL = `${contextPath}`+'/web/consulting/list.do?mId=64'
				history.replaceState({}, '', newURL);
				window.location.href = newURL;
			});

	  }else{ 
		  const requiredElements = cnslApplyForm.querySelectorAll('[required]');
		  requiredElements.forEach(element => {
			  if(!element.checkValidity()) {
				  var checkName = element.dataset.checkvalidity;
				  alert(`${checkName}은 빈값 일수 없습니다.`)
				  element.focus();
				  throw new Error('StopIteration');
} 
		  });
		  
	  }
	 
		
}

// (임시)저장 중이던 컨설팅 삭제
function deleteCnsl() {
	
	const cnslApplyForm = document.getElementById('applyForm');
	const formData = new FormData(cnslApplyForm);
	
	fetch(`${contextPath}`+'/web/consulting/delete.do?mId=85', {
		method: 'POST',
		body : formData
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		const newURL = `${contextPath}`+'/web/consulting/list.do?mId=64'
		history.replaceState({}, '', newURL);
		window.location.href = newURL;
	});
	
}


// 컨설팅 회수
function cancelCnsl(cnslIdx, confmStatus) {
	
	const cancelData = {
			cnslIdx: cnslIdx,
			confmStatus: confmStatus
	}
	if(confirm('해당 컨설팅 신청을 회수 하시겠습니까?')){
		fetch(`${contextPath}`+'/web/consulting/cancel.do?mId=85', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : JSON.stringify(cancelData)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const newURL = `${contextPath}`+'/web/consulting/list.do?mId=64'
			history.replaceState({}, '', newURL);
			window.location.href = newURL;
		});
	}else{
		return;
	}
}

// 컨설팅 반려요청
function reqRejectCnsl(cnslIdx, confmStatus) {
	
	const cancelData = {
			cnslIdx: cnslIdx,
			confmStatus: confmStatus
	}
	

	if(confirm('해당 컨설팅의 반려요청을 하시겠습니까?')){
		fetch(`${contextPath}`+'/web/consulting/cancel.do?mId=85', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : JSON.stringify(cancelData)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const newURL = `${contextPath}`+'/web/consulting/list.do?mId=64'
			history.replaceState({}, '', newURL);
			window.location.href = newURL;
		});
	}else{
		return;
	}
}

// 컨설팅 중도포기
function reqDropOut(cnslIdx, confmStatus) {
	
	const cancelData = {
			cnslIdx: cnslIdx,
			confmStatus: confmStatus
	}
	

	if(confirm('해당 컨설팅을 중도포기 하시겠습니까?')){
		fetch(`${contextPath}`+'/web/consulting/cancel.do?mId=85', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : JSON.stringify(cancelData)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const newURL = `${contextPath}`+'/web/consulting/cnslListAll.do?mId=95'
			history.replaceState({}, '', newURL);
			window.location.href = newURL;
		});
	}else{
		return;
	}
}



// 컨설팅 접수
function receiveCnsl(cnslIdx, confmStatus) {
	
	const cancelData = {
			cnslIdx: cnslIdx,
			confmStatus: confmStatus
	}
	

	if(confirm('해당 컨설팅 신청을 접수 하시겠습니까?')){
		fetch(`${contextPath}`+'/web/consulting/receive.do?mId=85', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : JSON.stringify(cancelData)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const newURL = `${contextPath}`+'/web/consulting/applyList.do?mId=96'
			history.replaceState({}, '', newURL);
			window.location.href = newURL;
		});
	}else{
		return;
	}
}

// 컨설팅 승인
function approveCnsl(confmStatus) {
	
	const cnslApplyForm = document.getElementById('applyForm');
	
	
	const formData = new FormData(cnslApplyForm);
	
	formData.set('confmStatus', confmStatus);
	
	// 적합여부 체크
	const elementsOfsuitableN = document.querySelectorAll('input[type="radio"][id$="suitableN"]');
	for(var i = 0 ; i< elementsOfsuitableN.length; i ++) {
		if(!elementsOfsuitableN[i].disabled){
			if(elementsOfsuitableN[i].checked) {	
				alert("적합여부가 N인 회원은 추가할 수 없습니다.")
				elementsOfsuitableN[i].focus();
				return ;
			}
		}
	}
	
	if(cnslApplyForm.checkValidity()){
			fetch(`${contextPath}`+'/web/consulting/save.do?mId=85', {
				method: 'POST',
				body : formData
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				const newURL = `${contextPath}`+'/web/consulting/applyList.do?mId=96';
				history.replaceState({}, '', newURL);
				window.location.href = newURL;
			});
			
	}else{
		  
		  const requiredElements = cnslApplyForm.querySelectorAll('[required]');
		  requiredElements.forEach(element => {
			  if(!element.checkValidity()) {
				  var checkName = element.dataset.checkvalidity;
				  alert(`${checkName}은 빈값 일수 없습니다.`)
				  element.focus();
				  throw new Error('StopIteration') }});

	}
		
	}

// 추가 컨설팅 승인
function saveByCenter(confmStatus) {
	
	const cnslApplyForm = document.getElementById('applyForm');
	
	
	const formData = new FormData(cnslApplyForm);
	
	formData.set('confmStatus', confmStatus);
	
	// 적합여부 체크
	const elementsOfsuitableN = document.querySelectorAll('input[type="radio"][id$="suitableN"]');
	for(var i = 0 ; i< elementsOfsuitableN.length; i ++) {
		if(!elementsOfsuitableN[i].disabled){
			if(elementsOfsuitableN[i].checked) {	
				alert("적합여부가 N인 회원은 추가할 수 없습니다.")
				elementsOfsuitableN[i].focus();
				return ;
			}
		}
	}
	
	
	if(cnslApplyForm.checkValidity()){
			fetch(`${contextPath}`+'/web/consulting/saveByCenter.do?mId=95', {
				method: 'POST',
				body : formData
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				const newURL = `${contextPath}`+'/web/consulting/cnslListAll.do?mId=95';
				history.replaceState({}, '', newURL);
				window.location.href = newURL;
			});
			
	}else{
		 
		  const requiredElements = cnslApplyForm.querySelectorAll('[required]');
		  requiredElements.forEach(element => {
			  if(!element.checkValidity()) {
				  var checkName = element.dataset.checkvalidity;
				  alert(`${checkName}은 빈값 일수 없습니다.`)
				  element.focus();
				  throw new Error('StopIteration');
			  }});

	}
		
	}






function openModal(modalId, obj, obj2) {
	$('.mask').fadeIn(150);
	
	if(modalId == 'reject-modal') {
		
		fetch(`${contextPath}`+'/web/consulting/selectConfmCn.do?mId=95', {
			method: 'POST',
			body : obj
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const contentsWrapper = document.getElementById('contentsWrapper');
			if(data.result.body) {
				data.result.body.forEach(list => {
					const formattedDate = new Date(list.regiDate);
					
					const oneBox = document.createElement('div');
					oneBox.className = 'one-box';
					
					oneBox.innerHTML = `<dl>
						<dt>
					<label> 날짜</label>
				</dt>
				<dd>
					${formattedDate.toLocaleString('ko-KR')}
				</dd>
			</dl>
									<dl>
				<dt>
					<label> 작성자</label>
				</dt>
				<dd>
					${list.regiName}
				</dd>
			</dl>
			<dl>
				<dt>
					<label> 의견 </label>
				</dt>

				<dd>
					<textarea id="confmCn" name="confmCn" rows="4"
						placeholder="의견을 입력하세요" readOnly>${list.confmCn}</textarea>
				</dd>
			</dl>`
				
						contentsWrapper.appendChild(oneBox);
						
				})
				
			}
		});
	
	}else if(modalId == 'changeCmptncBrffcPicModal') {
		
		fetch(`${contextPath}`+'/web/consulting/selectDoctorInfo.do?mId=96', {
			method: 'POST',
			body : obj
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const tbody = document.getElementById('doctorInfoList');
			tbody.innerHTML='';
			data.result.body.forEach((doctor) => {
				
				const tr = document.createElement('tr')
				const tdName = document.createElement('td');
				tdName.textContent = doctor.MEMBER_NAME;
				tr.appendChild(tdName);
				
				const tdId = document.createElement('td');
				tdId.textContent = doctor.MEMBER_ID;
				tr.appendChild(tdId);
				
				const tdTelno = document.createElement('td');
				tdTelno.textContent = doctor.DOCTOR_TELNO;
				tr.appendChild(tdTelno);
				
				const tdIdx = document.createElement('input');
				tdIdx.type = 'hidden';
				tdIdx.value = doctor.MEMBER_IDX;
				tr.appendChild(tdIdx)

				
				const tdButton = document.createElement('td');
				tdButton.innerHTML = `<button type="button" class="btn-m01 btn-color03" onclick="changeCmptncBrffcPic(this, ${obj2})">선택</button>`;
				tr.appendChild(tdButton);
				
				
				
				tbody.appendChild(tr);
			});

		});
		
	}else if(modalId == 'selectNcsModal') {
		const input = obj.previousElementSibling.value;
		
	/*	fetch(`${contextPath}`+'/web/consulting/selectNcsInfo.do?mId=85', {
			method: 'POST',
			body : input
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const tbody = document.getElementById('ncsInfoTbody');
			tbody.innerHTML='';
			
				data.result.body.forEach((ncs) => {
				const tr = document.createElement('tr')
				const tdName = document.createElement('td');
				tdName.textContent = ncs.NCS_CODE;
				tr.appendChild(tdName);
				
				const tdId = document.createElement('td');
				tdId.textContent = ncs.NCS_NAME;
				tr.appendChild(tdId);
				
				const tdButton = document.createElement('td');
				tdButton.innerHTML = `<button type="button" class="btn-m01 btn-color03" onclick="inputNcsCode(this)">선택</button>`;
				tr.appendChild(tdButton);

				tbody.appendChild(tr);
			});
			*/
/*		});*/
		
	}else if(modalId == "consentModal") {
		event.preventDefault();
	}else if(modalId == "changeReject-modal") {
		fetch(`${contextPath}`+'/web/consulting/selectChangeConfmCn.do?mId=95', {
			method: 'POST',
			body : obj
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const contentsWrapper = document.getElementById('contentsWrapper');
			if(data.result.body) {
				data.result.body.forEach(list => {
					const formattedDate = new Date(list.regiDate);
					
					const oneBox = document.createElement('div');
					oneBox.className = 'one-box';
					
					oneBox.innerHTML = `<dl>
						<dt>
					<label> 날짜</label>
				</dt>
				<dd>
					${formattedDate.toLocaleString('ko-KR')}
				</dd>
			</dl>
									<dl>
				<dt>
					<label> 작성자</label>
				</dt>
				<dd>
					${list.regiName}
				</dd>
			</dl>
			<dl>
				<dt>
					<label> 의견 </label>
				</dt>

				<dd>
					<textarea id="confmCn" name="confmCn" rows="4"
						placeholder="의견을 입력하세요" readOnly>${list.confmCn}</textarea>
				</dd>
			</dl>`
				
						contentsWrapper.appendChild(oneBox);
						
				})
				
			}
		});
	
		
	}
	
	
	document.getElementById(modalId).style.display = "block";
	
}



function closeModal(modalId) {
	document.getElementById(modalId).style.display = "none";
	
	if(document.querySelector("#contentsWrapper")){
		document.querySelector("#contentsWrapper").innerHTML = '';
	}
	
	$('.mask').fadeOut(150);
}

function changeCmptncBrffcPic(obj, cnslIdx) {
	const confirmChange = confirm("해당 컨설팅의 주치의를 변경 하시겠습니까?");
		
	if(confirmChange){
		const memberIdx = obj.parentNode.parentNode.querySelector('input').value;
		let json = {"memberIdx" : memberIdx , "cnslIdx" : cnslIdx};
		fetch(`${contextPath}`+'/web/consulting/changeCmptncBrffcPic.do?mId=64', {
			method: 'POST',
			body : JSON.stringify(json)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			if(data.result.body == '1') {
				const newURL = `${contextPath}`+'/web/consulting/applyList.do?mId=96'
				history.replaceState({}, '', newURL);
				window.location.href = newURL;
			}

		});
	}
}


// 컨설팅 반려
function rejectCnsl() {

	const cnslIdx = document.getElementById('cnslIdxByModal').value;
	const confmStatus = document.getElementById('statusByModal').value;
	const confmCn = document.getElementById('confmCn').value.trim();
	
	const json = {
			cnslIdx : cnslIdx,
			confmStatus : confmStatus,
			confmCn : confmCn
	}

	fetch(`${contextPath}`+'/web/consulting/reject.do?mId=85', {
		method: 'POST',
		body : JSON.stringify(json)
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		const newURL = `${contextPath}`+'/web/consulting/applyList.do?mId=96'
		history.replaceState({}, '', newURL);
		window.location.href = newURL;
	});
	
	
	
}


function checkDuplicatedId() {
	let memberIds = [];
	
	let memberIdTds = document.querySelectorAll('.mberIdTd');
	memberIdTds.forEach((memberIdTd)=> {
		let input = memberIdTd.querySelector("input");
		if(input){
			let memberId = input.value.trim();
			if(memberId != '') {
				memberIds.push(memberId)
			}
		}
		
		
	});
	
	return new Set(memberIds).size == memberIds.length;
}

	

	



// 인력풀 조회
function hrpUserSearchHandler(obj) {
	
	
	
	if(!checkDuplicatedId()) {
		alert('중복된 아이디를 사용할수 없습니다.')
		obj.value='';
		return;
	}
	
	
	
	
	const hrd4uId = obj.value;
	
	
	
	if(hrd4uId.length < 1) {
		return;
	}
	
	const thisElementId = obj.id;
	
	const memberIdx = document.getElementById(thisElementId+"_memberIdx");
	const name = document.getElementById(thisElementId+"_name");
	const psitn = document.getElementById(thisElementId+"_psitn");
	const ofcps = document.getElementById(thisElementId+"_ofcps");
	const telno = document.getElementById(thisElementId+"_telno");
	const isSuitableY = document.getElementById(thisElementId+"_suitableY")
	const isSuitableN = document.getElementById(thisElementId+"_suitableN")
	const isHrpY = document.getElementById(thisElementId+"_isHrpY");
	const isHrpN = document.getElementById(thisElementId+"_isHrpN");
	const attached = document.getElementById(thisElementId+"_file");
	const checkLimitTd = obj.parentElement.parentElement.querySelector('.checkLimitTd')


	fetch(`${contextPath}`+'/web/consulting/searchHRP.do?mId=85', {
		method: 'POST',
		body : hrd4uId
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == '-1') { // 4U 아이디가 없음
			isHrpY.checked = false;
			isHrpN.checked = true;
			name.value = '';
			document.getElementById(obj.id).value = '';
			document.getElementById(obj.id).placeholder = '존재하지 않는 HRD4U ID 입니다';
			
			return ;
		}else if(data.result.code == '1') { // 인력풀 등록
			isHrpY.checked = true;
			isHrpN.checked = false;
			memberIdx.value = data.result.body.hrpId;
			name.value = data.result.body.hrpNm;
			psitn.value = data.result.body.wrkAreaNm;
			ofcps.value = data.result.body.wrkAreaOfcps;
			telno.value = data.result.body.telNo;
			
			document.getElementById(obj.id).placeholder = '';
			disabledAllChildren(attached);
			
			fetch(`${contextPath}`+'/web/consulting/searchSuitable.do?mId=85', {
				method: 'POST',
				body : hrd4uId
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				if(data.result.body) {
					if(checkLimitTd){
					if(checkLimitTd.querySelector('input')) {
						checkLimitTd.querySelector('input').value = 20 - (data.result.body.TOTALCOUNT);
					}
					
					}
					if(data.result.msg == "true") {
						isSuitableY.checked = true;
					} else {
						isSuitableN.checked = true;
						switch(data.result.code) {
						case 2:
							alert("보안서약서가 완료되지 않았습니다.\n다시 확인하여 주시기 바랍니다.")
							break;
						case 3:
							alert("컨설턴트교육이 완료되지 않았습니다.\n다시 확인하여 주시기 바랍니다.")
							break;
						case 4:
							alert("컨설팅 한도가 초과되었습니다.\n다시 확인하여 주시기 바랍니다.")
							break;
						}
					}
				}else {
					if(data.result.msg == "true") {
						isSuitableY.checked = true;
					} else {
						isSuitableN.checked = true;
						switch(data.result.code) {
						case 2:
							alert("보안서약서가 완료되지 않았습니다.\n다시 확인하여 주시기 바랍니다.")
							break;
						case 3:
							alert("컨설턴트교육이 완료되지 않았습니다.\n다시 확인하여 주시기 바랍니다.")
							break;
						case 4:
							alert("컨설팅 한도가 초과되었습니다.\n다시 확인하여 주시기 바랍니다.")
							break;
						}
					}
					if(checkLimitTd){
					if(checkLimitTd.querySelector('input')) {
						checkLimitTd.querySelector('input').value = 20;
					}
					}
				}
			});
		}else if(data.result.code == '2') { // 인력풀 미등록
			isHrpY.checked = false;
			isHrpN.checked = true;
			
			memberIdx.value = data.result.body.MEMBER_IDX;		
			if(data.result.body.MEMBER_NAME){
				name.value = data.result.body.MEMBER_NAME;
			}
			if(data.result.body.ORG_NAME){
				psitn.value = data.result.body.ORG_NAME;
			}
			if(data.result.body.MOBILE_PHONE) {
				telno.value = data.result.body.MOBILE_PHONE;
			}
			
			document.getElementById(obj.id).placeholder = '';
			
			inabledAllChildren(attached);
			
			fetch(`${contextPath}`+'/web/consulting/searchSuitable.do?mId=85', {
				method: 'POST',
				body : hrd4uId
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				if(data.result.body) {
					if(checkLimitTd){
					if(checkLimitTd.querySelector('input')) {
						checkLimitTd.querySelector('input').value = 20 - (data.result.body.TOTALCOUNT);
					}
					
					}
					if(data.result.msg == "true") {
						isSuitableY.checked = true;
					} else {
						isSuitableN.checked = true;
						switch(data.result.code) {
						case 2:
							alert("보안 서약서가 완료되지 않은 회원입니다.")
							break;
						case 3:
							alert("컨설턴트교육이 완료되지 않은 회원입니다.")
							break;
						case 4:
							alert("컨설팅 한도가 초과한 회원입니다.")
							break;
						}
					}
				}else {
					if(data.result.msg == "true") {
						isSuitableY.checked = true;
					} else {
						isSuitableN.checked = true;
						switch(data.result.code) {
						case 2:
							alert("보안 서약서가 완료되지 않은 회원입니다.")
							break;
						case 3:
							alert("컨설턴트교육이 완료되지 않은 회원입니다.")
							break;
						case 4:
							alert("컨설팅 한도가 초과한 회원입니다.")
							break;
						}
					}
					if(checkLimitTd){
					if(checkLimitTd.querySelector('input')) {
						checkLimitTd.querySelector('input').value = 20;
					}
					}
				}
			}); 
		}

	});

	
}

// 회원 조회
function userSearchHandler(obj) {
	if(!checkDuplicatedId()) {
		alert('중복된 아이디를 사용할수 없습니다.')
		obj.value='';
		return;
	}
	
	const hrd4uId = obj.value;
	const thisElementId = obj.id;
	const name = document.getElementById(thisElementId+"_name");
	const ofcps = document.getElementById(thisElementId+"_ofcps");
	const psitn = document.getElementById(thisElementId+"_psitn");
	const memberIdx = document.getElementById(thisElementId+"_memberIdx");
	
	// 4UID 존재여부 를 조회
	fetch(`${contextPath}`+'/web/consulting/searchHRP.do?mId=85', {
		method: 'POST',
		body : hrd4uId
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == '-1') { //아이디가 없음
			document.getElementById(obj.id).value = '';
			document.getElementById(obj.id).placeholder = '존재하지 않는 HRD4U ID 입니다';
			name.value = '';
		}else if(data.result.code == '2') { // 인력풀 미등록
			document.getElementById(obj.id).placeholder = '';
			name.value = data.result.body.MEMBER_NAME;
			psitn.value = data.result.body.ORG_NAME;
			memberIdx.value = data.result.body.MEMBER_IDX;
		}else if(data.result.code == '1') { // 인력풀 등록
			document.getElementById(obj.id).placeholder = '';
			name.value = data.result.body.hrpNm;
			ofcps.value = data.result.body.wrkAreaOfcps;
			psitn.value = data.result.body.wrkAreaNm;
			memberIdx.value = data.result.body.hrpId;
		}

	});
	
}


// 컨설팅 변경신청
function changeCnsl(confmStatus) {
	
	const cnslChangeForm = document.getElementById('changeForm');
	

	const formData = new FormData(cnslChangeForm);
	
	formData.set('confmStatus', confmStatus);
	
	// 적합여부 체크
	const elementsOfsuitableN = document.querySelectorAll('input[type="radio"][id$="suitableN"]');
	for(var i = 0 ; i< elementsOfsuitableN.length; i ++) {
		if(!elementsOfsuitableN[i].disabled){
			if(elementsOfsuitableN[i].checked) {	
				alert("적합여부가 N인 회원은 추가할 수 없습니다.")
				elementsOfsuitableN[i].focus();
				return ;
			}
		}
	}

	if(cnslChangeForm.checkValidity()){
			fetch(`${contextPath}` + '/web/consulting/changeCnsl.do?mId=95', {
				method: 'POST',
				body : formData
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}
				return response.json();
			})
			.then((data) => {
				location.reload();
			});
	}else{
		  const requiredElements = cnslChangeForm.querySelectorAll('[required]');
		  requiredElements.forEach(element => {
			  if(!element.checkValidity()) {
				  var checkName = element.dataset.checkvalidity;
				  alert(`${checkName}은 빈값 일수 없습니다.`)
				  element.focus();
				  throw new Error('StopIteration');
} 
		  });
		  
	}
			
			
			
}

// 컨설팅 변경신청 승인
function reqChangeCnslApprove(cnslIdx) {
	if(confirm('해당 컨설팅 변경신청을 승인 하시겠습니까?')){
		fetch(`${contextPath}`+'/web/consulting/changeConfirm.do?mId=95', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : cnslIdx
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const newURL = `${contextPath}`+'/web/consulting/cnslListAll.do?mId=95'
			history.replaceState({}, '', newURL);
			window.location.href = newURL;
		});
	}else{
		return;
	}
}

// 컨설팅 변경신청 반려
	function reqChangeCnslReject() {

		const cnslIdx = document.getElementById('cnslIdxByModal').value;
		const chgIdx = document.getElementById('chgIdxByModal').value;
		const confmStatus = document.getElementById('statusByModal').value;
		const confmCn = document.getElementById('confmCn').value.trim();
		
		const json = {
				cnslIdx : cnslIdx,
				chgIdx : chgIdx,
				confmStatus : confmStatus,
				confmCn : confmCn
		}

		fetch(`${contextPath}`+'/web/consulting/changeReject.do?mId=95', {
			method: 'POST',
			body : JSON.stringify(json)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const newURL = `${contextPath}`+'/web/consulting/cnslListAll.do?mId=95'
			history.replaceState({}, '', newURL);
			window.location.href = newURL;
		});
		
		
		
	}
	
	

	
function addExExpert(button) {
	let exExpertCount = document.querySelectorAll('.exExpert');
	nowExIdx = exExpertCount.length;
	
	
	if(nowExIdx < maxExIdx) {
	let teamTable = document.getElementById("teamTable");
	let attachedTable = document.getElementById("attachedTable");
	
	let teamRow = button.parentNode.parentNode.parentNode;
	let attachedRow = document.getElementById("exExpertId_01_file");
	let newTeamRow = teamTable.insertRow(teamRow.rowIndex + nowExIdx);
	newTeamRow.id = `exExpert_0${nowExIdx+1}`;
	newTeamRow.classList.add('exExpert');
	newTeamRow.innerHTML = `<th scope="row">외부 내용전문가${nowExIdx+1}
						</th>
						<td class="mberIdTd"><input type="text" id="exExpertId_0${nowExIdx+1}"
							name="cnslTeam.members[${nowExIdx+1}].loginId"
							onblur="hrpUserSearchHandler(this)"
							value="" class="w100" placeholder="HRD4U ID" required></td>
						<td class=""><input type="text" id="exExpertId_0${nowExIdx+1}_name"
							name="cnslTeam.members[${nowExIdx+1}].mberName"
							value="" class="w100" required readOnly></td>
						<td class="checkSuitableRadioTd">
						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].rspnberYn" value="N"> 
						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].teamIdx" value="2"> 
						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].teamOrderIdx" value="${nowExIdx+1}">
						<input  type="hidden" id="exExpertId_0${nowExIdx+1}_memberIdx" name="cnslTeam.members[${nowExIdx+1}].memberIdx" value="${nowExIdx+1}">
						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].innerExtrlCd" value="1">
						<input  type="hidden" id="exExpertId_0${nowExIdx+1}_psitn" name="cnslTeam.members[${nowExIdx+1}].mberPsitn" value="">
						<input  type="hidden" id="exExpertId_0${nowExIdx+1}_ofcps" name="cnslTeam.members[${nowExIdx+1}].mberOfcps" value="">
						<input  type="hidden" id="exExpertId_0${nowExIdx+1}_telno" name="cnslTeam.members[${nowExIdx+1}].mberTelno" value="">
						
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_suitableY" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0${nowExIdx+1}01">
										Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_suitableN" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0${nowExIdx+1}02">
										N </label>
								</div>
							</div>
							</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_isHrpY" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0${nowExIdx+1}03">
										Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_isHrpN" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0${nowExIdx+1}04">
										N </label>
								</div>
							</div>
						</td>`;
	
	
	let newAttachedRow = attachedTable.insertRow(attachedRow.rowIndex + nowExIdx);
	newAttachedRow.id = `exExpertId_0${nowExIdx+1}_file`;
	newAttachedRow.classList.add('exExpertFile');
	newAttachedRow.innerHTML = `<td class="">외부 내용전문가${nowExIdx+1}
						</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${nowExIdx+2}" class="fileName"
									placeholder="파일찾기"
									value="" required> <label for="exExpert_0${nowExIdx+1}_file" class="btn_file">찾아보기</label> <input
									type="file" id="exExpert_0${nowExIdx+1}_file" name="exFile_0${nowExIdx+1}" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_0${nowExIdx+2}').value = this.value">
							</div>
						</td>`;
	
	
	
	nowExIdx++;
	
	} else {
		alert("더 이상 추가할 수 없습니다.")
	}
}

function addExExpertByAdmin(button) {
	let exExpertCount = document.querySelectorAll('.exExpert');
	nowExIdx = exExpertCount.length;
	
	
	if(nowExIdx < maxExIdx) {
	let teamTable = document.getElementById("teamTable");
	let attachedTable = document.getElementById("attachedTable");
	
	let teamRow = button.parentNode.parentNode.parentNode;
	let attachedRow = document.getElementById("exExpertId_01_file");
	
	let newTeamRow = teamTable.insertRow(teamRow.rowIndex + nowExIdx);
	newTeamRow.id = `exExpert_0${nowExIdx+1}`;
	newTeamRow.classList.add('exExpert');
	newTeamRow.innerHTML = `<th scope="row">외부 내용전문가${nowExIdx+1}
						</th>
						<td class="mberIdTd">
						<input type="text" id="exExpertId_0${nowExIdx+1}"
							name="cnslTeam.members[${nowExIdx+1}].loginId"
							onblur="hrpUserSearchHandler(this)"
							value="" class="w100" placeholder="HRD4U ID" required></td>
						<td class=""><input type="text" id="exExpertId_0${nowExIdx+1}_name"
							name="cnslTeam.members[${nowExIdx+1}].mberName"
							value="" class="w100" required readOnly></td>
						
							
						<td class="">
							<input  type="text" id="exExpertId_0${nowExIdx+1}_psitn" name="cnslTeam.members[${nowExIdx+1}].mberPsitn" value="" class="w100" required>
						</td>
						<td class="">	
							<input  type="text" id="exExpertId_0${nowExIdx+1}_ofcps" name="cnslTeam.members[${nowExIdx+1}].mberOfcps" value="" class="w100" required>
						</td>
						<td class="">	
							<input  type="text" id="exExpertId_0${nowExIdx+1}_telno" name="cnslTeam.members[${nowExIdx+1}].mberTelno" value="" class="w100" required>
						</td>	
							
							
						<td class="checkSuitableRadioTd">

						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].rspnberYn" value="N"> 
						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].teamIdx" value="2"> 
						<input  type="hidden" name="cnslTeam.members[${nowExIdx+1}].teamOrderIdx" value="${nowExIdx+1}"> 
						<input  type="hidden" id="exExpertId_0${nowExIdx+1}_memberIdx" name="cnslTeam.members[${nowExIdx+1}].memberIdx" value="${nowExIdx+1}">
	
						
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_suitableY" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0101">
										Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_suitableN" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">
										N </label>
								</div>
							</div>
							</td>
						<td class="checkHrpRadioTd">
							<div class="input-radio-wrapper center">
								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_isHrpY" name="" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0101">
										Y </label>
								</div>

								<div class="input-radio-area">
									<input type="radio" id="exExpertId_0${nowExIdx+1}_isHrpN" name="" value=""
										class="radio-type01" onclick="event.preventDefault()"> <label for="radio0102">
										N </label>
								</div>
							</div>
						</td>
						<td class="checkLimitTd">
							<input type="text" id="" name="" value="" class="w100"></td>
						</td>
						
						`;
	
	
	let newAttachedRow = attachedTable.insertRow(attachedRow.rowIndex + nowExIdx);
	newAttachedRow.id = `exExpertId_0${nowExIdx+1}_file`;
	newAttachedRow.classList.add('exExpertFile');
	newAttachedRow.innerHTML = `<td class="">외부 내용전문가${nowExIdx+1}
						</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${nowExIdx+2}" class="fileName"
									placeholder="파일찾기"
									value="" required> <label for="exExpert_0${nowExIdx+1}_file" class="btn_file">찾아보기</label> <input
									type="file" id="exExpert_0${nowExIdx+1}_file" name="exFile_0${nowExIdx+1}" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_0${nowExIdx+2}').value = this.value">
							</div>
						</td>`;
	
	
	
	nowExIdx++;
	
	} else {
		alert("더 이상 추가할 수 없습니다.")
	}
}



function addInExpert(button) {
	let inExpertCount = document.querySelectorAll('.inExpert');
	nowInIdx = inExpertCount.length;
	
	if(nowInIdx < maxInIdx) {
	let teamTable = document.getElementById("teamTable");
	let attachedTable = document.getElementById("attachedTable");
	
	let teamRow = button.parentNode.parentNode.parentNode;

	let attachedRow = document.getElementById("inExpertId_01_file");
	

	let newTeamRow = teamTable.insertRow(teamRow.rowIndex + nowInIdx);
	
	newTeamRow.id = `inExpert_0${nowInIdx+1}`;
	newTeamRow.classList.add('inExpert');
	newTeamRow.innerHTML = `<th scope="row">기업 내부전문가${nowInIdx+1}
							</th>
						<td class="mberIdTd">
						<input type="text" id="inExpertId_0${nowInIdx+1}" name="cnslTeam.members[${nowInIdx+4}].loginId" value="" class="w100" placeholder="HRD4U ID" onblur="userSearchHandler(this)" required></td>
							<td class="" colspan="2">
						<input type="text" id="inExpertId_0${nowInIdx+1}_name" name="cnslTeam.members[${nowInIdx+4}].mberName" value="" class="w100" required readOnly></td>
							<td class="">
						<input type="text" id="inExpertId_0${nowInIdx+1}_ofcps" name="cnslTeam.members[${nowInIdx+4}].mberOfcps" value="" class="w100" placeholder="직위">
						
						
						<input type="hidden" id="inExpertId_0${nowInIdx+1}_psitn" name="cnslTeam.members[${nowInIdx+4}].mberPsitn" value="" class="w100">
						<input type="hidden" id="inExpertId_0${nowInIdx+1}_telno" name="cnslTeam.members[${nowInIdx+4}].mberTelno" value="" class="w100">
						<input  type="hidden" name="cnslTeam.members[${nowInIdx+4}].innerExtrlCd" value="2">
						<input  type="hidden" name="cnslTeam.members[${nowInIdx+4}].rspnberYn" value="N"> 
						<input  type="hidden" name="cnslTeam.members[${nowInIdx+4}].teamIdx" value="3"> 
						<input  type="hidden" name="cnslTeam.members[${nowInIdx+4}].teamOrderIdx" value="${nowInIdx+1}"> 
						<input  type="hidden" id="inExpertId_0${nowInIdx+1}_memberIdx" name="cnslTeam.members[${nowInIdx+4}].memberIdx" value="">
							</td>`;
	
	
	let newAttachedRow = attachedTable.insertRow(attachedRow.rowIndex + nowInIdx);
	newAttachedRow.id = `inExpertId_0${nowInIdx+1}_file`;
	newAttachedRow.innerHTML = `<td class="">기업 내부전문가${nowInIdx+1}
						</td>
						<td class="left">
							<div class="fileBox">
								<input type="text" id="fileName_0${nowInIdx+5}" class="fileName"
									placeholder="파일찾기"
									value="" required> <label for="inExpert_0${nowInIdx+1}_file" class="btn_file">찾아보기</label> <input
									type="file" id="inExpert_0${nowInIdx+1}_file" name="inFile_0${nowInIdx+1}" class="uploadBtn"
									onchange="javascript:document.getElementById('fileName_0${nowInIdx+5}').value = this.value">
							</div>
						</td>`;
	
	
	
	nowInIdx++;
	
	} else {
		alert("더 이상 추가할 수 없습니다.")
	}
}
function deleteExRow(button) {
	let exExpertCount = document.querySelectorAll('.exExpert');
	nowExIdx = exExpertCount.length;

	if(nowExIdx>1) {
		let teamTable = document.getElementById("teamTable");
		let attachedTable = document.getElementById("attachedTable");
		
		let buttonRow = button.parentNode.parentNode.parentNode;
		let attachedRow = document.getElementById("exExpertId_01_file");

		teamTable.deleteRow(buttonRow.rowIndex+nowExIdx-1);
		attachedTable.deleteRow(attachedRow.rowIndex+nowExIdx-1);
		nowExIdx--;
		
	}else{
		return;
	}

}

function deleteInRow(button) {
	let inExpertCount = document.querySelectorAll('.inExpert');
	nowInIdx = inExpertCount.length;
	
	if(nowInIdx>1) {
		let teamTable = document.getElementById("teamTable");
		let attachedTable = document.getElementById("attachedTable");
		
		let buttonRow = button.parentNode.parentNode.parentNode;
		let attachedRow = document.getElementById("inExpertId_01_file");

		teamTable.deleteRow(buttonRow.rowIndex+nowInIdx-1);
		attachedTable.deleteRow(attachedRow.rowIndex+nowInIdx-1);
		nowInIdx--;
		
	}else{
		return;
	}
}

function centerInfoInputHanlder(centerIdx) {
	fetch(`${contextPath}`+'/web/consulting/selectCenterInfo.do?mId=85', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body : centerIdx
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		document.getElementById('spntNm').value = data.result.body.PRVTCNTR_NO;
		document.getElementById('spnt').value = data.result.body.PRVTCNTR_NAME;
		document.getElementById('spntTelno').value = data.result.body.TELNO;
		
		closeModal('centerModal');
		
		
	});
	
	
}

function insttMappingHandler(zip) {
	const cmptncBrffcNm = document.getElementById('cmptncBrffcNm');
	const cmptncBrffcIdx = document.getElementById('cmptncBrffcIdx');
	const cmptncBrffcPicTelno = document.getElementById('cmptncBrffcPicTelno');
	const cmptncBrffcPicIdx = document.getElementById('cmptncBrffcPicIdx');
	
	fetch(`${contextPath}`+'/web/consulting/selectInsttIdxByZip.do?mId=85', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body : zip
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		cmptncBrffcNm.value = data.result.body.INSTT_NAME;
		cmptncBrffcIdx.value = data.result.body.INSTT_IDX;
		if(cmptncBrffcPicTelno && data.result.body.DOCTOR_TELNO) {
			cmptncBrffcPicTelno.value = data.result.body.DOCTOR_TELNO;
		}
		
		if(cmptncBrffcPicIdx){
			cmptncBrffcPicIdx.value = data.result.body.MEMBER_IDX;
		}
		
	
	});
}


function cnslTypeCheckHandler() {
	

}

// SOJT 한도체크
function sojtLimitCheck(obj, bplNo) {
	const teamInfoInputboxs = document.querySelectorAll('.inExpert, .pm, .pmFile, .inExpertFile');
	
	const json = {"bplNo" : bplNo, "cnslType" : obj.value}
	
	if(obj.value == 3) {
		document.getElementById('exExpertId_01').removeAttribute('required');
		document.getElementById('fileName_02').removeAttribute('required');
		document.getElementById('exExpert_01_file').removeAttribute('required');
	}else {
		document.getElementById('exExpertId_01').setAttribute('required', 'true');
		document.getElementById('fileName_02').setAttribute('required', 'true');
		document.getElementById('exExpert_01_file').setAttribute('required', 'true');
	}
	
	
	fetch(`${contextPath}`+'/web/consulting/sojtSaveCheck.do?mId=85', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body : JSON.stringify(json)
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.body > 0) {
			alert("저장 또는 반려된 해당 유형의 컨설팅 신청서가 있습니다.")
			obj.checked = false;
			return;
		}
	});
	
	
	if(obj.value == 1 || obj.value == 2) {
		if(teamInfoInputboxs.length >0) {
			teamInfoInputboxs.forEach(element => {disabledAllChildren(element)});
			}
	}else{
		teamInfoInputboxs.forEach(element => {
			inabledAllChildren(element)});
	}
		
	
	if(obj.value == 2 || obj.value == 3) {
		const json = {"bplNo" : bplNo, "cnslType" : obj.value}
		
		fetch(`${contextPath}`+'/web/consulting/sojtLimitCheck.do?mId=85', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			body : JSON.stringify(json)
		}).then((response) => {
			if(!response.ok){
				alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}
			return response.json();
		})
		.then((data) => {
			const sojtConfm = data.result.body.sojtConfm;
			const sojtNomalCount = data.result.body.sojtNomalCount;
			const sojtPblCount = data.result.body.sojtPblCount;
			if(sojtConfm < 1) {
				alert("SOJT선정과정이 선행되어야 합니다.")
				obj.checked = false;
				return;
			}

			if(sojtNomalCount > 2) {
				alert("S-OJT일반 신청 가능 횟수를 초과 했습니다.\n현재  : " +sojtNomalCount+"회  한도 : 3회")
				obj.checked = false;
				return;
			}

			if(sojtPblCount > 2) {
				alert("S-OJT특화 신청 가능 횟수를 초과 했습니다.\n현재  : " +sojtPblCount+"회  한도 : 3회")
				obj.checked = false;
				return;
			}
		
		});
	}
	
	
	
}

// 심층진단 협약동의서 체크
function consentHandler() {
	const consentCheckbox = document.getElementById('consentCheckbox');
	const censentCheckboxInModal = document.getElementById('censentCheckboxInModal');
	if(censentCheckboxInModal.checked) {
		consentCheckbox.checked = true;
	}else {
		consentCheckbox.checked = false;
	}
	
	closeModal('consentModal');
}

function inputNcsCode(obj) {
	const ncsCodeinput = document.getElementById('ncsCode');
	const ncsNameinput = document.getElementById('ncsName');
	
	const ncsName = obj.parentElement.previousElementSibling.textContent;
	const ncsCode = obj.parentElement.previousElementSibling.previousElementSibling.textContent;
	
	ncsCodeinput.value = ncsCode;
	ncsNameinput.value = ncsName;
	
	closeModal('selectNcsModal')
	

}

function checkLimitCnslType(bplNo, obj) {
	const json = {"bplNo" : bplNo, "cnslType" : obj.value}
	
	fetch(`${contextPath}`+'/web/consulting/checkLimitCnslType.do?mId=95', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body : JSON.stringify(json)
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		
		if(data.result.body > 0) {
			alert("해당 컨설팅의 제한갯수를 초과 했습니다.")
			obj.checked = false;
			return ;
		}
	});
	
	
	
}

//NCS 다중 Select Handler
const ncsSelectHandler = (obj, destinationId) => {
	const selectedNcsDepth1 = document.getElementById(obj.id).value;
	const destination = document.getElementById(destinationId);
	fetch(`${contextPath}`+'/web/consulting/selectNextNcsDepth.do?mId=85', {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: selectedNcsDepth1
	}).then((response) => {
		if(!response.ok){
			throw new Error("HttpStatus is not ok")
		}
		return response.json();
	})
	.then((data) => {
		
		if(destinationId == "ncsDepth3") {
			const tbody = document.getElementById('ncsInfoTbody');
			tbody.innerHTML='';
			
				data.result.body.forEach((ncs) => {
				const tr = document.createElement('tr')
				const tdName = document.createElement('td');
				tdName.textContent = ncs.NCS_CODE;
				tr.appendChild(tdName);
				
				const tdId = document.createElement('td');
				tdId.textContent = ncs.NCS_NAME;
				tr.appendChild(tdId);
				
				const tdButton = document.createElement('td');
				tdButton.innerHTML = `<button type="button" class="btn-m01 btn-color03" onclick="inputNcsCode(this)">선택</button>`;
				tr.appendChild(tdButton);

				tbody.appendChild(tr);
				});
			
		}else{
		
		data.result.body.forEach(ncs => {
			var option = document.createElement('option');
			option.value = ncs.NCS_CODE;
			option.text = ncs.NCS_NAME;
			destination.add(option);
		});
		}
		
		
		
	});
}

const searchNcs = (inputId) => {
	
	var input = document.getElementById(inputId).value;
	
	fetch(`${contextPath}`+'/web/consulting/selectNcsInfo.do?mId=85', {
		method: 'POST',
		body : input
	}).then((response) => {
		if(!response.ok){
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		const tbody = document.getElementById('ncsInfoTbody');
		tbody.innerHTML='';
		
			data.result.body.forEach((ncs) => {
			const tr = document.createElement('tr')
			const tdName = document.createElement('td');
			tdName.textContent = ncs.NCS_CODE;
			tr.appendChild(tdName);
			
			const tdId = document.createElement('td');
			tdId.textContent = ncs.NCS_NAME;
			tr.appendChild(tdId);
			
			const tdButton = document.createElement('td');
			tdButton.innerHTML = `<button type="button" class="btn-m01 btn-color03" onclick="inputNcsCode(this)">선택</button>`;
			tr.appendChild(tdButton);

			tbody.appendChild(tr);
		});
		
		});
};

function toggleSelection() {
	var button = document.getElementById('toggleButton');
	const teamInfoInputboxsCsnlType1 = document.querySelectorAll('.exExpert, .exExpertFile');

	if(button.innerText == '선택 안함') {
		button.innerHTML = `<strong class="point-color01 right">선택</strong>`;
		teamInfoInputboxsCsnlType1.forEach(element => {
			disabledAllChildren(element);
			$("#id_chk").attr("disabled", false);
		});
		
	} else {
		button.innerHTML = `<strong class="point-color01 right">선택 안함</strong>`;
		teamInfoInputboxsCsnlType1.forEach(element => {
			inabledAllChildren(element);
		});
	}
}


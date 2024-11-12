let qustnr; // 설문 질문 데이터
let qustnr_origin;
let answer;
let required = [];
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;

// 임시저장(0)
document.getElementById('btn-save').addEventListener('click', function() {
	saveQustnrData('0');
});

// 신청하기(1)
document.getElementById('btn-apply').addEventListener('click', function() {
	saveQustnrData('1');
});

// 목록으로(0)
$('.btn-back').on('click', function() {	
	const parameters = new URLSearchParams(window.location.search);
	const mId = parameters.get('mId');
//	if(mId === '56') {
//		window.location.href = contextPath + '/web/bsisCnsl/list.do?mId=56';
//	} else {
//		window.location.href = contextPath + '/web/bsisCnsl/init.do?mId=55';
//	}
	// '기업HRD이음컨설팅 신청'페이지에서 오든 '기업HRD이음컨설팅 이력'페이지에서 오든 이 리스트페이지(기업HRD이음컨설팅 이력)로 이동해야할듯
	window.location.href = contextPath + '/web/bsisCnsl/list.do?mId=56';
});


showLoading();

$.ajax({
	type: "GET",
	data : {
		rsltIdx : document.getElementById('rsltIdx').value,
		qustnrIdx : document.getElementById('qustnrIdx').value,
		bscIdx : document.getElementById('bscIdx').value
	},
	url : `${contextPath}/web/bsisCnsl/qustnrData.do?mId=55`,
	success : function (data) {
		qustnr = data.qustnr;
		qustnr_origin = data.qustnr;
		document.getElementById('rsltIdx').value = data.rsltIdx;
		document.getElementById('qustnrIdx').value = qustnr[0].QUSTNR_IDX;
		answer = data.answer;
		
		// 1. CATEGORY로 그룹화
		qustnr = qustnr.reduce(function(r, a) {
			r[a.CATEGORY] = r[a.CATEGORY] || [];
			r[a.CATEGORY].push(a);
			return r;
		}, {});

		// 1-1. CATEGORY 그룹 내 NO로 재그룹화
		for(question in qustnr) {
			qustnr[question] = qustnr[question].reduce(function(r, a) {
				r[a.NO] = r[a.NO] || [];
				r[a.NO].push(a);
				return r;
			}, {});
		}
	
		
		// 2. HTML만들기
		let necessary = ['교육훈련여건', '교육훈련요구', '기업운영현황'];
		let html = '';
		for(question in qustnr) {
			const etc = (necessary.indexOf(question) < 0) ? `(선택작성 항목)` : `(필수작성 항목)`;
			
			let box = '';
			const orderArr = Object.keys(qustnr[question]).sort();
			for(let key of orderArr) {
				const obj = qustnr[question][key];
				let option = '';
				option = makeOption(obj, obj[0].TYPE);
				
				if(obj[0].TYPE === '5') {
					box += `${option}`;
				} else {
					box += `
						<div class="survey-box">
							<h5>
								<span class="number"> ${key}. </span> 
								${obj[0].QUESTION}
								<span class="span-mobile-br"></span>
							</h5>
							${option}
						</div>`;	
				}
			}
			
			
			let title = '';
			let flag = '';
			if(question === '직군별인력현황(명)' || question === '근속연수별인력현황') {
				title = ``;
				flag = 'type02';
			} else {
				title= `
					<div class="survey-title">
						<h4 class="type01">질문항목</h4>
						<h4 class="type02">응답항목</h4>
					</div>`;
			}
			
			const container = `
				<div class="contents-box pl0">
			        <div class="survey-wrapper">
			            <h3>
			                <strong>
			                    <span>${question}</span>${etc}
			                </strong>
			            </h3>
			            <div class="survey-area ${flag}">
							${title}
							${box}
						</div>
			        </div>
			    </div>`;
			html += container;
		}
		
		$('#qustnr-area').prepend(html);
		
		if(!isEmpty(answer)) {
			checkAnswer(answer);
		} else {
			document.querySelector('#btns-area-0').style.display = 'block';
			document.querySelector('#btns-area-1').style.display = 'none';
		}
		
		hideLoading();
	}
});

function makeOption(obj, type) {
	let detail = '';
	if(obj[0].ESSNTL_YN === 'Y') {
		required.push(obj[0].QUESTION_IDX);
	}
	
	switch(type) {
	case '0':
		// 숫자선택(단일)
		let option = '';
		for(let opt in obj) {
			const data = obj[opt];
			option += 
			`<li>
	            <input type="radio" id="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}" name="${data.QUESTION_IDX}" value="${data.DETAIL_IDX}" class="radio-type01" />
	            <label for="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}">${data.OPTN_CN}</label>
	        </li>`;
		}
		
		detail = `<ul>${option}</ul>`;
		break;
	case '1':
		// 숫자선택(복수)
		let input = '';
		for(let opt in obj) {
			const data = obj[opt];
			if(data.OPTN_CN === '기타') {
				input += `
					<div class="input-checkbox-area checkbox-height">
						<input type="checkbox" id="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}" name="${data.QUESTION_IDX}" value="${data.OPTN_CN}" class="checkbox-type01 checkbox-etc" />
						<label for="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}">${data.OPTN_CN}</label>
						<input type="text" id="${data.QUESTION_IDX}-txt" name="" value="" placeholder="입력" class="input-type01 etc" />
					</div>`;
				
			} else if(data.OPTN_CN === '변화없음') {
				input += `
					<div class="input-checkbox-area checkbox-height">
						<input type="checkbox" id="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}" name="${data.QUESTION_IDX}" value="${data.OPTN_CN}" class="checkbox-type01 only-checkbox" />
						<label for="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}">${data.OPTN_CN}</label>
					</div>`;
			} else {
				input += `
					<div class="input-checkbox-area checkbox-height">
						<input type="checkbox" id="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}" name="${data.QUESTION_IDX}" value="${data.OPTN_CN}" class="checkbox-type01" />
						<label for="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}">${data.OPTN_CN}</label>
					</div>`;				
			}
		}
		
		detail = `<div class="input-checkbox-wrapper">${input}</div>`;
		break;
	case '2':
		// 양자택일(Y/N)
		for(let opt in obj) {
			const data = obj[opt];
			detail = `
			<div class="radio-swhitch-wrapper">
                <div class="radio-swhitch-area">
                    <input type="radio" id="qustnr-${data.QUESTION_IDX}-Y" name="${data.QUESTION_IDX}" value="Y"  />
                    <label for="qustnr-${data.QUESTION_IDX}-Y">예</label>
                </div>
                <div class="radio-swhitch-area">
                    <input type="radio" id="qustnr-${data.QUESTION_IDX}-N" name="${data.QUESTION_IDX}" value="N"  />
                    <label for="qustnr-${data.QUESTION_IDX}-N">아니오</label>
                </div>
            </div>`;
		}
		break;
	case '3':
		// 순위선택(1-2-3)
		let selectOption = `<option value="">선택</option>`;
		let optionsArr = [];
		let etcObjects = obj.filter(data => data.OPTN_CN === '기타');
		let tempArr = obj.filter(data => data.OPTN_CN !== '기타');
		obj = tempArr.concat(etcObjects);
		
		for(let opt in obj) {
			const data = obj[opt];
			optionsArr.push(data.OPTN_CN);
			selectOption += `<option data-idx="${data.DETAIL_IDX}" value="${data.OPTN_CN}">${data.OPTN_CN}</option>`;
		}
		
		let sub = '';
		const questionIdx = obj[0].QUESTION_IDX;
		for(let i=0;i<3;i++) {
			let hide = '';
			if(obj.length < 4 && i === 2) hide = 'style="display:none"';
			sub += `
				<dl ${hide}>
		            <dt>
		                <label for="qustnr-${questionIdx}-${obj[i].DETAIL_IDX}">
		                    ${i+1}순위
		                </label>
		            </dt>
		            <dd>
		                <select id="qustnr-${questionIdx}-${obj[i].DETAIL_IDX}" name="${questionIdx}" class="select-type02 rank-type" data-rank="${i+1}" >
		                    ${selectOption}
		                </select>
		                <input type="text" id="qustnr-${questionIdx}-${obj[i].DETAIL_IDX}-txt" name="${questionIdx}-txt" value="" placeholder="입력" class="input-type01 etc" style="display:none" />
		            </dd>
		        </dl>`;	
		}
		
		let options = optionsArr.join(' / ');
		detail = `
			<p>
				예시: ${options}
			</p>
			<div class="subrvey-ranking-wrapper">
				${sub}
			</div>
		`;
		
		break;
	case '4':
		for(let opt in obj) {
			const data = obj[opt];
			detail = `
				<div class="form-reason-wrapper">
					<label for="qustnr-${data.QUESTION_IDX}">사유 : </label>
					<textarea type="text" name="${data.QUESTION_IDX}" id="qustnr-${data.QUESTION_IDX}" value="" placeholder="자유롭게 작성해 주세요." cols="50" rows="5"  /></textarea>
				</div>`;
		}
		// 단일 서술형
		break;
	case '5':
		// 다중 서술형
		obj.sort(function(a, b) {
			return a.DETAIL_IDX - b.DETAIL_IDX;
		});
		
		for(let opt in obj) {
			const data = obj[opt];
			let optionContent = data.OPTN_CN;
			const target = optionContent.indexOf('~');
			if(target > 0) {
				optionContent = optionContent.substring(0, target) + `<span class="span-br"></span>` + optionContent.substring(target);
			}
			detail += `
				<dl>
		            <dt>
		                <label for="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}">${optionContent}</label>
		            </dt>
		            <dd>
		                <input type="number" min="0" id="qustnr-${data.QUESTION_IDX}-${data.DETAIL_IDX}" name="${data.QUESTION_IDX}" value="" placeholder="입력" class="input-type01" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
		            </dd>
		        </dl>`;
		}
		break;
	}
	
	return detail;
}
	
function checkAnswer(answers) {
	showLoading();
	
	const qustnrStatus = answers[0].QUSTNR_STATUS; // 0:미완료 / 1:완료
	
	answers = answers.reduce((acc, cur) => {
		let key = cur['QUESTION_IDX'];
		if(!acc[key]) {
			acc[key] = [];
		}
		acc[key].push(cur);
		return acc;
	}, {});
	
	for(let questionIdx in answers) {
		const elements = document.querySelectorAll(`[name="${questionIdx}"]`);

		for(let idx in answers[questionIdx]) {
			const elem = elements[idx];
			const type = elem.type;
			const one = answers[questionIdx][idx];
			const answer = one.QUESTION_ANSWER;
			switch(type) {
			case "radio":
				for(const tempElem of elements) {
					if(tempElem.value === answer) tempElem.checked = true;
				}
				break;
			case "textarea":
				if(!isEmpty(answer)) elem.value = answer;
				break;
			case "checkbox": 
				if(!isEmpty(answer)) {
					if(elem.value === '기타') {
						let options = [];
						elements.forEach(element => {
							options.push(element.value);
						});
						let etcAnswer = answers[questionIdx].filter(item => !options.includes(item.QUESTION_ANSWER) && !isEmpty(item.QUESTION_ANSWER))[0].QUESTION_ANSWER;
						if(!isEmpty(etcAnswer)) {
							elem.checked = true;
							elem.parentElement.querySelector('input[type="text"]').value = etcAnswer;
						}
					} else {
						elements.forEach(e => {
							if(e.value === answer) e.checked = true;
						});
					}
				}
				break;
			case "select-one":
				if(isEmpty(answer)) {
					elem.querySelector('option[value=""]').selected = false;
				} else if(elem.querySelector(`option[value="${answer}"]`)) {
					elem.querySelector(`option[value="${answer}"]`).selected = true;
				} else {
					elem.querySelector(`option[value="기타"]`).selected = true;
					const nextInput = elem.nextSibling.nextSibling;
					nextInput.style.display = 'block';
					nextInput.value = answer;
				}
				break;
			case "number":
				elem.value = answer;
				break;
			}
		}
	}
	
	if(qustnrStatus === '0') {
		// 미완료
		document.querySelector('#btns-area-0').style.display = 'block';
		document.querySelector('#btns-area-1').style.display = 'none';
	} else {
		// 완료
		document.querySelector('#btns-area-0').style.display = 'none';
		document.querySelector('#btns-area-1').style.display = 'block';
		
		const parent = document.querySelector('#qustnr-area');
		const elements = parent.querySelectorAll('input, select, textarea');
		for(let i=0;i < elements.length ;i++) {
			const tagName = elements[i].tagName;
			const type = elements[i].getAttribute('type');
			if(tagName === 'INPUT' && (type === 'radio' || type === 'checkbox')) {
				elements[i].setAttribute('onclick', 'return false');
			} else {
				elements[i].disabled = true;
			}
				
		}
	}
	
}

$(document).on('change', '.rank-type', function() {
	const selected = $(this).val();
	const txtFieldId = $(this).attr('id') + '-txt';
	if(selected === '기타') {
		$(`input[id="${txtFieldId}"]`).show();
	} else {
		$(`input[id="${txtFieldId}"]`).hide();
		$(`input[id="${txtFieldId}"]`).val('');
	}
});


$(document).on('click', '.checkbox-etc', function() {
	const flag = $(this).is(':checked');
	const nextInput = $(this).next().next();
	if(!flag) {
		nextInput.prop('diabled', true).val('');
	} else {
		nextInput.prop('diabled', false);
	}
});

$(document).on('input', '.etc', function() {
	const checkbox = $(this).siblings('input[type="checkbox"]');
	const etcValue = $(this).val();
	
	if(checkbox.length > 0) {
		if(etcValue !== '') {
			checkbox.prop('checked', true);
		} else {
			checkbox.prop('checked', false);
		}
	}
});

$(document).on('change', '.only-checkbox', function() {
	const nameValue = $(this).attr('name');
	const sameNameElements = $(`input[name="${nameValue}"]`);
	const textInputs = sameNameElements.siblings('input[type="text"]');

	if($(this).prop('checked')) {
		sameNameElements.not(this).prop('checked', false);
		sameNameElements.not(this).prop('disabled', true);
		textInputs.val('');
		textInputs.prop('disabled', true);
	} else {
		sameNameElements.not(this).prop('disabled', false);
		textInputs.prop('disabled', false);
	}
});

function getCookie(key) {
	key = new RegExp(key + '=([^;]*)');
	return key.test(document.cookie) ? unescape(RegExp.$1) : '';
}

const isEmpty = (input) => {
	if(typeof input === "undefined" ||
		input === null || 
		input === "" || 
		input === "null" || 
		input.length === 0 || 
		(typeof input === "object" && !Object.keys(input).length)
	) {
		return true;
	} else {
		return false;
	}
}


// 설문조사 저장
const saveQustnrData = (flag) => {
	// validation check
	let maxFlag = false;
	const textElements = document.querySelectorAll("input[type='text'], textarea");
	textElements.forEach(textElem => {
		console.log(`textElem.value : ${textElem.value}`);
		if(textElem.value.length > 500) {
			alert("최대 500자까지 입력가능합니다.");
			textElem.focus();
			maxFlag = true;
		}
	});
	
	if(maxFlag) return;
	
	const form = document.getElementById('qustnr-form');
	const formData = new FormData();
	
	// textarea
	const textarea = form.querySelectorAll('textarea');
	textarea.forEach(function(element) {
		formData.append(element.name, element.value);
	});
	
	// number
	let numberFlag = false;
	const numbers = form.querySelectorAll('input[type="number"]');
	numbers.forEach(function(element, index) {
		let intValue = parseInt(element.value);
		if(isEmpty(element.value)) {
			formData.append(element.name, element.value);
		} else if(isNaN(intValue) || intValue < 0 || intValue > 999) {
			numberFlag = true;
			return;
		} else {
			formData.append(element.name, element.value);
		}
	});
	
	if(numberFlag) {
		alert('인력현황은 0~100 사이의 정수만 입력 가능합니다.');
		return;
	}
	
	// radio
	const radioInputs = form.querySelectorAll('input[type="radio"]');
	radioInputs.forEach(radio => {
		if(radio.checked) {
			formData.set(radio.name, radio.value);
		} else if(!formData.has(radio.name)) {
			formData.set(radio.name, '');
		}
	});
	
	// checkbox
	const checkButtons = form.querySelectorAll('input[type="checkbox"]');
	checkButtons.forEach(checkButton => {
		if(!checkButton.checked) {
			formData.append(checkButton.name, '');
		} else if(checkButton.value === '기타') {
			const nextInput = checkButton.nextElementSibling.nextElementSibling;
			if(nextInput.tagName === 'INPUT') {
				formData.append(checkButton.name, nextInput.value);
			}
		} else {
			formData.append(checkButton.name, checkButton.value);
		}
	});
	
	// select
	const selectElements = form.querySelectorAll('select');
	selectElements.forEach(select => {
		const name = select.name;
		const value = select.options[select.selectedIndex].value;
		if(value === '기타') {
			const nextInput = select.nextElementSibling;
			formData.append(name, nextInput.value);
		} else {
			formData.append(name, value);
		}
	});
	
	// status(0:미완료/1:완료)
	const qustnrIdx = document.getElementById('qustnrIdx').value;
	formData.append('status', flag);
	formData.append('rsltIdx', document.getElementById('rsltIdx').value);
	formData.append('qustnrIdx', qustnrIdx);
	formData.append('bscIdx', document.getElementById('bscIdx').value);
	
	const sortedFormData = sortFormData(formData);
	
	if(flag === '1') {
		for(let key of required) {
			let valueString = sortedFormData.getAll(key.toString());
			valueString = valueString.join('');
			
			if(qustnrIdx <= 3 && key === 1 && valueString === '2') {
				const required = document.getElementById('qustnr-2');
				const requiredValue = required.value.trim();
				if(isEmpty(requiredValue)) {
					alert(`[교육훈련여건 의 1-1번] 답변을 입력해주세요.`);
					required.focus();
					return;
				}
			}
			
			if(isEmpty(valueString)) {
				const question = qustnr_origin.find(x => x.QUESTION_IDX === key);
				const elements = document.querySelectorAll(`[name="${key}"]`);
				elements[0].focus();
				alert(`[${question.CATEGORY} 의 ${question.NO}번] 답변을 입력해주세요.`);
				return;
			}
			
			const elements = document.querySelectorAll(`[name="${key}"]`);
			const seenValues = {};
			for(let idx = 0; idx < elements.length; idx++) {
				const elem = elements[idx];
				
				if(elem.tagName === 'SELECT') {
					if(elem.value) {
						if(seenValues[elem.value]) {
							const question = qustnr_origin.find(x => x.QUESTION_IDX === key);
							elem.focus();
							alert(`[${question.CATEGORY} 의 ${question.NO}번] 질문의 각 순위는 중복될 수 없습니다.`);
							return;
						}
						seenValues[elem.value] = true;
					}
					
					if(idx === 0) {
						if(!elem.value) {
							const question = qustnr_origin.find(x => x.QUESTION_IDX === key);
							elem.focus();
							alert(`[${question.CATEGORY} 의 ${question.NO}번] 1순위를 선택해주세요.`);
							return;
						}
					} else if(!elem.value && idx !== elements.length-1) {
						const prevElem = elements[idx - 1];
						const nextElem = elements[idx + 1];
						
						if(prevElem.value && nextElem.value) {
							const question = qustnr_origin.find(x => x.QUESTION_IDX === key);
							elem.focus();
							alert(`[${question.CATEGORY} 의 ${question.NO}번] ${idx+1}순위를 선택해주세요.`);
							return;
						}
					}
				}
			}
		}
		
		const msg ='기업HRD이음컨설팅 단계로 넘어가면 설문조사는 수정할 수 없습니다.\n기업HRD이음컨설팅 작성 단계로 넘어가시겠습니까?';
		if(!confirm(msg)) {
			flag = 0;
			return;
		}
	}
	
	if(!isEmpty(answer)) {
		sortedFormData.append('answers', JSON.stringify(answer));
	}
	
	showLoading();
	
	
	
	fetch(`${contextPath}/web/bsisCnsl/insertQustnr.do?mId=55`, {
		method: "POST",
		body: sortedFormData
	})
	.then((response) => {
		hideLoading();
		if(!response.ok) {
			throw new Error('네트워크 통신 중 에러가 발생했습니다.\n다시 시도해주세요.');
			return false;
		}
		return response.json();
	})
	.then((data) => {
		if(!isEmpty(data.fail) && data.fail === 'BSC') {
			alert("유효하지 않은 접근입니다.\n다시 시도해주세요.");
			history.back();
			return;
		}
		
		if(!isEmpty(data.fail) && data.fail === 'CHK') {
			alert("이미 작성된 설문조사 입니다.\n다시 시도해주세요.");
			history.back();
			return;
		}
		
		if(data.param.status === '0') {
			// 임시저장
			const msg ='설문이 저장되었습니다.';
			alert(msg);
		} else {
			// 신청하기
			const msg = '설문이 완료되었습니다.';
			alert(msg);
		}
		
		window.location.href = contextPath + '/web/bsisCnsl/list.do?mId=56';
		hideLoading();
	})
	.catch(error => {
		alert(error);
	});
	
}


// formdata KEY에 따라 정렬하기
function sortFormData(formData) {
	let sortedFormDataArray  = Array.from(formData.entries()).sort(function(a, b) {
		const keyA = parseInt(a[0]);
		const keyB = parseInt(b[0]);
		
		if(isNaN(keyA)) return 1;
		if(isNaN(keyB)) return -1;
		
		return keyA - keyB;
	});
	
	let sortedFormData = new FormData();
	sortedFormDataArray.forEach(function(pair) {
		sortedFormData.append(pair[0], pair[1]);
	});
	
	return sortedFormData;
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
	loader.style.display = 'none';
	overlay.style.display = 'none';
}

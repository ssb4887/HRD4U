var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
const ALLOWED_EXTENSIONS = ['.txt', '.xlsx', '.xls', '.pdf', '.hwp', '.doc', '.ppt', '.pptx', '.jpeg', '.jpg', '.png'];
document.addEventListener('DOMContentLoaded', function() {
	hideLoading();

	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#${key}`);
		if(element) element.value = value;
	}
	
	// modal show&hide
	$(".btn-modal-close").on("click", function() {
		$(".modal-wrapper").hide();
		$(".mask").hide();
	});
});

function goView(e) {
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = e.currentTarget.getAttribute('data-idx');
	window.location.href = `${contextPath}/dct/edu/view.do?mId=${mId}&idx=${edcIdx}`;
}

function downloadFile(e) {
	const fleIdx = e.currentTarget.getAttribute('data-idx');
	const mId = document.querySelector('input[name="mId"]').value;
	const form = document.getElementById('form-box');
	const actionUrl = `${contextPath}/dct/edu/download.do?mId=${mId}`;
	form.action = actionUrl;
	document.getElementById('idx').value = fleIdx;
	form.submit();
}

function registerMember(e) {
	const edcName = document.getElementById('edcName').textContent;
	const msg = `"${edcName}" 교육을 신청하시겠습니까?`;
	if(!confirm(msg)) {
		return;
	}
	
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const edcCd = document.querySelector('input[name="edcCd"]').value;
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	formData.append('edcCd', edcCd);
	
	showLoading();
	fetch(`${contextPath}/dct/edu/register.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok) {
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result > 0) {
			alert(data.msg);
		} else {
			alert(data.msg);
		}
		window.location.reload();
	});
	
	hideLoading();
}

function cancelRegister(e) {
	const msg = `신청을 취소하시곘습니까?`;
	if(!confirm(msg)) {
		return;
	}
	
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = document.querySelector('input[name="idx"]').value;
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	
	showLoading();
	fetch(`${contextPath}/dct/edu/cancel.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok) {
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result > 0) {
			alert(data.msg);
		} else {
			alert(data.msg);
		}
		window.location.reload();
	});
	
	hideLoading();
}

function initSearhParams() {
	const form = document.querySelector('.basic-search-wrapper');
	const textInputs = form.querySelectorAll('input[type="text"],input[type="search"],select:not(#keyField)');
	textInputs.forEach(elem => {
		elem.value = '';
	});
}

function printCertificate(e) {
	const form = document.getElementById('form-box');
	const mId = document.querySelector('input[name="mId"]').value;
	const edcIdx = e.currentTarget.getAttribute('data-idx');
	const memberIdx = e.currentTarget.getAttribute('data-member');
	const actionUrl = `${contextPath}/dct/edu/printCertificate.do?mId=${mId}&idx=${edcIdx}&member=${memberIdx}`;
	form.action = actionUrl;
	form.submit();
}

function registerEdc(e) {
	const edcName = e.currentTarget.getAttribute('data-title');
	const edcIdx = e.currentTarget.getAttribute('data-idx');
	const msg = `"${edcName}"을 신청하시겠습니까?`;
	if(!confirm(msg)) {
		return;
	}
	
	const mId = document.querySelector('input[name="mId"]').value;
	const formData = new FormData();
	formData.append('edcIdx', edcIdx);
	
	showLoading();
	fetch(`${contextPath}/dct/edu/register.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	})
	.then((response) => {
		if(!response.ok) {
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	})
	.then((data) => {
		if(data.result > 0) {
			alert(data.msg);
		} else {
			alert(data.msg);
		}
		window.location.reload();
	});
	
	hideLoading();
}

function openModalForMember() {
	$(".modal-wrapper").hide();
	$(".mask").fadeIn(150, function() {
		$("#modal-doctor").show();
	});
	
	document.getElementById('is_doctorIdx').value = '';
}

function searchDoctor() {
	const mId = document.querySelector('input[name="mId"]').value;
	const form = document.getElementById('form-doctor');
	const formData = new FormData(form);
	
	showLoading();
	fetch(`${contextPath}/dct/edu/searchDoctor.do?mId=${mId}`, {
		method: 'POST',
		body: formData
	}).then((response) => {
		if(!response.ok) {
			alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
			throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
		}
		return response.json();
	}).then((data) => {
		const list = data.doctor;
		document.getElementById('doctorCount').textContent = list.length;
		const tbody = document.querySelector('tbody#modal-doctor-tbody');
		tbody.innerHTML = '';
		if(list.length > 0) {
			list.forEach((item, idx) => {
				let row = document.createElement('tr');
				row.innerHTML = `
					<td>${idx+1}</td>
					<td>${item.LOGINID}</td>
					<td>${item.HNAME}</td>
					<td>${item.SIDO}</td>
					<td>${item.INSTT_NAME}</td>
					<td>
						<button type="button" class="btn-m03 btn-color03 w100">선택</button>
					</td>
				`;
				let button = row.querySelector('button');
				button.addEventListener('click', function() {
					selectDoctor(item);
				});
				tbody.appendChild(row);
			});
		} else {
			let row = document.createElement('tr');
			row.innerHTML = `<td colspan="6">검색결과가 없습니다.</td>`;
			tbody.appendChild(row);
		}
	});
	
	hideLoading();
}

function selectDoctor(doctor) {
	document.querySelector('.btn-modal-close').click();
	document.getElementById('is_doctorIdx').value = doctor.MEMBER_IDX;
}

function formatPhoneNumbers(elements) {
	console.log(elements.length);
	elements.forEach(function(element) {
		const phoneNumber = element.value.replace(/\D/g, '');
		
		if(phoneNumber.length == 9) {
			element.value = phoneNumber.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
		}else if(phoneNumber.length == 10) {
			element.value = phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
		}else if(phoneNumber.length == 11) {
			element.value = phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		}else {
			element.value = phoneNumber;
		}
	});
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
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;
const parameters = new URLSearchParams(window.location.search);
let mId = parameters.get('mId');
let path = window.location.pathname;

/* 사업주, S-OJT 보고서 */
$(function() {
	if($("#id_trBiz1:checked").length == 1) {
		trBiz1();
	} else {
		trBiz2();
	}
});

function trBiz1() {
	$("#trainingType").html("");
	$(".trainingMethod").html("");
	$(".trainingMethod").html("사업주");
	$("#trainingType").html("사업주");
	$("#id_trType").val('1');
	$("#id_trMth").val('집체훈련');
}

function trBiz2() {
	$("#trainingType").html("");
 	$(".trainingMethod").html("");
	$(".trainingMethod").html("S-OJT");
	$("#trainingType").html("일반");
	$("#id_trType").val('4');
	$("#id_trMth").val('S-OJT훈련');
}


// 설문조사 저장
const save = (id) => {
	var f = document.forms.fn_sampleInputForm;
	var progress = document.createElement('input');
	
	if(id == 5) {
		// 저장
		progress.setAttribute("type", "hidden");
		progress.setAttribute("name", "progress");
		progress.setAttribute("value", "5");
		
		var result = confirm("저장하시겠습니까?");
		if (result) {
			f.appendChild(progress);
			document.body.appendChild(f);
			f.submit();
			showLoading();
		} else {
			return;
		}
		
	} else if(id == 10) {
		// 제출
		progress.setAttribute("type", "hidden");
		progress.setAttribute("name", "progress");
		progress.setAttribute("value", "10");
		
		if($("#tpNm").val() == "") {
	 		alert("훈련과정명 입력이 완료되지 않았습니다.");
			$("#tpNm").focus();
			return false;
		}
		
		if($("#tpPicName").val() == "") {
	 		alert("과정담당자명 입력이 완료되지 않았습니다.");
			$("#tpPicName").focus();
			return false;
		}
		
		if($("#id_tpPicTelNo").val() == "") {
	 		alert("과정담당자 전화번호 입력이 완료되지 않았습니다.");
			$("#id_tpPicTelNo").focus();
			return false;
		}
		
		if($("#trDayCnt").val() == "") {
	 		alert("훈련일수(일) 입력이 완료되지 않았습니다.");
			$("#trDayCnt").focus();
			return false;
		}
		
		if($("#clasNmpr").val() == "") {
	 		alert("학급정원 입력이 완료되지 않았습니다.");
			$("#clasNmpr").focus();
			return false;
		}
		
		if($("#clasCnt").val() == "") {
	 		alert("학급수 입력이 완료되지 않았습니다.");
			$("#clasCnt").focus();
			return false;
		}
		
		if($("#id_trSfe").val() == "") {
	 		alert("훈련특징 입력이 완료되지 않았습니다.");
			$("#id_trSfe").focus();
			return false;
		}
		
		if($("#id_trGoal").val() == "") {
	 		alert("훈련목표 입력이 완료되지 않았습니다.");
			$("#id_trGoal").focus();
			return false;
		}
		
		if($("#id_xptEffect").val() == "") {
	 		alert("기대효과 입력이 완료되지 않았습니다.");
			$("#id_xptEffect").focus();
			return false;
		}
		
		if($("#id_trnReqm").val() == "") {
	 		alert("훈련대상요건 입력이 완료되지 않았습니다.");
			$("#id_trnReqm").focus();
			return false;
		}
		
		var $dataMap = $(".profile");
		for(var i=0; i < $dataMap.length; i ++){
			if($dataMap.eq(i).val() == null || $dataMap.eq(i).val() == "" || $dataMap.eq(i).val() == " "){
				alert('교과프로필 입력이 완료되지 않았습니다.');
				return false;
			}
		}
		
		var result = confirm("제출하시겠습니까?");
		if (result) {
			f.appendChild(progress);
			document.body.appendChild(f);
			f.submit();
			showLoading();
		} else {
			return;
		}
	}
}

//설문조사 저장
const reject = () => {
	var f = document.forms.fn_sampleInputForm;
	var progress = document.createElement('input');
	var confmCn = document.createElement('input');
	var change = document.createElement('input');
	
	// 저장
	progress.setAttribute("type", "hidden");
	progress.setAttribute("name", "progress");
	progress.setAttribute("value", "40");
	
	confmCn.setAttribute("type", "hidden");
	confmCn.setAttribute("name", "confmCn");
	confmCn.setAttribute("value", $("#id_confmCn").val());
	
	change.setAttribute("type", "hidden");
	change.setAttribute("name", "change");
	change.setAttribute("value", "change");
	
	var result = confirm("반려하시겠습니까?");
	if (result) {
		f.appendChild(progress);
		f.appendChild(confmCn);
		f.appendChild(change);
		document.body.appendChild(f);
		
		f.action = `${contextPath}/web/report/inputProc.do?mId=100&mode=m`;
		f.submit();
		showLoading();
	} else {
		return;
	}
}

const approve = (id) => {
	var f = document.forms.fn_sampleInputForm;
	var progress = document.createElement('input');
	var change = document.createElement('input');
	
	// 저장
	progress.setAttribute("type", "hidden");
	progress.setAttribute("name", "progress");
	progress.setAttribute("value", id);
	
	change.setAttribute("type", "hidden");
	change.setAttribute("name", "change");
	change.setAttribute("value", "change");
	
	var result = confirm("승인하시겠습니까?");
	if (result) {
		f.appendChild(progress);
		f.appendChild(change);
		document.body.appendChild(f);
		f.action = `${contextPath}/web/report/inputProc.do?mId=100&mode=m`;
		f.submit();
		showLoading();
	} else {
		return;
	}
}

const openReject = (id) => {
	const modal = document.getElementById('openReject'); 
	modal.style.display = 'block';
}

const closeReject = (id) => {
	const modal = document.getElementById('openReject'); 
	modal.style.display = 'none';
}

const openViewReject = (id) => {
	const modal = document.getElementById('openViewReject'); 
	modal.style.display = 'block';
}

const closeViewReject = (id) => {
	const modal = document.getElementById('openViewReject'); 
	modal.style.display = 'none';
}

/* 현장활용 보고서 */

document.addEventListener('DOMContentLoaded', function() {
	hideLoading();
	const chkData = document.querySelectorAll(".chkData");
	const lastRow = chkData[chkData.length -1];
	const rowspan = $("#id_traContent").attr("rowspan");
	
	$("#id_traContent").attr("rowspan", rowspan + 1);
});

function addRow(button, max) {
	const table = button.closest('table');
	const tbody = table.querySelector('tbody');
	const allRows = tbody.querySelectorAll('tr');
	const lastRow = allRows[allRows.length -1];
	const rowspan = $("#id_traContent").attr("rowspan");
	
	var allRowsLen = allRows.length-7;
	
	if(allRowsLen === max) {
		alert(`최대 ${max}행까지 추가할 수 있습니다.`);
		return;
	}
	
	let newRow = lastRow.cloneNode(true);
	let tdElements = newRow.getElementsByTagName('td');
	for(let i=0;i < tdElements.length; i++) {
		const td = tdElements[i];
		td.removeAttribute('value');
		
		const inputElement = td.querySelector('input');
		if(inputElement && inputElement.classList.contains('rank')) {
			inputElement.value = allRows.length + 1;
		} else if(inputElement) {
			inputElement.value = '';
		}
	}
	
	$("#id_traContent").attr("rowspan", rowspan + 1);
	tbody.appendChild(newRow);
}

function deleteRow(button) {
	const table = button.closest('table');
	const tbody = table.querySelector('tbody');
	const rows = tbody.getElementsByTagName('tr');
	if(rows.length > 8) {
		tbody.removeChild(rows[rows.length - 1]);
	} else {
		alert('더이상 삭제할 수 없습니다.');
		return;
	}
}

function showPage(button, pageNumber) {
	const btns = document.querySelectorAll('.paging-navigation *');
	btns.forEach(btn => {
		if(btn.tagName === 'STRONG') {
			const aTag = document.createElement('a');
			aTag.textContent = btn.textContent;
			aTag.href = "javascript:void(0);";
			aTag.id = `js-page${btn.textContent}-btn`;
			aTag.setAttribute('onclick', `showPage(this, ${btn.textContent})`);
			btn.parentNode.replaceChild(aTag, btn);
		}
	});
	
	const pageCount = document.querySelectorAll('.page').length;
	for(let i=0;i < pageCount;i++) {
		if((i+1) === pageNumber) {
			const strongTag = document.createElement('strong');
			strongTag.textContent = button.textContent;
			button.parentNode.replaceChild(strongTag, button);
		}
		document.getElementById(`page${i+1}`).classList.add('hidden');
	}
	document.getElementById(`page${pageNumber}`).classList.remove('hidden');
	
	window.scrollTo({top: 0, behavior: 'smooth'});
	
	if(pageNumber != 3) {
		$(".receipt").attr("class", "btn-m01 btn-color03 receipt hidden");
	} else {
		$(".receipt").attr("class", "btn-m01 btn-color03 receipt");
	}
}

function showLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	if(loader && overlay) {
		loader.style.display = 'block';
		overlay.style.display = 'block';
	}
}

function hideLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	if(loader && overlay) {
		loader.style.display = 'none';
		overlay.style.display = 'none';
	}
}

function printReport() {
	window.print();
}

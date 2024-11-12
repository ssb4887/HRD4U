<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>				
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<style>
#overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0,0,0,0.5);
	display: none;
	z-index: 9999;
}
	
.loader {
	border:4px solid #f3f3f3;
	border-top: 4px solid #3498db;
	border-radius: 50%;
	width: 50px;
	height: 50px;
	animation: spin 2s linear infinite;
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	z-index: 10000;
}
span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }

@keyframes spin {
	0% { transform: translate(-50%, -50%) rotate(0deg); }
	100% { transform: translate(-50%, -50%) rotate(360deg); }
}
</style>
<div id="overlay"></div>
<div class="loader"></div>
<div id="cms_calendar_article"> 	
	<div class="contents-area02">
		<legend class="blind">협약 조회 검색양식</legend>
			<div class="basic-search-wrapper">
				<div class="one-box">
					<dl class="long">
						<dt>
							<label for="textfield01">고용보험 <span class="span-mobile-br"></span>사업장 관리번호</label>
						</dt>
						<dd>
							<div class="input-search-wrapper">
								<input type="search" id="textfield01" class="w100" name="" value="" title="고용보험 사업장 관리번호 입력" placeholder="고용보험 사업장 관리번호">
							</div>
						</dd>
					</dl>
				</div>
			</div>
		<div class="btns-area">
			<button class="btn-search02 btn-color02" onclick="search()"><strong>검색</strong></button>
		</div>
	</div>
	<div class="contents-area">
		<div class="title-wrapper">
			<div class="fr btns-area">
				<div class="btns-right">
					<button class="btn-m01 btn-color01" onclick="cleanupField()">초기화</button>
					<button class="btn-m01 btn-color01" onclick="submit()">제출</button>
				</div>
			</div>
		</div>
		<div class="table-type01 horizontal-scroll">
			<form id="formData">
			<table>
				<caption>협약 조회표 : 협약번호, 사업연도, 지원기관, 관할지부지사, 고용보험 관리번호, 기업명, 사업자등록번호, 대규모 기업구분, 협약서에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 10%">
					<col style="width: 6%">
					<col style="width: 10%">
					<col style="width: 10%">
					<col style="width: 12%">
					<col style="width: 10%">
					<col style="width: 10%">
					<col style="width: auto">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">협약번호</th>
						<th scope="col">사업연도</th>
						<th scope="col">지원기관</th>
						<th scope="col">관할지부지사</th>
						<th scope="col">고용보험 관리번호</th>
						<th scope="col">기업명</th>
						<th scope="col">대규모 기업구분</th>
						<th scope="col">협약서</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="field" id="agrem_no">${not empty register ? register.AGREM_NO : '-' }<input type="hidden" id="agrem_idx" name="agrem_idx" value/> <input type="hidden" id="agrem_no" name="agrem_no" value="${not empty register ? register.AGREM_NO : '-' }"/></td>
						<td class="field" id="year">${not empty register ? register.YEAR : '-' }</td>
						<td class="field" id="prvtcntr_name">${not empty register ? register.PRVTCNTR_NAME : '-'}</td>
						<td class="field" id="instt_name"></td>
						<td class="field" id="bpl_no"><span id="bpl_no"></span><input type="hidden" id="bpl_no" name="bpl_no" value/></td>
						<td class="field" id="bpl_nm"></td>
						<td class="field" id="lrscl_corp_se"></td>
						<td><span id="filename" style="margin-right:8px;"></span><label for="input-file" class="btn-m02 btn-color02" />업로드</label><input class="field" type="file" id="input-file" name="file" style="display:none"></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
	</div>
<form>
	<input type="hidden" id="bpl-no" name="bpl_no" value="${bpl_no }" />
</form>
<!-- //모달 창 -->
<script>
var context = location.pathname.split('/')[1]
var contextPath = ((context == 'dct') || (context == 'web')) ? '' : `/${context}`;
window.onload = () => {
	hideLoading();
	console.log("usertypeIdx = ${loginVO.usertypeIdx}");
	init();
	$('input#input-file').change(function() {
		var filename = this.files[0].name;
		console.log(filename);
		const fakepath = "/fakepath/";
		const fakefilename = fakepath+filename;
		$('span#filename').text(fakefilename);
	})
	$('input#textfield01').on('keyup', e => {
		if(e.key === 'Enter') {
			search();
		} 
	})
}
function init() {
	const data = '${data}';
	console.log(data);
	if(data != null && data.length > 0) {
		let obj = JSON.parse(decodeURIComponent(escape(window.atob(data))));
		console.log(obj)
		$('span#bpl_no').text(obj.bpl_no);
		$('td#bpl_nm').text(obj.bpl_nm);
		$('td#lrscl_corp_se').text(`\${obj.lrscl_copr_se == 1 ? '대규모기업' : '우선지원기업'}`);
		$('input#agrem_idx').val(obj.agrem_idx);
		$('input#bpl_no').val(obj.bpl_no);
	}
}
async function center_search() {
	showLoading();
	let response = await fetch(`${contextPath}/dct/agreement/getCenters.do?mId=52`, {
		method: 'POST',
		body: new FormData(formCenter)
	});
	let result = await response.json();
	render_board(result)
	hideLoading();
}
function render_board(result) {
	let tbody = document.querySelector('tbody#trbody');
	
	while(tbody.firstChild) {
		tbody.removeChild(tbody.firstChild);
	}
	
	if(result.length < 1) {
		let row = document.createElement('tr');
		row.innerHTML = `<tr>
				<td colspan="3">검색된 결과가 없습니다.</td>
			</tr>`;
		tbody.appendChild(row);
		return;
	}
	
	result.forEach((e,i) => {
		console.log(result.length, i, result.length-i);
		let row = document.createElement('tr');
		row.innerHTML = `<tr>
			<th scope="row">\${result.length-i}</th>
			<td class="left">
				<dl>
					<dt><strong class="point-color01">\${e.PRVTCNTR_NAME}</strong></dt>
				</dl>
			</td>
			<td><button onclick="request(\${e.PRVTCNTR_NO})"><span>협약 요청</span></button></td>
		</tr>`;
    	tbody.appendChild(row);
    	
	})
	let number_e = document.querySelector('strong#tr-search-number')
	number_e.textContent = result.length;
}
function request(prvtcntr_no) {
	const status = 7;
	const bpl_no = $('input#bpl-no').val();
	const item = {
			"prvtcntr_no": prvtcntr_no,
			"bpl_no": bpl_no,
			"status": status
	}
	let form_data = new FormData();
	for(let key in item) {
		form_data.append(key, item[key])
	}
	fetch(`${contextPath}/web/agreement/request.do?mId=52`,{
		method: 'POST',
		body: form_data
	}).then(e => console.log(e))
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
function cleanupField() {
	$('td.field').text('');
	$('input.field').val('');
	$('span#filename').text('');
}
async function submit() {
	// file check 해야됨
	
	let fd = new FormData(document.querySelector('form#formData'));
	const filename_ = $('input#input-file').val();
	if(filename_.length == 0 || filename_ == null) {
		alert('협약서를 업로드해주세요.')
		return
	}
	let result = await fetch(`${contextPath}/web/agreement/upload.do?mId=52`, {
		method: 'POST',
		body: fd
	})
	window.history.go(-1);
}
function search() {
	let bpl_no = $('input#textfield01').val();
	if(!isTenDigits(bpl_no)) {
		alert('잘못된 고용보험 관리번호 입니다. ' + bpl_no)
		return;
	}
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			render_result(JSON.parse(xhr.responseText));
		}
	}
	xhr.open('POST', '/doctor/web/agreement/getBsk.do?mId=52', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send('bpl_no='+encodeURIComponent(bpl_no));
}
function isTenDigits(str) {
	let t = parseInt(str)
	console.log(str)
	console.log(t)
	return /[0-9]{11}/.test(t);
}
function render_result(obj) {
	console.log(obj);
	$('span#bpl_no').text(obj.BPL_NO);
	$('td#bpl_nm').text(obj.BPL_NM);
	$('td#lrscl_corp_se').text(`\${obj.LRSCL_CORP_SE == 1 ? '대규모기업' : '우선지원기업'}`);
	$('input#agrem_idx').val(obj.agrem_idx);
	$('input#bpl_no').val(obj.BPL_NO);
}
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>

<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>						
<c:set var="searchFormId" value="fn_calendarSearchForm"/>
<c:set var="listFormId" value="fn_calendarListForm"/>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page }" />
<c:set var="start" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
<c:set var="end" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
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
<div class="contents-area02">
	<form id="main" action="${contextPath }/${siteId}/agreement/list.do?mId=52" method="GET">
		<legend class="blind">협약 검색양식</legend>
		<input type="hidden" name="mId" value="52" />
		<input type="hidden" name="page" id="page" value="1" />
		<c:if test="${usertype_idx != 5 }">
		<div class="basic-search-wrapper">
			<c:if test="${usertype_idx >= 20 }">
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label for="bpl_nm">사업장명</label>
						</dt>
						<dd>
							<input type="text" id="bpl_nm" name="bpl_nm" value="" title="사업장명 입력" placeholder="사업장명" value="${param.bpl_nm }">
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label for="bpl_no">고용보험 관리번호</label>
						</dt>
						<dd>
							<input type="text" id="bpl_no" name="bpl_no" value="${param.bpl_no }" title="고용보험 관리번호 입력" placeholder="고용보험 관리번호" >
						</dd>
					</dl>
				</div>
			</div>
			</c:if>
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label>체결일</label>
						</dt>
						<dd>
							<div class="input-calendar-wrapper">
								<div class="input-calendar-area">
									<input type="text" id="sdate" name="sdate" class="sdate" title="시작일 입력" placeholder="시작일" value="${param.sdate }"/>
								</div>
								<div class="word-unit">~</div>
								<div class="input-calendar-area">
									<input type="text" id="edate" name="edate" class="edate" title="시작일 입력" placeholder="종료일" value="${param.edate }" />
								</div>
							</div>
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label>협약 체결</label>
						</dt>
						<dd>
							<div class="input-radio-wrapper ratio">
								<div class="input-radio-area">
									<input type="radio" class="radio-type01" id="signed" name="sign" value="Y" ${param.sign == 'Y' ? 'checked' : '' }>
									<label for="signed">체결</label>
								</div>
								<div class="input-radio-area">
									<input type="radio" class="radio-type01" id="nosigned" name="sign" value="N" ${param.sign == 'N' ? 'checked' : '' }>
									<label for="nosigned">미체결</label>
								</div>
							</div>
						</dd>
					</dl>
				</div>
			</div>
			<c:if test="${usertype_idx >= 40 }">
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label for="instt">소속기관</label>
						</dt>
						<dd>
							<input type="text" id="instt" name="instt" value="${param.instt }" title="소속기관 입력" placeholder="소속기관">
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label for="center">지원기관</label>
						</dt>
						<dd>
							<input type="text" id="center" name="center" value="${param.center }" title="지원기관 입력" placeholder="지원기관">
						</dd>
					</dl>
				</div>
			</div>
			</c:if>
		</div>
		<div class="btns-area">
			<button type="submit" class="btn-search02">
				<img src="../../assets/img/icon/icon_search03.png" alt="">
				<strong>검색</strong>
			</button>
		</div>
		</c:if>
	</form>
</div>
<div id="cms_calendar_article"> 	
	<div class="title-wrapper">
		<p class="total fl">총 <strong>${ empty list ? '0' : list[0].TOTAL_COUNT }</strong> 건 ( ${empty param.page ? '1' : param.page } / ${ empty list ? '0' : list[0].TOTAL_PAGE } 페이지 ) </p>
		<div class="fr btns-area">
			<div class="btns-left">
			<c:if test="${loginVO.usertypeIdx eq 5 }">
				<button class="btn-m01 btn-color01 depth3 open-modal01"><span>협약 요청</span></button>
				<button class="btn-m01 btn-color01 depth3" onclick="seriesConfirm(20)">회수</button>
			</c:if>
			<c:if test="${loginVO.usertypeIdx eq 20 }">
				<a href="" id="register-link" class="btn-m01 btn-color01 depth3">등록</a>
				<button class="btn-m01 btn-color01 depth3" onclick="seriesConfirm(55)">일괄 승인</button>
				<button class="btn-m01 btn-color01 depth3" onclick="seriesConfirm(40)">일괄 반려</button>
			</c:if>
			<c:if test="${loginVO.usertypeIdx gt 30 }">
				<a href="${contextPath}/${siteId}/agreement/listExcel.do?mId=${param.mId}
			    	<c:if test="${not empty param.bpl_no}">&bpl_noe=${param.bpl_no}</c:if>
			    	<c:if test="${not empty param.sdate}">&sdate=${param.sdate}</c:if>
			    	<c:if test="${not empty param.edate}">&edate=${param.edate}</c:if>
			    	<c:if test="${not empty param.bpl_nm}">&bpl_nm=${param.bpl_nm}</c:if>
			    	<c:if test="${not empty param.sign}">&sign=${param.sign}</c:if>
			    	<c:if test="${not empty param.instt}">&instt=${param.instt}</c:if>
			    	<c:if test="${not empty param.center}">&center=${param.center}</c:if>" class="btn-m01 btn-color01">엑셀다운로드</a>
			</c:if>
			</div>
		</div>
	</div>
	<table class="tbListA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 제목 링크를 통해서 게시물 상세 내용으로 이동합니다.">
		<caption>협약표 : 체크박스, 협약번호, 사업연도, 지원기관, 소속기관, 고용보험 관리번호, 사업장명, 상태, 체결일, 완료일, 협약서, 승인에 관한 정보 제공표</caption>
		<colgroup id="main-colgroup">
			<c:if test="${loginVO.usertypeIdx eq 20 or loginVO.usertypeIdx eq 5}">
			<col style="width: 3%">
			</c:if>
			<col style="width: 10%">
			<col style="width: 8%">
			<col style="width: 9%">
			<col style="width: 9%">
			<col style="width: 11%">
			<col style="width: 9%">
			<col style="width: 8%">
			<col style="width: 5%">
			<col style="width: 8%">
			<col style="width: 6%">
			<col style="width: 10%">
			<c:if test="${loginVO.usertypeIdx eq 20}">
			<col style="width: 15%">
			</c:if>
		</colgroup>
		<thead>
		<tr>
			<c:if test="${loginVO.usertypeIdx eq 20 or loginVO.usertypeIdx eq 5}">
			<th scope="col"><input type="checkbox" class="checkbox-type01" id="toggler" name="" value=""></th>
			</c:if>
			<th scope="col">협약번호</th>
			<th scope="col">사업연도</th>
			<th scope="col">지원기관</th>
			<th scope="col">소속기관</th>
			<th scope="col">고용보험 관리번호</th>
			<th scope="col">기업명</th>
			<th scope="col">기업구분</th>
			<th scope="col">현황</th>
			<th scope="col">체결일자<a href="#" class="word-linked-notice02" id="open-modal01"></a></th>
			<th scope="col">완료일자</th>
			<c:choose>
				<c:when test="${loginVO.usertypeIdx eq 20}">
					<th scope="col">협약서</th>
					<th scope="col" class="end">승인</th>
				</c:when>
				<c:otherwise>
					<th scope="col" class="end">협약서</th>
				</c:otherwise>
			</c:choose>
			<!-- 마지막 th에 class="end" -->
		</tr>
		</thead>
		<tbody class="alignC" id="main">
			<c:if test="${empty list}">
			<tr class="no-item">
				<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
			<tr class="real-item">
				<c:if test="${loginVO.usertypeIdx eq 20 or loginVO.usertypeIdx eq 5}">
				<td class="num">
					<input type="checkbox" id="chk" class="item checkbox-type01" value="${listDt.AGREM_IDX }" ${listDt.STATUS eq 55 ? 'disabled' : '' }/>
					<input type="hidden" id="agrem-idx" value="${listDt.AGREM_IDX }"/>
				</td>
				</c:if>
				<td class="">
						<c:out value="${empty listDt.AGREM_NO ? '-' : listDt.AGREM_NO}"/>
				</td>
				<td>${empty listDt.YEAR ? '-' : listDt.YEAR}</td>
				<td>${empty listDt.PRVTCNTR_NAME ? '-' : listDt.PRVTCNTR_NAME }</td>
				<td>${empty listDt.INSTT_NAME ? '-' : listDt.INSTT_NAME}</td>
				<td data-key="bpl_no" data-value="${listDt.BPL_NO }">${empty listDt.BPL_NO ? '-' : listDt.BPL_NO }</td>	
				<td data-key="bpl_nm" data-value="${listDt.BPL_NM }">${empty listDt.BPL_NM ? '-' : listDt.BPL_NM }</td>
				<td data-key="lrscl_corp_se" data-value="${listDt.LRSCL_CORP_SE }">${empty listDt.LRSCL_CORP_SE ? '-' : listDt.LRSCL_CORP_SE eq 1 ? '대규모기업' : '우선지원기업' }</td>
				<c:choose>
					<c:when test="${loginVO.usertypeIdx eq 20 and (listDt.STATUS eq 7 or listDt.STATUS eq 40) }">
						<td class=""><a id="link" style="text-decoration: underline" href="">${confirmProgress.get(listDt.STATUS) }</a></td>
					</c:when>
					<c:otherwise>
						<td class="">${confirmProgress.get(listDt.STATUS) }</td>
					</c:otherwise>
				</c:choose>
				<td calss="date"><fmt:formatDate pattern="yy/MM/dd" value="${empty listDt.CNCLS_DATE ? null : listDt.CNCLS_DATE}"/></td>
				<td class="date"><fmt:formatDate pattern="yy/MM/dd" value="${listDt.END_DATE}"/></td>
				<td>
					<c:if test="${not empty listDt.FLE_IDX}">
					<button type="button" class="btn-m05" onclick="download(${listDt.AGREM_IDX})">
						<span>내려받기</span><img src="../../assets/img/icon/icon_download.png" alt="">
					</button>
					</c:if>
				</td>
				<c:if test="${loginVO.usertypeIdx eq 20}">
				<td>
					<c:choose>
					<c:when test="${listDt.STATUS eq 10}">
						<button type="button" class="btn-m04 btn-color03" onclick="confirm(${listDt.AGREM_IDX}, 55, false)">
							<span>승인</span>
						</button>
						<button type="button" class="btn-m04 btn-color04" onclick="confirm(${listDt.AGREM_IDX}, 40, false)">반려</button>
					</c:when>
					<c:when test="${listDt.STATUS eq 55}">
						<button type="button" class="btn-m04 btn-color02" onclick="confirm(${listDt.AGREM_IDX}, 40, false)">협약취소</button>
					</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
				</td>
				</c:if>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="paging-navigation-wrapper">
		<!-- 페이징 네비게이션 -->
		<p class="paging-navigation">
			<a href="#" data-page="1" class="btn-first btn-page">맨 처음 페이지로 이동</a>
			<a href="#" data-page="${curPage - 1 eq 0 ? 1 : curPage-1}" class="btn-preview btn-page">이전 페이지로 이동</a>
			<c:forEach var="i" begin="${start }" end="${end >= list[0].TOTAL_PAGE ? list[0].TOTAL_PAGE : end }">
				<c:choose>
					<c:when test="${i eq curPage }">
						<strong>${i }</strong>
					</c:when>
					<c:otherwise>
						<a href="#" data-page="${i}" class="btn-page">${i }</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<a href="#" data-page="${curPage + 1 eq list[0].TOTAL_PAGE+1 ? list[0].TOTAL_PAGE : curPage+1}" class="btn-next btn-page">다음 페이지로 이동</a>
			<a href="#" data-page="${list[0].TOTAL_PAGE}" class="btn-last btn-page">맨 마지막 페이지로 이동</a>
		</p>
		<!-- //페이징 네비게이션 -->
	</div>
</div>
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action01">
	<h2>협약 신청</h2>
	<div class="modal-area">
		<div class="contents-box pl0" style="display: none;">
			<form id="formCenter">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield00"> 센터명 </label>
							</dt>
							<dd>
								<input type="hidden" name="mId" value="52">
								<input type="text" id="modal-textfield00" name="name">
							</dd>
						</dl>
					</div>
				</div>
			</form>
			<div class="btns-area">
				<button type="button" class="btn-m02 btn-color02 round01" onclick="center_search()">검색</button>
			</div>
		</div>
		<div class="contents-box pl0">
			<p class="total mb05"> 총 <strong id="tr-search-number">0</strong> 건 ( 1 / 1 페이지 )</p>
			<div class="table-type02 horizontal-scroll">
				<table class="width-type03">
					<caption>훈련사업 및 과정표 : 훈련과정(과정명 및 제공기관, 적용업종), 시간, 선택에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 20%" />
						<col style="width: 50%" />
						<col style="width: 30%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">No</th>
							<th scope="col">업체명</th>
							<th scope="col">선택</th>
						</tr>
					</thead>
					<tbody id="trbody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<button type="button" class="btn-modal-close">모달 창 닫기</button>
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
	const children_num = $('colgroup#main-colgroup').children().length;
	$('td.bllist').attr('colspan', children_num);
	console.log(children_num)
	$('tbody#main tr:has(td a#link)').each(function() {
	    let obj = {};
	    $(this).find('td').each(function() {
	    	let key = $(this).data('key');
	    	let value = $(this).data('value');
	    	obj[key] = value;
	    });
	    let idx = $(this).find('input#agrem-idx').val();
	    obj['agrem_idx']=idx;
	    console.log(obj)
	    console.log(JSON.stringify(obj));
	    let result = window.btoa(unescape(encodeURIComponent(JSON.stringify(obj))));
	    let currentUrl = window.location.href;
	    let url = new URL(currentUrl);
	    url.searchParams.forEach((value,key) => {
	    	if(key != 'mId') {
	    		url.searchParams.delete(key);
	    	}
	    })
	    let newUrl = url.toString().replace('list.do', 'register.do');
	    let separator = currentUrl.includes('?') ? '&' : '?';
	    if(currentUrl.includes('?mId=52&a=')) {
	    	
	    }
	    let href = newUrl + separator + 'a=' + result;
	    $(this).find('a#link').attr('href', href);
	})
	$('input#toggler').change(function() {
    	let myValue = $(this).is(':checked');
    	$(':checkbox:not(:disabled).item').each(function() {
        	$(this).prop('checked', myValue)
    	})
	})
	let currentUrl = window.location.href;
		let url = new URL(currentUrl);
		url.searchParams.forEach((value,key) => {
	    if(key != 'mId') {
			url.searchParams.delete(key);
		}
	})
	let newUrl = url.toString().replace('list.do', 'register.do');
	$('a#register-link').attr('href', newUrl)
}

$(function() {
    $(".open-modal01").on("click", function(e) {
        $(".mask").fadeIn(150, function() {
            $("#modal-action01").show();
            center_search();
        });
    });
    $("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
});
async function center_search() {
	showLoading();
	let response = await fetch(`${contextPath}/${siteId}/agreement/getCenters.do?mId=52`, {
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
					<dt><strong>\${e.PRVTCNTR_NAME}</strong></dt>
				</dl>
			</td>
			<td><button onclick="request(\${e.PRVTCNTR_NO})"><span class="point-color01">협약 요청</span></button></td>
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
	}).then(e => {
		console.log(e)
		$("#modal-action01 .btn-modal-close").click();
		window.location.reload();
	})
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
function download(idx) {
	 let currentUrl = window.location.href;
	 let url = new URL(currentUrl);
	 url.searchParams.forEach((value,key) => {
	    	if(key != 'mId') {
	    		url.searchParams.delete(key);
	    	}
	    })
	let newUrl = url.toString().replace('list.do', 'download.do');
	let href = newUrl + "&agrem_idx=" + idx;
	console.log(href)
	let aelem = document.createElement('a');
	aelem.href = href;
	aelem.setAttribute('download', 'true')
	document.body.appendChild(aelem);
	aelem.click();
	document.body.removeChild(aelem);
}
async function confirm(idx, status, iscallback) {
	const item = {
			"agremIdx": idx,
			"status": status
	}
	let form_data = new FormData();
	for(let key in item) {
		form_data.append(key, item[key])
	}
	
	let result = await fetch(`${contextPath}/${siteId}/agreement/updateStatus.do?mId=52`,{
		method: 'POST',
		body: form_data
	})
	if(!iscallback) window.location.reload(true);
}
async function seriesConfirm(status) {
	let indexes = [];
	$('input:checkbox.item:checked').each(function() {
		
		let value_ = $(this).val();
		console.log(value_)
		if(value_ != null && value_.length > 0) indexes.push(parseInt($(this).val()));
	})
	const promises = indexes.map(async (e) => {
		await confirm(e, status,true);
	})
	await Promise.all(promises);
	window.location.reload(true);
}

$(function() {
	$('span#item').text($("select#modal-textfield01 option:selected").val());
	$("select#modal-textfield01").on("change", function() {
		console.log($(this).val());
		$('span#item').text($(this).val());
	})
	$('button#cancel').on("click", function() {
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
	})
	$('button#submit').on('click', function() {
		const contextPath = '${contextPath}'
		const text = '{{'+$('select#modal-textfield01 option:selected').val() + '}}' + $('textarea#modal-textfield02').val() 
		$.ajax({
			url: `\${contextPath}/web/taskhub/req.do?mId=48`,
			type: 'POST',
			contentType: 'application/x-www-form-urlencoded',
			data: {cn: text},
			success: function(response) {
				alert('지원요청 등록 완료')
			},
			error: function(xhr, status, error) {
				console.error('Error occurred:', error);
			}
		});
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
		cleanup();
	})
	$('.btn-list').click(function(e) {
		e.preventDefault();
		let dest = $(this).data('dest');
		let form = $('form#mover')
		switch(dest) {
		case 'bsc':
			form.attr('action', '${contextPath}/dct/diagnosis/view.do?mId=36')
			$('form#mover input#idx').val($(this).data('bsc'))
			form.submit();
			break;
		case 'rslt':
			form.attr('action', '${contextPath}/dct/bsisCnsl/qustnr.do?mId=37')
			$('form#mover input#bsc').val($(this).data('bsc'))
			$('form#mover input#rslt').val($(this).data('rslt'))
			form.submit();
			break;
		case 'bsis':
			form.attr('action', '${contextPath}/dct/bsisCnsl/cnslt.do?mId=37')
			$('form#mover input#bsc').val($(this).data('bsc'))
			$('form#mover input#rslt').val($(this).data('rslt'))
			form.submit();
			break;
		}
	})
	$('.btn-page').click(function(e) {
		e.preventDefault();
		$('form#main input#page').val($(this).data('page'))
		$('form#main').submit();
	})
	$('.btn-hashtag').click(function(e) {
		e.preventDefault();
		$('form#main input#hashtag').val($(this).text().replace('#', ''))
		$('form#main').submit();
	})
	$('input#modal-textfield00').keydown(function() {
		if(event.keyCode == 13 || event.code == "Enter") {
			event.preventDefault();
			center_search()
		}
	})
 });
 
 function cleanup() {
	 $('textarea#modal-textfield02').val('');
 }
 
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>

var bpl_no, rslt_idx, bsis_idx, previousFtfYn;
var current_slot = 0;
var recommend_list = [];
var ncscodes_list = [];
var select_boxes = [];
var recommends = [];
var tp_list = [];
var biz_list = [];
var context = location.pathname.split('/')[1];
var contextPath = context == 'dct' ? '' : `/${context}`;
var noTpList = [2, 3, 5, 15]; // 능력개발클리닉, 외부교육기관, 심층진단, 일학습병행은 훈련추천과정 없음

window.onload = () => {
	previousFtfYn = document.getElementById('ftf_yn').value;
	bpl_no = document.querySelector('input#bpl_no').value;
	rslt_idx = document.querySelector('input#rslt_idx').value;
	bsis_idx = document.querySelector('input#bsis_idx').value;
	render_recommend_list();
	render_trends();
	ncscodes();
	hideLoading();
	let span_update_max = document.querySelector('span#update-max')
	span_update_max.addEventListener('click', updateMax);
	
	const doctorTelno = document.getElementById('doctor_telno');
	if(doctorTelno) formatPhoneNumber(doctorTelno);
	
	// 설문조사
	const qustnrBtn = document.getElementById('btn-qustnr');
	qustnrBtn.addEventListener('click', function() {
		const rslt = qustnrBtn.getAttribute('data-rslt');
		const bsc = qustnrBtn.getAttribute('data-bsc');
		const form = document.getElementById('form-box');
		const actionUrl = `${contextPath}/dct/bsisCnsl/qustnr.do?mId=37`;
		form.action = actionUrl;
		form.target = '_blank';
		document.getElementById('rslt').value = rslt;
		document.getElementById('bsc').value = bsc;
		form.submit();
	});
	
	const status = document.getElementById('bsis_status').value;
	if(status === '1') {
		const parent = document.querySelector('#consult');
		const elements = parent.querySelectorAll('input, textarea');
		for(let i=0;i < elements.length; i++) {
			elements[i].disabled = true;
		}
		const buttons = document.getElementsByClassName('open-modal01');
		for(let i=0;i < buttons.length; i++) {
			buttons[i].style.display = 'none';
		}
		$('span#update-max').hide();
		const buttons2 = document.querySelectorAll('button.open-modal02');
		buttons2.forEach(e => e.style.display='none')
	}
	
	const detailButtons = document.querySelectorAll('.btn-detail');
	detailButtons.forEach(detailBtn => {
		detailBtn.addEventListener('click', function() {
			const idx = detailBtn.getAttribute('data-idx');
			const bizName = document.getElementById(`name${idx}`).textContent;
			const biz = biz_list.find((element) => element.NAME === bizName);
			if(biz) {
				window.open(biz.URL, '_blank');
			} else {
				alert('연결된 사업 상세 페이지가 없습니다.');
			}
		});
	});
}

$(function() {
    $(".open-modal01").on("click", function(e) {
        $(".mask").fadeIn(150, function() {
            $("#modal-action01").show();
        });
        let {id} = e.target;
        current_slot = id;
        adjust_bizlist(e);
        cleanup_tr_list();
        // 추천훈련사업 수정 버튼 누를 때 검색 옵션들 안보이게 하려고 추가한 코드, 리팩토링 대상
        $('#modal-action01 form .one-box:nth-child(2)').hide();
        $('#modal-action01 form .one-box:nth-child(3)').hide();
        $('#modal-action01 form .one-box:nth-child(4)').hide();
        $('#modal-action01 form .one-box:nth-child(5)').hide();
        $('#modal-action01 .contents-box:nth-child(2)').hide();
        $('#modal-action01 .btns-area').hide();
        $('#modal-action01 h2').text('추천훈련사업 변경')
    });

    $("#modal-action01 .btn-modal-close, #modal-action02 .btn-modal-close").on("click", function() {
        $("#modal-action01, #modal-action02").hide();
        $(".mask").fadeOut(150);
        cleanup_tr_list();
        $('#modal-textfield00').prop('disabled', false);
        
        // 원복
        $('#modal-action01 form .one-box:nth-child(2)').show();
        $('#modal-action01 form .one-box:nth-child(3)').show();
        $('#modal-action01 form .one-box:nth-child(4)').show();
        $('#modal-action01 form .one-box:nth-child(5)').show();
        $('#modal-action01 .contents-box:nth-child(2)').show();
        $('#modal-action01 .btns-area').show();
        $('#modal-action01 h2').text('훈련사업 및 과정')
    });
    
    $(".open-modal02").on("click", function(e) {
    	$(".mask").fadeIn(150, function() {
    		const {target} = e
    		$("#modal-action01").show();
    		const id = parseInt(target.id);
    		current_slot = id;
    		adjust_bizlist(e);
            cleanup_tr_list();
    		const prtbiz_idx = recommend_list[id].PRTBIZ_IDX;
    		$(`#modal-textfield00 option[value='${prtbiz_idx}']`).prop('selected', true);
    		$('input#prtbiz').val(prtbiz_idx);
    		$('#modal-textfield00').prop('disabled', true);
    		
    		document.getElementById('tr-search-number').textContent = 0;
    	});
    });
    
    $(".btn-change").on("click", function(e) {
        $(".mask").fadeIn(150, function() {
            $("#modal-action02").show(); 
            
        });
    });
});

function adjust_bizlist(e) {
	let {id} = e.target;
	let biz_name = e.target.parentElement.querySelector(`span#name${id}`);
	let biz_select = select_boxes[0];
	let biz_option = biz_select.querySelectorAll('option');

	if(isEmpty(recommend_list[id])) {
		biz_option[0].selected = true;
		return;
	}
	
	biz_option.forEach(e => {
		if(!isEmpty(recommend_list[id]) && e.value == recommend_list[id].PRTBIZ_IDX) {
			e.selected = true;
		}
	});
	
	if(!Array.from(biz_option).some(option => option.selected)) {
		biz_option[0].selected = true;
	}
}

function render_recommend_list() {
	$.post(`${contextPath}/dct/bsisCnsl/recomBiz.do?mId=37`, {"bsiscnsl_idx": bsis_idx},
			(data) => {
				recommend_list = JSON.parse(data);
				recommend_list = recommend_list
				.filter(e => e.CONSIDER !== null && e.CONSIDER !== undefined)
				.map(e=> ({
					...e,
					CONSIDERATION: e.CONSIDER.replaceAll('\n', '<br/>')
				}));
				rendering_recommends(recommend_list, true);
			});
}

function rendering_recommends(list, initFlag) {
	list.forEach((e,i) => e.RANK = i+1);
	for(let i=0; i<3; i++) {
		let r_ = list[i];
		let name_e = document.querySelector(`span.name#name${i}`);
		let desc_e = document.querySelector(`span.desc#desc${i}`);
		let cons_e = document.querySelector(`span.cons#cons${i}`);
		let sgst_e = document.querySelector(`textarea#sgst${i}`);
		let proc_e = document.querySelector(`textarea#proc${i}`);
		let tps_e = document.querySelector(`ul.train#train${i}`);
		name_e.textContent = r_.RCTR_NAME;
		desc_e.innerHTML = r_.INTRO;
		cons_e.innerHTML = r_.CONSIDERATION;
		sgst_e.value = r_.HRD_SGST ?? '';
		proc_e.value = r_.FUTR_SPRT_PROC ?? '';
		tps_e.innerHTML = '';
		r_.TPS.forEach(e => {
			trainElement('ul.train#train'+i, e.TP_NAME, e.PRTBIZ_IDX, e.TP_IDX);
		});
		const status = document.getElementById('bsis_status').value;
		if(status == 1) {
			const delete_buttons = document.getElementsByClassName('btn-delete');
			for(let i=0; i<delete_buttons.length; i++) {
				delete_buttons[i].style.display = 'none';
			}
		}
		
		const prtbizIdx = r_.PRTBIZ_IDX;
		const addTpButton = document.querySelectorAll('.open-modal02')[i];
		const extEduContents = document.getElementById(`ext-contents-${i}`);
		if(noTpList.includes(prtbizIdx)) {
			addTpButton.style.display = 'none';
			if(prtbizIdx == '3') {
				extEduContents.style.display = 'block';
			} else {
				extEduContents.style.display = 'none';
			}
		} else {
			addTpButton.style.display = 'block';
			extEduContents.style.display = 'none';
		}
	}
}
function trainElement(selector, txt, prtbiz, tp) {
	let ule = document.querySelector(selector)
	let lie = document.createElement('course-item');
	lie.setAttribute('name', txt);
	lie.setAttribute('prtbiz', prtbiz);
	lie.setAttribute('tp', tp);
	ule.appendChild(lie)

	let input = document.createElement('input')
	input.setAttribute('type', 'hidden')
	input.value = txt
	input.name = 'tps'+ule.childElementCount
}

function ncscodes() {
	$.post(`${contextPath}/dct/bsisCnsl/ncscodes.do?mId=37`, null, (data)=> {
		let res = JSON.parse(data);
		biz_list = res.biz;
		render_ncs_init();
		ncscodes_list = res.codes
		let e_ = select_boxes[0]
		let prisup_type = document.querySelector('td#prisup_code')?.innerText ?? '우선지원대상기업';
		let prisup_code = prisup_type == '우선지원대상기업' ? 2 : 1;
		for(var i=0; i<select_boxes.length; i++) {
			let e_ = select_boxes[i];
			if(i == 0) {
				res.biz.forEach((e) => {
					let o_ = document.createElement('option')
					o_.value = e.PRTBIZ_IDX;
					o_.textContent = e.NAME;
					e_.appendChild(o_);
				})
				
				e_.addEventListener('change', e => {
					let name = e.target.selectedOptions[0].textContent;
					let todo_index = recommend_list.indexOf(recommend_list.filter(e => e.RCTR_NAME == name)[0]);
					[recommend_list[current_slot], recommend_list[todo_index]] = [recommend_list[todo_index], recommend_list[current_slot]]
					rendering_recommends(recommend_list, false);
					$("#modal-action01 .btn-modal-close").click()
				})
			} else {
				ncscodes_list.forEach(e => {
					if(e.CLASS_LEVEL == i) {
						let o_ = document.createElement('option')
						o_.value = e.CLASS_IDX
						o_.textContent = e.CLASS_NAME + '(' + e.CLASS_IDX + ')'
						e_.appendChild(o_)
					}
				})
				e_.addEventListener('change', e => {
	    			e.stopPropagation()
	    			let {target} = e;
	    			let code_ = target.selectedOptions[0].value;
	    			let j = target.id[target.id.length-1]
	    			for(var n=3; n>j; n--) {
	    				select_boxes[n].innerHTML = ''
	    				let empty_o = document.createElement('option')
	    				empty_o.textContent = '선택'
	    				empty_o.value = ''
	    				select_boxes[n].appendChild(empty_o);
	    				let l_ = ncscodes_list.filter(k => k.CLASS_LEVEL == n && k.CLASS_IDX.startsWith(code_));
	    				l_.forEach(m => {
	    					let o_ = document.createElement('option')
	            			o_.value = m.CLASS_IDX
	            			o_.textContent = m.CLASS_NAME + '(' + m.CLASS_IDX + ')'
	            			select_boxes[n].appendChild(o_)	
	    				})	
	    			}
	    		})
			}
		}
	})
}

function unit(data) {
	const max_ = Math.max(...data);
	if(max_ < 1e3) return '';
	else if(max_ < 1e6) return 'K';
	else if(max_ < 1e8) return 'M';
	else if(max_ < 1e12) return 'B';
	else return 'T';
}

function formatted(n, unit) {
	switch(unit) {
	case 'K':
		return (n/1e3).toFixed(1);
	case 'M':
		return (n/1e6).toFixed(1);
	case 'B':
		return (n/1e8).toFixed(1);
	case 'T':
		return (n/1e12).toFixed(1);
	default:
		return n;
	}
}

function kunit(unit) {
	switch(unit) {
	case 'K':
		return '1천 ';
	case 'M':
		return '1백만 ';
	case 'B':
		return '1억 ';
	case 'T':
		return '1조 ';
	default:
		return '';
	}
	
}

function kunit2(unit) {
	switch(unit) {
	case 'K':
		return '천';
	case 'M':
		return '백만';
	case 'B':
		return '억';
	case 'T':
		return '조';
	default:
		return '';
	}
	
}

function render_trends() {
	$.post(`${contextPath}/dct/bsisCnsl/trends.do?mId=37`, {"BPL_NO": bpl_no},
			(data) => {
				let jsonObj = JSON.parse(data).reverse();
				let years = [];
				let total_nmprs = [];
				let nmcorps = [];
				let spt_pays = [];
				for(let i=0; i<jsonObj.length; i++) {
					years.push(jsonObj[i].YEAR)
					total_nmprs.push(jsonObj[i].TOTAL_NMPR)
					nmcorps.push(jsonObj[i].NMCORP)
					spt_pays.push(jsonObj[i].SPT_PAY)
				}
				
				unit_tot = unit(total_nmprs);
				unit_corp = unit(nmcorps);
				unit_pays = unit(spt_pays);
				total_nmprs = total_nmprs.map(e=>formatted(e, unit_tot))
				nmcorps = nmcorps.map(e=>formatted(e, unit_corp))
				spt_pays = spt_pays.map(e => formatted(e, unit_pays))
				name_tot = `참여 인원 (${kunit(unit_tot)}명)`;
				name_corp = `참여 기업 (${kunit(unit_corp)}개)`;
				name_pays = `지원금 (${kunit(unit_pays)}원)`;
				
				let execOption = {
						legend: {selectedMode: false},
						xAxis: [
							{
								type: 'category',
								data: years
							}
						],
						yAxis: [
							{
								type: 'value',
								name: name_corp,
								position: 'left',
								axisLabel: {
									formatter: '{value}' + kunit2(unit_corp)
								}
							},
							{
								type: 'value',
								name: name_tot,
								position: 'right',
								axisLabel: {
									formatter: '{value}' + kunit2(unit_tot)
								}
							}
						],
						series: [
							{
								name: '참여기업',
								type: 'bar',
								yAxisIndex: 0,
								data: nmcorps,
								color: '#191D88',
								barWidth: '30%'
							},
							{
								name: '참여인원',
								type: 'line',
								yAxisIndex: 1,
								data: total_nmprs,
								symbolSize: 8,
								color: '#FFC436',
								lineStyle: {
									width: 4,
									color: '#FFC436'
								}
							}
						]
				}
				let sprtOption = {
						legend: {
							data: ['지원금'],
							selectedMode: false
						},
						xAxis: [
							{
								type: 'category',
								data: years
							}
						],
						yAxis: [
							{
								type: 'value',
								name: name_pays,
								position: 'left',
								axisLabel: {
									formatter: '{value}' + kunit2(unit_pays)
								}
							}
						],
						series: [
							{
								name: '지원금',
								type: 'bar',
								yAxisIndex: 0,
								data: spt_pays,
								color: '#198E7F',
								barWidth: '30%'
							},
							{
								name: '추세선',
								type: 'line',
								yAxisIndex: 0,
								data: plotLogTrendLine([1,2,3], spt_pays),
								color: '#E74C3C',
								lineStyle: {
									normal: {
										color: '#E74C3C',
										width: 4,
										type: 'dashed'
									}
								}
							}
						]
				}
				let execChart = echarts.init(document.getElementById('exec'));
				let sprtChart = echarts.init(document.getElementById('sprt'));
				execChart.setOption(execOption);
				sprtChart.setOption(sprtOption);
			}
	)
	
}
function render_ncs_init(level) {
	select_boxes = [];
	let select_elements = document.querySelectorAll('select.select-type02')
	select_elements.forEach(e => select_boxes.push(e))
}
async function tr_search() {
	showLoading();
	let response = await fetch(`${contextPath}/dct/bsisCnsl/trsearch.do?mId=37`, {
		method: 'POST',
		body: new FormData(formNCS)
	});
	let result = await response.json();
	render_tp_board(result)
	tp_list = result;
	hideLoading();
}
function render_tp_board(result) {
	let tbody = document.querySelector('tbody#trbody');
	
	while(tbody.firstChild) {
		tbody.removeChild(tbody.firstChild);
	}
	
	if(result.length < 1) {
		let row = document.createElement('tr');
		row.innerHTML = `
			<tr>
				<td colspan="4">검색된 결과가 없습니다.</td>
			</tr>`;
		tbody.appendChild(row);
		return;
	}
	
	result.forEach(e => {
		let row_top = document.createElement('tr');
		let row_bot = document.createElement('tr');
		row_top.innerHTML = `<tr>
			<th scope="row">과정</th>
			<td class="left">
				<dl>
					<dt><strong class="point-color01">${e.TP_NAME}</strong></dt>
				</dl>
			</td>
			<td rowspan="2">${e.TR_DAYCNT}</td>
			<td rowspan="2"><input type="checkbox" value="${e.TP_IDX}" class="checkbox-type01 course"></td>
		</tr>`;
		row_bot.innerHTML = `<tr>
			<th scope="row">적용 업종</th>
			<td class="left">${e.APPLY_INDUTY}</td>
		</tr>`;
    	tbody.appendChild(row_top);
    	tbody.appendChild(row_bot);
    	
	})
	let number_e = document.querySelector('strong#tr-search-number')
	number_e.textContent = result.length;
}
function setTrainList() {
	let chk_list = document.querySelectorAll('input.course:checked')
	let items = [];
	chk_list.forEach( e=> {	
		let value = e.value;
		
		// tp_list(검색한 훈련과정)에서 선택한 훈련 과정 찾기
		let tp_ = tp_list.filter(k => k.TP_IDX == parseInt(e.value))[0];
		let recom_ = recommend_list.find(e => e.PRTBIZ_IDX == tp_.PRTBIZ_IDX);
		if(recom_ != null && recom_.TPS.find(e => e.PRTBIZ_IDX == tp_.PRTBIZ_IDX && e.TP_IDX == tp_.TP_IDX) == null) {
			recom_.TPS.push(tp_)
			trainElement('ul.train#train'+current_slot, tp_.TP_NAME, tp_.PRTBIZ_IDX, tp_.TP_IDX);
		}
	})
	$('#modal-action01 .btn-modal-close').click();
}
function closeModal() {
	$('#modal-action01 .btn-modal-close').click();
}
function cleanup_tr_list() {
	let tbody = document.querySelector('tbody#trbody');
	tbody.innerHTML = `
		<tr>
			<td colspan="4">검색해주세요.</td>
		</tr>
	`;
}


function computeSums(x, y) {
	let n = x.length;
	let sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
	for(let i=0; i<n; i++) {
		let logY = Math.log(y[i]);
		sumX += x[i];
		sumY += logY;
		sumXY += x[i] * logY;
		sumXX += x[i] * x[i];
	}
	return { sumX, sumY, sumXY, sumXX, n};
}

function computeTrendlineCoefficients(x, y) {
	let { sumX, sumY, sumXY, sumXX, n } = computeSums(x, y);
	let slope = (n * sumXY - sumX * sumY) / (n * sumXX -sumX *sumX);
	let intercept = (sumY - slope * sumX) / n;
	return { slope, intercept };
}

function plotLogTrendLine(x, y) {
	let { slope, intercept } = computeTrendlineCoefficients(x, y);
	return x.map(e => Math.exp(intercept + slope*e))
}


class CourseItem extends HTMLElement {
	connectedCallback() {
		let name = this.getAttribute('name');
		let prtbiz = this.getAttribute('prtbiz');
		let tp = this.getAttribute('tp');
		this.innerHTML = `
		<li>
			<a href="${contextPath}/dct/bsisCnsl/traing.do?mId=37&tpIdx=${tp}&prtbizIdx=${prtbiz}" class="point-color01" target="_blank">
				<span> - ${name}</span>
				<img src="${contextPath}/dct/images/icon/icon_search04.png" class="btn-linked" />
			</a>
			<button type="button" class="btn-delete" style="background-image: url(../images/btn/btn_delete01.png); width:12px; height:12px; background-size: 12px 12px;" > </button>
		</li>
		`;
		this.querySelector('button').addEventListener('click', (e) => {
			let {target} = e;
			let recom_ = recommend_list.find(e => e.PRTBIZ_IDX == prtbiz)
			recom_.TPS = recom_.TPS.filter(e => !(e.PRTBIZ_IDX == prtbiz && e.TP_IDX == tp))
			let parent_ = target.parentElement
			parent_.remove();
		} )
	}
	static get observedAttributes() {
		return ['name']
	}
	attributeChangedCallback() {
		let name = this.getAttribute('name');
		let prtbiz = this.getAttribute('prtbiz');
		let tp = this.getAttribute('tp');
		this.innerHTML = `
		<li>
			<a href="${contextPath}/dct/bsisCnsl/traing.do?mId=37&tpIdx=${tp}&prtbizIdx=${prtbiz}" class="point-color01" target="_blank">
				<span> - ${name}</span>
				<img src="${contextPath}/dct/images/icon/icon_search04.png" class="btn-linked" />
			</a>
			<button type="button" class="btn-delete" style="background-image: url(../images/btn/btn_delete01.png)" />
		</li>`;
	}
}
customElements.define('course-item', CourseItem);

async function saveConsult(temp) {
	
	if(temp === 'done') {
		const essentialIds = ['sgst0', 'sgst1', 'sgst2', 'proc0', 'proc1', 'proc2'];
		for(const id of essentialIds) {
			const element = document.getElementById(id);
			if(element && element.value.trim() === '') {
				const dataName = element.getAttribute('data-name');
				alert(`"${dataName}"의 값은 필수입니다.`);
				element.focus();
				return;
			}
		}
		
		const ftfYn = document.getElementById('ftf_yn').value;
		if(isEmpty(ftfYn)) {
			alert('보고서 전달방식을 선택해주세요.');
			return false;
		}
	}
	
	showLoading();
	let form = document.querySelector('form#consult')
	let formData = new FormData(form)
	// recommend 정보부터 담자
	let value = Object.fromEntries(formData.entries());
	
	value.recommends = recommend_list;
	value.recommends.forEach((e,i) => {
		e.BIZ_IDX=i;
		e.HRD_SGST = document.querySelector(`textarea#sgst${i}`) ? document.querySelector(`textarea#sgst${i}`).value : recommend_list[i].HRD_SGST;
		e.FUTR_SPRT_PROC = document.querySelector(`textarea#proc${i}`) ? document.querySelector(`textarea#proc${i}`).value : recommend_list[i].FUTR_SPRT_PROC;
	})
	value.recommends = value.recommends.slice(0,3);
	value.temp = temp;
	// fetch로 전송하자
	let response = await fetch(`${contextPath}/dct/bsisCnsl/saveConsult.do?mId=37`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(value)
	})
	const result = await response;
	
	await fetch(`${contextPath}/dct/bsisCnsl/updateMax.do?mId=37`, {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(value)})
	
	if(result.ok) {
		if(temp === 'temp') alert('기업HRD이음컨설팅이 임시 저장 되었습니다.')
		else alert('기업HRD이음컨설팅이 완료 되었습니다.')
		location.reload();
	} else {
		throw new Error('기업HRD이음컨설팅 저장 중 에러가 발생했습니다.');
		alert('기업HRD이음컨설팅 저장 중 에러가 발생했습니다.');
	}
	hideLoading();
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

async function updateMax(e) {
	let form_e = document.querySelector('form#consult');
	let formData = new FormData(form_e);
	let value = Object.fromEntries(formData.entries());
	let response = await fetch(`${contextPath}/dct/bsisCnsl/updateMax.do?mId=37`, {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(value)})
	let result = await response;
	alert('한도 금액 변경 성공')
}

function addComma(inputElement, index) {
	let numbersOnly = inputElement.value.replace(/[^0-9]/g,'');
	let formatted = parseFloat(numbersOnly).toLocaleString('en-US');
	if(formatted == 'NaN') {
		formatted = 0;
	}
	inputElement.value = formatted;
	let tot_pay_e = document.querySelector(`span#tot-pay${index}`);
	let percent_e = document.querySelector(`span#percent${index}`);
	let totPay = tot_pay_e.textContent.replace(/[^0-9]/g, '');
	let percent = numbersOnly == 0 ? '0' : (parseFloat(totPay)/parseFloat(numbersOnly) * 100).toFixed(1)
	document.getElementById(`actualNumber${index}`).value = numbersOnly;
	percent_e.textContent = percent;
}

var doctor_list = []
async function doctorSearch() {
	let form_e = document.querySelector('form#doctor-search');
	let formData = new FormData(form_e);
	let value = Object.fromEntries(formData.entries());
	let empty_search = (value.doctorName == null || value.doctorName == '') && (value.insttName == null || value.insttName == '');
	
	showLoading();
	
	if(empty_search) {
		alert('이름이나 소속기관 중 하나는 입력해주세요.')
		hideLoading();
		return
	}
	let response = await fetch(`${contextPath}/dct/bsisCnsl/docsearch.do?mId=37`, {
		method: 'POST',
		body: new FormData(form_e)
	});
	
	let result = await response.json();
	
	hideLoading();
	
	doctor_list = result
	render_doc_board(result)
}

function render_doc_board(result) {
	let tbody = document.querySelector('tbody#docbody');
	tbody.innerHTML = '';
	document.getElementById('doctor-cnt').textContent = result.length;
	result.forEach((e,i) => {
		let row = document.createElement('tr');
		row.innerHTML = `<th scope="row">주치의</th>
		<td class="left" colspan="2">
			<dl>
				<dt><strong class="point-color01">${e.DOCTOR_NAME}</strong></dt>
				<dd>${e.INSTT_NAME} - ${isEmpty(e.DOCTOR_DEPT_NAME) ? '없음' : e.DOCTOR_DEPT_NAME}</dd>
			</dl>
		</td>
		<td>
			<button type="button" class="btn-m02 btn-color03" onclick="setDoctor(${i})">변경</button>
		</td>`;
    	tbody.appendChild(row);
	});
	
	if(result.length < 1) {
		let row = document.createElement('tr');
		row.innerHTML = `<td colspan="4">검색결과가 존재하지 않습니다.<br/>핵심주치의 여부를 확인해주세요.</td>`;
		tbody.appendChild(row);
	}
}

function setDoctor(index) {
	const {DOCTOR_NAME, DOCTOR_DEPT_NAME, DOCTOR_OFCPS, DOCTOR_EMAIL, DOCTOR_TELNO, INSTT_NAME, INSTT_IDX} = doctor_list[index]
	$("input#doctor_name").val(DOCTOR_NAME);
	$("input#doctor_dept_name").val(DOCTOR_DEPT_NAME);
	$("input#doctor_email").val(DOCTOR_EMAIL);
	$("input#doctor_telno").val(DOCTOR_TELNO);
	$("input#doctor_ofcps").val(DOCTOR_OFCPS);
	$("input#instt_name").val(INSTT_NAME);
	$("input#instt_idx").val(INSTT_IDX);
	$("#modal-action02 .btn-modal-close").click();
}


function saveFtfYn() {
	const selectBox = document.getElementById('ftf_yn');
	const selectedOption = selectBox.options[selectBox.selectedIndex];
	const selectedText = selectedOption.textContent;
	const selectedValue = selectedOption.value;
	
	if(previousFtfYn !== selectedValue) {
		let msg = `보고서 전달방식을 "${selectedText}"으로 변경하시겠습니까?`;
		if(confirm(msg)) {
			showLoading();
			
			const bsiscnslIdx = document.getElementById('bsis_idx').value;
			const formData = new FormData();
			formData.append('ftfYn', selectedValue);
			formData.append('bsiscnslIdx', bsiscnslIdx);
			fetch(`${contextPath}/dct/bsisCnsl/change.do?mId=37`, {
				method: 'POST',
				body: formData
			}).then((response) => {
				if(!response.ok){
					alert(`서버 응답이 실패했습니다. 다시 시도해주세요.`);
					throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
				}             
				return response.json();
			})
			.then((data) => {
				if(data.result === 'success') {
					alert('보고서 전달 방식이 수정되었습니다.');
				} else {
					alert('보고서 전달 방식 수정에 실패하였습니다. 다시 시도해주세요.');
				}
				window.location.reload();
			});
		} else {
			selectBox.value = previousFtfYn;
		}
	}
}

function callAiRecommend() {
	const msg = 'AI가 추천하는 훈련사업으로 불러오시겠습니까?';
	if(confirm(msg)) {
		showLoading();
		const formData = new FormData();
		formData.append('bplNo', bpl_no);
		formData.append('rsltIdx', rslt_idx);
		fetch(`${contextPath}/dct/bsisCnsl/callAiRecommend.do?mId=37`, {
			method: 'POST',
			body: formData
		}).then((response) => {
			if(!response.ok){
				hideLoading();
				alert(`서버 응답이 실패했습니다. 새로고침 후 다시 시도해주세요.`);
				throw new Error(`HTTP erorr! Status : ${response.status} - ${response.statusText}`);
			}             
			return response.json();
		})
		.then((data) => {
			if(data.result === 'fail') {
				hideLoading();
				alert('AI추천 결과 조회에 실패하였습니다. 관리자에게 문의하세요.');
				
			} else if(data.result === 'success' && data.recommends.length < 1){
				hideLoading();
				alert('AI추천 결과가 없습니다.');
				
			} else if(data.result === 'success' && data.recommends.length > 0) {
				const newRecommends = data.recommends;
				for(let i=0;i < newRecommends.length; i++) {
					const newRecommend = newRecommends[i];
					let targetPrtbizIdx = newRecommend.PRTBIZ_IDX;
					let targetObj = recommend_list.find(item => item.PRTBIZ_IDX == targetPrtbizIdx);
					let changeRankObj = recommend_list.find(item => item.RANK == newRecommend.RANK);
//					let oldRank = targetObj.RANK;
					let oldRank = i+1;
					let newRank = newRecommend.RANK;
					if(targetObj) {
						targetObj.TPS = newRecommend.TPS;
						let todo_index = recommend_list.indexOf(recommend_list.filter(e => e.PRTBIZ_IDX == targetPrtbizIdx)[0]);
						let current_slot = i;
						[recommend_list[current_slot], recommend_list[todo_index]] = [recommend_list[todo_index], recommend_list[current_slot]]
					}
				}
				rendering_recommends(recommend_list, false);
				hideLoading();
				alert('AI추천 결과로 변경되었습니다.');
			} else {
				hideLoading();
				alert('AI추천 결과 조회에 실패하였습니다. 관리자에게 문의하세요.');
			}
		});
	}
}

//기업담당자 연락처 입력시 전화번호 입력 포맷팅
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

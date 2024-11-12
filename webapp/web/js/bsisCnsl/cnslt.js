var bpl_no, rslt_idx, bsis_idx;
var current_slot = 0;
var recommend_list = [];
var ncscodes_list = [];
var select_boxes = [];
var recommends = [];
var biz_list = [];
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;
window.onload = () => {
	bpl_no = document.querySelector('input#bpl_no').value;
	rslt_idx = document.querySelector('input#rslt_idx').value;
	bsis_idx = document.querySelector('input#bsis_idx').value;
	render_recommend_list();
	render_trends();
	ncscodes();
	
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
        $(".mask#modal1").fadeIn(150, function() {
        	adjust_bizlist(e);
            $("#modal-action01").show();
        });
        let {id} = e.target;
        current_slot = id
        cleanup_tr_list();
    });

    $("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
        cleanup_tr_list();
    });
    $(".open-modal10").on("click", function(e) {
        $(".mask#modal2").fadeIn(150, function() {
            $("#modal-action02").show();
        });
    });

    $("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action02").hide();
        $(".mask").fadeOut(150);
    });
});

function adjust_bizlist(e) {
	let {id} = e.target;
	let biz_name = e.target.parentElement.querySelector(`span#name${id}`);
	console.log(id, biz_name);
	let biz_select = select_boxes[0];
	let biz_option = biz_select.querySelectorAll('option')
	// 옵션 hidden 초기화
	biz_option.forEach(e => e.removeAttribute('hidden'))
	// 추천 1, 2, 3 순위에 없는거만 표시
	biz_option.forEach( (e,i) => {
		let name_ = e.text
		for(let j=0; j<3; j++) {
			if(recommend_list[j].RCTR_NAME == name_) {
				e.setAttribute('hidden', 'hidden');
			}
		}
	})
}

function render_recommend_list() {
	$.post(`${contextPath}/web/bsisCnsl/recomBiz.do?mId=56`, {"bsiscnsl_idx": bsis_idx},
			(data) => {
				recommend_list = JSON.parse(data);
				recommend_list = recommend_list.map(e=> ({
					...e,
					CONSIDERATION: e.CONSIDER.replaceAll('\n', '<br/>')
				}));
				rendering_recommends(recommend_list);  
			});
}

function rendering_recommends(list) {
	list.forEach( (e,i) => e.RANK = i+1)
	for(let i=0; i<3; i++) {
		let r_ = list[i]
		let name_e = document.querySelector(`span.name#name${i}`)
		let desc_e = document.querySelector(`span.desc#desc${i}`)
		let cons_e = document.querySelector(`span.cons#cons${i}`)
		let sgst_e = document.querySelector(`span#sgst${i}`)
		let proc_e = document.querySelector(`pre#proc${i}`)
		let tps_e = document.querySelector(`ul.train#train${i}`)
		let trains = []
		r_ = {...r_, trains: trains}
		name_e.textContent = r_.RCTR_NAME
		desc_e.innerHTML = r_.INTRO
		cons_e.innerHTML = r_.CONSIDERATION
		sgst_e.textContent = r_.HRD_SGST ?? ''
		proc_e.textContent = r_.FUTR_SPRT_PROC ?? ''
		tps_e.innerHTML = '';
		r_.TPS.forEach(e => {
			trainElement('ul.train#train'+i, e.TP_NAME, e.PRTBIZ_IDX, e.TP_IDX);
		});
		
		const extEduContents = document.getElementById(`ext-contents-${i}`);
		const prtbizIdx = r_.PRTBIZ_IDX;
		if(prtbizIdx == '3') {
			extEduContents.style.display = 'block';
		} else {
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
function btn_delete_callback(e) {
	let {target} = e
	let parent_ = target.parentElement
	parent_.remove();
}
function ncscodes() {
	$.post(`${contextPath}/web/bsisCnsl/ncscodes.do?mId=56`, null, (data)=> {
		let res = JSON.parse(data);
		biz_list = res.biz;
		render_ncs_init();
		ncscodes_list = res.codes
		let e_ = select_boxes[0]
		
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
					console.log(e.target)
					console.log(e.target.selectedOptions);
					console.log(e.target.selectedOptions[0]);
					console.log('change');
					let name = e.target.selectedOptions[0].textContent
					let todo_index = recommend_list.indexOf(recommend_list.filter(e => e.RCTR_NAME == name)[0])
					if(todo_index != -1) {
						[recommend_list[current_slot], recommend_list[todo_index]] = [recommend_list[todo_index], recommend_list[current_slot]]
						rendering_recommends(recommend_list);
						$("#modal-action01 .btn-modal-close").click()
					}
				})
			} else {
				ncscodes_list.forEach(e => {
					let o_ = document.createElement('option')
					o_.value = e.CLASS_IDX
					o_.textContent = e.CLASS_NAME + '(' + e.CLASS_IDX + ')'
					e_.appendChild(o_)
				})
				e_.addEventListener('change', e => {
	    			e.stopPropagation()
	    			let {target} = e;
	    			let code_ = target.selectedOptions[0].value;
	    			let j = target.id[target.id.length-1]
	    			for(var n=3; n>i; n--) {
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

function render_trends() {
	$.post(`${contextPath}/web/bsisCnsl/trends.do?mId=56`, {"BPL_NO": bpl_no},
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
				total_nmprs = total_nmprs.map(e=>parseInt(e))
				nmcorps = nmcorps.map(e=>parseInt(e))
				spt_pays2 = spt_pays.map(e => parseInt(e/10000000))
				let execOption = {
						animation: false,
						legend: { selectedMode: false },
						xAxis: [
							{
								type: 'category',
								data: years
							}
						],
						yAxis: [
							{
								type: 'value',
								name: '참여 기업',
								position: 'left',
								axisLabel: {
									formatter: `{value}천`
								}
							},
							{
								type: 'value',
								name: '참여 인원',
								position: 'right',
								axisLabel: {
									formatter: '{value}'
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
									color: '#FFC436',
								}
							}
						]
				}
				let sprtOption = {
						animation: false,
						legend: { selectedMode: false },
						xAxis: [
							{
								type: 'category',
								data: years
							}
						],
						yAxis: [
							{
								type: 'value',
								name: '지원금 (10억 원)',
								position: 'left',
								axisLabel: {
									formatter: '{value}천만'
								}
							}
						],
						series: [
							{
								name: '지원금',
								type: 'bar',
								yAxisIndex: 0,
								data: spt_pays2,
								color: '#198E7F',
								barWidth: '30%'
							},
							{
								type: 'line',
								yAxisIndex: 0,
								data: plotLogTrendline([1,2,3], spt_pays2),
								symbolSize: 8,
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
	let select_elements = document.querySelectorAll('select.select-type02.modal1')
	select_elements.forEach(e => select_boxes.push(e))
	console.log(select_boxes);
}
async function tr_search() {
	let response = await fetch(`${contextPath}/web/bsisCnsl/trsearch.do?mId=56`, {
		method: 'POST',
		body: new FormData(formNCS)
	});
	let result = await response.json();
	console.log(result)
	render_tp_board(result)
}
function render_tp_board(result) {
	let tbody = document.querySelector('tbody#trbody');
	result.forEach(e => {
		let row = document.createElement('board-row');
    	row.setAttribute('name', e.NAME);
    	row.setAttribute('crdns', e.CRDNS);
    	row.setAttribute('indust', e.APPLY_INDUST)
    	row.setAttribute('dcnt', e.DCNT);
    	row.setAttribute('idx', e.TP_IDX);
    	tbody.appendChild(row);
	})
}
function setTrainList() {
	let chk_list = document.querySelectorAll('input.course:checked')
	chk_list.forEach( e=> {
		let name = e.parentElement.parentElement.querySelector('strong').textContent;
		let value = e.value;
		let selector = 'ul.train' + current_slot;
		console.log(selector)
		console.log(document.querySelector(selector))
		trainElement('ul.train#train'+current_slot, name);
	})
}
function cleanup_tr_list() {
	/*let tbody = document.querySelector('tbody#trbody');
	tbody.innerHTML = '';*/
}
class CourseItem extends HTMLElement {
	connectedCallback() {
		let name = this.getAttribute('name');
		let tp = this.getAttribute('tp');
		let prtbiz = this.getAttribute('prtbiz');
		this.innerHTML = `
		<a href="${contextPath}/web/bsisCnsl/traing.do?mId=56&tpIdx=${tp}&prtbizIdx=${prtbiz}" class="point-color01" target="_blank" >
			<li>
				<span> - ${name}</span>
				<button type="button" class="btn-delete" onclick="btn_delete_callback()"/>
				<img src="${contextPath}/web/images/icon/icon_search04.png" class="btn-linked" />
			</li>
		</a>
		`;
	}
	static get observedAttributes() {
		return ['name']
	}
	attributeChangedCallback() {
		let name = this.getAttribute('name');
		let tp = this.getAttribute('tp');
		let prtbiz = this.getAttribute('prtbiz');
		this.innerHTML = `
		<a href="${contextPath}/web/bsisCnsl/traing.do?mId=56&tpIdx=${tp}&prtbizIdx=${prtbiz}" class="point-color01" target="_blank">
			<li>
				<span> - ${name}</span>
				<button type="button" class="btn-delete" onclick="btn_delete_callback()"/>
				<img src="${contextPath}/web/images/icon/icon_search04.png" class="btn-linked" />
			</li>
		</a>`;
	}
}
customElements.define('course-item', CourseItem);

class BoardRow extends HTMLElement {
	connectedCallback() {
		let name = this.getAttribute('name')
		let crnds = this.getAttribute('crdns')
		let indust = this.getAttribute('indust')
		let idx = this.getAttribute('idx')
		this.innerHTML = `
		<tr>
			<th scope="row">과정 및 제공기관</th>
			<td class="left">
				<dl>
					<dt>
						<strong class="point-color01">${name}</strong>
					</dt>
					<dd>${crdns}</dd>
				</dl>
			</td>
			<td rowspan="2">${DCNT}16</td>
			<td rowspan="2">
				<input type="checkbox" name="" value="${idx}" class="checkbox-type01 course">
			</td>
		</tr>
		<tr>
			<th scope="row">적용 업종</th>
			<td class="left">${indust}</td>
		</tr>`;
	}
}
customElements.define('board-row', BoardRow);

async function tr_search() {
	let response = await fetch(`${contextPath}/web/bsisCnsl/trsearch.do?mId=56`, {
		method: 'POST',
		body: new FormData(formNCS)
	});
	let result = await response.json();
	console.log(result)
	render_tp_board(result)
}

// 로그 추세선 그리기
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

function plotLogTrendline(x, y) {
	let { slope, intercept } = computeTrendlineCoefficients(x, y);
	console.log(x.map(e => Math.exp(intercept + slope*e)))
	return x.map(e => Math.exp(intercept + slope*e))
}
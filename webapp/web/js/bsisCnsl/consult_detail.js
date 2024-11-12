var bpl_no, rslt_idx, bsis_idx;
var current_slot = 0;
var recommend_list = [];
var ncscodes_list = [];
var select_boxes = [];
var recommends = []

window.onload = () => {
	bpl_no = document.querySelector('input#bpl_no').value;
	rslt_idx = document.querySelector('input#rslt_idx').value;
	bsis_idx = document.querySelector('input#bsiscnsl_idx').value
	render_recommend_list();
	render_trends();
}

$(function() {
    $(".open-modal01").on("click", function(e) {
        $(".mask").fadeIn(150, function() {
//        	adjust_bizlist(e);
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
	biz_option.forEach(e => {
		let name_ = e.text
		for(let n=0; n<3; n++) {
			if(recommend_list[n].NAME == name_) {
				e.setAttribute('hidden', 'hidden');
			}
		}
	})
}

function render_recommend_list() {
	$.post("/recommend/recomBiz.do", {"bsiscnsl_idx": bsis_idx},
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
	for(let i=0; i<3; i++) {
		let r_ = list[i]
		let name_e = document.querySelector(`span.name#name${i}`)
		let desc_e = document.querySelector(`span.desc#desc${i}`)
		let cons_e = document.querySelector(`span.cons#cons${i}`)
		let train_e = document.querySelector(`span.train#train${i}`)
		let proc_e = document.querySelector(`span.proc#proc${i}`)
		let trains = []
		r_ = {...r_, trains: trains}
		name_e.textContent = r_.RCTR_NAME
		desc_e.textContent = r_.INTRO
		cons_e.innerHTML = r_.CONSIDER.replaceAll('\n', '<br/>')
		train_e.innerHTML = r_.HRD_SGST ? r_.HRD_SGST.replaceAll('\n', '<br/>') : ''
		proc_e.innerHTML = r_.FUTR_SPRT_PROC ? r_.FUTR_SPRT_PROC.replaceAll('\n', '<br/>') : ''
	}
}
function trainElement(selector, txt) {
	let lie = document.createElement('course-item');
	lie.setAttribute('name', txt);
	ule.appendChild(lie)

	let input = document.createElement('input')
	input.setAttribute('type', 'hidden')
	input.value = txt
	input.name = 'tps'+ule.childElementCount
	let form_ = document.querySelector('form#save')
	form_.appendChild(input)
}
function btn_delete_callback(e) {
	let {target} = e
	let parent_ = target.parentElement
	parent_.remove();
}

function render_trends() {
	$.post("/recommend/trends.do", {"BPL_NO": bpl_no},
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
								color: '#191D88',
								barWidth: '30%'
							},
							{
								type: 'line',
								yAxisIndex: 0,
								data: plotLogTrendline([1,2,3], spt_pays2),
								symbolSize: 8,
								color: 'green',
								lineStyle: {
									normal: {
										color:'green',
										width: 4,
										type: 'dashed'
									}
								}
							},
						]
				}
				let execChart = echarts.init(document.getElementById('exec'));
				let sprtChart = echarts.init(document.getElementById('sprt'));
				execChart.setOption(execOption);
				sprtChart.setOption(sprtOption);
			}
	)
	
}
async function tr_search() {
	let response = await fetch('/recommend/trsearch.do', {
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
		let name = this.getAttribute('name')
		this.innerHTML = `<li>
			<span> - ${name}</span>
			<button type="button" class="btn-delete" onclick="btn_delete_callback()"/>
		</li>
		`;
	}
	static get observedAttributes() {
		return ['name']
	}
	attributeChangedCallback() {
		let name = this.getAttribute('name')
		this.innerHTML = `<li>
			<span> - ${name}</span>
			<button type="button" class="btn-delete" onclick="btn_delete_callback()"/>
		</li>`;
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

async function saveConsult() {
	let form = document.querySelector('form#consult')
	let formData = new FormData(form)
	// recommend 정보부터 담자
	let value = Object.fromEntries(formData.entries());
	value.recommends = recommend_list.slice(0,3);
	value.recommends.forEach( (e,i) => {
		e.sgst = document.querySelector(`textarea#sgst${i}`).value;
		e.proc = document.querySelector(`textarea#proc${i}`).value;
	})
	// fetch로 전송하자
	let response = await fetch('/recommend/saveConsult.do', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(value)
	})
	let result = await response.json();
	console.log(result)
}
async function tr_search() {
	let response = await fetch('/recommend/trsearch.do', {
		method: 'POST',
		body: new FormData(formNCS)
	});
	let result = await response.json();
	console.log(result)
	render_tp_board(result)
}


//로그 추세선 그리기
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
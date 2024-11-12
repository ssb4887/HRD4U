var json_obj;
var organization;

window.onload = e => {
		text_json = text_json.replaceAll('\n', '\\n')
		text_json = text_json.replaceAll('\r', '\\r')
		json_obj = JSON.parse(text_json)
		let result = init(json_obj);
		render(result);
		// 
		const { checkboxOrgan, targetsName } = json_obj;
		const checked = checkboxOrgan.map(e => e === 'Y' ? true: false)
		const checked_group = organization.filter((_,i) => checked[i]);
		const tbody_target = document.querySelector('tbody#train-target');
		const filtered = Object.keys(json_obj).filter(e => e.startsWith('radioTarget'));
		const radioValues = [];
		filtered.forEach(e => {
			const value = json_obj[e].filter(k => k !== '-')[0]
			radioValues.push(value);
		})
		checked_group.forEach(e => {
			e.team.forEach((t,i) => {
				const name = t.element.innerText;
				const is_checked = targetsName.indexOf(name) === -1 ? '' : 'checked';
				const radioValue_ = radioValues[i];
				// tr 만들기
				let row_ = `
					<td class="target-name">${name}</td>
					<td>
						${radioValue_ === '1' ? '★' : ''}
					</td>
					<td>
						${radioValue_ === '2' ? '★' : ''}
					</td>
					<td>
						${radioValue_ === '3' ? '★' : ''}
					</td>
					<td>
						${radioValue_ === '4' ? '★' : ''}
					</td>
					<td>
						${radioValue_ === '5' ? '★' : ''}
					</td>
					<td>
						${is_checked ? '⩗' : ''}
					</td>`;
				const tr_ = document.createElement('tr');
				tr_.innerHTML = row_;
				tbody_target.appendChild(tr_);
			})
		})
		
		// 훈련실시 배경
		const require_e = document.querySelector('pre#requirement').innerText;
		const text_ = require_e.split('\n').map(item => {
			const pe_ = document.createElement('p')
			pe_.classList.add('word-type02')
			pe_.textContent = item;
			return pe_;
			})
		const background_ = document.querySelector('div#background')
		background_.innerHTML = '';
		text_.forEach(txt => background.appendChild(txt))
		
		// 유사도 검사 결과
		const rate_e = document.querySelector('span#plagiarism-rate')
		const { plagiarismRate, plagiarismRated } = json_obj;
		if( plagiarismRate != null ) {
			rate_e.innerText = `${parseFloat(plagiarismRate[0]).toFixed(1)} %`;
		} else {
			rate_e.innerText = plagiarismRated[0];
		}
		
		// profile 테이블 rowspan 조정
		const rowspaner = document.querySelector('th#rowspaner');
		const init_number = rowspaner.getAttribute('rowspan');
		const feedback = 1;
		const num = parseInt(init_number) + targetsName.length + feedback;
		rowspaner.setAttribute('rowspan', num);
		
		// profile 시간
		const { profileDetailOuter, profileDetailInner, profileFeedbackOuter, profileFeedbackInner } = json_obj;
		const outer_time_ = profileDetailOuter?.reduce((a,b) => parseInt(a) + parseInt(b)) + parseInt(profileFeedbackOuter?.[0]);
		const inner_time_ = profileDetailInner?.reduce((a,b) => parseInt(a) + parseInt(b)) + parseInt(profileFeedbackInner?.[0]);
		const outer_e = document.querySelector('span#total-outer');
		const inner_e = document.querySelector('span#total-inner');
		outer_e.innerText = outer_time_;
		inner_e.innerText = inner_time_;
		
		//과정평가 수행기준 체크
		let eval_filtered = Object.keys(json_obj).filter(e => e.startsWith('targetsEval'));
		let evals = eval_filtered.map(e => ({'id': parseInt(e.split('targetsEval')[1]), 'key': e}))
		evals = evals.sort((a,b) => a.id-b.id);
		evals.forEach(e => {
			const checks = json_obj[e.key];
			console.log(checks);
			const checked_td = parseInt(checks.filter(e => e !== '-')[0])-1;
			const tr_ = document.querySelector(`tr.targets-select#eva-${e.id}`);
			const tds_ = tr_.querySelectorAll('td.eval');
			const target = Array.from(tds_).filter(item => item.id === `eval-${checked_td}`)[0];
			if(target) target.innerText = '✔';
			console.log(target)
		})
	}


	function init(json_obj) {
		// tab#3 나. 조직 및 주요 업무
		// 데이터 받아와서 조직도 자료구조 만들기 (nested array)
		let filtered = Object.keys(json_obj).filter(e => e.startsWith('organGroup'));
		let organs = filtered.map(e => ({'id': parseInt(e.split('_')[1]), 'key': e}))
		organs = organs.sort((a,b) => a.id-b.id)
		let result = []
		organs.forEach(e => {
			if(result[e.id] == null) {
				result.push({'id': e.id, 'items': [], 'checked': json_obj.checkboxOrgan[e.id] === 'Y' ? true : false})
			}
			json_obj[e.key].forEach(item => result[e.id].items.push(item));
		})
		return result
	}
	function render(result) {
		// 조직도 자료구조에서 html DOM 그리기
		organize();
		organization = organization.sort((a,b) => a.id - b.id)
		organization.forEach(e => {
			e.team = e.team.sort((a,b) => a.id - b.id)
		})
		result.forEach( (e,i) => {
			if(organization[i] == null) {
				addGroup(e.checked)
				organize();
			}
			e.items.forEach((item,j) => {
				if(organization[i].team[j] == null) {
					addTeam(i)
					organize();
				}
				organization[i].team[j].element.innerText = item;
			})
		})
		
		// 최초 실행에서 targets 업데이트
		let organ_checks = document.querySelectorAll('.organ-check');
		Array.from(organ_checks).filter(e => e.checked).forEach(elem => {
			let group_id_ = parseInt(elem.id.split('checkbox')[1])
			let is_checked = 
			organization.forEach(item => {
				if(item.id == group_id_) {
					item.team.forEach(t_ => {
						if(t_.element.value !== '') {
							team.push({'group_id': group_id_, 'value': t_.element.value})
							updateTrainingTarget()
							}
					})
				}
			})
		})
	}
	function organize() {
		// DOM으로 부터 organization을 만드는 함수
		let groups_ = document.querySelectorAll('tr.organ-group')
		let groups = Array.from({length: groups_.length}, (_, i) => ({'type': 'group', 'id': i, 'element': groups_[i], 'team': []}))
		groups.forEach(e => {
			let id_ = String(e.id).padStart(2,0)
			let class_name = `organ-group-${id_}-team`;
			let tbody = e.element.parentElement
			let inputs = tbody.querySelectorAll(`span.${class_name}`)
			e.team = Array.from({length:inputs.length}, (_,i) => ({'type': 'team', 'id': i, 'element': inputs[i]}));
			
		})
		organization = groups;
	}
	function addTeam(group_idx, elem) {
		// group-idx에 team을 추가하려면, group의 header의 rowspan을 1추가하고, team input의 tr 바로 뒤에 추가한다
		// 그룹보다 큰 idx를 넣으면 안됨
		if(group_idx > organization.length-1) return;
		let group_id = organization[group_idx].id
		let ths_ = organization[group_idx].element.querySelectorAll('th');
		ths_.forEach(e => {
			let rowspan_ = e.getAttribute('rowspan') != null ? parseInt(e.getAttribute('rowspan')) : 1;
			e.setAttribute('rowspan', rowspan_+1)
		})
		// 추가할 엘리먼트가 없다면 생성
		let idx_ = organization[group_idx].team.length-1
		if(elem == null) {
			let gid_ = String(group_id).padStart(2,0)
			let tid_ = String(idx_+1).padStart(2,0)
			let tr_ = document.createElement('tr')
			let td_ = document.createElement('td')
			let input_ = document.createElement('span')
			let id_ = `organ-group-${gid_}-team-${tid_}`;
			input_.setAttribute('id', id_);
			input_.classList.add(`organ-group-${gid_}-team`)
			td_.appendChild(input_)
			tr_.appendChild(td_)
			elem = tr_;
		}
		organization[group_idx].team[idx_].element.parentElement.parentElement.insertAdjacentElement('afterend', elem);
		organize();
	}
	function addGroup(checked) {
		// 추가할 위치 찾기
		const is_checked = checked;
		let last_group_idx = organization.length-1;
		let teams_ = organization[last_group_idx].team;
		let teams_idx = teams_.length-1;
		let last_team = teams_[teams_idx];
		// group element 생성
		let tr_ = document.createElement('tr')
		let gid_ = String(last_group_idx+1).padStart(2,0);
		let tid_ = '00';
		tr_.classList.add('organ-group')
		tr_.setAttribute('id', `organ-group-${gid_}`)
		tr_.innerHTML = `
			<th scope="row">
				부서 팀 명
			</th>
			<td>
				<span class="organ-group-${gid_}-team" id="organ-group-${gid_}-team-${tid_}" ></span>
			</td>
		`;
		last_team.element.parentElement.parentElement.insertAdjacentElement('afterend', tr_);
		organize();
	}
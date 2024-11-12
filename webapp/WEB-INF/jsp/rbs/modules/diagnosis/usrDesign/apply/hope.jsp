<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<body>

    <!-- CMS 시작 -->
    <div class="contents-area">
		<div class="contents-box pl0">
           <dl class="introduction-wrapper001">
               <dt>
                   <img src="${contextPath}${imgPath}/icon/icon_symbol02.png" alt="" />
                   <strong>
                       희망사업
                   </strong>
               </dt>
               <dd>
                   HRD기초진단(기업HRD이음컨설팅)에서 찾으시는 훈련사업이 없으신 기업에 지원대상 조건을 확인하여 드립니다.<br /> 하단에서 희망 사업을 선택해 주세요!
               </dd>
           </dl>
       </div>

        <form action="" method="">
            <fieldset>
                <legend class="blind">
                    희망사업 검색양식
                </legend>
                <div class="search-wrapper02">
                    <div class="search-area02">
                        <label for="search01">
                            희망사업
                        </label>
                        <select id="programs" name="programs" class="w100">
                            <option value="">선택해주세요</option>
                        </select>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>

    <div class="contents-area">
        <h3 class="title-type01 ml0">
            기업개요
        </h3>
        <div class="table-type03 horizontal-scroll">
            <table class="width-type02">
                <caption>
                    희망기업 - 기업개요 정보표 : 기업명, 사업장 관리번호, 업종코드, 소재지, 고용보험성립일자, 업종, 기업유형, 상시 근로자수
                </caption>
                <colgroup>
                    <col style="width: 15%" />
                    <col style="width: 30%" />
                    <col style="width: 15%" />
                    <col style="width: 20%" />
                    <col style="width: 15%" />
                    <col style="width: 10%" />
                </colgroup>
                <tbody>
                    <tr>
                        <th scope="row">
                            기업명
                        </th>
                        <td class="left">${bsk.CORP_NAME }</td>
                        <th scope="row">
                            사업장 관리번호
                        </th>
                        <td class="left">${bsk.BPL_NO }</td>
                        <th scope="row">
                            업종코드
                        </th>
                        <td>${bsk.INDUTY_CD }</td>
                    </tr>

                    <tr>
                        <th scope="row">
                            소재지
                        </th>
                        <td class="left" colspan="5">
                       		${bsk.CORP_LOCATION }
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">
                            고용보험성립일자
                        </th>
                        <td class="left" id="empins-start-date">
                                <strong>${bsk.EMPINS_START_DATE	 }</strong>
                        </td>
                        <th scope="row">
                            업종
                        </th>
                        <td class="left" colspan="3" id="induty-name">
                                <strong>${bsk.INDUTY_NAME }</strong>
                        </td>
                    </tr>


                    <tr>
                        <th scope="row">
                            기업유형
                        </th>
                        <td class="left" id="scale-cd">
                                <strong>${bsk.SCALE_CD }</strong>
                        </td>
                        <th scope="row">
                            상시 근로자수
                        </th>
                        <td class="left" colspan="3" id="nmfltimer">
                                <strong>${bsk.TOT_WORK_CNT }</strong>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <consideration-display name=""></consideration-display>
        <div class="business-support-information" id="remark" style="display:none">
        	<h4>추가 확인 사항</h4>
        	<ul id="display-list"></ul>
        </div>
    </div>
    <!-- //CMS 끝 -->
    <script>
	    window.onload = () => {
	    	init();
	    }
        var corp = {}
        var programs = []
        scale_name = ['대규모 기업', '우선지원대상 기업'];
        function init() {
        	corp = {
        			induty_code: '${bsk.INDUTY_CD}',
        			induty_name: '${bsk.INDUTY_NAME}',
        			prisup_code: ${bsk.PRI_SUP_CD},
        			nmfltimer: ${bsk.TOT_WORK_CNT}
        	};
        	let sojt = {
        			name: '체계적 현장훈련(S-OJT)',
        			prisup_code: 2,
        			nmfltimer: 0,
        			nmfltimer_text : '제한 없음',
        			induty_code: ${limit.SOJT} == 2 ? [{code: ${limit.CODE}, name:'${limit.NAME}'}] : [],
        			limit: '제외업종, 임금체불사업장 여부, 산재사업장 여부 등',
        			remark: '기업선정 필요'
        	};
        	let bpr = {
        			name: '사업주훈련',
        			prisup_code: 3,
        			nmfltimer: 0,
        			nmfltimer_text : '제한 없음',
        			induty_code: [],
        			limit: null,
        			remark: null
        	};
        	let pdms = {
        			name: '일학습병행',
        			prisup_code: 3,
        			nmfltimer: 20,
        			nmfltimer_text : '20인 이상',
        			induty_code: ${limit.WORKNLEARN} == 2 ? [{code: ${limit.CODE}, name:'${limit.NAME}'}] : [],
        			limit: '임금체불사업장 여부, 산재사업장 여부 등',
        			remark: '학습기업 지정, 약정체결 필요'
        	};
        	let outer = {
        			name: '외부 교육기관 훈련',
        			prisup_code: 3,
        			nmfltimer: 0,
        			nmfltimer_text : '제한 없음',
        			induty_code: ${limit.WORKNLEARN} == 2 ? [{code: ${limit.CODE}, name:'${limit.NAME}'}] : [],
        			limit: null,
        			remark: '단, 일부 훈련의 경우 우선지원대상기업만 참여 가능'
        	}
        	programs.push(bpr)
        	programs.push(sojt)
        	programs.push(pdms)
        	programs.push(outer)
        	
        	let select = document.querySelector('select#programs')
        	select.addEventListener('change', select_callback);
        	programs.forEach(e => {
        		let option_e = document.createElement('option')
        		option_e.value = e.name
        		option_e.textContent = e.name
        		select.appendChild(option_e)
        	})
        }
        
        function select_callback(e) {
        	let {value} = e.target;
        	let display = document.querySelector('consideration-display')
        	
        	let items = [
        		{selector: 'td#empins-start-date', content: '${bsk.EMPINS_START_DATE}'}
        		, {selector: 'td#induty-name', content: '${bsk.INDUTY_NAME}'}
        		, {selector: 'td#scale-cd', content: scale_name[corp.prisup_code-1]}
        		, {selector: 'td#nmfltimer', content: '${bsk.nmfltimer}'}
        	]
			if(value != null && value != '') {
				display.setAttribute('name', value);
        	} else {
        		items.forEach(e => reset(e.selector, e.content))
        		display.setAttribute('name', '')
        	}
        }
        
        var good = []
        var nogood = []
        var remark = []
        function test(name) {
        	let program = programs.filter( e=> e.name == name)[0]
        	good = []
        	nogood = []
        	remark = []
        	limit = []
        	if(program != null) {
        		let limited_code = program.induty_code.filter(e => e.code == corp.induty_code)
            	
            	let insurance_check = true;
            	set_table_item(
            			insurance_check
            			, 'td#empins-start-date'
            			, {good: '고용보험 가입 여부: 가입', nogood: '고용보험 가입 여부 : 미가입'}
            			, '${bsk.EMPINS_START_DATE}'
            	)
            	
            	let scale_check = (corp.prisup_code & program.prisup_code) > 0;
            	set_table_item(
            			scale_check
            			, 'td#scale-cd'
            			, {good: `기업 규모 : \${scale_name[corp.prisup_code-1]}`, nogood: `기업 규모 : \${scale_name[corp.prisup_code-1]}`}
            			, scale_name[corp.prisup_code-1]
            	)
            	
            	let worker_cnt_check = corp.nmfltimer > program.nmfltimer;
            	set_table_item(
            			worker_cnt_check
            			, 'td#nmfltimer'
            			, {good: `상시 근로자수 : \${program.nmfltimer_text}`, nogood: `상시 근로자수 : \${program.nmfltimer_text}`}
            			, corp.nmfltimer
            	)
            	let induty_check = program.induty_code?.filter(e => e.code == corp.induty_code).length > 0;
            	set_table_item(
            			!induty_check
            			, 'td#induty-name'
            			, {good: `제외 업종 : \${limited_code.length > 0 ? limited_code[0].name : '해당 없음' }`, nogood: `제외 업종 : \${limited_code.length > 0 ? limited_code[0].name : '해당 없음' }`}
            			, corp.induty_name
            	)
            	program.limit != null ? remark.push(`제외 사유 : \${program.limit}`) : null;
            	program.remark != null ? remark.push(`\${program.remark}`) : null;
        	}
        }
        function display_items() {
        	let display_list = document.querySelector('#display-list');
        	good.forEach(e => {
        		let item_ = document.createElement('display-item')
        		item_.setAttribute('type', 'good')
        		item_.setAttribute('value', e);
        		let li_ = document.createElement('li')
        		li_.appendChild(item_);
        		display_list.appendChild(li_)
        	})
        	nogood.forEach(e => {
        		let item_ = document.createElement('display-item')
        		item_.setAttribute('type', 'nogood')
        		item_.setAttribute('value', e);
        		let li_ = document.createElement('li')
        		li_.appendChild(item_);
        		display_list.appendChild(li_)
        	})
        	let display = document.querySelector('consideration-display > div')
        	if(remark.length > 0) {
        		let elem = document.querySelector('div.business-support-information#remark')
        		elem.style.display='block';
        		let ule = elem.querySelector('ul#display-list');
        		ule.innerHTML = '';
        		remark.forEach(e => {
	        		let e_ = document.createElement('li')
	        		e_.classList.add('word-type02')
	        		e_.textContent = e
	        		ule.appendChild(e_)
	        	})
        	}
        }
        function set_table_item(truth, selector, display_value, table_value) {
        	let e_ = document.querySelector(selector);
        	e_.innerHTML = ''
        	let item_ = document.createElement('table-item')
        	if(truth) {
        		good.push(display_value.good)
        		item_.setAttribute('type', 'good')
        		item_.setAttribute('value', table_value)
        	} else {
        		nogood.push(display_value.nogood)
        		item_.setAttribute('type', 'nogood')
        		item_.setAttribute('value', table_value)
        	}
        	e_.appendChild(item_)
        }
        function reset(selector, content) {
        	let target_e = document.querySelector(selector)
        	target_e.innerHTML = '';
        	let item_e = document.createElement('strong')
        	item_e.textContent = content
        	target_e.appendChild(item_e)
        }
        
        class ConsiderDisplay extends HTMLElement {
        	connectedCallback() {
        		let name = this.getAttribute('name')
        		this.innerHTML = `
        		<div class="business-support-information">
                    <h4>
                        \${name} 지원 대상 조건 안내
                    </h4>

                    <ul id="display-list"></ul>
                </div>
        		`;
        	}
        	static get observedAttributes() {
        		return ['name']
        	}
        	attributeChangedCallback() {
        		let name = this.getAttribute('name')
        		test(name)
        		this.innerHTML = `
       			<div class="business-support-information">
                    <h4>
                        \${name} 지원 대상 조건 안내
                    </h4>

                    <ul id="display-list"></ul>
                </div>
        		`;
        		display_items()
        	}
        }
        customElements.define('consideration-display', ConsiderDisplay);
        
        class DisplayItem extends HTMLElement {
        	connectedCallback() {
        		let type = this.getAttribute('type')
        		let value = this.getAttribute('value')
        		switch(type) {
        		case 'good':
        			this.innerHTML = `
                        <span class="word-satisfy">
                            <img src="${contextPath}${imgPath}/icon/icon_satisfy.png" alt="" />
                            <strong>\${value}</strong>
                        </span>
        			`;
        			return;
        		default:
        			this.innerHTML = `
                        <span class="word-none-satisfy">
                            <img src="${contextPath}${imgPath}/icon/icon_none_satisfy.png" alt="" />
                            <strong>\${value}</strong>
                        </span>
        			`;
        			return;
        		}
        	}
        }
        customElements.define('display-item', DisplayItem);
        
        class TableItem extends HTMLElement {
        	connectedCallback() {
        		let type = this.getAttribute('type')
        		let value = this.getAttribute('value')
        		switch(type) {
        		case 'good':
        			this.innerHTML = `
                        <span class="word-satisfy">
                            <strong>\${value}</strong>
                            <img src="${contextPath}${imgPath}/icon/icon_satisfy.png" alt="" />
                        </span>
        			`;
        			return;
        		default:
        			this.innerHTML = `
                        <span class="word-none-satisfy">
                            <strong>\${value}</strong>
                            <img src="${contextPath}${imgPath}/icon/icon_none_satisfy.png" alt="" />
                        </span>
        			`;
        			return;
        		}
        	}
        }
        customElements.define('table-item', TableItem);
    </script>
</body>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>
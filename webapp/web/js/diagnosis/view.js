var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;
var siteId = document.querySelector(".siteId").value;

if(siteId != "dct") {
	var mId = 54
}else {
	var mId = 37
}

$(document).ready(function(){
	console.log("siteId : "+siteId);
	var BPL_NO = document.querySelector(".bpl_no").value;
	
	$.post(`${contextPath}/`+siteId+`/diagnosis/trends.do?mId=`+mId, {"BPL_NO": BPL_NO},
			
		(data) => {
			let jsonObj = JSON.parse(data).reverse();
			let years = [];
			let tots = [];
			let corps = [];
			let pays = [];
			for(let i=0; i<jsonObj.length; i++) {
				years.push(jsonObj[i].YEAR)
				tots.push(jsonObj[i].TOTAL_NMPR)
				corps.push(jsonObj[i].NMCORP)
				pays.push(jsonObj[i].SPT_PAY)
			}
			unit_tot = unit(tots);
			unit_corp = unit(corps);
			unit_pays = unit(pays);
			tots = tots.map(e=>formatted(e, unit_tot))
			corps = corps.map(e=>formatted(e, unit_corp))
			pays = pays.map(e => formatted(e, unit_pays))
			name_tot = `참여 인원 (`+kunit(unit_tot)+`명)`;
			name_corp = `참여 기업 (`+kunit(unit_corp)+`개)`;
			name_pays = `지원금 (`+kunit(unit_pays)+`원)`;
			
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
							name: name_corp,
							position: 'left',
							axisLabel: {
								formatter: '{value}'+kunit2(unit_corp)
							}
						},
						{
							type: 'value',
							name: name_tot,
							position: 'right',
							splitLine: {
								show: false,
							},
							axisLabel: {
								formatter: '{value}'+kunit2(unit_tot),
							}
						}
					],
					series: [
						{
							name: '참여기업',
							type: 'bar',
							yAxisIndex: 0,
							data: corps,
							color: '#191D88',
							barWidth: '30%',
						},
						{
							name: '참여인원',
							type: 'line',
							yAxisIndex: 1,
							data: tots,
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
							name: name_pays,
							position: 'left',
							axisLabel: {
								formatter: '{value}'+kunit2(unit_pays)
							}
						}
					],
					series: [
						{
							name: '지원금',
							type: 'bar',
							yAxisIndex: 0,
							data: pays,
							color: '#198E7F',
							barWidth: '30%'
						},
						{
							type: 'line',
							yAxisIndex: 0,
							data: plotLogTrendLine([1,2,3], pays),
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
	
});

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

function gohope() {
	let form_ = document.querySelector("form#hope")
	form_.submit()
}

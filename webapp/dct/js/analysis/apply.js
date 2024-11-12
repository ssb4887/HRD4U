var context = location.pathname.split('/')[1]
var contextPath = context == 'dct' ? '' : `/${context}`;
var parameters = new URLSearchParams(window.location.search);
let mId = parameters.get('mId');
let path = window.location.pathname;

window.onload = function() {
	$("#allchkAnals").click(function(){
	    // 클릭되었으면
	    if($("#allchkAnals").prop("checked")){
	        // input태그의 name이 chk인 태그들을 찾아서 checked옵션을 true로 정의
	        $("input[name=rsltAnalsIdx]").prop("checked",true);
	        // 클릭이 안되있으면
	    }else{
	        // input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
	        $("input[name=rsltAnalsIdx]").prop("checked",false);
	    }
	});
}

if(path != "/dct/analysis/view.do" && path != "/dct/analysis/list.do") {
	document.getElementById('btn-save').addEventListener('click', function() {
		saveAnalysisData('0');
	});

	// 신청하기(1)
	document.getElementById('btn-apply').addEventListener('click', function() {
		saveAnalysisData('1');
	});
} 

// 목록으로(0)
$('.btn-back').on('click', function() {	
	window.location.href = contextPath + '/dct/analysis/list.do?mId='+mId;
});

let url = "";
if(parameters.get('rsltAnalsIdx') == null) {
	url = `${contextPath}`+"/dct/analysis/read.do?mId=130";
} else {
	let idx = parameters.get('rsltAnalsIdx');
	url = `${contextPath}`+"/dct/analysis/read.do?mId=130&rsltAnalsIdx="+idx+"&mode=m";
	let chk = 0;
	applyChk(0);
}

let chkType = $("#id_chkType").val();
let tpDevlopSe = $("#id_tpDevlopSe").val();

if(chkType == null) {
	if(tpDevlopSe == '1') {
		$("#trResultReprtSe1").prop("checked",true);
		$("#trResultReprtSe2").attr("disabled",true);
		applyChk(tpDevlopSe);
	} else if(tpDevlopSe == '2') {
		$("#trResultReprtSe2").prop("checked",true);
		$("#trResultReprtSe1").attr("disabled",true);
		applyChk(tpDevlopSe);
	}
} else {
	$("#trResultReprtSe1").prop("checked",true);
	applyChk(tpDevlopSe);
}

//modal close
const modal = document.getElementById('modal-action');
const closeModal = () => {
	modal.style.display = 'none';
}

const openModal = (id) => {
	modal.style.display = 'block';
	srchGo(id);
}

function dataTranse(param){
	$("#id_tpCd").val(param);
	modal.style.display = 'none';
}

function srchGo(id){
	$("#srchBody").html("");
	
	$.ajax({
		type : "GET",
		url : `${contextPath}/dct/analysis/subjectFind.do?mId=153&bplNo=`+id,
		success: function(data) {
			if(data.result.body.length > 0){
				var HTML = "";
				HTML +="<tr>";
				HTML +="		<th>";
				HTML +="			선택";
				HTML +="		</th>";
				HTML +="		<th>";
				HTML +="			과정 ID";
				HTML +="		</th>";
				HTML +="		<th>";
				HTML +="			회차";
				HTML +="		</th>";
				HTML +="		<th>";
				HTML +="			과정명";
				HTML +="		</th>";
				HTML +="		<th>";
				HTML +="			훈련 시작일";
				HTML +="		</th>";
				HTML +="		<th>";
				HTML +="			훈련 종료일";
				HTML +="		</th>";
				HTML +="</tr>";

				for(var i=0; i < data.result.body.length; i ++)	{
					let dateformat = new Date(data.result.body[i].TR_START_DATE);
					let dateformat2 = new Date(data.result.body[i].TR_END_DATE);
					
					HTML += "<tr>";
					HTML += "	<td style=\"center\">";
					HTML += "		<input type=\"checkbox\" name=\"srchType\" id=\"srchType\" value=\""+data.result.body[i].TP_CD+"\" onclick=\"dataTranse(this.value); return false;\">";
					HTML += "	</td>";
					HTML += "	<td>";
					HTML += 	data.result.body[i].TP_CD;
					HTML += "	</td>";
					HTML += "	<td>";
					HTML += 	data.result.body[i].TP_TME;
					HTML += "	차</td>";
					HTML += "	<td>";
					HTML += 	data.result.body[i].TP_NM;
					HTML += "	</td>";
					HTML += "	<td>";
					HTML += 	dateformat.getFullYear()+`-`+( (dateformat.getMonth()+1) < 10 ? `0` + (dateformat.getMonth()+1) : (dateformat.getMonth()+1) )+`-`;
					HTML += 	( (dateformat.getDate()) < 10 ? `0` + (dateformat.getDate()) : (dateformat.getDate()) );
					HTML += "	</td>";
					HTML += "	<td>";
					HTML += 	dateformat2.getFullYear()+`-`+( (dateformat2.getMonth()+1) < 10 ? `0` + (dateformat2.getMonth()+1) : (dateformat2.getMonth()+1) )+`-`;
					HTML += 	( (dateformat2.getDate()) < 10 ? `0` + (dateformat2.getDate()) : (dateformat2.getDate()) );
					HTML += "	</td>";
					HTML += "</tr>";
				}

				$("#srchBody").append(HTML);
				
			}else{
				alert('해당 고용보험관리번호로 등록된 사업주 훈련이 없습니다.');
				const modal = document.getElementById('modal-action');
				modal.style.display = 'none';
				return false;
			}
			
		}, error : function (e){
			alert('데이터를 불러올 때 오류가 발생하였습니다.');
			const modal = document.getElementById('modal-action');
			modal.style.display = 'none';
			return false;
		}
	});
}

const delOneAnalysis = (id) => {
	var formData = {
		"rsltAnalsIdx" : id
	}
	
	fetch(`${contextPath}/dct/analysis/deleteData.do?mId=153`, {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(formData)
	}).then((response) => {
		console.log(response);
		if(!response.ok){
			throw new Error("HttpStatus is not ok");
		}
		return response.json();
	})
	.then((data) => {
		if(data.result.code == 1) {
			window.location.reload();
		}
	});
}

const delAnalysis = (id) => {
	if($("input:checkbox[name='rsltAnalsIdx']:checked").length<1){
		alert("하나 이상의 체크박스를 선택 하시기 바랍니다."); 
		return false;
	}
	
	var result = confirm("삭제하시겠습니까?");
	
	if(result) {
		$("input[name=rsltAnalsIdx]").each(function(index){
			if($(this).is(":checked")){
				rsltAnalsIdx = $(this).val();
				var formData = {
						"rsltAnalsIdx" : rsltAnalsIdx
				}
				
				fetch(`${contextPath}/dct/analysis/deleteData.do?mId=130`, {
					method: 'POST',
					headers: {
						"Content-Type": "application/json"
					},
					body: JSON.stringify(formData)
				}).then((response) => {
					console.log(response);
					if(!response.ok){
						throw new Error("HttpStatus is not ok");
					}
					return response.json();
				})
				.then((data) => {
					if(data.result.code == 1) {
						window.location.reload();
					}
				});
				
			}
		});
	} else {
		return;
	}
}

document.addEventListener("keyup", function handleEnterKeyPress(event) {
	if(event.code == 'Enter') {
		const divElement =document.querySelector(".mask");
		
		if(divElement && window.getComputedStyle(divElement).display === "block") {
			searchBtn();
			document.removeEventListener("keyup", handleEnterKeyPress);
		}
		
	}
	
	document.addEventListener("keyup", handleEnterKeyPress);
});

function applyChk(chk) {
	let num = 0;
		
		if($("#trResultReprtSe1").is(":checked") == true) {
			num = 1;
		} else if($("#trResultReprtSe2").is(":checked") == true) {
			num = 2;
		}
		
		var html = "";
		$("#survey").empty();
		
		$.ajax({
			type: "GET",
			url : url,
			success : function (data) {
				const result = data.result.body;
				// 1. HTML만들기
				html += `
					<tr>
						<th scope="row">
							구분
						</th>
						<th scope="row">
							항목
						</th>
						<th scope="row">
							1 - 매우아니다
						</th>
						<th scope="row">
							2 - 아니다
						</th>
						<th scope="row">
							3 - 보통
						</th>
						<th scope="row">
							4 - 그렇다
						</th>
						<th scope="row">
							5 - 매우그렇다
						</th>
					</tr>
				`;
				
				if(result != null) {
					if(result.TR_RESULT_REPRT_SE == '1'){
						num = 1;
						$("#trResultReprtSe1").prop("checked",true);
						$("#trResultReprtSe2").attr("disabled",true);
						$("#survey").empty();
					} else if(result.TR_RESULT_REPRT_SE == '2'){
						num = 2;
						$("#trResultReprtSe2").prop("checked",true);
						$("#trResultReprtSe1").attr("disabled",true);
						$("#survey").empty();
					}
				}
				
				if(num == 1) {
					html += `
						<tr>
							<td rowspan="3">	
								운영
							</td>
							<td class="left">
								본 훈련과정에 대하여 전반적으로 만족한다.
							</td>
							<td>	
								<input type="radio" name="edcCrse1" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse1" class="radio-type01 satisfy" value="2" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse1" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse1" class="radio-type01 satisfy" value="4" />		
							</td>
							<td>	
								<input type="radio" name="edcCrse1" class="radio-type01 satisfy" value="5" />	
							</td>	
						</tr>
						<tr>
							<td class="left">
								본 훈련과정의 교육방법에 대해 만족하십니까?<br/>
								(이론, 실습 PBL 등)
							</td>
							<td>	
								<input type="radio" name="edcCrse2" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse2" class="radio-type01 satisfy" value="2" />		
							</td>
							<td>	
								<input type="radio" name="edcCrse2" class="radio-type01 satisfy" value="3" />		
							</td>
							<td>	
								<input type="radio" name="edcCrse2" class="radio-type01 satisfy" value="4" />		
							</td>
							<td>	
								<input type="radio" name="edcCrse2" class="radio-type01 satisfy" value="5" />	
							</td>	
						</tr>
						<tr>
							<td class="left">	
								본 훈련과정의 교육시간에 대해 만족하십니까?<br/>
								(교육 내용 대비 시간 부족, 과다 등)
							</td>
							<td>
								<input type="radio" name="edcCrse3" class="radio-type01 satisfy" value="1" />		
							</td>
							<td>	
								<input type="radio" name="edcCrse3" class="radio-type01 satisfy" value="2" />		
							</td>
							<td>
								<input type="radio" name="edcCrse3" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse3" class="radio-type01 satisfy" value="4" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse3" class="radio-type01 satisfy" value="5" />
							</td>
						</tr>
						<tr>
							<td>
								환경
							</td>
							<td class="left">	
								본 훈련과정의 교육환경에 대해 만족하십니까?<br/>
								(교육 장소, 시설, 장비, 교육자료 등)
							</td>
							<td>
								<input type="radio" name="edcCrse4" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>	
								<input type="radio" name="edcCrse4" class="radio-type01 satisfy" value="2" />	
							</td>
							<td>
								<input type="radio" name="edcCrse4" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>
								<input type="radio" name="edcCrse4" class="radio-type01 satisfy" value="4" />	
							</td>
							<td>
								<input type="radio" name="edcCrse4" class="radio-type01 satisfy" value="5" />
							</td>
						</tr>
						<tr>
							<td>
								훈련내용
							</td>
							<td class="left">	
								본 훈련과정의 교육내용에 대해 만족하십니까?<br/>
								(기업 요구와의 부합여부, 구성 내용, 교육 수준 등)
							</td>
							<td>
								<input type="radio" name="edcCrse5" class="radio-type01 satisfy" value="1" />
							</td>
							<td>
								<input type="radio" name="edcCrse5" class="radio-type01 satisfy" value="2" />	
							</td>
							<td>
								<input type="radio" name="edcCrse5" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>
								<input type="radio" name="edcCrse5" class="radio-type01 satisfy" value="4" />
							</td>
							<td>
								<input type="radio" name="edcCrse5" class="radio-type01 satisfy" value="5" />	
							</td>
						</tr>
						<tr>
							<td>
								강사
							</td>
							<td class="left">	
								본 훈련과정의 교육강사에 대해 만족하십니까?
								(강사 전문성, 강의 내용 전달능력 등)
							</td>
							<td>
								<input type="radio" name="gnrlz" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>
								<input type="radio" name="gnrlz" class="radio-type01 satisfy" value="2" />	
							</td>
							<td>
								<input type="radio" name="gnrlz" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>
								<input type="radio" name="gnrlz" class="radio-type01 satisfy" value="4" />
							</td>
							<td>
								<input type="radio" name="gnrlz" class="radio-type01 satisfy" value="5" />
							</td>
						</tr>
					`;
					html += `
					<h3 class="title-type01 ml0">성취도 조사</h3>
						<tr>
							<th scope="row">
								구분
							</th>
							<th scope="row">
								항목
							</th>
							<th scope="row">
								1 - 매우아니다
							</th>
							<th scope="row">
								2 - 아니다
							</th>
							<th scope="row">
								3 - 보통
							</th>
							<th scope="row">
								4 - 그렇다
							</th>
							<th scope="row">
								5 - 매우그렇다
							</th>
						</tr>
						<tr>
							<td rowspan="3">	
								지식·기술습득
							</td>
							<td class="left">
								본 훈련과정의 학습목표 및 학습내용을 충분히 이해하였다.
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup1" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup1" class="radio-type01 satisfy" value="2" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup1" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup1" class="radio-type01 satisfy" value="4" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup1" class="radio-type01 satisfy" value="5" />
							</td>
						</tr>
						<tr>
							<td class="left">	
								본 훈련과정을 통해 새로운 지식 및 기술을 습득하였다.
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup2" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup2" class="radio-type01 satisfy" value="2" />		
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup2" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup2" class="radio-type01 satisfy" value="4" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup2" class="radio-type01 satisfy" value="5" />	
							</td>
						</tr>
						<tr>
							<td class="left">	
								본 훈련과정을 통해 습득한 지식 및 기술을 실무에 적용할 수 있다.
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup3" class="radio-type01 satisfy" value="1" />		
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup3" class="radio-type01 satisfy" value="2" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup3" class="radio-type01 satisfy" value="3" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup3" class="radio-type01 satisfy" value="4" />	
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup3" class="radio-type01 satisfy" value="5" />
							</td>
						</tr>
						<tr>	
							<td>
								태도변화
							</td>
							<td class="left">	
								본 훈련과정을 통해 직무 전문성 및 업무 수행 자신감이 향상되었다.
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup4" class="radio-type01 satisfy" value="1" />	
							</td>
							<td>
								<input type="radio" name="knwldgTchnlgyPickup4" class="radio-type01 satisfy" value="2" />		
							</td>
							<td>
								<input type="radio" name="knwldgTchnlgyPickup4" class="radio-type01 satisfy" value="3" />		
							</td>
							<td>	
								<input type="radio" name="knwldgTchnlgyPickup4" class="radio-type01 satisfy" value="4" />		
							</td>
							<td>
								<input type="radio" name="knwldgTchnlgyPickup4" class="radio-type01 satisfy" value="5" />		
							</td>
						</tr>
					`;
				} else if(num == 2) {
					html += `
						<tr>
							<td rowspan="3">	
								훈련전이
							</td>
							<td class="left">
								본 훈련과정 이수 후 훈련생에게 관련 업무수행 기회를 제공하였나요?
							</td>
							<td>
								<input type="radio" name="stoprtApplc1" class="radio-type01 real" value="1" />		
							</td>
							<td>
								<input type="radio" name="stoprtApplc1" class="radio-type01 real" value="2" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc1" class="radio-type01 real" value="3" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc1" class="radio-type01 real" value="4" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc1" class="radio-type01 real" value="5" />	
							</td>
						</tr>
						<tr>
							<td class="left">	
								본 훈련과정 이수 후 훈련생의 업무수행 역량이 향상되었나요?
							</td>
							<td>
								<input type="radio" name="stoprtApplc2" class="radio-type01 real" value="1" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc2" class="radio-type01 real" value="2" />		
							</td>
							<td>	
								<input type="radio" name="stoprtApplc2" class="radio-type01 real" value="3" />		
							</td>
							<td>
								<input type="radio" name="stoprtApplc2" class="radio-type01 real" value="4" />		
							</td>
							<td>
								<input type="radio" name="stoprtApplc2" class="radio-type01 real" value="5" />		
							</td>
						</tr>
						<tr>
							<td class="left">	
								본 훈련과정 이수 후 훈련생의 업무수행에 실질적인 도움이 되었나요?
							</td>
							<td>
								<input type="radio" name="stoprtApplc3" class="radio-type01 real" value="1" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc3" class="radio-type01 real" value="2" />		
							</td>
							<td>
								<input type="radio" name="stoprtApplc3" class="radio-type01 real" value="3" />		
							</td>
							<td>	
								<input type="radio" name="stoprtApplc3" class="radio-type01 real" value="4" />
							</td>
							<td>
								<input type="radio" name="stoprtApplc3" class="radio-type01 real" value="5" />		
							</td>
						</tr>
						<tr>
							<td>
								조직전이
							</td>
							<td class="left">	
								경영상 이슈(예. 불량률 감소/매출액 증대 등) <br/>
								해결에 본 훈련과정이 기여했다고 생각하시나요?
							</td>
							<td>	
								<input type="radio" name="stoprtApplc4" class="radio-type01 real" value="1" />		
							</td>
							<td>	
								<input type="radio" name="stoprtApplc4" class="radio-type01 real" value="2" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc4" class="radio-type01 real" value="3" />
							</td>
							<td>
								<input type="radio" name="stoprtApplc4" class="radio-type01 real" value="4" />	
							</td>
							<td>
								<input type="radio" name="stoprtApplc4" class="radio-type01 real" value="5" />	
							</td>
						</tr>
					`;
				}
				
				$("#survey").prepend(html);
				
				if(!isEmpty(result)) {
					checkResult(result);
					
				} else {
					document.querySelector('#btns-area-0').style.display = 'block';
					document.querySelector('#btns-area-1').style.display = 'none';
				}
				
			}
		});
	}

function checkResult(result) {
	var status = result.STATUS;	// 0:작성중 / 1:제출
	
	if(path == "/dct/analysis/view.do") {
		var radioButton = document.querySelectorAll('input[type="radio"]');
		radioButton.forEach(function(radio) {
			radio.setAttribute('onclick', 'return false');
		});
	}
	
	if(result.TR_RESULT_REPRT_SE == '1') {
		var edcCrse1 = result.EDC_CRSE1;
		var edcCrse2 = result.EDC_CRSE2;
		var edcCrse3 = result.EDC_CRSE3;
		var edcCrse4 = result.EDC_CRSE4;
		var edcCrse5 = result.EDC_CRSE5;
		var gnrlz = result.GNRLZ;
		var edcCrseb1 = document.querySelectorAll('input[name="edcCrse1"]');
		var edcCrseb2 = document.querySelectorAll('input[name="edcCrse2"]');
		var edcCrseb3 = document.querySelectorAll('input[name="edcCrse3"]');
		var edcCrseb4 = document.querySelectorAll('input[name="edcCrse4"]');
		var edcCrseb5 = document.querySelectorAll('input[name="edcCrse5"]');
		var gnrlzb = document.querySelectorAll('input[name="gnrlz"]');
		
		edcCrseb1.forEach(function(radio) {
			if(radio.value === edcCrse1) {
				radio.checked = true;
			}
		});
		
		edcCrseb2.forEach(function(radio) {
			if(radio.value === edcCrse2) {
				radio.checked = true;
			}
		});
		
		edcCrseb3.forEach(function(radio) {
			if(radio.value === edcCrse3) {
				radio.checked = true;
			}
		});
		
		edcCrseb4.forEach(function(radio) {
			if(radio.value === edcCrse4) {
				radio.checked = true;
			}
		});
		
		edcCrseb5.forEach(function(radio) {
			if(radio.value === edcCrse5) {
				radio.checked = true;
			}
		});
		
		gnrlzb.forEach(function(radio) {
			if(radio.value === gnrlz) {
				radio.checked = true;
			}
		});
		
		var knwldgTchnlgyPickup1 = result.KNWLDG_TCHNLGY_PICKUP1;
		var knwldgTchnlgyPickup2 = result.KNWLDG_TCHNLGY_PICKUP2;
		var knwldgTchnlgyPickup3 = result.KNWLDG_TCHNLGY_PICKUP3;
		var knwldgTchnlgyPickup4 = result.KNWLDG_TCHNLGY_PICKUP4;
		var knwldgTchnlgyPickupb1 = document.querySelectorAll('input[name="knwldgTchnlgyPickup1"]');
		var knwldgTchnlgyPickupb2 = document.querySelectorAll('input[name="knwldgTchnlgyPickup2"]');
		var knwldgTchnlgyPickupb3 = document.querySelectorAll('input[name="knwldgTchnlgyPickup3"]');
		var knwldgTchnlgyPickupb4 = document.querySelectorAll('input[name="knwldgTchnlgyPickup4"]');
		
		knwldgTchnlgyPickupb1.forEach(function(radio) {
			if(radio.value === knwldgTchnlgyPickup1) {
				radio.checked = true;
			}
		});
		
		knwldgTchnlgyPickupb2.forEach(function(radio) {
			if(radio.value === knwldgTchnlgyPickup2) {
				radio.checked = true;
			}
		});
		
		knwldgTchnlgyPickupb3.forEach(function(radio) {
			if(radio.value === knwldgTchnlgyPickup3) {
				radio.checked = true;
			}
		});
		
		knwldgTchnlgyPickupb4.forEach(function(radio) {
			if(radio.value === knwldgTchnlgyPickup4) {
				radio.checked = true;
			}
		});
		
	} else {
		var stoprtApplc1 = result.STOPRT_APPLC1;
		var stoprtApplc2 = result.STOPRT_APPLC2;
		var stoprtApplc3 = result.STOPRT_APPLC3;
		var stoprtApplc4 = result.STOPRT_APPLC4;
		var stoprtApplcb1 = document.querySelectorAll('input[name="stoprtApplc1"]');
		var stoprtApplcb2 = document.querySelectorAll('input[name="stoprtApplc2"]');
		var stoprtApplcb3 = document.querySelectorAll('input[name="stoprtApplc3"]');
		var stoprtApplcb4 = document.querySelectorAll('input[name="stoprtApplc4"]');
		
		stoprtApplcb1.forEach(function(radio) {
			if(radio.value === stoprtApplc1) {
				radio.checked = true;
			}
		});
		
		stoprtApplcb2.forEach(function(radio) {
			if(radio.value === stoprtApplc2) {
				radio.checked = true;
			}
		});
		
		stoprtApplcb3.forEach(function(radio) {
			if(radio.value === stoprtApplc3) {
				radio.checked = true;
			}
		});
		
		stoprtApplcb4.forEach(function(radio) {
			if(radio.value === stoprtApplc4) {
				radio.checked = true;
			}
		});
	}
 }

function getCookie(key) {
	key = new RegExp(key + '=([^;]*)');
	return key.test(document.cookie) ? unescape(RegExp.$1) : '';
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

// 설문조사 저장
const saveAnalysisData = (flag) => {
	var f = document.forms.frm;
		if(flag == 0) {
			document.getElementById('id_status').value = 0;
		} else {
			document.getElementById('id_status').value = 1;
		}
		
	const parameters = new URLSearchParams(window.location.search);
	const idx = parameters.get('rsltAnalsIdx');
	const mode = parameters.get('mode');
	let url = "";
	
	if(mode == null) {
		url = `${contextPath}`+"/dct/analysis/inputProc.do?mId=153";
	} else {
		url = `${contextPath}`+"/dct/analysis/inputProc.do?mId=153&rsltAnalsIdx="+idx+"&mode=m";
	}
	
	f.action = url;
	f.submit();
}

$(".onlyNum").keyup(function(event){
	if($(this).val() == "0"){
		alert("최소 1이상 입력해 주세요.");
		$(this).val("");
	}
	fn_preventHan(event, $(this));
	return fn_onlyNum(event, $(this));
});

//복사 붙여넣기 시 숫자만 입력
$(".onlyNum").change(function(event){
	if($(this).val() == "0"){
		alert("최소 1이상 입력해 주세요.");
		$(this).val("");
	}
	fn_onlyNumNoCopy(event);
});

// 전화번호 입력
$(".onlyNumDash").keyup(function(event){
	if($(this).val().length > 13){
		$(this).val($(this).val().substr(0, 13));
	}
	fn_preventHan(event, $(this));
	return fn_onlyNumDash(event, $(this));
});

// 복사 붙여넣기 시 전화번호 입력
$(".onlyNumDash").change(function(event){
	if($(this).val().length > 13){
		$(this).val($(this).val().substr(0, 13));
	}		
	fn_onlyNumDashNoCopy(event);
});

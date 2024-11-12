<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="inputFormId1" value="fn_InputForm1"/>
<c:set var="inputFormId2" value="fn_inputForm2"/>
<c:set var="inputFormId3" value="fn_inputForm3"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
var modal;
var input;
var idx;

$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_authInputReset();
	
	
	/* ============================= form1 ============================= */
	// reset
	$("#<c:out value="${inputFormId1}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId1}"/> .fn_btn_cancel").click(function(){
		try {
			<c:choose>
				<c:when test="${param.dl == '1'}">
				self.close();
				</c:when>
				<c:otherwise>
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	</c:if>
	
	// 승인, 반려 선택시 등록
	/* $(".status").change(function(){
			const applyVal = $(this).val();
			const hiddenVal = $(this).prev().val();
			const preInsttVal = $("#PRE_INSTT_IDX").text();
			const insttVal = $('#INSTT_IDX').text();
			
			$('form#inputCorp1Proc > input#PSITN').prop("value", hiddenVal)
			$('form#inputCorp1Proc > input#statusVal').prop("value", applyVal)
			console.log($('form#inputDctCorp1Proc'))
						
			if(applyVal == "Y"){
				if(confirm('소속이 "' + preInsttVal + '"에서 "' + insttVal + '"로 변경됩니다.\n변경하시겠습니까?')){
					$("form#inputCorp1Proc").submit();	
				}
			} else {
				if(confirm('소속 변경을 반려합니다.')){
					$("form#inputCorp1Proc").submit();	
				}
			}
	}); */
	
	
	
	
	var regionVal = $("#region").val();
	
	console.log("region 값" + regionVal);
	
	menuOn(5, 2, 1, 0);
	$(".open-modal01").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-alert01").show();
        });
    });

    $(".open-modal02").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-alert02").show();
        });
    });
    
    // 정민 추가
    // 소속기관 변경
    // 소속기관 변경 등록시 팝업창 멘트 수정
    var preRegi = $("#region option:selected").text();
	var preRegiVal = $("#region").val();

	$(".status").change(function(){
			const applyVal = $(this).val();
			console.log(applyVal + "셀렉트값");
			const hiddenVal = $(this).prev().val();
			const preInstt = $("#PRE_INSTT_IDX0").text();
			const instt = $('#INSTT_IDX0').text();
			
			
			$('form#inputCorp1Proc > input#PSITN').prop("value", hiddenVal)
			$('form#inputCorp1Proc > input#statusVal').prop("value", applyVal)
			console.log($('form#inputDctCorp1Proc'));
			
	    	if(applyVal == "Y"){
	    		if(preRegiVal == ""){
		    		$("#mentUpdate").text('소속이 "' + instt + '"로 변경됩니다.\n변경하시겠습니까?');	
		    	} else{
		    		$("#mentUpdate").text('소속이 "' + preInstt + '"에서\n"' + instt + '"로 변경됩니다.\n변경하시겠습니까?');	
		    	}
	    	} else if(applyVal == "N") {
	    		$("#mentUpdate").text('소속 변경을 반려하시겠습니까?');	
	    	} else{
	    		return false;
	    	}
			
	    	
			/* if(applyVal == "Y"){
				$("#ment").text('소속이 "' + preInsttVal + '"에서 "' + insttVal + '"로 변경됩니다.\n변경하시겠습니까?');	
			} else {
				$("#ment").text('소속 변경을 반려하시겠습니까?');	
				/* if(confirm('소속 변경을 반려하시겠습니까?')){
					$("form#inputCorp1Proc").submit();	
				} 
			} */
			$(".modal-wrapper").hide();
	    	$(".mask").fadeIn(150, function() {
	    		$("#modal-action03").show();
	    	})
	});
    $(".open-modal04").on("click", function() {
    	const isApplied = $('input#is-applied').val();
    	if(isApplied == 'N') {
    		alert('변경 승인 요청이 있습니다.')
    		return;
    	}
    	var regi = $("#region option:selected").text();
    	if(preRegiVal == ""){
    		$("#ment").text('소속이 "' + regi + '"로 변경됩니다.\n변경하시겠습니까?');	
    	} else{
    		$("#ment").text('소속이 "' + preRegi + '"에서\n"' + regi + '"로 변경됩니다.\n변경하시겠습니까?');	
    	}
    	$(".modal-wrapper").hide();
    	$(".mask").fadeIn(150, function() {
    		$("#modal-action04").show();
    	})
    })

    $(".open-modal13").on("click", function() {
    	$(".modal-wrapper").hide();
    	$(".mask").fadeIn(150, function() {
    		modal = 'modal-action13';
    		input = 'doctor-normal-input';
    		idx = 'doctor-normal-idx';
    		$("#modal-action13").show();
    		
    	})
    })
    $(".open-modal05").on("click", function() {
    	$(".modal-wrapper").hide();
    	$(".mask").fadeIn(150, function() {
    		modal = 'modal-action13';
    		input = 'doctor-cli-input';
    		idx = 'doctor-cli-idx';
    		$("#modal-action13").show();
    	})
    })
    
    $('select#region').change(function() {
    	const instt = $(this).val();
    	$('form#inputCorp2Proc > input#instt').prop("value", instt);
    	$('form#inputCorp2Proc > input#pre_instt').prop("value", preRegiVal);
    })
    $("form#inputCorp2Proc").submit(function() {
    	$(".modal-wrapper").hide();
        $(".mask").hide();
        // list update하는 기능 필요
	})

    $(".modal-close").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").hide();
    });

	
	// 등록/수정
	$("#<c:out value="${inputFormId1}"/>").submit(function(){
		try {
			console.log("ddd");
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	/* ============================= form2 ============================= */
	// reset
	$("#<c:out value="${inputFormId2}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${inputFormId2}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId2}"/> .fn_btn_cancel").click(function(){
		try {
			<c:choose>
				<c:when test="${param.dl == '1'}">
				self.close();
				</c:when>
				<c:otherwise>
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	</c:if>
	
	// 등록/수정
	$("#doctorNormal").submit(function(){
		try {
			console.log("ddd");
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	
	/* ============================= form3 ============================= */
	// reset
	$("#<c:out value="${inputFormId3}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${inputFormId3}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId3}"/> .fn_btn_cancel").click(function(){
		try {
			<c:choose>
				<c:when test="${param.dl == '1'}">
				self.close();
				</c:when>
				<c:otherwise>
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	</c:if>
	
	// 등록/수정
	$("#doctorCli").submit(function(){
		try {
			console.log("ddd");
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
					
	// 전화번호 정규식
	var preMobile = $("#mobile").text();
	var mobile = preMobile.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
	$("#mobile").text(mobile);

	
});

function closeModal() {
  	$("#modal-action01").hide();
    $(".mask").fadeOut(150);
}

function fn_authInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_authInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}


var doctor_list = []
async function doctorSearch() {
	let form_e = document.querySelector('form#doctor-search')
	let formData = new FormData(form_e)
	let value = Object.fromEntries(formData.entries());
	console.log(value);
	console.log("value.doctorName" + value.doctorName);
	console.log("value.insttName" + value.insttName);
	let empty_search = (value.doctorName == null || value.doctorName == '') && (value.insttName == null || value.insttName == '')
	
	if(empty_search) {
		alert('이름이나 소속기관 중 하나는 입력해주세요.')
		hideLoading();
		return
	}
	let response = await fetch(`${contextPath}/dct/bsisCnsl/docsearch.do?mId=37`, {
		method: 'POST',
		body: new FormData(form_e)
	});
	console.log(response);

	let result = await response.json();
	
	
	doctor_list = result
	render_doc_board(result, modal, input, idx)
}

function render_doc_board(result, modal, input, idx) {
	let tbody = document.querySelector('tbody#docbody');
	tbody.innerHTML = '';
	document.getElementById('doctor-cnt').textContent = result.length;
	result.forEach((e,i) => {
		let row = document.createElement('tr');
		row.innerHTML = `<th scope="row">주치의</th>
		<td class="left" colspan="2">
			<dl>
				<dt><strong class="point-color01">\${e.DOCTOR_NAME}</strong></dt>
				<dd>\${e.INSTT_NAME} - \${e.DOCTOR_DEPT_NAME}</dd>
			</dl>
		</td>
		<td>
			<button type="button" class="btn-m02 btn-color03" onclick="setDoctor('\${modal}', '\${input}', '\${idx}', \${i})">변경</button>
		</td>`;
    	tbody.appendChild(row);
	})
}

function setDoctor(modal, input, idx, index) {
	const {DOCTOR_NAME, DOCTOR_EMAIL, DOCTOR_TELNO, DOCTOR_IDX} = doctor_list[index]
	const text = `\${DOCTOR_NAME} / \${DOCTOR_TELNO} / \${DOCTOR_EMAIL}`
	$(`input#\${input}`).val(text);
	$(`input#\${idx}`).val(DOCTOR_IDX);
	$(`#\${modal} .modal-close`).click();
	$(`#docbody`).empty();
}
</script>
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);	
var context = location.pathname.split('/')[1]
var contextPath = context == 'web' ? '' : `/${context}`;

document.addEventListener('DOMContentLoaded', function() {
	hideLoading();
});

$('.btn-back').on('click', function() {	
	history.back();
});

function isNumber() {
	if(event.keyCode < 48 || event.keyCode>57) {
		event.returnValue=false;
	}
}

$(".act").change( function() {
   	var cls = $(this).attr('id');
   	var rep = cls.replace('id_mtgStartDt', 'id_mtgEndDt');
   	$("#"+rep).val($("#"+cls).val());
});

var sportType = $('#id_sportType option:selected').val();
if(sportType == 1 || sportType == 2 || sportType == 3) {
	$("#id_excMth1").prop("checked", true);
	$("#id_excMth2").attr("onclick", "return false;");
	$("#id_excMth2").attr("disabled", true);
	$("#id_excMth3").attr("onclick", "return false;");
	$("#id_excMth3").attr("disabled", true);
} else {
	$('input[name="excMth"]').on("click", function(e) {
		if($("#id_excMth2:checked").length == 1) {
			$("#id_operMthd1").prop("checked", true);
			$("#id_operMthd2").attr("onclick", "return false;");
		} else {
			$("#id_operMthd1").prop("checked", false);
			$("#id_operMthd2").attr("onclick", "");
		}
	});
}

//수행일지 작성
const saveDiary = (flag) => {
	var f = document.forms.frm;
	let url = "";
	
	if(urlParams.get('mode') == null) {
		url = `${contextPath}`+"/web/consulting/inputProc.do?mId=102";
	} else {
		url = `${contextPath}`+"/web/consulting/inputProc.do?mId=102&mode=m";
	}
	
	f.method="post";
	
	if(flag == 0) {
		document.getElementById('id_status').value = 0;
	} else {
		document.getElementById('id_status').value = 1;
	}
	
	if(flag == 0) {
		
		if($("#id_mtgStartDt").val() == "") {
			alert("시작일 입력이 완료되지 않았습니다.");
			$("#id_mtgStartDt").focus();
			return false;
		}
		if($("#id_mtgStartTime").val() == "") {
			alert("시작시간 입력이 완료되지 않았습니다.");
			$("#id_mtgStartTime").focus();
			return false;
		}
		
		var sdate = frm.id_mtgStartTime.value.replace(':','').substring(0,4);
		var edate = frm.id_mtgEndTime.value.replace(':','').substring(0,4);
		
		if(Number(sdate) >= Number(edate)) {
			alert("수행일시 시간이 시작시간 보다 빠릅니다.");
			return false;
		}
		
		f.action = url;
		f.submit();
	}
	
	if(flag == 1) {
		
		if($("#id_bplNm").val() == "") {
	 		alert("기업명 입력이 완료되지 않았습니다.");
			$("#id_bplNm").focus();
			return false;
		}
		if($("#id_mtgStartDt").val() == "") {
			alert("시작일 입력이 완료되지 않았습니다.");
			$("#id_mtgStartDt").focus();
			return false;
		}
		if($("#id_mtgStartTime").val() == "") {
			alert("시작시간 입력이 완료되지 않았습니다.");
			$("#id_mtgStartTime").focus();
			return false;
		}
		
		var sdate = frm.id_mtgStartTime.value.replace(':','').substring(0,4);
		var edate = frm.id_mtgEndTime.value.replace(':','').substring(0,4);
		
		if(Number(sdate) >= Number(edate)) {
			alert("수행일시 시간이 시작시간 보다 빠릅니다.");
			return false;
		}
		
		if($("#id_mtgEndDt").val() == "") {
			alert("종료일 입력이 완료되지 않았습니다.");
			$("#id_mtgEndDt").focus();
			return false;
		}
		if($("#id_mtgEndTime").val() == "") {
			alert("종료시간 입력이 완료되지 않았습니다.");
			$("#id_mtgEndTime").focus();
			return false;
		}
		if($("#id_mtgTme").val() == "") {
			alert("수행차수 입력이 완료되지 않았습니다.");
			$("#id_mtgTme").focus();
			return false;
		}
		if($("input:radio[name='excMth']:checked").length < 1) {
			alert("수행방법이 체크되지 않았습니다.");
			$("input:radio[name='excMth']").focus();
			return false;
		}
		if($("input:radio[name='operMthd']:checked").length < 1) {
			alert("운영방식이 체크되지 않았습니다.");
			$("input:radio[name='operMthd']").focus();
			return false;
		}
		if($("#id_pmNm").val() == "") {
			alert("컨설팅책임자(PM) 입력이 완료되지 않았습니다.");
			$("#id_pmNm").focus();
			return false;
		}
		if($("#id_cnExpert").val() == "") {
			alert("내용전문가 입력이 완료되지 않았습니다.");
			$("#id_cnExpert").focus();
			return false;
		}
		if($("#id_corpInnerExpert").val() == "") {
			alert("기업 내부전문가 입력이 완료되지 않았습니다.");
			$("#id_corpInnerExpert").focus();
			return false;
		}
		if($("#id_mtgWeekExplsn1").val() == "") {
			alert("회의주제명1 입력이 완료되지 않았습니다.");
			$("#id_mtgWeekExplsn1").focus();
			return false;
		}
		if($("#id_mtgCn1").val() == "") {
			alert("회의내용1 입력이 완료되지 않았습니다.");
			$("#id_mtgCn1").focus();
			return false;
		}
	 	/* if($("#photo_total1").length < 1) {
			alert("첨부파일이 첨부되지 않았습니다.");
			$("#photo_total1").focus();
			return false;
		} */
		
		f.action = url;
		f.submit();
	}
	
	f.action = url;
	f.submit();
}

//수행일지 삭제
const deleteDiary = () => {
	var f = document.forms.fn_sampleInputForm;
	let url = `${contextPath}`+"/web/consulting/deleteProc.do?mId=102";
	f.method="post";
	
	var result = confirm("삭제하시겠습니까?");
	if (result) {
		f.action = url;
		f.submit();
	} else {
		return;
	}
	
	f.action = url;
	f.submit();
}

function showLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	if(loader && overlay) {
		loader.style.display = 'block';
		overlay.style.display = 'block';
	}
}

function hideLoading() {
	const loader = document.querySelector('.loader');
	const overlay = document.getElementById('overlay');
	if(loader && overlay) {
		loader.style.display = 'none';
		overlay.style.display = 'none';
	}
}



















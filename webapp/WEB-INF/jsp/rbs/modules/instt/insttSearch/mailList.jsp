<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="listFormId" value="fn_mailListListForm"/>
<c:set var="itemOrderName" value="writeproc_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	/* <itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_regiInputReset(); */
	
	// dialog
	fn_dialog.init("fn_list");
	
	// reset
	$("#regiProc .fn_btn_reset").click(function(){
		try {
			$("#regiProc").reset();
			fn_regiInputReset();
		}catch(e){alert(e); return false;}
	});
	
	// 우편번호 검색 숫자 유효성
	$("#key").attr("oninput",`this.value= this.value.replace(/[^0-9.]/g,'').replace(/(|..*)\\./g, '$1');`);
		
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			fn_listDeleteFormSubmit("<c:out value="${listFormId}"/>", $(this).attr("href"));
		}catch(e){}
		return false;
	});
	
	
	// 일괄등록 모달창 오픈
    $("#open-modal01").click(function() {
    	
    	if($("input[type='checkbox']:checked").length == 0){
			alert("우편번호를 선택해 주세요.");
			return false;
		}	
    	
    	$(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
        	var innerHtml = "";
        	$("input[type='checkbox']:checked").each(function() {
        		var zip = $(this).attr("data-zip");
            	var ctp = $(this).attr("data-ctp"); 
            	var signgu = $(this).attr("data-signgu");
            	
				
				innerHtml += `<li id="zip_\${zip}" class="bm05"><p class="word" data-zip="\${zip}" data-ctp="\${ctp}" data-signgu="\${signgu}"><strong>(\${zip}) \${ctp} \${signgu}</strong><button type="button" class="btn-delete" onclick="fn_mailList_delete('\${zip}');"></button></p></li>`;
        	});
        	$("#modal-action01").find("#mailList").append(innerHtml);
        	$("#mailList").find("#zip_undefined").remove();
        	$("#modal-action01").show();
        });
    	
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-action01").show();
        });
    });

 	// 우편번호 등록 모달창 오픈
    $("#open-modal02").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-action02").show();
        });
       
 	   
    });
 	
 	// 우편번호 등록(수정) 모달창 오픈
    $(".open-modal03").on("click", function() {
    	
    	// 수정 시 값 들고 오기
    	var zip = $(this).attr("data-zip");			// 우편번호 
    	var ctp = $(this).attr("data-ctp"); 		// 시/도
    	var signgu = $(this).attr("data-signgu");	// 구/군
    	var instt = $(this).attr("data-insttIdx");		// 지부지사
    	
    	console.log("signgu" + signgu + "instt" + instt);
    	
    	// 수정시 시/도 값 선택
    	$("select[name=sido] option:contains('" + ctp + "')").attr("selected",true);
    	

  		//시/군 선택시  구/군 리스트 생성
  		var area = "area" + $("option", $("select[name=sido]")).index($("option:selected",$("select[name=sido]"))); //선택지역의 구군 Array
	    var $gugun = $("select[name=sido]").next(); // 선택영역 구군 객체
	    $("option", $gugun).remove(); // 구군 초기화
	   
	    if(area == "area0")
	   	 $gugun.append("<option value=''>구/군 선택<option>");
	    else{
		   $.each(eval(area), function(){
			   $gugun.append("<option value='" + this + "'>" + this + "</option>");
		   });
	    }
	    
	    // 수정시 구/군 값 선택
	    $("select[name=gugun] option:contains('" + signgu + "')").attr("selected",true);
    	
  	    $("select[id=instt]").val(instt).attr("selected", true);
  	    $("#zip").val(zip);
  	    $("#instt").val(instt).attr("selected", true);
  	    
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-action02").show();
        });
    });
 	
 	// 지부지사 일괄변경 저장하기
	$("#fn-Allmail-submit").click(function () {
		var zipList = [];
		$("#mailList").find("p").each(function () {
			var zip = $(this).attr("data-zip");
			zipList.push(zip);
		});
		
		console.log(zipList.length + "기리이가");
		if(zipList.length==0){
			alert("변경할 항목이 없습니다.");
			return false;
		}
		updateAll(zipList);		
	});
 	
 	// 등록하기 
	$(".fn-input").on("click", function() {
		
		var zip = $("#zip").val();			// 우편번호 
		var ctp = $("#sido option:selected").val(); 		// 시/도
   
    	
    	if(zip == ""){
    		alert("우편번호를 입력해 주세요");
    		return false;
    	}
		console.log(ctp + "Sdsds");    	
    	if(ctp == "시/도 선택"){
    		alert("시/도를 선택해주세요");
    		return false;
    	}
    	
    	
    }); 

    $(".btn-modal-close, .fn-search-cancel").on("click", function() {
    	$("#gugun option").remove(); // 구군 초기화
    	 $("select[name^=gugun]").append("<option value=''>구/군 선택</option>");
    	$("select").find('option:first').attr("selected", "selected");
    	$("#zip").val('');
    	$("#mailList").empty();
        $(".modal-wrapper").hide();
        $(".mask").hide();
    });
    
    
	
    
    // 시/군구 select 박스 설정
    var area0 = ["시/도 선택", "서울특별시", "인천광역시", "대전광역시", "광주광역시", "대구광역시", "울산광역시", "부산광역시", "경기도", "강원특별자치도", "세종특별자치시", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"];
    var area1 = ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"];
    var area2 = ["강화군", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "옹진군", "중구"];
    var area3 = ["대덕구", "동구", "서구", "유성구", "중구"];
    var area4 = ["광산구", "남구", "동구", "북구", "서구"];
    var area5 = ["군위군", "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"];
    var area6 = ["남구", "동구", "북구", "울주군", "중구"];
    var area7 = ["강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"];
    var area8 = ["가평군", "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시 분당구", "성남시 수정구", "성남시 중원구", "성남시 권선구", "성남시 영통구", "성남시 장안구", "성남시 팔달구", "시흥시", "안산시 단원구", "안산시 상록구", "안성시", "안양시 동안구", "안양시 만안구", "양주시", "양평군", "여주시", "연천군", "오산시", "용인시 기흥구", "용인시 수지구", "용인시 처인구", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시"];
    var area9 = ["강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군"];
    var area10 = [];
    var area11 = ["괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시", "증평군", "진천군", "청주시 상당구", "청주시 서원구", "청주시 청원구", "청주시 흥덕구", "충주시"];
    var area12 = ["계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군", "천안시 동남구", "천안시 서북구", "청양군", "태안군", "홍성군"];
    var area13 = ["고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시 덕진구", "전주시 완산구", "정읍시", "진안군"];
    var area14 = ["강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"];
    var area15 = ["경산시", "경주시", "고령군", "구미시", "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시 남구", "포항시 북구"];
    var area16 = ["거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군", "창원시 마산합포구", "창원시 마산회원구", "창원시 성산구", "창원시 의창구", "창원시 진해구", "통영시", "하동군", "함안군", "함양군", "합천군"];
    var area17 = ["서귀포시", "제주시"];
 
   // 시/도 SELECT BOX 초기화
   $("select[name^=sido]").each(function(){
	   $selsido = $(this);
	   $.each(eval(area0), function(){
		   $selsido.append("<option name='sido' value='" + this + "'>" + this + "</option>");
	   });
	   $selsido.next().append("<option name='gugun' value=''>구/군 선택</option>");
   });
   
   // 시/도 선택시 구/군 설정 
   $("select[name^=sido]").on("change keyup paste", function(){
	   var area = "area" + $("option", $(this)).index($("option:selected",$(this))); //선택지역의 구군 Array
	   $("select[name^=sido] option:eq(" + $("option", $(this)).index($("option:selected",$(this))) + ")").attr("selected",true);
	   var $gugun = $(this).next(); // 선택영역 구군 객체
	   $("option", $gugun).remove(); // 구군 초기화
	   
	   if(area == "area0")
	   	$gugun.append("<option name='gugun' value=''>구/군 선택<option>");
	   else{
		   $.each(eval(area), function(){
			   $gugun.append("<option name='gugun' value='" + this + "'>" + this + "</option>");
		   });
	   }
   });
    
});

//일괄변경할 우편번호 삭제
function fn_mailList_delete(zip){
	$("#mailList").find("#zip_" + zip).remove();
}

//우편번호별 지부지사 일괄변경하기
function updateAll(zipList){
	var insttIdx = $("#modal-instt option:selected").val();
	
	$.ajax({
		url:'${URL_ZIPALLUPDATE}',
		type: 'GET',
		data: {
			insttIdx : insttIdx,
			zipList : zipList.toString()
		},
		success: function(data) {
			var result = data.result;
			if(result != 0){
				alert("우편번호별 지부지사가 일괄로 변경되었습니다.");
				$("#modal-instt option:eq(0)").prop("selected", true);
		    	$("#mailList").empty();
		        $("#modal-action01").hide();
		        $(".mask").fadeOut(150);
		        location.reload();
			} else {
				alert("우편번호별 지부지사 일괄 변경에 실패했습니다.");
			}
		},
		error: function(e) {
			alert(JSON.stringify(e));
		}
	});
}


//삭제
function fn_listDeleteFormSubmit(theFormId, theAction){
	try {
		if(!fn_isValFill(theFormId) || !fn_isValFill(theAction)) return false;
		// 선택
		if(!fn_checkElementChecked("select", "삭제")) return false;
		// 삭제여부
		var varConfirm = confirm("우편번호를 삭제하시면 기업 매칭에 오류가 발생할 수 있습니다. 정말로 삭제하시겠습니까?");
		if(!varConfirm) return false;
		
		$("#" + theFormId).attr("action", theAction);
		$("input[name='select']").prop("disabled", false);
		$("#" + theFormId).submit();
	}catch(e){}
	
	return true;
}


function fn_insttInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_insttInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}



</script>
<%@ include file="../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
var arr = [];
$(function(){
	// 주치의 소속기관 변경여부 확인
	var isChange = "${isChange}";
	
	if(isChange == 1){
		alert("해당 주치의의 소속기관이 변경되었습니다. 공단소속을 확인하고 관할구역을 변경해 주세요.");
		$("#blockCode1").focus();
	}
	
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
	$("#open-modal01").on("click", function() {
        $(".mask").fadeIn(150, function() {
            $("#modal-action01").show();
        });
    });

    $("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
	$(".fn-search-name").click(function(){
		searchBtn();
	});
	
	// reset
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_sampleInputReset();
		}catch(e){alert(e); return false;}
	});
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
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
	
	// 전화번호 입력
	$("#<c:out value="${param.inputFormId}"/> #doctorTel").keydown(function(event){
		fn_preventHan(event, $(this));
		return fn_onlyNumDash(event, $(this));
	});
	$("#<c:out value="${param.inputFormId}"/> #doctorTel").attr("placeholder", "숫자와'-'만 입력해 주세요");
	
	// 주치의 요건이 하나라도 Y이면 Y, 아니면 N으로 설정
	$("[id^='isSatisfy']").change(function (){
		if($("[id^='isSatisfy']:checked").length == 4 && $("[id^='isSatisfy']:checked[value='1']").length != 0){
			$("#doctorYn").val("Y");
		} else if($("[id^='isSatisfy']:checked").length == 4 && $("[id^='isSatisfy']:checked[value='1']").length == 0) {
			$("#doctorYn").val("N");
		}
	});
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		var check = 0;
		var isChecked = false;
		try {
			// 기본정보 확인 
			var submitType = "${submitType}";
			if(submitType == "write" && !$("#userName").val()){
				alert("기본정보를 입력해 주세요.");
				$("#open-modal01").focus();
				return false;
			} 
			// 주치의 요건을 모두 체크했는지 확인
			$("ul.form-raido-list01 li").each(function (){
				var checkRadio = $(this).find("input[type='radio']:checked")
				if(checkRadio.length == 0){
					check++;
				}
			});
			if(check != 0){
				alert("주치의 요건의 모든 항목에 예/아니오를 체크해 주세요.");
				return false;
			}
			
			// 관할구역을 체크했는지 확인
			$("input[type='checkbox'][id^='blockCode']").each(function (){
				if($(this).is(":checked")){
					isChecked = true;
					return false;
				}
			});
			if(!isChecked) {
				alert("관할구역을 선택해 주세요.");
				return false;
			}
			
			// 선택 안한 관할구역 중 배정된 주치의가 없는 군구가 있다면 저장 안됨
			var blockName = "";
			var isCheck = true;
			$("input[type='checkbox']:not(:checked)").each(function(index, element) {
				var count = $(this).attr("data-count");
				if(count == '0'){
					var itemId = $(this).attr("id");
					// 배정된 주치의가 없는 군구를 한번에 보여주기 위해 텍스트로 붙이기
					if(index != $("input[type='checkbox']:not(:checked)").length - 1){
						blockName += $("label[for='" + itemId + "']").text() + ", ";
					} else {
						blockName += $("label[for='" + itemId + "']").text();
					}
					isCheck = false;
				}
			});
			
			if(!isCheck){
				alert(blockName + "는 현재 배정된 주치의가 없습니다. 관할구역으로 추가해 주세요.");
				return false;
			}
			
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	 var submitType = "${submitType}";
	 
	 if(submitType == "modify"){
		// 주치의 관할구역을 뺄 때 해당 관할구역에 배정된 주치의가 본인만 있다면 빼는거 불가
		$("[id^='blockCode']").click(function() {
			if(!$(this).is(':checked')){
				// 해당 군구에 배정된 전제 주치의 수
				var count = $(this).attr("data-count");
				if(count == '1'){
					// 해당 군구에 배정된 전체 주치의 수가 1이고 거기 체크가 되어있다 >> 본인만 해당 군구의 주치의 >> 관할구역에서 뺄 수 없음
					var itemId = $(this).attr("id");
					var blockName = $("label[for='" + itemId + "']").text();
					//alert(blockName + "는 지정된 다른 주치의가 없습니다. 관할구역에 " + blockName + "는 반드시 포함되어야 합니다.");
					alert(blockName + "는 관할 제외 불가능 구역입니다.(구역 내 유일한 주치의)");
					return false;
				}
			}
		});
	 }
});


function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	fn_createMaskLayer();
	return true;
}

//전화번호 입력 
function fn_onlyNumDash(e, theObj) {

	if(e.shiftKey) {
		return false;
	}

	var varKeyCode = e.keyCode;
	var varEventElement = theObj;
	var varVal = $(varEventElement).val();
	if(varKeyCode == 37 || varKeyCode == 39) return true;
	if (varVal.trim() == '' && varKeyCode == 189 || (varKeyCode != 189 && varKeyCode != 109 && varKeyCode != 0 && varKeyCode != 8 && varKeyCode != 9 && varKeyCode != 13 && 
			(varKeyCode != 45 && varKeyCode != 46 && (varKeyCode < 48 || varKeyCode > 57 && varKeyCode < 96 || varKeyCode > 105) || 
			((varVal == '' || varVal.indexOf('-') != -1) && varKeyCode == 46) || 
			(varVal != '' && varKeyCode == 46) /*||
			(varVal == '0' && varKeyCode != 46)*/))){
		return false;
	}
	
	return true;
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

function doctorCheck(){
	
}

function selectBtn(memberIdx, insttIdx) {
	// var insttIdx = arr[6];
// 	console.log(arr);
	var userInfo;
	var itemList;
	var doctorList;
	$.ajax({
		url:'${URL_BLOCKLIST}',
		type: 'GET',
		data: {
			searchText1 : memberIdx,
			searchText2 : insttIdx
		},
		success: function(data) {
			userInfo = data.userInfo;
			itemList = data.blockList;
			doctorList = data.doctorList;
			
			$("#instt").text(itemList[0].INSTT_NAME);
			$("#insttIdx").val(itemList[0].INSTT_IDX);
			let html = "";
			html += '<dl><dt>' + itemList[0].SIDO + '</dt><dd style="display: flow;"><div class="input-checkbox-wrapper ratio type02">';
			
			// 기본정보 채워넣기
		    $("#memberIdx").val(userInfo[0].USERID);
		    $("#userName").val(userInfo[0].HNAME);
		    $("#loginId").text(userInfo[0].LOGINID);
		    $("#email strong").text(userInfo[0].EMAIL);
		    $("#address").text(userInfo[0].ADDRESS);
		    $("#insttIdx").val(userInfo[0].INSTT_IDX);
		    
			if(itemList != null && itemList.length > 0){
				
				for(var i = 0; i < itemList.length; i++){
					if(i < itemList.length -1){//추가
						if(itemList[i].PARENT_IDX == itemList[0].PARENT_IDX){
							//i가 인덱스의 마지막일때 i+1는 없음..(예 : itemList의 길이가 6이고 i가 5일때 i를 넣으면 itemList[5]=> 정상 / itemList의 길이가 6이고 i가 5일때 i+1을 넣으면 itemList[6] => undefined )
							if(itemList[i].PARENT_IDX == itemList[i+1].PARENT_IDX){
								html += '<div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div>'	
							} else {
								html += '<div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div></div></dd></dl>'
							}
						} else {
							if(itemList[i].PARENT_IDX != itemList[i-1].PARENT_IDX){
								html += '<dl><dt>' + itemList[i].SIDO + '</dt><dd style="display: flow;"><div class="input-checkbox-wrapper ratio type02"><div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div>'	
							} else if(i < itemList.length) {
								html += '<div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div>'
							} else {
								html += '<div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div></div></dd></dl>'
							}
						} 
					} else {
						if(itemList[i].PARENT_IDX != itemList[i-1].PARENT_IDX){
							html += '<dl><dt>' + itemList[i].SIDO + '</dt><dd style="display: flow;"><div class="input-checkbox-wrapper ratio type02"><div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div></div></dd></dl>'
						} else {
							html += '<div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode' + (i+1) + '" class="checkbox-type01 margin10" value="' + itemList[i].BLOCK_CD +'" data-count="' + doctorList[i].DOCTOR_COUNT + '"><label for="blockCode' + (i+1) + '">' + itemList[i].CLASS_NAME + '</label></div></div></dd></dl>'
						}
					}
				}
			} else {
				
			}
			$("#blockList").empty();
			$("#blockList").append(html);
			
			hideLoading();
		},
		error: function() {
			alert("지부지사 검색에 실패하였습니다.");
		}
});
    $("#modal-action01").hide();
    $(".mask").fadeOut(150);
}
/**
*	회원 검색
*/
function searchBtn() {
	var bplNm =  document.querySelector('#hName');
	let check1 = bplNm.value.length;
		
	if(check1 != 0) {
				
	$("#empty").hide();
	showLoading();
	$.ajax({
			url:'${URL_MEMBERLIST}',
			type: 'GET',
			data: {
				searchText1 : bplNm.value,
			},
			success: function(data) {
				
				let listItem = data.memberList;
				totalCnt = data.totalCnt;
				
				$("#searchList").empty();
				$("#infoBox").empty();
				
				let html = "";
				
				if(listItem != null && listItem.length > 0) {
					listItem.forEach(function(item,idx){
						var index = idx + 1;
						var memberIdx = item.USERID;
						var hName = item.HNAME;
						var loginId = item.LOGINID;
						var tel = item.PHONE;
						var email = item.EMAIL;
						var address = item.ADDRESS;
						var insttIdx = item.INSTT_IDX;
						
// 						arr[idx] = [memberIdx, hName, loginId, tel, email, address, insttIdx];
						html =	'<tr><td>' + index + '</td>' +
								'<td><button type="button" id="showDocInfo" class="checkbox-type02" onclick="selectBtn(' + memberIdx + ',' + insttIdx + ' );"></button></td>' +
								'<td>' + hName + '</td>' +
								'<td>' + loginId + '</td></tr>'
						$("#searchList").append(html);
					});
				} else {
					html = `<tr><td colspan="4">검색된 회원이 없습니다. </td></tr>`;
					$("#searchList").append(html);
				}
				$("#cnt").text(totalCnt);
				
				
				hideLoading();
			},
			error: function() {
				alert("회원 검색에 실패하였습니다.");
			}
	});
			
	} else {
		alert("회원 이름을 입력해주세요.");
	}
}
</script>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="itemOrderName" value="develop_info_column_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<style>
	#overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.5);
		display: none;
		z-index: 9999;
	}
	
	.loader {
		border:4px solid #f3f3f3;
		border-top: 4px solid #3498db;
		border-radius: 50%;
		width: 50px;
		height: 50px;
		animation: spin 2s linear infinite;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 10000;
	}
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
	
	.checkbox-height {
		height: 35px;
	}
</style>
<div class="mask2"></div>
<div id="overlay" style="display:none;"></div>
<div class="loader" style="display:none;"></div>
<div class="modal-wrapper type02" id="modal-action02">
	<h2>AI추천 훈련과정</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="basic-search-wrapper mt20 mb50" id="searchBar">
				<div class="one-box">
					<dl>
						<dt><label for="prtbizIdxBox" class="ml20 mr20">사업구분</label></dt>
						<dd><select id="prtbizIdxBox" name="사업구분 셀렉트박스" class="w100"></select></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><label for="ncsBox" class="ml20 mr20">NCS대분류</label></dt>
						<dd>
							<select id="ncsBox" name="NCS대분류 셀렉트박스" class="w100">
							</select>
						</dd>
					</dl>
				</div>
			</div>
			<div class="table-type01 horizontal-scroll">
				<table class="width-type02">
					<colgroup>
						<col style="width:12%">
						<col style="width:23%">
						<col style="width:auto">
					</colgroup>
					<thead>
					<tr>										
						<th scope="col">연번</th>
						<th scope="col">사업</th>
						<th scope="col">훈련과정</th>
					</tr>
					</thead>
					<tbody class="alignC" id="trainingList">							
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<button type="button" class="btn-modal-close" data-idx="6">모달 창 닫기</button>
</div>
<script type="text/javascript">
$(function(){
	// 모달창 열기
	$("#open-modal02").on("click", function() {
   		try {
   			//모달창을 껐다가 다시 켰을때 이전값이 남아있지 않게 초기화
   			$("#trainingList").empty();	
   			$("#prtbizIdxBox option").remove();
   			$("#ncsBox option").remove();
   			showLoading();
   			$.ajax({
   				url:'${DEVELOP_WRITE_FORM_URL}',
   				type: 'GET',
   				data: {
   					isAjax : 1,
   					bsiscnslIdx : '${bsisCnsl}'
   				},success: function(data) {
   					var trainingDt = data.trainingRecommend;
   					
   					//url에서 선택한 prtbizIdx를 가져온다.
   					const link = document.location.href;
   					const url = new URL(link);
   					const urlParams = url.searchParams;
   					var prtbizIdx = urlParams.get('prtbizIdx');
   					
   					var prtbizList = [];
   					var ncsList = [];   					
   					
   					var innerHtml = "";
   					if(trainingDt.length == 0){
   						innerHtml = '<tr><td colspan="3" class="bllist">추천 받은 훈련과정이 없습니다.</td></tr>';
   					}
   					
   					//모든 값 세팅전 셀렉트박스에 [전체]를 세팅한다.
   					$("#ncsBox").append("<option value=''>전체</option>" );
   					var index = 1;
   					
   					for(var i = 0; i < trainingDt.length; i++){
   						//훈련과정명이 존재하고 사업추천은 3위까지만 보여준다
   						if (trainingDt[i].TP_NAME != ('' || undefined) && trainingDt[i].RANK < 4){
   							
   							//사업구분 셀렉트박스 세팅
	   						if(i == 0){  
   								$("#prtbizIdxBox").append("<option value='" + trainingDt[i].PRTBIZ_IDX + "'>" + trainingDt[i].RCTR_NAME + "</option>")   							   								   							
	   						}
	   						//셀렉트박스의 모든 사업구분값을 가져온 후 리스트로 뽑는다.
	   						var prtbizValues = $("#prtbizIdxBox").find('option').map(function(){return $(this).val();}).get();
	   						//루프문을 돌며 해당 리스트안에 동일한 값이 있는지 체크한다. 
	   						if(i > 0 && prtbizValues.indexOf(trainingDt[i].PRTBIZ_IDX) < 0){	   							
	   							$("#prtbizIdxBox").append("<option value='" + trainingDt[i].PRTBIZ_IDX + "'>" + trainingDt[i].RCTR_NAME + "</option>")
	   						}
	   										
	   						
	   						//연번, 사업명, 훈련과정명 세팅
	   						if(trainingDt[i].PRTBIZ_IDX == prtbizIdx){ 
		   						
	   							//NCS소분류 셀렉트박스 세팅
	   							if (i == 0){			   	
	   								//첫번째 추천항목에 NCS코드가 없는 경우 그다음 인덱스의 ncs코드를 넣는다. (비교용)
	   								for(var j=0; j < trainingDt.length; j++){
	   									if(trainingDt[j].NCS_CD != '' && trainingDt[j].NCS_CD != undefined ){							
	   										$("#ncsBox").append("<option value='" + (trainingDt[j].NCS_CD).substring(0,6) + "'>" + trainingDt[j].NCS_NM + "</option>");	   										
	   										break;
	   									}
	   								}
	   							}
		   						
								//셀렉트박스의 모든 ncs값을 가져온 후 리스트로 뽑는다.
		   						var ncsValues = $("#ncsBox").find('option').map(function(){return $(this).val();}).get();	   									   					
		   						//루프문을 돌며 해당 리스트안에 동일한 값이 있는지 체크한다. 
		   						if(i > 0 && ncsValues.indexOf((trainingDt[i].NCS_CD).substring(0,6)) < 0){		   							
		   							$("#ncsBox").append("<option value='" + (trainingDt[i].NCS_CD).substring(0,6) + "'>" + trainingDt[i].NCS_NM + "</option>")
		   						}   				
	   							
		   						innerHtml += '<tr><td>'+ index +'</td>';
		   						innerHtml += '<td>'+ (trainingDt[i].RCTR_NAME  == undefined ? '' : trainingDt[i].RCTR_NAME.replace('[', '<br>[')) +'</td>';
		   						if(trainingDt[i].TP_NAME != undefined){
									innerHtml += '<td><strong class="point-color01"><a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx='+trainingDt[i].TP_IDX+'&prtbizIdx='+ trainingDt[i].PRTBIZ_IDX +'&isFromDevelop=Y" class="btn-linked" style="display:inline;" target="_blank">'+ trainingDt[i].TP_NAME +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';						   							
		   						}else{
		   							innerHtml += '<td></td>'
		   						}
		   						index++;
	   						}
	   						
   						}
   						
   					}
   					$("#trainingList").append(innerHtml);
   					$("#prtbizIdxBox option[value='" + prtbizIdx + "']").prop("selected", true);
   					var prtbizValues = $("#prtbizIdxBox").find('option').map(function(){return $(this).val();}).get();
   					
   					//사업추천 3위안에 훈련과정이 아무것도 없을 시 해당없음으로 세팅한다.
   					if(prtbizValues.length == 0){
   						$("#ncsBox option").remove();
   						$("#ncsBox").append("<option value=''>해당없음</option>" ); 
   						$("#prtbizIdxBox option").remove();
   						$("#prtbizIdxBox").append("<option value=''>해당없음</option>" ); 
   						innerHtml += '<tr><td colspan="3">해 당 없 음</td></tr>';
   						$("#trainingList").append(innerHtml);
   						
   					}
   					selectBoxChange(trainingDt);
   					hideLoading();
   				},
   				error: function(e) {
   					alert("AI훈련추천 결과를 불러오는데 실패했습니다.");
   	  				hideLoading();   	  				
   				}
   			});
   			}catch(e){return false;}
        $(".mask2").fadeIn(150, function() {        	        	
        	$("#modal-action02").show();
        	
        });
    });
	
	
	// 모달창 닫기
	$("#modal-action02 .btn-modal-close").on("click", function() {
        $("#modal-action02").hide();
        $(".mask2").fadeOut(150);
    });
	
	function showLoading() {
		const loader = document.querySelector('.loader');
		const overlay = document.getElementById('overlay');
		loader.style.display = 'block';
		overlay.style.display = 'block';
		
	}


	function hideLoading() {
		const loader = document.querySelector('.loader');
		const overlay = document.getElementById('overlay');
		loader ? loader.style.display = 'none' : null;
		overlay ? overlay.style.display = 'none' : null;
		
	}	
});

function selectBoxChange(trainingDt){	
	$("#prtbizIdxBox").change(function(){
		var index = 1;		
		var selectedPrtbizIdx = $("#prtbizIdxBox").val();
		
		var innerHtml = "";
		$("#trainingList").empty();	
		$("#ncsBox option").remove();
		$("#ncsBox").append("<option value=''>전체</option>" );   							   							   							
		
		for(var i = 0; i < trainingDt.length; i++){
			//사업구분을 변경했을 시 선택한 사업구분과 리스트의 사업구분을 비교하여 동일한 사업만 보여준다.
			if(trainingDt[i].PRTBIZ_IDX == selectedPrtbizIdx && trainingDt[i].TP_NAME != ('' || undefined) && trainingDt[i].RANK < 4){				
					innerHtml += '<tr><td>'+ index +'</td>';
					innerHtml += '<td>'+ (trainingDt[i].RCTR_NAME  == undefined ? '' : trainingDt[i].RCTR_NAME.replace('[', '<br>[')) +'</td>';
				if(trainingDt[i].TP_NAME != undefined){
					innerHtml += '<td><strong class="point-color01"><a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx='+trainingDt[i].TP_IDX+'&prtbizIdx='+ trainingDt[i].PRTBIZ_IDX +'&isFromDevelop=Y" class="btn-linked" style="display:inline;" target="_blank">'+ trainingDt[i].TP_NAME +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';						   							
				}else{
					innerHtml += '<td></td>'
				}
				
				//NCS소분류 셀렉트박스 세팅
				if (i == 0){			   	
					//첫번째 추천항목에 NCS코드가 없는 경우 그다음 인덱스의 ncs코드를 넣는다. (비교용)
					for(var j=0; j < trainingDt.length; j++){
						if(trainingDt[j].NCS_CD != '' && trainingDt[j].NCS_CD != undefined ){							
							$("#ncsBox").append("<option value='" + (trainingDt[j].NCS_CD).substring(0,6) + "'>" + trainingDt[j].NCS_NM + "</option>");
							
							break;
						}
					}
				}
				//셀렉트박스의 모든 ncs값을 가져온 후 리스트로 뽑는다.
				var ncsValues = $("#ncsBox").find('option').map(function(){return $(this).val();}).get();	   									   					
				//루프문을 돌며 해당 리스트안에 동일한 값이 있는지 체크한다. 
				if(i > 0 && ncsValues.indexOf((trainingDt[i].NCS_CD).substring(0,6)) < 0){						
					$("#ncsBox").append("<option value='" + (trainingDt[i].NCS_CD).substring(0,6) + "'>" + trainingDt[i].NCS_NM + "</option>")
				}   
				index++;
			}
		}
		$("#trainingList").append(innerHtml);
		
		var trLength = $("#trainingList tr").length;
		if(trLength == 0){
			innerHtml += '<tr><td colspan="3">해당하는 훈련과정이 없습니다.</td></tr>';
			$("#trainingList").append(innerHtml);
		}
	});
	
	
	
	
	
	$("#ncsBox").change(function(){
		var index = 1;
		var selectedPrtbizIdx = $("#prtbizIdxBox").val();
		var selectedNcs = $("#ncsBox").val();
		
		var innerHtml = "";
		$("#trainingList").empty();								   							   							
		
		for(var i = 0; i < trainingDt.length; i++){														
			//선택한 ncs대분류에 해당하는 훈련과정만 보여준다
			if(trainingDt[i].PRTBIZ_IDX == selectedPrtbizIdx && trainingDt[i].TP_NAME != ('' || undefined) && trainingDt[i].RANK < 4){
				
				if(selectedNcs != ''){
					//전체가 아닐때
					if((trainingDt[i].NCS_CD).substring(0,6) == selectedNcs){
						innerHtml += '<tr><td>'+ index +'</td>';
						innerHtml += '<td>'+ (trainingDt[i].RCTR_NAME  == undefined ? '' : trainingDt[i].RCTR_NAME.replace('[', '<br>[')) +'</td>';
						if(trainingDt[i].TP_NAME != undefined){
						innerHtml += '<td><strong class="point-color01"><a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx='+trainingDt[i].TP_IDX+'&prtbizIdx='+ trainingDt[i].PRTBIZ_IDX +'&isFromDevelop=Y" class="btn-linked" style="display:inline;" target="_blank">'+ trainingDt[i].TP_NAME +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';						   							
						}else{
							innerHtml += '<td></td>'
						}
						index++;
					}					
				}else{
					//전체일때
					innerHtml += '<tr><td>'+ index +'</td>';
					innerHtml += '<td>'+ (trainingDt[i].RCTR_NAME  == undefined ? '' : trainingDt[i].RCTR_NAME.replace('[', '<br>[')) +'</td>';
					if(trainingDt[i].TP_NAME != undefined){
					innerHtml += '<td><strong class="point-color01"><a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx='+trainingDt[i].TP_IDX+'&prtbizIdx='+ trainingDt[i].PRTBIZ_IDX +'&isFromDevelop=Y" class="btn-linked" style="display:inline;" target="_blank">'+ trainingDt[i].TP_NAME +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';						   							
					}else{
						innerHtml += '<td></td>'
					}
					index++;	
				}
			}
			
		}
		$("#trainingList").append(innerHtml);
		
		var trLength = $("#trainingList tr").length;
		if(trLength == 0){
			innerHtml += '<tr><td colspan="3">해당하는 훈련과정이 없습니다.</td></tr>';
			$("#trainingList").append(innerHtml);
		}
	});
}


</script>
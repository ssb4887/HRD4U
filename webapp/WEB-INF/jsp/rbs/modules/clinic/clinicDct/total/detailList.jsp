<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
var pageNumber = 1;
var index = 1;
$(function(){
	$(".contents-title").addClass("hide");
	
	
	$("#search, #searchAll").click(function(){
		try {
			var searchAllFlag = $(this).val();
			var is_year;
			var is_essntl;
			var is_actType;
			if(searchAllFlag == "search"){				
				 is_year = $("#is_year option:selected").val();
				 is_essntl = $("#is_essntl option:selected").val();
				 is_actType = $("#is_actType option:selected").val();
			}else{
				 is_year = '0';
				 is_essntl = '0';
				 is_actType = '0';
				$('#is_year').val("0").prop("checked", true);
				$('#is_essntl').val("0").prop("checked", true);
				$('#is_actType').val("0").prop("checked", true);
			}
			
			$.ajax({
				url:'${TOTAL_DETAIL_FORM_URL}',
				type: 'GET',
				data: {
					bpl_no : '${corpInfo.BPL_NO}',
					is_year : is_year,
					is_essntl : is_essntl,
					is_actType : is_actType,
					isAjax : 1
				},success: function(data) {
					var activityList = data.activityList;
					var innerHtml = "";
					$("#activityList").empty();	
					if(activityList.length == 0){
						innerHtml = '<tr><td colspan="10" class="bllist">등록된 내용이 없습니다.</td></tr>';
					}
					for(var i = 0; i < activityList.length; i++){
						innerHtml += '<tr><td class="bg01">'+ (activityList[i].ESSNTL_SE_NM == undefined ? '' : activityList[i].ESSNTL_SE_NM) +'</td>';
						innerHtml += '<td class="bg02">'+ (activityList[i].GUBUNNM == undefined ? '' : activityList[i].GUBUNNM) +'</td>';
						innerHtml += '<td>'+ (activityList[i].SPORT_ITEM_NM == undefined ? '' : activityList[i].SPORT_ITEM_NM) +'</td>';
						innerHtml += '<td>'+ (activityList[i].CONFM_STATUS_NM == undefined ? '' : activityList[i].CONFM_STATUS_NM)  +'</td>';
						innerHtml += '<td>'+ (activityList[i].YEARLY == undefined ? '' : activityList[i].YEARLY) +'년</td>';
						innerHtml += '<td>'+ (activityList[i].REGI_DATE == undefined ? '' : activityList[i].REGI_DATE) +'</td>';
						if(activityList[i].GUBUNCD == 'acmslt' || activityList[i].SPORT_ITEM_CD == '07'){							
							innerHtml += '<td><strong class="point-color01"><a href="/${crtSiteId}/clinic/activity_view_form.do?mId=68&acmsltIdx=' + activityList[i].IDX + '" class="btn-linked" style="display:inline;" target="_blank">'+ '활동 일지 보기' +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';
						}else if(activityList[i].GUBUNCD == 'plan'){
							innerHtml += '<td><strong class="point-color01"><a href="/${crtSiteId}/clinic/plan_view_form.do?mId=67&planIdx=' + activityList[i].IDX + '" class="btn-linked" style="display:inline;" target="_blank">'+ '훈련 계획서 보기' +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';
						}else if(activityList[i].GUBUNCD == 'rslt'){
							innerHtml += '<td><strong class="point-color01"><a href="/${crtSiteId}/clinic/result_view_form.do?mId=87&resltIdx=' + activityList[i].IDX + '" class="btn-linked" style="display:inline;" target="_blank">'+ '성과 보고서 보기' +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';
						}else if(activityList[i].GUBUNCD == 'cnsl'){
							innerHtml += '<td><strong class="point-color01"><a href="/${crtSiteId}/consulting/viewAll.do?mId=95&cnslIdx=' + activityList[i].IDX + '" class="btn-linked" style="display:inline;" target="_blank">'+ (activityList[i].SPORT_ITEM_NM == undefined ? '' : activityList[i].SPORT_ITEM_NM) +'&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;"></a></strong></td>';
						}else{
							innerHtml += '<td></td>';
						}
						innerHtml += '<td>'+ (activityList[i].SUPPORT_COST == undefined ? '0' : activityList[i].SUPPORT_COST.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')) + '원</td>';
						innerHtml += '<td>'+ (activityList[i].SPRMNY_REQST_YN == undefined ? '' : activityList[i].SPRMNY_REQST_YN) +'</td>';
						innerHtml += '<td>'+ (activityList[i].SPRMNY_PYMNT_YN == undefined ? '' : activityList[i].SPRMNY_PYMNT_YN) +'</td></tr>';
					}
					var sumCost = 0;
					for(var i = 0; i < activityList.length; i++){
						sumCost += activityList[i].SUPPORT_COST;
					}					
					innerHtml += '<tr><td colspan="8">합계</td><td colspan="2">'	+ sumCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '원</td></tr>';
					$("#activityList").append(innerHtml);
				},
				error: function(e) {
					alert("검색에 실패했습니다.");
				}
			});
		}catch(e){return false;}
	});	
});
</script>
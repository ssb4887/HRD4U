<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
var pageNumber = 1;
var index = 1;
$(function(){
	
	//팝업 테스트
// 	alert("${RESULT_VIEW_FORM_URL}&resltIdx=${resultProgress[0].RESLT_IDX}");
// 	window.open("${contextPath}/${crtSiteId}/clinic/result_view_form.do?mId=87&resltIdx=${resultProgress[0].RESLT_IDX}","팝업 테스트","width=1000,height=1000")
	
	//호출 주석
// 	render_recommend_list();	
	
// 	function render_recommend_list() {
// 		alert("!")


// 		$.ajax({
// 			url:'${RECOM_BIZ_URL}',
// 			type: 'GET',
// 			data: {
// 				bsiscnsl_idx : '426',
// 			},success: function(data) {
// 				alert(JSON.stringify(data.names))
// 				for(var i = 0; i < data.names.length; i++){
// 					alert(data.names[i].CONSIDER)
// 				}
// 			},
// 			error: function(e) {
// 				alert("검색에 실패했습니다.");
// 			}
// 		});
// 	}
	
	
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
				url:'${DASHBOARD_LIST_FORM_URL}',
				type: 'GET',
				data: {
					is_year : is_year,
					is_essntl : is_essntl,
					is_actType : is_actType,
					isAjax : 1
				},success: function(data) {
					var activityList = data.activityList;
					var innerHtml = "";
					$("#activityList").empty();	
					if(activityList.length == 0){
						innerHtml = '<tr><td colspan="8" class="bllist">등록된 내용이 없습니다.</td></tr>';
					}
					for(var i = 0; i < activityList.length; i++){
						innerHtml += '<tr><td class="bg01">'+ (activityList[i].ESSNTL_SE_NM == undefined ? '' : activityList[i].ESSNTL_SE_NM) +'</td>';
						innerHtml += '<td class="bg02">'+ (activityList[i].GUBUNNM  == undefined ? '' : activityList[i].GUBUNNM) +'</td>';
						innerHtml += '<td>'+ (activityList[i].SPORT_ITEM_NM == undefined ? '' : activityList[i].SPORT_ITEM_NM) +'</td>';
						innerHtml += '<td>'+ (activityList[i].CONFM_STATUS_NM == undefined ? '' : activityList[i].CONFM_STATUS_NM) +'</td>';
						innerHtml += '<td>'+ (activityList[i].YEARLY == undefined ? '' :  activityList[i].YEARLY) +'년</td>';
						innerHtml += '<td>'+ (activityList[i].ACT_DATE == undefined ? '-' :  activityList[i].ACT_DATE)  +'</td>';
						innerHtml += '<td>'+ (activityList[i].SPRMNY_REQST_YN == undefined ? '' :  activityList[i].SPRMNY_REQST_YN)  +'</td>';
						innerHtml += '<td>'+ (activityList[i].SPRMNY_PYMNT_YN == undefined ? '' :  activityList[i].SPRMNY_PYMNT_YN)  +'</td></tr>';
					}
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
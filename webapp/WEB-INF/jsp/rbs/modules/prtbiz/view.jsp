<%@ include file="../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
	var prtbizIdx = "${dt.PRTBIZ_IDX}";
	
	// 현재 날짜 setting
	var today = new Date();
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	var dateString = year + '-' + month + '-' + day;
	
	loadTp(prtbizIdx, 1, dateString);
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			var fnIdx = ${crtMenu.fn_idx};
			var deleteMessage = "";
			var isPrtbiz = $(this).attr("href").indexOf("mId=90");
			if(fnIdx == 1 && isPrtbiz == -1){
				deleteMessage = "참여가능사업을 삭제하면 관련된 훈련과정도 모두 삭제됩니다. 해당 참여가능사업을 삭제하시겠습니까?";
			} else {
				deleteMessage = "해당 훈련과정을 삭제하시겠습니까?";
			}
			var varConfirm = confirm(deleteMessage);
			if(!varConfirm) return false;
		}catch(e){return false;}
		return true;
	});
	
	// 참여가능사업 상세보기 페이지에서 훈련과정 삭제
	$(".fn_btn_delete2").click(function(){
		try {
			fn_listDeleteFormSubmit("fn_techSupportListForm", $(this).attr("href"));
		}catch(e){}
		return false;
	});
	// 훈련과정 페이지 이동
	$(".paging-navigation").on("click", "a[id^='page-']", function () {
		const page = parseInt($(this).attr("data-page"), 10);
		// 페이지 이동 전 기존 내용 지우기
		$("#tpListBody").empty();
		$(".paging-navigation").empty();
		// 목록 다시 채우기
		loadTp(prtbizIdx, page, dateString);
	});
 	
});

//삭제
function fn_listDeleteFormSubmit(theFormId, theAction){
	try {
		if(!fn_isValFill(theFormId) || !fn_isValFill(theAction)) return false;
		// 선택
		if(!fn_checkElementChecked("select", "삭제")) return false;
		// 삭제여부
		var fnIdx = ${crtMenu.fn_idx};
		var deleteMessage = "해당 훈련과정을 삭제하시겠습니까?";
		var isPrtbiz = theAction.indexOf("mId=90");
		var varConfirm = confirm(deleteMessage);
		if(!varConfirm) return false;
		
		$("#" + theFormId).attr("action", theAction);

		$("input[name='select']").prop("disabled", false);
		$("#" + theFormId).submit();
	}catch(e){}
	
	return true;
}

function loadTp(prtbizIdx, page, dateString) {
	var tpList;
	var pageInfo;
	$.ajax({
		url: '${URL_TPLIST}',
		type: 'GET',
		data: {
			page : page,
			prtbizIdx : prtbizIdx
		},
		success: function(data) {
			tpList = data.tpList;
			pageInfo = data.pageInfo;
			
			// 전체 갯수 및 현재 페이지 설정
			var curPage = pageInfo.currentPageNo + "/" + pageInfo.totalPageCount;
			$(".total strong").text(pageInfo.totalRecordCount);
			$("#pageInfo").text(curPage);
			
			// 훈련과정 setting
			let path = "${contextPath}/${crtSiteId}";
			let listNo = pageInfo.totalRecordCount - pageInfo.firstRecordIndex;
			if(tpList != null){
				for(var i = 0; i < tpList.length; i++){
					let regi = tpList[i].REGI_DATE_FORMAT;
					let img = "";
					
					if(regi != undefined){
						if(regi.toString() == dateString){
							img = '<img src="${contextPath}/${crtSiteId}/images/board/icon_new01.png" alt="new" class="icon-new">';
						}
					}
					$("#tpListBody").append('<tr><td><input type="checkbox" id="select" name="select" title="선택" value="' + tpList[i].TP_IDX + '" class="checkbox-type01"/></td>' +
											'<td class="num">' + listNo + '</td>' +
											'<td><a href="' + path + '/prtbiz/input.do?mId=90&mode=m&tpIdx=' + tpList[i].TP_IDX + '" class="btn-m03 btn-color03 fn_btn_modify">수정</a></td>' + 
											'<td><a href="' + path + '/prtbiz/view.do?mId=90&tpIdx=' + tpList[i].TP_IDX + '" class="fn_btn_view"><strong class="title">' + tpList[i].TP_NAME + '</strong></a>&nbsp;' + img + 
											'<td>' + tpList[i].TP_OPRINST + '</td>' + 
											'<td>' + tpList[i].NCS_NAME + '(' + tpList[i].NCS_SCLAS_CD + ')</td></tr>');
					listNo--;
				}
				// 페이지 네비게이션
				if(pageInfo.currentPageNo -1 != 0){
					$(".paging-navigation").append('<a href="#" data-page="' + pageInfo.firstPageNo + '" class="btn-first" id="page-first" title="처음 페이지"></a>' + 
												   '<a href="#" data-page="' + (pageInfo.currentPageNo -1) + '" class="btn-preview" id="page-preview" title="이전 페이지"></a>');
				}
				// 페이지 반복구간
				for(var j = pageInfo.firstPageNoOnPageList; j <= pageInfo.lastPageNoOnPageList; j++){
					if(j == pageInfo.currentPageNo){
						$(".paging-navigation").append('<strong>' + j + '</strong>');
					} else {
						$(".paging-navigation").append('<a href="#" data-page="' + j + '" id="page-' + j + '" title="' + j + '페이지">' + j + '</a>');
					}
				}
				if(pageInfo.totalPageCount > pageInfo.currentPageNo){
					$(".paging-navigation").append('<a href="#" data-page="' + (pageInfo.currentPageNo +1) + '" class="btn-next" id="page-next" title="다음 페이지"></a>' + 
												   '<a href="#" data-page="' + pageInfo.lastPageNo + '" class="btn-last" id="page-last" title="끝 페이지"></a>');
				}
			} else {
				$("#tpListBody").append('<tr><td colspan="6" class="bllist">등록된 내용이 없습니다.</td></tr>');
			}
		},
		error: function() {
			alert("훈련과정 검색에 실패하였습니다.");
		}
	});
}
</script>
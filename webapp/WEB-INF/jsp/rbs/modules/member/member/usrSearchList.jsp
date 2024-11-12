<%@ include file="../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
	fn_window.resizeTo(900);
	
	// 전체 선택/해제 change
	$("#selectAll").change(function(){
		try {
			$("input[name='select']").prop("checked", $(this).prop("checked"));
			if(fn_setAllSelectObjs) fn_setAllSelectObjs(this);
		}catch(e){}
	});
	
	// 선택
	$(".btn-m03").click(function(){
		
		var varChkObjs = $(this);
		var list = new Array();
		list.push(varChkObjs.parent().siblings('td:eq(1)').text());
		list.push(varChkObjs.parent().siblings('td:eq(3)').text());
		list.push(varChkObjs.parent().siblings('td:eq(4)').text());
		list.push(varChkObjs.parent().siblings('td:eq(5)').text());
		opener.fn_setMemberMmbrInfo("<c:out value="${queryString.itemId}"/>", varChkObjs, list);
		self.close();
	});
});
</script>
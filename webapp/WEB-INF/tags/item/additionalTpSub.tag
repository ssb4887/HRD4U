<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="itemId"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="defVal"%>
<%@ attribute name="objVal" type="java.lang.Object"%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>
<%@ attribute name="content"%>
<%@ attribute name="pageContext" type="javax.servlet.jsp.PageContext"%>
<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
<%
	PageContext page = (PageContext)this.getJspContext();
	page.setAttribute("newline","\r\n");
%>
<div class="table-type02">
<table id="tpSubContent">
	<colgroup>
		<col style="width:6%">	
		<col style="width:6%">	
		<col style="width:25%">	
		<col style="width:auto">	
		<col style="width:8%">	
	</colgroup>
	<thead>
        <tr>
            <th scope="row" colspan="5">교과목 프로필</th>
        </tr>
        <tr>
            <th scope="row"></th>
            <th scope="row">연번</th>
            <th scope="row">교과목명&nbsp;<strong class="point-important">*</strong></th>
            <th scope="row">세부내용 단원(과제명)&nbsp;<strong class="point-important">*</strong></th>
            <th scope="row">시간&nbsp;<strong class="point-important">*</strong></th>
        </tr>		
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${useFlag eq 'new'}">
				<tr>
					<td><input type="checkbox" class="checkbox-type01" name="chkbox"></td>
					<td><input type="hidden" value="1" name="courseNo0"><span id="courseNo">1</span></td>
					<td><input type="text" name="courseName0" id="courseName1" class="w100"></td>
					<td><textarea name="cn0" id="cn1" class="w100 h100 textareaCustom"><c:out value="${contentsList[i.count]}" /></textarea></td>
					<td><input type="text" name="time0" id="time1" class="w100 onlyNum"></td>
				</tr>
			</c:when>
			<c:when test="${useFlag eq 'reuse'}">
				<c:choose>
					<c:when test="${!empty tpSubList}">
	               	<c:forEach items="${tpSubList}" varStatus="i">
	                	<tr>
	                		<td>
                				<input type="checkbox" class="checkbox-type01" name="chkbox">
<%-- 	                				<button type="button" id="delete${i.index}" data-idx="${i.index}" class="btn-m02 btn-color02">삭제</button> --%>
	                		</td>
	                		<td> 
	                 			<input type="hidden" value="${i.count}" name="courseNo${i.index}" id="courseNo${i.count}" class="w100"><span id="courseNo">${i.count}</span></td>
	                		<td>
	                			<input type="text" value="${tpSubList[i.index].COURSE_NAME}" name="courseName${i.index}" id="courseName${i.count}" class="w100">
	                		</td>
	                		<td class="left">											
								<textarea name="cn${i.index}" id="cn${i.count}"  class="w100 h100 textareaCustom"><c:out value="${fn:replace(tpSubList[i.index].CN,'<br>',newline)}"></c:out></textarea>
	                		</td>
	                		<td>
	                			<input type="text" value="${tpSubList[i.index].TIME}" name="time${i.index}" id="time${i.count}" class="w100 onlyNum">
	                		</td>
	                	</tr>
	               	</c:forEach>
	               	</c:when>
	               	<c:otherwise>
						<tr>
							<td><input type="checkbox" class="checkbox-type01" name="chkbox"></td>
							<td><input type="hidden" value="1" name="courseNo0"><span id="courseNo">1</span></td>
							<td><input type="text" name="courseName0" id="courseName1" class="w100"></td>
							<td><textarea name="cn0" id="cn1" class="w100 h100 textareaCustom"><c:out value="${contentsList[i.count]}" /></textarea></td>
							<td><input type="text" name="time0" id="time1" class="w100 onlyNum"></td>
						</tr>
					</c:otherwise>
               	</c:choose>
			</c:when>	
			<c:when test="${useFlag eq 'modify'}">
				<c:choose>
					<c:when test="${!empty tpSubList}">
		               	<c:forEach items="${tpSubList }" varStatus="i">
		                	<tr>
		                		<td>
	                				<input type="checkbox" class="checkbox-type01" name="chkbox">
<%-- 		                				<button type="button" id="delete${i.index}" data-idx="${i.index}" class="btn-m02 btn-color02">삭제</button> --%>
		                		</td>
		                		<td> 
		                 			<input type="hidden" value="${i.count}" name="courseNo${i.index}" id="courseNo${i.count}" class="w100"><span id="courseNo">${i.count}</span>
		                		</td>
		                		<td>
		                			<input type="text" value="${tpSubList[i.index].COURSE_NAME}" name="courseName${i.index}" id="courseName${i.count}" class="w100">
		                		</td>
		                		<td class="left">											
									<textarea name="cn${i.index}" id="cn${i.count}"  class="w100 h100 textareaCustom"><c:out value="${fn:replace(tpSubList[i.index].TR_CN,'<br>',newline)}"></c:out></textarea>
		                		</td>
		                		<td>
		                			<input type="text" value="${tpSubList[i.index].TRTM}" name="time${i.index}" id="time${i.count}" class="w100">
		                		</td>
		                	</tr>
		               	</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td><input type="checkbox" class="checkbox-type01" name="chkbox"></td>
							<td><input type="hidden" value="1" name="courseNo0"><span id="courseNo">1</span></td>
							<td><input type="text" name="courseName0" id="courseName1" class="w100"></td>
							<td><textarea name="cn0" id="cn1" class="w100 h100 textareaCustom"><c:out value="${contentsList[i.count]}" /></textarea></td>
							<td><input type="text" name="time0" id="time1" class="w100 onlyNum"></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:when>			
		</c:choose>
	</tbody>
</table>
</div>
<div class="btns-area mt30 w100">
	<button type="button" id="addSub" class="btn-m02 btn-color03 three-select codeLevel">추가</button>
	<button type="button" id="deleteSub" class="btn-m02 btn-color02 three-select">삭제</button>
	<c:choose>
		<c:when test="${!empty tpSubList}">
			<input type="hidden" name="tpSubLength" id="tpSubLength" value="${fn:length(tpSubList) }">
		</c:when>
		<c:otherwise>
			<input type="hidden" name="tpSubLength" id="tpSubLength" value="1">
		</c:otherwise>
	</c:choose>	
</div>

<script type="text/javascript">
	fn_deleteTp();
	fn_sumTrTmByKeyup();
	
	// 과정수정일 때 기존 교과목 프로필의 시간 합계를 훈련시간에 setting
	var useFlag = "${useFlag}";
	if(useFlag == "reuse"){
		fn_sumTrTm();
	}
	
	//훈련내용 입력 행 추가
	$("#addSub").click(function(){
		var index = $("#tpSubContent > tbody > tr").length;
		var length = Number($("#tpSubLength").val());
		
		length += 1;
		$("#tpSubLength").val(length);
		var innerHtml = "";
		//innerHtml += '<tr><td><button type="button" id="delete' + (index -1) + '" data-idx="' + (index -1) + '" class="btn-m02 btn-color02">삭제</button></td>'
		innerHtml += '<tr><td><input type="checkbox" class="checkbox-type01" name="chkbox"></td>'
		innerHtml += '<td><input type="hidden" value="'+ (length) +'" name="courseNo' + (length -1) + '"><span id="courseNo">' + (index + 1) + '</span></td>'
		innerHtml += '<td><input type="text" name="courseName'+ (length -1) +'" id="courseName' + (length) + '" class="w100"></td>' 
		innerHtml += '<td><textarea name="cn'+ (length -1) +'" id="cn' + (length) + '" class="w100 h100 textareaCustom"></textarea></td>'
		innerHtml += '<td><input type="text" name="time'+ (length -1) +'" id="time' + (length) + '" onkeydown="fn_onlyNumNoCopy(event)" onchange="fn_onlyNumNoCopy(event)" class="w100 number"></td></tr>'
		
		$("#tpSubContent > tbody:last").append(innerHtml);
		
		fn_validationTp();
		fn_sumTrTmByKeyup();
	});
	
	function fn_validationTp(){
		$(".number").keyup(function(event) {
			if($(this).val() > 99999){
				alert("99999이하로 입력해 주세요.");
				$(this).val(99999);
			}
		});
	}
	
	// 훈련내용 입력 행 삭제
	function fn_deleteTp(){

		$("#deleteSub").click(function(){
			var index = $("#tpSubContent > tbody > tr").length;
			if(($("#tpSubContent > tbody > tr").length - $("#tpSubContent tr input[name=chkbox]:checked").length) < 1){
				alert("최소 하나의 교과목 행이 있어야 합니다.")
				return false;
			}
			if($("#tpSubContent tr input[name=chkbox]:checked").length == 0){
				alert("삭제할 행을 선택해 주세요.");
				return false;
			}
			$("#tpSubContent tr input[name=chkbox]:checked").each(function(){		
				$(this).closest('tr').remove();			
			});
			$("#tpSubContent > tbody > tr").each(function(index, item) {
				$(this).find("td:eq(1)").find('span').text(index +1);
				$(this).find("td:eq(1)").find('input').val(index +1);
			});
			
			fn_sumTrTm();
		});
	}
	
	// 교과목 프로필에 시간을 입력할 때마다 합계를 훈련시간(id=trTm)에 입력하기
	function fn_sumTrTmByKeyup(){
		// 교과목 프로필에 시간을 입력할 때마다 시간 합계를 훈련시간에 새로 setting
		$("[id^='time']").keyup(function() {
			var tpLength = $("#tpSubLength").val();
			var sum = 0;
			for(var i = 1; i <= tpLength; i++){
				if($("#time" + i).val() != undefined){
					var targetVal = Number($("#time" + i).val());
					sum += targetVal;
				}
			}
			$("#trTm").val(sum);
		})
	}
	
	// 교과목 프로필에 이미 입력되어 있는 시간의 합계를 훈련시간(id=trTm)에 입력하기(과정수정, 프로필 삭제할 때)
	function fn_sumTrTm(){
		var tpLength = $("#tpSubLength").val();
		var sum = 0;
		for(var i = 1; i <= tpLength; i++){
			if($("#time" + i).val() != undefined){
				var targetVal = Number($("#time" + i).val());
				sum += targetVal;
			}
		}
		$("#trTm").val(sum);
	}
</script>
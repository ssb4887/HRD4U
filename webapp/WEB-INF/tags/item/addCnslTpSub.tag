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
<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
<div class="table-type02">
<table id="tpSubContent">
	<colgroup>
		<col style="width:6%">	
		<col style="width:25%">	
		<col style="width:44%">	
		<col style="width:8%">	
		<col style="width:20%">	
	</colgroup>
	<thead>
        <tr>
            <th scope="row" colspan="5">교과프로필</th>
        </tr>
        <tr>
            <th scope="row">연번&nbsp;<strong class="point-important">*</strong></th>
            <th scope="row">교과목명&nbsp;<strong class="point-important">*</strong></th>
            <th scope="row">세부내용 단원(과제명)&nbsp;<strong class="point-important">*</strong></th>
            <th scope="row">시간&nbsp;<strong class="point-important">*</strong></th>
            <th scope="row">교수기법&nbsp;<strong class="point-important">*</strong></th>
        </tr>		
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty modify}">
				<tr>
					<td><input type="hidden" value="1" name="courseNo0"><span id="courseNo">1</span></td>
					<td><input type="text" name="courseName0" id="courseName0" class="w100 profile"></td>
					<td><textarea name="cn0" id="cn0" class="w100 h100 textareaCustom profile"><c:out value="${contentsList[i.count]}" /></textarea></td>
					<td><input type="text" name="time0" id="time0" maxlength="5" class="w100 onlyNum profile"></td>
					<td><input type="text" name="tchMethod0" id="tchMethod0" class="w100 profile"></td>
				</tr>
			</c:when>
			<c:when test="${not empty modify}">
               	<c:forEach items="${tpSubList }" varStatus="i">
                	<tr>
                		<td> 
                 		<input type="hidden" value="${tpSubList[i.index].COURSE_NO }" name="courseNo${i.index }" id="courseNo${i.count }" class="w100 profile">
                 		${tpSubList[i.index].COURSE_NO }
                		</td>
                		<td>
                			<input type="text" value="${tpSubList[i.index].COURSE_NAME }" name="courseName${i.index }" id="courseName${i.count }" class="w100 profile">
                		</td>
                		<td class="left">											
						<textarea name="cn${i.index }" id="cn${i.count }"  class="w100 h100 profile"><c:out value="${fn:replace(tpSubList[i.index].TR_CN,'<br>',newline)}"></c:out></textarea>
                		</td>
                		<td>
                			<input type="text" value="${tpSubList[i.index].TRTM }" name="time${i.index }" id="time${i.count }" maxlength="5" class="w100 onlyNum profile">
                		</td>
                		<td>
                			<input type="text" value="${tpSubList[i.index].TCHMETHOD }" name="tchMethod${i.index }" id="tchMethod${i.count }" class="w100 profile">
                		</td>
                	</tr>
               	</c:forEach>
			</c:when>			
		</c:choose>
	</tbody>
</table>
</div>
<div class="btns-area mt30 w100">
	<button type="button" id="addSub" class="btn-m02 btn-color03 codeLevel">추가</button>
	<button type="button" id="deleteSub" class="btn-m02 btn-color02">삭제</button>
	<c:choose>
		<c:when test="${not empty modify}">
			<input type="hidden" name="tpSubLength" id="tpSubLength" value="${fn:length(tpSubList) -1 }">
		</c:when>
		<c:otherwise>
			<input type="hidden" name="tpSubLength" id="tpSubLength" value="0">
		</c:otherwise>
	</c:choose>	
</div>

<script type="text/javascript">
	//훈련내용 입력 행 추가
	$("#addSub").click(function(){
		var index = $("#tpSubContent > tbody > tr").length;
		
		var inputList = ["courseName", "time", "cn", "tchMethod"];
		var inputName = ["세부내용 단원(과제명)", "시간", "주요내용", "교수기법"];
		
		
	/* 	if(index > 0) {
			for(var i = 0; i < inputList.length; i++){
				if(!$("#" + inputList[i] + index).val()){
					alert(inputName[i] + "을 입력해 주세요.");
					$("#" + inputList[i] + index).focus();
					return false;
				}
			}
		} */
		
		$("#tpSubLength").val(index);
		var innerHtml = "";
		innerHtml += '<tr><td><input type="hidden" value="'+(index+1)+'" name="courseNo' + (index) + '"><span id="courseNo">' + (index +1) + '</span></td>'
		innerHtml += '<td><input type="text" name="courseName'+ (index) +'" id="courseName' + (index +1) + '" class="w100 profile"></td>' 
		innerHtml += '<td><textarea name="cn'+ (index) +'" id="cn' + (index +1) + '" class="w100 h100 textareaCustom profile"></textarea></td>'
		innerHtml += '<td><input type="text" name="time'+ (index) +'" id="time' + (index +1) + '" onkeydown="fn_onlyNumNoCopy(event)" onchange="fn_onlyNumNoCopy(event)" class="w100 number profile"></td>'
		innerHtml += '<td><input type="text" name="tchMethod'+ (index) +'" id="tchMethod' + (index +1) + '" class="w100 profile"></td></tr>'
		
		$("#tpSubContent > tbody:last").append(innerHtml);
		
		fn_validationTp();
	});
	
	// 훈련내용 입력 행 삭제
	$("#deleteSub").click(function(){
		trCnt = $("#tpSubContent > tbody > tr").length;
		if(trCnt > 1){
			$("#tpSubContent > tbody:last > tr:last").remove();
			trCnt = $("#tpSubContent > tbody > tr").length-1;
			$("#tpSubLength").val(trCnt);
		} else {
			return false;
		}
	});
	
	function fn_validationTp(){
		$(".number").keyup(function(event) {
			if($(this).val() > 99999){
				alert("99999이하로 입력해 주세요.");
				$(this).val(99999);
			}
		});
	}
	
	// placeholder 추가
	$("input.onlyNum").attr("placeholder", "숫자만 입력하세요");
	$("input.onlyNumDash").attr("placeholder", "숫자와 '-'만 입력하세요");
	
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
		fn_preventHan(event, $(this));
		return fn_onlyNum(event, $(this));
	});
	
	// 전화번호 입력
	$(".onlyNumDash").keyup(function(event){
		if($(this).val().length > 13){
			$(this).val($(this).val().substr(0, 13));
		}
		fn_preventHan(event, $(this));
		return fn_onlyNumDash(event, $(this));
	});
	
	// 복사 붙여넣기 시 숫자만 입력
	$(".onlyNum").change(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
		fn_onlyNumNoCopy(event);
	});
	
	// 복사 붙여넣기 시 전화번호 입력
	$(".onlyNumDash").change(function(event){
		if($(this).val().length > 13){
			$(this).val($(this).val().substr(0, 13));
		}		
		fn_onlyNumDashNoCopy(event);
	});
	
</script>
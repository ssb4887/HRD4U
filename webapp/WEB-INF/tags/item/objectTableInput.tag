<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="tableId"%>
<th scope="row">
	<div class="input-yyyy">
		<input type="text" id="year" name="year" placeholder="연도 입력" title="연도를 입력하세요"><span>년</span>
	</div>
</th>
<c:choose>
	<c:when test="${tableId eq 'traing'}">
		<td><input type="text" name="corp" id="corp" class="right w100"></td>
		<td><input type="text" name="tot" id="tot" class="right w100"></td>
	</c:when>
	<c:otherwise>
		<td><input type="text" name="pay" id="pay" class="right w100"></td>
	</c:otherwise>
</c:choose>
<tr>
	<td colspan="3">
		<input type="button" value="추가" id="addSub">
		<input type="button" value="삭제" id="deleteSub">
	</td>
</tr>
<script type="text/javascript">
	var tableId = "${tableId}";
	//훈련내용 입력 행 추가
	$("#addSub").click(function(){
		var inputList = ["year", "corp", "tot", "pay"];
		var inputName = ["연도", "참여 기업수", "참여 인원수", "지급금액"];
		
		if(tableId == "traing"){
			inputList.pop();
			inputName.pop();
		} else {
			inputList.splice(2, 3);
			inputName.splice(2, 3);
		}
		
		for(var i = 0; i < inputList.length; i++){
			if($("#" + tableId +" tbody tr").length == 5){
				if(!$("#" + inputList[i]).val()){
					alert(inputName[i] + "을 입력해 주세요.");
					$("#" + inputList[i]).focus();
					return false;
				}
			} else {
				/* if(!$("#" + tableId + " > tbody > tr:last").prev().find("td[id='" + inputList[i] + "']").val()){
					alert(inputName[i] + "을 입력해 주세요.");
					$("#" + tableId + " > tbody > tr:last").prev().find("td[id='" + inputList[i] + "']").focus();
					return false;
				} */
			}
		}
		
		var innerHtml = "";
		
		if(tableId == "traing"){
			innerHtml += '<tr><th scope="row"><div class="input-yyyy"><input type="text" id="year" name="year" placeholder="연도 입력" title="연도를 입력하세요" onkeydown="onlyNum(event)"><span>년</span></div></th>' 
			innerHtml += '<td><input type="text" name="corp" id="corp" class="right w100" onkeydown="onlyNum(event)"></td>'
			innerHtml += '<td><input type="text" name="tot" id="tot" class="right w100" onkeydown="onlyNum(event)"></td></tr>'
		} else {
			innerHtml += '<tr><th scope="row"><div class="input-yyyy"><input type="text" id="year" name="year" placeholder="연도 입력" title="연도를 입력하세요" onkeydown="onlyNum(event)"><span>년</span></div></th>' 
			innerHtml += '<td><input type="text" name="pay" id="pay" class="right w100" onkeydown="onlyNum(event)"></td></tr>'
		}
		
		$("#" + tableId + " > tbody > tr:last").before(innerHtml);
	});
	
	// 훈련내용 입력 행 삭제
	$("#deleteSub").click(function(){
		var trCnt = $("#" + tableId +" tbody tr").length;
		if(trCnt > 5){
			$("#" + tableId + " > tbody > tr:last").prev().remove();
		} else {
			return false;
		}
	});
	
	// 숫자만 입력
	function onlyNum(event){
		const regExp = /[^0-9]/g;
		const ele = event.target;
		if(regExp.test(ele.value)){
			ele.value = ele.value.replace(regExp, '');
		}
	}
	
</script>
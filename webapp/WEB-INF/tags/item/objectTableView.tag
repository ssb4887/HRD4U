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
<%@ attribute name="pageContext" type="javax.servlet.jsp.PageContext"%>
<%
	PageContext page = (PageContext)this.getJspContext();
	page.setAttribute("newline","\r\n");
%>
<c:if test="${empty objDt}"><c:set var="objDt" value="${dt}"/></c:if>
<div class="table-type02">
<table id="tpSubContent">
	<colgroup>
		<col style="width:6%">	
		<col style="width:25%">	
		<col style="width:8%">	
		<col style="width:44%">	
		<col style="width:20%">	
	</colgroup>
	<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">교과목</th>
			<th scope="col">시간</th>
			<th scope="col">주요내용</th>
			<th scope="col">교수 기법</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${objVal eq 'write'}">
				<tr>
					<td>M<span id="subIdx">1</span></td>
					<td><input type="text" name="subName" id="subName1" class="input-width90"></td>
					<td><input type="text" name="time" id="subTime1" class="input-width90"></td>
					<td><textarea name="contents" id="subContent1" class="input-width90 textareaCustom"><c:out value="${contentsList[i.index]}" /></textarea></td>
					<td><input type="text" name="tchmethod" id="subTeach1" class="input-width90"></td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="subDt" items="${tpSubList}" varStatus="i">
					<tr>
						<td><c:out value="${subDt.COURSE_NO}" /></td>
						<td><input type="text" name="subName" id="subName${i.count}" value="${subDt.COURSE_NAME}" class="input-width90"></td>
						<td><input type="text" name="time" id="subTime${i.count}" value="<c:out value="${subDt.TIME}" />" class="input-width90"></td>
						<td><textarea name="contents" id="subContent${i.count}" class="input-width90 textareaCustom"><c:out value="${fn:replace(subDt.CN,'<br>',newline)}"/></textarea></td>
						<td><input type="text" name="tchmethod" id="subTeach${i.count}" value="<c:out value="${subDt.TCHMETHOD}" />" class="input-width90"></td>
					</tr>
				</c:forEach>
			</c:otherwise>	
		</c:choose>
	</tbody>
</table>
</div>
<div class="btns-area mt30 w100">
	<button type="button" id="addSub" class="btn-m02 btn-color03 three-select codeLevel">추가</button>
	<button type="button" id="deleteSub" class="btn-m02 btn-color02 three-select">삭제</button>
	<input type="hidden" name="maxSubIdx" id="maxSubIdx" value="1">
</div>

<script type="text/javascript">
	//훈련내용 입력 행 추가
	$("#addSub").click(function(){
		var index = $("#tpSubContent > tbody > tr").length;
		
		var inputList = ["subName", "subTime", "subContent", "subTeach"];
		var inputName = ["교과목", "시간", "주요내용", "교수기법"];
		
		for(var i = 0; i < inputList.length; i++){
			if(!$("#" + inputList[i] + index).val()){
				alert(inputName[i] + "을 입력해 주세요.");
				$("#" + inputList[i] + index).focus();
				return false;
			}
		}
		
		$("#maxSubIdx").val(index);
		
		var innerHtml = "";
		innerHtml += '<tr><td>M<span id="subIdx">' + (index +1) + '</span></td>'
		innerHtml += '<td><input type="text" name="subName" id="subName' + (index +1) + '" class="input-width90"></td>' 
		innerHtml += '<td><input type="text" name="time" id="subTime' + (index +1) + '" onkeydown="onlyNum(event)" class="input-width90"></td>'
		innerHtml += '<td><textarea name="contents" id="subContent' + (index +1) + '" class="input-width90 textareaCustom"></textarea></td>'
		innerHtml += '<td><input type="text" name="tchmethod" id="subTeach' + (index +1) + '" class="input-width90"></td></tr>'
		
		$("#tpSubContent > tbody:last").append(innerHtml);
	});
	
	// 훈련내용 입력 행 삭제
	$("#deleteSub").click(function(){
		var trCnt = $("#tpSubContent > tbody > tr").length;
		if(trCnt > 1){
			$("#tpSubContent > tbody:last > tr:last").remove();
			$("#maxSubIdx").val(trCnt);
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
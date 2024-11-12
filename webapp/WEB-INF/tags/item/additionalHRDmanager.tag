<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="itemId"%>
<%@ attribute name="objDt" type="com.woowonsoft.egovframework.form.DataMap"%>
<%@ attribute name="defVal"%>
<%@ attribute name="objVal" type="java.lang.Object"%>
<%@ attribute name="itemInfo" type="net.sf.json.JSONObject"%>
<%@ attribute name="content"%>
<%@ attribute name="form"%>
<c:choose>
	<c:when test="${objVal eq 'view'}"><c:set var="tableName" value="정보 제공표"/></c:when>
	<c:otherwise><c:set var="tableName" value="입력표"/></c:otherwise>
</c:choose>
<div class="table-type02 horizontal-scroll">
<table id="tpSubContent" class="width-type02">
	<caption>능력개발클리닉 ${form} 중 HRD 담당자 지정 현황 ${tableName} : 부서명, 직위, 이름, 재직경력, 연락처, 이메일 ${tableName}</caption>	
	<colgroup>
		<col width="auto">
		<col width="auto">
		<col width="auto">
		<col width="auto">
		<col width="auto">
		<col width="20%">
	</colgroup>
	<thead>
		<tr>
			<th scope="col" colspan="6">3.HRD 담당자 지정 현황</th>
		</tr>
	   	<tr>
	   		<th scope="col">부서명<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">직위<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">이름<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">재직경력(년)<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">연락처<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">E-mail<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   	</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when test="${objVal eq 'write'}">
			<tr>
				<td><input type="text" name="psitnDept1" id="psitnDept1" class="w100" value="${reqList.PSITN_DEPT }"></td>
				<td><input type="text" name="ofcps1" id="ofcps1" class="w100" value="${reqList.OFCPS }"></td>
				<td><input type="text" name="picNm1" id="picNm1" class="w100" value="${reqList.PIC_NM }"></td>
				<td><input type="text" name="hffcCareer1" id="hffcCareer1" class="w100 onlyNum" value="${reqList.HFFC_CAREER }"></td>
				<td><input type="text" name="telNo1" id="telNo1" class="w100 onlyNumDash" value="${reqList.TELNO }"></td>
				<td><input type="text" name="email1" id="email1" class="w100" value="${reqList.EMAIL }"></td>
			</tr>
		</c:when>
		<c:when test="${objVal eq 'modify'}">				
			<c:forEach var="pic" items="${picList}" varStatus="i">	
				<tr>
					<td><input type="text" name="psitnDept${i.count }" id="psitnDept${i.count }" value="${pic.DEPT_NAME }" class="w100"></td>
					<td><input type="text" name="ofcps${i.count }" id="ofcps${i.count }" value="${pic.OFCPS }" class="w100"></td>
					<td><input type="text" name="picNm${i.count }" id="picNm${i.count }" value="${pic.PIC_NM }" class="w100"></td>
					<td><input type="text" name="hffcCareer${i.count }" id="hffcCareer${i.count }" value="${pic.HFFC_CAREER }" class="w100 onlyNum"></td>
					<td><input type="text" name="telNo${i.count }" id="telNo${i.count }" value="${pic.TELNO }" class="w100 onlyNumDash"></td>
					<td><input type="text" name="email${i.count }" id="email${i.count }" value="${pic.EMAIL }" class="w100"></td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach var="pic" items="${picList}" varStatus="i">
				<tr>
					<td>${pic.DEPT_NAME}</td>
					<td>${pic.OFCPS}</td>
					<td>${pic.PIC_NM}</td>
					<td>${pic.HFFC_CAREER}년</td>
					<td>${pic.TELNO}</td>
					<td>${pic.EMAIL}</td>
				</tr>
			</c:forEach>
		</c:otherwise>	
	</c:choose>
	</tbody>
</table>
</div>
<c:if test="${objVal eq 'write' || objVal eq 'modify'}">
	<div class="btns-area mt20">
		<div class="btns-right">
			<button type="button" id="addSub" class="btn-m01 btn-color01">추가</button>
			<button type="button" id="deleteSub" class="btn-m01 btn-color01">삭제</button>
			<c:set var="subLength" value="1"/>
			<c:if test="${objVal eq 'modify'}">
			<c:set var="subLength" value="${fn:length(picList)}"/>
			</c:if>
			<input type="hidden" name="maxSubIdx" id="maxSubIdx" value="${subLength}">
		</div>
	</div>
</c:if>

<script type="text/javascript">
	//훈련내용 입력 행 추가
	$("#addSub").click(function(){
		var index = $("#tpSubContent tbody tr").length;
		
		var inputList = ["psitnDept", "ofcps", "picNm", "hffcCareer","telNo","email"];
		var inputName = ["부서명", "직위", "이름", "재직경력(년)","연락처","E-mail"];
		
		for(var i = 0; i < inputList.length; i++){
			if(!$("#" + inputList[i] + index).val()){
				alert(inputName[i] + " 항목을 입력해 주세요.");
				$("#" + inputList[i] + index).focus();
				return false;
			}
		}
		index += 1;

		$("#maxSubIdx").val(index);
		
		var innerHtml = "";
		innerHtml += '<tr><td><input type="text" name="psitnDept' + index + '" id="psitnDept' + index + '" class="w100"></td>'
		innerHtml += '<td><input type="text" name="ofcps' + index + '" id="ofcps' + index + '" class="w100"></td>' 
		innerHtml += '<td><input type="text" name="picNm' + index + '" id="picNm' + index + '" class="w100"></td>'
		innerHtml += '<td><input type="text" name="hffcCareer' + index + '" id="hffcCareer' + index + '" class="w100 career" onkeyup="fn_onlyNum(event)" onchange="fn_onlyNumNoCopy(event)"></td>'
		innerHtml += '<td><input type="text" name="telNo' + index + '" id="telNo' + index + '" class="w100 phone" onkeydown="fn_onlyNumDash(event)" onchange="fn_onlyNumDashNoCopy(event)"></td>'
		innerHtml += '<td><input type="text" name="email' + index + '" id="email' + index + '" class="w100"></td></tr>'
		
		$("#tpSubContent > tbody:last").append(innerHtml);
		
		fn_validationHrd();
	});
	
	// 훈련내용 입력 행 삭제
	$("#deleteSub").click(function(){
		var trCnt = $("#tpSubContent tbody tr").length;
		if(trCnt > 1){
			trCnt -= 1;
			$("#tpSubContent > tbody:last > tr:last").remove();
			$("#maxSubIdx").val(trCnt);
		} else {
			return false;
		}
	});
	
	function fn_validationHrd(){
		$(".career").keyup(function(event) {
			if($(this).val() > 99){
				alert("99이하로 입력해 주세요.");
				$(this).val(99);
			}
		});
		
		$(".phone").keyup(function(event) {
			if($(this).val().length > 13){
				$(this).val($(this).val().substr(0, 12));
			}
		});
	}
	
</script>
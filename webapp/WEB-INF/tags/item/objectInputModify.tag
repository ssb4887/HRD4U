<%@tag import="java.util.Calendar"%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="objDt" type="java.util.ArrayList"%>
<%@ attribute name="selectClass"%>
<%@ attribute name="tableId"%>
<%@ attribute name="idx"%>
<%@ attribute name="submitType"%>
<%@ attribute name="indutyCd"%>
<%@ attribute name="year"%>
<spring:message var="itemSearchPreFlag" code="Globals.item.search.pre.flag"/>
<c:choose>
	<c:when test="${selectClass eq 'traing'}">
		<tr id="trRange1">
			<th scope="col">5인 미만 <input type="hidden" name="range" value="5"></th>
			<td><input type="text" name="corp" id="corp1" class="right w100 padding10"></td>
			<td><input type="text" name="tot" id="tot1" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange2">
			<th scope="col">5인 이상 30인 미만 <input type="hidden" name="range" value="30"></th>
			<td><input type="text" name="corp" id="corp2" class="right w100 padding10"></td>
			<td><input type="text" name="tot" id="tot2" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange3">
			<th scope="col">30인 이상 100인 미만 <input type="hidden" name="range" value="100"></th>
			<td><input type="text" name="corp" id="corp3" class="right w100 padding10"></td>
			<td><input type="text" name="tot" id="tot3" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange4">
			<th scope="col">100인 이상 300인 미만 <input type="hidden" name="range" value="300"></th>
			<td><input type="text" name="corp" id="corp4" class="right w100 padding10"></td>
			<td><input type="text" name="tot" id="tot4" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange5">
			<th scope="col">300인 이상 1000인 미만 <input type="hidden" name="range" value="1000"></th>
			<td><input type="text" name="corp" id="corp5" class="right w100 padding10"></td>
			<td><input type="text" name="tot" id="tot5" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange6">
			<th scope="col">1000인 이상 <input type="hidden" name="range" value="10000000"></th>
			<td><input type="text" name="corp" id="corp6" class="right w100 padding10"></td>
			<td><input type="text" name="tot" id="tot6" class="right w100 padding10"></td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr id="trRange1">
			<th scope="col">5인 미만<input type="hidden" name="range" value="5"></th>
			<td><input type="text" name="pay" id="pay1" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange2">
			<th scope="col">5인 이상 30인 미만<input type="hidden" name="range" value="30"></th>
			<td><input type="text" name="pay" id="pay2" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange3">
			<th scope="col">30인 이상 100인 미만 <input type="hidden" name="range" value="100"></th>
			<td><input type="text" name="pay" id="pay3" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange4">
			<th scope="col">100인 이상 300인 미만 <input type="hidden" name="range" value="300"></th>
			<td><input type="text" name="pay" id="pay4" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange5">
			<th scope="col">300인 이상 1000인 미만 <input type="hidden" name="range" value="1000"></th>
			<td><input type="text" name="pay" id="pay5" class="right w100 padding10"></td>
		</tr>
		<tr id="trRange6">
			<th scope="col">1000인 이상 <input type="hidden" name="range" value="10000000"></th>
			<td><input type="text" name="pay" id="pay6" class="right w100 padding10"></td>
		</tr>
	</c:otherwise>
</c:choose>

<c:if test="${!empty objDt && submitType eq 'modify'}">
	<select id="${selectClass}" name="${selectClass}" style="display:none;">
		<c:forEach var="dt" items="${objDt}">
			<c:choose>
				<c:when test="${selectClass eq 'traing'}">
					<option value="${dt.INDUTY_CD}" data-range="${dt.TOT_WORK_CNT_SCOPE}" data-year="${dt.YEAR}" data-corp="${dt.NMCORP}" data-tot="${dt.TOTAL_NMPR}" data-pay></option>
				</c:when>
				<c:otherwise>
					<option value="${dt.INDUTY_CD}" data-range="${dt.TOT_WORK_CNT_SCOPE}" data-year="${dt.YEAR}" data-corp data-tot data-pay="${dt.SPT_PAY}"></option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
</c:if>
<script type="text/javascript">
$(function(){
	var theId = "${tableId}";
	var selectId = "${selectClass}";
	var idx = "${idx}";
	var selectIndustValue = "";
	var selectYearValue = "";
	var selectValue = "";
	var submitType="${submitType}";
	var year ="${year}";
	var indutyCd ="${indutyCd}";
	
	if(year != ""){
		$("#year").find("option[value='" + year + "']").attr("selected", "selected");
		selectYearValue = $("#year").find("option:selected").val();
		
		if(submitType == "modify"){
			fn_dataSetting(theId, selectId, indutyCd, selectYearValue);
		}
	}
	
	$("#indutyCode").change(function() {
		selectValue = $(this).find("option:selected").val();
		
		$("#year option[data-pcode='00']").prop("selected", true);
		$("#year").focus();
		$("[id^='trRange']").find("input[type='text']").val(0);
	});
	
	$("#year").change(function() {
		selectYearValue = $(this).find("option:selected").val();
		selectValue = $("#indutyCode").find("option:selected").val();
		
		if(submitType == "modify"){
			fn_dataSetting(theId, selectId, selectValue, selectYearValue);
		}
	});
	
	$("[id^='corp'], [id^='tot'], [id^='pay']").keyup(function(e) {
		const regExp = /[0-9]/g;
		const ele = event.target;
		if(regExp.test(ele.value)){
			let value = e.target.value;
			value = Number(value.replaceAll(/[^0-9]/g, ''));
			const formatValue = value.toLocaleString('ko-KR');
			$(this).val(formatValue);
		}
	});
});

function fn_dataSetting(theId, selectId, selectValue, selectYearValue){
	var rangeList = ["5", "30", "100", "300", "1000", "10000000"];
	for(var i = 0; i < 6; i++){
		var corpValue = $("#" + selectId + " option[value='" + selectValue + "'][data-year='" + selectYearValue + "'][data-range='" + rangeList[i] + "']").attr("data-corp");
		var totValue = $("#" + selectId + " option[value='" + selectValue + "'][data-year='" + selectYearValue + "'][data-range='" + rangeList[i] + "']").attr("data-tot");
		var payValue = $("#" + selectId + " option[value='" + selectValue + "'][data-year='" + selectYearValue + "'][data-range='" + rangeList[i] + "']").attr("data-pay");

		var corpNum = (typeof corpValue != "undefined") ? Number(corpValue).toLocaleString("ko-KR") : 0;
		var totNum = (typeof totValue != "undefined") ? Number(totValue).toLocaleString("ko-KR") : 0;
		var payNum = (typeof payValue != "undefined") ? Number(payValue).toLocaleString("ko-KR") : 0;
		
		if(selectId == "traing"){
			$("#" + theId + " tbody tr[id='trRange" + (i+1) + "'] td input[id='corp" + (i+1) + "']").val(corpNum);
			$("#" + theId + " tbody tr[id='trRange" + (i+1) + "'] td input[id='tot" + (i+1) +"']").val(totNum);
		} else {
			$("#" + theId + " tbody tr[id='trRange" + (i+1) + "'] td input[id='pay" + (i+1) +"']").val(payNum);
		}
	}
}
</script>
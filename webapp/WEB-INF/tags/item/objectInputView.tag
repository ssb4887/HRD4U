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
			<th scope="col">5인 미만</th>
			<td id="corp1">0</td>
			<td id="tot1">0</td>
		</tr>
		<tr id="trRange2">
			<th scope="col">5인 이상 30인 미만</th>
			<td id="corp2">0</td>
			<td id="tot2">0</td>
		</tr>
		<tr id="trRange3">
			<th scope="col">30인 이상 100인 미만</th>
			<td id="corp3">0</td>
			<td id="tot3">0</td>
		</tr>
		<tr id="trRange4">
			<th scope="col">100인 이상 300인 미만</th>
			<td id="corp4">0</td>
			<td id="tot4">0</td>
		</tr>
		<tr id="trRange5">
			<th scope="col">300인 이상 1000인 미만</th>
			<td id="corp5">0</td>
			<td id="tot5">0</td>
		</tr>
		<tr id="trRange6">
			<th scope="col">1000인 이상</th>
			<td id="corp6">0</td>
			<td id="tot6">0</td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr id="trRange1">
			<th scope="col">5인 미만</th>
			<td id="pay1">0</td>
		</tr>
		<tr id="trRange2">
			<th scope="col">5인 이상 30인 미만</th>
			<td id="pay2">0</td>
		</tr>
		<tr id="trRange3">
			<th scope="col">30인 이상 100인 미만</th>
			<td id="pay3">0</td>
		</tr>
		<tr id="trRange4">
			<th scope="col">100인 이상 300인 미만</th>
			<td id="pay4">0</td>
		</tr>
		<tr id="trRange5">
			<th scope="col">300인 이상 1000인 미만</th>
			<td id="pay5">0</td>
		</tr>
		<tr id="trRange6">
			<th scope="col">1000인 이상</th>
			<td id="pay6">0</td>
		</tr>
	</c:otherwise>
</c:choose>

<c:if test="${!empty objDt}">
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
		
		fn_dataSetting(theId, selectId, indutyCd, selectYearValue);
	}
	
	$("#indutyCode").change(function() {
		selectValue = $(this).find("option:selected").val();
		
		$("#year option[data-pcode='00']").prop("selected", true);
		$("#year").focus();
		$("[id^='trRange']").find("td").text("0");
	});
	
	$("#year").change(function() {
		selectYearValue = $(this).find("option:selected").val();
		selectValue = $("#indutyCode").find("option:selected").val();
		
		fn_dataSetting(theId, selectId, selectValue, selectYearValue);
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
			$("#" + theId + " tbody tr[id='trRange" + (i+1) + "'] td[id='corp" + (i+1) + "']").text(corpNum);
			$("#" + theId + " tbody tr[id='trRange" + (i+1) + "'] td[id='tot" + (i+1) +"']").text(totNum);
		} else {
			$("#" + theId + " tbody tr[id='trRange" + (i+1) + "'] td[id='pay" + (i+1) +"']").text(payNum);
		}
	}
}
</script>
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
<spring:message var="itemSearchPreFlag" code="Globals.item.search.pre.flag"/>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="nowYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
<c:set var="year" value="${nowYear - 1}" />
<c:forEach var="i" begin="1" end="3">
	<c:choose>
		<c:when test="${selectClass eq 'traing'}">
			<tr id="${i}">
				<th scope="row">
					<div class="input-yyyy height50" id="${year}"><span><c:out value="${year}"/>년</span></div>
				</th>
				<td id="corp" class="td-traing"></td>
				<td id="tot" class="td-traing"></td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr id="${i}">
				<th scope="row">
					<div class="input-yyyy height50" id="${year}"><span><c:out value="${year}"/>년</span></div>
				</th>
				<td id="pay" class="td-traing"></td>
			</tr>
		</c:otherwise>
	</c:choose>
	<c:set var="year" value="${year - 1}" />
</c:forEach>

<select id="${selectClass}" name="${selectClass}" style="display:none;">
	<c:forEach var="dt" items="${objDt}">
		<c:choose>
			<c:when test="${selectClass eq 'traing'}">
				<c:choose>
					<c:when test="${!empty dt.INDUTY_CD}">
						<option value="${dt.INDUTY_CD}" data-year="${dt.YEAR}" data-corp="${dt.NMCORP}" data-tot="${dt.TOTAL_NMPR}" data-pay></option>
					</c:when>
					<c:otherwise>
						<option value="${dt.TOT_WORK_CNT_SCOPE}" data-year="${dt.YEAR}" data-corp="${dt.NMCORP}" data-tot="${dt.TOTAL_NMPR}" data-pay></option>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${!empty dt.INDUTY_CD}">
						<option value="${dt.INDUTY_CD}" data-year="${dt.YEAR}" data-corp data-tot data-pay="${dt.SPT_PAY}"></option>
					</c:when>
					<c:otherwise>
						<option value="${dt.TOT_WORK_CNT_SCOPE}" data-year="${dt.YEAR}" data-corp data-tot data-pay="${dt.SPT_PAY}"></option>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</select>

<script type="text/javascript">
$(function(){
	var theId = "${tableId}";
	var selectId = "${selectClass}";
	var idx = "${idx}";
	var selectValue = $("#is_viewStandard_" + idx).find("option:selected").val();
	
	fn_dataSetting(theId, selectId, selectValue);
	
	$("#is_viewStandard1_" + idx).change(function() {
		selectValue = $(this).find("option:selected").val();
		
		if(selectValue == "Z"){
			fn_dataSetting(theId, selectId, selectValue);
		}
	});
	
	$("#is_viewStandard2_" + idx).change(function() {
		selectValue = $(this).find("option:selected").val();
		
		fn_dataSetting(theId, selectId, selectValue, selectId);
	});
});

function fn_dataSetting(theId, selectId, selectValue){
	for(var i = 1; i <= 5; i++){
		var yearValue = $("#" + theId + " tbody tr[id='" + i + "'] th div").attr("id");
		var corpValue = $("#" + selectId + " option[value='" + selectValue + "'][data-year='" + yearValue + "']").attr("data-corp");
		var totValue = $("#" + selectId + " option[value='" + selectValue + "'][data-year='" + yearValue + "']").attr("data-tot");
		var payValue = $("#" + selectId + " option[value='" + selectValue + "'][data-year='" + yearValue + "']").attr("data-pay");

		var corpNum = (typeof corpValue != "undefined") ? Number(corpValue).toLocaleString("ko-KR") : "-";
		var totNum = (typeof totValue != "undefined") ? Number(totValue).toLocaleString("ko-KR") : "-";
		var payNum = (typeof payValue != "undefined") ? Number(payValue).toLocaleString("ko-KR") : "-";
	
		if(selectId == "traing"){
			$("#" + theId + " tbody tr[id='" + i + "'] td[id=corp]").text(corpNum);
			$("#" + theId + " tbody tr[id='" + i + "'] td[id=tot]").text(totNum);
		} else {
			$("#" + theId + " tbody tr[id='" + i + "'] td[id=pay]").text(payNum);
		}
	}
}
</script>
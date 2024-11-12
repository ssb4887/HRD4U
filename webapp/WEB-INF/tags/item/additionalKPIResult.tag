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
<c:choose>
	<c:when test="${objVal eq 'view'}"><c:set var="tableName" value="정보 제공표"/></c:when>
	<c:otherwise><c:set var="tableName" value="입력표"/></c:otherwise>
</c:choose>
<div class="table-type02 horizontal-scroll">
<table id="resultKpi" class="width-type02">
	<caption>능력개발클리닉 성과보고서 중 자체 KPI 이행결과 ${tablaName} : 구분, 성과지표, 목표 , 결과 ${tableName}</caption>
	<colgroup>
		<col width="15%">
		<col width="auto">
		<col width="15%">
		<col width="15%">
	</colgroup>	
	<thead>
		<tr>
			<th colspan="4">자체 KPI 이행결과&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${submitType ne 'view'}"><a href="#" class="btn-m03 btn-color01" id="open-image02" data-idx="2">예시</a></c:if></th>
		</tr>
	   	<tr>
	   		<th scope="col">구분</th>
	   		<th scope="col">성과지표<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">목표<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">결과<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   	</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when test="${objVal eq 'write'}">
			<tr>
				<td>필수<input type="hidden" name="kpiEssntl1" id="kpiEssntl1" value="R"></td>
				<td><input type="text" name="kpiCn1" id="kpiCn1" class="w100" placeholder="자체훈련(S-JOT, 사업주훈련, 일학습병행 등) 실시"></td>
				<td><input type="text" name="kpiGoal1" id="kpiGoal1" class="w100" placeholder="1회 이상"></td>
				<td><input type="text" name="kpiResult1" id="kpiResult1" class="w100" ></td>
			</tr>
			<tr>
				<td>자율1<input type="hidden" name="kpiEssntl2" id="kpiEssntl2" value="R"></td>
				<td><input type="text" name="kpiCn2" id="kpiCn2" class="w100" ></td>
				<td><input type="text" name="kpiGoal2" id="kpiGoal2" class="w100" ></td>
				<td><input type="text" name="kpiResult2" id="kpiResult2" class="w100" ></td>
			</tr>
			<tr>
				<td>자율2<input type="hidden" name="kpiEssntl3" id="kpiEssntl3" value="R"></td>
				<td><input type="text" name="kpiCn3" id="kpiCn3" class="w100" ></td>
				<td><input type="text" name="kpiGoal3" id="kpiGoal3" class="w100" ></td>
				<td><input type="text" name="kpiResult3" id="kpiResult3" class="w100" ></td>
			</tr>
		</c:when>
		<c:when test="${objVal eq 'modify'}">
			<c:forEach var="kpiDt" items="${kpiList}" varStatus="i">
				<tr>
					<c:choose>
						<c:when test="${i.index eq 0}">
							<td>필수<input type="hidden" name="kpiEssntl1" id="kpiEssntl1" value="R"></td>
						</c:when>
						<c:otherwise>
							<td>자율${i.count}<input type="hidden" name="kpiEssntl${i.count}" id="kpiEssntl${i.count}" value="C"></td>
						</c:otherwise>
					</c:choose>
					<td><input type="text" name="kpiCn${i.count}" id="kpiCn${i.count}" class="w100" value="${kpiDt.KPI_CN}"></td>
					<td><input type="text" name="kpiGoal${i.count}" id="kpiGoal${i.count}" class="w100" value="${kpiDt.KPI_GOAL}"></td>
					<td><input type="text" name="kpiResult${i.count}" id="kpiResult${i.count}" class="w100" value="${kpiDt.KPI_RESULT}"></td>
				</tr>
			</c:forEach>		
		</c:when>
		<c:otherwise>				
			<c:forEach var="kpiDt" items="${kpiList}" varStatus="i">
				<tr>
					<td>
						<c:choose>
							<c:when test="${kpiDt.ESSNTL_SE eq 'R'}">필수</c:when>
							<c:otherwise>자율${i.count}</c:otherwise>
						</c:choose>
					</td>
					<td>${kpiDt.KPI_CN}</td>
					<td>${kpiDt.KPI_GOAL}</td>
					<td>${kpiDt.KPI_RESULT}</td>
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
			<button type="button" id="addKpi" class="btn-m01 btn-color01 three-select codeLevel">추가</button>
			<button type="button" id="deleteKpi" class="btn-m01 btn-color01 three-select">삭제</button>
			<c:set var="kpiLength" value="3"/>
			<c:if test="${objVal eq 'modify'}">
			<c:set var="kpiLength" value="${fn:length(kpiList)}"/>
			</c:if>
			<input type="hidden" name="maxKpiIdx" id="maxKpiIdx" value="${kpiLength}">
		</div>
	</div>
</c:if>

<script type="text/javascript">
	//훈련내용 입력 행 추가
	$("#addKpi").click(function(){
		var index = $("#resultKpi tbody tr").length;
		
		var inputList = ["kpiCn", "kpiGoal", "kpiResult"];
		var inputName = ["성과지표", "목표", "결과"];
		
		for(var i = 0; i < inputList.length; i++){
			if(!$("#" + inputList[i] + index).val()){
				alert(inputName[i] + "항목을 입력해 주세요.");
				$("#" + inputList[i] + index).focus();
				return false;
			}
		}
		index += 1;
		$("#maxKpiIdx").val(index);
		
		var innerHtml = "";
		innerHtml += '<tr><td>자율' + (index -1) + '<input type="hidden" name="kpiEssntl' + index + '" id="kpiEssntl' + index + '" value="C"></td>'
		innerHtml += '<td><input type="text" name="kpiCn' + index + '" id="kpiCn' + index + '" class="w100"></td>'
		innerHtml += '<td><input type="text" name="kpiGoal' + index + '" id="kpiGoal' + index + '" class="w100"></td>'
		innerHtml += '<td><input type="text" name="kpiResult' + index + '" id="kpiResult' + index + '" class="w100"></td></tr>'
		
		$("#resultKpi > tbody:last").append(innerHtml);
	});
	
	// 훈련내용 입력 행 삭제
	$("#deleteKpi").click(function(){
		var trCnt = $("#resultKpi tbody tr").length;
		if(trCnt > 1){
			trCnt -= 1;
			$("#resultKpi > tbody:last > tr:last").remove();
			$("#maxKpiIdx").val(trCnt);
		} else {
			return false;
		}
	});
	
</script>
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
<table id="yearResultSubContent" class="width-type02">
	<caption>능력개발클리닉 성과보고서 중 연간 훈련실시 결과 ${tableName} : 훈련시기, 훈련대상, 훈련내용, 훈련주체, 훈련방법, 기관명(외부), 훈련인원, 훈련시간, 훈련일, 수료인원, 정부지원 훈련여부, 비고 ${tableName}</caption>
	<colgroup>
		<col width="6%">
		<col width="6%">
		<col width="15%">
		<col width="6%">
		<col width="10%">
		<col width="11%">
		<col width="6%">
		<col width="6%">
		<col width="11%">
		<col width="6%">
		<col width="7%">
		<col width="10%">
	</colgroup>
	<thead>
		<tr>
			<th scope="col" colspan="12">연간 훈련실시 결과&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${submitType ne 'view'}"><a href="#" class="btn-m03 btn-color01" id="open-image01" data-idx="1">예시</a></c:if></th>
		</tr>
	   	<tr>
	   		<th scope="col">훈련시기(월)<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">훈련대상<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">훈련내용<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">훈련주체<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">훈련방법<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">(외부기관 훈련시)기관명</th>
	   		<th scope="col">훈련인원(명)<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">훈련시간(시간)<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">훈련일<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">수료인원(명)<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">정부지원 훈련여부<c:if test="${submitType ne 'view'}">&nbsp;<strong class="point-important">*</strong></c:if></th>
	   		<th scope="col">비고<br/>(훈련사업)</th>
	   	</tr>
   	</thead>
   	<tbody>
	<c:choose>
		<c:when test="${objVal eq 'write'}">
			<tr>
				<td>
					<div class="flex-box"><input type="text" name="trMt1" id="trMt1" class="w100 mr5 onlyNum"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trTarget1" id="trTarget1" class="w100 mr5"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trCn1" id="trCn1" class="w100 mr5"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trMby1" id="trMby1" class="w100 mr5"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trMth1" id="trMth1" class="w100 mr5"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trInsttNm1" id="trInsttNm1" class="w100 mr5"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trNmpr1" id="trNmpr1" class="w100 mr5 onlyNum"></div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="trTm1" id="trTm1" class="w100 mr5 onlyNum"></div>
				</td>
				<td>
					<div class="flex-box">
						<input type="text" id="trDate1" name="trDate1" data-idx="1" class="sdate hasDatepicker w100 mr5" style="text-indent: 10px;" placeholder="훈련일시"  onclick="fn_calendar(1)" onkeydown="return false;"/>
					</div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="comNmpr1" id="comNmpr1" class="w100 mr5 onlyNum"></div>
				</td>
				<td>
					<div class="flex-box">
						<select name="gvrnSportYn1" id="gvrnSportYn1" class="w100 mr5">
							<option value="">선택</option>
							<option value="Y">지원</option>
							<option value="N">미지원</option>
						</select>
					</div>
				</td>
				<td>
					<div class="flex-box"><input type="text" name="remark1" id="remark1" class="w100 mr5"></div>
				</td>
			</tr>
		</c:when>
		<c:when test="${objVal eq 'modify'}">				
			<c:forEach var="tr" items="${trList}" varStatus="i">
				<tr>
					<td>
						<div class="flex-box"><input type="text" name="trMt${i.count}" id="trMt${i.count}" class="w100 mr5 onlyNum" value="${tr.TR_MT}"></div>
					</td>
					<td>
						<div class="flex-box"><input type="text" name="trTarget${i.count}" id="trTarget${i.count}" class="w100 mr5" value="${tr.TR_TRGET}"></div>
					</td>
					<td>
						<div class="flex-box"><input type="text" name="trCn${i.count}" id="trCn${i.count}" class="w100 mr5" value="${tr.TR_CN}"></div>
					</td>
					<td><input type="text" name="trMby${i.count}" id="trMby${i.count}" class="w100 mr5" value="${tr.TR_MBY}"></td>
					<td><input type="text" name="trMth${i.count}" id="trMth${i.count}" class="w100 mr5" value="${tr.TR_MTH}"></td>
					<td><input type="text" name="trInsttNm${i.count}" id="trInsttNm${i.count}" class="w100 mr5" value="${tr.TRINSTT_NM}"></td>
					<td><input type="text" name="trNmpr${i.count}" id="trNmpr${i.count}" class="w100 mr5 onlyNum" value="${tr.TR_NMPR}"></td>
					<td><input type="text" name="trTm${i.count}" id="trTm${i.count}" class="w100 mr5 onlyNum" value="${tr.TRTM}"></td>
					<td>
						<fmt:formatDate var="objVal" value="${tr.TR_START_DATE}" pattern="yyyy-MM-dd"/>
						<input type="text" id="trDate${i.count}" name="trDate${i.count}" data-idx="${i.count}" class="sdate hasDatepicker w100 mr5" style="text-indent: 10px;" placeholder="훈련일시" value="${objVal}" onclick="fn_calendar(${i.count});"/>
					</td>
					<td><input type="text" name="comNmpr${i.count}" id="comNmpr${i.count}" class="w100 mr5 onlyNum" value="${tr.COMPL_NMPR}"></td>
					<td><select name="gvrnSportYn${i.count}" id="gvrnSportYn${i.count}" class="w100 mr5">
						<option value="">선택</option>
						<option value="Y" <c:if test="${tr.GVRN_SPORT_YN eq 'Y' }">selected</c:if>>지원</option>
						<option value="N" <c:if test="${tr.GVRN_SPORT_YN eq 'N' }">selected</c:if>>미지원</option>
					</select></td>
					
					<td><input type="text" name="remark${i.count}" id="remark${i.count}" class="w100 mr5" value="${tr.REMARKS}"></td>

				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach var="tr" items="${trList}" varStatus="i">
				<tr>
					<td>${tr.TR_MT}월</td>
					<td>${tr.TR_TRGET}</td>
					<td>${tr.TR_CN}</td>
					<td>${tr.TR_MBY}</td>
					<td>${tr.TR_MTH}</td>
					<td>${tr.TRINSTT_NM}</td>
					<td>${tr.TR_NMPR}</td>
					<td>${tr.TR_TM}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${tr.TR_START_DATE}"/></td>
					<td>${tr.COMPL_NMPR}</td>
					<td><c:choose>
							<c:when test="${tr.GVRN_SPORT_YN eq 'Y'}">지원</c:when>
							<c:otherwise>미지원</c:otherwise>
						</c:choose>
					</td>
					<td>${tr.REMARKS}</td>
				</tr>
			</c:forEach>
		</c:otherwise>	
	</c:choose>
	</tbody>
</table>
</div>
<c:if test="${objVal ne 'view'}">
	<div class="btns-area mt20">
		<div class="btns-right">
			<button type="button" id="addYearResult" class="btn-m02 btn-color03 three-select codeLevel">추가</button>
			<button type="button" id="deleteYearResult" class="btn-m02 btn-color02 three-select">삭제</button>
			<c:set var="yearResultLength" value="1"/>
			<c:if test="${objVal eq 'modify'}">
			<c:set var="yearResultLength" value="${fn:length(trList)}"/>
			</c:if>
			<input type="hidden" name="maxYearResultIdx" id="maxYearResultIdx" value="${yearResultLength}">
		</div>
	</div>
</c:if>

<script type="text/javascript">
	//훈련내용 입력 행 추가
	$("#addYearResult").click(function(){
		var index = $("#yearResultSubContent tbody tr").length;

		var inputList = ["trMt", "trTarget", "trCn", "trMby", "trMth", "trNmpr", "trTm", "trDate", "comNmpr", "gvrnSportYn"];
		var inputName = ["훈련시기(월)", "훈련대상", "훈련내용", "훈련주체", "훈련방법", "훈련인원", "훈련시간", "훈련일", "수료인원", "정부지원 훈련여부"];
		
		for(var i = 0; i < inputList.length; i++){
			if(!$("#" + inputList[i] + index).val()){
				alert(inputName[i] + "항목을 입력해 주세요.");
				$("#" + inputList[i] + index).focus();
				return false;
			}
		}
		index += 1;
		$("#maxYearResultIdx").val(index);
		
		var innerHtml = "";
		innerHtml += '<tr><td><input type="text" name="trMt' + index + '" id="trMt' + index + '" class="w100 mr5 month" onkeydown="fn_onlyNum(event)" onchange="fn_onlyNumNoCopy(event)"></td>'
		innerHtml += '<td><input type="text" name="trTarget' + index + '" id="trTarget' + index + '" class="w100 mr5"></td>' 
		innerHtml += '<td><input type="text" name="trCn' + index + '" id="trCn' + index + '" class="w100 mr5"></td>'
		innerHtml += '<td><input type="text" name="trMby' + index + '" id="trMby' + index + '" class="w100 mr5"></td>'
		innerHtml += '<td><input type="text" name="trMth' + index + '" id="trMth' + index + '" class="w100 mr5"></td>'
		innerHtml += '<td><input type="text" name="trInsttNm' + index + '" id="trInsttNm' + index + '" class="w100 mr5"></td>'
		innerHtml += '<td><input type="text" name="trNmpr' + index + '" id="trNmpr' + index + '" class="w100 mr5 number" onkeydown="fn_onlyNumNoCopy(event)" onchange="fn_onlyNumNoCopy(event)"></td>'
		innerHtml += '<td><input type="text" name="trTm' + index + '" id="trTm' + index + '" class="w100 mr5 number" onkeydown="fn_onlyNumNoCopy(event)" onchange="fn_onlyNumNoCopy(event)"></td>'
		innerHtml += '<td><input type="text" id="trDate' + index + '" name="trDate' + index + '" data-idx="' + index + '" class="sdate hasDatepicker w100 mr5" style="text-indent: 10px;" placeholder="훈련일시" onclick="fn_calendar(' + index + ')" onkeydown="return false;"/></td>'
		innerHtml += '<td><input type="text" name="comNmpr' + index + '" id="comNmpr' + index + '" class="w100 mr5 number" onkeydown="fn_onlyNumNoCopy(event)" onchange="fn_onlyNumNoCopy(event)"></td>'
		innerHtml += '<td><select name="gvrnSportYn' + index + '" id="gvrnSportYn' + index + '" class="w100 mr5"><option value="">선택</option><option value="Y">지원</option><option value="N">미지원</option></select></td>'
		innerHtml += '<td><input type="text" name="remark' + index + '" id="remark' + index + '" class="w100 mr5"></td></tr>'
		
		$("#yearResultSubContent > tbody:last").append(innerHtml);
		
		fn_validationYear();
	});
	
	// 훈련내용 입력 행 삭제
	$("#deleteYearResult").click(function(){
		var trCnt = $("#yearResultSubContent tbody tr").length;
		if(trCnt > 1){
			trCnt -= 1;
			$("#yearResultSubContent > tbody:last > tr:last").remove();
			$("#maxYearResultIdx").val(trCnt);
		} else {
			return false;
		}
	});
	
	function fn_validationYear(){
		$(".month").keyup(function() {
			var month = $(this).val();
			if(month > 12) {
				alert("1~12월 사이로 입력해 주세요.");
				$(this).val(12);
			}
		});
		
		$(".number").keyup(function(event) {
			if($(this).val() > 9999){
				alert("9999이하로 입력해 주세요.");
				$(this).val(9999);
			}
		});
	}
	
	// 달력 불러오기
	function fn_calendar(idx){
		var id = "#trDate" + idx;
		displayCalendar($(id), '', document.querySelector(id));
		return false;
	}
	
</script>
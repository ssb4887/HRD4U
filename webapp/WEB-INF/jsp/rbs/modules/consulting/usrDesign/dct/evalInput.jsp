<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="frm" />
<jsp:useBean id="today" class="java.util.Date" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/evalInput.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<style type="text/css">
	.cntA.on {
		background: #ffff00;
	}
	.cntB.on {
		background: #ffff00;
	}
	.cntC.on {
		background: #ffff00;
	}
	.cntD.on {
		background: #ffff00;
	}
	.cntE.on {
		background: #ffff00;
	}
</style>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<form id="${inputFormId}" name="${inputFormId}" method="post" target="submit_target" action="${contextPath}/dct/consulting/updateEntrst.do?mId=127<c:out value="${mode}"/>" enctype="multipart/form-data">
	<div class="contents-area">
		<div class="contents-box">
		<h3 class="title-type01">전문가 활동 및 평가표</h3>
			<input type="hidden" name="cnslIdx" id="id_cnslIdx" value="<c:out value="${cnsl.cnslIdx}"/>"/>
			<input type="hidden" name="bplNo" id="id_bplNo" value="<c:out value="${cnsl.bplNo}"/>"/>
			<input type="hidden" name="diaryIdx" id="id_diaryIdx" value="<c:out value="${dt.DIARY_IDX}"/>"/>
			<input type="hidden" name="evlIdx" id="id_evlIdx" value="<c:out value="${dt.EVL_IDX}"/>"/>
			<input type="hidden" name="postSeq" id="id_postSeq" value="<c:out value="${map.postSeq}"/>"/>
			<div class="board-write type02">
            <div class="one-box">
                <div class="half-box">
                    <dl>
                        <dt>
                            <label for="id_picNms">
                            	전문가 성명
                            </label>
                        </dt>
                        <dd>
                        	<input type="hidden" id="id_picNm" name="picNm" value="<c:out value="${dt.PIC_NM}"/>" />
                            <input type="text" id="id_expertNm" name="expertNm" title="전문가 성명" value="<c:out value="${cnsl.cnslTeam.members[0].mberName}"/>" disabled="disabled" />
                        </dd>
                    </dl>
                </div>
                <div class="half-box">
                    <dl>
                        <dt>
                            <label for="id_actRoleCd">
                            	활동 내역
                            </label>
                        </dt>
                        <dd>
                            <select id="id_actRoleCd" name="actRoleCd" class="w100" title="활동내역">
                                <option value="1" <c:if test="${dt.ACT_ROLE_CD eq '1'}" >selected="selected"</c:if>>현장심사</option>
                                <option value="2" <c:if test="${dt.ACT_ROLE_CD eq '2'}" >selected="selected"</c:if>>과정인정</option>
                                <option value="4" <c:if test="${dt.ACT_ROLE_CD eq '4'}" >selected="selected"</c:if>>인정심사</option>
                                <option value="5" <c:if test="${dt.ACT_ROLE_CD eq '5'}" >selected="selected"</c:if>>검토회의</option>
                                <option value="6" <c:if test="${dt.ACT_ROLE_CD eq '6'}" >selected="selected"</c:if>>서면심사</option>
                            </select>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="one-box">
                <div class="half-box">
                    <dl>
                        <dt>
                            <label for="id_actDaycnt">
                            	활동 일수
                            </label>
                        </dt>
                        <dd>
                            <input type="text" id="id_actDaycnt" name="actDaycnt" maxlength="5" title="활동 일수" value="<c:out value="${dt.ACT_DAYCNT}" />" onkeypress="isNumber();" />
                        </dd>
                    </dl>
                </div>
                <div class="half-box">
                    <dl>
                        <dt>
                            <label for="textfield03">
                            	활동 일자
                            </label>
                        </dt>
                        <dd>
                            <div class="input-calendar-area">
                                <input type="text" id="id_actDe" name="actDe" class="sdate" title="활동일자 선택" value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="yyyy-MM-dd" />" />
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>

    <div class="contents-box">
        <div class="table-type02 horizontal-scroll">
            <table>
                <caption>
                 	전문가 활동 및 평가표 : 평가지표, 세부지표, 배점, 평가점수(탁월, 우수, 보통, 미흡, 부족)에 관한 정보 제공표
                </caption>
                <colgroup>
                    <col style="width: 10%" />
                    <col style="width: 30%" />
                    <col style="width: 10%" />
                    <col style="width: 10%" />
                    <col style="width: 10%" />
                    <col style="width: 10%" />
                    <col style="width: 10%" />
                    <col style="width: 10%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col" rowspan="2">
                            	평가지표
                            </th>
                            <th scope="col" rowspan="2">
                            	세부지표
                            </th>
                            <th scope="col" rowspan="2">
                            	배점
                            </th>
                            <th scope="col" colspan="5">
                            	평가점수
                            </th>
                        </tr>
                        <tr>
                            <th scope="col">
                            	탁월
                            </th>
                            <th scope="col">
                            	우수
                            </th>
                            <th scope="col">
                            	보통
                            </th>
                            <th scope="col">
                            	미흡
                            </th>
                            <th scope="col">
                            	부족
                            </th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th scope="row" colspan="2" class="bg01">
                            	총점
                            </th>
                            <td class="bg01">
                                100
                            </td>
                            <td colspan="2" id="totalCnt" class="bg01">
                                0
                            </td>
                            <td class="bg01">
                            	점
                            </td>
                            <td class="bg01">
                            	평가:
                            </td>
                            <td class="bg01" id="resultGrade">
                            	
                            </td>
                        </tr>
                    </tfoot>
                    <tbody>
                        <tr>
                            <th scope="row">
                            	전문성
                            </th>
                            <td class="left">
                           		전문역량, 시설장비 지식, 심사평가기준 숙지 수준
                            </td>
                            <td>30</td>
                            <td id="A1" class="cntA <c:if test="${dt.EVL_ITEM1 eq '30'}" >on</c:if>" data-val="30">30</td>
                            <td id="A2" class="cntA <c:if test="${dt.EVL_ITEM1 eq '27'}" >on</c:if>" data-val="27">27</td>
							<td id="A3" class="cntA <c:if test="${dt.EVL_ITEM1 eq '24'}" >on</c:if>" data-val="24">24</td>
							<td id="A4" class="cntA <c:if test="${dt.EVL_ITEM1 eq '21'}" >on</c:if>" data-val="21">21</td>
							<td id="A5" class="cntA <c:if test="${dt.EVL_ITEM1 eq '18'}" >on</c:if>" data-val="18">18</td>
                        </tr>
                        <tr>
                            <th scope="row">
                            	사업이해도
                            </th>
                            <td class="left">
                            	당해 사업취지 및 공단사업 전반에 대한 이해정도
                            </td>
                            <td>20</td>
                            <td id="B1" class="cntB <c:if test="${dt.EVL_ITEM2 eq '20'}" >on</c:if>" data-val="20">20</td>
							<td id="B2" class="cntB <c:if test="${dt.EVL_ITEM2 eq '17'}" >on</c:if>" data-val="17">17</td>
							<td id="B3" class="cntB <c:if test="${dt.EVL_ITEM2 eq '14'}" >on</c:if>" data-val="14">14</td>
							<td id="B4" class="cntB <c:if test="${dt.EVL_ITEM2 eq '11'}" >on</c:if>" data-val="11">11</td>
							<td id="B5" class="cntB <c:if test="${dt.EVL_ITEM2 eq '8'}" >on</c:if>" data-val="8">8</td>
                        </tr>
                        <tr>
                            <th scope="row">
                            	고객지향성
                            </th>
                            <td class="left">
                            	언어 적절성, 의사전달 및 소통능력 (친밀성 및 호응도)
                            </td>
                            <td>20</td>
                           	<td id="C1" class="cntC <c:if test="${dt.EVL_ITEM3 eq '20'}" >on</c:if>" data-val="20">20</td>
							<td id="C2" class="cntC <c:if test="${dt.EVL_ITEM3 eq '17'}" >on</c:if>" data-val="17">17</td>
							<td id="C3" class="cntC <c:if test="${dt.EVL_ITEM3 eq '14'}" >on</c:if>" data-val="14">14</td>
							<td id="C4" class="cntC <c:if test="${dt.EVL_ITEM3 eq '11'}" >on</c:if>" data-val="11">11</td>
							<td id="C5" class="cntC <c:if test="${dt.EVL_ITEM3 eq '8'}" >on</c:if>" data-val="8">8</td>
                        </tr>
                        <tr>
                            <th scope="row">
                            	청렴도
                            </th>
                            <td class="left">
                            	청렴성과 윤리의식 수준, 공적업무와 사적업무 구분
                            </td>
                            <td>20</td>
                            <td id="D1" class="cntD <c:if test="${dt.EVL_ITEM4 eq '20'}" >on</c:if>" data-val="20">20</td>
							<td id="D2" class="cntD <c:if test="${dt.EVL_ITEM4 eq '17'}" >on</c:if>" data-val="17">17</td>
							<td id="D3" class="cntD <c:if test="${dt.EVL_ITEM4 eq '14'}" >on</c:if>" data-val="14">14</td>
							<td id="D4" class="cntD <c:if test="${dt.EVL_ITEM4 eq '11'}" >on</c:if>" data-val="11">11</td>
							<td id="D5" class="cntD <c:if test="${dt.EVL_ITEM4 eq '8'}" >on</c:if>" data-val="8">8</td>
                        </tr>
                        <tr>
                            <th scope="row">
                            	품위유지
                            </th>
                            <td class="left">
                            	사업수행과 관련하여 적절한 품위유지 여부
                            </td>
                            <td>10</td>
                            <td id="E1" class="cntE <c:if test="${dt.EVL_ITEM5 eq '10'}" >on</c:if>" data-val="10">10</td>
							<td id="E2" class="cntE <c:if test="${dt.EVL_ITEM5 eq '8'}" >on</c:if>" data-val="8">8</td>
							<td id="E3" class="cntE <c:if test="${dt.EVL_ITEM5 eq '6'}" >on</c:if>" data-val="6">6</td>
							<td id="E4" class="cntE <c:if test="${dt.EVL_ITEM5 eq '4'}" >on</c:if>" data-val="4">4</td>
							<td id="E5" class="cntE <c:if test="${dt.EVL_ITEM5 eq '2'}" >on</c:if>" data-val="2">2</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="contents-box">
            <div class="board-write type02">
                <div class="one-box">
                    <dl>
                        <dt>
                            <label for="id_gnrlzOpinion">
                                	종합의견<br />(특이사항)
                            </label>
                        </dt>
                        <dd>
                            <textarea id="id_gnrlzOpinion" name="gnrlzOpinion" cols="50" rows="5" title="종합의견" maxlength="1000"><c:out value="${dt.GNRLZ_OPINION}" /></textarea>
                        </dd>
                    </dl>
                </div>
                <div class="one-box">
                    <div class="half-box">
                        <dl>
                            <dt>
                                <label for="id_allwnc">
                                	수당
                                </label>
                            </dt>
                            <dd>
                                <input type="text" id="id_allwnc" name="allwnc" title="수당" maxlength="10" value="<c:out value="${dt.ALLWNC}" />" class="w100" onkeypress="isNumber();" />
                            </dd>
                        </dl>
                    </div>
                    <div class="half-box">
                        <dl>
                            <dt>
                                <label for="id_regiDate">
                                	작성일자
                                </label>
                            </dt>
                            <dd>
                                <input type="text" id="id_regiDate" name="regiDate" title="작성일자" value="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/>" class="w100" disabled />
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="one-box">
	                <dl>
	                    <dt>
	                        <label>
	                        	위촉자
	                        </label>
	                    </dt>
	                    <dd>
	                    	<c:out value="${dt.PIC_NM}" />
	                    </dd>
	                </dl>
	            </div>
	            <div class="one-box">
					<dl>
	                    <dt>
	                        <label>
	                        	평가등급
	                        </label>
	                    </dt>
	                    <dd>
	                        <strong class="point-color01">
	                            S(90점 이상), A(89∽80점), B(79∽70점), C(69∽60점), D(59∽50점), E(49점 이하))
	                        </strong>
		                    <input type="hidden" id="CA" name="evlItem1" value="${dt.EVL_ITEM1}"/>
							<input type="hidden" id="CB" name="evlItem2" value="${dt.EVL_ITEM2}"/>
							<input type="hidden" id="CC" name="evlItem3" value="${dt.EVL_ITEM3}"/>
							<input type="hidden" id="CD" name="evlItem4" value="${dt.EVL_ITEM4}"/>
							<input type="hidden" id="CE" name="evlItem5" value="${dt.EVL_ITEM5}"/>
							<input type="hidden" id="CT" name="evlTotPoint" value="${dt.EVL_TOT_POINT}"/>
							<input type="hidden" id="RE" name="evlGrad" value="${dt.EVL_GRAD}"/>
	                    </dd>
	                </dl>	            
                </div>
            </div>
        </div>
    </div>

    <div class="btns-area">
        <div class="btns-right">
            <button type="submit" class="btn-m01 btn-color03 depth2">
            	작성
            </button>
            <button type="button" onclick="history.back();" class="btn-m01 btn-color02 depth2">
            	취소
            </button>
        </div>
    </div>
</form>
</div>
<script>
	$(function() {
		$("#activeDate").datepicker();
		sumResult();
		
		$(".cntA").click(function() {
			$("#CA").val($(this).attr("data-val"));
			$(".cntA").removeClass("on");
			$(this).addClass("on");
			sumResult();
		});
		$(".cntB").click(function() {
			$("#CB").val($(this).attr("data-val"));
			$(".cntB").removeClass("on");
			$(this).addClass("on");
			sumResult();
		});
		$(".cntC").click(function() {
			$("#CC").val($(this).attr("data-val"));
			$(".cntC").removeClass("on");
			$(this).addClass("on");
			sumResult();
		});
		$(".cntD").click(function() {
			$("#CD").val($(this).attr("data-val"));
			$(".cntD").removeClass("on");
			$(this).addClass("on");
			sumResult();
		});
		$(".cntE").click(function() {
			$("#CE").val($(this).attr("data-val"));
			$(".cntE").removeClass("on");
			$(this).addClass("on");
			sumResult();
		});
	});
	
	function sumResult() {
		var ca = $("#CA").val() * 1;
		var cb = $("#CB").val() * 1;
		var cc = $("#CC").val() * 1;
		var cd = $("#CD").val() * 1;
		var ce = $("#CE").val() * 1;
		var ct = ca + cb + cc + cd + ce;
		$("#CT").val(ct);
		$("#totalCnt").text(ct);
		
		if(ct >= 90) {
			$("#resultGrade").text("S");
			$("#RE").val("S");
		} else if( ct >= 80) {
			$("#resultGrade").text("A");
			$("#RE").val("A");
		} else if( ct >= 70) {
			$("#resultGrade").text("B");
			$("#RE").val("B");
		} else if( ct >= 60) {
			$("#resultGrade").text("C");
			$("#RE").val("C");
		} else if( ct >= 50) {
			$("#resultGrade").text("D");
			$("#RE").val("D");
		} else {
			$("#resultGrade").text("E");
			$("#RE").val("E");
		}
	}
	
	function isNumber() {
		if(event.keyCode < 48 || event.keyCode>57) {
			event.returnValue=false;
		}
	}
	
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
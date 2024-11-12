<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/consulting/diary.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<h3 class="title-type02 ml0">컨설팅 수행일지</h3>
		<form id="${inputFormId}" name="${inputFormId}" method="post" target="submit_target" enctype="multipart/form-data">
			<input type="hidden" name="cnslIdx" id="id_cnslIdx" value="<c:out value="${dt.CNSL_IDX}" /> " />
			<input type="hidden" name="diaryIdx" id="id_diaryIdx" value="<c:out value="${dt.DIARY_IDX}" />" />
			<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table>
                        <caption>
                           	컨설팅 수행일지 정보표
                        </caption>
                        <colgroup>
                            <col width="15%">
	                        <col width="auto">
	                        <col width="15%">
	                        <col width="auto">
                        </colgroup>
						<tbody>
							<tr>
								<th scope="row">기업명</th>
								<td>
									<itui:objectView itemId="bplNm" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
								<th scope="row">컨설팅 유형</th>
								<td>
									<c:out value="${dt.SPORT_TYPE eq '1' ? '사업주자체 과정' : dt.SPORT_TYPE eq '2' ? '일반직무전수 OJT' : dt.SPORT_TYPE eq '3' ? '과제수행 OJT' : dt.SPORT_TYPE eq '4' ? '심층진단' : dt.SPORT_TYPE eq '5' ? '훈련체계 수립' : '현장활용'}" />
								</td>
							</tr>
							<tr>
								<th scope="row">수행일시</th>
								<td class="left" colspan="3">
									수행일자 : <fmt:formatDate value="${dt.MTG_START_DT}" pattern="yyyy년 MM월 dd일" /> <br/>
									시작시간 : <fmt:formatDate value="${dt.MTG_START_DT}" type="time" /> <br/>
									종료시간 : <fmt:formatDate value="${dt.MTG_END_DT}" type="time" />
								</td>
							</tr>
							<tr>
								<th scope="row">수행차수</th>
								<td class="left" colspan="3">
									<itui:objectView itemId="excOdr" itemInfo="${itemInfo}" objDt="${listDt}" />차
								</td>
							</tr>
							<tr>
								<th scope="row">수행방법</th>
								<td>
									<c:out value="${dt.EXC_MTH eq '1' ? '회의' : dt.EXC_MTH eq '2' ? '워크숍' : dt.EXC_MTH eq '3' ? 'FGI' : ''}"/>
								</td>
								<th scope="row">운영방식</th>
								<td>
									<c:out value="${dt.OPER_MTHD eq '1' ? '대면' : dt.OPER_MTHD eq '2' ? '비대면' : ''}"/>
								</td>
							</tr>
							<tr>
								<th scope="row" rowspan="5">참석자</th>
								<th scope="row">구분</th>
								<th scope="row" colspan="2">성명</th>
							</tr>
							<tr>
								<th scope="row" class="bg01">컨설팅책임자(PM)</th>
								<td colspan="2" class="left">
									<itui:objectView itemId="pmNm" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">내용전문가</th>
								<td colspan="2" class="left">
									<itui:objectView itemId="cnExpert" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">기업 내부전문가</th>
								<td colspan="2" class="left">
									<itui:objectView itemId="corpInnerExpert" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">기타 참석자(기업 등)</th>
								<td colspan="2" class="left">
									<itui:objectView itemId="spntPic" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
							</tr>
							<tr>
								<th scope="row" rowspan="2">수행내용</th>
								<td colspan="3" class="left">
									<itui:objectView itemId="mtgWeekExplsn1" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
							</tr>
							<tr>
								<td colspan="3" class="left">
									<itui:objectTextarea itemId="mtgCn1" itemInfo="${itemInfo}" objDt="${listDt}" />
								</td>
							</tr>
							<tr>
								<th>회의사진</th>
								<td colspan="3" class="left">
									<c:if test="${not empty multiFileHashMap.photo}">
										<c:forEach begin="0" end="${fn:length(multiFileHashMap.photo)-1}" varStatus="i">
											<img src='<c:url value='${contextPath}/dct/consulting/diaryDownload.do'/>?mId=133&diaryIdx=${dt.DIARY_IDX}&fidx=<c:out value="${not empty multiFileHashMap.photo[i.index].FLE_IDX ? multiFileHashMap.photo[i.index].FLE_IDX : 0}" />&itld=photo' alt="회의사진"/>
										</c:forEach>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
        
 		<div class="btns-area" id="btns-area-1" style="margin-top: 30px;">
 			<div class="btns-right">
	            <button type="button" class="btn-m01 btn-color01 btn-back">
	            	목록
	            </button>
          		<button type="submit" class="btn-m01 btn-color04 depth2" onclick="deleteDiary();" >
	            	삭제
	            </button>
 			</div>
        </div>
	</div>
</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>
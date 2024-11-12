<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ attribute name="divClass"%>														<% /* 검색 form layer(div) class */ %>
<%@ attribute name="formId"%>														<% /* 검색 form id */ %>
<%@ attribute name="formName"%>														<% /* 검색 form name */ %>
<%@ attribute name="formAction"%>													<% /* 검색 form action */ %>
<%@ attribute name="submitBtnId"%>													<% /* 검색 form submit버튼 id */ %>
<%@ attribute name="submitBtnClass"%>												<% /* 검색 form submit버튼 class */ %>
<%@ attribute name="submitBtnValue"%>												<% /* 검색 form submit버튼 value */ %>
<%@ attribute name="listAction"%>													<% /* 전체목록 경로 */ %>
<%@ attribute name="listBtnId"%>													<% /* 전체목록 버튼 id */ %>
<%@ attribute name="listBtnClass"%>													<% /* 전체목록 버튼 class */ %>
<%@ attribute name="listBtnValue"%>													<% /* 전체목록 버튼 value */ %>
<%@ attribute name="itemListSearch" type="net.sf.json.JSONObject"%>					<% /* 항목설정정보 > 검색항목설정정보 */ %>
<%@ attribute name="searchFormExceptParams" type="java.lang.String[]"%>				<% /* 검색 form에서 제외할 항목ID */ %>
<%@ attribute name="searchOptnHashMap" type="java.util.HashMap"%>					<% /* 기초코드 정보 */ %>
<%@ attribute name="isUseRadio" type="java.lang.Boolean"%>
<%@ attribute name="isUseMoreItem" type="java.lang.Boolean"%>						<% /* 검색조건 더보기 사용여부 */ %>
<%@ attribute name="moreBtnId"%>													<% /* 검색조건 더보기 버튼 id */ %>
<%@ attribute name="moreBtnClass"%>													<% /* 검색조건 더보기 버튼 class */ %>
<%@ attribute name="moreBtnValue"%>													<% /* 검색조건 더보기 버튼 value */ %>
<%@ attribute name="contextPath"%>
<%@ attribute name="imgPath"%>
<%@ attribute name="authList"%>
<%@ attribute name="insttList" type="java.util.List" %>
<c:if test="${empty isUseRadio}">
	<c:set var="isUseRadio" value="false"/>
</c:if>
<c:if test="${empty isUseMoreItem}">
	<c:set var="isUseMoreItem" value="false"/>
</c:if>
<c:if test="${empty isSearchList}">
	<c:set var="isSearchList" value="false"/>
</c:if>
	<spring:message var="itemSearchPreFlag" code="Globals.item.search.pre.flag"/>
	<c:if test="${!empty itemListSearch}">
		<c:set var="itemSearchOrder" value="${itemListSearch.searchForm_order}"/>
		<c:if test="${!empty itemSearchOrder}">
			<c:set var="keyItemName" value="key"/>
			<c:set var="keyFieldItemName" value="keyField"/>
			<c:set var="keyInfo" value="${itemListSearch[keyItemName]}"/>
			<c:set var="keyFieldInfo" value="${itemListSearch[keyFieldItemName]}"/>
			<c:choose>
				<c:when test="${empty divClass && !empty keyInfo && !empty keyInfo.items}"><c:set var="divClass" value="tbMSearch"/></c:when>
				<c:when test="${empty divClass}"><c:set var="divClass" value="tbSearch"/></c:when>
			</c:choose>
			<div class="${divClass}">
			<form name="${formName}" id="${formId}" method="get"  action="<c:out value="${formAction}"/>">
				<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
				<fieldset>
					<legend>상세검색</legend>
					<div class="basic-search-wrapper">
						<div class="one-box">
							<itui:searchFormItemInForDct2 itemListSearch="${itemListSearch}" searchOptnHashMap="${searchOptnHashMap}" isUseRadio="${isUseRadio}" isUseMoreItem="${isUseMoreItem}"/>
						</div>
						<div class="one-box">
							<c:forEach var="itemSearchId" items="${itemSearchOrder}">
								<c:if test="${itemSearchId == keyFieldItemName}">
									<c:if test="${!empty keyFieldInfo && !empty keyFieldInfo.select_name && !empty keyFieldInfo.query_name && !empty keyFieldInfo.items && !empty keyFieldInfo.select_order}">
									<!-- 옵션 -->
									<dl>
										<dt id="fn_dt_${keyFieldInfo.select_name}"><label for="${keyFieldInfo.select_name}">검색</label></dt>
										<dd>
											<div class="input-search-wrapper">
											<select name="${keyFieldInfo.select_name}" id="${keyFieldInfo.select_name}" class="select" title="검색옵션">
											<c:forEach var="itemKeyFieldId" items="${keyFieldInfo.select_order}">
												<c:set var="keyItem" value="${keyFieldInfo.items[itemKeyFieldId]}"/>
												<c:set var="itemKeyFieldName" value="${elfn:getItemName(keyItem)}"/>
												<option value="${itemKeyFieldId}"<c:if test="${queryString[keyFieldInfo.select_name] == itemKeyFieldId}"> selected="selected"</c:if>>${itemKeyFieldName}</option>
											</c:forEach>
											</select>
											<input type="text" name="${keyFieldInfo.query_name}" id="${keyFieldInfo.query_name}" value="<c:out value="${queryString[keyFieldInfo.query_name]}"/>" title="검색어" class="inputSearch"/>
											</div>
										</dd>
									</dl>
									</c:if>
							</c:if>
							</c:forEach>
						</div>
						<jsp:doBody/>
					</div>
					<div class="btns-area">
						<c:if test="${isUseMoreItem}">
						<c:if test="${empty moreBtnValue}"><c:set var="moreBtnValue" value="검색조건"/></c:if>
						<c:if test="${empty moreBtnId}"><c:set var="moreBtnId" value="fn_btn_moreItem"/></c:if>
						<button type="button"<c:if test="${!empty moreBtnId}"> id="${moreBtnId}"</c:if><c:if test="${!empty moreBtnClass}"> class="${moreBtnClass}"</c:if>>${moreBtnValue} 더보기</button>
						</c:if>
						<button type="submit" id="${submitBtnId}" class="codeLevel ${submitBtnClass}" value="${submitBtnValue}" title="${submitBtnValue}"><img src="${contextPath}${imgPath}/icon/icon_search03.png"/><strong>검색</strong></button>
						<c:if test="${isSearchList}">
						<c:if test="${empty listBtnValue}"><c:set var="listBtnValue" value="전체목록"/></c:if>
						<button type="button" <c:if test="${!empty listBtnId}"> id="${listBtnId}"</c:if><c:if test="${!empty listBtnClass}">  class="${listBtnClass}"</c:if>><strong>${listBtnValue}</strong></button>
						</c:if>
					</div>
				</fieldset>
			</form>
			</div>
			<itui:submitScript items="${keyInfo.items}" itemOrder="${itemSearchOrder}"/>
			<script type="text/javascript">
			$(function(){
				<itui:searchFormSubmitInit items="${keyInfo.items}" itemOrder="${itemSearchOrder}"/>
				// 검색 항목 정렬
				var varSearchDl = $("#<c:out value="${formId}"/> dl");
				var varSearchDDFull = varSearchDl.find("dd.full");
				//var varSearchDDFull = varSearchDl.find("dd.full:first");
				var varSearchDDs = varSearchDl.find("dd");
				var varAddIdx = 0;
				$.each(varSearchDDFull, function(){
					var varFullIdx = varSearchDDs.index($(this));
					if(/*varFullIdx > 0 && */(varFullIdx + varAddIdx)%2 == 1){
						varSearchDDs.filter(":eq(" + (varFullIdx - 1) + ")").addClass("full");
					}
					varAddIdx ++;
				});
				
				<c:if test="${isUseMoreItem}">
				// 검색조건 숨기기 / 더보기
				$("#${moreBtnId}").click(function(){
					var varText;
					($(this).text() == "${moreBtnValue} 더보기")?varText="${moreBtnValue} 숨기기":varText="${moreBtnValue} 더보기";
					$(this).text(varText);
					$("#${formId} .fn_moreItem").toggleClass("hide");
				});
				</c:if>
				try {
					$("#${formId} .fn_currentItem").keydown(function(event){
						return fn_onlyNumKD(event);
					});
					
					<%/* 한글입력막기  */%>
					$.each($("#${formId} .fn_currentItem"), function(){
						var varId = $(this).attr("id");
						document.getElementById(varId).addEventListener('paste', function(event){
							var clipboardData, pastedData;
	
						    // Stop data actually being pasted into div
						    event.stopPropagation();
						    event.preventDefault();
						
						    // Get pasted data via clipboard API
						    clipboardData = event.clipboardData || window.clipboardData;
						    pastedData = clipboardData.getData('Text');
							fn_preventHan(event, $(this), pastedData);
						});
					});
					$("#${formId} .fn_currentItem").keyup(function(event){
						return fn_preventHan(event, $(this));
					});
				} catch(e) {}
				
				$.each($(".fn_search_select_class"), function(){
					var varId = $(this).attr("id");
					try {fn_setInitItemOrderOption(varId);} catch(e){}
					try {fn_setItemOrderOption(varId);} catch(e){}
				});
				
				$("#fn_btn_totallist").click(function() {
					location.href="${formAction}";
				})
				;
				$("#fn_btn_search").click(function() {
					if($("#is_blockCode1").find("option:selected").val() && !$("#is_blockCode2").find("option:selected").val()){
						alert("관할구역의 시군구를 선택하세요.");
						$("#is_blockCode2").focus();
						return false;
					}
				});
				
				$("#${formId}").submit(function(){
					$.each($(".fn_search_select_class"), function(){
						var varId = $(this).attr("id");
						try {
							$("select[name='" + varId + "_tmp']").each(function(index, item){
								var varVal = $(item).val();
								if(varVal) $("#" + varId).val(varVal);
							});
						}catch(e){
						}
					});
				});
			});
			</script>
		</c:if>
	</c:if>
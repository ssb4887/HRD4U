<%@ include file="../include/commonTop.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
 
<c:if test="${!empty TOP_PAGE}"><jsp:include page = "../include/main_top.jsp" flush = "false"/></c:if>

<%
// 메인메뉴의 공지사항의 새글 이미지를 입히기 위해 현재날짜의 한시간을 빼서 준비한다

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.HOUR_OF_DAY, -1);	//한시간 뺌
	String currentDateMinusOneHour = sdf.format(cal.getTime());
%>
 <!-- container -->
 <section>
     <div class="main-container" id="container">
     
         <div class="main-contents" id="contents">
             <div class="main-contents01">
                 <div class="main-contents-wrapper">
                 
                     <div class="main-visaul-banner-wrapper">
                         <div class="main-visual-wrapper">
                             <dl>
                                 <dt>여러분의 전담주치의가 되어 드리겠습니다.</dt>
                                 <dd>
                                     <span>중소기업의 혁신성장을 위한 </span>
                                     <span>든든한 뒷받침</span>
                                     <span>능력개발전담주치의</span>
                                 </dd>
                             </dl>
                             <img src="${contextPath}${imgPath}/main/img_visual01.png" alt="" class="bg01" />
                             <img src="${contextPath}${imgPath}/main/img_visual02.png" alt="" class="bg02" />
                             <img src="${contextPath}${imgPath}/main/img_visual03.png" alt="" class="bg03" />
                         </div>
                     </div>
                     
					<div class="main-log-wrapper">
						<c:set var="listIdxName" value="memberIdx"/>
						<c:set var="listKey" value="${loginVO.memberIdx}"/>
						<c:set var="corpIdxName" value="corpNum"/>
						<c:set var="corpKey" value="${loginVO.corpNum}"/>
						<c:choose>
							<c:when test="${elfn:isLoginAuth()}">
		                        <!-- 로그인 후 -->
		                        <div class="main-logout-wrapper">
		                        <p><img src="../images/main/icon_smile.png" alt="" /><strong><c:out value="${loginVO.memberNameOrg}" /></strong>님 안녕하세요.</p>
		                        <div class="log-btns">
		                        	<c:choose>  
										<c:when test="${loginVO.usertypeIdx >= 20}"><a href="../memberManage/inputCenter.do?mId=5&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}" class="btn-color03">마이페이지</a></c:when>
										<c:when test="${loginVO.usertypeIdx >= 10}"><a href="../memberManage/inputConsult.do?mId=5&${listIdxName}=${listKey}" class="btn-color03">마이페이지</a></c:when>
										<c:otherwise><a href="../memberManage/inputCorp.do?mId=5&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}" class="btn-color03">마이페이지</a></c:otherwise>
									</c:choose>
		                            <a href="<%=MenuUtil.getMenuUrl(4) %>" class="btn-color02"><strong>로그아웃</strong></a>
		                        </div>
		                        </div>
		                        <!-- //로그인 후 -->
							</c:when>
							<c:otherwise>
								<!-- 로그인 전 -->
								<div class="main-login-wrapper">
								<h2>안녕하세요.<br /><strong>로그인</strong>을 해주세요.</h2>
								<form id="loginFrm" name="loginFrm" method="post" action="<c:out value="../login/loginPreProc.do?mId=3"/>" target="submit_target">
									<label for="mbrId" class="blind">아이디</label>
		                   			<input id="mbrId" name="mbrId" title="아이디를 입력하세요." type="search" placeholder="아이디" value="">
		                    		<label for="mbrPwd" class="blind"> 비밀번호</label>
		                    		<input id="mbrPwd" name="mbrPwd" title="비밀번호를 입력하세요." type="password" placeholder="비밀번호" value="" style="margin-top:4px;">
			                        <input type="submit" id="fn_logIn_btn" class="btnLogin" value="로그인"/>
								</form>
								<ul>
									<li>
										<a href="https://hrd4u.or.kr/portal/member/find_id.do"> 아이디찾기</a>
			                        </li>
			                        <li>
			                            <a href="https://hrd4u.or.kr/portal/member/find_pw.do"> 비밀번호찾기 </a>
			                        </li>
			                    </ul>
			                    </div>
		                    	<!-- //로그인 전 -->
							</c:otherwise>
						</c:choose>
					</div>
                     
                 </div>
             </div>

             <div class="main-contents02">
                 <div class="main-contents-wrapper">
                 
                 	<div class="main-menu02-wrapper">
                         <a href="<%=MenuUtil.getMenuUrl(122) %>" class="bg01">
                             <img src="${contextPath}${imgPath}/main/icon_menu0201.png" alt="" class="icon" />
                             <span class="main-menu02-area">
                             	<strong>HRD 상담신청</strong>
                             	<span>기업맞춤 성장코칭 능력개발전담주치의</span>
                             </span>
                         </a>
                         <a href="<%=MenuUtil.getMenuUrl(53) %>" class="bg02">
                             <img src="${contextPath}${imgPath}/main/icon_menu0202.png" alt="" class="icon" />
                             <span class="main-menu02-area">
                                 	<strong>HRD기초진단 신청</strong>
                             	<span>기업의 기초역량을 진단해드립니다.</span>
                             </span>
                         </a>
                     </div>
                     
                     <div class="main-board-wrapper">
                     	<ul>
							<li>
								<button type="button" class="active">공지사항</button>
								<div class="main-board-area">
									<c:set var="boardModuleIdx" value="board"/>
									<c:set var="boardMenuKey" value="board1"/>
									<c:if test="${!empty boardDashboardObject && !empty boardDashboardObject[boardMenuKey] && !empty boardDashboardObject[boardMenuKey]['menu_idx']}">
									<c:set var="boardMenuIdx" value="${boardDashboardObject[boardMenuKey]['menu_idx']}"/>
									<c:set var="boardList" value="${boardDashboardHashMap[boardMenuKey]}"/>
									</c:if>
									<!--공지사항 -->										
									<ul>
										<c:forEach var="boardDt" items="${boardList}" varStatus="i">
											<li>
												<a href="<c:out value="${contextPath}/${crtSiteId}/${boardModuleIdx}/view.do?mId=${boardMenuIdx}&brdIdx=${boardDt.BRD_IDX}"/>" title="<c:out value="${boardDt.SUBJECT}"/>">
													<strong class="title">
														<c:if test="${!empty boardDt.CATE_CODE_NAME}">[${boardDt.CATE_CODE_NAME}] </c:if>
														<c:out value="${boardDt.SUBJECT}"/>																
													</strong>															
													<!-- 공지사항의 등록날짜(REGI_DATE)가 jsp에서 가져온 날짜(currentDateMinusOneHour)보다 크면 새글 이미지를 붙인다 -->
													<c:set var="MinusOneHour" value="<%=currentDateMinusOneHour %>"/>															
									                                        <c:if test="${boardDt.REGI_DATE gt MinusOneHour}">			    	
									                                        	<img src="${contextPath}${imgPath}/main/icon_new01.png" alt="New(새글)" />
									                                        </c:if>
													<span class="date">
														<fmt:formatDate pattern="yyyy-MM-dd" value="${boardDt.REGI_DATE}"/>
													</span>
												</a>
											</li>
									</c:forEach>
									</ul>
									<a href="${elfn:getMenuUrl(boardMenuIdx)}" title="더보기" class="btn-more">더보기</a>	
								</div>
							</li>
							<li>
								<button type="button">HRD자료실</button>
								<div class="main-board-area">
									<c:set var="boardModuleIdx" value="board"/>
									<c:set var="boardMenuKey" value="board2"/>
									<c:if test="${!empty boardDashboardObject && !empty boardDashboardObject[boardMenuKey] && !empty boardDashboardObject[boardMenuKey]['menu_idx']}">
									<c:set var="boardMenuIdx" value="${boardDashboardObject[boardMenuKey]['menu_idx']}"/>
									<c:set var="boardList" value="${boardDashboardHashMap[boardMenuKey]}"/>
									</c:if>
									<!--공지사항 -->										
									<ul>
										<c:forEach var="boardDt" items="${boardList}" varStatus="i">
											<li>
												<a href="<c:out value="${contextPath}/${crtSiteId}/${boardModuleIdx}/view.do?mId=${boardMenuIdx}&brdIdx=${boardDt.BRD_IDX}"/>" title="<c:out value="${boardDt.SUBJECT}"/>">
													<strong class="title">
														<c:if test="${!empty boardDt.CATE_CODE_NAME}">[${boardDt.CATE_CODE_NAME}] </c:if>
														<c:out value="${boardDt.SUBJECT}"/>																
													</strong>															
													<!-- 공지사항의 등록날짜(REGI_DATE)가 jsp에서 가져온 날짜(currentDateMinusOneHour)보다 크면 새글 이미지를 붙인다 -->
													<c:set var="MinusOneHour" value="<%=currentDateMinusOneHour %>"/>															
									                                        <c:if test="${boardDt.REGI_DATE gt MinusOneHour}">			    	
									                                        	<img src="${contextPath}${imgPath}/main/icon_new01.png" alt="New(새글)" />
									                                        </c:if>
													<span class="date">
														<fmt:formatDate pattern="yyyy-MM-dd" value="${boardDt.REGI_DATE}"/>
													</span>
												</a>
											</li>
									</c:forEach>
									</ul>
									<a href="${elfn:getMenuUrl(boardMenuIdx)}" title="더보기" class="btn-more">더보기</a>	
								</div>
							</li>
                     	</ul>
<!--                                     <ul> -->
<%--                                     <c:forEach var="listDt" items="${list}" varStatus="i"> --%>
<!--                                        <li> -->
<!--                                             <a href="#"> -->
<!--                                                 <strong class="title"> -->
<%--                                                     ${listDt.CONTENTS } --%>
<!--                                                 </strong> -->
<!--                                                 공지사항의 등록날짜(REGI_DATE)가 jsp에서 가져온 날짜(currentDateMinusOneHour)보다 크면 새글 이미지를 붙인다 -->
<%--                                                 <c:if test="${listDt.REGI_DATE gt currentDateMinusOneHour }"> --%>
<%--                                                 	<img src="${contextPath}${imgPath}/main/icon_new01.png" alt="New(새글)" /> --%>
<%--                                                 </c:if> --%>
<!--                                                 <span class="date"> -->
<%--                                                 <fmt:parseDate var="dateString" pattern="yyyy-MM-dd HH:mm:ss.S" value=" ${listDt.REGI_DATE }"/> --%>
                                     
<%--                                                 <fmt:formatDate pattern="yyyy.MM.dd" value="${dateString}"/> --%>
<!--                                                 </span> -->
<!--                                             </a> -->
<!--                                         </li> -->
<%--                                     </c:forEach> --%>
<!--                                     </ul> -->

					</div>

					<div class="main-banner-slider-wrapper">
	                    <div class="owl-carousel" id="main-banner-slider">
	                         <div class="item">
	                             <a href="#">
	                                 <img src="${contextPath}${imgPath}/main/img_silde01.png" alt="2023년 최우수 우수기업을 찾아라 능력개발 이벤트" />
	                             </a>
	                         </div>
	                         <div class="item">
	                             <a href="#">
	                                 <img src="${contextPath}${imgPath}/main/img_silde01.png" alt="2023년 최우수 우수기업을 찾아라 능력개발 이벤트" />
	                             </a>
	                         </div>
	                     </div>
	                     <div class="main-banner-option">
	                         <div id="counter"></div>
	                         <button type="button" class="btn-play" style="display: none">play</button>
	                         <button type="button" class="btn-stop">stop</button>
	                     </div>
                     </div>

<!--                      <div class="main-menu01-wrapper"> -->
<!--                          <h2 class="main-title"> -->
<!--                              훈련패키지 -->
<!--                          </h2> -->

<!--                          <div class="main-menu01-area"> -->
<%--                              <a href="<%=MenuUtil.getMenuUrl(81) %>"> --%>
<!--                                  <span class="icon"> -->
<%--                                      <img src="${contextPath}${imgPath}/main/icon_menu01.svg" alt="" /> --%>
<!--                                  </span> -->
<!--                                  <strong> -->
<!--                                      훈련패키지 소개 -->
<!--                                  </strong> -->
<!--                              </a> -->

<%--                              <a href="<%=MenuUtil.getMenuUrl(82) %>"> --%>
<!--                                  <span class="icon"> -->
<%--                                      <img src="${contextPath}${imgPath}/main/icon_menu02.svg" alt="" /> --%>
<!--                                  </span> -->
<!--                                  <strong> -->
<!--                                      직업능력개발 <br /> -->
<!--                                      표준지수 -->
<!--                                  </strong> -->
<!--                              </a> -->

<%--                              <a href="<%=MenuUtil.getMenuUrl(83) %>"> --%>
<!--                                  <span class="icon"> -->
<%--                                      <img src="${contextPath}${imgPath}/main/icon_menu03.svg" alt="" /> --%>
<!--                                  </span> -->
<!--                                  <strong> -->
<!--                                      훈련과정ZIP -->
<!--                                  </strong> -->
<!--                              </a> -->

<%--                              <a href="<%=MenuUtil.getMenuUrl(84) %>"> --%>
<!--                                  <span class="icon"> -->
<%--                                      <img src="${contextPath}${imgPath}/main/icon_menu04.svg" alt="" /> --%>
<!--                                  </span> -->
<!--                                  <strong> -->
<!--                                      훈련가이드북 -->
<!--                                  </strong> -->
<!--                              </a> -->
<!--                          </div> -->
<!--                      </div> -->
                     
                 </div>
             </div>

         </div>
     </div>
 </section>
 <!-- //container -->


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "../include/main_bottom.jsp" flush = "false"/></c:if>
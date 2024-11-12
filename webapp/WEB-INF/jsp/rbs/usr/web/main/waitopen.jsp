<%@ include file="../include/commonTop.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
 
<c:if test="${!empty TOP_PAGE}"><jsp:include page = "../include/main_top.jsp" flush = "false"/></c:if>

           <section>
                <div class="container" id="container">
                    <!-- contents navigation, content options -->
                        <!-- contents navigation, content options -->
                        <div class="container-wrapper">
                            <div class="lnb-wrapper">
                                <div class="lnb-area">
                                
                                </div>
                            </div>
                            <!-- contents  -->
                            <article>
                                <div class="contents" id="contents">
                                    <div class="contents-wrapper">
                                        <!-- CMS 시작 -->
										<!-- 2023.10.18 2차 오픈 대상입니다. 준비중 작업 샘플 -->
										<div class="images-box">
											<img src="${contextPath}/dct/images/common/img_loading01.png" alt="2차 오픈 대상입니다. 빠른 시일내에 업데이트 되도록 하겠습니다." />
										</div> 
										<!-- 2023.10.18 2차 오픈 대상입니다. 준비중 작업 샘플 -->
                                        <!-- //CMS 끝 -->
                                    </div>
                                </div>
                            </article>
                        </div>
                        <!-- //contents  -->
                </div>
            </section>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "../include/main_bottom.jsp" flush = "false"/></c:if>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
    <jsp:include page="../include/meta.html"/>
        <title>
            기초진단관리 &lt; 기초진단 &lt; 기업진단 &lt; 기업직업훈련 지원시스템
        </title>
</head>

<body>
    <!-- skip navigation -->
    <p class="skip-navigation">
        <a href="#contents">본문 바로가기</a>
    </p>
    <!-- //skip navigation -->

    <!-- wrapper -->
    <div class="wrapper" id="wrapper">

        <jsp:include page="../include/popup.html"/>

            <!-- header -->
            <header>
                <jsp:include page= "../include/header.html" />
            </header>
            <!-- //header -->

            <!-- sub visual -->
            <div class="sub-visual">
                <h2>
                    기초진단관리
                </h2>

                <jsp:include page= "../include/additional_function.html" />
            </div>
            <!-- sub visual -->
            <!-- container -->
            <section>
                <div class="container" id="container">
                    <!-- contents navigation, content options -->
                    <jsp:include page="../include/contents_navigation.html"/>
                        <!-- contents navigation, content options -->
                        <div class="container-wrapper">
                            <div class="lnb-wrapper">
                                <div class="lnb-area">
                                    <jsp:include page="../include/lnb02.html"/>
                                </div>
                            </div>
                            <!-- contents  -->
                            <article>
                                <div class="contents" id="contents">
                                    <div class="contents-wrapper">

                                        <!-- CMS 시작 -->

                                        <div class="contents-area">
                                            <div class="contents-box pl0">
                                                <div class="temp-contets-area type02">
                                                    기초진단소개
                                                </div>
                                            </div>

                                            <div class="btns-area">
                                                <button type="button" class="btn-b01 round01 btn-color03 left">
                                                    <span>
                                                        신청하기
                                                    </span>
                                                    <img src="../img/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
                                                </button>
                                            </div>
                                        </div>





                                        <!-- //CMS 끝 -->


                                    </div>
                                </div>
                            </article>
                        </div>
                        <!-- //contents  -->

                </div>
            </section>
            <!-- //container -->

            <!-- footer -->
            <footer>
                <jsp:include page="../include/footer.html"/>
            </footer>
            <!-- //footer -->
    </div>
    <!-- //wrapper -->
    <script>
        menuOn(2, 1, 1, 0);
    </script>
</body>

</html>
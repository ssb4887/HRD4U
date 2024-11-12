<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
    <jsp:include page="../include/meta.html"/>
        <title>
            로그인 &lt; 기업직업훈련 지원시스템
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
                    로그인
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
                                    <jsp:include page="../include/lnb05.html"/>
                                </div>
                            </div>
                            <!-- contents  -->
                            <article>
                                <div class="contents" id="contents">
                                    <div class="contents-wrapper">

                                        <!-- CMS 시작 -->


                                        <div class="login-area">
                                            <div class="login-box">
                                                <form action="#" id="loginFrm" name="loginFrm" method="post">
                                                    <fieldset>
                                                        <legend class="blind">로그인 폼</legend>
                                                        <label for="userId" class="blind">
                                                            아이디
                                                        </label>
                                                        <input id="userId" name="userId" title="아이디를 입력하세요." type="search" placeholder="아이디" value="">
                                                        <label for="password" class="blind">
                                                            비밀번호
                                                        </label>
                                                        <input id="password" name="password" title="비밀번호를 입력하세요." type="password" placeholder="비밀번호" value="">
                                                        <p class="save-id">
                                                            <!-- 2018.10. 접근성 작업을 위한 수정 체크 -->
                                                            <input name="checksaveid" id="checksaveid" type="checkbox" value="">
                                                            <label for="checksaveid"> 아이디 저장 </label>
                                                            <!-- 2018.10. 접근성 작업을 위한 수정 체크 -->
                                                        </p>
                                                        <input type="submit" id="btnLogin" value="로그인">

                                                        <div class="members-menu-list">
                                                            <a href="#" class="btn-join01">
                                                                <span class="icon"></span>
                                                                <strong>
                                                                    회원가입
                                                                </strong>
                                                                <span class="arrow"></span>
                                                            </a>

                                                            <div class="word-find">
                                                                <a href="#"> 아이디찾기</a>
                                                                <a href="#"> 비밀번호찾기 </a>
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                </form>
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
        menuOn(5, 1, 0, 0);
    </script>
</body>

</html>
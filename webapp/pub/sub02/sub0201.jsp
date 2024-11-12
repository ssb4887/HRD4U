<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
    <jsp:include page="../include/meta.html"/>
        <title>
            HRD기초컨설팅 신청 &lt; HRD기초컨설팅 &lt; 기업진단 &lt; 기업직업훈련 지원시스템
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
                    HRD기초컨설팅 신청
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
                                                    HRD기초컨설팅 소개 Contents
                                                </div>
                                            </div>

                                            <div class="contents-box pl0">
                                                <div class="table-type01 horizontal-scroll">
                                                    <table class="width-type02">
                                                        <caption>
                                                            HRD기초컨설팅 소개 정보표 : 선택, 기초진단서 발급번호, 발급일자, 업체명, 사업장번호에 관한 정보 제공표
                                                        </caption>
                                                        <colgroup>
                                                            <col style="width: 10%" />
                                                            <col style="width: 30%" />
                                                            <col style="width: 20%" />
                                                            <col style="width: 20%" />
                                                            <col style="width: 20%" />
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">
                                                                    선택
                                                                </th>
                                                                <th scope="col">
                                                                    기초진단서 발급번호
                                                                </th>
                                                                <th scope="col">
                                                                    발급일자
                                                                </th>
                                                                <th scope="col">
                                                                    업체명
                                                                </th>
                                                                <th scope="col">
                                                                    사업장번호
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>
                                                                    <input type="radio" id="radio0101" name="radio01" value="" class="radio-type01">
                                                                </td>
                                                                <td>
                                                                    21-00-0000-01
                                                                </td>
                                                                <td>
                                                                    2023.09.24
                                                                </td>
                                                                <td>
                                                                    <span class="point-color01">
                                                                        순양전자검단본부
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    728000001
                                                                </td>
                                                            </tr>

                                                            <tr>
                                                                <td>
                                                                    <input type="radio" id="radio0102" name="radio01" value="" class="radio-type01">
                                                                </td>
                                                                <td>
                                                                    21-00-0000-01
                                                                </td>
                                                                <td>
                                                                    2023.09.24
                                                                </td>
                                                                <td>
                                                                    <span class="point-color01">
                                                                        순양전자검단본부
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    3220011101
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
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
        menuOn(2, 2, 1, 0);
    </script>
</body>

</html>
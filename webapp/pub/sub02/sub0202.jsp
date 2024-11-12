<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
    <jsp:include page="../include/meta.html"/>
        <title>
            HRD기초컨설팅 관리 &lt; HRD기초컨설팅 &lt; 기업진단 &lt; 기업직업훈련 지원시스템
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
                    HRD기초컨설팅 관리

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

                                        <div class="contents-area02">
                                            <form action="" method="">
                                                <fieldset>
                                                    <legend class="blind">
                                                        HRD기초컨설팅 이력 검색양식
                                                    </legend>
                                                    <div class="basic-search-wrapper">

                                                        <div class="one-box">
                                                            <div class="half-box">
                                                                <dl>
                                                                    <dt>
                                                                        <label for="textfield01">
                                                                            발급번호
                                                                        </label>
                                                                    </dt>
                                                                    <dd>
                                                                        <input type="text" id="textfield01" name="" value="" title="발급번호 입력" placeholder="발급번호" />
                                                                    </dd>
                                                                </dl>
                                                            </div>
                                                            <div class="half-box">
                                                                <dl>
                                                                    <dt>
                                                                        <label for="textfield02">
                                                                            기업명
                                                                        </label>
                                                                    </dt>
                                                                    <dd>
                                                                        <select id="textfield02" name="">
                                                                            <option value="">
                                                                                선택
                                                                            </option>
                                                                        </select>
                                                                    </dd>
                                                                </dl>
                                                            </div>
                                                        </div>

                                                        <div class="one-box">
                                                            <div class="half-box">
                                                                <dl>
                                                                    <dt>
                                                                        <label>
                                                                            기초진단일
                                                                        </label>
                                                                    </dt>
                                                                    <dd>
                                                                        <div class="input-calendar-wrapper">
                                                                            <div class="input-calendar-area">
                                                                                <input type="text" id="" name="" class="sdate" title="기준 시작일 입력" placeholder="기준 시작일" />
                                                                            </div>
                                                                            <div class="word-unit">
                                                                                ~
                                                                            </div>
                                                                            <div class="input-calendar-area">
                                                                                <input type="text" id="" name="" class="edate" title="기준 시작일 입력" placeholder="기준 종료일" />
                                                                            </div>
                                                                        </div>
                                                                    </dd>
                                                                </dl>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="btns-area">
                                                        <button type="submit" class="btn-search02">
                                                            <img src="../img/icon/icon_search03.png" alt="" />
                                                            <strong>
                                                                검색
                                                            </strong>
                                                        </button>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div>


                                        <p class="total">
                                            총 <strong>249</strong> 건 ( 1 / 1 페이지 )
                                        </p>

                                        <div class="table-type01 horizontal-scroll">
                                            <table>
                                                <caption>
                                                    HRD기초컨설팅 이력표 : 번호, 발급번호, 기업명, 사업장관리번호, 기초진단, 설문조사, 진행현황, 출력에 관한 정보 제공표
                                                </caption>
                                                <colgroup>
                                                    <col style="width: 7%" />
                                                    <col style="width: 18%" />
                                                    <col style="width: 12%" />
                                                    <col style="width: 13%" />
                                                    <col style="width: 15%" />
                                                    <col style="width: 12%" />
                                                    <col style="width: 10%" />
                                                    <col style="width: 12%" />
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th scope="col">
                                                            번호
                                                        </th>
                                                        <th scope="col">
                                                            발급번호
                                                        </th>
                                                        <th scope="col">
                                                            기업명
                                                        </th>
                                                        <th scope="col">
                                                            사업장관리번호
                                                        </th>
                                                        <th scope="col">
                                                            기초진단
                                                        </th>
                                                        <th scope="col">
                                                            설문조사
                                                        </th>
                                                        <th scope="col">
                                                            진행현황
                                                        </th>
                                                        <th scope="col">
                                                            출력
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                            249
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                21-00-0000-01
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            솔○○스
                                                        </td>
                                                        <td>
                                                            4060000111
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                2023.09.24
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn-m02 btn-color03 w100">
                                                                <span>
                                                                    수정하기
                                                                </span>
                                                            </button>
                                                        </td>
                                                        <td>
                                                            <span class="point-color02">
                                                                진행
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn-m02 btn-color02 w100">
                                                                <span>
                                                                    출력하기
                                                                </span>
                                                            </button>
                                                        </td>
                                                    </tr>


                                                    <tr>
                                                        <td>
                                                            248
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                21-00-0000-01
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            어○○○○○○브
                                                        </td>
                                                        <td>
                                                            4060000111
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                2023.06.23
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn-m02 btn-color03 w100">
                                                                <span>
                                                                    수정하기
                                                                </span>
                                                            </button>
                                                        </td>
                                                        <td>
                                                            <span class="point-color02">
                                                                진행
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn-m02 btn-color02 w100">
                                                                <span>
                                                                    출력하기
                                                                </span>
                                                            </button>
                                                        </td>
                                                    </tr>



                                                    <tr>
                                                        <td>
                                                            247
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                21-00-0000-01
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            세○○원
                                                        </td>
                                                        <td>
                                                            4060000111
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                2023.05.11
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                2023.05.11
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <span class="point-color03">
                                                                완료
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn-m02 btn-color02 w100">
                                                                <span>
                                                                    출력하기
                                                                </span>
                                                            </button>
                                                        </td>
                                                    </tr>




                                                    <tr>
                                                        <td>
                                                            246
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                21-00-0000-01
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            한○○○일
                                                        </td>
                                                        <td>
                                                            4060000111
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                2023.05.11
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <strong class="point-color01">
                                                                2023.05.11
                                                            </strong>
                                                            <a href="#" class="btn-linked">
                                                                <img src="../img/icon/icon_search04.png" alt="" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <span class="point-color04">
                                                                반려
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn-m02 btn-color02 w100">
                                                                <span>
                                                                    출력하기
                                                                </span>
                                                            </button>
                                                        </td>
                                                    </tr>






                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="paging-navigation-wrapper">
                                            <!-- 페이징 네비게이션 -->
                                            <p class="paging-navigation">
                                                <a href="#none" class="btn-first">맨 처음 페이지로 이동</a>
                                                <a href="#none" class="btn-preview">이전 페이지로 이동</a>
                                                <strong>1</strong>
                                                <a href="#">2</a>
                                                <a href="#">3</a>
                                                <a href="#">4</a>
                                                <a href="#">5</a>
                                                <a href="#none" class="btn-next">다음 페이지로 이동</a>
                                                <a href="#none" class="btn-last">맨 마지막 페이지로 이동</a>
                                            </p>

                                            <!-- //페이징 네비게이션 -->
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
        menuOn(2, 2, 2, 0);
    </script>
</body>

</html>
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
                                            <div class="temp-contets-area">
                                                희망 사업 선택 및 안내 Contents
                                            </div>

                                            <form action="" method="">
                                                <fieldset>
                                                    <legend class="blind">
                                                        희망사업 검색양식
                                                    </legend>
                                                    <div class="search-wrapper02">
                                                        <div class="search-area02">
                                                            <label for="search01">
                                                                희망사업
                                                            </label>
                                                            <select id="" name="" class="w100">
                                                                <option value="">
                                                                    선택해주세요
                                                                </option>
                                                            </select>
                                                            <input type="submit" id="" name="" value="검색" />
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div>

                                        <div class="contents-area">
                                            <h3 class="title-type01 ml0">
                                                기업개요
                                            </h3>
                                            <div class="table-type03 horizontal-scroll">
                                                <table class="width-type02">
                                                    <caption>
                                                        희망기업 - 기업개요 정보표 : 기업명, 사업장 관리번호, 업종코드, 소재지, 고용보험성립일자, 업종, 기업유형, 상시 근로자수
                                                    </caption>
                                                    <colgroup>
                                                        <col style="width: 15%" />
                                                        <col style="width: 30%" />
                                                        <col style="width: 15%" />
                                                        <col style="width: 20%" />
                                                        <col style="width: 15%" />
                                                        <col style="width: 10%" />
                                                    </colgroup>
                                                    <tbody>
                                                        <tr>
                                                            <th scope="row">
                                                                기업명
                                                            </th>
                                                            <td class="left">
                                                                주식회사세OOOO윈
                                                            </td>
                                                            <th scope="row">
                                                                사업장 관리번호
                                                            </th>
                                                            <td class="left">
                                                                4060000111
                                                            </td>
                                                            <th scope="row">
                                                                업종코드
                                                            </th>
                                                            <td>
                                                                0000
                                                            </td>
                                                        </tr>

                                                        <tr>
                                                            <th scope="row">
                                                                소재지
                                                            </th>
                                                            <td class="left" colspan="5">
                                                                [54882] 전라북도 전주시 OO구 OO길 17 (우정동2가)
                                                            </td>
                                                        </tr>


                                                        <tr>
                                                            <th scope="row">
                                                                고용보험성립일자
                                                            </th>
                                                            <td class="left">
                                                                <span class="word-satisfy">
                                                                    <strong>20161001</strong>
                                                                    <img src="../img/icon/icon_satisfy.png" alt="" />
                                                                </span>
                                                            </td>
                                                            <th scope="row">
                                                                업종
                                                            </th>
                                                            <td class="left" colspan="3">
                                                                <span class="word-none-satisfy">
                                                                    <strong>외국어학원</strong>                                                                    
                                                                    <img src="../img/icon/icon_none_satisfy.png" alt="" />
                                                                </span>
                                                            </td>
                                                        </tr>


                                                        <tr>
                                                            <th scope="row">
                                                                기업유형
                                                            </th>
                                                            <td class="left">
                                                                <span class="word-satisfy">
                                                                    <strong>우선지원대상기업</strong>
                                                                    
                                                                    <img src="../img/icon/icon_satisfy.png" alt="" />
                                                                </span>
                                                            </td>
                                                            <th scope="row">
                                                                상시 근로자수
                                                            </th>
                                                            <td class="left" colspan="3">
                                                                <span class="word-satisfy">
                                                                    <strong>27</strong>
                                                                    
                                                                    <img src="../img/icon/icon_satisfy.png" alt="" />
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>

                                            <div class="business-support-information">
                                                <h4>
                                                    S-OJT 지원 대상 조건 안내
                                                </h4>

                                                <ul>
                                                    <li>
                                                        <span class="word-satisfy">
                                                            <img src="../img/icon/icon_satisfy.png" alt="" />
                                                            <strong>고용보험 가입 여부 : 가입</strong>
                                                        </span>
                                                    </li>
                                                    <li>
                                                        <span class="word-satisfy">
                                                            <img src="../img/icon/icon_satisfy.png" alt="" />
                                                            <strong>기업규모 : 우선지원대상 기업</strong>
                                                        </span>
                                                    </li>
                                                    <li>
                                                        <span class="word-satisfy">
                                                            <img src="../img/icon/icon_satisfy.png" alt="" />
                                                            <strong>상시근로자수 : 제한 없음</strong>
                                                        </span>
                                                    </li>
                                                    <li>
                                                        <span class="word-none-satisfy">
                                                            <img src="../img/icon/icon_none_satisfy.png" alt="" />
                                                            <strong>제외 업종 : 외국어</strong>
                                                        </span>
                                                    </li>
                                                </ul>

                                                <p class="word-type02 circle-bullet">
                                                    특이사항 : 기업선정 및 약정체결 필요
                                                </p>
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
        menuOn(2, 1, 2, 0);
    </script>
</body>

</html>
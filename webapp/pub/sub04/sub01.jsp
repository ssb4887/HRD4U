<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
    <jsp:include page="../include/meta.html"/>
        <title>
            훈련과정 서비스 소개 &lt; 훈련과정 ZIP &lt; 훈련패키지 &lt; 기업직업훈련 지원시스템
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
                    훈련과정 서비스 소개
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
                                    <jsp:include page="../include/lnb04.html"/>
                                </div>
                            </div>
                            <!-- contents  -->
                            <article>
                                <div class="contents" id="contents">
                                    <div class="contents-wrapper">

                                        <!-- CMS 시작 -->


                                        <div class="search-wrapper">
                                            <form id="frm" name="frm" action="" method="post">
                                                <fieldset class="clear">
                                                    <legend class="blind">
                                                        게시판 검색
                                                    </legend>
                                                    <div class="search-area">
                                                        <select id="" name="" title="검색 조건 선택창">
                                                            <option value="1">제목</option>
                                                            <option value="2">내용</option>
                                                        </select>
                                                        <div class="search-box">
                                                            <input type="search" id="" name="searchWrd" title="내용을 입력해주세요." placeholder="내용을 입력해주세요." value="">
                                                            <button type="submit" class="btn-search">
                                                                검색
                                                            </button>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div>

                                        <div class="board-list">
                                            <table>
                                                <caption>
                                                    공지사항 목록 정보표 : 번호, 분야, 제목, 작성자, 등록일, 조회수에 관한 정보 제공표
                                                </caption>
                                                <thead class="no-thead">
                                                    <tr>
                                                        <th scope="col" class="number">
                                                            번호
                                                        </th>
                                                        <th scope="col" class="category">
                                                            카테고리
                                                        </th>
                                                        <th scope="col" class="title">
                                                            제목
                                                        </th>
                                                        <th scope="col" class="hit">
                                                            조회수
                                                        </th>
                                                        <th scope="col" class="date">
                                                            작성일
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr class="notice">
                                                        <td class="number">
                                                            <img src="../img/board/icon_notice01.png" alt="공지" class="icon-notice" />
                                                        </td>
                                                        <td class="category">
                                                            <span class="point-color01">기업수요발굴</span>
                                                        </td>
                                                        <td class="title left">
                                                            <a href="#">
                                                                <span class="title-wrapper">
                                                                    <span class="mobile-category">
                                                                        기업수요발굴
                                                                    </span>
                                                                <span class="mobile-icon-notice">
                                                                        공지
                                                                    </span> 2023년도 일학습병행(재학생 및 P-TECH) 공동훈련센터 성과평가 결과발표(등급) 2023년도 일학습병행(재학생 및 P-TECH) 공동훈련센터 성과평가 결과발표(등급)
                                                                </span>

                                                                <span class="mobile-info">
                                                                    <span>104</span>
                                                                <span class="icon-bar"></span>
                                                                <span>2023.09.24</span>
                                                                </span>
                                                            </a>
                                                            <div class="board-right-icon-wrapper">
                                                                <img src="../img/board/icon_new01.png" class="icon-new" alt="NEW (새글)" />
                                                            </div>
                                                        </td>

                                                        <td class="hit">
                                                            104
                                                        </td>
                                                        <td class="date">
                                                            2019-10-29
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="number">
                                                            249
                                                        </td>
                                                        <td class="category">
                                                            <span class="point-color01">HRD기초진단컨설팅</span>
                                                        </td>
                                                        <td class="title left">
                                                            <a href="#">
                                                                <span class="mobile-category">
                                                                    HRD기초진단컨설팅
                                                                </span>
                                                                <span class="title-wrapper">
                                                                    2023년도 사업주 자격검정사업 우수사례 경진대회 개최 안내
                                                                </span>

                                                                <span class="mobile-info">
                                                                    <span>752</span>
                                                                <span class="icon-bar"></span>
                                                                <span>2023.06.23</span>
                                                                </span>
                                                            </a>
                                                            <div class="board-right-icon-wrapper">
                                                                <img src="../img/board/icon_new01.png" class="icon-new" alt="NEW (새글)" />
                                                            </div>
                                                        </td>

                                                        <td class="hit">
                                                            752
                                                        </td>
                                                        <td class="date">
                                                            2023.06.23
                                                        </td>
                                                    </tr>


                                                    <tr>
                                                        <td class="number">
                                                            248
                                                        </td>
                                                        <td class="category">
                                                            <span class="point-color01">성과컨설팅</span>
                                                        </td>
                                                        <td class="title left">
                                                            <a href="#">
                                                                <span class="mobile-category">
                                                                    성과컨설팅
                                                                </span>
                                                                <span class="title-wrapper">
                                                                    2023 고용허가제 우수사례 공모전 입상작 발표
                                                                </span>

                                                                <span class="mobile-info">
                                                                    <span>1,023</span>
                                                                <span class="icon-bar"></span>
                                                                <span>2023.05.11</span>
                                                                </span>
                                                            </a>
                                                        </td>

                                                        <td class="hit">
                                                            1,023
                                                        </td>
                                                        <td class="date">
                                                            2023.05.11
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
        menuOn(4, 1, 0, 0);
    </script>
</body>

</html></html>
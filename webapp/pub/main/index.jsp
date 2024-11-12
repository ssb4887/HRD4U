<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="../include/meta.html"/>
        <link rel="stylesheet" href="../css/main.css">

        <script src="../js/main.js"></script>
		<meta charset="utf-8">
        <title>
            성균관대학교, 삼성창원병원
        </title>
</head>

<body class="main">
    <!-- skip navigation -->
    <p class="skip-navigation">
        <a href="#contents">콘텐츠 바로가기</a>
    </p>
    <!-- //skip navigation -->


    <!-- popup 1 -->
    <jsp:include page="../include/popup.html"/>
        <!-- //popup 1 -->

        <!-- wrapper -->
        <div class="wrapper" id="wrapper">
            <!-- header -->
            <header>
                <jsp:include page="../include/header.html"/>
            </header>
            <!-- //header -->

            <!-- container -->
            <section>
                <div class="main-container">
                    <!-- main contents -->

                    <!-- main contents01 -->
                    <div class="main-contents01" id="contents">
                        <div class="first-visitor-menu-wrapper">
                            <dl>
                                <dt>
                                <strong>
                                    <span>
                                        처
                                    </span>
                                    <span>
                                        음
                                    </span>
                                    <span>
                                        진
                                    </span>
                                    <span>
                                        료
                                    </span>
                                </strong>
                                <span class="space"></span>
                                <span>
                                    이
                                </span>
                                <span>
                                    신
                                </span>
                                <span>
                                    가
                                </span>
                                <span>
                                    요 
                                </span>
                                <span>
                                    ?
                                </span>
                            </dt>
                                <dd>
                                    <span>
                                    쉽
                                </span>
                                    <span>
                                    고
                                </span>
                                    <span class="space"></span>
                                    <span>
                                    빠
                                </span>
                                    <span>
                                    른
                                </span>
                                    <span>
                                    진
                                </span>
                                    <span>
                                    료
                                </span>
                                    <span>
                                    를
                                </span>
                                    <span class="space"></span>
                                    <span>
                                    도
                                </span>
                                    <span>
                                    와
                                </span>
                                    <span>
                                    드
                                </span>
                                    <span>
                                    립
                                </span>
                                    <span>
                                    니
                                </span>
                                    <span>
                                    다
                                </span>
                                    <span>
                                    .
                                </span>
                                </dd>
                            </dl>

                            <div class="bg01"></div>
                            <div class="bg02"></div>

                        </div>

                        <!-- main visual -->
                        <div class="main-visual-wrapper">
                            <div class="main-slogan-wrapper">
                                <p>
                                    Lifelong care service 평생 행복을 지켜드리겠습니다. 우리의 첫 상급종합병원, 여러분의 믿음을 건강으로 보답하겠습니다.
                                </p>
                            </div>

                            <div class="main-visual-area">
                                <div class="owl-carousel" id="main-visual-slider01">
                                    <div class="item">
                                        <img src="../img/main/img_slider01.jpg" class="pc" alt="">
                                        <img src="../img/main/img_slider01_mobile.jpg" class="mobile" alt="">
                                    </div>
                                    <div class="item">
                                        <img src="../img/main/img_slider02.jpg" class="pc" alt="">
                                        <img src="../img/main/img_slider02_mobile.jpg" class="mobile" alt="">
                                    </div>
                                    <div class="item">
                                        <img src="../img/main/img_slider03.jpg" class="pc" alt="">
                                        <img src="../img/main/img_slider03_mobile.jpg" class="mobile" alt="">
                                    </div>
                                </div>
                            </div>

                            <div class="main-contents01-wrapper">

                                <ul class="vistor-menu">
                                    <li>
                                        <a href="#">
                                            <strong>
                                            첫 방문자 
                                            <img src="../img/main/icon_symbol01.png" alt=""> 
                                        </strong>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <strong>
                                            예약 조회
                                            <img src="../img/main/icon_symbol02.png" alt=""> 
                                        </strong>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <strong>
                                            건강검진
                                            <img src="../img/main/icon_symbol03.png" alt=""> 
                                        </strong>
                                        </a>
                                    </li>
                                </ul>

                                <div class="owl-dots" id="custom-thumbnail">
                                    <button role="button" class="owl-dot">
                                    <img src="../img/main/img_thumbnail01.jpg" alt="">
                                </button>
                                    <button role="button" class="owl-dot">
                                    <img src="../img/main/img_thumbnail02.jpg" alt="">
                                </button>
                                    <button role="button" class="owl-dot">
                                    <img src="../img/main/img_thumbnail03.jpg" alt="">
                                </button>
                                </div>

                                <div class="main-contents01-area">
                                    <div class="main-contents01-box">
                                        <div class="main-reservation-wrapper">
                                            <h2>
                                                첫 방문 고객 예약상담
                                            </h2>

                                            <div class="main-reservation-area">
                                                <p>
                                                    입력하신 번호로 전문상담 간호사가 연락드립니다.<span></span> 운영시간 (<strong>평일</strong> 8:30 ~17:30, <strong>토요일</strong> 8:30~12:30)
                                                </p>

                                                <div class="main-reservation-box02">
                                                    <button type="button" id="open-reservation01">
                                                        <img src="../img/main/icon_phone01.png" alt="" />
                                                        <strong>
                                                            예약상담 신청하기
                                                        </strong>
                                                    </button>

                                                    <div class="mask"></div>
                                                    <div class="modal-wrapper" id="modal-first-reservation">
                                                        <h2>
                                                            첫 방문 고객 예약상담
                                                        </h2>

                                                        <div class="modal-area">
                                                            <div class="modal-box01">

                                                                <form action="" method="">
                                                                    <legend class="blind">
                                                                        첫 방문 고객 예약상담
                                                                    </legend>
                                                                    <div class="find-id-password-box">
                                                                        <div class="form-wrapper mt0">
                                                                            <dl>
                                                                                <dt>
                                                                                    <label for="modal-textfield01">
                                                                                        전화번호
                                                                                    </label>
                                                                                </dt>
                                                                                <dd>
                                                                                    <div class="input-phone-wrapper">
                                                                                        <select id="modal-textfield01" name="" title="전화번호 첫번째 3자리를 선택하세요.">
                                                                                            <option value="">
                                                                                                010
                                                                                            </option>
                                                                                        </select>
                                                                                        <span class="word-unit">
                                                                                            -
                                                                                        </span>
                                                                                        <input type="tel" name="" value="" maxlength="4" title="전화번호 두번째 4자리를 입력하세요.">
                                                                                        <span class="word-unit">
                                                                                            -
                                                                                        </span>
                                                                                        <input type="tel" name="" value="" maxlength="4" title="전화번호 세번째 4자리를 입력하세요.">
                                                                                    </div>
                                                                                </dd>
                                                                            </dl>
                                                                            <dl>
                                                                                <dt>
                                                                                    <label for="modal-textfield02">
                                                                                        남기실 말씀
                                                                                    </label>
                                                                                </dt>
                                                                                <dd>
                                                                                    <textarea id="modal-textfield02" name="" cols="50" rows="5"></textarea>
                                                                                </dd>
                                                                            </dl>
                                                                        </div>
                                                                    </div>

                                                                    <div class="btns-area">
                                                                        <button type="submit" class="btn-m01 btn-color01">
                                                                            신청하기
                                                                        </button>

                                                                        <button type="submit" class="btn-m01 btn-color02 btn-close">
                                                                            취소
                                                                        </button>
                                                                    </div>
                                                                </form>
                                                            </div>

                                                            <button type="button" class="btn-modal-close btn-close">
                                                                닫기
                                                            </button>
                                                        </div>
                                                    </div>

                                                    <script>
                                                        $(function() {
                                                            $("#open-reservation01").on("click", function() {
                                                                $("body").addClass('fixed');
                                                                $(".mask").fadeIn(150, function() {
                                                                    $("#modal-first-reservation").show();
                                                                });
                                                            });


                                                            $(".mask, .btn-close").on("click", function() {
                                                                $("body").removeClass('fixed');
                                                                $(".modal-wrapper").hide();
                                                                $(".mask").fadeOut();
                                                            });
                                                        });
                                                    </script>
                                                </div>

                                                <!--
                                                <form action="" method="">
                                                    <fieldset>
                                                        <legend class="blind">
                                                            첫 방문 고객 예약상담
                                                        </legend>
                                                        <div class="main-reservation-box">
                                                            <label for="name" class="blind">
                                                                이름
                                                            </label>
                                                            <input type="text" id="name" name="" value="" placeholder="이름">
                                                            <div class="main-reservation-group">
                                                                <input type="tel" name="" value="" placeholder="전화번호" />
                                                                <button type="submit">
                                                                    <span>
                                                                        신청
                                                                    </span>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                </form>
                                                -->
                                            </div>

                                            <a href="#" class="btn-more">
                                            첫 방문 고객 예약상담 더보기
                                        </a>
                                        </div>
                                        <div class="main-search-wrapper01">
                                            <h2>
                                                진료과/의료진 검색
                                            </h2>

                                            <div class="main-search-area01">
                                                <p>
                                                    진료 전, 전문의료진을 검색해보세요.<br /> 원하시는 정보를 빠르게 예약할 수 있습니다.
                                                </p>

                                                <form action="" method="">
                                                    <fieldset>
                                                        <legend class="blind">
                                                            첫 방문 고객 예약상담
                                                        </legend>

                                                        <div class="main-search-box01">
                                                            <label for="textfield02" class="blind">
                                                            진료과/의료진 검색
                                                        </label>
                                                            <input type="text" id="textfield02" name="" value="" placeholder="진료과명/의료진명을 입력하세요.">
                                                            <button type="submit">
                                                            검색
                                                        </button>
                                                        </div>
                                                    </fieldset>
                                                </form>
                                            </div>

                                            <a href="#" class="btn-more">
                                            진료과/의료진 검색 더보기
                                        </a>
                                        </div>
                                    </div>


                                    <div class="main-menu-wrapper01">
                                        <ul>
                                            <li>
                                                <a href="#">
                                                    <strong>
                                                    온라인<br />
                                                    진료예약
                                                </strong>
                                                    <img src="../img/main/icon_menu0101.png" alt="" />
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#">
                                                    <strong>
                                                    진료예약<br />
                                                    조회
                                                </strong>
                                                    <img src="../img/main/icon_menu0102.png" alt="" />
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#">
                                                    <strong>
                                                    종합건진<br />
                                                    예약상담
                                                </strong>
                                                    <img src="../img/main/icon_menu0103.png" alt="" />
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#">
                                                    <strong>
                                                    국가 암<br />
                                                    검진예약
                                                </strong>
                                                    <img src="../img/main/icon_menu0104.png" alt="" />
                                                </a>
                                            </li>
                                        </ul>

                                    </div>
                                </div>

                                <div class="main-menu-wrapper02">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0201.png" alt="" />
                                            </span>
                                                <strong>
                                                진료과/의료진
                                            </strong>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0202.png" alt="" />
                                            </span>
                                                <strong>
                                                진료시간표
                                            </strong>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0203.png" alt="" />
                                            </span>
                                                <strong>
                                                층별안내
                                            </strong>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0204.png" alt="" />
                                            </span>
                                                <strong>
                                                비급여안내
                                            </strong>
                                            </a>
                                        </li>
                                        <!--
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0205.png" alt="" />
                                            </span>
                                                <strong>
                                                오시는길
                                            </strong>
                                            </a>
                                        </li>
                                        -->
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0206.png" alt="" />
                                            </span>
                                                <strong>
                                                주차안내
                                            </strong>
                                            </a>
                                        </li>
                                        <!--
                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0207.png" alt="" />
                                            </span>
                                                <strong>
                                                장례식장 안내
                                            </strong>
                                            </a>
                                        </li>
                                        -->

                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0208.png" alt="" />
                                            </span>
                                                <strong>
                                                    제증명 발급
                                            </strong>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <span class="icon">
                                                <img src="../img/main/icon_menu0209.png" alt="" />
                                            </span>
                                                <strong>
                                                    의무기록사본 발급
                                            </strong>
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <!-- main visual -->
                    </div>
                    <!-- //main contents01 -->


                    <!-- main contents02 
                    <div class="main-contents02">
                        <div class="main-contents-wrapper">
                            <div class="main-title-area">
                                <h2>
                                    어디가 아프세요?
                                </h2>

                                <p>
                                    아프신 부위를 선택하시면 해당 진료과를 안내해 드립니다.
                                </p>
                            </div>


                            <div class="main-part-wrapper">
                                <div class="main-part-area">
                                    <img src="../img/main/img_human01.png" alt="" class="body" />
                                    <button type="button" id="point1">
                                    <img src="../img/main/icon_point01.png" alt="" />
                                </button>
                                    <button type="button" id="point2">
                                    <img src="../img/main/icon_point02.png" alt="" />
                                </button>
                                    <button type="button" id="point3">
                                    <img src="../img/main/icon_point03.png" alt="" />
                                </button>
                                    <button type="button" id="point4">
                                    <img src="../img/main/icon_point04.png" alt="" />
                                </button>
                                    <button type="button" id="point5">
                                    <img src="../img/main/icon_point05.png" alt="" />
                                </button>
                                    <button type="button" id="point6">
                                    <img src="../img/main/icon_point06.png" alt="" />
                                </button>
                                    <button type="button" id="point7">
                                    <img src="../img/main/icon_point07.png" alt="" />
                                </button>

                                    <button type="button" class="btn-etc">
                                    <span>
                                        그 외
                                    </span>
                                </button>
                                </div>

                                <div class="main-part-information-wrapper" id="main-part-information">
                                    <dl id="part1">
                                        <dt>
                                        머리
                                    </dt>
                                        <dd>
                                            건망증
                                        </dd>
                                        <dd>
                                            뇌출혈
                                        </dd>
                                        <dd>
                                            귀에서 냄새가 남
                                        </dd>
                                        <dd>
                                            귀 모양의 변형
                                        </dd>
                                        <dd>
                                            낮 시간대의 졸음
                                        </dd>
                                        <dd>
                                            짖수르는 느낌
                                        </dd>
                                    </dl>

                                    <dl id="part2">
                                        <dt>
                                        목
                                    </dt>
                                        <dd>
                                            갑상선 비대
                                        </dd>
                                        <dd>
                                            고음에서의 분열
                                        </dd>
                                        <dd>
                                            목 주변 부종
                                        </dd>
                                        <dd>
                                            목소리 변화
                                        </dd>
                                        <dd>
                                            성대마비
                                        </dd>
                                        <dd>
                                            인후염
                                        </dd>
                                    </dl>

                                    <dl id="part3">
                                        <dt>
                                        가슴/등
                                    </dt>
                                        <dd>
                                            가슴 두근거림
                                        </dd>
                                        <dd>
                                            검은색 가래
                                        </dd>
                                        <dd>
                                            락스를 마셨어요
                                        </dd>
                                        <dd>
                                            척추 측만
                                        </dd>
                                        <dd>
                                            굽은 등
                                        </dd>
                                        <dd>
                                            자세이상
                                        </dd>
                                    </dl>

                                    <dl id="part4">
                                        <dt>
                                        복부
                                    </dt>
                                        <dd>
                                            간기능 저하
                                        </dd>
                                        <dd>
                                            명치 부위 통증
                                        </dd>
                                        <dd>
                                            복부 불편감
                                        </dd>
                                        <dd>
                                            복부 압박 증상
                                        </dd>
                                        <dd>
                                            옆구리 통증
                                        </dd>
                                        <dd>
                                            가슴 쓰림
                                        </dd>
                                    </dl>

                                    <dl id="part5">
                                        <dt>
                                        엉덩이
                                    </dt>
                                        <dd>
                                            괄약근 기능 이상
                                        </dd>
                                        <dd>
                                            엉덩이 통증
                                        </dd>
                                        <dd>
                                            항문출혈
                                        </dd>
                                        <dd>
                                            설사
                                        </dd>
                                        <dd>
                                            생리불순
                                        </dd>
                                        <dd>
                                            변비
                                        </dd>
                                    </dl>

                                    <dl id="part6">
                                        <dt>
                                        다리
                                    </dt>
                                        <dd>
                                            관절 운동성 감소
                                        </dd>
                                        <dd>
                                            X자 다리
                                        </dd>
                                        <dd>
                                            O자 다리
                                        </dd>
                                        <dd>
                                            관절 불안정증
                                        </dd>
                                        <dd>
                                            가늘어지는 팔다리
                                        </dd>
                                        <dd>
                                            무감각
                                        </dd>
                                    </dl>

                                    <dl id="part7">
                                        <dt>
                                        팔
                                    </dt>
                                        <dd>
                                            가늘어지는 팔다리
                                        </dd>
                                        <dd>
                                            관절의 경직
                                        </dd>
                                        <dd>
                                            어깨 잡음
                                        </dd>
                                        <dd>
                                            저림
                                        </dd>
                                        <dd>
                                            어깨근육 약화
                                        </dd>
                                        <dd>
                                            방사통
                                        </dd>
                                    </dl>
                                    <!--
                                <button type="button" class="btn-close">
                                    창닫기
                                </button>
                                ->
                                </div>

                                <div class="main-disease-wrapper">
                                    <div class="title-area">
                                        <h3>
                                            질환별 안내
                                        </h3>
                                        <p>
                                            <strong id="disease-part">'다리'</strong>의 관련된 질환 안내입니다.
                                        </p>
                                    </div>

                                    <!-- 하위메뉴가 있을 시 백버튼 표시 ->
                                    <button type="button" class="btn-back">
                                    <span>
                                        back
                                    </span>
                                </button>
                                    <!-- //하위메뉴가 있을 시 백버튼 표시 ->

                                    <div class="main-disease-area">
                                        <div class="scrollbar-inner">
                                            <ul class="main-disease-box">
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        O자 다리
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        가늘어지는 팔다리
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절염
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절의 경직
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        말초부종
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절잡음
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절 운동성 감소
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        다리가 잘 안벌어짐
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        달걀 위에 앉아있는 느낌
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        가늘어지는 팔다리
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        하지의 근력약화
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절의 경직
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절 운동성 감소
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        관절잡음
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        달걀위에 앉아있는 느낌
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        발바닥아픔
                                                    </span>
                                                </button>
                                                </li>


                                            </ul>
                                        </div>
                                    </div>

                                </div>

                                <div class="main-doctor-information-wrapper">
                                    <div class="main-doctor-information">
                                        <h3>
                                            추천 의료진
                                        </h3>

                                        <div class="image">
                                            <img src="../img/doctor/doctor_small01.jpg" alt="">
                                        </div>

                                        <dl>
                                            <dt>
                                            어환 교수 (신경외과)
                                        </dt>
                                            <dd>
                                                요통, 허리디스크, 목디스크, 척추·척수종양, 척추관협착증, 척추전방전위증(척추처짐증), 척수혈관기형
                                            </dd>
                                        </dl>

                                        <ul>
                                            <li>
                                                <a href="#">
                                                    <span class="icon">
                                                    <img src="../img/main/icon_doctor_symbol01.png" alt="" />
                                                </span>
                                                    <strong>
                                                    프로필안내
                                                </strong>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#">
                                                    <span class="icon">
                                                    <img src="../img/main/icon_doctor_symbol02.png" alt="" />
                                                </span>
                                                    <strong>
                                                    진료예약
                                                </strong>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- main contents02 -->


                    <!-- main contents02 -->
                    <div class="main-contents02">
                        <div class="main-contents-wrapper">
                            <div class="main-title-area">
                                <h2>
                                    진료과/의료진
                                </h2>

                                <p>
                                    삼성창원병원 진료과를 안내해 드립니다.
                                </p>
                            </div>

                            <div class="main-department-wrapper">
                                <div class="main-department-area">
                                    <div class="owl-carousel" id="main-department-slider01">
                                        <div class="item">
                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IG'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0101.png" class="">
                                                            </span>
                                                        <strong>
                                                                소화기내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IC'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0102.png" class="">
                                                            </span>
                                                        <strong>
                                                                순환기내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IP'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0103.png" class="">
                                                            </span>
                                                        <strong>
                                                                호흡기·알레르기내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IE'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0104.png" class="">
                                                            </span>
                                                        <strong>
                                                                내분비내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IN'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0105.png" class="">
                                                            </span>
                                                        <strong>
                                                                신장내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IH'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0106.png" class="">
                                                            </span>
                                                        <strong>
                                                                혈액종양내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="item">
                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('IR'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0107.png" class="">
                                                            </span>
                                                        <strong>
                                                                류마티스내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('ID'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0108.png" class="">
                                                            </span>
                                                        <strong>
                                                                감염내과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('GS'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0109.png" class="">
                                                            </span>
                                                        <strong>
                                                                외과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('TS'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0110.png" class="">
                                                            </span>
                                                        <strong>
                                                                흉부외과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('NS'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0111.png" class="">
                                                            </span>
                                                        <strong>
                                                                신경외과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('OS'); return false;">
                                                        <span class="group">
                                                            <span class="icon">
                                                                <img src="../img/sub02/icon0112.png" class="">
                                                            </span>
                                                        <strong>
                                                                정형외과
                                                            </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="item">
                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('PS'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0113.png" class="">
                                                                </span>
                                                        <strong>
                                                                    성형외과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('OG'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0114.png" class="">
                                                                </span>
                                                        <strong>
                                                                    산부인과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('PD'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0115.png" class="">
                                                                </span>
                                                        <strong>
                                                                    소아청소년과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('NR'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0116.png" class="">
                                                                </span>
                                                        <strong>
                                                                    신경과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>

                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('NP'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0117.png" class="">
                                                                </span>
                                                        <strong>
                                                                    정신건강의학과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('DM'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0118.png" class="">
                                                                </span>
                                                        <strong>
                                                                    피부과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="item">
                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('UR'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0119.png" class="">
                                                                </span>
                                                        <strong>
                                                                    비뇨의학과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('OT'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0120.png" class="">
                                                                </span>
                                                        <strong>
                                                                    안과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('OL'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0121.png" class="">
                                                                </span>
                                                        <strong>
                                                                    이비인후과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>


                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('FM'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0122.png" class="">
                                                                </span>
                                                        <strong>
                                                                    가정의학과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('OM'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0123.png" class="">
                                                                </span>
                                                        <strong>
                                                                    직업환경의학과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('RM'); return false;">
                                                        <span class="group">
                                                                <span class="icon">
                                                                    <img src="../img/sub02/icon0124.png" class="">
                                                                </span>
                                                        <strong>
                                                                    재활의학과
                                                                </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="item">
                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('DS'); return false;">
                                                        <span class="group">
                                                                    <span class="icon">
                                                                        <img src="../img/sub02/icon0125.png" class="">
                                                                    </span>
                                                        <strong>
                                                                        치과
                                                                    </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('EM'); return false;">
                                                        <span class="group">
                                                                    <span class="icon">
                                                                        <img src="../img/sub02/icon0126.png" class="">
                                                                    </span>
                                                        <strong>
                                                                        응급의학과
                                                                    </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('AP'); return false;">
                                                        <span class="group">
                                                                    <span class="icon">
                                                                        <img src="../img/sub02/icon0127.png" class="">
                                                                    </span>
                                                        <strong>
                                                                        병리과
                                                                    </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="main-index-department-wrapper">

                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('CP'); return false;">
                                                        <span class="group">
                                                                    <span class="icon">
                                                                        <img src="../img/sub02/icon0128.png" class="">
                                                                    </span>
                                                        <strong>
                                                                        진단검사의학과
                                                                    </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('NM'); return false;">
                                                        <span class="group">
                                                                    <span class="icon">
                                                                        <img src="../img/sub02/icon0129.png" class="">
                                                                    </span>
                                                        <strong>
                                                                        핵의학과
                                                                    </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('RD'); return false;">
                                                        <span class="group">
                                                                    <span class="icon">
                                                                        <img src="../img/sub02/icon0130.png" class="">
                                                                    </span>
                                                        <strong>
                                                                        영상의학과
                                                                    </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="item">
                                            <div class="main-index-department-wrapper">
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('AN'); return false;">
                                                        <span class="group">
                                                                        <span class="icon">
                                                                            <img src="../img/sub02/icon0131.png" class="">
                                                                        </span>
                                                        <strong>
                                                                            마취통증의학과
                                                                        </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                                <div class="index-department-area">
                                                    <a href="javascript:void(0);" onclick="fn_goLink('RO'); return false;">
                                                        <span class="group">
                                                                        <span class="icon">
                                                                            <img src="../img/sub02/icon0132.png" class="">
                                                                        </span>
                                                        <strong>
                                                                            방사선종양학과
                                                                        </strong>
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <button type="button" class="btn-previous">
                                    이전 진료과/의료진으로 이동
                                </button>

                                <button type="button" class="btn-next">
                                    다음 진료과/의료진으로 이동
                                </button>

                                <div class="owl-dots" id="custom-department-thumbnail">
                                    <button class="owl-dot">1</button>
                                    <button class="owl-dot">2</button>
                                    <button class="owl-dot">3</button>
                                </div>


                            </div>

                        </div>
                    </div>





                    <!-- main contents02 -->


                    <!-- main contents03 -->
                    <div class="main-contents03">
                        <div class="main-contents-wrapper">

                            <div class="main-title-area">
                                <h2>
                                    병원소식
                                    <img src="../img/main/icon_title_symbol01.png" alt="" />
                                </h2>

                                <div class="sns-list">
                                    <a href="#">
                                        <img src="../img/main/icon_blog01.png" alt="blog" />
                                    </a>
                                    <a href="#">
                                        <img src="../img/main/icon_facebook01.png" alt="FACEBOOK" />
                                    </a>
                                    <a href="#">
                                        <img src="../img/main/icon_twiter01.png" alt="TWITER" />
                                    </a>
                                </div>
                            </div>

                            <div class="main-contents03-area">
                                <div class="main-slider-wrapper01">
                                    <div class="owl-carousel" id="main-doctor-slider01">
                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor01.jpg" alt="위암센터 - 김용석 센터장 : 최상의 진료를 구현하기 위해 고군분투">
                                                <p>
                                                    개복 수술부터 첨단 로봇수술까지<br /> 최상의 치료를 구현
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor02.jpg" alt="유방 갑상선암센터 - 외과 이준호 센터장 : 저를 믿고 잘 나아주셔서 감사합니다">
                                                <p>
                                                    완전한 회복을 위해 평생 함께<br /> 하는 동반자
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor03.jpg" alt="비뇨의학과 - 오태희 센터장 : 끊임없는  도전과 혁신">
                                                <p>
                                                    수술의 패러다임을 바꾼<br /> 로봇수술센터
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor04.jpg" alt="신경외과 - 어환 교수 : 비수술적 치료를 우선으로 고통으로부터 해방">
                                                <p>
                                                    오로지 환자만을 생각하다<br />대한민국 최고의 척추 질환 권위자
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor05.jpg" alt="영상의학과 - 변홍식 교수 : 뇌혈관 분야  신경중재의학의 개척자">
                                                <p>
                                                    정밀영상진단과 신경중재술의<br /> 권위자
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor06.jpg" alt="외과 - 김성 교수 : 위암 수술을 세계 최고수준까지 끌어올린">
                                                <p>
                                                    진단부터 수술 후 평생관리까지<br /> 통합위암치료의 완성
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor07.jpg" alt="소화기내과 - 권병수 교수 : 췌담도 질환치료의 차세대 주역">
                                                <p>
                                                    정복의 길은 여전히 멀지만 <br /> 환자의 생존을 최우선으로 하다.
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor08.jpg" alt="신경외과 - 김승환 교수 : 환자에게 가장 좋은 결과를 굿닥터">
                                                <p>
                                                    뇌동맥류 코일색전술과 노경색의<br /> 혈관 재개통술 스페셜리스트
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor09.jpg" alt="신경과 - 이진구 교수 : 뇌졸증 환자들의 골든타임을 사수하다">
                                                <p>
                                                    뇌졸중 하이브리드 치료의<br /> 선두주자
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor10.jpg" alt="정형외과 - 이도경 교수 : 끝없는 연구와 도전으로 치료법 제시">
                                                <p>
                                                    끊임없는 연구와 도전을 통해<br /> 새로운 치료 가이드라인을 제시
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor11.jpg" alt="산부인과 - 경희강 교수 : 여성 환자들에게 도움을 줄 수 있는 의사가 되고  싶었어요.">
                                                <p>
                                                    두 생명의 무게를 짊어지고<br /> 고위험 분만의 한계를 극복해나가다.
                                                </p>
                                            </a>
                                        </div>

                                        <div class="item">
                                            <a href="#">
                                                <img src="../img/main/img_doctor12.jpg" alt="피부과 - 고재완 교수 : 완치와 희망을 위해 고군분투">
                                                <p>
                                                    환자의 시선에서 치료를 넘어 치유로<br /> 완치의 희망을 위해 고군분투
                                                </p>
                                            </a>
                                        </div>

                                    </div>

                                    <div class="navigation">
                                        <button type="button" class="btn-previous">
                                    이전 의사로 이동
                                </button>

                                        <button type="button" class="btn-next">
                                    다음 의사로 이동
                                </button>
                                    </div>
                                </div>

                                <!-- main news -->
                                <div class="main-slider-wrapper02">
                                    <div class="only-pc">
                                        <div class="owl-carousel main-news-slider01">
                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news01.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                중증 진료 중심병원으로
                                                거듭나 동남권 의료 선도할 것
                                            </strong>
                                                    <span class="substance">
                                                삼성창원병원은 1981년 200병상 규모의 마산고려병원으로 시작해 40년 만에 762병상 규모의 창원 지역 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news02.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                코로나19 극복 캠페인 동참
                                                삼성창원병원
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원은 지난 9일 코로나19 극복을 위한 희망 캠페인 릴레이에 동참했다. 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news03.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                4년 연속 만성폐쇄성폐질환 적성성 평가 1등급
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원이 4년 연속 ´만성폐쇄성폐질환 치료 잘하는 우수병원´으로 선정됐다. 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news04.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                로봇수술센터 로봇수술 700예 달성
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원이 로봇 수술 도입 3년 4개월 만에 700예를 달성했다. 2017년 12월 첫 로봇 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news05.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                신경외과 김영준 교수, 대한신경외과학회 
                                            </strong>
                                                    <span class="substance">
                                                김영준 교수는 후천적으로 유전자를 변형시킬 수 있는 후성유전학을 통해 폐암의 뇌 전이를 막을 수 있다는...
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news06.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                안면인식 IT기술 접목 환자확인 시스템 도입
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원은 9월 2일부로 안면인식 기술로 환자를 확인하는 시스템을 개발하고 시범 
                                            </span>
                                                </a>
                                            </div>





                                        </div>
                                    </div>

                                    <div class="only-mobile">
                                        <div class="owl-carousel main-news-slider01" id="main-news-slider01">
                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news01.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                중증 진료 중심병원으로
                                                거듭나 동남권 의료 선도할 것
                                            </strong>
                                                    <span class="substance">
                                                삼성창원병원은 1981년 200병상 규모의 마산고려병원으로 시작해 40년 만에 762병상 규모의 창원 지역 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news02.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                코로나19 극복 캠페인 동참
                                                삼성창원병원
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원은 지난 9일 코로나19 극복을 위한 희망 캠페인 릴레이에 동참했다. 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news03.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                4년 연속 만성폐쇄성폐질환 적성성 평가 1등급
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원이 4년 연속 ´만성폐쇄성폐질환 치료 잘하는 우수병원´으로 선정됐다. 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news04.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                로봇수술센터 로봇수술 700예 달성
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원이 로봇 수술 도입 3년 4개월 만에 700예를 달성했다. 2017년 12월 첫 로봇 
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news05.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                신경외과 김영준 교수, 대한신경외과학회 
                                            </strong>
                                                    <span class="substance">
                                                김영준 교수는 후천적으로 유전자를 변형시킬 수 있는 후성유전학을 통해 폐암의 뇌 전이를 막을 수 있다는...
                                            </span>
                                                </a>
                                            </div>

                                            <div class="item">
                                                <a href="#">
                                                    <span class="image">
                                                <img src="../img/main/img_news06.jpg" alt="" />
                                            </span>
                                                    <strong class="title">
                                                안면인식 IT기술 접목 환자확인 시스템 도입
                                            </strong>
                                                    <span class="substance">
                                                성균관대학교 삼성창원병원은 9월 2일부로 안면인식 기술로 환자를 확인하는 시스템을 개발하고 시범 
                                            </span>
                                                </a>
                                            </div>
                                        </div>

                                        <div class="owl-dots owl-carousel" id="custom-news-thumbnail">
                                            <button class="owl-dot">1</button>
                                            <button class="owl-dot">2</button>
                                            <button class="owl-dot">3</button>
                                            <button class="owl-dot">4</button>
                                            <button class="owl-dot">5</button>
                                            <button class="owl-dot">6</button>
                                        </div>
                                    </div>

                                </div>
                                <!-- //main news -->
                            </div>
                        </div>
                    </div>
                    <!-- main contents03 -->


                    <!-- main contents04 -->
                    <div class="main-contents04">
                        <form action="">
                            <fieldset>
                                <legend class="blind">
                                    서비스 찾기
                                </legend>
                                <div class="main-search-service-wrapper">
                                    <img src="../img/main/bg_doctor_together.png" alt="" />
                                    <div class="main-search-service-area">
                                        <h2>
                                            어떤서비스를<span class="span-br"></span> 찾으세요?
                                        </h2>

                                        <div class="main-search-service-box pl0">
                                            <!--
                                        <div class="custom-select-wrapper">
                                            <button type="button" class="title">
                                            <span>
                                                선택
                                            </span>
                                        </button>

                                            <ul style="display: block">
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        서비스1
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        서비스2
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        서비스3
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        서비스4
                                                    </span>
                                                </button>
                                                </li>
                                                <li>
                                                    <button type="button">
                                                    <span>
                                                        서비스5
                                                    </span>
                                                </button>
                                                </li>
                                            </ul>
                                        </div>
                                    -->

                                            <input type="search" id="" name="" value="" placeholder="검색하실 단어를 입력하세요." />
                                            <button type="submit">
                                                검색
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>

                    </div>
                    <!-- main contents -->


                    <!-- main contents05 -->
                    <div class="main-contents05">
                        <div class="main-contents-wrapper">

                            <div class="main-title-area">
                                <h2>
                                    편의가이드
                                </h2>
                            </div>

                            <div class="main-guide-wrapper">
                                <div class="owl-carousel" id="main-guide-slider01">
                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide01.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            진료절차안내
                                        </strong>
                                        <span>
                                                삼성창원병원의 절차를<br /> 안내해드립니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide02.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            병원둘러보기
                                        </strong>
                                        <span>
                                            병원시설 곳곳을 둘러보실 수<br /> 있습니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide03.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            건강백과
                                        </strong>
                                        <span>
                                            실생활에 필요한 건강상식을<br /> 제공합니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide04.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            오시는길
                                        </strong>
                                        <span>
                                            삼성창원병원의 오시는 길을<br /> 안내해 드립니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide05.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            간단건강체크
                                        </strong>
                                        <span>
                                            간단하게 건강상태를<br /> 확인하실 수 있습니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide06.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            자주묻는 질문과 답
                                        </strong>
                                        <span>
                                            원하는 정보와 답을<br /> 확인하실 수 있습니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide07.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            면회안내
                                        </strong>
                                        <span>
                                            삼성창원병원의 면회를<br />안내해 드립니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>

                                    <div class="item">
                                        <a href="#">
                                            <span class="image">
                                        <img src="../img/main/img_guide08.png" alt="" />
                                    </span>
                                            <span class="group">
                                        <strong>
                                            장례식장안내
                                        </strong>
                                        <span>
                                            삼성창원병원의 장례식장을<br /> 안내해 드립니다.
                                        </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>

                                <button type="button" class="btn-previous">
                                이전 편의가이드로 이동
                            </button>

                                <button type="button" class="btn-next">
                                다음 편의가이드로 이동
                            </button>

                                <div class="owl-dots owl-carousel" id="custom-guide-thumbnail"></div>
                            </div>
                        </div>
                    </div>
                    <!-- //main contents05 -->
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

        <!-- 스크롤 디자인 변경 -->
        <link rel="stylesheet" href="../css/jquery.scrollbar.css">
        <script src="../js/jquery.scrollbar.min.js"></script>
        <!-- //스크롤 디자인 변경 -->
        <script>
            menuOn(0, 0, 0, 0);

            $(function() {
                $('.scrollbar-inner').scrollbar();
            });
        </script>
</body>

</html>
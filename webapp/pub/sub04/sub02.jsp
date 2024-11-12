<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Content-Script-Type" content="text/javascript" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="subject" content="인적자원 개발 컨퍼런스" />
    <meta name="keywords" content="인적자원 개발 컨퍼런스" />
    <meta name="description" content="인적자원 개발 컨퍼런스" />
    <meta name="Author" content="인적자원 개발 컨퍼런스" />
    <meta name="robots" content="index,follow">

    <meta property="og:title" content="HRD Conference" />
    <meta property="og:description" content="HRD Conference" />
    <meta property="og:image" content="/hrd4u/img/common/logo.png" />


    <link rel="stylesheet" href="/hrdconference/assets/css/notokr.css">
    <link rel="stylesheet" href="/hrdconference/assets/css/roboto.css">
    <link rel="stylesheet" href="/hrdconference/assets/css/reset.css">

    <link rel="stylesheet" href="/hrdconference/assets/css/owl.carousel.min.css">

    <link rel="stylesheet" href="/hrdconference/css/common.css">
    <link rel="stylesheet" href="/hrdconference/css/main.css">
    <link rel="stylesheet" href="/hrdconference/css/contents.css">
    <link rel="stylesheet" href="/hrdconference/css/board.css?ver=1.2">


    <script src="/hrdconference/assets/js/jquery.min.js"></script>
    <script src="/hrdconference/assets/js/owl.carousel.min.js"></script>
    <script src="/hrdconference/assets/js/jquery.easing.1.3.js"></script>
    <script src="/hrdconference/assets/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="/hrdconference/assets/js/common.js"></script>

    <link rel="stylesheet" href="/hrdconference/assets/js/jquery-ui.min.css">
    <script src="/hrdconference/assets/js/jquery-ui.min.js"></script>
    <script src="/hrdconference/assets/js/calendar.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            if (document.location.protocol == 'http:') {
                document.location.href = document.location.href.replace('http:', 'https:');
            }
            /* 메뉴(모바일) 존 */
            var popupzoneSlider = $('#popupzone-slider');
            popupzoneSlider.owlCarousel({
                items: 3,
                loop: false,
                nav: true,
                dots: true,
                margin: 0,
                responsiveClass: true,
                responsive: {
                    0: {
                        items: 1,
                    },
                    769: {
                        items: 3,
                    }
                }
            });
            /* 메뉴(모바일) 존 */
            if (!wcs_add) var wcs_add = {};
            wcs_add["wa"] = "eeae8bc9110068";
            if (window.wcs) {
                wcs_do();
            }

            cookiedata = document.cookie;

            if (cookiedata.indexOf("divpop=done") < 0) {
                var npop = document.getElementById("divpop");
                npop.style.display = "";
            } else {
                $("#divpop").html("");
            }
        });

        //팝업
        function setCookie(name, value, expiredays) {
            var todayDate = new Date();
            todayDate.setDate(todayDate.getDate() + expiredays);
            document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";
        }

        function closeWin(objname, formname) {
            if ($("#" + formname).is(":checked")) {
                setCookie(objname, "done", 1);
            }

            var npop = document.getElementById(objname);

            $("#" + objname).html("");
            npop.style.display = "none";
        }
    </script>



</head>

<body>



    <!-- popup zone -->







    <p class="skip-navigation">
        <a href="#contents">본문 바로가기</a>
    </p>









    <div id="divpop">
        <div class="popupzone-wrapper">
            <div class="popupzone-area">
                <div class="owl-carousel " id="popupzone-slider">

                    <div class="item first">
                        <div class="popup-information-wrapper">




                            <img src="/portal/cmm/fms/getImage.do?atchFileId=FILE_000000000090355&fileSn=1" style="width:67px; height:65px;" alt="전국민 평생직업능력개발을 위한 온라인 교육훈련 플랫폼 Step" />




                            <div class="popup-information">
                                <p class="title">
                                    전국민 평생직업능력개발을 위한 온라인 교육훈련 플랫폼 Step
                                </p>
                                <p class="information">
                                    전국민 평생직업능력개발을 위한 온라인 교육훈련 플랫폼 Step
                                </p>

                                <a href="http://www.step.or.kr/" target="_blank" title="새창열림">
									자세히 보기
								</a>
                            </div>
                        </div>
                    </div>

                    <div class="item first">
                        <div class="popup-information-wrapper">





                            <img src="/portal/cmm/fms/getImage.do?atchFileId=FILE_000000000075676&fileSn=1" style="width:67px; height:65px;" alt="YOUTUBE" />



                            <div class="popup-information">
                                <p class="title">
                                    한국직업방송 Youtube채널 안내
                                </p>
                                <p class="information">
                                    세상에서 가장 쉬운 취업준비 <br/>취업의 시작과 끝! 한국직업방송
                                </p>

                                <a href="https://www.youtube.com/user/worktv2010" target="_blank" title="새창열림">
									자세히 보기
								</a>
                            </div>
                        </div>
                    </div>

                    <div class="item first">
                        <div class="popup-information-wrapper">

                            <img src="/hrdbank/img/popup/img_popup03.png" style="width:67px; height:65px;" alt="" />




                            <div class="popup-information">
                                <p class="title">
                                    사업주 훈련 패키지 안내
                                </p>
                                <p class="information">
                                    직원 교육훈련 지금 시작하세요! 어느 기업이든 스스로 실행할 수 있는 사업주훈련 [훈련 패키지]
                                </p>

                                <a href="https://www.hrd4u.or.kr/hrd4u/contents.do?menuNo=0305" target="_blank" title="새창열림">
									자세히 보기
								</a>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="popupzone-footer-wrapper">
                    <div class="today-checked-close">
                        <!-- 					<input type="checkbox" id="checkbox-top-popupzone" name="" value="" />
					<label for="checkbox-top-popupzone">
						오늘하루 열지않기
					</label> -->
                        <input type="checkbox" name="popupchkbox" id="popupchkbox" value="checkbox" /> <label for="popupchkbox">하루동안 열지 않기 </label>
                    </div>

                    <button type="button" class="btn-popup-close" onclick="closeWin('divpop', 'popupchkbox');">
					팝업존 창닫기
				</button>
                </div>
            </div>
        </div>
    </div>


    <!-- //popup zone -->

    <!-- wrapper -->
    <div class="wrapper " id="wrapper">
        <!-- header -->
        <header>




























            <div class="header">
                <div class="gnb-wrapper">
                    <div class="gnb-area clear">
                        <div class="fr">
                            <dl>
                                <dt>
									<button type="button" class="btn-title">
										인적자원개발컨퍼런스
									</button>
								</dt>
                                <dd>
                                    <ul>
                                        <li>
                                            <a href="/hrd4u/main.do"> 
												HRD4U 메인
											</a>
                                        </li>
                                        <li>
                                            <a href="/portal/main.do">
												HRD 콘텐츠
											</a>
                                        </li>
                                        <li>
                                            <a href="/hrdfestival/main.do"> 
												직업능력의 달 
											</a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/main.do" class="active"> 
												인적자원개발컨퍼런스 
											</a>
                                        </li>
                                        <li>
                                            <a href="/expertpool/main.do"> 
												전문가인력풀 
											</a>
                                        </li>
                                        <li>
                                            <a href="/studyorg/main.do"> 
												학습조직화 사업 
											</a>
                                        </li>
                                        <li>
                                            <a href="/hrdcert/main.do"> 
												인적자원개발 우수기관인증 
											</a>
                                        </li>
                                        <li>
                                            <a href="/hrdesgsprt"> 
												청년친화형 <br/>기업ESG지원사업
											</a>
                                        </li>
                                        <li>
                                            <a href="/sojt"> 
												현장맞춤형<br/>
												체계적훈련지원사업
											</a>
                                        </li>
                                        <li>
                                            <a href="/hrddoctor"> 
												능력개발전담 주치의 
											</a>
                                        </li>

                                    </ul>
                                </dd>
                            </dl>

                            <ul class="big-small-wrapper">
                                <li>
                                    <button type="button" class="big" onclick="zoomOut(); return false;">
										화면 확대
									</button>
                                </li>
                                <li>
                                    <button type="button" class="small" onclick="zoomIn(); return false;">
										화면 축소
									</button>
                                </li>
                            </ul>

                            <div class="top-search-wrapper">
                                <form id="mainLecture" name="mainLecture" method="POST" action="/hrd4u/TotHrd4uSearch.do">
                                    <fieldset>
                                        <legend class="blind">
                                            종합검색
                                        </legend>
                                        <div class="top-search-area">
                                            <input type="search" id="header-menu-search-content" onkeyup="search_enter();" name="pQuery_tmp" title="검색어를 입력해 주세요." value="" />
                                            <button type="button" id="go_Search">
												Search
											</button>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                            <script type="text/javascript">
                                $(document).ready(function() {
                                    $("#go_Search").on("click", function(e) {
                                        $("#mainLecture").attr("action", "/hrd4u/TotHrd4uSearch.do");
                                        $("#mainLecture").submit();
                                    });
                                });

                                function search_enter() {
                                    if (event.keyCode == 13) {
                                        searchSubmit();
                                    }
                                }

                                function searchSubmit() {
                                    $("#mainLecture").submit();
                                }
                            </script>
                        </div>
                    </div>
                </div>

                <div class="header-wrapper">
                    <div class="header-area clear">
                        <h1>
                            <a href="/hrdconference/main.do" class="clear">
                                <img src="/hrdconference/img/common/logo.png" alt="인적자원개발 컨퍼런스" />
                            </a>
                        </h1>

                        <ul class="top-menu">
                            <li>
                                <a href="/hrdconference/contents.do?menuNo=0101" class="topmenu1">
									행사안내
									<span class="line"></span>
								</a>

                                <div class="submenu-wrapper">
                                    <h2>
                                        <strong class="topmenu1">
							행사안내
						</strong>
                                    </h2>
                                    <ul>
                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0101" class="topmenu1-1">
                                                <span class="title">
									인사말
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0102" class="topmenu1-2">
                                                <span class="title">
									행사 개요
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0103" class="topmenu1-3">
                                                <span class="title">
									운영사무국
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>



                                    </ul>
                                </div>

                            </li>
                            <li>
                                <a href="/hrdconference/contents.do?menuNo=0201" class="topmenu2">
									프로그램
									<span class="line"></span>
								</a>

                                <div class="submenu-wrapper">
                                    <h2>
                                        <strong class="topmenu2">
						프로그램
					</strong>
                                    </h2>
                                    <ul>
                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0201" class="topmenu2-1">
                                                <span class="title">
								전체 일정
							</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0202" class="topmenu2-2">
                                                <span class="title">
								세션 안내
							</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0203" class="topmenu2-3">
                                                <span class="title">
								엑스포
							</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>






                            </li>
                            <li>
                                <a href="/hrdconference/contents.do?menuNo=0301" class="topmenu3">
									강연소개
									<span class="line"></span>
								</a>

                                <div class="submenu-wrapper">
                                    <h2>
                                        <strong class="topmenu3">
							강연소개
						</strong>
                                    </h2>
                                    <ul>
                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0301" class="topmenu3-1">
                                                <span class="title">
									기조 강연
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=0302" class="topmenu3-2">
                                                <span class="title">
									이슈별 동시강연
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>


                                    </ul>
                                </div>

                            </li>
                            <li>
                                <a href="/hrdconference/observe/intro.do" class="topmenu4">
									참관등록
									<span class="line"></span>
								</a>

                                <div class="submenu-wrapper">
                                    <h2>
                                        <strong class="topmenu4">
							참관등록
						</strong>
                                    </h2>
                                    <ul>
                                        <li>
                                            <a href="/hrdconference/observe/intro.do" class="topmenu4-1">
                                                <span class="title">
									참관안내
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="/hrdconference/observe/offline_01.do" class="topmenu4-2">
                                                <span class="title">
									참관신청
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/observe/login.do" class="topmenu4-3">
                                                <span class="title">
									참관확인서 발급
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/observe/passCheck.do" class="topmenu4-4">
                                                <span class="title">
									등록정보 변경
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                            </li>
                            <li>
                                <a href="/hrdconference/contents.do?menuNo=05_2022" class="topmenu5">
									지난 컨퍼런스
									<span class="line"></span>
								</a>

                                <div class="submenu-wrapper">
                                    <h2>
                                        <strong class="topmenu5">
							지난 컨퍼런스
						</strong>
                                    </h2>
                                    <ul>
                                        <li>
                                            <a href="/hrdconference/contents.do?menuNo=05_2022" class="topmenu5-1">
                                                <span class="title">
									지난 컨퍼런스 보기
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/observe/searchLecturer.do" class="topmenu5-2">
                                                <span class="title">
									강연자 검색
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                            </li>

                            <li>
                                <a href="/hrdconference/bbs/list/B0000038.do?menuNo=0603" class="topmenu6">
									알림 마당
									<span class="line"></span>
								</a>

                                <div class="submenu-wrapper">
                                    <h2>
                                        <strong class="topmenu6">
							알림 마당
						</strong>
                                    </h2>
                                    <ul>
                                        <li>
                                            <a href="/hrdconference/bbs/list/B0000036.do?menuNo=0601" class="topmenu6-1">
                                                <span class="title">
									공지사항
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/bbs/list/B0000037.do?menuNo=0602" class="topmenu6-2">
                                                <span class="title">
									FAQ
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/hrdconference/bbs/list/B0000038.do?menuNo=0603&section=lev" class="topmenu6-3">
                                                <span class="title">
									자료실
								</span>
                                                <span class="bg"></span>
                                                <span class="arrow"></span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- 전체메뉴 -->
            <div class="mask-totalmenu"></div>
            <div class="totalmenu-wrapper">
                <div class="totalmenu-area clear">
                    <dl class="totalmenu-slogan">
                        <dt>
							인적자원개발 컨퍼런스에<br />
							여러분을 초대합니다!
						</dt>
                        <dd>
                            직업능력에 관심 있는 사람들에게는 상호 교류의 기회를,<br /> 기업에게는 HRD부문 투자확산과 HRD전문성을 제고할<br /> 기회가 될 것입니다.
                        </dd>
                    </dl>
                </div>
            </div>
        </header>
        <!-- //header -->
        <!-- container -->
        <section>




            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>
                    한국산업인력공단 홍보관 > 엑스포 > 프로그램 > 인적자원개발 컨퍼런스
                </title>
            </head>

            <body>
                <!-- container -->
                <section>
                    <div class="container" id="container">
                        <div class="contents-navigation-area">
                            <p class="contents-navigation clear">
                                <span class="icon-home">
							Home 
						</span>
                                <span class="location">
							프로그램 
						</span>
                                <span class="icon-gt">
							&gt; 
						</span>
                                <strong>
							엑스포 
						</strong>
                            </p>
                        </div>

                        <div class="container-wrapper clear">
                            <!-- lnb -->

                            <div class="submenu-wrapper">
                                <h2>
                                    <strong class="topmenu2">
						프로그램
					</strong>
                                </h2>
                                <ul>
                                    <li>
                                        <a href="/hrdconference/contents.do?menuNo=0201" class="topmenu2-1">
                                            <span class="title">
								전체 일정
							</span>
                                            <span class="bg"></span>
                                            <span class="arrow"></span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="/hrdconference/contents.do?menuNo=0202" class="topmenu2-2">
                                            <span class="title">
								세션 안내
							</span>
                                            <span class="bg"></span>
                                            <span class="arrow"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/hrdconference/contents.do?menuNo=0203" class="topmenu2-3">
                                            <span class="title">
								엑스포
							</span>
                                            <span class="bg"></span>
                                            <span class="arrow"></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>






                            <!-- //lnb -->

                            <!-- contents  -->
                            <article>
                                <div class="contents" id="contents">
                                    <h3 class="contents-title">
                                        엑스포 </h3>
                                    <div class="tabmenu line">
                                        <ul class="tab3">
                                            <li style="width:25%">
                                                <a href="/hrdconference/contents.do?menuNo=0203" class="topmenu1-1">
                                                    신기술관
                                                </a>
                                            </li>
                                            <li style="width:25%">
                                                <a href="/hrdconference/contents.do?menuNo=0204" class="topmenu1-2" style="outline: none; box-shadow: none;">
                                                    HRD EXPO
                                                </a>
                                            </li>
                                            <li style="width:25%">
                                                <a href="/hrdconference/contents.do?menuNo=0205" class="topmenu1-3"> 
                                                    네트워킹 라운지
                                                </a>
                                            </li>
                                            <li style="width:25%">
                                                <a href="/hrdconference/contents.do?menuNo=0206" class="topmenu1-4 active" title="선택됨">
                                                    한국산업인력공단 홍보관
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="contents-wrapper">
                                        <!-- CMS 시작 -->
                                        <div class="contents-area">
                                            <h4 class="title-type01">
                                                공단 홍보관 운영
                                            </h4>
                                            <div class="contents-box">
                                                <div class="lecinfo_box type03" id="info-K1">
                                                    <div class="lecinfo_group">
                                                        <div class="lecinfo_info01">
                                                            <!-- 2023.01.20 추가작업 -->
                                                            <img src="/hrdconference/img/sub02/expo04.jpg" class="img_box" alt="공단 홍보관 운영 안내">
                                                            <div class="blind">

                                                                <p>
                                                                    등록/안내 데스크
                                                                </p>
                                                                <p>
                                                                    셀프등록
                                                                </p>
                                                                <p>
                                                                    공단홍보관 1,2,3,4,5
                                                                </p>

                                                                <p>
                                                                    101 Track D
                                                                </p>
                                                                <p>
                                                                    102 Track C
                                                                </p>
                                                                <p>
                                                                    103 Track B
                                                                </p>
                                                                <p>
                                                                    104 Track A
                                                                </p>
                                                                <p>
                                                                    105 네트워킹라운지
                                                                </p>

                                                                <p>
                                                                    HRD EXPO 1,2,3,4,5,6,7,8,9,10,11,12,13,14
                                                                </p>
                                                                <p>
                                                                    신기술관
                                                                </p>

                                                                <h5>
                                                                    HRD EXPO
                                                                </h5>


                                                                <table>
                                                                    <caption>
                                                                        한국산업인력공단 홍보관 정보표 : 기관명, 부스번호에 관한 정보 제공표
                                                                    </caption>
                                                                    <thead>
                                                                        <tr>
                                                                            <th scope="col">
                                                                                기관명
                                                                            </th>
                                                                            <th scope="col">
                                                                                부스번호
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <tr>
                                                                            <td>
                                                                                한국산업인력공단_일학습지원국
                                                                            </td>
                                                                            <td>
                                                                                1
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                한국산업인력공단_직업능력국
                                                                            </td>
                                                                            <td>
                                                                                2
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                국가인적자원개발컨소시엄
                                                                            </td>
                                                                            <td>
                                                                                3
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                한국산업인력공단_서울권역소속기관
                                                                            </td>
                                                                            <td>
                                                                                4
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                한국산업인력공단_능력평가국, 과정평가국
                                                                            </td>
                                                                            <td>
                                                                                5
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <!-- 2023.01.20 추가작업 -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="contents-area">
                                            <h4 class="title-type01">
                                                운영개요
                                            </h4>
                                            <div class="contents-box">
                                                <h5 class="title-type02">
                                                    운영기간
                                                </h5>
                                                <p class="word-type02">
                                                    ’22. 9. 15.(목) ~ 9. 16.(금), 09:30 ~ 17:30
                                                </p>
                                            </div>
                                            <div class="contents-box">
                                                <h5 class="title-type02">
                                                    운영장소
                                                </h5>
                                                <p class="word-type02">
                                                    코엑스 그랜드볼룸 등록데스크 옆
                                                </p>
                                            </div>

                                            <div class="contents-box">
                                                <h5 class="title-type02">
                                                    참여기관 및 주요내용
                                                </h5>
                                                <div class="table-type01">
                                                    <table width="100%">
                                                        <caption>참여기업 및 주요내용</caption>
                                                        <colgroup>
                                                            <col width="6%">
                                                            <col width="23%">
                                                            <col width="63%">
                                                            <col width="8%">
                                                        </colgroup>

                                                        <thead>
                                                            <tr>
                                                                <th scope="col">
                                                                    순번
                                                                </th>
                                                                <th scope="col">
                                                                    기관
                                                                </th>
                                                                <th scope="col">
                                                                    주요내용
                                                                </th>
                                                                <th scope="col">
                                                                    비고
                                                                </th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <tr>
                                                                <th scope="row" class="bg01">1</th>
                                                                <td>일학습지원국</td>
                                                                <td class="left">
                                                                    일학습병행 사업 홍보
                                                                </td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row" class="bg01">2</th>
                                                                <td>직업능력국</td>
                                                                <td class="left">
                                                                    공단 능력개발사업 (청년친화형기업ESG사업, 사업주훈련 등) 및 한국직업방송 홍보
                                                                </td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row" class="bg01">3</th>
                                                                <td>지역산업지원국</td>
                                                                <td class="left">
                                                                    공단 능력개발사업 홍보(컨소시엄, 지산맞, 산업전환 공동훈련센터 등)
                                                                </td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row" class="bg01">4</th>
                                                                <td>서울권역 소속기관</td>
                                                                <td class="left">
                                                                    서울권역 내 직업능력개발사업 홍보 (사업주훈련, 일학습병행 등)
                                                                </td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row" class="bg01">5</th>
                                                                <td>능력평가국, 과정평가국</td>
                                                                <td class="left">
                                                                    국가기술자격, 과정평가형자격 등 공단 자격시험 관련 안내 및 홍보
                                                                </td>
                                                                <td></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- //CMS 끝 -->
                                    </div>
                                </div>
                            </article>
                            <!-- //contents  -->
                        </div>
                    </div>
                </section>
                <!-- //container -->
                <script type="text/javascript">
                    menuOn(2, 3);
                </script>
            </body>

            </html>


        </section>
        <!-- //container -->

        <!-- footer -->
        <footer>

            <div class="footer">
                <div class="footer-wrapper">

                    <div class="collage-informaiton01 clear">
                        <address>
							(44538) 울산광역시 중구 종가로 345(교동) 한국산업인력공단 한국산업인력공단 고객센터 : 1644-8000
						</address>
                        <a href="https://hrdbank.net/portal/member/memberInfoMngrGuide.do" id="footer-menu01">
							개인정보처리방침
						</a>
                    </div>
                    <p class="collage-informaiton02">
                        고객센터(사이트 이용 문의) 052-714-8288 이메일 hrdportal@hrdkorea.or.kr
                    </p>
                    <p class="copyright">
                        COPYRIGHT (C) 2022 HRD4U.OR.KR ALL RIGHTS RESERVED.
                    </p>

                    <p class="banner-good-contents">
                        <span style="float:left">
							<a href="http://www.wa.or.kr/board/list.asp?BoardID=0006" target="_blank" title="새창 열림">
								<img src="/hrdbank/img/common/footer_banner02.png" alt="(사)한국장애인단체총연합회 한국웹접근성인증평가원 웹 접근성 우수사이트 인증마크(WA인증마크)" height="77" />
							</a>
							<img src="/hrd4u/img/common/footer_banner02.png" alt="KSA ISO 9001 CERTIFIED" height="55" style="margin-left:30px;"/>
						</span>
                    </p>
                </div>

                <!-- 상단으로 이동 -->
                <a href="#btn-top-go" class="btn-top-go">
					TOP
				</a>
                <!-- //상단으로 이동 -->

            </div>

        </footer>
        <!-- //footer -->
    </div>
    <!-- //wrapper -->


    <script src="/hrdconference/js/wow.js"></script>
    <script>
        wow = new WOW({
            animateClass: 'animated',
            offset: 100
        });
        wow.init();
    </script>

    <script>
        $(function() {
            var html = $('html, body'),
                navContainer = $('.nav-container'),
                navToggle = $('.nav-toggle'),
                navDropdownToggle = $('.has-dropdown');

            // Nav toggle
            navToggle.on('click', function(e) {
                var $this = $(this);
                e.preventDefault();
                $this.toggleClass('is-active');
                navContainer.toggleClass('is-visible');
                html.toggleClass('nav-open');
            });

            // Nav dropdown toggle
            navDropdownToggle.on('click', function() {
                var $this = $(this);
                $this.toggleClass('is-active').children('ul').toggleClass('is-visible');
            });

            // Prevent click events from firing on children of navDropdownToggle
            navDropdownToggle.on('click', '*', function(e) {
                e.stopPropagation();
            });
        });
    </script>

</body>

</html>
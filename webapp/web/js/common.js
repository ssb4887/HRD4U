/* menu on */
function menuOn(depth1, depth2, depth3) {
    var topmenu = $(".topmenu" + depth1);
    topmenu.addClass("active");

    var totalmenuDepth1 = $(".topmenu" + depth1);
    totalmenuDepth1.addClass("active");

    var totalmenuDepth2 = $(".topmenu" + depth1 + "-" + depth2);
    totalmenuDepth2.addClass("active");

    var totalmenuDepth3 = $(".topmenu" + depth1 + "-" + depth2 + "-" + depth3);
    totalmenuDepth3.addClass("active");
}

/* 화면 확대/축소 */
var nowZoom = 1;

function zoomIn() {
    nowZoom = nowZoom - 0.01;
    if (nowZoom <= 0.95) nowZoom = 0.95;
    zooms();
}

function zoomOut() {
    nowZoom = nowZoom + 0.01;
    if (nowZoom >= 1.05) nowZoom = 1.05;
    zooms();
}

function zoomReset() {
    nowZoom = 1;
    zooms();
}

function zooms() {
    document.body.style.transform = "scale(" + nowZoom + ")";

    if (nowZoom == 0.95) {
        alert("20%축소 되었습니다. 더 이상 축소할 수 없습니다.");
    }

    if (nowZoom == 1.05) {
        alert("20%확대 되었습니다. 더 이상 확대할 수 없습니다.");
    }
}

/* 상단 메뉴 */
function chk() {
    $(".contents-navigation ul > li > ul").slideUp(75);
    $(".contents-navigation ul > li > button").removeClass('active');

    if (cc == 1) {
        $(".wrapper").addClass('focus');
        $(".top-menu-wrapper > ul > li > .top-submenu").slideDown(100);
        $(".header-wrapper > .bg").slideDown(100);
        $(".btn-totalmenu>strong").text('전체메뉴 닫기');
        $(".menu").addClass('active');
        if ($(window).width() > 1023) {
            $(".mask-totalmenu").show();
        }
    } else {
        $(".wrapper").removeClass('focus');
        $(".top-menu-wrapper > ul > li > .top-submenu").slideUp(150);
        $(".header-wrapper > .bg").slideUp(250);
        $(".btn-totalmenu>strong").text('전체메뉴 열기');
        $(".menu").removeClass('active');
        if ($(window).width() > 1023) {
            $(".mask-totalmenu").hide();
        }
    }
}

/* 스크롤 */
$(window).scroll(function(e) {
    if ($(window).width() >= 1024) {
        if ($(this).scrollTop() > 51) {
            $(".wrapper").addClass("fixed");
        } else {
            $(".wrapper").removeClass('fixed');
        }
    } else {
        if ($(this).scrollTop() > 0) {
            $(".wrapper").addClass("fixed");
        } else {
            $(".wrapper").removeClass('fixed');
        }
    }

    /* 부드럽게 위로 이 동*/
    if ($(this).scrollTop() > 200) {
        $('.btn-top-go, .btn-mobile-back').fadeIn(150);
    } else {
        $('.btn-top-go, .btn-mobile-back').fadeOut(150);
    }
});

$(function() {    
    $(document).on('focus', "a, input, input[type='checkbox'], input[type='radio'], select, button, textarea, label", function() {
    	$(this).css({ 'outline': 'dashed 3px #fe00f5' });
    });

    $(document).on('blur', "a, input, input[type='checkbox'], input[type='radio'], select, button, textarea, label", function() {
    	$(this).css({ 'outline': 'none'});
    });


    $(".skip-navigation > a").on("click", function() {
        if ($("body").hasClass('main') == false) {
            var offset = $("#contents").offset();
            $('html, body').animate({ scrollTop: offset.top - ($(".header").outerHeight()) }, 400);
            $("#contents").focusin();
        } else {
            var offset = $(".main-visual-wrapper").offset();
            $('html, body').animate({ scrollTop: offset.top - ($(".header").outerHeight()) }, 400);
            $(".main-visual-wrapper").focusin();
        }
    });

    $("body").delay(0).animate({ "opacity": "1" }, 0);
    $(".sub-visual").addClass('active');

    /* 전체메뉴 비율 지정 */
    var menuLength = $(".top-menu-wrapper > ul > li").length;
    var counter = 'counter' + menuLength;

    $(".top-menu-wrapper > ul > li").addClass(counter);

    /* 상단 2차메뉴 */
    if (navigator.userAgent.indexOf("Android") > 0 ||
        navigator.userAgent.indexOf("iPhone") > 0 ||
        navigator.userAgent.indexOf("iPod") > 0 ||
        navigator.userAgent.indexOf("BlackBerry") > 0) {

        $(function() {
            $('.top-menu-wrapper > ul > li > a').click(function() {

                /* 전체메뉴 높이 지정 */
                var maxHeight = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                    return $(this).height();
                }).get();

                var heights = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                        return $(this).height();
                    }).get(),

                    maxHeight = Math.max.apply(null, heights);

                $(".header-wrapper > .bg, .top-menu-wrapper > ul > li > .top-submenu").height(maxHeight);

                event.preventDefault();
                setTimeout(chk, 100);
                cc = 1;
                $('.top-menu-wrapper > ul > li > a').removeClass('point');
                $(this).addClass('point');
            });

            $('.header').mouseleave(function() {
                setTimeout(chk, 400);
                cc = 0;
                $('.top-menu-wrapper > ul > li > a').removeClass('point');
            });

            if ($(window).width() <= 1700) {
                $(".side-menu-area01").slideUp(75);
            } else {
                $(".side-menu-area01").show();
            }
        });
    } else {
        $('.top-menu-wrapper > ul > li > a').mouseover(function() {
            /* 전체메뉴 높이 지정 */
            var maxHeight = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                return $(this).height();
            }).get();



            var heights = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                    return $(this).height();
                }).get(),

                maxHeight = Math.max.apply(null, heights);

            console.log(maxHeight);


            $(".header-wrapper > .bg, .top-menu-wrapper > ul > li > .top-submenu").height(maxHeight);

            setTimeout(chk, 100);
            cc = 1;
            $('.top-menu-wrapper > ul > li > a').removeClass('point');
            $(this).addClass('point');

            if ($(window).width() <= 1700) {
                $(".side-menu-area01").slideUp(75);
            } else {
                $(".side-menu-area01").show();
            }
        });

        $('.header').mouseleave(function() {
            if ($(window).width() > 1023) {
                setTimeout(chk, 400);
                cc = 0;
                $('.top-menu-wrapper > ul > li > a').removeClass('point');
            }
        });

        $('.top-menu-wrapper > ul > li > a').focus(function() {
            /* 전체메뉴 높이 지정 */
            var maxHeight = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                return $(this).height();
            }).get();



            var heights = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                    return $(this).height();
                }).get(),

                maxHeight = Math.max.apply(null, heights);

            console.log(maxHeight);


            $(".header-wrapper > .bg, .top-menu-wrapper > ul > li > .top-submenu").height(maxHeight);

            setTimeout(chk, 100);
            cc = 1;
            $(this).addClass('point');
        });

        $('.top-menu-wrapper > ul > li > .top-submenu > ul > li:last-child > a').blur(function() {
            setTimeout(chk, 100);
            cc = 0;
            $('.top-menu-wrapper > ul > li > a').removeClass('point');
        });
    }

    /* 로그인, 대학메인, 사이트맵 1024 이상 1700 이하 */
    $(".global-menu > button").click(function() {
        $(this).toggleClass('active');

    });



    /* 전체 메뉴 */
    $(".btn-totalmenu").click(function() {
        $(".totalmenu-area > ul > li > .top-submenu").show();

        var thisWidth = $(window).width();
        if (thisWidth < 1024) {
            $(".totalmenu-area > ul > li > a.temp-depth1-active").removeClass('temp-depth1-active').addClass('active');
            $(".totalmenu-area>ul>li>.top-submenu>ul>li>a.temp-depth2-active").removeClass('temp-depth2-active').addClass('active');

            $("body").addClass("fixed");
            $('.totalmenu-wrapper').show();
            $(".totalmenu-area > ul > li > .top-submenu").hide();
            if ($("body").hasClass("main") == true) {
                $(".totalmenu-area > ul > li .top-submenu.topmenu1").show();
            } else {
                $(".totalmenu-area > ul > li a.active + .top-submenu").show();
            }
            $(".totalmenu-area > ul > li a.active + .top-submenu").show();
            $(".totalmenu-area>ul>li>.top-submenu>ul>li a.active> ul").show();
            $(".totalmenu-area>ul>li>.top-submenu>ul>li> ul.active").show();
            $(".mask-totalmenu").fadeIn(150, function() {
                $(".totalmenu-wrapper").addClass('active');
            });

            $(".contents-navigation").removeClass('active');
            $(".lnb-wrapper").removeClass('active');
        } else {
            /* 전체메뉴 높이 지정 */
            var maxHeight = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                return $(this).height();
            }).get();

            var heights = $(".top-menu-wrapper > ul > li > .top-submenu").map(function() {
                    return $(this).height();
                }).get(),

                maxHeight = Math.max.apply(null, heights);

            $(".header-wrapper > .bg, .top-menu-wrapper > ul > li > .top-submenu").height(maxHeight);

            if ($(".mask-totalmenu").css("display") == "none") {
                setTimeout(chk, 100);
                cc = 1;
            } else {
                setTimeout(chk, 100);
                cc = 0;
            }
        }
    });

    $(".mask-totalmenu, .btn-mobile-close").click(function() {
        var thisWidth = $(window).width();
        if (thisWidth < 1024) {
            $("body, .wrapper").removeClass("fixed");
            $(".btn-totalmenu").removeClass('on');
            $('.totalmenu-wrapper').removeClass('active');
            $(".mask-totalmenu").hide();
            $(".totalmenu-area > ul > li > a").removeClass('point');
            $(".totalmenu-area > ul > li > .top-submenu").hide();
            $(".totalmenu-area > ul > li > .top-submenu>ul > li > ul").hide();
            $(".totalmenu-area>ul>li>.top-submenu>ul>li>a").removeClass('point');
        } else {
            $("body, .wrapper").removeClass("fixed");
            $(".btn-totalmenu").removeClass('on');
            $('.totalmenu-wrapper').slideUp(150);
            $(".mask-totalmenu").hide();
            $(".totalmenu-area > ul > li > a").removeClass('point');
            $(".totalmenu-area > ul > li > .top-submenu").show();
            $(".totalmenu-area>ul>li>.top-submenu>ul>li > ul").show();
        }
    });

    $(".totalmenu-area > ul > li > a").click(function() {
        $(".totalmenu-area > ul > li > a.active").removeClass('active').addClass('temp-depth1-active');

        var thisWidth = $(window).width();
        if (thisWidth < 1024) {
            event.preventDefault();
            if ($(this).next(".top-submenu").css("display") != "none") {
                $(".totalmenu-area > ul > li > a").removeClass('point');
                $(".totalmenu-area > ul > li > .top-submenu").hide();
            } else {
                $(".totalmenu-area > ul > li > a").removeClass('point');
                $(".totalmenu-area > ul > li > .top-submenu").hide();
                $(this).addClass('point');
                $(this).next(".top-submenu").show();
            }
        }
    });

    $(".totalmenu-area>ul>li>.top-submenu>ul>li>a").on("click", function() {
        $(".totalmenu-area>ul>li>.top-submenu>ul>li>a.active").removeClass('active').addClass('temp-depth2-active');

        if ($(this).children().hasClass('arrow') == true) {
            event.preventDefault();
            if ($(this).next(".totalmenu-area>ul>li>.top-submenu>ul>li > ul").css("display") != "none") {
                $(".totalmenu-area>ul>li>.top-submenu>ul>li>a").removeClass('point');
                $(".totalmenu-area>ul>li>.top-submenu>ul>li > ul").slideUp(75);
            } else {
                $(".totalmenu-area>ul>li>.top-submenu>ul>li>a").removeClass('point');
                $(".totalmenu-area>ul>li>.top-submenu>ul>li > ul").slideUp(75);
                $(this).addClass('point');
                $(this).next(".totalmenu-area>ul>li>.top-submenu>ul>li > ul").slideDown(75);
            }
        } else {
            event.stopPropagation();
        }
    });

    $("#open-share-list").on("click", function() {
        $(".share-list-wrapper").show();
    });

    $(".share-list-wrapper .close").on("click", function() {
        $(".share-list-wrapper").hide();
    });


    $(".contents-navigation ul > li > button").on("click", function() {
        if ($(this).next(".contents-navigation ul > li > ul").css("display") == "none") {
            $(".contents-navigation ul > li > button").removeClass('active');
            $(".contents-navigation ul > li > ul").slideUp(75);
            $(this).next().slideDown(75);
            $(this).addClass('active');
        } else {
            $(".contents-navigation ul > li > ul").slideUp(75);
            $(".contents-navigation ul > li > button").removeClass('active');
        }
    });



    /* mobile contents title - lnb menu */
    $(".contents-navigation").on("click", function() {
        $(".lnb-area > ul > li > ul").hide();
        $(".lnb-area > ul > li > a").removeClass('active, point');
        $(".lnb-area > ul > li > ul.active").show();
        $(".lnb-area > ul > li > a.temp-active").removeClass('temp-active').addClass('active');

        if ($(window).width() < 1024) {
            if ($(".lnb-wrapper").css("display") != "none") {
                $("body, .wrapper").removeClass('fixed');
                $(this).removeClass('active');
                $(".lnb-wrapper").removeClass('active');
            } else {
                $("body, .wrapper").addClass('fixed');
                $(this).addClass('active');
                $(".lnb-wrapper").addClass('active');
            }
        }
    });

    $(".lnb-area > ul > li > a").on("click", function() {
        $(".lnb-area > ul > li > a.active").removeClass('active').addClass('temp-active');

        if ($(this).children().hasClass('arrow') == true) {
            event.preventDefault();

            if ($(this).next(".lnb-area > ul > li > ul").css("display") != "none") {
                $(".lnb-area > ul > li > a").removeClass('point');
                $(".lnb-area > ul > li > ul").slideUp(75);
            } else {
                $(".lnb-area > ul > li > a").removeClass('point');
                $(".lnb-area > ul > li > ul").slideUp(75);
                $(this).addClass('point');
                $(this).next(".lnb-area > ul > li > ul").slideDown(75);
            }
        } else {
            event.stopPropagation();
        }
    });
    /* 부드럽게 상단으로 이동 */
    $('.btn-top-go').click(function() {
        $('html, body').animate({ scrollTop: 0 }, 400);
        return false;
    });

    /* 최대화 최소화 */
    window.onresize = function() {
        if (window.innerHeight <= 1024) {
            $("body, .wrapper").removeClass('fixed');
            $(".totalmenu-wrapper").removeClass('active');
            $(".mask-totalmenu").hide();
            $(".header-wrapper > .bg").hide();
            $(".top-menu-wrapper > ul > li > .top-submenu").hide();
            $(".totalmenu-area > ul > li, .btn-totalmenu").removeClass('on');
            $(".totalmenu-area > ul > li > .top-submenu").show();
            $(".totalmenu-wrapper").hide().removeClass('active');
            $("body, .wrapper").removeClass('fixed');
            $(".contents-navigation").removeClass('active');
            $(".contents-navigation>ul>li>ul").hide();
            $(".lnb-wrapper").removeClass('active');
            $(".share-list-wrapper").hide();
        }
    }


    /* 탬메뉴 01 
    charterTextMenu = $(".tabmenu-wrapper > ul > li a.active").text().trim();
    $(".mobile-title").text(charterTextMenu);

    $(".mobile-title").on("click", function() {
        if ($(this).next().css('display') == 'none') {
            $(".mobile-title").removeClass('active');
            $(".tabmenu-wrapper > ul").slideUp(75);
            $(this).addClass('active');
            $(this).next().slideDown(75);
        } else {
            $(".mobile-title").removeClass('active');
            $(".tabmenu-wrapper > ul").slideUp(75);
            $(this).removeClass('active');
            $(this).next().slideUp(75);
        }
    });
    */

    $(".tabmenu-wrapper > ul > li > a.active").attr('title', '선택됨');
    $(".tabmenu-wrapper > ul > li > a").on("click", function() {
        $(".tabmenu-wrapper > ul > li > a").removeClass('active').attr('title', '');
        $(this).addClass('active').attr('title', '선택됨');
    });

});

/* resize */
$(window).resize(function() {
    $(".contents-navigation>ul>li>ul").hide();
    $(".totalmenu-wrapper").removeClass('active');
    $(".header-wrapper > .bg").hide();
    $(".menu").removeClass('active');
    $(".share-list-wrapper").hide();
    if ($(window).width() >= 1024) {
        $(".lnb-area > ul > li > ul").hide();
        $(".lnb-area > ul > li > ul.active").show();
        $("body").removeClass('fixed');
        $(".contents-navigation").removeClass('active');
    } else {
        $(".lnb-area > ul > li > ul").hide();
        $(".lnb-area > ul > li > ul.active").show();
    }
});



function printWin() {
    window.open('../include/print.html', '', 'width=960,height=600,scrollbars=yes');
}


function go_url01() {
    var url = document.getElementById('select-agency01').value;
    if (url == "http://www.hrd4u.or.kr") {
        return false;
    } else if (url != '') {
        window.open(url, '_blank');

    } else {
        alert("관련사이트를 선택하십시오.");
        return false;
    }
}

// -----------------------------------------------------------
// 화면 가운데에 새창 열기
// -----------------------------------------------------------
/** 캠퍼스맵 오픈 **/
function WindowOpen(page, w, h, s, r) {
    var win = null; // 프레임이 존재 할 경우 
    // parent.window.screenLeft 식으로 
    // document.body.offsetWidth, document.body.offsetHeight 도 창의 크기에 맞게 변경 가능 
    var x = window.screenLeft;
    var y = window.screenTop;
    var l = x + ((document.body.offsetWidth - w) / 2);
    var t = y + ((document.body.offsetHeight - h) / 2);
    var settings = '';

    settings = 'width=' + w + 'px,';
    settings += 'height=' + h + 'px,';
    settings += 'top=' + t + 'px,';
    settings += 'left=' + l + 'px,';
    settings += 'scrollbars=' + s + ',';
    settings += 'resizable=' + r + ',';
    settings += 'status=0';

    var windows = window.open(page, win, settings);
    windows.focus();
}
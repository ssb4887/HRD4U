$(function() {
    $(".btn-popup").on("click", function() {
        $('body').addClass('fixed');
        $(".popupzone-wrapper").fadeIn(150).addClass('active');
    });

    $(".popupzone-footer-wrapper > .btn-close").on("click", function() {
        $('body').removeClass('fixed');
        $(".popupzone-wrapper").fadeOut(150).removeClass('active');
        // $(".mask-popup").fadeOut(150);
    });


    /* 팝업 알림      
    var popupzoneSliderLength = $("#popupzone-slider .item").length;

    if ($(window).width() >= 1480) {
        if (popupzoneSliderLength == 1) {
            $(".popupzone-area").css("width", "480px");
        } else if (popupzoneSliderLength == 2) {
            $(".popupzone-area").css("width", "960px");
        } else {
            popupzoneSliderLength = 3;
            $(".popupzone-area").css("width", "984px");
        }
    } 
    */



    var popupZoneSlider = $('#popupzone-slider');
    popupZoneSlider.owlCarousel({
        autoplay: false,
        autoplayTimeout: 5000,
        slideSpeed: 5000,
        slideBy: 1,
        loop: true,
        margin: 10,
        nav: false,
        items: 3,
        dots: true,
        dotsData: true,
        mouseDrag: true,
        dotsContainer: '#popup-thumbs01',
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            640: {
                items: 2
            },
            1024: {
                items: 3
            }

        }
    });

});
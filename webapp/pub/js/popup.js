$(function() {
    $(".btn-popup").on("click", function() {
        $(".popupzone-wrapper").addClass('active');
    });

    $(".popupzone-footer-wrapper > .btn-close").on("click", function() {
        $(".popupzone-wrapper").removeClass('active');
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
        autoplay: true,
        autoplayTimeout: 5000,
        slideSpeed: 5000,
        loop: true,
        margin: 10,
        nav: false,
        items: 3,
        dots: true,
        mouseDrag: true,
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
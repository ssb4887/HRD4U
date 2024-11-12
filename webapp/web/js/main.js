$(function() {

    $(".main-visual-wrapper").addClass('active');

    /* main visual */
    var mainBannerSlider = $('#main-banner-slider');

    mainBannerSlider.owlCarousel({
        items: 1,
        margin: 0,
        loop: true,
        nav: false,
        dots: false,
        autoplay: true,
        autoplayTimeout: 10000,
        slideSpeed: 10000,
        onInitialized: counter,
        onTranslated: counter
    });

    function counter(event) {
        if (!event.namespace) {
            return;
        }
        var slides = event.relatedTarget;
        $('#counter').html("<strong>" + (slides.relative(slides.current()) + 1) + '</strong>/' + slides.items().length);
    }

    $('.main-banner-option .btn-next').click(function() {
        mainBannerSlider.trigger('next.owl.carousel');
    });
    $('.main-banner-option .btn-previous').click(function() {
        mainBannerSlider.trigger('prev.owl.carousel');
    });


    $('.main-banner-option .btn-play').on('click', function() {
        mainBannerSlider.trigger('play.owl.autoplay', [10000]);
        $('.main-banner-option .btn-play').hide();
        $('.main-banner-option .btn-stop').show();
    })
    $('.main-banner-option .btn-stop').on('click', function() {
        mainBannerSlider.trigger('stop.owl.autoplay');
        $('.main-banner-option .btn-play').show();
        $('.main-banner-option .btn-stop').hide();
    });

    $(".main-board-wrapper > ul > li > button").on("click", function() {
        $(".main-board-wrapper > ul > li > button").removeClass('active');
        $(this).addClass('active');
    });
});
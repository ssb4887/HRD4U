$(function() {
    /* 자동완성 */
    $(".btn-auto-complate").on("click", function() {
        $(this).toggleClass('on');
        $(".auto-complate-list").slideToggle(75);
    });

    /* 검색옵션 여닫기 */
    $(".btn-total-search-option").on("click", function() {
        $(this).toggleClass('on');
        $(".total-search-option").slideToggle(75);
        $(".btn-auto-complate").removeClass('on');
        $(".auto-complate-list").slideUp(75);
    });

    /* 카테고리별 토픽 */
    $(".aside-category-menu-list > li > a").on("click", function() {
        var currentClass = $(this).parent().attr('class');

        $(".aside-category-menu-list > li > a").removeClass('on');
        $(this).addClass('on');
        $(".aside-category-topic-box > ul").hide();
        $("." + currentClass).show();
    });

    /* 인기 검색어 */
    $(".best-search-wrod-tabmenu > ul > li > a").on("click", function() {
        $(".best-search-wrod-tabmenu > ul > li > a").removeClass('on');
        $(this).addClass('on');
    });
});
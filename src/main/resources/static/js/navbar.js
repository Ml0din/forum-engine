function animatedToggleNavMobile() {
    if($(".navbar-mobile-component").css("display") === "none") {
        $(".navbar-mobile-component").css("display", "flex");
        $(".toggle-button").addClass('is-active');

        $(".navbar-mobile-component").animate({
            opacity: 1
        }, 300, "linear")
    } else {
        $(".navbar-mobile-component").css("display", "none");
        $(".toggle-button").removeClass('is-active');

        $(".navbar-mobile-component").animate({
            opacity: 0
        }, 300, "linear")
    }
}
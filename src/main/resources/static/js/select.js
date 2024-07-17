var enabled = false;
var in_animation = false;
function toggleSelect() {
    if(!in_animation) {
        if(!enabled) {
            $(".select-menu").css("display", "flex");

            in_animation = true;
            $(".select-menu").animate({opacity: 1}, 500, "linear", function () {
                enabled = true;
                in_animation = false;
            })
        }else {
            in_animation = true;
            $(".select-menu").animate({opacity: 0}, 500, "linear", function () {
                $(".select-menu").css("display", "none");
                enabled = false;
                in_animation = false;
            })
        }
    }
}
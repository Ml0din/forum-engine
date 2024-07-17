var enabled = false;
var in_animation = false;
function toggleModal() {
    if(!in_animation) {
        if(!enabled) {
            in_animation = true

            $(".modal-panel").css("display", "flex");
            $(".modal-panel").animate({
                opacity: 1
            }, 500, "linear", function () {
                in_animation = false
                enabled = true
            })
        }else {
            in_animation = true
            $(".modal-panel").animate({
                opacity: 0
            }, 500, "linear", function () {
                $(".modal-panel").css("display", "none")

                in_animation = false
                enabled = false
            })
        }
    }
}
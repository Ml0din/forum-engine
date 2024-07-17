function toggleCategory(categoryID) {
    if($("." + categoryID).css("display") === "flex") {
        $("." + categoryID).css("display", "none");
    } else {
        $("." + categoryID).css("display", "flex");
    }
}
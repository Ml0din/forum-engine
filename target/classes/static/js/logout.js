var csrf_header = $('meta[name="_csrf_header"]').attr("content");
var csrf = $('meta[name="_csrf"]').attr("content");

function logout() {
    $.ajax({
        url: "/sign_out",
        method: "post",
        headers: {
            [csrf_header]: csrf
        },

        success: function (response) {
            window.location.href="/sign_in"
        },

        error: function (response) {
            notify("Error occurred.", notification_type.ERROR)
        }
    });
}
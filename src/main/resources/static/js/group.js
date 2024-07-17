var csrf_header = $('meta[name="_csrf_header"]').attr("content");
var csrf =  $('meta[name="_csrf"]').attr("content");

function addAuthority(group) {
    var payload = {
        "authority": $("#authority").val()
    }

    $.ajax({
        url: "/edit_authorities/" + group,
        dataType: 'json',
        contentType: 'application/json',
        data: toJSON(payload),
        method: "post",
        headers: {
            [csrf_header]: csrf
        },

        success: function (response) {
            $("#authority").val("");
            $(".authorities").append("                        <div class=\"authority\">\n" +
                "                            <div class=\"authority-label\">\n" +
                "                                <span>" + payload["authority"] + "</span>\n" +
                "                            </div>\n" +
                "                            <div class=\"authority-delete\">\n" +
                "                                <button><img src=\"/images/group/delete.svg\" width=\"20px\" height=\"20px\" draggable=\"false\"></button>\n" +
                "                            </div>\n" +
                "                        </div>")

            toggleModal();
            notify(response["message"], notification_type.SUCCESS)
        },

        error: function (response) {
            $("#authority").val("");
            toggleModal()
            notify(response["responseJSON"]["message"], notification_type.ERROR)
        }
    });
}

function deleteAuthority(group, authority) {
    $.ajax({
        url: "/edit_authorities/" + group,
        dataType: "json",
        contextType: "application/json",
        method: "delete",
        headers: {
            [csrf_header]: csrf,
            "authority": authority
        },

        success: function (response) {
            $("#" + authority).remove();
            notify(response["message"], notification_type.SUCCESS)
        },

        error: function (response) {
            notify(response["responseJSON"]["message"], notification_type.ERROR)
        }
    });
}
var current_key = 0;

function notify(message, type) {
    $('.notification-panel').add("<div class=\"notification-box " + current_key + "\">\n" +
        "      <div class=\"notification-content\">\n" +
        "        <div class=\"notification-icon\">\n" +
        "          <img src=\"/images/" + type + "\" width=\"70px\" height=\"70px\" draggable=\"false\">\n" +
        "        </div>\n" +
        "        <div class=\"notification-text\">\n" +
        "          <p>" + message + "</p>\n" +
        "        </div>\n" +
        "      </div>\n" +
        "      <div class=\"notification-timer-bar " + current_key + "-timer\"></div>\n" +
        "    </div>").appendTo('.notification-panel')


    var animation = new NotificationAnimation(current_key.toString());
    $('.' + animation.notification_id).animate({
        opacity: 1
    }, 1000, function () {
        $('.' + animation.notification_id + '-timer').animate({
            width: 0,
        }, 3000, function () {
            $('.' + animation.notification_id).animate({
                opacity: 0,
            }, 1000, function () {
                $("." + animation.notification_id).remove();
            });
        });
    });

    current_key++;
}

class NotificationAnimation {
    constructor(notification_id) {
        this.notification_id = notification_id;
    }
}

const notification_type = {
    INFO: 'info.svg',
    SUCCESS: 'success.svg',
    WARNING: 'warning.svg',
    ERROR: 'error.svg',
}
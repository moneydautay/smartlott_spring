/**
 * Created by greenlucky on 12/23/16.
 */

/**
 * Create message given by message, type and xButton
 * @param message notification
 * @param type [alert-danger, alert-success,...]
 * @param xButton: boolean [true, false]; to control button close message; default is true
 * @returns {string}
 */

function showMessage(message, type, xButton=true) {
    var strError = '';
    strError += '<div class="alert '+type+'" role="alert">';
    if(xButton) {
        strError += ' <button aria-hidden="true" class="close" data-dismiss="alert" aria-label="Close">';
        strError += ' <span>X</span>';
        strError += '</button>';
    }
    strError += '<p>';
    strError += message;
    strError += '</p>';
    strError += '</div>';

    return strError;
}


/**
 * Show all errors html tag has id messageArea
 * @param errors
 */
function showErrors(errors) {

    var messageArea = $('#messageArea');
    $.each(errors, function (index, error) {
        messageArea.append(showMessage(error.message,'alert-danger alert-dismissible'));
    });
}

/**
 * Show all errors html tag has id messageArea
 * @param errors
 */
function showNotifications(id, msgs, type) {
    var xButton = true;
    var messageArea = $('#'+id);
    $.each(msgs, function (index, msg) {
        var massge = msg.content;
        if (msg.notificationType.required) {
            xButton = false;
            var url = msg.notificationType.url;
            massge = '<a href="' + url + '">' + msg.content + '</a>';
        }
        messageArea.append(showMessage(massge,type,xButton));
    });
}


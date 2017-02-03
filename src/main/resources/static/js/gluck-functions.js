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

function showMessages(data) {
    var messageArea = $('#messageArea');

    messageArea.html('');
    $.each(data, function (index, msg) {
        var messageType = msg.messageType;
        if(messageType.$name == 'SUCCESS' || messageType == 'SUCCESS')
            messageArea.append(showMessage(msg.message,'alert-success alert-dismissible'));
        else if (messageType.$name == 'WARNING' || messageType == 'WARNING')
            messageArea.append(showMessage(msg.message,'alert-warning alert-dismissible'));
        else
            messageArea.append(showMessage(msg.message,'alert-danger alert-dismissible'));
    });
}

/**
 * Show all errors html tag has id messageArea
 * @param errors
 */
function showSuccess(data) {
    var messageArea = $('#messageArea');
    messageArea.html('');
    $.each(data, function (index, msg) {
        messageArea.append(showMessage(msg.message,'alert-success alert-dismissible'));
    });
}

/**
 * Show all errors html tag has id messageArea
 * @param errors
 */
function showErrors(errors) {
    var messageArea = $('#messageArea');
    messageArea.html('');
    $.each(errors, function (index, error) {
        messageArea.append(showMessage(error.message,'alert-danger alert-dismissible'));
    });
}

/**
 * Show notification
 * @param message
 * @param type
 * @param xButton
 * @returns {string}
 */
function showNotification(messageId, message, type, xButton=true) {
    var strError = '';
    strError += '<div class="alert '+type+'" role="alert">';
    //<!--aria-hidden="true" -->
    if(xButton) {
        strError += ' <button onclick="processedNotif('+messageId+',1)" aria-hidden="true" class="close" data-dismiss="alert" aria-label="Close" id="'+messageId+'">';
        strError += ' <span class="msgBtn">Để sau</span>';
        strError += '</button>';
        strError += ' <button onclick="processedNotif('+messageId+', 2)" aria-hidden="true" class="close" data-dismiss="alert" aria-label="Close" id="'+messageId+'">';
        strError += ' <span class="msgBtn">Đóng</span>';
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
        messageArea.append(showNotification(msg.id,massge,type,xButton));
    });
}

function getCurrentDate(seperate='-') {
    var currentDate = new Date()
    var day = currentDate.getDate()
    var month = currentDate.getMonth() + 1
    var year = currentDate.getFullYear();
    return day +seperate+ month + seperate +year;
}

var slug = function(str) {
    str = str.replace(/^\s+|\s+$/g, ''); // trim
    str = str.toLowerCase();

    // remove accents, swap ñ for n, etc
    var from = "ãàáäâấăắằẽèéëêếìíïîõòóöôơớởùúüûưứñç·/_,:;";
    var to   = "aaaaaaaaaeeeeeeiiiioooooooouuuuuunc------";
    for (var i=0, l=from.length ; i<l ; i++) {
        str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
    }

    str = str.replace(/[^a-z0-9 -]/g, '') // remove invalid chars
        .replace(/\s+/g, '-') // collapse whitespace and replace by -
        .replace(/-+/g, '-'); // collapse dashes

    return str;
};


/**
 * Shows success function and reload page
 * @param data
 */
function showSuccessAction(data) {
    showSuccess(data);
    setTimeout(function () {
        window.location.reload();
    },1000);
}



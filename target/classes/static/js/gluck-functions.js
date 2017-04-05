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

function createMessage(message, type, xButton=true) {
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

function showMessage(msg, type, xButton=true) {
    var messageArea = $('#messageArea');
    messageArea.html(createMessage(msg, type, xButton));
}

function showMessages(data) {
    var messageArea = $('#messageArea');

    messageArea.html('');
    $.each(data, function (index, msg) {
        var messageType = msg.messageType;
        if(messageType.$name == 'SUCCESS' || messageType == 'SUCCESS')
            messageArea.append(createMessage(msg.message,'alert-success alert-dismissible'));
        else if (messageType.$name == 'WARNING' || messageType == 'WARNING')
            messageArea.append(createMessage(msg.message,'alert-warning alert-dismissible'));
        else
            messageArea.append(createMessage(msg.message,'alert-danger alert-dismissible'));
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
        messageArea.append(createMessage(msg.message,'alert-success alert-dismissible'));
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
        messageArea.append(createMessage(error.message,'alert-danger alert-dismissible'));
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


/**
 * check all or uncheck table
 */
$('#selectAll').change(function () {
    var checked = false;
    if($(this).is(':checked'))
        checked = true;
    $('input[name="_id"]').prop('checked',checked);
})


/**
 * get all id of checked table data.
 *
 * @param idTable
 * @returns {Array}
 */
function getIdsCheckedTable(idTable) {
    var ids = [];
    $('#' + idTable + ' input:checked').map(function (index) {
        ids[index] = this.value;
    });
    return ids;
}

/**
 * show number data to select given by number and id select
 * @param levelNetwork
 * @param idSelect
 */
//<![CDATA[
function showOptionSelect(levelNetwork=0, idSelect) {
    var select = $('#' + idSelect);
    for (var i = 0; i <= levelNetwork; i++) {
        var selected = (i == 0) ? 'selected="selected"' : '';
        var strOption = '<option value="' + i + '">' + i + '</option>';
        select.append(strOption);
    }

    $('#levelNetwork').val('0').prop('selected', true);
}

//]]>
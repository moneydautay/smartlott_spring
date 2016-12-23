/**
 * Created by greenlucky on 12/23/16.
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

function showErrors(errors) {

    var messageArea = $('#messageArea');
    $.each(errors, function (index, error) {
        messageArea.append(showMessage(error.message,'alert-danger alert-dismissible'));
    });
}

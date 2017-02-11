/**
 * Created by Mrs Hoang on 11/02/2017.
 */

function showCheckoutCashSuccess(data) {
    showSuccess(data);
    //clear content form cash checkout
    $('#frmCheckoutCash').html('');
    setTimeout(function () {
        window.location.reload();
    },1000)
}

function showTransactionInfo(data, msg = null) {

    data = data.content;

    $('#orderNumber').html(data.id);
    var lotteryDetails = data.lotteryDetails;
    var orderDetail = $('#orderDetail');

    $.each(lotteryDetails, function (index, lottery) {
        var strNumberLottery = lottery.lottery.coupleOne + '-' + lottery.lottery.coupleTwo
            + '-' + lottery.lottery.coupleThree + '-' + lottery.lottery.coupleFour
            + '-' + lottery.lottery.coupleFive + '-' + lottery.lottery.coupleSix;
        var price = lottery.lottery.lotteryType.price;
        strRow = '<tr>';
        strRow += '<td>' + (index + 1) + '</td>';
        strRow += '<td>#' + (lottery.id) + '</td>';
        strRow += '<td>' + strNumberLottery + '</td>';
        strRow += '<td class="text-right">$' + price + '</td>';
        strRow += '</tr>';
        orderDetail.append(strRow);
    });

    //total
    strTotal = '<tr>';
    strTotal += '<td colspan="3" class="text-center" >' + msg + '</td>';
    strTotal += '<td class="text-right">';
    strTotal += '$' + data.amount;
    strTotal += '</td>';
    strTotal += '</tr>';

    orderDetail.append(strTotal);
    $('#buydate').html(data.createdDate);
    $('#discount').html(0);
    $('#feesOther').html(0);
    $('#statusOrder').html(data.transactionStatus.name);
    $('#totalOrder').html(data.amount);

    $('#amount').val(data.amount);
}

function showCheckoutErrors(data) {
    showMessages(data);
    $('#checkoutDetail').html('');
}


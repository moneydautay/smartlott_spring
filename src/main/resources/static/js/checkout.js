/**
 * Created by Mrs Hoang on 11/02/2017.
 */

function showCheckoutCashSuccess(data) {
    var urlCheckoutResult = '/checkout/result';
    urlCheckoutResult += '/'+ data.id;
    setTimeout(function () {
        window.location.href = urlCheckoutResult;
    },500)
}

function showTransactionInfo(data, msg = null) {

    data = data.content;

    $('#orderNumber').html(data.id);
    var lotteryDetails = data.lotteryDetails;
    var orderDetail = $('#orderDetail');

    $.each(lotteryDetails[0]['lotteries'], function (index, lottery) {
        console.log(lottery);
        var strNumberLottery = lottery.coupleOne + '-' + lottery.coupleTwo
            +'-'+lottery.coupleThree+'-'+lottery.coupleFour
            +'-'+lottery.coupleFive+'-'+lottery.coupleSix;
        var price = lottery.lotteryType.price;
        strRow = '<tr>';
        strRow+='<td>'+(index+1)+'</td>';
        strRow+='<td>#'+(lottery.id)+'</td>';
        strRow+='<td>'+strNumberLottery+'</td>';
        strRow+='<td class="text-right">$'+price+'</td>';
        strRow+='</tr>';
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


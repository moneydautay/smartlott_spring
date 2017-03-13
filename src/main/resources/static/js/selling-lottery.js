/**
 * Created by greenlucky on 12/8/16.
 */
jQuery(document).ready(function ($) {
    var current_lot_id = '';
    var pos = '';

    $('.textClose').click(function () {
        $('.box-choosing-number').hide();
    })
    
    $(document).on('click','.box-numeric-lottery li input', function () {
        current_lot_id = $(this).attr('id');
        console.log($(this).offset());
        showNumericBox(current_lot_id);
    })

    $('.box-choosing-number li').click(function () {
        var chooseing_number = $(this).html();
        $('#'+current_lot_id).val(chooseing_number);
        $('.box-choosing-number').hide();
        $('#'+current_lot_id).removeClass('invalid-input-lot');
        var lastNextId = current_lot_id.substr(current_lot_id.length-1,1);
        while (lastNextId < 6) {
            var nextId = current_lot_id.substr(0, current_lot_id.length - 1) + (parseInt(lastNextId) + 1);
            if ($('#' + nextId).val() == ''){
                current_lot_id = nextId;
                showNumericBox(current_lot_id);
                break;
            }
            lastNextId ++;
        }
    })

    $('.numeric-buying-lottery').change(function () {
        $('#numeric-needed-lottery').hide();
        var numeric_buying = $(this).val();
        var i = 0;

        $('.box-numeric-lottery').html('');
        while ( i < numeric_buying){
            var box = '<div class="row has-feedback" id="lot-'+i+'">';
            box += '<label>Vé số #'+(i+1)+'</label>';
            box += '<ul class="box-memeric">';
            for(var j = 0 ; j < 6; j++){
                box += '<li>';
                box += '<input type="text" id="lot-'+i+'-'+j+'" name="lot['+i+']['+j+']" class="form-control" value="">';
                box += '</li>';
            }
            box += '</ul>';
            box += '<small class="help-block" data-fv-validator="notEmpty" data-fv-for="phoneNumber" ' +
                'data-fv-result="NOT_VALIDATED" style="display: none;" id="errLot-'+i+'"></small>';
            box += '</div>';

            $('.box-numeric-lottery').append(box);
            i++;
        }
        $('.box-btn-control-buying-lot').show();
    })

    
    $('#btn-cancel').click(function () {
        var conf = confirm("Bạn muốn huỷ vé số đã chọn?");
        if(conf){
            $('.box-numeric-lottery').html('');
            $('.numeric-buying-lottery').val(-1);
            $('#numeric-needed-lottery').show();
            $('.box-btn-control-buying-lot').hide();
        }
    })

    
    $('#btn-buying-lottery').click(function () {

        var boxNumericLottery = $('.box-numeric-lottery');
        var count_invalid = 0;
        var duplicated = 0;
        $('.box-numeric-lottery input').each(function () {
            if($.trim($(this).val()).length  == 0){
                count_invalid++;
                $(this).addClass('invalid-input-lot');
            }
        });

        $('.has-feedback').removeClass('has-error');
        $('.help-block').hide();
        //checking duplicate lottery
        var numericBuying = $('.numeric-buying-lottery').val();
        var i = 0;

        var listLottery = [];
        while (i < numericBuying){
            var j = i + 1;
            var comparingLot = concatLottery(i);
            listLottery[i] = createArrLottery(i,1);
            while (j < numericBuying){
                var comparedLot = concatLottery(j);
                if(comparingLot == comparedLot && comparedLot != ''){
                    console.log("i: "+i+" = j: "+j);
                    $('#errLot-'+i).html('Vé số này trùng với vé số '+(j+1));
                    $('#errLot-'+i).show();
                    $('#errLot-'+j).html('Vé số này trùng với vé số '+(i+1));
                    $('#errLot-'+j).show();
                    $('#lot-'+i).addClass('has-error');
                    $('#lot-'+j).addClass('has-error');
                    duplicated++;
                }
               j++;
            }
            i++;
        }

        if(count_invalid > 0)
            alert("Vui lòng chọn tất cả các ô số bỏ trống; tô màu !");
        else
            if(duplicated > 0)
                alert("Vui lòng không chọn hai vé số trùng nhau");
            else {
                console.log(JSON.stringify(listLottery));
                var url = '/api/lottery';
                var data = {};
                data['lotteries'] = listLottery;
                data['userId'] = userId;
                data['lotteryTypeId'] = 1;
                saveOrupdateData(url, 'POST', data, showSuccessSellingLottery);
            }
    })

})

function concatLottery(orderNumber) {
    var boxNumericLottery = $('.box-numeric-lottery');
    var lottery= $(".box-numeric-lottery #lot-"+orderNumber+"-0").val();
    lottery+= $(".box-numeric-lottery #lot-"+orderNumber+"-1").val();
    lottery+= $(".box-numeric-lottery #lot-"+orderNumber+"-2").val();
    lottery+= $(".box-numeric-lottery #lot-"+orderNumber+"-3").val();
    lottery+= $(".box-numeric-lottery #lot-"+orderNumber+"-4").val();
    lottery+= $(".box-numeric-lottery #lot-"+orderNumber+"-5").val();
    return lottery;
}

function createArrLottery(orderNumber, lotteryTypeId=1){
    var boxNumericLottery = $('.box-numeric-lottery');
    var lottery= {}
    lottery['coupleOne'] = $(".box-numeric-lottery #lot-"+orderNumber+"-0").val();
    lottery['coupleTwo'] = $(".box-numeric-lottery #lot-"+orderNumber+"-1").val();
    lottery['coupleThree'] = $(".box-numeric-lottery #lot-"+orderNumber+"-2").val();
    lottery['coupleFour'] = $(".box-numeric-lottery #lot-"+orderNumber+"-3").val();
    lottery['coupleFive'] = $(".box-numeric-lottery #lot-"+orderNumber+"-4").val();
    lottery['coupleSix'] = $(".box-numeric-lottery #lot-"+orderNumber+"-5").val();
    lottery['lotteryType'] = {'id': lotteryTypeId};
    return lottery;
}

function showNumericBox(id) {
    $('#'+id).focus();
    $('.box-choosing-number').show();
    pos = $('#'+id).offset();
    var formGroupOffset = $('.selling-lottery .form-group').offset();
    console.log(formGroupOffset);
    var top = pos.top - formGroupOffset.top;
    $('.box-choosing-number').css('top',top + 50);
}

/**
 * Function handles after success created lotteries of user
 * @param data
 */
function showSuccessSellingLottery(data) {
    window.location.href = urlCheckout+"/"+data.id;
}

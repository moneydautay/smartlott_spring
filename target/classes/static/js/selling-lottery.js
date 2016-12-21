/**
 * Created by greenlucky on 12/8/16.
 */
jQuery(document).ready(function ($) {
    var current_lot_id = '';
    var pos = '';
    
    $(document).on('click','.box-numeric-lottery li input', function () {
        current_lot_id = $(this).attr('id');
        $('.box-choosing-number').show();
        pos = $(this).position();
        $('.box-choosing-number').css('top',pos.top + 50);
    })

    $('.box-choosing-number li').click(function () {
        var chooseing_number = $(this).html();
        $('#'+current_lot_id).val(chooseing_number);
        $('.box-choosing-number').hide();
        $('#'+current_lot_id).removeClass('invalid-input-lot');
    })

    $('.numeric-buying-lottery').change(function () {
        $('#numeric-needed-lottery').hide();
        var numeric_buying = $(this).val();
        var i = 0;

        $('.box-numeric-lottery').html('');
        while ( i < numeric_buying){
            var box = '<div class="row" id="lot-'+i+'">';
            box += '<label>Vé số #'+(i+1)+'</label>';
            box += '<ul class="box-memeric">';
            for(var j = 0 ; j < 6; j++){
                box += '<li>';
                box += '<input type="text" id="lot-'+i+'-'+j+'" name="lot[]['+j+']" class="form-control" value="">';
                box += '</li>';
            }
            box += '</ul>';
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
        var count_invalid = 0;
        $('.box-numeric-lottery input').each(function () {
            if($.trim($(this).val()).length  == 0){
                count_invalid++;
                $(this).addClass('invalid-input-lot');
            }
        });
        if(count_invalid > 0)
            alert("Vui lòng chọn tất cả các ô số bỏ trống; tô màu !");
    })
})

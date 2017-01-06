/**
 * Created by Mrs Hoang on 19/12/2016.
 */

/*<div class="item active">
    <img class="first-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="First slide" />
    <div class="container">
    <div class="carousel-caption">
    <h1>Example headline.</h1>
<p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
<p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p>
</div>
</div>
</div>*/
function showSliderImage(data){

    var myCarousel = $('#myCarousel .carousel-inner');
    myCarousel.html('');
    $.each(data, function (i, item) {
        var strHtml = '';
        var active = '';
        if(i==0)
            active = 'active';
        strHtml += '<div class="item '+active+'">';

        strHtml += '<img class="first-slide" src="data:image/gif;base64,'+item.image+'" alt="'+item.title+'" />';
        strHtml += '<div class="container">';

        strHtml += '<div class="carousel-caption">';

        strHtml += '<h1>'+item.title+'</h1>';
        strHtml += '<p>';
        strHtml += item.description;
        strHtml += '</p>';

        strHtml += '<p>';
        strHtml += '<a class="btn btn-lg btn-primary" href="'+item.url+'" role="button">Learn more</a>'
        strHtml += '</p>';

        strHtml += '</div>';

        strHtml += '</div>';

        strHtml += '</div>';
        myCarousel.append(strHtml);
    })
}

function getSliderImages() {
    var url = '/api/featured-slider-image';

    $.ajax({
        type : 'GET',
        contentType : "application/json",
        url : url,
        dataType : 'json',
        timeout : 10000,
        beforeSend: function(xhr){
            // xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            console.log(data);
            showSliderImage(data);

        },
        error : function(e) {
            console.log(e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}

function showNumericJoinedMember(data) {
    $('#joined_member').html(data);
    if(data > 999)
        $('#joined_member').formatNumber({format:"#,###", locale:"us"});

}

function getNumericJoinedMemember(roleId){
    var url = "/api/user/numeric-joined-member/"+roleId;

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url : url,
        dataType: 'json',
        timeout: 10000,
        beforeSend: function (xhr) {

        },
        success: function (data) {
            showNumericJoinedMember(data);
        },
        error: function (e) {
            console.log(e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

jQuery(document).ready(function ($) {

    //load slider
    getSliderImages();

    //load mueric joined memeber
    getNumericJoinedMemember(1);

});

function showCountDownTimerLotteryDialing(data) {

    $("#count-down-time")
        .countdown(data.tempToDate, function(event) {
            $(this).html(
                event.strftime(''
                    + '<li>%D</li>'
                    + '<li>%H</li>'
                    + '<li>%M</li>'
                    + '<li>%S</li>'
                )
            );
        });
}

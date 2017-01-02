/**
 * Created by greenlucky on 1/2/17.
 */
var currentPage = 0;
var totalPages = 0;
function pagination(url, callBack){
    var localUrl =url+'='+currentPage;
    getData(localUrl,callBack);

    //select pagination number page
    selectPageNumber(url, callBack);
}

function selectPageNumber(url, callBack) {
    $('body').on('click','.pagination .el_item', function () {
        if($(this).attr('page') != currentPage) {
            currentPage = $(this).attr('page');
            setActivePagiItem(url, callBack);
        }
    })


    $('body').on('click','.pagination .previous' ,function () {
        if(currentPage > 0){
            currentPage--;
            setActivePagiItem(url, callBack);
        }
    });

    $('body').on('click','.pagination .next', function () {
        if(currentPage < (totalPages - 1)){
            currentPage++;
            setActivePagiItem(url, callBack);
        }
    });
}

/**
 * active pagination background button and load data
 */
function setActivePagiItem(url, callBack) {
    $('.pagination li').removeClass('active');
    $('#el_'+currentPage).addClass('active');
    url+='='+currentPage;
    //get all bonus
    getData(url, callBack);
}

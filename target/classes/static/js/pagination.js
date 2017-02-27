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
    });


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
/**
 * Show pagination
 * @param data
 */
function showPagination(data) {
    //show pagination
    var pagination = $('.pagination');
    pagination.html('');
    var strPrevious = '<li class="previous">';
    strPrevious += ' <a href="#" aria-label="Previous">';
    strPrevious += '<span aria-hidden="true">«</span>';
    strPrevious += '</a>';
    strPrevious += '</li>';
    pagination.append(strPrevious);
    totalPages = data.totalPages;
    /*<![CDATA[*/
    for(var i=0 ; i < totalPages ; i++){
        var strNum = '';
        strNum += '<li id="el_'+i+'" class="el_item" page="'+i+'"><a href="#" >'+(i+1)+'</a></li>';
        pagination.append(strNum);
    }
    /*]]>*/
    var strNext = '<li class="next">';
    strNext += ' <a href="#" aria-label="Next">';
    strNext += '<span aria-hidden="true">»</span>';
    strNext += '</a>';
    strNext += '</li>';
    pagination.append(strNext);
    currentPage = data.number;
    //select active
    $('#el_'+currentPage).addClass('active');
}

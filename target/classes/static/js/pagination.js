/**
 * Created by greenlucky on 1/2/17.
 */
var currentPage = 0;
var totalPages = 0;

/**
 * Filed data to table given by data and columns of table.
 * Defend on the name of fields in the th of head table
 * the table will be field with the name of
 * columns in the data respectively
 *
 * @param data
 * @param urlDetail
 */
function fieldData(data, urlDetail) {

    var dataFields = $('#tableContent .dataFields').find('th[fields]');
    var area = $('#tableContent tbody');
    area.html('');
    $.each(data.content, function (i, item) {
        var _str = '<tr id="row_' + item.id + '">';
        urlDetail += item.id;

        _str += '<td>';
        _str += '<input type="checkbox" class="_id" name="_id" value="' + item.id + '"/>';
        _str += '</td>';

        dataFields.each(function (index, col) {
            var field = $(col).attr('fields');
            var row = '';
            if ((typeof field !== typeof undefined) && field !== false) {

                var fields = field.split('.');

                if (fields.length > 1) {
                    console.log(item);
                        var obj = null;
                        $.each(fields, function (i, fed) {

                            if (i == 0)
                                obj = item[fed];
                            else if (obj != null)
                                obj = obj[fed];
                        })

                    row = (obj != null) ? obj : '';
                }  else row = (item[fields] != null ) ? item[fields] : '';
            }

            _str += '<td>';
            _str += '<a href="' + urlDetail + '" title="edit">' + row + '</a>';
            _str += '</td>';
        });

        _str += '<td>';
        _str += '<a href="' + urlDetail + '" title="edit"><i class="fa fa-pencil-square" aria-hidden="true"></i></a> ';
        _str += '</td>';

        _str += '</tr>';
        area.append(_str);

        showPagination(data);
    });
}

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

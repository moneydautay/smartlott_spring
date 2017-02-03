function doSearchPost(urlPostAll,urlPostSearch, catId = -1, title = null, callBack) {
    //search
    if(catId != -1 || title != null){

        if(catId != -1){
            urlPostSearch += "?catid="+catId;
            if($.trim(title) != null && $.trim(title) != '')
                urlPostSearch += "&title="+title;
        }
        else
            if($.trim(title) != null && $.trim(title) != '')
                urlPostSearch += "?title="+title;

        getData(urlPostSearch, callBack, showErrors);
    }else {
        getData(urlPostAll, callBack);
    }
}

/**
 *
 * @param urlPostAll
 * @param catId
 * @param title
 */
function doSubmitPost(urlPostAll, catId = -1, title = null) {
    console.log(title);
    if(catId != -1){
        urlPostAll+="?catid="+catId;
        if($.trim(title) != null && $.trim(title) != '')
            urlPostAll+="&title="+title;
    }else {
        if(title != null && $.trim(title) != '')
            urlPostAll+="?title="+title;
    }
    window.location.href = urlPostAll;
}

/**
 *
 * @param urlAllMember
 * @param urlMemberSearch
 * @param status
 * @param query
 * @param callBack
 */
function doSearchMember(urlAllMember,urlMemberSearch, status = -1, query = null, callBack) {
    console.log(urlAllMember);
    getData(urlAllMember,callBack,showErrors);
}
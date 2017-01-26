/**
 * Created by Mrs Hoang on 20/12/2016.
 */

/**
 * Show data country retrieve form function getProvince to select of province
 *
 * @param selectId given by id select province
 * @param selectCountryId given by id select country or null if not exist select of country
 * @param defaultCountryId
 */
function showProvinceToSelect(data, selectId) {

    var select = $('#'+selectId);
    select.html('');
    select.append('<option value="-1">Chọn tỉnh/thành phố</option>')
    $.each(data, function (index, country) {
        var strOp = '';
        strOp += '<option value="'+country.id+'">';
        strOp+= country.name;
        strOp += '</option>';
        select.append(strOp);
    });
}

/**
 * Using ajax to retrieve provinces
 * @param selectId id of select will be render data to
 * @param defaultCountryId country id will be use if selectCountryId is null
 * @param selectCountryId id of country will be select by country selection
 */
function getProvinces(selectId, defaultCountryId, selectCountryId=null) {

    var countryId = (selectCountryId!=null)? $('#'+selectCountryId).val() : defaultCountryId;
    var url = '/api/province/'+countryId;

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        timeout: 10000,
        beforeSend: function (xhr) {

        },
        success: function (data) {
            showProvinceToSelect(data, selectId);
        },
        error: function (e) {
            console.log(e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

function showDataToForm(data) {
    $.each(data, function(name, value) {

        if ($.isPlainObject(value))
            objHandle(name, value);
        else if ($.isArray(value))
            arrHandle(name, value);
        else if ($('#' + name).attr('type') == "password")
            $('#' + name).val('');
        else if ($('#' + name).attr('type') == "radio" || $('#' + name).attr('type') == "checkbox")
            $("input[name='" + name + "'][value='" + value + "']").prop(
                "checked", true);
        else
            $('#' + name).val(value);
    })
}

function arrHandle(name, value) {
    $.each(value, function(index, item) {
        var val = item[Object.keys(item)[0]];
        $("input[name='" + name + "'][value='" + val + "']").prop("checked",
            true);
    })

}

function objHandle(name, value) {

    var val = value[Object.keys(value)[0]];

    if(name == "user" || name == "customer"){
        var firstName = (value.firstName != null) ? value.firstName : value.first_name;
        var lastName = (value.lastName != null) ? value.lastName : value.last_name;
        var na = firstName + " " + lastName;
        $('#' + name).val(na);
    }else if($('#' + name).attr('type') == "text"){
        if(value.name != null)
            $('#' + name).val(value.name);
        else if(value.title != null)
            $('#' + name).val(value.title);
    }
    else
        $('#' + name).val(val);
}


/**
 * Get address given by userid
 * @param userId
 * @param callBack function will be called after retrieved data successfully
 */
function getAddress(userId, callBack = null) {

    var url = '/api/address/'+userId;

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        timeout: 10000,
        success: function(data) {
            if(callBack != null)
                showAddress(data);
        },
        error: function(e) {
            console.log(e);
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}

/**
 * Retrieve data given by url
 * @param url rest control to retrieve data
 * @param callBack function will be call after retrieve data successfully
 * @param errorCallBack function will be call after function has error
 */
function getData(url, callBack=null, errorCallBack=null){
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        timeout: 10000,
        success: function (data) {
            if(callBack!=null)
                callBack(data);
        },
        error: function (e) {
            console.log(e);
            if(errorCallBack!=null)
                errorCallBack(JSON.parse(e.responseText));
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

/**
 * Get user by using ajax/json and username given by user
 * @param userName
 * @param frmId form id will be using to bind data
 */
function getUser(userName, nameFunction=null) {

    var url = '/api/user/'+userName;

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        timeout: 10000,
        beforeSend: function (xhr) {

        },
        success: function (data) {
            if(nameFunction!=null)
                nameFunction(data);
            else
            showDataToForm(data);
        },
        error: function (e) {
            console.log(e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}


function changePassword(username,frmId=null) {

    var header = $('meta[name="_csrf_header"]').attr('content');
    var token = $('meta[name="_csrf"]').attr('content');

    var url = '/api/user/change-password';
    if(frmId!=null)
        url = $('#'+frmId).attr('action');
    var data = {};
    data['username']=username;
    data['currentPassword']=$('#currentPassword').val();
    data['newPassword']=$('#newPassword').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        data: JSON.stringify(data),
        timeout: 10000,
        beforeSend: function (xhr) {
            if(typeof ($.trim(header)) === 'undefined')
                xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);
            var messageArea = $('#messageArea');
            messageArea.html('');
            messageArea.append(showMessage(data[0].message+' <span id="timeCountDown">1</span>s', 'alert-success', true));
            //clear all notified message
            $('#frmPassword').data('formValidation').resetForm();
            //auto direct to dashboard
            var timeCountDown = 1;
            setInterval(function () {
                $('#timeCountDown').html(timeCountDown);
                if(timeCountDown==0)
                    window.location.href = "/logout";
                timeCountDown--;
            },1000);
        },
        error: function (e) {
            console.log(e);
            showErrors(JSON.parse(e.responseText));
            $('#frmPassword')
                .data('formValidation')
                .updateStatus('currentPassword','INVALID');
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}


/**
 * Update user in dashboard
 */
function updateUser() {

    var header = $('meta[name="_csrf_header"]').attr('content');
    var token = $('meta[name="_csrf"]').attr('content');

    var data = {};
    var address = {};
    var province = {};
    var id = $('#id').val();

    data['id'] = id;
    data['username']    = $('#username').val();
    data['email']       = $('#email').val();
    data['phoneNumber'] = $('#phoneNumber').val();
    data['firstName']   = $('#firstName').val();
    data['lastName']    = $('#lastName').val();
    data['birthday']    = $('#birthday').val();
    data['sex']         = $('#sex:checked').val();

    //Province
    province['id'] = $('#province').val();
    province['name'] = $('#province option:selected').text();

    //Address
    address['id']       = $('#addressId').val();
    address['address']  = $('#address').val();
    address['city']     = $('#city').val();
    address['province'] = province;

    data['addresses']   = address;

    var url = $('#frmProfile').attr('action')+'/'+id;

    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        data: JSON.stringify(data),
        timeout: 10000,
        beforeSend: function (xhr) {
            if(typeof($.trim(header)) === 'undefined')
                xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            var messageArea = $('#messageArea');
            messageArea.html('');
            messageArea.append(showMessage("Cập nhật thông tin thành công", 'alert-success', true));
            //clear all notified message
            $('#frmProfile').data('formValidation').resetForm();
        },
        error: function(e) {
            showErrors(JSON.parse(e.responseText));
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}


function uploadDoc(frmId, nameFunction=null){
    var header = $('meta[name="_csrf_header"]').attr('content');
    var token = $('meta[name="_csrf"]').attr('content');
    var url = $(frmId).attr('action');
    var data = new FormData();
    data.append("tempFile", frmId[1].files[0]);
    data.append("docType", frmId[4].value);
    data.append("username", frmId[5].value);

    $.ajax({
        type: 'POST',
        contentType: false,
        url: url,
        data: data,
        enctype : 'multipart/form-data',
        dataType : false,
        cache : false,
        processData : false,
        timeout : 15000,
        beforeSend: function (xhr) {
            if(typeof($.trim(header)) === 'undefined')
                xhr.setRequestHeader(header, token);
        },
        success: function(data) {

            var messageArea = $('#messageArea');
            messageArea.html('');
            messageArea.append(showMessage("Cập nhật thông tin thành công", 'alert-success', true));
            //clear all notified message
            $('.frmDoc').data('formValidation').resetForm();
            if(nameFunction != null)
                nameFunction(data);
        },
        error: function(e) {
            showErrors(JSON.parse(e.responseText));
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}

/**
 * Create or Update data given by url and data
 * @param url will be sent data to
 * @param data
 * @param callBack will be call back after save or update successfully
 * @param errorCallBack will be call back if error
 */
function saveOrupdateData(url, type = 'POST', data, callBack=null, errorCallBack=null){

    var header = $('meta[name="_csrf_header"]').attr('content');
    var token = $('meta[name="_csrf"]').attr('content');

    $.ajax({
        type: type,
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        data: JSON.stringify(data),
        timeout: 10000,
        beforeSend: function (xhr) {
            if(typeof($.trim(header)) === 'undefined')
                xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(callBack != null)
                callBack(data);
            else
                showSuccess(JSON.parse(e.responseText));
        },
        error: function(e) {
            if(errorCallBack!=null)
                errorCallBack(JSON.parse(e.responseText));
            else
                showErrors(JSON.parse(e.responseText));
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}

/**
 * Create or Update data given by url and data
 * @param url will be sent data to
 * @param data
 * @param callBack will be call back after save or update successfully
 * @param errorCallBack will be call back if error
 */
function saveOrUpdateDataByGet(url, callBack=null, errorCallBack=null){

    var header = $('meta[name="_csrf_header"]').attr('content');
    var token = $('meta[name="_csrf"]').attr('content');

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        timeout: 10000,
        success: function(data) {
            if(callBack != null)
                callBack(data);
        },
        error: function(e) {
            if(errorCallBack!=null)
                errorCallBack(JSON.parse(e.responseText));
            else
                showErrors(JSON.parse(e.responseText));
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}



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
            console.log(data);
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
 * Get user by using ajax/json and username given by user
 * @param userName
 * @param frmId form id will be using to bind data
 */
function getUser(userName) {

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
            console.log(data);
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

        },
        success: function (data) {
            console.log(data);
            var messageArea = $('#messageArea');
            messageArea.html('');
            messageArea.append(showMessage('Thay đổi mật khẩu thành công. Vui lòng đăng nhập lại <span id="timeCountDown">1</span>s', 'alert-success', true));
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
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}



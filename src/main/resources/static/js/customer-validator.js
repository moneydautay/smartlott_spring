/**
 * Created by Mrs Hoang on 20/12/2016.
 */
$( document ).ready( main );

function main() {

    $('.btn-collapse').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var $collapse = $this.closest('.collapse-group').find('.collapse');
        $collapse.collapse('toggle');
    });

    /* Signup form validation */
    $('#signupForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'Email không được bỏ trống'
                    },
                    emailAddress: {
                        message: 'Vui lòng điền Email theo đúng định dạng email@xxx.xxx'
                    }
                }
            },
            username: {
                validators: {
                    notEmpty: {
                        message: 'Tên đăng nhập không được bỏ trống'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'Mật khẩu không được bỏ trống'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'Nhập lại mật khẩu không chính xác'
                    },
                    stringLength: {
                        message: 'Password must be greater or equal than 6 characters',
                        min: function (value, validator, $field) {
                            return 6 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty:{
                        field: 'confirmPassword',
                        message: 'Nhập lại mật khẩu không được bỏ trống'
                    },
                    identical: {
                        field: 'password',
                        message: 'Nhập lại mật khẩu không chính xác'
                    }
                }
            },

            phoneNumber: {
                validators: {
                    notEmpty: {
                        message: 'Số điện thoại không được bỏ trống'
                    },
                    phone: {
                        country: 'country',
                        message: 'Số điện thoại %s phải hợp lệ'
                    }
                }
            }
        }
    });

    /* Signup form validation */
    $('#frmProfile').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'Email không được bỏ trống'
                    },
                    emailAddress: {
                        message: 'Vui lòng điền Email theo đúng định dạng email@xxx.xxx'
                    }
                }
            },
            username: {
                validators: {
                    notEmpty: {
                        message: 'Tên đăng nhập không được bỏ trống'
                    }
                }
            },
            phoneNumber: {
                validators: {
                    notEmpty: {
                        message: 'Số điện thoại không được bỏ trống'
                    },
                    phone: {
                        country: 'country',
                        message: 'Số điện thoại %s phải hợp lệ'
                    }
                }
            },
            firstName:{
                validators:{
                    notEmpty:{
                        message:'Tên không được bỏ trống'
                    },
                    stringLength: {
                        message: 'Tên không được ít hơn 2 ký tự',
                        min: function (value, validator, $field) {
                            return 2 - (value.match(/\r/g) || []).length;
                        },
                    },
                    stringLength: {
                        message: 'Tên không được vượt quá hơn 20 ký tự',
                        max: function (value, validator, $field) {
                            return 20 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },
            lastName:{
                validators:{
                    notEmpty:{
                        message: 'Họ và tên đệm không được bỏ trống'
                    },
                    stringLength: {
                        message: 'Họ và tên đệm không được ít hơn 2 ký tự',
                        min: function (value, validator, $field) {
                            return 2 - (value.match(/\r/g) || []).length;
                        },
                    },
                    stringLength: {
                        message: 'Họ và tên đệm không được vượt quá hơn 40 ký tự',
                        max: function (value, validator, $field) {
                            return 40 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },
            birthday:{
                validators:{
                    notEmpty:{
                        message: 'Ngày sinh không được bỏ trống'
                    }
                }
            },
            address:{
                validators:{
                    notEmpty:{
                        message: 'Địa chỉ không được bỏ trống'
                    },
                    stringLength: {
                        message: 'Địa chỉ không được vượt quá 150 ký tự',
                        max: function (value, validator, $field) {
                            return 150 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },
            ward:{
                validators:{
                    notEmpty:{
                        message: 'Quận/Huyện không được bỏ trống'
                    },
                    stringLength: {
                        message: 'Quận/Huyện không được vượt quá 150 ký tự',
                        max: function (value, validator, $field) {
                            return 150 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },
            province:{
                validators: {
                    greaterThan: {
                        value: 0,
                        message: 'Tỉnh/Thành phố không được bỏ trống'
                    }
                }
            }
        }
    });



}
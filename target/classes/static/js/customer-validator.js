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
}
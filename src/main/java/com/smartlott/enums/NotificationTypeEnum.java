package com.smartlott.enums;

import com.smartlott.backend.api.NumberAccountRestController;
import com.smartlott.backend.api.UserRestController;
import com.smartlott.web.controllers.DashboardController;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
public enum NotificationTypeEnum {
    General(1,"Thông tin chung", "" , DashboardController.PROFILE_GENERAL_URL,true, UserRestController.API_USER_REST_URL),
    Pssword(2,"Mật khẩu","", DashboardController.PROFILE_PASSWORD_URL,true, UserRestController.API_USER_PASSWORD_REST_URL),
    AddressValidate(3,"Xác minh địa chỉ", "", DashboardController.PROFILE_DOC_URL,true, UserRestController.API_USER_UPLOADOC_REST_URL),
    NumberAccount(4,"Số tài khoản","", DashboardController.PROFILE_ACCOUNT_URL,true, NumberAccountRestController.NUMBER_ACCOUNT_URL),
    Orther(5,"Thông báo khác","", "",false, "");

    private int id;

    private String description;

    private String name;

    private String url;

    private boolean required;

    private String apiUrl;

    NotificationTypeEnum(int id, String name, String description,String url, boolean required, String apiUrl) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.required = required;
        this.apiUrl = apiUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRequired() {
        return required;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getDescription() {
        return description;
    }
}

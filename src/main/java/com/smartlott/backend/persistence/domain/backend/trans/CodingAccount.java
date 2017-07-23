package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;

import javax.persistence.Entity;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class CodingAccount extends BaseModel{

    private String name;

    private String code;

    private SysAccNum sysAccNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SysAccNum getSysAccNum() {
        return sysAccNum;
    }

    public void setSysAccNum(SysAccNum sysAccNum) {
        this.sysAccNum = sysAccNum;
    }

    @Override
    public String toString() {
        return "CodingAccount{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sysAccNum=" + sysAccNum +
                '}';
    }
}

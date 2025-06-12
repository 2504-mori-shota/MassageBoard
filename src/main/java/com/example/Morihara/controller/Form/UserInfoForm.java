package com.example.Morihara.controller.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoForm {
    private int id;
    private String account;
    private String name;
    private int isStopped;
    private int branchId;
    private int departmentId;
    private String branchName;
    private String departmentName;

    public UserInfoForm(int id, String account, String name, int isStopped,int branchId,int departmentId,String branchName, String departmentName){
        this.id = id;
        this.account = account;
        this.name = name;
        this.isStopped = isStopped;
        this.branchId = branchId;
        this.departmentId = departmentId;
        this.branchName = branchName;
        this.departmentName = departmentName;
    }
}

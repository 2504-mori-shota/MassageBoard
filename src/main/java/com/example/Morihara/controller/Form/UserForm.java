package com.example.Morihara.controller.Form;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;

    @NotBlank(message = "アカウントを入力してください")
    @Pattern(regexp = "^[^　]*$", message = "アカウントを入力してください")
    private String account;

    @NotBlank(message = "パスワードを入力してください")
    @Pattern(regexp = "^[^　]*$", message = "パスワードを入力してください")
    private String password;

    @Transient // DBにマッピングしない
    private String passwordConfirm;

    private String name;

    private Integer branchId;

    private Integer  departmentId;

    private int isStopped;

    private Date createdDate;
    private Date updatedDate;


}

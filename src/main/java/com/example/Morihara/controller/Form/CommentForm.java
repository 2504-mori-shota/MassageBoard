package com.example.Morihara.controller.Form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CommentForm {


    private int id;

    @NotBlank(message = "メッセージを入力してください")
    @Size(max = 501, message = "500文字以内で入力してください")
    private String text;


    private  int userId;


    private  int messageId;


}

package com.example.Morihara.controller.Form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CommentForm {


    private int id;


    private String text;


    private  int userId;


    private  int messageId;


}

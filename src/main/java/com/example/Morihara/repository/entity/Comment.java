package com.example.Morihara.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")

@Getter
@Setter

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String text;

    @Column(name ="user_Id")
    private  int userId;

    @Column(name ="message_Id")
    private  int messageId;

    @Column(insertable = false, updatable = false)
    private Date createdDate;
    @Column(insertable = false)
    private Date updatedDate;


}

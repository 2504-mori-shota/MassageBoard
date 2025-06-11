package com.example.Morihara.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "messages")
@Data

@Getter
@Setter
public class Message {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private  String title;

    @Column(name = "text")
    private  String text;

    @Column(name = "category")
    private String category;

    @Column(name = "user_id")
    private int userId;

    @Column(insertable = false, updatable = false)
    private Date createdDate;
    @Column(insertable = false)
    private Date updatedDate;
}

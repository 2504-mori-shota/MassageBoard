package com.example.Morihara.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "departments")
@Data

@Getter
@Setter
public class Department {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String departmentName;

    @Column(insertable = false, updatable = false)
    private Date createdDate;
    @Column(insertable = false)
    private Date updatedDate;

}

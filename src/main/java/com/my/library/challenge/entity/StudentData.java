package com.my.library.challenge.entity;

import lombok.Data;
import javax.persistence.*;


@Entity
@Table(name = "student")
@Data
public class StudentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    @Column(name = "class_name")
    private String className;

    private Integer fine;

    private Integer limit;
}

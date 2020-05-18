package com.example.demo.entity;

import javax.persistence.*;

//@Entity
//@Table(name = "user_table", catalog = "main.db")
public class User {

    public  int Id;
    public String UserId;
    public  String Username;
    public  String Password;
    public  String Sex;
    public  String Birth;

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)


    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", UserId='" + UserId + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Birth='" + Birth + '\'' +
                '}';
    }
}

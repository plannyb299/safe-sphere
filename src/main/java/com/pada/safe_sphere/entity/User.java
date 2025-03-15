package com.pada.safe_sphere.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String userId;
    private String password;
    private String nationalId;
    private String firstname;
    private String surname;
}

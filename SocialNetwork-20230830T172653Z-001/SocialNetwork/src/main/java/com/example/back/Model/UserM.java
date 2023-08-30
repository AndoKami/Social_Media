package com.example.back.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "account")
public class UserM {
    @Id
    private Long id_user;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private Gender gender;

    private Date birthday;
}
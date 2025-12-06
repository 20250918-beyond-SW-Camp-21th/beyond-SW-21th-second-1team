package com.valetparker.chagok.user.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @Column(name = "user_no", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "nickname", unique = true ,nullable = false)
    private String nickname;

    @Column(name = "car_number", unique = true, nullable = false)
    private String carNumber;
}

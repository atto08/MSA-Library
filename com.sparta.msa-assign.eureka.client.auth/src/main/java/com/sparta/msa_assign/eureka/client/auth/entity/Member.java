package com.sparta.msa_assign.eureka.client.auth.entity;

import com.sparta.msa_assign.eureka.client.auth.dto.request.SignupDto;
import com.sparta.msa_assign.eureka.client.auth.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UserRoleEnum role;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;


    public Member(SignupDto signupDto, String encodedPassword, UserRoleEnum role) {
        this.username = signupDto.getUsername();
        this.password = encodedPassword;
        this.email = signupDto.getEmail();
        this.role = role;
        this.firstName = signupDto.getFirstName();
        this.lastName = signupDto.getLastName();
        this.phoneNumber = signupDto.getPhoneNumber();
        this.address = signupDto.getAddress();
    }
}

package com.sparta.msa_assign.eureka.client.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private boolean admin = false;
    private String adminToken = "";
}

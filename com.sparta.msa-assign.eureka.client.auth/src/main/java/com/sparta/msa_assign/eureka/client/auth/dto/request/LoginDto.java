package com.sparta.msa_assign.eureka.client.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String username;
    private String password;
}

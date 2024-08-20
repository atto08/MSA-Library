package com.sparta.msa_assign.eureka.client.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDto {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}

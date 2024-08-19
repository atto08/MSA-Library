package com.sparta.msa_assign.eureka.client.auth.dto.response;

import com.sparta.msa_assign.eureka.client.auth.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String username;
    private String email;
    private UserRoleEnum role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}

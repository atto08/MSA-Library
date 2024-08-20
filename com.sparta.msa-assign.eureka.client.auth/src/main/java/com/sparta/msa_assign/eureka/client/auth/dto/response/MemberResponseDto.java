package com.sparta.msa_assign.eureka.client.auth.dto.response;

import com.sparta.msa_assign.eureka.client.auth.entity.Member;
import com.sparta.msa_assign.eureka.client.auth.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberResponseDto(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.phoneNumber = member.getPhoneNumber();
        this.address = member.getAddress();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
    }
}

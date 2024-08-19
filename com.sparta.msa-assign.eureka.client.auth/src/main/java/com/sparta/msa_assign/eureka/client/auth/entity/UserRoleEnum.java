package com.sparta.msa_assign.eureka.client.auth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
    MEMBER(Authority.MEMBER),
    MASTER(Authority.MASTER);

    private final String authority;

    public static class Authority {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String MASTER = "ROLE_MASTER";
    }
}

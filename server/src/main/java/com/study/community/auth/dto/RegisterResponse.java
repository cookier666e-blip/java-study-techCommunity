package com.study.community.auth.dto;

public record RegisterResponse(
        Long id,
        String username,
        String email,
        String nickname
) {
}


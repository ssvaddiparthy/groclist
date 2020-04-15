package com.groclist.groclistapp.dto;

public class AuthResponseDto {

    private String jwt;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

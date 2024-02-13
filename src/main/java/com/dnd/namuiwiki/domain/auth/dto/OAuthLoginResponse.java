package com.dnd.namuiwiki.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthLoginResponse {

    private final String accessToken;
    private final String refreshToken;

}
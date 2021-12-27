package kr.flab.movieon.account.application.payload;

import lombok.Data;

@Data
public class AccountTokenResponse {

    private final String accessToken;
    private final String refreshToken;
}

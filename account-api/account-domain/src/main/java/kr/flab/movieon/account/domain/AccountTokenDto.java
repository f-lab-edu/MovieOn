package kr.flab.movieon.account.domain;

import lombok.Data;

@Data
public final class AccountTokenDto {

    private final String accessToken;
    private final String refreshToken;
}

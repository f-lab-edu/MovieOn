package kr.flab.movieon.account.application.payload;

import lombok.Data;

@Data
public final class RegisterAccountResponse {

    private final String userId;

    private final String email;
}

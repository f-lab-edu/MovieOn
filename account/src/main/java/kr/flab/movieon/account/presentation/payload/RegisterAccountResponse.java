package kr.flab.movieon.account.presentation.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class RegisterAccountResponse {

    private String userId;

    private String email;
}

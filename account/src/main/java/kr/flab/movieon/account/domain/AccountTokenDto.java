package kr.flab.movieon.account.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class AccountTokenDto {

    private String accessToken;
    private String refreshToken;
    private String username;
    private String email;
    private List<String> roles;

}

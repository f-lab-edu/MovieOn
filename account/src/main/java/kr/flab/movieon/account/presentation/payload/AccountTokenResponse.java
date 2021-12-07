package kr.flab.movieon.account.presentation.payload;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String userId;
    private String email;
    private List<String> roles;
}

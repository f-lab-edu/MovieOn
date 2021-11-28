package kr.flab.movieon.account.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class AccountDto {

    private String accessToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}

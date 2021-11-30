package kr.flab.movieon.account.presentation.payload;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtResponse {

    @Builder.Default
    private String type = "Bearer";
    private String token;
    private String username;
    private String email;
    private List<String> roles;
}

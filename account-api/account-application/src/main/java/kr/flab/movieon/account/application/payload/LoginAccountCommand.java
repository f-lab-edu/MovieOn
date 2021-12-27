package kr.flab.movieon.account.application.payload;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginAccountCommand {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;
}

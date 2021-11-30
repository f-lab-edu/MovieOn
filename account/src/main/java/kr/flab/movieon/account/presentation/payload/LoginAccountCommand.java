package kr.flab.movieon.account.presentation.payload;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginAccountCommand {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

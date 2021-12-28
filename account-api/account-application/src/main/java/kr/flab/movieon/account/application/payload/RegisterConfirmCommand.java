package kr.flab.movieon.account.application.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class RegisterConfirmCommand {

    @NotBlank
    private String token;

    @Email
    @NotBlank
    private String email;
}

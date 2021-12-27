package kr.flab.movieon.account.application.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterAccountCommand {

    @NotBlank
    @Size(min = 3, max = 20)
    private String userId;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z1-9~!@#$%^&*()+|=]{6,40}$")
    private String password;
}

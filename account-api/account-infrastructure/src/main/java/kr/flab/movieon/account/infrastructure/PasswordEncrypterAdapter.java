package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.PasswordEncrypter;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordEncrypterAdapter implements PasswordEncrypter {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncrypterAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

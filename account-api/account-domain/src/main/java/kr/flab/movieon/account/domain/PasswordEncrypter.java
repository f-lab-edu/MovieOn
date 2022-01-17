package kr.flab.movieon.account.domain;

public interface PasswordEncrypter {

    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}

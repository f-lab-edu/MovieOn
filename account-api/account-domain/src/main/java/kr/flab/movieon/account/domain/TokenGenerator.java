package kr.flab.movieon.account.domain;

public interface TokenGenerator {

    Tokens generate(Account account);

}

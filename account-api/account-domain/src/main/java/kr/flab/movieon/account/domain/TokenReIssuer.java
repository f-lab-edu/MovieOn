package kr.flab.movieon.account.domain;

public interface TokenReIssuer {

    Tokens reIssuance(String payload);
}

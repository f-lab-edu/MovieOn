package kr.flab.movieon.account.infrastructure.jwt;

public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}

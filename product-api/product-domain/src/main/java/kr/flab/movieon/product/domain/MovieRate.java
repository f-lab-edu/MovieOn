package kr.flab.movieon.product.domain;

public enum MovieRate {
    GENERAL(0, "전체 이용가"),
    PARENTAL_GUIDANCE_12(12, "12세 이용가"),
    PARENTAL_GUIDANCE_15(15, "15세 이용가"),
    NO_CHILDREN(19, "청소년 관람불가");

    private final int age;
    private final String description;

    MovieRate(int age, String description) {
        this.age = age;
        this.description = description;
    }
}

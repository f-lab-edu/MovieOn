package kr.flab.movieon.product.domain;

import java.time.Duration;
import java.time.Year;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class ProductContentsDetail {

    private final Rate rate;
    private final Year release;
    private final Duration runningTime;
    private final String director;
    private final String actors;

    @Builder
    public ProductContentsDetail(Rate rate, Year release, Duration runningTime, String director,
        String actors) {
        this.rate = rate;
        this.release = release;
        this.runningTime = runningTime;
        this.director = director;
        this.actors = actors;
    }

    public enum Rate {
        GENERAL(0, "전체 이용가"),
        PARENTAL_GUIDANCE_12(12, "12세 이용가"),
        PARENTAL_GUIDANCE_15(15, "15세 이용가"),
        NO_CHILDREN(19, "청소년 관람불가");

        private final int age;
        private final String description;

        Rate(int age, String description) {
            this.age = age;
            this.description = description;
        }
    }
}

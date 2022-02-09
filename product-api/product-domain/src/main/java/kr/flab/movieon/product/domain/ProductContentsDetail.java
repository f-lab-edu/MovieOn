package kr.flab.movieon.product.domain;

import java.time.Duration;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class ProductContentsDetail {

    private Rate rate;
    private Year release;
    private Duration runningTime;
    private String director;
    private String actors;
    @ElementCollection
    private List<String> images = new ArrayList<>();

    protected ProductContentsDetail() {

    }

    @Builder
    public ProductContentsDetail(Rate rate, Year release, Duration runningTime, String director,
        String actors, List<String> images) {
        this.rate = rate;
        this.release = release;
        this.runningTime = runningTime;
        this.director = director;
        this.actors = actors;
        this.images = images;
    }

    @Getter
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

        public static Rate findByRate(String rateDescription) {
            return Arrays.stream(values())
                .filter(r -> r.getDescription().equals(rateDescription))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("일치하는 이용 등급을 찾을 수 없습니다."));
        }
    }
}

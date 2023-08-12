package kr.flab.movieon.product.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ProductContentsDetail {

    @Enumerated(EnumType.STRING)
    private Rate rate;
    private LocalDate releasedAt; // TODO 리팩토링 값 객체(개봉년도, 입고날짜)
    private Integer runningTime;
    private String director;
    private String actors;
    private String availableDevices;
    private String providedQuality;
    private boolean drm;
    @ElementCollection
    private List<String> images = new ArrayList<>();

    protected ProductContentsDetail() {

    }

    public ProductContentsDetail(Rate rate, LocalDate releasedAt, Integer runningTime,
        String director, String actors, String availableDevices, String providedQuality,
        boolean drm, List<String> images) {
        this.rate = rate;
        this.releasedAt = releasedAt;
        this.runningTime = runningTime;
        this.director = director;
        this.actors = actors;
        this.availableDevices = availableDevices;
        this.providedQuality = providedQuality;
        this.drm = drm;
        this.images = images;
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

        public static Rate findByRate(String rateDescription) {
            return Arrays.stream(values())
                .filter(r -> r.getDescription().equals(rateDescription))
                .findFirst()
                .orElseThrow(() -> new NotMatchedRateException("일치하는 이용 등급을 찾을 수 없습니다."));
        }

        public int getAge() {
            return age;
        }

        public String getDescription() {
            return description;
        }
    }

    public Rate getRate() {
        return rate;
    }

    public LocalDate getReleasedAt() {
        return releasedAt;
    }

    public Integer getRunningTime() {
        return runningTime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getAvailableDevices() {
        return availableDevices;
    }

    public String getProvidedQuality() {
        return providedQuality;
    }

    public boolean isDrm() {
        return drm;
    }

    public List<String> getImages() {
        return images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductContentsDetail that = (ProductContentsDetail) o;
        return drm == that.drm && rate == that.rate && Objects.equals(releasedAt, that.releasedAt)
            && Objects.equals(runningTime, that.runningTime) && Objects.equals(
            director, that.director) && Objects.equals(actors, that.actors)
            && Objects.equals(availableDevices, that.availableDevices)
            && Objects.equals(providedQuality, that.providedQuality)
            && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, releasedAt, runningTime, director, actors, availableDevices,
            providedQuality, drm, images);
    }
}

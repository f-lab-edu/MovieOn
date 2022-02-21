package kr.flab.movieon.product.domain;

import java.time.Duration;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import kr.flab.movieon.product.domain.ProductContentsDetail.Rate;

public final class ProductContentsDetailBuilder {

    private Rate rate;
    private Year release;
    private Duration runningTime;
    private String director;
    private String actors;
    private List<String> images = new ArrayList<>();

    private ProductContentsDetailBuilder() {

    }

    public static ProductContentsDetailBuilder builder() {
        return new ProductContentsDetailBuilder();
    }

    public ProductContentsDetailBuilder rate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public ProductContentsDetailBuilder release(Year release) {
        this.release = release;
        return this;
    }

    public ProductContentsDetailBuilder runningTime(Duration runningTime) {
        this.runningTime = runningTime;
        return this;
    }

    public ProductContentsDetailBuilder director(String director) {
        this.director = director;
        return this;
    }

    public ProductContentsDetailBuilder actors(String actors) {
        this.actors = actors;
        return this;
    }

    public ProductContentsDetailBuilder images(List<String> images) {
        this.images = images;
        return this;
    }

    public ProductContentsDetail build() {
        return new ProductContentsDetail(rate, release, runningTime,
            director, actors, images);
    }
}

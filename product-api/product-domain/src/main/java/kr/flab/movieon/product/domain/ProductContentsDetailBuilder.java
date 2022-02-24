package kr.flab.movieon.product.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import kr.flab.movieon.product.domain.ProductContentsDetail.Rate;

public final class ProductContentsDetailBuilder {

    private Rate rate;
    private LocalDate release;
    private Duration runningTime;
    private String director;
    private String actors;
    private String availableDevices;
    private String providedQuality;
    private boolean drm;
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

    public ProductContentsDetailBuilder release(LocalDate release) {
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

    public ProductContentsDetailBuilder availableDevices(String availableDevices) {
        this.availableDevices = availableDevices;
        return this;
    }

    public ProductContentsDetailBuilder providedQuality(String providedQuality) {
        this.providedQuality = providedQuality;
        return this;
    }

    public ProductContentsDetailBuilder drm(boolean drm) {
        this.drm = drm;
        return this;
    }

    public ProductContentsDetailBuilder images(List<String> images) {
        this.images = images;
        return this;
    }

    public ProductContentsDetail build() {
        return new ProductContentsDetail(rate, release, runningTime, director, actors,
            availableDevices, providedQuality, drm, images);
    }
}

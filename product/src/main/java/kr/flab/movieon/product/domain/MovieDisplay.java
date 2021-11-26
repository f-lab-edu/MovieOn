package kr.flab.movieon.product.domain;

import java.time.Duration;
import java.time.Year;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class MovieDisplay {

    private final String title;
    private final String description;
    private final Year release;
    private final Duration runningTime;
    private final String director;
    private final String actors;
    private final MovieRate movieRate;

    @Builder
    public MovieDisplay(String title, String description, Year release, Duration runningTime,
        String director, String actors, MovieRate movieRate) {
        this.title = title;
        this.description = description;
        this.release = release;
        this.runningTime = runningTime;
        this.director = director;
        this.actors = actors;
        this.movieRate = movieRate;
    }
}

package kr.flab.movieon.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Days {

    private final int value;

    public Days(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

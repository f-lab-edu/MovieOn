package kr.flab.movieon.purchase.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "available")
public class PurchaseHistory {

    private final Boolean available;
    private final LocalDateTime purchased;
    private final LocalDateTime expired;

    private PurchaseHistory(boolean available, LocalDateTime purchased,
        LocalDateTime expired) {
        this.available = available;
        this.purchased = purchased;
        this.expired = expired;
    }

    public static PurchaseHistory create(int days) {
        LocalDateTime purchased = LocalDateTime.now();
        return new PurchaseHistory(true, purchased, purchased.plusDays(days));
    }
}

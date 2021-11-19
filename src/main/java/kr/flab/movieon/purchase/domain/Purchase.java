package kr.flab.movieon.purchase.domain;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public class Purchase {

    public enum PurchaseStatus {
        PENDING, SUCCESS, FAILED
    }

    public enum PurchaseType {
        RENTAL, PURCHASE
    }

    private Long id;
    private Long productId;
    private Long customerId;
    private BigDecimal price;
    private PurchaseStatus status;
    private PurchaseType type;
    private PurchaseHistory history;

    private Purchase(Long productId, Long customerId, BigDecimal price, PurchaseType type) {
        this.productId = productId;
        this.customerId = customerId;
        this.price = price;
        this.status = PurchaseStatus.PENDING;
        this.type = type;
    }

    public static Purchase create(Long productId, Long customerId, BigDecimal price,
        String type) {
        return new Purchase(productId, customerId, price,
            PurchaseType.valueOf(type));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void proceed(int availableDays) {
        this.status = PurchaseStatus.SUCCESS;
        this.history = PurchaseHistory.create(availableDays);
    }

    public Long getId() {
        return id;
    }

    public PurchaseHistory getHistory() {
        return this.history;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public PurchaseType getType() {
        return type;
    }
}

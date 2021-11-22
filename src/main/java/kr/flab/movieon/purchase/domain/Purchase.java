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
    private Long customerId;
    private PurchasedProduct purchasedProduct;
    private PurchaseStatus status;
    private PurchaseType type;

    private Purchase(Long customerId, PurchasedProduct purchasedProduct, PurchaseType type) {
        this.customerId = customerId;
        this.purchasedProduct = purchasedProduct;
        this.status = PurchaseStatus.PENDING;
        this.type = type;
    }

    public static Purchase pending(Long customerId, PurchasedProduct purchasedProduct,
        String type) {
        return new Purchase(customerId, purchasedProduct, PurchaseType.valueOf(type));
    }

    void setId(Long id) {
        this.id = id;
    }

    public void complete() {
        this.status = PurchaseStatus.SUCCESS;
        this.purchasedProduct.complete();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getTitle() {
        return purchasedProduct.getTitle();
    }

    public BigDecimal getPrice() {
        return this.purchasedProduct.getPurchasedPrice();
    }

    public Long getId() {
        return id;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public PurchaseType getType() {
        return type;
    }
}

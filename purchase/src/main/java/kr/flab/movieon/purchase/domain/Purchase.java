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
    private Purchaser purchaser;
    private PurchasedProduct purchasedProduct;
    private PurchaseStatus status;
    private PurchaseType type;

    private Purchase(Purchaser purchaser, PurchasedProduct purchasedProduct, PurchaseType type) {
        this.purchaser = purchaser;
        this.purchasedProduct = purchasedProduct;
        this.status = PurchaseStatus.PENDING;
        this.type = type;
    }

    public static Purchase pending(Purchaser purchaser, PurchasedProduct purchasedProduct,
        String type) {
        return new Purchase(purchaser, purchasedProduct, PurchaseType.valueOf(type));
    }

    void setId(Long id) {
        this.id = id;
    }

    public void complete() {
        this.status = PurchaseStatus.SUCCESS;
        this.purchasedProduct.complete();
    }

    public Long getPurchaserId() {
        return purchaser.getPurchaserId();
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

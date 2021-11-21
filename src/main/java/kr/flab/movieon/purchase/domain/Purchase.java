package kr.flab.movieon.purchase.domain;

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
    private PurchaseProduct purchaseProduct;
    private PurchaseStatus status;
    private PurchaseType type;

    private Purchase(Long customerId, PurchaseProduct purchaseProduct, PurchaseType type) {
        this.customerId = customerId;
        this.purchaseProduct = purchaseProduct;
        this.status = PurchaseStatus.PENDING;
        this.type = type;
    }

    public static Purchase pending(Long customerId, PurchaseProduct purchaseProduct, String type) {
        return new Purchase(customerId, purchaseProduct, PurchaseType.valueOf(type));
    }

    void setId(Long id) {
        this.id = id;
    }

    public void proceed(int availableDays) {
        this.status = PurchaseStatus.SUCCESS;
        this.purchaseProduct.purchased(availableDays);
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

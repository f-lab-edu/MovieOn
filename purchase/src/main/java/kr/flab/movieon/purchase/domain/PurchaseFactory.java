package kr.flab.movieon.purchase.domain;

import java.math.BigDecimal;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;

public final class PurchaseFactory {

    private PurchaseFactory() {
    }

    public static Purchase pending(Long productId, Long purchaserId, String title, BigDecimal price,
        int availableDays, String type) {
        var purchasedProduct = PurchasedProduct.create(productId, title, price, availableDays);
        var purchaser = new Purchaser(purchaserId);
        return new Purchase(purchaser, purchasedProduct, PurchaseType.valueOf(type));
    }
}

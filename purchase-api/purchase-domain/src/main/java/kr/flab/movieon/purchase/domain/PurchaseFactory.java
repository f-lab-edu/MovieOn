package kr.flab.movieon.purchase.domain;

import java.math.BigDecimal;
import java.time.Period;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;

public final class PurchaseFactory {

    private PurchaseFactory() {
    }

    public static Purchase pending(Long purchaserId, Long productId, String title,
        BigDecimal price, Period availableDays, String type) {
        var purchasedProduct = PurchasedProduct.create(
            productId, title,
            price, availableDays);
        var purchaser = new Purchaser(purchaserId);
        return new Purchase(purchaser, purchasedProduct,
            PurchaseType.valueOf(type));
    }
}

package kr.flab.movieon.purchase.domain;

import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;
import kr.flab.movieon.purchase.integrate.Product;

public final class PurchaseFactory {

    private PurchaseFactory() {
    }

    public static Purchase pending(Long purchaserId, Product product) {
        var purchasedProduct = PurchasedProduct.create(
            product.getProductId(), product.getTitle(),
            product.getPrice(), product.getAvailableDays());
        var purchaser = new Purchaser(purchaserId);
        return new Purchase(purchaser, purchasedProduct,
            PurchaseType.valueOf(product.getType()));
    }
}

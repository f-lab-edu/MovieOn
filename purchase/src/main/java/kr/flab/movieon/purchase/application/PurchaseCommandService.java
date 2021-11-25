package kr.flab.movieon.purchase.application;

import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductRepository;
import kr.flab.movieon.purchase.domain.PaymentProcessor;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.PurchaseRepository;
import kr.flab.movieon.purchase.domain.PurchasedProduct;
import kr.flab.movieon.purchase.domain.Purchaser;

public final class PurchaseCommandService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final PaymentProcessor paymentProcessor;

    public PurchaseCommandService(ProductRepository productRepository,
        PurchaseRepository purchaseRepository,
        PaymentProcessor paymentProcessor) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.paymentProcessor = paymentProcessor;
    }

    // PENDING -> 할인 적용 -> PAYED -> 결제 적용 -> PURCHASED
    public Purchase pending(Long productId, Long purchaserId) {
        var product = productRepository.findById(productId);
        var purchasedProduct = PurchasedProduct.create(
            product.getId(), product.getTitle(),
            product.getPrice(), product.getAvailableDays());
        return purchaseRepository.save(Purchase.pending(
            new Purchaser(purchaserId),
            purchasedProduct,
            product.getType().toString()));
    }

    public Purchase payed(Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId);
        paymentProcessor.payed(purchase);
        purchase.complete();
        return purchase;
    }
}

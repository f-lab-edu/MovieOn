package kr.flab.movieon.purchase.application;

import kr.flab.movieon.purchase.domain.PaymentProcessor;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.PurchaseFactory;
import kr.flab.movieon.purchase.domain.PurchaseRepository;
import kr.flab.movieon.purchase.integrate.ProductRepository;

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
        var purchase = PurchaseFactory.pending(purchaserId, product);
        return purchaseRepository.save(purchase);
    }

    public Purchase payed(Long purchaseId, String paymentType) {
        var purchase = purchaseRepository.findById(purchaseId);
        paymentProcessor.payed(purchase, paymentType);
        purchase.complete();
        return purchase;
    }
}

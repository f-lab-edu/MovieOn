package kr.flab.movieon.purchase.application;

import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductRepository;
import kr.flab.movieon.purchase.domain.PaymentProcessor;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.PurchaseRepository;
import kr.flab.movieon.purchase.domain.PurchasedProduct;

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

    // PENDING -> 할인 적용 -> PAYED -> PURCHASED
    public Purchase purchase(Long productId, Long customerId) {
        Product product = productRepository.findById(productId);
        Purchase purchase = purchaseRepository.save(Purchase.pending(customerId,
            PurchasedProduct.create(product.getId(), product.getTitle(),
                product.getPrice(), product.getAvailableDays()),
            product.getType().toString()));
        return paymentProcessor.payed(purchase);
    }
}

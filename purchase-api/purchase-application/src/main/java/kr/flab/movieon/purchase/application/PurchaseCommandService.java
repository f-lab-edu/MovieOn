package kr.flab.movieon.purchase.application;

import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.PurchaseFactory;
import kr.flab.movieon.purchase.domain.PurchaseRepository;
import kr.flab.movieon.purchase.integrate.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;

public final class PurchaseCommandService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final ApplicationEventPublisher publisher;

    public PurchaseCommandService(ProductRepository productRepository,
        PurchaseRepository purchaseRepository,
        ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.publisher = publisher;
    }

    // PENDING -> 할인 적용 -> PAYED -> 결제 적용 -> PURCHASED
    public Purchase pending(Long productId, Long purchaserId) {
        var product = productRepository.findById(productId);
        var purchase = PurchaseFactory.pending(purchaserId, product.getProductId(),
            product.getTitle(), product.getPrice(),
            product.getAvailableDays(), product.getType());
        return purchaseRepository.save(purchase);
    }

    public Purchase payed(Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId);
        purchase.complete();
        purchase.pollAllEvents().forEach(publisher::publishEvent);
        return purchase;
    }
}

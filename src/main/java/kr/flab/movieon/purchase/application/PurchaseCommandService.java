package kr.flab.movieon.purchase.application;

import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductRepository;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.PurchaseProduct;
import kr.flab.movieon.purchase.domain.PurchaseRepository;

public final class PurchaseCommandService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseCommandService(ProductRepository productRepository,
        PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase purchase(Long productId, Long customerId) {
        Product product = productRepository.findById(productId);
        Purchase purchase = Purchase.pending(customerId,
            PurchaseProduct.create(product.getId(), product.getPrice()),
            product.getType().toString());
        purchase.proceed(product.getAvailableDays());
        product.purchased();
        return purchaseRepository.save(purchase);
    }
}

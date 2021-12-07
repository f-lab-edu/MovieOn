package kr.flab.movieon.purchase.integrate;

public final class ProductRepositoryAdapter implements ProductRepository {

    private final kr.flab.movieon.product.domain.ProductRepository productRepository;

    public ProductRepositoryAdapter(
        kr.flab.movieon.product.domain.ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long productId) {
        var origin = productRepository.findById(productId);
        return Product.builder()
            .productId(origin.getId())
            .title(origin.getTitle())
            .type(origin.getType().toString())
            .price(origin.getPrice())
            .availableDays(origin.getAvailableDays())
            .build();
    }
}

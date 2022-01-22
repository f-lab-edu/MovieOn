package kr.flab.movieon.order.domain;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

public class OrderValidator {

    private final ProductRepository productRepository;
    private final PointManager pointManager;

    public OrderValidator(ProductRepository productRepository, PointManager pointManager) {
        this.productRepository = productRepository;
        this.pointManager = pointManager;
    }

    public void validate(Order order) {
        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            throw new IllegalStateException("주문 항목이 비어 있습니다");
        }

        var products = getProducts(order);

        for (OrderProduct orderProduct : order.getProducts()) {
            validateOrderProduct(orderProduct, products.get(orderProduct.getProductId()));
        }

        pointManager.canUseOfPoint(order.getCustomer().getAccountId(), order.getUseOfPoint());
    }

    private void validateOrderProduct(OrderProduct orderProduct, Product product) {
        if (!product.isSatisfiedBy(orderProduct.getName(), orderProduct.getPrice())) {
            throw new IllegalStateException("상품 정보가 변경되었습니다");
        }
    }

    private Map<Long, Product> getProducts(Order order) {
        return productRepository.findAllById(order.getProductIds())
            .stream()
            .collect(toMap(Product::getId, identity()));
    }
}

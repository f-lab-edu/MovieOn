package kr.flab.movieon.order.integrate;

import java.util.List;
import kr.flab.movieon.order.domain.Product;
import kr.flab.movieon.order.domain.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public final class ProductRepositoryAdapterInOrderModule implements ProductRepository {

    @Override
    public List<Product> findAllById(List<Long> productIds) {
        return null;
    }
}

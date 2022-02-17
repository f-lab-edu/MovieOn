package kr.flab.movieon.product.infrastructure;

import kr.flab.movieon.product.infrastructure.jpa.CategoryRepositoryAdapter;
import kr.flab.movieon.product.infrastructure.jpa.ItemRepositoryAdapter;
import kr.flab.movieon.product.infrastructure.jpa.ProductRepositoryAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
    ProductRepositoryAdapter.class,
    ItemRepositoryAdapter.class,
    CategoryRepositoryAdapter.class
})
@Configuration
public class ProductModuleConfiguration {

}

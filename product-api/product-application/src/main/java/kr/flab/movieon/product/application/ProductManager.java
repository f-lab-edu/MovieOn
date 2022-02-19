package kr.flab.movieon.product.application;

import java.time.Duration;
import java.time.Year;
import kr.flab.movieon.product.domain.Category;
import kr.flab.movieon.product.domain.CategoryRepository;
import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductContentsDetail;
import kr.flab.movieon.product.domain.ProductContentsDetail.Rate;
import kr.flab.movieon.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductManager {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductManager(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void register(RegisterProductCommand command) {
        var category = categoryRepository.findById(command.getCategoryId());
        var product = mapFrom(command, category);
        productRepository.save(product);
    }

    private Product mapFrom(RegisterProductCommand command, Category category) {
        return Product.builder()
            .category(category)
            .name(command.getName())
            .description(command.getDescription())
            .thumbnails(command.getThumbnails())
            .contentsDetail(ProductContentsDetail.builder()
                .rate(Rate.findByRate(command.getRateDescription()))
                .release(Year.of(command.getRelease()))
                .director(command.getDirectors())
                .actors(command.getActors())
                .runningTime(Duration.ofMinutes(command.getRunningTime()))
                .images(command.getImages())
                .build())
            .build();
    }

}

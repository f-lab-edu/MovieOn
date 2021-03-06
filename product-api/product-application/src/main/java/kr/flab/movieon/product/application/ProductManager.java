package kr.flab.movieon.product.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import kr.flab.movieon.product.domain.Category;
import kr.flab.movieon.product.domain.CategoryRepository;
import kr.flab.movieon.product.domain.Item;
import kr.flab.movieon.product.domain.Item.ItemType;
import kr.flab.movieon.product.domain.ItemOption;
import kr.flab.movieon.product.domain.ItemRepository;
import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductBuilder;
import kr.flab.movieon.product.domain.ProductContentsDetail.Rate;
import kr.flab.movieon.product.domain.ProductContentsDetailBuilder;
import kr.flab.movieon.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductManager {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    public ProductManager(ProductRepository productRepository,
        CategoryRepository categoryRepository,
        ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void register(RegisterProductCommand command) {
        var category = categoryRepository.findById(command.getCategoryId());
        var product = productRepository.save(mapFrom(command, category));
        var item = mapFrom(product.getId(), command.getItem());
        itemRepository.save(item);
    }

    private Item mapFrom(Long productId, RegisterItemCommand command) {
        return new Item(productId, command.getName(), command.getAvailableDays(),
            BigDecimal.valueOf(command.getBasePrice()), ItemType.valueOf(command.getType()),
            command.getOptions().stream()
                .map(o -> new ItemOption(o.getOptionName(), BigDecimal.valueOf(o.getSalesPrice())))
                .collect(Collectors.toSet()));
    }

    private Product mapFrom(RegisterProductCommand command, Category category) {
        return ProductBuilder.builder()
            .category(category)
            .name(command.getName())
            .description(command.getDescription())
            .thumbnails(command.getThumbnails())
            .contentsDetail(ProductContentsDetailBuilder.builder()
                .rate(Rate.findByRate(command.getRateDescription()))
                .releasedAt(LocalDate.of(command.getYear(), command.getMonth(), command.getDays()))
                .director(command.getDirectors())
                .actors(command.getActors())
                .runningTime(command.getRunningTime())
                .images(command.getImages())
                .build())
            .build();
    }

}

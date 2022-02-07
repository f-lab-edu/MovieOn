package kr.flab.movieon.product.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
@Builder
@Getter
public class Product {

    private Long id;
    private String name;
    private String description;
    private String thumbnails;
    private Category category;
    private ProductContentsDetail contentsDetail;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

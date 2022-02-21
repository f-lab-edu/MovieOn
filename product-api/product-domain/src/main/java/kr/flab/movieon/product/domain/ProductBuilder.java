package kr.flab.movieon.product.domain;

public final class ProductBuilder {

    private String name;
    private String description;
    private String thumbnails;
    private Category category;
    private ProductContentsDetail contentsDetail;

    private ProductBuilder() {

    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder thumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
        return this;
    }

    public ProductBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder contentsDetail(ProductContentsDetail contentsDetail) {
        this.contentsDetail = contentsDetail;
        return this;
    }

    public Product build() {
        return new Product(name, description, thumbnails, category, contentsDetail);
    }
}

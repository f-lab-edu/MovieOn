package kr.flab.movieon.product.domain;

public interface CategoryRepository {

    Category save(Category entity);

    Category findById(Long categoryId);
}

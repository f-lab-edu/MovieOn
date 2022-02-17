package kr.flab.movieon.product.infrastructure.jpa;

import kr.flab.movieon.product.domain.Category;
import kr.flab.movieon.product.domain.CategoryRepository;

public final class CategoryRepositoryAdapter implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoryRepositoryAdapter(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    public Category save(Category entity) {
        return jpaCategoryRepository.save(entity);
    }

    @Override
    public Category findById(Long categoryId) {
        return jpaCategoryRepository.findById(categoryId)
            .orElseThrow(IllegalArgumentException::new);
    }
}

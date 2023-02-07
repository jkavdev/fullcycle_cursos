package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.utils.SpecificationUtils.like;

@Service
public class CategoryMysqlGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    public CategoryMysqlGateway(CategoryRepository categoryRepository) {
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
    }

    @Override
    public Category create(Category aCategory) {
        return save(aCategory);
    }

    @Override
    public void deleteById(CategoryID anId) {
        String anIdValue = anId.getValue();
        if (this.categoryRepository.existsById(anIdValue)) {
            this.categoryRepository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return this.categoryRepository.findById(anId.getValue())
                .map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Category update(Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(ter -> !ter.isBlank())
                .map(ter -> {
                    final Specification<CategoryJpaEntity> nameLike = like("name", ter);
                    final Specification<CategoryJpaEntity> descriptionLike = like("description", ter);
                    return nameLike.or(descriptionLike);
                })
                .orElse(null);

        final var pageResult =
                this.categoryRepository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }

    private Category save(final Category aCategory) {
        return this.categoryRepository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }
}

package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.update.UpdateCategoryCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.update.UpdateCategoryOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.CategoryAPI;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CategoryResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.UpdateCategoryRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest anInput) {
        final var aCommand = CreateCategoryCommand.with(
                anInput.name(),
                anInput.description(),
                anInput.active() != null && anInput.active()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity
                        .created(URI.create("/categories/" + output.id()))
                        .body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listCategoriesUseCase.execute(
                        new SearchQuery(page, perPage, search, sort, direction)
                )
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(final String id) {
        return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest anInput) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                anInput.name(),
                anInput.description(),
                anInput.active() != null && anInput.active()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }

}

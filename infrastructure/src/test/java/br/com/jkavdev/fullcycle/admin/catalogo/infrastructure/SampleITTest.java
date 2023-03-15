package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure;

import br.com.jkavdev.fullcycle.admin.catalogo.IntegrationTest;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleITTest {

    @Autowired
    private ListCategoriesUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjects() {
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(categoryRepository);
    }
}

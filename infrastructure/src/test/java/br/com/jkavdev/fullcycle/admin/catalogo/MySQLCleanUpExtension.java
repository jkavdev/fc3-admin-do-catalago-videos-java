package br.com.jkavdev.fullcycle.admin.catalogo;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence.VideoRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

public class MySQLCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {

        final var appContext = SpringExtension.getApplicationContext(context);

//        executando cleanup de acordo com a ordem das entidades que devem ser removidas primeiro
//        por isso pegamos o repositorio e indicamos manualmente
        cleanUp(List.of(
                appContext.getBean(VideoRepository.class),
                appContext.getBean(CastMemberRepository.class),
                appContext.getBean(GenreRepository.class),
                appContext.getBean(CategoryRepository.class)
        ));
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
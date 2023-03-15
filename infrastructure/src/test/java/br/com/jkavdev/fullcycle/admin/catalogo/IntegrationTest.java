package br.com.jkavdev.fullcycle.admin.catalogo;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

// indicando que sera usada nas classes de testes
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
// definindo profile de teste
@ActiveProfiles("integration")
// para testes integrados precisamos do spring com todo o seu contexto
@SpringBootTest(classes = WebServerConfig.class)
// vinculando a extension para a limpeza de dados a cada teste com o jupiter
@ExtendWith(MySQLCleanUpExtension.class)
@Tag("integrationTests")
public @interface IntegrationTest {

}
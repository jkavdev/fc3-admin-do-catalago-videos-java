package br.com.jkavdev.fullcycle.admin.catalogo;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("integration")
@SpringBootTest(classes = WebServerConfig.class)
@Tag("integrationTests")
public @interface AmqpTest {
}

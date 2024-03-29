package br.com.jkavdev.fullcycle.admin.catalogo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

// indicando que sera usada nas classes de testes
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
// definindo profile de teste
@ActiveProfiles("test-integration")
// configurando apenas o contexto jpa do spring
@DataJpaTest
// escaneando apenas os beans jpa
@ComponentScan(
        basePackages = "br.com.jkavdev.fullcycle.admin.catalogo",
        // informando para nao usar as classes beans configuradas no projeto
        // utilizando apenas as classes que forem indicadas abaixo, no caso os repositorios
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*MysqlGateway")
        }
)
// vinculando a extension para a limpeza de dados a cada teste com o jupiter
@ExtendWith(MySQLCleanUpExtension.class)
@Tag("integrationTests")
public @interface MysqlGatewayTest {

}
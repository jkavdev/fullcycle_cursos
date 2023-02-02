package br.com.jkavdev.fullcycle.admin.catalogo;

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
@ActiveProfiles("test")
// configurando apenas o contexto jpa do spring
@DataJpaTest
// escaneando apenas os beans jpa
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*MysqlGateway")
})
// vinculando a extension para a limpeza de dados a cada teste com o jupiter
@ExtendWith(MySQLCleanUpExtension.class)
public @interface MysqlGatewayTest {

}




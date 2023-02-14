package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember;

import br.com.jkavdev.fullcycle.admin.catalogo.MysqlGatewayTest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MysqlGatewayTest
class CastMemberMysqlGatewayTest {

    @Autowired
    private CastMemberMysqlGateway castMemberMysqlGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        Assertions.assertNotNull(castMemberMysqlGateway);
        Assertions.assertNotNull(castMemberRepository);
    }
}
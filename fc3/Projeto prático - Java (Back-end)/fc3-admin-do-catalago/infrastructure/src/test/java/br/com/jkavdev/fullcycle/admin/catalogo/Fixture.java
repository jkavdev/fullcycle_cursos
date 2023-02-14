package br.com.jkavdev.fullcycle.admin.catalogo;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberType;
import com.github.javafaker.Faker;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberType.ACTOR;
import static br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberType.DIRECTOR;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static final class CastMember {
        public static CastMemberType type() {
            return FAKER.options()
                    .option(
                            ACTOR, DIRECTOR
                    );
        }
    }
}

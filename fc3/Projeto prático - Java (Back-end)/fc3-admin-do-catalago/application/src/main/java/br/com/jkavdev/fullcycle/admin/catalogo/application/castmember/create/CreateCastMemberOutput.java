package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;

public record CreateCastMemberOutput(
        String id
) {

    public static CreateCastMemberOutput from(
            final CastMember aMember
    ) {
        return new CreateCastMemberOutput(aMember.getId().getValue());
    }

    public static CreateCastMemberOutput from(final String anId) {
        return new CreateCastMemberOutput(anId);
    }
}

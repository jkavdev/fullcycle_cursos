package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;

public record UpdateCastMemberOutput(
        String id
) {

    public static UpdateCastMemberOutput from(String anId) {
        return new UpdateCastMemberOutput(anId);
    }

    public static UpdateCastMemberOutput from(
            final CastMember aMember
    ) {
        return new UpdateCastMemberOutput(aMember.getId().getValue());
    }
}

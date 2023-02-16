package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.CastMemberAPI;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
    }

    @Override
    public ResponseEntity<?> create(CreateCastMemberRequest anInput) {
        final var aCommand = CreateCastMemberCommand.with(anInput.name(), anInput.type());

        final var output = createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }
}
